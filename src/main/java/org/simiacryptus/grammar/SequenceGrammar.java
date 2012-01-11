package org.simiacryptus.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class SequenceGrammar<T> extends Grammar<T>
{

  protected final List<Grammar<?>> children;
  
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
  public Iterable<MatchResult<T>> matchFromStart(CharSequence string)
  {
    ArrayList<MatchResult<T>> list = new ArrayList<MatchResult<T>>();
    int index = 0;
    List<MatchResult<?>> results = new ArrayList<MatchResult<?>>();
    for(Grammar<?> child : children)
    {
      CharSequence subSequence = string.subSequence(index, string.length());
      Iterable<?> matchFromStart = child.matchFromStart(subSequence);
      // TODO: This flow assumes this iterable is of one or zero elements
      for(Object o : matchFromStart)
      {
        MatchResult<?> result = (MatchResult<?>) o;  
        assert(0 == result.start);
        index += result.end;
        results.add(result);
      }
    }
    list.add(new MatchResult<T>(this, string, 0, index, getResult(results)));
    return list;
  }

  protected abstract T getResult(List<MatchResult<?>> results);

}
