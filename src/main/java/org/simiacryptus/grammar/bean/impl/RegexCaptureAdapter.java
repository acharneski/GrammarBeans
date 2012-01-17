package org.simiacryptus.grammar.bean.impl;

import java.util.Iterator;
import java.util.List;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.RegexCaptureGrammar;

public class RegexCaptureAdapter extends Grammar<CharSequence>
{
  private final RegexCaptureGrammar innerGrammar;

  public RegexCaptureAdapter(RegexCaptureGrammar innerGrammar)
  {
    this.innerGrammar = innerGrammar;
  }

  @Override
  public Iterable<MatchResult<CharSequence>> matchFromStart(final CharSequence charSequence)
  {
    return new Iterable<MatchResult<CharSequence>>()
    {
      @Override
      public Iterator<MatchResult<CharSequence>> iterator()
      {
        return new Iterator<MatchResult<CharSequence>>()
        {
          Iterator<MatchResult<List<CharSequence>>> iterator = innerGrammar.matchFromStart(charSequence).iterator();

          @Override
          public void remove()
          {
            iterator.remove();
          }
          
          @Override
          public MatchResult<CharSequence> next()
          {
            MatchResult<List<CharSequence>> next = iterator.next();
            CharSequence value = next.result.get(0);
            return new MatchResult<CharSequence>(RegexCaptureAdapter.this, charSequence, next.start, next.end, value);
          }
          
          @Override
          public boolean hasNext()
          {
            return iterator.hasNext();
          }
        };
      }
    };
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("RegexCaptureAdapter [");
    builder.append(innerGrammar);
    builder.append("]");
    return builder.toString();
  }
  
  
}