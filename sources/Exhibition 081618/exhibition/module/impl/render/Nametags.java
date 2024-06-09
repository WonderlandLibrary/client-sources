package exhibition.module.impl.render;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventNametagRender;
import exhibition.event.impl.EventRender3D;
import exhibition.event.impl.EventRenderGui;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.module.data.SettingsMap;
import exhibition.module.impl.render.ESP2D;
import exhibition.module.impl.render.TargetESP;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import exhibition.util.render.TTFFontRenderer;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Timer;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

public class Nametags
extends Module {
    public static Map<EntityPlayer, double[]> entityPositions = new HashMap<EntityPlayer, double[]>();
    private final String ARMOR = "ARMOR";
    private final String HEALTH = "HEALTH";
    private final String INVISIBLES = "INVISIBLES";
    private final String OPACITY = "OPACITY";
    private Setting distance = new Setting("DISTANCE", false, "Show distance before name.");

    public Nametags(ModuleData data) {
        super(data);
        this.settings.put("ARMOR", new Setting("ARMOR", true, "Show armor when not hovering."));
        this.settings.put("HEALTH", new Setting("HEALTH", false, "Show health when not hovering."));
        this.settings.put("INVISIBLES", new Setting("INVISIBLES", false, "Show invisibles."));
        this.settings.put("OPACITY", new Setting("OPACITY", false, "Lowers in opacity the farther away from screen center."));
        this.settings.put("DIST", this.distance);
    }

    @Override
    public EventListener.Priority getPriority() {
        return EventListener.Priority.MEDIUM;
    }

    @RegisterEvent(events={EventRender3D.class, EventRenderGui.class, EventNametagRender.class})
    public void onEvent(Event event) {
        if (event instanceof EventNametagRender) {
            event.setCancelled(true);
        }
        if (event instanceof EventRender3D) {
            try {
                this.updatePositions();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (event instanceof EventRenderGui) {
            EventRenderGui er = (EventRenderGui)event;
            ScaledResolution scaledRes = new ScaledResolution(mc, Nametags.mc.displayWidth, Nametags.mc.displayHeight);
            ArrayList<EntityPlayer> entList = new ArrayList<EntityPlayer>(entityPositions.keySet());
            entList.sort(Comparator.comparing(o -> TargetESP.isPriority(o) || FriendManager.isFriend(o.getName())));
            for (EntityPlayer ent : entList) {
                int backgroundColor = 0;
                boolean hovered;
                if (ent == Nametags.mc.thePlayer || !((Boolean)((Setting)this.settings.get("INVISIBLES")).getValue()).booleanValue() && ent.isInvisible()) continue;
                int dist = (int)Nametags.mc.thePlayer.getDistanceToEntity(ent);
                if (!(ent instanceof EntityPlayer)) continue;
                GlStateManager.pushMatrix();
                String str = ent.getDisplayName().getFormattedText();
                str = str.replace(ent.getDisplayName().getFormattedText(), FriendManager.isFriend(ent.getName()) ? "\u00a7b" + FriendManager.getAlias(ent.getName()) : "\u00a7f" + ent.getDisplayName().getFormattedText());
                double[] renderPositions = entityPositions.get(ent);
                if (renderPositions[3] < 0.0 || renderPositions[3] >= 1.0) {
                    GlStateManager.popMatrix();
                    continue;
                }
                TTFFontRenderer font = Client.nametagsFont;
                GlStateManager.translate(renderPositions[0] / (double)scaledRes.getScaleFactor(), renderPositions[1] / (double)scaledRes.getScaleFactor(), 0.0);
                this.scale();
                String healthInfo = (int)ent.getHealth() + "";
                GlStateManager.translate(0.0, -2.5, 0.0);
                int mouseY = er.getResolution().getScaledHeight() / 2;
                int mouseX = er.getResolution().getScaledWidth() / 2;
                double translateX = renderPositions[0] / (double)scaledRes.getScaleFactor();
                double translateY = renderPositions[1] / (double)scaledRes.getScaleFactor();
                boolean isPriority = Client.getModuleManager().isEnabled(TargetESP.class) && TargetESP.isPriority(ent) || FriendManager.isFriend(ent.getName());
                float percentage = (float)(1.0 - ((double)((float)Math.abs((double)mouseX - translateX)) + Math.abs((double)mouseY - translateY)) / (double)(mouseX + mouseY) * 3.0);
                if ((double)percentage < 0.2) {
                    percentage = 0.2f;
                }
                if (!((Boolean)((Setting)this.settings.get("OPACITY")).getValue()).booleanValue() || isPriority) {
                    percentage = 1.0f;
                }
                int n = FriendManager.isFriend(ent.getName()) ? Colors.getColor(50, 50, 125, 100) : (backgroundColor = isPriority ? Colors.getColor(55, 35, 0, 200) : Colors.getColor(0, (int)(130.0f * percentage)));
                int borderColor = FriendManager.isFriend(ent.getName()) ? Colors.getColor(100, 100, 255) : (isPriority ? Colors.getColor(255, 0, 0) : backgroundColor);
                float strWidth = font.getWidth(str);
                float strWidth2 = font.getWidth(healthInfo);
                RenderingUtil.rectangleBordered(-strWidth / 2.0f - 1.0f, -10.0, strWidth / 2.0f + 1.0f, 0.0, 0.5, backgroundColor, borderColor);
                int x3 = (int)(renderPositions[0] + (double)(-strWidth / 2.0f) - 3.0) / 2 - 26;
                int x4 = (int)(renderPositions[0] + (double)(strWidth / 2.0f) + 3.0) / 2 + 20;
                int y1 = (int)(renderPositions[1] + -30.0) / 2;
                int y2 = (int)(renderPositions[1] + 11.0) / 2;
                font.drawStringWithShadow(str, -strWidth / 2.0f, -9.5f, Colors.getColor(255, (int)(255.0f * percentage)));
                boolean healthOption = (Boolean)((Setting)this.settings.get("HEALTH")).getValue() == false;
                boolean armor = (Boolean)((Setting)this.settings.get("ARMOR")).getValue() == false;
                boolean bl = hovered = x3 < mouseX && mouseX < x4 && y1 < mouseY && mouseY < y2;
                if (!healthOption || hovered) {
                    float health = ent.getHealth();
                    float[] fractions = new float[]{0.0f, 0.5f, 1.0f};
                    Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
                    float progress = health / ent.getMaxHealth();
                    Color customColor = health >= 0.0f ? ESP2D.blendColors(fractions, colors, progress).brighter() : Color.RED;
                    try {
                        RenderingUtil.rectangleBordered(strWidth / 2.0f + 2.0f, -10.0, strWidth / 2.0f + 1.0f + strWidth2, 0.0, 0.5, backgroundColor, borderColor);
                        font.drawStringWithShadow(healthInfo, strWidth / 2.0f + 2.0f, -9.5f, Colors.getColor(customColor.getRed(), customColor.getGreen(), customColor.getBlue(), (int)(255.0f * percentage)));
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (((Boolean)this.distance.getValue()).booleanValue()) {
                    String distanceStr = (Boolean)this.distance.getValue() != false ? "[\u00a7a" + dist + "m\u00a7r] " : "";
                    float witdh = font.getWidth(distanceStr);
                    RenderingUtil.rectangleBordered(-strWidth / 2.0f - witdh, -10.0, -strWidth / 2.0f - 2.0f, 0.0, 0.5, backgroundColor, borderColor);
                    font.drawStringWithShadow(distanceStr, -strWidth / 2.0f - witdh + 1.0f, -9.5f, Colors.getColor(255, (int)(255.0f * percentage)));
                }
                if (hovered || !armor) {
                    ArrayList<ItemStack> itemsToRender = new ArrayList<ItemStack>();
                    for (int i = 0; i < 5; ++i) {
                        ItemStack stack = ent.getEquipmentInSlot(i);
                        if (stack == null) continue;
                        itemsToRender.add(stack);
                    }
                    int x = -(itemsToRender.size() * 9);
                    for (ItemStack stack : itemsToRender) {
                        RenderHelper.enableGUIStandardItemLighting();
                        mc.getRenderItem().remderItemIntoGUI(stack, x, -27);
                        mc.getRenderItem().renderItemOverlays(Nametags.mc.fontRendererObj, stack, x, -27);
                        x += 3;
                        RenderHelper.disableStandardItemLighting();
                        if (stack == null) continue;
                        int y = 21;
                        int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
                        int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
                        int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
                        if (sLevel > 0) {
                            Nametags.drawEnchantTag("Sh" + this.getColor(sLevel) + sLevel, x, y);
                            y -= 9;
                        }
                        if (fLevel > 0) {
                            Nametags.drawEnchantTag("Fir" + this.getColor(fLevel) + fLevel, x, y);
                            y -= 9;
                        }
                        if (kLevel > 0) {
                            Nametags.drawEnchantTag("Kb" + this.getColor(kLevel) + kLevel, x, y);
                        } else if (stack.getItem() instanceof ItemArmor) {
                            int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
                            int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
                            int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
                            if (pLevel > 0) {
                                Nametags.drawEnchantTag("P" + this.getColor(pLevel) + pLevel, x, y);
                                y -= 9;
                            }
                            if (tLevel > 0) {
                                Nametags.drawEnchantTag("Th" + this.getColor(tLevel) + tLevel, x, y);
                                y -= 9;
                            }
                            if (uLevel > 0) {
                                Nametags.drawEnchantTag("Unb" + this.getColor(uLevel) + uLevel, x, y);
                            }
                        } else if (stack.getItem() instanceof ItemBow) {
                            int powLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
                            int punLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
                            int fireLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
                            if (powLevel > 0) {
                                Nametags.drawEnchantTag("Pow" + this.getColor(powLevel) + powLevel, x, y);
                                y -= 9;
                            }
                            if (punLevel > 0) {
                                Nametags.drawEnchantTag("Pun" + this.getColor(punLevel) + punLevel, x, y);
                                y -= 9;
                            }
                            if (fireLevel > 0) {
                                Nametags.drawEnchantTag("Fir" + this.getColor(fireLevel) + fireLevel, x, y);
                            }
                        } else if (stack.getRarity() == EnumRarity.EPIC) {
                            Nametags.drawEnchantTag("\u00a76\u00a7lGod", x, y);
                        }
                        int var7 = (int)Math.round(255.0 - (double)stack.getItemDamage() * 255.0 / (double)stack.getMaxDamage());
                        int var10 = 255 - var7 << 16 | var7 << 8;
                        Color customColor = new Color(var10).brighter();
                        int x2 = (int)((double)x * 1.75);
                        if (stack.getMaxDamage() - stack.getItemDamage() > 0) {
                            GlStateManager.pushMatrix();
                            GlStateManager.disableDepth();
                            GL11.glScalef((float)0.57f, (float)0.57f, (float)0.57f);
                            font.drawStringWithShadow("" + (stack.getMaxDamage() - stack.getItemDamage()), x2, -54.0f, customColor.getRGB());
                            GlStateManager.enableDepth();
                            GlStateManager.popMatrix();
                        }
                        y = -53;
                        for (Object o2 : ent.getActivePotionEffects()) {
                            PotionEffect pot = (PotionEffect)o2;
                            String potName = StringUtils.capitalize((String)pot.getEffectName().substring(pot.getEffectName().lastIndexOf(".") + 1));
                            int XD = pot.getDuration() / 20;
                            SimpleDateFormat df = new SimpleDateFormat("m:ss");
                            String time = df.format(XD * 1000);
                            font.drawStringWithShadow(XD > 0 ? potName + " " + time : "", -30.0f, y, -1);
                            y -= 8;
                        }
                        x += 12;
                    }
                }
                GlStateManager.popMatrix();
            }
            entityPositions.clear();
        }
    }

    private String getColor(int level) {
        switch (level) {
            case 1: {
                return "\u00a7f";
            }
            case 2: {
                return "\u00a7a";
            }
            case 3: {
                return "\u00a73";
            }
            case 4: {
                return "\u00a74";
            }
            case 5: {
                return "\u00a76";
            }
        }
        return "\u00a7f";
    }

    private static void drawEnchantTag(String text, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        x = (int)((double)x * 1.75);
        GL11.glScalef((float)0.57f, (float)0.57f, (float)0.57f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, x, -30 - (y -= 4), Colors.getColor(255));
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    private void scale() {
        float scale = 1.0f;
        GlStateManager.scale(scale *= (float)(Nametags.mc.currentScreen == null && GameSettings.isKeyDown(Nametags.mc.gameSettings.ofKeyBindZoom) ? 2 : 1), scale, scale);
    }

    private void updatePositions() {
        entityPositions.clear();
        float pTicks = Nametags.mc.timer.renderPartialTicks;
        for (Object entity : Nametags.mc.theWorld.getLoadedEntityList()) {
            EntityPlayer ent;
            if (!(entity instanceof EntityPlayer) || (ent = (EntityPlayer)entity) == Nametags.mc.thePlayer || !((Boolean)((Setting)this.settings.get("INVISIBLES")).getValue()).booleanValue() && ent.isInvisible()) continue;
            double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - Nametags.mc.getRenderManager().viewerPosX;
            double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)pTicks - Nametags.mc.getRenderManager().viewerPosY;
            double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - Nametags.mc.getRenderManager().viewerPosZ;
            double[] convertedPoints = RenderingUtil.convertTo2D(x, y += (double)ent.height + 0.2, z);
            if (!(convertedPoints[2] >= 0.0) || !(convertedPoints[2] < 1.0)) continue;
            entityPositions.put(ent, new double[]{convertedPoints[0], convertedPoints[1], convertedPoints[1], convertedPoints[2]});
        }
    }
}

