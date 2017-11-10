package utils;

import model.EmailObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class XLSExporter {
    private XSSFWorkbook workbook = null;
    private List<EmailObject> emails = null;

    public XLSExporter(List<EmailObject> list) {
        this.emails = list;
    }

    public void createWorkSheet() throws IOException{
        //Create Blank workbook
        this.workbook = new XSSFWorkbook();

        createSpreadSheet();
        //Create file system using specific name
        OutputStream out = new FileOutputStream(new File("/media/elpsychris/U-Soft/elpsychris/Project/createworkbook.xlsx"));

        //write operation workbook using file out object
        workbook.write(out);
        out.close();
        System.out.println("createworkbook.xlsx written successfully");
    }

    private void createSpreadSheet() {
        if (workbook == null) return;

        Map<String, Object[]> map = new TreeMap<String, Object[]>();
        map.put("1",new Object[]{"Date","Sender","Receiver","Subject","Group","Type","Defect","Detail"});
        for (int i = 1; i < emails.size(); i++) {
            map.put(Integer.toString(i+1),emails.get(i).toArray());
        }

        XSSFSheet newSheet = workbook.createSheet("Report ");
        XSSFRow row;

        //Iterate over data and write to sheet
        Set<String> keyid = map.keySet();
        int rowid = 0;

        for (String key : keyid) {
            row = newSheet.createRow(rowid++);
            Object [] objectArr = map.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }

    }
}
