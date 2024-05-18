// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils.system;

import java.util.Iterator;
import java.lang.reflect.Constructor;
import net.minecraftforge.fml.common.eventhandler.ASMEventHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import java.util.Arrays;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.lang.reflect.Method;
import java.util.Set;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.IEventListener;
import java.util.ArrayList;
import java.lang.annotation.Annotation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.google.common.reflect.TypeToken;
import net.minecraftforge.fml.common.Loader;
import java.util.Map;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraftforge.fml.common.eventhandler.EventBus;

public class Nan0EventRegister
{
    private static final /* synthetic */ int[] lIIll;
    private static final /* synthetic */ String[] lIIII;
    
    public static void register(final EventBus llllIIllIlllIll, final Object llllIIllIllIlII) {
        final Class<EventBus> clazz = EventBus.class;
        final String[] array = new String[Nan0EventRegister.lIIll[0]];
        array[Nan0EventRegister.lIIll[1]] = Nan0EventRegister.lIIII[Nan0EventRegister.lIIll[1]];
        final ConcurrentHashMap<Object, ArrayList<IEventListener>> llllIIllIlllIIl = (ConcurrentHashMap<Object, ArrayList<IEventListener>>)ReflectionHelper.getPrivateValue((Class)clazz, (Object)llllIIllIlllIll, array);
        final Class<EventBus> clazz2 = EventBus.class;
        final String[] array2 = new String[Nan0EventRegister.lIIll[0]];
        array2[Nan0EventRegister.lIIll[1]] = Nan0EventRegister.lIIII[Nan0EventRegister.lIIll[0]];
        final Map<Object, ModContainer> llllIIllIlllIII = (Map<Object, ModContainer>)ReflectionHelper.getPrivateValue((Class)clazz2, (Object)llllIIllIlllIll, array2);
        if (lIllIll(llllIIllIlllIIl.containsKey(llllIIllIllIlII) ? 1 : 0)) {
            return;
        }
        final ModContainer llllIIllIllIlll = (ModContainer)Loader.instance().getMinecraftModContainer();
        llllIIllIlllIII.put(llllIIllIllIlII, llllIIllIllIlll);
        "".length();
        final Class<EventBus> clazz3 = EventBus.class;
        final Map<Object, ModContainer> map = llllIIllIlllIII;
        final String[] array3 = new String[Nan0EventRegister.lIIll[0]];
        array3[Nan0EventRegister.lIIll[1]] = Nan0EventRegister.lIIII[Nan0EventRegister.lIIll[2]];
        ReflectionHelper.setPrivateValue((Class)clazz3, (Object)llllIIllIlllIll, (Object)map, array3);
        final Set<? extends Class<?>> llllIIllIllIllI = (Set<? extends Class<?>>)TypeToken.of((Class)llllIIllIllIlII.getClass()).getTypes().rawTypes();
        final short llllIIllIlIllll = (Object)llllIIllIllIlII.getClass().getMethods();
        final boolean llllIIllIlIlllI = llllIIllIlIllll.length != 0;
        double llllIIllIlIllIl = Nan0EventRegister.lIIll[1];
        while (lIlllII((int)llllIIllIlIllIl, llllIIllIlIlllI ? 1 : 0)) {
            final Method llllIIllIllllII = llllIIllIlIllll[llllIIllIlIllIl];
            final String llllIIllIlIlIll = (String)llllIIllIllIllI.iterator();
            while (lIllIll(((Iterator)llllIIllIlIlIll).hasNext() ? 1 : 0)) {
                final Class<?> llllIIllIllllIl = ((Iterator<Class<?>>)llllIIllIlIlIll).next();
                try {
                    final Method llllIIllIlllllI = llllIIllIllllIl.getDeclaredMethod(llllIIllIllllII.getName(), llllIIllIllllII.getParameterTypes());
                    if (lIllIll(llllIIllIlllllI.isAnnotationPresent((Class<? extends Annotation>)SubscribeEvent.class) ? 1 : 0)) {
                        final Class<?>[] llllIIlllIIIIII = llllIIllIllllII.getParameterTypes();
                        final Class<?> llllIIllIllllll = llllIIlllIIIIII[Nan0EventRegister.lIIll[1]];
                        register(llllIIllIlllIll, llllIIllIllllll, llllIIllIllIlII, llllIIllIllllII, llllIIllIllIlll);
                        "".length();
                        if (null != null) {
                            return;
                        }
                        break;
                    }
                    else {
                        "".length();
                        if (" ".length() >= "   ".length()) {
                            return;
                        }
                    }
                }
                catch (NoSuchMethodException ex) {}
                "".length();
                if (((0x56 ^ 0x50 ^ (0x7 ^ 0x58)) & (0x7B ^ 0x1C ^ (0x87 ^ 0xB9) ^ -" ".length())) >= " ".length()) {
                    return;
                }
            }
            ++llllIIllIlIllIl;
            "".length();
            if ("  ".length() == " ".length()) {
                return;
            }
        }
    }
    
    private static String lIIlIII(final String llllIIlIIIIllII, final String llllIIlIIIIlIll) {
        try {
            final SecretKeySpec llllIIlIIIIllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIIlIIIIlIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llllIIlIIIIlllI = Cipher.getInstance("Blowfish");
            llllIIlIIIIlllI.init(Nan0EventRegister.lIIll[2], llllIIlIIIIllll);
            return new String(llllIIlIIIIlllI.doFinal(Base64.getDecoder().decode(llllIIlIIIIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIIlIIIIllIl) {
            llllIIlIIIIllIl.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIllIll(final int llllIIIlllIlIIl) {
        return llllIIIlllIlIIl != 0;
    }
    
    private static String lIIllIl(final String llllIIlIIlIIIll, final String llllIIlIIlIIIlI) {
        try {
            final SecretKeySpec llllIIlIIlIIllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIIlIIlIIIlI.getBytes(StandardCharsets.UTF_8)), Nan0EventRegister.lIIll[7]), "DES");
            final Cipher llllIIlIIlIIlIl = Cipher.getInstance("DES");
            llllIIlIIlIIlIl.init(Nan0EventRegister.lIIll[2], llllIIlIIlIIllI);
            return new String(llllIIlIIlIIlIl.doFinal(Base64.getDecoder().decode(llllIIlIIlIIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIIlIIlIIlII) {
            llllIIlIIlIIlII.printStackTrace();
            return null;
        }
    }
    
    private static String lIIlIlI(String llllIIlIlIIIIIl, final String llllIIlIlIIIlIl) {
        llllIIlIlIIIIIl = new String(Base64.getDecoder().decode(llllIIlIlIIIIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llllIIlIlIIIlII = new StringBuilder();
        final char[] llllIIlIlIIIIll = llllIIlIlIIIlIl.toCharArray();
        int llllIIlIlIIIIlI = Nan0EventRegister.lIIll[1];
        final double llllIIlIIlllIll = (Object)llllIIlIlIIIIIl.toCharArray();
        final boolean llllIIlIIlllIIl = llllIIlIIlllIll.length != 0;
        int llllIIlIIlllIII = Nan0EventRegister.lIIll[1];
        while (lIlllII(llllIIlIIlllIII, llllIIlIIlllIIl ? 1 : 0)) {
            final char llllIIlIlIIIlll = llllIIlIIlllIll[llllIIlIIlllIII];
            llllIIlIlIIIlII.append((char)(llllIIlIlIIIlll ^ llllIIlIlIIIIll[llllIIlIlIIIIlI % llllIIlIlIIIIll.length]));
            "".length();
            ++llllIIlIlIIIIlI;
            ++llllIIlIIlllIII;
            "".length();
            if (-(0x2C ^ 0x28) >= 0) {
                return null;
            }
        }
        return String.valueOf(llllIIlIlIIIlII);
    }
    
    private static void lIlIIlI() {
        (lIIII = new String[Nan0EventRegister.lIIll[6]])[Nan0EventRegister.lIIll[1]] = lIIlIII("G0aU2uGU/gXAXpFtGgB6Og==", "emZOj");
        Nan0EventRegister.lIIII[Nan0EventRegister.lIIll[0]] = lIIlIlI("Ci48ATcIIj06JQgiPQY=", "fGOuR");
        Nan0EventRegister.lIIII[Nan0EventRegister.lIIll[2]] = lIIlIlI("LxkCDSYtFQM2NC0VAwo=", "CpqyC");
        Nan0EventRegister.lIIII[Nan0EventRegister.lIIll[3]] = lIIllIl("qhps4ZmHlk0=", "bhtNI");
        Nan0EventRegister.lIIII[Nan0EventRegister.lIIll[4]] = lIIlIII("YREp/6vIOkI8SglygzyzFA==", "RJxxj");
        Nan0EventRegister.lIIII[Nan0EventRegister.lIIll[5]] = lIIlIII("5mj+HmS1GfAKWmv3y+bkEQ==", "NIDTt");
    }
    
    private static void register(final EventBus llllIIlIlllIllI, final Class<?> llllIIlIllIlllI, final Object llllIIlIlllIlII, final Method llllIIlIllIlIll, final ModContainer llllIIlIllIlIIl) {
        try {
            final Class<EventBus> clazz = EventBus.class;
            final String[] array = new String[Nan0EventRegister.lIIll[0]];
            array[Nan0EventRegister.lIIll[1]] = Nan0EventRegister.lIIII[Nan0EventRegister.lIIll[3]];
            final int llllIIlIlllllIl = (int)ReflectionHelper.getPrivateValue((Class)clazz, (Object)llllIIlIlllIllI, array);
            final Class<EventBus> clazz2 = EventBus.class;
            final String[] array2 = new String[Nan0EventRegister.lIIll[0]];
            array2[Nan0EventRegister.lIIll[1]] = Nan0EventRegister.lIIII[Nan0EventRegister.lIIll[4]];
            final ConcurrentHashMap<Object, ArrayList<IEventListener>> llllIIlIlllllII = (ConcurrentHashMap<Object, ArrayList<IEventListener>>)ReflectionHelper.getPrivateValue((Class)clazz2, (Object)llllIIlIlllIllI, array2);
            final Constructor<?> llllIIlIllllIll = llllIIlIllIlllI.getConstructor((Class<?>[])new Class[Nan0EventRegister.lIIll[1]]);
            llllIIlIllllIll.setAccessible((boolean)(Nan0EventRegister.lIIll[0] != 0));
            final Event llllIIlIllllIlI = (Event)llllIIlIllllIll.newInstance(new Object[Nan0EventRegister.lIIll[1]]);
            final ASMEventHandler llllIIlIllllIIl = new ASMEventHandler(llllIIlIlllIlII, llllIIlIllIlIll, llllIIlIllIlIIl);
            llllIIlIllllIlI.getListenerList().register(llllIIlIlllllIl, llllIIlIllllIIl.getPriority(), (IEventListener)llllIIlIllllIIl);
            ArrayList<IEventListener> llllIIlIllllIII = llllIIlIlllllII.get(llllIIlIlllIlII);
            if (lIlllIl(llllIIlIllllIII)) {
                llllIIlIllllIII = new ArrayList<IEventListener>();
                llllIIlIlllllII.put(llllIIlIlllIlII, llllIIlIllllIII);
                "".length();
                final Class<EventBus> clazz3 = EventBus.class;
                final ConcurrentHashMap<Object, ArrayList<IEventListener>> concurrentHashMap = llllIIlIlllllII;
                final String[] array3 = new String[Nan0EventRegister.lIIll[0]];
                array3[Nan0EventRegister.lIIll[1]] = Nan0EventRegister.lIIII[Nan0EventRegister.lIIll[5]];
                ReflectionHelper.setPrivateValue((Class)clazz3, (Object)llllIIlIlllIllI, (Object)concurrentHashMap, array3);
            }
            llllIIlIllllIII.add((IEventListener)llllIIlIllllIIl);
            "".length();
            "".length();
            if ("   ".length() > "   ".length()) {
                return;
            }
        }
        catch (Exception ex) {}
    }
    
    private static boolean lIlllII(final int llllIIIllllllII, final int llllIIIlllllIlI) {
        return llllIIIllllllII < llllIIIlllllIlI;
    }
    
    private static boolean lIlllIl(final Object llllIIIlllIllll) {
        return llllIIIlllIllll == null;
    }
    
    private static void lIllIlI() {
        (lIIll = new int[8])[0] = " ".length();
        Nan0EventRegister.lIIll[1] = ((0x26 ^ 0x4B ^ (0x5E ^ 0x3F)) & (0x6A ^ 0x54 ^ (0x11 ^ 0x23) ^ -" ".length()));
        Nan0EventRegister.lIIll[2] = "  ".length();
        Nan0EventRegister.lIIll[3] = "   ".length();
        Nan0EventRegister.lIIll[4] = (0x13 ^ 0x17);
        Nan0EventRegister.lIIll[5] = (28 + 160 - 75 + 68 ^ 87 + 101 - 135 + 123);
        Nan0EventRegister.lIIll[6] = (0x7E ^ 0x60 ^ (0x2B ^ 0x33));
        Nan0EventRegister.lIIll[7] = (0xB0 ^ 0xB8);
    }
    
    static {
        lIllIlI();
        lIlIIlI();
    }
}
