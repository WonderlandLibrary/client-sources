package lol.point.returnclient.util.system;

import lol.point.Return;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class FileUtil {
    public static void createFolder(String name) {
        Path directoryPath = Paths.get(name);
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e) {
                Return.LOGGER.error("Failed to create the directory: {} due to {}", name, e.getMessage().toLowerCase(Locale.ROOT));
            }
        }
    }

    public static void unzip(Path zipFilePath, String targetDirectory) throws IOException {
        File dir = new File(targetDirectory);
        if (!dir.exists()) dir.mkdirs();

        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                File file = new File(targetDirectory, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    // Ensure parent directories are created
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = zipIn.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                    }
                }
                zipIn.closeEntry();
            }
        }
    }

    public static java.util.List<String> getFilesInDirectory(String directoryPath) throws IOException {
        List<String> filenames = new ArrayList<>();

        Path path = Paths.get(directoryPath);
        if (Files.exists(path) && Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                for (Path entry : directoryStream) {
                    filenames.add(entry.getFileName().toString());
                }
            }
        } else {
            throw new IOException("Directory not found or is not a directory: " + directoryPath);
        }

        return filenames;
    }

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');
        } catch (Exception e) {
            /*    > / ~ / <    */
        }

        return stringBuilder.toString();
    }

    public static String listFiles(String directoryPath, String fileType) {
        File directory = new File(directoryPath);
        StringBuilder fileList = new StringBuilder();

        // Check if the directory exists and is indeed a directory
        if (directory.exists() && directory.isDirectory()) {
            File[] files;
            if (fileType.isEmpty()) {
                files = directory.listFiles();
            } else {
                files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith("." + fileType.toLowerCase()));
            }

            // If there are files, append their names (without extensions) to the StringBuilder
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    String fileName = files[i].getName();
                    int dotIndex = fileName.lastIndexOf('.');
                    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                        fileName = fileName.substring(0, dotIndex);
                    }
                    fileList.append("§7 - §6").append(fileName).append("§f");
                    if (i != files.length - 1) {
                        fileList.append(",\n");
                    }
                }
            }
        }

        return fileList.toString();
    }
}
