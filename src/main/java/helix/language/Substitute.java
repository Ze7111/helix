package helix.language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Substitute {
    public static void main(String[] args) {
        HashMap<String, String> WHITESPACE_ESCAPE = new HashMap<String, String>() {{
            put("\n", "\\n");
            put("\t", "\\t");
            put("\r", "\\r");
            put("\b", "\\b");
            put("\f", "\\f");
        }};
        String i = "foo \n \t bar";
        System.out.print(i + " -> ");
        i = substitute(i, WHITESPACE_ESCAPE);
        System.out.print(i + "reversed -> ");
        i = substitute(i, WHITESPACE_ESCAPE, true);
        System.out.print(i + "\n");

        List<String> i2 = new ArrayList<String>() {{
            add("foo \n \t bar");
            add("baz");
        }};

        System.out.print(i2 + " -> ");
        i2 = substitute(i2, WHITESPACE_ESCAPE);
        System.out.print(i2 + "reversed -> ");
        i2 = substitute(i2, WHITESPACE_ESCAPE, true);
        System.out.print(i2 + "\n");
    }

    /**
     * Substitute a string with a mapping
     * 
     * @param string the string to substitute
     * @param mapping the mapping to substitute with
     * @param safe if true, will not throw an exception if there is an error
     * @param reverse if true, will reverse the mapping
     * @return the substituted string
     */
    public static String substitute(String string,
                                Map<String, String> mapping,
                                boolean safe,
                                boolean reverse) {
        if (reverse) {
            Map<String, String> newMap = new HashMap<>();
            for (Map.Entry<String, String> entry : mapping.entrySet()) {
                newMap.put(entry.getValue(), entry.getKey());
            }
            mapping = newMap;
        }
        try {
            String out = string;
            for (Map.Entry<String, String> entry : mapping.entrySet()) {
                out = out.replace(entry.getKey(), entry.getValue());
            }
            return out;
        } catch (Exception e) {
            if (safe) {
                return string;
            } else {
                throw e;
            }
        }
    }

    /**
     * Substitute a string with a mapping
     * 
     * @param string the string to substitute
     * @param mapping the mapping to substitute with
     * @param safe if true, will not throw an exception if there is an error
     * @return the substituted string
     */
    public static String substitute(String string,
                                Map<String, String> mapping,
                                boolean safe) {
        try {
            String out = string;
            for (Map.Entry<String, String> entry : mapping.entrySet()) {
                out = out.replace(entry.getKey(), entry.getValue());
            }
            return out;
        } catch (Exception e) {
            if (safe) {
                return string;
            } else {
                throw e;
            }
        }
    }

    /**
     * Substitute a string with a mapping
     * 
     * @param string the string to substitute
     * @param mapping the mapping to substitute with
     * @return the substituted string
     */
    public static String substitute(String string,
                                Map<String, String> mapping) {
        try {
            String out = string;
            for (Map.Entry<String, String> entry : mapping.entrySet()) {
                out = out.replace(entry.getKey(), entry.getValue());
            }
            return out;
        } catch (Exception e) {
            return string; 
        }
    }

    /**
     * Substitute a list of strings with a mapping
     * 
     * @param string the string to substitute
     * @param mapping the mapping to substitute with
     * @param safe if true, will not throw an exception if there is an error
     * @param reverse if true, will reverse the mapping
     * @return the substituted List of strings
     */
    public static List<String> substitute(List<String> string,
                                          Map<String, String> mapping,
                                          boolean safe,
                                          boolean reverse) {
        if (reverse) {
            Map<String, String> newMap = new HashMap<>();
            for (Map.Entry<String, String> entry : mapping.entrySet()) {
                newMap.put(entry.getValue(), entry.getKey());
            }
            mapping = newMap;
        }
        List<String> out = new ArrayList<>();
        for (String i : string) {
            out.add(substitute(i, mapping, safe, false));
        }
        return out;
    }

    /**
     * Substitute a list of strings with a mapping
     * 
     * @param string the string to substitute
     * @param mapping the mapping to substitute with
     * @param safe if true, will not throw an exception if there is an error
     * @return the substituted List of strings
     */
    public static List<String> substitute(List<String> string,
                                          Map<String, String> mapping,
                                          boolean safe) {
        List<String> out = new ArrayList<>();
        for (String i : string) {
            out.add(substitute(i, mapping, safe, false));
        }
        return out;
    }

    /**
     * Substitute a list of strings with a mapping
     * 
     * @param string the string to substitute
     * @param mapping the mapping to substitute with
     * @return the substituted List of strings
     */
    public static List<String> substitute(List<String> string,
                                          Map<String, String> mapping) {
        List<String> out = new ArrayList<>();
        for (String i : string) {
            out.add(substitute(i, mapping, true, false));
        }
        return out;
    }
}
