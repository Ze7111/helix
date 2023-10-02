package helix.functions;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helix.classes.TokenizedResult;
import helix.language.ErrorReporting;
import helix.GlobalVariables;

public class Function {
    public static String transpileLine(ArrayList<TokenizedResult> TokenizedLine) {
        if (!TokenizedLine.get(0).processedWord.equals(GlobalVariables.BaseKeywords.FUNCTION.syntax)) return "";
        // replace the function keyword with def
        TokenizedLine.get(0).processedWord = GlobalVariables.BaseKeywords.FUNCTION.value;
        // find the { and replace it with :
        TokenizedLine.get(TokenizedLine.size() - 1).processedWord = ":"; 
        String params = "";
        // loop through the tokens and find the parameters
        int numberOfBrackets = 0;
        for (int index = 0; index < TokenizedLine.size(); index++) {
            TokenizedResult token = TokenizedLine.get(index);
            if (token.processedWord.equals("(")) {
                numberOfBrackets++;
                continue;
            } else if (token.processedWord.equals(")")) {
                numberOfBrackets--;
                continue;
            } else if (numberOfBrackets == 1) {
                params += token.processedWord + " ";
            }
        }
        // remove the last space
        params = "(" + params.trim() + " )";
        // Regular Expression to find text before variable names
        String regex = "\\((.*?)\\s+(\\w+)\\s+";
        String addRegex = ",\\s+(.*?)\\s+(\\w+)\\s+";
        
        int totalNumberOfVariables = params.split(",").length;
        
        for (int index = 0; index < totalNumberOfVariables - 1; index++) {
            regex += addRegex;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(params);
        
        // Replace matched text with variable names only
        String output = "(";
        
        if(matcher.find()){
            for (int index = 0; index < totalNumberOfVariables; index++) {
                output += matcher.group(2 + (index * 2)) + ", ";
            }
            output = output.substring(0, output.length() - 2);
        }

        output += ")";
        
        if ((output.equals("()")) && totalNumberOfVariables > 0) ErrorReporting.throwError("missing variable type");
        
        output = TokenizedLine.get(0).processedWord + " " + TokenizedLine.get(1).processedWord + " " + output + ":\n";

        return output;
    }

    public static String combineLines(ArrayList<String> lines) {
        return "";
    }

    public static void main(String[] args) {
        ArrayList<TokenizedResult> tokens = new ArrayList<>();
        tokens.add(new TokenizedResult("fn", 1, "success"));
        tokens.add(new TokenizedResult("main", 1, "success"));
        tokens.add(new TokenizedResult("(", 1, "success"));
        tokens.add(new TokenizedResult("int", 1, "success"));
        tokens.add(new TokenizedResult("a", 1, "success"));
        tokens.add(new TokenizedResult(",", 1, "success"));
        tokens.add(new TokenizedResult("int", 1, "success"));
        tokens.add(new TokenizedResult("b", 1, "success"));        
        tokens.add(new TokenizedResult(")", 1, "success"));
        tokens.add(new TokenizedResult("{", 1, "success"));
        
        System.out.println(transpileLine(tokens));

        tokens = new ArrayList<>();
        tokens.add(new TokenizedResult("fn", 1, "success"));
        tokens.add(new TokenizedResult("main", 1, "success"));
        tokens.add(new TokenizedResult("(", 1, "success"));
        tokens.add(new TokenizedResult("int", 1, "success"));
        tokens.add(new TokenizedResult("[", 1, "success"));
        tokens.add(new TokenizedResult("int", 1, "success"));
        tokens.add(new TokenizedResult("]", 1, "success"));
        tokens.add(new TokenizedResult("a", 1, "success"));
        tokens.add(new TokenizedResult(",", 1, "success"));
        tokens.add(new TokenizedResult("int", 1, "success"));
        tokens.add(new TokenizedResult("b", 1, "success"));        
        tokens.add(new TokenizedResult(")", 1, "success"));
        tokens.add(new TokenizedResult("{", 1, "success"));
        
        System.out.println(transpileLine(tokens));
    }
}
