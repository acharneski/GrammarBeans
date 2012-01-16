package org.simiacryptus.grammar.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.OptionalGrammar;
import org.simiacryptus.grammar.RecursionGrammar;
import org.simiacryptus.grammar.RegexCaptureGrammar;
import org.simiacryptus.grammar.RegexGrammar;
import org.simiacryptus.grammar.RepeatGrammar;
import org.simiacryptus.grammar.SequenceGrammar;
import org.simiacryptus.grammar.xml.XmlContent.XmlTree;

public class XmlGrammar extends SequenceGrammar<XmlTree>
{
  
  private static Grammar<?> endTag = new RegexCaptureGrammar("</(\\w+)>");
  private static RecursionGrammar<XmlTree> recursionPoint = new RecursionGrammar<XmlTree>();
  public static final XmlGrammar instance = new XmlGrammar();

  private static final class AttributeGrammar extends RepeatGrammar<Map<CharSequence, CharSequence>>
  {
    private AttributeGrammar()
    {
      super(new RegexCaptureGrammar("\\s+(\\w+)=\"(.*?)\""), 0, -1);
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

    private StartTagGrammar()
    {
      super(
          new RegexCaptureGrammar("<(\\w+)"), 
          new AttributeGrammar(), 
          new RegexGrammar(">"));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected XmlTree getResult(List<MatchResult<?>> results)
    {
      MatchResult<List<CharSequence>> tagName = (MatchResult<List<CharSequence>>) results.get(0);
      XmlTree xmlTree = new XmlTree(tagName.result.get(0).toString());
      if(results.get(1).result instanceof Map)
      {
        Map<CharSequence, CharSequence> attributes = (Map<CharSequence, CharSequence>) results.get(1).result;
        for(Entry<CharSequence, CharSequence> e : attributes.entrySet())
        {
          xmlTree.attributes.put(e.getKey().toString(), e.getValue().toString());
        }
      }
      return xmlTree;
    }
  }

  private XmlGrammar()
  {
    super(
        //startTag,
        new StartTagGrammar(),
        recursionPoint ,
        endTag
        );
    recursionPoint.setInner(new OptionalGrammar<XmlTree>(this));
  }

  @SuppressWarnings("unchecked")
  @Override
  protected XmlTree getResult(List<MatchResult<?>> results)
  {
    MatchResult<XmlTree> tagMatchResult = (MatchResult<XmlTree>) results.get(0);
    XmlTree pseudoXmlTree = tagMatchResult.result;
    
    MatchResult<XmlTree> childrenMatchResult = (MatchResult<XmlTree>) results.get(1);
    if(null != childrenMatchResult.result)
    {
      pseudoXmlTree.content.add(childrenMatchResult.result);
    }
    
    MatchResult<List<CharSequence>> endTagMatch = (MatchResult<List<CharSequence>>) results.get(2);
    String endNodeName = endTagMatch.result.get(0).toString();
    if(!pseudoXmlTree.nodeName.equals(endNodeName))
    {
      logSyntaxException("End tag does not match start tag", endTagMatch);
    }
    
    return pseudoXmlTree;
  }
  
}
