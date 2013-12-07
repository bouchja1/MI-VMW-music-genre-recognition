/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre.db;

import cz.cvut.fit.vmw.genre.crate.Feature;
import cz.cvut.fit.vmw.genre.crate.Song;
import cz.cvut.fit.vmw.genre.dial.FeatureEnum;
import cz.cvut.fit.vmw.genre.dial.GenreEnum;
import cz.cvut.fit.vmw.genre.exception.GenreException;
import java.sql.PreparedStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan Dufek
 */
public class SongDAOImpl implements SongDAO{

    
    @Override
    public int addSong(Song song) throws GenreException{
        try {
            Db db = Db.getInstance();
            PreparedStatement statement = null;
            if (song.hasRecognizedGenre()){
                statement = db.prepareStatement("INSERT INTO song (genre_id, src) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, song.getGenre().getGenreId());
                statement.setString(2, Math.random() + "-" + song.getSrc());
            } else {
                statement = db.prepareStatement("INSERT INTO song (src) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, Math.random() + "-" + song.getSrc());
            }
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getInt(1);
        } catch (SQLException ex) {
            throw new GenreException(ex);
        }
    }

    @Override
    public List<Song> getSongs(GenreEnum genre) throws GenreException{
        try {
            Db db = Db.getInstance();
            PreparedStatement ps = 
                    db.prepareStatement("SELECT song_id, src FROM song WHERE genre_id = ?");
            ps.setInt(1, genre.getGenreId());
            ResultSet rs = ps.executeQuery();
            List<Song> songs = new ArrayList<>();
            while (rs.next()){
                Song song = new Song(rs.getInt("song_id"), rs.getString("src"), genre);

                songs.add(song);
            }
            return songs;
        } catch (SQLException ex) {
            throw new GenreException(ex);
        }
    }
    
}
