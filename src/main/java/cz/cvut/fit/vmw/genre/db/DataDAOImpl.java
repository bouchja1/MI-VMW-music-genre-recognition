/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre.db;

import cz.cvut.fit.vmw.genre.crate.Feature;
import cz.cvut.fit.vmw.genre.crate.Song;
import cz.cvut.fit.vmw.genre.dial.FeatureEnum;
import cz.cvut.fit.vmw.genre.exception.GenreException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan Dufek
 */
public class DataDAOImpl implements DataDAO{
    private PreparedStatement insertDataPS;
    private PreparedStatement selectDataPS;

    public DataDAOImpl() {
        try {
            Db db = Db.getInstance();
            insertDataPS = db.prepareStatement("INSERT INTO data (song_id, feature_id, vector_id, value) VALUES (?, ?, ?, ?)");
            selectDataPS = db.prepareStatement("SELECT feature_id, value FROM data WHERE song_id = ?");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean saveFeatures(Song song, List<Feature> features) throws GenreException {
        try {
            int i = 1;
            for (Feature feature : features) {
                insertDataPS.clearParameters();
                insertDataPS.setInt(1, song.getSongId());
                insertDataPS.setInt(2, feature.getFeatureType().getFeatureId());
                insertDataPS.setInt(3, i++);
                insertDataPS.setDouble(4, feature.getValue());
                insertDataPS.execute();
            }
            return true;
        } catch (SQLException ex) {
            throw new GenreException(ex);
        }
        
    }
    
    @Override
    public List<Feature> getFeatures(Song song) throws GenreException{
        try {
            selectDataPS.clearParameters();
            selectDataPS.setInt(1, song.getSongId());
            ResultSet rs = selectDataPS.executeQuery();
            List<Feature> features = new ArrayList<>();
            while (rs.next()){
                Integer featureId = rs.getInt("feature_id");
                Double value = rs.getDouble("value");
                features.add(new Feature(FeatureEnum.getFeatureEnum(featureId), value));
            }
            return features;
        } catch (SQLException ex) {
            throw new GenreException(ex);
        }
    }
}
