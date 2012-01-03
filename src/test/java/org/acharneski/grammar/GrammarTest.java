package org.acharneski.grammar;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class GrammarTest
{
  @Test
  public void test10()
  {
    String tree = new StringRegexGrammar("ca\\w").matchFromStart("catdog").sequence.toString();
    Assert.assertEquals("cat", tree);
  }
  
  @Test
  public void test11()
  {
    MatchResult<CharSequence> tree = new StringRegexGrammar("ca\\w").matchFromStart("dogcat");
    Assert.assertNull(tree);
  }
  
  @Test
  public void test2()
  {
    PseudoXmlGrammar g = new PseudoXmlGrammar();
    PseudoXmlTree tree = g.matchFromStart("catdog").result;
    Assert.assertEquals(new PseudoXmlTree("cat", "dog"), tree);
  }
}
