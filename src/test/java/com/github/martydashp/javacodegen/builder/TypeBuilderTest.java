package com.github.martydashp.javacodegen.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TypeBuilderTest {

    @Test
    void getClassName() {
        assertEquals("java.lang.Object", TypeBuilder.getClassName("Object").canonicalName());
        assertEquals("java.lang.ClassCastException", TypeBuilder.getClassName("java.lang.ClassCastException").canonicalName());
        assertEquals("com.github.martydashp.TestClass",
            TypeBuilder.getClassName("com.github.martydashp.TestClass").canonicalName());
    }

    @Test
    void getClassNameException() {
        final String type = "short";
        Exception exception = assertThrows(ClassCastException.class,
            () -> TypeBuilder.getClassName(type));

        String expectedMessage = String.format("impossible get class name from value '%s'", type);
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void getTypeName() {
        assertFalse(TypeBuilder.getTypeName("Object").isPrimitive());
        assertFalse(TypeBuilder.getTypeName("Long").isBoxedPrimitive());
        assertFalse(TypeBuilder.getTypeName("Double").isPrimitive());
        assertTrue(TypeBuilder.getTypeName("double").isPrimitive());
    }

    @Test
    void getTypeNameException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TypeBuilder.getClassName(null));

        String expectedMessage = "type value is undefined";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}