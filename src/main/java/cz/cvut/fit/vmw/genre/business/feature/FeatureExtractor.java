/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre.business.feature;

import cz.cvut.fit.vmw.genre.crate.Feature;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan Dufek
 */
public abstract class FeatureExtractor {
        protected List<Feature> mFCCAreaMethodMoments = new ArrayList<>();
	protected List<Feature> mFCC = new ArrayList<>();
	protected List<Feature> mFCCStrengthOfStrongestBeat = new ArrayList<>();
	protected List<Feature> mFCCBeatSum = new ArrayList<>();
	protected List<Feature> mFCCStrongestBeat = new ArrayList<>();
	protected List<Feature> mFCCStandardDeviationOfSpectralCentroid = new ArrayList<>();
	protected List<Feature> mFCCSpectralCentroid = new ArrayList<>();
	protected List<Feature> mFCCRunningMeanOfSpectralCentroid = new ArrayList<>();
	protected List<Feature> mFCCDerivativeOfSpectralCentroid = new ArrayList<>();
	protected List<Feature> areaMethodOfMomentsOfMFCCDeviation = new ArrayList<>();
	protected List<Feature> mFCCStandardDeviation = new ArrayList<>();
	protected List<Feature> strengthOfStrongestBeatDeviation = new ArrayList<>();
	protected List<Feature> beatSumStandardDeviation = new ArrayList<>();
	protected List<Feature> strongestBeatDeviation = new ArrayList<>();
	protected List<Feature> spectralCentroidDeviation = new ArrayList<>();
	protected List<Feature> mFCCOverallAverage = new ArrayList<>();
	protected List<Feature> standardDeviationOfSpectralCentroidOverall = new ArrayList<>();
	protected List<Feature> spectralCentroidOverallAverage = new ArrayList<>();
        
        protected boolean filled;

    public List<Feature> getmFCCAreaMethodMoments() {
        return mFCCAreaMethodMoments;
    }

    public List<Feature> getmFCC() {
        return mFCC;
    }

    public List<Feature> getmFCCStrengthOfStrongestBeat() {
        return mFCCStrengthOfStrongestBeat;
    }

    public List<Feature> getmFCCBeatSum() {
        return mFCCBeatSum;
    }

    public List<Feature> getmFCCStrongestBeat() {
        return mFCCStrongestBeat;
    }

    public List<Feature> getmFCCStandardDeviationOfSpectralCentroid() {
        return mFCCStandardDeviationOfSpectralCentroid;
    }

    public List<Feature> getmFCCSpectralCentroid() {
        return mFCCSpectralCentroid;
    }

    public List<Feature> getmFCCRunningMeanOfSpectralCentroid() {
        return mFCCRunningMeanOfSpectralCentroid;
    }

    public List<Feature> getmFCCDerivativeOfSpectralCentroid() {
        return mFCCDerivativeOfSpectralCentroid;
    }

    public List<Feature> getAreaMethodOfMomentsOfMFCCDeviation() {
        return areaMethodOfMomentsOfMFCCDeviation;
    }

    public List<Feature> getmFCCStandardDeviation() {
        return mFCCStandardDeviation;
    }

    public List<Feature> getStrengthOfStrongestBeatDeviation() {
        return strengthOfStrongestBeatDeviation;
    }

    public boolean isFilled() {
        return filled;
    }
    
    

    public List<Feature> getBeatSumStandardDeviation() {
        return beatSumStandardDeviation;
    }

    public List<Feature> getStrongestBeatDeviation() {
        return strongestBeatDeviation;
    }

    public List<Feature> getSpectralCentroidDeviation() {
        return spectralCentroidDeviation;
    }

    public List<Feature> getmFCCOverallAverage() {
        return mFCCOverallAverage;
    }

    public List<Feature> getStandardDeviationOfSpectralCentroidOverall() {
        return standardDeviationOfSpectralCentroidOverall;
    }

    public List<Feature> getSpectralCentroidOverallAverage() {
        return spectralCentroidOverallAverage;
    }
        
        
}
