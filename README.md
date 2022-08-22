# PR Code Change Description:

![appRunAfter](resources/Montuno_appRunAfter.gif)

### frontend
1. created stylesheets directory with buttons.css & dialogs.css 
2. in Main.java, defined css file paths and added them to scene.getStylesheets()
3. in main.fxml, added fx:id listArtists

### backend
1. In CSS.java, defined CONSTANTS: stylesheets path and file names 

# Functional Impacts:
1. centralize css styles via stylesheets instead of inline style definitions & constants 

# QA Reminders:
1. In strings.SQL file, the variable CONNECTION_PATH must match your local directory path to the music.db file
2. run DatasourceTest first before launching app 
3. run Main.main() to launch application 
4. use DB Browser for SQLite app for database viewing 
5. there is a music_original.db backup file in resources dir if music.db is corrupted via testing 

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

# Kanban Board: 

### TO-DO

US0008-currently, app manually creates an Album for UI because return src model is automatically Artist object and when cast as model is mapped incorrectly to a compatible target dataType

US0009-currently, app requires user to manually change strings.SQL CONNECTION_PATH to match local directory path

US0010-create an addSong backend function to make testing easier for delete functionality

US0011-write out logic for ControllerTest tests

US0012-modularize backend functions by class instead of having them all in Datasource.java

US0013-add a UI playlist that uses a LinkedList 

US0014-add user input capture field 