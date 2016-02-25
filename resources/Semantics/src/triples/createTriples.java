package triples;

import database.GetAllSongs;
import lyrics.GetLyrics;
import model.Song;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.util.FileManager;
import semanticData.GetSemantics;

import java.io.*;
import java.util.ArrayList;

/**
 * Class for creating and saving the RDF triples
 */
public class createTriples {

    private static final String tripleStore = "tripleStore.nt";

    public static void main(String[] args) throws Exception {
        Model model = ModelFactory.createDefaultModel();
        //Model model = RDFDataMgr.loadModel(tripleStore);
        //PREFIXES
        //model.setNsPrefix("mo", "http://purl.org/ontology/mo/");
        //model.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");
        String sm = "http://freheims.xyz/semantic-music/";

        //PROPERTIES
        //Property maker = model.createProperty("http://xmlns.com/foaf/0.1/maker");
        //Property title = model.createProperty("http://purl.org/dc/elements/1.1/title");
        Property title = model.createProperty(sm + "title");
        Property artist = model.createProperty(sm + "artist");
        Property album = model.createProperty(sm + "album");
        Property genre = model.createProperty(sm + "genre");
        Property concept = model.createProperty(sm + "concept");
        Property emotion = model.createProperty(sm + "emotion");
        Property image = model.createProperty(sm + "image");
        Property duration = model.createProperty(sm + "duration");


        ArrayList<Song> songs = GetAllSongs.getAll();
        for (Song song : songs) {
            GetLyrics.getLyric(song);
            GetSemantics.getConcepts(song);
            GetSemantics.getEmotions(song);
            //System.out.println(song.toString());

            Resource res = model.createResource("#" + song.getSPOTIFY_ID());
            res.addProperty(title, song.getNAME());
            res.addProperty(artist, song.getARTIST_NAME());
            res.addProperty(album, song.getALBUM_NAME());
            res.addProperty(image, song.getIMAGE());
            res.addProperty(duration, String.valueOf(song.getDURATION()));
            for (String gen : song.getGENRE()) {
                res.addProperty(genre, gen);
            }
            for (String con : song.getConcepts()) {
                res.addProperty(concept, con);
            }
            for (String em : song.getEmotions()){
                res.addProperty(emotion, em);
            }
        }

        OutputStream os = new FileOutputStream(tripleStore);
        RDFDataMgr.write(os, model, RDFFormat.NTRIPLES_UTF8);
        os.close();
        model.close();
    }
}
