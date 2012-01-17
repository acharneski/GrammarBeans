package org.simiacryptus.grammar;


public abstract class RepeatGrammar<T> extends ChainGrammar<T>
{
  private Grammar<?> inner;
  private int min;
  private int max;

  protected RepeatGrammar(Grammar<?> inner, int min, int max)
  {
    this(min, max);
    setInner(inner);
  }

  protected RepeatGrammar(int min, int max)
  {
    this.min = min;
    this.max = max;
  }

  protected void setInner(Grammar<?> inner2)
  {
    if(null == inner2) throw new NullPointerException();
    this.inner = inner2;
  }

  @Override
  public Grammar<?> get(int i)
  {
    return inner;
  }

  protected int getMin()
  {
    return min;
  }

  protected int getMax()
  {
    return max;
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
