package com.kanishk.marketo.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * The Class PlayList. Play list class containing data for a play list.
 * Maintains a record of references to central song data store. 
 * The songset could also contain objects of type SongFile but we decided to keep
 * the id of the song to keep number of references of SongFile objects to minimum.
 * Not keeping track of references of an object can cause memory leak.
 */
public class PlayList {
	
	/** The play list id. */
	private int playListId;
	
	/** The play list name. */
	private String playListName;
	
	/** The song list. A set of songs in the play list. Set automatically manages duplicacy.*/
	private Set<Integer> songSet;
	
	/**
	 * Instantiates a new play list.
	 *
	 * @param playListId the play list id
	 * @param playListName the play list name
	 */
	public PlayList(int playListId, String playListName) {
		this.playListId = playListId;
		this.playListName = playListName;
		/* 
		 * A linked hash set has been used to maintain the insertion order. It's also
		 * useful when deleting the song. The underlying implementation ensures delete 
		 * operation in O(1) time.
		 */
		songSet = new LinkedHashSet<>();
	}

	/**
	 * Gets the play list id.
	 * @return the play list id
	 */
	public int getPlayListId() {
		return playListId;
	}

	/**
	 * Gets the play list name.
	 * @return the play list name
	 */
	public String getPlayListName() {
		return playListName;
	}

	/**
	 * Gets the song id list.
	 * @return the song id list
	 */
	public Set<Integer> getSongIdList() {
		return songSet;
	}
	
	public boolean insertSong(int songId) {
		return songSet.add(songId);
	}
	
	public void deleteSong(int songId) {
		songSet.remove(songId);
	}
	
	public void sort(CentralStore dataSource, Type sortType) {
		List<Integer> list = new ArrayList<>(songSet.size());
		list.addAll(songSet);
		songSet.removeAll(songSet);
		Collections.sort(list, new PlayListComparator(sortType, dataSource));
		songSet.addAll(list);
	}
	
	/**
	 * The Class PlayListComparator. The comparator used for comparing two files
	 * based on {@link Type}. Useful in sorting of a song collection.
	 */
	private static class PlayListComparator implements Comparator<Integer> {
		
		/** The sort type. */
		private Type sortType;
		
		/** The data store. */
		private CentralStore dataStore;

		/**
		 * Instantiates a new song comparator.
		 * @param sortType the sort type
		 */
		public PlayListComparator(Type sortType, CentralStore dataStore) {
			this.sortType = sortType;
			this.dataStore = dataStore;
		}

		@Override
		public int compare(Integer id1, Integer id2) {
			SongFile o1 = dataStore.getSongById(id1);
			SongFile o2 = dataStore.getSongById(id2);
			if(Type.ARTIST.equals(sortType)) {
				return o1.getArtist().compareTo(o2.getArtist());
			} else if(Type.TITLE.equals(sortType)) {
				return o1.getTitle().compareTo(o2.getTitle());
			} else if(Type.ID.equals(sortType)) {
				return o1.getSongId() - o2.getSongId();
			} else {
				return 0;
			}
		}
	}
}
