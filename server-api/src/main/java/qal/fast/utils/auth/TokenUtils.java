package qal.fast.utils.auth;

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
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import qal.fast.enums.MemberRoleEnum;

public class TokenUtils {

    public static String generateToken(String username, String roles, Long iat, Long exp, String issuer)
            throws Exception {
        String privateKeyLocation = "/privateKey.pem";
        PrivateKey privateKey = PrivateKeyUtils.readPrivateKey(privateKeyLocation);

        JwtClaimsBuilder claimsBuilder = Jwt.claims();

        Set<String> groups = Arrays.stream(roles.split(",")).collect(Collectors.toSet());

        claimsBuilder.issuer(issuer);
        claimsBuilder.subject(username);
        claimsBuilder.issuedAt(iat);
        claimsBuilder.expiresAt(exp);
        claimsBuilder.groups(groups);

        return claimsBuilder.jws().keyId(privateKeyLocation).sign(privateKey);
    }

    public static String generateToken(String username, Set<MemberRoleEnum> roles, Long iat, Long exp, String issuer)
            throws Exception {
        String privateKeyLocation = "/privateKey.pem";
        PrivateKey privateKey = PrivateKeyUtils.readPrivateKey(privateKeyLocation);

        JwtClaimsBuilder claimsBuilder = Jwt.claims();

        Set<String> groups = new HashSet<>();
        for (MemberRoleEnum role : roles)
            groups.add(role.getRole());

        claimsBuilder.issuer(issuer);
        claimsBuilder.subject(username);
        claimsBuilder.issuedAt(iat);
        claimsBuilder.expiresAt(exp);
        claimsBuilder.groups(groups);

        return claimsBuilder.jws().keyId(privateKeyLocation).sign(privateKey);
    }

}
