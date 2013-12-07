package cz.cvut.fit.vmw.genre.crate;

import cz.cvut.fit.vmw.genre.dial.FeatureEnum;

public class Feature {
	private FeatureEnum featureType;
	private double value;
	
	public Feature(FeatureEnum featureType, double value) {
		this.featureType = featureType;
		this.value = value;
	}

	public FeatureEnum getFeatureType() {
		return featureType;
	}

	public void setFeatureType(FeatureEnum featureType) {
		this.featureType = featureType;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Feature [featureType=" + featureType + ", value=" + value + "]";
	}
	
	
}
