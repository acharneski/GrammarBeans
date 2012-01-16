package org.simiacryptus.grammar;

import java.util.Iterator;


public class OptionalGrammar<T> extends Grammar<T>
{
  private Grammar<T> inner;

  public OptionalGrammar()
  {
    super();
  }

  public OptionalGrammar(Grammar<T> inner)
  {
    this();
    setInner(inner);
  }

  protected void setInner(Grammar<T> inner)
  {
    this.inner = inner;
  }

  @Override
  public Iterable<MatchResult<T>> matchFromStart(final CharSequence charSequence)
  {
    return new Iterable<MatchResult<T>>()
    {
      public Iterator<MatchResult<T>> iterator()
      {
        return new Iterator<MatchResult<T>>()
        {
          Iterator<MatchResult<T>> matchFromStart = null;

          public boolean hasNext()
          {
            return (null == matchFromStart) || matchFromStart.hasNext();
          }

          public MatchResult<T> next()
          {
            if(null == matchFromStart)
            {
              matchFromStart = inner.matchFromStart(charSequence).iterator();
              return new MatchResult<T>(OptionalGrammar.this, charSequence, 0, 0, null);
            }
            return matchFromStart.next();
          }

          public void remove()
          {
            throw new RuntimeException("Operation not supported");
          }
        };
      }
    };
  }

  public Grammar<T> getInner()
  {
    return inner;
  }

}
