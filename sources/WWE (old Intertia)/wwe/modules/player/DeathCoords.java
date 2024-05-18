package wwe.modules.player;

import wwe.modules.*;
import wwe.utils.*;
import wwe.*;

public class DeathCoords extends qMIe
{
    int OJXa;
    
    public DeathCoords() {
        super("Death Coords", "Shows your death coords in chat", 0, Category.PLAYER);
        this.OJXa = 0;
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (Wrapper.mc.player.getHealth() == 0.0f) {
            ++this.OJXa;
            if (this.OJXa == 1) {
                HAxG.TuzD(Math.round(Wrapper.mc.player.posX) + " " + Math.round(Wrapper.mc.player.posY) + " " + Math.round(Wrapper.mc.player.posZ));
            }
        }
        if (Wrapper.mc.player.getHealth() > 5.0f) {
            this.OJXa = 0;
        }
    }
}
