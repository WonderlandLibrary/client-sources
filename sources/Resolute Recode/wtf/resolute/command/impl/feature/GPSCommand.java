package wtf.resolute.command.impl.feature;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.util.ResourceLocation;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.command.*;
import wtf.resolute.command.impl.CommandException;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.moduled.ModuleRegister;
import wtf.resolute.moduled.impl.misc.PANIC;
import wtf.resolute.moduled.impl.render.HUD.HUD;
import wtf.resolute.utiled.client.IMinecraft;
import wtf.resolute.utiled.projections.ProjectionUtil;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.font.Fonts;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.TextFormatting;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GPSCommand implements Command, CommandWithAdvice, MultiNamedCommand, IMinecraft {
    final Prefix prefix;
    final Logger logger;

    private final Map<String, Vector3i> waysMap = new LinkedHashMap<>();

    public GPSCommand(Prefix prefix, Logger logger) {
        this.prefix = prefix;
        this.logger = logger;
        ResoluteInfo.getInstance().getEventBus().register(this);
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
                    throw new CommandException(TextFormatting.RED + "Укажите тип команды:" + TextFormatting.GRAY + " add, remove, clear");
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
                "Пример: " + TextFormatting.RED + commandPrefix + "gps add аирдроп 1000 100 1000"
        );
    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        ModuleRegister functionRegistry = ResoluteInfo.getInstance().getFunctionRegistry();
        PANIC selfDestruct = functionRegistry.getSelfDestruct();

        if (selfDestruct.unhooked) {
            return;
        }
        if (waysMap.isEmpty()) {
            return;
        }
        for (String name : waysMap.keySet()) {
            Vector3i vec3i = waysMap.get(name);

            Vector3d vec3d = new Vector3d(
                    vec3i.getX() + 0.5,
                    vec3i.getY() + 0.5,
                    vec3i.getZ() + 0.5
            );

            Vector2f vec2f = ProjectionUtil.project(vec3d.x, vec3d.y, vec3d.z);

            int distance = (int) Minecraft.getInstance().player.getPositionVec().distanceTo(vec3d);

            String text = name + " (" + distance + "M)";

            if (vec2f.equals(new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE))) {

                Vector3d localVec = vec3d.subtract(mc.getRenderManager().info.getProjectedView());

                double x = localVec.getX();
                double z = localVec.getZ();

                double cos = MathHelper.cos((float) (mc.getRenderManager().info.getYaw() * (Math.PI * 2 / 360)));
                double sin = MathHelper.sin((float) (mc.getRenderManager().info.getYaw() * (Math.PI * 2 / 360)));
                double rotY = -(z * cos - x * sin);
                double rotX = -(x * cos + z * sin);

                float angle = (float) (Math.atan2(rotY, rotX) * 180 / Math.PI);

                double x2 = 30 * MathHelper.cos((float) Math.toRadians(angle)) + window.getScaledWidth() / 2f;
                double y2 = 30 * MathHelper.sin((float) Math.toRadians(angle)) + window.getScaledHeight() / 2f;

                GlStateManager.pushMatrix();
                GlStateManager.disableBlend();
                GlStateManager.translated(x2, y2, 0);

                Fonts.sfMedium.drawCenteredText(e.getMatrixStack(), text, 0, -15, -1, 6);

                GlStateManager.rotatef(angle, 0, 0, 1);

                int color = -1;

                final ResourceLocation logo = new ResourceLocation("resolute/images/triangle.png");
                DisplayUtils.drawImage(logo, 1, 0, 0 + 15, 0 + 12, HUD.getColor(0, 1));
                GlStateManager.enableBlend();
                GlStateManager.popMatrix();

                continue;
            }

            float textWith = Fonts.sfMedium.getWidth(text, 8);
            float fontHeight = Fonts.sfMedium.getHeight(8);

            float posX = vec2f.x - textWith / 2;
            float posY = vec2f.y - fontHeight / 2;

            float padding = 2;

            DisplayUtils.drawRectW(posX - padding, posY - padding, padding + textWith + padding, padding + fontHeight + padding, ColorUtils.rgba(0, 0, 0, 128));
            Fonts.sfMedium.drawText(e.getMatrixStack(), text, posX, posY, -1, 8);
        }
    }

    @Override
    public List<String> aliases() {
        return List.of("way");
    }
}
