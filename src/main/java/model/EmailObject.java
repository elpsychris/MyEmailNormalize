package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailObject {
    private String sender;
    private String receiver;
    private String subject;
    private String content;
    private String[] group;
    private Date date;
    private String[] type;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
        this.group = subject.split(" ");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
//    public EmailType getType() {
//        return type;
//    }
//
//    public void setType(EmailType type) {
//        this.type = type;
//    }

    public void print() {
        System.out.println("========= EMAIL ===========");
        System.out.println("Subject " + this.subject);
        System.out.println("Sender " + this.sender);
        System.out.println("Receiver " + this.receiver);
        System.out.println("Content: \n " + this.content);
        System.out.println("--------- EO EMAIL -----------");
    }

    public Object[] toArray() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
        System.out.println(type.length);
        Object[] result = {date==null?"???":formatter.format(date),
                sender,
                receiver,
                subject,
                group==null?"":String.join(";",group),
                type.length < 2?"Other":type[1],
                content};
        return result;

    }

    public void setType() {
        System.out.println(content);
        String[] info = content.split("\n");
        if (info.length == 0) {
            return;
        }
        String[] subInfo = info[0].split("]");
        if (subInfo.length < 2) {
            type = subInfo;
            return;
        }
        type = subInfo[1].split(":");
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setDate(String date) {

    }

}
