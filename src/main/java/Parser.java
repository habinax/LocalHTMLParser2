import org.apache.commons.codec.binary.StringUtils.*;
import org.apache.poi.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;


public class Parser {
    private ArrayList<Text> textList = new ArrayList<Text>();
    private static final String unorderedList = "ul";
    private static final String listItem = "li";
    private static final String fat = "b";
    private static final String underline = "u";
    private static final String orderedList = "ol";
    private static final String paragraph = "p";
    private static final String breakPoint = "br";
    private static final String kursiv = "i";
    private static final ArrayList<String> tagList = new ArrayList<>(Arrays.asList("ul>","li>","b>","u>","ol>","p>","br>","i>"));
    private String str3 = "<p><b>ok <i>mies <u>verschachtelter Satz </u></i></b></p><p>mit allem drum <u><b>und dran, ne<i>?</i></b></u></p><p><u><b><i><br></i></b></u><b><i><u><br></u></i><br></b></p>";
    private String str2 = "einfacher text mit <b><u>fetten </u></b>und <u>unterstrichenem</u><br>";
    private String str = "<ul><li><u>okok</u></li><li><u><br></u></li><li><u>next <b>item</b></u></li></ul><p>normaler text<br><b></b><u><b></b><br></u><br></p>";
    private String str4 = "<u><b>wir chillen mal <i>ok?</i></b></u><br>";
    private String str5 = "kein problem <b> bei sowas </b> und <u>sowas</u>";
    private ArrayList<String> partialStrings = new ArrayList<>();

    Parser(){
        partialStrings=getHTMLParts(str5,null);
        System.out.println(partialStrings);
        processInput(partialStrings);
        System.out.println("printing textList");
        System.out.println(textList.size());
        for(Text t:textList)
            t.print();
    }


    private void processInput(ArrayList<String> partStrings){
        for(String str:partStrings){
            Text text = new Text();
            String substring;
            while(str.contains("<")){
                if(str.indexOf("<")!=0){
                    text.setContent(str.substring(0,str.indexOf("<")));
                    textList.add(new Text(text));
                    System.out.println("Text added: " + text.getContent());
                    str=str.substring(str.indexOf("<"),str.length());
                } else{
                    int bracketCount = (int) str.chars().filter(ch -> ch == '<').count();
                    int startIndex = str.indexOf("<");
                    int nextBracketIndex = str.indexOf(">");

                    substring = str.substring(startIndex+1,nextBracketIndex);
                    if(substring.equals(breakPoint)){
                        text.setContent("\n");
                        textList.add(new Text(text));
                        if(str.length()>4){
                            str=str.substring(4,str.length());
                        } else{
                            str="";
                        }
                        continue;
                    }
                    int endOfBracket = str.indexOf("</".concat(substring).concat(">"));

                    str=str.substring(nextBracketIndex+1,endOfBracket);
                    System.out.println("New String: " + str);
                    System.out.println("-----------------------------------");

                    if(bracketCount>2){
                        if(substring.equals(fat)){
                            text.setFat(true);
                            processNewInputLayer(getHTMLParts(str,null),text);
                            continue;
                        } else if(substring.equals(underline)){
                            text.setUnderlined(true);
                            processNewInputLayer(getHTMLParts(str, null),text);
                            continue;
                        } else if(substring.equals(kursiv)){
                            text.setKursiv(true);
                            processNewInputLayer(getHTMLParts(str,null),text);
                            continue;
                        } else if(substring.equals(paragraph)){
                            text.setContent("\n");
                            textList.add(new Text(text));
                            System.out.println("Paragraph entfernt.");
                            continue;
                        } else if(substring.equals(orderedList)){
                            processOrderedList(str);
                        } else if(substring.equals(unorderedList)){
                            processUnorderedList(str);
                        }
                    } else {
                        if(substring.equals(fat)){
                            text.setFat(true);
                            text.setContent(str);
                        } else if(substring.equals(underline)){
                            text.setUnderlined(true);
                            text.setContent(str);
                        } else if(substring.equals(kursiv)){
                            text.setKursiv(true);
                            text.setContent(str);
                        } else if(substring.equals(paragraph)){
                            text.setContent("\n".concat(str));
                        } else if(substring.equals(orderedList)){
                            processOrderedList(str);
                        } else if(substring.equals(unorderedList)){
                            processUnorderedList(str);
                        }
                    }
                }
            }

            if(str.length()>0){
                text.setContent(str);
                textList.add(new Text(text));
                System.out.println("Text added: ");
                text.print();
            }
        }
    }

    private void processNewInputLayer(ArrayList<String> partStrings, Text text){
        for(String str:partStrings){
            String substring;
            while(str.contains("<")){
                if(str.indexOf("<")!=0){
                    text.setContent(str.substring(0,str.indexOf("<")));
                    textList.add(new Text(text));
                    System.out.println("Text added: " + text.getContent());
                    str=str.substring(str.indexOf("<"),str.length());
                } else{
                    int bracketCount = (int) str.chars().filter(ch -> ch == '<').count();
                    int startIndex = str.indexOf("<");
                    int nextBracketIndex = str.indexOf(">");

                    substring = str.substring(startIndex+1,nextBracketIndex);
                    if(substring.equals(breakPoint)){
                        text.setContent("\n");
                        textList.add(new Text(text));
                        if(str.length()>4){
                            str=str.substring(4,str.length());
                        } else{
                            str="";
                        }
                        continue;
                    }
                    int endOfBracket = str.indexOf("</".concat(substring).concat(">"));

                    str=str.substring(nextBracketIndex+1,endOfBracket);
                    System.out.println("New String: " + str);
                    System.out.println("-----------------------------------");

                    if(bracketCount>2){
                        if(substring.equals(fat)){
                            text.setFat(true);
                            textList.add(new Text(text));
                            processNewInputLayer(getHTMLParts(str,null),text);
                            break;
                        } else if(substring.equals(underline)){
                            text.setUnderlined(true);
                            textList.add(new Text(text));
                            processNewInputLayer(getHTMLParts(str, null),text);
                            break;
                        } else if(substring.equals(kursiv)){
                            text.setKursiv(true);
                            textList.add(new Text(text));
                            processNewInputLayer(getHTMLParts(str,null),text);
                            break;
                        } else if(substring.equals(paragraph)){
                            text.setContent("\n");
                            textList.add(new Text(text));
                            System.out.println("Paragraph entfernt.");
                            break;
                        } else if(substring.equals(orderedList)){
                            processOrderedList(str);
                        } else if(substring.equals(unorderedList)){
                            processUnorderedList(str);
                        }
                    } else {
                        if(substring.equals(fat)){
                            text.setFat(true);
                            text.setContent(str);
                        } else if(substring.equals(underline)){
                            text.setUnderlined(true);
                            text.setContent(str);
                        } else if(substring.equals(kursiv)){
                            text.setKursiv(true);
                            text.setContent(str);
                        } else if(substring.equals(paragraph)){
                            text.setContent("\n".concat(str));
                        } else if(substring.equals(orderedList)){
                            processOrderedList(str);
                        } else if(substring.equals(unorderedList)){
                            processUnorderedList(str);
                        }
                    }
                }
            }
        }
    }

    private void processOrderedList(String str){

    }

    private void processUnorderedList(String str){

    }
    private ArrayList<String> getHTMLParts(String str, ArrayList<String> al){
        ArrayList<String> stringList = new ArrayList<>();
        if(al!=null){
            stringList = al;
        }

        if(str.contains("<")){
            if(str.indexOf("<")!=0){
                stringList.add(str.substring(0,str.indexOf("<")));
                if(str.length()>0){
                    str = str.substring(str.indexOf("<"),str.length());
                    getHTMLParts(str,stringList);
                }
            } else {
                int startIndex = str.indexOf("<");
                int nextBracketIndex = str.indexOf(">");
                String substring = str.substring(startIndex+1,nextBracketIndex);
//                System.out.println(substring);

                if(substring.equals(breakPoint)){
                    stringList.add("\n");
                    if(str.length()>nextBracketIndex+1){
                        str=str.substring(nextBracketIndex+1,str.length());
                        getHTMLParts(str,stringList);
                    }
                } else{
                    int endOfBracket = str.indexOf("</".concat(substring).concat(">"))+substring.length()+3;
//                    System.out.println(endOfBracket);

                    stringList.add(str.substring(0,endOfBracket));
//                    System.out.println("String in list:" + str.substring(0,endOfBracket));
                    str = str.substring(endOfBracket,str.length());

                    if(str.length()>0){
//                        System.out.println("Remaining str: " + str);
                        getHTMLParts(str,stringList);
                    }
                }

            }
        } else {
            stringList.add(str);
        }
        return stringList;
    }
}
