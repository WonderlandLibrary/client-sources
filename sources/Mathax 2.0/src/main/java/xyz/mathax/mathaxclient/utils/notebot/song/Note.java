package xyz.mathax.mathaxclient.utils.notebot.song;

import net.minecraft.block.enums.Instrument;

import java.util.Objects;

public class Note {
    private Instrument instrument;

    private int noteLevel;

    public Note(Instrument instrument, int noteLevel) {
        this.instrument = instrument;
        this.noteLevel = noteLevel;
    }

    public Instrument getInstrument() {
        return this.instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public int getNoteLevel() {
        return noteLevel;
    }

    public void setNoteLevel(int noteLevel) {
        this.noteLevel = noteLevel;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Note note = (Note) object;
        return instrument == note.instrument && noteLevel == note.noteLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, noteLevel);
    }

    @Override
    public String toString() {
        return "Note{instrument=" + getInstrument() + ", noteLevel=" + getNoteLevel() + '}';
    }
}