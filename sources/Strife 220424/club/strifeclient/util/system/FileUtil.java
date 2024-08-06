package club.strifeclient.util.system;

import net.minecraft.util.Util;
import org.lwjglx.Sys;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class FileUtil {

    public static final class Zip {
        public static void unzipFolder(final File source, final Path target) throws IOException {
            try (final ZipInputStream zis = new ZipInputStream(new FileInputStream(source))) {
                ZipEntry zipEntry = zis.getNextEntry();
                while (zipEntry != null) {
                    boolean isDirectory = zipEntry.getName().endsWith(File.separator);
                    final Path newPath = zipSlipProtect(zipEntry, target);
                    if (isDirectory)
                        Files.createDirectories(newPath);
                    else {
                        if (newPath.getParent() != null)
                            if (Files.notExists(newPath.getParent()))
                                Files.createDirectories(newPath.getParent());
                        Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                    zipEntry = zis.getNextEntry();
                }
                zis.closeEntry();
            }
        }

        public static void unzip(String zipFilePath, String destDirectory) throws IOException {
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdir();
            }
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();
            // iterates over entries in the zip file
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // if the entry is a file, extracts it
                    extractFile(zipIn, filePath);
                } else {
                    // if the entry is a directory, make the directory
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
        }

        public static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] bytesIn = new byte[8192];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
            bos.close();
        }

        public static Path zipSlipProtect(ZipEntry zipEntry, Path targetDir) throws IOException {
            final Path targetDirResolved = targetDir.resolve(zipEntry.getName());
            final Path normalizePath = targetDirResolved.normalize();
            if (!normalizePath.startsWith(targetDir))
                throw new IOException("Bad zip entry: " + zipEntry.getName());
            return normalizePath;
        }
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static boolean openFolder(File file) {
        String path = file.getAbsolutePath();
        try {
            switch (Util.getOSType()) {
                case OSX:
                    Runtime.getRuntime().exec(new String[]{"/usr/bin/open", path});
                    break;
                case WINDOWS:
                    Runtime.getRuntime().exec(String.format("cmd.exe /C start \"Open file\" \"%s\"", path));
                    break;
                default:
                    boolean failed = false;
                    try {
                        Class<?> oClass = Class.forName("java.awt.Desktop");
                        Object object = oClass.getMethod("getDesktop", new Class[0]).invoke(null);
                        oClass.getMethod("browse", new Class[]{URI.class}).invoke(object, file.toURI());
                    } catch (Throwable throwable) {
                        System.out.println("Couldn't open link.\n" + Arrays.toString(throwable.getStackTrace()));
                        failed = true;
                    }
                    if (failed) {
                        if (!Sys.openURL("file://" + path))
                            return false;
                    }
                    break;
            }
            return true;
        } catch (IOException e) {
            System.out.println("Couldn't open folder.\n" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}
