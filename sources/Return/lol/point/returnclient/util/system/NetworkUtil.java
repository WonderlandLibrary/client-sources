package lol.point.returnclient.util.system;

import lol.point.Return;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class NetworkUtil {

    public static String raw(String url) throws IOException, URISyntaxException {
        StringBuilder result = new StringBuilder();
        URL url1 = new URI(url).toURL();
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        result.append(line);
                        firstLine = false;
                    } else {
                        result.append("\n").append(line);
                    }
                    return result.toString();
                }
            }
        } else {
            result.toString().trim();
        }
        return "-1";
    }

    public static void download_nozip(String fileURL, String targetDirectory) {
        try {
            Return.LOGGER.info("Starting download from URL: {}", fileURL);
            download(fileURL, targetDirectory);
            Return.LOGGER.info("Download completed. File saved to: {}", targetDirectory);
        } catch (IOException e) {
            Return.LOGGER.error("Error during download: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void download_zip(String fileURL, String targetDirectory) {
        Path tempZipFile = null;
        try {
            Return.LOGGER.info("Starting download from URL: {}", fileURL);
            tempZipFile = download(fileURL);
            Return.LOGGER.info("Download completed. Temp file created at: {}", tempZipFile);

            Return.LOGGER.info("Starting extraction to directory: {}", targetDirectory);
            FileUtil.unzip(tempZipFile, targetDirectory);
            Return.LOGGER.info("Extraction completed successfully.");
        } catch (IOException e) {
            Return.LOGGER.error("Error during download or extraction: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (tempZipFile != null) {
                try {
                    Files.delete(tempZipFile);
                    Return.LOGGER.info("Temporary file deleted: {}", tempZipFile);
                } catch (IOException e) {
                    Return.LOGGER.error("Failed to delete temporary file: {}", tempZipFile, e);
                }
            }
        }
    }

    private static Path download(String fileURL) throws IOException {
        URL url = null;
        try {
            url = new URI(fileURL).toURL();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Path tempZipFile = Files.createTempFile("downloaded", ".zip");

        try (InputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream out = new FileOutputStream(tempZipFile.toFile())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        return tempZipFile;
    }

    private static void download(String fileURL, String targetDirectory) throws IOException {
        URL url = null;
        try {
            url = new URI(fileURL).toURL();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Path targetPath = Paths.get(targetDirectory, Paths.get(url.getPath()).getFileName().toString());

        try (InputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream out = new FileOutputStream(targetPath.toFile())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

}
