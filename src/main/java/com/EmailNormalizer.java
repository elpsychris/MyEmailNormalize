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

//
//    private static boolean mailSubjectFilter(EmailObject mailObj) {
//        return mailObj.getSubject().toLowerCase().contains("error");
//    }
//
//    private static boolean mailContentFilter(EmailObject mailObj) {
//        return mailObj.getContent()!=null && mailObj.getContent().toLowerCase().contains("[err");
//    }
//
//    private static List<EmailObject> mailFilter(List<EmailObject> before) {
//        List<EmailObject> after = new ArrayList<>();
//        for (EmailObject email : before) {
//            if (mailSubjectFilter(email) && mailContentFilter(email)) {
//                after.add(email);
//            }
//        }
//        return after;
//    }

    public static void main(String[] args) {
        System.out.println("======== Welcome to TTL Mail Normalizer");
        System.out.print("Input file: ");
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.nextLine();



//        emailList = mailFilter(emailList);

//        for (EmailObject email : emailList) {
//            email.print();
//        }


    }
}
