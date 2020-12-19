package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cription {
    String checMethod(String[] args) {
        String method = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-alg") && args[i + 1].equals("unicode")) {
                method = args[i + 1];
            } else if (args[i].equals("-alg") && args[i + 1].equals("shift")) {
                method = args[i + 1];
            }
        }
        return method;
    }

    String checAlg(String[] args) {
        String alg = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode") && args[i + 1].equals("dec")) {
                alg = args[i + 1];
            } else if (args[i].equals("-mode") && args[i + 1].equals("enc")) {
                alg = args[i + 1];
            }
        }
        return alg;
    }

    boolean checMassenger(String[] args) {
        boolean checText = false;
        for (String arg : args) {
            if (arg.equals("-data")) {
                checText = true;
                break;
            }
        }
        return checText;
    }

    int checKey(String[] args) {
        int key = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i + 1]);
            }
        }
        return key;
    }

    String readFiles(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    String checFile(String[] args) {
        String rezult = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-in") && !checMassenger(args)) {
                String file = args[i + 1];
                try {
                    rezult = readFiles(file);
                } catch (IOException e) {
                    System.out.println("Error! Cannot read file: " + e.getMessage());
                }
            } else if (args[i].equals("-data")) {
                rezult = args[i + 1];
            }
        }
        return rezult;
    }

    void fileWrite(String str, String[] args) {
        File file = new File(checWrite(args));
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(str);
        } catch (IOException e) {
            System.out.printf("Error! An exception occurs %s", e.getMessage());
        }
    }

    String checWrite(String[] args) {
        String file = " ";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-out")) {
                file = args[i + 1];
            }
        }
        return file;
    }
}
