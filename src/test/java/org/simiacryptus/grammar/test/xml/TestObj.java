package org.simiacryptus.grammar.test.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class TestObj
{
  @Override
  public int hashCode()
  {
    try
    {
      final int prime = 31;
      int result = 1;
      for (Field f : this.getClass().getDeclaredFields())
      {
        Object object = f.get(this);
        result = prime * result + ((object == null) ? 0 : object.hashCode());
      }
      return result;
    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    try
    {
      for (Field f : this.getClass().getDeclaredFields())
      {
        if(0 != (f.getModifiers() & Modifier.TRANSIENT)) continue;
        Object a = f.get(this);
        Object b = f.get(obj);
        final boolean equal;
        if (a == b)
        {
          equal = true;
        }
        else if (null == a && null == b)
        {
          equal = true;
        }
        else if (null == a || null == b)
        {
          equal = false;
        }
        else
        {
          equal = a.equals(b);
        }
        if (!equal)
        {
          return false;
        }
      }
      return true;
    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
}
