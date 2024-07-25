package club.bluezenith.core.data;

import club.bluezenith.core.data.preferences.DataHandler;

import java.io.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static java.io.File.separator;
import static java.nio.file.Files.readAllBytes;
import static java.util.zip.Deflater.BEST_SPEED;

@SuppressWarnings("all")
public class ClientResourceRepository {

    protected DataHandler[] dataHandlers = new DataHandler[16];
    protected int lastDataHandler = 0;

    protected final File clientDirectory;

    public ClientResourceRepository(String path) {
        this(new File(path));
    }

    public ClientResourceRepository(File clientDirectory) {
        this.clientDirectory = clientDirectory;
    }

    public ClientResourceRepository hookHandlers(DataHandler... handlers) {
        for (DataHandler handler : handlers) {
            dataHandlers[lastDataHandler] = handler;
            lastDataHandler++;

            handler.deserialize();
        }
        return this;
    }

    public void onShutdown() {
        for (DataHandler dataHandler : dataHandlers) {
            if(dataHandler == null) continue;
            dataHandler.serialize();
        }
    }


    public File extend(String additionalPath) {
        return new File(this.clientDirectory.getAbsolutePath() + separator + additionalPath);
    }

    public boolean fileExists(String fileName) {
        return extend(fileName).exists();
    }

    public boolean fileExists(String folderName, String fileName) {
        return fileExists(folderName + separator + fileName);
    }

    public boolean createFileInDirectory(String fileName, boolean overwriteExisting) {
        final File newFile = extend(fileName);
        if(newFile.exists()) {
            if(overwriteExisting) {
                try {
                    return newFile.delete() && newFile.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                    return false;
                }
            } else return true;
        } else try {
            return newFile.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public boolean createFileInDirectory(String folderName, String fileName, boolean overwriteExisting) {
        return createFileInDirectory(folderName + separator + fileName, overwriteExisting);
    }

    public boolean createFolderInDirectory(String folderName, boolean overwriteExisting) {
        final File folder = extend(folderName);
        if(folder.exists()) {
            if(overwriteExisting) {
                return folder.delete() && folder.mkdirs();
            } else return true;
        } else return folder.mkdirs();
    }

    public boolean deleteFile(String fileName) {
        return extend(fileName).delete();//createFileInDirectory(fileName, true);
    }

    public String[] getAllFiles() {
        return this.clientDirectory.list();
    }

    public String[] getAllFilesInFolder(String folderName) {
        if(fileExists(folderName)) {
            return extend(folderName).list();
        } else return new String[]{};
    }

    public String getFilePath(String filename) {
        return extend(filename).getAbsolutePath();
    }

    public String getFilePath(String folderName, String fileName) {
        return extend(folderName + separator + fileName).getAbsolutePath();
    }

    public boolean writeToFile(String fileName, String fileContents) {
        final File file = extend(fileName);
        if(!file.exists()) create(file);

        try (final BufferedWriter writer = writer(file)) {
            writer.write(fileContents);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean writeToFile(String folderName, String fileName, String fileContents) {
        return writeToFile(folderName + separator + fileName, fileContents);
    }

    public File getClientDirectory() {
        return this.clientDirectory;
    }

    public String readFromFile(String fileName) {
         final File file = extend(fileName);
         if(!file.exists()) return null;

         try (final BufferedReader reader = reader(file)){
             final StringBuilder builder = new StringBuilder();
             String line;

             while ((line = reader.readLine()) != null) {
                 builder.append(line);
             }

             return builder.toString();
         } catch (IOException exception) {
             exception.printStackTrace();
         }

         return null;
    }

    public BufferedReader getReaderForFile(String fileName) {
        final File file = extend(fileName);
        if(!file.exists()) return null;

        try {
            return new BufferedReader(reader(file));
        } catch (Exception exception) {}

        return null;
    }

    public BufferedWriter getWriterForFile(String fileName) {
        final File file = extend(fileName);
        if(!file.exists()) return null;

        try {
            return new BufferedWriter(writer(file));
        } catch (Exception exception) {}

        return null;
    }

    public String readFromFile(String folderName, String fileName) {
        return readFromFile(folderName + separator + fileName);
    }

    public void writeCompressed(byte[] toCompress, File target) {
        if(toCompress.length == 0) return; //nothing to compress

        final Deflater deflater = new Deflater();
        deflater.setLevel(BEST_SPEED); //configure deflater
        deflater.setInput(toCompress);
        deflater.finish(); //tell it that there's nothing else to add

        try (final FileOutputStream fos = new FileOutputStream(target)) {
            final byte[] output = new byte[1024];

            while (!deflater.finished()) {
                final int arrayLength = deflater.deflate(output); // deflate() fills the provided array with the compressed data
                fos.write(output, 0, arrayLength);
            }

            deflater.end(); //the deflater is no longer used, release all resources. FileOutputStream is closed automatically.
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public byte[] getDecompressed(File target) {
        if(!target.exists()) throw new IllegalArgumentException("Attempted to decompress a non-existing file");

        Inflater inflater = new Inflater();

        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            final byte[] fileContent = readAllBytes(target.toPath());
            inflater.setInput(fileContent);

            final byte[] buffer = new byte[1024];

            while (!inflater.finished()) {
                final int arrayLength = inflater.inflate(buffer);
                output.write(buffer, 0, arrayLength);
            }
            inflater.end(); //release all resources.
            return output.toByteArray();
        } catch (IOException | DataFormatException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private BufferedWriter writer(File file) throws IOException {
        if(!file.exists()) create(file);
        return new BufferedWriter(new FileWriter(file));
    }

    private BufferedReader reader(File file) throws IOException {
        if(!file.exists()) create(file);
        return new BufferedReader(new FileReader(file));
    }

    private void create(File file) {
        try {
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
