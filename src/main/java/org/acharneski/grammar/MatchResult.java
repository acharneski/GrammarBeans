package org.acharneski.grammar;

public class MatchResult<T>
{
  private final Grammar<T> parent;
  public final int start;
  public final int end;
  public final CharSequence sequence;
  public final T result;
  public final Grammar<T> grammar;
  
  protected MatchResult(Grammar<T> grammar, CharSequence sequence, int start, int end, T result)
  {
    super();
    parent = grammar;
    this.start = start;
    this.end = end;
    this.sequence = sequence.subSequence(start, end);
    this.result = result;
    this.grammar = (Grammar<T>) parent;
  }
}