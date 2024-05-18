package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;

public class RenderRabbit extends RenderLiving<EntityRabbit>
{
    private static final ResourceLocation GOLD;
    private static final ResourceLocation WHITE_SPLOTCHED;
    private static final ResourceLocation BROWN;
    private static final ResourceLocation WHITE;
    private static final ResourceLocation SALT;
    private static final ResourceLocation BLACK;
    private static final ResourceLocation CAERBANNOG;
    private static final ResourceLocation TOAST;
    private static final String[] I;
    
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityRabbit)entity);
    }
    
    static {
        I();
        BROWN = new ResourceLocation(RenderRabbit.I["".length()]);
        WHITE = new ResourceLocation(RenderRabbit.I[" ".length()]);
        BLACK = new ResourceLocation(RenderRabbit.I["  ".length()]);
        GOLD = new ResourceLocation(RenderRabbit.I["   ".length()]);
        SALT = new ResourceLocation(RenderRabbit.I[0xA9 ^ 0xAD]);
        WHITE_SPLOTCHED = new ResourceLocation(RenderRabbit.I[0x42 ^ 0x47]);
        TOAST = new ResourceLocation(RenderRabbit.I[0x88 ^ 0x8E]);
        CAERBANNOG = new ResourceLocation(RenderRabbit.I[0x29 ^ 0x2E]);
    }
    
    private static void I() {
        (I = new String[0x85 ^ 0x8C])["".length()] = I("\u0007#6\u0005\u001b\u0001#=^\u000b\u001d2'\u0005\u0017\\4/\u0013\f\u001a2a\u0013\u001c\u001c1 _\u001e\u001d!", "sFNqn");
        RenderRabbit.I[" ".length()] = I("\u0011)/'\u0003\u0017)$|\u0013\u000b8>'\u000fJ>61\u0014\f8x$\u001e\f82}\u0006\u000b+", "eLWSv");
        RenderRabbit.I["  ".length()] = I("8\f/\u001c4>\f$G$\"\u001d>\u001c8c\u001b6\n#%\u001dx\n--\n<F1\"\u000e", "LiWhA");
        RenderRabbit.I["   ".length()] = I("\u0013\u0003\u0015\u00072\u0015\u0003\u001e\\\"\t\u0012\u0004\u0007>H\u0014\f\u0011%\u000e\u0012B\u0014(\u000b\u0002C\u0003)\u0000", "gfmsG");
        RenderRabbit.I[0x6A ^ 0x6E] = I("'\u001d\u000f,\u001e!\u001d\u0004w\u000e=\f\u001e,\u0012|\n\u0016:\t:\fX+\n?\fY(\u00054", "SxwXk");
        RenderRabbit.I[0x7A ^ 0x7F] = I("=0<\u001f-;07D='!-\u001f!f'%\t: !k\u001c0 !!4+99+\u001f;!0 E('2", "IUDkX");
        RenderRabbit.I[0x9D ^ 0x9B] = I("'=+.?!= u/=,:.3|*28(:,|.%2+'t:=?", "SXSZJ");
        RenderRabbit.I[0xA ^ 0xD] = I("=3\u0002'\r;3\t|\u001d'\"\u0013'\u0001f$\u001b1\u001a \"U0\u0019,$\u00182\u0016'9\u001d}\b'1", "IVzSx");
        RenderRabbit.I[0x6E ^ 0x66] = I("\r\u0017\u0010\u0003\u0019", "Yxqpm");
    }
    
    public RenderRabbit(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityRabbit entityRabbit) {
        final String textWithoutFormattingCodes = EnumChatFormatting.getTextWithoutFormattingCodes(entityRabbit.getName());
        if (textWithoutFormattingCodes != null && textWithoutFormattingCodes.equals(RenderRabbit.I[0x7F ^ 0x77])) {
            return RenderRabbit.TOAST;
        }
        switch (entityRabbit.getRabbitType()) {
            default: {
                return RenderRabbit.BROWN;
            }
            case 1: {
                return RenderRabbit.WHITE;
            }
            case 2: {
                return RenderRabbit.BLACK;
            }
            case 3: {
                return RenderRabbit.WHITE_SPLOTCHED;
            }
            case 4: {
                return RenderRabbit.GOLD;
            }
            case 5: {
                return RenderRabbit.SALT;
            }
            case 99: {
                return RenderRabbit.CAERBANNOG;
            }
        }
    }
}
