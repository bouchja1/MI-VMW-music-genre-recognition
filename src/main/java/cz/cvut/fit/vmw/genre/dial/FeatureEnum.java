package cz.cvut.fit.vmw.genre.dial;

import cz.cvut.fit.vmw.genre.exception.GenreException;

public enum FeatureEnum {
	
	MFCC_AREA_METHOD_MOMENTS(1),
	MFCC(2),
	MFCC_STRENGTH_OF_STRONGEST_BEAT(3),
	MFCC_BEAT_SUM(4),
	MFCC_STRONGEST_BEAT(5),
	MFCC_STANDARD_DEVIATION_OF_SPECTRAL_CENTROID(6),
	MFCC_SPECTRAL_CENTROID(7),
	MFCC_RUNNING_MEAN_OF_SPECTRAL_CENTROID(8),
	MFCC_DERIVATIVE_OF_SPECTRAL_CENTROID(9),
	AREA_METHOD_OF_MOMENTS_OF_MFCC_DEVIATION(10),
	MFCC_STANDARD_DEVIATION(11),
	STRENGTH_OF_STRONGEST_BEAT_DEVIATION(12),
	BEAT_SUM_STANDARD_DEVIATION(13),
	STRONGEST_BEAT_DEVIATION(14),
	SPECTRAL_CENTROID_DEVIATION(15),
	MFCC_OVERALL_AVERAGE(16),
	STANDARD_DEVIATION_OF_SPECIAL_CENTROID_OVERALL(17),
	SPECTRAL_CENTROID_OVERALL_AVERAGE(18);
	
	private Integer featureId;
	
	private FeatureEnum(Integer featureId) {
		this.featureId = featureId;
	}
	
	public Integer getFeatureId() {
		return featureId;
	}
        
        public static FeatureEnum getFeatureEnum(Integer featureId) throws GenreException{
            for(FeatureEnum f : values()){
                if (f.getFeatureId() == featureId){
                    return f;
                }
            }
            throw new GenreException("Feature with ID " + featureId + "doesnt exist");
        }

}
