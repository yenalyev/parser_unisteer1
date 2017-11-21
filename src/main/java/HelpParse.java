import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

public class HelpParse {
    // Таблиця розбивається на блоки по два стобці, в кожному блоці перший стовбець - стовбець заголовка, другий - значень
    // If row of title has duplicate values and different values in second row then their values concatinated
    public static HashMap<String, String> parseTableToMap(Element table){
        HashMap <String, String> resultMap = new HashMap<String, String>();
        Elements rows = table.select("tr");
        Elements cols = rows.get(0).select("td");
        Elements colKey = null;
        //Elements colValue;
        ArrayList<String> keys = new ArrayList<String>();
        ArrayList <String> values = new ArrayList<String>();
        String lastColumnValue = "";
        StringBuilder sb = new StringBuilder();
        int countPairCol = cols.size() / 2;
        for(int j = 0; j < rows.size(); j++){
            for (int i = 0; i < countPairCol; i++){
                colKey = rows.get(j).select("td");
                keys.add(colKey.get(2*i).text());
                //colValue = rows.get(j).select("td");
                values.add(colKey.get(2*i+1).text());

                if (keys.size() != values.size()){
                    resultMap.put("Feedback", "Different list sizes");
                }
                int lastKeyInList = keys.size()-1;
                if (resultMap.containsKey(keys.get(lastKeyInList))){
                    if (resultMap.get(keys.get(lastKeyInList)).equals(values.get(lastKeyInList))){}
                    else {
                        String newValue = resultMap.get(keys.get(lastKeyInList)) + ", " + values.get(lastKeyInList);
                        resultMap.put(keys.get(lastKeyInList), newValue);
                    }
                } else{
                    resultMap.put(keys.get(lastKeyInList), values.get(lastKeyInList));
                }

            }
            if (cols.size() > 2 * countPairCol){

                sb.append(colKey.get(colKey.size()-1).text() + ", ");
                lastColumnValue = sb.toString();
            }
        }
        if (cols.size() > 2 * countPairCol){
            resultMap.put("Last Column", lastColumnValue);
        }

        return resultMap;
    }


}
