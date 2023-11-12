package by.stolybko.api.exception;

import lombok.Getter;
import by.stolybko.api.validator.Error;
import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
        for(Error error : errors) {
            System.out.println(error.getMessage());
        }
    }
}
