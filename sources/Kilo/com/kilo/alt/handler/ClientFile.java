package com.kilo.alt.handler;

import java.io.File;
import java.io.IOException;

/**
 * @author Foundry
 */
public interface ClientFile {
    String getName();

    String getExtension();

    File getFile();

    void load() throws IOException;

    void save() throws IOException;
}
