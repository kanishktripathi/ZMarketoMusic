package com.kanishk.marketo.operations;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.kanishk.marketo.constants.Constants;
import com.kanishk.marketo.constants.ErrorMessages;
import com.kanishk.marketo.constants.MarketoException;
import com.kanishk.marketo.data.CentralStore;
import com.kanishk.marketo.data.PlayList;
import com.kanishk.marketo.data.SongFile;
import com.kanishk.marketo.navigate.Utils;

/**
 * The Class PrintManager. This singleton class manages all the operations to
 * print the songs and play list details from the central data store.
 */
public class PrintManager {

	/** The data store. */
	private CentralStore dataStore;

	/** The play list manager. */
	private PlaylistManager playListManager;

	/** The Constant MANAGER. singleton instance of print manager */
	private static final PrintManager MANAGER = new PrintManager();

	/**
	 * Instantiates a new prints the manager.
	 */
	public PrintManager() {
		dataStore = CentralStore.getInstance();
		playListManager = PlaylistManager.getInstance();
	}

	/**
	 * Prints the song based on command text.
	 * The command should be in syntax: song songId
	 * @param commandOptions the command options
	 * @throws MarketoException the marketo exception
	 */
	public void printSong(String[] commandOptions) throws MarketoException {
		int songId = Utils.validateAndReturnId(commandOptions);
		SongFile song = dataStore.getSongById(songId);
		if(song != null) {
			Utils.print(Constants.SONG_PRINT_HEADER);
			printSong(song);
			Utils.print(Constants.END);
		} else {
			Utils.print(ErrorMessages.SONG_NOT_FOUND);
		}
	}

	/**
	 * Prints the all songs. It either prints all the songs of data store
	 * or all the songs in every playlist.
	 * Syntax : print song or print playlist 
	 * @param commandOptions the command options
	 * @throws MarketoException the marketo exception
	 */
	public void print(String[] commandOptions) throws MarketoException {
		if(Utils.checkStringOption(commandOptions, Constants.PLAYLIST, Constants.SONG)) {
			//Validation performed so second word of the command is a valid option
			String option = commandOptions[1];
			if(Constants.PLAYLIST.equals(option)) {
				printAllPlayList();
			} else {
				printSongs(dataStore.getSongList());
			}
		}
	}

	/**
	 * Prints the play list from the play list id in the command text.
	 * Syntax playlist playlistId
	 * @param commandOptions the command options array
	 * @throws MarketoException the marketo exception
	 */
	public void printPlayList(String[] commandOptions) throws MarketoException {
		int playListId = Utils.validateAndReturnId(commandOptions);
		PlayList playList = playListManager.getPlayListById(playListId);
		if(playList != null) {
			Utils.print(Constants.PLAYLIST_PRINT_HEADER);
			printPlayList(playList);
			Utils.print(Constants.END);
		} else {
			Utils.print(ErrorMessages.PLAYLIST_NOT_FOUND);
		}
	}
	
	/**
	 * Prints the all play lists in the memory.
	 */
	public void printAllPlayList() {
		Map<Integer, PlayList> playLists = playListManager.getAllPlayLists();
		if(playLists.size() > 0) {
			Utils.print(Constants.PLAYLIST_PRINT_HEADER);
			StringBuilder sb = new StringBuilder();
			for(Entry<Integer, PlayList> entry : playLists.entrySet()) {
				PlayList list = entry.getValue();
				sb.append("Play list:").append(list.getPlayListName())
				.append("::::Id:").append(list.getPlayListId());
				Utils.print(sb.toString());
				printPlayList(list);
				Utils.print(Constants.END);
				sb.setLength(0);
			}			
		} else {
			Utils.print(Constants.NO_PLAYLIST);
		}
	}
	
	/**
	 * Prints the content of the playList object
	 *
	 * @param playList the play list
	 */
	public void printPlayList(PlayList playList) {
		Set<Integer> listItems = playList.getSongIdList();
		StringBuilder sb = new StringBuilder();
		for(Integer id : listItems) {
			SongFile file = dataStore.getSongById(id);
			sb.append(file.getArtist()).append(":::").append(file.getTitle());
			Utils.print(sb.toString());
			sb.setLength(0);
		}
	}
	
	/**
	 * Prints the songs present in the songList parameter.
	 *
	 * @param songList the song list
	 */
	public void printSongs(List<SongFile> songList) {
		Utils.print(Constants.SONG_PRINT_HEADER);
		for(SongFile file : songList) {
			printSong(file);
		}
	}
	
	/**
	 * Prints the all songs in the data store.
	 */
	public void printAllSongs() {
		printSongs(dataStore.getSongList());
	}
	
	/**
	 * Prints the contents of the song object. Used for printing
	 * a single or multiple song file objects in a loop.
	 * @param song the song object
	 */
	private void printSong(SongFile song) {
		StringBuilder sb = new StringBuilder();
		sb.append(song.getSongId()).append(":::").append(song.getTitle()).
		append(":::").append(song.getArtist());
		Utils.print(sb.toString());
	}

	/**
	 * Gets the single instance of PrintManager.
	 *
	 * @return single instance of PrintManager
	 */
	public static PrintManager getInstance() {
		return MANAGER;
	}
}
