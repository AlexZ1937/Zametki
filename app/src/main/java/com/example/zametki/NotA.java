package com.example.zametki;

import java.util.Date;

public class NotA
{
    int ID_KEY;
    String tittle;
    String date;
    String nodetext;
    int priority;

    public NotA()
    {
        ID_KEY=0;
        tittle="Tittle";
        date="1970.1.1";
        nodetext="";
        priority=1;
    }

    public NotA(int ID,String tittle, String date, String nodetext,int priority)
    {
        this.ID_KEY=ID;
        this.tittle=tittle;
        this.date=date;
        this.nodetext=nodetext;
        this.priority=priority;
    }

    @Override
    public String toString() {
        return date+" "+tittle;
    }

    public int getID_KEY() {
        return ID_KEY;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public String getNodetext() {
        return nodetext;
    }

    public String getTittle() {
        return tittle;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNodetext(String nodetext) {
        this.nodetext = nodetext;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
}
