package wwe;

import net.minecraft.client.multiplayer.*;
import net.minecraftforge.fml.common.*;
import org.lwjgl.opengl.*;
import wwe.utils.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import java.util.*;
import java.net.*;
import java.io.*;
import net.minecraft.util.*;

@Mod(modid = "wwe", name = "wwe", version = "2.7.2", acceptableRemoteVersions = "*")
public class vlwv
{
    public static final String FXtG = "WWE";
    public static final String dlOH = "2.7.2";
    public static eOnJ hNxW;
    public static DhFe YNBy;
    public static hEGx pRNz;
    private static rpiY jXAD;
    public static Quvl IJGi;
    public static flSi fomn;
    public static ArrayList<hfst> zkER;
    public static boolean lHpz;
    public static ServerData FjUS;
    public static ArrayList<String> uPqc;
    public static float Axho;
    public static cRUT VFOG;
    public static nwaa kXkt;
    public static ArrayList<IcRI> tiEw;
    public static sbfc wMQO;
    
    public vlwv() {
        super();
    }
    
    @Mod.EventHandler
    public void JHOS(final FMLInitializationEvent fmlInitializationEvent) {
        final ProgressManager.ProgressBar push = ProgressManager.push("WWE", 7, true);
        push.step("Setting Up");
        vlwv.tiEw = new ArrayList<IcRI>();
        vlwv.hNxW = new eOnJ();
        vlwv.YNBy = new DhFe();
        vlwv.zkER = new ArrayList<hfst>();
        vlwv.pRNz = new hEGx();
        vlwv.fomn = new flSi();
        Display.setTitle("WWE Version 2.7.2");
        bozE.JduO();
        vlwv.uPqc = new ArrayList<String>();
        vlwv.VFOG = new cRUT();
        vlwv.wMQO = new sbfc();
        Wrapper.ypJK().YTps();
        Wrapper.ypJK().YiMl();
        Wrapper.ypJK().CXTl();
        Wrapper.ypJK().IBoz();
        Wrapper.ypJK().slrP();
        Wrapper.ypJK().UlzW();
        Wrapper.ypJK().JIqk();
        Wrapper.ypJK().nPHj();
        Wrapper.ypJK().CrJR();
        Wrapper.ypJK().YfyR();
        Wrapper.ypJK().UPkU();
        Wrapper.ypJK().trRc();
        Wrapper.ypJK().CLso();
        Wrapper.ypJK().rhSy();
        Wrapper.ypJK().XwQQ();
        Wrapper.ypJK().MbuN();
        Wrapper.ypJK().QcuG();
        Wrapper.ypJK().bVwI();
        Wrapper.ypJK().HFWe();
        push.step("Checking Users With Capes");
        PKGo.OyGy();
        push.step("Getting Custom WWE Splash's");
        qdlI.jBhh = cZoo.fxyp();
        push.step("Checking For Update");
        qdlI.WFhU = RXVB.XSfe();
        push.step("Checking Important Messages");
        qdlI.gGMK = WzVI.Iean();
        push.step("Getting Current WWE users");
        wNWV();
        push.step("Almost Done");
        IDwi.SPcL();
        IDwi.lZga(Wrapper.mc.getSession().getUsername());
    }
    
    @Mod.EventHandler
    public void uKOF(final FMLPostInitializationEvent fmlPostInitializationEvent) {
        yYGD.vDDx();
        MinecraftForge.EVENT_BUS.register((Object)new XybC());
    }
    
    public static String xYZU() {
        return "WWE";
    }
    
    public static String eQrq() {
        return "2.7.2";
    }
    
    public static void XHpH() {
        final Iterator<qMIe> iterator = vlwv.hNxW.xRen().iterator();
        while (iterator.hasNext()) {
            iterator.next().qHNm();
            if (vlwv.\u2005) {
                throw null;
            }
        }
    }
    
    public static DhFe oYtq() {
        return vlwv.YNBy;
    }
    
    public static rpiY Zqnd() {
        if (vlwv.jXAD == null) {
            vlwv.jXAD = new rpiY();
        }
        return vlwv.jXAD;
    }
    
    public static void wNWV() {
        try {
            final ArrayList<String> uPqc = new ArrayList<String>();
            HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("http://wweclient.com/WhosOn.php?name=" + Wrapper.mc.getSession().getUsername()).openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "WWE CLIENT BEST CLIENT USER-AGENT FOR CURRENT USERS");
            final int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 302 || responseCode == 301) {
                httpURLConnection = (HttpURLConnection)new URL(httpURLConnection.getHeaderField("Location")).openConnection();
                httpURLConnection.setRequestProperty("User-Agent", "WWE CLIENT BEST CLIENT USER-AGENT FOR CURRENT USERS");
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                uPqc.add(line);
                if (vlwv.\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            vlwv.uPqc = uPqc;
            if (vlwv.\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean xXAB(final String s) {
        boolean b;
        if (StringUtils.stripControlCodes(s).equalsIgnoreCase("IKnowImEZ") || StringUtils.stripControlCodes(s).equalsIgnoreCase("THEREALWWEFAN231") || StringUtils.stripControlCodes(s).equalsIgnoreCase("WWEClient")) {
            b = true;
            if (vlwv.\u2005) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    static {
        vlwv.Axho = 1.0f;
    }
}
