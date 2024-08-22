package ru.vitte.online.helpdesk.service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
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
public class KeycloakService {

    private final Keycloak keycloakAdmin;

    public void createUser(String username, String email, String firstName, String lastName, String password, Role roleName) {
        RealmResource realmResource = keycloakAdmin.realm("Vitte");

        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        user.setCredentials(Collections.singletonList(credential));

        Response response = realmResource.users().create(user);

        if (response.getStatus() == 201) {
            System.out.println("User created successfully");

            String userId = realmResource.users().search(username).get(0).getId();
            UserResource userResource = realmResource.users().get(userId);

            RoleRepresentation role = realmResource.roles().get(roleName.toString()).toRepresentation();
            userResource.roles().realmLevel().add(Collections.singletonList(role));

            System.out.println("Role assigned successfully");
        } else {
            System.err.println("Failed to create user: " + response.getStatusInfo());
        }

        response.close();
    }
}
