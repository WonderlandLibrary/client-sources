/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.vfs;

import com.google.common.collect.AbstractIterator;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.reflections.Reflections;
import org.reflections.vfs.Vfs;
import org.slf4j.Logger;

public class CommonsVfs2UrlType
implements Vfs.UrlType {
    public boolean matches(URL url) throws Exception {
        try {
            FileSystemManager manager = VFS.getManager();
            FileObject fileObject = manager.resolveFile(url.toExternalForm());
            return fileObject.exists() && fileObject.getType() == FileType.FOLDER;
        }
        catch (FileSystemException e) {
            Reflections.log.warn("Could not create CommonsVfs2UrlType from url " + url.toExternalForm(), (Throwable)e);
            return false;
        }
    }

    public Vfs.Dir createDir(URL url) throws Exception {
        FileSystemManager manager = VFS.getManager();
        FileObject fileObject = manager.resolveFile(url.toExternalForm());
        return new Dir(fileObject);
    }

    public static class File
    implements Vfs.File {
        private final FileObject root;
        private final FileObject file;

        public File(FileObject root, FileObject file) {
            this.root = root;
            this.file = file;
        }

        public String getName() {
            return this.file.getName().getBaseName();
        }

        public String getRelativePath() {
            String filepath = this.file.getName().getPath().replace("\\", "/");
            if (filepath.startsWith(this.root.getName().getPath())) {
                return filepath.substring(this.root.getName().getPath().length() + 1);
            }
            return null;
        }

        public InputStream openInputStream() throws IOException {
            return this.file.getContent().getInputStream();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Dir
    implements Vfs.Dir {
        private final FileObject file;

        public Dir(FileObject file) {
            this.file = file;
        }

        @Override
        public String getPath() {
            try {
                return this.file.getURL().getPath();
            }
            catch (FileSystemException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Iterable<Vfs.File> getFiles() {
            return new Iterable<Vfs.File>(){

                @Override
                public Iterator<Vfs.File> iterator() {
                    return new FileAbstractIterator();
                }
            };
        }

        @Override
        public void close() {
            try {
                this.file.close();
            }
            catch (FileSystemException fileSystemException) {
                // empty catch block
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        private class FileAbstractIterator
        extends AbstractIterator<Vfs.File> {
            final Stack<FileObject> stack = new Stack();

            private FileAbstractIterator() {
                this.listDir(Dir.this.file);
            }

            protected Vfs.File computeNext() {
                while (!this.stack.isEmpty()) {
                    FileObject file = this.stack.pop();
                    try {
                        if (this.isDir(file)) {
                            this.listDir(file);
                            continue;
                        }
                        return this.getFile(file);
                    }
                    catch (FileSystemException e) {
                        throw new RuntimeException(e);
                    }
                }
                return (Vfs.File)this.endOfData();
            }

            private File getFile(FileObject file) {
                return new File(Dir.this.file, file);
            }

            private boolean listDir(FileObject file) {
                return this.stack.addAll(this.listFiles(file));
            }

            private boolean isDir(FileObject file) throws FileSystemException {
                return file.getType() == FileType.FOLDER;
            }

            protected List<FileObject> listFiles(FileObject file) {
                try {
                    FileObject[] files = file.getType().hasChildren() ? file.getChildren() : null;
                    return files != null ? Arrays.asList(files) : new ArrayList<FileObject>();
                }
                catch (FileSystemException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

}

