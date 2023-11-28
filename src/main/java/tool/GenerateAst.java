package tool;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("ERROR : Missing output directory!");
            System.exit(64);
        }
        String outputDir = args[0];

        defineAst(outputDir, "Expr", List.of(
                "Binary: Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal : Object value",
                "Unary : Token operator, Expr right"
        ));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        Path path1 = Path.of(outputDir + ".java");
        BufferedWriter writer = Files.newBufferedWriter(path1, StandardCharsets.UTF_8);


        writer.write("package com.craftinginterpreters.lox");
        writer.newLine();
        writer.write("import java.util.list");
        writer.newLine();
        writer.write("abstract class " + baseName + " {");

        for (String type : types) {
            String classname = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, classname, fields);
        }
        writer.write("}");
        writer.close();
    }


    private static void defineType(
            BufferedWriter writer, String baseName, String className, String fieldList) throws IOException {

        writer.write(" static class " + className + " extends " +
                baseName + " {");
        writer.newLine();

        // Constructor.
        writer.write(" " + className + "(" + fieldList + ") {");
        writer.newLine();

        // Store parameters in fields.
        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.write(" this." + name + " = " + name + ";");
            writer.newLine();
        }

        writer.write(" }");
        writer.newLine();

        // Fields.
        writer.newLine();
        for (String field : fields) {
            writer.write(" final " + field + ";");
            writer.newLine();
        }

        writer.write(" }");
        writer.newLine();
    }
}

