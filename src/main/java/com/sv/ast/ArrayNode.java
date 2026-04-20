package com.sv.ast;

/**
 * Representa un array JSON en el árbol sintáctico
 */
public class ArrayNode extends ASTNode {
    public ArrayNode() {
        super("Array");
    }

    @Override
    public String getNodeType() {
        return "Array";
    }
}
