/*
 * Decompiled with CFR 0.143.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

public class ScalableTest
extends BasicGame {
    public ScalableTest() {
        super("Scalable Test For Widescreen");
    }

    public void init(GameContainer container) throws SlickException {
    }

    public void update(GameContainer container, int delta) throws SlickException {
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(new Color(0.4f, 0.6f, 0.8f));
        g.fillRect(0.0f, 0.0f, 1024.0f, 568.0f);
        g.setColor(Color.white);
        g.drawRect(5.0f, 5.0f, 1014.0f, 558.0f);
        g.setColor(Color.white);
        g.drawString(container.getInput().getMouseX() + "," + container.getInput().getMouseY(), 10.0f, 400.0f);
        g.setColor(Color.red);
        g.fillOval(container.getInput().getMouseX() - 10, container.getInput().getMouseY() - 10, 20.0f, 20.0f);
    }

    public static void main(String[] argv) {
        try {
            ScalableGame game = new ScalableGame(new ScalableTest(), 1024, 568, true){

                protected void renderOverlay(GameContainer container, Graphics g) {
                    g.setColor(Color.white);
                    g.drawString("Outside The Game", 350.0f, 10.0f);
                    g.drawString(container.getInput().getMouseX() + "," + container.getInput().getMouseY(), 400.0f, 20.0f);
                }
            };
            AppGameContainer container = new AppGameContainer(game);
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

}

