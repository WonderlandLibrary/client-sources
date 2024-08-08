// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import me.perry.mcdonalds.util.BlockUtil;
import me.perry.mcdonalds.event.events.BlockEvent;
import me.perry.mcdonalds.util.RenderUtil;
import java.awt.Color;
import me.perry.mcdonalds.McDonalds;
import me.perry.mcdonalds.event.events.Render3DEvent;
import me.perry.mcdonalds.util.InventoryUtil;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.util.Timer;
import me.perry.mcdonalds.features.modules.Module;

public class Speedmine extends Module
{
    private static Speedmine INSTANCE;
    private final Timer timer;
    public Setting<Mode> mode;
    public Setting<Float> damage;
    public Setting<Boolean> webSwitch;
    public Setting<Boolean> doubleBreak;
    public Setting<Boolean> render;
    public Setting<Boolean> box;
    private final Setting<Integer> boxAlpha;
    public Setting<Boolean> outline;
    private final Setting<Float> lineWidth;
    public BlockPos currentPos;
    public IBlockState currentBlockState;
    
    public Speedmine() {
        super("Speedmine", "Speeds up mining.", Category.PLAYER, true, false, false);
        this.timer = new Timer();
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.PACKET));
        this.damage = (Setting<Float>)this.register(new Setting("Damage", (T)0.7f, (T)0.0f, (T)1.0f, v -> this.mode.getValue() == Mode.DAMAGE));
        this.webSwitch = (Setting<Boolean>)this.register(new Setting("WebSwitch", (T)false));
        this.doubleBreak = (Setting<Boolean>)this.register(new Setting("DoubleBreak", (T)false));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)false));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (T)false, v -> this.render.getValue()));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)85, (T)0, (T)255, v -> this.box.getValue() && this.render.getValue()));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true, v -> this.render.getValue()));
        this.lineWidth = (Setting<Float>)this.register(new Setting("Width", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.outline.getValue() && this.render.getValue()));
        this.setInstance();
    }
    
    public static Speedmine getInstance() {
        if (Speedmine.INSTANCE == null) {
            Speedmine.INSTANCE = new Speedmine();
        }
        return Speedmine.INSTANCE;
    }
    
    private void setInstance() {
        Speedmine.INSTANCE = this;
    }
    
    @Override
    public void onTick() {
        if (this.currentPos != null) {
            if (!Speedmine.mc.world.getBlockState(this.currentPos).equals(this.currentBlockState) || Speedmine.mc.world.getBlockState(this.currentPos).getBlock() == Blocks.AIR) {
                this.currentPos = null;
                this.currentBlockState = null;
            }
            else if (this.webSwitch.getValue() && this.currentBlockState.getBlock() == Blocks.WEB && Speedmine.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
                InventoryUtil.switchToHotbarSlot(ItemSword.class, false);
            }
        }
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        Speedmine.mc.playerController.blockHitDelay = 0;
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.render.getValue() && this.currentPos != null && this.currentBlockState.getBlock() == Blocks.OBSIDIAN) {
            final Color color = new Color(this.timer.passedMs((int)(2000.0f * McDonalds.serverManager.getTpsFactor())) ? 0 : 255, this.timer.passedMs((int)(2000.0f * McDonalds.serverManager.getTpsFactor())) ? 255 : 0, 0, 255);
            RenderUtil.drawBoxESP(this.currentPos, color, false, color, this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), false);
        }
    }
    
    @SubscribeEvent
    public void onBlockEvent(final BlockEvent event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getStage() == 3 && Speedmine.mc.playerController.curBlockDamageMP > 0.1f) {
            Speedmine.mc.playerController.isHittingBlock = true;
        }
        if (event.getStage() == 4) {
            if (BlockUtil.canBreak(event.pos)) {
                Speedmine.mc.playerController.isHittingBlock = false;
                switch (this.mode.getValue()) {
                    case PACKET: {
                        if (this.currentPos == null) {
                            this.currentPos = event.pos;
                            this.currentBlockState = Speedmine.mc.world.getBlockState(this.currentPos);
                            this.timer.reset();
                        }
                        Speedmine.mc.player.swingArm(EnumHand.MAIN_HAND);
                        Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.pos, event.facing));
                        Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.pos, event.facing));
                        event.setCanceled(true);
                        break;
                    }
                    case DAMAGE: {
                        if (Speedmine.mc.playerController.curBlockDamageMP < this.damage.getValue()) {
                            break;
                        }
                        Speedmine.mc.playerController.curBlockDamageMP = 1.0f;
                        break;
                    }
                    case INSTANT: {
                        Speedmine.mc.player.swingArm(EnumHand.MAIN_HAND);
                        Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.pos, event.facing));
                        Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.pos, event.facing));
                        Speedmine.mc.playerController.onPlayerDestroyBlock(event.pos);
                        Speedmine.mc.world.setBlockToAir(event.pos);
                        break;
                    }
                }
            }
            final BlockPos above;
            if (this.doubleBreak.getValue() && BlockUtil.canBreak(above = event.pos.add(0, 1, 0)) && Speedmine.mc.player.getDistance((double)above.getX(), (double)above.getY(), (double)above.getZ()) <= 5.0) {
                Speedmine.mc.player.swingArm(EnumHand.MAIN_HAND);
                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, above, event.facing));
                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, above, event.facing));
                Speedmine.mc.playerController.onPlayerDestroyBlock(above);
                Speedmine.mc.world.setBlockToAir(above);
            }
        }
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.currentEnumName();
    }
    
    static {
        Speedmine.INSTANCE = new Speedmine();
    }
    
    public enum Mode
    {
        PACKET, 
        DAMAGE, 
        INSTANT;
    }
}
