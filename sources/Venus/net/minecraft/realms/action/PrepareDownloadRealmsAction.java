/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms.action;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.LongRunningTask;
import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class PrepareDownloadRealmsAction
extends LongRunningTask {
    private final long field_238111_c_;
    private final int field_238112_d_;
    private final Screen field_238113_e_;
    private final String field_238114_f_;

    public PrepareDownloadRealmsAction(long l, int n, String string, Screen screen) {
        this.field_238111_c_ = l;
        this.field_238112_d_ = n;
        this.field_238113_e_ = screen;
        this.field_238114_f_ = string;
    }

    @Override
    public void run() {
        this.func_224989_b(new TranslationTextComponent("mco.download.preparing"));
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        for (int i = 0; i < 25; ++i) {
            try {
                if (this.func_224988_a()) {
                    return;
                }
                WorldDownload worldDownload = realmsClient.func_224917_b(this.field_238111_c_, this.field_238112_d_);
                PrepareDownloadRealmsAction.func_238125_a_(1);
                if (this.func_224988_a()) {
                    return;
                }
                PrepareDownloadRealmsAction.func_238127_a_(new RealmsDownloadLatestWorldScreen(this.field_238113_e_, worldDownload, this.field_238114_f_, PrepareDownloadRealmsAction::lambda$run$0));
                return;
            } catch (RetryCallException retryCallException) {
                if (this.func_224988_a()) {
                    return;
                }
                PrepareDownloadRealmsAction.func_238125_a_(retryCallException.field_224985_e);
                continue;
            } catch (RealmsServiceException realmsServiceException) {
                if (this.func_224988_a()) {
                    return;
                }
                field_238124_a_.error("Couldn't download world data");
                PrepareDownloadRealmsAction.func_238127_a_(new RealmsGenericErrorScreen(realmsServiceException, this.field_238113_e_));
                return;
            } catch (Exception exception) {
                if (this.func_224988_a()) {
                    return;
                }
                field_238124_a_.error("Couldn't download world data", (Throwable)exception);
                this.func_237703_a_(exception.getLocalizedMessage());
                return;
            }
        }
    }

    private static void lambda$run$0(boolean bl) {
    }
}

