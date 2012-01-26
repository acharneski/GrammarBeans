package org.simiacryptus.grammar;

import java.util.ArrayList;
import java.util.List;

public abstract class SequenceGrammar<T> extends ChainGrammar<T>
{
  protected final List<Grammar<?>> children = new ArrayList<Grammar<?>>();
  
  public SequenceGrammar(Class<? super T> resultType)
  {
    super(resultType);
  }

  public SequenceGrammar(Class<? super T> resultType, Grammar<?>... children)
  {
    this(resultType);
    for(Grammar<?> c : children){
      add(c);
    }
  }
  
  @Override
  public Grammar<?> get(int i)
  {
    return this.children.get(i);
  }

  protected int getMin()
  {
    return children.size();
  }

  protected int getMax()
  {
    return children.size();
  }

  protected void add(Grammar<?> regexGrammar)
  {
    if(null == regexGrammar) throw new NullPointerException();
    children.add(regexGrammar);
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("SequenceGrammar [");
    boolean first = true;
    for(Grammar<?> child : children)
    {
      if(first)
      {
        first = false;
      }
      else
      {
        builder.append(", ");
      } 
      builder.append(child);
    }
    builder.append("]");
    return builder.toString();
  }

}
