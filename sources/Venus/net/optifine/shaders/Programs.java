/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.util.ArrayList;
import java.util.List;
import net.optifine.shaders.Program;
import net.optifine.shaders.ProgramStage;

public class Programs {
    private List<Program> programs = new ArrayList<Program>();
    private Program programNone = this.make("", ProgramStage.NONE, false);

    public Program make(String string, ProgramStage programStage, Program program) {
        int n = this.programs.size();
        Program program2 = new Program(n, string, programStage, program);
        this.programs.add(program2);
        return program2;
    }

    private Program make(String string, ProgramStage programStage, boolean bl) {
        int n = this.programs.size();
        Program program = new Program(n, string, programStage, bl);
        this.programs.add(program);
        return program;
    }

    public Program makeGbuffers(String string, Program program) {
        return this.make(string, ProgramStage.GBUFFERS, program);
    }

    public Program makeComposite(String string) {
        return this.make(string, ProgramStage.COMPOSITE, this.programNone);
    }

    public Program makeDeferred(String string) {
        return this.make(string, ProgramStage.DEFERRED, this.programNone);
    }

    public Program makeShadow(String string, Program program) {
        return this.make(string, ProgramStage.SHADOW, program);
    }

    public Program makeVirtual(String string) {
        return this.make(string, ProgramStage.NONE, false);
    }

    public Program[] makePrograms(String string, int n, ProgramStage programStage, Program program) {
        Program[] programArray = new Program[n];
        for (int i = 0; i < n; ++i) {
            String string2 = i == 0 ? string : string + i;
            programArray[i] = this.make(string2, programStage, this.programNone);
        }
        return programArray;
    }

    public Program[] makeComposites(String string, int n) {
        return this.makePrograms(string, n, ProgramStage.COMPOSITE, this.programNone);
    }

    public Program[] makeShadowcomps(String string, int n) {
        return this.makePrograms(string, n, ProgramStage.SHADOWCOMP, this.programNone);
    }

    public Program[] makePrepares(String string, int n) {
        return this.makePrograms(string, n, ProgramStage.PREPARE, this.programNone);
    }

    public Program[] makeDeferreds(String string, int n) {
        return this.makePrograms(string, n, ProgramStage.DEFERRED, this.programNone);
    }

    public Program getProgramNone() {
        return this.programNone;
    }

    public int getCount() {
        return this.programs.size();
    }

    public Program getProgram(String string) {
        if (string == null) {
            return null;
        }
        for (int i = 0; i < this.programs.size(); ++i) {
            Program program = this.programs.get(i);
            String string2 = program.getName();
            if (!string2.equals(string)) continue;
            return program;
        }
        return null;
    }

    public String[] getProgramNames() {
        String[] stringArray = new String[this.programs.size()];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = this.programs.get(i).getName();
        }
        return stringArray;
    }

    public Program[] getPrograms() {
        return this.programs.toArray(new Program[this.programs.size()]);
    }

    public Program[] getPrograms(Program program, Program program2) {
        int n;
        int n2 = program.getIndex();
        if (n2 > (n = program2.getIndex())) {
            int n3 = n2;
            n2 = n;
            n = n3;
        }
        Program[] programArray = new Program[n - n2 + 1];
        for (int i = 0; i < programArray.length; ++i) {
            programArray[i] = this.programs.get(n2 + i);
        }
        return programArray;
    }

    public String toString() {
        return this.programs.toString();
    }
}

