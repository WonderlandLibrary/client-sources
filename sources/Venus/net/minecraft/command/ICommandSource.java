/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import java.util.UUID;
import net.minecraft.util.text.ITextComponent;

public interface ICommandSource {
    public static final ICommandSource DUMMY = new ICommandSource(){

        @Override
        public void sendMessage(ITextComponent iTextComponent, UUID uUID) {
        }

        @Override
        public boolean shouldReceiveFeedback() {
            return true;
        }

        @Override
        public boolean shouldReceiveErrors() {
            return true;
        }

        @Override
        public boolean allowLogging() {
            return true;
        }
    };

    public void sendMessage(ITextComponent var1, UUID var2);

    public boolean shouldReceiveFeedback();

    public boolean shouldReceiveErrors();

    public boolean allowLogging();
}

