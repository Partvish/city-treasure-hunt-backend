package hu.bme.aut.treasurehunt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.bme.aut.treasurehunt.model.constants.UserRole;
import hu.bme.aut.treasurehunt.model.dtos.UserDto;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name="users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "User cannot be createad without an e-mail attached")
    @Email(message = "The e-mail has to be formatted as an actual e-mail")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "User cannot be created without a name attached")
    @Column(unique = true)
    private String name;

    @NotBlank(message = "User cannot be created without a password attached")
    private String password;

    @NotBlank(message = "User cannot be created without a role attached")
    private String role = UserRole.Normal;

    @OneToMany(mappedBy = "user")
    private List<Suggestion> suggestions;

    @OneToMany(mappedBy = "user")
    private List<UserQuest> userQuests;

    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public List<UserQuest> getUserQuests() {
        return userQuests;
    }

    public void setUserQuests(List<UserQuest> userQuests) {
        this.userQuests = userQuests;
    }

    public User(){}

    @JsonIgnore
    public User(String email, String name, String role, String password, int points){
        this.email = email;
        this.name = name;
        this.role = role;
        this.password = password;
        this.points = points;
    }

    @JsonIgnore
    public User(UserDto dto){
        this.id = dto.id;
        this.email = dto.email;
        this.name = dto.name;
        this.role = dto.role;
        this.password = dto.password;
        this.points = dto.points;
    }

    @JsonIgnore
    public UserDto getDto(){
        return new UserDto(id, name, email, password, role, points);
    }
}

