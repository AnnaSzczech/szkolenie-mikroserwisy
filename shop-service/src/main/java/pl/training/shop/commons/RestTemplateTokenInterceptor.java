package pl.training.shop.commons;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class RestTemplateTokenInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().set(HttpHeaders.AUTHORIZATION, getToken());
        return clientHttpRequestExecution.execute(httpRequest, body);
    }

    private String getToken() {
        var authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        var principal = (KeycloakPrincipal) authentication.getPrincipal();
        return "bearer " + principal.getKeycloakSecurityContext().getTokenString();
    }

}
