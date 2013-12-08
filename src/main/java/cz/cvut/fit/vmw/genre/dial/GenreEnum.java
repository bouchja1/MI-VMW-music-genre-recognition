/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre.dial;

import static cz.cvut.fit.vmw.genre.dial.GenreEnum.CHODSKA;
import static cz.cvut.fit.vmw.genre.dial.GenreEnum.DEATH_METAL;
import static cz.cvut.fit.vmw.genre.dial.GenreEnum.DECHOVKA;
import static cz.cvut.fit.vmw.genre.dial.GenreEnum.HIPHOP;
import static cz.cvut.fit.vmw.genre.dial.GenreEnum.JAZZ;
import static cz.cvut.fit.vmw.genre.dial.GenreEnum.ROCK;
import static cz.cvut.fit.vmw.genre.dial.GenreEnum.SKA;
import static cz.cvut.fit.vmw.genre.dial.GenreEnum.TECHNO;
import static cz.cvut.fit.vmw.genre.dial.GenreEnum.VAZNA_HUDBA;
import cz.cvut.fit.vmw.genre.exception.GenreException;
import java.util.ArrayList;

/**
 *
 * @author Jan Dufek
 */
public enum GenreEnum {
    DECHOVKA (1, "Dechovka"),
    DEATH_METAL (2, "Death metal"),
    JAZZ (3, "Jazz"),
    SKA (4, "Ska"),
    TECHNO (5, "Techno"),
    VAZNA_HUDBA(6, "Vážná hudba"),
    ROCK (7, "Rock"),
    CHODSKA (8, "Chodská"),
    HIPHOP(9, "Hip Hop");
    
    private Integer genreId;
    private String name;
    
	
    public GenreEnum[] getUsedGenres(){
        return new GenreEnum[]{DECHOVKA, DEATH_METAL, JAZZ, SKA, HIPHOP};
    }
    
    private GenreEnum(Integer genreId, String name) {
            this.genreId = genreId;
            this.name = name;
    }
    
    public GenreEnum getGenreById (Integer genreId) throws GenreException{
        switch (genreId){
            case 1: return DECHOVKA;
            case 2: return DEATH_METAL;
            case 3: return JAZZ;
            case 4: return SKA;
            case 5: return TECHNO;
            case 6: return VAZNA_HUDBA;
            case 7: return ROCK;
            case 8: return CHODSKA;
            case 9: return HIPHOP;
        }
        throw new GenreException("Genre ID " + genreId + " was not found");
    }

    public Integer getGenreId() {
            return genreId;
    }
}
