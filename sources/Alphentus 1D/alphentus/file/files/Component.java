package alphentus.file.files;

import alphentus.init.Init;
import net.minecraft.client.Minecraft;

import java.io.File;

/**
 * @author avox | lmao
 * @since on 07/08/2020.
 */
public class Component {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final Init init = Init.getInstance();
    private final File fileDirectory = new File(mc.mcDataDir, init.CLIENT_NAME);

    public void create(){
    }

    public void load(){
    }

    public Minecraft getMc () {
        return mc;
    }

    public Init getInit () {
        return init;
    }

    public File getFileDirectory () {
        return fileDirectory;
    }
}
