package com.krazzzzymonkey.catalyst.module.modules.combat;

import java.util.Arrays;
import net.minecraft.init.MobEffects;
import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.value.Mode;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.krazzzzymonkey.catalyst.utils.system.Connection;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.value.ModeValue;
import com.krazzzzymonkey.catalyst.utils.TimerUtils;
import com.krazzzzymonkey.catalyst.module.Modules;

public class Criticals extends Modules
{
    TimerUtils timer;
    public ModeValue mode;
    boolean cancelSomePackets;
        

    @Override
    public boolean onPacket(final Object obj, final Connection.Side connectionSide) {
        if ((Wrapper.INSTANCE.player().onGround) && (connectionSide == Connection.Side.OUT)) {
            if (obj instanceof CPacketUseEntity) {
                final CPacketUseEntity cpacketUseEntity = (CPacketUseEntity)obj;
                if ((cpacketUseEntity.getAction() == CPacketUseEntity.Action.ATTACK)) {
                    if (this.mode.getMode("Packet").isToggled()) {
                        if ((Wrapper.INSTANCE.player().collidedVertically) && (this.timer.isDelay(500L))) {
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.0627, Wrapper.INSTANCE.player().posZ, false));
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ, false));
                            final Entity entity = cpacketUseEntity.getEntityFromWorld((World)Wrapper.INSTANCE.world());
                            if (entity != null) {
                                Wrapper.INSTANCE.player().onCriticalHit(entity);
                            }
                            this.timer.setLastMS();
                            this.cancelSomePackets = true;
                        }
                    }
                    else if ((this.mode.getMode("Jump").isToggled()) && (this.canJump())) {
                        Wrapper.INSTANCE.player().jump();
                    }
                }
            }
            else if ((this.mode.getMode("Packet").isToggled()) && (obj instanceof CPacketPlayer0) && (this.cancelSomePackets)) {
                this.cancelSomePackets = false;
                return false;
            }
        }
        return true;
    }
    
    
    public Criticals() {
        super("Criticals", ModuleCategory.COMBAT);
        final String mode = "Mode";
        final Mode[] setting = new Mode[2];
        setting[0] = new Mode("Packet", true);
        setting[1] = new Mode("Jump", false);
        this.mode = new ModeValue(mode, setting);
        final Value[] value = new Value[1];
        value[0] = this.mode;
        this.addValue(value);
        this.timer = new TimerUtils();
    }
    
    boolean canJump() {
        if (Wrapper.INSTANCE.player().isOnLadder()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isInWater()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isInLava()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isSneaking()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isRiding()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isPotionActive(MobEffects.BLINDNESS)) {
            return false;
        }
        return true;
    }
    
}
