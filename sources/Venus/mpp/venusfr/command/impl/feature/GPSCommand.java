/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl.feature;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mpp.venusfr.command.Command;
import mpp.venusfr.command.CommandWithAdvice;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.MultiNamedCommand;
import mpp.venusfr.command.Parameters;
import mpp.venusfr.command.Prefix;
import mpp.venusfr.command.impl.CommandException;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.render.Pointers;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.projections.ProjectionUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.TextFormatting;

public class GPSCommand
implements Command,
CommandWithAdvice,
MultiNamedCommand,
IMinecraft {
    private final Prefix prefix;
    private final Logger logger;
    private final Map<String, Vector3i> waysMap = new LinkedHashMap<String, Vector3i>();

    public GPSCommand(Prefix prefix, Logger logger) {
        this.prefix = prefix;
        this.logger = logger;
        venusfr.getInstance().getEventBus().register(this);
    }

    @Override
    public void execute(Parameters parameters) {
        String string;
        switch (string = parameters.asString(0).orElse("")) {
            case "add": {
                this.addGPS(parameters);
                break;
            }
            case "remove": {
                this.removeGPS(parameters);
                break;
            }
            case "clear": {
                this.waysMap.clear();
                this.logger.log("\u0412\u0441\u0435 \u043f\u0443\u0442\u0438 \u0431\u044b\u043b\u0438 \u0443\u0434\u0430\u043b\u0435\u043d\u044b!");
                break;
            }
            case "list": {
                this.logger.log("\u0421\u043f\u0438\u0441\u043e\u043a \u043f\u0443\u0442\u0435\u0439:");
                for (String string2 : this.waysMap.keySet()) {
                    this.logger.log("- " + string2 + " " + this.waysMap.get(string2));
                }
                break;
            }
            default: {
                throw new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0442\u0438\u043f \u043a\u043e\u043c\u0430\u043d\u0434\u044b:" + TextFormatting.GRAY + " add, remove, clear");
            }
        }
    }

    private void addGPS(Parameters parameters) {
        String string = parameters.asString(1).orElseThrow(GPSCommand::lambda$addGPS$0);
        int n = parameters.asInt(2).orElseThrow(GPSCommand::lambda$addGPS$1);
        int n2 = parameters.asInt(3).orElseThrow(GPSCommand::lambda$addGPS$2);
        int n3 = parameters.asInt(4).orElseThrow(GPSCommand::lambda$addGPS$3);
        Vector3i vector3i = new Vector3i(n, n2, n3);
        this.waysMap.put(string, vector3i);
        this.logger.log("\u041f\u0443\u0442\u044c " + string + " \u0431\u044b\u043b \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d!");
    }

    private void removeGPS(Parameters parameters) {
        String string = parameters.asString(1).orElseThrow(GPSCommand::lambda$removeGPS$4);
        this.waysMap.remove(string);
        this.logger.log("\u041f\u0443\u0442\u044c " + string + " \u0431\u044b\u043b \u0443\u0434\u0430\u043b\u0451\u043d!");
    }

    @Override
    public String name() {
        return "gps";
    }

    @Override
    public String description() {
        return "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0440\u0430\u0431\u043e\u0442\u0430\u0442\u044c \u0441 \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u0430\u043c\u0438 \u043f\u0443\u0442\u0435\u0439";
    }

    @Override
    public List<String> adviceMessage() {
        String string = this.prefix.get();
        return List.of((Object)(string + "gps add <\u0438\u043c\u044f, x, y, z> - \u041f\u0440\u043e\u043b\u043e\u0436\u0438\u0442\u044c \u043f\u0443\u0442\u044c \u043a WayPoint'\u0443"), (Object)(string + "gps remove <\u0438\u043c\u044f> - \u0423\u0434\u0430\u043b\u0438\u0442\u044c WayPoint"), (Object)(string + "gps list - \u0421\u043f\u0438\u0441\u043e\u043a WayPoint'\u043e\u0432"), (Object)(string + "gps clear - \u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a WayPoint'\u043e\u0432"), (Object)("\u041f\u0440\u0438\u043c\u0435\u0440: " + TextFormatting.RED + string + "gps add \u0430\u0438\u0440\u0434\u0440\u043e\u043f 1000 100 1000"));
    }

    @Subscribe
    private void onDisplay(EventDisplay eventDisplay) {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        if (this.waysMap.isEmpty()) {
            return;
        }
        for (String string : this.waysMap.keySet()) {
            Vector3i vector3i = this.waysMap.get(string);
            Vector3d vector3d = new Vector3d((double)vector3i.getX() + 0.5, (double)vector3i.getY() + 0.5, (double)vector3i.getZ() + 0.5);
            Vector2f vector2f = ProjectionUtil.project(vector3d.x, vector3d.y, vector3d.z);
            int n = (int)Minecraft.getInstance().player.getPositionVec().distanceTo(vector3d);
            String string2 = string + " (" + n + "M)";
            if (vector2f.equals(new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE))) {
                Vector3d vector3d2 = vector3d.subtract(GPSCommand.mc.getRenderManager().info.getProjectedView());
                double d = vector3d2.getX();
                double d2 = vector3d2.getZ();
                double d3 = MathHelper.cos((float)((double)GPSCommand.mc.getRenderManager().info.getYaw() * (Math.PI / 180)));
                double d4 = MathHelper.sin((float)((double)GPSCommand.mc.getRenderManager().info.getYaw() * (Math.PI / 180)));
                double d5 = -(d2 * d3 - d * d4);
                double d6 = -(d * d3 + d2 * d4);
                float f = (float)(Math.atan2(d5, d6) * 180.0 / Math.PI);
                double d7 = 30.0f * MathHelper.cos((float)Math.toRadians(f)) + (float)window.getScaledWidth() / 2.0f;
                double d8 = 30.0f * MathHelper.sin((float)Math.toRadians(f)) + (float)window.getScaledHeight() / 2.0f;
                GlStateManager.pushMatrix();
                GlStateManager.disableBlend();
                GlStateManager.translated(d7, d8, 0.0);
                Fonts.montserrat.drawCenteredText(eventDisplay.getMatrixStack(), string2, 0.0f, -15.0f, -1, 6.0f);
                GlStateManager.rotatef(f, 0.0f, 0.0f, 1.0f);
                int n2 = -1;
                DisplayUtils.drawShadowCircle(1.0f, 0.0f, 10.0f, ColorUtils.setAlpha(n2, 64));
                Pointers.drawTriangle(-4.0f, -1.0f, 4.0f, 7.0f, new Color(0, 0, 0, 32));
                Pointers.drawTriangle(-3.0f, 0.0f, 3.0f, 5.0f, new Color(n2));
                GlStateManager.enableBlend();
                GlStateManager.popMatrix();
                continue;
            }
            float f = Fonts.montserrat.getWidth(string2, 8.0f);
            float f2 = Fonts.montserrat.getHeight(8.0f);
            float f3 = vector2f.x - f / 2.0f;
            float f4 = vector2f.y - f2 / 2.0f;
            float f5 = 2.0f;
            DisplayUtils.drawRectW(f3 - f5, f4 - f5, f5 + f + f5, f5 + f2 + f5, ColorUtils.rgba(0, 0, 0, 128));
            Fonts.montserrat.drawText(eventDisplay.getMatrixStack(), string2, f3, f4, -1, 8.0f);
        }
    }

    @Override
    public List<String> aliases() {
        return List.of((Object)"way");
    }

    private static CommandException lambda$removeGPS$4() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0438\u043c\u044f \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b!");
    }

    private static CommandException lambda$addGPS$3() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0442\u0440\u0435\u0442\u044c\u044e \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u0443!");
    }

    private static CommandException lambda$addGPS$2() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0432\u0442\u043e\u0440\u0443\u044e \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u0443!");
    }

    private static CommandException lambda$addGPS$1() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u043f\u0435\u0440\u0432\u0443\u044e \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u0443!");
    }

    private static CommandException lambda$addGPS$0() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0438\u043c\u044f \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b!");
    }
}

