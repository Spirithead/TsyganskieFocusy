package ru.mosit.pahotest.parsing;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    List<Token> in;

    public Parser(List<Token> in) {
        this.in = in;
    }

    public List<Data> parse() {
        List<Data> res = new ArrayList<>();
        boolean doneFile = false;
        in.remove(0);//remove '['

        while (!doneFile) {
            Data currData = new Data();
            boolean doneObj = false;
            in.remove(0);//remove '{'
            while (!doneObj) {
                String currKey = in.get(0).getValue();
                currKey = currKey.substring(1, currKey.length() - 1);//"abc" -> abc
                in.remove(0);//remove token with key
                in.remove(0);//remove ':'
                String currVal = in.get(0).getValue();
                if(in.get(0).getType().equals("STRING")){
                    currVal = currVal.substring(1, currVal.length() - 1);//"abc" -> abc
                }
                in.remove(0);//remove token with value
                currData.add(currKey, currVal);

                if (in.get(0).getValue().equals("}")) {
                    doneObj = true;
                    in.remove(0);//remove '}'
                }

                if (in.get(0).getValue().equals(",")) {
                    in.remove(0);//remove ','
                }
            }

            res.add(currData);
            if (in.get(0).getValue().equals("]")) {
                doneFile = true;
            }
        }
        return res;
    }
}
