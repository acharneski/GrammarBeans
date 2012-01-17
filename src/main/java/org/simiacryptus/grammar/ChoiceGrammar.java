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
  
  @SuppressWarnings("unchecked")
  public ChoiceGrammar(@SuppressWarnings("rawtypes") Grammar... grammars)
  {
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

}
