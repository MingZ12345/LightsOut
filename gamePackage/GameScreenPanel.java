//declares the package the class is in
package gamePackage;

//imports the following libraries for class to use
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.util.Random;

//Start of Frame that extends JPanel class and uses MouseListener and MouseMotionListener
public class GameScreenPanel extends JPanel implements MouseListener, MouseMotionListener{

    //Initializes 2D int array that determines what state the game buttons are in
    private int buttonStateArray[][] = new int[7][7];
    //Initializes 3D int array that stores the location of the game buttons
    private int buttonLocationArray[][][] = new int [2][5][5];
    //Initializes 4D int array that stores the coordinates of game button polygons
    private int buttonPolygonCoordinatesArray[][][][] = new int[8][2][5][5];
    //Initializes 1D int array that temporarily stores which button is pressed
    private int tempListenedToComponentXY[] = new int[2];
    //Initializes int that temporarily stores which side button is clicked
    private int tempListenedtoComponentSideButton = 0;
    //Initializes 3D int array that stores the coordinates of the side button polygons
    private int rectangleButtonPolygonLocationArray[][][] = new int[2][7][4];
    //Initializes 1D string array that stores the old rankings
    private String[] rankingStrOld = new String[13];
    //Initializes 1D string array that stores the new rankings
    private String[] rankingStrNew = new String[13];
    //Initializes 1D int array that stores the rankings as int
    private int[] rankingInt = new int[13];
    //Initializes int that tracks number of moves used
    int numMoves = 0;
    //Initializes 2D boolean array that stores the on and off state of game buttons
    private boolean buttonOnOffStateArray[][] = new boolean[7][7];
    //Initializes 2D boolean array that stores the on and off state of game buttons as a clone
    private boolean cloneOfButtonOnOffStateArray[][] = new boolean[7][7];
    //Initializes 2D boolean array that stores which button should be clicked to solve
    private boolean buttonSolveArray[][] = new boolean [7][7];
    //Initializes 2D boolean array that stores the on and off state of game buttons as a clone
    private boolean cloneOfbuttonSolveArray[][] = new boolean [7][7];
    //Initializes boolean that determines if solve mode is on or off
    private boolean buttonSolveMode;
    //Initializes 3D image array that stores all the images that is used for all the game buttons
    private Image buttonImgArray[][][] = new Image[8][5][5];
    //Initializes 2D image array that stores all the images used for one game button
    private Image buttonImgBasicArray[] = new Image[8];
    //Initializes 2D polygon array that sets the polygon for game buttons
    private Polygon buttonPolygonArray[][] = new Polygon[5][5];
    //Initializes 1D polygon array that sets the polygon for the side option buttons
    private Polygon rectangleButtonPolygonArray[] = new Polygon[4];
    ////Initializes Image that stores images used for background and title
    private Image backgroundImg, titleImg;

    //Instantiates each of the following class in this class
    private RightSideGameScreenPanel RightSideGameScreenPanel;
    private LeftSideGameScreenPanel LeftSideGameScreenPanel;
    private TopGameScreenSpacingPanel TopGameScreenSpacingPanel;
    private StartScreenListener startListener;
    private EndOfGameDisplay endOfGameDisplay;
    UniversalValues universalValues = new UniversalValues();
    Random randomGenerator = new Random();
    FileFunctions fileFunctions = new FileFunctions();

    // =======================================================================
    // GameScreenPanel method
    // Starts the main game panel
    // No parameters
    // Returns void
    // =======================================================================
    public GameScreenPanel() {
        initiate();
        int[] intScreenSizeArray = universalValues.screenSize();
        RightSideGameScreenPanel = new RightSideGameScreenPanel();
        LeftSideGameScreenPanel = new LeftSideGameScreenPanel();
        TopGameScreenSpacingPanel = new TopGameScreenSpacingPanel();
        endOfGameDisplay = new EndOfGameDisplay();
        addMouseListener(this);
        addMouseMotionListener(this);
        Dimension dim = getPreferredSize();
        dim.width = intScreenSizeArray[0];
        dim.height = intScreenSizeArray[1];
        setPreferredSize(dim);
        setLayout(new BorderLayout());
        add(RightSideGameScreenPanel, BorderLayout.EAST);
        add(LeftSideGameScreenPanel, BorderLayout.WEST);
        add(TopGameScreenSpacingPanel, BorderLayout.NORTH);
    }

    // =======================================================================
    // checkIfSolved method
    // Checks if the game is solved
    // No parameters
    // Returns boolean
    // =======================================================================
    public boolean checkIfSolved() {
        boolean solved = true;
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                solved = !buttonOnOffStateArray[x + 1][y + 1];
                if(!solved) {
                    return false;
                }
            }
        }
        return true;
    }

    // =======================================================================
    // setPolygon method
    // Makes the polygon for the side option buttons
    // No parameters
    // Returns void
    // =======================================================================
    public void setPolygon() {
        int intX = 2457;
        int intY = 475;
        int kY = 0;
        for(int i = 0; i < 3; i++) {
            kY = i * 350;
            int tempPolygonXArray[] = new int[4];
            int tempPolygonYArray[] = new int[4];
            rectangleButtonPolygonLocationArray[0][i][0] = intX;
            rectangleButtonPolygonLocationArray[0][i][1] = intX + 346;
            rectangleButtonPolygonLocationArray[0][i][2] = intX + 346;
            rectangleButtonPolygonLocationArray[0][i][3] = intX;
            rectangleButtonPolygonLocationArray[1][i][0] = intY + kY;
            rectangleButtonPolygonLocationArray[1][i][1] = intY + kY;
            rectangleButtonPolygonLocationArray[1][i][2] = intY + kY + 150;
            rectangleButtonPolygonLocationArray[1][i][3] = intY + kY + 150;
            for(int j = 0; j < 4; j++) {
                tempPolygonXArray[j] = rectangleButtonPolygonLocationArray[0][i][j];
                tempPolygonYArray[j] = rectangleButtonPolygonLocationArray[1][i][j];
            }
            rectangleButtonPolygonArray[i] = new Polygon(tempPolygonXArray, tempPolygonYArray, 4);
        }
    }

    // =======================================================================
    // initSolveButton method
    // Turns on the solve game mode and turns on the solve lights
    // No parameters
    // Returns void
    // =======================================================================
    public void initSolveButton() {
        buttonSolveMode = true;
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                if(buttonSolveArray[x + 1][y + 1] == true) {
                    buttonStateArray[x + 1][y + 1]  = stateSwapperTurnOnSolveLight(buttonStateArray[x + 1][y + 1] );
                }
            }
        }
    }

    // =======================================================================
    // clickedStateSwapper method
    // XOR gate that processes the incoming button boolean value
    // boolean k, currentValue parameter
    // Returns boolean
    // =======================================================================
    public boolean clickedStateSwapper(boolean k, boolean currentValue) {
        return k^currentValue;
    }

    // =======================================================================
    // resetButtonOnOff method
    // Resets and randomizes which buttons are on and off
    // No parameters
    // void
    // =======================================================================
    public void resetButtonOnOff() {
        boolean k;
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                buttonOnOffStateArray[x + 1][y + 1] = false;
                buttonSolveArray[x + 1][y + 1] = false;
            }
        }
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                k = randomGenerator.nextBoolean();
                if(k == true) {
                    buttonStateChangerOnOffBool(x + 1, y + 1);
                    buttonSolveArray[x + 1][y + 1] = k;
                }

            }
        }
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                cloneOfButtonOnOffStateArray[x + 1][y + 1] = buttonOnOffStateArray[x + 1][y + 1];
                cloneOfbuttonSolveArray[x + 1][y + 1] = buttonSolveArray[x + 1][y + 1];
            }
        }


    }

    // =======================================================================
    // resetButtonsOnOffPreviouse method
    // Resets buttons to the state of the previous game that was just solved
    // No parameters
    // void
    // =======================================================================
    public void resetButtonOnOffPrevious() {
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                buttonOnOffStateArray[x + 1][y + 1] = cloneOfButtonOnOffStateArray[x + 1][y + 1];
                buttonSolveArray[x + 1][y + 1] = cloneOfbuttonSolveArray[x + 1][y + 1];
            }
        }
    }

    // =======================================================================
    // resetButtons method
    // resets buttons by randomizing it or set it the same as the board that was just solved
    // int check parameters
    // Returns void
    // =======================================================================
    public void resetButtons(int check) {
        if(check == 0) {
            resetButtonOnOff();
        }
        else if(check == 1) {
            resetButtonOnOffPrevious();
        }
        buttonSolveMode = false;
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                if(buttonOnOffStateArray[x + 1][y + 1] == false) {
                    buttonStateArray[x + 1][y + 1] = 4;
                }
                else if(buttonOnOffStateArray[x + 1][y + 1] == true) {
                    buttonStateArray[x + 1][y + 1] = 0;
                }
            }
        }
        numMoves = 0;
    }

    // =======================================================================
    // paintComponent method
    // Paints the components that is in the method
    // Graphics g parameter
    // Returns void
    // =======================================================================
    public void paintComponent(Graphics g) {
        int[] intScreenSizeArray = universalValues.screenSize();
        g.drawImage(backgroundImg, 0, 0, this);
        g.drawImage(titleImg, intScreenSizeArray[0] / 4 + 125, intScreenSizeArray[1] / 20, this);

        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                g.drawImage(buttonImgArray[buttonStateArray[x + 1][y + 1]][x][y], buttonLocationArray[0][x][y], buttonLocationArray[1][x][y], this);
            }
        }

    }

    // =======================================================================
    // importImage method
    // Imports the images that need to be used by the program
    // No parameters
    // Returns void
    // =======================================================================
    public void importImage() {
        backgroundImg  = new ImageIcon("Graphics/background.png").getImage();
        titleImg = new ImageIcon("Graphics/Title.png").getImage();
        buttonImgBasicArray[0]= new ImageIcon("Graphics/GameButtonOn.png").getImage();
        buttonImgBasicArray[1]= new ImageIcon("Graphics/GameButtonOnPressed.png").getImage();
        buttonImgBasicArray[2]= new ImageIcon("Graphics/GameButtonOnWithSolutionRing.png").getImage();
        buttonImgBasicArray[3]= new ImageIcon("Graphics/GameButtonOnPressedWithSolutionRing.png").getImage();
        buttonImgBasicArray[4]= new ImageIcon("Graphics/GameButtonOff.png").getImage();
        buttonImgBasicArray[5]= new ImageIcon("Graphics/GameButtonOffPressed.png").getImage();
        buttonImgBasicArray[6]= new ImageIcon("Graphics/GameButtonOffWithSolutionRing.png").getImage();
        buttonImgBasicArray[7]= new ImageIcon("Graphics/GameButtonOffPressedWithSolutionRing.png").getImage();
    }

    // =======================================================================
    // setButton method
    // Sets of the coordinates of game button polygon
    // No parameters
    // Returns void
    // =======================================================================
    public void setButton() {
        int[] intScreenSizeArray = universalValues.screenSize();
        tempListenedToComponentXY[0] = 0;
        tempListenedToComponentXY[1] = 0;
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                int buttonX = intScreenSizeArray[0] / 4 + 145 + x * 250;
                int buttonY = 350 + y * 250;
                int tempPolygonXArray[] = new int[8];
                int tempPolygonYArray[] = new int[8];
                buttonLocationArray[0][x][y] = buttonX;
                buttonLocationArray[1][x][y] = buttonY;
                buttonPolygonCoordinatesArray[0][0][x][y] = buttonX + 51;
                buttonPolygonCoordinatesArray[1][0][x][y] = buttonX + 101;
                buttonPolygonCoordinatesArray[2][0][x][y] = buttonX + 137;
                buttonPolygonCoordinatesArray[3][0][x][y] = buttonX + 137;
                buttonPolygonCoordinatesArray[4][0][x][y] = buttonX + 101;
                buttonPolygonCoordinatesArray[5][0][x][y] = buttonX + 51;
                buttonPolygonCoordinatesArray[6][0][x][y] = buttonX + 15;
                buttonPolygonCoordinatesArray[7][0][x][y] = buttonX + 15;
                buttonPolygonCoordinatesArray[0][1][x][y] = buttonY;
                buttonPolygonCoordinatesArray[1][1][x][y] = buttonY;
                buttonPolygonCoordinatesArray[2][1][x][y] = buttonY + 36;
                buttonPolygonCoordinatesArray[3][1][x][y] = buttonY + 68;
                buttonPolygonCoordinatesArray[4][1][x][y] = buttonY + 103;
                buttonPolygonCoordinatesArray[5][1][x][y] = buttonY + 103;
                buttonPolygonCoordinatesArray[6][1][x][y] = buttonY + 68;
                buttonPolygonCoordinatesArray[7][1][x][y] = buttonY + 36;
                for(int z = 0; z < 8; z++) {
                    tempPolygonXArray[z] = buttonPolygonCoordinatesArray[z][0][x][y];
                    tempPolygonYArray[z] = buttonPolygonCoordinatesArray[z][1][x][y];
                    buttonImgArray[z][x][y] = buttonImgBasicArray[z];
                }
                buttonPolygonArray[x][y] = new Polygon(tempPolygonXArray, tempPolygonYArray, 8);
            }
        }
        resetButtons(0);
    }

    // =======================================================================
    // initiate method
    // Initializes the game for it to be playable
    // No parameters
    // Returns void
    // =======================================================================
    public void initiate() {
        importImage();
        setButton();
        setPolygon();
    }

    // =======================================================================
    // stateSwapperOnOffBool method
    // Swap the boolean state of game button
    // boolean state parameter
    // Returns boolean
    // =======================================================================
    public boolean stateSwapperOnOffBool(boolean state) {
        state = !state;
        return state;
    }

    // =======================================================================
    // stateSwapperTurnOnSolveLight method
    // Swap the state of the game buttons so the solve game light is on
    // int state parameter
    // Returns int
    // =======================================================================
    public int stateSwapperTurnOnSolveLight(int state) {
        if(state == 0) {
            state = 2;
        }
        else if(state == 4) {
            state = 6;
        }
        return state;
    }

    // =======================================================================
    // stateSwapperOnOff method
    // Swap the state of the game buttons so the button turn on or off regardless if the solve light is no or not
    // int state parameter
    // Returns int
    // =======================================================================
    public int stateSwapperOnOff(int state) {
        if(state == 0) {
            state = 4;
        }
        else if(state == 4) {
            state = 0;
        }
        else if(state == 2) {
            state = 6;
        }
        else if(state == 6) {
            state = 2;
        }
        return state;
    }

    // =======================================================================
    // stateSwapperPressedNormal method
    // Swap the state of the game buttons so the buttons look like it is pressed down without solve light on
    // int state parameter
    // Returns int
    // =======================================================================
    public int stateSwapperPressedNormal(int state) {
        if(state == 0) {
            state = 1;
        }
        else if(state == 4) {
            state = 5;
        }
        return state;
    }

    // =======================================================================
    // stateSwapperReleasedNormal method
    // Swap the state of the game buttons so the buttons look like it is released from a pressed position without solve light on
    // int state parameter
    // Returns int
    // =======================================================================
    public int stateSwapperReleasedNormal(int state) {
        if(state == 1) {
            state = 4;
        }
        else if(state == 5) {
            state = 0;
        }
        return state;
    }

    // =======================================================================
    // stateSwapperReleasedNormal method
    // Swap the state of the game buttons so the buttons return to orginal state when released outside of the button without solve light on
    // int state parameter
    // Returns int
    // =======================================================================
    public int stateSwapperReleasedOutNormal(int state) {
        if(state == 1) {
            state = 0;
        }
        if(state == 5) {
            state = 4;
        }
        return state;
    }

    // =======================================================================
    // stateSwapperPressedWithSolveLight method
    // Swap the state of the game buttons so the buttons look like it is pressed with solve light
    // int state parameter
    // Returns int
    // =======================================================================
    public int stateSwapperPressedWithSolveLight(int state) {
        if(state == 2) {
            state = 3;
        }
        else if(state == 6) {
            state = 7;
        }
        else if(state == 0) {
            state = 1;
        }
        else if(state == 4) {
            state = 5;
        }
        return state;
    }

    // =======================================================================
    // stateSwapperReleasedWithSolveLight method
    // Swap the state of the game buttons so the buttons look like it is released from a pressed position with solve light
    // int state parameter
    // Returns int
    // =======================================================================
    public int stateSwapperReleasedWithSolveLight(int state) {
        if(state == 3) {
            state = 4;
        }
        else if(state == 7) {
            state = 0;
        }
        else if(state == 1) {
            state = 4;
        }
        else if(state == 5) {
            state = 0;
        }
        return state;
    }

    // =======================================================================
    // stateSwapperReleasedWithSolveLight method
    // Swap the state of the game buttons so the buttons return to original state when released outside of button with solve light
    // int state parameter
    // Returns int
    // =======================================================================
    public int stateSwapperReleasedOutWithSolveLight(int state) {
        if(state == 3) {
            state = 2;
        }
        else if(state == 7) {
            state = 6;
        }
        else if(state == 1) {
            state = 0;
        }
        else if(state == 5) {
            state = 4;
        }
        return state;
    }

    // =======================================================================
    // buttonStateChangerOnOff method
    // Swap the button state of the pressed game button and neighboring buttons without solve light
    // int x, y parameter
    // Returns void
    // =======================================================================
    public void buttonStateChangerOnOff(int x, int y) {
        buttonStateArray[x][y] = stateSwapperReleasedNormal(buttonStateArray[x][y]);
        buttonStateArray[x + 1][y] = stateSwapperOnOff(buttonStateArray[x + 1][y]);
        buttonStateArray[x][y + 1] = stateSwapperOnOff(buttonStateArray[x][y + 1]);
        buttonStateArray[x - 1][y] = stateSwapperOnOff(buttonStateArray[x - 1][y]);
        buttonStateArray[x][y - 1] = stateSwapperOnOff(buttonStateArray[x][y - 1]);
    }

    // =======================================================================
    // buttonStateChangerOnOff method
    // Swap the button state of the pressed game button and neighboring buttons with solve light
    // int x, y parameter
    // Returns void
    // =======================================================================
    public void buttonStateChangerOnOffSolveLight(int x, int y) {
        buttonStateArray[x][y] = stateSwapperReleasedWithSolveLight(buttonStateArray[x][y]);
        buttonStateArray[x + 1][y] = stateSwapperOnOff(buttonStateArray[x + 1][y]);
        buttonStateArray[x][y + 1] = stateSwapperOnOff(buttonStateArray[x][y + 1]);
        buttonStateArray[x - 1][y] = stateSwapperOnOff(buttonStateArray[x - 1][y]);
        buttonStateArray[x][y - 1] = stateSwapperOnOff(buttonStateArray[x][y - 1]);
    }

    // =======================================================================
    // buttonStateChangerOnOffBool method
    // Swap the boolean state of the pressed game button and neighboring buttons
    // int x, y parameter
    // Returns void
    // =======================================================================
    public void buttonStateChangerOnOffBool(int x, int y) {
        buttonOnOffStateArray[x][y] = stateSwapperOnOffBool(buttonOnOffStateArray[x][y]);
        buttonOnOffStateArray[x + 1][y] = stateSwapperOnOffBool(buttonOnOffStateArray[x + 1][y]);
        buttonOnOffStateArray[x][y + 1] = stateSwapperOnOffBool(buttonOnOffStateArray[x][y + 1]);
        buttonOnOffStateArray[x - 1][y] = stateSwapperOnOffBool(buttonOnOffStateArray[x - 1][y]);
        buttonOnOffStateArray[x][y - 1] = stateSwapperOnOffBool(buttonOnOffStateArray[x][y - 1]);
    }

    // =======================================================================
    // insideButtonCheck method
    // Check if mouse is inside any of the buttons
    // MouseEvent e parameter
    // Returns boolean
    // =======================================================================
    public boolean insideButtonCheck(MouseEvent e) {
        boolean insideButtonChecker = false;
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                insideButtonChecker = buttonPolygonArray[x][y].contains(e.getPoint());
                if(insideButtonChecker == true) {
                    return insideButtonChecker;
                }
            }
        }
        for(int i = 0; i < 3; i++) {
            insideButtonChecker = rectangleButtonPolygonArray[i].contains(e.getPoint());
            if(insideButtonChecker == true) {
                return insideButtonChecker;
            }
        }
        return insideButtonChecker;
    }

    // =======================================================================
    // buttonStateChangerPressedWithSolveLight method
    // Changes the state of buttons when it is pressed with solve light
    // int x, y parameter
    // Returns void
    // =======================================================================
    public void buttonStateChangerPressedWithSolveLight(int x, int y) {
        buttonStateArray[x][y] = stateSwapperPressedWithSolveLight(buttonStateArray[x][y]);
    }

    // =======================================================================
    // buttonStateChangerReleasedOutWithSolveLight method
    // Changes the state of buttons when it is released outside of the button with solve light
    // int x, y parameter
    // Returns void
    // =======================================================================
    public void buttonStateChangerReleasedOutWithSolveLight(int x, int y) {
        buttonStateArray[x][y] = stateSwapperReleasedOutWithSolveLight(buttonStateArray[x][y]);
    }

    // =======================================================================
    // buttonStateChangerReleasedOutWithSolveLight method
    // Changes the state of buttons when it is released outside of the button with solve light
    // int x, y parameter
    // Returns void
    // =======================================================================
    public void buttonStateChangerPressed(int x, int y) {
        buttonStateArray[x][y] = stateSwapperPressedNormal(buttonStateArray[x][y]);
    }

    // =======================================================================
    // buttonStateChangerReleasedOut method
    // Changes the state of buttons when it is released outside of the button without solve light
    // int x, y parameter
    // Returns void
    // =======================================================================
    public void buttonStateChangerReleasedOut(int x, int y) {
        buttonStateArray[x][y] = stateSwapperReleasedOutNormal(buttonStateArray[x][y]);
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
    // Called when mouse is clicked, calls certain events when mouse is clicked with in the three option buttons
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseClicked(MouseEvent e) {
        if(rectangleButtonPolygonArray[0].contains(e.getPoint())){

            int checker = 0;

            StartScreenEvent sSE = new StartScreenEvent(e, checker);

            if(startListener != null) {
                startListener.startScreenEventOccurred(sSE);
            }
        }
        else if(rectangleButtonPolygonArray[2].contains(e.getPoint())) {
            initSolveButton();
            repaint();
            tempListenedtoComponentSideButton = 4;
        }
        else if(rectangleButtonPolygonArray[1].contains(e.getPoint())) {
            resetButtons(0);
            repaint();
            numMoves = 0;
            LeftSideGameScreenPanel.changeNumMoves(numMoves);
            tempListenedtoComponentSideButton = 4;
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
    // Called when mouse is pressed, calls game buttons to change to pressed state if mouse is pressed inside button
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mousePressed(MouseEvent e) {
        if(!buttonSolveMode) {
            for(int x = 0; x < 5; x++) {
                for(int y = 0; y < 5; y++) {
                    if (buttonPolygonArray[x][y].contains(e.getPoint())) {
                        tempListenedToComponentXY[0] = x;
                        tempListenedToComponentXY[1] = y;
                        buttonStateChangerPressed(tempListenedToComponentXY[0] + 1, tempListenedToComponentXY[1] + 1);
                        repaint();
                    }
                }
            }
        }
        else if(buttonSolveMode){
            for(int x = 0; x < 5; x++) {
                for(int y = 0; y < 5; y++) {
                    if (buttonPolygonArray[x][y].contains(e.getPoint())) {
                        tempListenedToComponentXY[0] = x;
                        tempListenedToComponentXY[1] = y;
                        buttonStateChangerPressedWithSolveLight(tempListenedToComponentXY[0] + 1, tempListenedToComponentXY[1] + 1);
                        repaint();
                    }
                }
            }

        }

    }

    // =======================================================================
    // mousePressed method
    // Called when mouse is pressed, calls game buttons to change state
    // MouseEvent e parameter
    // Returns void
    // =======================================================================
    @Override
    public void mouseReleased(MouseEvent e) {
        if(!buttonSolveMode) {
            if (buttonPolygonArray[tempListenedToComponentXY[0]][tempListenedToComponentXY[1]].contains(e.getPoint())) {
                buttonStateChangerOnOff(tempListenedToComponentXY[0] + 1, tempListenedToComponentXY[1] + 1);
                buttonStateChangerOnOffBool(tempListenedToComponentXY[0] + 1, tempListenedToComponentXY[1] + 1);
                buttonSolveArray[tempListenedToComponentXY[0] + 1][tempListenedToComponentXY[1] + 1] = stateSwapperOnOffBool(buttonSolveArray[tempListenedToComponentXY[0] + 1][tempListenedToComponentXY[1] + 1]);
                if(checkIfSolved()) {
                    int checker = 3;
                    numMoves = numMoves + 1;
                    endOfGameDisplay.setMoves(numMoves);
                    try {
                        changeFile();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    StartScreenEvent sSE = new StartScreenEvent(e, checker);

                    if(startListener != null) {
                        startListener.startScreenEventOccurred(sSE);
                    }
                }
                repaint();
                numMoves = numMoves + 1;
                LeftSideGameScreenPanel.changeNumMoves(numMoves);
                tempListenedToComponentXY[0] = 0;
                tempListenedToComponentXY[1] = 0;

            }
            else if (buttonPolygonArray[tempListenedToComponentXY[0]][tempListenedToComponentXY[1]].contains(e.getPoint()) == false){
                buttonStateChangerReleasedOut(tempListenedToComponentXY[0] + 1, tempListenedToComponentXY[1] + 1);
                repaint();
                tempListenedToComponentXY[0] = 0;
                tempListenedToComponentXY[1] = 0;
            }

        }

        else if(buttonSolveMode){
            if (buttonPolygonArray[tempListenedToComponentXY[0]][tempListenedToComponentXY[1]].contains(e.getPoint())) {
                buttonStateChangerOnOffSolveLight(tempListenedToComponentXY[0] + 1, tempListenedToComponentXY[1] + 1);
                buttonStateChangerOnOffBool(tempListenedToComponentXY[0] + 1, tempListenedToComponentXY[1] + 1);
                buttonSolveArray[tempListenedToComponentXY[0] + 1][tempListenedToComponentXY[1] + 1] = stateSwapperOnOffBool(buttonSolveArray[tempListenedToComponentXY[0] + 1][tempListenedToComponentXY[1] + 1]);
                if(checkIfSolved()) {
                    int checker = 3;
                    numMoves = numMoves + 1;
                    endOfGameDisplay.setMoves(numMoves);
                    StartScreenEvent sSE = new StartScreenEvent(e, checker);
                    try {
                        changeFile();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    if(startListener != null) {
                        startListener.startScreenEventOccurred(sSE);
                    }
                }
                initSolveButton();
                repaint();
                numMoves = numMoves + 1;
                LeftSideGameScreenPanel.changeNumMoves(numMoves);
                tempListenedToComponentXY[0] = 0;
                tempListenedToComponentXY[1] = 0;

            }
            else if (buttonPolygonArray[tempListenedToComponentXY[0]][tempListenedToComponentXY[1]].contains(e.getPoint()) == false){
                buttonStateChangerReleasedOutWithSolveLight(tempListenedToComponentXY[0] + 1, tempListenedToComponentXY[1] + 1);
                repaint();
                tempListenedToComponentXY[0] = 0;
                tempListenedToComponentXY[1] = 0;
            }

        }
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

    // =======================================================================
    // changeFiles method
    // Changes the ranking
    // No parameter
    // Returns void
    // =======================================================================
    public void changeFile() throws IOException {
        for(int i = 2; i < 12; i++) {
            rankingStrNew[i] = Files.readAllLines(Paths.get("ranking.txt")).get(i - 2);
            rankingStrOld[i] = Files.readAllLines(Paths.get("ranking.txt")).get(i - 2);
            if(rankingInt[i - 2] == 0) {
                rankingInt[i - 2] = Integer.parseInt(rankingStrOld[i]);
                rankingInt[i - 2] = Integer.parseInt(rankingStrNew[i]);
            }
            else {
                rankingInt[i - 2] = Integer.parseInt(rankingStrNew[i]);
            }

        }
        rankingInt[10] = numMoves;
        bubbleSort();
        for(int i = 2; i < 12; i++) {
            rankingStrNew[i] = Integer.toString(rankingInt[i - 2]);

            fileFunctions.modifyFile("../ranking.txt", rankingStrOld[i], rankingStrNew[i]);
        }
    }

    // =======================================================================
    // bubbleSort method
    // Sorts the ranking with the most recent rank
    // No parameter
    // Returns void
    // =======================================================================
    public void bubbleSort() {
        boolean flag = true;
        int temp;
        for(byte i = 0; i < 11; i++) {
            for(byte j = 0; j <  11 - i; j++) {
                if(rankingInt[j] > rankingInt[j+1]) {
                    flag = false;
                    temp = rankingInt[j];
                    rankingInt[j] = rankingInt[j+1];
                    rankingInt[j+1] = temp;
                }
            }
            if(flag == true) {
                break;
            }
            else {
                flag = true;
            }
        }
    }
}


