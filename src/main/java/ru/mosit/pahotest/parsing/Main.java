package ru.mosit.pahotest.parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "data.json";
        String line;
        StringBuilder in = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            in.append(line);
        }
        br.close();
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.readTokens(in.toString());
        //System.out.println(tokens);
        Parser parser = new Parser(tokens);
        List<Data> dataList = parser.parse();
        for(Data data : dataList){
            System.out.print(data);
            System.out.print("-------------\n");
        }

    }
}
