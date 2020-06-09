package com.github.ternyx.models;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Patient
 */
@Entity(name = "Patient")
@Getter @Setter @ToString
public class Patient extends User {

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    Collection<Appointment> appointments;

    public Patient() {
        super();
        super.setRoles("ROLE_PATIENT");
    }

    public Patient(@Size(min = 4) @NotBlank @NonNull String username,
            @Size(min = 4) @NotBlank @NonNull String password,
            @Pattern(regexp = "[A-Z][a-z\\s]*") @NonNull String name,
            @Pattern(regexp = "[A-Z][a-z\\s]+") @NonNull String surname) {
        super(username, password, name, surname, "ROLE_PATIENT");
    }

}
