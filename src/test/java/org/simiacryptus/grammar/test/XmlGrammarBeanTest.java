package org.simiacryptus.grammar.test;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.bean.GrammarBean;
import org.simiacryptus.grammar.test.xml.XmlContent.XmlTree;

public class XmlGrammarBeanTest extends XmlTestBase
{
  private final Grammar<XmlTree> grammar = GrammarBean.get(XmlTree.class);

  @Override
  protected Grammar<XmlTree> getGrammar()
  {
    return grammar;
  }
}
