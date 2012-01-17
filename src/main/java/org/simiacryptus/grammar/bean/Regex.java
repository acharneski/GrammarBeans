package org.simiacryptus.grammar.bean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention (RetentionPolicy.RUNTIME)
public @interface Regex
{
  String value();
  boolean capture() default false;
}
