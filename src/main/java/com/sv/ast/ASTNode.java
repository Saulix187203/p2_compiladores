package com.sv.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase base para todos los nodos del Árbol Sintáctico Abstracto (AST)
 */
public abstract class ASTNode {
    protected String label;
    protected List<ASTNode> children;

    public ASTNode(String label) {
        this.label = label;
        this.children = new ArrayList<>();
    }

    public void addChild(ASTNode child) {
        if (child != null) {
            this.children.add(child);
        }
    }

    public String getLabel() {
        return label;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public abstract String getNodeType();

    @Override
    public String toString() {
        return label;
    }
}
