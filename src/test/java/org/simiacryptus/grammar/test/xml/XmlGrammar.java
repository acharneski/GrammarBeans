package org.simiacryptus.grammar.test.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.OptionalGrammar;
import org.simiacryptus.grammar.RecursionGrammar;
import org.simiacryptus.grammar.RegexCaptureGrammar;
import org.simiacryptus.grammar.RegexGrammar;
import org.simiacryptus.grammar.RepeatGrammar;
import org.simiacryptus.grammar.SequenceGrammar;
import org.simiacryptus.grammar.test.xml.XmlContent.XmlTree;

public class XmlGrammar extends SequenceGrammar<XmlTree>
{
  
  public static final XmlGrammar instance = new XmlGrammar();
  
  private final StartTagGrammar startTag = new StartTagGrammar();
  private final RecursionGrammar<XmlTree> recursionPoint = new RecursionGrammar<XmlTree>();
  private final RegexCaptureGrammar endTag = new RegexCaptureGrammar("</(\\w+)>");

  private static final class AttributeGrammar extends RepeatGrammar<Map<CharSequence, CharSequence>>
  {
    private final RegexCaptureGrammar entry = new RegexCaptureGrammar("\\s+(\\w+)=\"(.*?)\"");
    
    private AttributeGrammar()
    {
      super(0, -1);
      setInner(entry);
    }

    @Override
    protected Map<CharSequence, CharSequence> getResult(List<MatchResult<?>> results)
    {
      HashMap<CharSequence, CharSequence> map = new HashMap<CharSequence, CharSequence>();
      for(MatchResult<?> r : results)
      {
        @SuppressWarnings("unchecked") List<CharSequence> result = ((MatchResult<List<CharSequence>>) r).result;
        map.put(result.get(0), result.get(1));
      }
      return map;
    }
  }

  private static final class StartTagGrammar extends SequenceGrammar<XmlTree>
  {
    private final RegexCaptureGrammar name = new RegexCaptureGrammar("<(\\w+)");
    private final AttributeGrammar attributes = new AttributeGrammar();
    private final RegexGrammar end = new RegexGrammar(">");
    
    private StartTagGrammar()
    {
      super();
      add(name);
      add(attributes);
      add(end);
    }

    @Override
    protected XmlTree getResult(List<MatchResult<?>> results)
    {
      MatchResult<List<CharSequence>> tagName = name.cast(results.get(0));
      XmlTree xmlTree = new XmlTree(tagName.result.get(0).toString());
      if(results.get(1).result instanceof Map)
      {
        Map<CharSequence, CharSequence> attributeMap = attributes.cast(results.get(1)).result;
        for(Entry<CharSequence, CharSequence> e : attributeMap.entrySet())
        {
          xmlTree.attributes.put(e.getKey().toString(), e.getValue().toString());
        }
      }
      return xmlTree;
    }
  }

  private XmlGrammar()
  {
    super();
    add(startTag);
    add(recursionPoint);
    add(endTag);
    recursionPoint.setInner(new OptionalGrammar<XmlTree>(this));
  }

  @Override
  protected XmlTree getResult(List<MatchResult<?>> results)
  {
    XmlTree pseudoXmlTree = startTag.cast(results.get(0)).result;
    
    MatchResult<XmlTree> childrenMatchResult = recursionPoint.cast(results.get(1));
    if(null != childrenMatchResult.result)
    {
      pseudoXmlTree.content.add(childrenMatchResult.result);
    }
    
    MatchResult<List<CharSequence>> endTagMatch = endTag.cast(results.get(2));
    String endNodeName = endTagMatch.result.get(0).toString();
    if(!pseudoXmlTree.nodeName.equals(endNodeName))
    {
      logSyntaxException("End tag does not match start tag", endTagMatch);
    }
    
    return pseudoXmlTree;
  }
  
}
