package com.github.martydashp.java_codegen.model;

import lombok.Data;

@Data
public class Entry {

    private String key;
    private String value;

    public Entry() {
    }

    public Entry(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
