package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraft.util.AxisAlignedBB;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;
import wtf.diablo.client.font.FontRepository;
import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@ModuleMetaData(name = "Reveal", description = "Reveals what a player has in their inventory through walls", category = ModuleCategoryEnum.RENDER)
public final class RevealModule extends AbstractModule {
    public RevealModule() {
    }

    @EventHandler
    private Listener<OverlayEvent> overlayListener = e -> {
        double playerX = mc.thePlayer.posX;
        double playerY = mc.thePlayer.posY + mc.thePlayer.getEyeHeight(); // Adjust for player's eye level
        double playerZ = mc.thePlayer.posZ;

        float pitch = mc.thePlayer.rotationPitch;
        float yaw = mc.thePlayer.rotationYaw;

        double dirX = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        double dirY = -Math.sin(Math.toRadians(pitch));
        double dirZ = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));

        double maxDistance = mc.gameSettings.renderDistanceChunks * 16;
        double stepSize = 0.1;

        for (double t = 0; t < maxDistance; t += stepSize) {
            double hitX = playerX + dirX * t;
            double hitY = playerY + dirY * t;
            double hitZ = playerZ + dirZ * t;

            AxisAlignedBB boundingBox = new AxisAlignedBB(hitX, hitY, hitZ, hitX + 1, hitY + 1, hitZ + 1);
            List<Entity> entitiesAtPosition = mc.theWorld.getEntitiesWithinAABB(Entity.class, boundingBox);

            for (Entity entity : entitiesAtPosition) {
                if (entity != mc.thePlayer ) {
                    if (entity instanceof EntityPlayer) {
                        if (entity.isInvisible())
                            continue;

                        draw((EntityPlayer) entity, e.getScaledResolution());
                        return;
                    }
                    break;
                }
            }
        }
    };

    private final void draw(final EntityPlayer entity, final ScaledResolution scaledResolution) {
        final int width = 242;
        final int height = 70;

        final int x = scaledResolution.getScaledWidth() / 2 - width / 2;
        final int y = 40;

        RenderUtil.drawRect(x, y, width, 10, Color.BLACK.getRGB());

        RenderUtil.drawRect(x, y + height - 10 , width, 10, Color.BLACK.getRGB());


        final TTFFontRenderer font = FontRepository.SLKSCR16;

        final String entityName = entity.getName();
        font.drawStringWithOutline(entityName, x + (width / 2.0) - (font.getWidth(entityName) / 2), y + 2, Color.WHITE.getRGB(), Color.BLACK.getRGB(), 2);

        final InventoryPlayer inventory = entity.inventory;

        final Color color = new Color(134, 134, 134, 119);

        int baseX = x;
        for (int i = 0; i < 5; i++) {
            final int size = 46;

            final int x1 = baseX + 2 + (i * size);
            final int y1 = y + 12;

            baseX += 2;

            GlStateManager.pushMatrix();
            RenderUtil.drawRect(x1, y1, size, size, color.getRGB());
            GlStateManager.popMatrix();

            ItemStack itemStack = null;
            if (i == 0) {
                itemStack = inventory.getCurrentItem();
            } else {
                itemStack = inventory.armorInventory[3 - (i - 1)];
            }

            if (itemStack != null) {
                GlStateManager.pushMatrix();
                GlStateManager.scale(2.5, 2.5, 2.5);
                RenderUtil.renderItemStack(itemStack, (int) ((x1 + 4) / 2.5), (int) ((y1 + 4) / 2.5));
                GlStateManager.scale(1, 1, 1);
                GlStateManager.popMatrix();

                final Item item = itemStack.getItem();

                int offset = 10;
                for (final ItemEnchantments itemEnchantment : ItemEnchantments.values()) {
                    final Class<?> target = itemEnchantment.getTarget();

                    if (target.isInstance(item)) {
                        for (final Enchantment enchantment : itemEnchantment.getEnchantments()) {
                            final int level = EnchantmentHelper.getEnchantmentLevel(enchantment.effectId, itemStack);

                            if (level > 0) {
                                mc.fontRendererObj.drawString(String.valueOf(enchantment.getTranslatedName(level).substring(0,1).toLowerCase(Locale.ROOT) + level), x1 + size - 14, y1 + size - offset, -1);

                                offset += mc.fontRendererObj.FONT_HEIGHT;
                            }
                        }
                    }
                }
            }
        }
    }

    public enum ItemEnchantments {
        WEAPON(ItemSword.class, Enchantment.sharpness, Enchantment.knockback, Enchantment.fireAspect, Enchantment.unbreaking),
        ARMOR(ItemArmor.class, Enchantment.protection, Enchantment.thorns, Enchantment.unbreaking),
        BOW(ItemBow.class, Enchantment.power, Enchantment.punch, Enchantment.sharpness, Enchantment.unbreaking);

        private final List<Enchantment> enchantments;
        private final Class<? extends Item> target;

        ItemEnchantments(final Class<? extends Item> target, final Enchantment... enchantments) {
            this.target = target;
            this.enchantments = new ArrayList<>();
            this.enchantments.addAll(Arrays.asList(enchantments));
        }

        public Class<? extends Item> getTarget() {
            return target;
        }

        public List<Enchantment> getEnchantments() {
            return enchantments;
        }

        public static ItemEnchantments get(final Class<? extends Item> target) {
            for (final ItemEnchantments itemEnchantment : values()) {
                if (itemEnchantment.getTarget() == target) {
                    return itemEnchantment;
                }
            }
            return null;
        }
    }
}
