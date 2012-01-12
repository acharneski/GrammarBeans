package org.simiacryptus.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class SequenceGrammar<T> extends Grammar<T>
{

  private final class TailMatchIterator implements Iterator<List<MatchResult<?>>>
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
      this.child = children.get(childIndex);
      this.matchIterator = child.matchFromStart(subSequence).iterator();
      this.nextChildIndex = childIndex + 1;
      this.isTerminal = nextChildIndex >= children.size();
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
        updateNextIterator();
        ArrayList<MatchResult<?>> list = new ArrayList<MatchResult<?>>();
        list.add(result);
        list.addAll(nextIterator.next());
        //Log.debug("New match for sequence: %s", result);
        return list;
      }
    }

    private void updateNextIterator()
    {
      if(!isTerminal)
      {
        while(null == nextIterator || !nextIterator.hasNext())
        {
          if(matchIterator.hasNext())
          {
            result = (MatchResult<?>) matchIterator.next();
            nextIterator = new TailMatchIterator(nextChildIndex, subSequence.subSequence(result.end, subSequence.length()));
            //Log.debug("Next NonTerminal match of %s: %s", child, result);
          }
          else
          {
            //Log.debug("NonTerminal %s is out of matches", child);
            result = null;
            nextIterator = null;
            break;
          }
        }
      }
    }

    public boolean hasNext()
    {
      boolean hasNext;
      if(isTerminal)
      {
        hasNext = matchIterator.hasNext();
      }
      else
      {
        updateNextIterator();
        hasNext = null != nextIterator && nextIterator.hasNext();
      }
      //Log.debug("hasNext: %s (%s)", hasNext,this);
      return hasNext;
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

  protected final List<Grammar<?>> children;
  
  public SequenceGrammar(Grammar<?>... subgrammars)
  {
    ArrayList<Grammar<?>> list = new ArrayList<Grammar<?>>(subgrammars.length);
    for(Grammar<?> c : subgrammars)
    {
      list.add(c);
    }
    this.children = Collections.unmodifiableList(list);
  }


  public Grammar<?> get(int i)
  {
    return this.children.get(i);
  }

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
          
          
          public boolean hasNext()
          {
            return tailSequence.hasNext();
          }

          public MatchResult<T> next()
          {
            return getResult(string, tailSequence.next());
          }

          public void remove()
          {
            throw new RuntimeException("Operation not supported");
          }
        };
      }
    };
  }

  private MatchResult<T> getResult(final CharSequence string, List<MatchResult<?>> next)
  {
    T result = getResult(next);
    int startIndex = 0;
    int endIndex = 0;
    for(MatchResult<?> r : next)
    {
      assert(0 == r.start);
      endIndex += r.end;
    }
    MatchResult<T> matchResult = new MatchResult<T>(SequenceGrammar.this, string, startIndex, endIndex, result);
    return matchResult;
  }

  protected abstract T getResult(List<MatchResult<?>> results);

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
