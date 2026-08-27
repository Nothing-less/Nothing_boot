package mu.nothingless.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import mu.nothingless.exception.CryptoException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

/**
 *   AES-256-GCM 工具
 * - 每次加密随机 12 字节 IV
 * - 输出格式：v1:<base64(iv)>:<base64(ciphertext+tag)>
 */
public final class AesGcmUtil {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_BITS = 128;
    private static final int IV_BYTES = 12;
    private static final int KEY_BITS = 256;

    private static final String VERSION = "v1";
    private static final String SEP = ":";

    private final SecretKey secretKey;
    private final SecureRandom secureRandom;

    private AesGcmUtil(SecretKey secretKey) {
        if (secretKey == null) {
            throw new CryptoException("SecretKey must not be null", null);
        }
        this.secretKey = secretKey;
        this.secureRandom = new SecureRandom();
    }

    /* ----------------- 工厂方法 ----------------- */

    /** 从 Base64 编码的 32 字节密钥构造（推荐：KMS / Vault 下发） */
    public static AesGcmUtil fromBase64Key(String base64Key) {
        byte[] raw = Base64.getDecoder().decode(base64Key);
        if (raw.length != 32) {
            throw new CryptoException("AES-256 key must be 32 bytes, got " + raw.length, null);
        }
        return new AesGcmUtil(new SecretKeySpec(raw, "AES"));
    }

    /** 从密码派生（仅适合离线/本地加密，不建议做业务主密钥） */
    public static AesGcmUtil fromPassword(String password, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 600_000, KEY_BITS);
            byte[] key = factory.generateSecret(spec).getEncoded();
            return new AesGcmUtil(new SecretKeySpec(key, "AES"));
        } catch (Exception e) {
            throw new CryptoException("PBKDF2 key derive failed", e);
        }
    }

    /** 生成新密钥（用于初始化 KMS / 本地 KeyStore） */
    public static SecretKey generateKey() {
        try {
            KeyGenerator gen = KeyGenerator.getInstance("AES");
            gen.init(KEY_BITS, new SecureRandom());
            return gen.generateKey();
        } catch (Exception e) {
            throw new CryptoException("AES key generation failed", e);
        }
    }

    /* ----------------- 加密 / 解密 ----------------- */

    public String encryptToString(String plaintext) {
        byte[] cipher = encrypt(plaintext.getBytes(StandardCharsets.UTF_8));
        byte[] iv = extractIv(cipher);
        byte[] ct = new byte[cipher.length - IV_BYTES];
        System.arraycopy(cipher, IV_BYTES, ct, 0, ct.length);

        return VERSION + SEP
                + Base64.getEncoder().encodeToString(iv) + SEP
                + Base64.getEncoder().encodeToString(ct);
    }

    public byte[] encrypt(byte[] plaintext) {
        try {
            byte[] iv = new byte[IV_BYTES];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_BITS, iv));
            byte[] ct = cipher.doFinal(plaintext);

            ByteBuffer buf = ByteBuffer.allocate(IV_BYTES + ct.length);
            return buf.put(iv).put(ct).array();
        } catch (Exception e) {
            throw new CryptoException("AES encrypt failed", e);
        }
    }

    public String decryptToString(String token) {
        try {
            String[] parts = token.split("\\" + SEP);
            if (parts.length != 3 || !VERSION.equals(parts[0])) {
                throw new CryptoException("Invalid ciphertext format", null);
            }
            byte[] iv = Base64.getDecoder().decode(parts[1]);
            byte[] ct = Base64.getDecoder().decode(parts[2]);

            ByteBuffer buf = ByteBuffer.allocate(iv.length + ct.length);
            buf.put(iv).put(ct);

            byte[] plain = decrypt(buf.array());
            return new String(plain, StandardCharsets.UTF_8);
        } catch (CryptoException e) {
            throw e;
        } catch (Exception e) {
            throw new CryptoException("AES decrypt failed", e);
        }
    }

    public byte[] decrypt(byte[] ivAndCipher) {
        try {
            byte[] iv = extractIv(ivAndCipher);
            byte[] ct = new byte[ivAndCipher.length - IV_BYTES];
            System.arraycopy(ivAndCipher, IV_BYTES, ct, 0, ct.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_BITS, iv));
            return cipher.doFinal(ct);
        } catch (Exception e) {
            // GCM tag 校验失败会到这里：密文被篡改或密钥错误
            throw new CryptoException("AES decrypt failed (bad tag/key)", e);
        }
    }

    private static byte[] extractIv(byte[] ivAndCipher) {
        if (ivAndCipher.length < IV_BYTES) {
            throw new CryptoException("Ciphertext too short", null);
        }
        byte[] iv = new byte[IV_BYTES];
        System.arraycopy(ivAndCipher, 0, iv, 0, IV_BYTES);
        return iv;
    }
}
