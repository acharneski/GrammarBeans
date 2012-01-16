package org.simiacryptus.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SequenceGrammar<T> extends ChainGrammar<T>
{
  protected final List<Grammar<?>> children;
  
  public SequenceGrammar(Grammar<?>... subgrammars)
  {
    super(subgrammars.length, subgrammars.length);
    ArrayList<Grammar<?>> list = new ArrayList<Grammar<?>>(subgrammars.length);
    for(Grammar<?> c : subgrammars)
    {
      if(null == c) throw new NullPointerException();
      list.add(c);
    }
    this.children = Collections.unmodifiableList(list);
  }

  @Override
  public Grammar<?> get(int i)
  {
    return this.children.get(i);
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
