package org.simiacryptus.grammar.bean.impl;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.SequenceGrammar;

public class FieldSequenceGrammar<T> extends SequenceGrammar<T>
{
  private final Constructor<T> constructor;
  private final List<FieldGrammar<?>> fields = new ArrayList<FieldGrammar<?>>();

  public FieldSequenceGrammar(Class<T> class1) throws SecurityException, NoSuchMethodException
  {
    this.constructor = class1.getDeclaredConstructor();
    this.constructor.setAccessible(true);
  }

  public void add(FieldGrammar<?> fieldGrammar)
  {
    super.add(fieldGrammar.grammar);
    fields.add(fieldGrammar);
  }

  @Override
  protected T getResult(List<MatchResult<?>> results)
  {
    try
    {
      T object = this.constructor.newInstance();
      int index = 0;
      for(FieldGrammar<?> field : fields)
      {
        field.populate(object, results.get(index++));
      }
      return object;
    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
}
