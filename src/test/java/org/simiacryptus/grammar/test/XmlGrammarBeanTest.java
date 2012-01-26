package org.simiacryptus.grammar.test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.JavaFile;
import org.simiacryptus.grammar.JavaOutputFile;
import org.simiacryptus.grammar.bean.GrammarBean;
import org.simiacryptus.grammar.test.xml.XmlContent.XmlTree;

public class XmlGrammarBeanTest extends XmlTestBase
{
  private final Grammar<XmlTree> grammar = GrammarBean.get(XmlTree.class);

  @Test
  public void testGenerate() throws IOException
  {
    JavaFile file = new JavaFile();
    file.add(grammar.write(file));
    file.write(new JavaOutputFile(new File("src/test/java/"), "Test"));
  }
  
  @Override
  protected Grammar<XmlTree> getGrammar()
  {
    return grammar;
  }
}
