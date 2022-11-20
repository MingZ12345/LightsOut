package gamePackage;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EnterNamePanel extends JPanel implements MouseListener, MouseMotionListener{

    private Image enterNameImage;
    private Polygon enterButton;
    private StartScreenListener startListener;
    private JTextField nameTextField;
    private SpacingPanel spacingPanel;
    UniversalValues universalValues = new UniversalValues();

    public EnterNamePanel() throws IOException {
        importImage();
        setButtonPolygon();
        spacingPanel = new SpacingPanel();
        int[] intScreenSizeArray = universalValues.screenSize();
        Dimension dim = getPreferredSize();
        dim.width = intScreenSizeArray[0];
        dim.height = intScreenSizeArray[1];
        nameTextField = new JTextField(20);
        nameTextField.setHorizontalAlignment(JTextField.CENTER);
        nameTextField.setFont(new Font("Lucida", Font.PLAIN, 100));
        add(spacingPanel);
        add(nameTextField);
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(dim);
    }

    public void importImage() {
        enterNameImage = new ImageIcon("Graphics/EnterName.png").getImage();
    }

    public void setButtonPolygon() {
        int[] intXArray = new int[4];
        int[] intYArray = new int[4];
        int intX = 1267;
        int intY = 1200;
        intXArray[0] = intX;
        intXArray[1] = intX + 346;
        intXArray[2] = intX + 346;
        intXArray[3] = intX;
        intYArray[0] = intY;
        intYArray[1] = intY;
        intYArray[2] = intY + 150;
        intYArray[3] = intY + 150;
        enterButton = new Polygon(intXArray, intYArray, 4);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(enterNameImage, 0, 0, this);
    }

    public boolean insideButtonCheck(MouseEvent e) {
        boolean insideButtonChecker = false;
        insideButtonChecker = enterButton.contains(e.getPoint());
        return insideButtonChecker;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (insideButtonCheck(e) == false) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        else {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(enterButton.contains(e.getPoint())) {
            int checker = 2;

            StartScreenEvent sSE = new StartScreenEvent(e, checker);

            if(startListener != null) {
                startListener.startScreenEventOccurred(sSE);
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void setStartScreenListener(StartScreenListener listener) {
        this.startListener = listener;
    }

}

