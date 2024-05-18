package pw.latematt.xiv.mod.mods.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.NametagRenderEvent;
import pw.latematt.xiv.event.events.Render3DEvent;
import pw.latematt.xiv.event.events.RenderStringEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.utils.RenderUtils;
import pw.latematt.xiv.value.Value;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthew
 */
public class Nametags extends Mod implements CommandHandler {
    private final Listener nametagRenderListener, render3DListener;
    private final Value<Boolean> health = new Value<>("nametags_health", true);
    private final Value<Boolean> armor = new Value<>("nametags_armor", false);
    private final DecimalFormat decimalFormat = new DecimalFormat("#.#");

    public Nametags() {
        super("Nametags", ModType.RENDER);
        Command.newCommand().cmd("nametags").description("Base command for the Nametags mod.").arguments("<action>").aliases("tags", "nt").handler(this).build();

        nametagRenderListener = new Listener<NametagRenderEvent>() {
            @Override
            public void onEventCalled(NametagRenderEvent event) {
                if (event.getEntity() instanceof EntityPlayer) {
                    event.setCancelled(true);
                }
            }
        };

        render3DListener = new Listener<Render3DEvent>() {
            @Override
            public void onEventCalled(Render3DEvent event) {
                if (!Minecraft.isGuiEnabled())
                    return;
                for (EntityPlayer player : mc.theWorld.playerEntities) {
                    if (!isValidEntity(player))
                        continue;

                    float partialTicks = event.getPartialTicks();
                    double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks - mc.getRenderManager().renderPosX;
                    double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks - mc.getRenderManager().renderPosY;
                    double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks - mc.getRenderManager().renderPosZ;

                    drawNametags(player, x, y, z);
                }
            }
        };
        setEnabled(true);
    }

    private boolean isValidEntity(Entity entity) {
        return entity != null && entity != mc.thePlayer && entity.isEntityAlive();
    }

    public void drawNametags(EntityLivingBase entity, double x, double y, double z) {
        String entityName = entity.getDisplayName().getFormattedText();

        if (XIV.getInstance().getFriendManager().isFriend(entity.getName()) || XIV.getInstance().getAdminManager().isAdmin(entity.getName())) {
            RenderStringEvent event = new RenderStringEvent(entityName, RenderStringEvent.State.NAMETAG);
            XIV.getInstance().getListenerManager().call(event);
            entityName = event.getString();
        }

        if (getNametagColor(entity) != 0xFFFFFFFF)
            entityName = StringUtils.stripControlCodes(entityName);

        if ((entity instanceof EntityPlayer) && ((EntityPlayer) entity).capabilities.isFlying)
            entityName = "\247a[F] \247r" + entityName;

        if ((entity instanceof EntityPlayer) && ((EntityPlayer) entity).capabilities.isCreativeMode)
            entityName = "\247a[C] \247r" + entityName;

        if (entity.getDistanceToEntity(mc.thePlayer) >= 64) {
            entityName = "\2472* \247r" + entityName;
        }

        double health = entity.getHealth() / 2;
        double maxHealth = entity.getMaxHealth() / 2;
        double percentage = 100 * (health / maxHealth);
        String healthColor;
        if (percentage > 75)
            healthColor = "2";
        else if (percentage > 50)
            healthColor = "e";
        else if (percentage > 25)
            healthColor = "6";
        else
            healthColor = "4";

        String healthDisplay = decimalFormat.format(Math.floor((health + (double)0.5F / 2) / 0.5F) * 0.5F);
        String maxHealthDisplay = decimalFormat.format(Math.floor((entity.getMaxHealth() + (double)0.5F / 2) / 0.5F) * 0.5F);

        if (this.health.getValue())
            entityName = String.format("%s \247%s%s", entityName, healthColor, healthDisplay);

        float distance = mc.thePlayer.getDistanceToEntity(entity);
        float var13 = (distance / 5 <= 2 ? 2.0F : distance / 5) * RenderUtils.getNametagSize().getValue();
        float var14 = 0.016666668F * var13;
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(x + 0.0F, y + entity.height + 0.5F, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        if (mc.gameSettings.thirdPersonView == 2) {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, -1.0F, 0.0F, 0.0F);
        } else {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        }
        GlStateManager.scale(-var14, -var14, var14);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        int var17 = 0;
        if (entity.isSneaking()) {
            var17 += 4;
        }

        var17 -= distance / 5;
        if (var17 < -8) {
            var17 = -8;
        }

        GlStateManager.func_179090_x();
        worldRenderer.startDrawingQuads();
        int var18 = mc.fontRendererObj.getStringWidth(entityName) / 2;
        worldRenderer.func_178960_a(0.0F, 0.0F, 0.0F, RenderUtils.getNametagOpacity().getValue());
        worldRenderer.addVertex(-var18 - 2, -2 + var17, 0.0D);
        worldRenderer.addVertex(-var18 - 2, 9 + var17, 0.0D);
        worldRenderer.addVertex(var18 + 2, 9 + var17, 0.0D);
        worldRenderer.addVertex(var18 + 2, -2 + var17, 0.0D);
        tessellator.draw();
        GlStateManager.func_179098_w();

        mc.fontRendererObj.func_175065_a(entityName, -var18, var17, getNametagColor(entity), true);
        if (armor.getValue() && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            List<ItemStack> items = new ArrayList<>();
            if (player.getCurrentEquippedItem() != null) {
                items.add(player.getCurrentEquippedItem());
            }

            for (int index = 3; index >= 0; index--) {
                ItemStack stack = player.inventory.armorInventory[index];
                if (stack != null) {
                    items.add(stack);
                }
            }

            int offset = var18 - (items.size() - 1) * 9 - 9;
            int xPos = 0;
            for (ItemStack stack : items) {
                GlStateManager.pushMatrix();
                RenderHelper.enableStandardItemLighting();
                mc.getRenderItem().zLevel = -100.0F;
                mc.getRenderItem().renderItemAboveHead(stack, -var18 + offset + xPos, var17 - 20);
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, stack, -var18 + offset + xPos, var17 - 20);
                mc.getRenderItem().zLevel = 0.0F;
                RenderHelper.disableStandardItemLighting();
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                GlStateManager.disableLighting();
                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GlStateManager.disableDepth();
                GlStateManager.scale(0.50F, 0.50F, 0.50F);
                if (stack.getItem() == Items.golden_apple && stack.hasEffect()) {
                    mc.fontRendererObj.drawStringWithShadow("god", (-var18 + offset + xPos) * 2, (var17 - 20) * 2, 0xFFFF0000);
                } else {
                    NBTTagList enchants = stack.getEnchantmentTagList();

                    if (enchants != null) {
                        int encY = 0;
                        Enchantment[] important = new Enchantment[]{Enchantment.PROTECTION, Enchantment.UNBREAKING, Enchantment.SHARPNESS, Enchantment.FIRE_ASPECT, Enchantment.EFFICIENCY, Enchantment.FEATHER_FALLING, Enchantment.POWER, Enchantment.FLAME, Enchantment.PUNCH, Enchantment.FORTUNE, Enchantment.INFINITY, Enchantment.THORNS};
                        if (enchants.tagCount() >= 6) {
                            mc.fontRendererObj.drawStringWithShadow("god", (-var18 + offset + xPos) * 2, (var17 - 20) * 2, 0xFFFF0000);
                        } else {
                            for (int index = 0; index < enchants.tagCount(); ++index) {
                                short id = enchants.getCompoundTagAt(index).getShort("id");
                                short level = enchants.getCompoundTagAt(index).getShort("lvl");
                                Enchantment enc = Enchantment.func_180306_c(id);
                                if (enc != null) {
                                    for (Enchantment importantEnchantment : important) {
                                        if (enc == importantEnchantment) {
                                            String encName = enc.getTranslatedName(level).substring(0, 1).toLowerCase();
                                            if (level > 99)
                                                encName = encName + "99+";
                                            else
                                                encName = encName + level;
                                            mc.fontRendererObj.drawStringWithShadow(encName, (-var18 + offset + xPos) * 2, (var17 - 20 + encY) * 2, 0xFFAAAAAA);
                                            encY += 5;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
                xPos += 18;
            }
        }
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public int getNametagColor(EntityLivingBase entity) {
        int color = 0xFFFFFFFF;
        if (entity instanceof EntityPlayer && XIV.getInstance().getFriendManager().isFriend(entity.getName())) {
            color = 0xFF4DB3FF;
        } else if (entity instanceof EntityPlayer && XIV.getInstance().getAdminManager().isAdmin(entity.getName())) {
            color = 0xFFFF00FF;
        } else if (entity.isInvisibleToPlayer(mc.thePlayer)) {
            color = 0xFFFFE600;
        } else if (entity.isSneaking()) {
            color = 0xFFFF0000;
        }
        return color;
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "health":
                    if (arguments.length >= 3) {
                        health.setValue(Boolean.parseBoolean(arguments[2]));
                    } else {
                        health.setValue(!health.getValue());
                    }
                    ChatLogger.print(String.format("Nametags will %s show health.", health.getValue() ? "now" : "no longer"));
                    break;
                case "armor":
                    if (arguments.length >= 3) {
                        armor.setValue(Boolean.parseBoolean(arguments[2]));
                    } else {
                        armor.setValue(!armor.getValue());
                    }
                    ChatLogger.print(String.format("Nametags will %s show armor.", armor.getValue() ? "now" : "no longer"));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: health, armor");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: nametags <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(nametagRenderListener, render3DListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(nametagRenderListener, render3DListener);
    }
}
