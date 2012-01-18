package org.simiacryptus.grammar.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.simiacryptus.grammar.ChoiceGrammar;
import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.bean.impl.FieldGrammar;
import org.simiacryptus.grammar.bean.impl.FieldSequenceGrammar;


public class GrammarBean
{

  private static final Map<Class<?>, Grammar<?>> grammarMap = new HashMap<Class<?>, Grammar<?>>();
  
  @SuppressWarnings("unchecked")
  public static <T> Grammar<T> get(Class<T> class1)
  {
    if(grammarMap.containsKey(class1))
    {
      return (Grammar<T>) grammarMap.get(class1);
    }
    try
    {
      Subclasses annotation_subclass = class1.getAnnotation(Subclasses.class);
      if(null != annotation_subclass)
      {
        ChoiceGrammar<T> grammar = new ChoiceGrammar<T>();
        grammarMap.put(class1, grammar);
        for(Class<?> g : annotation_subclass.value())
        {
          grammar.add((Grammar<T>) get(g));
        }
        return grammar;
      }
      else
      {
        FieldSequenceGrammar<T> grammar = new FieldSequenceGrammar<T>(class1);
        grammarMap.put(class1, grammar);
        Field[] declaredFields = class1.getDeclaredFields();
        assert(0 < declaredFields.length);
        for(Field field : declaredFields)
        {
          if(0 != (field.getModifiers() & Modifier.STATIC))
          {
            continue;
          }
          grammar.add(FieldGrammar.get(field));
        }
        return grammar;
      }
    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

}
