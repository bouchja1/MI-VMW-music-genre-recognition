/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre.business.feature;

import cz.cvut.fit.vmw.genre.crate.Feature;
import cz.cvut.fit.vmw.genre.crate.Song;
import cz.cvut.fit.vmw.genre.db.DataDAO;
import cz.cvut.fit.vmw.genre.db.DataDAOImpl;
import cz.cvut.fit.vmw.genre.exception.GenreException;
import java.util.List;

/**
 *
 * @author Jan Dufek
 */
public class DbFeatureExtractor extends FeatureExtractor{

    public DbFeatureExtractor(Song song) throws GenreException {
        DataDAO dataDao = new DataDAOImpl();
        List<Feature> features = dataDao.getFeatures(song);
        
        filled = features.size() > 0;
        
        for (Feature feature : features) {
            switch (feature.getFeatureType()) {
                case AREA_METHOD_OF_MOMENTS_OF_MFCC_DEVIATION:
                    areaMethodOfMomentsOfMFCCDeviation.add(feature);
                    break;
                case BEAT_SUM_STANDARD_DEVIATION:
                    beatSumStandardDeviation.add(feature);
                    break;
                case MFCC:
                    mFCC.add(feature);
                    break;
                case MFCC_AREA_METHOD_MOMENTS:
                    mFCCAreaMethodMoments.add(feature);
                    break;
                case MFCC_BEAT_SUM:
                    mFCCBeatSum.add(feature);
                    break;
                case MFCC_DERIVATIVE_OF_SPECTRAL_CENTROID:
                    mFCCDerivativeOfSpectralCentroid.add(feature);
                    break;
                case MFCC_OVERALL_AVERAGE:
                    mFCCOverallAverage.add(feature);
                    break;
                case MFCC_RUNNING_MEAN_OF_SPECTRAL_CENTROID:
                    mFCCRunningMeanOfSpectralCentroid.add(feature);
                    break;
                case MFCC_SPECTRAL_CENTROID:
                    mFCCSpectralCentroid.add(feature);
                    break;
                case MFCC_STANDARD_DEVIATION:
                    mFCCStandardDeviation.add(feature);
                    break;
                case MFCC_STANDARD_DEVIATION_OF_SPECTRAL_CENTROID:
                    mFCCStandardDeviationOfSpectralCentroid.add(feature);
                    break;
                case MFCC_STRENGTH_OF_STRONGEST_BEAT:
                    mFCCStrengthOfStrongestBeat.add(feature);
                    break;
                case MFCC_STRONGEST_BEAT:
                    mFCCStrongestBeat.add(feature);
                    break;
                case SPECTRAL_CENTROID_DEVIATION:
                    spectralCentroidDeviation.add(feature);
                    break;
                case SPECTRAL_CENTROID_OVERALL_AVERAGE:
                    spectralCentroidOverallAverage.add(feature);
                    break;
                case STANDARD_DEVIATION_OF_SPECIAL_CENTROID_OVERALL:
                    standardDeviationOfSpectralCentroidOverall.add(feature);
                    break;
                case STRENGTH_OF_STRONGEST_BEAT_DEVIATION:
                    strengthOfStrongestBeatDeviation.add(feature);
                    break;
                case STRONGEST_BEAT_DEVIATION:
                    strongestBeatDeviation.add(feature);
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }
    
}
