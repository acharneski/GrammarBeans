package org.simiacryptus.grammar.test;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.MatchResult;

public class RecursionGrammar<T> extends Grammar<T>
{
  private Grammar<T> inner = null;
  
  @Override
  public Iterable<MatchResult<T>> matchFromStart(CharSequence charSequence)
  {
    return inner.matchFromStart(charSequence);
  }

  public Grammar<T> getInner()
  {
    return inner;
  }

  public synchronized void setInner(Grammar<T> inner)
  {
    if(null != this.inner) throw new RuntimeException("inner instance already set");
    this.inner = inner;
  }

}
