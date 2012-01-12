package org.simiacryptus.grammar.xml;

import java.util.List;

import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.OptionalGrammar;
import org.simiacryptus.grammar.RecursionGrammar;
import org.simiacryptus.grammar.RegexCaptureGrammar;
import org.simiacryptus.grammar.SequenceGrammar;
import org.simiacryptus.grammar.xml.XmlContent.XmlTree;

public class XmlGrammar extends SequenceGrammar<XmlTree>
{

  public static final XmlGrammar instance = new XmlGrammar();
  
  @SuppressWarnings("unchecked")
  private XmlGrammar()
  {
    super(
        new RegexCaptureGrammar("<(\\w+)>"),
        new RecursionGrammar<XmlTree>(),
        new RegexCaptureGrammar("</(\\w+)>")
        );
    ((RecursionGrammar<XmlTree>)get(1)).setInner(new OptionalGrammar<XmlTree>(this));
  }

  @SuppressWarnings("unchecked")
  @Override
  protected XmlTree getResult(List<MatchResult<?>> results)
  {
    MatchResult<List<CharSequence>> tagMatchResult = (MatchResult<List<CharSequence>>) results.get(0);
    XmlTree pseudoXmlTree = new XmlTree(tagMatchResult.result.get(1).toString());
    
    MatchResult<XmlTree> childrenMatchResult = (MatchResult<XmlTree>) results.get(1);
    if(null != childrenMatchResult.result)
    {
      pseudoXmlTree.content.add(childrenMatchResult.result);
    }
    
    tagMatchResult = (MatchResult<List<CharSequence>>) results.get(2);
    String endNodeName = tagMatchResult.result.get(1).toString();
    if(!pseudoXmlTree.nodeName.equals(endNodeName))
    {
      logSyntaxException("End tag does not match start tag", tagMatchResult);
    }
    
    return pseudoXmlTree;
  }
  
}
