package com.github.martydashp.javacodegen.builder;

import com.github.martydashp.javacodegen.model.Generic;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class TypeVariableNameBuilder {

    private final Generic generic;

    public static List<TypeVariableName> getTypeVariableNames(final List<Generic> generics) {
        Objects.requireNonNull(generics);

        return generics.stream()
                       .map(TypeVariableNameBuilder::getTypeVariableName)
                       .collect(Collectors.toList());
    }

    public static TypeVariableName getTypeVariableName(final Generic generic) {
        return new TypeVariableNameBuilder(generic).build();
    }

    protected TypeVariableNameBuilder(final Generic generic) {
        this.generic = generic;
        this.validate();
    }

    protected TypeVariableName build() {
        if (generic.getTypes() != null) {
            final TypeName[] bounds = generic.getTypes().stream()
                                             .map(TypeBuilder::getTypeName)
                                             .toArray(TypeName[]::new);

            return TypeVariableName.get(generic.getName(), bounds);
        }

        return TypeVariableName.get(generic.getName());
    }

    private void validate() {
        Objects.requireNonNull(generic.getName());
    }
}
