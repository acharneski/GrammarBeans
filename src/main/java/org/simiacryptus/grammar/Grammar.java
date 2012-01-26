package org.simiacryptus.grammar;

public abstract class Grammar<T> implements JavaGenerator
{

  public final Class<? super T> resultType;
  
  public Grammar(Class<? super T> resultType)
  {
    super();
    this.resultType = resultType;
  }

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
  
  @Override
  public String write(JavaFile file)
  {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public String getType()
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
