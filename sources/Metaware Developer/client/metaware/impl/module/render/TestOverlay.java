package client.metaware.impl.module.render;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.Priorities;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.api.gui.notis.NotificationRenderer;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.api.shader.implementations.BlurShader;
import client.metaware.api.shader.implementations.LoginShader;
import client.metaware.impl.event.impl.render.Render2DEvent;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.render.Translate;
import client.metaware.impl.utils.util.Stencil;
import client.metaware.impl.utils.util.StencilUtil;
import client.metaware.impl.utils.util.other.PlayerUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

@ModuleInfo(renderName = "HUD", name = "HUD", category = Category.VISUALS)
public class TestOverlay extends Module {

    private float hue = 1.0F;


    private final BlurShader blurShader = new BlurShader(25);
    private final EnumProperty<ColorMode> arrayListColor = new EnumProperty<>("Module List Color", ColorMode.Rainbow);
    private final EnumProperty<Watermarks> watermarks = new EnumProperty<Watermarks>("Watermark Mode", Watermarks.Simple);

    private final EnumProperty<FontMode> fontMode = new EnumProperty<>("Font Mode", FontMode.Normal);

    private final Property<Boolean> pulsing = new Property<Boolean>("Pulsing", true);
    private final Property<Boolean> background = new Property<Boolean>("Background", false);
    private final Property<Boolean> radar = new Property<Boolean>("Radar", false);
    private final Property<Boolean> arrayList = new Property<Boolean>("Module List", true);
    private final Property<Boolean> info = new Property<Boolean>("Info", true);
    private final Property<Boolean> armorStatus = new Property<Boolean>("Armor Status", true);
    private final Property<Boolean> potionStatus = new Property<Boolean>("Potion Status", true);
    private final Property<Boolean> toggleSounds = new Property<Boolean>("Toggle Sounds", true);
    private final Property<Boolean> watermark = new Property<Boolean>("Watermark", true);
    private final Property<Boolean> sideLine = new Property<Boolean>("Bar", true);

    private final DoubleProperty rainbowSaturation = new DoubleProperty("Rainbow Saturation", 0.6f, 0.1f, 1.0f, 0.1f);

    private final DoubleProperty modListBackgroundAlpha = new DoubleProperty("BG Alpha", 0.2f, 0.0f, 1.0f, 0.05f);;

    private final DoubleProperty X = new DoubleProperty("Radar X", 1, 1, 1920, 5);
    private final DoubleProperty Y = new DoubleProperty("Radar Y", 15, 1, 1920, 2);
    private final DoubleProperty size = new DoubleProperty("Radar Size", 60, 50, 130, 5);

    private final Property<Integer> colorValue = new Property<Integer>("Custom Color", new Color(255, 255, 255).getRGB());


    private float xOffset;
    private float yOffset;
    private boolean dragging;

    @Override
    public void init() {
        super.init();

        toggle();
    }


    private enum Watermarks {
        Simple, Skeet, Whiz;
    }

    private enum ColorMode {
        Custom, Rainbow, Felix, Astolfo;
    }

    private enum FontMode {
        Normal, Tahoma, Vanilla;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler(Priorities.VERY_HIGH)
    private Listener<Render2DEvent> eventListener = event -> {
        if(mc.thePlayer != null && mc.theWorld != null) {
            NotificationRenderer.draw(event.getScaledResolution());
        }
        if (arrayList.getValue()) {
            draw(event.getScaledResolution());
        }
        if (armorStatus.getValue()) {
            drawArmorStatus(event.getScaledResolution());
        }
        if (potionStatus.getValue()) {
            drawPotionStatus(event.getScaledResolution());
        }
        if (radar.getValue()) {
            drawRadar(event.getScaledResolution());
        }


       // RenderUtil.drawLoadingCircle(mc.thePlayer, 46, 29);

        //RenderUtil.drawRoundedRect19(30, 30, 20, 20, 12, -1);


        boolean tahoma = fontMode.getValue().equals(FontMode.Tahoma);
        EntityPlayerSP player = mc.thePlayer;
        double xDist = player.posX - player.lastTickPosX;
        double zDist = player.posZ - player.lastTickPosZ;
        float d = (float) StrictMath.sqrt(xDist * xDist + zDist * zDist);
        final double xz = (Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * mc.timer.timerSpeed) * 20;
        final DecimalFormat bpsFormat = new DecimalFormat("#.#");
        final String bps = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.WHITE + bpsFormat.format(xz) + " BPS" + EnumChatFormatting.GRAY + "]";
        final String fps = info.getValue() ? ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Minecraft.getDebugFPS() + " FPS" + ChatFormatting.GRAY + "]" : "";
        final CustomFontRenderer font = Metaware.INSTANCE.getFontManager().currentFont().size(20);
        final CustomFontRenderer fr = Metaware.INSTANCE.getFontManager().currentFont().size(20);
        final CustomFontRenderer sfont = Metaware.INSTANCE.getFontManager().currentFont().size(18);
        final CustomFontRenderer bigfont = Metaware.INSTANCE.getFontManager().currentFont().size(40);
        final String text = ChatFormatting.GRAY + "X" + ChatFormatting.WHITE + ": " + MathHelper.floor_double(mc.thePlayer.posX) + " " + ChatFormatting.GRAY + "Y" + ChatFormatting.WHITE + ": " + MathHelper.floor_double(mc.thePlayer.posY) + " " + ChatFormatting.GRAY + "Z" + ChatFormatting.WHITE + ": " + MathHelper.floor_double(mc.thePlayer.posZ);
        final int ychat = mc.ingameGUI.getChatGUI().getChatOpen() ? 25 : 10;
        int color = 0;
        switch (arrayListColor.getValue()) {
            case Custom:
                color = new Color(colorValue.getValue()).getRGB();
                break;
            case Rainbow:
                color = RenderUtil.getRainbowFelix(6000, (int) (30), rainbowSaturation.getValue().floatValue());
                break;
            case Felix:
                color = RenderUtil.getGradientOffset(new Color(64, 85, 150), new Color(130, 191, 226), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + (1 / (50))).getRGB();
                break;
            case Astolfo:
                color = RenderUtil.getGradientOffset(new Color(0, 255, 255), new Color(255,105,180), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + (1 / (50))).getRGB();
                break;
            default:
                break;
        }
        boolean vanilla = fontMode.getValue().equals(FontMode.Vanilla);
        if (watermark.getValue()) {
            if (vanilla) {
               // mc.fontRendererObj.drawStringWithShadow("F" + ChatFormatting.WHITE + "elix " + fps + bps, 1, 1, color);
            }
        switch (watermarks.getValue()) {
            case Whiz: {
                final String server2 = mc.isSingleplayer() ? "Singleplayer" : mc.getCurrentServerData().serverIP.toLowerCase();
                //final String user = Client.INSTANCE.user;
                final String uid = "UID: " + Metaware.INSTANCE.getUID();
                final StringBuilder append2 = new StringBuilder("Metaware").append(" | ");
                final String print = append2.append(Minecraft.getDebugFPS()).append(" FPS | ").append(Metaware.INSTANCE.getUser()).append(" | ").append(server2).append(" | BPS: ").append(bpsFormat.format(xz)).toString();
                final float width2 = (float) (font.getWidth(print) + 8.5f);
                final int height = 20;
                final int posX2 = 2;
                final int posY1 = 2;

                Gui.drawRect(posX2, posY1, width2, height, new Color(64, 64, 64).getRGB());
                Gui.drawRect(posX2 + 3, posY1 + 3, width2 - 3, height - 3, new Color(30, 30, 30).getRGB());
                RenderUtil.drawGradientSideways(posX2 + 3.2f, posY1 + 3, posX2 + 4 + width2 / 3.0f, posY1 + 5, new Color(81, 149, 219, 255).getRGB(), new Color(180, 49, 218, 255).getRGB());
                RenderUtil.drawGradientSideways(posX2 + 3.2f + width2 / 3.0f, posY1 + 3, posX2 + 5 + width2 / 3.0f * 2.0f, posY1 + 5, new Color(180, 49, 218, 255).getRGB(), new Color(236, 93, 128, 255).getRGB());
                RenderUtil.drawGradientSideways(posX2 + 3.2f + width2 / 3.0f * 2.0f, posY1 + 3, width2, posY1 + 5, new Color(236, 93, 128, 255).getRGB(), new Color(167, 171, 90, 255).getRGB());
                font.drawString(print, posX2 + 4, 6.5f, -1);

                // RenderUtil.drawOutlinedString(font, print, posX2 + 4, 6, Color.BLACK.getRGB(), -1);
                //                RenderUtil.drawRoundedRect1232(posX2, posY1, width2, height, 16, 0x90000000);
//                RenderUtil.drawGradientSideways(2.0, posY1, 3.0f + width2 / 3.0f, posY1 + 2, new Color(81, 149, 219, 255).getRGB(), new Color(180, 49, 218, 255).getRGB());
//                RenderUtil.drawGradientSideways(2.0f + width2 / 3.0f, posY1, 4.0f + width2 / 3.0f * 2.0f, posY1 + 2, new Color(180, 49, 218, 255).getRGB(), new Color(236, 93, 128, 255).getRGB());
//                RenderUtil.drawGradientSideways(2.0f + width2 / 3.0f * 2.0f, posY1, width2 / 3.0f * 3.0f + 2f, posY1 + 2, new Color(236, 93, 128, 255).getRGB(), new Color(167, 171, 90, 255).getRGB());
//                RenderUtil.drawOutlinedString(font, print, 6.5f, 7.5f, Color.BLACK.getRGB(), -1);
//                ScaledResolution sr= new ScaledResolution(Minecraft.getMinecraft());
                break;
            }
            case Simple: {
                SimpleDateFormat td = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                font.drawStringWithShadow( "Metaware " + "[" + td.format(date) + "]", 3, 3, new Color(0, 0, 0).getRGB());
                break;
            }
            case Skeet: {
                color = RenderUtil.fade(new Color(this.colorValue.getValue()), 100, (int) 4);
                Date date = new Date();
                SimpleDateFormat td = new SimpleDateFormat("HH:mm");
                String string = ChatFormatting.BOLD + "Meta" + ChatFormatting.DARK_GREEN + ChatFormatting.BOLD + "sense" + ChatFormatting.WHITE + ChatFormatting.BOLD +" | " + Metaware.INSTANCE.getUser() + " | " + Minecraft.getDebugFPS() + " fps | " + bpsFormat.format(xz) + " bps | " + td.format(date);

                ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
                RenderUtil.drawBorderedRect(5, 5, sfont.getWidth(string) + 8, 21, 3, new Color(70, 70, 70).getRGB(), new Color(21, 21, 21).getRGB());
                RenderUtil.drawGradientSideways(8, 8.5, sfont.getWidth(string) + 9.5f, 9.5, new Color(0, 128, 255, 255).getRGB(), new Color(75, 255, 0, 255).getRGB());
                sfont.drawString(string, 10, 12f, -1);
                break;
            }
        }
        }
        final String uid = "UID: " + Metaware.INSTANCE.getUID();
        RenderUtil.drawOutlinedString(font, uid, new ScaledResolution(mc).getScaledWidth() - font.getWidth(uid), new ScaledResolution(mc).getScaledHeight() - font.getHeight(uid), Color.BLACK.getRGB(), -1);

    };

    public void drawRadar(final ScaledResolution sr) {
        int size = this.size.getValue().intValue();
        xOffset = this.X.getValue().floatValue();
        yOffset = this.Y.getValue().floatValue();
        float playerOffsetX = (float) mc.thePlayer.posX;
        float playerOffSetZ = (float) mc.thePlayer.posZ;
        int var141 = sr.getScaledWidth();
        int var151 = sr.getScaledHeight();
        int mouseX = Mouse.getX() * var141 / mc.displayWidth;
        int mouseY = var151 - Mouse.getY() * var151 / mc.displayHeight - 1;
        if ((float) mouseX >= xOffset && (float) mouseX <= xOffset + (float) size && (float) mouseY >= yOffset - 3.0F && (float) mouseY <= yOffset + 10.0F && Mouse.getEventButton() == 0) {
            this.dragging = !this.dragging;
        }

        if (mc.currentScreen instanceof GuiChat) {
        }

        if (this.hue > 255.0F) {
            this.hue = 0.0F;
        }

        float h = this.hue;
        float h2 = this.hue + 85.0F;
        float h3 = this.hue + 170.0F;
        if (h > 255.0F) {
            h = 0.0F;
        }

        if (h2 > 255.0F) {
            h2 -= 255.0F;
        }

        if (h3 > 255.0F) {
            h3 -= 255.0F;
        }

        Color color33 = Color.getHSBColor(h / 255.0F, 0.9F, 1.0F);
        Color color332 = Color.getHSBColor(h2 / 255.0F, 0.9F, 1.0F);
        Color color333 = Color.getHSBColor(h3 / 255.0F, 0.9F, 1.0F);
        int color1 = color33.getRGB();
        int color2 = color332.getRGB();
        int color3 = color333.getRGB();
        this.hue = (float) ((double) this.hue + 0.1D);
        RenderUtil.drawBorderedRect((double) (xOffset + 3.0F), (double) (yOffset + 3.0F), (double) ((float) size - 6.0F), (double) ((float) size - 6.0F), 1.2, new Color(28, 28, 28, 255).getRGB(), new Color(50, 50, 50, 255).getRGB());
        //Gui.drawRect((double) (xOffset + 4F), (double) (yOffset + 5F), (double) (xOffset + (float) (size / 2)), (double) yOffset + 4d, Panel.color);
        //Gui.drawRect((double) (xOffset + (float) (size / 3)), (double) (yOffset + 5F), (double) (xOffset + (float) size - 4F), (double) yOffset + 4d, Panel.color);
        Gui.drawRect((double) xOffset + ((double) (size / 2) + 0.7D), (double) yOffset + 4.0D, (double) xOffset + (double) (size / 2) + 1.5, (double) (yOffset + (float) size) - 3.8D, new Color(28, 28, 28, 255).getRGB());
        Gui.drawRect((double) xOffset + 3.5D, (double) yOffset + ((double) (size / 2) - 0.5D), (double) (xOffset + (float) size) - 4.0D, (double) yOffset + (double) (size / 2) + 0.56, new Color(28, 28, 28, 255).getRGB());
        Iterator var21 = mc.theWorld.getLoadedEntityList().iterator();

        while (var21.hasNext()) {
            Object o = var21.next();
            if (o instanceof EntityPlayer) {
                EntityPlayer ent = (EntityPlayer) o;
                if (ent.isEntityAlive() && ent != mc.thePlayer && !ent.isInvisible() && !ent.isInvisibleToPlayer(mc.thePlayer)) {
                    float pTicks = mc.timer.renderPartialTicks;
                    float posX = (float) ((ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double) pTicks - (double) playerOffsetX));
                    float posZ = (float) ((ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double) pTicks - (double) playerOffSetZ));

                    float cos = (float) Math.cos((double) mc.thePlayer.rotationYaw * 0.017453292519943295D);
                    float sin = (float) Math.sin((double) mc.thePlayer.rotationYaw * 0.017453292519943295D);
                    float rotY = -(posZ * cos - posX * sin);
                    float rotX = -(posX * cos + posZ * sin);
                    if (rotY > (float) (size / 2 - 5)) {
                        rotY = (float) (size / 2) - 5.0F;
                    } else if (rotY < (float) (-(size / 2 - 5))) {
                        rotY = (float) (-(size / 2 - 5));
                    }

                    if (rotX > (float) (size / 2) - 5.0F) {
                        rotX = (float) (size / 2 - 5);
                    } else if (rotX < (float) (-(size / 2 - 5))) {
                        rotX = -((float) (size / 2) - 5.0F);
                    }

                    int color = PlayerUtil.isOnSameTeam((EntityLivingBase) o) ? new Color(255, 255, 255).getRGB() : new Color(200, 0, 0).getRGB();
                    RenderUtil.drawCircle((float) (xOffset + (float) (size / 2) + rotX) - 1.0f, (float) (yOffset + (float) (size / 2) + rotY) - 1.0f, 2.2f, new Color(0, 0, 0).getRGB());
                    RenderUtil.drawCircle((float) (xOffset + (float) (size / 2) + rotX) - 1.0f, (float) (yOffset + (float) (size / 2) + rotY) - 1.0f, 2, color);
                }
            }
        }
    }

    private void drawArmorStatus(ScaledResolution sr) {
        GL11.glPushMatrix();
        List stuff = new ArrayList();
        boolean onwater = mc.thePlayer.isEntityAlive() && mc.thePlayer.isInsideOfMaterial(Material.water);
        int split = -3;

        ItemStack errything;
        for(int index = 3; index >= 0; --index) {
            errything = mc.thePlayer.inventory.armorInventory[index];
            if (errything != null) {
                stuff.add(errything);
            }
        }

        if (mc.thePlayer.getCurrentEquippedItem() != null) {
            stuff.add(mc.thePlayer.getCurrentEquippedItem());
        }

        Iterator var8 = stuff.iterator();

        while(var8.hasNext()) {
            errything = (ItemStack)var8.next();
            if (mc.theWorld != null) {
                RenderHelper.enableGUIStandardItemLighting();
                split += 16;
            }

            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            mc.getRenderItem().zLevel = -150.0F;
            int s = mc.thePlayer.capabilities.isCreativeMode ? 15 : 0;
            mc.getRenderItem().renderItemAndEffectIntoGUI(errything, split + sr.getScaledWidth() / 2 - 4, sr.getScaledHeight() - (onwater ? 65 : 55) + s);
            mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, errything, split + sr.getScaledWidth() / 2 - 4, sr.getScaledHeight() - (onwater ? 65 : 55) + s);
            mc.getRenderItem().zLevel = 0.0F;
            GlStateManager.disableBlend();
            GlStateManager.scale(0.5D, 0.5D, 0.5D);
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
            errything.getEnchantmentTagList();
        }

        GL11.glPopMatrix();
    }

    private void drawPotionStatus(ScaledResolution sr) {
        int y = 0;
        for (final PotionEffect effect : (Collection<PotionEffect>) this.mc.thePlayer.getActivePotionEffects()) {
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String PType = I18n.format(potion.getName());
            switch (effect.getAmplifier()) {
                case 1:
                    PType = PType + " II";
                    break;
                case 2:
                    PType = PType + " III";
                    break;
                case 3:
                    PType = PType + " IV";
                    break;
                default:
                    break;
            }
            if (effect.getDuration() < 600 && effect.getDuration() > 300) {
                PType = PType + "\2477:\2476 " + Potion.getDurationString(effect);
            } else if (effect.getDuration() < 300) {
                PType = PType + "\2477:\247c " + Potion.getDurationString(effect);
            } else if (effect.getDuration() > 600) {
                PType = PType + "\2477:\2477 " + Potion.getDurationString(effect);
            }
            int ychat = mc.ingameGUI.getChatGUI().getChatOpen() ? 5 : 2;
            mc.fontRendererObj.drawStringWithShadow(PType, sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(PType) - 1, sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT + y - 12 - ychat, new Color(255, 255, 255).getRGB());
            y -= 10;
        }
    }

    public void draw(ScaledResolution sr) {
        GL11.glPushMatrix();
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();

        boolean tahoma = fontMode.getValue().equals(FontMode.Tahoma);
        final CustomFontRenderer fr = Metaware.INSTANCE.getFontManager().currentFont().size(18);

        final FontRenderer fro = mc.fontRendererObj;

        final boolean bottom = false;

        boolean vanilla = fontMode.getValue().equals(FontMode.Vanilla);

        List<Module> sortedList = getSortedModules(fr, fro);
        float translationFactor = 40.4F / Minecraft.getDebugFPS();
        double listOffset = 11.5f, y = bottom ? (height - listOffset) : 0;
        hue += translationFactor / 255.0F;
        if (hue > 1.0F)
            hue = 0.0F;
        float h = hue;
        GL11.glEnable(3042);
        GL11.glPushMatrix();
        StencilUtil.initStencilToWrite();
        for (int i = 0, sortedListSize = sortedList.size(); i < sortedListSize; i++) {
            if (background.getValue()) {
                GL11.glPushMatrix();
                Module module = sortedList.get(i);
                Translate translate = module.translate;
                String moduleLabel = module.getRenderName();
                float length = vanilla ? fro.getStringWidth(moduleLabel) : fr.getWidth(moduleLabel);
                double translateX = translate.getX();
                double translateY = translate.getY();


                double x = vanilla ? translateX - 4.0D : translateX - 2.5f;
                //background for dinozoid
                Gui.drawRect(x, translateY - 1.0D, width, translateY + listOffset - 1.0D, -1);
                GL11.glPopMatrix();
            }
        }
        StencilUtil.readStencilBuffer(1);
        Metaware.INSTANCE.getBlurShader(10).blur();
        StencilUtil.uninitStencilBuffer();
        GL11.glPopMatrix();
        for (int i = 0, sortedListSize = sortedList.size(); i < sortedListSize; i++) {
            Module module = sortedList.get(i);
            Translate translate = module.translate;
            String moduleLabel = module.getRenderName();
            float length = vanilla ? fro.getStringWidth(moduleLabel) : fr.getWidth(moduleLabel);
            float featureX = width - length;
            boolean enable = module.isToggled();
            if (bottom) {
                if (enable) {
                    translate.interpolate(featureX, (y + 1), translationFactor);
                }
                else {
                    translate.interpolate(width, (height + 1), translationFactor);
                }
            }
            else if (enable) {
                translate.interpolate(featureX, (y + 1), translationFactor);
            }
            else {
                translate.interpolate(width * width, (-listOffset - 1), translationFactor);
            }
            double translateX = translate.getX();
            double translateY = translate.getY();
            boolean visible = bottom ? ((translateY < height)) : ((translateY > -listOffset));
            if (visible) {
                int color;
                color = 0;
                switch (arrayListColor.getValue()) {
                    case Custom:
                        color = new Color(colorValue.getValue()).getRGB();
                        boolean pulsing = this.pulsing.getValue();
                        if (pulsing) {
                            float colorWidth = vanilla ? fro.getStringWidth(moduleLabel) * 2 + 10 : fr.getWidth(moduleLabel) * 2 + 10;
                            color = RenderUtil.fade(new Color(this.colorValue.getValue()), 100, (int) colorWidth);
                        }
                        break;
                    case Rainbow:
                        color = RenderUtil.getRainbowFelix(6000, (int) (y * 30), rainbowSaturation.getValue().floatValue());
                        break;
                    case Felix:
                        color = RenderUtil.getGradientOffset(new Color(64, 85, 150), new Color(130, 191, 226), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + (y / (50))).getRGB();
                        break;
                    case Astolfo:
                        color = RenderUtil.getGradientOffset(new Color(0, 255, 255), new Color(255,105,180), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + (y / (50))).getRGB();
                        break;
                    default:
                        break;
                }
                int nextIndex = sortedList.indexOf(module) + 1;
                final int outlineColor = arrayListColor.getValue().equals(ColorMode.Custom) ? new Color(colorValue.getValue()).getRGB()  : color;
                if (background.getValue()) {
                    GL11.glPushMatrix();
                    double x = vanilla ? translateX - 4.0D : translateX - 3.0D;
                    //background for dinozoid
                    Gui.drawRect(x, translateY - 1.0D, translateX - 2, translateY + listOffset - 1.0D, color);
                    Gui.drawRect(x, translateY + listOffset, (sortedList.indexOf(module) >= sortedListSize - 1 ? width : sortedList.indexOf(module) < sortedListSize ? width - 2 - fr.getWidth(sortedList.get(nextIndex).getRenderName()) : null), translateY + listOffset - 1.0D, color);
                    GL11.glPopMatrix();
                }
                if (sideLine.getValue()) {
                    Gui.drawRect(translateX + length - 2, translateY - 1.0D, width - 0.7f, translateY + listOffset - 1.0D, color);
                    Gui.drawRect(translateX + length - 2, translateY - 1.0D, width - 0.7f, translateY + listOffset - 1.0D, Color.TRANSLUCENT);
                } else {
                    Gui.drawRect(translateX + length - 1.0, translateY - 1.0D, width, translateY + listOffset - 1.0D, Color.TRANSLUCENT);
                }
                if (vanilla) {
                    fro.drawStringWithShadow(moduleLabel, (float)translateX - 1.4f, (float)translateY + 1, color);
                }
                else {
                    fr.drawStringWithShadow(moduleLabel, (float)translateX - 1.5f, (float)translateY + 0.5f, color);
                }
                if (module.isToggled())
                    y += bottom ? -listOffset : listOffset;
                h += translationFactor / 6.0F;
            }
        }
        GL11.glPopMatrix();
    }

    public boolean hasToggleSoundsEnabled() {
        return toggleSounds.getValue();
    }

    public static List<Module> getActiveModules() {
        List<Module> modds = new ArrayList<>();
        for (Module mod : Metaware.INSTANCE.getModuleManager().getModules()) {
            if (mod.isToggled()) {
                modds.add(mod);
            }
        }
        return modds;
    }

    private List<Module> getSortedModules(final CustomFontRenderer fr, FontRenderer fro) {
        boolean vanilla = fontMode.getValue().equals(FontMode.Vanilla);
        List<Module> sortedList = getActiveModules();
        sortedList.sort(Comparator.comparingDouble(e -> vanilla ? -fro.getStringWidth(e.getRenderName()) : -fr.getWidth(e.getRenderName())));
        return sortedList;
    }
}
