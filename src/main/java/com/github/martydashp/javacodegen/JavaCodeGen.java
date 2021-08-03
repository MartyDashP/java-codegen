package com.github.martydashp.javacodegen;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.martydashp.javacodegen.model.Source;
import java.io.File;
import java.io.IOException;
import java.util.List;

final public class JavaCodeGen {

    @Parameter(names = {"-specPath"}, required = true)
    private String specPath;

    @Parameter(names = {"-targetPath"}, required = true)
    private String targetPath;

    public static void main(String[] args) throws IOException {
        JavaCodeGen codeGen = new JavaCodeGen();
        JCommander.newBuilder()
                  .addObject(codeGen)
                  .build()
                  .parse(args);

        codeGen.run();
    }

    private void run() throws IOException {
        final List<Source> sourceFileList = SpecReader.ofYAML(specPath);
        final File targetDir = new File(targetPath);

        for (final Source sourceFile : sourceFileList) {
            Generator.generate(sourceFile, targetDir);
        }

    }

}
