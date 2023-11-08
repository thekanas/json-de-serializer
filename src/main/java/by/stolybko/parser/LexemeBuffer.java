package by.stolybko.parser;

import java.util.List;

public class LexemeBuffer {
    private int pos;

    public List<Lexeme> lexemes;

    public LexemeBuffer(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public Lexeme next() {
        return lexemes.get(pos++);
    }

    public Lexeme nextKey() {
        for(int i = pos; i < lexemes.size(); i++) {
            Lexeme lexemeKey = lexemes.get(i);
            if(lexemeKey.getType() == LexemeType.KEY) {
                pos = i;
                return lexemeKey;
            }
        }

        return null;
    }

    public Lexeme nextValue() {
        for(int i = pos; i < lexemes.size(); i++) {
            Lexeme lexemeValue = lexemes.get(i);
            if(lexemeValue.getType() == LexemeType.VALUE) {
                pos = i;
                return lexemeValue;
            }
        }
        return null;
    }

    public List<Lexeme> nextObject() {
        int start = 0;
        int end = 0;
        int check = 0;
        for(int i = pos; i < lexemes.size(); i++) {
            Lexeme lexemeValue = lexemes.get(i);
            if(lexemeValue.getType() == LexemeType.START_OBJECT) {
                start = i;
                pos = i;
                check++;
                break;
            }
        }
        for(int i = pos; i < lexemes.size(); i++) {
            Lexeme lexemeValue = lexemes.get(i);
            if(lexemeValue.getType() == LexemeType.END_OBJECT) {
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

    public void back() {
        pos--;
    }

    public int getPos() {
        return pos;
    }
}
