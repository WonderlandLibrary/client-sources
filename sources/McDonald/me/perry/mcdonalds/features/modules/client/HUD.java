// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.client;

import me.perry.mcdonalds.event.events.ClientEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import me.perry.mcdonalds.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import java.util.function.ToIntFunction;
import net.minecraft.init.Items;
import me.perry.mcdonalds.util.MathUtil;
import me.perry.mcdonalds.util.EntityUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import net.minecraft.potion.PotionEffect;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.perry.mcdonalds.McDonalds;
import me.perry.mcdonalds.util.Util;
import net.minecraft.client.gui.GuiChat;
import me.perry.mcdonalds.util.ColorUtil;
import me.perry.mcdonalds.event.events.Render2DEvent;
import java.util.HashMap;
import me.perry.mcdonalds.util.TextUtil;
import java.util.Map;
import me.perry.mcdonalds.util.Timer;
import me.perry.mcdonalds.features.setting.Setting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import me.perry.mcdonalds.features.modules.Module;

public class HUD extends Module
{
    private static final ResourceLocation box;
    private static final ItemStack totem;
    private static HUD INSTANCE;
    private final Setting<Boolean> grayNess;
    private final Setting<Boolean> renderingUp;
    private final Setting<Boolean> waterMark;
    private final Setting<Boolean> discordMark;
    private final Setting<Boolean> arrayList;
    private final Setting<Boolean> coords;
    private final Setting<Boolean> direction;
    private final Setting<Boolean> armor;
    private final Setting<Boolean> totems;
    private final Setting<Boolean> greeter;
    private final Setting<Boolean> speed;
    private final Setting<Boolean> potions;
    private final Setting<Boolean> ping;
    private final Setting<Boolean> tps;
    private final Setting<Boolean> fps;
    private final Setting<Boolean> lag;
    private final Timer timer;
    private final Map<String, Integer> players;
    public Setting<String> command;
    public Setting<TextUtil.Color> bracketColor;
    public Setting<TextUtil.Color> commandColor;
    public Setting<String> commandBracket;
    public Setting<String> commandBracket2;
    public Setting<Boolean> notifyToggles;
    public Setting<Boolean> magenDavid;
    public Setting<Integer> animationHorizontalTime;
    public Setting<Integer> animationVerticalTime;
    public Setting<RenderingMode> renderingMode;
    public Setting<Integer> waterMarkY;
    public Setting<Integer> discordMarkY;
    public Setting<Boolean> time;
    public Setting<Integer> lagTime;
    private int color;
    private boolean shouldIncrement;
    private int hitMarkerTimer;
    
    public HUD() {
        super("HUD", "HUD Elements rendered on your screen", Category.CLIENT, true, false, false);
        this.grayNess = (Setting<Boolean>)this.register(new Setting("Gray", (T)true));
        this.renderingUp = (Setting<Boolean>)this.register(new Setting("RenderingUp", (T)false, "Orientation of the HUD-Elements."));
        this.waterMark = (Setting<Boolean>)this.register(new Setting("Watermark", (T)false, "displays watermark"));
        this.discordMark = (Setting<Boolean>)this.register(new Setting("Discordmark", (T)false, "displays watermark"));
        this.arrayList = (Setting<Boolean>)this.register(new Setting("ActiveModules", (T)false, "Lists the active modules."));
        this.coords = (Setting<Boolean>)this.register(new Setting("Coords", (T)false, "Your current coordinates"));
        this.direction = (Setting<Boolean>)this.register(new Setting("Direction", (T)false, "The Direction you are facing."));
        this.armor = (Setting<Boolean>)this.register(new Setting("Armor", (T)false, "ArmorHUD"));
        this.totems = (Setting<Boolean>)this.register(new Setting("Totems", (T)false, "TotemHUD"));
        this.greeter = (Setting<Boolean>)this.register(new Setting("Welcomer", (T)false, "The time"));
        this.speed = (Setting<Boolean>)this.register(new Setting("Speed", (T)false, "Your Speed"));
        this.potions = (Setting<Boolean>)this.register(new Setting("Potions", (T)false, "Your Speed"));
        this.ping = (Setting<Boolean>)this.register(new Setting("Ping", (T)false, "Your response time to the server."));
        this.tps = (Setting<Boolean>)this.register(new Setting("TPS", (T)false, "Ticks per second of the server."));
        this.fps = (Setting<Boolean>)this.register(new Setting("FPS", (T)false, "Your frames per second."));
        this.lag = (Setting<Boolean>)this.register(new Setting("LagNotifier", (T)false, "The time"));
        this.timer = new Timer();
        this.players = new HashMap<String, Integer>();
        this.command = (Setting<String>)this.register(new Setting("Command", (T)"McDonalds Client"));
        this.bracketColor = (Setting<TextUtil.Color>)this.register(new Setting("BracketColor", (T)TextUtil.Color.GRAY));
        this.commandColor = (Setting<TextUtil.Color>)this.register(new Setting("NameColor", (T)TextUtil.Color.YELLOW));
        this.commandBracket = (Setting<String>)this.register(new Setting("Bracket", (T)"["));
        this.commandBracket2 = (Setting<String>)this.register(new Setting("Bracket2", (T)"]"));
        this.notifyToggles = (Setting<Boolean>)this.register(new Setting("ChatNotify", (T)false, "notifys in chat"));
        this.magenDavid = (Setting<Boolean>)this.register(new Setting("McDonalds", (T)true, "draws McDonalds happy meal"));
        this.animationHorizontalTime = (Setting<Integer>)this.register(new Setting("AnimationHTime", (T)500, (T)1, (T)1000, v -> this.arrayList.getValue()));
        this.animationVerticalTime = (Setting<Integer>)this.register(new Setting("AnimationVTime", (T)50, (T)1, (T)500, v -> this.arrayList.getValue()));
        this.renderingMode = (Setting<RenderingMode>)this.register(new Setting("Ordering", (T)RenderingMode.ABC));
        this.waterMarkY = (Setting<Integer>)this.register(new Setting("WatermarkPosY", (T)0, (T)0, (T)20, v -> this.waterMark.getValue()));
        this.discordMarkY = (Setting<Integer>)this.register(new Setting("DiscordPosY", (T)9, (T)0, (T)20, v -> this.waterMark.getValue()));
        this.time = (Setting<Boolean>)this.register(new Setting("Time", (T)false, "The time"));
        this.lagTime = (Setting<Integer>)this.register(new Setting("LagTime", (T)1000, (T)0, (T)2000));
        this.setInstance();
    }
    
    public static HUD getInstance() {
        if (HUD.INSTANCE == null) {
            HUD.INSTANCE = new HUD();
        }
        return HUD.INSTANCE;
    }
    
    private void setInstance() {
        HUD.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (this.shouldIncrement) {
            ++this.hitMarkerTimer;
        }
        if (this.hitMarkerTimer == 10) {
            this.hitMarkerTimer = 0;
            this.shouldIncrement = false;
        }
    }
    
    @Override
    public void onRender2D(final Render2DEvent event) {
        if (fullNullCheck()) {
            return;
        }
        final int width = this.renderer.scaledWidth;
        final int height = this.renderer.scaledHeight;
        this.color = ColorUtil.toRGBA(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue());
        if (this.waterMark.getValue()) {
            final String string = this.command.getPlannedValue() + " v1.1.7";
            if (ClickGui.getInstance().rainbow.getValue()) {
                if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    this.renderer.drawString(string, 2.0f, this.waterMarkY.getValue(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                }
                else {
                    final int[] arrayOfInt = { 1 };
                    final char[] stringToCharArray = string.toCharArray();
                    float f = 0.0f;
                    for (final char c : stringToCharArray) {
                        this.renderer.drawString(String.valueOf(c), 2.0f + f, this.waterMarkY.getValue(), ColorUtil.rainbow(arrayOfInt[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                        f += this.renderer.getStringWidth(String.valueOf(c));
                        ++arrayOfInt[0];
                    }
                }
            }
            else {
                this.renderer.drawString(string, 2.0f, this.waterMarkY.getValue(), this.color, true);
            }
        }
        final int[] counter1 = { 1 };
        int j = (Util.mc.currentScreen instanceof GuiChat && !this.renderingUp.getValue()) ? 14 : 0;
        if (this.arrayList.getValue()) {
            if (this.renderingUp.getValue()) {
                if (this.renderingMode.getValue() == RenderingMode.ABC) {
                    for (int k = 0; k < McDonalds.moduleManager.sortedModulesABC.size(); ++k) {
                        final String str = McDonalds.moduleManager.sortedModulesABC.get(k);
                        this.renderer.drawString(str, (float)(width - 2 - this.renderer.getStringWidth(str)), (float)(2 + j * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                        ++j;
                        ++counter1[0];
                    }
                }
                else {
                    for (int k = 0; k < McDonalds.moduleManager.sortedModules.size(); ++k) {
                        final Module module = McDonalds.moduleManager.sortedModules.get(k);
                        final String str2 = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                        this.renderer.drawString(str2, (float)(width - 2 - this.renderer.getStringWidth(str2)), (float)(2 + j * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                        ++j;
                        ++counter1[0];
                    }
                }
            }
            else if (this.renderingMode.getValue() == RenderingMode.ABC) {
                for (int k = 0; k < McDonalds.moduleManager.sortedModulesABC.size(); ++k) {
                    final String str = McDonalds.moduleManager.sortedModulesABC.get(k);
                    j += 10;
                    this.renderer.drawString(str, (float)(width - 2 - this.renderer.getStringWidth(str)), (float)(height - j), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
            }
            else {
                for (int k = 0; k < McDonalds.moduleManager.sortedModules.size(); ++k) {
                    final Module module = McDonalds.moduleManager.sortedModules.get(k);
                    final String str2 = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                    j += 10;
                    this.renderer.drawString(str2, (float)(width - 2 - this.renderer.getStringWidth(str2)), (float)(height - j), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
            }
        }
        final String grayString = this.grayNess.getValue() ? String.valueOf(ChatFormatting.GRAY) : "";
        int i = (Util.mc.currentScreen instanceof GuiChat && this.renderingUp.getValue()) ? 13 : (this.renderingUp.getValue() ? -2 : 0);
        if (this.renderingUp.getValue()) {
            if (this.potions.getValue()) {
                final List<PotionEffect> effects = new ArrayList<PotionEffect>(Minecraft.getMinecraft().player.getActivePotionEffects());
                for (final PotionEffect potionEffect : effects) {
                    final String str3 = McDonalds.potionManager.getColoredPotionString(potionEffect);
                    i += 10;
                    this.renderer.drawString(str3, (float)(width - this.renderer.getStringWidth(str3) - 2), (float)(height - 2 - i), potionEffect.getPotion().getLiquidColor(), true);
                }
            }
            if (this.speed.getValue()) {
                final String str2 = grayString + "Speed " + ChatFormatting.WHITE + McDonalds.speedManager.getSpeedKpH() + " km/h";
                i += 10;
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            if (this.time.getValue()) {
                final String str2 = grayString + "Time " + ChatFormatting.WHITE + new SimpleDateFormat("h:mm a").format(new Date());
                i += 10;
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            if (this.tps.getValue()) {
                final String str2 = grayString + "TPS " + ChatFormatting.WHITE + McDonalds.serverManager.getTPS();
                i += 10;
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            final String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
            final String str4 = grayString + "Ping " + ChatFormatting.WHITE + McDonalds.serverManager.getPing();
            if (this.renderer.getStringWidth(str4) > this.renderer.getStringWidth(fpsText)) {
                if (this.ping.getValue()) {
                    i += 10;
                    this.renderer.drawString(str4, (float)(width - this.renderer.getStringWidth(str4) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
                if (this.fps.getValue()) {
                    i += 10;
                    this.renderer.drawString(fpsText, (float)(width - this.renderer.getStringWidth(fpsText) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
            }
            else {
                if (this.fps.getValue()) {
                    i += 10;
                    this.renderer.drawString(fpsText, (float)(width - this.renderer.getStringWidth(fpsText) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
                if (this.ping.getValue()) {
                    i += 10;
                    this.renderer.drawString(str4, (float)(width - this.renderer.getStringWidth(str4) - 2), (float)(height - 2 - i), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
            }
        }
        else {
            if (this.potions.getValue()) {
                final List<PotionEffect> effects = new ArrayList<PotionEffect>(Minecraft.getMinecraft().player.getActivePotionEffects());
                for (final PotionEffect potionEffect : effects) {
                    final String str3 = McDonalds.potionManager.getColoredPotionString(potionEffect);
                    this.renderer.drawString(str3, (float)(width - this.renderer.getStringWidth(str3) - 2), (float)(2 + i++ * 10), potionEffect.getPotion().getLiquidColor(), true);
                }
            }
            if (this.speed.getValue()) {
                final String str2 = grayString + "Speed " + ChatFormatting.WHITE + McDonalds.speedManager.getSpeedKpH() + " km/h";
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            if (this.time.getValue()) {
                final String str2 = grayString + "Time " + ChatFormatting.WHITE + new SimpleDateFormat("h:mm a").format(new Date());
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            if (this.tps.getValue()) {
                final String str2 = grayString + "TPS " + ChatFormatting.WHITE + McDonalds.serverManager.getTPS();
                this.renderer.drawString(str2, (float)(width - this.renderer.getStringWidth(str2) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                ++counter1[0];
            }
            final String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
            final String str4 = grayString + "Ping " + ChatFormatting.WHITE + McDonalds.serverManager.getPing();
            if (this.renderer.getStringWidth(str4) > this.renderer.getStringWidth(fpsText)) {
                if (this.ping.getValue()) {
                    this.renderer.drawString(str4, (float)(width - this.renderer.getStringWidth(str4) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
                if (this.fps.getValue()) {
                    this.renderer.drawString(fpsText, (float)(width - this.renderer.getStringWidth(fpsText) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
            }
            else {
                if (this.fps.getValue()) {
                    this.renderer.drawString(fpsText, (float)(width - this.renderer.getStringWidth(fpsText) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
                if (this.ping.getValue()) {
                    this.renderer.drawString(str4, (float)(width - this.renderer.getStringWidth(str4) - 2), (float)(2 + i++ * 10), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ((ClickGui.getInstance().rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB()) : this.color, true);
                    ++counter1[0];
                }
            }
        }
        final boolean inHell = Util.mc.world.getBiome(Util.mc.player.getPosition()).getBiomeName().equals("Hell");
        final int posX = (int)Util.mc.player.posX;
        final int posY = (int)Util.mc.player.posY;
        final int posZ = (int)Util.mc.player.posZ;
        final float nether = inHell ? 8.0f : 0.125f;
        final int hposX = (int)(Util.mc.player.posX * nether);
        final int hposZ = (int)(Util.mc.player.posZ * nether);
        i = ((Util.mc.currentScreen instanceof GuiChat) ? 14 : 0);
        final String coordinates = ChatFormatting.WHITE + "XYZ " + ChatFormatting.RESET + (inHell ? (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]" + ChatFormatting.RESET) : (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]"));
        final String direction = this.direction.getValue() ? McDonalds.rotationManager.getDirection4D(false) : "";
        final String coords = this.coords.getValue() ? coordinates : "";
        i += 10;
        if (ClickGui.getInstance().rainbow.getValue()) {
            final String rainbowCoords = this.coords.getValue() ? ("XYZ " + (inHell ? (posX + ", " + posY + ", " + posZ + " [" + hposX + ", " + hposZ + "]") : (posX + ", " + posY + ", " + posZ + " [" + hposX + ", " + hposZ + "]"))) : "";
            if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                this.renderer.drawString(direction, 2.0f, (float)(height - i - 11), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                this.renderer.drawString(rainbowCoords, 2.0f, (float)(height - i), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
            }
            else {
                final int[] counter2 = { 1 };
                final char[] stringToCharArray2 = direction.toCharArray();
                float s = 0.0f;
                for (final char c2 : stringToCharArray2) {
                    this.renderer.drawString(String.valueOf(c2), 2.0f + s, (float)(height - i - 11), ColorUtil.rainbow(counter2[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                    s += this.renderer.getStringWidth(String.valueOf(c2));
                    ++counter2[0];
                }
                final int[] counter3 = { 1 };
                final char[] stringToCharArray3 = rainbowCoords.toCharArray();
                float u = 0.0f;
                for (final char c3 : stringToCharArray3) {
                    this.renderer.drawString(String.valueOf(c3), 2.0f + u, (float)(height - i), ColorUtil.rainbow(counter3[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                    u += this.renderer.getStringWidth(String.valueOf(c3));
                    ++counter3[0];
                }
            }
        }
        else {
            this.renderer.drawString(direction, 2.0f, (float)(height - i - 11), this.color, true);
            this.renderer.drawString(coords, 2.0f, (float)(height - i), this.color, true);
        }
        if (this.armor.getValue()) {
            this.renderArmorHUD(true);
        }
        if (this.totems.getValue()) {
            this.renderTotemHUD();
        }
        if (this.greeter.getValue()) {
            this.renderGreeter();
        }
        if (this.lag.getValue()) {
            this.renderLag();
        }
        if (fullNullCheck()) {
            return;
        }
        this.color = ColorUtil.toRGBA(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue());
        if (this.discordMark.getValue()) {
            final String string2 = "https://discord.gg/snDa88Vjfz";
            if (ClickGui.getInstance().rainbow.getValue()) {
                if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    this.renderer.drawString(string2, 2.0f, this.discordMarkY.getValue(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                }
                else {
                    final int[] arrayOfInt2 = { 1 };
                    final char[] stringToCharArray2 = string2.toCharArray();
                    float f2 = 0.0f;
                    for (final char c2 : stringToCharArray2) {
                        this.renderer.drawString(String.valueOf(c2), 2.0f + f2, this.discordMarkY.getValue(), ColorUtil.rainbow(arrayOfInt2[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                        f2 += this.renderer.getStringWidth(String.valueOf(c2));
                        ++arrayOfInt2[0];
                    }
                }
            }
            else {
                this.renderer.drawString(string2, 2.0f, this.discordMarkY.getValue(), this.color, true);
            }
        }
    }
    
    public Map<String, Integer> getTextRadarPlayers() {
        return EntityUtil.getTextRadarPlayers();
    }
    
    public void renderGreeter() {
        final int width = this.renderer.scaledWidth;
        String text = "";
        if (this.greeter.getValue()) {
            text = text + MathUtil.getTimeOfDay() + Util.mc.player.getDisplayNameString();
        }
        if (ClickGui.getInstance().rainbow.getValue()) {
            if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                this.renderer.drawString(text, width / 2.0f - this.renderer.getStringWidth(text) / 2.0f + 2.0f, 2.0f, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
            }
            else {
                final int[] counter1 = { 1 };
                final char[] stringToCharArray = text.toCharArray();
                float i = 0.0f;
                for (final char c : stringToCharArray) {
                    this.renderer.drawString(String.valueOf(c), width / 2.0f - this.renderer.getStringWidth(text) / 2.0f + 2.0f + i, 2.0f, ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
                    i += this.renderer.getStringWidth(String.valueOf(c));
                    ++counter1[0];
                }
            }
        }
        else {
            this.renderer.drawString(text, width / 2.0f - this.renderer.getStringWidth(text) / 2.0f + 2.0f, 2.0f, this.color, true);
        }
    }
    
    public void renderLag() {
        final int width = this.renderer.scaledWidth;
        if (McDonalds.serverManager.isServerNotResponding()) {
            final String text = ChatFormatting.RED + "Server not responding " + MathUtil.round(McDonalds.serverManager.serverRespondingTime() / 1000.0f, 1) + "s.";
            this.renderer.drawString(text, width / 2.0f - this.renderer.getStringWidth(text) / 2.0f + 2.0f, 20.0f, this.color, true);
        }
    }
    
    public void renderTotemHUD() {
        final int width = this.renderer.scaledWidth;
        final int height = this.renderer.scaledHeight;
        int totems = Util.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::func_190916_E).sum();
        if (Util.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            totems += Util.mc.player.getHeldItemOffhand().getCount();
        }
        if (totems > 0) {
            GlStateManager.enableTexture2D();
            final int i = width / 2;
            final int iteration = 0;
            final int y = height - 55 - ((Util.mc.player.isInWater() && Util.mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
            final int x = i - 189 + 180 + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0f;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(HUD.totem, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(Util.mc.fontRenderer, HUD.totem, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            this.renderer.drawStringWithShadow(totems + "", (float)(x + 19 - 2 - this.renderer.getStringWidth(totems + "")), (float)(y + 9), 16777215);
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }
    
    public void renderArmorHUD(final boolean percent) {
        final int width = this.renderer.scaledWidth;
        final int height = this.renderer.scaledHeight;
        GlStateManager.enableTexture2D();
        final int i = width / 2;
        int iteration = 0;
        final int y = height - 55 - ((Util.mc.player.isInWater() && Util.mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
        for (final ItemStack is : Util.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) {
                continue;
            }
            final int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0f;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(Util.mc.fontRenderer, is, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
            this.renderer.drawStringWithShadow(s, (float)(x + 19 - 2 - this.renderer.getStringWidth(s)), (float)(y + 9), 16777215);
            if (!percent) {
                continue;
            }
            final float green = (float)((is.getMaxDamage() - is.getItemDamage()) / is.getMaxDamage());
            final float red = 1.0f - green;
            final int dmg = 100 - (int)(red * 100.0f);
            this.renderer.drawStringWithShadow(dmg + "", (float)(x + 8 - this.renderer.getStringWidth(dmg + "") / 2), (float)(y - 11), ColorUtil.toRGBA((int)(red * 255.0f), (int)(green * 255.0f), 0));
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final AttackEntityEvent event) {
        this.shouldIncrement = true;
    }
    
    @Override
    public void onLoad() {
        McDonalds.commandManager.setClientMessage(this.getCommandMessage());
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        if (event.getStage() == 2 && this.equals(event.getSetting().getFeature())) {
            McDonalds.commandManager.setClientMessage(this.getCommandMessage());
        }
    }
    
    public String getCommandMessage() {
        return TextUtil.coloredString(this.commandBracket.getPlannedValue(), this.bracketColor.getPlannedValue()) + TextUtil.coloredString(this.command.getPlannedValue(), this.commandColor.getPlannedValue()) + TextUtil.coloredString(this.commandBracket2.getPlannedValue(), this.bracketColor.getPlannedValue());
    }
    
    public void drawTextRadar(final int yOffset) {
        if (!this.players.isEmpty()) {
            int y = this.renderer.getFontHeight() + 7 + yOffset;
            for (final Map.Entry<String, Integer> player : this.players.entrySet()) {
                final String text = player.getKey() + " ";
                final int textheight = this.renderer.getFontHeight() + 1;
                this.renderer.drawString(text, 2.0f, (float)y, this.color, true);
                y += textheight;
            }
        }
    }
    
    static {
        box = new ResourceLocation("textures/gui/container/shulker_box.png");
        totem = new ItemStack(Items.TOTEM_OF_UNDYING);
        HUD.INSTANCE = new HUD();
    }
    
    public enum RenderingMode
    {
        Length, 
        ABC;
    }
}
