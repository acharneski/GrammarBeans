package org.acharneski.grammar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRegexGrammar extends Grammar<CharSequence>
{

  @SuppressWarnings("unused")
  private final Pattern floatingRegex;
  private final Pattern startAnchoredRegex;

  public StringRegexGrammar(String pattern)
  {
    this.floatingRegex = Pattern.compile(pattern);
    this.startAnchoredRegex = Pattern.compile("^" + pattern);
  }

  @Override
  public MatchResult<CharSequence> matchFromStart(CharSequence string)
  {
    Matcher matcher = startAnchoredRegex.matcher(string);
    if(matcher.find())
    {
      int start = 0;
      int end = matcher.group(0).length();
      CharSequence result = string.subSequence(start, end);
      return new MatchResult<CharSequence>(this, string, start, end, result);
    }
    return null;
  }

}
