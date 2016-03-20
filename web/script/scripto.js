var PREFIX = "PREFIX test: <freheims.xyz/semantic-music/>" //Global variabel? Sidan det er felles for alle queries


//tar i mot input og deler det opp, alle søkeord splittes med ','
//burde kanskje legge til hva man IKKE vil ha? kanskje? IDK
function searchinput(temp){
    var tempep = temp.value;
    var input = tempep.split(",");
    console.log(input);
    
    
    //fjerner leading og trailing whitespace fra strings
    for(var x = 0; x < input.length; x++){
        var oldString = input[x];
        input[x] = oldString.replace(/^\s+|\s+$/g, '');
    }
    searchConcepts(input);
    
    //Er det meininga å gjere dette to gongar?
    for(var x = 0; x < input.length; x++){
        var oldString = input[x];
        input[x] = oldString.replace(/\s+/g,"");
    }
}

//Concept? Var det det vi ville ha her
function concept(input) {
    var query = [
        PREFIX, // må fikse alle prefixene ayy
        "SELECT ?songs",
        "WHERE {",
            "?songs test:concept " + input +'.',
        "}"
        ].join(" ");
    
    console.log(query);
}

//Describes? What is?
function describes(input) {
    var query = [
        PREFIX, // må fikse alle prefixene ayy
        "SELECT ?songs",
        "WHERE {",
            "?songs .contains" ,
        "}"
        ].join(" ");
}

function semtech() {
    
}


function searchConcepts(searchwords){
    var wherestatement = [
        "WHERE {",
        "?song test:title ?songTitle .",
        "?song test:artist ?songArtist .",
        "?song test:album ?songAlbum .",
        "?song test:image ?songImage .",
        ];
    for (var i in searchwords){
        wherestatement.push("?song test:concept " + searchwords[i] + ".");
    }
    wherestatement.push("}");
    var where = wherestatement.join("\n");
    
    var query = [
        PREFIX,
        "SELECT ?songTitle, ?songArtist, ?songAlbum, ?songImage",
        where
        ].join("\n");
    console.log(query);
}