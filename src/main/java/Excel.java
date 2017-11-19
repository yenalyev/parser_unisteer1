import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Максим on 10.07.2017.
 */
public class Excel {
    public static void main(String[] args) throws IOException {
        ArrayList<String> excelContent = ReadFromExcel("C:\\Users\\Максим\\Desktop\\example.xls");
        for (String s : excelContent) {
            System.out.println(s);
        }

    }
    public static Workbook WriteHSSFWorkbook(String filename, Workbook wb){
        FileOutputStream fos;
        {try {
            fos = new FileOutputStream(filename);
            wb.write(fos);
            fos.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        return wb;
    }
    public static ArrayList<String> ReadFromExcel(String filename) throws IOException {
        InputStream inputStream = new FileInputStream(filename);
        HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inputStream));
        ArrayList<String> ExcelContent = new ArrayList<String>();
        Sheet sheet1 = wb.getSheetAt(0);
        for (Row row : sheet1){
            for (Cell cell : row){

                ExcelContent.add(cell.getStringCellValue());
            }
        }
        return ExcelContent;
    }

}
