package by.stolybko;

import by.stolybko.api.parser.Lexeme;

import java.util.List;

import static by.stolybko.api.parser.ParserJson.parseJson;

public class Lexem {

    public static void main(String[] args) {

        String s = " { } [ ] : , ";
        String json = """
            {
                 "id": "1024a45d-f272-4189-a58c-fc24f2822bad",
                 "firstName": "Reuben",
                 "orders": [
                     {
                         "products": [
                             {
                                 "name": "1colbasa",
                                 "price": 205.6
                             },
                             {
                                 "name": "1colbasa",
                                 "price": 255.6
                             }
                         ],
                         "createDate": "2021-09-30T15:30:00+01:00"
                     }
                 ]
             }
            """;


        /*for(String c : s.split("")) {
            char cc = c.charAt(0);
            if(cc != 32) System.out.println(cc);

        }*/

        List<Lexeme> lexems = parseJson(json);
//        LexemeBuffer lexemeBuffer1 = new LexemeBuffer(lexems);
//        List<Lexeme> nextArray1 = lexemeBuffer1.nextObject();
//        LexemeBuffer lexemeBuffer2 = new LexemeBuffer(nextArray1);
//        List<Lexeme> nextArray2 = lexemeBuffer2.nextObject();
        for (Lexeme lexeme : lexems)
            System.out.println(lexeme);
    }
}
