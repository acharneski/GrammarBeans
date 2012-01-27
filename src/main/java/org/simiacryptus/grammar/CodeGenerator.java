package org.simiacryptus.grammar;

public interface CodeGenerator
{
  JavaValue getCode(JavaValue parent);
  
  String getTypeString();
}