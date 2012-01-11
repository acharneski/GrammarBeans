package org.simiacryptus.grammar.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PseudoXmlContent
{
  public static class PseudoXmlContentText implements PseudoXmlContent
  {

  }

  public class PseudoXmlTree implements PseudoXmlContent
  {

    public final String nodeName;
    public final Map<String, String> attributes = new HashMap<String, String>();
    public final List<PseudoXmlContent> content = new ArrayList<PseudoXmlContent>();

    public PseudoXmlTree(String name)
    {
      super();
      this.nodeName = name;
    }

  }
}
