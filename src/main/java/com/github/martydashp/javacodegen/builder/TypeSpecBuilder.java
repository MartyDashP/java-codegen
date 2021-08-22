package com.github.martydashp.javacodegen.builder;

import com.github.martydashp.javacodegen.model.ClassDefinition;
import com.github.martydashp.javacodegen.model.EnumConstant;
import com.github.martydashp.javacodegen.model.EnumDefinition;
import com.github.martydashp.javacodegen.model.InterfaceDefinition;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
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

    private final InterfaceDefinition definition;
    private TypeSpec.Builder builder;
    private DefinitionKind definitionKind;

    public static TypeSpec getTypeSpec(final InterfaceDefinition definition) {
        return new TypeSpecBuilder(definition).build();
    }

    protected TypeSpecBuilder(final InterfaceDefinition definition) {
        this.definition = definition;
    }

    private TypeSpec build() {
        Objects.requireNonNull(definition);
        Objects.requireNonNull(definition.getKind());
        Objects.requireNonNull(definition.getName());

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

            case CLASS:
                return buildClassSpec((ClassDefinition) definition);

            case INTERFACE:
                return buildInterfaceSpec(definition);
        }

        throw new RuntimeException("Unknown definition type");
    }

    private TypeSpec buildEnumSpec(final EnumDefinition enumDefinition) {
        builder = TypeSpec.enumBuilder(enumDefinition.getName());

        addModifiers();
        addJavaDoc();
        addAnnotations();
        addTypeVariables();
        addMethods();
        addSuperInterfaces();
        addProperties();
        addEnumConstant();

        return builder.build();
    }

    private TypeSpec buildInterfaceSpec(final InterfaceDefinition interfaceDefinition) {
        builder = TypeSpec.interfaceBuilder(interfaceDefinition.getName());

        addModifiers();
        addJavaDoc();
        addAnnotations();
        addTypeVariables();
        addMethods();
        addSuperInterfaces();
        addProperties();

        return builder.build();
    }

    private TypeSpec buildClassSpec(final ClassDefinition classDefinition) {
        builder = TypeSpec.classBuilder(classDefinition.getName());

        addModifiers();
        addJavaDoc();
        addAnnotations();
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

            if (definitionKind.equals(DefinitionKind.ENUM)) {
                for (final Modifier modifier : builder.modifiers) {
                    if (modifier.equals(Modifier.FINAL)) {
                        throw new RuntimeException("Enum definition can't be final");
                    }
                }
            }

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
            List<TypeVariableName> typeVariables = TypeVariableNameBuilder.getTypeVariableNames(
                definition.getGenerics());
            builder.addTypeVariables(typeVariables);
        }
    }

    private void addProperties() {
        if (definition.getProperties() != null) {
            final List<FieldSpec> fields = FieldSpecBuilder.getFieldSpecs(definition.getProperties());
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
        if (definition.getSuperInterfaces() != null) {
            final List<TypeName> interfaces = definition.getSuperInterfaces()
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

    private void addEnumConstant() {
        final EnumDefinition enumDefinition = (EnumDefinition) definition;

        if (enumDefinition.getConstants() != null) {
            for (EnumConstant enumConstant : enumDefinition.getConstants()) {
                Objects.requireNonNull(enumConstant.getName());

                String constParams = "";

                if (enumConstant.getParameters() != null) {
                    constParams = String.join(", ", enumConstant.getParameters());
                }

                builder.addEnumConstant(enumConstant.getName(),
                    TypeSpec.anonymousClassBuilder(CodeBlock.of(constParams)).build());
            }
        }
    }

}
