package by.stolybko;

import by.stolybko.api.parser.Lexeme;

import java.util.List;

import static by.stolybko.api.parser.ParserJson.lexAnalyze;

public class Lexem {

    public static void main(String[] args) {

        String s = " { } [ ] : , ";
        String json = """
                {
                 id : 7024a45d-f272-4189-a58c-fc24f2822bac,
                 name : "colbasa",
                 products :[ {
                            id2 : 7024a45d-f272-4189-a58c-fc24f2822bac,
                            name2 : "colbasa",
                            price2 : 255.6
                            },
                            {
                            id2 : 7024a45d-f272-4189-a58c-fc24f2822bac,
                            name2 : "colbasa",
                            price2 : 255.6
                            }]
                 price : 255.6
                }
                """;


        /*for(String c : s.split("")) {
            char cc = c.charAt(0);
            if(cc != 32) System.out.println(cc);

        }*/

        List<Lexeme> lexems = lexAnalyze(json);
        for (Lexeme lexeme : lexems)
            System.out.println(lexeme);
    }
}
