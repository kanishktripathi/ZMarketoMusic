package com.kanishk.marketo.operations;

import java.util.Comparator;

import com.kanishk.marketo.data.SongFile;
import com.kanishk.marketo.data.Type;

/**
 * The Class SongComparator. The comparator used for comparing two files
 * based on {@link Type}. Useful in sorting of a song collection.
 */
public class SongComparator implements Comparator<SongFile> {
	
	/** The sort type. */
	private Type sortType;

	/**
	 * Instantiates a new song comparator.
	 *
	 * @param sortType the sort type on which the song files will
	 * be compared.
	 */
	public SongComparator(Type sortType) {
		this.sortType = sortType;
	}

	@Override
	public int compare(SongFile o1, SongFile o2) {
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
