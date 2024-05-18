package org.newdawn.slick.tests;

import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import java.awt.Frame;
import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.CanvasGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class CanvasContainerTest extends BasicGame
{
    private Image Ó;
    private Image à;
    private Image Ø;
    private Image áŒŠÆ;
    private Image áˆºÑ¢Õ;
    private Image ÂµÈ;
    private float á;
    
    public CanvasContainerTest() {
        super("Canvas Container Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        final Image image = new Image("testdata/logo.tga");
        this.Ó = image;
        this.áˆºÑ¢Õ = image;
        this.à = new Image("testdata/logo.tga", true, 2);
        this.áŒŠÆ = new Image("testdata/logo.gif");
        this.Ø = this.áŒŠÆ.Ý(120, 120);
        this.ÂµÈ = this.áˆºÑ¢Õ.HorizonCode_Horizon_È(200, 0, 70, 260);
        this.á = 0.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        this.áˆºÑ¢Õ.HorizonCode_Horizon_È(0.0f, 0.0f);
        this.áˆºÑ¢Õ.HorizonCode_Horizon_È(500.0f, 0.0f, 200.0f, 100.0f);
        this.à.HorizonCode_Horizon_È(500.0f, 100.0f, 200.0f, 100.0f);
        this.Ø.HorizonCode_Horizon_È(400.0f, 500.0f);
        final Image flipped = this.Ø.HorizonCode_Horizon_È(true, false);
        flipped.HorizonCode_Horizon_È(520.0f, 500.0f);
        final Image flipped2 = flipped.HorizonCode_Horizon_È(false, true);
        flipped2.HorizonCode_Horizon_È(520.0f, 380.0f);
        final Image flipped3 = flipped2.HorizonCode_Horizon_È(true, false);
        flipped3.HorizonCode_Horizon_È(400.0f, 380.0f);
        for (int i = 0; i < 3; ++i) {
            this.ÂµÈ.HorizonCode_Horizon_È(200 + i * 30, 300.0f);
        }
        g.Â(500.0f, 200.0f);
        g.HorizonCode_Horizon_È(50.0f, 50.0f, this.á);
        g.HorizonCode_Horizon_È(0.3f, 0.3f);
        this.áˆºÑ¢Õ.Ø­áŒŠá();
        g.Ø();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        this.á += delta * 0.1f;
        if (this.á > 360.0f) {
            this.á -= 360.0f;
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final CanvasGameContainer container = new CanvasGameContainer(new CanvasContainerTest());
            final Frame frame = new Frame("Test");
            frame.setLayout(new GridLayout(1, 2));
            frame.setSize(500, 500);
            frame.add(container);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(final WindowEvent e) {
                    System.exit(0);
                }
            });
            frame.setVisible(true);
            container.HorizonCode_Horizon_È();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 57) {
            if (this.áˆºÑ¢Õ == this.áŒŠÆ) {
                this.áˆºÑ¢Õ = this.Ó;
            }
            else {
                this.áˆºÑ¢Õ = this.áŒŠÆ;
            }
        }
    }
}
