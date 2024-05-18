package me.protocol_client.files.allfiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.protocol_client.thanks_slicky.properties.ListManager;

public class OtherFileManager extends ListManager<GuiFile> {
    public OtherFileManager() {
        super(new ArrayList<>());
    }

    @Override
    public void setup() {
    }

    public void loadAllFiles() {
        for (GuiFile file : contents) {
            try {
                file.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveAllFiles() {
        for (GuiFile file : contents) {
            try {
                file.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFile(String fileName) {
        for (GuiFile file : contents) {
            try {
                if (file.getName().equalsIgnoreCase(fileName)) {
                    file.save();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadFile(String fileName) {
        for (GuiFile file : contents) {
            try {
                if (file.getName().equalsIgnoreCase(fileName)) {
                    file.load();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public abstract class ListManager<T> {
        protected List<T> contents;

        public ListManager(List<T> contents) {
            this.contents = contents;
        }

        public List<T> getContents() {
            return contents;
        }

        public void setup() {
        }
    }
}
