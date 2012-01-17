package org.simiacryptus.grammar.test;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.test.xml.XmlGrammar;
import org.simiacryptus.grammar.test.xml.XmlContent.XmlTree;

public class XmlGrammarTest
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

  @Test
  public void test2()
  {
    ArrayList<MatchResult<XmlTree>> list = toList(XmlGrammar.instance.matchFromStart("<xml></xml>"));
    Assert.assertEquals(1, list.size());
    XmlTree tree = list.get(0).result;
    XmlTree expected = new XmlTree("xml");
    Assert.assertEquals(expected, tree);
  }
  
  @Test
  public void test3()
  {
    XmlGrammar g = XmlGrammar.instance;
    ArrayList<MatchResult<XmlTree>> list = toList(g.matchFromStart("<xml><foo></foo></xml>"));
    Assert.assertEquals(1, list.size());
    XmlTree tree = list.get(0).result;
    XmlTree expected = new XmlTree("xml");
    expected.content.add(new XmlTree("foo"));
    Assert.assertEquals(expected, tree);
  }
  
  @Test
  public void test4()
  {
    ArrayList<MatchResult<XmlTree>> list = toList(XmlGrammar.instance.matchFromStart("<xml bar=\"true\"></xml>"));
    Assert.assertEquals(1, list.size());
    XmlTree tree = list.get(0).result;
    XmlTree expected = new XmlTree("xml");
    expected.attributes.put("bar", "true");
    Assert.assertEquals(expected, tree);
  }
  
  @Test
  public void test5()
  {
    ArrayList<MatchResult<XmlTree>> list = toList(XmlGrammar.instance.matchFromStart("<xml foo=\"false\" bar=\"true\"></xml>"));
    Assert.assertEquals(1, list.size());
    XmlTree tree = list.get(0).result;
    XmlTree expected = new XmlTree("xml");
    expected.attributes.put("foo", "false");
    expected.attributes.put("bar", "true");
    Assert.assertEquals(expected, tree);
  }

  @Test
  public void test6()
  {
    ArrayList<MatchResult<XmlTree>> list = toList(XmlGrammar.instance.matchFromStart("<xml><foo bar=\"true\"></foo></xml>"));
    Assert.assertEquals(1, list.size());
    XmlTree tree = list.get(0).result;
    XmlTree expected = new XmlTree("xml");
    XmlTree foo = new XmlTree("foo");
    foo.attributes.put("bar", "true");
    expected.content.add(foo);
    Assert.assertEquals(expected, tree);
  }
}
