package xyz.mathax.mathaxclient.utils.render;

import net.minecraft.client.util.math.MatrixStack;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.misc.NameProtect;
import xyz.mathax.mathaxclient.systems.proxies.Proxies;
import xyz.mathax.mathaxclient.systems.proxies.Proxy;
import xyz.mathax.mathaxclient.utils.render.color.Color;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class LoggedProxyText {
    public static void render(MatrixStack matrixStack) {
        double y = 2;
        double y2 = y + mc.textRenderer.fontHeight + y;

        String space = " ";
        int spaceLength = mc.textRenderer.getWidth(space);

        String loggedInAs = "Logged in as";
        int loggedInAsLength = mc.textRenderer.getWidth(loggedInAs);
        String loggedName = Modules.get().get(NameProtect.class).getName();

        mc.textRenderer.drawWithShadow(matrixStack, loggedInAs, 2, (int) y, Color.fromRGBA(Color.LIGHT_GRAY));
        mc.textRenderer.drawWithShadow(matrixStack, space, (loggedInAsLength + 2), (int) y, Color.fromRGBA(Color.LIGHT_GRAY));
        mc.textRenderer.drawWithShadow(matrixStack, loggedName, loggedInAsLength + spaceLength + 2, (int) y, Color.fromRGBA(Color.WHITE));

        Proxy proxy = Proxies.get().getEnabled();
        String proxyUsing = proxy != null ? "Using proxy" + " " : "Not using a proxy";
        mc.textRenderer.drawWithShadow(matrixStack, proxyUsing, 2, (int) y2, Color.fromRGBA(Color.LIGHT_GRAY));
        if (proxy != null) {
            int proxyUsingLength = mc.textRenderer.getWidth(proxyUsing);
            mc.textRenderer.drawWithShadow(matrixStack, "(" + proxy.nameSetting.get() + ") " + proxy.addressSetting.get() + ":" + proxy.portSetting.get(), 2 + proxyUsingLength, (int) y2, Color.fromRGBA(Color.WHITE));
        }
    }
}
