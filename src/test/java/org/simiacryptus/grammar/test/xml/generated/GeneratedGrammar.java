package org.simiacryptus.grammar.test.xml.generated;

import java.util.*;
import org.simiacryptus.grammar.*;
import org.simiacryptus.grammar.bean.impl.*;

public class GeneratedGrammar
{
  public static SequenceGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlTree> get()
  {
    final RecursionGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlTree> v1;
    {
      v1 = new RecursionGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlTree>(org.simiacryptus.grammar.test.xml.XmlContent.XmlTree.class);
    }
    ;
    final RegexCaptureAdapter v3;
    {
      final RegexCaptureGrammar v2;
      {
        v2 = new RegexCaptureGrammar("<(\\w+)");
      }
      ;
      v3 = new RegexCaptureAdapter(v2);
    }
    ;
    final MapEntriesAdapter v5;
    {
      final RegexCaptureGrammar v4;
      {
        v4 = new RegexCaptureGrammar("\\s+(\\w+)=\"(.*?)\"");
      }
      ;
      v5 = new MapEntriesAdapter(v4);
    }
    ;
    final RegexGrammar v6;
    {
      v6 = new RegexGrammar(">");
    }
    ;
    final ListRepeatGrammar<java.util.List> v11;
    {
      final ChoiceGrammar<org.simiacryptus.grammar.test.xml.XmlContent> v10;
      {
        final SequenceGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText> v9;
        {
          final RecursionGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText> v7;
          {
            v7 = new RecursionGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText>(org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText.class);
          }
          ;
          final RegexGrammar v8;
          {
            v8 = new RegexGrammar("[^<]+");
          }
          ;
          v7.setInner(new SequenceGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText>(org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText.class, v8)
          {
            @Override
            protected org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText getResult(List<MatchResult<?>> results)
            {
              org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText obj = new org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText();
              obj.string = (java.lang.CharSequence) results.get(0).result;
              return obj;
            }
          });
          v9 = new SequenceGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText>(org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText.class, v8)
          {
            @Override
            protected org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText getResult(List<MatchResult<?>> results)
            {
              org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText obj = new org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText();
              obj.string = (java.lang.CharSequence) results.get(0).result;
              return obj;
            }
          };
        }
        ;
        v10 = new ChoiceGrammar<org.simiacryptus.grammar.test.xml.XmlContent>(v1, v9);
      }
      ;
      v11 = new ListRepeatGrammar<java.util.List>(v10, 0, -1);
    }
    ;
    final RegexCaptureAdapter v13;
    {
      final RegexCaptureGrammar v12;
      {
        v12 = new RegexCaptureGrammar("</(\\w+)>");
      }
      ;
      v13 = new RegexCaptureAdapter(v12);
    }
    ;
    v1.setInner(new SequenceGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlTree>(org.simiacryptus.grammar.test.xml.XmlContent.XmlTree.class, v3, v5, v6, v11, v13)
    {
      @Override
      protected org.simiacryptus.grammar.test.xml.XmlContent.XmlTree getResult(List<MatchResult<?>> results)
      {
        org.simiacryptus.grammar.test.xml.XmlContent.XmlTree obj = new org.simiacryptus.grammar.test.xml.XmlContent.XmlTree();
        obj.nodeName = (java.lang.CharSequence) results.get(0).result;
        obj.attributes = (java.util.Map) results.get(1).result;
        obj.terminateStartNode = (java.lang.CharSequence) results.get(2).result;
        obj.content = (java.util.List) results.get(3).result;
        obj.endNode = (java.lang.CharSequence) results.get(4).result;
        return obj;
      }
    });

    return new SequenceGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlTree>(org.simiacryptus.grammar.test.xml.XmlContent.XmlTree.class, v3, v5, v6, v11, v13)
    {
      @Override
      protected org.simiacryptus.grammar.test.xml.XmlContent.XmlTree getResult(List<MatchResult<?>> results)
      {
        org.simiacryptus.grammar.test.xml.XmlContent.XmlTree obj = new org.simiacryptus.grammar.test.xml.XmlContent.XmlTree();
        obj.nodeName = (java.lang.CharSequence) results.get(0).result;
        obj.attributes = (java.util.Map) results.get(1).result;
        obj.terminateStartNode = (java.lang.CharSequence) results.get(2).result;
        obj.content = (java.util.List) results.get(3).result;
        obj.endNode = (java.lang.CharSequence) results.get(4).result;
        return obj;
      }
    };
  }
}
