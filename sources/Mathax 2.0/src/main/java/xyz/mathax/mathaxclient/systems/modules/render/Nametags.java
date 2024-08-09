package xyz.mathax.mathaxclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render2DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.GL;
import xyz.mathax.mathaxclient.renderer.Renderer2D;
import xyz.mathax.mathaxclient.renderer.text.Section;
import xyz.mathax.mathaxclient.renderer.text.TextRenderer;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.misc.NameProtect;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.entity.EntityUtils;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.player.ArmorUtils;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.player.TotemPopUtils;
import xyz.mathax.mathaxclient.utils.render.NametagUtils;
import xyz.mathax.mathaxclient.utils.render.RenderUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import org.joml.Vector3d;

import java.util.*;

public class Nametags extends Module {
    private static final Identifier MATHAX_ICON = new MatHaxIdentifier("icons/64.png");
    private static final Identifier MATHAX_DEVELOPER_ICON = new MatHaxIdentifier("icons/developer_64.png");

    private final Map<Enchantment, Integer> enchantmentsToShowScale = new HashMap<>();

    private final List<Entity> entityList = new ArrayList<>();

    private final Vector3d pos = new Vector3d();

    private final double[] itemWidths = new double[6];

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup playersSettings = settings.createGroup("Players");
    private final SettingGroup itemsSettings = settings.createGroup("Items");

    // General

    private final Setting<Object2BooleanMap<EntityType<?>>> entitiesSetting = generalSettings.add(new EntityTypeListSetting.Builder()
            .name("Entities")
            .description("Select entities to draw nametags on.")
            .defaultValue(
                    EntityType.PLAYER,
                    EntityType.ITEM,
                    EntityType.TNT
            )
            .build()
    );

    private final Setting<Double> scaleSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Scale")
            .description("The scale of the nametag.")
            .defaultValue(1.5)
            .min(0.1)
            .sliderRange(0.1, 2)
            .build()
    );

    private final Setting<SettingColor> nameColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Color")
            .description("The color of the nametag names.")
            .defaultValue(new SettingColor(Color.MATHAX))
            .build()
    );

    private final Setting<Boolean> selfSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Self")
            .description("Displays a nametag on you.")
            .defaultValue(true)
            .build()
    );

    private final Setting<SettingColor> selfColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Self color")
            .description("The color of your nametag.")
            .defaultValue(new SettingColor(0, 165, 255))
            .visible(selfSetting::get)
            .build()
    );

    private final Setting<Boolean> ignoreFriendsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Ignore friends")
            .description("Stop nametag rendering for friends.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> ignoreEnemiesSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Ignore enemies")
            .description("Stop nametag rendering for enemies.")
            .defaultValue(false)
            .build()
    );

    private final Setting<SettingColor> backgroundSetting = generalSettings.add(new ColorSetting.Builder()
            .name("background-color")
            .description("The color of the nametag background.")
            .defaultValue(new SettingColor(0, 0, 0, 75))
            .build()
    );

    private final Setting<Boolean> cullingSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Culling")
            .description("Only render a certain number of nametags at a certain distance.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Double> maxCullRangeSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Culling range")
            .description("Only render nametags within this distance of you.")
            .defaultValue(50)
            .min(0)
            .sliderRange(0, 200)
            .visible(cullingSetting::get)
            .build()
    );

    private final Setting<Integer> maxCullingCountSetting = generalSettings.add(new IntSetting.Builder()
            .name("Culling count")
            .description("Only render this many nametags.")
            .defaultValue(50)
            .min(1)
            .sliderRange(1, 100)
            .visible(cullingSetting::get)
            .build()
    );

    private final Setting<Boolean> excludeBots = generalSettings.add(new BoolSetting.Builder()
            .name("Exclude bots")
            .description("Only render non-bot nametags.")
            .defaultValue(true)
            .build()
    );

    // Players

    private final Setting<Boolean> displayItemsSetting = playersSettings.add(new BoolSetting.Builder()
            .name("Show items")
            .description("Display armor and hand items above the name tags.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Double> itemSpacingSetting = playersSettings.add(new DoubleSetting.Builder()
            .name("item-spacing")
            .description("The spacing between items.")
            .defaultValue(2)
            .range(0, 10)
            .sliderRange(0, 5)
            .visible(displayItemsSetting::get)
            .build()
    );

    private final Setting<Boolean> ignoreEmptySetting = playersSettings.add(new BoolSetting.Builder()
            .name("Ignore empty slots")
            .description("Does not add spacing where an empty item stack would be.")
            .defaultValue(true)
            .visible(displayItemsSetting::get)
            .build()
    );

    private final Setting<Boolean> displayItemEnchantsSetting = playersSettings.add(new BoolSetting.Builder()
            .name("Display enchants")
            .description("Display item enchantments on the items.")
            .defaultValue(true)
            .visible(displayItemsSetting::get)
            .build()
    );

    private final Setting<Position> enchantPositionSetting = playersSettings.add(new EnumSetting.Builder<Position>()
            .name("Enchantment position")
            .description("Where the enchantments are rendered.")
            .defaultValue(Position.Above)
            .visible(displayItemEnchantsSetting::get)
            .build()
    );

    private final Setting<Integer> enchantLengthSetting = playersSettings.add(new IntSetting.Builder()
            .name("Enchant name length")
            .description("The length enchantment names are trimmed to.")
            .defaultValue(3)
            .range(1, 5)
            .sliderRange(1, 5)
            .visible(displayItemEnchantsSetting::get)
            .build()
    );

    private final Setting<List<Enchantment>> ignoredEnchantmentsSetting = playersSettings.add(new EnchantmentListSetting.Builder()
            .name("Ignored enchantments")
            .description("The enchantments that aren't shown on nametags.")
            .visible(displayItemEnchantsSetting::get)
            .build()
    );

    private final Setting<Double> enchantTextScaleSetting = playersSettings.add(new DoubleSetting.Builder()
            .name("Enchant text scale")
            .description("The scale of the enchantment text.")
            .defaultValue(1)
            .range(0.1, 2)
            .sliderRange(0.1, 2)
            .visible(displayItemEnchantsSetting::get)
            .build()
    );

    private final Setting<Boolean> displayGameModeSetting = playersSettings.add(new BoolSetting.Builder()
            .name("Gamemode")
            .description("Show the player's game mode.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> displayPingSetting = playersSettings.add(new BoolSetting.Builder()
            .name("Ping")
            .description("Show the player's ping.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> displayDistanceSetting = playersSettings.add(new BoolSetting.Builder()
            .name("Distance")
            .description("Show the distance between you and the player.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> displayTotemPopsSetting = playersSettings.add(new BoolSetting.Builder()
            .name("Totem pops")
            .description("Show the player's totem pops.")
            .defaultValue(true)
            .build()
    );

    // Items

    private final Setting<Boolean> itemCountSetting = itemsSettings.add(new BoolSetting.Builder()
            .name("Show count")
            .description("Display the number of items in the stack.")
            .defaultValue(true)
            .build()
    );

    public Nametags(Category category) {
        super(category, "Nametags", "Displays customizable nametags above players.");
    }

    private static String ticksToTime(int ticks) {
        if (ticks > 20 * 3600) {
            int h = ticks / 20 / 3600;
            return h + " h";
        } else if (ticks > 20 * 60) {
            int m = ticks / 20 / 60;
            return m + " m";
        } else {
            int s = ticks / 20;
            int ms = (ticks % 20) / 2;
            return s + "." + ms + " s";
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        entityList.clear();

        Vec3d cameraPos = mc.gameRenderer.getCamera().getPos();
        for (Entity entity : mc.world.getEntities()) {
            EntityType<?> type = entity.getType();
            if (!entitiesSetting.get().containsKey(type)) {
                continue;
            }

            if (type == EntityType.PLAYER) {
                if (!selfSetting.get() && entity == mc.player) {
                    continue;
                }

                if (EntityUtils.getGameMode((PlayerEntity) entity) == null && excludeBots.get()) {
                    continue;
                }
            }

            if (!cullingSetting.get() || entity.getPos().distanceTo(cameraPos) < maxCullRangeSetting.get()) {
                entityList.add(entity);
            }
        }

        entityList.sort(Comparator.comparing(e -> e.squaredDistanceTo(cameraPos)));
    }

    @EventHandler
    private void onRender2D(Render2DEvent event) {
        int count = getRenderCount();
        for (int i = count - 1; i > -1; i--) {
            Entity entity = entityList.get(i);
            Utils.set(pos, entity, event.tickDelta);
            pos.add(0, getHeight(entity), 0);

            EntityType<?> type = entity.getType();
            if (NametagUtils.to2D(pos, scaleSetting.get())) {
                if (type == EntityType.PLAYER) {
                    renderNametagPlayer((PlayerEntity) entity);
                } else if (type == EntityType.ITEM) {
                    renderNametagItem(((ItemEntity) entity).getStack());
                } else if (type == EntityType.ITEM_FRAME) {
                    renderNametagItem(((ItemFrameEntity) entity).getHeldItemStack());
                } else if (type == EntityType.TNT) {
                    renderTntNametag((TntEntity) entity);
                } else if (entity instanceof LivingEntity) {
                    renderGenericNametag((LivingEntity) entity);
                }
            }
        }
    }

    private int getRenderCount() {
        int count = cullingSetting.get() ? maxCullingCountSetting.get() : entityList.size();
        count = MathHelper.clamp(count, 0, entityList.size());
        return count;
    }

    @Override
    public String getInfoString() {
        return Integer.toString(getRenderCount());
    }

    private double getHeight(Entity entity) {
        double height = entity.getEyeHeight(entity.getPose());
        if (entity.getType() == EntityType.ITEM || entity.getType() == EntityType.ITEM_FRAME) {
            height += 0.2;
        } else {
            height += 0.5;
        }

        return height;
    }

    private void renderNametagPlayer(PlayerEntity player) {
        if (!selfSetting.get() && player == mc.player) {
            return;
        } else if (ignoreFriendsSetting.get() && Friends.get().contains(player)) {
            return;
        } else if (ignoreEnemiesSetting.get() && Enemies.get().contains(player)) {
            return;
        }

        TextRenderer text = TextRenderer.get();

        NametagUtils.begin(pos);

        // TODO: Show MatHax icon when the player is online on the API.
        boolean showMatHax = false, showMatHaxDeveloper = false;
        /*boolean showMatHax = Config.get().onlineSetting.get() && API.isPlayerOnline(player.getGameProfile().getId());
        boolean showMatHaxDeveloper = showMatHax && API.isPlayerDeveloper(player.getGameProfile().getId());*/

        // Gamemode
        GameMode gm = EntityUtils.getGameMode(player);
        String gmText = gm != null ? switch (gm) {
            case SPECTATOR -> "Sp";
            case SURVIVAL -> "S";
            case CREATIVE -> "C";
            case ADVENTURE -> "A";
        } : "BOT";

        gmText = "[" + gmText + "] ";

        // Name
        String name;
        Color nameColor = PlayerUtils.getPlayerColor(player, nameColorSetting.get());
        if (player == mc.player) {
            if (selfSetting.get()) {
                nameColor = selfColorSetting.get();
            }

            name = Modules.get().get(NameProtect.class).getName(player.getEntityName());
        } else {
            name = player.getEntityName();
        }

        name += " ";

        // Health
        float absorption = player.getAbsorptionAmount();
        int health = Math.round(player.getHealth() + absorption);
        double healthPercentage = health / (player.getMaxHealth() + absorption);
        Color healthColor;
        if (healthPercentage <= 0.333) {
            healthColor = Color.RED;
        } else if (healthPercentage <= 0.666) {
            healthColor = Color.ORANGE;
        } else {
            healthColor = Color.GREEN;
        }

        String healthText = String.valueOf(health);

        // Ping
        int ping = EntityUtils.getPing(player);
        String pingText = " [" + ping + "ms]";

        // Distance
        double distance = 0.0;
        if (player != mc.cameraEntity) {
            distance = Math.round(PlayerUtils.distanceToCamera(player) * 10.0) / 10.0;
        }

        String distanceText = " (" + distance + "m)";

        //Pops
        String popText = " [" + TotemPopUtils.getPops(player.getGameProfile().getId()) + "]";

        // Calc widths
        double gmWidth = text.getWidth(gmText);
        double nameWidth = text.getWidth(name);
        double healthWidth = text.getWidth(healthText);
        double pingWidth = text.getWidth(pingText);
        double distanceWidth = text.getWidth(distanceText);
        double popWidth = text.getWidth(popText);
        double width = nameWidth + healthWidth;

        if (showMatHax) {
            width += text.getHeight();
        }

        if (displayGameModeSetting.get()) {
            width += gmWidth;
        }

        if (displayPingSetting.get()) {
            width += pingWidth;
        }

        if (displayDistanceSetting.get()) {
            width += distanceWidth;
        }

        if (displayTotemPopsSetting.get()) {
            width += popWidth;
        }

        double widthHalf = width / 2;
        double heightDown = text.getHeight();

        drawBackground(-widthHalf, -heightDown, width, heightDown);

        // Render texts
        text.beginBig();
        double hX = -widthHalf;
        double hY = -heightDown;

        if (showMatHax) {
            hX += text.getHeight();
        }

        List<Section> sections = new ArrayList<>();
        if (displayGameModeSetting.get()) {
            sections.add(new Section(gmText, Color.YELLOW));
        }

        sections.add(new Section(name, nameColor));

        sections.add(new Section(healthText, healthColor));

        if (displayPingSetting.get()) {
            sections.add(new Section(pingText, Color.BLUE));
        }

        if (displayDistanceSetting.get()) {
            sections.add(new Section(distanceText, Color.LIGHT_GRAY));
        }

        if (displayTotemPopsSetting.get()) {
            sections.add(new Section(popText, Color.ORANGE));
        }

        text.render(sections, hX, hY);

        text.end();

        if (displayItemsSetting.get()) {
            Arrays.fill(itemWidths, 0);
            boolean hasItems = false;
            int maxEnchantCount = 0;
            for (int i = 0; i < 6; i++) {
                ItemStack itemStack = getItem(player, i);
                if (itemWidths[i] == 0 && (!ignoreEmptySetting.get() || !itemStack.isEmpty())) {
                    itemWidths[i] = 32 + itemSpacingSetting.get();
                }

                if (!itemStack.isEmpty()) {
                    hasItems = true;
                }

                if (displayItemEnchantsSetting.get()) {
                    Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(itemStack);
                    enchantmentsToShowScale.clear();

                    for (Enchantment enchantment : enchantments.keySet()) {
                        if (!ignoredEnchantmentsSetting.get().contains(enchantment)) {
                            enchantmentsToShowScale.put(enchantment, enchantments.get(enchantment));
                        }
                    }

                    for (Enchantment enchantment : enchantmentsToShowScale.keySet()) {
                        String enchantName = Utils.getEnchantSimpleName(enchantment, enchantLengthSetting.get()) + " " + enchantmentsToShowScale.get(enchantment);
                        itemWidths[i] = Math.max(itemWidths[i], (text.getWidth(enchantName) / 2));
                    }

                    maxEnchantCount = Math.max(maxEnchantCount, enchantmentsToShowScale.size());
                }
            }

            double itemsHeight = (hasItems ? 32 : 0);
            double itemWidthTotal = 0;
            for (double itemWidth : itemWidths) {
                itemWidthTotal += itemWidth;
            }

            double itemWidthHalf = itemWidthTotal / 2;
            double y = -heightDown - 7 - itemsHeight;
            double x = -itemWidthHalf;
            for (int i = 0; i < 6; i++) {
                ItemStack stack = getItem(player, i);
                RenderUtils.drawItem(stack, (int) x, (int) y, 2, true);

                if (maxEnchantCount > 0 && displayItemEnchantsSetting.get()) {
                    text.begin(0.5 * enchantTextScaleSetting.get(), false, true);

                    Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
                    Map<Enchantment, Integer> enchantmentsToShow = new HashMap<>();
                    for (Enchantment enchantment : enchantments.keySet()) {
                        if (!ignoredEnchantmentsSetting.get().contains(enchantment)) {
                            enchantmentsToShow.put(enchantment, enchantments.get(enchantment));
                        }
                    }

                    double aW = itemWidths[i];
                    double enchantY = 0;

                    double addY = switch (enchantPositionSetting.get()) {
                        case Above -> -((enchantmentsToShow.size() + 1) * text.getHeight());
                        case On_Top -> (itemsHeight - enchantmentsToShow.size() * text.getHeight()) / 2;
                    };

                    double enchantX;
                    for (Enchantment enchantment : enchantmentsToShow.keySet()) {
                        String enchantName = Utils.getEnchantSimpleName(enchantment, enchantLengthSetting.get()) + " " + enchantmentsToShow.get(enchantment);

                        Color enchantColor = Color.WHITE;
                        if (enchantment.isCursed()) {
                            enchantColor = Color.RED;
                        }

                        enchantX = switch (enchantPositionSetting.get()) {
                            case Above -> x + (aW / 2) - (text.getWidth(enchantName) / 2);
                            case On_Top -> x + (aW - text.getWidth(enchantName)) / 2;
                        };

                        text.render(enchantName, enchantX, y + addY + enchantY, enchantColor);

                        enchantY += text.getHeight();
                    }

                    text.end();
                }

                x += itemWidths[i];
            }
        } else if (displayItemEnchantsSetting.get()) {
            displayItemEnchantsSetting.set(false);
        }

        if (showMatHax) {
            if (showMatHaxDeveloper) {
                GL.bindTexture(MATHAX_DEVELOPER_ICON);
            } else {
                GL.bindTexture(MATHAX_ICON);
            }

            Renderer2D.TEXTURE.begin();
            Renderer2D.TEXTURE.texturedQuad(-width / 2 + 2, -text.getHeight(), 16, 16, Color.WHITE);
            Renderer2D.TEXTURE.render(null);
        }

        NametagUtils.end();
    }

    private void renderNametagItem(ItemStack stack) {
        TextRenderer text = TextRenderer.get();

        NametagUtils.begin(pos);

        String name = stack.getName().getString();
        String count = " x" + stack.getCount();
        double nameWidth = text.getWidth(name);
        double countWidth = text.getWidth(count);
        double heightDown = text.getHeight();
        double width = nameWidth;
        if (itemCountSetting.get()) {
            width += countWidth;
        }

        double widthHalf = width / 2;
        drawBackground(-widthHalf, -heightDown, width, heightDown);

        text.beginBig();

        List<Section> sections = new ArrayList<>();
        sections.add(new Section(name, nameColorSetting.get()));
        if (itemCountSetting.get()) {
            sections.add(new Section(count, Color.YELLOW));
        }

        text.render(sections, -widthHalf, -heightDown);

        text.end();

        NametagUtils.end();
    }

    private void renderGenericNametag(LivingEntity entity) {
        TextRenderer text = TextRenderer.get();
        NametagUtils.begin(pos);

        //Name
        String nameText = entity.getType().getName().getString();
        nameText += " ";

        //Health
        float absorption = entity.getAbsorptionAmount();
        int health = Math.round(entity.getHealth() + absorption);
        double healthPercentage = health / (entity.getMaxHealth() + absorption);
        Color healthColor;
        if (healthPercentage <= 0.333) {
            healthColor = Color.RED;
        } else if (healthPercentage <= 0.666) {
            healthColor = Color.ORANGE;
        } else {
            healthColor = Color.GREEN;
        }

        String healthText = String.valueOf(health);
        double nameWidth = text.getWidth(nameText);
        double healthWidth = text.getWidth(healthText);
        double heightDown = text.getHeight();
        double width = nameWidth + healthWidth;
        double widthHalf = width / 2;
        drawBackground(-widthHalf, -heightDown, width, heightDown);

        text.beginBig();

        List<Section> sections = new ArrayList<>();
        sections.add(new Section(nameText, nameColorSetting.get()));
        sections.add(new Section(healthText, healthColor));
        text.render(sections, -widthHalf, -heightDown);

        text.end();

        NametagUtils.end();
    }

    private void renderTntNametag(TntEntity entity) {
        TextRenderer text = TextRenderer.get();

        NametagUtils.begin(pos);

        String fuseText = ticksToTime(entity.getFuse());
        double width = text.getWidth(fuseText);
        double heightDown = text.getHeight();
        double widthHalf = width / 2;
        drawBackground(-widthHalf, -heightDown, width, heightDown);

        text.beginBig();

        double hX = -widthHalf;
        double hY = -heightDown;
        text.render(fuseText, hX, hY, nameColorSetting.get());

        text.end();

        NametagUtils.end();
    }

    private ItemStack getItem(PlayerEntity player, int index) {
        return switch (index) {
            case 0 -> player.getMainHandStack();
            case 1 -> ArmorUtils.getArmor(player, 3);
            case 2 -> ArmorUtils.getArmor(player, 2);
            case 3 -> ArmorUtils.getArmor(player, 1);
            case 4 -> ArmorUtils.getArmor(player, 0);
            case 5 -> player.getOffHandStack();
            default -> ItemStack.EMPTY;
        };
    }

    private void drawBackground(double x, double y, double width, double height) {
        Renderer2D.COLOR.begin();
        Renderer2D.COLOR.quad(x - 1, y - 1, width + 2, height + 2, backgroundSetting.get());
        Renderer2D.COLOR.render(null);
    }

    public boolean excludeBots() {
        return excludeBots.get();
    }

    public boolean playerNametags() {
        return isEnabled() && entitiesSetting.get().containsKey(EntityType.PLAYER);
    }

    public enum Position {
        Above("Above"),
        On_Top("On Top");

        private final String name;

        Position(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}