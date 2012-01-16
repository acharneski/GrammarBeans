package org.simiacryptus.grammar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ChainGrammar<T> extends Grammar<T>
{
  protected final class TailMatchIterator implements Iterator<List<MatchResult<?>>>
  {
    private final CharSequence subSequence;
    private final Iterator<?> matchIterator;
    private TailMatchIterator nextIterator = null;
    private final Grammar<?> child;
    private MatchResult<?> result;
    private int nextChildIndex;
    private boolean isTerminal;
    
    public TailMatchIterator(int childIndex, CharSequence subSequence)
    {
      super();
      this.subSequence = subSequence;
      this.child = get(childIndex);
      this.matchIterator = child.matchFromStart(subSequence).iterator();
      this.nextChildIndex = childIndex + 1;
      this.isTerminal = getMax() > 0 && nextChildIndex >= getMax();
    }

    public void remove()
    {
      throw new RuntimeException("Operation not supported");
    }

    public List<MatchResult<?>> next()
    {
      if(isTerminal)
      {
        result = (MatchResult<?>) matchIterator.next();
        //Log.debug("Terminal item: %s", this);
        ArrayList<MatchResult<?>> list = new ArrayList<MatchResult<?>>();
        list.add(result);
        return list;
      }
      else
      {
        if(null == nextIterator || !nextIterator.hasNext())
        {
          if(matchIterator.hasNext())
          {
            result = (MatchResult<?>) matchIterator.next();
            nextIterator = new TailMatchIterator(nextChildIndex, subSequence.subSequence(result.end, subSequence.length()));
            //Log.debug("Next NonTerminal match of %s: %s", child, result);
          }
          else
          {
            Log.debug("NonTerminal %s is out of matches", child);
            result = null;
            nextIterator = null;
          }
        }
        ArrayList<MatchResult<?>> list = new ArrayList<MatchResult<?>>();
        list.add(result);
        if(null != nextIterator && nextIterator.hasNext())
        {
          list.addAll(nextIterator.next());
        }
        //Log.debug("New match for sequence: %s", result);
        return list;
      }
    }

    public boolean hasNext()
    {
      if(matchIterator.hasNext()) return true;
      if(null != nextIterator && nextIterator.hasNext()) return true;
      return false;
    }

    @Override
    public String toString()
    {
      StringBuilder builder = new StringBuilder();
      builder.append("TailMatchIterator [subSequence=");
      builder.append(subSequence);
      builder.append(", child=");
      builder.append(child);
      builder.append(", isTerminal=");
      builder.append(isTerminal);
      builder.append("]");
      return builder.toString();
    }
    
    
  }

  protected ChainGrammar()
  {
    super();
  }

  protected abstract T getResult(List<MatchResult<?>> results);

  public abstract Grammar<?> get(int i);

  @Override
  public Iterable<MatchResult<T>> matchFromStart(final CharSequence string)
  {
    return new Iterable<MatchResult<T>>()
    {
      public Iterator<MatchResult<T>> iterator()
      {
        return new Iterator<MatchResult<T>>()
        {
          TailMatchIterator tailSequence = new TailMatchIterator(0, string);
          List<MatchResult<?>> next = null;
          boolean returnNull = getMin() == 0;
          
          public boolean hasNext()
          {
            if(returnNull) return true;
            updateNext();
            return null != next;
          }
  
          public MatchResult<T> next()
          {
            if(returnNull)
            {
              returnNull = false;
              return new MatchResult<T>(ChainGrammar.this, string, 0, 0, null);
            }
            updateNext();
            T result = getResult(next);
            int startIndex = 0;
            int endIndex = 0;
            for(MatchResult<?> r : next)
            {
              assert(0 == r.start);
              endIndex += r.end;
            }
            MatchResult<T> matchResult = new MatchResult<T>(ChainGrammar.this, string, startIndex, endIndex, result);
            next = null;
            return matchResult;
          }

          private void updateNext()
          {
            while (null == next || next.size() < getMin())
            {
              if(!tailSequence.hasNext())
              {
                next = null;
                break;
              }
              next = tailSequence.next();
            }
          }
  
          public void remove()
          {
            throw new RuntimeException("Operation not supported");
          }
        };
      }
    };
  }

  protected abstract int getMin();

  protected abstract int getMax();

}