# PR Code Change Description:

![appRunAfter](resources/Montuno_appRunAfter.gif)

### frontend
1. for Controller.java, added missing @FXML id listSongsForAlbum to resolve app breaking on startup
2. for main.fxml & Controller.java, added @FXML id listAlbumsForArtist, updateArtistName, deleteSongForAlbum, exit to display appropriate buttons for dynamic page rendering 
3. for Controller.java, added artistSource & albumSource to display respective names for dynamic page rendering 
4. for main.fxml & Controller.java, added exit() button with basic css styling + CONSTANTS

### backend
n/a: no PR changes

# Functional Impacts:
1. resolve app breaking on startup & started implementing dynamic page rendering

# QA Reminders:
1. run Main.main() to launch application
2. use DB Browser for SQLite app for database viewing
3. there is a music_original.db backup file in resources dir if music.db is corrupted via testing 

FUTURE STORY FIX: had to manually create an Album for UI because return src model is automatically Artist object and when cast as model is mapped incorrectly to a compatible target dataType

# Acceptance Criteria:
1. write an MVP that can get data from a SQLite3 DB and update the frontend & backend
2. the MVP app manages a small database of artists and their respective albums & songs
3. run tests first: music.db file should start without an artist_list view prior to DatasourceTest execution

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

