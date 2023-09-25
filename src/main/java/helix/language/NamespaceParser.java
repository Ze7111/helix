package helix.language;

import java.util.ArrayList;
import helix.classes.TokenizedResult;
import helix.classes.Namespace;
import helix.classes.NamespaceList;
import helix.GlobalVariables;

public class NamespaceParser {
    private static final String functionSyntax = GlobalVariables.BaseKeywords.FUNCTION.syntax;
    private static final String classSyntax = GlobalVariables.BaseKeywords.CLASS.syntax; 
    private static final String interfaceSyntax = GlobalVariables.BaseKeywords.INTERFACE.syntax;
    private static final String asyncSyntax = GlobalVariables.BaseKeywords.ASYNC.syntax;
    private static final String enumSyntax = GlobalVariables.BaseKeywords.ENUM.syntax;
    private static final String structSyntax = GlobalVariables.BaseKeywords.STRUCT.syntax;
    
    private static String namespaceName = "";
    public static NamespaceList GetFunctionNamespace(ArrayList<ArrayList<TokenizedResult>> tokens) {
        /*
         * Input looks like this:
         * [[TokenizedResult, TokenizedResult], [TokenizedResult, TokenizedResult]]
         * Output should look like this:
         * [Namespace(name, type, variables, functions) : [TokenizedResult, TokenizedResult], [TokenizedResult, TokenizedResult]]
         */
                
        ArrayList<Namespace> functionsNamespace = new ArrayList<Namespace>();
        ArrayList<Namespace> classesNamespace = new ArrayList<Namespace>();
        ArrayList<Namespace> interfaceNamespace = new ArrayList<Namespace>();
        ArrayList<Namespace> asyncNamespace = new ArrayList<Namespace>();
        ArrayList<Namespace> structNamespace = new ArrayList<Namespace>();
        ArrayList<Namespace> enumNamespace = new ArrayList<Namespace>();
        Namespace globalNamespace = new Namespace(
            "global",
            "global",
            null,
            new ArrayList<ArrayList<TokenizedResult>>()
        );
        
        boolean foundNamespace = false;
        int namespaceBraceStart = 0;
        String namespaceType = "";
        int namespaceBraceCurrent = 0;
        ArrayList<ArrayList<TokenizedResult>> tempFunctions = new ArrayList<ArrayList<TokenizedResult>>();
        ArrayList<ArrayList<TokenizedResult>> tempLines = new ArrayList<ArrayList<TokenizedResult>>();

        for (ArrayList<TokenizedResult> LineOfTokens : tokens) {
            // add all the lines which start with a namespace token(functionSyntax, ...) and end with a brace ignoring all the braced in between by use of a counter
            
            // if start token is class add all lines in class to classNamespace until a the end brace is found
            // if start token is function add all lines in function to functionNamespace until a the end brace is found
            // if start token is interface add all lines in interface to interfaceNamespace until a the end brace is found
            // if start token is async add all lines in async to asyncNamespace until a the end brace is found 
            // if start token is struct add all lines in struct to structNamespace until a the end brace is found 
            // ignore if one of the above is found inside another one of the above
            // and add that to the respective namespace as a regular line
            
            /*
             * exact layout of this function
             * if token.processedWord == functionSyntax
             *     if foundNamespace == false
             *        foundNamespace = true
             *  keep adding LineOfTokens to functionNamespace until the right ending brace is found
             *  if namespaceBraceCurrent == namespaceBraceStart
             *     foundNamespace = false
             *    add functionNamespace to functionsNamespace
             *  keep doing this for all the other types
             * 
             */
            // do the brace counting
            if ((   !LineOfTokens.get(0).processedWord.equals(functionSyntax) 
                 && !LineOfTokens.get(0).processedWord.equals(classSyntax) 
                 && !LineOfTokens.get(0).processedWord.equals(interfaceSyntax) 
                 && !LineOfTokens.get(0).processedWord.equals(asyncSyntax) 
                 && !LineOfTokens.get(0).processedWord.equals(enumSyntax) 
                 && !LineOfTokens.get(0).processedWord.equals(structSyntax)
                )
            ) {
                for (TokenizedResult token : LineOfTokens) {
                    if (token.processedWord.equals("{")) namespaceBraceCurrent++;
                    if (token.processedWord.equals("}")) namespaceBraceCurrent--;
                }
            }

            if (LineOfTokens.get(0).processedWord.equals(functionSyntax)) {
                if (!foundNamespace) {
                    foundNamespace = true;
                    namespaceName = LineOfTokens.get(1).processedWord;
                    namespaceBraceStart = namespaceBraceCurrent;
                    namespaceType = "function";
                } else {
                     tempFunctions.add(LineOfTokens);
                     if (namespaceType == "interface") {
                        // check if there is a brace in the line
                        boolean foundBrace = false;
                        for (TokenizedResult token : LineOfTokens) {
                            if (token.processedWord.equals("}")) foundBrace = true;
                            if (token.processedWord.equals("{")) foundBrace = true;
                        } if (!foundBrace) namespaceBraceCurrent--;
                     }
                }
                tempLines.add(LineOfTokens);
                namespaceBraceCurrent++;
                continue;
            } else if (LineOfTokens.get(0).processedWord.equals(classSyntax)) {
                if (!foundNamespace) {
                    foundNamespace = true;
                    namespaceName = LineOfTokens.get(1).processedWord;
                    namespaceBraceStart = namespaceBraceCurrent;
                    namespaceType = "class";
                }
                tempLines.add(LineOfTokens);
                namespaceBraceCurrent++;
                continue;
            } else if (LineOfTokens.get(0).processedWord.equals(interfaceSyntax)) {
                if (!foundNamespace) {
                    foundNamespace = true;
                    namespaceName = LineOfTokens.get(1).processedWord;
                    namespaceBraceStart = namespaceBraceCurrent;
                    namespaceType = "interface";
                }
                tempLines.add(LineOfTokens);
                namespaceBraceCurrent++;
                continue;
            } else if (LineOfTokens.get(0).processedWord.equals(asyncSyntax)) {
                if (!foundNamespace) {
                    foundNamespace = true;
                    namespaceName = LineOfTokens.get(1).processedWord;
                    namespaceBraceStart = namespaceBraceCurrent;
                    namespaceType = "async";
                } else {
                     tempFunctions.add(LineOfTokens);
                }
                tempLines.add(LineOfTokens);
                namespaceBraceCurrent++;
                continue;
            } else if (LineOfTokens.get(0).processedWord.equals(enumSyntax)) {
                if (!foundNamespace) {
                    foundNamespace = true;
                    namespaceName = LineOfTokens.get(1).processedWord;
                    namespaceBraceStart = namespaceBraceCurrent;
                    namespaceType = "enum";
                }
                tempLines.add(LineOfTokens);
                namespaceBraceCurrent++;
                continue;
            } else if (LineOfTokens.get(0).processedWord.equals(structSyntax)) {
                if (!foundNamespace) {
                    foundNamespace = true;
                    namespaceName = LineOfTokens.get(1).processedWord;
                    namespaceBraceStart = namespaceBraceCurrent;
                    namespaceType = "struct";
                }
                tempLines.add(LineOfTokens);
                namespaceBraceCurrent++;
                continue;
            } 
            
            tempLines.add(LineOfTokens);

            if (foundNamespace) {
                if (namespaceType == "function") {
                    for (TokenizedResult token : LineOfTokens) {
                        if (namespaceBraceCurrent == namespaceBraceStart && token.processedWord.equals("}")) {
                            final String tempNamespaceName = namespaceName;
                            final String tempNamespaceType = namespaceType;
                            functionsNamespace.add(
                                new Namespace(
                                    tempNamespaceName,
                                    tempNamespaceType,
                                    null,
                                    new ArrayList<ArrayList<TokenizedResult>>() {{
                                        addAll(tempLines);
                                    }}
                                )
                            );
                            tempLines.clear();
                            tempFunctions.clear();
                            foundNamespace = false;
                            namespaceName = "";
                        }
                    }
                }

                else if (namespaceType == "class") {
                    for (TokenizedResult token : LineOfTokens) {
                        if (namespaceBraceCurrent == namespaceBraceStart && token.processedWord.equals("}")) {
                            final String tempNamespaceName = namespaceName;
                            final String tempNamespaceType = namespaceType;
                            classesNamespace.add(
                                new Namespace(
                                    tempNamespaceName,
                                    tempNamespaceType,
                                    new ArrayList<ArrayList<TokenizedResult>>() {{
                                        addAll(tempFunctions);
                                    }},
                                    new ArrayList<ArrayList<TokenizedResult>>() {{
                                        addAll(tempLines);
                                    }}
                                )
                            );
                            tempLines.clear();
                            tempFunctions.clear();
                            foundNamespace = false;
                            namespaceName = "";
                        }
                    }
                }

                else if (namespaceType == "interface") {
                    for (TokenizedResult token : LineOfTokens) {
                        if (namespaceBraceCurrent == namespaceBraceStart && token.processedWord.equals("}")) {
                            final String tempNamespaceName = namespaceName;
                            final String tempNamespaceType = namespaceType;
                            interfaceNamespace.add(
                                new Namespace(
                                    tempNamespaceName,
                                    tempNamespaceType,
                                    new ArrayList<ArrayList<TokenizedResult>>() {{
                                        addAll(tempFunctions);
                                    }},
                                    new ArrayList<ArrayList<TokenizedResult>>() {{
                                        addAll(tempLines);
                                    }}
                                )
                            );
                            tempLines.clear();
                            tempFunctions.clear();
                            foundNamespace = false;
                            namespaceName = "";
                        }
                    }
                }

                else if (namespaceType == "async") {
                    for (TokenizedResult token : LineOfTokens) {
                        if (namespaceBraceCurrent == namespaceBraceStart && token.processedWord.equals("}")) {
                            final String tempNamespaceName = namespaceName;
                            final String tempNamespaceType = namespaceType;
                            asyncNamespace.add(
                                new Namespace(
                                    tempNamespaceName,
                                    tempNamespaceType,
                                    null,
                                    new ArrayList<ArrayList<TokenizedResult>>() {{
                                        addAll(tempLines);
                                    }}
                                )
                            );
                            tempLines.clear();
                            tempFunctions.clear();
                            foundNamespace = false;
                            namespaceName = "";
                        }
                    }
                }

                else if (namespaceType == "enum") {
                    for (TokenizedResult token : LineOfTokens) {
                        if (namespaceBraceCurrent == namespaceBraceStart && token.processedWord.equals("}")) {
                            final String tempNamespaceName = namespaceName;
                            final String tempNamespaceType = namespaceType;
                            enumNamespace.add(
                                new Namespace(
                                    tempNamespaceName,
                                    tempNamespaceType,
                                    new ArrayList<ArrayList<TokenizedResult>>() {{
                                        addAll(tempFunctions);
                                    }},
                                    new ArrayList<ArrayList<TokenizedResult>>() {{
                                        addAll(tempLines);
                                    }}
                                )
                            );
                            tempLines.clear();
                            tempFunctions.clear();
                            foundNamespace = false;
                            namespaceName = "";
                        }
                    }
                }

                else if (namespaceType == "struct") {
                    for (TokenizedResult token : LineOfTokens) {
                        if (namespaceBraceCurrent == namespaceBraceStart && token.processedWord.equals("}")) {
                            final String tempNamespaceName = namespaceName;
                            final String tempNamespaceType = namespaceType;
                            structNamespace.add(
                                new Namespace(
                                    tempNamespaceName,
                                    tempNamespaceType,
                                    new ArrayList<ArrayList<TokenizedResult>>() {{
                                        addAll(tempFunctions);
                                    }},
                                    new ArrayList<ArrayList<TokenizedResult>>() {{
                                        addAll(tempLines);
                                    }}
                                )
                            );
                            tempLines.clear();
                            tempFunctions.clear();
                            foundNamespace = false;
                            namespaceName = "";
                        }
                    }
                }
            } else {
                globalNamespace.lines.addAll(tempLines);;
                tempLines.clear();
                tempFunctions.clear();
            }

            if (namespaceBraceCurrent == namespaceBraceStart) foundNamespace = false;
        }
        
        return new NamespaceList(
            functionsNamespace,
            classesNamespace,
            interfaceNamespace,
            asyncNamespace,
            structNamespace,
            enumNamespace,
            globalNamespace
        );
    }
}
