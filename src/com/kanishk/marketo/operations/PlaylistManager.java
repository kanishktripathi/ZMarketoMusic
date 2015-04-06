package com.kanishk.marketo.operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kanishk.marketo.constants.Constants;
import com.kanishk.marketo.constants.ErrorMessages;
import com.kanishk.marketo.constants.MarketoException;
import com.kanishk.marketo.data.CentralStore;
import com.kanishk.marketo.data.PlayList;
import com.kanishk.marketo.data.SongFile;
import com.kanishk.marketo.data.Type;
import com.kanishk.marketo.navigate.Utils;

/**
 * The Class PlaylistManager. This singleton class manages all the operations to
 * be performed on the playlists.
 */
public class PlaylistManager {

	/** The play lists. */
	private Map<Integer, PlayList> playLists;

	/** The db central store. */
	private CentralStore dbCentralStore;

	/** The manager. */
	private static PlaylistManager MANAGER = new PlaylistManager();

	/**
	 * Instantiates a new playlist manager.
	 */
	private PlaylistManager() {
		playLists = new HashMap<>();
		dbCentralStore = CentralStore.getInstance();
	}

	/**
	 * Gets the single instance of PlaylistManager.
	 *
	 * @return single instance of PlaylistManager
	 */
	public static PlaylistManager getInstance() {
		return MANAGER;
	}

	/**
	 * Gets the play list by play list id.
	 *
	 * @param playListId the play list id
	 * @return the play list by id
	 */
	public PlayList getPlayListById(int playListId) {
		return playLists.get(playListId);
	}

	/**
	 * Gets all the play lists created by user.
	 *
	 * @return the all play lists
	 */
	public Map<Integer, PlayList> getAllPlayLists() {
		return playLists;
	}

	/**
	 * Inserts the song in the playList object.
	 * Will display an error message if the song is already in the list.
	 * @param playList the play list in which song is to be inserted
	 * @param songId the id of the song
	 */
	public void insertSong(PlayList playList, int songId) {
		if (isSongPresent(songId)) {
			boolean insertSucess = playList.insertSong(songId);
			if (!insertSucess) {
				Utils.print("The song already exists in the play list");
			}
		}
	}

	/**
	 * Deletes song from the given play list.
	 *
	 * @param playList the play list from which song will be removed
	 * @param songId the song id
	 */
	public void deleteSong(PlayList playList, int songId) {
		if (isSongPresent(songId)) {
			playList.deleteSong(songId);
		}
	}

	/**
	 * Creates the play list of the given name
	 *
	 * @param playListName the play list name
	 * @return the play list
	 */
	public PlayList createPlayList(String playListName) {
		int key = playLists.size() + 1;
		PlayList playList = new PlayList(key, playListName);
		playLists.put(key, playList);
		return playList;
	}
	
	/**
	 * Sort play list based on console input.
	 * The input is in format sort options. options can be artist, title or songid
	 * @param playList the play list
	 * @param commandOptions the command options
	 * @throws MarketoException the marketo exception
	 */
	public void sortPlayList(PlayList playList, String[] commandOptions) throws MarketoException {
		if(Utils.checkStringOption(commandOptions, Constants.ARTIST, Constants.TITLE, 
				Constants.SONG_ID)) {
			String commandOption = commandOptions[1];
			playList.sort(dbCentralStore, Type.getType(commandOption));
		}
	}
	
	
	/**
	 * Insert all the songs in the given song list in the play list.
	 * Songs already present in the list are discarded.
	 * @param songList the song list
	 * @param playList the play list where song are to be inserted
	 */
	public void insertMultipleSong(List<SongFile> songList, PlayList playList) {
		for(SongFile song : songList) {
			playList.insertSong(song.getSongId());
		}
	}
	
	/**
	 * Gets the search results for a text in the given play list.
	 * @param playList the play list. The search command is of type 
	 * search options(title or artist) "search text". Ex: search artist "ac/dc"
	 * @param commandText the command text
	 * @return the search results containing search text on a particular field.
	 * @throws MarketoException the marketo exception thrwown for invalid command options.
	 */
	public List<SongFile> getSearchResults(PlayList playList, 
			String commandText) throws MarketoException {
		String[] searchParams = Utils.validateAndReturnSearchParam(commandText);
		Type type = Type.getType(searchParams[0]);
		List<SongFile> fileList = new ArrayList<>();
		Set<Integer> songList = playList.getSongIdList();
		String searchString = searchParams[1];
		for(Integer songId : songList) {
			String matchText;
			SongFile song = dbCentralStore.getSongById(songId);
			if(Type.ARTIST.equals(type)) {
				matchText = song.getArtist().toLowerCase();
			} else {
				matchText = song.getTitle().toLowerCase();
			}
			if(matchText.contains(searchString)) {
				fileList.add(song);
			}
		}
		Collections.sort(fileList, new SongComparator(type));
		return fileList;
	}

	/**
	 * Checks if the song is present in the data store.
	 *
	 * @param songId the song id
	 * @return true, if is song present
	 */
	private boolean isSongPresent(int songId) {
		if (dbCentralStore.getSongById(songId) != null) {
			return true;
		} else {
			Utils.print(ErrorMessages.SONG_NOT_FOUND);
			return false;
		}
	}
}
