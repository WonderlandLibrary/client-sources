package net.minecraft.src;

import paulscode.sound.codecs.*;
import java.io.*;

public class CodecMus extends CodecJOrbis
{
    @Override
    protected InputStream openInputStream() {
        try {
            return new MusInputStream(this, this.url, this.urlConnection.getInputStream());
        }
        catch (IOException var2) {
            return null;
        }
    }
}
