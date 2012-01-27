package org.simiacryptus.grammar.bean.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.JavaValue;
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
  public JavaValue getCode(JavaValue parent)
  {
    JavaValue file = new JavaValue(parent, getTypeString());
    StringBuffer sb = new StringBuffer();
    sb.append("new MapEntriesAdapter(");
    sb.append(file.newVar(inner));
    sb.append(")");
    file.setValueString(sb.toString());
    return file;
  }
  
}