package net.minecraft.client.resources;

import java.io.*;

static final class ResourcePackRepository$1 implements FileFilter {
    @Override
    public boolean accept(final File p_accept_1_) {
        final boolean var2 = p_accept_1_.isFile() && p_accept_1_.getName().endsWith(".zip");
        final boolean var3 = p_accept_1_.isDirectory() && new File(p_accept_1_, "pack.mcmeta").isFile();
        return var2 || var3;
    }
}