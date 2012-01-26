package org.simiacryptus.grammar.bean.impl;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.simiacryptus.grammar.Grammar;
import org.simiacryptus.grammar.JavaFile;
import org.simiacryptus.grammar.MatchResult;
import org.simiacryptus.grammar.RecursionGrammar;
import org.simiacryptus.grammar.SequenceGrammar;

public class FieldSequenceGrammar<T> extends SequenceGrammar<T>
{
  private final Constructor<T> constructor;
  private final List<FieldGrammar<?>> fields = new ArrayList<FieldGrammar<?>>();

  public FieldSequenceGrammar(Class<T> class1) throws SecurityException, NoSuchMethodException
  {
    super(class1);
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
  
  @Override
  public String write(JavaFile file)
  {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    String recursionVar = file.newVar(new RecursionGrammar(resultType));
    file.cache.put(this, recursionVar);

    StringBuffer sb = new StringBuffer();
    sb.append("new ");
    sb.append("SequenceGrammar<");
    sb.append(resultType.getCanonicalName());
    sb.append(">(");
    sb.append(resultType.getCanonicalName());
    sb.append(".class");
    for(Grammar<?> c : children)
    {
      sb.append(",");
      String var = file.newVar(c);
      sb.append(var);
    }
    sb.append(")");
    sb.append("{");
    sb.append("\n");
    sb.append("  @Override\n");
    sb.append("  protected ");
    sb.append(resultType.getCanonicalName());
    sb.append(" getResult(List<MatchResult<?>> results)\n");
    sb.append("  {\n");
    sb.append("    " + resultType.getCanonicalName() + " obj = new " + resultType.getCanonicalName() + "();\n");
    int i = 0;
    for(FieldGrammar<?> field : fields)
    {
      sb.append("    obj." + field.field.getName() + " = (" + field.field.getType().getCanonicalName() + ") results.get(" + i++ + ").result;\n");
    }
    sb.append("    return obj;\n");
    sb.append("  }\n");
    sb.append("}");
    file.add(String.format("%s.setInner(%s);", recursionVar, sb.toString()));
    return sb.toString();
  }

  @Override
  public String getType()
  {
    return "SequenceGrammar<" + resultType.getCanonicalName() + ">";
  }

  
  
}
