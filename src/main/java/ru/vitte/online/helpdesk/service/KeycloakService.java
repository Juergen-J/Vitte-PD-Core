package ru.vitte.online.helpdesk.service;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import ru.vitte.online.helpdesk.entity.enums.Role;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {

    private final static String REALM_NAME = "Vitte";

    private final Keycloak keycloakAdmin;

    public String createUser(String username, String email, String firstName, String lastName, String password, Role roleName) {
        RealmResource realmResource = keycloakAdmin.realm(REALM_NAME);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(true);

        user.setCredentials(Collections.singletonList(credential));

        Response response = realmResource.users().create(user);

        if (response.getStatus() == 201) {
            System.out.println("User created successfully");

            String userId = realmResource.users().search(username).get(0).getId();
            UserResource userResource = realmResource.users().get(userId);

            RoleRepresentation role = realmResource.roles().get(roleName.toString()).toRepresentation();
            userResource.roles().realmLevel().add(Collections.singletonList(role));

            log.info("Role assigned successfully");
            return userId;
        } else {
            log.info("Failed to create user: {}", response.getStatusInfo());
            return null;
        }
    }


    public void updateUser(String userId, String email, String firstName, String lastName, Role roleName) {
        RealmResource realmResource = keycloakAdmin.realm(REALM_NAME);

        UserResource userResource = realmResource.users().get(userId);
        UserRepresentation user = userResource.toRepresentation();

        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        userResource.update(user);

        UserResource userResourceForRole = realmResource.users().get(userId);

        RoleRepresentation role = realmResource.roles().get(roleName.toString()).toRepresentation();
        userResourceForRole.roles().realmLevel().add(Collections.singletonList(role));

        log.info("User updated successfully in Keycloak");
    }

    public void deleteUser(String keycloakUserId) {
        RealmResource realmResource = keycloakAdmin.realm(REALM_NAME);

        try {
            UserResource userResource = realmResource.users().get(keycloakUserId);
            userResource.remove();
            log.info("User deleted successfully from Keycloak");
        } catch (NotFoundException e) {
            log.error("User not found in Keycloak: {}", keycloakUserId);
        } catch (Exception e) {
            log.error("An error occurred while deleting user in Keycloak: {}", e.getMessage());
        }
    }
}
