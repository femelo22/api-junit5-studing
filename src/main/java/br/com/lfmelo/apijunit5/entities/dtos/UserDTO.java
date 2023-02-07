package br.com.lfmelo.apijunit5.entities.dtos;

import javax.validation.constraints.NotEmpty;

public class UserDTO {

    @NotEmpty(message = "Name cannot be null")
    private String name;

    @NotEmpty(message = "Email cannot be null")
    private String email;

    @NotEmpty(message = "Password cannot be null")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
