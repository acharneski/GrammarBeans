package org.simiacryptus.grammar.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public interface XmlContent
{
  public static class XmlContentText implements XmlContent
  {
    public final CharSequence string;

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

  public class XmlTree implements XmlContent
  {

    public final String nodeName;
    public final Map<String, String> attributes = new HashMap<String, String>();
    public final List<XmlContent> content = new ArrayList<XmlContent>();

    public XmlTree(String name)
    {
      super();
      this.nodeName = name;
    }

    @Override
    public String toString()
    {
      StringBuilder builder = new StringBuilder();
      builder.append("<");
      builder.append(nodeName);
      boolean first = true;
      for(Entry<String, String> e : attributes.entrySet())
      {
        if(first)
        {
          first = false;
        }
        else
        {
          builder.append(" ");
        }
        builder.append(e.getKey());
        builder.append("=\"");
        builder.append(e.getValue());
        builder.append("\"");
      }
      builder.append(">");
      for(XmlContent item : content)
      {
        builder.append(item);
      }
      builder.append("</");
      builder.append(nodeName);
      builder.append(">");
      return builder.toString();
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
      result = prime * result + ((content == null) ? 0 : content.hashCode());
      result = prime * result + ((nodeName == null) ? 0 : nodeName.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj)
    {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      XmlTree other = (XmlTree) obj;
      if (attributes == null)
      {
        if (other.attributes != null)
          return false;
      } else if (!attributes.equals(other.attributes))
        return false;
      if (content == null)
      {
        if (other.content != null)
          return false;
      } else if (!content.equals(other.content))
        return false;
      if (nodeName == null)
      {
        if (other.nodeName != null)
          return false;
      } else if (!nodeName.equals(other.nodeName))
        return false;
      return true;
    }

  }
}
