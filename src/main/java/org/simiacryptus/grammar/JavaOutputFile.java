package org.simiacryptus.grammar;

import java.io.File;

public class JavaOutputFile
{
  public final File baseDir;
  public final String className;
  public final String packageName;

  public JavaOutputFile(File baseDir, String className, String packageName)
  {
    this.baseDir = baseDir;
    this.className = className;
    this.packageName = packageName;
  }

  public File getFile()
  {
    String packagePath = packageName.replaceAll("\\.", "/");
    File packageDir = new File(baseDir, packagePath);
    packageDir.mkdirs();
    return new File(packageDir, className + ".java");
  }
}