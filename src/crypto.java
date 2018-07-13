import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

public class crypto
{
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static String encrypt(String toBeEncrypted)
    {
        return doCrypto(Cipher.ENCRYPT_MODE, toBeEncrypted);
    }

    public static String decrypt(String toBeDecrypted)
    {
        return doCrypto(Cipher.DECRYPT_MODE, toBeDecrypted);
    }

    private static String doCrypto(int cipherMode, String input) {
        try {
            String key = "I have a crush on Mahi Jain of C";
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            int inputLength = input.length();
            while (true)
            {
                if(inputLength % 16 != 0)
                {
                    inputLength++;
                    input+=" ";
                }
                else
                {
                    break;
                }
            }

            byte[] outputBytes = cipher.doFinal(input.getBytes());

            return outputBytes.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "NULL";
        }
    }
}