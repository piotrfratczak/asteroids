package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuComponent extends AbstractComponent {

    private MenuSelection selected;

    MenuComponent() {
        super();
        selected = MenuSelection.CLASSIC;
        bindActions();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(STROKE_WIDTH));
        g2.setPaint(Color.white);

        g2.setFont(digitalFont.deriveFont(Font.BOLD,60));
        drawText(g2, "ASTEROIDS", -2);

        g2.setFont(digitalFont);
        drawText(g2, MenuSelection.CLASSIC, 0);
        drawText(g2, MenuSelection.ENHANCED, 1);
        drawText(g2, MenuSelection.QUIT, 3);
    }

    private void drawText(Graphics2D g2, MenuSelection option, int level) {
        String text = option.getStr();
        if (option == selected) text = "> " + text + " <";
        drawText(g2, text, level);
    }

    private void drawText(Graphics2D g2, String text, int level) {
        int offset = g2.getFontMetrics().stringWidth(text)/2;
        int height = g2.getFontMetrics().getHeight();
        g2.drawString(text, Display.WIDTH / 2 - offset, Display.HEIGHT / 2 + level * 3 * height);
    }

    private void bindActions() {
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "upAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "downAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "confirmAction");

        this.getActionMap().put("upAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPrevious();
            }
        });
        this.getActionMap().put("downAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectNext();
            }
        });
        this.getActionMap().put("confirmAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmSelection();
            }
        });
    }

    private void selectPrevious() {
        this.selected = this.selected.getPrevious();
    }

    private void selectNext() {
        this.selected = this.selected.getNext();
    }

    private void confirmSelection() {
        switch (selected) {
            case CLASSIC -> GameController.startNewGame(false);
            case ENHANCED -> GameController.startNewGame(true);
            case QUIT ->  GameController.closeWindow();
        }
    }


}
