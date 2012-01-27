package org.simiacryptus.grammar.bean.impl;

import java.util.ArrayList;
import java.util.List;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.JavaValue;
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
  public JavaValue getCode(JavaValue parent)
  {
    JavaValue file = new JavaValue(parent, getTypeString());
    StringBuffer sb = new StringBuffer();
    sb.append("new ");
    sb.append(getTypeString());
    sb.append("(");
    sb.append(file.newVar(inner));
    sb.append(",");
    sb.append(min);
    sb.append(",");
    sb.append(max);
    sb.append(")");
    file.setValueString(sb.toString());
    return file;
  }
  
}
