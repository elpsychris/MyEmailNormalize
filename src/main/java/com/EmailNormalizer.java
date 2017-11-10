package com;

import model.EmailObject;
import utils.Logger;
import utils.XLSExporter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EmailNormalizer {

    private final static int EMAIL_COMPONENT_EXIST = -1;
    private final static int OK = 0;


    private static List<String> readFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        File file = new File(filePath);
        Files.lines(file.toPath(), StandardCharsets.ISO_8859_1).forEachOrdered(lines::add);
        return lines;
    }

    private static int text2EmailObj(EmailObject curEmail, String line) {
        if (line.contains("From:")) {

            if (curEmail.getSender() != null) {
                return EMAIL_COMPONENT_EXIST;
            }
            String dataString = line.substring(line.indexOf("From:") + "From:".length());
            curEmail.setSender(dataString.trim());
            Logger.print("||    Found a Sender: " + dataString, "EmailNormalizer");
            return OK;
        }
        if (line.contains("To:")) {

            if (curEmail.getReceiver() != null) {
                return EMAIL_COMPONENT_EXIST;
            }

            String dataString = line.substring(line.indexOf("To:") + "To:".length());
            curEmail.setReceiver(dataString.trim());
            Logger.print("||    Found a Receiver: " + dataString, "EmailNormalizer");
            return OK;
        }

        if (line.contains("Subject:")) {

            if (curEmail.getSubject() != null) {
                return EMAIL_COMPONENT_EXIST;
            }

            String dataString = line.substring(line.indexOf("Subject:") + "Subject:".length());
            curEmail.setSubject(dataString.trim());
            Logger.print("||    Found a Subject: " + dataString, "EmailNormalizer");
            return OK;
        }

        if (line.contains("Sent:")) {

            if (curEmail.getDate() != null) {
                return EMAIL_COMPONENT_EXIST;
            }

            String dataString = line.substring(line.indexOf("Sent:") + "Sent:".length()).trim();
            if (dataString.contains("(")) {
                dataString = dataString.substring(0, dataString.indexOf("(") - 1);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMMMM d, yyyy h:mm a");

            try {
                curEmail.setDate(parseDate(dataString, formatter));
            } catch (ParseException e) {
                formatter = new SimpleDateFormat("EEE, MMMMM d, yyyy h:mm:ss a");
                try {
                    curEmail.setDate(parseDate(dataString, formatter));
                } catch (ParseException e1) {
                    Logger.print("Parse Exception: " + e.getMessage(), "EmailNormalizer");
                }
            }

            return OK;
        }

        String curContent = curEmail.getContent();
        if (!line.isEmpty()){
            curContent = (curContent == null) ? line : curContent.concat("\n" + line);
            Logger.print("||    Found a Content: " + line, "EmailNormalizer");
            curEmail.setContent(curContent);
        }

        return OK;
    }

    private static Date parseDate(String dateString, SimpleDateFormat formatter) throws ParseException {
        Date date = formatter.parse(dateString);
        Logger.print("||    Found a Date: " + formatter.format(date), "EmailNormalizer");
        return date;
    }

    private static boolean mailSubjectFilter(EmailObject mailObj) {
        return mailObj.getSubject().toLowerCase().contains("error");
    }

    private static boolean mailContentFilter(EmailObject mailObj) {
        return mailObj.getContent()!=null && mailObj.getContent().toLowerCase().contains("[err");
    }

    private static List<EmailObject> mailFilter(List<EmailObject> before) {
        List<EmailObject> after = new ArrayList<>();
        for (EmailObject email : before) {
            if (mailSubjectFilter(email) && mailContentFilter(email)) {
                after.add(email);
            }
        }
        return after;
    }

    public static void main(String[] args) {
        System.out.println("======== Welcome to TTL Mail Normalizer");
        System.out.print("Input file: ");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.nextLine();

        List<EmailObject> emailList = new ArrayList<>();
        try {
            List<String> lines = readFile(inputPath);
            EmailObject tempMail = null;
            for (String line : lines) {
                if (tempMail == null) {
                    tempMail = new EmailObject();
                }
                int result = text2EmailObj(tempMail, line);
                if (result == EMAIL_COMPONENT_EXIST) {
                    tempMail.setType();
                    emailList.add(tempMail);
                    tempMail = new EmailObject();
                    text2EmailObj(tempMail, line);
                }
            }

        } catch (IOException e) {
            Logger.print("Error " + e.getMessage(), "main");
            return;
        }

        emailList = mailFilter(emailList);

//        for (EmailObject email : emailList) {
//            email.print();
//        }

        XLSExporter exporter = new XLSExporter(emailList);
        try {
            exporter.createWorkSheet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
