package org.simiacryptus.grammar.bean.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.JavaFile;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.RegexCaptureGrammar;
import org.simiacryptus.grammar.RepeatGrammar;

public final class MapEntriesAdapter extends RepeatGrammar<Map<CharSequence, CharSequence>>
{
  public MapEntriesAdapter(String pattern)
  {
    this(new RegexCaptureGrammar(pattern));
  }

  public MapEntriesAdapter(Grammar<List<CharSequence>> inner)
  {
    super(Map.class, inner, 0, -1);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Map<CharSequence,CharSequence> getResult(List<MatchResult<?>> results)
  {
    HashMap<CharSequence, CharSequence> map = new HashMap<CharSequence,CharSequence>();
    for(MatchResult<?> r : results)
    {
      List<CharSequence> entry = (List<CharSequence>) r.result;
      map.put(entry.get(0), entry.get(1));
    }
    return map;
  }

  @Override
  protected Map<CharSequence, CharSequence> getNullResult()
  {
    return new HashMap<CharSequence, CharSequence>();
  }

  @Override
  public String write(JavaFile file)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("new MapEntriesAdapter(");
    sb.append(file.newVar(inner));
    sb.append(")");
    return sb.toString();
  }
  
}