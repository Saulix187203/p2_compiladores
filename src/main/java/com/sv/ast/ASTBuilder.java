package com.sv.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilidades para construir y manipular el Árbol Sintáctico
 */
public class ASTBuilder {

    /**
     * Crea un nodo de propiedad a partir de una clave y un valor
     */
    public static PropertyNode createProperty(String key, ASTNode value) {
        return new PropertyNode(key, value);
    }

    /**
     * Crea un nodo de objeto vacío
     */
    public static ObjectNode createEmptyObject() {
        return new ObjectNode();
    }

    /**
     * Crea un nodo de objeto con propiedades
     */
    public static ObjectNode createObject(List<PropertyNode> properties) {
        ObjectNode obj = new ObjectNode();
        for (PropertyNode prop : properties) {
            obj.addChild(prop);
        }
        return obj;
    }

    /**
     * Crea un nodo de array vacío
     */
    public static ArrayNode createEmptyArray() {
        return new ArrayNode();
    }

    /**
     * Crea un nodo de array con valores
     */
    public static ArrayNode createArray(List<ASTNode> values) {
        ArrayNode arr = new ArrayNode();
        for (ASTNode value : values) {
            arr.addChild(value);
        }
        return arr;
    }

    /**
     * Crea un nodo de valor string
     */
    public static ValueNode createStringValue(String value) {
        return new ValueNode(value, "String");
    }

    /**
     * Crea un nodo de valor entero
     */
    public static ValueNode createIntegerValue(int value) {
        return new ValueNode(value, "Integer");
    }

    /**
     * Crea un nodo de valor decimal
     */
    public static ValueNode createDecimalValue(float value) {
        return new ValueNode(value, "Decimal");
    }

    /**
     * Crea un nodo de valor booleano
     */
    public static ValueNode createBooleanValue(boolean value) {
        return new ValueNode(value, "Boolean");
    }

    /**
     * Crea un nodo de valor null
     */
    public static ValueNode createNullValue() {
        return new ValueNode(null, "Null");
    }
}
