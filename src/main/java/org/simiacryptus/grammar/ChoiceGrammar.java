package org.simiacryptus.grammar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChoiceGrammar<T> extends Grammar<T>
{

  private class ChoiceIterator implements Iterator<MatchResult<T>>
  {
    private final CharSequence charSequence;
    private Iterator<Grammar<T>> choiceIterator = list.iterator();
    private Iterator<MatchResult<T>> innerIterator;

    public ChoiceIterator(CharSequence charSequence)
    {
      this.charSequence = charSequence;
    }

    @Override
    public boolean hasNext()
    {
      updateInner();
      return null != innerIterator && innerIterator.hasNext();
    }

    @Override
    public MatchResult<T> next()
    {
      updateInner();
      return innerIterator.next();
    }

    private void updateInner()
    {
      while (choiceIterator.hasNext() && (null == innerIterator || !innerIterator.hasNext()))
      {
        innerIterator = choiceIterator.next().matchFromStart(charSequence).iterator();
      }
    }

    @Override
    public void remove()
    {
      throw new RuntimeException("Unsupported Operation");
    }
  }

  private final List<Grammar<T>> list = new ArrayList<Grammar<T>>();
  
  public ChoiceGrammar(Class<? super T> resultType)
  {
    super(resultType);
  }
  
  @SuppressWarnings("unchecked")
  public ChoiceGrammar(Grammar<? extends T>... grammars)
  {
    this((Class<? super T>) grammars[0].resultType);
    for(Grammar<?> g : grammars)
    {
      add((Grammar<T>) g);
    }
  }

  public void add(Grammar<T> grammar)
  {
    list.add(grammar);
  }

  @Override
  public Iterable<MatchResult<T>> matchFromStart(final CharSequence charSequence)
  {
    return new Iterable<MatchResult<T>>()
    {
      @Override
      public Iterator<MatchResult<T>> iterator()
      {
        return new ChoiceIterator(charSequence);
      }
    };
  }

  @Override
  public String write(JavaFile file)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("new ");
    sb.append(getType());
    sb.append("(");
    if(0 == list.size())
    {
      sb.append(resultType.getSimpleName());
      sb.append(".class");
    }
    else
    {
      boolean isFirst = true;
      for(Grammar<?> c : list)
      {
        if(isFirst)
        {
          isFirst = false;
        }
        else
        {
          sb.append(",");
        }
        String var = file.newVar(c);
        sb.append(var);
      }
    }
    sb.append(")");
    return sb.toString();
  }

}
