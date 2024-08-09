package wtf.shiyeno.ui.proxy;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.net.InetSocketAddress;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.proxy.ProxyConnection;
import wtf.shiyeno.proxy.ProxyType;
import wtf.shiyeno.util.font.Fonts;

public class ProxyUI extends Screen {
    ProxyConnection pc;
    private TextFieldWidget proxy;

    public ProxyUI() {
        super(new StringTextComponent(""));
        this.pc = Managment.PROXY_CONN;
    }

    protected void init() {
        super.init();
        float[] center = new float[]{(float)this.width / 2.0F, (float)this.height / 2.0F};
        this.addButton(new Button((int)center[0] - 100 - 5, (int)center[1] + 30, 100, 20, new TranslationTextComponent("Применить"), (ppp) -> {
            this.parse(this.proxy.getText());
        }));
        this.addButton(new Button((int)center[0] + 5, (int)center[1] + 30, 100, 20, new TranslationTextComponent("Назад"), (ppp) -> {
            Minecraft.getInstance().displayGuiScreen(new MultiplayerScreen((Screen)null));
        }));
        this.proxy = new TextFieldWidget(this.font, (int)center[0] - 100, (int)center[1] - 10, 200, 20, new TranslationTextComponent("Ваш прокси"));
        this.proxy.setMaxStringLength(32);
        this.children.add(this.proxy);
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        String cProxy = this.pc.getProxyAddr() != null ? this.pc.getProxyType().name().toLowerCase(Locale.ROOT) + "://" + this.pc.getProxyAddr().getHostString() + ":" + this.pc.getProxyAddr().getPort() : "БЕЗ ПРОКСИ";
        float[] center = new float[]{(float)this.width / 2.0F, (float)this.height / 2.0F};
        Fonts.gilroyBold[14].drawCenteredString(matrixStack, "Активный прокси: " + cProxy, (double)center[0], (double)(center[1] - 30.0F), -1);
        Fonts.gilroyBold[14].drawCenteredString(matrixStack, "Пример: socks4://123.123.123.123:1234", (double)center[0], (double)(center[1] + 60.0F), -1);
        this.proxy.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void parse(String input) {
        input = input.toLowerCase(Locale.ROOT);

        try {
            ProxyType type = input.startsWith("http://") ? ProxyType.HTTP : (input.startsWith("socks4://") ? ProxyType.SOCKS4 : (input.startsWith("socks5://") ? ProxyType.SOCKS5 : ProxyType.DIRECT));
            String addr = input.split("//")[1];
            this.pc.setup(type, new InetSocketAddress(addr.split(":")[0], Integer.parseInt(addr.split(":")[1])));
        } catch (Exception var4) {
            this.pc.reset();
        }
    }

    public void tick() {
        super.tick();
        this.proxy.tick();
    }
}