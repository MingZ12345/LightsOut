//declares the package the class is in
package gamePackage;

//imports the following libraries for class to use
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

//Start of StartScreen class that extends JPanel class and uses MouseListener and MouseMotionListener
public class StartScreen extends JPanel implements MouseListener, MouseMotionListener{

    //Initializes Image that stores background image for this panel
    private Image mainScreenImg;
    //Initializes polygon[] that stores the main button polygons;
    private Polygon mainButtonPolygonArray[] = new Polygon[2];

    //Instantiates each of the following class in this class
    private StartScreenListener startListener;
    UniversalValues universalValues = new UniversalValues();

    // =======================================================================
    // StartScreen method
    // Starts the start screen
    // No parameters
    // Returns void
    // =======================================================================
    public StartScreen() {
        setVisible(true);
        importImage();
        setButtonPolygon();
        int[] intScreenSizeArray = universalValues.screenSize();
        Dimension dim = getPreferredSize();
        dim.width = intScreenSizeArray[0] / 4 * 3;
        dim.height = intScreenSizeArray[1] / 4 * 3 - 60;
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(dim);
    }

    // =======================================================================
    // importImage method
    // Imports the image that needs to be used by this JPanel
    // No parameters
    // Returns void
    // =======================================================================
    public void importImage() {
        mainScreenImg  = new ImageIcon("MainPage.png").getImage();
    }

    // =======================================================================
    // setButtonPolygon method
    // Sets polygon for the amin buttons
    // No parameters
    // Returns void
    // =======================================================================
    public void setButtonPolygon() {
        int intX = 1250;
        int intY = 725;
        int[][] intXArray = new int[4][2];
        int[][] intYArray = new int[4][2];
        for(int i = 0; i < 2; i++) {
            int tempPolygonXArray[] = new int[4];
            int tempPolygonYArray[] = new int[4];
            intXArray[0][i] = intX;
            intXArray[1][i] = intX + 380;
            intXArray[2][i] = intX + 380;
            intXArray[3][i] = intX;
            intYArray[0][i] = intY + i * 400;
            intYArray[1][i] = intY + i * 400;
            intYArray[2][i] = intY + 150 + i * 400;
            intYArray[3][i] = intY + 150 + i * 400;
            for(int j = 0; j < 4; j++) {
                tempPolygonXArray[j] = intXArray[j][i];
                tempPolygonYArray[j] = intYArray[j][i];
            }
            mainButtonPolygonArray[i] = new Polygon(tempPolygonXArray, tempPolygonYArray, 4);
        }
    }

    // =======================================================================
    // paintComponent method
    // Paints any component that is inside the method
    // Graphics g parameter
    // Returns void
    // =======================================================================
    public void paintComponent(Graphics g) {
        g.drawImage(mainScreenImg, 0, 0, this);
    }

    // =======================================================================
    // insideButtonCheck method
    // Check if mouse is inside any clickable area
    // MouseEvent e parameter
    // Returns boolean
    // =======================================================================
    public boolean insideButtonCheck(MouseEvent e) {
        boolean insideButtonChecker = false;
        for(int i = 0; i < 2; i++) {
            insideButtonChecker = mainButtonPolygonArray[i].contains(e.getPoint());
            if(insideButtonChecker) {
                return true;
            }
        }
        return false;
    }

    // =======================================================================
    // mouseClicked method
    // Called when mouse is clicked, calls certain events when mouse is clicked with in the buttons
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseClicked(MouseEvent e) {
        if(mainButtonPolygonArray[0].contains(e.getPoint())) {
            int checker = 1;

            StartScreenEvent sSE = new StartScreenEvent(e, checker);

            if(startListener != null) {
                startListener.startScreenEventOccurred(sSE);
            }
        }
        else if(mainButtonPolygonArray[1].contains(e.getPoint())) {
            int checker = 4;

            StartScreenEvent sSE = new StartScreenEvent(e, checker);

            if(startListener != null) {
                startListener.startScreenEventOccurred(sSE);
            }

        }
    }

    // =======================================================================
    // mouseEntered method
    // Called when mouse is Entered
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    // =======================================================================
    // mouseExited
    // Called when mouse exits the frame
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseExited(MouseEvent e) {
    }

    // =======================================================================
    // mousePressed method
    // Called when mouse is pressed
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mousePressed(MouseEvent e) {
    }

    // =======================================================================
    // mouseReleased method
    // Called when mouse is released
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    // =======================================================================
    // mouseDragged method
    // Called when mouse is dragged
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    // =======================================================================
    // mouseMoved method
    // Called when mouse is moved, changes cursor shape when mouse is inside a pressable area
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseMoved(MouseEvent e) {
        if (!insideButtonCheck(e)) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        else {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

    }

    public void setStartScreenListener(StartScreenListener listener) {
        this.startListener = listener;
    }

}

