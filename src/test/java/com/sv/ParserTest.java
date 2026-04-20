package com.sv;

import org.junit.jupiter.api.Test;
import com.sv.ast.*;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    public ASTNode parseJSON(String input_json) {
        Lexer lexer = new Lexer(new StringReader(input_json));
        Parser parser = new Parser(lexer);
        try {
            Object result = parser.parse().value;
            if (result instanceof ASTNode) {
                System.out.println("✓ JSON válido - Árbol sintáctico generado");
                return (ASTNode) result;
            } else {
                System.out.println("✗ Resultado no es un ASTNode");
                return null;
            }
        } catch (Exception e) {
            System.out.println("✗ Error al parsear JSON: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void testParser() {
        try {
            // Leer el JSON desde el archivo ejemplo_entrada.txt
            String input_json = leerJSONDelArchivo("ejemplo_entrada.txt");

            if (input_json == null || input_json.isEmpty()) {
                System.out.println("❌ El archivo ejemplo_entrada.txt está vacío o no existe");
                return;
            }

            System.out.println("📄 JSON leído desde archivo:");
            System.out.println(input_json);
            System.out.println("\n" + "═".repeat(50));

            ASTNode ast = parseJSON(input_json);
            assertTrue(ast != null, "El árbol sintáctico debería haberse generado");

            // Generar visualización con Graphviz
            if (ast != null) {
                try {
                    GraphvizGenerator generator = new GraphvizGenerator();
                    System.out.println("\n" + "─".repeat(50));
                    System.out.println("Representación del AST:");
                    System.out.println("─".repeat(50));
                    generator.printDot(ast);
                    generator.generateAndSave(ast, "ast_entrada.dot");
                    System.out.println("✅ Archivo 'ast_entrada.dot' generado en target/ast/");
                } catch (Exception e) {
                    System.out.println("❌ Error al generar archivo Graphviz: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al procesar el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lee el contenido JSON desde un archivo
     * Busca el archivo en la raíz del proyecto y en target/
     */
    private String leerJSONDelArchivo(String nombreArchivo) throws Exception {
        try {
            // Intentar leer desde la raíz del proyecto
            String contenido = new String(Files.readAllBytes(Paths.get(nombreArchivo)));
            return contenido.trim();
        } catch (Exception e1) {
            try {
                // Si no está en la raíz, intentar en target/
                String contenido = new String(Files.readAllBytes(Paths.get("target/" + nombreArchivo)));
                return contenido.trim();
            } catch (Exception e2) {
                System.out.println("⚠️  No se encontró el archivo " + nombreArchivo);
                System.out.println("   Buscado en: " +
                    Paths.get(nombreArchivo).toAbsolutePath() + " o " +
                    Paths.get("target/" + nombreArchivo).toAbsolutePath());
                throw new Exception("Archivo no encontrado: " + nombreArchivo);
            }
        }
    }
}
