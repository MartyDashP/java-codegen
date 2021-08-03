package com.github.martydashp.javacodegen;

import com.github.martydashp.javacodegen.model.Annotation;
import com.github.martydashp.javacodegen.model.ClassDefinition;
import com.github.martydashp.javacodegen.model.Definition;
import com.github.martydashp.javacodegen.model.Generic;
import com.github.martydashp.javacodegen.model.Method;
import com.github.martydashp.javacodegen.model.Parameter;
import com.github.martydashp.javacodegen.model.Property;
import com.github.martydashp.javacodegen.model.Source;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

final class Generator {

    static void generate(final Source specModel, final File targetDir) throws IOException {
        assert specModel != null;
        assert targetDir != null;

        final Generator generator = new Generator();
        final JavaFile javaFile = generator.buildJavaFile(specModel);
        javaFile.writeToFile(targetDir);
    }

    private JavaFile buildJavaFile(final Source src) {
        assert src.getDefinition() != null;

        final TypeSpec typeSpec = buildTypeSpec(src.getDefinition());
        JavaFile.Builder builder = JavaFile.builder(src.getPackageName(), typeSpec);

        if (src.getComment() != null) {
            builder.addFileComment(src.getComment());
        }

        return builder.build();
    }

    private TypeSpec buildTypeSpec(final Definition definition) {
        if (definition instanceof ClassDefinition) {
            return buildClassSpec((ClassDefinition) definition);
        }

        throw new RuntimeException("Unknown definition type");
    }

    private TypeSpec buildClassSpec(final ClassDefinition classDefinition) {
        assert classDefinition.getName() != null;

        final TypeSpec.Builder builder = TypeSpec.classBuilder(classDefinition.getName());

        if (classDefinition.getModifiers() != null) {
            final Modifier[] modifiers = getModifiers(classDefinition.getModifiers());
            builder.addModifiers(modifiers);
        }

        if (classDefinition.getGenerics() != null) {
            final List<TypeVariableName> typeVariableNames = buildTypeVariableNames(classDefinition.getGenerics());
            builder.addTypeVariables(typeVariableNames);
        }

        if (classDefinition.getProperties() != null) {
            final List<FieldSpec> fields = buildFieldSpecs(classDefinition.getProperties());
            builder.addFields(fields);
        }

        if (classDefinition.getAnnotations() != null) {
            final List<AnnotationSpec> annotations = buildAnnotationSpecs(classDefinition.getAnnotations());
            builder.addAnnotations(annotations);
        }

        if (classDefinition.getMethods() != null) {
            final List<MethodSpec> methods = buildMethodSpecs(classDefinition.getMethods());
            builder.addMethods(methods);
        }

        if (classDefinition.getSuperInterface() != null) {
            final List<TypeName> interfaces = classDefinition.getSuperInterface()
                                                             .stream()
                                                             .map(this::getTypeName)
                                                             .collect(Collectors.toList());
            builder.addSuperinterfaces(interfaces);
        }

        if (classDefinition.getSuperClassName() != null) {
            final TypeName className = getTypeName(classDefinition.getSuperClassName());
            builder.superclass(className);
        }

        return builder.build();
    }

    private List<FieldSpec> buildFieldSpecs(final List<Property> properties) {
        return properties.stream()
                         .map(this::buildFieldSpec)
                         .collect(Collectors.toList());
    }

    private FieldSpec buildFieldSpec(final Property property) {
        assert property.getName() != null;

        final TypeName typeName = getTypeName(property.getType());
        final FieldSpec.Builder builder = FieldSpec.builder(typeName, property.getName());

        if (property.getAnnotations() != null) {
            final List<AnnotationSpec> annotations = buildAnnotationSpecs(property.getAnnotations());
            builder.addAnnotations(annotations);
        }

        if (property.getModifiers() != null) {
            final Modifier[] modifiers = getModifiers(property.getModifiers());
            builder.addModifiers(modifiers);
        }

        if (property.getJavaDoc() != null) {
            builder.addJavadoc(property.getJavaDoc());
        }

        if (property.getInitValue() != null) {
            builder.initializer(property.getInitValue());
        }

        return builder.build();
    }

    private List<TypeVariableName> buildTypeVariableNames(final List<Generic> generics) {
        return generics.stream().map(this::buildTypeVariableName).collect(Collectors.toList());
    }

    private TypeVariableName buildTypeVariableName(final Generic generic) {
        if (generic.getTypes() != null) {
            final TypeName[] bounds = generic.getTypes().stream()
                                             .map(this::getTypeName)
                                             .toArray(TypeName[]::new);

            return TypeVariableName.get(generic.getName(), bounds);
        }

        return TypeVariableName.get(generic.getName());
    }

    private Modifier[] getModifiers(final List<String> modifierValueList) {
        return modifierValueList.stream()
                                .map(this::getModifier)
                                .toArray(Modifier[]::new);
    }

    private Modifier getModifier(final String modifierValue) {
        switch (modifierValue.toLowerCase()) {
            case "final":
                return Modifier.FINAL;
            case "abstract":
                return Modifier.ABSTRACT;
            case "default":
                return Modifier.DEFAULT;
            case "public":
                return Modifier.PUBLIC;
            case "protected":
                return Modifier.PROTECTED;
            case "private":
                return Modifier.PRIVATE;
            case "static":
                return Modifier.STATIC;
            case "transient":
                return Modifier.TRANSIENT;
            case "volatile":
                return Modifier.VOLATILE;
            case "synchronized":
                return Modifier.SYNCHRONIZED;
            case "native":
                return Modifier.NATIVE;
            case "strictfp":
                return Modifier.STRICTFP;
            default:
                throw new RuntimeException(String.format("Modifier '%s' is unknown", modifierValue));
        }
    }

    private List<AnnotationSpec> buildAnnotationSpecs(List<Annotation> annotations) {
        return annotations.stream()
                          .map(this::buildAnnotation)
                          .collect(Collectors.toList());
    }

    private AnnotationSpec buildAnnotation(Annotation annotation) {
        assert annotation.getName() != null;

        final ClassName className = ClassName.bestGuess(annotation.getName());
        final AnnotationSpec.Builder builder = AnnotationSpec.builder(className);

        if (annotation.getValues() != null) {
            annotation.getValues()
                      .forEach(builder::addMember);
        }

        return builder.build();
    }

    private List<MethodSpec> buildMethodSpecs(List<Method> methods) {
        return methods.stream()
                      .map(this::buildMethodSpec)
                      .collect(Collectors.toList());
    }

    private MethodSpec buildMethodSpec(Method method) {
        final MethodSpec.Builder builder;

        if (!method.isConstructor()) {
            assert method.getName() != null;
            assert method.getReturnType() != null;
            builder = MethodSpec.methodBuilder(method.getName());

            final TypeName returnType = getTypeName(method.getReturnType());
            builder.returns(returnType);
        } else {
            builder = MethodSpec.constructorBuilder();
        }

        if (method.getAnnotations() != null) {
            final List<AnnotationSpec> annotations = buildAnnotationSpecs(method.getAnnotations());
            builder.addAnnotations(annotations);
        }

        if (method.getModifiers() != null) {
            final Modifier[] modifiers = getModifiers(method.getModifiers());
            builder.addModifiers(modifiers);
        }

        if (method.getJavaDoc() != null) {
            builder.addJavadoc(method.getJavaDoc());
        }

        if (method.getCode() != null) {
            builder.addCode(method.getCode());
        }

        if (method.getParameters() != null) {
            List<ParameterSpec> parameterSpecs = buildParameterSpecs(method.getParameters());
            builder.addParameters(parameterSpecs);
        }

        if (method.getGenerics() != null) {
            List<TypeVariableName> typeVariableNames = buildTypeVariableNames(method.getGenerics());
            builder.addTypeVariables(typeVariableNames);
        }

        if (!method.isConstructor() && (method.getExceptions() != null)) {
            final List<TypeName> exceptionTypes = method.getExceptions()
                                                        .stream()
                                                        .map(this::getTypeName)
                                                        .collect(Collectors.toList());
            builder.addExceptions(exceptionTypes);
        }

        return builder.build();
    }

    private List<ParameterSpec> buildParameterSpecs(List<Parameter> parameters) {
        return parameters.stream()
                         .map(this::buildParameterSpecs)
                         .collect(Collectors.toList());
    }

    private ParameterSpec buildParameterSpecs(Parameter parameter) {
        assert parameter.getName() != null;
        assert parameter.getType() != null;

        TypeName type = getTypeName(parameter.getType());
        final ParameterSpec.Builder builder = ParameterSpec.builder(type, parameter.getName());

        if (parameter.getModifiers() != null) {
            final Modifier[] modifiers = getModifiers(parameter.getModifiers());
            builder.addModifiers(modifiers);
        }

        if (parameter.getAnnotations() != null) {
            final List<AnnotationSpec> annotationSpecs = buildAnnotationSpecs(parameter.getAnnotations());
            builder.addAnnotations(annotationSpecs);
        }

        if (parameter.getJavaDoc() != null) {
            builder.addJavadoc(parameter.getJavaDoc());
        }

        return builder.build();
    }

    private TypeName getTypeName(String type) {
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
