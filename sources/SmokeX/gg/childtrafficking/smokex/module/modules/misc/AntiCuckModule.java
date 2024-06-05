// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.misc;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import java.util.Objects;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import java.util.List;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "AntiCuck", renderName = "Anti Cuck", description = "Saves you from being banned by players with staff connections.", category = ModuleCategory.MISC)
public final class AntiCuckModule extends Module
{
    private final String CUCKS_URL = "https://xerusuwu.000webhostapp.com/cucks.txt";
    private List<String> CUCKS;
    private final EventListener<EventUpdate> updateEventListener;
    
    public AntiCuckModule() {
        this.CUCKS = new ArrayList<String>();
        this.updateEventListener = (event -> {
            this.mc.theWorld.playerEntities.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final EntityPlayer entityPlayer = iterator.next();
                this.CUCKS.iterator();
                final Iterator iterator2;
                while (iterator2.hasNext()) {
                    final String iWantToDie = iterator2.next();
                    if (Objects.equals(entityPlayer.getName(), iWantToDie)) {
                        ChatUtils.addChatMessage("§cCUCK DETECTED - IGN: " + entityPlayer.getName());
                        this.mc.thePlayer.sendChatMessage(",hub");
                        this.mc.thePlayer.sendChatMessage("/hub");
                    }
                }
            }
        });
    }
    
    @Override
    public void onEnable() {
        this.CUCKS.clear();
        try {
            final URL url = new URL("https://xerusuwu.000webhostapp.com/cucks.txt");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s;
            while ((s = reader.readLine()) != null) {
                this.CUCKS.add(s);
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        super.onEnable();
    }
}
