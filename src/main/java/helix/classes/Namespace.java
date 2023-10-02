package helix.classes;

import java.util.ArrayList;

public class Namespace extends Object {
    public String nameOfNamespace;
    public String typeOfNameSpace;
    public ArrayList<TokenizedResult> namespace = new ArrayList<>();
    public ArrayList<ArrayList<TokenizedResult>> lines;
    public ArrayList<ArrayList<TokenizedResult>> functions;

    public Namespace(String nameOfNamespace, String typeOfNameSpace, ArrayList<ArrayList<TokenizedResult>> arrayList, ArrayList<ArrayList<TokenizedResult>> arrayList2) {
        this.nameOfNamespace = nameOfNamespace;
        this.typeOfNameSpace = typeOfNameSpace;
        // if functions is nothing then set it to an empty arraylist
        if (arrayList == null) arrayList = null;
        else this.functions = arrayList;
        this.lines = arrayList2;
    }

    public String toString() {
        return "Namespace(nameOfNamespace="
              + this.nameOfNamespace
              + ", typeOfNameSpace="
              + this.typeOfNameSpace
              + ", functions="
              + this.functions
              + ", lines="
              + this.lines
              + " )";
    }
}