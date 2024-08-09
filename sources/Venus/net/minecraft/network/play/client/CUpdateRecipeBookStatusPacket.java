/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CUpdateRecipeBookStatusPacket
implements IPacket<IServerPlayNetHandler> {
    private RecipeBookCategory field_244314_a;
    private boolean field_244315_b;
    private boolean field_244316_c;

    public CUpdateRecipeBookStatusPacket() {
    }

    public CUpdateRecipeBookStatusPacket(RecipeBookCategory recipeBookCategory, boolean bl, boolean bl2) {
        this.field_244314_a = recipeBookCategory;
        this.field_244315_b = bl;
        this.field_244316_c = bl2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_244314_a = packetBuffer.readEnumValue(RecipeBookCategory.class);
        this.field_244315_b = packetBuffer.readBoolean();
        this.field_244316_c = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.field_244314_a);
        packetBuffer.writeBoolean(this.field_244315_b);
        packetBuffer.writeBoolean(this.field_244316_c);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.func_241831_a(this);
    }

    public RecipeBookCategory func_244317_b() {
        return this.field_244314_a;
    }

    public boolean func_244318_c() {
        return this.field_244315_b;
    }

    public boolean func_244319_d() {
        return this.field_244316_c;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

