package ru.vitte.online.helpdesk.mock;

import org.springframework.stereotype.Component;

@Component
public class SecurityModuleMock {


    public Long getUserPersId() {
        return 2L;
    }


    public boolean isAdmin() {
        return true;
    }

    public boolean isEmployee() {
        return false;
    }
}
