package flaviodeangeelis.u5w2d5.payload;

import jakarta.validation.constraints.NotEmpty;

public record UpdateDeviceUserDTO(
        @NotEmpty(message = "L'utente del dispositivo è un campo obbligatorio! Scegli un id valido")
        int userId) {
}
