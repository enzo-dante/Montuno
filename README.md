# PR Code Change Description:

![appRunAfter](resources/Montuno_appRunAfter.gif)

### frontend
1. for Controller.java, added _showMessageDialog() & showMessageDialog() that will render popup window
2. for Controller.java, added error CONSTANTS
3. for Controller.java, called showMessageDialog() in listSongForAlbum(), listAlbumsForArtist(), listArtists()  
4. for Main.java, called showMessageDialog() in init() 
5. started dialogs.css file

### backend
n/a: no PR changes

# Functional Impacts:
1. render informative popup dialog on error button clicks when nothing is selected or error starting app 

# QA Reminders:
1. In Datasource.java, variable CONNECTION_STRING must match your local directory path
2. run DatasourceTest first before launching app 
3. run Main.main() to launch application 
4. use DB Browser for SQLite app for database viewing 
5. there is a music_original.db backup file in resources dir if music.db is corrupted via testing 

FUTURE STORY: currently, app manually creates an Album for UI because return src model is automatically Artist object and when cast as model is mapped incorrectly to a compatible target dataType

FUTURE STORY: currently, app requires user to manually change Datasource.java CONNECTION_STRING to match local directory path

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

