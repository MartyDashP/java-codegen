package com.github.martydashp.javacodegen.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.martydashp.javacodegen.model.Annotation;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class AnnotationSpecBuilderTest {

    @Test
    void getAnnotationSpecs() {
        List<AnnotationSpec> result = AnnotationSpecBuilder.getAnnotationSpecs(null);

        assertNotEquals(null, result);
        assertEquals(0, result.size());

        result = AnnotationSpecBuilder.getAnnotationSpecs(Collections.emptyList());

        assertNotEquals(null, result);
        assertEquals(0, result.size());

        List<Annotation> annotations = new ArrayList<>();

        Annotation annotation = new Annotation();
        annotation.setName("TestAnnotation");
        annotations.add(annotation);

        annotation = new Annotation();
        annotation.setName("TestAnnotation2");
        annotations.add(annotation);

        result = AnnotationSpecBuilder.getAnnotationSpecs(annotations);

        assertEquals(2, result.size());
    }

    @Test
    void getAnnotationSpec() {
        Annotation annotation = new Annotation();
        annotation.setName("TestAnnotation");

        AnnotationSpec result = AnnotationSpecBuilder.getAnnotationSpec(annotation);

        assertEquals("TestAnnotation", ((ClassName) result.type).canonicalName());
        assertEquals(0, result.members.size());

        annotation = new Annotation();
        annotation.setName("TestAnnotation2");
        annotation.setValues(Collections.singletonMap("key1", "value1 = {}"));

        result = AnnotationSpecBuilder.getAnnotationSpec(annotation);

        assertEquals("TestAnnotation2", ((ClassName) result.type).canonicalName());
        assertEquals(1, result.members.size());
        assertEquals("value1 = {}", result.members.get("key1").get(0).toString());
        assertTrue(result.members.containsKey("key1"));
    }

    @Test
    void getAnnotationSpecExceptions() {
        assertThrows(NullPointerException.class, () -> AnnotationSpecBuilder.getAnnotationSpec(null));
        assertThrows(NullPointerException.class, () -> AnnotationSpecBuilder.getAnnotationSpec(new Annotation()));
    }
}