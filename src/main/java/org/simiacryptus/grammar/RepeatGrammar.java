package org.simiacryptus.grammar;


public abstract class RepeatGrammar<T> extends ChainGrammar<T>
{
  protected Grammar<?> inner;
  protected int min;
  protected int max;

  protected RepeatGrammar(Class<? super T> resultType, Grammar<?> inner, int min, int max)
  {
    this(resultType, min, max);
    setInner(inner);
  }

  protected RepeatGrammar(Class<? super T> resultType, int min, int max)
  {
    super(resultType);
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
