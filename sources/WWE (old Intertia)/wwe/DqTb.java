package wwe;

import org.lwjgl.input.*;
import net.minecraft.block.*;
import wwe.utils.*;
import net.minecraft.client.network.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import wwe.modules.combat.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.io.*;
import wwe.modules.world.*;
import java.util.regex.*;
import wwe.modules.player.*;
import java.awt.*;
import wwe.modules.render.*;
import net.minecraft.util.text.*;

public class DqTb
{
    public File XeLH;
    public File IXkJ;
    public File oSNt;
    public File aClN;
    public boolean zzbo;
    
    public DqTb() {
        super();
        this.XeLH = new File(Wrapper.mc.gameDir + File.separator + "WWE");
        if (!this.XeLH.exists()) {
            this.XeLH.mkdirs();
            this.zzbo = true;
        }
        this.IXkJ = new File(this.XeLH.getAbsolutePath() + File.separator + "WWE Settings");
        if (!this.IXkJ.exists()) {
            this.IXkJ.mkdirs();
        }
        this.oSNt = new File(this.XeLH.getAbsolutePath() + File.separator + "WWE Presets");
        if (!this.oSNt.exists()) {
            this.oSNt.mkdirs();
        }
        this.aClN = new File(this.XeLH.getAbsolutePath() + File.separator + "WWE Notebot");
        if (!this.aClN.exists()) {
            this.aClN.mkdirs();
        }
    }
    
    public void ZqQq() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Modules.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final qMIe qmIe : vlwv.hNxW.xRen()) {
                if (qmIe.IuiN() && qmIe.LXyR()) {
                    bufferedWriter.write(qmIe.czUH());
                    bufferedWriter.write("\r\n");
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void YTps() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Modules.txt");
            if (!file.exists()) {
                this.ZqQq();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                for (final qMIe qmIe : vlwv.hNxW.xRen()) {
                    if (qmIe.czUH().equals(line)) {
                        qmIe.GDVp();
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                        continue;
                    }
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.ZqQq();
        }
    }
    
    public void hUMI() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "keybinds.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final qMIe qmIe : vlwv.hNxW.xRen()) {
                final int aPjp = qmIe.APjp();
                if (aPjp <= 114) {
                    bufferedWriter.write(qmIe.czUH().toLowerCase().replace(" ", "") + " " + Keyboard.getKeyName(aPjp));
                    bufferedWriter.write("\r\n");
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            HAxG.TuzD("Failed to save keybind!");
        }
    }
    
    public void YiMl() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "keybinds.txt");
            if (!file.exists()) {
                this.hUMI();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] array = line.toLowerCase().trim().split(" ");
                final String s = array[0];
                final int keyIndex = Keyboard.getKeyIndex(array[1].toUpperCase());
                for (final qMIe qmIe : vlwv.hNxW.xRen()) {
                    if (s.equalsIgnoreCase(qmIe.czUH().toLowerCase().replace(" ", ""))) {
                        qmIe.hzzk(keyIndex);
                    }
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void pRVY() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "friends.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final LdXC ldXC : vlwv.Zqnd().padc) {
                bufferedWriter.write(ldXC.CjlP() + " " + ldXC.ugqf());
                bufferedWriter.write("\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void CXTl() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "friends.txt");
            if (!file.exists()) {
                this.pRVY();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String trim = line.trim();
                vlwv.Zqnd().kDXm(trim.split(" ")[0], trim.split(" ")[1]);
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void TQyP() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "xray.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final Block block : Xray.dMba) {
                bufferedWriter.write(Block.getIdFromBlock(block) + ":" + block.getLocalizedName().replaceAll("\\s+", "_") + "\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void IBoz() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "xray.txt");
            if (!file.exists()) {
                this.TQyP();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Xray.dMba.add(Block.getBlockById(Integer.parseInt(line.trim().split(":")[0])));
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.TQyP();
        }
    }
    
    public void rXPb() {
        try {
            final File file = new File(this.IXkJ.getAbsolutePath(), "Settings.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final IcRI<Object> icRI : vlwv.tiEw) {
                if (icRI.wnpo()) {
                    bufferedWriter.write(icRI.lJZv.czUH() + ":" + icRI.VQBN + ":Boolean:" + icRI.OnGi() + "\r\n");
                }
                if (icRI.dTln()) {
                    bufferedWriter.write(icRI.lJZv.czUH() + ":" + icRI.VQBN + ":String:" + icRI.OnGi() + "\r\n");
                }
                if (icRI.MATs()) {
                    bufferedWriter.write(icRI.lJZv.czUH() + ":" + icRI.VQBN + ":Double:" + icRI.OnGi() + "\r\n");
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void slrP() {
        try {
            final File file = new File(this.IXkJ.getAbsolutePath(), "Settings.txt");
            if (!file.exists()) {
                this.rXPb();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String s = line.trim().split(":")[0];
                final String s2 = line.trim().split(":")[1];
                final String s3 = line.trim().split(":")[2];
                final String s4 = line.trim().split(":")[3];
                for (final IcRI icRI : vlwv.tiEw) {
                    if (icRI.lJZv.czUH().equals(s) && icRI.VQBN.equals(s2)) {
                        if (s3.equals("Boolean") && icRI.wnpo()) {
                            ((BooleanValue)icRI).thqV(Boolean.parseBoolean(s4));
                            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                                throw null;
                            }
                        }
                        else if (s3.equals("String") && icRI.dTln()) {
                            ((ModeValue)icRI).thqV(s4);
                            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                                throw null;
                            }
                        }
                        else if (s3.equals("Double") && icRI.MATs()) {
                            ((NumberValue)icRI).thqV(Double.parseDouble(s4));
                        }
                    }
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.rXPb();
        }
    }
    
    public void VvWw() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Gui.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final FinI finI : vlwv.pRNz.nYFA) {
                bufferedWriter.write(finI.XLez.name() + "=" + finI.kVnf + "=" + finI.CQIZ + "=" + finI.aBNN + "\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            final BufferedWriter bufferedWriter2 = bufferedWriter;
            final StringBuilder sb = new StringBuilder();
            final hEGx prNz = vlwv.pRNz;
            final StringBuilder append = sb.append(hEGx.aTlJ.nLkA).append("=");
            final hEGx prNz2 = vlwv.pRNz;
            final StringBuilder append2 = append.append(hEGx.aTlJ.OsiI).append("=");
            final hEGx prNz3 = vlwv.pRNz;
            final StringBuilder append3 = append2.append(hEGx.aTlJ.zzJD).append("=");
            final hEGx prNz4 = vlwv.pRNz;
            bufferedWriter2.write(append3.append(hEGx.aTlJ.wMBz).append("\r\n").toString());
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void UlzW() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Gui.txt");
            if (!file.exists()) {
                this.VvWw();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String trim = line.trim();
                final String s = trim.split("=")[0];
                final String s2 = trim.split("=")[1];
                final String s3 = trim.split("=")[2];
                final String s4 = trim.split("=")[3];
                for (final FinI finI : vlwv.pRNz.nYFA) {
                    if (finI.XLez.name().equals(s)) {
                        finI.kVnf = Integer.parseInt(s2);
                        finI.CQIZ = Integer.parseInt(s3);
                        finI.aBNN = Boolean.parseBoolean(s4);
                    }
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                final hEGx prNz = vlwv.pRNz;
                hEGx.aTlJ.OsiI = Integer.parseInt(s2);
                final hEGx prNz2 = vlwv.pRNz;
                hEGx.aTlJ.zzJD = Integer.parseInt(s3);
                final hEGx prNz3 = vlwv.pRNz;
                hEGx.aTlJ.wMBz = Boolean.parseBoolean(s4);
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.VvWw();
        }
    }
    
    public void XRKn() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "ColorManager.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final RCMD rcmd : vlwv.fomn.JNkz) {
                bufferedWriter.write(rcmd.VGnY + " " + rcmd.zlwQ + " " + rcmd.elYg + " " + rcmd.Kbjh + "\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void JIqk() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "ColorManager.txt");
            if (!file.exists()) {
                this.XRKn();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String trim = line.trim();
                final String s = trim.split(" ")[0];
                final String s2 = trim.split(" ")[1];
                final String s3 = trim.split(" ")[2];
                final String s4 = trim.split(" ")[3];
                for (final RCMD rcmd : vlwv.fomn.JNkz) {
                    if (rcmd.VGnY.equalsIgnoreCase(s)) {
                        rcmd.zlwQ = Integer.parseInt(s2);
                        rcmd.elYg = Integer.parseInt(s3);
                        rcmd.Kbjh = Integer.parseInt(s4);
                    }
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.XRKn();
        }
    }
    
    public void iroC() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "inventorycleaner.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            final Iterator<Item> iterator = InventoryCleaner.KtLJ.iterator();
            while (iterator.hasNext()) {
                bufferedWriter.write(Item.getIdFromItem((Item)iterator.next()) + "\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void nPHj() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "inventorycleaner.txt");
            if (!file.exists()) {
                this.iroC();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                InventoryCleaner.KtLJ.add(Item.getItemById(Integer.parseInt(line)));
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.iroC();
        }
    }
    
    public ArrayList<String> fmny() throws IOException {
        final File file = new File(this.XeLH.getAbsolutePath(), "Spammer.txt");
        if (!file.exists()) {
            file.createNewFile();
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("> WWE is the best free anarchy utility mod, download at wweclient.com\r\nWWE Client's spammer\r\nLol get gud\r\nFuture client is bad\r\nWWE > Future\r\nWWE > Impact\r\nDefault Message\r\nIKnowImEZ is a god\r\nTHEREALWWEFAN231 is a god\r\nWWE Client made by IKnowImEZ/THEREALWWEFAN231\r\nWWE Client was the first public client to have Path Finder/New Chunks\r\nWWE Client was the first public client to have color signs\r\nWWE Client was the first client to have Teleport Finder");
            bufferedWriter.close();
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
        final ArrayList<String> list = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        bufferedReader.close();
        return list;
    }
    
    public ArrayList<String> jRoj(final NetworkPlayerInfo networkPlayerInfo) throws IOException {
        final File file = new File(this.XeLH.getAbsolutePath(), "Join Messages.txt");
        if (!file.exists()) {
            file.createNewFile();
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("Oh, You're back again !p ?\r\nGood to see you again, !p !\r\nAww, it's you !p\r\nGood evening, !p\r\nNice to see you !p");
            bufferedWriter.close();
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
        final ArrayList<String> list = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line.replaceAll("!p", networkPlayerInfo.getGameProfile().getName()));
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        bufferedReader.close();
        return list;
    }
    
    public ArrayList<String> Nprd(final NetworkPlayerInfo networkPlayerInfo) throws IOException {
        final File file = new File(this.XeLH.getAbsolutePath(), "Leave Messages.txt");
        if (!file.exists()) {
            file.createNewFile();
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("Well, It was nice to have you here, !p\r\nBye, Bye !p\r\nHope you had a good time, !p");
            bufferedWriter.close();
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
        final ArrayList<String> list = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line.replaceAll("!p", networkPlayerInfo.getGameProfile().getName()));
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        bufferedReader.close();
        return list;
    }
    
    public void VLak() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Way Points.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final Xssq xssq : WayPoints.COfQ) {
                bufferedWriter.write(xssq.CkDS + ":" + xssq.Nehz.getX() + ":" + xssq.Nehz.getY() + ":" + xssq.Nehz.getZ() + ":" + xssq.rnCG.getDyeDamage() + ":" + xssq.ADFS + ":" + xssq.kKMJ + ":" + xssq.MUAZ);
                bufferedWriter.write("\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void CrJR() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Way Points.txt");
            if (!file.exists()) {
                this.VLak();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String trim = line.trim();
                WayPoints.VlSt(new Xssq(trim.split(":")[0], new BlockPos(Integer.parseInt(trim.split(":")[1]), Integer.parseInt(trim.split(":")[2]), Integer.parseInt(trim.split(":")[3])), EnumDyeColor.byDyeDamage(Integer.parseInt(trim.split(":")[4])), Boolean.parseBoolean(trim.split(":")[5]), Boolean.parseBoolean(trim.split(":")[6]), Boolean.parseBoolean(trim.split(":")[7])));
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.VLak();
        }
    }
    
    public void qmiM() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Module Visability.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final qMIe qmIe : vlwv.hNxW.xRen()) {
                bufferedWriter.write(qmIe.czUH() + ":" + qmIe.sUHq() + "\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void YfyR() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Module Visability.txt");
            if (!file.exists()) {
                this.qmiM();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String trim = line.trim();
                final String s = trim.split(":")[0];
                Boolean.parseBoolean(trim.split(":")[1]);
                for (final qMIe qmIe : vlwv.hNxW.xRen()) {
                    if (qmIe.czUH().equals(s)) {
                        final qMIe qmIe2 = qmIe;
                        final boolean b = true;
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                        qmIe2.FSRf(b);
                    }
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.qmiM();
        }
    }
    
    public void hziJ(final String s) {
        final File file = new File(this.oSNt.getAbsolutePath() + File.separator + s);
        if (!file.exists()) {
            file.mkdirs();
        }
        for (final hfst hfst : vlwv.zkER) {
            try {
                final File file2 = new File(file.getAbsolutePath(), "Modules.txt");
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2));
                for (final qMIe qmIe : hfst.dqNd) {
                    if (qmIe.IuiN()) {
                        bufferedWriter.write(qmIe.czUH());
                        bufferedWriter.write("\r\n");
                    }
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                bufferedWriter.close();
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            catch (Exception ex) {}
            try {
                final File file3 = new File(file.getAbsolutePath(), "Settings.txt");
                if (!file3.exists()) {
                    file3.createNewFile();
                }
                final BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file3));
                for (final IcRI<Object> icRI : vlwv.tiEw) {
                    if (icRI.wnpo()) {
                        bufferedWriter2.write(icRI.lJZv.czUH() + ":" + icRI.VQBN + ":Boolean:" + icRI.OnGi() + "\r\n");
                    }
                    if (icRI.dTln()) {
                        bufferedWriter2.write(icRI.lJZv.czUH() + ":" + icRI.VQBN + ":String:" + icRI.OnGi() + "\r\n");
                    }
                    if (icRI.MATs()) {
                        bufferedWriter2.write(icRI.lJZv.czUH() + ":" + icRI.VQBN + ":Double:" + icRI.OnGi() + "\r\n");
                    }
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                bufferedWriter2.close();
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            catch (Exception ex2) {}
            try {
                final File file4 = new File(file.getAbsolutePath(), "xray.txt");
                if (!file4.exists()) {
                    file4.createNewFile();
                }
                final BufferedWriter bufferedWriter3 = new BufferedWriter(new FileWriter(file4));
                for (final Block block : Xray.dMba) {
                    bufferedWriter3.write(Block.getIdFromBlock(block) + ":" + block.getLocalizedName().replaceAll("\\s+", "_") + "\r\n");
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                bufferedWriter3.close();
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            catch (Exception ex3) {}
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
    }
    
    public void eTCw(final String s) {
        final File file = new File(this.oSNt.getAbsolutePath() + File.separator + s);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            final File file2 = new File(file.getAbsolutePath(), "Modules.txt");
            if (!file2.exists()) {
                file2.createNewFile();
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file2.getAbsolutePath()))));
            final ArrayList<String> list = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            for (final qMIe qmIe : vlwv.hNxW.xRen()) {
                if (list.contains(qmIe.czUH()) && !qmIe.IuiN()) {
                    qmIe.GDVp();
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                else if (!list.contains(qmIe.czUH()) && qmIe.IuiN()) {
                    qmIe.GDVp();
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            final File file3 = new File(file.getAbsolutePath(), "Settings.txt");
            if (!file3.exists()) {
                file3.createNewFile();
            }
            final BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file3.getAbsolutePath()))));
            String line2;
            while ((line2 = bufferedReader2.readLine()) != null) {
                final String s2 = line2.trim().split(":")[0];
                final String s3 = line2.trim().split(":")[1];
                final String s4 = line2.trim().split(":")[2];
                final String s5 = line2.trim().split(":")[3];
                for (final IcRI icRI : vlwv.tiEw) {
                    if (icRI.lJZv.czUH().equals(s2) && icRI.VQBN.equals(s3)) {
                        if (s4.equals("Boolean") && icRI.wnpo()) {
                            ((BooleanValue)icRI).thqV(Boolean.parseBoolean(s5));
                            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                                throw null;
                            }
                        }
                        else if (s4.equals("String") && icRI.dTln()) {
                            ((ModeValue)icRI).thqV(s5);
                            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                                throw null;
                            }
                        }
                        else if (s4.equals("Double") && icRI.MATs()) {
                            ((NumberValue)icRI).thqV(Double.parseDouble(s5));
                        }
                    }
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader2.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
        try {
            final File file4 = new File(file.getAbsolutePath(), "xray.txt");
            if (!file4.exists()) {
                file4.createNewFile();
            }
            final BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file4.getAbsolutePath()))));
            final ArrayList<Block> dMba = new ArrayList<Block>();
            String line3;
            while ((line3 = bufferedReader3.readLine()) != null) {
                dMba.add(Block.getBlockById(Integer.parseInt(line3.trim().split(":")[0])));
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            Xray.dMba = dMba;
            bufferedReader3.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex3) {
            ex3.printStackTrace();
            this.TQyP();
        }
    }
    
    public void UPkU() {
        class sFmK implements FilenameFilter
        {
            final DqTb YUxS;
            
            sFmK(final DqTb yUxS) {
                this.YUxS = yUxS;
                super();
            }
            
            @Override
            public boolean accept(final File file, final String s) {
                return new File(file, s).isDirectory();
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: new             Ljava/io/File;
        //     7: dup            
        //     8: aload_0        
        //     9: getfield        wwe/DqTb.oSNt:Ljava/io/File;
        //    12: invokevirtual   java/io/File.getAbsolutePath:()Ljava/lang/String;
        //    15: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //    18: astore_1       
        //    19: aload_1        
        //    20: new             Lwwe/sFmK;
        //    23: dup            
        //    24: aload_0        
        //    25: invokespecial   wwe/sFmK.<init>:(Lwwe/DqTb;)V
        //    28: invokevirtual   java/io/File.list:(Ljava/io/FilenameFilter;)[Ljava/lang/String;
        //    31: checkcast       [Ljava/lang/String;
        //    34: astore_2       
        //    35: aload_2        
        //    36: astore_3       
        //    37: aload_3        
        //    38: arraylength    
        //    39: istore          4
        //    41: ldc_w           0
        //    44: istore          5
        //    46: iload           5
        //    48: iload           4
        //    50: if_icmpge       103
        //    53: aload_3        
        //    54: iload           5
        //    56: aaload         
        //    57: astore          6
        //    59: getstatic       wwe/vlwv.pRNz:Lwwe/hEGx;
        //    62: pop            
        //    63: getstatic       wwe/hEGx.aTlJ:Lwwe/XdhZ;
        //    66: new             Lwwe/hfst;
        //    69: dup            
        //    70: aload           6
        //    72: aload_0        
        //    73: aload           6
        //    75: invokevirtual   wwe/DqTb.begv:(Ljava/lang/String;)Ljava/util/ArrayList;
        //    78: invokespecial   wwe/hfst.<init>:(Ljava/lang/String;Ljava/util/ArrayList;)V
        //    81: ldc_w           0
        //    84: invokevirtual   wwe/XdhZ.QWLO:(Lwwe/hfst;Z)V
        //    87: iinc            5, 1
        //    90: getstatic       wwe/DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005:Z
        //    93: ifeq            46
        //    96: aconst_null    
        //    97: athrow         
        //    98: nop            
        //    99: nop            
        //   100: nop            
        //   101: nop            
        //   102: athrow         
        //   103: getstatic       wwe/DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005:Z
        //   106: ifeq            116
        //   109: aconst_null    
        //   110: athrow         
        //   111: astore_1       
        //   112: aload_1        
        //   113: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   116: return         
        //   117: nop            
        //   118: nop            
        //   119: nop            
        //   120: nop            
        //   121: athrow         
        //    StackMapTable: 00 08 FF 00 03 00 08 07 00 02 00 00 00 00 00 00 01 00 00 00 FF 00 29 00 08 07 00 02 07 00 2E 07 01 01 07 01 01 01 01 00 01 00 00 FF 00 33 00 00 00 01 07 00 2A FF 00 04 00 08 07 00 02 07 00 2E 07 01 01 07 01 01 01 01 00 01 00 00 FF 00 07 00 08 07 00 02 00 00 00 00 00 00 01 00 01 07 00 6E FF 00 04 00 08 07 00 02 07 00 04 00 00 00 00 00 01 00 00 FF 00 00 00 00 00 01 07 00 2A
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  4      98     111    116    Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public ArrayList<qMIe> begv(final String s) throws IOException {
        final File file = new File(this.oSNt.getAbsolutePath() + File.separator + s);
        if (!file.exists()) {
            file.mkdirs();
        }
        final File file2 = new File(file.getAbsolutePath(), "Modules.txt");
        if (!file2.exists()) {
            file2.createNewFile();
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file2.getAbsolutePath()))));
        final ArrayList<qMIe> list = new ArrayList<qMIe>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final String trim = line.trim();
            for (final qMIe qmIe : vlwv.hNxW.xRen()) {
                if (qmIe.czUH().equals(trim)) {
                    list.add(qmIe);
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        bufferedReader.close();
        return list;
    }
    
    public void JDPi() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Search.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final Map.Entry<Block, Color> entry : Search.Hxmz.entrySet()) {
                bufferedWriter.write(Block.getIdFromBlock((Block)entry.getKey()) + ":" + entry.getKey().getLocalizedName().replaceAll("\\s+", "_") + ":" + entry.getValue().getRed() + ":" + entry.getValue().getGreen() + ":" + entry.getValue().getBlue() + "\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void trRc() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Search.txt");
            if (!file.exists()) {
                this.JDPi();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Search.Hxmz.put(Block.getBlockById(Integer.parseInt(line.trim().split(":")[0])), new Color(Integer.parseInt(line.trim().split(":")[2]), Integer.parseInt(line.trim().split(":")[3]), Integer.parseInt(line.trim().split(":")[4])));
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.JDPi();
        }
    }
    
    public void GwVp() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Auto Sign.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("1:" + AutoSign.ALQZ[0].getUnformattedText() + "\r\n");
            bufferedWriter.write("2:" + AutoSign.ALQZ[1].getUnformattedText() + "\r\n");
            bufferedWriter.write("3:" + AutoSign.ALQZ[2].getUnformattedText() + "\r\n");
            bufferedWriter.write("4:" + AutoSign.ALQZ[3].getUnformattedText() + "\r\n");
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void CLso() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Auto Sign.txt");
            if (!file.exists()) {
                this.GwVp();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String s = line.trim().split(":")[0];
                final String s2 = line.trim().split(":")[1];
                if (s.equals("1")) {
                    AutoSign.ALQZ[0] = (ITextComponent)new TextComponentString(s2);
                }
                if (s.equals("2")) {
                    AutoSign.ALQZ[1] = (ITextComponent)new TextComponentString(s2);
                }
                if (s.equals("3")) {
                    AutoSign.ALQZ[2] = (ITextComponent)new TextComponentString(s2);
                }
                if (s.equals("4")) {
                    AutoSign.ALQZ[3] = (ITextComponent)new TextComponentString(s2);
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.GwVp();
        }
    }
    
    public void iDTw() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "F3 Spoofer.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final FZwI.wwe/DqTb wwe/DqTb : F3Spoofer.HHRM) {
                final BufferedWriter bufferedWriter2 = bufferedWriter;
                final StringBuilder append = new StringBuilder().append(wwe/DqTb.NKEQ).append(":").append(wwe/DqTb.wgqj).append(":");
                String plwx;
                if (wwe/DqTb.Plwx.equals("") || wwe/DqTb.Plwx == null) {
                    plwx = "WWEnothing";
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                else {
                    plwx = wwe/DqTb.Plwx;
                }
                bufferedWriter2.write(append.append(plwx).append("\r\n").toString());
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void rhSy() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "F3 Spoofer.txt");
            if (!file.exists()) {
                this.iDTw();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String s = line.trim().split(":")[0];
                final String s2 = line.trim().split(":")[1];
                final String plwx = line.trim().split(":")[2];
                for (final FZwI.wwe/DqTb wwe/DqTb : F3Spoofer.HHRM) {
                    if (wwe/DqTb.NKEQ.equals(s)) {
                        wwe/DqTb.wgqj = Boolean.parseBoolean(s2);
                        if (!plwx.equals("WWEnothing")) {
                            wwe/DqTb.Plwx = plwx;
                        }
                    }
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void stpV() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Log Out Spots.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final YEmw yEmw : LogOutSpot.zEsy) {
                bufferedWriter.write(yEmw.AAtC + ":" + yEmw.Yhqv + ":" + yEmw.EQEV + ":" + yEmw.YdCe + ":" + yEmw.TZqL + ":" + yEmw.AepW.getTime());
                bufferedWriter.write("\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void edMP() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Log Out Spots.txt");
            if (!file.exists()) {
                this.stpV();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String trim = line.trim();
                LogOutSpot.zEsy.add(new YEmw(Double.parseDouble(trim.split(":")[1]), Double.parseDouble(trim.split(":")[2]), Double.parseDouble(trim.split(":")[3]), trim.split(":")[0], UUID.fromString(trim.split(":")[4]), null, new Date(Long.parseLong(trim.split(":")[5]))));
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void XwQQ() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Custom Image.png");
            if (file.exists()) {
                final ResourceLocation mpCo = FIwW.MPCo(file);
                if (mpCo != null) {
                    if (qdlI.JTTe.size() > qdlI.GHWo) {
                        qdlI.JTTe.set(qdlI.JTTe.size() - 1, mpCo);
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                    }
                    else {
                        qdlI.JTTe.add(mpCo);
                    }
                }
            }
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<String> kLDM() {
        final ArrayList<String> list = new ArrayList<String>();
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Sign Symbols.txt");
            if (!file.exists()) {
                file.createNewFile();
                final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
                bufferedWriter.write("µ\r\n");
                bufferedWriter.write("¶\r\n");
                bufferedWriter.write("\u0164\r\n");
                bufferedWriter.write("\u0163\r\n");
                bufferedWriter.write("\u0162\r\n");
                bufferedWriter.write("\u0166\r\n");
                bufferedWriter.write("\u0168\r\n");
                bufferedWriter.write("\u0169\r\n");
                bufferedWriter.write("\u0170\r\n");
                bufferedWriter.write("\u0171\r\n");
                int i = 0;
                while (i < "To edit   theses    symbols   go to your.minecraftfolder,   then wwe  then edit the Sign  Symbols.txt   file. Replace   sign".length()) {
                    bufferedWriter.write("To edit   theses    symbols   go to your.minecraftfolder,   then wwe  then edit the Sign  Symbols.  txt file. Replace   sign".charAt(i) + "\r\n");
                    ++i;
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                bufferedWriter.close();
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath())), "UTF8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public void pRYs() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "nuker.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final Block block : Nuker.mTio) {
                bufferedWriter.write(Block.getIdFromBlock(block) + ":" + block.getLocalizedName().replaceAll("\\s+", "_") + "\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public void MbuN() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "nuker.txt");
            if (!file.exists()) {
                this.pRYs();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Nuker.mTio.add(Block.getBlockById(Integer.parseInt(line.trim().split(":")[0])));
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.pRYs();
        }
    }
    
    public void QFCM() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "chat bot.txt");
            if (!file.exists()) {
                final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                final UUBS.wwe/DqTb[] array = UUBS.wwe/DqTb.DsUE();
                final int length = array.length;
                int i = 0;
                while (i < length) {
                    final UUBS.wwe/DqTb wwe/DqTb = array[i];
                    final StringBuilder sb = new StringBuilder();
                    final String[] otco = wwe/DqTb.Otco;
                    final int length2 = otco.length;
                    int j = 0;
                    while (j < length2) {
                        sb.append("\"" + otco[j] + "\",");
                        ++j;
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                    }
                    final String string = sb.toString();
                    bufferedWriter.write(wwe/DqTb.XGUi + ":" + string.substring(0, string.length() - 1) + "\r\n");
                    ++i;
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                bufferedWriter.close();
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            ChatBot.aVmP.clear();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String s = line.split(":")[0];
                final String s2 = line.split(":")[1];
                UUBS.wwe/DqTb wwe/DqTb2 = null;
                final UUBS.wwe/DqTb[] array2 = UUBS.wwe/DqTb.DsUE();
                final int length3 = array2.length;
                int k = 0;
                while (k < length3) {
                    final UUBS.wwe/DqTb wwe/DqTb3 = array2[k];
                    if (wwe/DqTb3.XGUi.equalsIgnoreCase(s)) {
                        wwe/DqTb2 = wwe/DqTb3;
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++k;
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                        continue;
                    }
                }
                if (wwe/DqTb2 != null) {
                    final Matcher matcher = Pattern.compile("\"([^\"]*)\"").matcher(line);
                    final ArrayList<String> list = new ArrayList<String>();
                    while (matcher.find()) {
                        list.add(matcher.group(1));
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                    }
                    wwe/DqTb2.xlWb = list.toArray(new String[list.size()]);
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                else {
                    final Matcher matcher2 = Pattern.compile("\"([^\"]*)\"").matcher(line);
                    final ArrayList<String> list2 = new ArrayList<String>();
                    while (matcher2.find()) {
                        list2.add(matcher2.group(1));
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                    }
                    ChatBot.aVmP.add(new UUBS.wwe/DqTb(s, (String[])list2.toArray(new String[list2.size()])));
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<String> MFbs() throws IOException {
        final File file = new File(this.XeLH.getAbsolutePath(), "insulter.txt");
        if (!file.exists()) {
            file.createNewFile();
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("!object1 Download WWE utility mod, Its free!\r\n!object1 4b4t is da best mintscreft serber\r\n!object1 dont abouse\r\n!object1 you cuck\r\n!object1 https://www.youtube.com/channel/UCJGCNPEjvsCn0FKw3zso0TA\r\n!object1 is my step dad\r\n!object1 again daddy!\r\ndont worry !object1 it happens to every one\r\n!object1 dont buy future it's crap, compared to WWE!\r\nWhat are you, fucking gay, !object1?\r\nDid you know? !object1 hates you, !object2\r\nYou are literally 10, !object1\r\n!object1 finally lost their virginity, sadly they lost it to !object2... yeah, that's unfortunate.\r\n!object1, don't be upset, it's not like anyone cares about you, fag.\r\n!object1, see that rubbish bin over there? Get your ass in it, or I'll get !object2 to whoop your ass.\r\n!object1, may I borrow that dirt block? that guy named !object2 needs it...\r\nYo, !object1, btfo you virgin\r\nHey !object1 want to play some High School RP with me and !object2?\r\n!object1 is an Archon player. Why is he on here? Fucking factions player.\r\nDid you know? !object1 just joined The Vortex Coalition!\r\n!object1 has successfully conducted the cactus dupe and duped a itemhand!\r\n!object1, are you even human? You act like my dog, holy shit.\r\n!object1, you were never loved by your family.\r\nCome on !object1, you hurt !object2's feelings. You meany.\r\nStop trying to meme !object1, you can't do that. kek\r\n!object1, !object2 is gay. Don't go near him.\r\nWhoa !object2 didn't mean to offend you, !object1.\r\n!object1 im not pvping !object2, im WWE'ing !object2.");
            bufferedWriter.close();
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
        final ArrayList<String> list = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        bufferedReader.close();
        return list;
    }
    
    public void wBGm(final Announcer announcer) {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "announcer.txt");
            if (!file.exists()) {
                final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                for (final xbUc.wwe/DqTb wwe/DqTb : announcer.objE) {
                    final StringBuilder sb = new StringBuilder();
                    final String[] qfVg = wwe/DqTb.qFVg;
                    final int length = qfVg.length;
                    int i = 0;
                    while (i < length) {
                        sb.append("\"" + qfVg[i] + "\",");
                        ++i;
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                    }
                    final String string = sb.toString();
                    bufferedWriter.write(wwe/DqTb.zVOT + ":" + string.substring(0, string.length() - 1) + "\r\n");
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                bufferedWriter.close();
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String s = line.split(":")[0];
                final String s2 = line.split(":")[1];
                xbUc.wwe/DqTb wwe/DqTb2 = null;
                for (final xbUc.wwe/DqTb wwe/DqTb3 : announcer.objE) {
                    if (wwe/DqTb3.zVOT.equalsIgnoreCase(s)) {
                        wwe/DqTb2 = wwe/DqTb3;
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                        continue;
                    }
                }
                if (wwe/DqTb2 != null) {
                    final Matcher matcher = Pattern.compile("\"([^\"]*)\"").matcher(line);
                    final ArrayList<String> list = new ArrayList<String>();
                    while (matcher.find()) {
                        list.add(matcher.group(1));
                        if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                            throw null;
                        }
                    }
                    wwe/DqTb2.ACbc = list.toArray(new String[list.size()]);
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void OsgT() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "WWE Stuff.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("Auto Reconnect:" + vlwv.lHpz + "\r\n");
            bufferedWriter.write("Command Preset:" + DhFe.itOx + "\r\n");
            if (bozE.Jfgf != null) {
                bufferedWriter.write("Custom Font:" + bozE.Jfgf.KtJZ() + "\r\n");
            }
            bufferedWriter.write("Main Menu Index:" + qdlI.kbXN + "\r\n");
            bufferedWriter.write("Use WWE Menu:" + ssGO.jMLq + "\r\n");
            bufferedWriter.write("Main Menu Picture Cycle:" + qdlI.bppf);
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void QcuG() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "WWE Stuff.txt");
            if (!file.exists()) {
                this.OsgT();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String s = line.trim().split(":")[0];
                final String itOx = line.trim().split(":")[1];
                if (s.equals("Auto Reconnect")) {
                    vlwv.lHpz = Boolean.parseBoolean(itOx);
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                else if (s.equals("Command Preset")) {
                    DhFe.itOx = itOx;
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                else if (s.equals("Custom Font")) {
                    bozE.Jfgf = new giQz(new Font(itOx.trim(), 0, 36), true, 8);
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                else if (s.equals("Main Menu Index")) {
                    final int int1 = Integer.parseInt(itOx.trim());
                    if (int1 < qdlI.JTTe.size()) {
                        qdlI.kbXN = int1;
                    }
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                else if (s.equals("Use WWE Menu")) {
                    ssGO.jMLq = Boolean.parseBoolean(itOx);
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                else if (s.equals("Main Menu Picture Cycle")) {
                    qdlI.bppf = Boolean.parseBoolean(itOx);
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.OsgT();
        }
    }
    
    public void dheG() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Alt Accounts.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            final Iterator<aRkr> iterator = wGez.VkYf.iterator();
            while (iterator.hasNext()) {
                bufferedWriter.write(iterator.next().poph() + "\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void bVwI() {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Alt Accounts.txt");
            if (!file.exists()) {
                this.OsgT();
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(":")) {
                    final String[] array = line.split(":", 0);
                    wGez.VkYf.add(new aRkr(array[0], array[1]));
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                    continue;
                }
                else {
                    wGez.VkYf.add(new aRkr(line.trim()));
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                    continue;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.dheG();
        }
    }
    
    public void HLif(final String s, final ajLR ajLR, final boolean b) {
        try {
            File file = new File(this.aClN.getAbsolutePath(), s + ".txt");
            if (!file.exists()) {
                file.createNewFile();
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            else if (file.exists()) {
                int n = 0;
                file = new File(this.aClN.getAbsolutePath(), s + "_" + n + ".txt");
                while (file.exists()) {
                    file = new File(this.aClN.getAbsolutePath(), s + "_" + n + ".txt");
                    ++n;
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                file.createNewFile();
                HAxG.TuzD("You already have a note bot song with the name " + s + " so we are renaming it to, " + s + "_" + n);
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            else {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (final ajLR.wwe/ABpb wwe/ABpb : ajLR.nOsS) {
                bufferedWriter.write("lyric:" + wwe/ABpb.RZpY + ":" + wwe/ABpb.MSAn + "\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            for (final ajLR.wwe/WkII wwe/WkII : ajLR.FgWb) {
                bufferedWriter.write(wwe/WkII.cCcA + ":" + wwe/WkII.fljR + ":" + wwe/WkII.eEsY + "\r\n");
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public ajLR FpyV(final String s) {
        final ajLR ajLR = new ajLR(new ArrayList<ajLR.wwe/ABpb>(), new ArrayList<String>(), new ArrayList<ajLR.wwe/WkII>());
        try {
            final File file = new File(this.aClN.getAbsolutePath(), s);
            if (!file.exists()) {
                return null;
            }
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String trim = line.trim();
                final String s2 = trim.split(":")[0];
                final String s3 = trim.split(":")[1];
                final String s4 = trim.split(":")[2];
                if (s2.equals("lyric")) {
                    ajLR.nOsS.add(new ajLR.wwe/ABpb(Integer.parseInt(s3), s4));
                    if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                        throw null;
                    }
                }
                else {
                    final int int1 = Integer.parseInt(s4);
                    if (!ajLR.YDyR.contains(int1)) {
                        ajLR.YDyR.add(int1);
                    }
                    ajLR.FgWb.add(new ajLR.wwe/WkII(Integer.parseInt(s2), Integer.parseInt(s3), int1));
                }
                if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return ajLR;
    }
    
    public ArrayList<File> lUoc() {
        final ArrayList<File> list = new ArrayList<File>();
        final File[] array = this.aClN.listFiles();
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final File file = array[i];
            if (file.isFile() && file.getName().toLowerCase().substring(file.getName().lastIndexOf(".") + 1).equals("txt")) {
                list.add(file);
            }
            ++i;
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        return list;
    }
    
    public void pMRJ(final String s) {
        try {
            final File file = new File(this.XeLH.getAbsolutePath(), "Teleport Finder.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(s);
            bufferedWriter.close();
            if (DqTb.\u200d\u2007\u2001\u2004\u2006\u2001\u2002\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void HFWe() {
        if (this.zzbo && !vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN()) {
            vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).GDVp();
        }
    }
}
