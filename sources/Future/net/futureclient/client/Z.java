package net.futureclient.client;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import net.futureclient.client.modules.world.Wallhack;

public class Z extends q
{
    public final Wallhack k;
    
    public Z(final Wallhack k, final String s) {
        this.k = k;
        super(s);
    }
    
    @Override
    public void e(final Object... array) {
        try {
            if (!this.e().exists()) {
                this.e().createNewFile();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.e()));
            final Iterator<Block> iterator2;
            Iterator<Block> iterator = iterator2 = this.k.d.iterator();
            while (iterator.hasNext()) {
                final ResourceLocation resourceLocation;
                if ((resourceLocation = (ResourceLocation)Block.REGISTRY.getNameForObject((Object)iterator2.next())) != null) {
                    bufferedWriter.write(resourceLocation.toString());
                }
                bufferedWriter.newLine();
                iterator = iterator2;
            }
            bufferedWriter.close();
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }
    
    @Override
    public void M(final Object... array) {
        this.k.d.clear();
        try {
            if (this.e().length() > 6000L && !this.e().delete()) {
                this.e().deleteOnExit();
            }
            if (!this.e().exists()) {
                this.e().createNewFile();
                Wallhack.M(this.k);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        if (!this.e().exists()) {
            return;
        }
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.e()));
        Label_0108:
            while (true) {
                BufferedReader bufferedReader2 = bufferedReader;
                String line;
                while ((line = bufferedReader2.readLine()) != null) {
                    try {
                        final Block blockFromName;
                        if ((blockFromName = Block.getBlockFromName(line)) != null) {
                            this.k.d.add(blockFromName);
                            continue Label_0108;
                        }
                        continue Label_0108;
                    }
                    catch (Exception ex2) {
                        bufferedReader2 = bufferedReader;
                        ex2.printStackTrace();
                        continue;
                    }
                    break;
                }
                break;
            }
            bufferedReader.close();
        }
        catch (Exception ex3) {
            ex3.printStackTrace();
        }
    }
}
