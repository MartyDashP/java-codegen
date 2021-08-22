package com.github.martydashp.javacodegen.builder;

import com.github.martydashp.javacodegen.model.Annotation;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class AnnotationSpecBuilder {

    private final Annotation annotation;
    private ClassName className;
    private AnnotationSpec.Builder builder;

    public static List<AnnotationSpec> getAnnotationSpecs(final List<Annotation> annotations) {
        if (annotations == null || annotations.size() == 0) {
            return Collections.emptyList();
        }

        return annotations.stream()
                          .map(AnnotationSpecBuilder::getAnnotationSpec)
                          .collect(Collectors.toList());
    }

    public static AnnotationSpec getAnnotationSpec(final Annotation annotation) {
        return new AnnotationSpecBuilder(annotation).build();
    }

    protected AnnotationSpecBuilder(final Annotation annotation) {
        this.annotation = annotation;
    }

    protected AnnotationSpec build() {
        Objects.requireNonNull(annotation);
        Objects.requireNonNull(annotation.getName());

        initClassName();
        initBuilder();
        addValues();

        return builder.build();
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
