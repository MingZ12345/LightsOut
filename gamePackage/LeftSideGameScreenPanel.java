//declares the package the class is in
package gamePackage;

//imports the following libraries for class to use
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

//Start of LeftSideGameScreenPanel class that extends JPanel class
public class LeftSideGameScreenPanel extends JPanel{

    //Initializes Image that stores background image for this panel
    private Image leftSidePanelImg;
    //Initializes string that stores the number of moves
    String numMovesStr = "0";

    // =======================================================================
    // LeftSideGameScreenPanel method
    // Starts the left game screen panel
    // No parameters
    // Returns void
    // =======================================================================
    public LeftSideGameScreenPanel() {
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
        leftSidePanelImg  = new ImageIcon("Graphics/LeftSidePanel.png").getImage();
    }

    // =======================================================================
    // paintComponent method
    // Paints any component that is inside the method
    // Graphics g parameter
    // Returns void
    // =======================================================================
    public void paintComponent(Graphics g) {
        g.drawImage(leftSidePanelImg, 0, 0, this);
        g.setFont(new Font("Lucida", Font.PLAIN, 100));
        g.setColor(Color.white);
        g.drawString(numMovesStr, 200, 800);
    }

    // =======================================================================
    // changeNumMoves method
    // Changes the number of moves whenever it is called to numMoves
    // int numMoves parameter
    // Returns void
    // =======================================================================
    public void changeNumMoves(int numMoves) {
        numMovesStr = Integer.toString(numMoves);
        repaint();
    }
}

