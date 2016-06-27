package triples;

import database.GetAllSongs;
import lyrics.GetLyrics;
import model.Song;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.rulesys.builtins.GE;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import semanticData.GetSemantics;

import java.io.*;
import java.util.ArrayList;

/**
 * Class for creating and saving the RDF triples
 */
public class createTriples {

    private static final String tripleStore = "../tripleStore.nt";

    public static void main(String[] args) throws Exception {
        int songCounter = 0;
        ArrayList<Song> songs = GetAllSongs.getAll();
        while (!songs.isEmpty()){
            songCounter += songs.size();
            createTriples(songs);
            songs = GetAllSongs.getAll();

            try {
                System.out.println("Sleep: 10min");
                Thread.sleep(600000); //Ten minutes, milliseconds
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(songCounter>=450){
                try {
                    System.out.println("Sleep: 24h");
                    Thread.sleep(86400000); //24 hours, milliseconds
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                songCounter = 0;
            }

        }


        

    }
    public static void createTriples(ArrayList<Song> songs) throws Exception {

        //Model model = ModelFactory.createDefaultModel();
        Model model = RDFDataMgr.loadModel(tripleStore);
        //PREFIXES
        String semanticMusic = "http://semanticmusic.xyz/vocab/";

        //PROPERTIES
        Property title = model.createProperty(semanticMusic + "title");
        Property artist = model.createProperty(semanticMusic + "artist");
        Property album = model.createProperty(semanticMusic + "album");
        Property genre = model.createProperty(semanticMusic + "genre");
        Property concept = model.createProperty(semanticMusic + "concept");
        Property emotion = model.createProperty(semanticMusic + "emotion");
        Property image = model.createProperty(semanticMusic + "image");
        Property duration = model.createProperty(semanticMusic + "duration");

        for (Song song : songs) {
            try {
                GetLyrics.getLyric(song);
                GetSemantics.getConcepts(song);
                GetSemantics.getEmotions(song);
            }catch (Exception e){
            }

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
            GetAllSongs.setAsAnnotated(song);
        }

        OutputStream os = new FileOutputStream(tripleStore);
        RDFDataMgr.write(os, model, RDFFormat.NTRIPLES_UTF8);
        os.close();
        model.close();
    }
}
