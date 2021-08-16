package com.github.martydashp.javacodegen.builder;

import com.github.martydashp.javacodegen.model.Annotation;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class AnnotationBuilder {

    private final Annotation annotation;
    private ClassName className;
    private AnnotationSpec.Builder builder;

    public static List<AnnotationSpec> getAnnotationSpecs(final List<Annotation> annotations) {
        Objects.requireNonNull(annotations);

        return annotations.stream()
                          .map(AnnotationBuilder::getAnnotationSpec)
                          .collect(Collectors.toList());
    }

    public static AnnotationSpec getAnnotationSpec(final Annotation annotation) {
        return new AnnotationBuilder(annotation).build();
    }

    protected AnnotationBuilder(final Annotation annotation) {
        this.annotation = annotation;
        this.validate();
    }

    protected AnnotationSpec build() {
        initClassName();
        initBuilder();
        addValues();

        return builder.build();
    }

    private void validate() {
        Objects.requireNonNull(annotation.getName());
    }

    private void initClassName() {
        className = TypeBuilder.getClassName(annotation.getName());
    }

    private void initBuilder() {
        builder = AnnotationSpec.builder(className);
    }

    private void addValues() {
        if (annotation.getValues() != null) {
            annotation.getValues()
                      .forEach(builder::addMember);
        }
    }
}
