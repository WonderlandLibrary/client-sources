package src.Wiksi.command.impl.feature;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import src.Wiksi.Wiksi;
import src.Wiksi.command.*;
import src.Wiksi.command.impl.CommandException;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.client.IMinecraft;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.player.MoveUtils;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.KawaseBlur;
import src.Wiksi.utils.render.font.Fonts;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GPSCommand implements Command, CommandWithAdvice, MultiNamedCommand, IMinecraft {
    final Prefix prefix;
    final Logger logger;
    public float animationStep;
    private final Map<String, Vector3i> waysMap = new LinkedHashMap<>();

    public GPSCommand(Prefix prefix, Logger logger) {
        this.prefix = prefix;
        this.logger = logger;
        Wiksi.getInstance().getEventBus().register(this);
    }

    @Override
    public void execute(Parameters parameters) {
        String commandType = parameters.asString(0).orElse("");

        switch (commandType) {
            case "add" -> addGPS(parameters);
            case "remove" -> removeGPS(parameters);
            case "clear" -> {
                waysMap.clear();
                logger.log("Все пути были удалены!");
            }
            case "list" -> {
                logger.log("Список путей:");

                for (String s : waysMap.keySet()) {
                    logger.log("- " + s + " " + waysMap.get(s));
                }
            }
            default ->
                    throw new CommandException(TextFormatting.RED + "Команды:" + TextFormatting.GRAY + " add, remove, clear");
        }
    }

    private void addGPS(Parameters param) {
        String name = param.asString(1)
                .orElseThrow(() -> new CommandException(TextFormatting.RED + "Укажите имя координаты!"));
        int x = param.asInt(2)
                .orElseThrow(() -> new CommandException(TextFormatting.RED + "Укажите первую координату!"));

        int y = param.asInt(3)
                .orElseThrow(() -> new CommandException(TextFormatting.RED + "Укажите вторую координату!"));

        int z = param.asInt(4)
                .orElseThrow(() -> new CommandException(TextFormatting.RED + "Укажите третью координату!"));

        Vector3i vec = new Vector3i(x, y, z);
        waysMap.put(name, vec);
        logger.log("Путь " + name + " был добавлен!");
    }

    private void removeGPS(Parameters param) {
        String name = param.asString(1)
                .orElseThrow(() -> new CommandException(TextFormatting.RED + "Укажите имя координаты!"));

        waysMap.remove(name);
        logger.log("Путь " + name + " был удалён!");
    }


    @Override
    public String name() {
        return "gps";
    }

    @Override
    public String description() {
        return "Позволяет работать с координатами путей";
    }

    @Override
    public List<String> adviceMessage() {
        String commandPrefix = prefix.get();
        return List.of(commandPrefix + "gps add <имя, x, y, z> - Проложить путь к WayPoint'у",
                commandPrefix + "gps remove <имя> - Удалить WayPoint",
                commandPrefix + "gps list - Список WayPoint'ов",
                commandPrefix + "gps clear - Очистить список WayPoint'ов",
                "Пример: " + TextFormatting.RED + commandPrefix + "gps add мистик 1000 100 1000"
        );
    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (waysMap.isEmpty()) {
            return;
        }

        for (String name : waysMap.keySet()) {
            Vector3i vec3i = waysMap.get(name);
            Vector3d vec3d = new Vector3d(vec3i.getX() + 0.5, vec3i.getY() + 0.5, vec3i.getZ() + 0.5);


            double posX = 460;
            double posY = 400;
            int distance = (int) Minecraft.getInstance().player.getPositionVec().distanceTo(vec3d);
            String text =   "GPS /" + distance + "м";

            Vector3d localVec = vec3d.subtract(mc.getRenderManager().info.getProjectedView());
            double localVecX = localVec.getX();
            double localVecZ = localVec.getZ();
            double epsilon = 0.001;
            double x = localVec.getX();
            double z = localVec.getZ();
            float size = 70.0f;

            if (mc.currentScreen instanceof InventoryScreen) {
                size += 80.0f;
            }

            if (MoveUtils.isMoving()) {
                size += 10.0f;
            }

            animationStep = MathUtil.fast(animationStep, size, 1);

            double cos = MathHelper.cos((float) (mc.getRenderManager().info.getYaw() * (Math.PI * 2 / 360)));
            double sin = MathHelper.sin((float) (mc.getRenderManager().info.getYaw() * (Math.PI * 2 / 360)));
            double rotY = -(localVecZ * cos - (localVecX + epsilon) * sin);
            double rotX = -((localVecX + epsilon) * cos + localVecZ * sin);
            String x1 = "X: " + vec3i.getX();
            String y1 ="Y: " + vec3i.getY();
            String z1 = "Z:" + vec3i.getZ();
            float angle = (float) (Math.atan2(rotY, rotX) * 180 / Math.PI);
            float wh = 80;
            float wh1 = 40;
            double x2 = animationStep * MathHelper.cos((float) Math.toRadians(angle)) + posX;
            double y2 = animationStep * MathHelper.sin((float) Math.toRadians(angle)) + posY;
            Style style = Wiksi.getInstance().getStyleManager().getCurrentStyle();

            KawaseBlur.blur.updateBlur(1.5f, 3);

            KawaseBlur.blur.render(() -> {
                DisplayUtils.drawRoundedRect((float) posX - 20, (float) posX - 80, wh + 20  , wh, 1, ColorUtils.rgba(17, 17, 17, 100));
            });
            DisplayUtils.drawRoundedRect((float) posX +37.5f, (float) posX - 42.5f, wh1 - 5  , wh1- 5, 0.1f, ColorUtils.rgba(255, 255, 255, 50));
            DisplayUtils.drawRoundedRect((float) posX +35, (float) posX - 45, wh1  , wh1, 0.1f, ColorUtils.rgba(255, 255, 255, 50));
            Fonts.sfMedium.drawCenteredText(e.getMatrixStack(), text, (float) posX + 14, (float) posX - 75, style.getSecondColor().getRGB(), 8);
            Fonts.montserrat.drawCenteredText(e.getMatrixStack(), name, (float) posX + 20, (float) posX - 65, ColorUtils.rgb(180,180,180), 8);
            Fonts.montserrat.drawCenteredText(e.getMatrixStack(), x1, (float) posX -1 , (float) posX - 45, ColorUtils.rgb(180,180,180), 8);
            Fonts.montserrat.drawCenteredText(e.getMatrixStack(), y1, (float) posX -1 , (float) posX - 45 + 13, ColorUtils.rgb(180,180,180), 8);
            Fonts.montserrat.drawCenteredText(e.getMatrixStack(), z1, (float) posX  -1, (float) posX - 45 + 13 + 13, ColorUtils.rgb(180,180,180), 8);
            GlStateManager.pushMatrix();
            GlStateManager.disableBlend();


            GlStateManager.translated(posX + 53, posY + 35, 0);


            GlStateManager.rotatef(angle, 0, 0, 1);


            drawTriangle(1, 1, 10, 10, Color.RED);

            GlStateManager.enableBlend();
            GlStateManager.popMatrix();
        }
    }

    public static void drawTriangle(float x, float y, float width, float height, Color color) {
        DisplayUtils.drawImage(new ResourceLocation("Wiksi/images/triangl.png"), -14.0F, -14.0F, 30, 30, ColorUtils.getColor(1));

        GL11.glPushMatrix();
        GL11.glPopMatrix();
    }

    @Override
    public List<String> aliases() {
        return List.of("way");
    }
}