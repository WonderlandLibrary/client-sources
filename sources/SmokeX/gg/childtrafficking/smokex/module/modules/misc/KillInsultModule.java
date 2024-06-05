// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.misc;

import gg.childtrafficking.smokex.SmokeXClient;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.combat.KillauraModule;
import org.apache.commons.lang3.RandomUtils;
import net.minecraft.network.play.server.S02PacketChat;
import java.util.ArrayList;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.EventListener;
import java.util.List;
import java.io.File;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "KillInsult", renderName = "Kill Insult", description = "Makes fun of dirty little Rise users after you 5 heart them", category = ModuleCategory.MISC)
public final class KillInsultModule extends Module
{
    public static final File KILLINSULTS_TXT;
    private final List<String> killInsultList;
    private int lastIndex;
    private String[] messageTriggers;
    private final EventListener<EventReceivePacket> receivePacketEventListener;
    
    public KillInsultModule() {
        this.killInsultList = new ArrayList<String>();
        this.receivePacketEventListener = (event -> {
            if (!this.killInsultList.isEmpty()) {
                if (event.getPacket() instanceof S02PacketChat && this.mc.thePlayer != null) {
                    this.messageTriggers = new String[] { "by " + this.mc.thePlayer.getName(), "was killed with magic while fighting " + this.mc.thePlayer.getName(), "KILL!", "fell to their death while escaping " + this.mc.thePlayer.getName() };
                    final S02PacketChat packetChat = (S02PacketChat)event.getPacket();
                    final String chatComponent = packetChat.getChatComponent().getUnformattedText();
                    int index = RandomUtils.nextInt(0, this.killInsultList.size() - 1);
                    this.lastIndex = index;
                    if (this.killInsultList.size() > 1) {
                        while (index == this.lastIndex) {
                            index = RandomUtils.nextInt(0, this.killInsultList.size() - 1);
                        }
                    }
                    final String[] messageTriggers = this.messageTriggers;
                    int i = 0;
                    for (int length = messageTriggers.length; i < length; ++i) {
                        final String bruh = messageTriggers[i];
                        if (chatComponent.contains(bruh)) {
                            final String string = this.killInsultList.get(index).replaceAll("<target>", ModuleManager.getInstance(KillauraModule.class).previousTarget.getName());
                            this.mc.thePlayer.sendChatMessage(string);
                        }
                    }
                }
            }
        });
    }
    
    @Override
    public void onEnable() {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(KillInsultModule.KILLINSULTS_TXT));
            String s;
            while ((s = reader.readLine()) != null) {
                this.killInsultList.add(s);
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        super.onEnable();
    }
    
    static {
        KILLINSULTS_TXT = new File(SmokeXClient.getInstance().getClientDirectory(), "killInsults.txt");
    }
}
