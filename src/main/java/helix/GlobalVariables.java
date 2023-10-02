package helix;

import java.util.ArrayList;

import helix.classes.TokenizedResult;

public class GlobalVariables {
    public static ArrayList<TokenizedResult> preResults = new ArrayList<>();
    
    public enum DataTypes {
        STRING("Str", "str"),
        INTEGER("Int", "int"),
        FLOAT("Float", "float"),
        BOOLEAN("Bool", "bool"),
        ARRAY("Array", "list"), 
        DICTIONARY("Dict", "dict"),
        SET("Set", "set"),
        CHARACTER("Char", "char"),
        NULL("nil", "None");

        public String syntax;
        public String value;

        DataTypes(String syntax, String value) {
            this.syntax = syntax;
            this.value = value;
        }
    }

    public enum BaseKeywords {
        FUNCTION("fn", "def"),
        CLASS("class", "class"), 
        ENUM("enum", "class"),
        INTERFACE("interface", "class"),
        STRUCT("struct", "class"),
        RETURN("return", "return"),
        IMPORT_PY("import", "import"),
        IF("if", "if"),
        ELSE("else", "else"),
        ELIF("else if", "elif"),
        WHILE("while", "while"), 
        FOR("for", "for"), 
        TRY("try", "try"),
        CATCH("catch", "except"),
        FINALLY("finally", "finally"), 
        THROW("throw", "raise"),
        SWITCH("switch", "match"),
        CASE("case", "case"),
        DEFAULT("let", ""),
        BREAK("break", "break"),
        CONTINUE("continue", "continue"),
        NEW("new", "new"),
        REFERENCE("ref", "global"),
        YELD("next", "yield"),
        ASYNC("async", "async"),
        WITH("with", "with"),
        REM("rem", "del"),
        END("end", "pass");

        public String syntax;
        public String value;
        
        BaseKeywords(String syntax, String value) {
            this.syntax = syntax;
            this.value = value;
        }
    }

    public enum ReplaceInline {
        NAMELESS("nameless", "_"),
        NULL("nil", "None"),
        TRUE("true", "True"),
        FALSE("false", "False"), 
        AND("&&", " and "),
        OR("||", " or "),
        THIS("this", "self"),
        NOT("!", " not ");
        
        public String syntax;
        public String value;
        
        ReplaceInline(String syntax, String value) {
            this.syntax = syntax;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
