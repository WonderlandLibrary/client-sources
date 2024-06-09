/*
 * Decompiled with CFR 0.143.
 */
package org.newdawn.slick.tests;

import java.io.PrintStream;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.util.Log;

public class GUITest
extends BasicGame
implements ComponentListener {
    private Image image;
    private MouseOverArea[] areas = new MouseOverArea[4];
    private GameContainer container;
    private String message = "Demo Menu System with stock images";
    private TextField field;
    private TextField field2;
    private Image background;
    private Font font;
    private AppGameContainer app;

    public GUITest() {
        super("GUI Test");
    }

    public void init(GameContainer container) throws SlickException {
        if (container instanceof AppGameContainer) {
            this.app = (AppGameContainer)container;
            this.app.setIcon("testdata/icon.tga");
        }
        this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
        this.field = new TextField(container, this.font, 150, 20, 500, 35, new ComponentListener(){

            public void componentActivated(AbstractComponent source) {
                GUITest.this.message = "Entered1: " + GUITest.this.field.getText();
                GUITest.this.field2.setFocus(true);
            }
        });
        this.field2 = new TextField(container, this.font, 150, 70, 500, 35, new ComponentListener(){

            public void componentActivated(AbstractComponent source) {
                GUITest.this.message = "Entered2: " + GUITest.this.field2.getText();
                GUITest.this.field.setFocus(true);
            }
        });
        this.field2.setBorderColor(Color.red);
        this.container = container;
        this.image = new Image("testdata/logo.tga");
        this.background = new Image("testdata/dungeontiles.gif");
        container.setMouseCursor("testdata/cursor.tga", 0, 0);
        for (int i = 0; i < 4; ++i) {
            this.areas[i] = new MouseOverArea(container, this.image, 300, 100 + i * 100, 200, 90, this);
            this.areas[i].setNormalColor(new Color(1.0f, 1.0f, 1.0f, 0.8f));
            this.areas[i].setMouseOverColor(new Color(1.0f, 1.0f, 1.0f, 0.9f));
        }
    }

    public void render(GameContainer container, Graphics g) {
        this.background.draw(0.0f, 0.0f, 800.0f, 500.0f);
        for (int i = 0; i < 4; ++i) {
            this.areas[i].render(container, g);
        }
        this.field.render(container, g);
        this.field2.render(container, g);
        g.setFont(this.font);
        g.drawString(this.message, 200.0f, 550.0f);
    }

    public void update(GameContainer container, int delta) {
    }

    public void keyPressed(int key, char c) {
        if (key == 1) {
            System.exit(0);
        }
        if (key == 60) {
            this.app.setDefaultMouseCursor();
        }
        if (key == 59 && this.app != null) {
            try {
                this.app.setDisplayMode(640, 480, false);
            }
            catch (SlickException e) {
                Log.error(e);
            }
        }
    }

    public static void main(String[] argv) {
        try {
            AppGameContainer container = new AppGameContainer(new GUITest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void componentActivated(AbstractComponent source) {
        System.out.println("ACTIVL : " + source);
        for (int i = 0; i < 4; ++i) {
            if (source != this.areas[i]) continue;
            this.message = "Option " + (i + 1) + " pressed!";
        }
        if (source == this.field2) {
            // empty if block
        }
    }

}

