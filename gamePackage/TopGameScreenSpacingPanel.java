//declares the package the class is in
package gamePackage;

//imports the following libraries for class to use
import java.awt.Dimension;
import javax.swing.JPanel;

//Start of TopGameScreenSpacingPanel class that extends JPanel class
public class TopGameScreenSpacingPanel extends JPanel {

    // =======================================================================
    // TopGameScreenSpacingPanel method
    // Starts the top game screen panel
    // No parameters
    // Returns void
    // =======================================================================
    public TopGameScreenSpacingPanel() {
        Dimension dim = getPreferredSize();
        dim.width = 2880;
        dim.height = 350;
        setPreferredSize(dim);
        setOpaque(false);
    }
}