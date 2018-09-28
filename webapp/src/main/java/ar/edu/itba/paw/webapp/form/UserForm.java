package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserForm {
    @Size(min = 0, max = 100)
    @Pattern(regexp ="[a-zA-Z]+")
    private String firstName;

    @Size(min = 0, max = 100)
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp ="[0-9][0-9][0-9][0-9]-?[0-9][0-9][0-9][0-9]")
    private String cellphone;

    @Size(min = 0, max = 100)
    @Pattern(regexp ="[a-zA-Z ]+")
    private String country;

    @Size(min = 0, max = 100)
    @Pattern(regexp ="[a-zA-Z0 ]+")
    private String state;

    @Size(min = 0, max = 100)
    @Pattern(regexp ="[a-zA-Z0 ]+")
    private String city;


    @Size(min = 0, max = 100)
    @Pattern(regexp ="[a-zA-Z0 ,]+ [0-9]*")
    private String street;

    @Pattern(regexp ="[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]")
    private String birthday;

    @Size(min = 6, max = 10)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;

    @Size(min = 6, max = 10)
    private String password;

    @Size(min = 6, max = 10)
    private String repeatPassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(final String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(final String birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(final String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
