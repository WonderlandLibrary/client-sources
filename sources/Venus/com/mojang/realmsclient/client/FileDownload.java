/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.client;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsDefaultUncaughtExceptionHandler;
import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.SharedConstants;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.WorldSummary;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileDownload {
    private static final Logger LOGGER = LogManager.getLogger();
    private volatile boolean field_224844_b;
    private volatile boolean field_224845_c;
    private volatile boolean field_224846_d;
    private volatile boolean field_224847_e;
    private volatile File field_224848_f;
    private volatile File field_224849_g;
    private volatile HttpGet field_224850_h;
    private Thread field_224851_i;
    private final RequestConfig field_224852_j = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).build();
    private static final String[] field_224853_k = new String[]{"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long func_224827_a(String string) {
        long l;
        Closeable closeable = null;
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(string);
            closeable = HttpClientBuilder.create().setDefaultRequestConfig(this.field_224852_j).build();
            CloseableHttpResponse closeableHttpResponse = ((CloseableHttpClient)closeable).execute(httpGet);
            long l2 = Long.parseLong(closeableHttpResponse.getFirstHeader("Content-Length").getValue());
            return l2;
        } catch (Throwable throwable) {
            LOGGER.error("Unable to get content length for download");
            l = 0L;
        } finally {
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException iOException) {
                    LOGGER.error("Could not close http client", (Throwable)iOException);
                }
            }
        }
        return l;
    }

    public void func_237688_a_(WorldDownload worldDownload, String string, RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus, SaveFormat saveFormat) {
        if (this.field_224851_i == null) {
            this.field_224851_i = new Thread(() -> this.lambda$func_237688_a_$0(worldDownload, downloadStatus, string, saveFormat));
            this.field_224851_i.setUncaughtExceptionHandler(new RealmsDefaultUncaughtExceptionHandler(LOGGER));
            this.field_224851_i.start();
        }
    }

    public void func_224834_a() {
        if (this.field_224850_h != null) {
            this.field_224850_h.abort();
        }
        if (this.field_224848_f != null) {
            this.field_224848_f.delete();
        }
        this.field_224844_b = true;
    }

    public boolean func_224835_b() {
        return this.field_224845_c;
    }

    public boolean func_224836_c() {
        return this.field_224846_d;
    }

    public boolean func_224837_d() {
        return this.field_224847_e;
    }

    public static String func_224828_b(String object) {
        object = ((String)object).replaceAll("[\\./\"]", "_");
        for (String string : field_224853_k) {
            if (!((String)object).equalsIgnoreCase(string)) continue;
            object = "_" + (String)object + "_";
        }
        return object;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void func_237690_a_(String string, File file, SaveFormat saveFormat) throws IOException {
        Object object;
        Pattern pattern = Pattern.compile(".*-([0-9]+)$");
        int n = 1;
        for (char c : SharedConstants.ILLEGAL_FILE_CHARACTERS) {
            string = string.replace(c, '_');
        }
        if (StringUtils.isEmpty(string)) {
            string = "Realm";
        }
        string = FileDownload.func_224828_b(string);
        try {
            object = saveFormat.getSaveList().iterator();
            while (object.hasNext()) {
                WorldSummary worldSummary = (WorldSummary)object.next();
                if (!worldSummary.getFileName().toLowerCase(Locale.ROOT).startsWith(string.toLowerCase(Locale.ROOT))) continue;
                Matcher matcher = pattern.matcher(worldSummary.getFileName());
                if (matcher.matches()) {
                    if (Integer.valueOf(matcher.group(1)) <= n) continue;
                    n = Integer.valueOf(matcher.group(1));
                    continue;
                }
                ++n;
            }
        } catch (Exception exception) {
            LOGGER.error("Error getting level list", (Throwable)exception);
            this.field_224846_d = true;
            return;
        }
        if (saveFormat.isNewLevelIdAcceptable(string) && n <= 1) {
            object = string;
        } else {
            object = string + (String)(n == 1 ? "" : "-" + n);
            if (!saveFormat.isNewLevelIdAcceptable((String)object)) {
                boolean bl = false;
                while (!bl) {
                    if (!saveFormat.isNewLevelIdAcceptable((String)(object = string + (String)(++n == 1 ? "" : "-" + n)))) continue;
                    bl = true;
                }
            }
        }
        TarArchiveInputStream tarArchiveInputStream = null;
        File file2 = new File(Minecraft.getInstance().gameDir.getAbsolutePath(), "saves");
        try {
            file2.mkdir();
            tarArchiveInputStream = new TarArchiveInputStream(new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(file))));
            Object object2 = tarArchiveInputStream.getNextTarEntry();
            while (object2 != null) {
                Comparable<File> comparable = new File(file2, ((TarArchiveEntry)object2).getName().replace("world", (CharSequence)object));
                if (((TarArchiveEntry)object2).isDirectory()) {
                    ((File)comparable).mkdirs();
                } else {
                    ((File)comparable).createNewFile();
                    try (FileOutputStream fileOutputStream = new FileOutputStream((File)comparable);){
                        IOUtils.copy((InputStream)tarArchiveInputStream, (OutputStream)fileOutputStream);
                    }
                }
                object2 = tarArchiveInputStream.getNextTarEntry();
            }
        } catch (Exception exception) {
            LOGGER.error("Error extracting world", (Throwable)exception);
            this.field_224846_d = true;
        } finally {
            if (tarArchiveInputStream != null) {
                tarArchiveInputStream.close();
            }
            if (file != null) {
                file.delete();
            }
            try (SaveFormat.LevelSave levelSave = saveFormat.getLevelSave((String)object);){
                levelSave.updateSaveName(((String)object).trim());
                Path path = levelSave.resolveFilePath(FolderName.LEVEL_DAT);
                FileDownload.func_237689_a_(path.toFile());
            } catch (IOException iOException) {
                LOGGER.error("Failed to rename unpacked realms level {}", object, (Object)iOException);
            }
            this.field_224849_g = new File(file2, (String)object + File.separator + "resources.zip");
        }
    }

    private static void func_237689_a_(File file) {
        if (file.exists()) {
            try {
                CompoundNBT compoundNBT = CompressedStreamTools.readCompressed(file);
                CompoundNBT compoundNBT2 = compoundNBT.getCompound("Data");
                compoundNBT2.remove("Player");
                CompressedStreamTools.writeCompressed(compoundNBT, file);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void lambda$func_237688_a_$0(WorldDownload worldDownload, RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus, String string, SaveFormat saveFormat) {
        Closeable closeable = null;
        try {
            this.field_224848_f = File.createTempFile("backup", ".tar.gz");
            this.field_224850_h = new HttpGet(worldDownload.field_230643_a_);
            closeable = HttpClientBuilder.create().setDefaultRequestConfig(this.field_224852_j).build();
            CloseableHttpResponse closeableHttpResponse = ((CloseableHttpClient)closeable).execute(this.field_224850_h);
            downloadStatus.field_225140_b = Long.parseLong(closeableHttpResponse.getFirstHeader("Content-Length").getValue());
            if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                FileOutputStream fileOutputStream = new FileOutputStream(this.field_224848_f);
                ProgressListener progressListener = new ProgressListener(this, string.trim(), this.field_224848_f, saveFormat, downloadStatus);
                DownloadCountingOutputStream downloadCountingOutputStream = new DownloadCountingOutputStream(this, fileOutputStream);
                downloadCountingOutputStream.func_224804_a(progressListener);
                IOUtils.copy(closeableHttpResponse.getEntity().getContent(), (OutputStream)downloadCountingOutputStream);
                return;
            }
            this.field_224846_d = true;
            this.field_224850_h.abort();
            return;
        } catch (Exception exception) {
            LOGGER.error("Caught exception while downloading: " + exception.getMessage());
            this.field_224846_d = true;
            return;
        } finally {
            block40: {
                block41: {
                    CloseableHttpResponse closeableHttpResponse;
                    this.field_224850_h.releaseConnection();
                    if (this.field_224848_f != null) {
                        this.field_224848_f.delete();
                    }
                    if (this.field_224846_d) break block40;
                    if (worldDownload.field_230644_b_.isEmpty() || worldDownload.field_230645_c_.isEmpty()) break block41;
                    try {
                        this.field_224848_f = File.createTempFile("resources", ".tar.gz");
                        this.field_224850_h = new HttpGet(worldDownload.field_230644_b_);
                        closeableHttpResponse = ((CloseableHttpClient)closeable).execute(this.field_224850_h);
                        downloadStatus.field_225140_b = Long.parseLong(closeableHttpResponse.getFirstHeader("Content-Length").getValue());
                        if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
                            this.field_224846_d = true;
                            this.field_224850_h.abort();
                            return;
                        }
                    } catch (Exception exception) {
                        LOGGER.error("Caught exception while downloading: " + exception.getMessage());
                        this.field_224846_d = true;
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(this.field_224848_f);
                    ResourcePackProgressListener resourcePackProgressListener = new ResourcePackProgressListener(this, this.field_224848_f, downloadStatus, worldDownload);
                    DownloadCountingOutputStream downloadCountingOutputStream = new DownloadCountingOutputStream(this, fileOutputStream);
                    downloadCountingOutputStream.func_224804_a(resourcePackProgressListener);
                    IOUtils.copy(closeableHttpResponse.getEntity().getContent(), (OutputStream)downloadCountingOutputStream);
                    break block40;
                    finally {
                        this.field_224850_h.releaseConnection();
                        if (this.field_224848_f != null) {
                            this.field_224848_f.delete();
                        }
                    }
                }
                this.field_224845_c = true;
            }
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException iOException) {
                    LOGGER.error("Failed to close Realms download client");
                }
            }
        }
    }

    class ProgressListener
    implements ActionListener {
        private final String field_224813_b;
        private final File field_224814_c;
        private final SaveFormat field_224815_d;
        private final RealmsDownloadLatestWorldScreen.DownloadStatus field_224816_e;
        final FileDownload this$0;

        private ProgressListener(FileDownload fileDownload, String string, File file, SaveFormat saveFormat, RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus) {
            this.this$0 = fileDownload;
            this.field_224813_b = string;
            this.field_224814_c = file;
            this.field_224815_d = saveFormat;
            this.field_224816_e = downloadStatus;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            this.field_224816_e.field_225139_a = ((DownloadCountingOutputStream)actionEvent.getSource()).getByteCount();
            if (this.field_224816_e.field_225139_a >= this.field_224816_e.field_225140_b && !this.this$0.field_224844_b && !this.this$0.field_224846_d) {
                try {
                    this.this$0.field_224847_e = true;
                    this.this$0.func_237690_a_(this.field_224813_b, this.field_224814_c, this.field_224815_d);
                } catch (IOException iOException) {
                    LOGGER.error("Error extracting archive", (Throwable)iOException);
                    this.this$0.field_224846_d = true;
                }
            }
        }
    }

    class DownloadCountingOutputStream
    extends CountingOutputStream {
        private ActionListener field_224806_b;
        final FileDownload this$0;

        public DownloadCountingOutputStream(FileDownload fileDownload, OutputStream outputStream) {
            this.this$0 = fileDownload;
            super(outputStream);
        }

        public void func_224804_a(ActionListener actionListener) {
            this.field_224806_b = actionListener;
        }

        @Override
        protected void afterWrite(int n) throws IOException {
            super.afterWrite(n);
            if (this.field_224806_b != null) {
                this.field_224806_b.actionPerformed(new ActionEvent(this, 0, null));
            }
        }
    }

    class ResourcePackProgressListener
    implements ActionListener {
        private final File field_224819_b;
        private final RealmsDownloadLatestWorldScreen.DownloadStatus field_224820_c;
        private final WorldDownload field_224821_d;
        final FileDownload this$0;

        private ResourcePackProgressListener(FileDownload fileDownload, File file, RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus, WorldDownload worldDownload) {
            this.this$0 = fileDownload;
            this.field_224819_b = file;
            this.field_224820_c = downloadStatus;
            this.field_224821_d = worldDownload;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            this.field_224820_c.field_225139_a = ((DownloadCountingOutputStream)actionEvent.getSource()).getByteCount();
            if (this.field_224820_c.field_225139_a >= this.field_224820_c.field_225140_b && !this.this$0.field_224844_b) {
                try {
                    String string = Hashing.sha1().hashBytes(Files.toByteArray(this.field_224819_b)).toString();
                    if (string.equals(this.field_224821_d.field_230645_c_)) {
                        FileUtils.copyFile(this.field_224819_b, this.this$0.field_224849_g);
                        this.this$0.field_224845_c = true;
                    } else {
                        LOGGER.error("Resourcepack had wrong hash (expected " + this.field_224821_d.field_230645_c_ + ", found " + string + "). Deleting it.");
                        FileUtils.deleteQuietly(this.field_224819_b);
                        this.this$0.field_224846_d = true;
                    }
                } catch (IOException iOException) {
                    LOGGER.error("Error copying resourcepack file", (Object)iOException.getMessage());
                    this.this$0.field_224846_d = true;
                }
            }
        }
    }
}

