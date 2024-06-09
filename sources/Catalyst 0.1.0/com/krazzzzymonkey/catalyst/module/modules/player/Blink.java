package com.krazzzzymonkey.catalyst.module.modules.player;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import net.minecraft.network.play.client.CPacketPlayer;
import com.krazzzzymonkey.catalyst.utils.system.Connection;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.krazzzzymonkey.catalyst.utils.Entity301;
import com.krazzzzymonkey.catalyst.module.Modules;

public class Blink extends Modules
{
    public Entity301 entity301;

    @Override
    public void onEnable() {
        if ((Wrapper.INSTANCE.player() != null) && (Wrapper.INSTANCE.world() != null)) {
            this.entity301 = new Entity301((World)Wrapper.INSTANCE.world(), Wrapper.INSTANCE.player().getGameProfile());
            this.entity301.setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ);
            this.entity301.inventory = Wrapper.INSTANCE.player().inventory;
            this.entity301.rotationPitch = Wrapper.INSTANCE.player().rotationPitch;
            this.entity301.rotationYaw = Wrapper.INSTANCE.player().rotationYaw;
            this.entity301.rotationYawHead = Wrapper.INSTANCE.player().rotationYawHead;
            Wrapper.INSTANCE.world().spawnEntity((Entity)this.entity301);
        }
        super.onEnable();
    }
    
    
    @Override
    public void onDisable() {
        if ((this.entity301 != null) && (Wrapper.INSTANCE.world() != null)) {
            Wrapper.INSTANCE.world().removeEntity((Entity)this.entity301);
            this.entity301 = null;
        }
        super.onDisable();
    }

    
    @Override
    public boolean onPacket(final Object obj, final Connection.Side connectionSide) {int n;
        if (!(connectionSide == Connection.Side.OUT) || (!((obj instanceof CPacketPlayer)) == 0 && ((obj instanceof CPacketPlayer.Position) != 0) && ((obj instanceof CPacketPlayer.Rotation) != 0) && ((obj instanceof CPacketPlayer.PositionRotation) != 0))) {
            n = 1;
        }
        else {
            n = 0;
        }
        return n != 0;
    }
    
    public Blink() {
        super("Blink", ModuleCategory.PLAYER);
        this.entity301 = null;
    }
    
}
