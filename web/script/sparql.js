var rdfstore = require('rdfstore');

rdfstore.create(function(err, store) {
    store.load("text/n3", "http://semanticmusic.xyz/tripleStore.nt", function(err, store){
        if(!err){
            console.log("Hallaien");
        }else{
            console.log(err);
        }
    });
    
    store.execute("SELECT * { ?s ?p ?o }", function(err, results){
        if(!err) {
            // process results
            //console.log(results[0]);
        }
    });
    
});



function skriv(){
    console.log("Hello")
}