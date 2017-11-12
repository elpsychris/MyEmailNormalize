package utils;

import com.EmailConverter;
import model.EmailObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtils {
    private final static int EMAIL_COMPONENT_EXIST = -1;
    private final static int OK = 0;

    private static List<String> readTxtFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        File file = new File(filePath);
        Files.lines(file.toPath(), StandardCharsets.ISO_8859_1).forEachOrdered(lines::add);
        return lines;
    }

    public static List<EmailObject> readEmailFromFile(String filePath) throws IOException {
        List<String> rawText = readTxtFile(filePath);
        EmailConverter converter = new EmailConverter(rawText);
        List<EmailObject> result = converter.getObjectData();
        return result;
    }

    public static void writeXlsFile(String fileName, List<EmailObject> emailList) {
        XLSExporter exporter = new XLSExporter(emailList);
        try {
            exporter.createWorkSheet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
