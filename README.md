# PR Code Change Description:

![appRunAfter](resources/Montuno_appRunAfter.gif)

### frontend
1. in controller on the frontend, changed CONSTANT name from UPDATED_ARTIST_NAME to UPDATED_AC_DC_NAME to avoid incorrect references
2. added FXML tags & method signatures for deleteSongBtn and listSongsForAlbum

### backend
1. n/a 

# Functional Impacts:
1. UI update button change to "AC DC" will preserve on the UI after window changes and in the DB

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
![appRunBefore](resources/Montuno_knownBug.gif)

# After:
![appRunAfter](resources/Montuno_appRunAfter.gif)

