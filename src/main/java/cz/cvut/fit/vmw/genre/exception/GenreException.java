/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre.exception;

/**
 *
 * @author Jan Dufek
 */
public class GenreException extends Exception{

    public GenreException(String message) {
        super(message);
    }

    public GenreException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenreException(Throwable cause) {
        super(cause);
    }
    
    
    
}
