package helix.functions;

import java.util.ArrayList;

import helix.classes.TokenizedResult;
import helix.GlobalVariables;

public class Function {
    public static String transpileLine(ArrayList<TokenizedResult> TokenizedLine) {
        if (TokenizedLine.get(0).processedWord != GlobalVariables.BaseKeywords.FUNCTION.syntax) return "";
        return null;
    }

    public static String combineLines(ArrayList<String> lines) {
        return "";
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
