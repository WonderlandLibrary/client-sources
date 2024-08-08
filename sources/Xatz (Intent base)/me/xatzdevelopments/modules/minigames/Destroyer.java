package me.xatzdevelopments.modules.minigames;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.modules.Module.Category;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.util.ModulesUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

import com.sun.javafx.geom.Vec3d;
import com.sun.javafx.geom.Vec3f;

public class Destroyer extends Module
{
    private BlockPos currentPos;
    private EnumFacing currentFacing;
    boolean rotated;
    public NumberSetting Range;
    public BooleanSetting Swing;
    public BooleanSetting Cake;
    public BooleanSetting Bed;
    
    public Destroyer() {
		super("Destroyer", Keyboard.KEY_NONE, Category.MINIGAMES, "Destroys blocks through defence, useful for bedwars and etc.");
        this.rotated = true;
        this.Range = new NumberSetting("Range", 4.0, 3.0, 7.0, 0.5);
        this.Swing = new BooleanSetting("Swing", true);
        this.Cake = new BooleanSetting("Cake", true);
        this.Bed = new BooleanSetting("Bed", true);
        this.addSettings(this.Range, this.Swing, this.Cake, this.Bed);
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            this.isInteracting = false;
            if (Xatz.getModuleByName("Fly").isEnabled() && ModulesUtils.GetModeSetting("Fly", "Mode").getMode() == "Mineplex") {
                return;
            }
            ArrayList<Integer> pos = this.getBlock(Blocks.cake, (int)this.Range.getValue());
            if (this.Cake.isEnabled() && pos != null) {
                final BlockPos pos2 = new BlockPos(this.mc.thePlayer.posX + pos.get(0), this.mc.thePlayer.posY + pos.get(1), this.mc.thePlayer.posZ + pos.get(2));
                final BlockPos currentPos = pos2.add(pos.get(0), pos.get(1), pos.get(2));
                this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem(), pos2, EnumFacing.UP, new Vec3(currentPos.getX(), currentPos.getY(), currentPos.getZ()));
                this.isInteracting = true;
                if (this.Swing.isEnabled()) {
                    this.mc.thePlayer.swingItem();
                }
            }
            pos = this.getBlock(Blocks.bed, (int)this.Range.getValue());
            if (this.Bed.isEnabled() && pos != null) {
                final BlockPos pos2 = new BlockPos(this.mc.thePlayer.posX + pos.get(0), this.mc.thePlayer.posY + pos.get(1), this.mc.thePlayer.posZ + pos.get(2));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos2, EnumFacing.NORTH));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos2, EnumFacing.NORTH));
                this.isInteracting = true;
                if (this.Swing.isEnabled()) {
                    this.mc.thePlayer.swingItem();
                }
            }
        }
    }
    
    public ArrayList<Integer> getBlock(final Block b, final int r) {
        final ArrayList<Integer> pos = new ArrayList<Integer>();
        for (int x = -r; x < r; ++x) {
            for (int y = r; y > -r; --y) {
                for (int z = -r; z < r; ++z) {
                    if (this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX + x, this.mc.thePlayer.posY + y, this.mc.thePlayer.posZ + z)).getBlock() == b) {
                        pos.add(x);
                        pos.add(y);
                        pos.add(z);
                        return pos;
                    }
                }
            }
        }
        return null;
    }
}

