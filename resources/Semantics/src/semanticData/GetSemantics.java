package semanticData;

import com.alchemyapi.api.AlchemyApi;
import com.alchemyapi.api.AlchemyApiConfiguration;
import model.Song;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import java.io.BufferedInputStream;
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
            //TODO
            song.setConcepts(conceptsList);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
