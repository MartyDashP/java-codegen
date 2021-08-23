package com.github.martydashp.java_codegen.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import javax.lang.model.element.Modifier;
import org.junit.jupiter.api.Test;

class ModifierBuilderTest {

    @Test
    void getModifiers() {
        Modifier[] modifiers = ModifierBuilder.getModifiers(Arrays.asList("Private", "PUBLIC", "fInaL"));

        assertEquals(3, modifiers.length);
        assertEquals(Modifier.PUBLIC, modifiers[1]);
        assertEquals(Modifier.FINAL, modifiers[2]);
        assertEquals(Modifier.PRIVATE, modifiers[0]);

        modifiers = ModifierBuilder.getModifiers(null);
        assertNotEquals(null, modifiers);
        assertEquals(0, modifiers.length);

        modifiers = ModifierBuilder.getModifiers(Collections.emptyList());
        assertEquals(0, modifiers.length);
    }

    @Test
    void getModifierThrowExceptions() {
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> ModifierBuilder.getModifier("UnknownModifier"));

        String expectedMessage = "Modifier 'UnknownModifier' is unknown";
        assertTrue(exception.getMessage().contains(expectedMessage));

        exception = assertThrows(NullPointerException.class, () -> ModifierBuilder.getModifier(null));

        expectedMessage = "Modifier is null";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void getModifier() {
        final Modifier modifiers = ModifierBuilder.getModifier("ABSTRACT");
        assertEquals(Modifier.ABSTRACT, modifiers);
    }
}