package org.simiacryptus.grammar.test;

import java.io.File;
import org.junit.Test;
import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.JavaOutputFile;
import org.simiacryptus.grammar.JavaValue;
import org.simiacryptus.grammar.bean.GrammarBean;
import org.simiacryptus.grammar.test.xml.XmlContent.XmlTree;

public class XmlGrammarBeanTest extends XmlTestBase
{
  private final Grammar<XmlTree> grammar = GrammarBean.get(XmlTree.class);

  @Test
  public void testGenerate() throws Throwable
  {
    try
    {
      JavaValue javaValue = grammar.getCode(null);
      JavaOutputFile javaOutputFile = new JavaOutputFile(new File("src/test/java/"), "GeneratedGrammar", "org.simiacryptus.grammar.test.xml.generated");
      javaValue.write(javaOutputFile);
    } catch (Throwable e)
    {
      for(StackTraceElement frame : e.getStackTrace())
      {
        System.out.println(frame.toString());
      }
      throw e;
    }
  }
  
  @Override
  protected Grammar<XmlTree> getGrammar()
  {
    return grammar;
  }
}
