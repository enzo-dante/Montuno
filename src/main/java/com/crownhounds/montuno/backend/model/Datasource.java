package com.crownhounds.montuno.backend.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static com.crownhounds.montuno.strings.SQL.*;
import static com.crownhounds.montuno.strings.Errors.*;

public class Datasource {

    // OOP ENCAPSULATION private class variables
    private Connection connection;

    /*
        instance variable for PreparedStatement that is only pre-compiled only once
        helpful for performance and protecting against SQL Injection Attacks
        PreparedStatement is a subclass of Statement
     */
    private PreparedStatement queryArtistListView;
    private PreparedStatement queryArtistListViewByTitle;
    private PreparedStatement queryArtists;
    private PreparedStatement queryAlbums;
    private PreparedStatement querySongs;
    private PreparedStatement queryAlbumsByArtistId;
    private PreparedStatement querySongsByAlbumId;

    private PreparedStatement insertIntoArtists;
    private PreparedStatement insertIntoAlbums;
    private PreparedStatement insertIntoSongs;

    private PreparedStatement updateArtistName;

    private PreparedStatement deleteFromSongs;

    // lazy instantiation: only create instance using singleton pattern when instance is needed
    private static Datasource datasourceInstance = new Datasource();

    // OOP constructor that initializes the class field on class/object instantiation
    private Datasource() {}

    /*
        singleton pattern: ensures that only one object of its kind exists and provides a single point of access to it for any other code
        validate only 1 instance of DataSource class is being used and thread safe if multiple threads are running
     */
    public static Datasource getDatasourceInstance() {
        return datasourceInstance;
    }

    // OOP CLASS METHODS: unique object behavior
    public boolean open() {

        // ! EXCEPTION HANDLING: EASY TO ASK FOR FORGIVENESS THAN PERMISSION (EAFTP) = use try-catch block
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);

            /*
                ? JDBC: preparedStatement(SQL_PREPARED_STATEMENT)
                    great for performance instead of creating a new instance on every query

                queryArtistListView = connection.prepareStatement(QUERY_ARTIST_LIST_PREPARED_STATEMENT);
             */
            queryArtistListView = connection.prepareStatement(QUERY_ARTIST_LIST_VIEW);
            queryArtistListViewByTitle = connection.prepareStatement(QUERY_ARTIST_LIST_VIEW_BY_TITLE);

            queryArtists = connection.prepareStatement(QUERY_ARTISTS);
            queryAlbums = connection.prepareStatement(QUERY_ALBUMS);
            querySongs = connection.prepareStatement(QUERY_SONGS);
            queryAlbumsByArtistId = connection.prepareStatement(QUERY_ALBUMS_BY_ARTIST_ID);
            querySongsByAlbumId = connection.prepareStatement(QUERY_SONGS_BY_ALBUM_ID);

            // ? JDBC: Statement.RETURN_GENERATED_KEYS = need ids to pass into subsequent insert statement until committing to songs table
            insertIntoArtists = connection.prepareStatement(INSERT_INTO_ARTISTS, Statement.RETURN_GENERATED_KEYS);
            insertIntoAlbums = connection.prepareStatement(INSERT_INTO_ALBUMS, Statement.RETURN_GENERATED_KEYS);
            insertIntoSongs = connection.prepareStatement(INSERT_INTO_SONGS);

            updateArtistName = connection.prepareStatement(UPDATE_ARTIST_NAME);

            deleteFromSongs = connection.prepareStatement(DELETE_FROM_SONGS);

            return true;

        } catch (SQLException e) {
            System.out.println(NO_CONNECTION + e.getMessage());
            return false;
        }
    }

    /**
     * close resources before connection
     * @return
     */
    public boolean close() {

        // ! EXCEPTION HANDLING: EASY TO ASK FOR FORGIVENESS THAN PERMISSION (EAFTP) = use try-catch block
        try {

            if (insertIntoArtists != null) {
                insertIntoArtists.close();
            }

            if (insertIntoAlbums != null) {
                insertIntoAlbums.close();
            }

            if (insertIntoSongs != null) {
                insertIntoSongs.close();
            }

            if (deleteFromSongs != null) {
                deleteFromSongs.close();
            }

            if (queryArtistListView != null) {
                queryArtistListView.close();
            }

            if (queryArtistListViewByTitle != null) {
                queryArtistListViewByTitle.close();
            }

            if (queryArtists != null) {
                queryArtists.close();
            }

            if (queryAlbums != null) {
                queryAlbums.close();
            }

            if (queryAlbumsByArtistId != null) {
                queryAlbumsByArtistId.close();
            }

            if (querySongsByAlbumId != null) {
                querySongsByAlbumId.close();
            }

            if (querySongs != null) {
                querySongs.close();
            }

            if (updateArtistName != null) {
                updateArtistName.close();
            }

            if (connection != null) {
                connection.close();
            }

            return true;

        } catch (SQLException e) {
            System.out.println(CLOSE_CONNECTION_FAIL + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static StringBuilder handleSort(StringBuilder stringBuilder, int sortOrder) {

        if (sortOrder != ORDER_BY_NONE) {

            stringBuilder.append(ORDER_BY);
            stringBuilder.append(COLUMN_ARTISTS_NAME);
            stringBuilder.append(COLLATE_NO_CASE);

            if (sortOrder == ORDER_BY_DESC) {
                stringBuilder.append(DESC);
            } else if (sortOrder == ORDER_BY_ASC) {
                stringBuilder.append(ASC);
            } else {
                return null;
            }
        }
        return stringBuilder;
    }

    public static StringBuilder handleSort(StringBuilder stringBuilder, int sortOrder, String querySort) {

        if (sortOrder != ORDER_BY_NONE) {

            if (querySort.equals(QUERY_ALBUMS_BY_ARTIST_SORT)) {

                stringBuilder.append(QUERY_ALBUMS_BY_ARTIST_SORT);

            } else if (querySort.equals(QUERY_ARTISTS_FOR_SONGS_SORT)) {

                stringBuilder.append(QUERY_ARTISTS_FOR_SONGS_SORT);
            }

            if (sortOrder == ORDER_BY_DESC) {

                stringBuilder.append(DESC);

            } else if (sortOrder == ORDER_BY_ASC) {

                stringBuilder.append(ASC);

            } else {
                return null;
            }
        }
        return stringBuilder;
    }

    /**
     * print to console list of songArtists
     *
     * @param songArtists
     */
    public static void printSongArtists(List<SongArtist> songArtists) {

        for (SongArtist artist : songArtists) {
            System.out.println(ARTIST_NAME + artist.getArtistName() + "\n" +
                    ALBUM_NAME + artist.getAlbumName() + "\n" +
                    TRACK_NUMBER + artist.getTrack() + "\n" +
                    SONG_TITLE + artist.getTrack() + "\n" +
                    SONG_ID + artist.getSong_id());
        }
    }

    /**
     * Force app to wait 2 seconds to test the progress bar
     */
    public boolean testProgressBar() {
        try {
            Thread.sleep(10);
            return true;
        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * print to console list of artists
     *
     * @param artists
     */
    public static void printArtists(List<Artist> artists) {
        for (Artist artist : artists) {
            System.out.println(ARTIST_NAME + artist.getName() + "\n" +
                    ARTIST_ID + artist.get_id() + "\n");
        }
    }

    /**
     * prepare the given field for a SQL query
     *
     * @param field
     * @return the formatted field
     */
    public String formatField(String field) {
        return "\"" + field + "\"";
    }

    /**
     * get the count of all records in a given table
     *
     * @param table
     * @return all records count
     */
    public int getCount(String table) {

        String sql = SELECT_COUNT_ALL + table;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql.toString())) {

            int count = resultSet.getInt(SELECT_COLUMN_COUNT);
            int min = resultSet.getInt(SELECT_COLUMN_MIN);

            return count;

        } catch (SQLException e) {
            System.out.println(QUERY_FAILED + e.getMessage());
            return -1;
        }
    }

    /**
     * execute & check status of SQL update()
     *
     * @param preparedStatement
     * @param msg
     * @param connection
     * @throws SQLException
     */
    private void validateDatabaseUpdate(PreparedStatement preparedStatement, String msg, Connection connection) throws SQLException {

        int affectedRows = preparedStatement.executeUpdate();
        boolean noRowsUpdated = affectedRows != 1;

        if (noRowsUpdated) {
            throw new SQLException(msg);
        } else if (msg.equals(INSERT_SONG_FAIL)) {

            // execute update to database with custom SQL PreparedStatement
            connection.commit();
        }
    }

    /**
     * ! OVERLOADED METHOD: same name method w/ unique parameters that optimize readability & scalability
     * get list of songArtist
     *
     * @param sb
     * @return list of songArtists
     */
    public List<SongArtist> buildSongArtists(StringBuilder sb) {

        // ? JAVA_FX: create new instance of connection statement to maintain query data integrity during asynchronous calls
        try (Statement statement = connection.createStatement();
             // ? JAVA_FX: executeQuery with built SQL toString
             ResultSet resultSet = statement.executeQuery(sb.toString())) {

            return buildSongArtists(resultSet);

        } catch (SQLException e) {
            System.out.println(QUERY_FAILED + e.getMessage());
            return null;
        }
    }

    /**
     * ! OVERLOADED METHOD: same name method w/ unique parameters that optimize readability & scalability
     * get list of songArtist
     *
     * @param queryArtistListView the SQL for the artist_list view
     * @param title song title
     * @return list of songArtists
     */
    public List<SongArtist> buildSongArtists(PreparedStatement queryArtistListView, String title) {

        try {
            // JDBC list starts at 1 not 0
            queryArtistListView.setString(1, title);
            ResultSet resultSet = queryArtistListView.executeQuery();

            return buildSongArtists(resultSet);

        } catch (SQLException e) {
            System.out.println(QUERY_FAILED + e.getMessage());
            return null;
        }
    }

    /**
     * ! OVERLOADED METHOD: same name method w/ unique parameters that optimize readability & scalability
     * get list of songArtist
     *
     * @param resultSet SQL query results
     * @return list of songArtists
     */
    private List<SongArtist> buildSongArtists(ResultSet resultSet) throws SQLException {

        // ! INTERFACE: an abstract collection of public signatures that designated classes MUST uniquely implement/@Override for standardization
        // ! GENERICS: improve OOP ENCAPSULATION by creating classes, interfaces, & methods that only take a specific dataType parameter
        List<SongArtist> songArtists = new ArrayList<>();

        while (resultSet.next()) {

            SongArtist songArtist = new SongArtist();

            songArtist.setArtistName(resultSet.getString(INDEX_ARTIST_LIST_ARTIST_NAME));
            songArtist.setAlbumName(resultSet.getString(INDEX_ARTIST_LIST_ALBUM_NAME));
            songArtist.setTrack(resultSet.getInt(INDEX_ARTIST_LIST_SONG_TRACK));
            songArtist.setSongTitle(resultSet.getString(INDEX_ARTIST_LIST_SONG_TITLE));
            songArtist.setSong_id(resultSet.getInt(INDEX_ARTIST_LIST_SONG_ID));

            songArtists.add(songArtist);
        }
        return songArtists;
    }

    /**
     * validate that built SQL statement is a valid StringBuilder object
     *
     * @param sb
     * @return given stringBuilder validity
     */
    public boolean validateDataStructure(StringBuilder sb) {
        return sb != null && !sb.isEmpty();
    }

    /**
     * get list of arist
     *
     * @param sortOrder
     * @return list of artists
     */
    public List<Artist> queryArtist(int sortOrder) {

        StringBuilder stringBuilder = new StringBuilder(SELECT_ALL_FROM);
        stringBuilder.append(TABLE_ARTISTS);

        stringBuilder = handleSort(stringBuilder, sortOrder);

        if (!validateDataStructure(stringBuilder)) {
            return null;
        }

        // ? JAVA_FX: create new instance of connection statement to maintain query data integrity during asynchronous calls
        try (Statement statement = connection.createStatement();
             // ? JAVA_FX: executeQuery with built SQL toString
             ResultSet results = statement.executeQuery(stringBuilder.toString())
        ) {

            // ! INTERFACE: an abstract collection of public signatures that designated classes MUST uniquely implement/@Override for standardization
            // ! GENERICS: improve OOP ENCAPSULATION by creating classes, interfaces, & methods that only take a specific dataType parameter
            List<Artist> artists = new ArrayList<Artist>();

            // loop for each SQL row, build an Artist object & populate artists ArrayList
            while (results.next()) {

                testProgressBar();

                Artist artist = new Artist();

                artist.set_id(results.getInt(INDEX_ARTIST_ID));
                artist.setName(results.getString(INDEX_ARTIST_NAME));

                artists.add(artist);
            }
            return artists;

        } catch (SQLException e) {

            // ! EXCEPTION HANDLING: EASY TO ASK FOR FORGIVENESS THAN PERMISSION (EAFTP) = use try-catch block
            System.out.println(QUERY_FAILED + e.getMessage());
            return null;
        }
    }

    /**
     * get list of albums by given arist
     *
     * @param sortOrder
     * @return list of albums
     */
    public List<String> queryAlbumsForArtist(String artistName, int sortOrder) {

        StringBuilder stringBuilder = new StringBuilder(QUERY_ALBUMS_BY_ARTIST_START);
        stringBuilder.append(formatField(artistName));

        stringBuilder = handleSort(stringBuilder, sortOrder, QUERY_ALBUMS_BY_ARTIST_SORT);

        if (!validateDataStructure(stringBuilder)) {
            return null;
        }

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(stringBuilder.toString())
        ) {

            List<String> albums = new ArrayList<>();

            while (resultSet.next()) {
                albums.add(resultSet.getString(1));
            }

            return albums;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * get list of albums by an artist via SQL query to database
     * @param artistId
     * @return list of albums
     */
    public List<Album> queryAlbumsForArtistId(int artistId) {

        try {
            queryAlbumsByArtistId.setInt(1, artistId);
            ResultSet resultSet = queryAlbumsByArtistId.executeQuery();

            List<Album> albums = new ArrayList<>();

            while(resultSet.next()) {
                Album album = new Album();
                album.setArtistId(resultSet.getInt(INDEX_ALBUM_ID));
                album.setName(resultSet.getString(INDEX_ALBUM_NAME));
                album.set_id(artistId);
                albums.add(album);
            }

            return albums;
        } catch(Exception e) {
            System.out.println(QUERY_FAILED + e.getMessage());
            return null;
        }
    }

    /**
     * get song info via the SQL artist table
     *
     * @param songName
     * @param sortOrder
     * @return
     */
    public List<SongArtist> queryArtistsForSong(String songName, int sortOrder) {

        if(sortOrder == ORDER_BY_NONE || sortOrder == ORDER_BY_ASC || sortOrder == ORDER_BY_DESC) {
            StringBuilder sb = new StringBuilder(QUERY_ARTISTS_FOR_SONGS_START);
            sb.append(formatField(songName));

            System.out.println(QUERY_ARTIST_FOR_SONG_SQL + sb.toString());

            List<SongArtist> results = buildSongArtists(sb);

            if (results != null && !results.isEmpty()) {
                return results;
            }
        }
        return null;
    }

    /**
     * get all SQL song table data
     *
     * @return if SQL SELECT query was successful
     */
    public boolean querySongsMetadata() {
        String sql = SELECT_ALL_FROM + TABLE_SONGS;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            ResultSetMetaData metadata = resultSet.getMetaData();
            int numColumns = metadata.getColumnCount();

            System.out.println(QUERY_SONGS_METADATA);

            for (int i = SQL_START_INDEX; i <= numColumns; i++) {
                System.out.format(
                        META_DATA_FORMAT,
                        i,
                        metadata.getColumnName(i)
                );
            }
            return true;
        } catch (SQLException e) {
            System.out.println(QUERY_FAILED + e.getMessage());
            return false;
        }
    }

    /**
     * create view artist_list
     *
     * @return if SQL CREATE VIEW query was successful
     */
    public static boolean createArtistListViewForSongArtists() {

        try (Statement statement = DriverManager.getConnection(CONNECTION_STRING).createStatement()) {

            // use .execute(sql.toString()) instead of .executeQuery(sql.toString()) given no return values
            statement.execute(CREATE_ARTIST_LIST_VIEW);
            statement.close();

            System.out.println(CREATED_VIEW_ARTIST_LIST);
            return true;

        } catch (SQLException e) {
            System.out.println(CREATE_VIEW_FAILED + e.getMessage());
            return false;
        }
    }

    /**
     * create table playlists
     *
     * @return if SQL CREATE TABLE query was successful
     */
    public static boolean createPlaylistsTable() {

        try(Statement statement = DriverManager.getConnection(CONNECTION_STRING).createStatement()) {

            statement.execute(CREATE_TABLE_PLAYLISTS);
            statement.close();

            System.out.println(CREATE_TABLE_PLAYLISTS);
            return true;

        } catch(SQLException e) {
            System.out.println(CREATE_TABLE_FAILED + e.getMessage());
            return false;
        }
    }
     /**
     * drop view artist_list from database
     * @return SQL query success state
     */
    public static boolean dropPlaylistsTable() {

        // ! EXCEPTION HANDLING easier to ask for forgiveness than permission: use try-catch block to handle exceptions
        try (Statement statement = DriverManager.getConnection(CONNECTION_STRING).createStatement()) {

            statement.execute(DROP_TABLE_PLAYLISTS);
            statement.close();

            System.out.println(DROP_TABLE_PLAYLISTS);
            return true;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * drop view artist_list from database
     * @return SQL query success state
     */
    public static boolean dropArtistListView() {

        try (Statement statement = DriverManager.getConnection(CONNECTION_STRING).createStatement()) {

            // use .execute(sql.toString()) instead of .executeQuery(sql.toString()) given no return values
            statement.execute(DROP_ARTIST_LIST_VIEW);
            statement.close();

            System.out.println(DROPPED_VIEW_ARTIST_LIST);
            return true;

        } catch (SQLException e) {
            System.out.println(DELETE_FAILED + e.getMessage());
            return false;
        }
    }

    /**
     * get list of songArtists via artist_list view
     *
     * @return if SQL SELECT query was successful
     */
    public List<SongArtist> queryArtistListViewByTitle(String title) {

        // ! GENERICS: improve OOP ENCAPSULATION by creating classes, interfaces, & methods that only take a specific dataType parameter
        List<SongArtist> results = buildSongArtists(queryArtistListViewByTitle, title);

        if (results != null && !results.isEmpty()) {
            return results;
        }

        System.out.println(QUERY_ARTIST_LIST_VIEW_FAILED);
        return null;
    }

    public List<Song> querySongsByAlbumId(int albumId) {

        if(albumId < 0) {
            return null;
        }

        try {
            List<Song> songs = new ArrayList<>();

            querySongsByAlbumId.setInt(1, albumId);
            ResultSet resultSet = querySongsByAlbumId.executeQuery();

            while(resultSet.next()) {

                Song song = new Song();

                song.set_id(resultSet.getInt(1));
                song.setTrack(resultSet.getInt(2));
                song.setName(resultSet.getString(3));
                song.setAlbumId(resultSet.getInt(4));

                songs.add(song);
            }

            if(songs.isEmpty()) {
                return null;
            }

            return songs;

        } catch (SQLException e) {
            System.out.println(QUERY_FAILED + e.getMessage());
            return null;
        }
    }

    /**
     * ? THROW EXCEPTION: initiate specific exception with provided error msg
     * <p>
     * insert new artist with many-to-many SQL join data to SQLite3 database
     *
     * @param name
     * @return
     * @throws SQLException
     */
    private int insertIntoArtists(String name) throws SQLException {

        // instance variable for PreparedStatement that is only pre-compiled only once
        queryArtists.setString(1, name);

        ResultSet resultSet = queryArtists.executeQuery();

        // artist already in database, return existing artist _id
        if (resultSet.next()) {
            return resultSet.getInt(INDEX_ARTIST_ID);
        } else {

            // insert new artist
            insertIntoArtists.setString(1, name);

            validateDatabaseUpdate(insertIntoArtists, INSERT_ARTIST_FAIL, connection);

            ResultSet generatedKeys = insertIntoArtists.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(INDEX_ARTIST_ID);
            } else {
                throw new SQLException(GET_ARTIST_FAIL);
            }
        }
    }

    /**
     * ? THROW EXCEPTION: initiate specific exception with provided error msg
     * <p>
     * insert new album with many-to-many SQL join data to SQLite3 database
     *
     * @param title
     * @param artistId
     * @return
     * @throws SQLException
     */
    private int insertIntoAlbums(String title, int artistId) throws SQLException {

        // instance variable for PreparedStatement that is only pre-compiled only once
        queryAlbums.setString(1, title);

        ResultSet resultSet = queryAlbums.executeQuery();

        // album already in database, return album _id
        if (resultSet.next()) {
            return resultSet.getInt(INDEX_ALBUM_ID);
        } else {

            // insert new album
            insertIntoAlbums.setString(1, title);
            insertIntoAlbums.setInt(2, artistId);

            validateDatabaseUpdate(insertIntoAlbums, INSERT_ALBUM_FAIL, connection);

            ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(INDEX_ALBUM_ID);
            } else {
                throw new SQLException(GET_ALBUM_FAIL);
            }
        }
    }

    /**
     * insert new song with many-to-many SQL join data to SQLite3 database
     *
     * @param title
     * @param artist
     * @param album
     * @param track
     * @return status of SQL insert()
     */
    public boolean insertIntoSongs(String title, String artist, String album, int track) {

        /*
            ? TRANSACTIONS
            use transactions when we want to perform a series of SQL statements as a unit,
            so that the changes are only committed to the database if all the statements run successfully.

            If a statement fails or something else happens,
                perhaps the lost connection or the database server goes down in the middle of performing a transaction,
                then we won't end up with a database in an invalid state.

            We'll be able to roll back any changes that have been made or if the connection goes down for some reason,
                the database will automatically roll back any changes and end the transaction.
         */
        boolean insertedSong = false;

        try {
            querySongs.setString(1, title);

            ResultSet resultSet = querySongs.executeQuery();

            if (resultSet.next()) {

                System.out.println(INSERT_SONG_FAIL);
                System.out.println(ALREADY_IN_DB + "\t" + ARTIST_NAME + artist + "\n\t" + TRACK_NUMBER + title);

                return insertedSong;
            }
        } catch (Exception e) {
            System.out.println(QUERY_FAILED + e.getMessage());
            return insertedSong;
        }

        try {
            // ? JDBC: disable default jdbc behavior to prepare for custom transaction
            connection.setAutoCommit(false);

            // get variables for PreparedStatement
            int artistId = insertIntoArtists(artist);
            int albumId = insertIntoAlbums(album, artistId);

            // set instance variables for PreparedStatement that is only pre-compiled only once
            insertIntoSongs.setInt(1, track);
            insertIntoSongs.setString(2, title);
            insertIntoSongs.setInt(3, albumId);

            // execute update to database with SQL PreparedStatement
            validateDatabaseUpdate(insertIntoSongs, INSERT_SONG_FAIL, connection);

            insertedSong = true;

        } catch (Exception e) {

            System.out.println(INSERT_SONG_FAIL + e.getMessage());

            try {
                System.out.println(PERFORMING_ROLLBACK);
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(ROLLBACK_FAILED + e.getMessage());
            }

        } finally {
            try {
                System.out.println(AUTO_COMMIT_SUCCESS);
                connection.setAutoCommit(true);
                return insertedSong;
            } catch (SQLException e) {
                System.out.println(AUTO_COMMIT_FAILED + e.getMessage());
                return insertedSong;
            }
        }
    }

    /**
     * update artist name by SongArtist song_id from database after validating data first from artist_list view
     * @param artistId
     * @param updateName
     * @return
     */
    public boolean updateArtistName(int artistId, String updateName) {

        if(artistId < 0 || updateName.isEmpty()) {
            return false;
        }

        try {
            updateArtistName.setString(1, updateName);
            updateArtistName.setInt(2, artistId);

            int updatedRecords = updateArtistName.executeUpdate();

            return updatedRecords == 1;

        } catch(SQLException e) {
            System.out.println(UPDATE_FAILED + e.getMessage());
            return false;
        }
    }

    /**
     * delete song by SongArtist song_id from database after validating data first from artist_list view
     * @param song
     * @param artist
     * @return SQL query success state
     */
    public boolean deleteSong(String song, String artist) {

        if(song.isEmpty() || artist.isEmpty()) {
            System.out.println(DELETE_FAILED);
            return false;
        }

        try {
            queryArtistListView.setString(1, song.trim());
            queryArtistListView.setString(2, artist.trim());

            ResultSet resultSet = queryArtistListView.executeQuery();

            // ! GENERICS: improve ENCAPSULATION by enforcing element dataType
            List<SongArtist> songArtists = buildSongArtists(resultSet);

            // ! EXCEPTION HANDLING: LOOK BEFORE YOU LEAP (LBYL) = use conditional if-else block
            if(songArtists.size() == 1) {

                // ! EXCEPTION HANDLING: EASY TO ASK FOR FORGIVENESS THAN PERMISSION (EAFTP) = use try-catch block
                try {
                    SongArtist songArtist = songArtists.get(0);

                    deleteFromSongs.setInt(1, songArtist.getSong_id());
                    int deletedRows = deleteFromSongs.executeUpdate();

                    if(deletedRows == 1) {
                        System.out.println(DELETED_SONG_ID + songArtist.getSong_id() + DELETED_FOR + songArtist.toString());
                        return true;
                    }

                } catch(SQLException e) {
                    System.out.println(DELETE_FAILED + e.getMessage());
                    return false;
                }
            }
            System.out.println(DELETE_FAILED_SIZE);
            return false;

        } catch(SQLException e) {
            System.out.println(QUERY_FAILED + e.getMessage());
        }
        return false;
    }
}