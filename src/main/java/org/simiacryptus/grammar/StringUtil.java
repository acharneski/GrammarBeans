package org.simiacryptus.grammar;

public class StringUtil
{

  public static String escapeStringLiteral(String str)
  {
    str = str.replaceAll("\\\\", "\\\\\\\\");
    str = str.replaceAll("\"", "\\\\\"");
    return str;
  }

}
