package com;

import model.EmailObject;
import utils.DateConverter;
import utils.Logger;

import java.util.*;

public class EmailConverter {
    public static final String EMAIL_SENDER = "S";
    public static final String EMAIL_RECEIVER = "R";
    public static final String EMAIL_SUBJECT = "SUB";
    public static final String EMAIL_DATE = "D";
    public static final String EMAIL_CONTENT = "C";

    private List<String> rawData = null;
    private List<EmailObject> objectData = null;

    public EmailConverter(List<String> rawData) {
        setRawData(rawData);
        convert();
    }

    public void convert() {
        if (this.getRawData() == null) {
            Logger.print("Nothing to convert","EmailConverter");
        }

        boolean foundNewMail = false;
        Map<String,String> singleEmailData = null;
        for (String line : rawData) {
            //Skip if line contains only newline
            if (line.equals("\n")) continue;

            String lineType = findType(line);
            if (lineType.equals(EMAIL_SENDER) && !foundNewMail) {
                if (singleEmailData==null) {
                    singleEmailData = new HashMap<String, String>();
                }
                String curTypeData = singleEmailData.get(lineType);
                String newTypeData = curTypeData==null?line:curTypeData.concat(line);
                singleEmailData.put(lineType, newTypeData);

                foundNewMail = true;
            }else if (lineType.equals(EMAIL_SENDER) && foundNewMail) {
                EmailObject newEmail = new EmailObject();
                for (Map.Entry<String,String> emailComp : singleEmailData.entrySet()) {
                    String dataString = null;
                    switch (emailComp.getKey()) {
                        case EMAIL_SENDER:
                            dataString = emailComp.getValue();
                            //Reformat text data (Remove 'From:' and spare space)
                            dataString = dataString.substring(line.indexOf("From:") + "From:".length()).trim();
                            newEmail.setSender(dataString);
                            Logger.print("||    Found a Sender: " + dataString, "EmailConverter");
                            break;
                        case EMAIL_RECEIVER:
                            dataString = emailComp.getValue();
                            //Reformat text data (Remove 'To:' and spare space)
                            dataString = dataString.substring(line.indexOf("To:") + "To:".length()).trim();
                            newEmail.setReceiver(dataString);
                            Logger.print("||    Found a Receiver: " + dataString, "EmailConverter");
                            break;
                        case EMAIL_DATE:
                            dataString = emailComp.getValue();
                            //Reformat text data (Remove 'From:' and spare space)
                            dataString = dataString.substring(line.indexOf("Sent:") + "Sent:".length()).trim();
                            if (dataString.contains("(")) {
                                dataString = dataString.substring(0, dataString.indexOf("(") - 1).trim();
                            }
                            Date newDate = DateConverter.String2Date(dataString);
                            newEmail.setDate(newDate);
                            Logger.print("||    Found a Date: " +
                                    dataString, "EmailConverter");
                            break;
                        case EMAIL_SUBJECT:
                            dataString = emailComp.getValue();
                            //Reformat text data (Remove 'From:' and spare space)
                            dataString = dataString.substring(line.indexOf("Subject:") + "Subject:".length()).trim();
                            newEmail.setSubject(dataString.trim());
                            Logger.print("||    Found a Subject: " +
                                    dataString, "EmailConverter");
                            break;
                        case EMAIL_CONTENT:
                            dataString = emailComp.getValue();
                            newEmail.setContent(dataString.trim());
                            Logger.print("||    Found a Content: " +
                                            (dataString.length()<10?dataString:dataString.substring(0,10)),
                                    "EmailConverter");
                            break;
                    }
                }
                addNewEmail(newEmail);
            }//End of if

        }//End of for
    }

    public static String findType(String line) {
        if (line.contains("From:")) {
            return EMAIL_SENDER;
        }
        if (line.contains("To:")) {
            return EMAIL_RECEIVER;
        }

        if (line.contains("Subject:")) {
            return EMAIL_SUBJECT;
        }

        if (line.contains("Sent:")) {
            return EMAIL_DATE;
        }

        return EMAIL_CONTENT;
    }

    private void addNewEmail(EmailObject newEmail) {
        if (this.getObjectData() == null) {
            this.setObjectData(new ArrayList<EmailObject>());
        }

        this.getObjectData().add(newEmail);
    }

    public List<String> getRawData() {
        return rawData;
    }

    public void setRawData(List<String> rawData) {
        this.rawData = rawData;
    }

    public List<EmailObject> getObjectData() {
        return objectData;
    }

    public void setObjectData(List<EmailObject> objectData) {
        this.objectData = objectData;
    }
}
