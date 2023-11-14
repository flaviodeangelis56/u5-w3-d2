package flaviodeangeelis.u5w2d5.payload;

import jakarta.validation.constraints.NotNull;

public record UpdateDeviceStatusDTO(
        @NotNull(message = "Il stato del dispositivo è un campo obbligatorio! Scegli tra DISPONIBILE,ASSEGNATO,MANUTENZIONE,DISMESSO")
        String deviceStatus) {
}
