package com.github.martydashp.java_codegen.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClassDefinition extends InterfaceDefinition {

    private String superClassName;
}
