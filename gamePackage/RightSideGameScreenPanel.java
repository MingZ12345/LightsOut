//declares the package the class is in
package gamePackage;

//imports the following libraries for class to use
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

//Start of RightSideGameScreenPanel class that extends JPanel class
public class RightSideGameScreenPanel extends JPanel{

    //Initializes Image that stores background image for this panel
    private Image rightSidePanelImg;

    // =======================================================================
    // RightSideGameScreenPanel method
    // Starts the right game screen panel
    // No parameters
    // Returns void
    // =======================================================================
    public RightSideGameScreenPanel() {
        importImage();
        Dimension dim = getPreferredSize();
        dim.width = 500;
        dim.height = 1000;
        setPreferredSize(dim);
    }

    // =======================================================================
    // importImage method
    // Imports the image that needs to be used by this JPanel
    // No parameters
    // Returns void
    // =======================================================================
    public void importImage() {
        rightSidePanelImg  = new ImageIcon("RightSidePanel.png").getImage();
    }

    // =======================================================================
    // paintComponent method
    // Paints any component that is inside the method
    // Graphics g parameter
    // Returns void
    // =======================================================================
    public void paintComponent(Graphics g) {
        g.drawImage(rightSidePanelImg, 0, 0, this);
    }




}

