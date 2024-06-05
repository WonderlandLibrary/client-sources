package tk.rektsky.Module.World;

import tk.rektsky.Module.*;
import tk.rektsky.Module.Settings.*;
import tk.rektsky.Utils.Display.*;
import tk.rektsky.Module.Render.*;
import tk.rektsky.*;
import tk.rektsky.Utils.Block.*;
import java.util.*;
import tk.rektsky.Event.*;
import net.minecraft.client.*;
import net.minecraft.network.play.server.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class BedRekter extends Module
{
    float range;
    int ticks;
    int targetTick;
    public float shouldDamage;
    public float damage;
    public BlockPos target;
    public BlockPos playerBed;
    public BlockPos playerBedHead;
    public Long lastHudRenderTime;
    public Long firstHudRenderTime;
    String targetBlock;
    boolean enable;
    public BooleanSetting espSetting;
    public BooleanSetting hudSetting;
    
    public BedRekter() {
        super("BedRekter", "Automatically destroys beds around you. (You can run away before it fully breaks)", 0, Category.WORLD);
        this.targetBlock = "tile.bed";
        this.enable = true;
        this.espSetting = new BooleanSetting("ESP", true);
        this.hudSetting = new BooleanSetting("HUD", true);
    }
    
    @Override
    public void onEnable() {
        this.ticks = 0;
        this.target = null;
        this.searchForPlayerBed();
    }
    
    public void doWarning(final String msg) {
        Client.notify(new Notification.PopupMessage("BedRekter", msg, ColorUtil.NotificationColors.YELLOW, 40));
    }
    
    public void doError(final String msg) {
        Client.notify(new Notification.PopupMessage("BedRekter", msg, ColorUtil.NotificationColors.RED, 40));
    }
    
    public void doNotification(final String msg) {
        Client.notify(new Notification.PopupMessage("BedRekter", msg, ColorUtil.NotificationColors.GREEN, 40));
    }
    
    @Override
    public void onDisable() {
    }
    
    public void searchForPlayerBed() {
        final ArrayList<BlockPos> beds = BlockUtils.searchForBlock(this.targetBlock, 50.0f);
        if (beds.size() <= 0) {
            this.doError(" I can't find your bed so I might try to break it! If you're sure this is Bedwars, please contact developer and tell them to fix me : )");
            return;
        }
        for (final BlockPos pos : beds) {
            if (this.playerBed == null) {
                this.playerBed = pos;
            }
            else {
                if (this.mc.thePlayer.getDistance(pos.getX(), pos.getY(), pos.getZ()) >= this.mc.thePlayer.getDistance(this.playerBed.getX(), this.playerBed.getY(), this.playerBed.getZ())) {
                    continue;
                }
                this.playerBed = pos;
            }
        }
        if (this.playerBed == null) {
            this.doError(" I can't find your bed so I might try to break it! If you're sure this is Bedwars, please contact developer and tell them to fix me : )");
            return;
        }
        BlockPos head = new BlockPos(this.playerBed.getX() + 1, this.playerBed.getY(), this.playerBed.getZ());
        if (this.mc.theWorld.getBlockState(head).getBlock().getUnlocalizedName().equals(this.targetBlock)) {
            this.playerBedHead = head;
        }
        head = new BlockPos(this.playerBed.getX() + 1, this.playerBed.getY(), this.playerBed.getZ());
        if (this.mc.theWorld.getBlockState(head).getBlock().getUnlocalizedName().equals(this.targetBlock)) {
            this.playerBedHead = head;
        }
        head = new BlockPos(this.playerBed.getX(), this.playerBed.getY(), this.playerBed.getZ() + 1);
        if (this.mc.theWorld.getBlockState(head).getBlock().getUnlocalizedName().equals(this.targetBlock)) {
            this.playerBedHead = head;
        }
        head = new BlockPos(this.playerBed.getX(), this.playerBed.getY(), this.playerBed.getZ() + 1);
        if (this.mc.theWorld.getBlockState(head).getBlock().getUnlocalizedName().equals(this.targetBlock)) {
            this.playerBedHead = head;
        }
        head = new BlockPos(this.playerBed.getX() - 1, this.playerBed.getY(), this.playerBed.getZ());
        if (this.mc.theWorld.getBlockState(head).getBlock().getUnlocalizedName().equals(this.targetBlock)) {
            this.playerBedHead = head;
        }
        head = new BlockPos(this.playerBed.getX() - 1, this.playerBed.getY(), this.playerBed.getZ());
        if (this.mc.theWorld.getBlockState(head).getBlock().getUnlocalizedName().equals(this.targetBlock)) {
            this.playerBedHead = head;
        }
        head = new BlockPos(this.playerBed.getX(), this.playerBed.getY(), this.playerBed.getZ() - 1);
        if (this.mc.theWorld.getBlockState(head).getBlock().getUnlocalizedName().equals(this.targetBlock)) {
            this.playerBedHead = head;
        }
        head = new BlockPos(this.playerBed.getX(), this.playerBed.getY(), this.playerBed.getZ() - 1);
        if (this.mc.theWorld.getBlockState(head).getBlock().getUnlocalizedName().equals(this.targetBlock)) {
            this.playerBedHead = head;
        }
        if (this.playerBedHead == null) {
            this.doError(" I can't find your bed so I might going to break it! If you're sure this is Bedwars, please contact developer and tell them to fix me : )");
            return;
        }
        this.doNotification("Your bed is at " + this.playerBed.getX() + ", " + this.playerBed.getY() + "," + this.playerBed.getZ() + " !");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PacketReceiveEvent && Minecraft.getMinecraft().theWorld != null) {
            final Packet p = ((PacketReceiveEvent)e).getPacket();
            if (p instanceof S07PacketRespawn) {
                this.playerBed = null;
                this.playerBedHead = null;
            }
            if (p instanceof S02PacketChat && ((S02PacketChat)p).chatComponent.getUnformattedText().contains("Seu objetivo \u00e9 proteger sua cama enquanto tenta destruir as camas de ilhas advers\u00e1rias e eliminar os jogadores inimigos. Use os min\u00e9rios gerados em sua il") && (this.playerBed == null || this.playerBedHead == null)) {
                this.searchForPlayerBed();
            }
        }
        if (e instanceof PacketSentEvent && Minecraft.getMinecraft().theWorld != null) {
            final Packet p = ((PacketSentEvent)e).getPacket();
            if (p instanceof C08PacketPlayerBlockPlacement) {
                final C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = (C08PacketPlayerBlockPlacement)p;
            }
        }
        if (e instanceof RenderEvent && this.target != null) {
            this.ESPBed(this.target);
        }
        if (e instanceof WorldTickEvent) {
            if (!this.enable) {
                return;
            }
            this.range = this.mc.playerController.getBlockReachDistance() - 0.5f;
            if (this.target == null) {
                for (final BlockPos b : BlockUtils.searchForBlock(this.targetBlock, this.range)) {
                    if (!b.equals(this.playerBed)) {
                        if (b.equals(this.playerBedHead)) {
                            continue;
                        }
                        this.doWarning("I'm start breaking bed: " + b.getX() + ", " + b.getY() + ", " + b.getZ() + " ! Please wait !");
                        this.target = b;
                        this.damage = 0.0f;
                        this.shouldDamage = this.mc.theWorld.getBlockState(this.target).getBlock().getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.theWorld, this.target);
                        this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.target, EnumFacing.DOWN));
                    }
                }
                return;
            }
            ++this.targetTick;
            this.damage += this.shouldDamage;
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.target, EnumFacing.DOWN));
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.target, EnumFacing.DOWN));
            if (!this.mc.theWorld.getBlockState(this.target).getBlock().getUnlocalizedName().equals(this.targetBlock)) {
                this.target = null;
            }
            ++this.ticks;
        }
    }
    
    public void ESPBed(final BlockPos head) {
        GlStateManager.pushMatrix();
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GlStateManager.color(255.0f, 255.0f, 255.0f, 255.0f);
        GL11.glLineWidth(4.0f);
        GL11.glBegin(2);
        GL11.glVertex3d(head.getX() - this.mc.renderManager.viewerPosX, head.getY() - this.mc.renderManager.viewerPosY + 0.5625, head.getZ() - this.mc.renderManager.viewerPosZ + 1.0);
        GL11.glVertex3d(head.getX() - this.mc.renderManager.viewerPosX + 1.0, head.getY() - this.mc.renderManager.viewerPosY + 0.5625, head.getZ() - this.mc.renderManager.viewerPosZ + 1.0);
        GL11.glVertex3d(head.getX() - this.mc.renderManager.viewerPosX + 1.0, head.getY() - this.mc.renderManager.viewerPosY + 0.5625, head.getZ() - this.mc.renderManager.viewerPosZ);
        GL11.glVertex3d(head.getX() - this.mc.renderManager.viewerPosX, head.getY() - this.mc.renderManager.viewerPosY + 0.5625, head.getZ() - this.mc.renderManager.viewerPosZ);
        GL11.glEnd();
        GL11.glRotatef(-this.mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(this.mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GlStateManager.popMatrix();
    }
}
