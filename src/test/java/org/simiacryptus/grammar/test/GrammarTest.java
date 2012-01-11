package org.simiacryptus.grammar.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.StringRegexGrammar;
import org.simiacryptus.grammar.test.PseudoXmlContent.PseudoXmlTree;

/**
 * Unit test for simple App.
 */
public class GrammarTest
{
  @Test
  public void test10()
  {
    ArrayList<MatchResult<CharSequence>> list = toList(new StringRegexGrammar("ca\\w").matchFromStart("catdog"));
    Assert.assertEquals(1, list.size());
    String tree = list.get(0).sequence.toString();
    Assert.assertEquals("cat", tree);
  }
  
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
  public void test12()
  {
    ArrayList<MatchResult<List<CharSequence>>> list = toList(new RegexCaptureGrammar("(\\w+) (\\w+)").matchFromStart("foo bar"));
    MatchResult<List<CharSequence>> matchFromStart = list.get(0);
    Assert.assertEquals("foo bar", matchFromStart.result.get(0));
    Assert.assertEquals("foo", matchFromStart.result.get(1));
    Assert.assertEquals("bar", matchFromStart.result.get(2));
  }
  
  @Test
  public void test11()
  {
    ArrayList<MatchResult<CharSequence>> list = toList(new StringRegexGrammar("ca\\w").matchFromStart("dogcat"));
    Assert.assertEquals(0, list.size());
  }
  
  @Test
  public void test2()
  {
    PseudoXmlGrammar g = PseudoXmlGrammar.instance;
    // Needs optional content - currently expects exactly 1 inner node (impossible recursion)
    ArrayList<MatchResult<PseudoXmlTree>> list = toList(g.matchFromStart("<xml></xml>"));
    PseudoXmlTree tree = list.get(0).result;
    PseudoXmlTree expected = new PseudoXmlTree("xml");
    expected.content.add(new PseudoXmlTree("foo"));
    Assert.assertEquals(expected, tree);
  }
  
  @Test
  public void test3()
  {
    PseudoXmlGrammar g = PseudoXmlGrammar.instance;
    // Needs optional content - currently expects exactly 1 inner node (impossible recursion)
    ArrayList<MatchResult<PseudoXmlTree>> list = toList(g.matchFromStart("<xml><foo></foo></xml>"));
    PseudoXmlTree tree = list.get(0).result;
    PseudoXmlTree expected = new PseudoXmlTree("xml");
    expected.content.add(new PseudoXmlTree("foo"));
    Assert.assertEquals(expected, tree);
  }
}
