# PR Code Change Description:

![appRunAfter](resources/Montuno_appRunAfter.gif)

### frontend
1. added createArtistListView() call in main() of Main.java

### backend
1. added logic & test to backend for dropArtistListView()
2. added dropArtistListView() test in beforeAll to allow for app to run
3. added test for badInput sortOrder for queryArtistsForSong()

# Functional Impacts:
1. automated creation of artist_list view by createArtistListView() before app launch()
2. artist_list view dropped automatically before all tests are run to preserve original db integrity before testing

# QA Reminders:
1. run Main.main() to launch application
2. use DB Browser for SQLite app for database viewing
3. there is a music_original.db backup file in resources dir if music.db is corrupted via testing 

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

