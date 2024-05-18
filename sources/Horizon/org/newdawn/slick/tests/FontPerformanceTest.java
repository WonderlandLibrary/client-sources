package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Font;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import java.util.ArrayList;
import HORIZON-6-0-SKIDPROTECTION.AngelCodeFont;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class FontPerformanceTest extends BasicGame
{
    private AngelCodeFont Ó;
    private String à;
    private ArrayList Ø;
    private boolean áŒŠÆ;
    
    public FontPerformanceTest() {
        super("Font Performance Test");
        this.à = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin bibendum. Aliquam ac sapien a elit congue iaculis. Quisque et justo quis mi mattis euismod. Donec elementum, mi quis aliquet varius, nisi leo volutpat magna, quis ultricies eros augue at risus. Integer non magna at lorem sodales molestie. Integer diam nulla, ornare sit amet, mattis quis, euismod et, mauris. Proin eget tellus non nisl mattis laoreet. Nunc at nunc id elit pretium tempor. Duis vulputate, nibh eget rhoncus eleifend, tellus lectus sollicitudin mi, rhoncus tincidunt nisi massa vitae ipsum. Praesent tellus diam, luctus ut, eleifend nec, auctor et, orci. Praesent eu elit. Pellentesque ante orci, volutpat placerat, ornare eget, cursus sit amet, eros. Duis pede sapien, euismod a, volutpat pellentesque, convallis eu, mauris. Nunc eros. Ut eu risus et felis laoreet viverra. Curabitur a metus.";
        this.Ø = new ArrayList();
        this.áŒŠÆ = true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new AngelCodeFont("testdata/perffont.fnt", "testdata/perffont.png");
        for (int j = 0; j < 2; ++j) {
            for (int lineLen = 90, i = 0; i < this.à.length(); i += lineLen) {
                if (i + lineLen > this.à.length()) {
                    lineLen = this.à.length() - i;
                }
                this.Ø.add(this.à.substring(i, i + lineLen));
            }
            this.Ø.add("");
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.HorizonCode_Horizon_È(this.Ó);
        if (this.áŒŠÆ) {
            for (int i = 0; i < this.Ø.size(); ++i) {
                this.Ó.HorizonCode_Horizon_È(10.0f, 50 + i * 20, this.Ø.get(i), (i > 10) ? Color.Âµá€ : Color.à);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
        if (key == 57) {
            this.áŒŠÆ = !this.áŒŠÆ;
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new FontPerformanceTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
