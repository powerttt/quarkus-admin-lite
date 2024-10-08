package qal.fast.utils.auth;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class PBKDF2Test {

    @Inject
    PBKDF2Encoder passwordEncoder;

    //  123456  pokEpKIz+sqBhaXdqUFAIs/7SqsmzhDzaJNmIdtZWCk=
    @Test
    public void testEncode() {
        String password = "123456";
        String encode = passwordEncoder.encode(password);
        Log.info("encode: " + encode);

    }
}
