/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre.db;

import cz.cvut.fit.vmw.genre.crate.Song;
import cz.cvut.fit.vmw.genre.dial.GenreEnum;
import cz.cvut.fit.vmw.genre.exception.GenreException;
import java.util.List;

/**
 *
 * @author Jan Dufek
 */
public interface SongDAO {
    
    public int addSong(Song song) throws GenreException;
    
    public List<Song> getSongs(GenreEnum genre) throws GenreException;
    
}
