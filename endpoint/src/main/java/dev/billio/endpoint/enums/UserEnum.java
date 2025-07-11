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

    /**
     * Enum representing the role of a user.
     * It can be ADMIN, USER, or GUEST.
     */
    public enum eUserRole {
        ADMIN,
        USER,
        GUEST,
    }

    public enum eUserPermission {
        CAN_SUPER_ADMIN,
        CAN_READ_USERS,
        CAN_MANAGE_USERS,
    }
}
