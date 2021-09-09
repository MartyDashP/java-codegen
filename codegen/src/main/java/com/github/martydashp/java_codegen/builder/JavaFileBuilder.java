package com.github.martydashp.java_codegen.builder;

import com.github.martydashp.java_codegen.model.Source;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.util.Objects;

public final class JavaFileBuilder {

    private final Source source;
    private JavaFile.Builder builder;

    public static JavaFile getJavaFile(final Source src) {
        return new JavaFileBuilder(src).build();
    }

    protected JavaFileBuilder(final Source src) {
        this.source = src;
    }

    protected JavaFile build() {
        Objects.requireNonNull(source);
        Objects.requireNonNull(source.getPackageName());

        final TypeSpec typeSpec = TypeSpecBuilder.getTypeSpec(source.getDefinition());

        builder = JavaFile.builder(source.getPackageName(), typeSpec);
        addComment();

        return builder.build();
    }

    private void addComment() {
        if (source.getComment() != null) {
            builder.addFileComment(source.getComment());
        }
    }

}
