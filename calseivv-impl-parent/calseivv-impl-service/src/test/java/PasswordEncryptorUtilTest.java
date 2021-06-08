import com.calseivv.project.util.PasswordEncryptorUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PasswordEncryptorUtilTest {

    @Test
    @Ignore
    public void testUtil() {
        String str = "uwu";
        String encryptedStr = PasswordEncryptorUtil.encrypt(str);
        System.out.println("Encrypted: " + encryptedStr);
        String decryptedStr = PasswordEncryptorUtil.decrypt(encryptedStr);
        System.out.println("Decrypted: " + decryptedStr);
    }

    @Test
    @Ignore
    public void generateUUID() {
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID());
        }
    }

    @Test
    @Ignore
    public void dateTest() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssSS");
        String text = formatter.format(new Date());
        System.out.println(text);
    }

}
