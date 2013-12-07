/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre.db;

import cz.cvut.fit.vmw.genre.crate.Feature;
import cz.cvut.fit.vmw.genre.crate.Song;
import cz.cvut.fit.vmw.genre.exception.GenreException;
import java.util.List;

/**
 *
 * @author Jan Dufek
 */
public interface DataDAO {
    
    public boolean saveFeatures(Song song, List<Feature> features) throws GenreException;
    
    public List<Feature> getFeatures(Song song) throws GenreException;
    
}
