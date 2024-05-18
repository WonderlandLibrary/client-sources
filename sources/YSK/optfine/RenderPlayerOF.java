package optfine;

import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import java.lang.reflect.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;

public class RenderPlayerOF extends RenderPlayer
{
    private static final String[] I;
    
    @Override
    protected void renderLayers(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        super.renderLayers(abstractClientPlayer, n, n2, n3, n4, n5, n6, n7);
        this.renderEquippedItems(abstractClientPlayer, n7, n3);
    }
    
    static {
        I();
    }
    
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RenderPlayerOF(final RenderManager renderManager, final boolean b) {
        super(renderManager, b);
    }
    
    private static Map getMapRenderTypes(final RenderManager renderManager) {
        try {
            final Field[] fields = Reflector.getFields(RenderManager.class, Map.class);
            int i = "".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (i < fields.length) {
                final Map map = (Map)fields[i].get(renderManager);
                if (map != null && map.get(RenderPlayerOF.I["   ".length()]) instanceof RenderPlayer) {
                    return map;
                }
                ++i;
            }
            return null;
        }
        catch (Exception ex) {
            Config.warn(RenderPlayerOF.I[0xBD ^ 0xB9]);
            Config.warn(String.valueOf(ex.getClass().getName()) + RenderPlayerOF.I[0xB ^ 0xE] + ex.getMessage());
            return null;
        }
    }
    
    @Override
    protected void renderLayers(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.renderLayers((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public static void register() {
        final RenderManager renderManager = Config.getMinecraft().getRenderManager();
        final Map mapRenderTypes = getMapRenderTypes(renderManager);
        if (mapRenderTypes == null) {
            Config.warn(RenderPlayerOF.I["".length()]);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            mapRenderTypes.put(RenderPlayerOF.I[" ".length()], new RenderPlayerOF(renderManager, (boolean)("".length() != 0)));
            mapRenderTypes.put(RenderPlayerOF.I["  ".length()], new RenderPlayerOF(renderManager, (boolean)(" ".length() != 0)));
        }
    }
    
    protected void renderEquippedItems(final EntityLivingBase entityLivingBase, final float n, final float n2) {
        if (entityLivingBase instanceof AbstractClientPlayer) {
            final AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer)entityLivingBase;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableRescaleNormal();
            PlayerConfigurations.renderPlayerItems((ModelBiped)this.mainModel, abstractClientPlayer, n, n2);
        }
    }
    
    private static void I() {
        (I = new String[0x7 ^ 0x1])["".length()] = I("\u00170\u00074\r7\u0005\u00051\u0011 '&\u0016H,;\u0000$@lu\u000f1\u0001)0\rjH\u00170\u00074\r7\u0018\b>\t\"0\u001b~%$%;5\u0006!0\u001b\u0004\u001150\u001ap\u0006*!I6\u00070;\r", "EUiPh");
        RenderPlayerOF.I[" ".length()] = I("54\u0014#\u0013=%", "QQrBf");
        RenderPlayerOF.I["  ".length()] = I("\u0000\u0002\u001d\u0006", "sntkB");
        RenderPlayerOF.I["   ".length()] = I(" \u0002\u0014\f&(\u0013", "DgrmS");
        RenderPlayerOF.I[0x2B ^ 0x2F] = I("\f\u000b\u001a \u0005i\u001e\r;\u0003 \u0017\u000fo%,\u0017\f*\u0005\u0004\u0018\u0006.\u0010,\u000bF\"\u00169+\r!\u0013,\u000b<6\u0007,\n", "IyhOw");
        RenderPlayerOF.I[0x6B ^ 0x6E] = I("rO", "HodwU");
    }
}
