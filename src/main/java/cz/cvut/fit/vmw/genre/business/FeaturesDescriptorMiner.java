package cz.cvut.fit.vmw.genre.business;

import jAudioFeatureExtractor.DataTypes.RecordingInfo;
import jAudioFeatureExtractor.jAudioTools.AudioSamples;

import java.io.File;

public class FeaturesDescriptorMiner {

	private File file;

	public FeaturesDescriptorMiner(File file) {
		this.file = file;
	}

	public RecordingInfo addRecording() throws Exception {
		// Assume file is invalid as first guess
		RecordingInfo recording_info = null;

		// Verify that the file exists
		if (file.exists()) {
			try {
				// The samples extracted from file
				AudioSamples audio_samples = null;

				// Load the samples. Throw an exception if the file is not a
				// valid audio file of a type that can be read and processed.
				audio_samples = new AudioSamples(file, file.getPath(), false);

				// Generate a RecordingInfo object for the loaded file
				recording_info = new RecordingInfo(file.getName(),
						file.getPath(), audio_samples, false);
			} catch (Exception e) {
				System.out.println("Nejaka exception");
			}
		} else {
			System.out.println("The selected file " + file.getName()
					+ " does not exist.");
		}

		// mam na konci recording_info
		return recording_info;
	}

}
