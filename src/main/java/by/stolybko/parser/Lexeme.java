package by.stolybko.parser;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Lexeme {
    LexemeType type;

    //String key;
    String value;

 /*   START_OBJECT, END_OBJECT,

    START_ARRAY, END_ARRAY,

    KEY_VALUE_SEPARATOR
    PROPERTY_SEPARATOR

    IS_OBJECT, IS_ARRAY,
    IS_STRING, IS_BOOLEAN, IS_NUMBER,

    PROPERTY;*/


    public static List<Lexeme> lexAnalyze(String expText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        boolean isValue = false;
        while (pos< expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '{':
                    lexemes.add(new Lexeme(LexemeType.START_OBJECT, String.valueOf(c)));
                    isValue = false;
                    pos++;
                    continue;
                case '}':
                    lexemes.add(new Lexeme(LexemeType.END_OBJECT, String.valueOf(c)));
                    isValue = false;
                    pos++;
                    continue;
                case '[':
                    lexemes.add(new Lexeme(LexemeType.START_ARRAY, String.valueOf(c)));
                    isValue = false;
                    pos++;
                    continue;
                case ']':
                    lexemes.add(new Lexeme(LexemeType.END_ARRAY, String.valueOf(c)));
                    isValue = false;
                    pos++;
                    continue;
                case ':':
                    lexemes.add(new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, String.valueOf(c)));
                    isValue = !isValue;
                    pos++;
                    continue;
                case ',':
                    lexemes.add(new Lexeme(LexemeType.PROPERTY_SEPARATOR, String.valueOf(c)));
                    isValue = !isValue;
                    pos++;
                    continue;
                case 32:
                case 10:
                case 34:
                    pos++;
                    continue;
                default:
                    StringBuilder sb = new StringBuilder();
                    do {
                        sb.append(c);
                        pos++;
                        if (pos >= expText.length()) {
                            break;
                        }
                        c = expText.charAt(pos);
                    } while (c != 32 && c != 34 && c != '[' && c != ']' && c != '{' && c != '}' && c != ',' && c != ':');
                    if(isValue) {
                        lexemes.add(new Lexeme(LexemeType.VALUE, sb.toString()));
                    }
                    else lexemes.add(new Lexeme(LexemeType.KEY, sb.toString()));

                /*    if (c <= '9' && c >= '0') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c <= '9' && c >= '0');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Unexpected character: " + c);
                        }
                        pos++;
                    }*/
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

}
