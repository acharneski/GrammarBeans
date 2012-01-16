package org.simiacryptus.grammar;

public abstract class Grammar<T>
{

  public abstract Iterable<MatchResult<T>> matchFromStart(CharSequence charSequence);

  protected void logSyntaxException(String msg, MatchResult<?> matchResult)
  {
    throw new RuntimeException(String.format("Syntax Exception: %s (%s)", msg, matchResult));
  }
  
  @SuppressWarnings("unchecked")
  public MatchResult<T> cast(MatchResult<?> result)
  {
    return (MatchResult<T>) result;
  }
}
