package cz.cvut.fit.vmw.genre;

import java.io.File;

import cz.cvut.fit.vmw.genre.business.FeaturesDescriptorMiner;
import cz.cvut.fit.vmw.genre.business.feature.FeatureExtractor;
import cz.cvut.fit.vmw.genre.crate.Song;
import cz.cvut.fit.vmw.genre.dial.GenreEnum;
import jAudioFeatureExtractor.DataTypes.RecordingInfo;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
            try {
                String dir = "/home/johny/Stažené/vmw/Vážná/";
                FileInputStream fstream = new FileInputStream(dir + "songlist");
                
                 DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                //Read File Line By Line
                while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                    System.out.println (strLine);
                    extractFile(dir + strLine, GenreEnum.VAZNA_HUDBA);
                }
                //Close the input stream
                in.close();
                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex){
                ex.printStackTrace();
            }
	}
        
        private static void extractFile(String filePath, GenreEnum genre){
            File file = new File(filePath);
            if (!file.exists()){
                System.out.println("File does not exist");
                return;
            }
            FeaturesDescriptorMiner featuresMiner = new FeaturesDescriptorMiner(
                            file);
            try {
                    RecordingInfo recordingInfo = featuresMiner.addRecording();

                    Song song = new Song(file.getName(), genre);
                    Extractor extractor = new Extractor(file, song);
                    FeatureExtractor featuresExtractor = extractor.getFeatures();
                    if (featuresExtractor != null) extractor.saveFeaturesToDb(song, featuresExtractor);
            } catch (Exception e1) {
                    e1.printStackTrace();
            }
        }
}
