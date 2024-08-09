/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms.action;

import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerAddress;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.LongRunningTask;
import com.mojang.realmsclient.gui.screens.RealmsBrokenWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsTermsScreen;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.realms.action.ConnectedToRealmsAction;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ConnectingToRealmsAction
extends LongRunningTask {
    private final RealmsServer field_238116_c_;
    private final Screen field_238117_d_;
    private final RealmsMainScreen field_238118_e_;
    private final ReentrantLock field_238119_f_;

    public ConnectingToRealmsAction(RealmsMainScreen realmsMainScreen, Screen screen, RealmsServer realmsServer, ReentrantLock reentrantLock) {
        this.field_238117_d_ = screen;
        this.field_238118_e_ = realmsMainScreen;
        this.field_238116_c_ = realmsServer;
        this.field_238119_f_ = reentrantLock;
    }

    @Override
    public void run() {
        this.func_224989_b(new TranslationTextComponent("mco.connect.connecting"));
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        boolean bl = false;
        boolean bl2 = false;
        int n = 5;
        RealmsServerAddress realmsServerAddress = null;
        boolean bl3 = false;
        boolean bl4 = false;
        for (int i = 0; i < 40 && !this.func_224988_a(); ++i) {
            try {
                realmsServerAddress = realmsClient.func_224904_b(this.field_238116_c_.field_230582_a_);
                bl = true;
            } catch (RetryCallException retryCallException) {
                n = retryCallException.field_224985_e;
            } catch (RealmsServiceException realmsServiceException) {
                if (realmsServiceException.field_224983_c == 6002) {
                    bl3 = true;
                    break;
                }
                if (realmsServiceException.field_224983_c == 6006) {
                    bl4 = true;
                    break;
                }
                bl2 = true;
                this.func_237703_a_(realmsServiceException.toString());
                field_238124_a_.error("Couldn't connect to world", (Throwable)realmsServiceException);
                break;
            } catch (Exception exception) {
                bl2 = true;
                field_238124_a_.error("Couldn't connect to world", (Throwable)exception);
                this.func_237703_a_(exception.getLocalizedMessage());
                break;
            }
            if (bl) break;
            this.func_238123_b_(n);
        }
        if (bl3) {
            ConnectingToRealmsAction.func_238127_a_(new RealmsTermsScreen(this.field_238117_d_, this.field_238118_e_, this.field_238116_c_));
        } else if (bl4) {
            if (this.field_238116_c_.field_230588_g_.equals(Minecraft.getInstance().getSession().getPlayerID())) {
                ConnectingToRealmsAction.func_238127_a_(new RealmsBrokenWorldScreen(this.field_238117_d_, this.field_238118_e_, this.field_238116_c_.field_230582_a_, this.field_238116_c_.field_230594_m_ == RealmsServer.ServerType.MINIGAME));
            } else {
                ConnectingToRealmsAction.func_238127_a_(new RealmsGenericErrorScreen(new TranslationTextComponent("mco.brokenworld.nonowner.title"), new TranslationTextComponent("mco.brokenworld.nonowner.error"), this.field_238117_d_));
            }
        } else if (!this.func_224988_a() && !bl2) {
            if (bl) {
                RealmsServerAddress realmsServerAddress2 = realmsServerAddress;
                if (realmsServerAddress2.field_230602_b_ != null && realmsServerAddress2.field_230603_c_ != null) {
                    TranslationTextComponent translationTextComponent = new TranslationTextComponent("mco.configure.world.resourcepack.question.line1");
                    TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("mco.configure.world.resourcepack.question.line2");
                    ConnectingToRealmsAction.func_238127_a_(new RealmsLongConfirmationScreen(arg_0 -> this.lambda$run$2(realmsServerAddress2, arg_0), RealmsLongConfirmationScreen.Type.Info, translationTextComponent, translationTextComponent2, true));
                } else {
                    this.func_224987_a(new RealmsLongRunningMcoTaskScreen(this.field_238117_d_, new ConnectedToRealmsAction(this.field_238117_d_, this.field_238116_c_, realmsServerAddress2)));
                }
            } else {
                this.func_230434_a_(new TranslationTextComponent("mco.errorMessage.connectionFailure"));
            }
        }
    }

    private void func_238123_b_(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (InterruptedException interruptedException) {
            field_238124_a_.warn(interruptedException.getLocalizedMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void lambda$run$2(RealmsServerAddress realmsServerAddress, boolean bl) {
        try {
            if (bl) {
                Function<Throwable, Void> function = this::lambda$run$0;
                try {
                    ((CompletableFuture)Minecraft.getInstance().getPackFinder().downloadResourcePack(realmsServerAddress.field_230602_b_, realmsServerAddress.field_230603_c_).thenRun(() -> this.lambda$run$1(realmsServerAddress))).exceptionally(function);
                } catch (Exception exception) {
                    function.apply(exception);
                }
            } else {
                ConnectingToRealmsAction.func_238127_a_(this.field_238117_d_);
            }
        } finally {
            if (this.field_238119_f_ != null && this.field_238119_f_.isHeldByCurrentThread()) {
                this.field_238119_f_.unlock();
            }
        }
    }

    private void lambda$run$1(RealmsServerAddress realmsServerAddress) {
        this.func_224987_a(new RealmsLongRunningMcoTaskScreen(this.field_238117_d_, new ConnectedToRealmsAction(this.field_238117_d_, this.field_238116_c_, realmsServerAddress)));
    }

    private Void lambda$run$0(Throwable throwable) {
        Minecraft.getInstance().getPackFinder().clearResourcePack();
        field_238124_a_.error(throwable);
        ConnectingToRealmsAction.func_238127_a_(new RealmsGenericErrorScreen(new StringTextComponent("Failed to download resource pack!"), this.field_238117_d_));
        return null;
    }
}

