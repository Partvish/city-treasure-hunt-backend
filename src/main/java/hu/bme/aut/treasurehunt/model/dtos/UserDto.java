package hu.bme.aut.treasurehunt.model.dtos;

public class UserDto {
    public String name;
    public String email;
    public String password;
    public String role;
    public int points;

    public UserDto(String name, String email, String password, String role, int points) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.points = points;
    }
}
