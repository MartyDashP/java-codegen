package io.github.martydashp.java_codegen.gradle_plugin

import com.github.martydashp.java_codegen.JavaCodeGen
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

import java.nio.file.FileSystemException

class GenerateSourceCodeTask extends DefaultTask {

  @Input()
  List<String> specs

  @Input()
  String targetPath

  @Input()
  Boolean clearTargetDir = true

  @Input()
  Boolean createTargetDir = false

  @Internal()
  File targetDir

  @TaskAction
  void exec() {
    targetDir = new File(targetPath)

    if (!targetDir.exists()) {
      if (!createTargetDir) {
        throw new FileNotFoundException(targetPath)
      }

      targetDir.mkdirs()
    }

    if (!targetDir.isDirectory()) {
      throw new FileSystemException(targetPath, null, "target path must refer to a directory")
    }

    if (clearTargetDir) {
      for (File innerFile : targetDir.listFiles()) {
        deleteFile(innerFile)
      }
    }

    for (final s : specs) {
      final File javaSpecs = new File(s)

      if (!javaSpecs.exists()) {
        throw new FileNotFoundException(s)
      }

      final JavaCodeGen codeGen = new JavaCodeGen()
      codeGen.setTargetDir(targetDir)
      codeGen.setSpecs(javaSpecs)
      codeGen.generate()
    }
  }

  void deleteFile(File file) {
    if (file.isDirectory()) {
      for (File innerFile : file.listFiles()) {
        deleteFile(innerFile)
      }

      file.deleteDir()
      return
    }

    file.delete()
  }

}
