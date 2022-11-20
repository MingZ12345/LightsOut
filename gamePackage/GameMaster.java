//=================================================================
// Lights Out
// Ming Zhang
// Jan 21, 2022
// Java 18.0.2
//=================================================================
// Features:
//-Lights out game
//-Ranking system (only displays the best scores)
//-Counts number of moves used
//-Self solve mode
//-Play new game or same game after solving
//=================================================================  

//declares the package the class is in
package gamePackage;

//imports IOException library
import java.io.IOException;
import javax.swing.SwingUtilities;
import gamePackage.Frame;

//Start of GameMaster class
public class GameMaster {

    // =======================================================================
    // Main method
    // Runs main program
    // No parameters
    // Returns void
    // =======================================================================
    public static void main(String[] args) {

        //Uses invokelater to run main components of game
        SwingUtilities.invokeLater(new Runnable() {

            // =======================================================================
            // Run method
            // Runs main game program
            // No parameters
            // Returns void
            // =======================================================================
            public void run() {
                try {
                    new Frame();

                } catch (IOException e) {
                    System.out.println("Error");
                    e.printStackTrace();
                }
            }
        });
    }
}