package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class TransformTest2 extends BasicGame
{
    private float Ó;
    private boolean à;
    private boolean Ø;
    private float áŒŠÆ;
    private float áˆºÑ¢Õ;
    private boolean ÂµÈ;
    private boolean á;
    private boolean ˆÏ­;
    private boolean £á;
    
    public TransformTest2() {
        super("Transform Test");
        this.Ó = 1.0f;
        this.áŒŠÆ = 320.0f;
        this.áˆºÑ¢Õ = 240.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        container.Ó(100);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer contiainer, final Graphics g) {
        g.Â(320.0f, 240.0f);
        g.Â(-this.áŒŠÆ * this.Ó, -this.áˆºÑ¢Õ * this.Ó);
        g.HorizonCode_Horizon_È(this.Ó, this.Ó);
        g.Â(Color.Âµá€);
        for (int x = 0; x < 10; ++x) {
            for (int y = 0; y < 10; ++y) {
                g.Ø­áŒŠá(-500 + x * 100, -500 + y * 100, 80.0f, 80.0f);
            }
        }
        g.Â(new Color(1.0f, 1.0f, 1.0f, 0.5f));
        g.Ø­áŒŠá(-320.0f, -240.0f, 640.0f, 480.0f);
        g.Â(Color.Ý);
        g.Â(-320.0f, -240.0f, 640.0f, 480.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        if (this.à) {
            this.Ó += delta * 0.001f;
        }
        if (this.Ø) {
            this.Ó -= delta * 0.001f;
        }
        final float moveSpeed = delta * 0.4f * (1.0f / this.Ó);
        if (this.ÂµÈ) {
            this.áŒŠÆ -= moveSpeed;
        }
        if (this.á) {
            this.áˆºÑ¢Õ -= moveSpeed;
        }
        if (this.ˆÏ­) {
            this.áŒŠÆ += moveSpeed;
        }
        if (this.£á) {
            this.áˆºÑ¢Õ += moveSpeed;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
        if (key == 16) {
            this.à = true;
        }
        if (key == 30) {
            this.Ø = true;
        }
        if (key == 203) {
            this.ÂµÈ = true;
        }
        if (key == 200) {
            this.á = true;
        }
        if (key == 205) {
            this.ˆÏ­ = true;
        }
        if (key == 208) {
            this.£á = true;
        }
    }
    
    @Override
    public void Â(final int key, final char c) {
        if (key == 16) {
            this.à = false;
        }
        if (key == 30) {
            this.Ø = false;
        }
        if (key == 203) {
            this.ÂµÈ = false;
        }
        if (key == 200) {
            this.á = false;
        }
        if (key == 205) {
            this.ˆÏ­ = false;
        }
        if (key == 208) {
            this.£á = false;
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new TransformTest2());
            container.HorizonCode_Horizon_È(640, 480, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
