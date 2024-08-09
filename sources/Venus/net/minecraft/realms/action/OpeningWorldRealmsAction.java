/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms.action;

import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.LongRunningTask;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class OpeningWorldRealmsAction
extends LongRunningTask {
    private final RealmsServer field_238128_c_;
    private final Screen field_238129_d_;
    private final boolean field_238130_e_;
    private final RealmsMainScreen field_238131_f_;

    public OpeningWorldRealmsAction(RealmsServer realmsServer, Screen screen, RealmsMainScreen realmsMainScreen, boolean bl) {
        this.field_238128_c_ = realmsServer;
        this.field_238129_d_ = screen;
        this.field_238130_e_ = bl;
        this.field_238131_f_ = realmsMainScreen;
    }

    @Override
    public void run() {
        this.func_224989_b(new TranslationTextComponent("mco.configure.world.opening"));
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        for (int i = 0; i < 25; ++i) {
            if (this.func_224988_a()) {
                return;
            }
            try {
                boolean bl = realmsClient.func_224942_e(this.field_238128_c_.field_230582_a_);
                if (!bl) continue;
                if (this.field_238129_d_ instanceof RealmsConfigureWorldScreen) {
                    ((RealmsConfigureWorldScreen)this.field_238129_d_).func_224398_a();
                }
                this.field_238128_c_.field_230586_e_ = RealmsServer.Status.OPEN;
                if (this.field_238130_e_) {
                    this.field_238131_f_.func_223911_a(this.field_238128_c_, this.field_238129_d_);
                    break;
                }
                OpeningWorldRealmsAction.func_238127_a_(this.field_238129_d_);
                break;
            } catch (RetryCallException retryCallException) {
                if (this.func_224988_a()) {
                    return;
                }
                OpeningWorldRealmsAction.func_238125_a_(retryCallException.field_224985_e);
                continue;
            } catch (Exception exception) {
                if (this.func_224988_a()) {
                    return;
                }
                field_238124_a_.error("Failed to open server", (Throwable)exception);
                this.func_237703_a_("Failed to open the server");
            }
        }
    }
}

