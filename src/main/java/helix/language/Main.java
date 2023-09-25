package helix.language;

import org.python.util.PythonInterpreter;


public class Main {
    public static void main(String[] args) {
        PythonInterpreter interpreter = new PythonInterpreter();
        
        // Set a variable with the content you want to work with
        interpreter.set("code", "print('Hello World!')");

        // Simple use Pygments as you would in Python
        interpreter.exec("from pygments import highlight\n"
            + "from pygments.lexers import PythonLexer\n"
            + "from pygments.formatters import Terminal256Formatter\n"
            + "\nresult = highlight(code, PythonLexer(), Terminal256Formatter())");

        // Get the result that has been set in a variable
        System.out.println(interpreter.get("result", String.class));
        interpreter.close();
    }
}