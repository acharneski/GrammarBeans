package org.simiacryptus.grammar;


public abstract class RepeatGrammar<T> extends ChainGrammar<T>
{

  private final Grammar<?> inner;

  public RepeatGrammar(RegexCaptureGrammar inner, int min, int max)
  {
    super(min, max);
    if(null == inner) throw new NullPointerException();
    this.inner = inner;
  }

  @Override
  public Grammar<?> get(int i)
  {
    return inner;
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("RepeatGrammar [inner=");
    builder.append(inner);
    builder.append(", min=");
    builder.append(min);
    builder.append(", max=");
    builder.append(max);
    builder.append("]");
    return builder.toString();
  }

  
}
