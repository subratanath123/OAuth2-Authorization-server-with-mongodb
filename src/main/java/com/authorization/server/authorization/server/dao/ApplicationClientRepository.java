package com.authorization.server.authorization.server.dao;

import com.authorization.server.authorization.server.entity.application.ApplicationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Repository;

import java.util.Random;
import java.util.UUID;

@Repository
@Primary
public class ApplicationClientRepository implements RegisteredClientRepository {

    @Autowired
    private ApplicationClientDao applicationClientDao;

    /*
     * Have to investigate
     */
    @Override
    public void save(RegisteredClient registeredClient) {
        ApplicationClient applicationClient = new ApplicationClient();

        applicationClient.setId(registeredClient.getId());
        applicationClient.setClientId(registeredClient.getClientId());
        applicationClient.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        applicationClient.setClientSecret(registeredClient.getClientSecret());
        applicationClient.setClientSecretExpiresAt(registeredClient.getClientSecretExpiresAt());
        applicationClient.setClientName(registeredClient.getClientName());
        applicationClient.setClientAuthenticationMethods(registeredClient.getClientAuthenticationMethods());
        applicationClient.setAuthorizationGrantTypes(registeredClient.getAuthorizationGrantTypes());
        applicationClient.setRedirectUris(registeredClient.getRedirectUris());
        applicationClient.setPostLogoutRedirectUris(registeredClient.getPostLogoutRedirectUris());
        applicationClient.setScopes(registeredClient.getScopes());
        applicationClient.setRequireAuthorizationConsent(registeredClient.getClientSettings().isRequireAuthorizationConsent());

        applicationClientDao.save(applicationClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        ApplicationClient applicationClient = applicationClientDao.findById(id).orElseThrow();
        return converToRegisteredClient(applicationClient);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        ApplicationClient applicationClient = applicationClientDao.findByClientId(clientId);
        return converToRegisteredClient(applicationClient);
    }

    public RegisteredClient converToRegisteredClient(ApplicationClient applicationClient) {
        applicationClient.setId(UUID.randomUUID().toString());

        RegisteredClient.Builder clientBuilder = RegisteredClient.withId(applicationClient.getId())
                .clientId(applicationClient.getClientId())
                .clientSecret(applicationClient.getClientSecret())
                .clientSettings(ClientSettings
                        .builder()
                        .requireAuthorizationConsent(applicationClient.isRequireAuthorizationConsent())
                        .build()
                );

        applicationClient
                .getClientAuthenticationMethods()
                .forEach(clientBuilder::clientAuthenticationMethod);

        applicationClient
                .getAuthorizationGrantTypes()
                .forEach(clientBuilder::authorizationGrantType);

        applicationClient
                .getRedirectUris()
                .forEach(clientBuilder::redirectUri);

        applicationClient
                .getScopes()
                .forEach(clientBuilder::scope);

        return clientBuilder.build();
    }

}
