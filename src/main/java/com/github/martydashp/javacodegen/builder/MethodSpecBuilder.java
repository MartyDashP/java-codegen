package com.github.martydashp.javacodegen.builder;

import com.github.martydashp.javacodegen.model.Method;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public final class MethodSpecBuilder {

    private final Method method;
    private MethodSpec.Builder builder;

    public static List<MethodSpec> getMethodSpecs(List<Method> methods) {
        Objects.requireNonNull(methods);

        return methods.stream()
                      .map(MethodSpecBuilder::getMethodSpec)
                      .collect(Collectors.toList());
    }

    public static MethodSpec getMethodSpec(Method method) {
        return new MethodSpecBuilder(method).build();
    }

    protected MethodSpecBuilder(Method method) {
        this.method = method;
        this.validate();
    }

    protected MethodSpec build() {
        if (method.isConstructor()) {
            builder = MethodSpec.constructorBuilder();
        } else {
            builder = MethodSpec.methodBuilder(method.getName());
            addReturnType();
        }

        addAnnotations();
        addModifiers();
        addJavaDoc();
        addCode();
        addParameters();
        addTypeVariables();
        addExceptions();

        return builder.build();
    }

    private void validate() {
        Objects.requireNonNull(method);
        Objects.requireNonNull(method.getName());

        if (!method.isConstructor()) {
            Objects.requireNonNull(method.getReturnType());
        }
    }

    private void addReturnType() {
        final TypeName returnType = TypeBuilder.getTypeName(method.getReturnType());
        builder.returns(returnType);
    }

    private void addModifiers() {
        if (method.getModifiers() != null) {
            final Modifier[] modifiers = ModifierBuilder.getModifiers(method.getModifiers());
            builder.addModifiers(modifiers);
        }
    }

    private void addAnnotations() {
        if (method.getAnnotations() != null) {
            final List<AnnotationSpec> specs = AnnotationBuilder.getAnnotationSpecs(method.getAnnotations());
            builder.addAnnotations(specs);
        }
    }

    private void addJavaDoc() {
        if (method.getJavaDoc() != null) {
            builder.addJavadoc(method.getJavaDoc());
        }
    }

    private void addCode() {
        if (method.getCode() != null) {
            builder.addCode(method.getCode());
        }
    }

    private void addParameters() {
        if (method.getParameters() != null) {
            List<ParameterSpec> parameterSpecs = ParameterSpecBuilder.getParameterSpecs(method.getParameters());
            builder.addParameters(parameterSpecs);
        }
    }

    private void addTypeVariables() {
        if (method.getGenerics() != null) {
            List<TypeVariableName> typeVariables = TypeVariableNameBuilder.getTypeVariableNames(method.getGenerics());
            builder.addTypeVariables(typeVariables);
        }
    }

    private void addExceptions() {
        final List<TypeName> exceptionTypes = method.getExceptions()
                                                    .stream()
                                                    .map(TypeBuilder::getTypeName)
                                                    .collect(Collectors.toList());
        builder.addExceptions(exceptionTypes);
    }

}
