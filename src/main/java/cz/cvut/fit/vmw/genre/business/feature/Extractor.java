/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre.business.feature;

import jAudioFeatureExtractor.ACE.DataTypes.FeatureDefinition;
import jAudioFeatureExtractor.ACE.XMLParsers.XMLDocumentParser;
import jAudioFeatureExtractor.Aggregators.Aggregator;
import jAudioFeatureExtractor.Aggregators.AggregatorContainer;
import jAudioFeatureExtractor.AudioFeatures.Derivative;
import jAudioFeatureExtractor.AudioFeatures.FeatureExtractor;
import jAudioFeatureExtractor.AudioFeatures.Mean;
import jAudioFeatureExtractor.AudioFeatures.MetaFeatureFactory;
import jAudioFeatureExtractor.AudioFeatures.StandardDeviation;
import jAudioFeatureExtractor.Cancel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import cz.cvut.fit.vmw.genre.business.feature.XmlFeatureExtractor;
import cz.cvut.fit.vmw.genre.crate.Song;
import cz.cvut.fit.vmw.genre.db.DataDAO;
import cz.cvut.fit.vmw.genre.db.DataDAOImpl;
import cz.cvut.fit.vmw.genre.db.SongDAO;
import cz.cvut.fit.vmw.genre.db.SongDAOImpl;
import cz.cvut.fit.vmw.genre.exception.GenreException;

/**
 * 
 * @author Jan Dufek
 */
public class Extractor {

	private File file;
	private final String valuesSavePath = "values.xml";
	private final String definitionSavePath = "definition.xml";
	private FeatureExtractor[] features;
	private boolean[] defaults;
        
        private Song song;

	public Extractor(File file, Song song) {
		this.file = file;
                this.song = song;
		dataModelConstruct();
	}

	private void dataModelConstruct() {
		try {

			Object[] lists = (Object[]) XMLDocumentParser.parseXMLDocument(
					"/home/johny/projects/skola/vmw/MI-VMW-music-genre-recognition/features.xml", "feature_list");
			LinkedList<FeatureExtractor> extractors = (LinkedList<FeatureExtractor>) lists[0];
			LinkedList<Boolean> def = (LinkedList<Boolean>) lists[1];

			/**
			 * Mapping between aggregator names and aggregator prototypes
			 */
			HashMap<String, Aggregator> aggregatorMap = new HashMap<>();

			Aggregator[] aggArray = ((LinkedList<Aggregator>) lists[2])
					.toArray(new Aggregator[] {});

			for (int i = 0; i < aggArray.length; ++i) {
				aggregatorMap.put(aggArray[i].getAggregatorDefinition().name,
						aggArray[i]);
			}

			LinkedList<MetaFeatureFactory> metaExtractors = new LinkedList<>();

			metaExtractors.add(new Derivative());
			metaExtractors.add(new Mean());
			metaExtractors.add(new StandardDeviation());
			metaExtractors.add(new Derivative(new Mean()));
			metaExtractors.add(new Derivative(new StandardDeviation()));

			populateMetaFeatures(metaExtractors, extractors, def);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	void populateMetaFeatures(LinkedList<MetaFeatureFactory> listMFF,
			LinkedList<FeatureExtractor> listFE, LinkedList<Boolean> def) {
		LinkedList<Boolean> tmpDefaults = new LinkedList<>();
		LinkedList<FeatureExtractor> tmpFeatures = new LinkedList<>();
		LinkedList<Boolean> isPrimaryList = new LinkedList<>();
		Iterator<FeatureExtractor> lFE = listFE.iterator();
		Iterator<Boolean> lD = def.iterator();
		while (lFE.hasNext()) {
			FeatureExtractor tmpF = lFE.next();
			Boolean tmpB = lD.next();
			tmpFeatures.add(tmpF);
			tmpDefaults.add(tmpB);
			isPrimaryList.add(true);
			if (tmpF.getFeatureDefinition().dimensions != 0) {
				Iterator<MetaFeatureFactory> lM = listMFF.iterator();
				while (lM.hasNext()) {
					MetaFeatureFactory tmpMFF = lM.next();
					FeatureExtractor tmp = tmpMFF
							.defineFeature((FeatureExtractor) tmpF.clone());
					tmpFeatures.add(tmp);
					tmpDefaults.add(false);
					isPrimaryList.add(false);
				}
			}
		}
		this.features = tmpFeatures.toArray(new FeatureExtractor[1]);
		Boolean[] defaults_temp = tmpDefaults.toArray(new Boolean[1]);
		Boolean[] is_primary_temp = isPrimaryList.toArray(new Boolean[] {});
		this.defaults = new boolean[defaults_temp.length];
		boolean[] is_primary = new boolean[defaults_temp.length];
		for (int i = 0; i < this.defaults.length; i++) {
			this.defaults[i] = defaults_temp[i].booleanValue();
			is_primary[i] = is_primary_temp[i].booleanValue();
		}
		FeatureDefinition[] featureDefinitions = new FeatureDefinition[this.defaults.length];
		for (int i = 0; i < featureDefinitions.length; ++i) {
			featureDefinitions[i] = features[i].getFeatureDefinition();
		}

	}

	public XmlFeatureExtractor getFeatures() {
		try {
			File feature_values_save_file = new File(valuesSavePath);
			File feature_definitions_save_file = new File(definitionSavePath);

			// Prepare stream writers
			FileOutputStream values_to = new FileOutputStream(
					feature_values_save_file);
			FileOutputStream definitions_to = new FileOutputStream(
					feature_definitions_save_file);

			AggregatorContainer container = new AggregatorContainer();
			Aggregator[] aggregators = new Aggregator[3];
			aggregators[0] = new jAudioFeatureExtractor.Aggregators.Mean();
			aggregators[1] = new jAudioFeatureExtractor.Aggregators.StandardDeviation();
			aggregators[2] = new jAudioFeatureExtractor.Aggregators.MFCC();
			// aggregators[2].setParameters(new
			// String[]{"Area Method of Moments of MFCCs"},new String[]{""});
			container.add(aggregators);

			for (int i = 0; i < this.defaults.length; i++) {
				this.defaults[i] = false;
			}

			this.defaults[3] = this.defaults[4] = this.defaults[5] = this.defaults[6] = this.defaults[7] = this.defaults[8] = this.defaults[53] = this.defaults[59] = this.defaults[65] = this.defaults[89] = this.defaults[139] = true;

			MyFeatureProcessor processor = new MyFeatureProcessor(512, 0,
					16000, true, this.features, this.defaults, false, true,
					values_to, definitions_to, 0, new Cancel(), container);

			String returnedXml = processor.extractFeaturesString(file, null);
			//System.out.println(returnedXml.toString());
			XmlFeatureExtractor xmlExtractor = new XmlFeatureExtractor(
					returnedXml);
			xmlExtractor.buildXml();
			
			return xmlExtractor;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
                return null;

	}

	public void saveFeaturesToDb(Song song, cz.cvut.fit.vmw.genre.business.feature.FeatureExtractor xmlExtractor) {   
            try {
                
                DataDAO dataDao = new DataDAOImpl();
                
                dataDao.saveFeatures(song, xmlExtractor.getmFCCAreaMethodMoments());
                dataDao.saveFeatures(song, xmlExtractor.getmFCC());
                dataDao.saveFeatures(song, xmlExtractor.getmFCCStrengthOfStrongestBeat());
                dataDao.saveFeatures(song, xmlExtractor.getmFCCBeatSum());
                dataDao.saveFeatures(song, xmlExtractor.getmFCCStrongestBeat());
                dataDao.saveFeatures(song, xmlExtractor.getmFCCStandardDeviationOfSpectralCentroid());
                dataDao.saveFeatures(song, xmlExtractor.getmFCCSpectralCentroid());
                dataDao.saveFeatures(song, xmlExtractor.getmFCCRunningMeanOfSpectralCentroid());
                dataDao.saveFeatures(song, xmlExtractor.getmFCCDerivativeOfSpectralCentroid());
                dataDao.saveFeatures(song, xmlExtractor.getAreaMethodOfMomentsOfMFCCDeviation());
                dataDao.saveFeatures(song, xmlExtractor.getmFCCStandardDeviation());
                dataDao.saveFeatures(song, xmlExtractor.getStrengthOfStrongestBeatDeviation());
                dataDao.saveFeatures(song, xmlExtractor.getBeatSumStandardDeviation());
                dataDao.saveFeatures(song, xmlExtractor.getStrongestBeatDeviation());
                dataDao.saveFeatures(song, xmlExtractor.getSpectralCentroidDeviation());
                dataDao.saveFeatures(song, xmlExtractor.getmFCCOverallAverage());
                dataDao.saveFeatures(song, xmlExtractor.getStandardDeviationOfSpectralCentroidOverall());
                dataDao.saveFeatures(song, xmlExtractor.getSpectralCentroidOverallAverage());
            } catch (GenreException ex) {
                ex.printStackTrace();
            }
	}


}
