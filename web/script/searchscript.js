//jQuery
const PREFIX = "PREFIX sm: <http://semanticmusic.xyz/vocab/>";
var searchTypes = ["concept", "emotion", "genre", "title", "artist", "album"]; //The different things we can search for.

/**
 * Method which is called upon by the eventListener
 * It is a wrapper that runs the different methods needed to perform the search
 * and populate the HTML-page
 *
 */
function search(forminput){
    document.getElementById("results").style.display="table";
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
    var query = buildQuery(words);
    executeQuery(query);
}

/**
 * Readies the new data to be inserted in the HTML table
 */
function populateHTML(response) {
    var json = JSON.parse(response);
    var results = json.results.bindings;
    results = removeDuplicates(results);
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
        wherestatement.push("FILTER regex(?predicate,\"" + searchwords[i].toLowerCase() + "\",\"i\") .");
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
 */
function removeDuplicates(arr) {
    var cleaned = [];
    arr.forEach(function(itm) {
        var unique = true;
        cleaned.forEach(function(itm2) {
            if (arr.isEqual(itm, itm2)) unique = false;
        });
        if (unique)  cleaned.push(itm);
    });
    return cleaned;
}

/**
 * Receives parameters, puts them in the HTML table
 */
function insertRow(img, title, artist, album, duration, spotify) {
    var table = document.getElementById("results");
    var indexToInsert = table.rows.length-1;
    var newJob = table.tBodies[0].insertRow(indexToInsert);
    newJob.innerHTML = "<td class=\"cover\"><img src=" + img + "></td> <td class=\"song\"><p>" + title + "</td> <td class=\"artist\"><p>" + artist + "</p></td>" + "<td class=\"album\"><p>" + album + "</p></td>" + "<td class=\"duration\"><p>" + millisToMinutesAndSeconds(duration) + "</p></td>" + "<td class=\"link\"><a href=\"https://play.spotify.com/track/" + spotify.replace("#", "") + "\" target=\"_blank\">Spotify</a></td>";
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

function millisToMinutesAndSeconds(millis) {
  var minutes = Math.floor(millis / 60000);
  var seconds = ((millis % 60000) / 1000).toFixed(0);
  return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
}
