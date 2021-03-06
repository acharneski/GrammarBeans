package org.simiacryptus.grammar.test.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.simiacryptus.grammar.bean.Entries;
import org.simiacryptus.grammar.bean.Regex;
import org.simiacryptus.grammar.bean.Subclasses;

@Subclasses({ XmlContent.XmlTree.class, XmlContent.XmlContentText.class })
public interface XmlContent
{
  public static class XmlContentText extends TestObj implements XmlContent
  {
    @Regex("[^<]+")
    public CharSequence string;

    public XmlContentText()
    {
      this("");
    }

    public XmlContentText(CharSequence string)
    {
      super();
      this.string = string;
    }

    @Override
    public String toString()
    {
      return string.toString();
    }
  }

  public class XmlTree extends TestObj implements XmlContent
  {
    @Regex(capture = true, value = "<(\\w+)")
    public CharSequence nodeName;

    @Entries("\\s+(\\w+)=\"(.*?)\"")
    public Map<CharSequence, CharSequence> attributes = new HashMap<CharSequence, CharSequence>();

    @Regex(">")
    public
    transient CharSequence terminateStartNode = "";

    public List<XmlContent> content = new ArrayList<XmlContent>();

    @Regex(capture = true, value = "</(\\w+)>")
    public CharSequence endNode;

    public XmlTree()
    {
      this("");
    }

    public XmlTree(String name)
    {
      super();
      this.nodeName = name;
      this.endNode = name;
    }

    @Override
    public String toString()
    {
      StringBuilder builder = new StringBuilder();
      builder.append("<");
      builder.append(nodeName);
      for (Entry<CharSequence, CharSequence> e : attributes.entrySet())
      {
        builder.append(" ");
        builder.append(e.getKey());
        builder.append("=\"");
        builder.append(e.getValue());
        builder.append("\"");
      }
      builder.append(">");
      for (XmlContent item : content)
      {
        builder.append(item);
      }
      builder.append("</");
      builder.append(nodeName);
      builder.append(">");
      return builder.toString();
    }
  }
}
