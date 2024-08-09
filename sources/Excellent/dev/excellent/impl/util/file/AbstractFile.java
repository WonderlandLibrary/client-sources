package dev.excellent.impl.util.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AbstractFile {

    protected static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private final java.io.File file;
    private final FileType fileType;

    public abstract boolean read();

    public abstract boolean write();
}