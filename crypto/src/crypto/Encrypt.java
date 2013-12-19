package crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Java Cryptography experiment. Encrypt a file.
 * 
 * @author Warwick Hunter
 * @since  2010-03-21
 */
public class Encrypt
{
    private static boolean USE_RANDOM_KEY = false;
    private static boolean USE_KNOWN_KEY  = true;
    
    private static final String KEYSTORE          = "crypto.ks";
    private static final String KEYSTORE_PASSWORD = "crypto";
    private static final String KEY_ALIAS         = "crypto";
    private static final String KEY_PASSWORD      = "crypto";

    /**
     * @param args args[0] is the file name to encrypt
     */
    public static void main(String[] args) {
        try {
        	Security.addProvider(new BouncyCastleProvider());

        	Key key = null;
            Cipher cipher = null;
        	if (USE_RANDOM_KEY) {
            	KeyGenerator keyGen = KeyGenerator.getInstance("DES", "BC");
            	keyGen.init(new SecureRandom());
            	key = keyGen.generateKey();
            	cipher = Cipher.getInstance("DES/CBC/PKCS5Padding", "BC");
        	}
        	
        	if (USE_KNOWN_KEY) {
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                FileInputStream keyStoreIn = new FileInputStream(KEYSTORE);
                keyStore.load(keyStoreIn, KEYSTORE_PASSWORD.toCharArray());
                keyStoreIn.close();
                key = keyStore.getKey(KEY_ALIAS, KEY_PASSWORD.toCharArray());
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
        	}

            for (int i = 0; i < args.length; ++i) {
                encrypt(cipher, key, args[i], args[i] + ".crypt");
                decrypt(cipher, key, args[i] + ".crypt", args[i] + ".decrypt");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void encrypt(Cipher cipher, Key key, String sourceFile, String destinationFile) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException
    {
        System.out.printf("encrypt %s to %s %n", sourceFile, destinationFile);
        System.out.printf("key=%s%n", key.getEncoded());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        FileInputStream fileIn = new FileInputStream(sourceFile);
        FileOutputStream fileOut = new FileOutputStream(destinationFile);
        CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher); 

        byte[] buffer = new byte[16];
        int totalLen = 0;
        for (int len = fileIn.read(buffer); len != -1; len = fileIn.read(buffer)) {
            cipherOut.write(buffer, 0, len);
            totalLen += len;
        }
        cipherOut.close();
        fileIn.close();
        fileOut.close();
        System.out.printf("    %d bytes read %n", totalLen);
    }
    
    private static void decrypt(Cipher cipher, Key key, String sourceFile, String destinationFile) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException
    {
        System.out.printf("decrypt %s to %s ...", sourceFile, destinationFile);
        cipher.init(Cipher.DECRYPT_MODE, key);
        FileInputStream fileIn = new FileInputStream(sourceFile);
        FileOutputStream fileOut = new FileOutputStream(destinationFile);
        CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher); 

        byte[] buffer = new byte[8];
        int totalLen = 0;
        for (int len = cipherIn.read(buffer); len != -1; len = cipherIn.read(buffer)) {
            System.out.printf("read %d bytes %n", len);
            fileOut.write(buffer, 0, len);
            totalLen += len;
        }
        cipherIn.close();
        fileIn.close();
        fileOut.close();
        System.out.printf(" %d bytes read %n", totalLen);
    }
}
