package by.stolybko.api.parser;

import java.util.ArrayList;
import java.util.List;

public class ParserJson {
    public static List<Lexeme> lexAnalyze(String json) {

        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        boolean isValue = false;
        while (pos< json.length()) {
            char c = json.charAt(pos);
            switch (c) {
                case 34: // "
                    pos = getKeyValue(json, pos, isValue, lexemes);
                    continue;
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
                    isValue = false;
                    pos++;
                    continue;
                case 32: // " "
                case 10: // \n
//                case 34: // "
                    pos++;
                    continue;
                default:
                    StringBuilder sb = new StringBuilder();
                    do {
                        sb.append(c);
                        pos++;
                        if (pos >= json.length()) {
                            break;
                        }
                        c = json.charAt(pos);
                    } while (c != 32 && c != 34 && c != '[' && c != ']' && c != '{' && c != '}' && c != ',' && c != ':');
                    if(isValue) {
                        lexemes.add(new Lexeme(LexemeType.VALUE, sb.toString()));
                    }
                    else lexemes.add(new Lexeme(LexemeType.KEY, sb.toString()));

            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    private static int getKeyValue(String json, int pos, boolean isValue, ArrayList<Lexeme> lexemes) {
        char c = json.charAt(pos);
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(c);
            pos++;
            if (pos >= json.length()) {
                break;
            }
            c = json.charAt(pos);
        } while (c != 34);

        if(isValue) {
            lexemes.add(new Lexeme(LexemeType.VALUE, sb.deleteCharAt(0).toString()));
        }
        else lexemes.add(new Lexeme(LexemeType.KEY, sb.deleteCharAt(0).toString()));

        return ++pos;
    }
}
