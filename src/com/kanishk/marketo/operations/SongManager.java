package com.kanishk.marketo.operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kanishk.marketo.constants.Constants;
import com.kanishk.marketo.constants.MarketoException;
import com.kanishk.marketo.data.CentralStore;
import com.kanishk.marketo.data.SongFile;
import com.kanishk.marketo.data.Type;
import com.kanishk.marketo.navigate.Utils;

/**
 * The Class SongManager. This class manages all the operations to be performed
 * on the {@link CentralStore} data structure.
 */
public class SongManager {
	
	/** The song store. Data store for the songs.*/
	private CentralStore songStore;

	/**
	 * Instantiates a new song manager.
	 */
	public SongManager() {
		this.songStore = CentralStore.getInstance();
		
	}
	
	/**
	 * Sorts the songs list in the {@link CentralStore} object based on the strings
	 * extracted from the command options. 
	 * @param commandOptions the command options
	 * @throws MarketoException the marketo exception
	 */
	public void sort(String[] commandOptions) throws MarketoException {
		if(Utils.checkStringOption(commandOptions, Constants.ARTIST, Constants.TITLE, 
				Constants.SONG_ID)) {
			String commandOption = commandOptions[1];
			Type type = Type.getType(commandOption);
			Collections.sort(songStore.getSongList(), new SongComparator(type));
		}
	}
	
	/**
	 * Searches for songs by title or artist in the list of string. 
	 * The search command is of type search options(title or artist) "search text"
	 * Ex: search artist "ac/dc"
	 * @param searchCommand the search command
	 * @return the list
	 * @throws MarketoException the marketo exception
	 */
	public List<SongFile> search(String searchCommand) throws MarketoException {
		String[] searchParams = Utils.validateAndReturnSearchParam(searchCommand);
		Type type = Type.getType(searchParams[0]);
		List<SongFile> fileList = new ArrayList<>();
		List<SongFile> songList = songStore.getSongList();
		String searchString = searchParams[1];
		for(SongFile song : songList) {
			String matchText;
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
}
