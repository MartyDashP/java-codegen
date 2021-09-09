package com.github.martydashp.java_codegen.builder;

import static org.junit.jupiter.api.Assertions.*;

import com.github.martydashp.java_codegen.model.Generic;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeVariableName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class TypeVariableNameBuilderTest {

    @Test
    void getTypeVariableNames() {
        List<TypeVariableName> result = TypeVariableNameBuilder.getTypeVariableNames(null);

        assertNotEquals(null, result);
        assertEquals(0, result.size());

        result = TypeVariableNameBuilder.getTypeVariableNames(Collections.emptyList());

        assertNotEquals(null, result);
        assertEquals(0, result.size());

        List<Generic> generics = new ArrayList<>();

        Generic generic = new Generic();
        generic.setName("TestGeneric");
        generic.setTypes(Collections.singletonList("TestType"));
        generics.add(generic);

        generic = new Generic();
        generic.setName("com.github.martydashp.TestGeneric");
        generics.add(generic);

        result = TypeVariableNameBuilder.getTypeVariableNames(generics);

        assertEquals(2, result.size());
    }

    @Test
    void getTypeVariableName() {
        Generic generic = new Generic();
        generic.setName("TestGeneric");
        generic.setTypes(Collections.singletonList("TestType"));

        TypeVariableName result = TypeVariableNameBuilder.getTypeVariableName(generic);

        assertEquals("TestGeneric", result.name);
        assertEquals(1, result.bounds.size());
        assertEquals("TestType", ((ClassName) result.bounds.get(0)).canonicalName());

        generic = new Generic();
        generic.setName("com.github.martydashp.TestGeneric");
        generic.setTypes(Arrays.asList("com.github.martydashp.TestType1", "com.github.martydashp.TestType2", "TestType3"));

        result = TypeVariableNameBuilder.getTypeVariableName(generic);

        assertEquals("com.github.martydashp.TestGeneric", result.name);
        assertEquals(3, result.bounds.size());
        assertEquals("com.github.martydashp.TestType1", ((ClassName) result.bounds.get(0)).canonicalName());
        assertEquals("com.github.martydashp.TestType2", ((ClassName) result.bounds.get(1)).canonicalName());
        assertEquals("TestType3", ((ClassName) result.bounds.get(2)).canonicalName());

        generic = new Generic();
        generic.setName("com.github.martydashp.TestGeneric4");

        result = TypeVariableNameBuilder.getTypeVariableName(generic);

        assertEquals("com.github.martydashp.TestGeneric4", result.name);
        assertEquals(0, result.bounds.size());
    }

    @Test
    void getTypeVariableNameException() {
        Generic generic = new Generic();
        generic.setTypes(Collections.singletonList("TestType"));

        assertThrows(NullPointerException.class, () -> TypeVariableNameBuilder.getTypeVariableName(generic));
    }
}