# PR Code Change Description:

![appRunAfter](resources/Montuno_appRunAfter.gif)

### frontend
1. wrote controller that uses FXML to interface with JavaFX via main.fxml file
2. manage SQLite3 database via SQL
3. current UI functionality: GET, UPDATE 

### backend
1. added fields songTitle & song_id to SongArtist class
2. added fields songTitle & song_id to CREATE_ARTIST_LIST_VIEW SQL string
3. added fields songTitle & song_id to QUERY_ARTIST_LIST_VIEW SQL string
4. added fields songTitle & song_id to SongArtist instances in printSongArtists() and buildSongArtists()
5. updated anything that is SongArtist obj related like SONG_INFO_VIEW to ARTIST_LIST_VIEW
6. updated DatasourceTest: createArtistListViewForSongArtists to static & moved test to static beforeAll()
7. added resources dir to top level
8. added to resources: SQLite driver, JavaFX driver, original music.db reference, and PR gifs

# Functional Impacts:
1. prepared db table structure for deleteSong()
2. automated createArtistListViewForSongArtists() on execution of DatasourceTest file
3. easier to test music.db file

# QA Reminders:
1. run DatasourceTest.java before starting application to create artist_list view
2. use DB Browser for SQLite app for database viewing
3. Datasource.deleteSong() is not implemented and should expect two failing tests

### Pre-existing Bug
1. clicking updateArtist button on "AC DC", viewing their albums,
2. then returning to list view will cause "AC/DC (updated name)" to disappear 
![knownBug](resources/Montuno_knownBug.gif)

# Acceptance Criteria:
1. write an MVP that can get data from a SQLite3 DB and update the frontend & backend
2. the MVP app manages a small database of artists and their albums & songs
3. music.db file should start without an artist_list view prior to DatasourceTest execution
4. after DatasourceTest execution, artist_list view created, all but 2 tests pass, and app starts successfully

### story reference:
https://github.com/enzo-dante/Montuno

# Build Tests:

### java Junit5
![testRun](resources/Montuno_testRun.gif)

### jest
n/a

### angular
n/a

# Before:
![appRunBefore](resources/Montuno_appRunBefore.gif)

# After:
![appRunAfter](resources/Montuno_appRunAfter.gif)

