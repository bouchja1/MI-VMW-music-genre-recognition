/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre.crate;

import cz.cvut.fit.vmw.genre.db.SongDAO;
import cz.cvut.fit.vmw.genre.db.SongDAOImpl;
import cz.cvut.fit.vmw.genre.dial.GenreEnum;
import cz.cvut.fit.vmw.genre.exception.GenreException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Jan Dufek
 */
public class Song {

    private GenreEnum genre;
    private String src;
    private int songId;

    private double distance;
    
    public Song(String src) throws GenreException {
        this.src = src;
        
        SongDAO songDao = new SongDAOImpl();
        songDao.addSong(this);
    }
    
    public Song(String src, GenreEnum genre) throws GenreException {
        this.src = src;
        this.genre = genre;
        SongDAO songDao = new SongDAOImpl();
        songId = songDao.addSong(this);

    }
    
    public Song (Integer songId, String src, GenreEnum genre){
        this.src = src;
        this.songId = songId;
        this.genre = genre;
    }
    
    
    public boolean hasRecognizedGenre(){
        return genre != null;
    }

    public GenreEnum getGenre() {
        return genre;
    }

    public void setGenre(GenreEnum genre) {
        this.genre = genre;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getSongId() {
        return songId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    
    
}
