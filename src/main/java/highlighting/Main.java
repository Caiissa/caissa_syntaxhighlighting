package highlighting;

import highlighting.antlr.*;
import org.antlr.v4.runtime.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Anzahl Leerzeichen pro Einrueckstufe (2, 4, 8): ");
        int indentWidth = scanner.nextInt();
        scanner.nextLine();

        // Test
        System.out.print("Pfad zur MiniJava-Datei: ");
        String file = scanner.nextLine();

        String source = Files.readString(Path.of(file));

        MiniJavaLexer lexer = new MiniJavaLexer(CharStreams.fromString(source));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniJavaParser parser = new MiniJavaParser(tokens);

        MiniJavaParser.CompilationUnitContext tree =
            parser.compilationUnit();

        PrettyPrinterVisitor printer =
            new PrettyPrinterVisitor(indentWidth);

        printer.visit(tree);

        System.out.println("\n===== Pretty Printed =====");
        System.out.println(printer.result());
    }
}
