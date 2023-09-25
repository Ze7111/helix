package helix.language;

import java.util.ArrayList;

import helix.GlobalVariables;
import helix.classes.TokenizedResult;

public class TokenParser {
    public static ArrayList<ArrayList<TokenizedResult>> parseTokens(ArrayList<TokenizedResult> tokensList) {
        // parse tokens into their respective types
        // so TokenizedResult([package, helix, ;], 1, success) becomes [TokenizedResult([package, helix, ;], 1, success)]
        ArrayList<String> baseKeywords = new ArrayList<>();
        // get all the elements in the BaseKeywords enum
        for (GlobalVariables.BaseKeywords keyword : GlobalVariables.BaseKeywords.values())
            baseKeywords.add(keyword.syntax);
        

        // do a loop so each token is processed if there is a base keyword and no semi colon before it then raise an error
        // TODO: GET IT DONE
        ArrayList<ArrayList<TokenizedResult>> newTokensList = inferSemicolons(tokensList);

        return newTokensList;
    }

    public static ArrayList<ArrayList<TokenizedResult>> inferSemicolons(ArrayList<TokenizedResult> tokens) {
        ArrayList<String> baseKeywords = new ArrayList<>();
        // get all the elements in the BaseKeywords enum
        for (GlobalVariables.BaseKeywords keyword : GlobalVariables.BaseKeywords.values())
            baseKeywords.add(keyword.syntax);
        
        // loop through the tokens
        ArrayList<TokenizedResult> line = new ArrayList<>();
        ArrayList<ArrayList<TokenizedResult>> lines = new ArrayList<>();
        // keep adding to the line until either a semicolon is found or a base keyword is found or { is found or } is found
        for (int index = 0; index < tokens.size(); index++) {

            TokenizedResult token = tokens.get(index);
            if (token.processedWord.equals("{")) {
                line.add(token);
                // add the line to the lines in its own list
                lines.add(new ArrayList<>(line));
                // reset the line
                line.clear();
            } else if (token.processedWord.equals("}")) {
                // add a smicolon to the line
                if (!tokens.get(index - 1).processedWord.equals(";") && !tokens.get(index - 1).processedWord.equals("{") && !tokens.get(index - 1).processedWord.equals("}")) {
                    tokens.get(index - 1).endCode = "missing semicolon";
                }
                line.add(token);
                // add the line to the token
                lines.add(new ArrayList<>(line));
                // reset the line
                line.clear();
            } else if (token.processedWord.equals(";")) {
                line.add(token);
                // add the line to the token
                lines.add(new ArrayList<>(line));
                // reset the line
                line.clear();
            } else {
                // add the line to the line
                line.add(token);
            }
        }
        
        return lines;
    }
}
