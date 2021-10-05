package pl.training.shop.commons;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FeignTokenInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaders.AUTHORIZATION, getToken());
    }

    private String getToken() {
        KeycloakAuthenticationToken authentication;
        try {
            authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        } catch (ClassCastException exception) {
            return "";
        }
        var principal = (KeycloakPrincipal) authentication.getPrincipal();
        return "bearer " + principal.getKeycloakSecurityContext().getTokenString();
    }

}
