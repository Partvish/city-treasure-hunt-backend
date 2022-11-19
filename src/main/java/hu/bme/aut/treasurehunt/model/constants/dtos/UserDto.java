package hu.bme.aut.treasurehunt.model.constants.dtos;

public class UserDto {
    public String name;
    public String email;
    public String password;
    public String role;

    public UserDto(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
