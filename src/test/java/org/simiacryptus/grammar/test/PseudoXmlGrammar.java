package org.simiacryptus.grammar.test;

import java.util.List;

import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.SequenceGrammar;
import org.simiacryptus.grammar.test.PseudoXmlContent.PseudoXmlTree;

public class PseudoXmlGrammar extends SequenceGrammar<PseudoXmlTree>
{

  private static final RecursionGrammar<PseudoXmlTree> recursionPoint = new RecursionGrammar<PseudoXmlTree>();
  public static final PseudoXmlGrammar instance = new PseudoXmlGrammar();
  
  private PseudoXmlGrammar()
  {
    super(
        new RegexCaptureGrammar("<(\\w+)>"),
        recursionPoint,
        new RegexCaptureGrammar("</(\\w+)>")
        );
    recursionPoint.setInner(this);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected PseudoXmlTree getResult(List<MatchResult<?>> results)
  {
    MatchResult<List<CharSequence>> tagMatchResult = (MatchResult<List<CharSequence>>) results.get(0);
    PseudoXmlTree pseudoXmlTree = new PseudoXmlTree(tagMatchResult.result.get(1).toString());
    
    MatchResult<PseudoXmlTree> childrenMatchResult = (MatchResult<PseudoXmlTree>) results.get(1);
    pseudoXmlTree.content.add(childrenMatchResult.result);
    
    tagMatchResult = (MatchResult<List<CharSequence>>) results.get(2);
    String endNodeName = tagMatchResult.result.get(1).toString();
    if(!pseudoXmlTree.nodeName.equals(endNodeName))
    {
      logSyntaxException("End tag does not match start tag", tagMatchResult);
    }
    
    return pseudoXmlTree;
  }
  
}
