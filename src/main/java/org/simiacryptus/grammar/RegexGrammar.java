package org.simiacryptus.grammar;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexGrammar extends Grammar<CharSequence>
{

  private final Pattern floatingRegex;
  private final Pattern startAnchoredRegex;

  public RegexGrammar(String pattern)
  {
    super(CharSequence.class);
    this.floatingRegex = Pattern.compile(pattern);
    this.startAnchoredRegex = Pattern.compile("^" + pattern);
  }

  @Override
  public Iterable<MatchResult<CharSequence>> matchFromStart(CharSequence string)
  {
    ArrayList<MatchResult<CharSequence>> list = new ArrayList<MatchResult<CharSequence>>();
    Matcher matcher = startAnchoredRegex.matcher(string);
    if(matcher.find())
    {
      int start = 0;
      int end = matcher.group(0).length();
      CharSequence result = string.subSequence(start, end);
      list.add(new MatchResult<CharSequence>(this, string, start, end, result));
    }
    return list;
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("RegexGrammar [");
    builder.append(floatingRegex);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public JavaValue getCode(JavaValue parent)
  {
    JavaValue file = new JavaValue(parent, getTypeString());
    StringBuffer sb = new StringBuffer();
    sb.append("new ");
    sb.append(getTypeString());
    sb.append("(\"");
    sb.append(StringUtil.escapeStringLiteral(floatingRegex.toString()));
    sb.append("\")");
    file.setValueString(sb.toString());
    return file;
  }
  
}
