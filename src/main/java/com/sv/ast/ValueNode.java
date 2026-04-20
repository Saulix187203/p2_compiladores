package com.sv.ast;

/**
 * Representa un valor primitivo (String, número, booleano, null) en el árbol sintáctico
 */
public class ValueNode extends ASTNode {
    private Object value;
    private String type;

    public ValueNode(Object value, String type) {
        super(formatLabel(value, type));
        this.value = value;
        this.type = type;
    }

    private static String formatLabel(Object value, String type) {
        if (value == null) {
            return "null";
        }
        if ("String".equals(type)) {
            // Remover comillas del string si existen
            String str = value.toString();
            if (str.startsWith("\"") && str.endsWith("\"")) {
                str = str.substring(1, str.length() - 1);
            }
            return "\"" + str + "\"";
        }
        return value.toString();
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getNodeType() {
        return type;
    }
}
