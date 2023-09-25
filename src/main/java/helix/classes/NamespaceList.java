package helix.classes;

import java.util.ArrayList;

public class NamespaceList extends Object {
    public ArrayList<Namespace> functionsNamespace;
    public ArrayList<Namespace> classesNamespace;
    public ArrayList<Namespace> interfaceNamespace;
    public ArrayList<Namespace> asyncNamespace;
    public ArrayList<Namespace> structNamespace;
    public ArrayList<Namespace> enumNamespace;
    public Namespace globalNamespace;
    

    public NamespaceList(
        ArrayList<Namespace> functionsNamespace,
        ArrayList<Namespace> classesNamespace,
        ArrayList<Namespace> interfaceNamespace,
        ArrayList<Namespace> asyncNamespace,
        ArrayList<Namespace> structNamespace,
        ArrayList<Namespace> enumNamespace,
        Namespace globalNamespace
    ) {
        this.functionsNamespace = functionsNamespace;
        this.classesNamespace = classesNamespace;
        this.interfaceNamespace = interfaceNamespace;
        this.asyncNamespace = asyncNamespace;
        this.structNamespace = structNamespace;
        this.enumNamespace = enumNamespace;
        this.globalNamespace = globalNamespace;
    }

    public String toString() {
        return "NamespaceList(functionsNamespace=" + this.functionsNamespace + ",\n\tclassesNamespace=" + this.classesNamespace + ",\n\tinterfaceNamespace=" + this.interfaceNamespace + ",\n\tasyncNamespace=" + this.asyncNamespace + ",\n\tstructNamespace=" + this.structNamespace + ",\n\tenumNamespace=" + this.enumNamespace + ",\n\tglobalNamespace=" + this.globalNamespace + " )";
    }
}