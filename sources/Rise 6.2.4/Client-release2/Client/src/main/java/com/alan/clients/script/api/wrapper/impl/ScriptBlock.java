package com.alan.clients.script.api.wrapper.impl;

import com.alan.clients.script.api.wrapper.ScriptWrapper;
import net.minecraft.block.Block;

public class ScriptBlock extends ScriptWrapper<Block> {

    public ScriptBlock(final Block wrapped) {
        super(wrapped);
    }

    public int getId() {
        return Block.getIdFromBlock(this.wrapped);
    }

    public String getName() {
        return this.wrapped.getLocalizedName();
    }

    public boolean isOpaque() {
        return this.wrapped.isOpaqueCube();
    }

    public boolean isFullBlock() {
        return this.wrapped.isFullBlock();
    }
}
