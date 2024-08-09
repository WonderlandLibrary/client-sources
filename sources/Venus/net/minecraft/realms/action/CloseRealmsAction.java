/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms.action;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.LongRunningTask;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import net.minecraft.util.text.TranslationTextComponent;

public class CloseRealmsAction
extends LongRunningTask {
    private final RealmsServer field_238107_c_;
    private final RealmsConfigureWorldScreen field_238108_d_;

    public CloseRealmsAction(RealmsServer realmsServer, RealmsConfigureWorldScreen realmsConfigureWorldScreen) {
        this.field_238107_c_ = realmsServer;
        this.field_238108_d_ = realmsConfigureWorldScreen;
    }

    @Override
    public void run() {
        this.func_224989_b(new TranslationTextComponent("mco.configure.world.closing"));
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        for (int i = 0; i < 25; ++i) {
            if (this.func_224988_a()) {
                return;
            }
            try {
                boolean bl = realmsClient.func_224932_f(this.field_238107_c_.field_230582_a_);
                if (!bl) continue;
                this.field_238108_d_.func_224398_a();
                this.field_238107_c_.field_230586_e_ = RealmsServer.Status.CLOSED;
                CloseRealmsAction.func_238127_a_(this.field_238108_d_);
                break;
            } catch (RetryCallException retryCallException) {
                if (this.func_224988_a()) {
                    return;
                }
                CloseRealmsAction.func_238125_a_(retryCallException.field_224985_e);
                continue;
            } catch (Exception exception) {
                if (this.func_224988_a()) {
                    return;
                }
                field_238124_a_.error("Failed to close server", (Throwable)exception);
                this.func_237703_a_("Failed to close the server");
            }
        }
    }
}

