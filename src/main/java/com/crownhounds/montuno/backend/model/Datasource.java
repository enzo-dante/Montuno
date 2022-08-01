package com.crownhounds.montuno.backend.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Datasource {

    // CONSTANTS/static class variables assigned FINAL value before compilation/instantiation
    private static final String QUERY_FAILED = "Query failed: ";
    private static final String UPDATE_FAILED = "Update failed: ";
    private static final String DELETE_FAILED = "Delete failed: ";
    private static final String CREATE_VIEW_FAILED = "Create View failed: ";
    private static final String QUERY_SONG_INFO_VIEW_FAILED = "querySongInfoView failed: ";
    private static final String INSERT_ARTIST_FAIL = "Couldn't insert artist!";
    private static final String GET_ARTIST_FAIL = "Couldn't get _id for artist!";
    private static final String INSERT_ALBUM_FAIL = "Couldn't insert album!";
    private static final String GET_ALBUM_FAIL = "Couldn't get _id for album!";
    private static final String INSERT_SONG_FAIL = "Couldn't insert song!";
    private static final String ROLLBACK_FAILED = "Rollback failed: ";
    private static final String PERFORMING_ROLLBACK = "Performing rollback";
    private static final String AUTO_COMMIT_FAILED = "Resetting auto-commit failed: ";
    private static final String AUTO_COMMIT_SUCCESS = "Resetting default auto-commit behavior";
    private static final String ALREADY_IN_DB = "Already in database:\n";

    // ? ResultSet columns start at 1 and not 0
    private static final int SQL_START_INDEX = 1;

    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:/Users/enzo_dante/git/Montuno/" + DB_NAME;

    public static final String NO_CONNECTION = "Couldn't connect to database...\n";
    public static final String CLOSE_CONNECTION_FAIL = "Couldn't close() database...\n";

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUMS_ID = "_id";
    public static final String COLUMN_ALBUMS_NAME = "name";
    public static final String COLUMN_ALBUMS_ARTIST = "artist";
    private static final int INDEX_ALBUM_ID = 1;
    private static final int INDEX_ALBUM_NAME = 2;

    public static final String ARTIST_NAME = "Artist Name: ";
    public static final String ARTIST_ID = "Artist ID: ";
    public static final String ALBUM_NAME = "Album Name: ";
    public static final String TRACK_NUMBER = "Track: ";

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTISTS_ID = "_id";
    public static final String COLUMN_ARTISTS_NAME = "name";
    private static final int INDEX_ARTIST_ID = 1;
    private static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONGS_ID = "_id";
    public static final String COLUMN_SONGS_TRACK = "track";
    public static final String COLUMN_SONGS_TITLE = "title";
    public static final String COLUMN_SONGS_ALBUM = "album";

    public static final String SELECT_COLUMN_MIN = "min";
    public static final String SELECT_COLUMN_COUNT = "count";

    // SQL statement query composition
    private static final String ORDER_BY = " ORDER BY ";
    private static final String SELECT_ALL = "SELECT * FROM ";
    private static final String SELECT_COUNT_ALL = "SELECT COUNT(*) AS count, MIN(_id) AS min FROM ";
    private static final String COLLATE_NO_CASE = " COLLATE NOCASE ";
    private static final String ASC = " ASC ";
    private static final String DESC = " DESC ";
    private static final String SELECT = "SELECT ";
    private static final String UPDATE = "UPDATE ";
    private static final String SET = " SET ";
    private static final String INNER_JOIN = " INNER JOIN ";
    private static final String FROM = " FROM ";
    private static final String WHERE = " WHERE ";
    private static final String ON = " ON ";
    private static final String EQUALS = " = ";
    private static final String COMMA = ", ";
    private static final String PERIOD = ".";
    private static final String SEMICOLON = ";";
    private static final String AS = " AS ";
    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String DELETE_FROM = "DELETE FROM ";

    private static final String PLACEHOLDER_QUESTION_MARK = " ?";
    private static final String PLACEHOLDER_VALUES_OPEN = "(";
    private static final String PLACEHOLDER_SINGLE_VALUE_CLOSE = ") VALUES(?)";
    private static final String PLACEHOLDER_DOUBLE_VALUE_CLOSE = ") VALUES(?, ?)";
    private static final String PLACEHOLDER_TRIPLE_VALUE_CLOSE = ") VALUES(?, ?, ?)";

    public static final String TABLE_ARTIST_LIST_VIEW = "artist_list";
    public static final String CREATE_VIEW_IF_NOT_EXISTS = "CREATE VIEW IF NOT EXISTS ";
    private static final String VIEW_AS_SELECT = " AS SELECT ";

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    public static final String QUERY_ALBUMS_BY_ARTIST_START =
            SELECT + TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_NAME +
                    FROM + TABLE_ALBUMS +
                    INNER_JOIN + TABLE_ARTISTS +
                    ON + TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_ARTIST + EQUALS + TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_ID +
                    WHERE + TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_NAME + EQUALS;

    public static final String QUERY_ALBUMS_BY_ARTIST_SORT =
            ORDER_BY + TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_NAME + COLLATE_NO_CASE;

    public static final String QUERY_ARTISTS_FOR_SONGS_START =
            SELECT + TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_NAME + COMMA +
                    TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_NAME + COMMA +
                    TABLE_SONGS + PERIOD + COLUMN_SONGS_TRACK +
                    FROM + TABLE_SONGS +
                    INNER_JOIN + TABLE_ALBUMS +
                    ON + TABLE_SONGS + PERIOD + COLUMN_SONGS_ALBUM + EQUALS + TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_ID +
                    INNER_JOIN + TABLE_ARTISTS +
                    ON + TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_ARTIST + EQUALS + TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_ID +
                    WHERE + TABLE_SONGS + PERIOD + COLUMN_SONGS_TITLE + EQUALS;

    public static final String QUERY_ARTISTS_FOR_SONGS_SORT =
            ORDER_BY + TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_NAME + COMMA + TABLE_SONGS + PERIOD + COLUMN_SONGS_TITLE + COLLATE_NO_CASE;

    public static final String CREATE_ARTIST_FOR_SONG_VIEW =
            CREATE_VIEW_IF_NOT_EXISTS + TABLE_ARTIST_LIST_VIEW + VIEW_AS_SELECT +
                    TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_NAME + COMMA +
                    TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_NAME + AS + COLUMN_SONGS_ALBUM + COMMA +
                    TABLE_SONGS + PERIOD + COLUMN_SONGS_TRACK + COMMA +
                    TABLE_SONGS + PERIOD + COLUMN_SONGS_TITLE +
                    FROM + TABLE_SONGS +
                    INNER_JOIN + TABLE_ALBUMS +
                    ON + TABLE_SONGS + PERIOD + COLUMN_SONGS_ALBUM + EQUALS + TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_ID +
                    INNER_JOIN + TABLE_ARTISTS +
                    ON + TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_ARTIST + EQUALS + TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_ID +
                    ORDER_BY + TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_NAME + COMMA +
                    TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_NAME + COMMA +
                    TABLE_SONGS + PERIOD + COLUMN_SONGS_TRACK + SEMICOLON;

    public static final String QUERY_VIEW_SONG_INFO =
            SELECT +
                    COLUMN_ARTISTS_NAME + COMMA +
                    COLUMN_SONGS_ALBUM + COMMA +
                    COLUMN_SONGS_TRACK +
                    FROM + TABLE_ARTIST_LIST_VIEW +
                    WHERE + COLUMN_SONGS_TITLE + EQUALS;

    // ? = placeholder for title
    // SQL statement: SELECT name, album, track FROM artist_list WHERE title = ?

    public static final String QUERY_SONG_INFO_VIEW_PREP =
            SELECT +
                    COLUMN_ARTISTS_NAME + COMMA +
                    COLUMN_SONGS_ALBUM + COMMA +
                    COLUMN_SONGS_TRACK +
                    FROM + TABLE_ARTIST_LIST_VIEW +
                    WHERE + COLUMN_SONGS_TITLE + EQUALS + PLACEHOLDER_QUESTION_MARK;

    public static final String INSERT_INTO_ARTISTS =
            INSERT_INTO + TABLE_ARTISTS +
                    PLACEHOLDER_VALUES_OPEN + COLUMN_ARTISTS_NAME + PLACEHOLDER_SINGLE_VALUE_CLOSE;

    public static final String INSERT_INTO_ALBUMS =
            INSERT_INTO + TABLE_ALBUMS +
                    PLACEHOLDER_VALUES_OPEN + COLUMN_ALBUMS_NAME + COMMA + COLUMN_ALBUMS_ARTIST + PLACEHOLDER_DOUBLE_VALUE_CLOSE;

    public static final String INSERT_INTO_SONGS =
            INSERT_INTO + TABLE_SONGS +
                    PLACEHOLDER_VALUES_OPEN + COLUMN_SONGS_TRACK + COMMA + COLUMN_SONGS_TITLE + COMMA + COLUMN_SONGS_ALBUM + PLACEHOLDER_TRIPLE_VALUE_CLOSE;

    public static final String QUERY_ARTISTS =
            SELECT + COLUMN_ARTISTS_ID +
                    FROM + TABLE_ARTISTS +
                    WHERE + COLUMN_ARTISTS_NAME + EQUALS + PLACEHOLDER_QUESTION_MARK;

    public static final String QUERY_ALBUMS =
            SELECT + COLUMN_ALBUMS_ID +
                    FROM + TABLE_ALBUMS +
                    WHERE + COLUMN_ALBUMS_NAME + EQUALS + PLACEHOLDER_QUESTION_MARK;

    public static final String QUERY_SONGS =
            SELECT + COLUMN_SONGS_ID +
                    FROM + TABLE_SONGS +
                    WHERE + COLUMN_SONGS_TITLE + EQUALS + PLACEHOLDER_QUESTION_MARK;

    public static final String QUERY_ALBUMS_BY_ARTIST_ID =
            SELECT_ALL + TABLE_ALBUMS +
                    WHERE + COLUMN_ALBUMS_ARTIST + EQUALS + PLACEHOLDER_QUESTION_MARK + ORDER_BY + COLUMN_ALBUMS_NAME + COLLATE_NO_CASE;

    public static final String UPDATE_ARTIST_NAME =
            UPDATE + TABLE_ARTISTS +
                    SET + COLUMN_ARTISTS_NAME + EQUALS + PLACEHOLDER_QUESTION_MARK +
                    WHERE + COLUMN_ARTISTS_ID + EQUALS + PLACEHOLDER_QUESTION_MARK;

    public static final String DELETE_FROM_SONGS =
            DELETE_FROM + TABLE_SONGS +
                    WHERE + COLUMN_SONGS_ID + EQUALS + PLACEHOLDER_QUESTION_MARK;

    // OOP ENCAPSULATION private class variables
    private Connection connection;

    /*
        instance variable for PreparedStatement that is only pre-compiled only once
        helpful for performance and protecting against SQL Injection Attacks
        PreparedStatement is a subclass of Statement
     */
    private PreparedStatement queryArtistListView;
    private PreparedStatement querySongInfoView;
    private PreparedStatement queryArtists;
    private PreparedStatement queryAlbums;
    private PreparedStatement querySongs;
    private PreparedStatement queryAlbumsByArtistId;

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
            querySongInfoView = connection.prepareStatement(QUERY_SONG_INFO_VIEW_PREP);
            queryArtists = connection.prepareStatement(QUERY_ARTISTS);
            queryAlbums = connection.prepareStatement(QUERY_ALBUMS);
            querySongs = connection.prepareStatement(QUERY_SONGS);
            queryAlbumsByArtistId = connection.prepareStatement(QUERY_ALBUMS_BY_ARTIST_ID);

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
            if (queryArtistListView != null) {
                queryArtistListView.close();
            }

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

            if (queryArtists != null) {
                queryArtists.close();
            }

            if (queryAlbums != null) {
                queryAlbums.close();
            }

            if (querySongs != null) {
                querySongs.close();
            }

            if (queryAlbumsByArtistId != null) {
                queryAlbumsByArtistId.close();
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
                    TRACK_NUMBER + artist.getTrack());
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
            System.out.println(Datasource.ARTIST_NAME + artist.getName() + "\n" +
                    Datasource.ARTIST_ID + artist.get_id() + "\n");
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

            // ! INTERFACE: an abstract collection of public signatures that designated classes MUST uniquely implement/@Override for standardization
            // ! GENERICS: improve OOP ENCAPSULATION by creating classes, interfaces, & methods that only take a specific dataType parameter
            List<SongArtist> songArtists = new ArrayList<>();

            while (resultSet.next()) {

                SongArtist songArtist = new SongArtist();

                songArtist.setArtistName(resultSet.getString(1));
                songArtist.setAlbumName(resultSet.getString(2));
                songArtist.setTrack(resultSet.getInt(3));

                songArtists.add(songArtist);
            }
            return songArtists;

        } catch (SQLException e) {
            System.out.println(QUERY_FAILED + e.getMessage());
            return null;
        }
    }

    /**
     * ! OVERLOADED METHOD: same name method w/ unique parameters that optimize readability & scalability
     * get list of songArtist
     *
     * @param preparedStatement
     * @param title
     * @return list of songArtists
     */
    public List<SongArtist> buildSongArtists(PreparedStatement preparedStatement, String title) {

        try {
            // JDBC list starts at 1 not 0
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            // ! INTERFACE: an abstract collection of public signatures that designated classes MUST uniquely implement/@Override for standardization
            // ! GENERICS: improve OOP ENCAPSULATION by creating classes, interfaces, & methods that only take a specific dataType parameter
            List<SongArtist> songArtists = new ArrayList<>();

            while (resultSet.next()) {

                SongArtist songArtist = new SongArtist();

                songArtist.setArtistName(resultSet.getString(1));
                songArtist.setAlbumName(resultSet.getString(2));
                songArtist.setTrack(resultSet.getInt(3));

                songArtists.add(songArtist);
            }
            return songArtists;

        } catch (SQLException e) {
            System.out.println(QUERY_FAILED + e.getMessage());
            return null;
        }
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

        StringBuilder stringBuilder = new StringBuilder(SELECT_ALL);
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
    public List<SongArtist> queryArtistForSong(String songName, int sortOrder) {
        StringBuilder sb = new StringBuilder(QUERY_ARTISTS_FOR_SONGS_START);
        sb.append(formatField(songName));

        System.out.println("queryArtistForSong SQL:\n" + sb.toString());

        List<SongArtist> results = buildSongArtists(sb);

        if (results != null && !results.isEmpty()) {
            return results;
        }
        return null;
    }

    /**
     * get all SQL song table data
     *
     * @return if SQL SELECT query was successful
     */
    public boolean querySongsMetadata() {
        String sql = SELECT_ALL + TABLE_SONGS;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            ResultSetMetaData metadata = resultSet.getMetaData();
            int numColumns = metadata.getColumnCount();

            for (int i = SQL_START_INDEX; i <= numColumns; i++) {
                System.out.format(
                        "Column %d in the songs table is names %s\n",
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
    public boolean createViewForSongArtists() {

        try (Statement statement = connection.createStatement()) {

            // use .execute(sql.toString()) instead of .executeQuery(sql.toString()) given no return values
            statement.execute(CREATE_ARTIST_FOR_SONG_VIEW);

            return true;

        } catch (SQLException e) {
            System.out.println(CREATE_VIEW_FAILED + e.getMessage());
            return false;
        }
    }

    /**
     * get list of songArtists via artist_list view
     *
     * @return if SQL SELECT query was successful
     */
    public List<SongArtist> querySongInfoView(String title) {

        // ! GENERICS: improve OOP ENCAPSULATION by creating classes, interfaces, & methods that only take a specific dataType parameter
        List<SongArtist> results = buildSongArtists(querySongInfoView, title);

        if (results != null && !results.isEmpty()) {
            return results;
        }

        System.out.println(QUERY_SONG_INFO_VIEW_FAILED);
        return null;
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
}