/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre;

import cz.cvut.fit.vmw.genre.dial.GenreEnum;
import cz.cvut.fit.vmw.genre.exception.GenreException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan Dufek
 */
public class Test {
    
    public static void main(String[] args) {
        try {
            GenreAnalyzator genreAnalyzator = new GenreAnalyzator();
            File file = new File("/home/johny/Stažené/vmw/jazz/Glenn Miller/1983 - In the mood/Stardust.mp3");
            boolean fileExists = file.exists();
           // genreAnalyzator.analyze(file, GenreEnum.SKA);
             genreAnalyzator.analyze(file);
           // System.out.println(result.getGenreId());
        } catch (GenreException ex) {
            ex.printStackTrace();
        }
    }
    
}
