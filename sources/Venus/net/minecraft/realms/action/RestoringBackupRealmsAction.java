/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms.action;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.Backup;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.LongRunningTask;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class RestoringBackupRealmsAction
extends LongRunningTask {
    private final Backup field_238139_c_;
    private final long field_238140_d_;
    private final RealmsConfigureWorldScreen field_238141_e_;

    public RestoringBackupRealmsAction(Backup backup, long l, RealmsConfigureWorldScreen realmsConfigureWorldScreen) {
        this.field_238139_c_ = backup;
        this.field_238140_d_ = l;
        this.field_238141_e_ = realmsConfigureWorldScreen;
    }

    @Override
    public void run() {
        this.func_224989_b(new TranslationTextComponent("mco.backup.restoring"));
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        for (int i = 0; i < 25; ++i) {
            try {
                if (this.func_224988_a()) {
                    return;
                }
                realmsClient.func_224928_c(this.field_238140_d_, this.field_238139_c_.field_230553_a_);
                RestoringBackupRealmsAction.func_238125_a_(1);
                if (this.func_224988_a()) {
                    return;
                }
                RestoringBackupRealmsAction.func_238127_a_(this.field_238141_e_.func_224407_b());
                return;
            } catch (RetryCallException retryCallException) {
                if (this.func_224988_a()) {
                    return;
                }
                RestoringBackupRealmsAction.func_238125_a_(retryCallException.field_224985_e);
                continue;
            } catch (RealmsServiceException realmsServiceException) {
                if (this.func_224988_a()) {
                    return;
                }
                field_238124_a_.error("Couldn't restore backup", (Throwable)realmsServiceException);
                RestoringBackupRealmsAction.func_238127_a_(new RealmsGenericErrorScreen(realmsServiceException, (Screen)this.field_238141_e_));
                return;
            } catch (Exception exception) {
                if (this.func_224988_a()) {
                    return;
                }
                field_238124_a_.error("Couldn't restore backup", (Throwable)exception);
                this.func_237703_a_(exception.getLocalizedMessage());
                return;
            }
        }
    }
}

