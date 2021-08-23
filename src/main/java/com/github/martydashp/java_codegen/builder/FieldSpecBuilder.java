package com.github.martydashp.java_codegen.builder;

import com.github.martydashp.java_codegen.model.Property;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public final class FieldSpecBuilder {

    private final Property property;
    private FieldSpec.Builder builder;

    public static List<FieldSpec> getFieldSpecs(final List<Property> properties) {
        if (properties == null || properties.size() == 0) {
            return Collections.emptyList();
        }

        return properties.stream()
                         .map(FieldSpecBuilder::getFieldSpec)
                         .collect(Collectors.toList());
    }

    public static FieldSpec getFieldSpec(final Property property) {
        return new FieldSpecBuilder(property).build();
    }

    protected FieldSpecBuilder(final Property property) {
        this.property = property;
    }

    protected FieldSpec build() {
        Objects.requireNonNull(property);
        Objects.requireNonNull(property.getName());

        final TypeName typeName = TypeBuilder.getTypeName(property.getType());
        builder = FieldSpec.builder(typeName, property.getName());

        addModifiers();
        addAnnotations();
        addJavaDoc();
        addInitValue();

        return builder.build();
    }

    private void addModifiers() {
        if (property.getModifiers() != null) {
            final Modifier[] modifiers = ModifierBuilder.getModifiers(property.getModifiers());
            builder.addModifiers(modifiers);
        }
    }

    private void addAnnotations() {
        if (property.getAnnotations() != null) {
            final List<AnnotationSpec> specs = AnnotationSpecBuilder.getAnnotationSpecs(property.getAnnotations());
            builder.addAnnotations(specs);
        }
    }

    private void addJavaDoc() {
        if (property.getJavaDoc() != null) {
            builder.addJavadoc(property.getJavaDoc());
        }
    }

    private void addInitValue() {
        if (property.getInitValue() != null) {
            builder.initializer(property.getInitValue());
        }
    }

}
