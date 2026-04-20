package com.sv;

import java.io.StringReader;
import java.util.Scanner;
import com.sv.ast.*;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔═════════════════════════════════════════╗");
        System.out.println("║       Analizador de JSON                ║");
        System.out.println("╚═════════════════════════════════════════╝");
        System.out.println();

        boolean continuar = true;

        while (continuar) {
            System.out.println("Opciones:");
            System.out.println("1. Ingresar JSON desde línea de comando");
            System.out.println("2. Ingresar JSON en modo multilinea");
            System.out.println("3. Salir");
            System.out.print("\nSelecciona una opción (1-3): ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    procesarJSONLineaComando(scanner);
                    break;
                case "2":
                    procesarJSONMultilinea(scanner);
                    break;
                case "3":
                    continuar = false;
                    System.out.println("\n¡Hasta luego!");
                    break;
                default:
                    System.out.println("❌ Opción inválida. Intenta de nuevo.\n");
            }
        }

        scanner.close();
    }

    /**
     * Procesa JSON ingresado en una sola línea
     */
    private static void procesarJSONLineaComando(Scanner scanner) {
        System.out.print("\nIngresa el JSON en una línea: ");
        String jsonInput = scanner.nextLine().trim();

        if (jsonInput.isEmpty()) {
            System.out.println("❌ El JSON no puede estar vacío.\n");
            return;
        }

        analizarJSON(jsonInput);
    }

    /**
     * Procesa JSON ingresado en múltiples líneas
     */
    private static void procesarJSONMultilinea(Scanner scanner) {
        System.out.println("\nIngresa el JSON (termina con una línea que contenga solo '{}' o '[]'):");
        System.out.println("(Nota: Para terminar, escribe en una nueva línea: FIN)");

        StringBuilder jsonBuilder = new StringBuilder();
        int braces = 0;
        int brackets = 0;

        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine();

            if (linea.trim().equals("FIN")) {
                break;
            }

            jsonBuilder.append(linea).append("\n");

            // Contar llaves y corchetes para detectar fin automático
            for (char c : linea.toCharArray()) {
                if (c == '{') braces++;
                else if (c == '}') braces--;
                else if (c == '[') brackets++;
                else if (c == ']') brackets--;
            }

            // Si todas las llaves y corchetes están cerrados y no está vacío
            if (braces == 0 && brackets == 0 && !jsonBuilder.isEmpty() &&
                (jsonBuilder.toString().trim().startsWith("{") || jsonBuilder.toString().trim().startsWith("["))) {
                break;
            }
        }

        String jsonInput = jsonBuilder.toString().trim();

        if (jsonInput.isEmpty()) {
            System.out.println("❌ El JSON no puede estar vacío.\n");
            return;
        }

        analizarJSON(jsonInput);
    }

    /**
     * Analiza el JSON usando el Lexer y Parser
     */
    private static void analizarJSON(String jsonInput) {
        try {
            System.out.println("\n" + "═".repeat(50));
            System.out.println("Procesando JSON...");
            System.out.println("═".repeat(50));

            Lexer lexer = new Lexer(new StringReader(jsonInput));
            Parser parser = new Parser(lexer);

            Object result = parser.parse().value;

            if (result instanceof ASTNode) {
                System.out.println("✅ JSON válido - Árbol sintáctico generado exitosamente\n");

                ASTNode ast = (ASTNode) result;

                // Mostrar información del AST
                mostrarInfoAST(ast);

                // Ofrecer generar visualización Graphviz
                System.out.print("\n¿Deseas generar visualización Graphviz? (s/n): ");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextLine() && scanner.nextLine().trim().equalsIgnoreCase("s")) {
                    try {
                        GraphvizGenerator generator = new GraphvizGenerator();
                        System.out.println("\n" + "─".repeat(50));
                        System.out.println("Representación del AST:");
                        System.out.println("─".repeat(50));
                        generator.printDot(ast);
                        generator.generateAndSave(ast, "ast_salida.dot");
                        System.out.println("\n✅ Archivo 'ast_salida.dot' generado en el directorio target/");
                    } catch (Exception e) {
                        System.out.println("❌ Error al generar visualización: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("❌ El resultado no es un ASTNode válido\n");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al parsear JSON: " + e.getMessage());
            System.out.println("Detalles: " + e.getClass().getName());
        }

        System.out.println();
    }

    /**
     * Muestra información sobre el AST generado
     */
    private static void mostrarInfoAST(ASTNode ast) {
        System.out.println("Información del AST:");
        System.out.println("  Tipo de raíz: " + ast.getClass().getSimpleName());
        System.out.println("  Número de hijos: " + ast.getChildren().size());

        if (ast instanceof ObjectNode) {
            System.out.println("  └─ Objeto con " + ast.getChildren().size() + " propiedades");
        } else if (ast instanceof ArrayNode) {
            System.out.println("  └─ Array con " + ast.getChildren().size() + " elementos");
        }
    }
}
