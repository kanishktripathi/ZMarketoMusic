package com.kanishk.marketo.data;

/**
 * The Class SongFile. The class containing the song data.
 */
public class SongFile {
	
	/** The song id. */
	private int songId;
	
	/** The title. */
	private String title;
	
	/** The artist. */
	private String artist;
	
	public SongFile(int songId,String artist, String title) {
		this.songId = songId;
		this.title = title;
		this.artist = artist;
	}

	/**
	 * Gets the song id.
	 *
	 * @return the song id
	 */
	public int getSongId() {
		return songId;
	}

	/**
	 * Sets the song id.
	 *
	 * @param songId the new song id
	 */
	public void setSongId(int songId) {
		this.songId = songId;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the artist.
	 *
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Sets the artist.
	 *
	 * @param artist the new artist
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public int hashCode() {
		return songId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SongFile other = (SongFile) obj;
		if (songId != other.songId)
			return false;
		return true;
	}
}
