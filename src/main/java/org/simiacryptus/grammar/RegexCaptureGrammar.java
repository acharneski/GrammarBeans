package org.simiacryptus.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCaptureGrammar extends Grammar<List<CharSequence>>
{

  private final Pattern floatingRegex;
  private final Pattern startAnchoredRegex;

  public RegexCaptureGrammar(String pattern)
  {
    super(List.class);
    this.floatingRegex = Pattern.compile(pattern);
    this.startAnchoredRegex = Pattern.compile("^" + pattern);
  }

  @Override
  public Iterable<MatchResult<List<CharSequence>>> matchFromStart(CharSequence string)
  {
    ArrayList<MatchResult<List<CharSequence>>> list = new ArrayList<MatchResult<List<CharSequence>>>();
    Matcher matcher = startAnchoredRegex.matcher(string);
    if(matcher.find())
    {
      List<CharSequence> result = new ArrayList<CharSequence>();
      for(int i=1;i<matcher.groupCount()+1;i++)
      {
        int start = matcher.start(i);
        int end = matcher.end(i);
        result.add(string.subSequence(start, end));
      }
      list.add(new MatchResult<List<CharSequence>>(this, string, matcher.start(), matcher.end(), result));
    }
    return list;
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("RegexCaptureGrammar [");
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
