import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Максим on 10.07.2017.
 */
public class HTMLParser {
    public static void parser () throws IOException, InterruptedException {

        Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("Parse_result");
        String filename = GUI_FileChooser.path;

        //String filename = "C:\\Users\\Максим\\Desktop\\example.xls";
        ArrayList<String> excelContent = Excel.ReadFromExcel(filename);
        for (int i = 0; i < excelContent.size(); i++) {
            TimeUnit.SECONDS.sleep(0);
            String sku = excelContent.get(i);
            String linkToParse = "";

            if (sku != "") {
                Document doc;
                String link = "http://www.unisteer.com/index.php?option=com_mijosearch&view=search&query=" + sku;
                Row row = sheet1.createRow(i);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(sku);

                try {
                    doc = Jsoup.connect(link).get();
                    Elements linkFromSearch = doc.select("#mijosearch-results").select("a[href]");
                    if(!linkFromSearch.isEmpty()){
                        linkToParse = linkFromSearch.attr("abs:href");
                        Document doc1 = Jsoup.connect(linkToParse).get();
                        Elements parseSKU = doc1.select(".description");
                        Cell cell4 = row.createCell(4);
                        cell4.setCellValue(parseSKU.text());

                        Elements fin5 = doc1.select(".mijoshop_heading_h1");
                        Cell cell5 = row.createCell(5);
                        cell5.setCellValue(fin5.text());

                        Elements fin3 = doc1.select("#tab-description");
                        Cell cell3 = row.createCell(3);
                        Cell cellInstallInstructions = row.createCell(6);
                        cell3.setCellValue(fin3.text());
                        cellInstallInstructions.setCellValue(fin3.attr("abs:href"));

                        Elements links = doc1.select("a[href]");
                        for (Element element: links){
                            if (element.text().equals("CLICK HERE TO LOOK AT PRODUCT INSTRUCTIONS")){
                                Cell cell7 = row.createCell(7);
                                cell7.setCellValue(element.attr("href"));
                            }
                        }


                        Elements skuImage = doc1.select(".image").select("href");
                        Cell cell8 = row.createCell(8);
                        cell8.setCellValue(skuImage.attr("abs:href"));
                        System.out.println(skuImage.attr("abs:href"));

                    } else {
                        System.out.println("Missing sku # " + sku);
                        continue;
                    }

                    //String title = doc.title();
                    //System.out.println(title);
                    System.out.println("Parsing SKU No " + i + " ...");
                    System.out.println(linkToParse);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Connection to SKU No" + i + " has been failed...");
                }

                /*Elements fin1 = doc1.select(".itemSku");
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(fin1.text());

                Elements fin2 = doc1.select(".itemUPC");
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(fin2.text());

                Elements fin8 = doc1.select(".mainImage").select("[src]");
                Cell cell7 = row.createCell(7);
                cell7.setCellValue(fin8.attr("abs:src"));*/

            } else {
                break;
            }
            //break;
        }
        Excel.WriteHSSFWorkbook("C:\\Users\\Maxim\\Desktop\\Parse_Unisteer.xls", wb);
        System.out.println("Done!");
    }

}
