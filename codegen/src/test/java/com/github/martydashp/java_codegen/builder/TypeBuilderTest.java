package com.github.martydashp.java_codegen.builder;

import static org.junit.jupiter.api.Assertions.*;

import com.github.martydashp.java_codegen.model.Type;
import org.junit.jupiter.api.Test;

class TypeBuilderTest {

    @Test
    void getClassName() {
        assertEquals("java.lang.Object", TypeBuilder.getClassName(new Type("Object")).canonicalName());
        assertEquals("java.lang.ClassCastException", TypeBuilder.getClassName(new Type("java.lang.ClassCastException")).canonicalName());
        assertEquals("com.github.martydashp.TestClass",
            TypeBuilder.getClassName(new Type("com.github.martydashp.TestClass")).canonicalName());
    }

    @Test
    void getClassNameException() {
        final Type type = new Type("short");
        Exception exception = assertThrows(ClassCastException.class,
            () -> TypeBuilder.getClassName(type));

        String expectedMessage = String.format("impossible get class name from value '%s'", type);
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void getTypeName() {
        assertFalse(TypeBuilder.getTypeName(new Type("Object")).isPrimitive());
        assertFalse(TypeBuilder.getTypeName(new Type("Long")).isBoxedPrimitive());
        assertFalse(TypeBuilder.getTypeName(new Type("Double")).isPrimitive());
        assertTrue(TypeBuilder.getTypeName(new Type("double")).isPrimitive());
    }

    @Test
    void getTypeNameException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TypeBuilder.getClassName(null));

        String expectedMessage = "type value is undefined";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
