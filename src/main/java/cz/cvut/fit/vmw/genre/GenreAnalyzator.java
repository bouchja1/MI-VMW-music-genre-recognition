/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre;

import cz.cvut.fit.vmw.genre.analyzing.Distance;
import cz.cvut.fit.vmw.genre.business.feature.Extractor;
import cz.cvut.fit.vmw.genre.business.FeaturesDescriptorMiner;
import cz.cvut.fit.vmw.genre.business.feature.DbFeatureExtractor;
import cz.cvut.fit.vmw.genre.business.feature.FeatureExtractor;
import cz.cvut.fit.vmw.genre.crate.Song;
import cz.cvut.fit.vmw.genre.db.SongDAO;
import cz.cvut.fit.vmw.genre.db.SongDAOImpl;
import cz.cvut.fit.vmw.genre.dial.GenreEnum;
import cz.cvut.fit.vmw.genre.exception.GenreException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Jan Dufek
 */
public class GenreAnalyzator {
    
    private static final int N = 5;
    
    public Map<GenreEnum, Double> analyze (File file) throws GenreException{
        
        FeatureExtractor fileExtractor = getFileExtractor(file);
        
        Map<GenreEnum, Double> result = new HashMap<>();
        double totalCoef = 0;
        
        for (GenreEnum genre : GenreEnum.values()) {
            
            List<Song> songs = getTrainedData(genre);
            
            int count = 0;
            int songsCount = 0;
            
            for (Song song : songs) {
                FeatureExtractor dbFeatureExtractor = new DbFeatureExtractor(song);
                if (!dbFeatureExtractor.isFilled()){
                    continue;
                }
                songsCount++;
                double distance = Distance.getDistance(fileExtractor, dbFeatureExtractor);

                song.setDistance(distance);
                if (distance < N){
                    count ++;
                }
            }
            if (songsCount == 0) continue;
            
            System.out.println(genre.getGenreId() + " - " + count);
            
            result.put(genre, (double)count/songsCount);
            
            totalCoef += (double)count/songsCount;
            
        }
        
        for (Entry<GenreEnum, Double> entry: result.entrySet()) {
            entry.setValue(entry.getValue()/totalCoef);
        }
        
        return result;
        
    }
    
     public void analyze (File file, GenreEnum genre) throws GenreException{
        
        FeatureExtractor fileExtractor = getFileExtractor(file);
            
        List<Song> songs = getTrainedData(genre);

        int count = 0;
        int songsCount = 0;

        for (Song song : songs) {
            FeatureExtractor dbFeatureExtractor = new DbFeatureExtractor(song);
            if (!dbFeatureExtractor.isFilled()){
                continue;
            }
            songsCount++;
            double distance = Distance.getDistance(fileExtractor, dbFeatureExtractor);
            System.out.println(distance);
            song.setDistance(distance);
            if (distance >= N){
                count ++;
            }
        }

        
    }
    
    private List<Song> getTrainedData(GenreEnum genre) throws GenreException{
        SongDAO songDao = new SongDAOImpl();
        return songDao.getSongs(genre);
    }
    
    private FeatureExtractor getFileExtractor(File file){
        FeaturesDescriptorMiner featuresMiner = new FeaturesDescriptorMiner(
                            file);
            try {
                   // RecordingInfo recordingInfo = featuresMiner.addRecording();

                    Song song = new Song(file.getName());
                    Extractor extractor = new Extractor(file, song);
                    FeatureExtractor featuresExtractor = extractor.getFeatures();
                    return featuresExtractor;
            } catch (Exception e1) {
                    e1.printStackTrace();
            }
            return null;
    }
    
}
