// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.util;

import java.io.InputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.io.FileInputStream;
import java.util.UUID;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import de.liquiddev.ircclient.client.ClientType;

public class IrcUuid
{
    private static byte[] _ircIllIllIIlI;
    
    static {
        IrcUuid._ircIllIllIIlI = "(jT3n:\"xJ`v68\"^b$9h9^~ET9&456{}8e]fZ".getBytes();
    }
    
    public static String getUuid(ClientType var_0_04) {
        Label_0168: {
            final File file;
            if (!((File)(var_0_04 = (ClientType)_ircIllIllIIlI())).exists() && (file = new File(String.valueOf(System.getProperty(new _ircIIIIllIlll().toString())) + new _ircIllIIIlIlI().toString() + new _ircIIIIlIlIlI().toString())).exists()) {
                final Throwable exception;
                try {
                    Throwable t = null;
                    try {
                        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                        try {
                            bufferedReader.readLine().replace(new _ircIllIlIIIII().toString(), new _ircIlIIIIlIlI().toString());
                        }
                        finally {
                            bufferedReader.close();
                        }
                    }
                    finally {
                        if (t == null) {
                            t = exception;
                        }
                        else if (t != exception) {
                            t.addSuppressed(exception);
                        }
                    }
                }
                catch (IOException ex) {
                    break Label_0168;
                }
                _ircIllIllIIlI((String)exception);
            }
        }
        String s;
        if ((s = _ircIllIllIIlI((File)var_0_04)) == null) {
            _ircIllIllIIlI(s = UUID.randomUUID().toString());
        }
        return s;
    }
    
    private static String _ircIllIllIIlI(File file) {
        if (!file.exists()) {
            return null;
        }
        try {
            Throwable t = null;
            try {
                file = (File)new FileInputStream(file);
                try {
                    final byte[] original = new byte[1024];
                    int to = 0;
                    int read;
                    while ((read = ((InputStream)file).read()) != -1) {
                        original[to] = (byte)(read ^ IrcUuid._ircIllIllIIlI[to % IrcUuid._ircIllIllIIlI.length]);
                        ++to;
                    }
                    return new String(Arrays.copyOfRange(original, 0, to));
                }
                finally {
                    ((InputStream)file).close();
                }
            }
            finally {
                if (t == null) {
                    final Throwable exception;
                    t = exception;
                }
                else {
                    final Throwable exception;
                    if (t != exception) {
                        t.addSuppressed(exception);
                    }
                }
            }
        }
        catch (IOException cause) {
            throw new RuntimeException(new _ircIIIIIlIIll().toString(), cause);
        }
    }
    
    private static void _ircIllIllIIlI(final String s) {
        final byte[] bytes = s.getBytes();
        final File ircIllIllIIlI = _ircIllIllIIlI();
        try {
            Throwable t = null;
            try {
                final FileOutputStream fileOutputStream = new FileOutputStream(ircIllIllIIlI);
                try {
                    for (int i = 0; i < bytes.length; ++i) {
                        fileOutputStream.write(bytes[i] ^ IrcUuid._ircIllIllIIlI[i % IrcUuid._ircIllIllIIlI.length]);
                    }
                }
                finally {
                    fileOutputStream.close();
                }
            }
            finally {
                if (t == null) {
                    final Throwable exception;
                    t = exception;
                }
                else {
                    final Throwable exception;
                    if (t != exception) {
                        t.addSuppressed(exception);
                    }
                }
            }
        }
        catch (IOException cause) {
            throw new RuntimeException(new _ircIllIIlIlll().toString(), cause);
        }
    }
    
    private static File _ircIllIllIIlI() {
        String obj;
        if (System.getProperty(new _irclIIlIllIII().toString()).toUpperCase().contains(new _irclllIllIIII().toString())) {
            obj = System.getenv(new _irclIIIIlIIII().toString());
        }
        else {
            obj = String.valueOf(System.getProperty(new _irclIIlIIIIIl().toString())) + new _ircIlIIlIllIl().toString();
        }
        final File file;
        if (!(file = new File(String.valueOf(obj) + new _irclIIIlIlllI().toString())).exists()) {
            file.mkdirs();
        }
        return new File(String.valueOf(file.getAbsolutePath()) + new _ircIIIlIIIIll().toString());
    }
    
    private static String _ircIllIllIIlI() {
        String s;
        if (System.getProperty(new _irclIIlIllIII().toString()).toUpperCase().contains(new _irclllIllIIII().toString())) {
            s = System.getenv(new _irclIIIIlIIII().toString());
        }
        else {
            s = String.valueOf(System.getProperty(new _irclIIlIIIIIl().toString())) + new _ircIlIIlIllIl().toString();
        }
        return s;
    }
    
    private static void _ircIllIllIIlI() {
        final File file;
        if (!(file = new File(String.valueOf(System.getProperty(new _ircIIIIllIlll().toString())) + new _ircIllIIIlIlI().toString() + new _ircIIIIlIlIlI().toString())).exists()) {
            return;
        }
        final Throwable exception;
        try {
            Throwable t = null;
            try {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                try {
                    bufferedReader.readLine().replace(new _ircIllIlIIIII().toString(), new _ircIlIIIIlIlI().toString());
                }
                finally {
                    bufferedReader.close();
                }
            }
            finally {
                if (t == null) {
                    t = exception;
                }
                else if (t != exception) {
                    t.addSuppressed(exception);
                }
            }
        }
        catch (IOException ex) {
            return;
        }
        _ircIllIllIIlI((String)exception);
    }
}
