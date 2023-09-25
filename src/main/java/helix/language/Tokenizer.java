package helix.language;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import helix.classes.TokenizedResult;

public class Tokenizer {
    private static final String WHITESPACE_STRING_SENSITIVE = "\\s+(?![^']*'[^']*'[^']*|\\s)\n";
    private static final String PUNCTUATION = "?@[\\]^`{|}!\"$%&'()*+,-./:;<=>";
    private static final String ASCII_LETTERS = "abcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String COMMENT_START = "~"; // Placeholder, update as needed
    private static boolean inMultiLineComment = false;

    public static int ascii_code(String character) {
        return (int) character.charAt(0);
    }

    public static ArrayList<ArrayList<TokenizedResult>> tokenize_file(String fileName) {
        ArrayList<TokenizedResult> results = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (!inMultiLineComment && line.contains("~*~")) {
                    inMultiLineComment = true;
                } else if (inMultiLineComment && line.contains("~*~")) {
                    inMultiLineComment = false;
                } else if (inMultiLineComment) {
                    continue;
                } else {
                    TokenizedResult result = tokenize(line, lineNumber);
                    results.add(result);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // remove empty lines
        results.removeIf(result -> result.processedLine.isEmpty());
        
        // reprocess tokens
        
        return reProcessTokens(results);
    }

    // how to make a function with 2 return types
    public static TokenizedResult tokenize(String line, int lineNumber) {
        List<String> processedLine = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        boolean inString = false;
        char stringChar = 0;
        char previousChar = 0;
        String endCode = "success";

        line = line.trim().replaceAll(WHITESPACE_STRING_SENSITIVE, " ");

        for (int index = 0; index < line.length(); index++) {
            char charAtIndex = line.charAt(index);
        
            if (charAtIndex == '"' || charAtIndex == '\'') {
                tempList.add(String.valueOf(charAtIndex));
                if (inString && stringChar == charAtIndex) {
                    processedLine.add(String.join("", tempList));
                    tempList.clear();
                    inString = false;
                } else if (inString) {
                    continue;
                } else {
                    inString = true;
                    stringChar = charAtIndex;
                }
                continue;
            }
        
            if (inString) {
                tempList.add(String.valueOf(charAtIndex));
                continue;
            }
            
            if (PUNCTUATION.indexOf(charAtIndex) != -1) {
                if (!tempList.isEmpty()) {
                    processedLine.add(String.join("", tempList));
                    tempList.clear();
                }
                processedLine.add(String.valueOf(charAtIndex));
                previousChar = charAtIndex; 
                continue;
            }
            //System.out.println(processedLine); System.out.println(tempList); System.out.println(line);
            if (charAtIndex == previousChar) {
                String lastElement = processedLine.get(processedLine.size() - 1);
                processedLine.set(processedLine.size() - 1, lastElement + charAtIndex);
                continue;
            }
            
            if (charAtIndex == ' ') {
                if (!tempList.isEmpty()) {
                    processedLine.add(String.join("", tempList));
                    tempList.clear();
                }
                continue;
            }
        
            if (ASCII_LETTERS.indexOf(charAtIndex) != -1) {
                tempList.add(String.valueOf(charAtIndex));
                continue;
            }
        
            if (DIGITS.indexOf(charAtIndex) != -1) {
                tempList.add(String.valueOf(charAtIndex));
                continue;
            }
        
            if (COMMENT_START.length() > 1) {
                if (charAtIndex == COMMENT_START.charAt(0) && line.substring(index, index + COMMENT_START.length()).equals(COMMENT_START)) {
                    break;
                }
            } else {
                if (charAtIndex == COMMENT_START.charAt(0)) {
                    break;
                }
            }
        
            if (PUNCTUATION.indexOf(charAtIndex) == -1 && ASCII_LETTERS.indexOf(charAtIndex) == -1 && DIGITS.indexOf(charAtIndex) == -1) {
                endCode = "unknown character " + charAtIndex;
                tempList.add(String.valueOf(charAtIndex));
            }
        }
        
        if (!tempList.isEmpty()) {
            processedLine.add(String.join("", tempList));
        }

        // also merge stuff like 1, ., 2 into 1.2
        // or even 1, ., 2f into 1.2f but if its like 1, ., 2, ., 2 then add an error code
        for (int index = 0; index < processedLine.size(); index++) {
            String token = processedLine.get(index);

            if (index + 1 < processedLine.size()) {
                String nextToken = processedLine.get(index + 1);
                if (token.equals(".") && DIGITS.indexOf(nextToken.charAt(0)) != -1) {
                    String previousToken = processedLine.get(index - 1);
                    if (DIGITS.indexOf(previousToken.charAt(previousToken.length() - 1)) != -1) {
                        processedLine.set(index - 1, previousToken + token + nextToken);
                        processedLine.remove(index);
                        processedLine.remove(index);
                    } else {
                        processedLine.set(index, token + nextToken);
                        processedLine.remove(index + 1);
                    }
                } else if (token.equals(".") && nextToken.equals(".")) {
                    endCode = "invalid syntax";
                }
            }
        }

        // merge symbols like + =, - =, * =, / =, % =, - > into one token
        // so +, = becomes +=, - = becomes -=, etc.
        for (int index = 0; index < processedLine.size(); index++) {
            String token = processedLine.get(index);
            if (index + 1 < processedLine.size()) {
                String nextToken = processedLine.get(index + 1);
                if (token.equals("+") && nextToken.equals("=")) {
                    processedLine.set(index, "+=");
                    processedLine.remove(index + 1);
                } else if (token.equals("-") && nextToken.equals("=")) {
                    processedLine.set(index, "-=");
                    processedLine.remove(index + 1);
                } else if (token.equals("*") && nextToken.equals("=")) {
                    processedLine.set(index, "*=");
                    processedLine.remove(index + 1);
                } else if (token.equals("/") && nextToken.equals("=")) {
                    processedLine.set(index, "/=");
                    processedLine.remove(index + 1);
                } else if (token.equals("%") && nextToken.equals("=")) {
                    processedLine.set(index, "%=");
                    processedLine.remove(index + 1);
                } else if (token.equals("-") && nextToken.equals(">")) {
                    processedLine.set(index, "->");
                    processedLine.remove(index + 1);
                } else if (token.equals("<") && nextToken.equals("=")) {
                    processedLine.set(index, "<=");
                    processedLine.remove(index + 1);
                } else if (token.equals(">") && nextToken.equals("=")) {
                    processedLine.set(index, ">=");
                    processedLine.remove(index + 1);
                } else if (token.equals("!") && nextToken.equals("=")) {
                    processedLine.set(index, "!=");
                    processedLine.remove(index + 1);
                } else if (token.equals("<") && nextToken.equals("<") && index + 2 < processedLine.size()) {
                    String nextNextToken = processedLine.get(index + 2);
                    if (nextNextToken.equals("=")) {
                        processedLine.set(index, "<<=");
                        processedLine.remove(index + 1);
                        processedLine.remove(index + 1);
                    }
                } else if (token.equals(">") && nextToken.equals(">") && index + 2 < processedLine.size()) {
                    String nextNextToken = processedLine.get(index + 2);
                    if (nextNextToken.equals("=")) {
                        processedLine.set(index, ">>=");
                        processedLine.remove(index + 1);
                        processedLine.remove(index + 1);
                    }
                }
            }
        }
        
        return new TokenizedResult(processedLine, lineNumber, endCode);
    }

    public static ArrayList<ArrayList<TokenizedResult>> reProcessTokens(ArrayList<TokenizedResult> tokensList) {
        // septate every word in a token into its own token
        // so TokenizedResult([package, helix, ;], 1, success) becomes [TokenizedResult(package, 1, success), TokenizedResult(helix, 1, success), TokenizedResult(;, 1, success)]
        ArrayList<TokenizedResult> newTokensList = new ArrayList<>();
        for (TokenizedResult result : tokensList)
            for (String token : result.processedLine)
                newTokensList.add(
                    new TokenizedResult(
                        token, 
                        result.lineNumber, 
                        result.endCode
                    )
                );
        return TokenParser.parseTokens(newTokensList);
    }
}

/*
[package, helix, ;]
[import, helix, ;]
for (TokenizedResult result3 : results) {
    System.out.println(result3.processedWord);
}
[fn, main, (, String, ..., args, ), ->, String, {]
[int, a, =, 1, ;]
[print, (, "Hello, World!", ), ;]
[print, (, f"Hello, World! {a}", ), ;]
[return, "success", ;]
[}]
[interface, Animal, {]
[fn, eat, (, this, ), ->, String, ;]
[fn, sound, (, this, ), ->, String, ;]
[}]
[class, Dog, implements, Animal, {]
[private, String, processedLine, ?, ;]
[fn, eat, (, this, ), ->, String, {]
[return, "Dog is # eating", ;]
[}]
[fn, sound, (, this, ), ->, String, {]
[return, "Woof", ;]
[}]
[}]
 */