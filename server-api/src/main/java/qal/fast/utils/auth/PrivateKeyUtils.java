package qal.fast.utils.auth;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

public class PrivateKeyUtils {

    public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
        try (InputStream contentIS = PrivateKeyUtils.class.getResourceAsStream(pemResName)) {
            String content = getString(pemResName, contentIS);
            // 去除 PEM 文件中的 "-----BEGIN PRIVATE KEY-----" 和 "-----END PRIVATE KEY-----"
            content = content.replaceAll("-+BEGIN PRIVATE KEY-+", "").replaceAll("-+END PRIVATE KEY-+", "")
                    .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(content);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        }
    }

    private static String getString(String pemResName, InputStream contentIS) throws IOException {
        if (contentIS == null) {
            throw new IOException("Resource not found: " + pemResName);
        }

        // 使用 Reader 读取内容，避免大文件一次性加载到内存
        StringBuilder contentBuilder = new StringBuilder();
        try (Reader reader = new InputStreamReader(contentIS, StandardCharsets.UTF_8)) {
            int c;
            while ((c = reader.read()) != -1) {
                contentBuilder.append((char) c);
            }
        }

        return contentBuilder.toString();
    }

    public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    public static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    public static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }
}
