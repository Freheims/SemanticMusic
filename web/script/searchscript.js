const PREFIX = "PREFIX sm: <http://semanticmusic.xyz/vocab/>";
var searchTypes = ["concept", "emotion", "genre", "title", "artist", "album"]; //The different things we can search for.
var temprandomtablesongs = ["Senseless Massacre", "Routine", "Perfect Life", "Vacuity", "Eaten", "O Majestic Being, Hear My Call", "Blue Clouds", "Grandloves"];
var temprandomtableartists = ["Gojira", "Hate Eternal", "Purity Ring", "Porcupine Tree", "Steven Wilson", "Bloodbath", "Rings of Saturn", "Tyler, the Creator", "Schoolboy Q"];
var temprandomtableimages = ["http://i.imgur.com/NxPuN.jpg", "http://i.imgur.com/2qpcGjX.jpg", "http://i.imgur.com/56ZW0ud.jpg", "http://i.imgur.com/iAQeQsk.jpg", "http://i.imgur.com/o0NyKVg.jpg"];
var temprandomtablealbums = ["The Way of All Flesh", "Infernus", "Cherry Bomb", "Phenotype", "Lingua Franca", "Gaia-Medea", "Ob(servant)", "This Is All Yours", "Juggernaut", "Pale Communion"];

/**
 * Method which is called upon by the eventListener
 * It is a wrapper that runs the different methods needed to perform the search
 * and populate the HTML-page
 *
 */
function search(forminput){
    var words = searchinput(forminput);
    console.log("Words: " + words);
    var result = performSearch(words);
    var songs = getData(result);
    populateHTML(songs);
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
    for (var i = 0; i < searchTypes.length; i++) {
        result.concat(executeSearch(words, searchTypes[i]));
    }
    
    result = removeDuplicates(result);
    return result;
}

/**
 * Gets the data about the songs in the result.  
 *
 */
function getData(result){
    //TODO get the song data from file
}

/**
 * Populates the HTML-page with the search-results. 
 *
 */
function populateHTML(songs) {
    //TODO this is the thing that shall popultate the HTML-page is it tho
    //Maybe this should be placed somewhere else? So that it is easyer to use
    //from other files aswell
}

/**
 * Creates the search-query and executes the search
 *
 */
function executeSearch(searchwords, searchType){
    var wherestatement = [
        "WHERE {"
        ];
    for (var i in searchwords){
        wherestatement.push("?song sm:" + searchType + " " + searchwords[i] + ".");
    }
    wherestatement.push("}");
    var where = wherestatement.join("\n");
    
    var query = [
        PREFIX,
        "SELECT ?song",
        where
        ].join("\n");
        
    console.log("Query: " + query);
    var result = executeQuery(query);
    return result;
}

/**
 * Executes the SPARQL-Query and gets the result from the DB/file
 *
 */
function executeQuery(query) {
    $.get("http://freheims.xyz/test.py", function(data){
        console.log(data);
	console.log("Halla");
    });
    //TODO find out how to do this.
    
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

/** BRUKES SJE MEN KAN VÆRE GREIE Å HA TIL Å SMYGE ET BLIKK PÅ IDK
function InsertBefore() {
    var table = document.getElementById("results");
    var under = document.getElementById("toprow");
    var newJob = document.createElement("tr");
    //newJob.innerHTML = "<tr><td>Foo</td><td>Bar</td></tr>"; // its inserted inside TR. no additional TR's needed.
    newJob.innerHTML = "<td>Foo "+(i++)+".</td><td>Bar</td>";
    table.tBodies[0].insertBefore(newJob,under);
}

function AppendChild() {
    var table = document.getElementById("results");
    var newJob = document.createElement("tr");
    newJob.innerHTML = "<td>Foo "+(i++)+".</td><td>Bar</td>";
    table.tBodies[0].appendChild(newJob);
}
*/


function InsertRow() {
    var randArt = Math.floor((Math.random() * temprandomtableartists.length));
    var randSong = Math.floor((Math.random() * temprandomtablesongs.length));
    var randImg = Math.floor((Math.random() * temprandomtableimages.length));
    var randAlb = Math.floor((Math.random() * temprandomtablealbums.length));
    var art = temprandomtableartists[randArt];
    var sng = temprandomtablesongs[randSong];
    var img = temprandomtableimages[randImg];
    var alb = temprandomtablealbums[randAlb];
    

    console.log(randArt + " " + randSong + " " + randImg);
    
    var table = document.getElementById("results");
    var indexToInsert = table.rows.length;
    var newJob = table.tBodies[0].insertRow(indexToInsert);
    newJob.innerHTML = "<td class=\"cover\"><img src=" + img + "></td> <td class=\"song\"><p>" + sng + "</td> <td class=\"artist\"><p>" + art + "</p></td>" + "<td class=\"album\"><p>" + alb + "</p></td>" + "<td class=\"link\"><a href=\"https://play.spotify.com/track/5e0OSsSpWpQ2kS7Z9CGLDu\" target=\"_blank\">Spotify</a></td>";
    /* newJob.innerHTML = "<td><img src=\"https://upload.wikimedia.org/wikipedia/en/1/16/Gojira_-_The_Way_of_All_Flesh_-_2008.jpg\"></td> <td><p>Song name</p></td> <td><p>Artist name</p></td> <td><p>Album name</p></td> <td><a href=\"https://play.spotify.com/track/5e0OSsSpWpQ2kS7Z9CGLDu\" target=\"_blank\">Link to song on spotify</a></td>";
*/
}


function httpGetAsync(theUrl, callback)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous 
    xmlHttp.send(null);
}
