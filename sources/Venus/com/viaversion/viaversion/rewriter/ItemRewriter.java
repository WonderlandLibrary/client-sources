/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends Protocol<C, ?, ?, S>>
extends RewriterBase<T>
implements com.viaversion.viaversion.api.rewriter.ItemRewriter<T> {
    protected ItemRewriter(T t) {
        super(t);
    }

    @Override
    public @Nullable Item handleItemToClient(@Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getNewItemId(item.identifier()));
        }
        return item;
    }

    @Override
    public @Nullable Item handleItemToServer(@Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getOldItemId(item.identifier()));
        }
        return item;
    }

    public void registerWindowItems(C c, Type<Item[]> type) {
        this.protocol.registerClientbound(c, new PacketHandlers(this, type){
            final Type val$type;
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
                this.val$type = type;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(this.val$type);
                this.handler(this.this$0.itemArrayHandler(this.val$type));
            }
        });
    }

    public void registerWindowItems1_17_1(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Item[] itemArray;
                for (Item item : itemArray = packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT)) {
                    this.this$0.handleItemToClient(item);
                }
                this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
    }

    public void registerOpenWindow(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                int n2 = ItemRewriter.access$000(this.this$0).getMappingData().getMenuMappings().getNewId(n);
                if (n2 == -1) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.write(Type.VAR_INT, n2);
            }
        });
    }

    public void registerSetSlot(C c, Type<Item> type) {
        this.protocol.registerClientbound(c, new PacketHandlers(this, type){
            final Type val$type;
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
                this.val$type = type;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(this.val$type);
                this.handler(this.this$0.itemToClientHandler(this.val$type));
            }
        });
    }

    public void registerSetSlot1_17_1(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.SHORT);
                this.map(Type.FLAT_VAR_INT_ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.FLAT_VAR_INT_ITEM));
            }
        });
    }

    public void registerEntityEquipment(C c, Type<Item> type) {
        this.protocol.registerClientbound(c, new PacketHandlers(this, type){
            final Type val$type;
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
                this.val$type = type;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(this.val$type);
                this.handler(this.this$0.itemToClientHandler(this.val$type));
            }
        });
    }

    public void registerEntityEquipmentArray(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by;
                do {
                    by = packetWrapper.passthrough(Type.BYTE);
                    this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                } while ((by & 0xFFFFFF80) != 0);
            }
        });
    }

    public void registerCreativeInvAction(S s, Type<Item> type) {
        this.protocol.registerServerbound(s, new PacketHandlers(this, type){
            final Type val$type;
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
                this.val$type = type;
            }

            @Override
            public void register() {
                this.map(Type.SHORT);
                this.map(this.val$type);
                this.handler(this.this$0.itemToServerHandler(this.val$type));
            }
        });
    }

    public void registerClickWindow(S s, Type<Item> type) {
        this.protocol.registerServerbound(s, new PacketHandlers(this, type){
            final Type val$type;
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
                this.val$type = type;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(this.val$type);
                this.handler(this.this$0.itemToServerHandler(this.val$type));
            }
        });
    }

    public void registerClickWindow1_17_1(S s) {
        this.protocol.registerServerbound(s, new PacketHandlers(this){
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < n; ++i) {
                    packetWrapper.passthrough(Type.SHORT);
                    this.this$0.handleItemToServer(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                }
                this.this$0.handleItemToServer(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
    }

    public void registerSetCooldown(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerSetCooldown$0);
    }

    public void registerTradeList(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerTradeList$1);
    }

    public void registerTradeList1_19(C c) {
        this.protocol.registerClientbound(c, this::lambda$registerTradeList1_19$2);
    }

    public void registerAdvancements(C c, Type<Item> type) {
        this.protocol.registerClientbound(c, arg_0 -> this.lambda$registerAdvancements$3(type, arg_0));
    }

    public void registerWindowPropertyEnchantmentHandler(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Mappings mappings = ItemRewriter.access$100(this.this$0).getMappingData().getEnchantmentMappings();
                if (mappings == null) {
                    return;
                }
                short s = packetWrapper.passthrough(Type.SHORT);
                if (s >= 4 && s <= 6) {
                    short s2 = (short)mappings.getNewId(packetWrapper.read(Type.SHORT).shortValue());
                    packetWrapper.write(Type.SHORT, s2);
                }
            }
        });
    }

    public void registerSpawnParticle(C c, Type<Item> type, Type<?> type2) {
        this.protocol.registerClientbound(c, new PacketHandlers(this, type2, type){
            final Type val$coordType;
            final Type val$itemType;
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
                this.val$coordType = type;
                this.val$itemType = type2;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(this.val$coordType);
                this.map(this.val$coordType);
                this.map(this.val$coordType);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(this.this$0.getSpawnParticleHandler(this.val$itemType));
            }
        });
    }

    public void registerSpawnParticle1_19(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final ItemRewriter this$0;
            {
                this.this$0 = itemRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(this.this$0.getSpawnParticleHandler(Type.VAR_INT, Type.FLAT_VAR_INT_ITEM));
            }
        });
    }

    public PacketHandler getSpawnParticleHandler(Type<Item> type) {
        return this.getSpawnParticleHandler(Type.INT, type);
    }

    public PacketHandler getSpawnParticleHandler(Type<Integer> type, Type<Item> type2) {
        return arg_0 -> this.lambda$getSpawnParticleHandler$4(type, type2, arg_0);
    }

    public PacketHandler itemArrayHandler(Type<Item[]> type) {
        return arg_0 -> this.lambda$itemArrayHandler$5(type, arg_0);
    }

    public PacketHandler itemToClientHandler(Type<Item> type) {
        return arg_0 -> this.lambda$itemToClientHandler$6(type, arg_0);
    }

    public PacketHandler itemToServerHandler(Type<Item> type) {
        return arg_0 -> this.lambda$itemToServerHandler$7(type, arg_0);
    }

    private void lambda$itemToServerHandler$7(Type type, PacketWrapper packetWrapper) throws Exception {
        this.handleItemToServer((Item)packetWrapper.get(type, 0));
    }

    private void lambda$itemToClientHandler$6(Type type, PacketWrapper packetWrapper) throws Exception {
        this.handleItemToClient((Item)packetWrapper.get(type, 0));
    }

    private void lambda$itemArrayHandler$5(Type type, PacketWrapper packetWrapper) throws Exception {
        Item[] itemArray;
        for (Item item : itemArray = (Item[])packetWrapper.get(type, 0)) {
            this.handleItemToClient(item);
        }
    }

    private void lambda$getSpawnParticleHandler$4(Type type, Type type2, PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2 = (Integer)packetWrapper.get(type, 0);
        if (n2 == -1) {
            return;
        }
        ParticleMappings particleMappings = this.protocol.getMappingData().getParticleMappings();
        if (particleMappings.isBlockParticle(n2)) {
            n = packetWrapper.read(Type.VAR_INT);
            packetWrapper.write(Type.VAR_INT, this.protocol.getMappingData().getNewBlockStateId(n));
        } else if (particleMappings.isItemParticle(n2)) {
            this.handleItemToClient((Item)packetWrapper.passthrough(type2));
        }
        n = this.protocol.getMappingData().getNewParticleId(n2);
        if (n != n2) {
            packetWrapper.set(type, 0, n);
        }
    }

    private void lambda$registerAdvancements$3(Type type, PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.BOOLEAN);
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            int n2;
            packetWrapper.passthrough(Type.STRING);
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                packetWrapper.passthrough(Type.STRING);
            }
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                packetWrapper.passthrough(Type.COMPONENT);
                packetWrapper.passthrough(Type.COMPONENT);
                this.handleItemToClient((Item)packetWrapper.passthrough(type));
                packetWrapper.passthrough(Type.VAR_INT);
                n2 = packetWrapper.passthrough(Type.INT);
                if ((n2 & 1) != 0) {
                    packetWrapper.passthrough(Type.STRING);
                }
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.FLOAT);
            }
            packetWrapper.passthrough(Type.STRING_ARRAY);
            n2 = packetWrapper.passthrough(Type.VAR_INT);
            for (int j = 0; j < n2; ++j) {
                packetWrapper.passthrough(Type.STRING_ARRAY);
            }
        }
    }

    private void lambda$registerTradeList1_19$2(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            this.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            this.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            this.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            packetWrapper.passthrough(Type.BOOLEAN);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.FLOAT);
            packetWrapper.passthrough(Type.INT);
        }
    }

    private void lambda$registerTradeList$1(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
        int n = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
        for (int i = 0; i < n; ++i) {
            this.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            this.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                this.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
            packetWrapper.passthrough(Type.BOOLEAN);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.FLOAT);
            packetWrapper.passthrough(Type.INT);
        }
    }

    private void lambda$registerSetCooldown$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        packetWrapper.write(Type.VAR_INT, this.protocol.getMappingData().getNewItemId(n));
    }

    static Protocol access$000(ItemRewriter itemRewriter) {
        return itemRewriter.protocol;
    }

    static Protocol access$100(ItemRewriter itemRewriter) {
        return itemRewriter.protocol;
    }
}

