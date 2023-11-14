package flaviodeangeelis.u5w2d5.payload;

import java.util.Date;
import java.util.List;

public record ListErrorsresponseDTO(String message,
                                    Date timestamp,
                                    List<String> errorsList) {
}
