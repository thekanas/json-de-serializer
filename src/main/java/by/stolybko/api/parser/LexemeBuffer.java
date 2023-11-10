package by.stolybko.api.parser;

import java.util.List;

public class LexemeBuffer {

    private int pos;

    public List<Lexeme> lexemes;

    public LexemeBuffer(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public Lexeme nextKey() {
        for (int i = pos; i < lexemes.size(); i++) {
            Lexeme lexemeKey = lexemes.get(i);
            if (lexemeKey.getType() == LexemeType.KEY) {
                pos = i + 1;
                return lexemeKey;
            }
        }
        return null;
    }

    public Lexeme nextValue() {
        for (int i = pos; i < lexemes.size(); i++) {
            Lexeme lexemeValue = lexemes.get(i);
            if (lexemeValue.getType() == LexemeType.VALUE) {
                pos = i + 1;
                return lexemeValue;
            }
        }
        return null;
    }

    public List<Lexeme> nextObject() {
        int start = 0;
        int end = 0;
        int check = 0;
        for (int i = pos; i < lexemes.size(); i++) {
            Lexeme lexemeValue = lexemes.get(i);
            if (lexemeValue.getType() == LexemeType.START_OBJECT) {
                start = i + 1;
                pos = start;
                check++;
                break;
            }
        }
        for (int i = pos; i < lexemes.size(); i++) {
            Lexeme lexemeValue = lexemes.get(i);
            if (lexemeValue.getType() == LexemeType.START_OBJECT) {
                check++;
            }
            if (lexemeValue.getType() == LexemeType.END_OBJECT) {
                check--;
                if (check == 0) {
                    end = i;
                    pos = i;
                    break;
                }
            }
        }
        return lexemes.subList(start, end);
    }

    public List<Lexeme> nextArray() {
        int start = 0;
        int end = 0;
        int check = 0;
        for (int i = pos; i < lexemes.size(); i++) {
            Lexeme lexemeValue = lexemes.get(i);
            if (lexemeValue.getType() == LexemeType.START_ARRAY) {
                start = i + 1;
                pos = start;
                check++;
                break;
            }
        }
        for (int i = pos; i < lexemes.size(); i++) {
            Lexeme lexemeValue = lexemes.get(i);
            if (lexemeValue.getType() == LexemeType.START_ARRAY) {
                check++;
            }
            if (lexemeValue.getType() == LexemeType.END_ARRAY) {
                check--;
                if (check == 0) {
                    end = i;
                    pos = end;
                    break;
                }
            }
        }
        return lexemes.subList(start, end);
    }
}
