import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HelpParse {
    // Таблиця розбивається на блоки по два стобці, в кожному блоці перший стовбець - стовбець заголовка, другий - значень
    // If row of title has duplicate values and different values in second row then their values concatinated
    public HashMap<String, String> parseTableToMap(Element table, boolean includeFirstRaw){
        HashMap <String, String> resultMap = new HashMap<String, String>();
        Elements rows = table.select("tr");
        Elements colKey;
        Elements colValue;
        ArrayList<String> keys = new ArrayList<String>();
        ArrayList <String> values = new ArrayList<String>();
        int countPairRows = rows.size() / 2;
        for (int i = 0; i < countPairRows; i++){
            colKey = rows.get(i).select("td");
            for (int k = 0; k < colKey.size(); k++){
                keys.add(colKey.get(k).text());
            }
            colValue = rows.get(i + 1).select("td");
            for (int k = 0; k < colValue.size(); k++){
                values.add(colKey.get(k).text());
            }
            if (keys.size() != values.size()){
                resultMap.put("Feedback", "Different list sizes");
            }
            for (int k = 0; k < colKey.size(); k++){
                if (resultMap.containsKey(keys.get(k))){
                    if (resultMap.get(keys.get(k)).equals(values.get(k))){}
                    else {
                        String newValue = resultMap.get(keys.get(k)) + ", " + values.get(k);
                        resultMap.put(keys.get(k), newValue);
                    }
                } else{
                    resultMap.put(keys.get(k), values.get(k));
                }
            }

        }

        return resultMap;
    }


}
