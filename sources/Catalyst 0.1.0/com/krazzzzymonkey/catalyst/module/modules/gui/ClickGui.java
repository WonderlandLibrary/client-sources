package com.krazzzzymonkey.catalyst.module.modules.gui;

import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.client.gui.GuiScreen;
import com.krazzzzymonkey.catalyst.Main;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.krazzzzymonkey.catalyst.utils.visual.ColorUtils;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.value.Mode;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import com.krazzzzymonkey.catalyst.value.ModeValue;
import com.krazzzzymonkey.catalyst.value.NumberValue;
import com.krazzzzymonkey.catalyst.value.BooleanValue;
import com.krazzzzymonkey.catalyst.module.Modules;

public class ClickGui extends Modules
{
    public static BooleanValue rainbow;
    public static NumberValue blue;
    public static BooleanValue shadow;
    private static final String[] strings;
    private static final int[] integers;
    public static NumberValue red;
    public static NumberValue green;
    public ModeValue theme;
    public static boolean isLight;
    private static int color;
    public static NumberValue alpha;
    
    public ClickGui() {
        super("ClickGui", ModuleCategory.GUI);
        this.setKey(54);
        this.setShow((boolean)(0 != 0));
        final String theme = "Theme";
        final Mode[] mode = new Mode[2];
        mode[0] = new Mode("Dark", (boolean)(1 != 0));
        mode[1] = new Mode("Light", (boolean)(0 != 0));
        this.theme = new ModeValue(theme, mode);
        
        ClickGui.shadow = new BooleanValue("Shadow", (boolean)(1 != 0));
        ClickGui.rainbow = new BooleanValue("Rainbow", (boolean)(1 != 0));
        ClickGui.red = new NumberValue("Red", 170.0, 0.0, 255.0);
        ClickGui.green = new NumberValue("Green", 170.0, 0.0, 255.0);
        ClickGui.blue = new NumberValue("Blue", 170.0, 0.0, 255.0);
        ClickGui.alpha = new NumberValue("Alpha", 170.0, 0.0, 255.0);

        final Value[] value = new Value[7];
        value[0] = this.theme;
        value[1] = ClickGui.shadow;
        value[2] = ClickGui.rainbow;
        value[3] = ClickGui.red;
        value[4] = ClickGui.green;
        value[5] = ClickGui.blue;
        value[6] = ClickGui.alpha;
        this.addValue(value);
        
        setColor();
    }
    
    static {
        ClickGui.isLight = (0 != 0);
    }

    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent clientTickEvent) {
        
        setColor();
        
        ClickGui.isLight = this.theme.getMode("Light").isToggled();
        super.onClientTick(clientTickEvent);
    }
    
    public static int getColor() {
        int n;
        if ((boolean)ClickGui.rainbow.getValue())  {
            n = ColorUtils.rainbow().getRGB();
        }
        else {
            n = ClickGui.color;
        }
        return n;
    }
    
    @Override
    public void onEnable() {
        Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)Main.hackManager.getGui());
        super.onEnable();
    }
    
    
    @Override
    public void onRenderGameOverlay(final RenderGameOverlayEvent.Text overlayText) {
        if ((boolean)ClickGui.shadow.getValue()) {
            final ScaledResolution resolution = new ScaledResolution(Wrapper.INSTANCE.mc());
            RenderUtils.drawRect(0.0f, 0.0f, (float)resolution.getScaledWidth(), (float)resolution.getScaledHeight(), ColorUtils.color(0.0f, 0.0f, 0.0f, 0.5f));
        }
        super.onRenderGameOverlay(overlayText);
    }
    
    public static void setColor() {
        ClickGui.color = ColorUtils.color(ClickGui.red.getValue().intValue(), ClickGui.green.getValue().intValue(), ClickGui.blue.getValue().intValue(), ClickGui.alpha.getValue().intValue());
    }
    
}
