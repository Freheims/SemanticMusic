package semanticData;

import com.alchemyapi.api.AlchemyApi;
import com.alchemyapi.api.AlchemyApiConfiguration;
import model.Song;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Class for getting sematic data from Alchemy
 * Created by fredrik on 2/16/16.
 */
public class GetSemantics {

    private static String API_KEY = ApacheApiKey.getApiKey();

    private static AlchemyApi alchemyApi = new AlchemyApi(new AlchemyApiConfiguration(API_KEY));

    public static void getConcepts(Song song){
        if(song.getLyrics() == "") return;
        ArrayList<String> conceptsList = new ArrayList<>();
        try {
            Document doc = alchemyApi.textGetRankedConcepts(song.getLyrics());
            Elements elements = doc.select("concepts").select("concept");

            for(int i = 0; i < elements.size(); i++){
                Element element = elements.get(i);
                String concept = element.select("text").text();
                double relevance = Double.parseDouble(element.select("relevance").text());
                if(relevance > 0.3){
                    conceptsList.add(concept);
                }
            }
            song.setConcepts(conceptsList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
