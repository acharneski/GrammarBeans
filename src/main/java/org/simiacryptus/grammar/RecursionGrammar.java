package org.simiacryptus.grammar;



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

  boolean insideToString = false;
  
  @Override
  public synchronized String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("RecursionGrammar@");
    builder.append(Integer.toHexString(System.identityHashCode(this)));
    if(!insideToString)
    {
      insideToString = true;
      builder.append(" [inner=");
      builder.append(inner);
      builder.append("]");
      insideToString = false;
    }
    return builder.toString();
  }

}
