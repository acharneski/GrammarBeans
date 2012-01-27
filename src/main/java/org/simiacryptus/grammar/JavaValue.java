package org.simiacryptus.grammar;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class JavaValue
{

  private String typeString;
  private String valueString;
  private List<String> lines = new ArrayList<String>();
  private AtomicInteger varNum;
  private Map<CodeGenerator, String> cache = new HashMap<CodeGenerator, String>();
  private final JavaValue parent;

  public JavaValue(JavaValue parent, String typeString)
  {
    this.varNum = (null==parent)?new AtomicInteger(0):parent.varNum;
    this.parent = parent;
    assert(100 > getDepth());
    this.typeString = typeString;
  }

  private int getDepth()
  {
    if(null == parent) return 0;
    return 1 + parent.getDepth();
  }

  public String getTypeString()
  {
    return typeString;
  }

  public void write(JavaOutputFile file) throws IOException
  {
    PrintWriter out = new PrintWriter(file.getFile());
    out.println("package " + file.packageName + ";");
    out.println("");
    out.println("import java.util.*;");
    out.println("import org.simiacryptus.grammar.*;");
    out.println("import org.simiacryptus.grammar.bean.impl.*;");
    out.println();
    out.println("public class " + file.className + " {");
    out.println("  public static " + getTypeString() + " get(){");
    out.println(getPrefixCode());
    out.println("return " + getValueString() + ";");
    out.println("  }");
    out.println("}");
    out.close();
  }

  public String getPrefixCode()
  {
    StringBuffer sb = new StringBuffer();
    for (String v : lines)
    {
      String line = v;
      sb.append(line);
      if(!line.endsWith(";")) sb.append(";");
      sb.append("\n");
    }
    return sb.toString();
  }

  public String newVar(CodeGenerator c)
  {
    if (null == c)
      return newVar("Object", "null");
    String cachedValue = getCachedVariable(c);
    if (null != cachedValue)
    {
      return cachedValue;
    }
    String newVar = newVar(c.getCode(this));
    cache.put(c, newVar);
    return newVar;
  }
  
  private String newVar(JavaValue c)
  {
    String v = "v" + varNum.incrementAndGet();
    add("final " + c.getTypeString() + " " + v + ";");
    StringBuffer sb = new StringBuffer();
    sb.append("{\n");
    sb.append(c.getPrefixCode());
    sb.append(v + " = " + c.getValueString() + ";\n");
    sb.append("}\n");
    add(sb.toString());
    return v;
  }

  public void alias(CodeGenerator c, CodeGenerator alias)
  {
    cache.put(alias, getCachedVariable(c));
  }

  public String getCachedVariable(CodeGenerator c)
  {
    String cachedValue = cache.get(c);
    if(null != cachedValue) return cachedValue;
    if(null != parent) return parent.getCachedVariable(c);
    return null;
  }

  public void add(String string)
  {
    lines.add(string);
  }

  public String newVar(String type, String line)
  {
    String v = "v" + varNum.incrementAndGet();
    String l = type + " " + v + " = " + line;
    add(l);
    return v;
  }

  public String getValueString()
  {
    return valueString;
  }

  public void setValueString(String valueString)
  {
    this.valueString = valueString;
  }

  public void setTypeString(String typeString)
  {
    this.typeString = typeString;
  }

}
