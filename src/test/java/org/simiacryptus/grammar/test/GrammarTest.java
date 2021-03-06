package org.simiacryptus.grammar.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.simiacryptus.grammar.ChoiceGrammar;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.RegexCaptureGrammar;
import org.simiacryptus.grammar.RegexGrammar;

public class GrammarTest
{
  public static <T> ArrayList<T> toList(Iterable<T> matches)
  {
    ArrayList<T> list = new ArrayList<T>();
    for (T m : matches)
    {
      list.add(m);
    }
    return list;
  }

  @Test()
  public void test1()
  {
    RegexGrammar grammar = new RegexGrammar("ca\\w");
    ArrayList<MatchResult<CharSequence>> list = toList(grammar.matchFromStart("catdog"));
    Assert.assertEquals(1, list.size());
    String tree = list.get(0).sequence.toString();
    Assert.assertEquals("cat", tree);
  }
  
  @Test
  public void test2()
  {
    ArrayList<MatchResult<List<CharSequence>>> list = toList(new RegexCaptureGrammar("(\\w+) (\\w+)").matchFromStart("foo bar"));
    MatchResult<List<CharSequence>> matchFromStart = list.get(0);
    Assert.assertEquals("foo", matchFromStart.result.get(0));
    Assert.assertEquals("bar", matchFromStart.result.get(1));
  }

  @Test
  public void test3()
  {
    RegexGrammar grammar = new RegexGrammar("ca\\w");
    ArrayList<MatchResult<CharSequence>> list = toList(grammar.matchFromStart("dogcat"));
    Assert.assertEquals(0, list.size());
  }

  @Test
  public void test4()
  {
    @SuppressWarnings("unchecked")
    ChoiceGrammar<CharSequence> grammar = new ChoiceGrammar<CharSequence>(new RegexGrammar("dog"), new RegexGrammar("cat"));
    ArrayList<MatchResult<CharSequence>> list = toList(grammar.matchFromStart("catdog"));
    Assert.assertEquals(1, list.size());
    String tree = list.get(0).sequence.toString();
    Assert.assertEquals("cat", tree);
  }
  
}
