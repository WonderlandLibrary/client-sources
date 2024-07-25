package club.bluezenith.core.data.font;

import club.bluezenith.core.data.ClientResourceRepository;
import club.bluezenith.core.data.font.parsing.ConfigParser;
import club.bluezenith.core.data.font.parsing.ContentRetriever;
import club.bluezenith.core.data.font.parsing.ParseResult;
import club.bluezenith.util.font.TFontRenderer;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static club.bluezenith.core.data.font.parsing.ConfigParser.configReadme;
import static club.bluezenith.util.font.FontUtil.fonts;
import static java.io.File.separator;

public class FontRepository extends ClientResourceRepository {

    public List<ParseResult> customFonts = Lists.newArrayList();

    public FontRepository(String path) {
        super(path);
        try {
            ensureExistence();
            refresh();
        } catch (Exception exception) {
            System.err.println("Failed to load the font repository!");
        }
    }

    //unused
    public FontRepository(File clientDirectory) {
        super(clientDirectory);
    }

    @SuppressWarnings("all")
    public void refresh() {
       this.customFonts.clear();
       this.customFonts = new ConfigParser().parse(ContentRetriever.readConfigFile(extend("fonts" + separator + "config.txt")));
       this.customFonts.removeIf(r -> !r.verify());
       fonts.removeIf(f -> f instanceof TFontRenderer && ((TFontRenderer)f).isCustom);
       customFonts.forEach(obj -> fonts.addAll(obj.generateFontRenderers()));
    }

    void ensureExistence() throws IOException {
        final File fontsFolder = extend("fonts" + separator);
        if(!fontsFolder.exists())
            fontsFolder.mkdirs();
        final File configFile = extend("fonts" + separator + "config.txt");
        if(!configFile.exists()) {
            configFile.createNewFile();
            writeToFile("fonts", "config.txt", configReadme);
        }

    }
}
