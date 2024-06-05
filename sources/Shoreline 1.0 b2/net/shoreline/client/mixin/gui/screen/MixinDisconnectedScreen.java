package net.shoreline.client.mixin.gui.screen;

import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;
import net.shoreline.client.mixin.accessor.AccessorClickableWidget;
import net.shoreline.client.util.Globals;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Mixin(DisconnectedScreen.class)
public abstract class MixinDisconnectedScreen extends MixinScreen implements Globals
{
    //
    @Shadow
    private int reasonHeight;
    //
    @Unique
    private long reconnectSeconds;

    /**
     *
     * @param ci
     */
    @Inject(method = "init", at = @At(value = "RETURN"))
    private void hookInit(CallbackInfo ci)
    {
        ButtonWidget.Builder reconnectButton = ButtonWidget.builder(Text.of("Reconnect"),
                (button) ->
                {
                    if (Managers.NETWORK.getAddress() != null && Managers.NETWORK.getInfo() != null)
                    {
                        ConnectScreen.connect((DisconnectedScreen) (Object) this, mc,
                                Managers.NETWORK.getAddress(), Managers.NETWORK.getInfo());
                    }
                });
        ButtonWidget.Builder autoReconnectButton = ButtonWidget.builder(
                Text.of("AutoReconnect"), (button) ->
                {
                    Modules.AUTO_RECONNECT.toggle();
                    if (Modules.AUTO_RECONNECT.isEnabled())
                    {
                        reconnectSeconds = Math.round(Modules.AUTO_RECONNECT.getDelay() * 20.0f);
                    }
                });
        addDrawableChild(reconnectButton.dimensions(width / 2 - 100,
                height / 2 + reasonHeight / 2 + mc.textRenderer.fontHeight + 72, 200, 20).build());
        addDrawableChild(autoReconnectButton.dimensions(width / 2 - 100,
                height / 2 + reasonHeight / 2 + mc.textRenderer.fontHeight + 96, 200, 20).build());
        if (Modules.AUTO_RECONNECT.isEnabled())
        {
            reconnectSeconds = Math.round(Modules.AUTO_RECONNECT.getDelay() * 20.0f);
        }
    }

    /**
     *
     */
    @Override
    public void tick()
    {
        super.tick();
        if (getDrawables().size() > 1)
        {
            if (Modules.AUTO_RECONNECT.isEnabled())
            {
                ((AccessorClickableWidget) getDrawables().get(2)).setMessage(
                        Text.of("AutoReconnect (" + (reconnectSeconds / 20 + 1) + ")"));
                if (reconnectSeconds > 0)
                {
                    --reconnectSeconds;
                }
                else if (Managers.NETWORK.getAddress() != null && Managers.NETWORK.getInfo() != null)
                {
                    ConnectScreen.connect((DisconnectedScreen) (Object) this, mc,
                            Managers.NETWORK.getAddress(), Managers.NETWORK.getInfo());
                }
            }
            else
            {
                ((AccessorClickableWidget) getDrawables().get(2)).setMessage(Text.of("AutoReconnect"));
            }
        }
    }
}
