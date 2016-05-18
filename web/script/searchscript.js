//jQuery
const PREFIX = "PREFIX sm: <http://semanticmusic.xyz/vocab/>";
var searchTypes = ["concept", "emotion", "genre", "title", "artist", "album"]; //The different things we can search for.
var queryResponse;

/**
 * Method which is called upon by the eventListener
 * It is a wrapper that runs the different methods needed to perform the search
 * and populate the HTML-page
 *
 */
function search(forminput){
    var words = searchinput(forminput);
    performSearch(words);
}

/**
 * Takes in the input from the HTML-form and splits it into an array 
 * and removes leading and trailing whitespaces from each word
 *
 */
function searchinput(forminput){
    var searchwords = forminput.value;
    var words = searchwords.split(",");

    //Trim words    
    for(var x = 0; x < words.length; x++){
        var oldString = words[x];
        words[x] = oldString.replace(/^\s+|\s+$/g, '');
    }
    return words;
}

/**
 * Calls on the different search-functions and combines the result in an array
 * and removes duplicates from the array
 *
 */
function performSearch(words){
    var result = [];
    var query = buildQuery(words);
    executeQuery(query);
}

/**
 * Populates the HTML-page with the search-results. 
 *
 */
function populateHTML(response) {
    var json = JSON.parse(response);
    var results = json.results.bindings;
    for(var i = 0; i < results.length; i++ ){
        insertRow(results[i].image.value, results[i].title.value, results[i].artist.value, results[i].album.value, results[i].duration.value, results[i].song.value);
    }
    
}

/**
 * Creates the search-query and executes the search
 *
 */
function buildQuery(searchwords){
    var wherestatement = [
        "WHERE {"
        ];
    for (var i in searchwords){
        wherestatement.push("?song ?predicate" + " \"" + searchwords[i] + "\" .");
    }
    wherestatement.push("?song sm:title ?title . ");
    wherestatement.push("?song sm:album ?album . ");
    wherestatement.push("?song sm:artist ?artist . ");
    wherestatement.push("?song sm:duration ?duration . ");
    wherestatement.push("?song sm:image ?image . ");
    wherestatement.push("}");
    var where = wherestatement.join(" ");
    
    var query = [
        PREFIX,
        "SELECT ?song ?title ?album ?artist ?duration ?image",
        where
        ].join(" ");
        
    return query;
}


/**
 * Removes duplicates from a list
 *
 * (We should probably deal with this in another way, since a song that occurs 
 * multiple times in the result probably is more relevant than a song with fewer
 * occurences. But for now we dont care about that at all.)
 */
function removeDuplicates(list){
    return Array.from(new Set(list));
}

function insertRow(img, title, artist, album, duration, spotify) {
    var table = document.getElementById("results");
    var indexToInsert = table.rows.length;
    var newJob = table.tBodies[0].insertRow(indexToInsert);
    newJob.innerHTML = "<td class=\"cover\"><img src=" + img + "></td> <td class=\"song\"><p>" + title + "</td> <td class=\"artist\"><p>" + artist + "</p></td>" + "<td class=\"album\"><p>" + album + "</p></td>" + "<td class=\"duration\"><p>" + msToMS(duration) + "</p></td>" + "<td class=\"link\"><a href=\"https://play.spotify.com/track/" + spotify.replace("#", "") + "\" target=\"_blank\">Spotify</a></td>";
}

function executeQuery(sparql){
    var xmlHttp = new XMLHttpRequest;
	
	var url = 'http://semanticmusic.xyz:3030/ds/sparql?query=' + sparql;
	var response = "";

    xmlHttp.open("GET", url, true); // true for asynchronous 
    xmlHttp.send(null);
    
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4){
			response = xmlHttp.responseText;
			populateHTML(response);
		}
	};
}
function msToMS(ms) {
    var seconds = ms / 1000;
    var minutes = parseInt( seconds / 60 );
    seconds = seconds % 60;
    minutes.toFixed(2);
    seconds.toFixed(2);
    return(minutes + ":" + seconds);
}