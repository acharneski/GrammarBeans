package org.simiacryptus.grammar.bean.impl;

import java.util.ArrayList;
import java.util.List;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.RepeatGrammar;

public class ListRepeatGrammar<T> extends RepeatGrammar<List<T>>
{

  protected ListRepeatGrammar(Grammar<?> inner, int min, int max)
  {
    super(inner, min, max);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected List<T> getResult(List<MatchResult<?>> results)
  {
    ArrayList<T> list = new ArrayList<T>();
    for(MatchResult<?> r : results)
    {
      list.add((T) r.result);
    }
    return list;
  }

  @Override
  protected List<T> getNullResult()
  {
    return new ArrayList<T>();
  }
  
  
}
