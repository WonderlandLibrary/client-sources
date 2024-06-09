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
import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;

public class ImageGraphicsTest
extends BasicGame {
    private Image preloaded;
    private Image target;
    private Image cut;
    private Graphics gTarget;
    private Graphics offscreenPreload;
    private Image testImage;
    private Font testFont;
    private float ang;
    private String using = "none";

    public ImageGraphicsTest() {
        super("Image Graphics Test");
    }

    public void init(GameContainer container) throws SlickException {
        this.testImage = new Image("testdata/logo.png");
        this.preloaded = new Image("testdata/logo.png");
        this.testFont = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
        this.target = new Image(400, 300);
        this.cut = new Image(100, 100);
        this.gTarget = this.target.getGraphics();
        this.offscreenPreload = this.preloaded.getGraphics();
        this.offscreenPreload.drawString("Drawing over a loaded image", 5.0f, 15.0f);
        this.offscreenPreload.setLineWidth(5.0f);
        this.offscreenPreload.setAntiAlias(true);
        this.offscreenPreload.setColor(Color.blue.brighter());
        this.offscreenPreload.drawOval(200.0f, 30.0f, 50.0f, 50.0f);
        this.offscreenPreload.setColor(Color.white);
        this.offscreenPreload.drawRect(190.0f, 20.0f, 70.0f, 70.0f);
        this.offscreenPreload.flush();
        if (GraphicsFactory.usingFBO()) {
            this.using = "FBO (Frame Buffer Objects)";
        } else if (GraphicsFactory.usingPBuffer()) {
            this.using = "Pbuffer (Pixel Buffers)";
        }
        System.out.println(this.preloaded.getColor(50, 50));
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        this.gTarget.setBackground(new Color(0, 0, 0, 0));
        this.gTarget.clear();
        this.gTarget.rotate(200.0f, 160.0f, this.ang);
        this.gTarget.setFont(this.testFont);
        this.gTarget.fillRect(10.0f, 10.0f, 50.0f, 50.0f);
        this.gTarget.drawString("HELLO WORLD", 10.0f, 10.0f);
        this.gTarget.drawImage(this.testImage, 100.0f, 150.0f);
        this.gTarget.drawImage(this.testImage, 100.0f, 50.0f);
        this.gTarget.drawImage(this.testImage, 50.0f, 75.0f);
        this.gTarget.flush();
        g.setColor(Color.red);
        g.fillRect(250.0f, 50.0f, 200.0f, 200.0f);
        this.target.draw(300.0f, 100.0f);
        this.target.draw(300.0f, 410.0f, 200.0f, 150.0f);
        this.target.draw(505.0f, 410.0f, 100.0f, 75.0f);
        g.setColor(Color.white);
        g.drawString("Testing On Offscreen Buffer", 300.0f, 80.0f);
        g.setColor(Color.green);
        g.drawRect(300.0f, 100.0f, this.target.getWidth(), this.target.getHeight());
        g.drawRect(300.0f, 410.0f, this.target.getWidth() / 2, this.target.getHeight() / 2);
        g.drawRect(505.0f, 410.0f, this.target.getWidth() / 4, this.target.getHeight() / 4);
        g.setColor(Color.white);
        g.drawString("Testing Font On Back Buffer", 10.0f, 100.0f);
        g.drawString("Using: " + this.using, 10.0f, 580.0f);
        g.setColor(Color.red);
        g.fillRect(10.0f, 120.0f, 200.0f, 5.0f);
        int xp = (int)(60.0 + Math.sin(this.ang / 60.0f) * 50.0);
        g.copyArea(this.cut, xp, 50);
        this.cut.draw(30.0f, 250.0f);
        g.setColor(Color.white);
        g.drawRect(30.0f, 250.0f, this.cut.getWidth(), this.cut.getHeight());
        g.setColor(Color.gray);
        g.drawRect(xp, 50.0f, this.cut.getWidth(), this.cut.getHeight());
        this.preloaded.draw(2.0f, 400.0f);
        g.setColor(Color.blue);
        g.drawRect(2.0f, 400.0f, this.preloaded.getWidth(), this.preloaded.getHeight());
    }

    public void update(GameContainer container, int delta) {
        this.ang += (float)delta * 0.1f;
    }

    public static void main(String[] argv) {
        try {
            GraphicsFactory.setUseFBO(false);
            AppGameContainer container = new AppGameContainer(new ImageGraphicsTest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

