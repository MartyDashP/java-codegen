package io.github.martydashp

import com.github.martydashp.java_codegen.JavaCodeGen
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class GenerateSourceCodeTask extends DefaultTask {

  @Input()
  String specs

  @Input()
  String targetPath

  @TaskAction
  void exec() {
    final JavaCodeGen codeGen = new JavaCodeGen()
    codeGen.setTargetDir(targetPath)
    codeGen.setSpecs(specs)
    codeGen.generate()
  }

}
