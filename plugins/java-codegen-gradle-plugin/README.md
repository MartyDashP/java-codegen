## Example connect plugin to Gradle

```groovy
buildscript {
  repositories {
    mavenCentral()
  }

  dependencies {
    classpath 'io.github.martydashp.java_codegen:gradle-plugin:1.1.0'
  }
}

apply plugin: 'java'
apply plugin: 'io.github.martydashp.java_codegen'

...<other code of build.gradle file>...

task generateJavaSources(type: GenerateSourceCodeTask) {
  specs = [<absolute path to specs>]
  targetPath = <absolute path to target dir>
}
```
