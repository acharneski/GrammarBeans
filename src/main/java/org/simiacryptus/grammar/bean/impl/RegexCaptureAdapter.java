package org.simiacryptus.grammar.bean.impl;

import java.util.List;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.JavaValue;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.RegexCaptureGrammar;

public class RegexCaptureAdapter extends Grammar<CharSequence>
{
  private final RegexCaptureGrammar innerGrammar;

  public RegexCaptureAdapter(RegexCaptureGrammar innerGrammar)
  {
    super(CharSequence.class);
    this.innerGrammar = innerGrammar;
  }

  @Override
  public Iterable<MatchResult<CharSequence>> matchFromStart(final CharSequence charSequence)
  {
    return new IterableAdapter<MatchResult<List<CharSequence>>, MatchResult<CharSequence>>(innerGrammar.matchFromStart(charSequence))
    {
      @Override
      protected MatchResult<CharSequence> convert(MatchResult<List<CharSequence>> next)
      {
        CharSequence value = next.result.get(0);
        return new MatchResult<CharSequence>(RegexCaptureAdapter.this, charSequence, next.start, next.end, value);
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

  @Override
  public JavaValue getCode(JavaValue parent)
  {
    JavaValue file = new JavaValue(parent, getTypeString());
    StringBuffer sb = new StringBuffer();
    sb.append("new RegexCaptureAdapter(");
    sb.append(file.newVar(innerGrammar));
    sb.append(")");
    file.setValueString(sb.toString());
    return file;
  }

}