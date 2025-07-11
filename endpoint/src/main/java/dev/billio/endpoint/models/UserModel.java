package dev.billio.endpoint.models;

import dev.billio.endpoint.enums.UserEnum;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dbo_datatable_user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "u_username", nullable = false, unique = true)
    private String username;

    @Column(name = "u_email", nullable = false, unique = true)
    private String email;

    @Column(name = "u_password", nullable = false)
    private String password;

    @Column(name = "u_type", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PERSON'")
    @Enumerated(EnumType.STRING)
    private UserEnum.eUserType type = UserEnum.eUserType.PERSON;

    @Column(name = "u_role", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'GUEST'")
    @Enumerated(EnumType.STRING)
    private UserEnum.eUserRole role = UserEnum.eUserRole.GUEST;

    @ElementCollection(targetClass = UserEnum.eUserPermission.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "dbo_datatable_user_permission", joinColumns = @JoinColumn(name = "up_id"))
    @Column(name = "up_permission", nullable = false, columnDefinition = "VARCHAR(255)")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Enumerated(EnumType.STRING)
    private Set<UserEnum.eUserPermission> permissions = new HashSet<>();

}
