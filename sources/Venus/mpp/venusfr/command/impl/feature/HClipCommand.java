/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl.feature;

import java.util.List;
import mpp.venusfr.command.Command;
import mpp.venusfr.command.CommandWithAdvice;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.Parameters;
import mpp.venusfr.command.Prefix;
import mpp.venusfr.command.impl.CommandException;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.math.NumberUtils;

public class HClipCommand
implements Command,
CommandWithAdvice {
    private final Prefix prefix;
    private final Logger logger;
    private final Minecraft mc;

    @Override
    public void execute(Parameters parameters) {
        int n;
        String string = parameters.asString(0).orElseThrow(HClipCommand::lambda$execute$0);
        if (!NumberUtils.isNumber(string)) {
            this.logger.log(TextFormatting.RED + "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430, \u0432\u0432\u0435\u0434\u0438\u0442\u0435 \u0447\u0438\u0441\u043b\u043e \u0434\u043b\u044f \u044d\u0442\u043e\u0439 \u043a\u043e\u043c\u0430\u043d\u0434\u044b.");
            return;
        }
        double d = Double.parseDouble(string);
        Vector3d vector3d = Minecraft.getInstance().player.getLook(1.0f).mul(d, 0.0, d);
        double d2 = this.mc.player.getPosX() + vector3d.getX();
        double d3 = this.mc.player.getPosZ() + vector3d.getZ();
        for (n = 0; n < 5; ++n) {
            this.mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(d2, this.mc.player.getPosY(), d3, false));
        }
        this.mc.player.setPositionAndUpdate(d2, this.mc.player.getPosY(), d3);
        for (n = 0; n < 5; ++n) {
            this.mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(d2, this.mc.player.getPosY(), d3, false));
        }
        String string2 = d > 1.0 ? "\u0431\u043b\u043e\u043a\u0430" : "\u0431\u043b\u043e\u043a";
        String string3 = String.format("\u0412\u044b \u0431\u044b\u043b\u0438 \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u044b \u043d\u0430 %.1f %s \u043f\u043e \u0433\u043e\u0440\u0438\u0437\u043e\u043d\u0442\u0430\u043b\u0438", d, string2);
        this.logger.log(TextFormatting.GRAY + string3);
    }

    @Override
    public String name() {
        return "hclip";
    }

    @Override
    public String description() {
        return "\u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u0443\u0435\u0442 \u0432\u043f\u0435\u0440\u0451\u0434/\u043d\u0430\u0437\u0430\u0434 \u043f\u043e \u0433\u043e\u0440\u0438\u0437\u043e\u043d\u0442\u0430\u043b\u0438";
    }

    @Override
    public List<String> adviceMessage() {
        String string = this.prefix.get();
        return List.of((Object)(TextFormatting.GRAY + string + "hclip <distance> - \u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0430\u0446\u0438\u044f \u043d\u0430 \u0443\u043a\u0430\u0437\u0430\u043d\u043d\u043e\u0435 \u0440\u0430\u0441\u0441\u0442\u043e\u044f\u043d\u0438\u0435"), (Object)("\u041f\u0440\u0438\u043c\u0435\u0440: " + TextFormatting.RED + string + "hclip 1"));
    }

    public HClipCommand(Prefix prefix, Logger logger, Minecraft minecraft) {
        this.prefix = prefix;
        this.logger = logger;
        this.mc = minecraft;
    }

    private static CommandException lambda$execute$0() {
        return new CommandException(TextFormatting.RED + "\u041d\u0435\u043e\u0431\u0445\u043e\u0434\u0438\u043c\u043e \u0443\u043a\u0430\u0437\u0430\u0442\u044c \u0440\u0430\u0441\u0441\u0442\u043e\u044f\u043d\u0438\u0435.");
    }
}

