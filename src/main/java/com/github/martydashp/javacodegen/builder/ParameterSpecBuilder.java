package com.github.martydashp.javacodegen.builder;

import com.github.martydashp.javacodegen.model.Parameter;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public final class ParameterSpecBuilder {

    private final Parameter parameter;
    private TypeName typeName;
    private ParameterSpec.Builder builder;

    public static List<ParameterSpec> getParameterSpecs(final List<Parameter> parameters) {
        if (parameters == null || parameters.size() == 0) {
            return Collections.emptyList();
        }

        return parameters.stream()
                         .map(ParameterSpecBuilder::getParameterSpec)
                         .collect(Collectors.toList());
    }

    public static ParameterSpec getParameterSpec(final Parameter parameter) {
        return new ParameterSpecBuilder(parameter).build();
    }

    protected ParameterSpecBuilder(final Parameter parameter) {
        this.parameter = parameter;
    }

    protected ParameterSpec build() {
        Objects.requireNonNull(parameter);

        initTypeName();
        initBuilder();
        addModifiers();
        addAnnotations();
        addJavaDoc();

        return builder.build();
    }

    private void initTypeName() {
        Objects.requireNonNull(parameter.getType());

        typeName = TypeBuilder.getTypeName(parameter.getType());
    }

    private void initBuilder() {
        Objects.requireNonNull(typeName);
        Objects.requireNonNull(parameter.getName());

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
            final List<AnnotationSpec> specs = AnnotationSpecBuilder.getAnnotationSpecs(parameter.getAnnotations());
            builder.addAnnotations(specs);
        }
    }

    private void addJavaDoc() {
        if (parameter.getJavaDoc() != null) {
            builder.addJavadoc(parameter.getJavaDoc());
        }
    }

}
