package semanticData;

import com.alchemyapi.api.AlchemyApi;
import com.alchemyapi.api.AlchemyApiConfiguration;
import model.Song;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/*
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import java.io.BufferedInputStream;*/
import java.util.ArrayList;
import java.util.Objects;

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
            Elements elements = doc.select("concepts");
            String[] concepts = elements.select("text").text().split(" ");
            String[] relevances = elements.select("relevance").text().split(" ");

            System.out.println();
            System.out.println("ConLength: " + concepts.length + " " + concepts[0] + " , relLength: " + relevances.length + " " + relevances[0]);
            System.out.println();

            double[] relevance = new double[relevances.length];
            for (int i = 0; i< relevances.length;  i++) {
                String rel = relevances[i];
                relevance[i] = Double.parseDouble(rel);
            }
            int len = Math.min(concepts.length, relevance.length);
            for (int i = 0; i < len; i++){
                if (relevance[i]> 0.3){
                    conceptsList.add(concepts[i]);
                    System.out.println(concepts[i]);
                }
            }

            song.setConcepts(conceptsList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
