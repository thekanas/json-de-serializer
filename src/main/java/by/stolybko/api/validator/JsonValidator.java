package by.stolybko.api.validator;

import by.stolybko.api.parser.Lexeme;
import by.stolybko.api.parser.LexemeType;
import lombok.NoArgsConstructor;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class JsonValidator {

    private static final JsonValidator INSTANCE = new JsonValidator();

    public static JsonValidator getInstance() {
        return INSTANCE;
    }

    public ValidationResult validate(List<Lexeme> lexemes) {

        var validationResult = new ValidationResult();
        int countStartObject = 0;
        int countEndObject = 0;
        int countStartArray = 0;
        int countEndArray = 0;

        for (int i = 0; i < lexemes.size(); i++) {
            if(lexemes.get(i).getType() == LexemeType.KEY && lexemes.get(i+1).getType() == LexemeType.KEY) {
                validationResult.add(Error.of("invalid separator", "Missing separator key value"));
            }

            switch (lexemes.get(i).getType()) {
                case START_OBJECT -> countStartObject++;
                case END_OBJECT -> countEndObject++;
                case START_ARRAY -> countStartArray++;
                case END_ARRAY -> countEndArray++;
            }
        }

        if (countStartObject != countEndObject) {
            validationResult.add(Error.of("invalid { or }", "Count of opening { and closing } brackets must match"));
        }

        if (countStartArray != countEndArray) {
            validationResult.add(Error.of("invalid [ or ]", "Count of opening [ and closing ] brackets must match"));
        }

        return validationResult;
    }
}
