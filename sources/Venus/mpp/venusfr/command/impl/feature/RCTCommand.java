/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl.feature;

import java.util.Collections;
import java.util.List;
import mpp.venusfr.command.Command;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.MultiNamedCommand;
import mpp.venusfr.command.Parameters;
import mpp.venusfr.utils.client.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;

public class RCTCommand
implements Command,
MultiNamedCommand {
    private final Logger logger;
    private final Minecraft mc;

    @Override
    public void execute(Parameters parameters) {
        if (!ClientUtil.isConnectedToServer("funtime")) {
            this.logger.log("\u042d\u0442\u043e\u0442 RCT \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u0442\u043e\u043b\u044c\u043a\u043e \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0435 FunTime");
            return;
        }
        int n = this.getAnarchyServerNumber();
        if (n == -1) {
            this.logger.log("\u041d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u043f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u043d\u043e\u043c\u0435\u0440 \u0430\u043d\u0430\u0440\u0445\u0438\u0438.");
            return;
        }
        this.mc.player.sendChatMessage("/hub");
        try {
            Thread.sleep(400L);
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
        this.mc.player.sendChatMessage("/an" + n);
    }

    private int getAnarchyServerNumber() {
        String string;
        if (this.mc.ingameGUI.getTabList().header != null && (string = TextFormatting.getTextWithoutFormattingCodes(this.mc.ingameGUI.getTabList().header.getString())) != null && string.contains("\u0410\u043d\u0430\u0440\u0445\u0438\u044f-")) {
            return Integer.parseInt(string.split("\u0410\u043d\u0430\u0440\u0445\u0438\u044f-")[1].trim());
        }
        return 1;
    }

    @Override
    public String name() {
        return "rct";
    }

    @Override
    public String description() {
        return "\u041f\u0435\u0440\u0435\u0437\u0430\u0445\u043e\u0434\u0438\u0442 \u043d\u0430 \u0430\u043d\u0430\u0440\u0445\u0438\u044e";
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList("reconnect");
    }

    public RCTCommand(Logger logger, Minecraft minecraft) {
        this.logger = logger;
        this.mc = minecraft;
    }
}

