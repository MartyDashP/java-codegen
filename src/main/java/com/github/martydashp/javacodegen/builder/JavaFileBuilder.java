package com.github.martydashp.javacodegen.builder;

import com.github.martydashp.javacodegen.model.Source;
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
        this.validate();
    }

    protected JavaFile build() {
        final TypeSpec typeSpec = TypeSpecBuilder.getTypeSpec(source.getDefinition());

        builder = JavaFile.builder(source.getPackageName(), typeSpec);
        addComment();

        return builder.build();
    }

    private void validate() {
        Objects.requireNonNull(source);
        Objects.requireNonNull(source.getPackageName());
    }

    private void addComment() {
        if (source.getComment() != null) {
            builder.addFileComment(source.getComment());
        }
    }

}
