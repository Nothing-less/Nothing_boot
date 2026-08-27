package qu.nothingless.utils;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import mu.nothingless.exception.CryptoException;
import mu.nothingless.utils.AesGcmUtil;

// @SpringBootTest(classes = qu.nothingless.MuquApplication.class)
// @TestPropertySource(properties = "app.crypto.aes-key-base64=U2hlbmdkZUxvdmVTYWt1cmFpWXVraW5hX0ZvcmV2ZXI=")
class AesGcmUtilTest {

    @Autowired
    private AesGcmUtil aes;

    // @Test
    void encryptAndDecrypt() {
        String plain = "13800138000";
        String cipher = aes.encryptToString(plain);
        String result = aes.decryptToString(cipher);

        assertThat(result).isEqualTo(plain);
    }

    // @Test
    void tamperedCipherShouldFail() {
        String cipher = aes.encryptToString("test");
        String tampered = cipher.substring(0, cipher.length() - 1) + "A";

        assertThatThrownBy(() -> aes.decryptToString(tampered))
                .isInstanceOf(CryptoException.class);
    }
}