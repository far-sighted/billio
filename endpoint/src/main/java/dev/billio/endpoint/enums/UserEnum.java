package dev.billio.endpoint.enums;

import org.springframework.stereotype.Component;

@Component
public class UserEnum {

    /**
     * Enum representing the type of user.
     * It can either be a PERSON or a COMPANY.
     */
    public enum eUserType {
        PERSON,
        COMPANY,
    }

}
