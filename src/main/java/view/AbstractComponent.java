package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class AbstractComponent extends JComponent {

    private final int FONT_SIZE = 28;
    private final String RES_PATH = "src/main/resources/";
    private final String DIGITAL_FONT = "digital-mono-7.ttf";

    public static final float STROKE_WIDTH = 1.5f;
    public static Font digitalFont;

    public AbstractComponent() {
        addFont();
    }

    protected void addFont() {
        try {
            digitalFont = Font.createFont(Font.TRUETYPE_FONT, new File(RES_PATH + DIGITAL_FONT));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        digitalFont = digitalFont.deriveFont(Font.BOLD,FONT_SIZE);
    }

    @Override
    abstract public void paint(Graphics g);

}
