package com.github.ternyx.models;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.github.ternyx.models.enums.DoctorType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Doctor
 */
@Entity(name = "Doctor")
@Getter @Setter @ToString
public class Doctor extends User {

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NonNull
    private DoctorType type;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    Collection<Appointment> appointments;

    public Doctor() {
        super();
        super.setRoles("ROLE_DOCTOR");
    }

    public Doctor(@Size(min = 4) @NotBlank @NonNull String username,
            @Size(min = 4) @NotBlank @NonNull String password,
            @Pattern(regexp = "[A-Z][a-z\\s]*") @NonNull String name,
            @Pattern(regexp = "[A-Z][a-z\\s]+") @NonNull String surname, @NonNull DoctorType type) {
        super(username, password, name, surname, "ROLE_DOCTOR");
        this.type = type;
    }

}
