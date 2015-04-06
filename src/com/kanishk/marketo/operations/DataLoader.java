package com.kanishk.marketo.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.kanishk.marketo.constants.Constants;
import com.kanishk.marketo.data.CentralStore;
import com.kanishk.marketo.data.SongFile;
import com.kanishk.marketo.navigate.Utils;

/**
 * The Class DataLoader. Loader class for loading data from the file in the given path
 * into memory.
 */
public class DataLoader {

	/**
	 * Load data. Loads song data from the file at the specified file path.
	 * The method to populate {@link CentralStore} object. The data is read line by line.
	 * from the file. The line must be in the format &lt;artist&gt;, &lt;title&gt;
	 * @param filePath the file path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void loadData(String filePath) throws IOException {
		InputStream fileInputStream = null;
		BufferedReader reader = null;
		List<SongFile> songList = new ArrayList<>();
		try {
			File file = new File(filePath);
			fileInputStream = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(fileInputStream));
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				processData(songList, inputLine);
			}
			CentralStore datasStore = CentralStore.getInstance();
			datasStore.initCentralStore(songList);
		} catch (IOException e) {
			Utils.print(e.getMessage());
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
			if (reader != null) {
				reader.close();
			}
		}
	}

	/**
	 * Process data. Creates {@link SongFile} object from the single line of the file.
	 * Inserts the line into fileList.
	 * @param fileList the file list
	 * @param inputLine the input line
	 */
	private void processData(List<SongFile> fileList, String inputLine) {
		if (!inputLine.isEmpty()) {
			String[] inputData = inputLine.split(",");
			if (inputData.length == Constants.ITEMS_COUNT) {
				int id = fileList.size();
				SongFile songFile = new SongFile(id, inputData[0].trim(),
						inputData[1].trim());
				fileList.add(songFile);
			} else {
				Utils.print("Invalid line in the file::" + inputLine);
			}
		}
	}
}
