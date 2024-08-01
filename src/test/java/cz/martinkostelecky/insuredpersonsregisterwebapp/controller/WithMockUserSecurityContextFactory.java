package cz.martinkostelecky.insuredpersonsregisterwebapp.controller;

import cz.martinkostelecky.insuredpersonsregisterwebapp.security.UserPrincipal;
import cz.martinkostelecky.insuredpersonsregisterwebapp.security.UserPrincipalAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;

@RequiredArgsConstructor
public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {

        var authorities = Arrays.stream(annotation.authorities())
                .map(SimpleGrantedAuthority::new)
                .toList();

        var principal = UserPrincipal.builder()
                .userId(annotation.id())
                .email("user@test.cz")
                .authorities(authorities)
                .build();

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UserPrincipalAuthenticationToken(principal));

        return context;
    }
}
