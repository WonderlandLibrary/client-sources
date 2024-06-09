package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.ShapeRenderer;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Vector2f;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.TexCoordGenerator;
import HORIZON-6-0-SKIDPROTECTION.Rectangle;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.Polygon;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class TexturePaintTest extends BasicGame
{
    private Polygon Ó;
    private Image à;
    private Rectangle Ø;
    private TexCoordGenerator áŒŠÆ;
    
    public TexturePaintTest() {
        super("Texture Paint Test");
        this.Ó = new Polygon();
        this.Ø = new Rectangle(50.0f, 50.0f, 100.0f, 100.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó.Â(120.0f, 120.0f);
        this.Ó.Â(420.0f, 100.0f);
        this.Ó.Â(620.0f, 420.0f);
        this.Ó.Â(300.0f, 320.0f);
        this.à = new Image("testdata/rocks.png");
        this.áŒŠÆ = new TexCoordGenerator() {
            @Override
            public Vector2f HorizonCode_Horizon_È(final float x, final float y) {
                final float tx = (TexturePaintTest.this.Ø.£á() - x) / TexturePaintTest.this.Ø.F_();
                final float ty = (TexturePaintTest.this.Ø.Å() - y) / TexturePaintTest.this.Ø.G_();
                return new Vector2f(tx, ty);
            }
        };
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È(this.Ó, this.à);
        ShapeRenderer.HorizonCode_Horizon_È(this.Ó, this.à, this.áŒŠÆ);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new TexturePaintTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
