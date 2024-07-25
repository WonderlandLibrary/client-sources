package club.bluezenith.module.modules.render.hud;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.ModuleToggledEvent;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.render.ClickGUI;
import club.bluezenith.module.modules.render.hud.arraylist.NewRenderer;
import club.bluezenith.module.modules.render.hud.elements.*;
import club.bluezenith.module.value.types.*;
import club.bluezenith.ui.draggables.Draggable;
import club.bluezenith.ui.notifications.NotificationType;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.math.Range;
import club.bluezenith.util.render.ColorUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.module.value.builders.AbstractBuilder.*;
import static club.bluezenith.util.math.MathUtil.getRandomInt;
import static net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting;
import static net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting;

public class HUD extends Module {
    public static HUD module;
    
    public List<Module> modules = new ArrayList<>();

    private final NewRenderer arrayListRenderer = new NewRenderer();

    public final StringValue hudName = createString("Client name")
            .defaultOf(BlueZenith.fancyName)
            .index(-3)
            .build();
    
    public static final ListValue elements = createList("Elements")
            .range("ArrayList",
                    "Ping", 
                    "Speed", 
                    "Time", 
                    "FPS", 
                    "Bossbar", 
                    "Effects", 
                    "Armor", 
                    "Coords",
                    "Playtime", 
                    "Build Info", 
                    "Scoreboard", 
                    "Transparent Chat",
                    "Custom hotbar",
                    "Animate tooltip",
                    "Scoreboard font"
            ).index(-2)
            .build();

    public final ListValue notificationOptions = createList("Notifications")
            .range("Blur",
                   "On toggle",
                   "Centered"
            ).index(-1)
            .build();
    
    public final ListValue outlineOptions = createList("Outline")
            .range("Top, Split, Bar, Bottom, Connect split")
            .showIf(() -> elements.getOptionState("ArrayList"))
            .index(1)
            .build();

    public final ModeValue watermarkMode = createMode("Watermark")
            .range("Simple, Generic")
            .index(3)
            .build();

    public final ListValue genericWatermarkOptions = createList("Watermark options")
            .range("Server info, FPS, Username, Playtime")
            .index(3)
            .showIf(() -> watermarkMode.is("Generic"))
            .build();

    public final ModeValue arrayListAnimationMode = createMode("Animation")
            .showIf(() -> elements.getOptionState("ArrayList"))
            .range("Smooth, Linear, Slay, None")
            .defaultOf("Smooth")
            .index(4)
            .build();
    
    public final FontValue font = new FontValue("Font", FontUtil.mavenMedium42)
            .setValueChangeListener((before, after) -> {

                getBlueZenith().getModuleManager()
                        .getModules()
                        .forEach(module -> module.width = after.getStringWidthF(getDisplayString(module)));
                return after;

            }).setIndex(5);

    private final ModeValue suffixesMode = createMode("Suffix")
            .range("Simple", "Dash", "Bracket", "Round", "Dawn", "HvH", "None")
            .showIf(() -> elements.getOptionState("ArrayList"))
            .whenChanged((before, after) -> {

                if(after.equals("Dawn")) {
                    final String[] values = { "", "", "Verus", "Verus", "NCP", "Bypass", "", "NCP", "Bypass", "Vulcan" };
                    getBlueZenith().getModuleManager()
                            .getModules()
                            .forEach(module -> module.dawnSuffix = values[getRandomInt(0, values.length)]);
                }

                getBlueZenith().getModuleManager()
                        .getModules()
                        .forEach(module -> module.width = font.get().getStringWidthF(getDisplayString(module)));

                return after;
            })
            .index(7)
            .build();

    public final ModeValue colorMode = createMode("Color")
            .range("Custom", "Pulse", "Rainbow", "Astolfo", "Type", "Dark pulse")
            .index(8)
            .build();

    public final ColorValue primaryColor = createColor("Primary")
            .index(9)
            .build();

    public final FloatValue rainbowMulti = createFloat("Rainbow multiplier")
            .defaultOf(0.4F)
            .increment(0.1F)
            .range(Range.of(0F, 1F))
            .showIf(() -> colorMode.is("Rainbow"))
            .index(10)
            .build();

    public final ColorValue secondaryColor = createColor("Secondary")
            .showIf(() -> colorMode.is("Pulse"))
            .index(11)
            .build();

    private final IntegerValue timesDarker = createInteger("Times darker")
            .showIf(() -> colorMode.is("Dark pulse"))
            .index(12)
            .range(Range.of(1, 5))
            .increment(1)
            .build();

    public final FloatValue scoreboardHeight = createFloat("Scoreboard height")
            .defaultOf(0F)
            .increment(1F)
            .range(Range.of(-150F, 150F))
            .showIf(() -> elements.getOptionState("Scoreboard"))
            .index(13)
            .build();

    public final FloatValue marginX = createFloat("Pos X")
            .defaultOf(0.2F)
            .increment(0.2F)
            .range(Range.of(0F, 15F))
            .showIf(() -> elements.getOptionState("ArrayList"))
            .index(14)
            .build();

    public final FloatValue marginY = createFloat("Pos Y")
            .defaultOf(0F)
            .increment(0.2F)
            .range(Range.of(0F, 15F))
            .showIf(() -> elements.getOptionState("ArrayList"))
            .index(15)
            .build();

    public final IntegerValue backgroundOpacity = createInteger("Background opacity")
            .defaultOf(80)
            .increment(1)
            .range(Range.of(0, 255))
            .showIf(() -> elements.getOptionState("ArrayList"))
            .index(16)
            .build();

    public final IntegerValue backgroundBrightness = createInteger("Background brightness")
            .defaultOf(35)
            .increment(1)
            .range(Range.of(0, 255))
            .showIf(() -> backgroundOpacity.get() > 0 && elements.getOptionState("ArrayList"))
            .index(17)
            .build();

    public final FloatValue textX = createFloat("Text X")
            .defaultOf(2.8F)
            .increment(0.1F)
            .range(Range.of(-5F, 5F))
            .showIf(() -> elements.getOptionState("ArrayList"))
            .index(18)
            .build();

    public final FloatValue textY = createFloat("Text Y")
            .defaultOf(0F)
            .increment(0.1F)
            .range(Range.of(-5F, 5F))
            .showIf(() -> elements.getOptionState("ArrayList"))
            .index(19)
            .build();

    public final FloatValue extraRectWidth = createFloat("Extra background width")
            .defaultOf(2.0F)
            .increment(0.1F)
            .range(Range.of(0F, 5F))
            .index(20)
            .showIf(() -> elements.getOptionState("ArrayList") && (!outlineOptions.getSelectedOptions().isEmpty() || backgroundOpacity.get() > 0))
            .build();

    public final FloatValue height = createFloat("Height")
            .defaultOf(1.3F)
            .increment(0.1F)
            .range(Range.of(0F, 15F))
            .showIf(() -> elements.getOptionState("ArrayList"))
            .index(21)
            .build();

    public final FloatValue outlineWidth = createFloat("Line width")
            .defaultOf(1F)
            .increment(0.1F)
            .range(Range.of(1.1F, 3F))
            .showIf(() -> elements.getOptionState("ArrayList") && !outlineOptions.getSelectedOptions().isEmpty())
            .index(22)
            .build();

    private final List<Draggable> drawableElements = new ArrayList<Draggable>() {{
        add(new Playtime());
        add(new Watermark());
        add(new Effects());
    }};

    BuildInfo buildInfo = new BuildInfo();

    public HUD() {
        super("HUD", ModuleCategory.RENDER);
        module = this;
        this.setState(true);
        elements.setOptionState("ArrayList", true);
        elements.setOptionState("Scoreboard", true);

        getBlueZenith().registerStartupTask((bz) -> {
            drawableElements.forEach(drawableElement -> bz.getDraggableRenderer().addDraggable(drawableElement));
            bz.getDraggableRenderer().addDraggable(arrayListRenderer);
        });
    }

    private final VirtueWatermark virtueWatermark = new VirtueWatermark();

    @Listener
    public void onTick(UpdateEvent event) {
        arrayListRenderer.onTick();
    }

    @Listener
    public void onRender2D(Render2DEvent event) {
        if (mc.gameSettings.showDebugInfo) return;

        if(BlueZenith.isVirtueTheme)
            virtueWatermark.draw();

        final FontRenderer font = this.font.get();

        drawInfo(event.resolution);

        if(buildInfo.shouldBeRendered()) {
            buildInfo.resetPosition();
            buildInfo.draw(event);
        }

        if (elements.getOptionState("ArrayList")) {
            arrayListRenderer.render(font, event.resolution, this);
        }

        if(elements.getOptionState("Armor") && mc.playerController.gameIsSurvivalOrAdventure()) {
            enableStandardItemLighting();

            int x = (int)(event.getWidth()/1.96);

            final ItemStack[] reversed = new ItemStack[5];
            for (int index = 1; index < 5; index++) {
                final ItemStack itemStack = player.inventory.armorInventory[4 - index];
                if(itemStack == null) continue;

                reversed[index] = itemStack;
            }

            final boolean isUnderwater = mc.thePlayer.getAir() < 300;
            final int height = (int) event.getHeight();
            GlStateManager.disableDepth();
            GlStateManager.disableAlpha();
            for (ItemStack itemStack : reversed) {
                if(itemStack == null) continue;
                mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, height - (isUnderwater ? 65 : 57));
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, itemStack, x, height - (isUnderwater ? 64 : 56), null);
                x += 22;
            }
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
            disableStandardItemLighting();
        }

    }

    @Listener
    public void onModuleToggle(ModuleToggledEvent event) {
        if(mc.thePlayer != null && notificationOptions.getOptionState("On toggle") && !(event.module instanceof ClickGUI))
            getBlueZenith().getNotificationPublisher().post(
                    "Modules",
                    (event.isEnabled ? "Enabled " : "Disabled ") + event.module.getName(),
                    event.isEnabled ? NotificationType.SUCCESS : NotificationType.ERROR,
                    1000
            );

    }
    // kinda by fxy, i just made it less cancerous
    private void drawInfo(ScaledResolution sr) {
        if (mc.currentScreen instanceof GuiChat || BlueZenith.isVirtueTheme) return;
        FontRenderer f = this.font.get();
        final float fy = f.FONT_HEIGHT + 2;
        int z = 0;
        if (elements.getOptionState("Coords")) {
            String displayString = "XYZ§f: " + Math.round(mc.thePlayer.posX) + ", " + Math.round(mc.thePlayer.posY) + ", " + Math.round(mc.thePlayer.posZ);
            int colorD = getColor(z);
            f.drawString(displayString, 2, sr.getScaledHeight() - fy - (fy * z), colorD, true);
            z++;
        }
        if (!mc.isSingleplayer() && elements.getOptionState("Ping")) {
            String displayString = "Ping§f: " + mc.getCurrentServerData().pingToServer + "ms";
            int colorD = getColor(z);
            f.drawString(displayString, 2, sr.getScaledHeight() - fy - (fy * z), colorD, true);
            z++;
        }
        if (elements.getOptionState("FPS")) {
            String displayString = "FPS§f: " + Minecraft.getDebugFPS();
            int colorD = getColor(z);
            f.drawString(displayString, 2, sr.getScaledHeight() - fy - (fy * z), colorD, true);
            z++;
        }
        if (elements.getOptionState("Speed")) {
            String displayString = "Blocks/sec§f: " + MathUtil.round(getBPS(), 2);
            int colorD = getColor(z);
            f.drawString(displayString, 2, sr.getScaledHeight() - fy - (fy * z), colorD, true);
        }
    }

    @Override
    protected void getDataFromConfig(JsonObject configObject) {
        final JsonObject primaryObject = configObject.get("settings").getAsJsonObject();

        if(primaryObject.has("drawables")) {
            final JsonObject settings = primaryObject.get("drawables").getAsJsonObject();

            for (Draggable drawableElement : this.drawableElements) {
                if (!settings.has(drawableElement.getIdentifier())) continue;

                final JsonObject elementObject = settings.get(drawableElement.getIdentifier()).getAsJsonObject();

                drawableElement.moveTo(elementObject.get("x").getAsFloat(), elementObject.get("y").getAsFloat());
            }
        }

        if(primaryObject.has("arraylist")) {
            final JsonObject arraylist = primaryObject.get("arraylist").getAsJsonObject();

            arrayListRenderer.moveTo(arraylist.get("pos-x").getAsFloat(), arraylist.get("pos-y").getAsFloat());
        }
    }

    @Override
    protected void addDataToConfig(JsonObject configObject) {
        final JsonObject jsonObject = new JsonObject();

        for (Draggable drawableElement : this.drawableElements) {
            if(drawableElement.getIdentifier() == null) continue;;

            final JsonObject elementConfig = new JsonObject();
            elementConfig.add("x", new JsonPrimitive(drawableElement.getX()));
            elementConfig.add("y", new JsonPrimitive(drawableElement.getY()));

            jsonObject.add(drawableElement.getIdentifier(), elementConfig);
        }

        final JsonObject arrayList = new JsonObject();
        arrayList.addProperty("pos-x", arrayListRenderer.getX());
        arrayList.addProperty("pos-y", arrayListRenderer.getY());

        configObject.get("settings").getAsJsonObject().add("arraylist", arrayList);
        configObject.get("settings").getAsJsonObject().add("drawables", jsonObject);
    }

    public int getColor(int i) {
        switch (colorMode.get()) {
            case "Astolfo":
                return ColorUtil.astolfoColor(i / 2, primaryColor.b, primaryColor.s).getRGB();
            case "Type":
            case "Rainbow":
                return ColorUtil.rainbow(i, rainbowMulti.get(), primaryColor.s, primaryColor.b).getRGB();
            case "Pulse":
                int[] c1 = ColorUtil.getInt(primaryColor.getRGB());
                int[] c2 = ColorUtil.getInt(secondaryColor.getRGB());
                return ColorUtil.pulse(i, c1[0], c1[1], c1[2], c2[0], c2[1], c2[2]);
            case "Dark pulse":
                final Color primary = primaryColor.get();
                Color darkerPrimary = primary;

                for (int i1 = 0; i1 < timesDarker.get(); i1++) {
                    darkerPrimary = darkerPrimary.darker();
                }

                final int[] rgb1 = ColorUtil.getInt(primary.getRGB()),
                            rgb2 = ColorUtil.getInt(darkerPrimary.getRGB());
                return ColorUtil.pulse(i, rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);
            case "Custom":
                return primaryColor.getRGB();
            default:
                return ColorUtil.get(255, 255, 255, 255);
        }
    }

    public Color getTypeColor(Module mod) {
        switch (mod.getCategory()) {
            case COMBAT:
                return combatType;
            case PLAYER:
                return playerType;
            case MOVEMENT:
                return movementType;
            case RENDER:
                return renderType;
            case MISC:
                return miscType;
            default:
                return funType;
        }
    }

    public static double getBPS() {
        if (mc.thePlayer == null || mc.thePlayer.ticksExisted < 1) {
            return 0;
        }
        return mc.thePlayer.getDistance(mc.thePlayer.lastTickPosX, mc.thePlayer.posY, mc.thePlayer.lastTickPosZ) * (Minecraft.getMinecraft().timer.ticksPerSecond * Minecraft.getMinecraft().timer.timerSpeed);
    }

    private final Color combatType = new Color(255, 76, 76);
    private final Color playerType = new Color(255, 211, 100);
    private final Color movementType = new Color(91, 241, 166);
    private final Color renderType = new Color(73, 180, 246);
    private final Color miscType = new Color(105, 93, 245);
    private final Color funType = new Color(246, 128, 201);

    private final Color combatVirtueType = new Color(210, 74, 74);

    private final Color playerVirtueType = new Color(125, 224, 125);

    private final Color movementVirtueType = new Color(191, 229, 221);

    private final Color renderVirtueType = new Color(238, 211, 140);

    private final Color miscVirtueType = new Color(43, 178, 151);

    public Color getVirtueColor(Module module) {
        switch (module.getCategory()) {
            case COMBAT:
                return combatVirtueType;
            case PLAYER:
            case EXPLOIT:
                return playerVirtueType;
            case MOVEMENT:
                return movementVirtueType;
            case RENDER:
                return renderVirtueType;
            case MISC:
            case FUN:
                return miscVirtueType;
        }
        return funType;
    }
    public String getDisplayString(String mode, Module m) {
        height.set(2.5F);
        String name = m.displayName.equals("") ? m.defaultDisplayName : m.displayName;

        if(BlueZenith.isVirtueTheme) {
            name = name.replaceAll(" ", "");
            mode = "Bracket";
        }

        if (mode.equals("HvH")) {
            if (!m.getValues().isEmpty())
                return name + " §7" + "[HvH]";
            else return name;
        }
        if (StringUtils.isBlank(m.getTag()))
            return name;
        switch (mode) {
            case "Simple":
                return name + " §7" + m.getTag();
            case "Dash":
                return name + " §7- " + m.getTag();
            case "Bracket":
                return name + " §7[" + m.getTag() + "]";
            case "Round":
                return name + " §7(" + m.getTag() + ")";
            case "Dawn":
                return name + (m.dawnSuffix.isEmpty() ? "" : " §7" + m.dawnSuffix);
            default:
                return name;
        }
    }

    public String getDisplayString(Module m) {
        return getDisplayString(suffixesMode.get(), m);
    }

    public static boolean shouldRotateItems() {
        return false;//enableExperimentRotation && ((HUD) getBlueZenith().getModuleManager().getModule(HUD.class)).rotation.get();
    }

    public static int getChatOffset() {
        int offset = 0;
        int increment = module.font.get().FONT_HEIGHT / 2;
        if (!module.getState())
            return 0;
        if (elements.getOptionState("Ping") && !Minecraft.getMinecraft().isSingleplayer())
            offset += increment + increment / 2 + 5;
        if (elements.getOptionState("Speed"))
            offset += increment;
        if (elements.getOptionState("FPS"))
            offset += increment;
        if (elements.getOptionState("Coords"))
            offset += increment;

        return offset;
        // this is unused because elements doesn't render when chat is opened, not sure who added that
        //return 0;
    }
}
