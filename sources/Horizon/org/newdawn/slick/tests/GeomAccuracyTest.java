package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.RoundedRectangle;
import HORIZON-6-0-SKIDPROTECTION.Rectangle;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.Ellipse;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class GeomAccuracyTest extends BasicGame
{
    private GameContainer Ó;
    private Color à;
    private Color Ø;
    private boolean áŒŠÆ;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private static final int á = 3;
    private Image ˆÏ­;
    
    public GeomAccuracyTest() {
        super("Geometry Accuracy Tests");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = container;
        this.à = Color.Å;
        this.Ø = Color.Ý;
        this.ˆÏ­ = new Image(21, 21);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        String text = new String();
        switch (this.ÂµÈ) {
            case 0: {
                text = "Rectangles";
                this.Ý(g);
                break;
            }
            case 1: {
                text = "Ovals";
                this.Â(g);
                break;
            }
            case 2: {
                text = "Arcs";
                this.HorizonCode_Horizon_È(g);
                break;
            }
        }
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("Press T to toggle overlay", 200.0f, 55.0f);
        g.HorizonCode_Horizon_È("Press N to switch tests", 200.0f, 35.0f);
        g.HorizonCode_Horizon_È("Press C to cycle drawing colors", 200.0f, 15.0f);
        g.HorizonCode_Horizon_È("Current Test:", 400.0f, 35.0f);
        g.Â(Color.Ó);
        g.HorizonCode_Horizon_È(text, 485.0f, 35.0f);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("Normal:", 10.0f, 150.0f);
        g.HorizonCode_Horizon_È("Filled:", 10.0f, 300.0f);
        g.HorizonCode_Horizon_È("Drawn with Graphics context", 125.0f, 400.0f);
        g.HorizonCode_Horizon_È("Drawn using Shapes", 450.0f, 400.0f);
        g.HorizonCode_Horizon_È(this.ˆÏ­, container.á€().á() - 10, container.á€().ˆÏ­() - 10);
        this.ˆÏ­.HorizonCode_Horizon_È(351.0f, 451.0f, 5.0f);
        g.HorizonCode_Horizon_È("Mag Area -", 250.0f, 475.0f);
        g.Â(Color.ÂµÈ);
        g.Â(350.0f, 450.0f, 106.0f, 106.0f);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("NOTE:", 500.0f, 450.0f);
        g.HorizonCode_Horizon_È("lines should be flush with edges", 525.0f, 470.0f);
        g.HorizonCode_Horizon_È("corners should be symetric", 525.0f, 490.0f);
    }
    
    void HorizonCode_Horizon_È(final Graphics g) {
        if (!this.áŒŠÆ) {
            g.Â(this.Ø);
            g.HorizonCode_Horizon_È(198.0f, 100.0f, 198.0f, 198.0f);
            g.HorizonCode_Horizon_È(100.0f, 198.0f, 198.0f, 198.0f);
        }
        g.Â(this.à);
        g.HorizonCode_Horizon_È(100.0f, 100.0f, 99.0f, 99.0f, 0.0f, 90.0f);
    }
    
    void Â(final Graphics g) {
        g.Â(this.à);
        g.Âµá€(100.0f, 100.0f, 99.0f, 99.0f);
        g.Ó(100.0f, 250.0f, 99.0f, 99.0f);
        Ellipse elip = new Ellipse(449.0f, 149.0f, 49.0f, 49.0f);
        g.HorizonCode_Horizon_È(elip);
        elip = new Ellipse(449.0f, 299.0f, 49.0f, 49.0f);
        g.Â(elip);
        if (!this.áŒŠÆ) {
            g.Â(this.Ø);
            g.HorizonCode_Horizon_È(100.0f, 149.0f, 198.0f, 149.0f);
            g.HorizonCode_Horizon_È(149.0f, 100.0f, 149.0f, 198.0f);
            g.HorizonCode_Horizon_È(100.0f, 299.0f, 198.0f, 299.0f);
            g.HorizonCode_Horizon_È(149.0f, 250.0f, 149.0f, 348.0f);
            g.HorizonCode_Horizon_È(400.0f, 149.0f, 498.0f, 149.0f);
            g.HorizonCode_Horizon_È(449.0f, 100.0f, 449.0f, 198.0f);
            g.HorizonCode_Horizon_È(400.0f, 299.0f, 498.0f, 299.0f);
            g.HorizonCode_Horizon_È(449.0f, 250.0f, 449.0f, 348.0f);
        }
    }
    
    void Ý(final Graphics g) {
        g.Â(this.à);
        g.Â(100.0f, 100.0f, 99.0f, 99.0f);
        g.Ø­áŒŠá(100.0f, 250.0f, 99.0f, 99.0f);
        g.Ý(250.0f, 100.0f, 99.0f, 99.0f, 10);
        g.Ø­áŒŠá(250.0f, 250.0f, 99.0f, 99.0f, 10);
        Rectangle rect = new Rectangle(400.0f, 100.0f, 99.0f, 99.0f);
        g.HorizonCode_Horizon_È((Shape)rect);
        rect = new Rectangle(400.0f, 250.0f, 99.0f, 99.0f);
        g.Â((Shape)rect);
        RoundedRectangle rrect = new RoundedRectangle(550.0f, 100.0f, 99.0f, 99.0f, 10.0f);
        g.HorizonCode_Horizon_È((Shape)rrect);
        rrect = new RoundedRectangle(550.0f, 250.0f, 99.0f, 99.0f, 10.0f);
        g.Â((Shape)rrect);
        if (!this.áŒŠÆ) {
            g.Â(this.Ø);
            g.HorizonCode_Horizon_È(100.0f, 149.0f, 198.0f, 149.0f);
            g.HorizonCode_Horizon_È(149.0f, 100.0f, 149.0f, 198.0f);
            g.HorizonCode_Horizon_È(250.0f, 149.0f, 348.0f, 149.0f);
            g.HorizonCode_Horizon_È(299.0f, 100.0f, 299.0f, 198.0f);
            g.HorizonCode_Horizon_È(400.0f, 149.0f, 498.0f, 149.0f);
            g.HorizonCode_Horizon_È(449.0f, 100.0f, 449.0f, 198.0f);
            g.HorizonCode_Horizon_È(550.0f, 149.0f, 648.0f, 149.0f);
            g.HorizonCode_Horizon_È(599.0f, 100.0f, 599.0f, 198.0f);
            g.HorizonCode_Horizon_È(100.0f, 299.0f, 198.0f, 299.0f);
            g.HorizonCode_Horizon_È(149.0f, 250.0f, 149.0f, 348.0f);
            g.HorizonCode_Horizon_È(250.0f, 299.0f, 348.0f, 299.0f);
            g.HorizonCode_Horizon_È(299.0f, 250.0f, 299.0f, 348.0f);
            g.HorizonCode_Horizon_È(400.0f, 299.0f, 498.0f, 299.0f);
            g.HorizonCode_Horizon_È(449.0f, 250.0f, 449.0f, 348.0f);
            g.HorizonCode_Horizon_È(550.0f, 299.0f, 648.0f, 299.0f);
            g.HorizonCode_Horizon_È(599.0f, 250.0f, 599.0f, 348.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
        if (key == 49) {
            ++this.ÂµÈ;
            this.ÂµÈ %= 3;
        }
        if (key == 46) {
            ++this.áˆºÑ¢Õ;
            this.áˆºÑ¢Õ %= 4;
            this.Ó();
        }
        if (key == 20) {
            this.áŒŠÆ = !this.áŒŠÆ;
        }
    }
    
    private void Ó() {
        switch (this.áˆºÑ¢Õ) {
            case 0: {
                this.Ø = Color.Ý;
                this.à = Color.Å;
                break;
            }
            case 1: {
                this.Ø = Color.Å;
                this.à = Color.Ý;
                break;
            }
            case 2: {
                this.Ø = Color.Âµá€;
                this.à = Color.à;
                break;
            }
            case 3: {
                this.Ø = Color.Âµá€;
                this.à = Color.Ý;
                break;
            }
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new GeomAccuracyTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
