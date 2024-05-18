package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import java.util.ArrayList;
import HORIZON-6-0-SKIDPROTECTION.TrueTypeFont;
import java.awt.Font;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class TrueTypeFontPerformanceTest extends BasicGame
{
    private Font Ó;
    private TrueTypeFont à;
    private String Ø;
    private ArrayList áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    
    public TrueTypeFontPerformanceTest() {
        super("Font Performance Test");
        this.Ø = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin bibendum. Aliquam ac sapien a elit congue iaculis. Quisque et justo quis mi mattis euismod. Donec elementum, mi quis aliquet varius, nisi leo volutpat magna, quis ultricies eros augue at risus. Integer non magna at lorem sodales molestie. Integer diam nulla, ornare sit amet, mattis quis, euismod et, mauris. Proin eget tellus non nisl mattis laoreet. Nunc at nunc id elit pretium tempor. Duis vulputate, nibh eget rhoncus eleifend, tellus lectus sollicitudin mi, rhoncus tincidunt nisi massa vitae ipsum. Praesent tellus diam, luctus ut, eleifend nec, auctor et, orci. Praesent eu elit. Pellentesque ante orci, volutpat placerat, ornare eget, cursus sit amet, eros. Duis pede sapien, euismod a, volutpat pellentesque, convallis eu, mauris. Nunc eros. Ut eu risus et felis laoreet viverra. Curabitur a metus.";
        this.áŒŠÆ = new ArrayList();
        this.áˆºÑ¢Õ = true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new Font("Verdana", 0, 16);
        this.à = new TrueTypeFont(this.Ó, false);
        for (int j = 0; j < 2; ++j) {
            for (int lineLen = 90, i = 0; i < this.Ø.length(); i += lineLen) {
                if (i + lineLen > this.Ø.length()) {
                    lineLen = this.Ø.length() - i;
                }
                this.áŒŠÆ.add(this.Ø.substring(i, i + lineLen));
            }
            this.áŒŠÆ.add("");
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.HorizonCode_Horizon_È(this.à);
        if (this.áˆºÑ¢Õ) {
            for (int i = 0; i < this.áŒŠÆ.size(); ++i) {
                this.à.HorizonCode_Horizon_È(10.0f, 50 + i * 20, this.áŒŠÆ.get(i), (i > 10) ? Color.Âµá€ : Color.à);
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
            this.áˆºÑ¢Õ = !this.áˆºÑ¢Õ;
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new TrueTypeFontPerformanceTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
