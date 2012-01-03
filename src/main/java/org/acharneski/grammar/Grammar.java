package org.acharneski.grammar;

public abstract class Grammar<T>
{

  public abstract MatchResult<T> matchFromStart(CharSequence charSequence);

}
