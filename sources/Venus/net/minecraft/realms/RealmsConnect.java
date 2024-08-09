/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms;

import com.mojang.realmsclient.dto.RealmsServer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.login.ClientLoginNetHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.handshake.client.CHandshakePacket;
import net.minecraft.network.login.client.CLoginStartPacket;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsConnect {
    private static final Logger field_230719_a_ = LogManager.getLogger();
    private final Screen field_230720_b_;
    private volatile boolean field_230721_c_;
    private NetworkManager field_230722_d_;

    public RealmsConnect(Screen screen) {
        this.field_230720_b_ = screen;
    }

    public void func_244798_a(RealmsServer realmsServer, String string, int n) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.setConnectedToRealms(false);
        RealmsNarratorHelper.func_239550_a_(I18n.format("mco.connect.success", new Object[0]));
        new Thread(this, "Realms-connect-task", string, n, minecraft, realmsServer){
            final String val$p_244798_2_;
            final int val$p_244798_3_;
            final Minecraft val$minecraft;
            final RealmsServer val$p_244798_1_;
            final RealmsConnect this$0;
            {
                this.this$0 = realmsConnect;
                this.val$p_244798_2_ = string2;
                this.val$p_244798_3_ = n;
                this.val$minecraft = minecraft;
                this.val$p_244798_1_ = realmsServer;
                super(string);
            }

            @Override
            public void run() {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getByName(this.val$p_244798_2_);
                    if (this.this$0.field_230721_c_) {
                        return;
                    }
                    this.this$0.field_230722_d_ = NetworkManager.createNetworkManagerAndConnect(inetAddress, this.val$p_244798_3_, this.val$minecraft.gameSettings.isUsingNativeTransport());
                    if (this.this$0.field_230721_c_) {
                        return;
                    }
                    this.this$0.field_230722_d_.setNetHandler(new ClientLoginNetHandler(this.this$0.field_230722_d_, this.val$minecraft, this.this$0.field_230720_b_, 1::lambda$run$0));
                    if (this.this$0.field_230721_c_) {
                        return;
                    }
                    this.this$0.field_230722_d_.sendPacket(new CHandshakePacket(this.val$p_244798_2_, this.val$p_244798_3_, ProtocolType.LOGIN));
                    if (this.this$0.field_230721_c_) {
                        return;
                    }
                    this.this$0.field_230722_d_.sendPacket(new CLoginStartPacket(this.val$minecraft.getSession().getProfile()));
                    this.val$minecraft.setServerData(this.val$p_244798_1_.func_244783_d(this.val$p_244798_2_));
                } catch (UnknownHostException unknownHostException) {
                    this.val$minecraft.getPackFinder().clearResourcePack();
                    if (this.this$0.field_230721_c_) {
                        return;
                    }
                    field_230719_a_.error("Couldn't connect to world", (Throwable)unknownHostException);
                    DisconnectedRealmsScreen disconnectedRealmsScreen = new DisconnectedRealmsScreen(this.this$0.field_230720_b_, DialogTexts.CONNECTION_FAILED, new TranslationTextComponent("disconnect.genericReason", "Unknown host '" + this.val$p_244798_2_ + "'"));
                    this.val$minecraft.execute(() -> 1.lambda$run$1(this.val$minecraft, disconnectedRealmsScreen));
                } catch (Exception exception) {
                    Object object;
                    this.val$minecraft.getPackFinder().clearResourcePack();
                    if (this.this$0.field_230721_c_) {
                        return;
                    }
                    field_230719_a_.error("Couldn't connect to world", (Throwable)exception);
                    String string = exception.toString();
                    if (inetAddress != null) {
                        object = inetAddress + ":" + this.val$p_244798_3_;
                        string = string.replaceAll((String)object, "");
                    }
                    object = new DisconnectedRealmsScreen(this.this$0.field_230720_b_, DialogTexts.CONNECTION_FAILED, new TranslationTextComponent("disconnect.genericReason", string));
                    this.val$minecraft.execute(() -> 1.lambda$run$2(this.val$minecraft, (DisconnectedRealmsScreen)object));
                }
            }

            private static void lambda$run$2(Minecraft minecraft, DisconnectedRealmsScreen disconnectedRealmsScreen) {
                minecraft.displayGuiScreen(disconnectedRealmsScreen);
            }

            private static void lambda$run$1(Minecraft minecraft, DisconnectedRealmsScreen disconnectedRealmsScreen) {
                minecraft.displayGuiScreen(disconnectedRealmsScreen);
            }

            private static void lambda$run$0(ITextComponent iTextComponent) {
            }
        }.start();
    }

    public void func_231396_a_() {
        this.field_230721_c_ = true;
        if (this.field_230722_d_ != null && this.field_230722_d_.isChannelOpen()) {
            this.field_230722_d_.closeChannel(new TranslationTextComponent("disconnect.genericReason"));
            this.field_230722_d_.handleDisconnection();
        }
    }

    public void func_231398_b_() {
        if (this.field_230722_d_ != null) {
            if (this.field_230722_d_.isChannelOpen()) {
                this.field_230722_d_.tick();
            } else {
                this.field_230722_d_.handleDisconnection();
            }
        }
    }
}

