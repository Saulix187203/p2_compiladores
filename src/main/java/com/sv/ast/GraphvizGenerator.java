package com.sv.ast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generador de archivos Graphviz (.dot) a partir de un Árbol Sintáctico
 */
public class GraphvizGenerator {
    private StringBuilder dotContent;
    private int nodeCounter;
    private List<String> nodes;
    private List<String> edges;

    public GraphvizGenerator() {
        this.nodeCounter = 0;
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Genera el contenido DOT a partir del árbol sintáctico
     */
    public String generateDot(ASTNode root) {
        nodeCounter = 0;
        nodes.clear();
        edges.clear();

        StringBuilder dot = new StringBuilder();
        dot.append("digraph AST {\n");
        dot.append("    rankdir=TB;\n");
        dot.append("    node [shape=box, style=filled, fillcolor=lightblue];\n");
        dot.append("    edge [color=black];\n\n");

        // Procesar el árbol
        String rootId = processNode(root);

        // Agregar nodos
        for (String node : nodes) {
            dot.append("    ").append(node).append("\n");
        }

        dot.append("\n");

        // Agregar aristas
        for (String edge : edges) {
            dot.append("    ").append(edge).append("\n");
        }

        dot.append("}\n");

        return dot.toString();
    }

    /**
     * Procesa recursivamente un nodo del árbol
     */
    private String processNode(ASTNode node) {
        if (node == null) {
            return null;
        }

        String nodeId = "node" + nodeCounter++;
        String label = node.getLabel();

        // Escapar caracteres especiales para Graphviz
        label = escapeLabel(label);

        // Crear nodo con color según tipo
        String nodeDeclaration = nodeId + " [label=\"" + label + "\", fillcolor=" + getColorByType(node) + "]";
        nodes.add(nodeDeclaration);

        // Procesar hijos
        if (node instanceof PropertyNode) {
            PropertyNode propNode = (PropertyNode) node;
            ASTNode value = propNode.getValue();
            if (value != null) {
                String childId = processNode(value);
                edges.add(nodeId + " -> " + childId);
            }
        } else {
            for (ASTNode child : node.getChildren()) {
                String childId = processNode(child);
                if (childId != null) {
                    edges.add(nodeId + " -> " + childId);
                }
            }
        }

        return nodeId;
    }

    /**
     * Escapa caracteres especiales en las etiquetas
     */
    private String escapeLabel(String label) {
        if (label == null) {
            return "";
        }
        return label.replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r");
    }

    /**
     * Determina el color del nodo según su tipo
     */
    private String getColorByType(ASTNode node) {
        if (node instanceof ObjectNode) {
            return "lightgreen";
        } else if (node instanceof ArrayNode) {
            return "lightyellow";
        } else if (node instanceof PropertyNode) {
            return "lightcyan";
        } else if (node instanceof ValueNode) {
            return "lightcoral";
        }
        return "lightblue";
    }

    /**
     * Guarda el archivo DOT en el sistema de archivos
     */
    public void saveDotFile(String dotContent, String filename) throws IOException {
        File outputDir = new File("target/ast");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File dotFile = new File(outputDir, filename);
        try (FileWriter writer = new FileWriter(dotFile)) {
            writer.write(dotContent);
        }
    }

    /**
     * Genera un archivo DOT a partir del árbol y lo guarda
     */
    public void generateAndSave(ASTNode root, String filename) throws IOException {
        String dotContent = generateDot(root);
        saveDotFile(dotContent, filename);
        System.out.println("Archivo DOT guardado en: target/ast/" + filename);
    }

    /**
     * Imprime el contenido DOT en la consola
     */
    public void printDot(ASTNode root) {
        String dotContent = generateDot(root);
        System.out.println("\n========== GRAPHVIZ DOT OUTPUT ==========");
        System.out.println(dotContent);
        System.out.println("=========================================\n");
    }
}
