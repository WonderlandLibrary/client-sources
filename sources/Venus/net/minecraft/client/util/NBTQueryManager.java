/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.client.CQueryEntityNBTPacket;
import net.minecraft.network.play.client.CQueryTileEntityNBTPacket;
import net.minecraft.util.math.BlockPos;

public class NBTQueryManager {
    private final ClientPlayNetHandler connection;
    private int transactionId = -1;
    @Nullable
    private Consumer<CompoundNBT> handler;

    public NBTQueryManager(ClientPlayNetHandler clientPlayNetHandler) {
        this.connection = clientPlayNetHandler;
    }

    public boolean handleResponse(int n, @Nullable CompoundNBT compoundNBT) {
        if (this.transactionId == n && this.handler != null) {
            this.handler.accept(compoundNBT);
            this.handler = null;
            return false;
        }
        return true;
    }

    private int setHandler(Consumer<CompoundNBT> consumer) {
        this.handler = consumer;
        return ++this.transactionId;
    }

    public void queryEntity(int n, Consumer<CompoundNBT> consumer) {
        int n2 = this.setHandler(consumer);
        this.connection.sendPacket(new CQueryEntityNBTPacket(n2, n));
    }

    public void queryTileEntity(BlockPos blockPos, Consumer<CompoundNBT> consumer) {
        int n = this.setHandler(consumer);
        this.connection.sendPacket(new CQueryTileEntityNBTPacket(n, blockPos));
    }
}

