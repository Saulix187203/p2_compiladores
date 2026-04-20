package com.sv.ast;

/**
 * Representa un objeto JSON en el árbol sintáctico
 */
public class ObjectNode extends ASTNode {
    public ObjectNode() {
        super("Object");
    }

    @Override
    public String getNodeType() {
        return "Object";
    }
}
