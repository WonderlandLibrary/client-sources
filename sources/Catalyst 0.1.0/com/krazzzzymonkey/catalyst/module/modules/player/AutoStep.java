package com.krazzzzymonkey.catalyst.module.modules.player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.value.Mode;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.value.ModeValue;
import com.krazzzzymonkey.catalyst.value.NumberValue;
import com.krazzzzymonkey.catalyst.module.Modules;

public class AutoStep extends Modules
{
    public NumberValue height;
    public ModeValue mode;
    public int ticks;
    
    @Override
    public void onEnable() {
        this.ticks = 0;
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        Wrapper.INSTANCE.player().stepHeight = 0.5f;
        super.onDisable();
    }
    
    public AutoStep() {
        super("AutoStep", ModuleCategory.PLAYER);
        this.ticks = 0;
        final String mode = "Mode";
        final Mode[] modes = new Mode[2];
        modes[0] = new Mode("Simple", true);
        modes[1] = new Mode("AAC", false);
        this.mode = new ModeValue(mode, modes);
        this.height = new NumberValue("Height", 0.5, 0.0, 10.0);
        final Value[] value = new Value[2];
        value[0] = this.mode;
        value[1] = this.height;
        this.addValue(value);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent tickEvent) {
        if (this.mode.getMode("AAC").isToggled()) {
            final EntityPlayerSP entityPlayer = Wrapper.INSTANCE.player();
            if (entityPlayer.collidedHorizontally) {
                switch (this.ticks) {
                    case 0: {
                        if (!(entityPlayer.onGround)) {
                            break;
                        }
                        entityPlayer.jump();
                        break;
                    }
                    case 7: {
                        entityPlayer.motionY = 0.0;
                        break;
                    }
                    case 8: {
                        if (!(entityPlayer.onGround)) {
                            entityPlayer.setPosition(entityPlayer.posX, entityPlayer.posY + 1.0, entityPlayer.posZ);
                            break;
                        }
                        break;
                    }
                }
                this.ticks += 1;
            }
            else {
                this.ticks = 0;
            }
        }
        else if (this.mode.getMode("Simple").isToggled()) {
            Wrapper.INSTANCE.player().stepHeight = this.height.getValue().floatValue();
        }
        super.onClientTick(tickEvent);
    }
    
