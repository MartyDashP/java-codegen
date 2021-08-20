package com.github.martydashp.javacodegen.builder;

import com.github.martydashp.javacodegen.model.ClassDefinition;
import com.github.martydashp.javacodegen.model.Definition;
import com.github.martydashp.javacodegen.model.EnumDefinition;
import com.github.martydashp.javacodegen.model.InterfaceDefinition;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public final class TypeSpecBuilder {

    protected enum DefinitionKind {CLASS, ENUM, INTERFACE}

    private final Definition definition;
    private TypeSpec.Builder builder;

    public static TypeSpec getTypeSpec(final Definition definition) {
        return new TypeSpecBuilder(definition).build();
    }

    protected TypeSpecBuilder(final Definition definition) {
        this.definition = definition;
    }

    private TypeSpec build() {
        Objects.requireNonNull(definition);
        Objects.requireNonNull(definition.getKind());
        Objects.requireNonNull(definition.getName());

        DefinitionKind definitionKind;

        try {
            definitionKind = DefinitionKind.valueOf(definition.getKind().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(String.format("definition kind '%s' is unknown", definition.getKind()));
        } catch (NullPointerException ex) {
            throw new NullPointerException("definition kind is undefined");
        }

        switch (definitionKind) {
            case ENUM:
                return buildEnumSpec((EnumDefinition) definition);

            case INTERFACE:
                return buildInterfaceSpec((InterfaceDefinition) definition);

            case CLASS:
                return buildClassSpec((ClassDefinition) definition);
        }

        throw new RuntimeException("Unknown definition type");
    }

    private TypeSpec buildEnumSpec(final EnumDefinition enumDefinition) {
        throw new RuntimeException("method is not implemented");
    }

    private TypeSpec buildInterfaceSpec(final InterfaceDefinition enumDefinition) {
        throw new RuntimeException("method is not implemented");
    }

    private TypeSpec buildClassSpec(final ClassDefinition classDefinition) {
        builder = TypeSpec.classBuilder(classDefinition.getName());

        addModifiers();
        addJavaDoc();
        addAnnotations();
        addJavaDoc();
        addTypeVariables();
        addMethods();
        addSuperInterfaces();
        addProperties();
        addSuperClassName();

        return builder.build();
    }

    private void addModifiers() {
        if (definition.getModifiers() != null) {
            final Modifier[] modifiers = ModifierBuilder.getModifiers(definition.getModifiers());
            builder.addModifiers(modifiers);
        }
    }

    private void addAnnotations() {
        if (definition.getAnnotations() != null) {
            final List<AnnotationSpec> specs = AnnotationSpecBuilder.getAnnotationSpecs(definition.getAnnotations());
            builder.addAnnotations(specs);
        }
    }

    private void addJavaDoc() {
        if (definition.getJavaDoc() != null) {
            builder.addJavadoc(definition.getJavaDoc());
        }
    }

    private void addTypeVariables() {
        if (definition.getGenerics() != null) {
            List<TypeVariableName> typeVariables = TypeVariableNameBuilder.getTypeVariableNames(definition.getGenerics());
            builder.addTypeVariables(typeVariables);
        }
    }

    private void addProperties() {
        final ClassDefinition classDefinition = (ClassDefinition) definition;

        if (classDefinition.getProperties() != null) {
            final List<FieldSpec> fields = FieldSpecBuilder.getFieldSpecs(classDefinition.getProperties());
            builder.addFields(fields);
        }
    }

    private void addMethods() {
        if (definition.getMethods() != null) {
            final List<MethodSpec> methods = MethodSpecBuilder.getMethodSpecs(definition.getMethods());
            builder.addMethods(methods);
        }
    }

    private void addSuperInterfaces() {
        if (definition.getSuperInterface() != null) {
            final List<TypeName> interfaces = definition.getSuperInterface()
                                                        .stream()
                                                        .map(TypeBuilder::getTypeName)
                                                        .collect(Collectors.toList());
            builder.addSuperinterfaces(interfaces);
        }
    }

    private void addSuperClassName() {
        final ClassDefinition classDefinition = (ClassDefinition) definition;

        if (classDefinition.getSuperClassName() != null) {
            final TypeName className = TypeBuilder.getTypeName(classDefinition.getSuperClassName());
            builder.superclass(className);
        }
    }

}
