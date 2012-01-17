package org.simiacryptus.grammar.bean.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.RegexCaptureGrammar;
import org.simiacryptus.grammar.RegexGrammar;
import org.simiacryptus.grammar.bean.Entries;
import org.simiacryptus.grammar.bean.GrammarBean;
import org.simiacryptus.grammar.bean.Regex;

public class FieldGrammar<T>
{
  public final Field field;
  public final Grammar<T> grammar;
  
  private FieldGrammar(Field field, Grammar<T> grammar)
  {
    super();
    this.field = field;
    this.field.setAccessible(true);
    this.grammar = grammar;
  }
  
  public void populate(Object object, MatchResult<?> result) throws IllegalArgumentException, IllegalAccessException
  {
    field.set(object, grammar.cast(result).result);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static FieldGrammar<?> get(Field field) throws SecurityException, NoSuchMethodException
  {
    if(CharSequence.class.isAssignableFrom(field.getType()))
    {
      Regex annotation_regex = field.getAnnotation(Regex.class);
      if(null != annotation_regex)
      {
        if(annotation_regex.capture())
        {
          final RegexCaptureGrammar innerGrammar = new RegexCaptureGrammar(annotation_regex.value());
          return new FieldGrammar<CharSequence>(field, new RegexCaptureAdapter(innerGrammar));
        }
        else
        {
          final RegexGrammar innerGrammar = new RegexGrammar(annotation_regex.value());
          return new FieldGrammar<CharSequence>(field, innerGrammar);
        }
      }
      else
      {
        throw new RuntimeException("Cannot form a grammar from " + field);
      }
    }
    else if(Map.class.isAssignableFrom(field.getType()))
    {
      Entries annotation_entries = field.getAnnotation(Entries.class);
      if(null != annotation_entries)
      {
        return new FieldGrammar<Map<CharSequence,CharSequence>>(field, new MapEntriesAdapter(annotation_entries.value()));
      }
      else
      {
        throw new RuntimeException("Cannot form a grammar from " + field);
      }
    }
    else if(List.class.isAssignableFrom(field.getType()))
    {
      ParameterizedType genericType = (ParameterizedType) field.getGenericType();
      Class type = (Class) genericType.getActualTypeArguments()[0];
      return new FieldGrammar(field, new ListRepeatGrammar(GrammarBean.get(type), 0, -1));
    }
    else
    {
      throw new RuntimeException("Cannot form a grammar from " + field);
    }
  }

}
