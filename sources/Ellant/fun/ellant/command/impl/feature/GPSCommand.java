package fun.ellant.command.impl.feature;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.Ellant;
import fun.ellant.command.Command;
import fun.ellant.command.CommandWithAdvice;
import fun.ellant.command.Logger;
import fun.ellant.command.MultiNamedCommand;
import fun.ellant.command.Parameters;
import fun.ellant.command.Prefix;
import fun.ellant.command.impl.CommandException;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.api.FunctionRegistry;
import fun.ellant.functions.impl.player.SelfDestruct;
import fun.ellant.functions.impl.render.Pointers;
import fun.ellant.utils.client.IMinecraft;
import fun.ellant.utils.projections.ProjectionUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.TextFormatting;

public class GPSCommand implements Command, CommandWithAdvice, MultiNamedCommand, IMinecraft {
    private final Prefix prefix;
    private final Logger logger;
    private final Map<String, Vector3i> waysMap = new LinkedHashMap();

    public GPSCommand(Prefix prefix, Logger logger) {
        this.prefix = prefix;
        this.logger = logger;
        Ellant.getInstance().getEventBus().register(this);
    }

    public void execute(Parameters parameters) {
        String commandType = (String)parameters.asString(0).orElse("");
        byte var4 = -1;
        switch(commandType.hashCode()) {
            case -934610812:
                if (commandType.equals("remove")) {
                    var4 = 1;
                }
                break;
            case 96417:
                if (commandType.equals("add")) {
                    var4 = 0;
                }
                break;
            case 3322014:
                if (commandType.equals("list")) {
                    var4 = 3;
                }
                break;
            case 94746189:
                if (commandType.equals("clear")) {
                    var4 = 2;
                }
        }

        switch(var4) {
            case 0:
                this.addGPS(parameters);
                break;
            case 1:
                this.removeGPS(parameters);
                break;
            case 2:
                this.waysMap.clear();
                this.logger.log("Все пути были удалены!");
                break;
            case 3:
                this.logger.log("Список путей:");
                Iterator var5 = this.waysMap.keySet().iterator();

                while(var5.hasNext()) {
                    String s = (String)var5.next();
                    this.logger.log("- " + s + " " + this.waysMap.get(s));
                }

                return;
            default:
                throw new CommandException(TextFormatting.RED + "Укажите тип команды:" + TextFormatting.GRAY + " add, remove, clear");
        }

    }

    private void addGPS(Parameters param) {
        String name = (String)param.asString(1).orElseThrow(() -> {
            return new CommandException(TextFormatting.RED + "Укажите имя координаты!");
        });
        int x = (Integer)param.asInt(2).orElseThrow(() -> {
            return new CommandException(TextFormatting.RED + "Укажите первую координату!");
        });
        int y = (Integer)param.asInt(3).orElseThrow(() -> {
            return new CommandException(TextFormatting.RED + "Укажите вторую координату!");
        });
        int z = (Integer)param.asInt(4).orElseThrow(() -> {
            return new CommandException(TextFormatting.RED + "Укажите третью координату!");
        });
        Vector3i vec = new Vector3i(x, y, z);
        this.waysMap.put(name, vec);
        this.logger.log("Путь " + name + " был добавлен!");
    }

    private void removeGPS(Parameters param) {
        String name = (String)param.asString(1).orElseThrow(() -> {
            return new CommandException(TextFormatting.RED + "Укажите имя координаты!");
        });
        this.waysMap.remove(name);
        this.logger.log("Путь " + name + " был удалён!");
    }

    public String name() {
        return "gps";
    }

    public String description() {
        return "Позволяет работать с координатами путей";
    }

    public List<String> adviceMessage() {
        String commandPrefix = this.prefix.get();
        return List.of(commandPrefix + "gps add <имя, x, y, z> - Проложить путь к WayPoint'у", commandPrefix + "gps remove <имя> - Удалить WayPoint", commandPrefix + "gps list - Список WayPoint'ов", commandPrefix + "gps clear - Очистить список WayPoint'ов", "Пример: " + TextFormatting.RED + commandPrefix + "gps add аирдроп 1000 100 1000");
    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        MatrixStack ms = e.getMatrixStack();

        FunctionRegistry functionRegistry = Ellant.getInstance().getFunctionRegistry();
        SelfDestruct selfDestruct = functionRegistry.getSelfDestruct();
        if (!selfDestruct.unhooked) {
            if (!this.waysMap.isEmpty()) {
                Iterator var4 = this.waysMap.keySet().iterator();

                while(var4.hasNext()) {
                    String name = (String)var4.next();
                    Vector3i vec3i = (Vector3i)this.waysMap.get(name);
                    Vector3d vec3d = new Vector3d((double)vec3i.getX() + 0.5D, (double)vec3i.getY() + 0.5D, (double)vec3i.getZ() + 0.5D);
                    Vector2f vec2f = ProjectionUtil.project(vec3d.x, vec3d.y, vec3d.z);
                    int distance = (int)Minecraft.getInstance().player.getPositionVec().distanceTo(vec3d);
                    String text = name + " (" + distance + " Mетров)";


                    if (vec2f.equals(new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE))) {
                        Vector3d localVec = vec3d.subtract(mc.getRenderManager().info.getProjectedView());
                        double x = localVec.getX();
                        double z = localVec.getZ();
                        double cos = (double)MathHelper.cos((float)((double)mc.getRenderManager().info.getYaw() * 0.017453292519943295D));
                        double sin = (double)MathHelper.sin((float)((double)mc.getRenderManager().info.getYaw() * 0.017453292519943295D));
                        double rotY = -(z * cos - x * sin);
                        double rotX = -(x * cos + z * sin);
                        float angle = (float)(Math.atan2(rotY, rotX) * 180.0D / 3.141592653589793D);
                        double x2 = (double)(30.0F * MathHelper.cos((float)Math.toRadians((double)angle)) + (float)window.getScaledWidth() / 2.0F);
                        double y2 = (double)(30.0F * MathHelper.sin((float)Math.toRadians((double)angle)) + (float)window.getScaledHeight() / 2.0F);
                        GlStateManager.pushMatrix();
                        GlStateManager.disableBlend();
                        GlStateManager.translated(x2, y2, 0.0D);
                        Fonts.montserrat.drawCenteredText(e.getMatrixStack(), text, 0.0F, -15.0F, -1, 6.0F);
                        GlStateManager.rotatef(angle, 0.0F, 0.0F, 1.0F);
                        boolean color = true;
                        Pointers.drawTriangle(-4.0F, -1.0F, 4.0F, 7.0F, new Color(0, 0, 0, 32));
                        GlStateManager.enableBlend();
                        GlStateManager.popMatrix();
                    } else {
                        float textWith = Fonts.montserrat.getWidth(text, 8.0F);
                        float fontHeight = Fonts.montserrat.getHeight(8.0F);
                        float posX = vec2f.x - textWith / 2.0F;
                        float posY = vec2f.y - fontHeight / 2.0F;
                        float padding = 2.0F;
                        DisplayUtils.drawRectW((double)(posX - padding), (double)(posY - padding), (double)(padding + textWith + padding), (double)(padding + fontHeight + padding), ColorUtils.setAlpha(ColorUtils.getColor(90), 10));
                        Fonts.montserrat.drawText(e.getMatrixStack(), text, posX, posY, -1, 8.0F);
                    }
                }

            }
        }
    }

    public List<String> aliases() {
        return List.of("way");
    }
}