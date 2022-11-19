package hu.bme.aut.treasurehunt.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.bme.aut.treasurehunt.model.constants.UserRole;
import hu.bme.aut.treasurehunt.model.constants.dtos.UserDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name="users")
public class User {

    @Id
    @NotBlank(message = "User cannot be createad without an e-mail attached")
    @Email(message = "The e-mail has to be formatted as an actual e-mail")
    private String email;

    @NotBlank(message = "User cannot be created without a name attached")
    private String name;

    @NotBlank(message = "User cannot be created without a password attached")
    private String password;

    @NotNull(message = "User cannot be created without a status attached")
    private boolean enabled =false;

    private UUID uuid;

    @NotBlank(message = "User cannot be created without a role attached")
    private String role = UserRole.Normal;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonIgnore
    public UserDto getDto(){
        return new UserDto(name, email, password, role);
    }
}

