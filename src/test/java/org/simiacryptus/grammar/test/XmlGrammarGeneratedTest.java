package org.simiacryptus.grammar.test;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.test.xml.XmlContent.XmlTree;
import org.simiacryptus.grammar.test.xml.generated.GeneratedGrammar;

public class XmlGrammarGeneratedTest extends XmlTestBase
{
  private final Grammar<XmlTree> grammar = GeneratedGrammar.get();

  @Override
  protected Grammar<XmlTree> getGrammar()
  {
    return grammar;
  }
}
