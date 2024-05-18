package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Renderer;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import java.util.ArrayList;
import HORIZON-6-0-SKIDPROTECTION.Polygon;
import HORIZON-6-0-SKIDPROTECTION.Circle;
import HORIZON-6-0-SKIDPROTECTION.Ellipse;
import HORIZON-6-0-SKIDPROTECTION.RoundedRectangle;
import HORIZON-6-0-SKIDPROTECTION.Rectangle;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class ShapeTest extends BasicGame
{
    private Rectangle Ó;
    private RoundedRectangle à;
    private Ellipse Ø;
    private Circle áŒŠÆ;
    private Polygon áˆºÑ¢Õ;
    private ArrayList ÂµÈ;
    private boolean[] á;
    private char[] ˆÏ­;
    private Polygon £á;
    
    public ShapeTest() {
        super("Geom Test");
        this.£á = new Polygon();
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y) {
        final int size = 20;
        final int change = 10;
        (this.£á = new Polygon()).Â(0 + (int)(Math.random() * change), 0 + (int)(Math.random() * change));
        this.£á.Â(size - (int)(Math.random() * change), 0 + (int)(Math.random() * change));
        this.£á.Â(size - (int)(Math.random() * change), size - (int)(Math.random() * change));
        this.£á.Â(0 + (int)(Math.random() * change), size - (int)(Math.random() * change));
        this.£á.Ó(x);
        this.£á.à(y);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.ÂµÈ = new ArrayList();
        this.Ó = new Rectangle(10.0f, 10.0f, 100.0f, 80.0f);
        this.ÂµÈ.add(this.Ó);
        this.à = new RoundedRectangle(150.0f, 10.0f, 60.0f, 80.0f, 20.0f);
        this.ÂµÈ.add(this.à);
        this.Ø = new Ellipse(350.0f, 40.0f, 50.0f, 30.0f);
        this.ÂµÈ.add(this.Ø);
        this.áŒŠÆ = new Circle(470.0f, 60.0f, 50.0f);
        this.ÂµÈ.add(this.áŒŠÆ);
        this.áˆºÑ¢Õ = new Polygon(new float[] { 550.0f, 10.0f, 600.0f, 40.0f, 620.0f, 100.0f, 570.0f, 130.0f });
        this.ÂµÈ.add(this.áˆºÑ¢Õ);
        this.á = new boolean[256];
        this.ˆÏ­ = new char[256];
        this.HorizonCode_Horizon_È(200.0f, 200.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.Â(Color.à);
        for (int i = 0; i < this.ÂµÈ.size(); ++i) {
            g.Â(this.ÂµÈ.get(i));
        }
        g.Â(this.£á);
        g.Â(Color.Ø);
        g.HorizonCode_Horizon_È(true);
        g.HorizonCode_Horizon_È(this.£á);
        g.HorizonCode_Horizon_È(false);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("keys", 10.0f, 300.0f);
        g.HorizonCode_Horizon_È("wasd - move rectangle", 10.0f, 315.0f);
        g.HorizonCode_Horizon_È("WASD - resize rectangle", 10.0f, 330.0f);
        g.HorizonCode_Horizon_È("tgfh - move rounded rectangle", 10.0f, 345.0f);
        g.HorizonCode_Horizon_È("TGFH - resize rounded rectangle", 10.0f, 360.0f);
        g.HorizonCode_Horizon_È("ry - resize corner radius on rounded rectangle", 10.0f, 375.0f);
        g.HorizonCode_Horizon_È("ikjl - move ellipse", 10.0f, 390.0f);
        g.HorizonCode_Horizon_È("IKJL - resize ellipse", 10.0f, 405.0f);
        g.HorizonCode_Horizon_È("Arrows - move circle", 10.0f, 420.0f);
        g.HorizonCode_Horizon_È("Page Up/Page Down - resize circle", 10.0f, 435.0f);
        g.HorizonCode_Horizon_È("numpad 8546 - move polygon", 10.0f, 450.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        this.HorizonCode_Horizon_È(200.0f, 200.0f);
        if (this.á[1]) {
            System.exit(0);
        }
        if (this.á[17]) {
            if (this.ˆÏ­[17] == 'w') {
                this.Ó.d_(this.Ó.Å() - 1.0f);
            }
            else {
                this.Ó.Ø­áŒŠá(this.Ó.G_() - 1.0f);
            }
        }
        if (this.á[31]) {
            if (this.ˆÏ­[31] == 's') {
                this.Ó.d_(this.Ó.Å() + 1.0f);
            }
            else {
                this.Ó.Ø­áŒŠá(this.Ó.G_() + 1.0f);
            }
        }
        if (this.á[30]) {
            if (this.ˆÏ­[30] == 'a') {
                this.Ó.c_(this.Ó.£á() - 1.0f);
            }
            else {
                this.Ó.Ý(this.Ó.F_() - 1.0f);
            }
        }
        if (this.á[32]) {
            if (this.ˆÏ­[32] == 'd') {
                this.Ó.c_(this.Ó.£á() + 1.0f);
            }
            else {
                this.Ó.Ý(this.Ó.F_() + 1.0f);
            }
        }
        if (this.á[20]) {
            if (this.ˆÏ­[20] == 't') {
                this.à.d_(this.à.Å() - 1.0f);
            }
            else {
                this.à.Ø­áŒŠá(this.à.G_() - 1.0f);
            }
        }
        if (this.á[34]) {
            if (this.ˆÏ­[34] == 'g') {
                this.à.d_(this.à.Å() + 1.0f);
            }
            else {
                this.à.Ø­áŒŠá(this.à.G_() + 1.0f);
            }
        }
        if (this.á[33]) {
            if (this.ˆÏ­[33] == 'f') {
                this.à.c_(this.à.£á() - 1.0f);
            }
            else {
                this.à.Ý(this.à.F_() - 1.0f);
            }
        }
        if (this.á[35]) {
            if (this.ˆÏ­[35] == 'h') {
                this.à.c_(this.à.£á() + 1.0f);
            }
            else {
                this.à.Ý(this.à.F_() + 1.0f);
            }
        }
        if (this.á[19]) {
            this.à.Âµá€(this.à.áˆºÑ¢Õ() - 1.0f);
        }
        if (this.á[21]) {
            this.à.Âµá€(this.à.áˆºÑ¢Õ() + 1.0f);
        }
        if (this.á[23]) {
            if (this.ˆÏ­[23] == 'i') {
                this.Ø.à(this.Ø.Â() - 1.0f);
            }
            else {
                this.Ø.Ý(this.Ø.E_() - 1.0f);
            }
        }
        if (this.á[37]) {
            if (this.ˆÏ­[37] == 'k') {
                this.Ø.à(this.Ø.Â() + 1.0f);
            }
            else {
                this.Ø.Ý(this.Ø.E_() + 1.0f);
            }
        }
        if (this.á[36]) {
            if (this.ˆÏ­[36] == 'j') {
                this.Ø.Ó(this.Ø.HorizonCode_Horizon_È() - 1.0f);
            }
            else {
                this.Ø.Â(this.Ø.Ø() - 1.0f);
            }
        }
        if (this.á[38]) {
            if (this.ˆÏ­[38] == 'l') {
                this.Ø.Ó(this.Ø.HorizonCode_Horizon_È() + 1.0f);
            }
            else {
                this.Ø.Â(this.Ø.Ø() + 1.0f);
            }
        }
        if (this.á[200]) {
            this.áŒŠÆ.à(this.áŒŠÆ.Â() - 1.0f);
        }
        if (this.á[208]) {
            this.áŒŠÆ.à(this.áŒŠÆ.Â() + 1.0f);
        }
        if (this.á[203]) {
            this.áŒŠÆ.Ó(this.áŒŠÆ.HorizonCode_Horizon_È() - 1.0f);
        }
        if (this.á[205]) {
            this.áŒŠÆ.Ó(this.áŒŠÆ.HorizonCode_Horizon_È() + 1.0f);
        }
        if (this.á[201]) {
            this.áŒŠÆ.HorizonCode_Horizon_È(this.áŒŠÆ.D_() - 1.0f);
        }
        if (this.á[209]) {
            this.áŒŠÆ.HorizonCode_Horizon_È(this.áŒŠÆ.D_() + 1.0f);
        }
        if (this.á[72]) {
            this.áˆºÑ¢Õ.d_(this.áˆºÑ¢Õ.Å() - 1.0f);
        }
        if (this.á[76]) {
            this.áˆºÑ¢Õ.d_(this.áˆºÑ¢Õ.Å() + 1.0f);
        }
        if (this.á[75]) {
            this.áˆºÑ¢Õ.c_(this.áˆºÑ¢Õ.£á() - 1.0f);
        }
        if (this.á[77]) {
            this.áˆºÑ¢Õ.c_(this.áˆºÑ¢Õ.£á() + 1.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        this.á[key] = true;
        this.ˆÏ­[key] = c;
    }
    
    @Override
    public void Â(final int key, final char c) {
        this.á[key] = false;
    }
    
    public static void main(final String[] argv) {
        try {
            Renderer.HorizonCode_Horizon_È(2);
            final AppGameContainer container = new AppGameContainer(new ShapeTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
