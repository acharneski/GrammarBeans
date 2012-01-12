package org.simiacryptus.grammar;

public class MatchResult<T>
{
  public final int start;
  public final int end;
  public final CharSequence sequence;
  public final T result;
  public final Grammar<T> grammar;
  
  public MatchResult(Grammar<T> grammar, CharSequence sequence, int start, int end, T result)
  {
    super();
    this.start = start;
    this.end = end;
    this.sequence = sequence.subSequence(start, end);
    this.result = result;
    this.grammar = (Grammar<T>) grammar;
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("MatchResult [start=");
    builder.append(start);
    builder.append(", end=");
    builder.append(end);
    builder.append(", sequence=");
    builder.append(sequence);
    builder.append(", result=");
    builder.append(result);
    builder.append(", grammar=");
    builder.append(grammar);
    builder.append("]");
    return builder.toString();
  }
  
}