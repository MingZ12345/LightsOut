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
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

//Start of scoreBoard class that extends JFrame class and uses MouseListener and MouseMotionListener
public class ScoreBoard extends JPanel implements MouseListener, MouseMotionListener{

    //Initializes 1D string array that stores the ranking in string
    private String[] rankingStr = new String[13];
    //Initializes 1D int array that stores the ranking in int
    private int[] rankingInt = new int[13];
    //Initializes image that stores the background image
    private Image rankingImg;
    //Initializes polygon that stores the menu button polygon
    private Polygon menuButton;

    //Instantiates each of the following class in this class
    private StartScreenListener startListener;
    UniversalValues universalValues = new UniversalValues();

    // =======================================================================
    // ScoreBoard method
    // Starts the score board panel
    // No parameters
    // Returns void
    // =======================================================================
    public ScoreBoard() throws IOException {
        for(int i = 0; i < 10; i++) {
            rankingStr[i] = ("");
        }
        importImage();
        setButtonPolygon();
        readFile();
        int[] intScreenSizeArray = universalValues.screenSize();
        Dimension dim = getPreferredSize();
        dim.width = intScreenSizeArray[0];
        dim.height = intScreenSizeArray[1];
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(dim);
    }

    // =======================================================================
    // readFile method
    // Reads the file that stores the rankings
    // No parameters
    // Returns void
    // =======================================================================
    public void readFile() throws IOException {
        for(int i = 2; i < 12; i++) {
            rankingStr[i] = Files.readAllLines(Paths.get("Ranking/ranking.txt")).get(i - 2);
        }
    }

    // =======================================================================
    // importImage method
    // Imports the image that needs to be used by this JPanel
    // No parameters
    // Returns void
    // =======================================================================
    public void importImage() {
        rankingImg  = new ImageIcon("Graphics/Ranking.png").getImage();
    }

    // =======================================================================
    // setButtonPolygon method
    // Sets the polygon for menu button
    // No parameters
    // Returns void
    // =======================================================================
    public void setButtonPolygon() {
        int[] intScreenSizeArray = universalValues.screenSize();
        int[] intXArray = new int[4];
        int[] intYArray = new int[4];
        int intX = intScreenSizeArray[0] - 346;
        int intY = 0;
        intXArray[0] = intX;
        intXArray[1] = intX + 346;
        intXArray[2] = intX + 346;
        intXArray[3] = intX;
        intYArray[0] = intY;
        intYArray[1] = intY;
        intYArray[2] = intY + 150;
        intYArray[3] = intY + 150;
        menuButton = new Polygon(intXArray, intYArray, 4);
    }

    // =======================================================================
    // paintComponent method
    // Paints the components in the method
    // Graphics g parameters
    // Returns void
    // =======================================================================
    public void paintComponent(Graphics g) {
        g.drawImage(rankingImg, 0, 0, this);
        g.setFont(new Font("Lucida", Font.PLAIN, 100));
        g.setColor(Color.white);
        for(int i = 2; i < 7; i++) {
            g.drawString(rankingStr[i], 500, 630 + (i - 2) * 195);
        }
        for(int i = 7; i < 12; i++) {
            g.drawString(rankingStr[i], 2000, 630 + (i - 7) * 195);
        }

    }

    // =======================================================================
    // insideButtonCheck method
    // Checks if mouse is inside the button area
    // MouseEvent e parameter
    // Returns boolean
    // =======================================================================
    public boolean insideButtonCheck(MouseEvent e) {
        boolean insideButtonChecker = false;
        insideButtonChecker = menuButton.contains(e.getPoint());
        return insideButtonChecker;
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
        if(menuButton.contains(e.getPoint())) {
            int checker = 0;

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
    // setStartScreenListener method
    // Listens to actions when called
    // StartScreenListener listener parameter
    // Returns void
    // =======================================================================
    public void setStartScreenListener(StartScreenListener listener) {
        this.startListener = listener;
    }
}

