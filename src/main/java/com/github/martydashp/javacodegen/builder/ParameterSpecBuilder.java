package com.github.martydashp.javacodegen.builder;

import com.github.martydashp.javacodegen.model.Parameter;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public final class ParameterSpecBuilder {

    private final Parameter parameter;
    private TypeName typeName;
    private ParameterSpec.Builder builder;

    public static List<ParameterSpec> getParameterSpecs(final List<Parameter> parameters) {
        return parameters.stream()
                         .map(ParameterSpecBuilder::getParameterSpec)
                         .collect(Collectors.toList());
    }

    public static ParameterSpec getParameterSpec(final Parameter parameter) {
        return new ParameterSpecBuilder(parameter).build();
    }

    protected ParameterSpecBuilder(final Parameter parameter) {
        this.parameter = parameter;
        this.validate();
    }

    protected ParameterSpec build() {
        initTypeName();
        initBuilder();
        addModifiers();
        addAnnotations();
        addJavaDoc();

        return builder.build();
    }

    private void validate() {
        Objects.requireNonNull(parameter);
        Objects.requireNonNull(parameter.getName());
        Objects.requireNonNull(parameter.getType());
    }

    private void initTypeName() {
        typeName = TypeBuilder.getTypeName(parameter.getType());
    }

    private void initBuilder() {
        builder = ParameterSpec.builder(typeName, parameter.getName());
    }


    private void addModifiers() {
        if (parameter.getModifiers() != null) {
            final Modifier[] modifiers = ModifierBuilder.getModifiers(parameter.getModifiers());
            builder.addModifiers(modifiers);
        }
    }

    private void addAnnotations() {
        if (parameter.getAnnotations() != null) {
            final List<AnnotationSpec> specs = AnnotationBuilder.getAnnotationSpecs(parameter.getAnnotations());
            builder.addAnnotations(specs);
        }
    }

    private void addJavaDoc() {
        if (parameter.getJavaDoc() != null) {
            builder.addJavadoc(parameter.getJavaDoc());
        }
    }

}
