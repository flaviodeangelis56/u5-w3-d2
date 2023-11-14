package flaviodeangeelis.u5w2d5.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record NewUserDTO
        (@NotEmpty(message = "L'username è un campo obbligatorio!")
         String username,
         @NotEmpty(message = "Il nome è un campo obbligatorio!")
         String name,
         @NotEmpty(message = "Il cognome è un campo obbligatorio!")
         String surname,
         @NotEmpty(message = "L'email è un campo obbligatorio!")
         @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "L'email inserita non è valida")
         String email,
         @NotEmpty(message = "La password è un campo obbligatorio!")
         String password) {
}
