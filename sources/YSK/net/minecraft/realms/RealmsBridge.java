package net.minecraft.realms;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.lang.reflect.*;
import org.apache.logging.log4j.*;

public class RealmsBridge extends RealmsScreen
{
    private static final Logger LOGGER;
    private static final String[] I;
    private GuiScreen previousScreen;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void switchToRealms(final GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
        try {
            final Class<?> forName = Class.forName(RealmsBridge.I["".length()]);
            final Class[] array = new Class[" ".length()];
            array["".length()] = RealmsScreen.class;
            final Constructor<?> declaredConstructor = forName.getDeclaredConstructor((Class<?>[])array);
            declaredConstructor.setAccessible(" ".length() != 0);
            final Constructor<?> constructor = declaredConstructor;
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = this;
            Minecraft.getMinecraft().displayGuiScreen(((RealmsScreen)constructor.newInstance(array2)).getProxy());
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        catch (Exception ex) {
            RealmsBridge.LOGGER.error(RealmsBridge.I[" ".length()], (Throwable)ex);
        }
    }
    
    @Override
    public void init() {
        Minecraft.getMinecraft().displayGuiScreen(this.previousScreen);
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("%%\u0018K8) \u0014\u000b2h8\u0010\u00049+9\u0016\t<#$\u0001K\u0007#+\u0019\b&\u000b+\u001c\u000b\u0006%8\u0010\u0000;", "FJueU");
        RealmsBridge.I[" ".length()] = I("&\u0003\u0018\u0016\u0014\u0007F\u0014\u0015\u001d\u0001\n\u001cZ\u0014\u001d\u0015\n\u0013\u0017\u0013", "tfyzy");
    }
}
