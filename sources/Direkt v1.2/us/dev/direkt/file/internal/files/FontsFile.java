package us.dev.direkt.file.internal.files;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import us.dev.direkt.file.internal.AbstractClientFile;
import us.dev.direkt.file.internal.FileData;
import us.dev.direkt.gui.font.FontManager;

import java.io.*;

/**
 * @author Foundry
 */
@FileData(fileName = "fonts")
public class FontsFile extends AbstractClientFile {
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setPrettyPrinting()
            .create();

    @Override
    public void load() throws IOException {
        try (Reader reader = new BufferedReader(new FileReader(this.getFile()))) {
            final String savedFont = gson.fromJson(reader, String.class);
            FontManager.setFont(savedFont);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() throws IOException {
        try {
            Files.write(gson.toJson(FontManager.getFontName()).getBytes("UTF-8"), this.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
