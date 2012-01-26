package org.simiacryptus.grammar;

public interface JavaGenerator
{
  String write(JavaFile file);

  String getType();
}
