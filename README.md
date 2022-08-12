# PR Code Change Description:

![appRunAfter](resources/Montuno_appRunAfter.gif)

### frontend
1. completed logic for listSongsForAlbum() that connects to backend to fetch data and renders it to the UI
2. added Show Song button that toggles if on album list view

FUTURE STORY FIX: had to manually create an Album for UI because return src model is automatically Artist object and when cast as model is mapped incorrectly to a compatible target dataType

### backend
1. added SQL constants, tests, and querySongsById method to Datasource
2. updated Song field "title" to "name"
3. updated constants that pertain to Song class using "title" to "name"

# Functional Impacts:
1. can SQL query songs by albumId and display on UI if on album list view

# QA Reminders:
1. run Main.main() to launch application
2. use DB Browser for SQLite app for database viewing
3. there is a music_original.db backup file in resources dir if music.db is corrupted via testing 

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

