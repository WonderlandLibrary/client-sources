/*
 * Decompiled with CFR 0.143.
 */
package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class ImageOutTest
extends BasicGame {
    private GameContainer container;
    private ParticleSystem fire;
    private Graphics g;
    private Image copy;
    private String message;

    public ImageOutTest() {
        super("Image Out Test");
    }

    public void init(GameContainer container) throws SlickException {
        this.container = container;
        try {
            this.fire = ParticleIO.loadConfiguredSystem("testdata/system.xml");
        }
        catch (IOException e) {
            throw new SlickException("Failed to load particle systems", e);
        }
        this.copy = new Image(400, 300);
        String[] formats = ImageOut.getSupportedFormats();
        this.message = "Formats supported: ";
        for (int i = 0; i < formats.length; ++i) {
            this.message = this.message + formats[i];
            if (i >= formats.length - 1) continue;
            this.message = this.message + ",";
        }
    }

    public void render(GameContainer container, Graphics g) {
        g.drawString("T - TGA Snapshot", 10.0f, 50.0f);
        g.drawString("J - JPG Snapshot", 10.0f, 70.0f);
        g.drawString("P - PNG Snapshot", 10.0f, 90.0f);
        g.setDrawMode(Graphics.MODE_ADD);
        g.drawImage(this.copy, 200.0f, 300.0f);
        g.setDrawMode(Graphics.MODE_NORMAL);
        g.drawString(this.message, 10.0f, 400.0f);
        g.drawRect(200.0f, 0.0f, 400.0f, 300.0f);
        g.translate(400.0f, 250.0f);
        this.fire.render();
        this.g = g;
    }

    private void writeTo(String fname) throws SlickException {
        this.g.copyArea(this.copy, 200, 0);
        ImageOut.write(this.copy, fname);
        this.message = "Written " + fname;
    }

    public void update(GameContainer container, int delta) throws SlickException {
        this.fire.update(delta);
        if (container.getInput().isKeyPressed(25)) {
            this.writeTo("ImageOutTest.png");
        }
        if (container.getInput().isKeyPressed(36)) {
            this.writeTo("ImageOutTest.jpg");
        }
        if (container.getInput().isKeyPressed(20)) {
            this.writeTo("ImageOutTest.tga");
        }
    }

    public static void main(String[] argv) {
        try {
            AppGameContainer container = new AppGameContainer(new ImageOutTest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(int key, char c) {
        if (key == 1) {
            this.container.exit();
        }
    }
}

