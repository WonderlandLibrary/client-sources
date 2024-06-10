package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.config.*;
import java.util.*;

public class AutoGGModule extends Module
{
    public static AutoGGModule INSTANCE;
    private long lastTrigger;
    
    public AutoGGModule() {
        super("AutoGG");
        this.lastTrigger = 0L;
        AutoGGModule.INSTANCE = this;
    }
    
    public void onChat(final eu message) {
        if (ModuleConfig.INSTANCE.isEnabled(this) && ave.A().D() != null && ave.A().D().b != null && System.currentTimeMillis() > this.lastTrigger + 1000L && Arrays.stream(this.getHypixelTrigger().split("\n")).anyMatch(match -> message.c().contains(match))) {
            ave.A().h.e("/achat gg");
            ave.A().q.d().a("/achat gg");
            this.lastTrigger = System.currentTimeMillis();
        }
    }
    
    private String getHypixelTrigger() {
        return "1st Killer - \n1st Place - \nWinner: \n - Damage Dealt - \nWinning Team -\n1st - \nWinners: \nWinner: \nWinning Team: \n won the game!\nTop Seeker: \n1st Place: \nLast team standing!\nWinner #1 (\nTop Survivors\nWinners - \nSumo Duel - \n";
    }
}
