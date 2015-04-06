package com.kanishk.marketo.navigate;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.kanishk.marketo.constants.Constants;
import com.kanishk.marketo.constants.ErrorMessages;
import com.kanishk.marketo.constants.MarketoException;
import com.kanishk.marketo.data.PlayList;
import com.kanishk.marketo.data.SongFile;
import com.kanishk.marketo.operations.PlaylistManager;
import com.kanishk.marketo.operations.PrintManager;
import com.kanishk.marketo.operations.SongManager;

/**
 * The Class PlayListMenu. The class for displaying and editing playlist information.
 */
public class PlayListMenu {

	/** The input reader. */
	private Scanner inputReader;

	/** The current play list. */
	private PlayList currentPlayList;

	/** The playlist manager. */
	private PlaylistManager playlistManager;

	/** The print manager. */
	private PrintManager printManager;
	
	/** The song manager. */
	private SongManager songManager;

	/**
	 * Instantiates a new play list menu.
	 */
	public PlayListMenu(SongManager songManager) {
		this.inputReader = new Scanner(System.in);
		this.playlistManager = PlaylistManager.getInstance();
		this.printManager = PrintManager.getInstance();
		this.songManager = songManager;
	}

	/**
	 * Creates the play list.
	 *
	 * @param commandOptions the command options
	 * @param commandText the command text
	 */
	public void createPlayList(String[] commandOptions, String commandText) {
		if (commandOptions.length >= 2) {
			int index = commandText.indexOf(' ');
			String playListName = commandText.substring(index + 1);
			currentPlayList = playlistManager.createPlayList(playListName);
			loadMenu();
		} else {
			Utils.print(ErrorMessages.PLAYLIST_CREATE_NO_NAME);
		}
	}

	/**
	 * Edits the play list.
	 *
	 * @param commandOptions the command options
	 * @throws MarketoException the marketo exception
	 */
	public void editPlayList(String[] commandOptions) throws MarketoException {
		int playListId = Utils.validateAndReturnId(commandOptions);
		currentPlayList = playlistManager.getPlayListById(playListId);
		if (currentPlayList == null) {
			Utils.print(ErrorMessages.PLAYLIST_NOT_FOUND);
		} else {
			loadMenu();
		}
	}

	/**
	 * Load menu.
	 */
	private void loadMenu() {
		Utils.print(Constants.PLAYLIST_MENU);
		String commandText = null;
		try {
			commandText = inputReader.nextLine();
			while (!Constants.MAIN.equalsIgnoreCase(commandText)) {
				if(!commandText.isEmpty()) {
					processCommand(commandText);
					commandText = inputReader.nextLine();					
				}
			}
		} catch (IOException e) {
			Utils.print(e.getMessage());
		}
		Utils.print(Constants.MAIN_MENU);
	}

	/**
	 * Process command.
	 *
	 * @param commandText the command text
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void processCommand(String commandText) throws IOException {
		String[] commandOption = commandText.split(" ");
		String commandName = commandOption[0].toLowerCase();
		try {
			int id;
			List<SongFile> searchResult = null;
			switch (commandName) {
			case Constants.INSERT: // insert <songId>
				id = Utils.validateAndReturnId(commandOption);
				playlistManager.insertSong(currentPlayList, id);
				break;
			case Constants.DELETE: // delete <songId>
				id = Utils.validateAndReturnId(commandOption);
				playlistManager.deleteSong(currentPlayList, id);
				break;
			case Constants.INSERT_SEARCH: // insert_search <artist or song> "search text"
				searchResult = songManager.search(commandText);
				playlistManager.insertMultipleSong(searchResult, currentPlayList);
				break;
			case Constants.PRINT: //print current playlist
				printManager.printPlayList(currentPlayList);
				break;
			case Constants.SEARCH:
				searchResult = playlistManager.getSearchResults(currentPlayList, commandText);
				printManager.printSongs(searchResult);
				break;
			case Constants.SORT: // sort <title or artist>
				playlistManager.sortPlayList(currentPlayList, commandOption);
				printManager.printPlayList(currentPlayList);
				break;
			default:
				Utils.print(ErrorMessages.INVALID_COMMAND);
				break;
			}
		} catch (MarketoException e) {
			Utils.print(e.getMessage());
		}
	}
}
