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

    @Test
    @Ignore
    public void answerTest() {
        String answer = "Inertia,Acceleration,Action Reaction";
        String userAnswer = "inertia,acceleration,action reaction";
        String[] multipleAnswers = answer.split(",");
        int multipleAnswerSize = multipleAnswers.length;
        int correctAnswerCtr = 0;
        for (String indivAnswer : multipleAnswers) {
            System.out.println("ANSWER: " + indivAnswer);
            System.out.println("indivAnswer.toLowerCase(): " + indivAnswer.toLowerCase());
            System.out.println("userAnswer.toLowerCase(): " + userAnswer.toLowerCase());
            System.out.println((userAnswer.equalsIgnoreCase(indivAnswer)));
            System.out.println(userAnswer.toLowerCase().contains(indivAnswer.toLowerCase()));
            if ((userAnswer.equalsIgnoreCase(indivAnswer) || userAnswer.toLowerCase().contains(indivAnswer.toLowerCase())) && !userAnswer.isEmpty()) {
                correctAnswerCtr++;
            }
        }
        System.out.println(correctAnswerCtr);

    }

}
