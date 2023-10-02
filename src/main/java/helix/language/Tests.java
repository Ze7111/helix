package helix.language;

import java.util.ArrayList;

import helix.classes.NamespaceList;
import helix.classes.TokenizedResult;

public class Tests {
    public static void testTokenizer() {
        ArrayList<ArrayList<TokenizedResult>> results = Tokenizer.tokenize_file("syntax");
        for (ArrayList<TokenizedResult> result3 : results) {
            for (TokenizedResult result2 : result3) {
                System.out.print(result2.processedWord + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        NamespaceList results2 = NamespaceParser.GetFunctionNamespace(results);
        System.out.println(results2);
    }

    public static void main(String[] args) {
        testTokenizer();
    }
    
}