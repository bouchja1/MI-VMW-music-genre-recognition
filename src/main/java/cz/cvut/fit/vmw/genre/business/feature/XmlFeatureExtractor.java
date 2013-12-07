package cz.cvut.fit.vmw.genre.business.feature;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import cz.cvut.fit.vmw.genre.crate.Feature;
import cz.cvut.fit.vmw.genre.dial.FeatureEnum;

public class XmlFeatureExtractor extends FeatureExtractor{

	private String returnedXml;

	
	public XmlFeatureExtractor(String returnedXml) {
		this.returnedXml = returnedXml;
                filled = true;
	}

	public void buildXml() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(
					returnedXml)));

			// Evaluate XPath against Document itself
			XPath xPath = XPathFactory.newInstance().newXPath();

			NodeList nodeList = getFeatures(xPath, document);

			// mine features from xml
			fillListsWithVectors(nodeList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillListsWithVectors(NodeList nodeList) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element e = (Element) nodeList.item(i);
			NodeList children = e.getChildNodes();
			for (int j = 0; j < children.getLength(); j++) {
				Node node = (Node) children.item(j);
				if (node.getTextContent().equals(
						"MFCC: Area Method of Moments of MFCCs")) {
					Node sibling = node.getNextSibling();
					getMFCCAreaMethodMoments(sibling.getNextSibling());
				} else if (node.getTextContent().equals("MFCC: MFCC")) {
					Node sibling = node.getNextSibling();
					getMFCC(sibling.getNextSibling());
				} else if (node.getTextContent().equals(
						"MFCC: Strength Of Strongest Beat")) {
					Node sibling = node.getNextSibling();
					getMFCCStrengthOfStrongestBeat(sibling.getNextSibling());
				} else if (node.getTextContent().equals("MFCC: Beat Sum")) {
					Node sibling = node.getNextSibling();
					getMFCCBeatSum(sibling.getNextSibling());
				} else if (node.getTextContent().equals("MFCC: Strongest Beat")) {
					Node sibling = node.getNextSibling();
					getMFCCStrongestBeat(sibling.getNextSibling());
				} else if (node.getTextContent().equals(
						"MFCC: Standard Deviation of Spectral Centroid")) {
					Node sibling = node.getNextSibling();
					getMFCCStandardDeviationOfSpectralCentroid(sibling
							.getNextSibling());
				} else if (node.getTextContent().equals(
						"MFCC: Spectral Centroid")) {
					Node sibling = node.getNextSibling();
					getMFCCSpectralCentroid(sibling.getNextSibling());
				} else if (node.getTextContent().equals(
						"MFCC: Running Mean of Spectral Centroid")) {
					Node sibling = node.getNextSibling();
					getMFCCRunningMeanOfSpectralCentroid(sibling
							.getNextSibling());
				} else if (node.getTextContent().equals(
						"MFCC: Derivative of Spectral Centroid")) {
					Node sibling = node.getNextSibling();
					getMFCCDerivativeOfSpectralCentroid(sibling
							.getNextSibling());
				} else if (node
						.getTextContent()
						.equals("Area Method of Moments of MFCCs Overall Standard Deviation")) {
					Node sibling = node.getNextSibling();
					getAreaMethodOfMomentsOfMFCCDeviation(sibling
							.getNextSibling());
				} else if (node.getTextContent().equals(
						"MFCC Overall Standard Deviation")) {
					Node sibling = node.getNextSibling();
					getMFCCStandardDeviation(sibling.getNextSibling());
				} else if (node
						.getTextContent()
						.equals("Strength Of Strongest Beat Overall Standard Deviation")) {
					Node sibling = node.getNextSibling();
					getStrengthOfStrongestBeatDeviation(sibling);
				} else if (node.getTextContent().equals(
						"Beat Sum Overall Standard Deviation")) {
					Node sibling = node.getNextSibling();
					getBeatSumStandardDeviation(sibling.getNextSibling());
				} else if (node.getTextContent().equals(
						"Strongest Beat Overall Standard Deviation")) {
					Node sibling = node.getNextSibling();
					getStrongestBeatDeviation(sibling.getNextSibling());
				} else if (node.getTextContent().equals(
						"Spectral Centroid Overall Standard Deviation")) {
					Node sibling = node.getNextSibling();
					getSpectralCentroidDeviation(sibling.getNextSibling());
				} else if (node.getTextContent().equals("MFCC Overall Average")) {
					Node sibling = node.getNextSibling();
					getMFCCOverallAverage(sibling.getNextSibling());
				} else if (node
						.getTextContent()
						.equals("Standard Deviation of Spectral Centroid Overall Average")) {
					Node sibling = node.getNextSibling();
					getStandardDeviationOfSpectralCentroidOverall(sibling
							.getNextSibling());
				} else if (node.getTextContent().equals(
						"Spectral Centroid Overall Average")) {
					Node sibling = node.getNextSibling();
					getSpectralCentroidOverallAverage(sibling.getNextSibling());
				}
			}
		}
	}

	private void getSpectralCentroidOverallAverage(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {			
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.SPECTRAL_CENTROID_OVERALL_AVERAGE, val);
			spectralCentroidOverallAverage.add(feature);
		}
		sibling = sibling.getNextSibling();
		getSpectralCentroidOverallAverage(sibling);
	}

	private void getStandardDeviationOfSpectralCentroidOverall(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.STANDARD_DEVIATION_OF_SPECIAL_CENTROID_OVERALL, val);
			standardDeviationOfSpectralCentroidOverall.add(feature);
		}
		sibling = sibling.getNextSibling();
		getStandardDeviationOfSpectralCentroidOverall(sibling);
	}

	private void getMFCCOverallAverage(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.MFCC_OVERALL_AVERAGE, val);
			mFCCOverallAverage.add(feature);
		}
		sibling = sibling.getNextSibling();
		getMFCCOverallAverage(sibling);
	}

	private void getSpectralCentroidDeviation(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.SPECTRAL_CENTROID_DEVIATION, val);
			spectralCentroidDeviation.add(feature);
		}
		sibling = sibling.getNextSibling();
		getSpectralCentroidDeviation(sibling);
	}

	private void getStrongestBeatDeviation(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.STRONGEST_BEAT_DEVIATION, val);
			strongestBeatDeviation.add(feature);
		}
		sibling = sibling.getNextSibling();
		getStrongestBeatDeviation(sibling);
	}

	private void getBeatSumStandardDeviation(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.BEAT_SUM_STANDARD_DEVIATION, val);
			beatSumStandardDeviation
					.add(feature);
		}
		sibling = sibling.getNextSibling();
		getBeatSumStandardDeviation(sibling);
	}

	private void getStrengthOfStrongestBeatDeviation(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.STRENGTH_OF_STRONGEST_BEAT_DEVIATION, val);
			strengthOfStrongestBeatDeviation.add(feature);
		}
		sibling = sibling.getNextSibling();
		getStrengthOfStrongestBeatDeviation(sibling);
	}

	private void getMFCCStandardDeviation(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.MFCC_STANDARD_DEVIATION, val);
			mFCCStandardDeviation.add(feature);
		}
		sibling = sibling.getNextSibling();
		getMFCCStandardDeviation(sibling);
	}

	private void getAreaMethodOfMomentsOfMFCCDeviation(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.AREA_METHOD_OF_MOMENTS_OF_MFCC_DEVIATION, val);
			areaMethodOfMomentsOfMFCCDeviation.add(feature);
		}
		sibling = sibling.getNextSibling();
		getAreaMethodOfMomentsOfMFCCDeviation(sibling);
	}

	private void getMFCCDerivativeOfSpectralCentroid(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.MFCC_DERIVATIVE_OF_SPECTRAL_CENTROID, val);
			mFCCDerivativeOfSpectralCentroid.add(feature);
		}
		sibling = sibling.getNextSibling();
		getMFCCDerivativeOfSpectralCentroid(sibling);
	}

	private void getMFCCRunningMeanOfSpectralCentroid(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.MFCC_RUNNING_MEAN_OF_SPECTRAL_CENTROID, val);
			mFCCRunningMeanOfSpectralCentroid.add(feature);
		}
		sibling = sibling.getNextSibling();
		getMFCCRunningMeanOfSpectralCentroid(sibling);
	}

	private void getMFCCSpectralCentroid(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.MFCC_SPECTRAL_CENTROID, val);		
			mFCCSpectralCentroid.add(feature);
		}
		sibling = sibling.getNextSibling();
		getMFCCSpectralCentroid(sibling);
	}

	private void getMFCCStandardDeviationOfSpectralCentroid(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.MFCC_STANDARD_DEVIATION_OF_SPECTRAL_CENTROID, val);
			mFCCStandardDeviationOfSpectralCentroid.add(feature);
		}
		sibling = sibling.getNextSibling();
		getMFCCStandardDeviationOfSpectralCentroid(sibling);
	}

	private void getMFCCStrongestBeat(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.MFCC_STRONGEST_BEAT, val);
			mFCCStrongestBeat.add(feature);
		}
		sibling = sibling.getNextSibling();
		getMFCCStrongestBeat(sibling);
	}

	private void getMFCCBeatSum(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.MFCC_BEAT_SUM, val);
			mFCCBeatSum.add(feature);
		}
		sibling = sibling.getNextSibling();
		getMFCCBeatSum(sibling);
	}

	private void getMFCCStrengthOfStrongestBeat(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.MFCC_STRENGTH_OF_STRONGEST_BEAT, val);
			mFCCStrengthOfStrongestBeat.add(feature);
		}
		sibling = sibling.getNextSibling();
		getMFCCStrengthOfStrongestBeat(sibling);
	}

	private void getMFCC(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.MFCC, val);
			mFCC.add(feature);
		}
		sibling = sibling.getNextSibling();
		getMFCC(sibling);
	}

	private void getMFCCAreaMethodMoments(Node sibling) {
		if (sibling == null)
			return;
		String vSib = sibling.getNodeName();
		if (vSib.equals("v")) {
			DecimalFormat df = new DecimalFormat();
			Number num = df.parse(sibling.getFirstChild().getNodeValue(), new ParsePosition(0));
			double val = num.doubleValue();						
			Feature feature = new Feature(FeatureEnum.MFCC_AREA_METHOD_MOMENTS, val);
			mFCCAreaMethodMoments.add(feature);
		}
		sibling = sibling.getNextSibling();
		getMFCCAreaMethodMoments(sibling);
	}

	private NodeList getFeatures(XPath xPath, Document document)
			throws XPathExpressionException {
		NodeList nodes = (NodeList) xPath.evaluate("//feature",
				document.getDocumentElement(), XPathConstants.NODESET);
		return nodes;
	}

	
	
}
