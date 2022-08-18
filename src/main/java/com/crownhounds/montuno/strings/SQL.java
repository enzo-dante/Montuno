package com.crownhounds.montuno.strings;

public class SQL {

    // CONSTANTS/static class variables assigned FINAL value before compilation/instantiation
    public static final String DB_NAME = "music.db";
    private static final String JDBC_SQLITE = "jdbc:sqlite:";
    private static final String CONNECTION_PATH = "/Users/enzo_dante/git/Montuno/";

    public static final String CONNECTION_STRING = JDBC_SQLITE + CONNECTION_PATH + DB_NAME;

    // ? ResultSet columns start at 1 and not 0
    public static final int SQL_START_INDEX = 1;
    public static final String META_DATA_FORMAT = "Column %d in the songs table is names %s\n";

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUMS_ID = "_id";
    public static final String COLUMN_ALBUMS_NAME = "name";
    public static final String COLUMN_ALBUMS_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;

    // artist_list VIEW/SongArtist member fields
    public static final String TABLE_ARTIST_LIST_VIEW = "artist_list";
    public static final String COLUMN_ARTIST_LIST_SONG_ID = "song_id";
    public static final int INDEX_ARTIST_LIST_SONG_ID = 5;
    public static final int INDEX_ARTIST_LIST_SONG_TITLE = 4;
    public static final int INDEX_ARTIST_LIST_SONG_TRACK = 3;
    public static final int INDEX_ARTIST_LIST_ALBUM_NAME = 2;
    public static final int INDEX_ARTIST_LIST_ARTIST_NAME = 1;
    public static final String ARTIST_NAME = "Artist Name: ";
    public static final String ARTIST_ID = "Artist ID: ";
    public static final String ALBUM_NAME = "Album Name: ";
    public static final String TRACK_NUMBER = "Track: ";
    public static final String SONG_TITLE = "Song Title: ";
    public static final String SONG_ID = "Song ID: ";

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTISTS_ID = "_id";
    public static final String COLUMN_ARTISTS_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONGS_ID = "_id";
    public static final String COLUMN_SONGS_TRACK = "track";
    public static final String COLUMN_SONGS_TITLE = "title";
    public static final String COLUMN_SONGS_ALBUM = "album";

    public static final String SELECT_COLUMN_MIN = "min";
    public static final String SELECT_COLUMN_COUNT = "count";

    // SQL statement query composition
    public static final String ORDER_BY = " ORDER BY ";
    public static final String SELECT_ALL_FROM = "SELECT * FROM ";
    public static final String SELECT_COUNT_ALL = "SELECT COUNT(*) AS count, MIN(_id) AS min FROM ";
    public static final String COLLATE_NO_CASE = " COLLATE NOCASE ";
    public static final String ASC = " ASC ";
    public static final String DESC = " DESC ";
    public static final String SELECT = "SELECT ";
    public static final String UPDATE = "UPDATE ";
    public static final String SET = " SET ";
    public static final String INNER_JOIN = " INNER JOIN ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";
    public static final String ON = " ON ";
    public static final String EQUALS = " = ";
    public static final String COMMA = ", ";
    public static final String PERIOD = ".";
    public static final String SEMICOLON = ";";
    public static final String AS = " AS ";
    public static final String AND = " AND ";
    public static final String INSERT_INTO = "INSERT INTO ";
    public static final String DELETE_FROM = "DELETE FROM ";
    public static final String IF_NOT_EXISTS = " IF NOT EXISTS ";
    public static final String IF_EXISTS = " IF EXISTS ";

    public static final String PLACEHOLDER_QUESTION_MARK = " ?";
    public static final String PLACEHOLDER_VALUES_OPEN = "(";
    public static final String PLACEHOLDER_SINGLE_VALUE_CLOSE = ") VALUES(?)";
    public static final String PLACEHOLDER_DOUBLE_VALUE_CLOSE = ") VALUES(?, ?)";
    public static final String PLACEHOLDER_TRIPLE_VALUE_CLOSE = ") VALUES(?, ?, ?)";

    public static final String CREATE_VIEW_IF_NOT_EXISTS = "CREATE VIEW" + IF_NOT_EXISTS;
    public static final String DROP_VIEW = "DROP VIEW" + IF_EXISTS;

    public static final String VIEW_AS_SELECT = " AS SELECT ";

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
            SELECT +
                    TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_NAME + COMMA +
                    TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_NAME + COMMA +
                    TABLE_SONGS + PERIOD + COLUMN_SONGS_TRACK + COMMA +
                    TABLE_SONGS + PERIOD + COLUMN_SONGS_TITLE + COMMA +
                    TABLE_SONGS + PERIOD + COLUMN_SONGS_ID +
                    FROM + TABLE_SONGS +
                    INNER_JOIN + TABLE_ALBUMS +
                    ON + TABLE_SONGS + PERIOD + COLUMN_SONGS_ALBUM + EQUALS + TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_ID +
                    INNER_JOIN + TABLE_ARTISTS +
                    ON + TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_ARTIST + EQUALS + TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_ID +
                    WHERE + TABLE_SONGS + PERIOD + COLUMN_SONGS_TITLE + EQUALS;

    public static final String QUERY_ARTISTS_FOR_SONGS_SORT =
            ORDER_BY + TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_NAME + COMMA + TABLE_SONGS + PERIOD + COLUMN_SONGS_TITLE + COLLATE_NO_CASE;

    public static final String CREATE_ARTIST_LIST_VIEW =
            CREATE_VIEW_IF_NOT_EXISTS + TABLE_ARTIST_LIST_VIEW + VIEW_AS_SELECT +
                    TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_NAME + COMMA +
                    TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_NAME + AS + COLUMN_SONGS_ALBUM + COMMA +
                    TABLE_SONGS + PERIOD + COLUMN_SONGS_TRACK + COMMA +
                    TABLE_SONGS + PERIOD + COLUMN_SONGS_TITLE + COMMA +
                    TABLE_SONGS + PERIOD + COLUMN_SONGS_ID + AS + COLUMN_ARTIST_LIST_SONG_ID +
                    FROM + TABLE_SONGS +
                    INNER_JOIN + TABLE_ALBUMS +
                    ON + TABLE_SONGS + PERIOD + COLUMN_SONGS_ALBUM + EQUALS + TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_ID +
                    INNER_JOIN + TABLE_ARTISTS +
                    ON + TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_ARTIST + EQUALS + TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_ID +
                    ORDER_BY + TABLE_ARTISTS + PERIOD + COLUMN_ARTISTS_NAME + COMMA +
                    TABLE_ALBUMS + PERIOD + COLUMN_ALBUMS_NAME + COMMA +
                    TABLE_SONGS + PERIOD + COLUMN_SONGS_TRACK + SEMICOLON;

    // ? = placeholder for title
    // SQL statement: SELECT name, album, track FROM artist_list WHERE title = ?
    public static final String QUERY_ARTIST_LIST_VIEW =
            SELECT_ALL_FROM + TABLE_ARTIST_LIST_VIEW +
                    WHERE + COLUMN_SONGS_TITLE + EQUALS + PLACEHOLDER_QUESTION_MARK + COLLATE_NO_CASE +
                    AND + COLUMN_ARTISTS_NAME + EQUALS + PLACEHOLDER_QUESTION_MARK + COLLATE_NO_CASE;

    public static final String QUERY_ARTIST_LIST_VIEW_BY_TITLE =
            SELECT_ALL_FROM + TABLE_ARTIST_LIST_VIEW +
                    WHERE + COLUMN_SONGS_TITLE + EQUALS + PLACEHOLDER_QUESTION_MARK + COLLATE_NO_CASE;

    public static final String DROP_ARTIST_LIST_VIEW =
            DROP_VIEW + TABLE_ARTIST_LIST_VIEW;

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
            SELECT_ALL_FROM + TABLE_ALBUMS +
                    WHERE + COLUMN_ALBUMS_ARTIST + EQUALS + PLACEHOLDER_QUESTION_MARK + ORDER_BY + COLUMN_ALBUMS_NAME + COLLATE_NO_CASE;

    public static final String QUERY_SONGS_BY_ALBUM_ID =
            SELECT_ALL_FROM + TABLE_SONGS +
                    WHERE + COLUMN_SONGS_ALBUM + EQUALS + PLACEHOLDER_QUESTION_MARK + ORDER_BY + COLUMN_SONGS_TRACK + COLLATE_NO_CASE;

    public static final String UPDATE_ARTIST_NAME =
            UPDATE + TABLE_ARTISTS +
                    SET + COLUMN_ARTISTS_NAME + EQUALS + PLACEHOLDER_QUESTION_MARK +
                    WHERE + COLUMN_ARTISTS_ID + EQUALS + PLACEHOLDER_QUESTION_MARK;

    public static final String DELETE_FROM_SONGS =
            DELETE_FROM + TABLE_SONGS +
                    WHERE + COLUMN_SONGS_ID + EQUALS + PLACEHOLDER_QUESTION_MARK;

}
