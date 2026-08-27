package mu.nothingless.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
public final class KeyLoader {

    private KeyLoader() {}

    public static SecretKey loadHmacKey(String base64Secret) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
    }

    public static KeyPair loadRsaKeyPair(String base64PrivateKey, String base64PublicKey) {
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");

            byte[] priBytes = Decoders.BASE64.decode(base64PrivateKey);
            PrivateKey privateKey = factory.generatePrivate(new PKCS8EncodedKeySpec(priBytes));

            byte[] pubBytes = Decoders.BASE64.decode(base64PublicKey);
            PublicKey publicKey = factory.generatePublic(new X509EncodedKeySpec(pubBytes));

            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new IllegalArgumentException("RSA 密钥加载失败，请确认是 Base64 编码的 DER 格式", e);
        }
    }

    public static KeyPair loadEcKeyPair(String base64PrivateKey, String base64PublicKey) {
        try {
            KeyFactory factory = KeyFactory.getInstance("EC");

            byte[] priBytes = Decoders.BASE64.decode(base64PrivateKey);
            PrivateKey privateKey = factory.generatePrivate(new PKCS8EncodedKeySpec(priBytes));

            byte[] pubBytes = Decoders.BASE64.decode(base64PublicKey);
            PublicKey publicKey = factory.generatePublic(new X509EncodedKeySpec(pubBytes));

            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new IllegalArgumentException("EC 密钥加载失败，请确认是 Base64 编码的 DER 格式", e);
        }
    }
}
