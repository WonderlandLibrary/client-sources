package com.krazzzzymonkey.catalyst.module.modules.player;

import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.WorldClient;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.value.NumberValue;
import com.krazzzzymonkey.catalyst.module.Modules;

public class Disconnect extends Modules
{
    private static final int[] lIlIlI;
    public NumberValue leaveHealth;
    private static final String[] lIlIII;
    

    @Override
    public void onClientTick(final TickEvent.ClientTickEvent tickEvent) {
        if ((fcmpg(Wrapper.INSTANCE.player().getHealth(), this.leaveHealth.getValue().floatValue())) <= 0) {
            final boolean serverRunning = Wrapper.INSTANCE.mc().isIntegratedServerRunning();
            Wrapper.INSTANCE.world().sendQuittingDisconnectingPacket();
            Wrapper.INSTANCE.mc().loadWorld((WorldClient)null);
            if (serverRunning) {
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new GuiMainMenu());
            }
            else {
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()));
            }
            this.setToggled(false);
        }
        super.onClientTick(tickEvent);
    }

    public Disconnect() {
        super("Disconnect", ModuleCategory.COMBAT);
        this.leaveHealth = new NumberValue("LeaveHealth", 4.0, 0.0, 20.0);
        final Value[] value = new Value[1];
        value[0] = this.leaveHealth;
        this.addValue(value);
    }
    
}
