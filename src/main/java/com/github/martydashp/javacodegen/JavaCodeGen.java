package com.github.martydashp.javacodegen;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.martydashp.javacodegen.builder.JavaFileBuilder;
import com.github.martydashp.javacodegen.model.Source;
import com.squareup.javapoet.JavaFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

final public class JavaCodeGen {

    @Parameter(names = {"-specPath"}, required = true)
    private String specPath;

    @Parameter(names = {"-targetPath"}, required = true)
    private String targetPath;

    private File specs;
    private File targetDir;

    public static void main(String[] args) throws IOException {
        JavaCodeGen codeGen = new JavaCodeGen();
        JCommander.newBuilder()
                  .addObject(codeGen)
                  .build()
                  .parse(args);

        codeGen.setSpecs(codeGen.specPath);
        codeGen.setTargetDir(codeGen.targetPath);
        codeGen.generate();
    }

    public void setSpecs(String specPath) {
        Objects.requireNonNull(specPath);
        specs = new File(specPath);
    }

    public void setSpecs(File specs) {
        Objects.requireNonNull(specs);
        this.specs = specs;
    }

    public void setTargetDir(String targetPath) {
        Objects.requireNonNull(targetPath);
        this.targetDir = new File(targetPath);
    }

    public void setTargetDir(File targetDir) {
        Objects.requireNonNull(targetDir);
        this.targetDir = targetDir;
    }

    public void generate() throws IOException {
        Objects.requireNonNull(targetDir);
        Objects.requireNonNull(specs);

        if (!targetDir.isDirectory()) {
            throw new RuntimeException("Target path is not directory");
        }

        if (!specs.isDirectory()) {
            generateSources(specs);
            return;
        }

        File[] specFiles = specs.listFiles();

        if (specFiles == null) {
            return;
        }

        for (final File specFile : specFiles) {
            generateSources(specFile);
        }
    }

    private void generateSources(final File spec) throws IOException {
        List<Source> sourceFileList = SpecReader.ofYAML(Objects.requireNonNull(spec));

        for (final Source sourceFile : sourceFileList) {
            final JavaFile javaFile = JavaFileBuilder.getJavaFile(sourceFile);
            javaFile.writeToFile(Objects.requireNonNull(targetDir));
        }
    }
}
