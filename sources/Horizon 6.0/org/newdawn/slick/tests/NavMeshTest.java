package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.ResourceLoader;
import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.Bootstrap;
import HORIZON-6-0-SKIDPROTECTION.Mover;
import HORIZON-6-0-SKIDPROTECTION.Link;
import HORIZON-6-0-SKIDPROTECTION.Space;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.TileBasedMap;
import java.io.IOException;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.NavPath;
import HORIZON-6-0-SKIDPROTECTION.NavMeshBuilder;
import HORIZON-6-0-SKIDPROTECTION.NavMesh;
import HORIZON-6-0-SKIDPROTECTION.PathFindingContext;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class NavMeshTest extends BasicGame implements PathFindingContext
{
    private NavMesh Ó;
    private NavMeshBuilder à;
    private boolean Ø;
    private boolean áŒŠÆ;
    private NavPath áˆºÑ¢Õ;
    private float ÂµÈ;
    private float á;
    private float ˆÏ­;
    private float £á;
    private HorizonCode_Horizon_È Å;
    
    public NavMeshTest() {
        super("Nav-mesh Test");
        this.Ø = true;
        this.áŒŠÆ = true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        container.ÂµÈ(false);
        try {
            this.Å = new HorizonCode_Horizon_È("testdata/map.dat");
        }
        catch (IOException e) {
            throw new SlickException("Failed to load map data", e);
        }
        this.à = new NavMeshBuilder();
        this.Ó = this.à.HorizonCode_Horizon_È(this.Å);
        System.out.println("Navmesh shapes: " + this.Ó.HorizonCode_Horizon_È());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        if (container.á€().Âµá€(2)) {
            this.áŒŠÆ = !this.áŒŠÆ;
        }
        if (container.á€().Âµá€(3)) {
            this.Ø = !this.Ø;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(50.0f, 50.0f);
        for (int x = 0; x < 50; ++x) {
            for (int y = 0; y < 50; ++y) {
                if (this.Å.HorizonCode_Horizon_È(this, x, y)) {
                    g.Â(Color.áŒŠÆ);
                    g.Ø­áŒŠá(x * 10 + 1, y * 10 + 1, 8.0f, 8.0f);
                }
            }
        }
        if (this.Ø) {
            for (int i = 0; i < this.Ó.HorizonCode_Horizon_È(); ++i) {
                final Space space = this.Ó.HorizonCode_Horizon_È(i);
                if (this.à.HorizonCode_Horizon_È(this.Å, space)) {
                    g.Â(new Color(1.0f, 1.0f, 0.0f, 0.5f));
                    g.Ø­áŒŠá(space.Ý() * 10.0f, space.Ø­áŒŠá() * 10.0f, space.HorizonCode_Horizon_È() * 10.0f, space.Â() * 10.0f);
                }
                g.Â(Color.Ø­áŒŠá);
                g.Â(space.Ý() * 10.0f, space.Ø­áŒŠá() * 10.0f, space.HorizonCode_Horizon_È() * 10.0f, space.Â() * 10.0f);
                if (this.áŒŠÆ) {
                    for (int links = space.Âµá€(), j = 0; j < links; ++j) {
                        final Link link = space.HorizonCode_Horizon_È(j);
                        g.Â(Color.Âµá€);
                        g.Ø­áŒŠá(link.HorizonCode_Horizon_È() * 10.0f - 2.0f, link.Â() * 10.0f - 2.0f, 5.0f, 5.0f);
                    }
                }
            }
        }
        if (this.áˆºÑ¢Õ != null) {
            g.Â(Color.Ý);
            for (int i = 0; i < this.áˆºÑ¢Õ.HorizonCode_Horizon_È() - 1; ++i) {
                g.HorizonCode_Horizon_È(this.áˆºÑ¢Õ.HorizonCode_Horizon_È(i) * 10.0f, this.áˆºÑ¢Õ.Â(i) * 10.0f, this.áˆºÑ¢Õ.HorizonCode_Horizon_È(i + 1) * 10.0f, this.áˆºÑ¢Õ.Â(i + 1) * 10.0f);
            }
        }
    }
    
    @Override
    public Mover Ó() {
        return null;
    }
    
    @Override
    public int à() {
        return 0;
    }
    
    @Override
    public int Ø() {
        return 0;
    }
    
    @Override
    public int áŒŠÆ() {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int button, final int x, final int y) {
        final float mx = (x - 50) / 10.0f;
        final float my = (y - 50) / 10.0f;
        if (button == 0) {
            this.ÂµÈ = mx;
            this.á = my;
        }
        else {
            this.ˆÏ­ = mx;
            this.£á = my;
        }
        this.áˆºÑ¢Õ = this.Ó.HorizonCode_Horizon_È(this.ÂµÈ, this.á, this.ˆÏ­, this.£á, true);
    }
    
    public static void main(final String[] argv) {
        Bootstrap.HorizonCode_Horizon_È(new NavMeshTest(), 600, 600, false);
    }
    
    private class HorizonCode_Horizon_È implements TileBasedMap
    {
        private byte[] Â;
        
        public HorizonCode_Horizon_È(final String ref) throws IOException {
            this.Â = new byte[2500];
            ResourceLoader.HorizonCode_Horizon_È(ref).read(this.Â);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final PathFindingContext context, final int tx, final int ty) {
            return tx >= 0 && ty >= 0 && tx < 50 && ty < 50 && this.Â[tx + ty * 50] != 0;
        }
        
        @Override
        public float Â(final PathFindingContext context, final int tx, final int ty) {
            return 1.0f;
        }
        
        @Override
        public int HorizonCode_Horizon_È() {
            return 50;
        }
        
        @Override
        public int Â() {
            return 50;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int x, final int y) {
        }
    }
}
