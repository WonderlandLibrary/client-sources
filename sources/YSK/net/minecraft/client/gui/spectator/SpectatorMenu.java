package net.minecraft.client.gui.spectator;

import net.minecraft.client.gui.spectator.categories.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class SpectatorMenu
{
    private int field_178660_i;
    private static final ISpectatorMenuObject field_178655_b;
    private final List<SpectatorDetails> field_178652_g;
    private static final ISpectatorMenuObject field_178656_c;
    private static final ISpectatorMenuObject field_178654_e;
    private static final ISpectatorMenuObject field_178653_d;
    private final ISpectatorMenuRecipient field_178651_f;
    private ISpectatorMenuView field_178659_h;
    private int field_178658_j;
    public static final ISpectatorMenuObject field_178657_a;
    
    public ISpectatorMenuObject func_178645_b() {
        return this.func_178643_a(this.field_178660_i);
    }
    
    public void func_178647_a(final ISpectatorMenuView field_178659_h) {
        this.field_178652_g.add(this.func_178646_f());
        this.field_178659_h = field_178659_h;
        this.field_178660_i = -" ".length();
        this.field_178658_j = "".length();
    }
    
    public int func_178648_e() {
        return this.field_178660_i;
    }
    
    static void access$0(final SpectatorMenu spectatorMenu, final int field_178658_j) {
        spectatorMenu.field_178658_j = field_178658_j;
    }
    
    public ISpectatorMenuView func_178650_c() {
        return this.field_178659_h;
    }
    
    public SpectatorDetails func_178646_f() {
        return new SpectatorDetails(this.field_178659_h, this.func_178642_a(), this.field_178660_i);
    }
    
    public List<ISpectatorMenuObject> func_178642_a() {
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i <= (0xB4 ^ 0xBC)) {
            arrayList.add(this.func_178643_a(i));
            ++i;
        }
        return (List<ISpectatorMenuObject>)arrayList;
    }
    
    public void func_178641_d() {
        this.field_178651_f.func_175257_a(this);
    }
    
    public ISpectatorMenuObject func_178643_a(final int n) {
        final int n2 = n + this.field_178658_j * (0x90 ^ 0x96);
        ISpectatorMenuObject spectatorMenuObject;
        if (this.field_178658_j > 0 && n == 0) {
            spectatorMenuObject = SpectatorMenu.field_178656_c;
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else if (n == (0x7F ^ 0x78)) {
            if (n2 < this.field_178659_h.func_178669_a().size()) {
                spectatorMenuObject = SpectatorMenu.field_178653_d;
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                spectatorMenuObject = SpectatorMenu.field_178654_e;
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
        }
        else if (n == (0x1A ^ 0x12)) {
            spectatorMenuObject = SpectatorMenu.field_178655_b;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else if (n2 >= 0 && n2 < this.field_178659_h.func_178669_a().size()) {
            spectatorMenuObject = (ISpectatorMenuObject)Objects.firstNonNull((Object)this.field_178659_h.func_178669_a().get(n2), (Object)SpectatorMenu.field_178657_a);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            spectatorMenuObject = SpectatorMenu.field_178657_a;
        }
        return spectatorMenuObject;
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void func_178644_b(final int field_178660_i) {
        final ISpectatorMenuObject func_178643_a = this.func_178643_a(field_178660_i);
        if (func_178643_a != SpectatorMenu.field_178657_a) {
            if (this.field_178660_i == field_178660_i && func_178643_a.func_178662_A_()) {
                func_178643_a.func_178661_a(this);
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else {
                this.field_178660_i = field_178660_i;
            }
        }
    }
    
    public SpectatorMenu(final ISpectatorMenuRecipient field_178651_f) {
        this.field_178652_g = (List<SpectatorDetails>)Lists.newArrayList();
        this.field_178659_h = new BaseSpectatorGroup();
        this.field_178660_i = -" ".length();
        this.field_178651_f = field_178651_f;
    }
    
    static {
        field_178655_b = new EndSpectatorObject(null);
        field_178656_c = new MoveMenuObject(-" ".length(), " ".length() != 0);
        field_178653_d = new MoveMenuObject(" ".length(), " ".length() != 0);
        field_178654_e = new MoveMenuObject(" ".length(), "".length() != 0);
        field_178657_a = new ISpectatorMenuObject() {
            private static final String[] I;
            
            @Override
            public boolean func_178662_A_() {
                return "".length() != 0;
            }
            
            @Override
            public void func_178663_a(final float n, final int n2) {
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("", "JMYyq");
            }
            
            static {
                I();
            }
            
            @Override
            public IChatComponent getSpectatorName() {
                return new ChatComponentText(SpectatorMenu$1.I["".length()]);
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
                    if (3 <= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void func_178661_a(final SpectatorMenu spectatorMenu) {
            }
        };
    }
    
    static class MoveMenuObject implements ISpectatorMenuObject
    {
        private static final String[] I;
        private final int field_178666_a;
        private final boolean field_178665_b;
        
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
                if (3 != 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public IChatComponent getSpectatorName() {
            ChatComponentText chatComponentText;
            if (this.field_178666_a < 0) {
                chatComponentText = new ChatComponentText(MoveMenuObject.I["".length()]);
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
            else {
                chatComponentText = new ChatComponentText(MoveMenuObject.I[" ".length()]);
            }
            return chatComponentText;
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u0002\u001d\f\u0018\u0005=\u001a\u001aN<3\b\f", "Roinl");
            MoveMenuObject.I[" ".length()] = I("\r)\u000f S\u0013-\u00101", "CLwTs");
        }
        
        @Override
        public void func_178661_a(final SpectatorMenu spectatorMenu) {
            SpectatorMenu.access$0(spectatorMenu, this.field_178666_a);
        }
        
        @Override
        public boolean func_178662_A_() {
            return this.field_178665_b;
        }
        
        static {
            I();
        }
        
        @Override
        public void func_178663_a(final float n, final int n2) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
            if (this.field_178666_a < 0) {
                Gui.drawModalRectWithCustomSizedTexture("".length(), "".length(), 144.0f, 0.0f, 0x30 ^ 0x20, 0x6F ^ 0x7F, 256.0f, 256.0f);
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                Gui.drawModalRectWithCustomSizedTexture("".length(), "".length(), 160.0f, 0.0f, 0x6E ^ 0x7E, 0x1B ^ 0xB, 256.0f, 256.0f);
            }
        }
        
        public MoveMenuObject(final int field_178666_a, final boolean field_178665_b) {
            this.field_178666_a = field_178666_a;
            this.field_178665_b = field_178665_b;
        }
    }
    
    static class EndSpectatorObject implements ISpectatorMenuObject
    {
        private static final String[] I;
        
        @Override
        public IChatComponent getSpectatorName() {
            return new ChatComponentText(EndSpectatorObject.I["".length()]);
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
                if (1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
        }
        
        EndSpectatorObject(final EndSpectatorObject endSpectatorObject) {
            this();
        }
        
        @Override
        public boolean func_178662_A_() {
            return " ".length() != 0;
        }
        
        @Override
        public void func_178663_a(final float n, final int n2) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
            Gui.drawModalRectWithCustomSizedTexture("".length(), "".length(), 128.0f, 0.0f, 0x32 ^ 0x22, 0x10 ^ 0x0, 256.0f, 256.0f);
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I(".\u0007\u0003\u00120M\u0006\t\u000f ", "mklaU");
        }
        
        private EndSpectatorObject() {
        }
        
        @Override
        public void func_178661_a(final SpectatorMenu spectatorMenu) {
            spectatorMenu.func_178641_d();
        }
    }
}
