import java.util.*;
import org.simiacryptus.grammar.*;
import org.simiacryptus.grammar.bean.impl.*;

public class Test {
  public void run(){
RecursionGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlTree> v1 = new RecursionGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlTree>(org.simiacryptus.grammar.test.xml.XmlContent.XmlTree.class);
RegexCaptureGrammar v2 = new RegexCaptureGrammar("<(\\w+)");
RegexCaptureAdapter v3 = new RegexCaptureAdapter(v2);
RegexCaptureGrammar v4 = new RegexCaptureGrammar("\\s+(\\w+)=\"(.*?)\"");
MapEntriesAdapter v5 = new MapEntriesAdapter(v4);
RegexGrammar v6 = new RegexGrammar(">");
RecursionGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText> v7 = new RecursionGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText>(org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText.class);
RegexGrammar v8 = new RegexGrammar("[^<]+");
v7.setInner(new SequenceGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText>(org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText.class,v8){
  @Override
  protected org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText getResult(List<MatchResult<?>> results)
  {
    org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText obj = new org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText();
    obj.string = (java.lang.CharSequence) results.get(0).result;
    return obj;
  }
});;
SequenceGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText> v9 = new SequenceGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText>(org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText.class,v8){
  @Override
  protected org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText getResult(List<MatchResult<?>> results)
  {
    org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText obj = new org.simiacryptus.grammar.test.xml.XmlContent.XmlContentText();
    obj.string = (java.lang.CharSequence) results.get(0).result;
    return obj;
  }
};
ChoiceGrammar<org.simiacryptus.grammar.test.xml.XmlContent> v10 = new ChoiceGrammar<org.simiacryptus.grammar.test.xml.XmlContent>(v1,v9);
ListRepeatGrammar<java.util.List> v11 = new ListRepeatGrammar<java.util.List>(v10,0,-1);
RegexCaptureGrammar v12 = new RegexCaptureGrammar("</(\\w+)>");
RegexCaptureAdapter v13 = new RegexCaptureAdapter(v12);
v1.setInner(new SequenceGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlTree>(org.simiacryptus.grammar.test.xml.XmlContent.XmlTree.class,v3,v5,v6,v11,v13){
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
});;
new SequenceGrammar<org.simiacryptus.grammar.test.xml.XmlContent.XmlTree>(org.simiacryptus.grammar.test.xml.XmlContent.XmlTree.class,v3,v5,v6,v11,v13){
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
