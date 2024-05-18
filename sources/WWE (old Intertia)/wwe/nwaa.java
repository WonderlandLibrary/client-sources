package wwe;

import java.net.*;
import java.io.*;
import java.util.*;
import wwe.utils.*;
import java.lang.reflect.*;

public class nwaa extends Thread
{
    public Socket zLtI;
    public ObjectInputStream kmiX;
    public ObjectOutputStream TaQG;
    public boolean jdqo;
    public String svnA;
    public ArrayList<cJGk> SoAu;
    public ArrayList<String> Ycaq;
    public ArrayList<Kdwt> CkvS;
    public int JQUw;
    public String xLiJ;
    
    public nwaa() {
        super();
        this.jdqo = false;
        this.SoAu = new ArrayList<cJGk>();
        this.Ycaq = new ArrayList<String>();
        this.CkvS = new ArrayList<Kdwt>();
        this.JQUw = -1;
        this.xLiJ = "localhost";
        try {
            this.AByw();
            this.start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void AByw() {
        try {
            this.svnA = null;
            this.zLtI = new Socket(this.xLiJ, 2121);
            this.TaQG = new ObjectOutputStream(this.zLtI.getOutputStream());
            this.kmiX = new ObjectInputStream(this.zLtI.getInputStream());
            this.DKWU(new nbue(Wrapper.mc.getSession().getUsername(), "2.7.2", "1.12.2 forge", Wrapper.mc.getSession().getProfile().getId().toString(), this.JQUw));
        }
        catch (Exception ex) {
            this.svnA = "Couldent connect to the WWE server, probably down):";
            this.eCZB();
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        while (true) {
            this.xKdg();
        }
    }
    
    public void xKdg() {
        try {
            Thread.sleep(0x36CC9482786E3AEEL ^ 0x36CC9482786E3B1AL);
            if (this.xFrI()) {
                this.WFcy();
            }
        }
        catch (Exception ex) {
            this.svnA = "Couldent connect to the WWE server, probably down): Error message 1";
            this.eCZB();
        }
    }
    
    public void WFcy() throws Exception {
        if (!this.xFrI()) {
            return;
        }
        Object object;
        while ((object = this.kmiX.readObject()) != null) {
            final ArrayList<Integer> list = (ArrayList<Integer>)object;
            if (list.size() >= 1) {
                if ((int)list.get(list.size() - 1) == xglv.boBT.ordinal()) {
                    MHOv.ZGAN((ArrayList<Object>)list);
                }
                else if (list.get(list.size() - 1) == xglv.syWl.ordinal()) {
                    MHOv.PGUO((ArrayList<Object>)list);
                }
                else if (list.get(list.size() - 1) == xglv.dxrk.ordinal()) {
                    MHOv.JbkI((ArrayList<Object>)list);
                }
                else if (list.get(list.size() - 1) == xglv.oLld.ordinal()) {
                    MHOv.Elgt((ArrayList<Object>)list);
                }
                else if (list.get(list.size() - 1) == xglv.EcHe.ordinal()) {
                    MHOv.GoaD((ArrayList<Object>)list);
                }
                else if (list.get(list.size() - 1) == xglv.Hbdl.ordinal()) {
                    MHOv.Ujlm((ArrayList<Object>)list);
                }
                else if (list.get(list.size() - 1) == xglv.dKlB.ordinal()) {
                    MHOv.iOWJ((ArrayList<Object>)list);
                }
                else {
                    if (list.get(list.size() - 1) == xglv.LnkR.ordinal()) {
                        MHOv.KZvi((ArrayList<Object>)list);
                        break;
                    }
                    if (list.get(list.size() - 1) == xglv.WEUh.ordinal()) {
                        MHOv.uBan((ArrayList<Object>)list);
                    }
                    else if (list.get(list.size() - 1) == xglv.pQBU.ordinal()) {
                        MHOv.hMgJ((ArrayList<Object>)list);
                    }
                    else if (list.get(list.size() - 1) == xglv.PePW.ordinal()) {
                        MHOv.QoYT((ArrayList<Object>)list);
                    }
                    else if (list.get(list.size() - 1) == xglv.AkRM.ordinal()) {
                        MHOv.WSND((ArrayList<Object>)list);
                    }
                }
                list.clear();
            }
        }
    }
    
    public void DKWU(final xthd.wwe/vgeS wwe/vgeS) {
        try {
            if (this.xFrI()) {
                final ArrayList<Object> list = new ArrayList<Object>();
                final Field[] array = wwe/vgeS.getClass().getFields();
                for (int length = array.length, i = 0; i < length; ++i) {
                    list.add(array[i].get(wwe/vgeS));
                }
                this.TaQG.writeObject(list);
                this.TaQG.flush();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean xFrI() {
        return this.zLtI != null || this.kmiX != null || this.TaQG != null;
    }
    
    public void eCZB() {
        try {
            if (this.zLtI != null) {
                this.zLtI.close();
            }
            this.zLtI = null;
            if (this.TaQG != null) {
                this.TaQG.close();
            }
            this.TaQG = null;
            if (this.kmiX != null) {
                this.kmiX.close();
            }
            this.kmiX = null;
            this.CkvS.clear();
            this.Ycaq.clear();
            this.SoAu.clear();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
