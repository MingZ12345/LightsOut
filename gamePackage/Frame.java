//declares the package the class is in
package gamePackage;

//imports the following libraries for class to use
import java.awt.*;
import java.io.IOException;
import javax.swing.JFrame;

//Start of Frame that extends JPanel class
public class Frame extends JFrame {

    //Instantiates each of the following class in this class
    private GameScreenPanel gameScreenPanel;
    private StartScreen startScreen;
    private ScoreBoard scoreBoard;
    private EnterNamePanel enterNamePanel;
    private EndOfGameDisplay endOfGameDisplay;
    UniversalValues universalValues = new UniversalValues();

    // =======================================================================
    // Frame method
    // Starts the frame of the game and add JPanels
    // No parameters
    // Returns void
    // =======================================================================
    public Frame() throws IOException {

        //Names the game frame
        super("Lights Out");

        //Uses border layout in this frame
        setLayout(new BorderLayout());

        //Instantiates each of the following method
        gameScreenPanel = new GameScreenPanel();
        startScreen = new StartScreen();
        scoreBoard = new ScoreBoard();
        enterNamePanel = new EnterNamePanel();
        endOfGameDisplay = new EndOfGameDisplay();

        //Intiates screensize array that take screen size
        int[] intScreenSizeArray = universalValues.screenSize();

        //Sets the basic properties of the frame

        //Calls method callPanel
        callPanel(0);

        //sets action listener for the following imported class to change between the JPanels being displayed
        startScreen.setStartScreenListener(new StartScreenListener() {
            public void startScreenEventOccurred(StartScreenEvent e) {
                callPanel(e.getChecker());
            }
        });

        gameScreenPanel.setStartScreenListener(new StartScreenListener() {
            public void startScreenEventOccurred(StartScreenEvent e) {
                callPanel(e.getChecker());
            }
        });

        scoreBoard.setStartScreenListener(new StartScreenListener() {
            public void startScreenEventOccurred(StartScreenEvent e) {
                callPanel(e.getChecker());
            }
        });

        enterNamePanel.setStartScreenListener(new StartScreenListener() {
            public void startScreenEventOccurred(StartScreenEvent e) {
                callPanel(e.getChecker());
            }
        });

        endOfGameDisplay.setStartScreenListener(new StartScreenListener() {
            public void startScreenEventOccurred(StartScreenEvent e) {
                callPanel(e.getChecker());
            }
        });
        Dimension dim = getPreferredSize();
        dim.width = intScreenSizeArray[0];
        dim.height = intScreenSizeArray[1];
        setSize(dim);
        setVisible(true);
        setLocationRelativeTo(null);

    }

    // =======================================================================
    // callPanel method
    // Checks which JPanel is called and call the JPanel
    // int checker parameter
    // Returns void
    // =======================================================================
    public void callPanel(int checker) {
        //When check is 0, call startScreenPanel
        if(checker == 0) {
            remove(gameScreenPanel);
            remove(scoreBoard);
            remove(endOfGameDisplay);
            remove(enterNamePanel);
            add(startScreen, BorderLayout.SOUTH);
            validate();
            startScreen.repaint();
        }
        //When check is 1, call enterNamePanel
        else if(checker == 1) {
            remove(startScreen);
            add(enterNamePanel, BorderLayout.SOUTH);
            validate();
            enterNamePanel.repaint();
        }
        //When check is 2, call gameScreenPanel and reset board
        else if(checker == 2) {
            remove(enterNamePanel);
            remove(endOfGameDisplay);
            add(gameScreenPanel, BorderLayout.SOUTH);
            gameScreenPanel.resetButtons(0);
            validate();
            gameScreenPanel.repaint();
        }
        //When check is 5, call gameScreenPanel and set board to previous board
        else if(checker == 5) {
            remove(enterNamePanel);
            remove(endOfGameDisplay);
            add(gameScreenPanel, BorderLayout.SOUTH);
            gameScreenPanel.resetButtons(1);
            validate();
            gameScreenPanel.repaint();
        }
        //When check is 3, call endOfGameDisplay
        else if(checker == 3) {
            remove(gameScreenPanel);
            add(endOfGameDisplay, BorderLayout.SOUTH);
            validate();
            endOfGameDisplay.repaint();
        }
        //When check is 4, call scoreBoard
        else if(checker == 4) {
            remove(startScreen);
            add(scoreBoard, BorderLayout.SOUTH);
            validate();
            scoreBoard.repaint();
        }
    }
}

