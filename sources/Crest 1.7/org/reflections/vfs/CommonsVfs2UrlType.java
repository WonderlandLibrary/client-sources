// 
// Decompiled by Procyon v0.5.30
// 

package org.reflections.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Stack;
import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemException;
import org.reflections.Reflections;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import java.net.URL;

public class CommonsVfs2UrlType implements Vfs.UrlType
{
    public boolean matches(final URL url) throws Exception {
        try {
            final FileSystemManager manager = VFS.getManager();
            final FileObject fileObject = manager.resolveFile(url.toExternalForm());
            return fileObject.exists() && fileObject.getType() == FileType.FOLDER;
        }
        catch (FileSystemException e) {
            Reflections.log.warn("Could not create CommonsVfs2UrlType from url " + url.toExternalForm(), (Throwable)e);
            return false;
        }
    }
    
    public Vfs.Dir createDir(final URL url) throws Exception {
        final FileSystemManager manager = VFS.getManager();
        final FileObject fileObject = manager.resolveFile(url.toExternalForm());
        return new Dir(fileObject);
    }
    
    public static class Dir implements Vfs.Dir
    {
        private final FileObject file;
        
        public Dir(final FileObject file) {
            this.file = file;
        }
        
        public String getPath() {
            try {
                return this.file.getURL().getPath();
            }
            catch (FileSystemException e) {
                throw new RuntimeException((Throwable)e);
            }
        }
        
        public Iterable<Vfs.File> getFiles() {
            return new Iterable<Vfs.File>() {
                public Iterator<Vfs.File> iterator() {
                    return (Iterator<Vfs.File>)new FileAbstractIterator();
                }
            };
        }
        
        public void close() {
            try {
                this.file.close();
            }
            catch (FileSystemException ex) {}
        }
        
        private class FileAbstractIterator extends AbstractIterator<Vfs.File>
        {
            final Stack<FileObject> stack;
            
            private FileAbstractIterator() {
                this.stack = new Stack<FileObject>();
                this.listDir(Dir.this.file);
            }
            
            protected Vfs.File computeNext() {
                while (!this.stack.isEmpty()) {
                    final FileObject file = this.stack.pop();
                    try {
                        if (!this.isDir(file)) {
                            return this.getFile(file);
                        }
                        this.listDir(file);
                    }
                    catch (FileSystemException e) {
                        throw new RuntimeException((Throwable)e);
                    }
                }
                return (Vfs.File)this.endOfData();
            }
            
            private CommonsVfs2UrlType.File getFile(final FileObject file) {
                return new CommonsVfs2UrlType.File(Dir.this.file, file);
            }
            
            private boolean listDir(final FileObject file) {
                return this.stack.addAll((Collection<?>)this.listFiles(file));
            }
            
            private boolean isDir(final FileObject file) throws FileSystemException {
                return file.getType() == FileType.FOLDER;
            }
            
            protected List<FileObject> listFiles(final FileObject file) {
                try {
                    final FileObject[] files = (FileObject[])(file.getType().hasChildren() ? file.getChildren() : null);
                    return (files != null) ? Arrays.asList(files) : new ArrayList<FileObject>();
                }
                catch (FileSystemException e) {
                    throw new RuntimeException((Throwable)e);
                }
            }
        }
    }
    
    public static class File implements Vfs.File
    {
        private final FileObject root;
        private final FileObject file;
        
        public File(final FileObject root, final FileObject file) {
            this.root = root;
            this.file = file;
        }
        
        public String getName() {
            return this.file.getName().getBaseName();
        }
        
        public String getRelativePath() {
            final String filepath = this.file.getName().getPath().replace("\\", "/");
            if (filepath.startsWith(this.root.getName().getPath())) {
                return filepath.substring(this.root.getName().getPath().length() + 1);
            }
            return null;
        }
        
        public InputStream openInputStream() throws IOException {
            return this.file.getContent().getInputStream();
        }
    }
}
