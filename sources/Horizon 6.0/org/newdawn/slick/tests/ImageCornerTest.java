package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class ImageCornerTest extends BasicGame
{
    private Image Ó;
    private Image[] à;
    private int Ø;
    private int áŒŠÆ;
    
    public ImageCornerTest() {
        super("Image Corner Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new Image("testdata/logo.png");
        this.Ø = this.Ó.ŒÏ() / 3;
        this.áŒŠÆ = this.Ó.Çªà¢() / 3;
        this.à = new Image[] { this.Ó.HorizonCode_Horizon_È(0, 0, this.Ø, this.áŒŠÆ), this.Ó.HorizonCode_Horizon_È(this.Ø, 0, this.Ø, this.áŒŠÆ), this.Ó.HorizonCode_Horizon_È(this.Ø * 2, 0, this.Ø, this.áŒŠÆ), this.Ó.HorizonCode_Horizon_È(0, this.áŒŠÆ, this.Ø, this.áŒŠÆ), this.Ó.HorizonCode_Horizon_È(this.Ø, this.áŒŠÆ, this.Ø, this.áŒŠÆ), this.Ó.HorizonCode_Horizon_È(this.Ø * 2, this.áŒŠÆ, this.Ø, this.áŒŠÆ), this.Ó.HorizonCode_Horizon_È(0, this.áŒŠÆ * 2, this.Ø, this.áŒŠÆ), this.Ó.HorizonCode_Horizon_È(this.Ø, this.áŒŠÆ * 2, this.Ø, this.áŒŠÆ), this.Ó.HorizonCode_Horizon_È(this.Ø * 2, this.áŒŠÆ * 2, this.Ø, this.áŒŠÆ) };
        this.à[0].HorizonCode_Horizon_È(2, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[1].HorizonCode_Horizon_È(3, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[1].HorizonCode_Horizon_È(2, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[2].HorizonCode_Horizon_È(3, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[3].HorizonCode_Horizon_È(1, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[3].HorizonCode_Horizon_È(2, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[4].HorizonCode_Horizon_È(1, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[4].HorizonCode_Horizon_È(0, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[4].HorizonCode_Horizon_È(3, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[4].HorizonCode_Horizon_È(2, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[5].HorizonCode_Horizon_È(0, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[5].HorizonCode_Horizon_È(3, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[6].HorizonCode_Horizon_È(1, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[7].HorizonCode_Horizon_È(1, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[7].HorizonCode_Horizon_È(0, 0.0f, 1.0f, 1.0f, 1.0f);
        this.à[8].HorizonCode_Horizon_È(0, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                this.à[x + y * 3].HorizonCode_Horizon_È(100 + x * this.Ø, 100 + y * this.áŒŠÆ);
            }
        }
    }
    
    public static void main(final String[] argv) {
        final boolean sharedContextTest = false;
        try {
            final AppGameContainer container = new AppGameContainer(new ImageCornerTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
}
