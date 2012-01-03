package org.acharneski.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class SequenceGrammar<T> extends Grammar<T>
{

  private final List<Grammar<?>> children;
  
  public SequenceGrammar(Grammar<?>... subgrammars)
  {
    ArrayList<Grammar<?>> list = new ArrayList<Grammar<?>>(subgrammars.length);
    for(Grammar<?> c : subgrammars)
    {
      list.add(c);
    }
    this.children = Collections.unmodifiableList(list);
  }

  @Override
  public MatchResult<T> matchFromStart(CharSequence string)
  {
    int index = 0;
    List<MatchResult<?>> results = new ArrayList<MatchResult<?>>();
    for(Grammar<?> child : children)
    {
      CharSequence subSequence = string.subSequence(index, string.length());
      MatchResult<?> matchFromStart = child.matchFromStart(subSequence);
      assert(0 == matchFromStart.start);
      index += matchFromStart.end;
      results.add(matchFromStart);
    }
    return new MatchResult<T>(this, string, 0, index, getResult(results));
  }

  protected abstract T getResult(List<MatchResult<?>> results);

}
