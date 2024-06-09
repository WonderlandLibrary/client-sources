package org.newdawn.slick.tests;

import java.io.IOException;
import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Input;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.ColorEffect;
import java.awt.Color;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.UnicodeFont;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class UnicodeFontTest extends BasicGame
{
    private UnicodeFont Ó;
    
    public UnicodeFontTest() {
        super("Font Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        container.ÂµÈ(false);
        this.Ó = new UnicodeFont("c:/windows/fonts/arial.ttf", 48, false, false);
        this.Ó.µÕ().add(new ColorEffect(Color.white));
        container.ˆáŠ().HorizonCode_Horizon_È(HORIZON-6-0-SKIDPROTECTION.Color.ÂµÈ);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.Â(HORIZON-6-0-SKIDPROTECTION.Color.Ý);
        final String text = "This is UnicodeFont!\nIt rockz. Kerning: T,";
        this.Ó.HorizonCode_Horizon_È(10.0f, 33.0f, text);
        g.Â(HORIZON-6-0-SKIDPROTECTION.Color.Âµá€);
        g.Â(10.0f, 33.0f, this.Ó.Ý(text), this.Ó.HorizonCode_Horizon_È());
        g.Â(HORIZON-6-0-SKIDPROTECTION.Color.Ó);
        final int yOffset = this.Ó.Ø­áŒŠá(text);
        g.Â(10.0f, 33 + yOffset, this.Ó.Ý(text), this.Ó.Â(text) - yOffset);
        this.Ó.HorizonCode_Horizon_È("~!@!#!#$%___--");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        this.Ó.HorizonCode_Horizon_È(1);
    }
    
    public static void main(final String[] args) throws SlickException, IOException {
        Input.HorizonCode_Horizon_È();
        final AppGameContainer container = new AppGameContainer(new UnicodeFontTest());
        container.HorizonCode_Horizon_È(512, 600, false);
        container.Ó(20);
        container.Ø­áŒŠá();
    }
}
