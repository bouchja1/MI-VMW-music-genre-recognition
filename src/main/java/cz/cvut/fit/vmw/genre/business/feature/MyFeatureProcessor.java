package cz.cvut.fit.vmw.genre.business.feature;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;

import jAudioFeatureExtractor.Cancel;
import jAudioFeatureExtractor.ExplicitCancel;
import jAudioFeatureExtractor.Updater;
import jAudioFeatureExtractor.Aggregators.AggregatorContainer;
import jAudioFeatureExtractor.AudioFeatures.FeatureExtractor;
import jAudioFeatureExtractor.jAudioTools.FeatureProcessor;

public class MyFeatureProcessor extends FeatureProcessor {

	public MyFeatureProcessor(int window_size, double window_overlap,
			double sampling_rate, boolean normalise,
			FeatureExtractor[] all_feature_extractors,
			boolean[] features_to_save_among_all,
			boolean save_features_for_each_window,
			boolean save_overall_recording_features,
			OutputStream feature_values_save_path,
			OutputStream feature_definitions_save_path, int outputType,
			Cancel cancel, AggregatorContainer container) throws Exception {
		super(window_size, window_overlap, sampling_rate, normalise,
				all_feature_extractors, features_to_save_among_all,
				save_features_for_each_window, save_overall_recording_features,
				feature_values_save_path, feature_definitions_save_path,
				outputType, cancel, container);
	}

	public String extractFeaturesString(File recording_file, Updater updater)
			throws Exception {
		// Pre-process the recording and extract the samples from the audio
		this.updater = updater;
		double[] samples = preProcessRecording(recording_file);
		if (this.cancel.isCancel()) {
			throw new ExplicitCancel("Killed after loading data");
		}
		// Calculate the window start indices
		LinkedList<Integer> window_start_indices_list = new LinkedList<Integer>();
		int this_start = 0;
		while (this_start < samples.length) {
			window_start_indices_list.add(new Integer(this_start));
			this_start += this.window_size - this.window_overlap_offset;
		}
		Integer[] window_start_indices_I = window_start_indices_list
				.toArray(new Integer[1]);
		int[] window_start_indices = new int[window_start_indices_I.length];

		// if were using a progress bar, set its max update
		if (updater != null) {
			updater.setFileLength(window_start_indices.length);
		}

		for (int i = 0; i < window_start_indices.length; i++)
			window_start_indices[i] = window_start_indices_I[i].intValue();

		// Extract the feature values from the samples
		double[][][] window_feature_values = getFeatures(samples,
				window_start_indices);

		if (this.save_overall_recording_features) {
			this.aggregator.add(this.feature_extractors, this.features_to_save);
			this.aggregator.aggregate(window_feature_values);
		}

		// Save the feature values for this recording
		String returnedXml = saveACEFeatureVectorsForARecordingString(
				window_feature_values, window_start_indices,
				recording_file.getPath(), aggregator);

		// Save the feature definitions
		if (!definitions_written && (outputType == 0)) {
			saveFeatureDefinitions(window_feature_values, aggregator);
		}

		return returnedXml;
	}

	private String saveACEFeatureVectorsForARecordingString(
			double[][][] feature_values, int[] window_start_indices,
			String identifier, AggregatorContainer aggContainer)
			throws Exception {
		// Start the entry for the recording
		StringBuilder tmp = new StringBuilder(); // Using default 16 character
													// size
		tmp.append("\t<data_set>\n");
		tmp.append("\t\t<data_set_id>" + identifier + "</data_set_id>\n");

		// Write the features for individual windows
		if (save_features_for_each_window)
			for (int win = 0; win < feature_values.length; win++) {
				double start_time = ((double) window_start_indices[win])
						/ sampling_rate;
				double end_time = ((double) (window_start_indices[win]
						+ window_size - 1))
						/ sampling_rate;
				tmp.append("\t\t<section start=\"" + start_time + "\" stop=\""
						+ end_time + "\">\n");
				for (int feat = 0; feat < feature_values[win].length; feat++) {
					if (features_to_save[feat])
						if (feature_values[win][feat] != null) {
							String feature_name = feature_extractors[feat]
									.getFeatureDefinition().name;
							tmp.append("\t\t\t<feature>\n");
							tmp.append("\t\t\t\t<name>" + feature_name
									+ "</name>\n");
							for (int val = 0; val < feature_values[win][feat].length; val++) {
								String value = jAudioFeatureExtractor.GeneralTools.StringMethods
										.getDoubleInScientificNotation(
												feature_values[win][feat][val],
												4);
								tmp.append("\t\t\t\t<v>" + value + "</v>\n");
							}
							tmp.append("\t\t\t</feature>\n");
						}
				}
				tmp.append("\t\t</section>\n");
			}

		// Write the features for the file

		final class StringBuilderOutputStream extends OutputStream {

			private final StringBuilder sb = new StringBuilder();

			@Override
			public void write(int b) throws IOException {
				sb.append((char) b);
			}
		}

		StringBuilderOutputStream os = new StringBuilderOutputStream();
		if (save_overall_recording_features) {
			aggContainer.outputACEValueEntries(new DataOutputStream(os));
			tmp.append(os.sb.toString());
		}

		// End the entry for the recording
		tmp.append("\t</data_set>\n\n");

		return tmp.toString();
	}

}
