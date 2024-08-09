package xyz.mathax.mathaxclient.systems.hud.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.renderer.Renderer2D;
import xyz.mathax.mathaxclient.renderer.text.Section;
import xyz.mathax.mathaxclient.renderer.text.TextRenderer;
import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.hud.HudElement;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.entity.EntityUtils;
import xyz.mathax.mathaxclient.utils.entity.SortPriority;
import xyz.mathax.mathaxclient.utils.entity.TargetUtils;
import xyz.mathax.mathaxclient.utils.misc.FakeClientPlayer;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.render.RenderUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.MathHelper;
import xyz.mathax.mathaxclient.settings.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombatInfoHudElement extends HudElement {
    private PlayerEntity playerEntity;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup pingSettings = settings.createGroup("Ping");
    private final SettingGroup distanceSettings = settings.createGroup("Distance");
    private final SettingGroup healthSettings = settings.createGroup("Health");

    // General

    private final Setting<Double> scaleSetting = generalSettings.add(new DoubleSetting.Builder()
        .name("Scale")
        .description("The scale.")
        .defaultValue(2)
        .min(1)
        .sliderRange(1, 5)
        .build()
    );

    private final Setting<Double> rangeSetting = generalSettings.add(new DoubleSetting.Builder()
        .name("Range")
        .description("The range to target players.")
        .defaultValue(100)
        .min(1)
        .sliderRange(0, 200)
        .build()
    );

    private final Setting<List<Enchantment>> displayedEnchantmentsSetting = generalSettings.add(new EnchantmentListSetting.Builder()
            .name("Displayed enchantments")
            .description("The enchantments that are shown on nametags.")
            .defaultValue(getDefaultEnchantments())
            .build()
    );

    private final Setting<SettingColor> enchantmentTextColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Enchantment color")
            .description("Color of enchantment text.")
            .defaultValue(new SettingColor(255, 255, 255))
            .build()
    );

    private final Setting<SettingColor> backgroundColorSetting = generalSettings.add(new ColorSetting.Builder()
        .name("Background color")
        .description("Color of background.")
        .defaultValue(new SettingColor(Color.BLACK, 64))
        .build()
    );

    // Ping

    private final Setting<Boolean> displayPingSetting = pingSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Show the player's ping.")
            .defaultValue(true)
            .build()
    );

    private final Setting<SettingColor> pingColor1Setting = pingSettings.add(new ColorSetting.Builder()
            .name("Ping stage 1")
            .description("Color of ping text when under 75.")
            .defaultValue(new SettingColor(15, 255, 15))
            .visible(displayPingSetting::get)
            .build()
    );

    private final Setting<SettingColor> pingColor2Setting = pingSettings.add(new ColorSetting.Builder()
        .name("Ping stage 2")
        .description("Color of ping text when between 75 and 200.")
        .defaultValue(new SettingColor(255, 150, 15))
        .visible(displayPingSetting::get)
        .build()
    );

    private final Setting<SettingColor> pingColor3Setting = pingSettings.add(new ColorSetting.Builder()
        .name("Ping stage 3")
        .description("Color of ping text when over 200.")
        .defaultValue(new SettingColor(255, 15, 15))
        .visible(displayPingSetting::get)
        .build()
    );

    // Distance

    private final Setting<Boolean> displayDistanceSetting = distanceSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Show the distance between you and the player.")
            .defaultValue(true)
            .build()
    );

    private final Setting<SettingColor> distColor1Setting = distanceSettings.add(new ColorSetting.Builder()
        .name("Distance stage 1")
        .description("The color when a player is within 10 blocks of you.")
        .defaultValue(new SettingColor(255, 15, 15))
        .visible(displayDistanceSetting::get)
        .build()
    );

    private final Setting<SettingColor> distColor2Setting = distanceSettings.add(new ColorSetting.Builder()
        .name("Distance stage 2")
        .description("The color when a player is within 50 blocks of you.")
        .defaultValue(new SettingColor(255, 150, 15))
        .visible(displayDistanceSetting::get)
        .build()
    );

    private final Setting<SettingColor> distColor3Setting = distanceSettings.add(new ColorSetting.Builder()
        .name("Distance stage 3")
        .description("The color when a player is greater then 50 blocks away from you.")
        .defaultValue(new SettingColor(15, 255, 15))
        .visible(displayDistanceSetting::get)
        .build()
    );

    // Health

    private final Setting<SettingColor> healthColor1Setting = healthSettings.add(new ColorSetting.Builder()
        .name("Health stage 1")
        .description("The color on the left of the health gradient.")
        .defaultValue(new SettingColor(255, 15, 15))
        .build()
    );

    private final Setting<SettingColor> healthColor2Setting = healthSettings.add(new ColorSetting.Builder()
        .name("Health stage 2")
        .description("The color in the middle of the health gradient.")
        .defaultValue(new SettingColor(255, 150, 15))
        .build()
    );

    private final Setting<SettingColor> healthColor3Setting = healthSettings.add(new ColorSetting.Builder()
        .name("Health stage 3")
        .description("The color on the right of the health gradient.")
        .defaultValue(new SettingColor(15, 255, 15))
        .build()
    );

    public CombatInfoHudElement(Hud hud) {
        super(hud, "Combat Info", "Displays information about your combat target.");
    }

    @Override
    public void update(OverlayRenderer renderer) {
        box.setSize(175 * scaleSetting.get(), 95 * scaleSetting.get());
    }

    @Override
    public void render(OverlayRenderer renderer) {
        renderer.addPostTask(() -> {
            double x = box.getX();
            double y = box.getY();

            if (isInEditor()) {
                playerEntity = FakeClientPlayer.getPlayer();
            } else {
                playerEntity = TargetUtils.getPlayerTarget(rangeSetting.get(), SortPriority.Lowest_Distance);
            }

            if (playerEntity == null) {
                return;
            }

            // Background
            Renderer2D.COLOR.begin();
            Renderer2D.COLOR.quad(x, y, box.width, box.height, backgroundColorSetting.get());
            Renderer2D.COLOR.render(null);

            // Player Model
            InventoryScreen.drawEntity((int) (x + (25 * scaleSetting.get())), (int) (y + (66 * scaleSetting.get())), (int) (30 * scaleSetting.get()), -MathHelper.wrapDegrees(playerEntity.prevYaw + (playerEntity.getYaw() - playerEntity.prevYaw) * mc.getTickDelta()), -playerEntity.getPitch(), playerEntity);

            // Moving pos to past player model
            x += 50 * scaleSetting.get();
            y += 5 * scaleSetting.get();

            // Setting up texts
            String breakText = " | ";

            // Name
            String nameText = playerEntity.getEntityName();
            Color nameColor = PlayerUtils.getPlayerColor(playerEntity, hud.primaryColorSetting.get());

            // Ping
            int ping = EntityUtils.getPing(playerEntity);
            String pingText = ping + "ms";

            Color pingColor;
            if (ping <= 75) {
                pingColor = pingColor1Setting.get();
            } else if (ping <= 200) {
                pingColor = pingColor2Setting.get();
            } else {
                pingColor = pingColor3Setting.get();
            }

            // Distance
            double distance = 0;
            if (!isInEditor()) {
                distance = Math.round(mc.player.distanceTo(playerEntity) * 100.0) / 100.0;
            }

            String distText = distance + "m";

            Color distanceColor;
            if (distance <= 10) {
                distanceColor = distColor1Setting.get();
            } else if (distance <= 50) {
                distanceColor = distColor2Setting.get();
            } else {
                distanceColor = distColor3Setting.get();
            }

            // Status Text
            String statusText = "Unknown";
            Color statusColor = hud.primaryColorSetting.get();

            if (Friends.get().contains(playerEntity)) {
                statusText = "Friend";
                statusColor = Friends.get().colorSetting.get();
            } else if (Enemies.get().contains(playerEntity)) {
                statusText = "Enemy";
                statusColor = Enemies.get().colorSetting.get();
            } else {
                boolean naked = true;
                for (int position = 3; position >= 0; position--) {
                    ItemStack itemStack = getItem(position);
                    if (!itemStack.isEmpty()) {
                        naked = false;
                    }
                }

                if (naked) {
                    statusText = "Naked";
                    statusColor = Color.GREEN;
                } else {
                    boolean threat = false;
                    for (int position = 5; position >= 0; position--) {
                        ItemStack itemStack = getItem(position);
                        if (itemStack.getItem() instanceof SwordItem || itemStack.getItem() == Items.END_CRYSTAL || itemStack.getItem() == Items.RESPAWN_ANCHOR || itemStack.getItem() instanceof BedItem) {
                            threat = true;
                        }
                    }

                    if (threat) {
                        statusText = "Threat";
                        statusColor = Color.RED;
                    }
                }
            }

            TextRenderer.get().begin(0.45 * scaleSetting.get(), false, true);

            double breakWidth = TextRenderer.get().getWidth(breakText);
            double pingWidth = TextRenderer.get().getWidth(pingText);
            double statusWidth = TextRenderer.get().getWidth(statusText);

            TextRenderer.get().render(nameText, x, y, nameColor != null ? nameColor : hud.primaryColorSetting.get());

            y += TextRenderer.get().getHeight();

            TextRenderer.get().render(statusText, x, y, statusColor);

            if (displayPingSetting.get()) {
                TextRenderer.get().render(breakText, x + statusWidth, y, hud.secondaryColorSetting.get());
                TextRenderer.get().render(pingText, x + statusWidth + breakWidth, y, pingColor);

                if (displayDistanceSetting.get()) {
                    TextRenderer.get().render(breakText, x + statusWidth + breakWidth + pingWidth, y, hud.secondaryColorSetting.get());
                    TextRenderer.get().render(distText, x + statusWidth + breakWidth + pingWidth + breakWidth, y, distanceColor);
                }
            } else if (displayDistanceSetting.get()) {
                TextRenderer.get().render(breakText, x + statusWidth, y, hud.secondaryColorSetting.get());
                TextRenderer.get().render(distText, x + statusWidth + breakWidth, y, distanceColor);
            }

            TextRenderer.get().end();

            // Moving pos down for armor
            y += 10 * scaleSetting.get();

            double armorX;
            double armorY;
            int slot = 5;

            // Drawing armor
            MatrixStack matrixStack = RenderSystem.getModelViewStack();
            matrixStack.push();
            matrixStack.scale(scaleSetting.get().floatValue(), scaleSetting.get().floatValue(), 1);

            x /= scaleSetting.get();
            y /= scaleSetting.get();

            TextRenderer.get().begin(0.35, false, true);

            for (int position = 0; position < 6; position++) {
                armorX = x + position * 20;
                armorY = y;

                ItemStack itemStack = getItem(slot);

                RenderUtils.drawItem(itemStack, (int) armorX, (int) armorY, true);

                armorY += 18;

                Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(itemStack);
                Map<Enchantment, Integer> enchantmentsToShow = new HashMap<>();

                for (Enchantment enchantment : displayedEnchantmentsSetting.get()) {
                    if (enchantments.containsKey(enchantment)) {
                        enchantmentsToShow.put(enchantment, enchantments.get(enchantment));
                    }
                }

                for (Enchantment enchantment : enchantmentsToShow.keySet()) {
                    String enchantName = Utils.getEnchantSimpleName(enchantment, 3) + " " + enchantmentsToShow.get(enchantment);

                    double enchX = (armorX + 8) - (TextRenderer.get().getWidth(enchantName) / 2);

                    TextRenderer.get().render(enchantName, enchX, armorY, enchantment.isCursed() ? Color.RED : enchantmentTextColorSetting.get());
                    armorY += TextRenderer.get().getHeight();
                }

                slot--;
            }

            TextRenderer.get().end();

            y = (int) (box.getY() + 75 * scaleSetting.get());
            x = box.getX();

            // Health bar

            x /= scaleSetting.get();
            y /= scaleSetting.get();

            x += 5;
            y += 5;

            Renderer2D.COLOR.begin();
            Renderer2D.COLOR.boxLines(x, y, 165, 11, Color.BLACK);
            Renderer2D.COLOR.render(null);

            x += 2;
            y += 2;

            float maxHealth = playerEntity.getMaxHealth();
            int maxAbsorb = 16;
            int maxTotal = (int) (maxHealth + maxAbsorb);

            int totalHealthWidth = (int) (161 * maxHealth / maxTotal);
            int totalAbsorbWidth = 161 * maxAbsorb / maxTotal;

            float health = playerEntity.getHealth();
            float absorb = playerEntity.getAbsorptionAmount();

            double healthPercent = health / maxHealth;
            double absorbPercent = absorb / maxAbsorb;

            int healthWidth = (int) (totalHealthWidth * healthPercent);
            int absorbWidth = (int) (totalAbsorbWidth * absorbPercent);

            Renderer2D.COLOR.begin();
            Renderer2D.COLOR.quad(x, y, healthWidth, 7, healthColor1Setting.get(), healthColor2Setting.get(), healthColor2Setting.get(), healthColor1Setting.get());
            Renderer2D.COLOR.quad(x + healthWidth, y, absorbWidth, 7, healthColor2Setting.get(), healthColor3Setting.get(), healthColor3Setting.get(), healthColor2Setting.get());
            Renderer2D.COLOR.render(null);

            matrixStack.pop();
        });
    }

    private ItemStack getItem(int i) {
        if (isInEditor()) {
            return switch (i) {
                case 0 -> Items.END_CRYSTAL.getDefaultStack();
                case 1 -> Items.NETHERITE_BOOTS.getDefaultStack();
                case 2 -> Items.NETHERITE_LEGGINGS.getDefaultStack();
                case 3 -> Items.NETHERITE_CHESTPLATE.getDefaultStack();
                case 4 -> Items.NETHERITE_HELMET.getDefaultStack();
                case 5 -> Items.TOTEM_OF_UNDYING.getDefaultStack();
                default -> ItemStack.EMPTY;
            };
        }

        if (playerEntity == null) {
            return ItemStack.EMPTY;
        }

        return switch (i) {
            case 4 -> playerEntity.getOffHandStack();
            case 5 -> playerEntity.getMainHandStack();
            default -> playerEntity.getInventory().getArmorStack(i);
        };
    }

    public static List<Enchantment> getDefaultEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        for (Enchantment enchantment : Registries.ENCHANTMENT) {
            enchantments.add(enchantment);
        }

        return enchantments;
    }
}
