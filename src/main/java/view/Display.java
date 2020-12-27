package view;

import controller.Game;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;

public class Display extends JFrame implements ActionListener {

    private static final int WIDTH  = 1000;
    private static final int HEIGHT = 1000;

    private final ShipComponent shipComponent;

    private final Timer timer = new Timer(3, this);

    public Display() {
        timer.start();

        this.setTitle(GameController.getTitle());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.getContentPane().setBackground(Color.BLACK);
        this.setResizable(false);

        shipComponent = new ShipComponent();
        bindShipActions();
        this.add(shipComponent);

        this.pack();
        this.setVisible(true);
    }

    private void bindShipActions() {
        Action upAction = new UpAction();
        Action leftAction = new LeftAction();
        Action rightAction = new RightAction();
        Action shootAction = new ShootAction();
        Action teleportAction = new TeleportAction();

        shipComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "upAction");
        shipComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "leftAction");
        shipComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "rightAction");
        shipComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "shootAction");
        shipComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK), "teleportAction");

        shipComponent.getActionMap().put("upAction", upAction);
        shipComponent.getActionMap().put("leftAction", leftAction);
        shipComponent.getActionMap().put("rightAction", rightAction);
        shipComponent.getActionMap().put("shootAction", shootAction);
        shipComponent.getActionMap().put("teleportAction", teleportAction);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            this.repaint();
        }
    }

    public static class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.boostSpaceship();
        }
    }

    public static class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.rotateSpaceshipLeft();
        }
    }

    public static class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.rotateSpaceshipRight();
        }
    }

    public static class ShootAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.shootSpaceship();
        }
    }

    public static class TeleportAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.teleportSpaceship();
        }
    }

    private static class ShipComponent extends JComponent {

        private final int[] X_COORDS = {0, 25, 0, -25};
        private final int[] Y_COORDS = {-30, 30, 10, 30};
        private final float STROKE_WIDTH = 1.5f;

        private final GeneralPath shape;

        ShipComponent() {
            shape = new GeneralPath(BasicStroke.JOIN_ROUND, X_COORDS.length);
            shape.moveTo(X_COORDS[0], Y_COORDS[0]);
            for (int i=0; i<4; ++i) {
                shape.lineTo(X_COORDS[i], Y_COORDS[i]);
            }
            shape.closePath();
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            GameController.updateSpaceship();
            double x = Display.WIDTH / (double)GameController.getGameWidth() * GameController.getSpaceshipX();
            double y = Display.HEIGHT / (double)GameController.getGameHeight() * GameController.getSpaceshipY();

            g2.setStroke(new BasicStroke(STROKE_WIDTH));
            g2.setPaint(Color.white);

            // Draw the actual ship
            paintOffset(g2, x, y);
            // Smooth edge transitions
            paintOffset(g2, x + Display.WIDTH, y);   // Draw the ship on the right side when it's over the left edge
            paintOffset(g2, x - Display.WIDTH, y);   // And so on...
            paintOffset(g2, x, y + Display.HEIGHT);
            paintOffset(g2, x, y - Display.HEIGHT);

        }

        private void paintOffset(Graphics2D g2, double x, double y) {
            AffineTransform transform = new AffineTransform();
            transform.translate(Display.WIDTH/2.0 + x,Display.HEIGHT/2.0 + y);
            transform.rotate(GameController.getSpaceshipRotation() + Math.PI/2);

            g2.transform(transform);
            g2.draw(shape);

            try{
                g2.transform(transform.createInverse());
            }catch(NoninvertibleTransformException e){
                e.printStackTrace();
            }
        }

    }

}
