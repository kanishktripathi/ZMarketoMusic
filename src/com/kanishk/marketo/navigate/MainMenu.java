package com.kanishk.marketo.navigate;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.kanishk.marketo.constants.Constants;
import com.kanishk.marketo.constants.ErrorMessages;
import com.kanishk.marketo.constants.MarketoException;
import com.kanishk.marketo.data.SongFile;
import com.kanishk.marketo.operations.PrintManager;
import com.kanishk.marketo.operations.SongManager;

/**
 * The Class MainMenu. The class for displaying and getting user commands on main menu.
 */
public class MainMenu {

	/** The play list menu. */
	private PlayListMenu playListMenu;
	
	/** The print manager. */
	private PrintManager printManager;
	
	/** The song manager. */
	private SongManager songManager;

	/**
	 * Instantiates a new main menu.
	 */
	public MainMenu() {
		this.songManager = new SongManager();
		this.playListMenu = new PlayListMenu(songManager);
		printManager = PrintManager.getInstance();
	}

	/**
	 * Load. Load the main menu
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void load() throws IOException {
		Utils.print(Constants.WELCOME);
		Utils.print(Constants.MAIN_MENU);
		String commandText = null;
		Scanner scanner = new Scanner(System.in);
		commandText = scanner.nextLine();
		while (!Constants.QUIT.equalsIgnoreCase(commandText)) {
			if(!commandText.isEmpty()) {
				processCommand(commandText);				
			}
			commandText = scanner.nextLine();
		}
		scanner.close();
	}

	/**
	 * Process command. Processes the command text from the console
	 * and calls the appropriate functions.
	 * @param commandText the command text
	 */
	public void processCommand(String commandText) {
		String[] commandOption = commandText.split(" ");
		String commandName = commandOption[0].toLowerCase();
		try {		
			switch (commandName) {
			case Constants.CREATE: // create <playlist-name>
				playListMenu.createPlayList(commandOption, commandText);
				break;
			case Constants.EDIT: // edit <playlist-id>
				playListMenu.editPlayList(commandOption);
				break;
			case Constants.SONG: //song <songId>
				printManager.printSong(commandOption);
				break;
			case Constants.SEARCH:// search <search-option> "search text"
				List<SongFile> searchResult = songManager.search(commandText);
				printManager.printSongs(searchResult);
				break;
			case Constants.PLAYLIST: // playlist <playlist-id>
				printManager.printPlayList(commandOption);
				break;
			case Constants.PRINT: // print <song or playlist>
				printManager.print(commandOption);
				break;
			case Constants.SORT: // sort <title or artist>
				songManager.sort(commandOption);
				printManager.printAllSongs();
				break;
			default:
				Utils.print(ErrorMessages.INVALID_COMMAND);
				break;
			}
		} catch(MarketoException e) {
			System.out.println(e.getMessage());
		}
	}
}
