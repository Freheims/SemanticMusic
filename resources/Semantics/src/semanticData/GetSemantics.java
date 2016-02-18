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
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder;
            Document doc = null;
            XPathExpression expr = null;
            builder = factory.newDocumentBuilder();
            doc = builder.parse(String.valueOf(alchemyApi.textGetRankedConcepts(song.getLyrics())));

            XPathFactory xFactory = XPathFactory.newInstance();

            XPath xPath = xFactory.newXPath();

            expr = xPath.compile("//concepts");
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            for (int i = 0; i < nodes.getLength(); i++){
                Node node = nodes.item(i);
                String concept = node.getChildNodes().item(1).getNodeValue();
                double relevance = Double.parseDouble(node.getChildNodes().item(2).getNodeValue());
                conceptsList.add(concept);

            }
            song.setConcepts(conceptsList);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
