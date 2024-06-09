package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Transform;
import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Vector2f;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Rectangle;
import HORIZON-6-0-SKIDPROTECTION.Polygon;
import HORIZON-6-0-SKIDPROTECTION.Circle;
import HORIZON-6-0-SKIDPROTECTION.GeomUtil;
import java.util.ArrayList;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.GeomUtilListener;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class GeomUtilTest extends BasicGame implements GeomUtilListener
{
    private Shape Ó;
    private Shape à;
    private Shape[] Ø;
    private ArrayList áŒŠÆ;
    private ArrayList áˆºÑ¢Õ;
    private ArrayList ÂµÈ;
    private boolean á;
    private GeomUtil ˆÏ­;
    private int £á;
    private int Å;
    private Circle £à;
    private Shape µà;
    private Polygon ˆà;
    private boolean ¥Æ;
    
    public GeomUtilTest() {
        super("GeomUtilTest");
        this.áŒŠÆ = new ArrayList();
        this.áˆºÑ¢Õ = new ArrayList();
        this.ÂµÈ = new ArrayList();
        this.ˆÏ­ = new GeomUtil();
    }
    
    public void Ó() {
        final Polygon source = new Polygon();
        source.Â(100.0f, 100.0f);
        source.Â(150.0f, 80.0f);
        source.Â(210.0f, 120.0f);
        source.Â(340.0f, 150.0f);
        source.Â(150.0f, 200.0f);
        source.Â(120.0f, 250.0f);
        this.Ó = source;
        this.£à = new Circle(0.0f, 0.0f, 50.0f);
        this.µà = new Rectangle(-100.0f, -40.0f, 200.0f, 80.0f);
        this.ˆà = new Polygon();
        float dis = 40.0f;
        for (int i = 0; i < 360; i += 30) {
            dis = ((dis == 40.0f) ? 60 : 40);
            final double x = Math.cos(Math.toRadians(i)) * dis;
            final double y = Math.sin(Math.toRadians(i)) * dis;
            this.ˆà.Â((float)x, (float)y);
        }
        (this.à = this.£à).Âµá€(203.0f, 78.0f);
        this.£á = (int)this.à.HorizonCode_Horizon_È();
        this.Å = (int)this.à.Â();
        this.à();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.ˆÏ­.HorizonCode_Horizon_È(this);
        this.Ó();
        container.á(true);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        if (container.á€().Âµá€(57)) {
            this.á = !this.á;
        }
        if (container.á€().Âµá€(28)) {
            this.¥Æ = !this.¥Æ;
            this.à();
        }
        if (container.á€().Âµá€(2)) {
            this.à = this.£à;
            this.£à.Ó(this.£á);
            this.£à.à(this.Å);
            this.à();
        }
        if (container.á€().Âµá€(3)) {
            this.à = this.µà;
            this.µà.Ó(this.£á);
            this.µà.à(this.Å);
            this.à();
        }
        if (container.á€().Âµá€(4)) {
            this.à = this.ˆà;
            this.ˆà.Ó(this.£á);
            this.ˆà.à(this.Å);
            this.à();
        }
        if (this.á) {
            this.£á = container.á€().á();
            this.Å = container.á€().ˆÏ­();
            this.à();
        }
    }
    
    private void à() {
        this.áˆºÑ¢Õ.clear();
        this.áŒŠÆ.clear();
        this.ÂµÈ.clear();
        this.à.Ó(this.£á);
        this.à.à(this.Å);
        if (this.¥Æ) {
            this.Ø = this.ˆÏ­.Â(this.Ó, this.à);
        }
        else {
            this.Ø = this.ˆÏ­.HorizonCode_Horizon_È(this.Ó, this.à);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.HorizonCode_Horizon_È("Space - toggle movement of cutting shape", 530.0f, 10.0f);
        g.HorizonCode_Horizon_È("1,2,3 - select cutting shape", 530.0f, 30.0f);
        g.HorizonCode_Horizon_È("Mouse wheel - rotate shape", 530.0f, 50.0f);
        g.HorizonCode_Horizon_È("Enter - toggle union/subtract", 530.0f, 70.0f);
        g.HorizonCode_Horizon_È("MODE: " + (this.¥Æ ? "Union" : "Cut"), 530.0f, 200.0f);
        g.Â(Color.à);
        g.HorizonCode_Horizon_È(this.Ó);
        g.Â(Color.Âµá€);
        g.HorizonCode_Horizon_È(this.à);
        g.Â(Color.Ý);
        for (int i = 0; i < this.ÂµÈ.size(); ++i) {
            final Vector2f pt = this.ÂµÈ.get(i);
            g.Âµá€(pt.HorizonCode_Horizon_È - 3.0f, pt.Â - 3.0f, 7.0f, 7.0f);
        }
        g.Â(Color.Ø­áŒŠá);
        for (int i = 0; i < this.áŒŠÆ.size(); ++i) {
            final Vector2f pt = this.áŒŠÆ.get(i);
            g.Ó(pt.HorizonCode_Horizon_È - 1.0f, pt.Â - 1.0f, 3.0f, 3.0f);
        }
        g.Â(Color.Ý);
        for (int i = 0; i < this.áˆºÑ¢Õ.size(); ++i) {
            final Vector2f pt = this.áˆºÑ¢Õ.get(i);
            g.Ó(pt.HorizonCode_Horizon_È - 1.0f, pt.Â - 1.0f, 3.0f, 3.0f);
        }
        g.Â(0.0f, 300.0f);
        g.Â(Color.Ý);
        if (this.Ø != null) {
            for (int i = 0; i < this.Ø.length; ++i) {
                g.HorizonCode_Horizon_È(this.Ø[i]);
            }
            g.HorizonCode_Horizon_È("Polys:" + this.Ø.length, 10.0f, 100.0f);
            g.HorizonCode_Horizon_È("X:" + this.£á, 10.0f, 120.0f);
            g.HorizonCode_Horizon_È("Y:" + this.Å, 10.0f, 130.0f);
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new GeomUtilTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y) {
        this.ÂµÈ.add(new Vector2f(x, y));
    }
    
    @Override
    public void Â(final float x, final float y) {
        this.áˆºÑ¢Õ.add(new Vector2f(x, y));
    }
    
    @Override
    public void Ý(final float x, final float y) {
        this.áŒŠÆ.add(new Vector2f(x, y));
    }
    
    @Override
    public void áŒŠÆ(final int change) {
        if (this.á) {
            if (change < 0) {
                this.à = this.à.HorizonCode_Horizon_È(Transform.HorizonCode_Horizon_È((float)Math.toRadians(10.0), this.à.HorizonCode_Horizon_È(), this.à.Â()));
            }
            else {
                this.à = this.à.HorizonCode_Horizon_È(Transform.HorizonCode_Horizon_È((float)Math.toRadians(-10.0), this.à.HorizonCode_Horizon_È(), this.à.Â()));
            }
        }
    }
}
