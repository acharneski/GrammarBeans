package org.simiacryptus.grammar;

public abstract class Grammar<T> implements CodeGenerator
{

  public final Class<? super T> resultType;
  
  public Grammar(Class<? super T> resultType)
  {
    super();
    this.resultType = resultType;
  }

  /* (non-Javadoc)
   * @see org.simiacryptus.grammar.CodeGenerator#matchFromStart(java.lang.CharSequence)
   */
  public abstract Iterable<MatchResult<T>> matchFromStart(CharSequence charSequence);

  protected void logSyntaxException(String msg, MatchResult<?> matchResult)
  {
    throw new RuntimeException(String.format("Syntax Exception: %s (%s)", msg, matchResult));
  }
  
  @SuppressWarnings("unchecked")
  public MatchResult<T> cast(MatchResult<?> result)
  {
    return (MatchResult<T>) result;
  }
  
  /* (non-Javadoc)
   * @see org.simiacryptus.grammar.CodeGenerator#getCode(org.simiacryptus.grammar.JavaValue)
   */
  @Override
  public JavaValue getCode(JavaValue parent)
  {
    throw new RuntimeException("Not Implemented");
  }

  public String getTypeString()
  {
    if(0 == this.getClass().getTypeParameters().length)
    {
      return this.getClass().getSimpleName();
    }
    else
    {
      return this.getClass().getSimpleName() + "<" + resultType.getCanonicalName() + ">";
    }
  }

}
