package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventRender2D;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Manager.ModuleManager;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.UI.Font.CFontRenderer;
import Ascii4UwUWareClient.UI.Font.FontLoaders;
import Ascii4UwUWareClient.Util.MainMenuUtil;
import Ascii4UwUWareClient.Util.Render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.awt.*;
import java.util.ArrayList;

public class HUD extends Module {

    public Mode<Enum> fontMode = new Mode ( "Font Mode", "Font Mode", FontMode.values(), FontMode.SF_UI );

    public Option<Boolean> backGround = new Option ("Back Ground", "Back Ground", true);
    public Numbers<Double> backGroundAlpha = new Numbers <Double> ("Bg Opacity", "Bg Opacity", 51D, 1D, 255D, 1D);

    public static CFontRenderer font = null;

    public HUD() {
        super("HUD", new String[]{"HUD"}, ModuleType.Render);
        this.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
        this.setEnabled(true);
        //this.setRemoved(true);
        addValues(fontMode, backGround, backGroundAlpha);
    }

    @EventHandler
    public void onHUD(EventRender2D event) {
        //Font And sorting

        setSuffix(fontMode.getModeAsString());

        if (this.mc.gameSettings.showDebugInfo) {
            return;
        }

        if (mc.isSingleplayer()){
            MainMenuUtil.drawString("Sorry But SinglePlayer is currently bugged.", GuiScreen.width/2, GuiScreen.height/2, astofloc(Integer.MAX_VALUE));
        }

        String name;

        switch (fontMode.getModeAsString()){
            case "SF_UI":
                font = FontLoaders.SfUiArray;
                break;
            case "Google":
                font = FontLoaders.GoogleSans18;
                break;
        }

        ArrayList<Module> sorted = new ArrayList<Module>();
        for (Module m : ModuleManager.getModules()) {
            if (!m.isEnabled() || m.wasRemoved())
                continue;
            sorted.add(m);
        }
        if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")){
            sorted.sort((o1, o2) -> mc.fontRendererObj.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix())) - mc.fontRendererObj.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s %s", o1.getName(), o1.getSuffix())));
        }else {
            CFontRenderer finalFont = font;
            sorted.sort((o1, o2) -> finalFont.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix())) - finalFont.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s %s", o1.getName(), o1.getSuffix())));
        }

        //Arraylist
        int y = 1;
        int count = 0;
        for (Module m : sorted) {
            name = m.getSuffix().isEmpty() ? m.getName() : String.format("%s %s", m.getName(), m.getSuffix());
            float x = 0;
            if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")){
                x = RenderUtil.width() - mc.fontRendererObj.getStringWidth(name);
            }else {
                x = RenderUtil.width() - font.getStringWidth(name);
            }
            if(backGround.getValue()) {
                if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")){
                    Gui.drawRect2(x - 2, y + 3, mc.fontRendererObj.getStringWidth(name), mc.fontRendererObj.FONT_HEIGHT + 2, new Color(0, 0, 0, backGroundAlpha.getValue().intValue()).hashCode());
                }else {
                    Gui.drawRect2(x - 2, y + 3, font.getStringWidth(name), font.getHeight() + 2, new Color(0, 0, 0, backGroundAlpha.getValue().intValue()).hashCode());
                }
            }
            if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")) {
                mc.fontRendererObj.drawStringWithShadow(name, x - 2, y + 4, astofloc(count * 500));
            }else {
                font.drawStringWithShadowNew(name, x - 2, y + 4, astofloc(count * 500));
            }
            y += 10;
            count++;
        }

        String firstLetter = Client.instance.name.substring(0, 1);
        if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")) {
            mc.fontRendererObj.drawStringWithShadow(firstLetter, 2.0F, 2.0F, astofloc(100));
            mc.fontRendererObj.drawStringWithShadow(Client.instance.name.substring(1), (mc.fontRendererObj.getStringWidth(firstLetter) + 2), 2.0F, -1);
        }else {
            assert font != null;
            font.drawStringWithShadow(firstLetter, 2.0F, 2.0F, astofloc(100));
            font.drawStringWithShadow(Client.instance.name.substring(1), (mc.fontRendererObj.getStringWidth(firstLetter) + 2), 2.0F, -1);
        }

        this.drawPotionStatus(new ScaledResolution(this.mc));

    }

    public static int astofloc(int delay) {
        float speed = 3200.0F;
        float hue = (float)(System.currentTimeMillis() % (int)speed) + (delay / 2);
        while (hue > speed)
            hue -= speed;
        hue /= speed;
        if (hue > 0.5D)
            hue = 0.5F - hue - 0.5F;
        hue += 0.5F;
        return Color.HSBtoRGB(hue, 0.5F, 1.0F);
    }

    private void drawPotionStatus(ScaledResolution sr) {
        int y = 0;
        for (PotionEffect effect : Minecraft.thePlayer.getActivePotionEffects()) {
            int ychat;
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String PType = I18n.format(potion.getName() );
            switch (effect.getAmplifier()) {
                case 1: {
                    PType = PType + " II";
                    break;
                }
                case 2: {
                    PType = PType + " III";
                    break;
                }
                case 3: {
                    PType = PType + " IV";
                    break;
                }
            }
            if (effect.getDuration() < 600 && effect.getDuration() > 300) {
                PType = PType + "\u00a77:\u00a76 " + Potion.getDurationString(effect);
            } else if (effect.getDuration() < 300) {
                PType = PType + "\u00a77:\u00a7c " + Potion.getDurationString(effect);
            } else if (effect.getDuration() > 600) {
                PType = PType + "\u00a77:\u00a77 " + Potion.getDurationString(effect);
            }
            int n = ychat = this.mc.ingameGUI.getChatGUI().getChatOpen() ? 5 : -10;
            this.mc.fontRendererObj.drawStringWithShadow(PType,
                    sr.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(PType) - 2,
                    sr.getScaledHeight() - this.mc.fontRendererObj.FONT_HEIGHT + y - 12 - ychat,
                    potion.getLiquidColor());
            y -= 10;
        }

    }

    public enum FontMode{
        SF_UI, Google, Minecraft
    }

}
