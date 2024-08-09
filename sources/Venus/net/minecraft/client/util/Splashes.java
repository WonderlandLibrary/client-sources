/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

public class Splashes
extends ReloadListener<List<String>> {
    private static final ResourceLocation SPLASHES_LOCATION = new ResourceLocation("texts/splashes.txt");
    private static final Random RANDOM = new Random();
    private final List<String> possibleSplashes = Lists.newArrayList();
    private final Session gameSession;

    public Splashes(Session session) {
        this.gameSession = session;
    }

    /*
     * Enabled aggressive exception aggregation
     */
    @Override
    protected List<String> prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        try (IResource iResource = Minecraft.getInstance().getResourceManager().getResource(SPLASHES_LOCATION);){
            List<String> list;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iResource.getInputStream(), StandardCharsets.UTF_8));){
                list = bufferedReader.lines().map(String::trim).filter(Splashes::lambda$prepare$0).collect(Collectors.toList());
            }
            return list;
        } catch (IOException iOException) {
            return Collections.emptyList();
        }
    }

    @Override
    protected void apply(List<String> list, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.possibleSplashes.clear();
        this.possibleSplashes.addAll(list);
    }

    @Nullable
    public String getSplashText() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            return "Merry X-mas!";
        }
        if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            return "Happy new year!";
        }
        if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
            return "OOoooOOOoooo! Spooky!";
        }
        if (this.possibleSplashes.isEmpty()) {
            return null;
        }
        return this.gameSession != null && RANDOM.nextInt(this.possibleSplashes.size()) == 42 ? this.gameSession.getUsername().toUpperCase(Locale.ROOT) + " IS YOU" : this.possibleSplashes.get(RANDOM.nextInt(this.possibleSplashes.size()));
    }

    @Override
    protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.apply((List)object, iResourceManager, iProfiler);
    }

    @Override
    protected Object prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        return this.prepare(iResourceManager, iProfiler);
    }

    private static boolean lambda$prepare$0(String string) {
        return string.hashCode() != 125780783;
    }
}

