/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms.action;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.LongRunningTask;
import net.minecraft.util.text.TranslationTextComponent;

public class SwitchMinigameRealmsAction
extends LongRunningTask {
    private final long field_238145_c_;
    private final int field_238146_d_;
    private final Runnable field_238147_e_;

    public SwitchMinigameRealmsAction(long l, int n, Runnable runnable) {
        this.field_238145_c_ = l;
        this.field_238146_d_ = n;
        this.field_238147_e_ = runnable;
    }

    @Override
    public void run() {
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        this.func_224989_b(new TranslationTextComponent("mco.minigame.world.slot.screen.title"));
        for (int i = 0; i < 25; ++i) {
            try {
                if (this.func_224988_a()) {
                    return;
                }
                if (!realmsClient.func_224927_a(this.field_238145_c_, this.field_238146_d_)) continue;
                this.field_238147_e_.run();
                break;
            } catch (RetryCallException retryCallException) {
                if (this.func_224988_a()) {
                    return;
                }
                SwitchMinigameRealmsAction.func_238125_a_(retryCallException.field_224985_e);
                continue;
            } catch (Exception exception) {
                if (this.func_224988_a()) {
                    return;
                }
                field_238124_a_.error("Couldn't switch world!");
                this.func_237703_a_(exception.toString());
            }
        }
    }
}

