package helix.language;

public class ArgParse {
    /*
     * ./helix -h -> print help
     * ./helix -v -> print version
     * ./helix -c <file> -> compile file to filename.out
     * ./helix -c <file> -o <outFile> -> compile file to outFile.out
     * ./helix <file> -> run file
     * ./helix -t <file> -> tokenize file
     * ./helix -d <file> -> run file in debug mode
     * ./helix -s <file> -> run file in silent mode (no output only errors)
     * ./helix --time <file> -> time file execution
     * ./helix -w <file> -> watch file for changes and run
     * -h or --help
     * -v or --version
     * -c or --compile
     * -o or --out
     * -t or --tokenize
     * -w or --watch
     * -d or --debug
     * -s or --silent
     */

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments provided");
            System.exit(0);
        }

        if (args[0].equals("-h") || args[0].equals("--help")) {
            System.out.println("Helix Programming Language");
            System.out.println("Usage: ./helix [options] <file>");
            System.out.println("Options:");
            System.out.println("  -h, --help\t\t\t\tPrint this help message");
            System.out.println("  -v, --version\t\t\t\tPrint version");
            System.out.println("  -c, --compile <file>\t\t\tCompile file to filename.out");
            System.out.println("  -c, --compile <file> \n\t-o, --out <outFile>\t\tCompile file to outFile.out");
            System.out.println("  -t, --tokenize <file>\t\t\tTokenize file");
            System.out.println("  -w, --watch <file>\t\t\tWatch file for changes and run");
            System.out.println("  -d, --debug <file>\t\t\tRun file in debug mode");
            System.out.println("  -s, --silent <file>\t\t\tRun file in silent mode (no output only errors)");
            System.out.println("  --time <file>\t\t\t\tTime file execution");
            System.exit(0);
        } else if (args[0].equals("-v") || args[0].equals("--version")) {
            System.out.println("Helix Programming Language v0.0.1");
            System.exit(0);
        } else if (args[0].equals("-c") || args[0].equals("--compile")) {
            if (args.length == 1) {
                System.out.println("No file provided");
                System.exit(0);
            } else if (args.length == 2) {
                System.out.println("Compiling " + args[1] + " to " + args[1] + ".out");
                System.exit(0);
            } else if (args.length == 4) {
                if (args[2].equals("-o") || args[2].equals("--out")) {
                    System.out.println("Compiling " + args[1] + " to " + args[3] + ".out");
                    System.exit(0);
                } else {
                    System.out.println("Invalid option " + args[2]);
                    System.exit(0);
                }
            } else {
                System.out.println("Invalid number of arguments");
                System.exit(0);
            }
        } else if (args[0].equals("-t") || args[0].equals("--tokenize")) {
            if (args.length == 1) {
                System.out.println("No file provided");
                System.exit(0);
            } else if (args.length == 2) {
                System.out.println("Tokenizing " + args[1]);
                System.exit(0);
            } else {
                System.out.println("Invalid number of arguments");
                System.exit(0);
            }
        } else if (args[0].equals("-w") || args[0].equals("--watch")) {
            if (args.length == 1) {
                System.out.println("No file provided");
                System.exit(0);
            } else if (args.length == 2) {
                System.out.println("Watching " + args[1]);
                System.exit(0);
            } else {
                System.out.println("Invalid number of arguments");
                System.exit(0);
            }
        } else if (args[0].equals("-d") || args[0].equals("--debug")) {
            if (args.length == 1) {
                System.out.println("No file provided");
                System.exit(0);
            } else if (args.length == 2) {
                System.out.println("Debugging " + args[1]);
                System.exit(0);
            } else {
                System.out.println("Invalid number of arguments");
                System.exit(0);
            }
        } else if (args[0].equals("-s") || args[0].equals("--silent")) {
            if (args.length == 1) {
                System.out.println("No file provided");
                System.exit(0);
            } else if (args.length == 2) {
                System.out.println("Running " + args[1] + " in silent mode");
                System.exit(0);
            } else {
                System.out.println("Invalid number of arguments");
                System.exit(0);
            }
        } else if (args[0].equals("--time")) {
            if (args.length == 1) {
                System.out.println("No file provided");
                System.exit(0);
            } else if (args.length == 2) {
                System.out.println("Timing " + args[1]);
                System.exit(0);
            } else {
                System.out.println("Invalid number of arguments");
                System.exit(0);
            }
        } else {
            System.out.println("Invalid option " + args[0]);
            System.exit(0);
        }
    }
}
