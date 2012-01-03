package org.acharneski.grammar;

import java.util.List;

public class PseudoXmlGrammar extends SequenceGrammar<PseudoXmlTree>
{

  public PseudoXmlGrammar()
  {
    super(
        new StringRegexGrammar("cat"), 
        new StringRegexGrammar("dog"));
  }

  @Override
  protected PseudoXmlTree getResult(List<MatchResult<?>> results)
  {
    PseudoXmlTree pseudoXmlTree = new PseudoXmlTree();
    pseudoXmlTree.cat = results.get(0).sequence.toString();
    pseudoXmlTree.dog = results.get(1).sequence.toString();
    return pseudoXmlTree;
  }
  
}
