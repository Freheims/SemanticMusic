package semanticData;

import com.alchemyapi.api.AlchemyApi;
import com.alchemyapi.api.AlchemyApiConfiguration;
import model.Song;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by fredrik on 2/16/16.
 */
public class GetSemantics {

    private static String API_KEY = ApacheApiKey.getApiKey();

    private static AlchemyApi alchemyApi = new AlchemyApi(new AlchemyApiConfiguration(API_KEY));

    public static void getConcepts(Song song){
        if(song.getLyrics() == "") return;
        ArrayList<String> conceptsList =  new ArrayList<>();
        Document conceptObject = alchemyApi.textGetRankedConcepts(song.getLyrics());
        //System.out.println(conceptObject);
        Elements concepts = conceptObject.getElementsByTag("<concepts>");
        System.out.println(concepts == null);
        for(Element con : concepts){
            System.out.println("con: " + con == null);
            String name = con.getElementById("text").ownText();
            double relevance = Double.parseDouble(con.getElementById("relevance").ownText());
            if(relevance > 0.4){
                conceptsList.add(name);
            }
        }
        song.setConcepts(conceptsList);

    }


}
