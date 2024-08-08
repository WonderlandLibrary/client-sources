package net.futureclient.client.modules.render;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.GameType;
import net.futureclient.loader.mixin.common.network.wrapper.INetworkPlayerInfo;
import net.futureclient.client.modules.render.freecam.Listener6;
import net.futureclient.client.modules.render.freecam.Listener5;
import net.futureclient.client.modules.render.freecam.Listener4;
import net.futureclient.client.modules.render.freecam.Listener3;
import net.futureclient.client.modules.render.freecam.Listener2;
import net.futureclient.client.modules.render.freecam.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.futureclient.client.utils.NumberValue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.futureclient.client.dd;
import net.futureclient.client.R;
import net.futureclient.client.Ea;

public class Freecam extends Ea
{
    private R<dd.FB> mode;
    private EntityOtherPlayerMP d;
    private boolean a;
    public NumberValue speed;
    private Entity k;
    
    public static Minecraft getMinecraft() {
        return Freecam.D;
    }
    
    public Freecam() {
        super("Freecam", new String[] { "Freecam", "Reecam", "camera" }, false, -3217280, Category.RENDER);
        this.mode = new R<dd.FB>(dd.FB.D, new String[] { "Mode", "m", "type" });
        this.speed = new NumberValue(1.0, 0.0, 0.0, 1.273197475E-314, new String[] { "Speed", "camspeed" });
        final int n = 2;
        this.a = false;
        final Value[] array = new Value[n];
        array[0] = this.mode;
        array[1] = this.speed;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this), new Listener5(this), new Listener6(this) });
        this.M(false);
    }
    
    public static Minecraft getMinecraft1() {
        return Freecam.D;
    }
    
    public void B() {
        super.B();
        if (Freecam.D.world == null) {
            this.M(false);
            return;
        }
        if (Freecam.D.player != null) {
            if (this.mode.M() == dd.FB.k && Freecam.D.getConnection() != null && Freecam.D.getConnection().getPlayerInfo(Freecam.D.player.getUniqueID()) != null) {
                ((INetworkPlayerInfo)Freecam.D.getConnection().getPlayerInfo(Freecam.D.player.getGameProfile().getId())).setGameType(GameType.SPECTATOR);
            }
            (this.d = new EntityOtherPlayerMP((World)Freecam.D.world, new GameProfile(Freecam.D.player.getUniqueID(), Freecam.D.player.getName()))).setPositionAndRotation(Freecam.D.player.posX, Freecam.D.player.posY, Freecam.D.player.posZ, Freecam.D.player.rotationYaw, Freecam.D.player.rotationPitch);
            this.d.inventory = Freecam.D.player.inventory;
            this.d.inventoryContainer = Freecam.D.player.inventoryContainer;
            this.d.rotationYawHead = Freecam.D.player.rotationYawHead;
            this.d.motionX = Freecam.D.player.motionX;
            this.d.motionY = Freecam.D.player.motionY;
            this.d.motionZ = Freecam.D.player.motionZ;
            this.d.setSneaking(Freecam.D.player.isSneaking());
            if (Freecam.D.player.getRidingEntity() != null) {
                this.d.startRiding(Freecam.D.player.getRidingEntity(), true);
                Freecam.D.player.dismountRidingEntity();
            }
            Freecam.D.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Freecam.D.player, CPacketEntityAction.Action.STOP_SPRINTING));
            Freecam.D.world.addEntityToWorld(-1337, (Entity)this.d);
        }
    }
    
    public static Minecraft getMinecraft2() {
        return Freecam.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Freecam.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Freecam.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Freecam.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Freecam.D;
    }
    
    public void b() {
        super.b();
        if (Freecam.D.world != null && Freecam.D.player != null) {
            final NetworkPlayerInfo playerInfo;
            if (Freecam.D.getConnection() != null && Freecam.D.getConnection().getPlayerInfo(Freecam.D.player.getUniqueID()) != null && Freecam.D.getConnection().getPlayerInfo(Freecam.D.player.getGameProfile().getId()) != null && (playerInfo = Freecam.D.getConnection().getPlayerInfo(Freecam.D.player.getGameProfile().getId())).getGameType() == GameType.SPECTATOR) {
                ((INetworkPlayerInfo)playerInfo).setGameType(GameType.SURVIVAL);
                Freecam.D.player.capabilities.isFlying = false;
            }
            if (this.d != null) {
                Freecam.D.player.setPositionAndRotation(this.d.posX, this.d.posY, this.d.posZ, this.d.rotationYaw, this.d.rotationPitch);
                Freecam.D.player.motionX = this.d.motionX;
                Freecam.D.player.motionY = this.d.motionY;
                Freecam.D.player.motionZ = this.d.motionZ;
                if (!Freecam.D.gameSettings.keyBindSneak.isKeyDown() && this.d.isSneaking()) {
                    Freecam.D.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Freecam.D.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (this.d.getRidingEntity() != null) {
                    Freecam.D.player.startRiding(this.d.getRidingEntity(), true);
                    this.d.dismountRidingEntity();
                }
                Freecam.D.world.removeEntityFromWorld(-1337);
            }
            Freecam.D.player.noClip = false;
        }
    }
    
    public static Minecraft getMinecraft7() {
        return Freecam.D;
    }
    
    public static Minecraft getMinecraft8() {
        return Freecam.D;
    }
    
    public static Minecraft getMinecraft9() {
        return Freecam.D;
    }
    
    public static Minecraft getMinecraft10() {
        return Freecam.D;
    }
    
    public static Minecraft getMinecraft11() {
        return Freecam.D;
    }
    
    public static Minecraft getMinecraft12() {
        return Freecam.D;
    }
    
    public static EntityOtherPlayerMP M(final Freecam freecam) {
        return freecam.d;
    }
    
    public static R M(final Freecam freecam) {
        return freecam.mode;
    }
    
    public static Minecraft getMinecraft13() {
        return Freecam.D;
    }
}
