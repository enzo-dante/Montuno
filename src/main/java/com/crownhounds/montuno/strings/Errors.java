package com.crownhounds.montuno.strings;

public class Errors {

    // CONSTANTS/static class variables assigned FINAL value before compilation/instantiation
    public static final String NO_CONNECTION = "Couldn't connect to database...\n";
    public static final String FATAL_ERROR = "FATAL ERROR: Couldn't connect to database";
    public static final String CLOSE_CONNECTION_FAIL = "Couldn't close() database...\n";

    public static final String QUERY_FAILED = "Query failed: ";
    public static final String UPDATE_FAILED = "Update failed: ";
    public static final String DELETE_FAILED = "Delete failed: ";
    public static final String DELETE_FAILED_SIZE = DELETE_FAILED + "songArtists.size() != 1";
    public static final String DELETED_SONG_ID = "DELETED song_id: ";
    public static final String DELETED_FOR = " for:\n\t";
    public static final String CREATE_TABLE_FAILED = "Create Table failed: ";
    public static final String CREATE_VIEW_FAILED = "Create View failed: ";
    public static final String CREATED_VIEW_ARTIST_LIST = "CREATED VIEW: artist_list\n";
    public static final String DROPPED_VIEW_ARTIST_LIST = "DROPPED VIEW: artist_list\n";
    public static final String QUERY_ARTIST_LIST_VIEW_FAILED = "queryArtistListView failed: ";
    public static final String QUERY_ARTIST_FOR_SONG_SQL = "queryArtistForSong SQL:\n";
    public static final String QUERY_SONGS_METADATA = "\nQuerySongsMetadata():";
    public static final String INSERT_ARTIST_FAIL = "Couldn't insert artist!";
    public static final String GET_ARTIST_FAIL = "Couldn't get _id for artist!";
    public static final String INSERT_ALBUM_FAIL = "Couldn't insert album!";
    public static final String GET_ALBUM_FAIL = "Couldn't get _id for album!";
    public static final String INSERT_SONG_FAIL = "Couldn't insert song!";
    public static final String ROLLBACK_FAILED = "Rollback failed: ";
    public static final String PERFORMING_ROLLBACK = "Performing rollback";
    public static final String AUTO_COMMIT_FAILED = "Resetting auto-commit failed: ";
    public static final String AUTO_COMMIT_SUCCESS = "Resetting default auto-commit behavior";
    public static final String ALREADY_IN_DB = "Already in database:\n";
    public static final String ERROR_TITLE = "ERROR";
    public static final String ERROR_NO_ARTIST_SELECTED = "No artist selected";
    public static final String ERROR_NO_ALBUM_SELECTED = "No album selected";
    public static final String ERROR_NO_SONG_SELECTED = "No song selected";
}
