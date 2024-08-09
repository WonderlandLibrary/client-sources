package xyz.mathax.mathaxclient.utils.notebot.decoder;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.misc.Notebot;
import xyz.mathax.mathaxclient.utils.notebot.song.Song;

import java.io.File;

public abstract class SongDecoder {
    protected Notebot notebot = Modules.get().get(Notebot.class);

    public abstract Song parse(File file);
}