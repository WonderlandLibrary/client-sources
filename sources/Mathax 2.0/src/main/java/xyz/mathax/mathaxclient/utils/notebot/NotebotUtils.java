package xyz.mathax.mathaxclient.utils.notebot;

import xyz.mathax.mathaxclient.utils.notebot.song.Note;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.enums.Instrument;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class NotebotUtils {
    public static Note getNoteFromNoteBlock(BlockState noteBlock, NotebotMode mode) {
        Instrument instrument = null;
        int level = noteBlock.get(NoteBlock.NOTE);
        if (mode == NotebotMode.Exact_Instruments) {
            instrument = noteBlock.get(NoteBlock.INSTRUMENT);
        }

        return new Note(instrument, level);
    }

    public enum NotebotMode {
        Any_Instrument("Any instrument"),
        Exact_Instruments("Exact instruments");

        private final String name;

        NotebotMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum OptionalInstrument {
        None(null),
        Harp(Instrument.HARP),
        Basedrum(Instrument.BASEDRUM),
        Snare(Instrument.SNARE),
        Hat(Instrument.HAT),
        Bass(Instrument.BASS),
        Flute(Instrument.FLUTE),
        Bell(Instrument.BELL),
        Guitar(Instrument.GUITAR),
        Chime(Instrument.CHIME),
        Xylophone(Instrument.XYLOPHONE),
        IronXylophone(Instrument.IRON_XYLOPHONE),
        CowBell(Instrument.COW_BELL),
        Didgeridoo(Instrument.DIDGERIDOO),
        Bit(Instrument.BIT),
        Banjo(Instrument.BANJO),
        Pling(Instrument.PLING);

        public static final Map<Instrument, OptionalInstrument> BY_MINECRAFT_INSTRUMENT = new HashMap<>();

        static {
            for (OptionalInstrument optionalInstrument : values()) {
                BY_MINECRAFT_INSTRUMENT.put(optionalInstrument.minecraftInstrument, optionalInstrument);
            }
        }

        private final Instrument minecraftInstrument;

        OptionalInstrument(@Nullable Instrument minecraftInstrument) {
            this.minecraftInstrument = minecraftInstrument;
        }

        public Instrument toMinecraftInstrument() {
            return minecraftInstrument;
        }

        public static OptionalInstrument fromMinecraftInstrument(Instrument instrument) {
            if (instrument != null) {
                return BY_MINECRAFT_INSTRUMENT.get(instrument);
            } else {
                return null;
            }
        }
    }
}