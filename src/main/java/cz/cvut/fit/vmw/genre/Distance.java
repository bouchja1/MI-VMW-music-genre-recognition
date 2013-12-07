/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre;

import cz.cvut.fit.vmw.genre.business.feature.FeatureExtractor;
import cz.cvut.fit.vmw.genre.crate.Feature;
import java.util.List;

/**
 *
 * @author Jan Dufek
 */
public class Distance {
    
    public static double getDistance(FeatureExtractor a, FeatureExtractor b){
        double sum = 0;
        sum += euclidusDistance(a.getBeatSumStandardDeviation(), b.getBeatSumStandardDeviation()) / 188;
        sum += euclidusDistance(a.getSpectralCentroidDeviation(), b.getSpectralCentroidDeviation()) / 17;
        sum += euclidusDistance(a.getmFCCDerivativeOfSpectralCentroid(), b.getmFCCDerivativeOfSpectralCentroid()) / 18;
        sum += euclidusDistance(a.getmFCCRunningMeanOfSpectralCentroid(), b.getmFCCRunningMeanOfSpectralCentroid()) / 11;
        sum += euclidusDistance(a.getmFCCStandardDeviation(), b.getmFCCStandardDeviation()) / 43;
        sum += euclidusDistance(a.getmFCCStandardDeviationOfSpectralCentroid(), b.getmFCCStandardDeviationOfSpectralCentroid()) / 19;
        return sum;
    }
    
    public static double euclidusDistance(List<Feature> v1, List<Feature> v2){
        int n = v1.size();
        if (v1.size() > v2.size()){
            n = v2.size();
        }
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += Math.pow(v1.get(i).getValue() - v2.get(i).getValue(), 2);
        }
        return Math.sqrt(sum);
    }
    
    public static void testing(FeatureExtractor a, FeatureExtractor b){
        System.out.print(euclidusDistance(a.getBeatSumStandardDeviation(), b.getBeatSumStandardDeviation()) + "\t");
        System.out.print(euclidusDistance(a.getSpectralCentroidDeviation(), b.getSpectralCentroidDeviation()) + "\t");
        System.out.print(euclidusDistance(a.getStandardDeviationOfSpectralCentroidOverall(), b.getStandardDeviationOfSpectralCentroidOverall()) + "\t");
        System.out.print(euclidusDistance(a.getmFCC(), b.getmFCC()) + "\t");
        System.out.print(euclidusDistance(a.getmFCCDerivativeOfSpectralCentroid(), b.getmFCCDerivativeOfSpectralCentroid()) + "\t");
        System.out.print(euclidusDistance(a.getmFCCOverallAverage(), b.getmFCCOverallAverage()) + "\t");
        System.out.print(euclidusDistance(a.getmFCCRunningMeanOfSpectralCentroid(), b.getmFCCRunningMeanOfSpectralCentroid()) + "\t");
        System.out.print(euclidusDistance(a.getmFCCSpectralCentroid(), b.getmFCCSpectralCentroid()) + "\t");
        System.out.print(euclidusDistance(a.getmFCCStandardDeviation(), b.getmFCCStandardDeviation()) + "\t");
        System.out.print(euclidusDistance(a.getmFCCStandardDeviationOfSpectralCentroid(), b.getmFCCStandardDeviationOfSpectralCentroid()) + "\t");
        System.out.print(euclidusDistance(a.getmFCCStrengthOfStrongestBeat(), b.getmFCCStrengthOfStrongestBeat()) + "\t");
        System.out.print(euclidusDistance(a.getmFCCStrongestBeat(), b.getmFCCStrongestBeat()) + "\t");
        System.out.println("");
    }
    
}
