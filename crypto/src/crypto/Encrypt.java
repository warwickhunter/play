package crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

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
    private static final String KEYSTORE          = "crypto.ks";
    private static final String KEYSTORE_PASSWORD = "crypto";
    private static final String KEY_ALIAS         = "crypto";
    private static final String KEY_PASSWORD      = "crypto";

    /**
     * @param args
     *      element[0] is the file name to encrypt
     * @throws KeyStoreException 
     * @throws IOException 
     * @throws CertificateException 
     * @throws NoSuchAlgorithmException 
     * @throws UnrecoverableKeyException 
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     * @throws NoSuchPaddingException 
     * @throws InvalidKeyException 
     */
    public static void main(String[] args)
    {
        try
        {
        	Security.addProvider(new BouncyCastleProvider());
        	KeyGenerator keyGen = KeyGenerator.getInstance("DES", "BC");
        	keyGen.init(new SecureRandom());
        	Key key = keyGen.generateKey();
        	
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            FileInputStream keyStoreIn = new FileInputStream(KEYSTORE);
//            keyStore.load(keyStoreIn, KEYSTORE_PASSWORD.toCharArray());
//            keyStoreIn.close();
            
//            Key    key    = keyStore.getKey(KEY_ALIAS, KEY_PASSWORD.toCharArray());
//            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding"); // RSA");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding", "BC");

            for (int i = 0; i < args.length; ++i)
            {
                encrypt(cipher, key, args[i], args[i] + ".crypt");
                decrypt(cipher, key, args[i] + ".crypt", args[i] + ".decrypt");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void encrypt(Cipher cipher, Key key, String sourceFile, String destinationFile) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException
    {
        System.out.printf("encrypt %s to %s %n", sourceFile, destinationFile);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        FileInputStream fileIn = new FileInputStream(sourceFile);
        FileOutputStream fileOut = new FileOutputStream(destinationFile);
        CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher); 

        byte[] buffer = new byte[16];
        int totalLen = 0;
        for (int len = fileIn.read(buffer); len != -1; len = fileIn.read(buffer)) 
        {
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
        for (int len = cipherIn.read(buffer); len != -1; len = cipherIn.read(buffer)) 
        {
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
