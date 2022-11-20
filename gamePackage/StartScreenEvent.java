//declares the package the class is in
package gamePackage;

//imports the following libraries for class to use
import java.util.EventObject;

//Start of Frame that extends EventObject
public class StartScreenEvent extends EventObject{

    //Initializes int checker that calls different JPanels
    private int checker;

    // =======================================================================
    // StartScreenEvent method
    // Checks if the game is solved
    // Object source, int checker parameters
    // Returns null
    // =======================================================================
    public StartScreenEvent(Object source, int checker) {
        super(source);
        this.checker = checker;
    }

    public int getChecker() {
        return checker;
    }

    public void setChecker(int checker) {
        this.checker = checker;
    }
}
