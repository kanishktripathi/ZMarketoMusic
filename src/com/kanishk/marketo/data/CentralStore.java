package com.kanishk.marketo.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class CentralStore. Contains collection of {@link SongFile} objects.
 * Each song is indexed as a key value pair and can be retrieved on O(1) time
 * by it's song id. It also maintains a collection of the song files. By default
 * the song order is defined by the order in which they were inserted from the data file.
 * The list can later be sorted based on the sorting parameter(artist or song). 
 */
public class CentralStore {
	
	private static CentralStore STORE = new CentralStore();
	
	/** The song index. */
	private Map<Integer, SongFile> songIndex;
	
	/** The song list. */
	private List<SongFile> songList;
	
	private CentralStore() {
		songList = new ArrayList<>(0);
		songIndex = new HashMap<>(songList.size());
	}
	
	/**
	 * Instantiates the song container with new data.
	 * @param songList the song list containing all the songs.
	 */
	public void initCentralStore(List<SongFile> songList) {
		this.songList = songList;
		songIndex = new HashMap<>(songList.size());
		for(SongFile song : songList) {
			songIndex.put(song.getSongId(), song);
		}
	}

	/**
	 * Gets the song index.
	 *
	 * @return the song index
	 */
	public Map<Integer, SongFile> getSongIndex() {
		return songIndex;
	}

	/**
	 * Gets the song list.
	 *
	 * @return the song list
	 */
	public List<SongFile> getSongList() {
		return songList;
	}
	
	public SongFile getSongById(int songId) {
		return songIndex.get(songId);
	}
	
	public static CentralStore getInstance() {
		return STORE;
	}
}
