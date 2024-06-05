package net.minecraft.world.chunk.storage;

import java.io.*;

class AnvilSaveConverter$1 implements FilenameFilter {
    @Override
    public boolean accept(final File p_accept_1_, final String p_accept_2_) {
        return p_accept_2_.endsWith(".mcr");
    }
}