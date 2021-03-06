package com.github.martydashp.java_codegen.builder;

import java.util.List;
import javax.lang.model.element.Modifier;

public final class ModifierBuilder {

    public static Modifier[] getModifiers(final List<String> modifierValueList) {
        if (modifierValueList == null || modifierValueList.size() == 0) {
            return new Modifier[0];
        }

        return modifierValueList.stream()
                                .map(ModifierBuilder::getModifier)
                                .toArray(Modifier[]::new);
    }

    public static Modifier getModifier(final String modifierValue) {
        try {
            return Modifier.valueOf(modifierValue.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(String.format("Modifier '%s' is unknown", modifierValue));
        } catch (NullPointerException ex) {
            throw new NullPointerException("Modifier is null");
        }
    }

}
