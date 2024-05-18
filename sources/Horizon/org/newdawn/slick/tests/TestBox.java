package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.SlickCallable;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Log;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import java.util.ArrayList;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class TestBox extends BasicGame
{
    private ArrayList Ó;
    private BasicGame à;
    private int Ø;
    private AppGameContainer áŒŠÆ;
    
    public TestBox() {
        super("Test Box");
        this.Ó = new ArrayList();
    }
    
    public void HorizonCode_Horizon_È(final Class game) {
        this.Ó.add(game);
    }
    
    private void Ó() {
        if (this.Ø == -1) {
            return;
        }
        ++this.Ø;
        if (this.Ø >= this.Ó.size()) {
            this.Ø = 0;
        }
        this.à();
    }
    
    private void à() {
        try {
            this.à = this.Ó.get(this.Ø).newInstance();
            this.áŒŠÆ.ˆáŠ().HorizonCode_Horizon_È(Color.Ø);
            this.à.HorizonCode_Horizon_È(this.áŒŠÆ);
            this.à.HorizonCode_Horizon_È(this.áŒŠÆ, this.áŒŠÆ.ˆáŠ());
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
        }
        this.áŒŠÆ.HorizonCode_Horizon_È(this.à.Â());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer c) throws SlickException {
        if (this.Ó.size() == 0) {
            (this.à = new BasicGame("NULL") {
                @Override
                public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
                }
                
                @Override
                public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
                }
                
                @Override
                public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
                }
            }).HorizonCode_Horizon_È(c);
            this.Ø = -1;
        }
        else {
            this.Ø = 0;
            this.áŒŠÆ = (AppGameContainer)c;
            this.à();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        this.à.HorizonCode_Horizon_È(container, delta);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        SlickCallable.HorizonCode_Horizon_È();
        this.à.HorizonCode_Horizon_È(container, g);
        SlickCallable.Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int controller, final int button) {
        this.à.HorizonCode_Horizon_È(controller, button);
    }
    
    @Override
    public void Â(final int controller, final int button) {
        this.à.Â(controller, button);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int controller) {
        this.à.HorizonCode_Horizon_È(controller);
    }
    
    @Override
    public void Â(final int controller) {
        this.à.Â(controller);
    }
    
    @Override
    public void Ý(final int controller) {
        this.à.Ý(controller);
    }
    
    @Override
    public void Ø­áŒŠá(final int controller) {
        this.à.Ø­áŒŠá(controller);
    }
    
    @Override
    public void Âµá€(final int controller) {
        this.à.Âµá€(controller);
    }
    
    @Override
    public void Ó(final int controller) {
        this.à.Ó(controller);
    }
    
    @Override
    public void à(final int controller) {
        this.à.à(controller);
    }
    
    @Override
    public void Ø(final int controller) {
        this.à.Ø(controller);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        this.à.HorizonCode_Horizon_È(key, c);
        if (key == 28) {
            this.Ó();
        }
    }
    
    @Override
    public void Â(final int key, final char c) {
        this.à.Â(key, c);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int oldx, final int oldy, final int newx, final int newy) {
        this.à.HorizonCode_Horizon_È(oldx, oldy, newx, newy);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int button, final int x, final int y) {
        this.à.HorizonCode_Horizon_È(button, x, y);
    }
    
    @Override
    public void Â(final int button, final int x, final int y) {
        this.à.Â(button, x, y);
    }
    
    @Override
    public void áŒŠÆ(final int change) {
        this.à.áŒŠÆ(change);
    }
    
    public static void main(final String[] argv) {
        try {
            final TestBox box = new TestBox();
            box.HorizonCode_Horizon_È(AnimationTest.class);
            box.HorizonCode_Horizon_È(AntiAliasTest.class);
            box.HorizonCode_Horizon_È(BigImageTest.class);
            box.HorizonCode_Horizon_È(ClipTest.class);
            box.HorizonCode_Horizon_È(DuplicateEmitterTest.class);
            box.HorizonCode_Horizon_È(FlashTest.class);
            box.HorizonCode_Horizon_È(FontPerformanceTest.class);
            box.HorizonCode_Horizon_È(FontTest.class);
            box.HorizonCode_Horizon_È(GeomTest.class);
            box.HorizonCode_Horizon_È(GradientTest.class);
            box.HorizonCode_Horizon_È(GraphicsTest.class);
            box.HorizonCode_Horizon_È(ImageBufferTest.class);
            box.HorizonCode_Horizon_È(ImageReadTest.class);
            box.HorizonCode_Horizon_È(ImageTest.class);
            box.HorizonCode_Horizon_È(KeyRepeatTest.class);
            box.HorizonCode_Horizon_È(MusicListenerTest.class);
            box.HorizonCode_Horizon_È(PackedSheetTest.class);
            box.HorizonCode_Horizon_È(PedigreeTest.class);
            box.HorizonCode_Horizon_È(PureFontTest.class);
            box.HorizonCode_Horizon_È(ShapeTest.class);
            box.HorizonCode_Horizon_È(SoundTest.class);
            box.HorizonCode_Horizon_È(SpriteSheetFontTest.class);
            box.HorizonCode_Horizon_È(TransparentColorTest.class);
            final AppGameContainer container = new AppGameContainer(box);
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
