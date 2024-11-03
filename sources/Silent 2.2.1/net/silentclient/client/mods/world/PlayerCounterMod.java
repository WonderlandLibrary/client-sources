package net.silentclient.client.mods.world;

import net.silentclient.client.Client;
import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

import java.text.DecimalFormat;

public class PlayerCounterMod extends HudMod {
    private static final DecimalFormat df = new DecimalFormat("#,##0.##");

    public PlayerCounterMod() {
        super("Player Counter", ModCategory.MODS, "silentclient/icons/mods/playercounter.png");
    }

    @Override
    public String getText() {
        return getPostText() + ": " + df.format(1000000000);
    }

    @Override
    public String getTextForRender() {
        if(mc.getCurrentServerData() == null) {
            return getPostText() + ": 1";
        }
        return getPostText() + ": " + df.format(Client.getInstance().playersCount);
    }
    
    @Override
    public String getDefautPostText() {
    	return "Players Online";
    }
}
