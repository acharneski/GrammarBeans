package org.simiacryptus.grammar.test;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;
import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.Log;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.test.xml.XmlContent.XmlTree;

public abstract class XmlTestBase
{

  public static <T> ArrayList<T> toList(Iterable<T> matches)
  {
    ArrayList<T> list = new ArrayList<T>();
    for(T m : matches)
    {
      list.add(m);
    }
    return list;
  }

  protected abstract Grammar<XmlTree> getGrammar();

  public XmlTestBase()
  {
    super();
  }

  @Test
  public void test1()
  {
    XmlTree xml = new XmlTree("xml");
    test(xml);
  }

  @Test
  public void test2()
  {
    XmlTree xml = new XmlTree("xml");
    xml.content.add(new XmlTree("foo"));
    test(xml);
  }

  @Test
  public void test3()
  {
    XmlTree xml = new XmlTree("xml");
    xml.content.add(new XmlTree("foo"));
    xml.attributes.put("foo", "false");
    xml.attributes.put("bar", "true");
    test(xml);
  }

  @Test
  public void test4()
  {
    
    XmlTree xml = new XmlTree("xml");
    XmlTree foo = new XmlTree("foo");
    xml.content.add(foo);
    foo.attributes.put("foo", "false");
    xml.attributes.put("bar", "true");
    test(xml);
  }

  protected void test(XmlTree expected)
  {
    Grammar<XmlTree> grammar = getGrammar();
    Log.debug("Testing xml %s against grammar: %s", expected, grammar);
    ArrayList<MatchResult<XmlTree>> list = toList(grammar.matchFromStart(expected.toString()));
    Assert.assertEquals(1, list.size());
    MatchResult<XmlTree> match = list.get(0);
    Log.debug("Matched %s", match);
    XmlTree tree = match.result;
    Assert.assertEquals(expected, tree);
  }

}