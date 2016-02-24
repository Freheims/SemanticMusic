
import spotipy
import pymysql.cursors

#DATABASE INTERACTIONS

def getArtistFromDB(id):
    with connection.cursor() as cursor:
    # Create a new record
        sql = "SELECT * FROM `artist` WHERE `id`=%s"
        cursor.execute(sql, id)
        result = cursor.fetchone()
    return result

def addArtistToDB(id, name):
    with connection.cursor() as cursor:
        sql = "INSERT INTO `artist` VALUES (%s, %s)"
        cursor.execute(sql, (id, name))
        connection.commit()

def getAlbumFromDB(id):
    with connection.cursor() as cursor:
    # Create a new record
        sql = "SELECT * FROM `album` WHERE `id`=%s"
        cursor.execute(sql, id)
        result = cursor.fetchone()
    return result

def addAlbumToDB(id, name, type):
    with connection.cursor() as cursor:
        sql = "INSERT INTO `album` VALUES (%s, %s, %s)"
        cursor.execute(sql, (id, name, type))
        connection.commit()

def getTrackFromDB(id):
    with connection.cursor() as cursor:
    # Create a new record
        sql = "SELECT * FROM `track` WHERE `id`=%s"
        cursor.execute(sql, id)
        result = cursor.fetchone()
    return result

def addTrackToDB(id, name, duration, image):
    with connection.cursor() as cursor:
        sql = "INSERT INTO `track` VALUES (%s, %s, %s, %s, %s)"
        cursor.execute(sql, (id, name, duration, image, 0))
        connection.commit()

def getGenreFromDB(name):
    with connection.cursor() as cursor:
    # Create a new record
        sql = "SELECT * FROM `genre` WHERE `name`=%s"
        cursor.execute(sql, name)
        result = cursor.fetchone()
    return result

def addGenreToDB(name):
    with connection.cursor() as cursor:
        sql = "INSERT INTO `genre` VALUES (%s)"
        cursor.execute(sql, (name))
        connection.commit()

def getTrackAlbumFromDB(trackId):
    with connection.cursor() as cursor:
    # Create a new record
        sql = "SELECT * FROM `trackAlbum` WHERE `track_id`=%s"
        cursor.execute(sql, id)
        result = cursor.fetchone()
    return result

def addTrackAlbumToDB(trackId, albumId):
    with connection.cursor() as cursor:
        sql = "INSERT INTO `trackAlbum` (`track_id`,`album_id`) VALUES (%s, %s)"
        cursor.execute(sql, (trackId, albumId))
        connection.commit()

def getTrackArtistFromDB(trackId):
    with connection.cursor() as cursor:
    # Create a new record
        sql = "SELECT * FROM `trackArtist` WHERE `track_id`=%s"
        cursor.execute(sql, id)
        result = cursor.fetchone()
    return result

def addTrackArtistToDB(trackId, artistId):
    with connection.cursor() as cursor:
        sql = "INSERT INTO `trackArtist` (`track_id`,`artist_id`)  VALUES (%s, %s)"
        cursor.execute(sql, (trackId, artistId))
        connection.commit()

def getTrackGenreFromDB(trackId):
    with connection.cursor() as cursor:
    # Create a new record
        sql = "SELECT * FROM `trackGenre` WHERE `track_id`=%s"
        cursor.execute(sql, id)
        result = cursor.fetchone()
    return result

def addTrackGenreToDB(trackId, genre):
    with connection.cursor() as cursor:
        sql = "INSERT INTO `trackGenre` (`track_id`,`genre`)  VALUES (%s, %s)"
        cursor.execute(sql, (trackId, genre))
        connection.commit()

#OTHER STUFF

def addTracks(tracks, artistId, albumId, genres, image):
    for track in tracks:
        trackId = track['id']
        trackName = track['name']
        trackDuration = track['duration_ms']
        addTrackToDB(trackId, trackName, trackDuration, image)
        addTrackArtistToDB(trackId, artistId)
        addTrackAlbumToDB(trackId, albumId)

        for genre in genres:
            addTrackGenreToDB(trackId, genre)

def addAlbums(artistId, artistGenres):
    albumStack = addAlbumsToStack(artistId)
    for albumId in albumStack:
        album = sp.album(albumId)
        albumName = album['name']
        albumType = album['type']
        albumGenres = album['genres']
        albumImage = album['images'][0]['url']
        albumTracks = album['tracks']['items']
        allGenres = artistGenres + albumGenres

        addAlbumToDB(albumId, albumName, albumType)
        
        addGenres(albumGenres)
        addTracks(albumTracks, artistId, albumId, allGenres, albumImage)

def addRelatedArtists(id, artistStack):
    rArtist = sp.artist_related_artists(id)
    for currArtist in rArtist['artists']:
        if((getArtistFromDB(currArtist['id']) == None) and
            (artistStack.count(currArtist['id']) == 0)):
            artistStack.append(currArtist['id'])
    return artistStack

def addAlbumsToStack(id):
    artistAlbums = sp.artist_albums(id, None, None, 50, 0)
    stack = []
    for album in artistAlbums['items']:
        if((getAlbumFromDB(album['id']) == None) and (stack.count(album['id']) == 0)):
            stack.append(album['id'])
    return stack

def addGenres(genreList):
    if(len(genreList)<1):
        return
    for genre in genreList:
        if(getGenreFromDB(genre) == None):
            addGenreToDB(genre)

def addArtists(rootArtist, numberToAdd):

    artistStack = [rootArtist]
    count = 0

    while len(artistStack)>0:
        artist = sp.artist(artistStack.pop())
        artistId = artist['id']
        artistName = artist['name']
        artistGenre = artist['genres']
        addGenres(artistGenre)

        addArtistToDB(artistId, artistName)

        addAlbums(artistId, artistGenre)
        #print(albumStack)
        artistStack += addRelatedArtists(artistId, artistStack)
        count += 1
        if(count > numberToAdd):
            break


#RUNNING CODE

# Connect to the database
connection = pymysql.connect(host='freheims.xyz',
                             user='fredrik',
                             password='semanticMusic',
                             db='spotify',
                             charset='utf8mb4',
                             cursorclass=pymysql.cursors.DictCursor)

sp = spotipy.Spotify()

try:
    addArtists('0DbbnkFMhhDvinDYIiHhGS', 4)

finally:
    connection.close()
