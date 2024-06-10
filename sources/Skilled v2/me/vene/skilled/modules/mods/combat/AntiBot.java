 package me.vene.skilled.modules.mods.combat;
 
 import com.google.common.collect.Ordering;
 import java.lang.reflect.Field;
 import java.util.ArrayList;
 import java.util.List;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.GuiPlayerTabOverlay;
 import net.minecraft.client.network.NetworkPlayerInfo;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class AntiBot extends Module
{
    public static List<EntityPlayer> bots;
    private static Minecraft mc;
    
    public AntiBot() {
        super("AntiBot", 0, Category.C);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.PlayerTickEvent e) {
        if (AntiBot.mc.thePlayer == null || AntiBot.mc.theWorld == null) {
            return;
        }
        for (int k = 0; k < AntiBot.mc.theWorld.playerEntities.size(); ++k) {
            final EntityPlayer ent = AntiBot.mc.theWorld.playerEntities.get(k);
            final List<EntityPlayer> tabList = this.getTabPlayerList();
            if (!AntiBot.bots.contains(ent) && !tabList.contains(ent)) {
                AntiBot.bots.add(ent);
            }
            else if (AntiBot.bots.contains(ent) && tabList.contains(ent)) {
                AntiBot.bots.remove(ent);
            }
        }
    }
    
    @Override
    public void onDisable() {
        AntiBot.bots.clear();
    }
    
    private List<EntityPlayer> getTabPlayerList() {
        final List<EntityPlayer> list;
        (list = new ArrayList<EntityPlayer>()).clear();
        final Ordering<NetworkPlayerInfo> field_175252_a = this.field_175252_a();
        if (field_175252_a == null) {
            return list;
        }
        final List players = field_175252_a.sortedCopy((Iterable)AntiBot.mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object o : players) {
            final NetworkPlayerInfo info = (NetworkPlayerInfo)o;
            if (info == null) {
                continue;
            }
            list.add(AntiBot.mc.theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
        }
        return list;
    }
    
    private Ordering<NetworkPlayerInfo> field_175252_a() {
        try {
            final Class<GuiPlayerTabOverlay> c = GuiPlayerTabOverlay.class;
            final Field f = c.getField("field_175252_a");
            f.setAccessible(true);
            return (Ordering<NetworkPlayerInfo>)f.get(GuiPlayerTabOverlay.class);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    static {
        AntiBot.bots = new ArrayList<EntityPlayer>();
        AntiBot.mc = Minecraft.getMinecraft();
    }
}
