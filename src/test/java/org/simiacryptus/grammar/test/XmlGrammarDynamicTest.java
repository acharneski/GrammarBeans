package org.simiacryptus.grammar.test;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.test.xml.XmlContent.XmlTree;
import org.simiacryptus.grammar.test.xml.XmlGrammar;

public class XmlGrammarDynamicTest extends XmlTestBase
{
  private final XmlGrammar grammar = XmlGrammar.instance;

  @Override
  protected Grammar<XmlTree> getGrammar()
  {
    return grammar;
  }
}
