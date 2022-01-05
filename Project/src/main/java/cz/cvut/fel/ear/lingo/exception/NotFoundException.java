package cz.cvut.fel.ear.lingo.exception;

/**
 * @author Mukan Atazhanov
 * Created on 06-Nov-21
 */

public class NotFoundException extends LingoException{

    public NotFoundException(String message){
        super(message);
    }

    public static NotFoundException create(String resourceName, Object identifier) {
        return new NotFoundException(resourceName + " identified by " + identifier + " not found.");
    }
}
