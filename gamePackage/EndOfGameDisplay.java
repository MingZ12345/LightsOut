//declares the package the class is in
package gamePackage;

//imports the following libraries for class to use
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

//Start of scoreBoard class that extends JPanel class and uses MouseListener and MouseMotionListener
public class EndOfGameDisplay extends JPanel implements MouseListener, MouseMotionListener{

    //Initializes Image that stores background image for this panel
    private Image endOfGameImg;
    //Initializes 1D polygon array that stores the option button polygon
    private Polygon[] buttonPolygonArray = new Polygon[2];
    //Initializes string that stores the string that will be displayed
    private String outputStr;
    //Initializes string that stores the number of moves used in string
    private String numMovesStr;

    //Instantiates each of the following class in this class
    private StartScreenListener startListener;
    private LeftSideGameScreenPanel LeftSideGameScreenPanel;
    UniversalValues universalValues = new UniversalValues();

    // =======================================================================
    // EndOfGameDisplay method
    // Starts the end of game display panel
    // No parameters
    // Returns void
    // =======================================================================
    public EndOfGameDisplay() {
        importImage();
        setButtonPolygon();
        int[] intScreenSizeArray = universalValues.screenSize();
        LeftSideGameScreenPanel = new LeftSideGameScreenPanel();
        setMoves(11);
        setString();
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
        endOfGameImg = new ImageIcon("EndGameScreen.png").getImage();
    }

    // =======================================================================
    // setButtonPolygon method
    // Sets polygon for the option buttons
    // No parameters
    // Returns void
    // =======================================================================
    public void setButtonPolygon() {
        int intX = 380;
        int intY = 1000;
        int[][] intXArray = new int[4][2];
        int[][] intYArray = new int[4][2];
        for(int i = 0; i < 2; i++) {
            int tempPolygonXArray[] = new int[4];
            int tempPolygonYArray[] = new int[4];
            intXArray[0][i] = intX + i * 1520;
            intXArray[1][i] = intX + 600 + i * 1520;
            intXArray[2][i] = intX + 600 + i * 1520;
            intXArray[3][i] = intX + i * 1520;
            intYArray[0][i] = intY;
            intYArray[1][i] = intY;
            intYArray[2][i] = intY + 150;
            intYArray[3][i] = intY + 150;
            for(int j = 0; j < 4; j++) {
                tempPolygonXArray[j] = intXArray[j][i];
                tempPolygonYArray[j] = intYArray[j][i];
            }
            buttonPolygonArray[i] = new Polygon(tempPolygonXArray, tempPolygonYArray, 4);
        }
    }

    // =======================================================================
    // paintComponent method
    // Paints any componenet that is inside the method
    // Graphics g parameter
    // Returns void
    // =======================================================================
    public void paintComponent(Graphics g) {
        g.drawImage(endOfGameImg , 0, 0, this);
        g.setFont(new Font("Lucida", Font.PLAIN, 150));
        g.setColor(Color.white);
        g.drawString(outputStr, 650, 500);
    }

    // =======================================================================
    // setMoves method
    // Change numMoves to numMoves when method is called
    // int numMoves parameter
    // Returns void
    // =======================================================================
    public void setMoves(int numMoves) {
        numMovesStr = Integer.toString(numMoves);
        repaint();
    }

    // =======================================================================
    // setString method
    // Set the displayed string
    // No parameters
    // Returns void
    // =======================================================================
    public void setString() {
        outputStr = ("It took " + numMovesStr + " moves to win!");
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
            insideButtonChecker = buttonPolygonArray[i].contains(e.getPoint());
            if(insideButtonChecker) {
                return true;
            }
        }
        return false;
    }

    // =======================================================================
    // mouseDragged method
    // Called when mouse is dragged
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    // =======================================================================
    // mouseMoved method
    // Called when mouse is moved, changes cursor shape when mouse is inside a pressable area
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseMoved(MouseEvent e) {
        if (insideButtonCheck(e) == false) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        else {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

    }

    // =======================================================================
    // mouseClicked method
    // Called when mouse is clicked, calls certain events when mouse is clicked with in the option button
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseClicked(MouseEvent e) {
        if(buttonPolygonArray[0].contains(e.getPoint())) {
            int checker = 2;

            StartScreenEvent sSE = new StartScreenEvent(e, checker);

            if(startListener != null) {
                startListener.startScreenEventOccurred(sSE);
            }

        }
        else if(buttonPolygonArray[1].contains(e.getPoint())) {
            int checker = 5;
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
    public void mouseEntered(MouseEvent arg0) {
    }

    // =======================================================================
    // mouseExited
    // Called when mouse exits the frame
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    // =======================================================================
    // mousePressed method
    // Called when mouse is pressed
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    // =======================================================================
    // mouseReleased method
    // Called when mouse is released
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    // =======================================================================
    // setStartScreenListener method
    // Listens to actions when called
    // StartScreenListener listener parameter
    // Returns void
    // =======================================================================
    public void setStartScreenListener(StartScreenListener listener) {
        this.startListener = listener;
    }
}

