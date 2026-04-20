package com.sv.ast;

/**
 * Representa una propiedad clave:valor en el árbol sintáctico
 */
public class PropertyNode extends ASTNode {
    private String key;
    private ASTNode value;

    public PropertyNode(String key, ASTNode value) {
        super(key);
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public ASTNode getValue() {
        return value;
    }

    @Override
    public String getNodeType() {
        return "Property";
    }
}
