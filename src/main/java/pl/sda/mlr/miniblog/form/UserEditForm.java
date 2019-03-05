package pl.sda.mlr.miniblog.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserEditForm {

    @NotBlank(message = "Pole nazwisko nie może być puste.")
    private String firstName;
    @NotBlank(message = "Pole nazwisko nie może być puste.")
    private String lastName;
    @Email (message="Niepoprawny format email")
    private String email;
    @Size(min=5, max=25, message = "Hasło musi mieć pomiędzy {min} a {max} znaków")
    private String password;
}
