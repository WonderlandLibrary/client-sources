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
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.svg.Diagram;
import org.newdawn.slick.svg.InkscapeLoader;
import org.newdawn.slick.svg.SimpleDiagramRenderer;

public class InkscapeTest
extends BasicGame {
    private SimpleDiagramRenderer[] renderer = new SimpleDiagramRenderer[5];
    private float zoom = 1.0f;
    private float x;
    private float y;

    public InkscapeTest() {
        super("Inkscape Test");
    }

    public void init(GameContainer container) throws SlickException {
        container.getGraphics().setBackground(Color.white);
        InkscapeLoader.RADIAL_TRIANGULATION_LEVEL = 2;
        this.renderer[3] = new SimpleDiagramRenderer(InkscapeLoader.load("testdata/svg/clonetest.svg"));
        container.getGraphics().setBackground(new Color(0.5f, 0.7f, 1.0f));
    }

    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyDown(16)) {
            this.zoom += (float)delta * 0.01f;
            if (this.zoom > 10.0f) {
                this.zoom = 10.0f;
            }
        }
        if (container.getInput().isKeyDown(30)) {
            this.zoom -= (float)delta * 0.01f;
            if (this.zoom < 0.1f) {
                this.zoom = 0.1f;
            }
        }
        if (container.getInput().isKeyDown(205)) {
            this.x += (float)delta * 0.1f;
        }
        if (container.getInput().isKeyDown(203)) {
            this.x -= (float)delta * 0.1f;
        }
        if (container.getInput().isKeyDown(208)) {
            this.y += (float)delta * 0.1f;
        }
        if (container.getInput().isKeyDown(200)) {
            this.y -= (float)delta * 0.1f;
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.scale(this.zoom, this.zoom);
        g.translate(this.x, this.y);
        g.scale(0.3f, 0.3f);
        g.scale(3.3333333f, 3.3333333f);
        g.translate(400.0f, 0.0f);
        g.translate(100.0f, 300.0f);
        g.scale(0.7f, 0.7f);
        g.scale(1.4285715f, 1.4285715f);
        g.scale(0.5f, 0.5f);
        g.translate(-1100.0f, -380.0f);
        this.renderer[3].render(g);
        g.scale(2.0f, 2.0f);
        g.resetTransform();
    }

    public static void main(String[] argv) {
        try {
            Renderer.setRenderer(2);
            Renderer.setLineStripRenderer(4);
            AppGameContainer container = new AppGameContainer(new InkscapeTest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

