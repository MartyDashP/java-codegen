package com.github.martydashp.java_codegen.builder;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

public final class TypeBuilder {

    private final String type;

    public static ClassName getClassName(final String type) {
        final TypeName typeName = getTypeName(type);

        if (typeName instanceof ClassName) {
            return (ClassName) typeName;
        }

        throw new ClassCastException(String.format("impossible get class name from value '%s'", type));
    }

    public static TypeName getTypeName(final String type) {
        return new TypeBuilder(type).build();
    }

    private TypeBuilder(String type) {
        this.type = type;
    }

    private TypeName build() {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("type value is undefined");
        }

        switch (type) {
            case "void":
                return TypeName.VOID;
            case "boolean":
                return TypeName.BOOLEAN;
            case "byte":
                return TypeName.BYTE;
            case "short":
                return TypeName.SHORT;
            case "int":
                return TypeName.INT;
            case "long":
                return TypeName.LONG;
            case "char":
                return TypeName.CHAR;
            case "float":
                return TypeName.FLOAT;
            case "double":
                return TypeName.DOUBLE;
            case "Object":
                return TypeName.OBJECT;
        }

        return ClassName.bestGuess(type);
    }
}
