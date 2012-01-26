package org.simiacryptus.grammar;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaFile
{

  List<String> lines = new ArrayList<String>();
  int varNum = 0;
  public Map<JavaGenerator, String> cache = new HashMap<JavaGenerator, String>();
  
  public void write(JavaOutputFile file) throws IOException
  {
    PrintWriter out = new PrintWriter(file.getFile());
    out.println("import java.util.*;");
    out.println("import org.simiacryptus.grammar.*;");
    out.println("import org.simiacryptus.grammar.bean.impl.*;");
    out.println();
    out.println("public class "+file.className+" {");
    out.println("  public void run(){");
    for (String v : lines)
    {
      out.println(v + ";");
    }
    out.println("  }");
    out.println("}");
    out.close();
  }

  public String newVar(JavaGenerator c)
  {
    if(null == c) return newVar("Object", "null");
    if(cache.containsKey(c))
    {
      return cache.get(c);
    }
    String line = c.write(this);
    String v = newVar(c.getType(), line);
    cache.put(c, v);
    return v;
  }

  public void add(String string)
  {
    lines.add(string);
  }

  public String newVar(String type, String line)
  {
    String v = "v" + ++varNum;
    String l = type + " " + v + " = " + line;
    add(l);
    return v;
  }

}
