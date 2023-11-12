package by.stolybko.api.parser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Lexeme {

    LexemeType type;
    String value;
}