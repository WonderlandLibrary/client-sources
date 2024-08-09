package xyz.mathax.mathaxclient.utils.notebot.decoder;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.utils.notebot.song.Note;
import xyz.mathax.mathaxclient.utils.notebot.song.Song;
import net.minecraft.block.enums.Instrument;

import java.io.*;

// https://github.com/koca2000/NoteBlockAPI/blob/master/src/main/java/com/xxmicloxx/NoteBlockAPI/utils/NBSDecoder.java

/**
 * Utils for reading Note Block Studio data
 *
 */
public class NBSSongDecoder extends SongDecoder {
    public static final int NOTE_OFFSET = 33; // Magic value (https://opennbs.org/nbs)

    @Override
    public Song parse(File songFile) {
        try {
            return parse(new FileInputStream(songFile), songFile);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public Song parse(InputStream inputStream) {
        return parse(inputStream, null); // Source is unknown -> no file
    }

    private Song parse(InputStream inputStream, File songFile) {
        Multimap<Integer, Note> notesMap = MultimapBuilder.linkedHashKeys().arrayListValues().build();
        try {
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            int nbsversion = 0;
            if (readShort(dataInputStream) == 0) {
                nbsversion = dataInputStream.readByte();
                dataInputStream.readByte(); // first custom instrument
            }

            readShort(dataInputStream); // Song Height
            String name = readString(dataInputStream);
            String author = readString(dataInputStream);
            readString(dataInputStream); // original author
            readString(dataInputStream); // description
            float speed = readShort(dataInputStream) / 100f;
            dataInputStream.readBoolean(); // auto-save
            dataInputStream.readByte(); // auto-save duration
            dataInputStream.readByte(); // x/4ths, time signature
            readInt(dataInputStream); // minutes spent on project
            readInt(dataInputStream); // left clicks (why?)
            readInt(dataInputStream); // right clicks (why?)
            readInt(dataInputStream); // blocks added
            readInt(dataInputStream); // blocks removed
            readString(dataInputStream); // .mid/.schematic file name
            if (nbsversion >= 4) {
                dataInputStream.readByte(); // loop on/off
                dataInputStream.readByte(); // max loop count
                readShort(dataInputStream); // loop start tick
            }

            double tick = -1;
            while (true) {
                short jumpTicks = readShort(dataInputStream); // jumps till next tick
                if (jumpTicks == 0) {
                    break;
                }

                tick += jumpTicks * (20f / speed);

                while (true) {
                    short jumpLayers = readShort(dataInputStream); // jumps till next layer
                    if (jumpLayers == 0) {
                        break;
                    }

                    byte instrument = dataInputStream.readByte();

                    byte key = dataInputStream.readByte();
                    if (nbsversion >= 4) {
                        dataInputStream.readByte(); // note block velocity
                        dataInputStream.readUnsignedByte(); // note panning, 0 is right in nbs format
                        readShort(dataInputStream); // note block pitch
                    }

                    Note note = new Note(fromNBSInstrument(instrument) /* instrument */, key - NOTE_OFFSET /* note */);
                    setNote((int) Math.round(tick), note, notesMap);
                }
            }

            return new Song(notesMap, name, author);
        } catch (EOFException exception) {
            String file = "";
            if (songFile != null) {
                file = songFile.getName();
            }

            MatHax.LOG.error("Song is corrupted: " + file, exception);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static void setNote(int ticks, Note note, Multimap<Integer, Note> notesMap) {
        notesMap.put(ticks, note);
    }

    private static short readShort(DataInputStream dataInputStream) throws IOException {
        int byte1 = dataInputStream.readUnsignedByte();
        int byte2 = dataInputStream.readUnsignedByte();

        return (short) (byte1 + (byte2 << 8));
    }

    private static int readInt(DataInputStream dataInputStream) throws IOException {
        int byte1 = dataInputStream.readUnsignedByte();
        int byte2 = dataInputStream.readUnsignedByte();
        int byte3 = dataInputStream.readUnsignedByte();
        int byte4 = dataInputStream.readUnsignedByte();

        return (byte1 + (byte2 << 8) + (byte3 << 16) + (byte4 << 24));
    }

    private static String readString(DataInputStream dataInputStream) throws IOException {
        int length = readInt(dataInputStream);
        StringBuilder builder = new StringBuilder(length);
        for (; length > 0; --length) {
            char c = (char) dataInputStream.readByte();
            if (c == (char) 0x0D) {
                c = ' ';
            }

            builder.append(c);
        }

        return builder.toString();
    }

    // Magic Values (https://opennbs.org/nbs)
    private static Instrument fromNBSInstrument(int instrument) {
        return switch (instrument) {
            case 0 -> Instrument.HARP;
            case 1 -> Instrument.BASS;
            case 2 -> Instrument.BASEDRUM;
            case 3 -> Instrument.SNARE;
            case 4 -> Instrument.HAT;
            case 5 -> Instrument.GUITAR;
            case 6 -> Instrument.FLUTE;
            case 7 -> Instrument.BELL;
            case 8 -> Instrument.CHIME;
            case 9 -> Instrument.XYLOPHONE;
            case 10 -> Instrument.IRON_XYLOPHONE;
            case 11 -> Instrument.COW_BELL;
            case 12 -> Instrument.DIDGERIDOO;
            case 13 -> Instrument.BIT;
            case 14 -> Instrument.BANJO;
            case 15 -> Instrument.PLING;
            default -> null;
        };
    }
}