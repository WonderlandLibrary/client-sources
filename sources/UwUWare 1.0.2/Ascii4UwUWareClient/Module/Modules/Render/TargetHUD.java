package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventRender2D;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Combat.Killaura;
import Ascii4UwUWareClient.UI.Font.CFontRenderer;
import Ascii4UwUWareClient.UI.Font.FontLoaders;
import Ascii4UwUWareClient.Util.GuiUtils;
import Ascii4UwUWareClient.Util.Helper;
import Ascii4UwUWareClient.Util.Render.AnimationUtil;
import Ascii4UwUWareClient.Util.Render.ColorUtils;
import Ascii4UwUWareClient.Util.Render.RenderUtil;
import Ascii4UwUWareClient.Util.Render.RenderUtils;
import com.ibm.icu.text.NumberFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.optifine.MathUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TargetHUD extends Module {
    public static boolean shouldMove;
    public static boolean useFont;
    private Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[]) TargetHUDMode.values(), (Enum) TargetHUDMode.Exhi);
    public DecimalFormat format;
    public float lastHealth;
    public float damageDelt;
    public float lastPlayerHealth;
    public float damageDeltToPlayer;
    private float animated = 20f;

    private int targetX = 400, targetY = 300, bpsX = 0, bpsY = 440, playerListX = 30, playerListY = 30, pingX = 0, pingY = 425, coordX = 0, coordY= 425, labelX = 2, labelY = 2, buildInfoX = 0, buildInfoY = 400, size = 16, potionsX = 37, potionsY = (new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - (230) - size * 2) - 5, moduleListX = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), moduleListY = 0;
    private double lastPosX;
    private double lastPosZ;
    public ArrayList <Double> distances;
    private int y;
    private int x;

    public double animation;
    public EntityLivingBase lastEnt;
    private EntityLivingBase target;
    private double healthBarWidth;
    private double hudHeight;

    public TargetHUD() {
        super("TargetHUD", new String[]{"gui"}, ModuleType.Render);
        this.addValues(this.mode);

    }

    public static int[] getFractionIndicies(final float[] fractions, final float progress) {
        final int[] range = new int[2];
        int startPoint;
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
        }
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }

    public static Color blendColors(final float[] fractions, final Color[] colors, final float progress) {
        Color color = null;
        if (fractions == null) {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        if (colors == null) {
            throw new IllegalArgumentException("Colours can't be null");
        }
        if (fractions.length == colors.length) {
            final int[] indicies = getFractionIndicies(fractions, progress);
            final float[] range = {fractions[indicies[0]], fractions[indicies[1]]};
            final Color[] colorRange = {colors[indicies[0]], colors[indicies[1]]};
            final float max = range[1] - range[0];
            final float value = progress - range[0];
            final float weight = value / max;
            color = blend(colorRange[0], colorRange[1], 1.0f - weight);
            return color;
        }
        throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
    }

    public static Color blend(final Color color1, final Color color2, final double ratio) {
        final float r = (float) ratio;
        final float ir = 1.0f - r;
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0f) {
            red = 0.0f;
        } else if (red > 255.0f) {
            red = 255.0f;
        }
        if (green < 0.0f) {
            green = 0.0f;
        } else if (green > 255.0f) {
            green = 255.0f;
        }
        if (blue < 0.0f) {
            blue = 0.0f;
        } else if (blue > 255.0f) {
            blue = 255.0f;
        }
        Color color3 = null;
        try {
            color3 = new Color(red, green, blue);
        } catch (IllegalArgumentException exp) {
            final NumberFormat nf = NumberFormat.getNumberInstance();
            System.out.println(nf.format((double) red) + "; " + nf.format((double) green) + "; " + nf.format((double) blue));
            exp.printStackTrace();
        }
        return color3;
    }

    @EventHandler
    public void onScreenDraw(EventRender2D er) {
        setSuffix ( mode.getModeAsString () );
        ScaledResolution res = new ScaledResolution ( this.mc );
        final EntityLivingBase player = (EntityLivingBase) Killaura.target;
        this.target = Killaura.target;
        final Entity Mineplex = (Entity) Killaura.target;
        if (mode.getValue () == TargetHUDMode.Exhi) {
            CFontRenderer font14 = FontLoaders.Tahoma12;
            CFontRenderer font12 = FontLoaders.Tahoma12;
            CFontRenderer font10 = FontLoaders.roboto10;


            final Killaura aura = (Killaura) Client.instance.getModuleManager ().getModuleByClass ( Killaura.class );
            ScaledResolution sr = new ScaledResolution ( Minecraft.getMinecraft () );
            final float scaledWidth = sr.getScaledWidth ();
            final float scaledHeight = sr.getScaledHeight ();
            if (Killaura.target != null && aura.isEnabled ()) {
                if (Killaura.target instanceof EntityOtherPlayerMP) {
                    float startX = 20;
                    float renderX = (sr.getScaledWidth () / 2) + startX;
                    float renderY = (sr.getScaledHeight () / 2) + 10;
                    int maxX2 = 30;
                    if (Killaura.target.getCurrentArmor ( 3 ) != null) {
                        maxX2 += 15;
                    }
                    if (Killaura.target.getCurrentArmor ( 2 ) != null) {
                        maxX2 += 15;
                    }
                    if (Killaura.target.getCurrentArmor ( 1 ) != null) {
                        maxX2 += 15;
                    }
                    if (Killaura.target.getCurrentArmor ( 0 ) != null) {
                        maxX2 += 15;
                    }
                    if (Killaura.target.getHeldItem () != null) {
                        maxX2 += 15;
                    }
                    final float width = 140.0f;
                    final float height = 40.0f;
                    final float xOffset = 40.0f;
                    final float x = scaledWidth / 2.0f + 30.0f;
                    final float y = scaledHeight / 2.0f + 30.0f;
                    final float health = this.target.getHealth ();
                    double hpPercentage = health / this.target.getMaxHealth ();
                    hpPercentage = MathHelper.clamp_double ( hpPercentage, 0.0, 1.0 );
                    final double hpWidth = 60.0 * hpPercentage;
                    final int healthColor = ColorUtils.getHealthColor ( this.target.getHealth (), this.target.getMaxHealth () )
                            .getRGB ();
                    final String healthStr = String.valueOf ( (int) this.target.getHealth () / 1.0f );
                    int xAdd = 0;
                    double multiplier = 0.85;
                    GlStateManager.pushMatrix ();
                    GlStateManager.scale ( multiplier, multiplier, multiplier );
                    if (Killaura.target.getCurrentArmor ( 3 ) != null) {
                        mc.getRenderItem ().renderItemAndEffectIntoGUI ( Killaura.target.getCurrentArmor ( 3 ), (int) ((((sr.getScaledWidth () / 2) + startX + 33) + xAdd) / multiplier), (int) (((sr.getScaledHeight () / 2) + 56) / multiplier) );
                        xAdd += 15;
                    }
                    if (Killaura.target.getCurrentArmor ( 2 ) != null) {
                        mc.getRenderItem ().renderItemAndEffectIntoGUI ( Killaura.target.getCurrentArmor ( 2 ), (int) ((((sr.getScaledWidth () / 2) + startX + 33) + xAdd) / multiplier), (int) (((sr.getScaledHeight () / 2) + 56) / multiplier) );
                        xAdd += 15;
                    }
                    if (Killaura.target.getCurrentArmor ( 1 ) != null) {
                        mc.getRenderItem ().renderItemAndEffectIntoGUI ( Killaura.target.getCurrentArmor ( 1 ), (int) ((((sr.getScaledWidth () / 2) + startX + 33) + xAdd) / multiplier), (int) (((sr.getScaledHeight () / 2) + 56) / multiplier) );
                        xAdd += 15;
                    }
                    if (Killaura.target.getCurrentArmor ( 0 ) != null) {
                        mc.getRenderItem ().renderItemAndEffectIntoGUI ( Killaura.target.getCurrentArmor ( 0 ), (int) ((((sr.getScaledWidth () / 2) + startX + 33) + xAdd) / multiplier), (int) (((sr.getScaledHeight () / 2) + 56) / multiplier) );
                        xAdd += 15;
                    }
                    if (Killaura.target.getHeldItem () != null) {
                        mc.getRenderItem ().renderItemAndEffectIntoGUI ( Killaura.target.getHeldItem (), (int) ((((sr.getScaledWidth () / 2) + startX + 33) + xAdd) / multiplier), (int) (((sr.getScaledHeight () / 2) + 56) / multiplier) );
                    }
                    GlStateManager.popMatrix ();
                    this.healthBarWidth = AnimationUtil.INSTANCE.animate ( hpWidth, this.healthBarWidth, 0.1 );
                    Gui.drawGradientRect ( x - 3.5, y - 3.5, x + 105.5f, y + 42.4f, new Color ( 10, 10, 10, 255 ).getRGB (), new Color ( 10, 10, 10, 255 ).getRGB () );
                    RenderUtils.prepareScissorBox ( x, y, x + 140.0f, (float) (y + this.hudHeight) );
                    Gui.drawGradientRect ( x - 3, y - 3.2, x + 104.8f, y + 41.8f, new Color ( 40, 40, 40, 255 ).getRGB (), new Color ( 40, 40, 40, 255 ).getRGB () );
                    Gui.drawGradientRect ( x - 1.4, y - 1.5, x + 103.5f, y + 40.5f, new Color ( 74, 74, 74, 255 ).getRGB (), new Color ( 74, 74, 74, 255 ).getRGB () );
                    Gui.drawGradientRect ( x - 1, y - 1, x + 103.0f, y + 40.0f, new Color ( 32, 32, 32, 255 ).getRGB (), new Color ( 10, 10, 10, 255 ).getRGB () );
                    Gui.drawRect ( x + 25.0f, y + 11.0f, x + 87f, y + 14.29f, new Color ( 105, 105, 105, 40 ).getRGB () );
                    Gui.drawRect ( x + 25.0f, y + 11.0f, x + 27f + this.healthBarWidth, y + 14.29f, RenderUtil.getColorFromPercentage ( this.target.getHealth (), this.target.getMaxHealth () ) );
                    font14.drawStringWithShadow ( this.target.getName (), x + 24.8f, y + 1.9f, new Color ( 255, 255, 255 ).getRGB () );
                    font12.drawStringWithShadow ( "l   " + "l   " + "l   " + "l   " + "l   " + "l   " + "l   " + "l   ", x + 29.2f, y + 11.4f, new Color ( 50, 50, 50 ).getRGB () );
                    font10.drawStringWithShadow ( "HP:" + healthStr + "  l   " + "Dist:" + ((int) target.getDistanceToEntity ( Minecraft.thePlayer )) + "  l " + "Hurt:" + (Killaura.target.hurtResistantTime > 0), x - 11.2f + 44.0f - font10.getStringWidth ( healthStr ) / 2.0f, y + 17.0f, -1 );
                    GuiInventory.drawEntityOnScreen ( (int) (x + 12f), (int) (y + 34.0f), 15, this.target.rotationYaw, this.target.rotationPitch, this.target );
                }
            } else {
                this.healthBarWidth = 92.0;
                this.hudHeight = 0.0;
                this.target = null;
            }
        }
        if (mode.getValue () == TargetHUDMode.Novoline) {
            EntityLivingBase currentEntity = Killaura.target;
            targetX = x;
            targetY = y;
            int width1 = 250;
            Killaura aura = (Killaura) Client.instance.getModuleManager ().getModuleByClass ( Killaura.class );

            if (currentEntity != null && Killaura.target != null) {
                String name = "Name: " + (currentEntity instanceof EntityPlayer ? currentEntity.getDisplayName ().getFormattedText () : currentEntity.getName ());
                String reach = "Reach: " + String.valueOf ( MathUtils.round ( (double) mc.thePlayer.getDistanceToEntity ( currentEntity ), 2 ) );
                String armor = "Armor: " + Math.round ( currentEntity.getTotalArmorValue () );
                String hasBetterArmor = "";
                if (currentEntity.getTotalArmorValue () > Minecraft.getMinecraft ().thePlayer.getTotalArmorValue ()) {
                    hasBetterArmor = "Them";
                } else if (currentEntity.getTotalArmorValue () == Minecraft.getMinecraft ().thePlayer.getTotalArmorValue ()) {
                    hasBetterArmor = "None";
                } else {
                    hasBetterArmor = "You";
                }

                String moreHealth = "Health Advantage: ";
                if (currentEntity.getHealth () > mc.thePlayer.getHealth ()) {
                    moreHealth += "Them: " + EnumChatFormatting.GRAY + MathUtils.round ( (double) (Math.abs ( currentEntity.getHealth () - mc.thePlayer.getHealth () ) / 2), 2 );
                } else if (currentEntity.getTotalArmorValue () == mc.thePlayer.getHealth () || Math.abs ( currentEntity.getHealth () - mc.thePlayer.getHealth () ) / 2 == 0.0) {
                    moreHealth += "None";
                } else {
                    moreHealth += "You: " + EnumChatFormatting.GRAY + MathUtils.round ( (double) (Math.abs ( currentEntity.getHealth () - mc.thePlayer.getHealth () ) / 2), 2 );
                }

                width1 = 170;
                RenderUtils.drawRoundedRect ( targetX, targetY, targetX + width1, targetY - 75, new Color ( 255, 90, 90, 100 ).getRGB (), new Color ( 0, 0, 0, 127 ).getRGB () );
                ;

                FontLoaders.GoogleSans18.drawStringWithShadow ( name, targetX + 45, targetY - 70, new Color ( 255, 255, 255 ).getRGB () );
                FontLoaders.GoogleSans18.drawStringWithShadow ( reach, targetX + 45, targetY - 60, new Color ( 255, 255, 255 ).getRGB () );
                FontLoaders.GoogleSans18.drawStringWithShadow ( armor, targetX + 45, targetY - 50, new Color ( 255, 255, 255 ).getRGB () );

                FontLoaders.GoogleSans18.drawStringWithShadow ( "Armor Advantage: " + hasBetterArmor, targetX + 45, targetY - 30, new Color ( 255, 255, 255 ).getRGB () );
                if (!Float.isNaN ( currentEntity.getHealth () )) {
                    float xSpeed = (150 - 45) / (mc.debugFPS * 1.2f);
                    float desiredWidth = (150 - 45) / currentEntity.getMaxHealth ()
                            * Math.min ( currentEntity.getHealth (), currentEntity.getMaxHealth () );
                    if (desiredWidth < animated || desiredWidth > animated) {
                        if (Math.abs ( desiredWidth - animated ) <= xSpeed) {
                            animated = desiredWidth;
                        } else {
                            animated += (animated < desiredWidth ? xSpeed * 4 : -xSpeed);
                        }
                    }
                    double hpPercentage = currentEntity.isDead ? 0 : currentEntity.getHealth () / 20;
                    if (hpPercentage > 1)
                        hpPercentage = 1;
                    else if (hpPercentage < 0)
                        hpPercentage = 0;


                    int r = (int) (230 + (50 - 230) * hpPercentage);
                    int g = (int) (50 + (230 - 50) * hpPercentage);
                    int b = 50;

                    FontLoaders.GoogleSans18.drawStringWithShadow ( moreHealth, targetX + 45, targetY - 20, new Color ( 255, 255, 255 ).getRGB () );
                    RenderUtils.drawRoundedRect ( targetX + 44, targetY - 3, targetX + 151, targetY - 8, new Color ( 255, 90, 90, 100 ).getRGB (), new Color ( 0, 0, 0, 90 ).getRGB () );
                    RenderUtils.drawRoundedRect ( targetX + 45, targetY - 3, targetX + 45 + animated, targetY - 8, new Color ( 255, 90, 90, 100 ).getRGB (), new Color ( r, g, b ).getRGB () );
                } else {
                    FontLoaders.GoogleSans18.drawStringWithShadow ( "Health is a NaN! (Bot?)", targetX + 35, targetY - 16, new Color ( 255, 255, 255 ).getRGB () );
                    RenderUtils.drawRoundedRect ( targetX + 30, targetY - 5, targetX + 150, targetY - 20, new Color ( 255, 90, 90, 100 ).getRGB (), new Color ( 0, 0, 0, 127 ).getRGB () );
                }
                RenderUtils.drawRoundedRect ( targetX + 5, targetY - 7, targetX + 45, targetY - 70, new Color ( 255, 90, 90, 100 ).getRGB (), new Color ( 0, 0, 0, 127 ).getRGB () );
                GL11.glEnable ( GL11.GL_BLEND );
                GL11.glColor4f ( 1, 1, 1, 1 );
                RenderUtils.drawRoundedRect ( targetX + 25, targetY - 10, 30, currentEntity.rotationYaw, -currentEntity.rotationPitch, currentEntity.chunkCoordX );
                GL11.glDisable ( GL11.GL_BLEND );
            }

            if (mode.getValue () == TargetHUDMode.New) {

                FontRenderer pjes = mc.fontRendererObj;

                DecimalFormat decimalFormat = new DecimalFormat ( "#.#" );
                drawExhiRect ( x, y, x + 130, y + 40 );
                pjes.drawString ( target.getName (), (int) x + 3, (int) y - pjes.FONT_HEIGHT + 32, -1 );//+37
                Gui.drawRect ( x + 3 - 1.5f, y + 35 - 1.5f, x + 127 + 1.5f, y + 37 + 1.5f, new Color ( 24, 22, 22 ).getRGB () );
                Helper.sendMessage ("i hate niggers");
                float width = (x + 127 + 1.5f) - (x + 3 - 1.5f);
                float pjesjesjedzony = width / 5;
                Gui.drawRect ( x + 3, y + 35, x + 127, y + 37, new Color ( 56, 54, 54 ).getRGB () );
                for (int i = 0; i < 5; i++) {
                    Gui.drawRect ( x + 3 - 1.5f + pjesjesjedzony * i, y + 35, x + 3 - 1.5f + pjesjesjedzony * i + 1, y + 37, new Color ( 28, 26, 26 ).getRGB () );
                }
                pjes.drawString ( decimalFormat.format ( mc.thePlayer.getDistanceToEntity ( target ) ) + " m", (int) x + 3, (int) y - pjes.FONT_HEIGHT + 21, -1 );
                GlStateManager.pushMatrix ();
                GlStateManager.scale ( 2, 2, 2 );
                float health = target.getHealth () / 2;
                pjes.drawString ( decimalFormat.format ( health ).replace ( ",", "." ) + " ❤", (int) (x + 128 - (pjes.getStringWidth ( decimalFormat.format ( health ).replace ( ",", "." ) + " ❤" )) * 2) / 2, (int) (y - pjes.FONT_HEIGHT + 20) / 2, -1 );
                GlStateManager.popMatrix ();
            }
        }


        if (mode.getValue() == TargetHUDMode.UwUWare) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            float sw = (float) sr.getScaledWidth();
            float sh = (float) sr.getScaledHeight();
            if (Killaura.target != null) {
                if (Killaura.target instanceof EntityPlayer) {
                    this.target = (EntityOtherPlayerMP) Killaura.target;
                    String name = this.target.getName();
                    int percent = Integer.valueOf(((int) Killaura.target.getHealth()) / 2);
                    String uni = "";
                    String healthColor = "";
                    uni = "\u2764 ";
                    healthColor = "\2477";
                    float xSpeed = 133f / (Minecraft.getDebugFPS() * 1.05f);
                    float desiredWidth = ((FontLoaders.Comfortaa18.getStringWidth(name) + 45) / Killaura.target.getMaxHealth()) * Math.min(Killaura.target.getHealth(), Killaura.target.getMaxHealth());
                    if (desiredWidth < animated || desiredWidth > animated) {
                        if (Math.abs(desiredWidth - animated) <= xSpeed) {
                            animated = desiredWidth;
                        } else {
                            animated += (animated < desiredWidth ? xSpeed * 3 : -xSpeed);
                        }
                    }
                    GuiUtils.drawRoundedRect(sw / 2 + 10, sh / 2 - 30, (FontLoaders.Comfortaa18.getStringWidth(name) - 35) + sw / 2 + 110, sh / 2 + 20, 0x90000000, 0x90000000);
                    drawFace((int) sw / 2 + 12, (int) sh / 2 - 28, 8, 8, 8, 8, 28, 28, 64, 64, (AbstractClientPlayer) this.target);
                    Minecraft.getMinecraft().fontRendererObj.drawString(uni, (int) sw / 2 + 12, (int) sh / 2, new Color(244, 102, 101).getRGB());
                    GuiUtils.drawRoundedRect1(sw / 2 + 21, sh / 2 + 3.5f, (FontLoaders.Comfortaa18.getStringWidth(name) + 45), 0.1F, 0x900000, 0x90000000);
                    GuiUtils.drawRoundedRect1(sw / 2 + 21, sh / 2 + 3.5f, animated, 0.1F, 0x900000, getHealthColorTest(this.target).getRGB());
                    FontLoaders.Comfortaa18.drawString(name, sw / 2 + 45, sh / 2 - 20, -1);
                    FontLoaders.Tahoma12.drawString("Health: " + String.format("%.1f", target.getHealth()), sw / 2 + 20, sh / 2 + 10, -1);
                }
            }
        }

        if (mode.getValue() == TargetHUDMode.CatSense) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

            float sx = (float) sr.getScaledWidth();
            float sy = (float) sr.getScaledHeight();

            if (Killaura.target != null && Killaura.target instanceof EntityPlayer) {
                //FontRenderer pjes = mc.fontRendererObj;
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                drawExhiRect(sx/2, sy/2, sx/2 + 130, sy/2 + 40);
                mc.fontRendererObj.drawString(target.getName(), (int) sx/2 + 3, (int) sy/2 - mc.fontRendererObj.FONT_HEIGHT + 32, -1);//+37
                Gui.drawRect(sx/2 + 3 - 1.5f, sy/2 + 35 - 1.5f, sx/2 + 127 + 1.5f, sy/2 + 37 + 1.5f, new Color(24, 22, 22).getRGB());
                float width = (sx/2 + 127 + 1.5f) - (sx/2 + 3 - 1.5f);
                float pjesjesjedzony = width / 5;
                Gui.drawRect(sx/2 + 3, sy/2 + 35, sx/2 + 127, sy/2 + 37, new Color(56, 54, 54).getRGB());
                GuiUtils.drawBar(target.getHealth(), target.getMaxHealth(), sx/2 + 3, sy/2 + 35, width - 3, 2, getHealthColorTest(target).getRGB());
                for (int i = 0; i < 5; i++) {
                    Gui.drawRect(sx/2 + 3 - 1.5f + pjesjesjedzony * i, sy/2 + 35, sx/2 + 3 - 1.5f + pjesjesjedzony * i + 1, sy/2 + 37, new Color(28, 26, 26).getRGB());
                }
                mc.fontRendererObj.drawString(decimalFormat.format(mc.thePlayer.getDistanceToEntity(target)) + " m", (int)sx/2 + 3, (int) sy/2 - mc.fontRendererObj.FONT_HEIGHT + 21, -1);
                GlStateManager.pushMatrix();
                GlStateManager.scale(2, 2, 2);
                float health = target.getHealth() / 2;
                mc.fontRendererObj.drawString(decimalFormat.format(health).replace(",", ".") + " ❤", (int) (sx/2 + 128 - (mc.fontRendererObj.getStringWidth(decimalFormat.format(health).replace(",", ".") + " ❤")) * 2) / 2, (int) (sy/2 - mc.fontRendererObj.FONT_HEIGHT + 20) / 2, -1);
                GlStateManager.popMatrix();
            }
        }
    }


    public void drawFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
        try {
            ResourceLocation skin = target.getLocationSkin();
            Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1, 1, 1, 1);
            Gui.drawScaledCustomSizeModalRect((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
            GL11.glDisable(GL11.GL_BLEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void drawExhiRect(float left, float top, float right, float bottom){
        final float gownoPsa = 1.5f;
        final float gownoZPsem = 1;
        Gui.drawRect(left-gownoPsa-gownoZPsem, top-gownoPsa-gownoZPsem, right+gownoPsa+gownoZPsem, bottom+gownoPsa+gownoZPsem, new Color(62, 59, 59).getRGB());
        Gui.drawRect(left-gownoPsa, top-gownoPsa, right+gownoPsa, bottom+gownoPsa, new Color(42, 39, 39).getRGB());
        Gui.drawRect(left, top, right, bottom, new Color(18, 16, 16).getRGB());
    }
    public Color getHealthColorTest(EntityLivingBase entityLivingBase) {
        float health = entityLivingBase.getHealth();
        float[] fractions = new float[]{0.0F, 0.15f, .55F, 0.7f, .9f};
        Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
        float progress = health / entityLivingBase.getMaxHealth();
        return health >= 0.0f ? blendColors(fractions, colors, progress).brighter() : colors[0];
    }

    private double getIncremental ( final double val, final double inc){
        final double one = 1.0 / inc;
        return Math.round ( val * one ) / one;
    }
    enum TargetHUDMode {
                Exhi, Novoline, New, UwUWare, CatSense
    }
}
