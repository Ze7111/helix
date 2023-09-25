package helix.classes;

import java.util.ArrayList;
import java.util.List;

public class TokenizedResult extends Object {
    public ArrayList<String> processedLine;
    public String processedWord;
    public int lineNumber;
    public String endCode;

    public TokenizedResult(List<String> processedLine, int lineNumber, String endCode) {
        // convert from List to ArrayList
        this.processedLine = new ArrayList<String>();
        this.processedLine.addAll(processedLine);

        this.lineNumber = lineNumber;
        this.endCode = endCode;
    }

    public TokenizedResult(String processedWord, int lineNumber, String endCode) {
        // convert from List to ArrayList
        this.processedWord = processedWord;
        this.lineNumber = lineNumber;
        this.endCode = endCode;
    }

    public String toString() {
        if (this.processedLine == null) {
            return '\"' + this.processedWord + '\"';
        }
        return this.processedLine.toString();
    }
}

/*
TokenizedResult([package, helix, ;], 1, success)
TokenizedResult([import, helix, ;], 2, success)

[TokenizedResult(package, 1, success), TokenizedResult(helix, 1, success)]
Accessing is as follows:
results.get(0).processedLine // package
// for whole line
String line = "";
int lineNumber = 1;
for (int index = 0; index < results.size(); index++) {
    TokenizedResult result = results.get(index);
    if (result.lineNumber == lineNumber) {
        line += result.processedLine;
    } else {
        lineNumber++;
        System.out.println(line);
        line = result.processedLine;
    }
}
 */