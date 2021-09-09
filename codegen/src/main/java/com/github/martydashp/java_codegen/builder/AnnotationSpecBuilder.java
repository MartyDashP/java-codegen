package com.github.martydashp.java_codegen.builder;

import com.github.martydashp.java_codegen.model.Annotation;
import com.github.martydashp.java_codegen.model.Entry;
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
        if (annotation.getElements() != null) {

            for (final Entry element : annotation.getElements()) {
                Objects.requireNonNull(element);
                Objects.requireNonNull(element.getKey());

                builder.addMember(element.getKey(), element.getValue());
            }
        }
    }
}
