package wwe;

import java.net.*;
import java.io.*;
import wwe.utils.*;
import java.util.*;
import java.lang.reflect.*;

public class DROu extends Thread
{
    public Socket ZnqM;
    public ObjectInputStream urgx;
    public ObjectOutputStream wilV;
    public boolean OHxT;
    public String TTRL;
    public String DLoQ;
    public int ZrIx;
    public int ZElF;
    public Socket ZnqM;
    public ObjectInputStream urgx;
    public ObjectOutputStream wilV;
    public boolean OHxT;
    public String TTRL;
    public String DLoQ;
    public int ZrIx;
    public int ZElF;
    
    public DROu() {
        super();
        this.OHxT = false;
        this.DLoQ = "localhost";
        this.ZElF = -1;
        this.xMmW();
        this.start();
    }
    
    public void xMmW() {
        try {
            this.TTRL = null;
            this.ZnqM = new Socket(this.DLoQ, 2121);
            this.wilV = new ObjectOutputStream(this.ZnqM.getOutputStream());
            this.urgx = new ObjectInputStream(this.ZnqM.getInputStream());
            this.HzdM(new bWzb(Wrapper.mc.getSession().getUsername(), this.ZElF));
            if (DROu.\u2009\u200a\u2009\u200e\u200d\u2002\u2000\u2008) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.TTRL = "Couldent connect to the IRC server, probably down):";
            this.mrKt();
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public DROu() {
        super();
        this.OHxT = false;
        this.DLoQ = "localhost";
        this.ZElF = -1;
        this.xMmW();
        this.start();
    }
    
    public void xMmW() {
        try {
            this.TTRL = null;
            this.ZnqM = new Socket(this.DLoQ, 2121);
            this.wilV = new ObjectOutputStream(this.ZnqM.getOutputStream());
            this.urgx = new ObjectInputStream(this.ZnqM.getInputStream());
            this.HzdM(new bWzb(Wrapper.mc.getSession().getUsername(), this.ZElF));
            if (DROu.\u2009\u200a\u2009\u200e\u200d\u2002\u2000\u2008) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.TTRL = "Couldent connect to the IRC server, probably down):";
            this.mrKt();
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        do {
            this.eQYx();
        } while (!DROu.\u2009\u200a\u2009\u200e\u200d\u2002\u2000\u2008);
        throw null;
    }
    
    public void eQYx() {
        try {
            Thread.sleep(0x947F586853B6064CL ^ 0x947F586853B607B8L);
            if (this.JmLq()) {
                this.hJoY();
            }
            if (DROu.\u2009\u200a\u2009\u200e\u200d\u2002\u2000\u2008) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.TTRL = "Couldent connect to the IRC server, probably down): Error message 1";
            this.mrKt();
            ex.printStackTrace();
        }
    }
    
    public void hJoY() throws Exception {
        if (!this.JmLq()) {
            return;
        }
        Object object;
        while ((object = this.urgx.readObject()) != null) {
            final ArrayList<Integer> list = (ArrayList<Integer>)object;
            if (list.size() >= 1) {
                if ((int)list.get(list.size() - 1) == jtnJ.lfKR.ordinal()) {
                    XMTT.GAPX((ArrayList<Object>)list);
                    if (DROu.\u2009\u200a\u2009\u200e\u200d\u2002\u2000\u2008) {
                        throw null;
                    }
                }
                else if (list.get(list.size() - 1) == jtnJ.zEqJ.ordinal()) {
                    XMTT.OIIt((ArrayList<Object>)list);
                }
            }
            if (DROu.\u2009\u200a\u2009\u200e\u200d\u2002\u2000\u2008) {
                throw null;
            }
        }
    }
    
    public void HzdM(final EMRf emRf) {
        try {
            if (this.JmLq()) {
                final ArrayList<Object> list = new ArrayList<Object>();
                final Field[] array = emRf.getClass().getFields();
                final int length = array.length;
                int i = 0;
                while (i < length) {
                    list.add(array[i].get(emRf));
                    ++i;
                    if (DROu.\u2009\u200a\u2009\u200e\u200d\u2002\u2000\u2008) {
                        throw null;
                    }
                }
                this.wilV.writeObject(list);
                this.wilV.flush();
            }
            if (DROu.\u2009\u200a\u2009\u200e\u200d\u2002\u2000\u2008) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean JmLq() {
        boolean b;
        if (this.ZnqM != null || this.urgx != null || this.wilV != null) {
            b = true;
            if (DROu.\u2009\u200a\u2009\u200e\u200d\u2002\u2000\u2008) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public void mrKt() {
        try {
            if (this.ZnqM != null) {
                this.ZnqM.close();
            }
            this.ZnqM = null;
            if (this.wilV != null) {
                this.wilV.close();
            }
            this.wilV = null;
            if (this.urgx != null) {
                this.urgx.close();
            }
            this.urgx = null;
            if (DROu.\u2009\u200a\u2009\u200e\u200d\u2002\u2000\u2008) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
