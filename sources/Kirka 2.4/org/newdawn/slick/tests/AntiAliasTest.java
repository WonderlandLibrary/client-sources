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
import org.newdawn.slick.SlickException;

public class AntiAliasTest
extends BasicGame {
    public AntiAliasTest() {
        super("AntiAlias Test");
    }

    public void init(GameContainer container) throws SlickException {
        container.getGraphics().setBackground(Color.green);
    }

    public void update(GameContainer container, int delta) throws SlickException {
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setAntiAlias(true);
        g.setColor(Color.red);
        g.drawOval(100.0f, 100.0f, 100.0f, 100.0f);
        g.fillOval(300.0f, 100.0f, 100.0f, 100.0f);
        g.setAntiAlias(false);
        g.setColor(Color.red);
        g.drawOval(100.0f, 300.0f, 100.0f, 100.0f);
        g.fillOval(300.0f, 300.0f, 100.0f, 100.0f);
    }

    public static void main(String[] argv) {
        try {
            AppGameContainer container = new AppGameContainer(new AntiAliasTest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

