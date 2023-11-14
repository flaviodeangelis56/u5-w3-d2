package flaviodeangeelis.u5w2d5.payload;

import jakarta.validation.constraints.NotEmpty;

public record NewDeviceDTO(
        @NotEmpty(message = "Il tipo del dispositivo è un campo obbligatorio! Scegli tra SMARTPHONE,TABLET,LAPTOP")
        String deviceType
) {
}
