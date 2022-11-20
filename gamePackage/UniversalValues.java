//declares the package the class is in
package gamePackage;

//imports the following libraries for class to use
import java.awt.Dimension;
import java.awt.Toolkit;

//Start of UniversalValuesClass class that extends JPanel class
public class UniversalValues{

    // =======================================================================
    // screenSize method
    // Gets the user's screen size in pixels
    // No parameters
    // Returns int[]
    // =======================================================================
    public int[] screenSize() {
        int[] intScreenSizeArray = new int[2];
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        intScreenSizeArray[0] = screenSize.width * 3 / 4;
        intScreenSizeArray[1] = screenSize.height * 27 / 40 - 60;
        return intScreenSizeArray;
    }
}
