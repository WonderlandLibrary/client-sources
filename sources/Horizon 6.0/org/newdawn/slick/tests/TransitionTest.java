package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGameState;
import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Log;
import HORIZON-6-0-SKIDPROTECTION.Transition;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameState;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.SelectTransition;
import HORIZON-6-0-SKIDPROTECTION.BlobbyTransition;
import HORIZON-6-0-SKIDPROTECTION.HorizontalSplitTransition;
import HORIZON-6-0-SKIDPROTECTION.RotateTransition;
import HORIZON-6-0-SKIDPROTECTION.FadeInTransition;
import HORIZON-6-0-SKIDPROTECTION.FadeOutTransition;
import HORIZON-6-0-SKIDPROTECTION.VerticalSplitTransition;
import HORIZON-6-0-SKIDPROTECTION.StateBasedGame;

public class TransitionTest extends StateBasedGame
{
    private Class[][] HorizonCode_Horizon_È;
    private int Â;
    
    public TransitionTest() {
        super("Transition Test - Hit Space To Transition");
        this.HorizonCode_Horizon_È = new Class[][] { { null, VerticalSplitTransition.class }, { FadeOutTransition.class, FadeInTransition.class }, { null, RotateTransition.class }, { null, HorizontalSplitTransition.class }, { null, BlobbyTransition.class }, { null, SelectTransition.class } };
    }
    
    @Override
    public void Â(final GameContainer container) throws SlickException {
        this.HorizonCode_Horizon_È(new HorizonCode_Horizon_È(0, "testdata/wallpaper/paper1.png", 1));
        this.HorizonCode_Horizon_È(new HorizonCode_Horizon_È(1, "testdata/wallpaper/paper2.png", 2));
        this.HorizonCode_Horizon_È(new HorizonCode_Horizon_È(2, "testdata/bigimage.tga", 0));
    }
    
    public Transition[] áˆºÑ¢Õ() {
        final Transition[] pair = new Transition[2];
        try {
            if (this.HorizonCode_Horizon_È[this.Â][0] != null) {
                pair[0] = this.HorizonCode_Horizon_È[this.Â][0].newInstance();
            }
            if (this.HorizonCode_Horizon_È[this.Â][1] != null) {
                pair[1] = this.HorizonCode_Horizon_È[this.Â][1].newInstance();
            }
        }
        catch (Throwable e) {
            Log.HorizonCode_Horizon_È(e);
        }
        ++this.Â;
        if (this.Â >= this.HorizonCode_Horizon_È.length) {
            this.Â = 0;
        }
        return pair;
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new TransitionTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    private class HorizonCode_Horizon_È extends BasicGameState
    {
        private int Â;
        private int Ý;
        private String Ø­áŒŠá;
        private Image Âµá€;
        
        public HorizonCode_Horizon_È(final int id, final String ref, final int next) {
            this.Ø­áŒŠá = ref;
            this.Â = id;
            this.Ý = next;
        }
        
        @Override
        public int HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        @Override
        public void Ý(final GameContainer container, final StateBasedGame game) throws SlickException {
            this.Âµá€ = new Image(this.Ø­áŒŠá);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final GameContainer container, final StateBasedGame game, final Graphics g) throws SlickException {
            this.Âµá€.HorizonCode_Horizon_È(0.0f, 0.0f, 800.0f, 600.0f);
            g.Â(Color.Âµá€);
            g.Ø­áŒŠá(-50.0f, 200.0f, 50.0f, 50.0f);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final GameContainer container, final StateBasedGame game, final int delta) throws SlickException {
            if (container.á€().Âµá€(57)) {
                final Transition[] pair = TransitionTest.this.áˆºÑ¢Õ();
                game.HorizonCode_Horizon_È(this.Ý, pair[0], pair[1]);
            }
        }
    }
}
