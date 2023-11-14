package flaviodeangeelis.u5w2d5.exception;

import flaviodeangeelis.u5w2d5.payload.ErrorsResponseDTO;
import flaviodeangeelis.u5w2d5.payload.ListErrorsresponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ListErrorsresponseDTO handleBadRequest(BadRequestException e) {
        if (e.getErrorsList() != null) {
            List<String> errorsList = e.getErrorsList().stream().map(error -> error.getDefaultMessage()).toList();
            return new ListErrorsresponseDTO(e.getMessage(), new Date(), errorsList);
        } else {
            return new ListErrorsresponseDTO(e.getMessage(), new Date(), new ArrayList<>());
        }

    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorsResponseDTO handleAccessDenied(AccessDeniedException e) {
        return new ErrorsResponseDTO(e.getMessage(), new Date());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsResponseDTO handleNotFound(NotFoundException e) {
        return new ErrorsResponseDTO(e.getMessage(), new Date());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsResponseDTO handleGeneric(Exception e) {
        e.printStackTrace();
        return new ErrorsResponseDTO("Problema lato server, contatta lo sviluppatore per risolvere!!", new Date());
    }
}
