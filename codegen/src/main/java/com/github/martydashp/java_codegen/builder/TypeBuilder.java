package com.github.martydashp.java_codegen.builder;

import com.github.martydashp.java_codegen.model.Type;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

public final class TypeBuilder {

    private final Type type;

    public static ClassName getClassName(final Type type) {
        final TypeName typeName = getTypeName(type);

        if (typeName instanceof ClassName) {
            return (ClassName) typeName;
        }

        throw new ClassCastException(String.format("impossible get class name from value '%s'", type));
    }

    public static TypeName getTypeName(final Type type) {
        return new TypeBuilder(type).build();
    }

    private TypeBuilder(Type type) {
        this.type = type;
    }

    private TypeName build() {
        if (type == null || type.getName() == null || type.getName().isBlank()) {
            throw new IllegalArgumentException("type value is undefined");
        }

        final TypeName typeName = getTypeName();

        if (type.getArguments() != null) {
            if (!(typeName instanceof ClassName)) {
                throw new ClassCastException(String.format("impossible get class name from value '%s'", type));
            }

            final TypeName[] typeArguments = type.getArguments().stream()
                                                 .map(TypeBuilder::getTypeName)
                                                 .toArray(TypeName[]::new);
            return ParameterizedTypeName.get((ClassName) typeName, typeArguments);
        }

        return typeName;
    }

    private TypeName getTypeName() {
        switch (type.getName()) {
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

        return ClassName.bestGuess(type.getName());
    }
}
