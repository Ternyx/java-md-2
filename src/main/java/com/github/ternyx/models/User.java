package com.github.ternyx.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * User
 */
@Entity(name = "User")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter @NoArgsConstructor @ToString
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.PRIVATE)
    private int userId;

    @Column(name = "username")
    @Size(min = 4) 
    @NotBlank 
    @NonNull 
    private String username;

    @Column(name = "password")
    @Size(min = 4) 
    @NotBlank 
    @NonNull 
    private String password;

    @Column(name = "name")
    @Pattern(regexp = "[A-Z][a-z\\s]*")
    @NonNull
    private String name;
    
    @Column(name = "surname")
    @Pattern(regexp = "[A-Z][a-z\\s]+")
    @NonNull
    private String surname;

    @Column(name = "roles")
    @NonNull
    private String roles;

    public User(@Size(min = 4) @NotBlank @NonNull String username,
            @Size(min = 4) @NotBlank @NonNull String password,
            @Pattern(regexp = "[A-Z][a-z\\s]*") @NonNull String name,
            @Pattern(regexp = "[A-Z][a-z\\s]+") @NonNull String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.roles = "ROLE_USER";
    }

    public User(@Size(min = 4) @NotBlank @NonNull String username,
            @Size(min = 4) @NotBlank @NonNull String password,
            @Pattern(regexp = "[A-Z][a-z\\s]*") @NonNull String name,
            @Pattern(regexp = "[A-Z][a-z\\s]+") @NonNull String surname, @NonNull String roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
    }


}
