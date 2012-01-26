package org.simiacryptus.grammar.bean.impl;

import java.util.ArrayList;
import java.util.List;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.JavaFile;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.RepeatGrammar;

public class ListRepeatGrammar<T> extends RepeatGrammar<List<T>>
{

  public ListRepeatGrammar(Grammar<?> inner, int min, int max)
  {
    super(List.class, inner, min, max);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected List<T> getResult(List<MatchResult<?>> results)
  {
    ArrayList<T> list = new ArrayList<T>();
    for(MatchResult<?> r : results)
    {
      list.add((T) r.result);
    }
    return list;
  }

  @Override
  protected List<T> getNullResult()
  {
    return new ArrayList<T>();
  }

  @Override
  public String write(JavaFile file)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("new ");
    sb.append(getType());
    sb.append("(");
    sb.append(file.newVar(inner));
    sb.append(",");
    sb.append(min);
    sb.append(",");
    sb.append(max);
    sb.append(")");
    return sb.toString();
  }
  
}
