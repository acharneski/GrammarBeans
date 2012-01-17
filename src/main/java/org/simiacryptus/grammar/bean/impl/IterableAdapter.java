package org.simiacryptus.grammar.bean.impl;

import java.util.Iterator;

public abstract class IterableAdapter<F,T> implements Iterable<T>
{
  private final Iterable<F> iterable;

  IterableAdapter(Iterable<F> iterable)
  {
    this.iterable = iterable;
  }

  @Override
  public Iterator<T> iterator()
  {
    return new Iterator<T>()
    {
      Iterator<F> iterator = iterable.iterator();
      
      @Override
      public void remove()
      {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public T next()
      {
        return convert(iterator.next());
      }
      
      @Override
      public boolean hasNext()
      {
        // TODO Auto-generated method stub
        return false;
      }
    };
  }

  protected abstract T convert(F next);
}