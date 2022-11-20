//declares the package the class is in
package gamePackage;

//imports the following libraries for class to use
import java.awt.Dimension;

import javax.swing.JPanel;

//Start of SpacingPanel class that extends JPanel class
public class SpacingPanel extends JPanel{

    //Instantiates each of the following class in this class
    UniversalValues universalValues = new UniversalValues();

    // =======================================================================
    // SpacingPanel method
    // Starts the spacing panel
    // No parameters
    // Returns void
    // =======================================================================
    public SpacingPanel() {
        int[] intScreenSizeArray = universalValues.screenSize();
        Dimension dim = getPreferredSize();
        dim.width = intScreenSizeArray[0] * 3 / 4;
        dim.height = intScreenSizeArray[1] - 700;
        setPreferredSize(dim);
        setOpaque(false);
    }
}
