package com.example.zametki;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.LinkedList;

public class NoteXmlParser
{
    LinkedList<NotA> notes;
    public  NoteXmlParser()
    {
        notes=new LinkedList<NotA>();
    }

    public boolean FromXmlParse(XmlPullParser pullparser) {
        boolean isparsed=true;
        boolean inEntity = false;
        String textValue = "";
        NotA currnote = null;

        try {

            int iterator=pullparser.getEventType();
            while(iterator!=XmlPullParser.END_DOCUMENT)
            {
                String tagName= pullparser.getName();
                switch (iterator)
                {
                    case XmlPullParser.START_TAG:
                    {
                        if("user".equalsIgnoreCase(tagName)){
                            inEntity = true;
                            currnote = new NotA();
                        }

                    }
                        break;
                    case XmlPullParser.TEXT:
                    {
                        textValue = pullparser.getText();
                    }
                    break;
                    case XmlPullParser.END_TAG:
                    {
                        if(inEntity) {
                            if ("user".equalsIgnoreCase(tagName)) {
                                inEntity = false;
                                notes.add(currnote);
                            } else if ("tittle".equalsIgnoreCase(tagName)) {
                                currnote.setTittle(textValue);
                            } else if ("date".equalsIgnoreCase(tagName)) {
                                currnote.setDate(textValue);
                            } else if ("notetext".equalsIgnoreCase(tagName)) {
                                currnote.setNodetext(textValue);
                            }
                        }
                    }
                    break;
                }
                iterator=pullparser.next();
            }


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return  isparsed;

    }
}
