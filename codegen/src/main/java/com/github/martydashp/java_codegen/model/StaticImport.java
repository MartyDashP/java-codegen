package com.github.martydashp.java_codegen.model;

import java.util.List;
import lombok.Data;

@Data
public class StaticImport {

    private Type type;
    private List<String> names;
}
