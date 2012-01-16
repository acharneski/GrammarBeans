package org.simiacryptus.grammar.test;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.xml.XmlContent.XmlTree;
import org.simiacryptus.grammar.xml.XmlGrammar;

public class Xml1GrammarTest extends XmlTestBase
{
  private final XmlGrammar grammar = XmlGrammar.instance;

  @Override
  protected Grammar<XmlTree> getGrammar()
  {
    return grammar;
  }
}
