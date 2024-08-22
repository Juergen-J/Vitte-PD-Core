package ru.vitte.online.helpdesk.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class TokenExtractor {
    public static Collection<GrantedAuthority> extractRoles(OidcUser oidcUser) {
        if (oidcUser == null) {
            return List.of();
        }

        Map<String, Object> claims = oidcUser.getIdToken().getClaims();

        if (!claims.containsKey("realm_access")) {
            return List.of();
        }

        Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");

        if (!realmAccess.containsKey("roles")) {
            return List.of();
        }

        List<String> roles = (List<String>) realmAccess.get("roles");

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    public static boolean isEmployee(OidcUser oidcUser) {
        var roles = extractRoles(oidcUser);
        return roles.contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    }

    public static boolean isEAdmin(OidcUser oidcUser) {
        var roles = extractRoles(oidcUser);
        return roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }


    public static String extractUserId(OidcUser oidcUser) {
        Map<String, Object> claims = oidcUser.getIdToken().getClaims();
        return (String) claims.get("sub");
    }
}
