/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms.action;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.LongRunningTask;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class CreateWorldRealmsAction
extends LongRunningTask {
    private final String field_238148_c_;
    private final String field_238149_d_;
    private final long field_238150_e_;
    private final Screen field_238151_f_;

    public CreateWorldRealmsAction(long l, String string, String string2, Screen screen) {
        this.field_238150_e_ = l;
        this.field_238148_c_ = string;
        this.field_238149_d_ = string2;
        this.field_238151_f_ = screen;
    }

    @Override
    public void run() {
        this.func_224989_b(new TranslationTextComponent("mco.create.world.wait"));
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        try {
            realmsClient.func_224900_a(this.field_238150_e_, this.field_238148_c_, this.field_238149_d_);
            CreateWorldRealmsAction.func_238127_a_(this.field_238151_f_);
        } catch (RealmsServiceException realmsServiceException) {
            field_238124_a_.error("Couldn't create world");
            this.func_237703_a_(realmsServiceException.toString());
        } catch (Exception exception) {
            field_238124_a_.error("Could not create world");
            this.func_237703_a_(exception.getLocalizedMessage());
        }
    }
}

