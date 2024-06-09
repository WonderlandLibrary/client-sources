/*
 * Decompiled with CFR 0.143.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.PackedSpriteSheet;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class PackedSheetTest
extends BasicGame {
    private PackedSpriteSheet sheet;
    private GameContainer container;
    private float r = -500.0f;
    private Image rocket;
    private Animation runner;
    private float ang;

    public PackedSheetTest() {
        super("Packed Sprite Sheet Test");
    }

    public void init(GameContainer container) throws SlickException {
        this.container = container;
        this.sheet = new PackedSpriteSheet("testdata/testpack.def", 2);
        this.rocket = this.sheet.getSprite("rocket");
        SpriteSheet anim = this.sheet.getSpriteSheet("runner");
        this.runner = new Animation();
        for (int y = 0; y < 2; ++y) {
            for (int x = 0; x < 6; ++x) {
                this.runner.addFrame(anim.getSprite(x, y), 50);
            }
        }
    }

    public void render(GameContainer container, Graphics g) {
        this.rocket.draw((int)this.r, 100.0f);
        this.runner.draw(250.0f, 250.0f);
        g.scale(1.2f, 1.2f);
        this.runner.draw(250.0f, 250.0f);
        g.scale(1.2f, 1.2f);
        this.runner.draw(250.0f, 250.0f);
        g.resetTransform();
        g.rotate(670.0f, 470.0f, this.ang);
        this.sheet.getSprite("floppy").draw(600.0f, 400.0f);
    }

    public void update(GameContainer container, int delta) {
        this.r += (float)delta * 0.4f;
        if (this.r > 900.0f) {
            this.r = -500.0f;
        }
        this.ang += (float)delta * 0.1f;
    }

    public static void main(String[] argv) {
        try {
            AppGameContainer container = new AppGameContainer(new PackedSheetTest());
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

