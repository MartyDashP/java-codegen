package io.github.martydashp

import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaCodegenPlugin implements Plugin<Project> {
  void apply(Project project) {
    project.extensions.extraProperties.set(GenerateSourceCodeTask.getSimpleName(), GenerateSourceCodeTask)
  }
}
