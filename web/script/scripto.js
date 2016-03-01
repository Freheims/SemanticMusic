var input = [];

//tar i mot input og deler det opp, alle søkeord splittes med ','
//burde kanskje legge til hva man IKKE vil ha? kanskje? IDK
function searchinput(temp){
    input = temp.split(',');
    
    //fjerner whitespace fra strings
    for(var x = 0; x < input.length(); x++){
        var oldString = input[x];
        input[x] = oldString.replace(/\s+/g,"");
    }
}

function test(search) {
    var query = [
        "PREFIX", // må fikse alle prefixene ayy
        "SELECT ?songs",
        "WHERE {",
            "sparql query som fikser shittet",
        "}"
        ].join(" ");
}

function test(search, search2) {
    var query = [
        "PREFIX", // må fikse alle prefixene ayy
        "SELECT ?songs",
        "WHERE {",
            "sparql query som fikser shittet",
        "}"
        ].join(" ");
}