package hu.bme.aut.treasurehunt.model.dtos;

public class UserDto {
    public Long id;
    public String name;
    public String email;
    public String password;
    public String role;
    public int points;

    public UserDto(Long id, String name, String email, String password, String role, int points) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.points = points;
    }
}
