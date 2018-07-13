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

    public static boolean encrypt(String key, File inputFile, File outputFile)
    {
        boolean status = doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
        return status;
    }

    public static boolean decrypt(String key, File inputFile, File outputFile)
    {
        boolean status = doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
        return status;
    }

    private static boolean doCrypto(int cipherMode, String key, File inputFile, File outputFile) {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}