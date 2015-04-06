package com.kanishk.marketo.constants;

/**
 * The Class Constants. Stores constants to be used in the program
 */
public class Constants {

	/** The Constant QUIT. */
	public static final String QUIT = "quit";
	
	/** The Constant CREATE. */
	public static final String CREATE = "create";
	
	/** The Constant EDIT. */
	public static final String EDIT = "edit";
	
	/** The Constant SONG. */
	public static final String SONG = "song";
	
	/** The Constant PLAYLIST. */
	public static final String PLAYLIST = "playlist";
	
	/** The Constant PRINT. */
	public static final String PRINT = "print";
	
	/** The Constant SEARCH. */
	public static final String SEARCH = "search";
	
	/** The Constant SORT. */
	public static final String SORT = "sort";
	
	/** The Constant TITLE. */
	public static final String TITLE = "title";
	
	/** The Constant ARTIST. */
	public static final String ARTIST = "artist";
	
	public static final String DELETE = "delete";
	
	public static final String INSERT = "insert";
	
	public static final String INSERT_SEARCH = "insert_search";
	
	public static final String MAIN = "main";
	
	public static final String SONG_ID = "songid";
	
	public static final String END = "************------**********";
	
	public static final int ITEMS_COUNT = 2;
	
	public static final String SONG_PRINT_HEADER = "Song Display format: Id:::Song:::Artist";
	
	public static final String PLAYLIST_PRINT_HEADER = "Playlist Display format: Song:::Artist";
	
	public static final String NO_PLAYLIST = "No playlist to show";
	
	public static final String WELCOME  = "****Welcome to Marketo music*********";
	
	public static final String MAIN_MENU  = "Main menu:::Use following commands to navigate: \n"
			+ "1.create <playlist name>, 2.edit <playlistId>, song <songId>, 3.playlist <playlistId>,\n"
			+ "4.print <print option(print option can be song or playlist)>\n"
			+ "5.search <search option> \"<string of words>\" search option is either title or artist,\n"
			+ "6.sort <sort option> sort by title or artist, 7.quit";
	
	public static final String PLAYLIST_MENU  = "Playlist menu:::Use following commands to manage "
			+ "playlist:\n1.delete <songId>, 2.insert <songId>, "
			+ "3.insert_search <search option><string of words> 4.print\n"
			+ "5.search <search option><string of words> search option is either title or artist,\n"
			+ "6.sort <sort option> sort by title or artist, 7.main";
}
