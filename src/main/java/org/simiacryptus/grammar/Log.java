package org.simiacryptus.grammar;

public class Log
{

  public static void debug(String format, Object... args)
  {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    StackTraceElement frame = stackTrace[2];
    String msg = String.format(format, args);
    msg = String.format("(%s:%s) - %s", frame.getFileName(), frame.getLineNumber(), msg);
    System.out.println(msg);
  }

}
