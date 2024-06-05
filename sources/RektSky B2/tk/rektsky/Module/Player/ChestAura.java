package tk.rektsky.Module.Player;

import tk.rektsky.Module.*;
import tk.rektsky.Module.Settings.*;
import tk.rektsky.Utils.Display.*;
import tk.rektsky.Module.Render.*;
import tk.rektsky.*;
import tk.rektsky.Event.*;
import tk.rektsky.Utils.Block.*;
import net.minecraft.util.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import net.minecraft.network.*;

public class ChestAura extends Module
{
    public ArrayList<BlockPos> position;
    public IntSetting rangeSetting;
    private int ticks;
    private int ticksSinceStartGame;
    private boolean notified;
    
    public ChestAura() {
        super("ChestAura", "Auto open chests in a specific range", 0, Category.PLAYER);
        this.position = new ArrayList<BlockPos>();
        this.rangeSetting = new IntSetting("Range", 1, 10, 4);
        this.ticks = 0;
        this.registerSetting(this.rangeSetting);
    }
    
    @Override
    public void onEnable() {
        this.position.clear();
        Client.notifyWithClassName(new Notification.PopupMessage("ChestAura", "You can re-open all chests you've opened by re-enable this module!", ColorUtil.NotificationColors.GREEN.getColor(), ColorUtil.NotificationColors.GREEN.getTitleColor(), 20));
        this.ticks = 0;
        this.ticksSinceStartGame = 0;
        this.notified = false;
    }
    
    @Override
    public void onEvent(final Event event) {
        ++this.ticks;
        if (event instanceof WorldTickEvent) {
            if (this.ticks < this.ticksSinceStartGame + 40) {
                return;
            }
            if (this.ticks % 2 == 0) {
                for (final BlockPos block : BlockUtils.searchForBlock("tile.chest", 3.0f)) {
                    if (!this.position.contains(block)) {
                        this.position.add(block);
                        this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem(), block, EnumFacing.DOWN, new Vec3(block.getX(), block.getY(), block.getZ()));
                        break;
                    }
                }
            }
        }
        if (event instanceof PacketReceiveEvent) {
            final Packet p = ((PacketReceiveEvent)event).getPacket();
            if (p instanceof S02PacketChat && ((S02PacketChat)p).getChatComponent().getUnformattedText().contains("Utilize os itens encontrados em ba\u00fas em sua ilha para se equipar, eliminar outros jogadores e chegar ao centro do mapa, onde encontrar\u00e1 itens ainda melhores. \u00c9 um jogo r\u00e1pido de muito combate. Vencer\u00e1 o \u00faltimo jogador")) {
                this.ticksSinceStartGame = this.ticks;
                Client.notify(new Notification.PopupMessage("ChestAura", "Detect game started! ChestAura will work after 2 seconds!", ColorUtil.NotificationColors.YELLOW.getColor(), ColorUtil.NotificationColors.YELLOW.getTitleColor(), 40));
            }
        }
    }
}
