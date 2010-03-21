package crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream keyStoreIn = new FileInputStream(KEYSTORE);
            keyStore.load(keyStoreIn, KEYSTORE_PASSWORD.toCharArray());
            Key key = keyStore.getKey(KEY_ALIAS, KEY_PASSWORD.toCharArray());
            
            for (int i = 0; i < args.length; ++i)
            {
                encrypt(key, args[i]);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void encrypt(Key key, String filename) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException
    {
        // get cipher object for password-based encryption
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        
        // initialize cipher for encryption, without supplying
        // any parameters. Here, "myKey" is assumed to refer 
        // to an already-generated key.
        cipher.init(Cipher.ENCRYPT_MODE, key);
        
        FileInputStream fileIn = new FileInputStream(filename);
        FileOutputStream fileOut = new FileOutputStream(filename + ".enc");
        CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher); 

        byte[] buffer = new byte[8];
        int len = fileIn.read(buffer);
        while (len != -1) 
        {
            cipherOut.write(buffer, 0, 1);
            len = fileIn.read(buffer);
        }
        cipherOut.close();
        fileIn.close();
        fileOut.close();
    }
    
}
