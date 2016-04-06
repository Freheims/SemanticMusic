var rdfstore = require('rdfstore');
var store;

rdfstore.create(function(err, store) {
    store.load("remote", "../rdf/tripleStore.nt", function(err, store){
        if(!err){
            console.log("Hallaien");
        }else{
            console.log("Satan");
            console.log(err);
        }
    });
});
//console.log(store.typeof);