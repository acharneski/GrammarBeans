package org.simiacryptus.grammar;

import java.io.File;

public class JavaOutputFile
{
  public File baseDir;
  public String className;

  public JavaOutputFile(File baseDir, String className)
  {
    this.baseDir = baseDir;
    this.className = className;
  }

  public File getFile()
  {
    return new File(baseDir, className + ".java");
  }
}