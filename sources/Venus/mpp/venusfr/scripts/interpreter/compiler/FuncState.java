/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler;

import java.util.Hashtable;
import mpp.venusfr.scripts.interpreter.LocVars;
import mpp.venusfr.scripts.interpreter.Lua;
import mpp.venusfr.scripts.interpreter.LuaDouble;
import mpp.venusfr.scripts.interpreter.LuaInteger;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Prototype;
import mpp.venusfr.scripts.interpreter.Upvaldesc;
import mpp.venusfr.scripts.interpreter.compiler.Constants;
import mpp.venusfr.scripts.interpreter.compiler.InstructionPtr;
import mpp.venusfr.scripts.interpreter.compiler.IntPtr;
import mpp.venusfr.scripts.interpreter.compiler.LexState;
import mpp.venusfr.scripts.interpreter.compiler.LuaC;

public class FuncState
extends Constants {
    Prototype f;
    Hashtable h;
    FuncState prev;
    LexState ls;
    BlockCnt bl;
    int pc;
    int lasttarget;
    IntPtr jpc;
    int nk;
    int np;
    int firstlocal;
    short nlocvars;
    short nactvar;
    short nups;
    short freereg;

    FuncState() {
    }

    InstructionPtr getcodePtr(LexState.expdesc expdesc2) {
        return new InstructionPtr(this.f.code, expdesc2.u.info);
    }

    int getcode(LexState.expdesc expdesc2) {
        return this.f.code[expdesc2.u.info];
    }

    int codeAsBx(int n, int n2, int n3) {
        return this.codeABx(n, n2, n3 + 131071);
    }

    void setmultret(LexState.expdesc expdesc2) {
        this.setreturns(expdesc2, -1);
    }

    void checkrepeated(LexState.Labeldesc[] labeldescArray, int n, LuaString luaString) {
        for (int i = this.bl.firstlabel; i < n; ++i) {
            if (!luaString.eq_b(labeldescArray[i].name)) continue;
            String string = this.ls.L.pushfstring("label '" + luaString + " already defined on line " + labeldescArray[i].line);
            this.ls.semerror(string);
        }
    }

    void checklimit(int n, int n2, String string) {
        if (n > n2) {
            this.errorlimit(n2, string);
        }
    }

    void errorlimit(int n, String string) {
        String string2 = this.f.linedefined == 0 ? this.ls.L.pushfstring("main function has more than " + n + " " + string) : this.ls.L.pushfstring("function at line " + this.f.linedefined + " has more than " + n + " " + string);
        this.ls.lexerror(string2, 0);
    }

    LocVars getlocvar(int n) {
        short s = this.ls.dyd.actvar[this.firstlocal + n].idx;
        FuncState._assert(s < this.nlocvars);
        return this.f.locvars[s];
    }

    void removevars(int n) {
        this.ls.dyd.n_actvar -= this.nactvar - n;
        while (this.nactvar > n) {
            this.nactvar = (short)(this.nactvar - 1);
            this.getlocvar((int)((short)(this.nactvar - 1))).endpc = this.pc;
        }
    }

    int searchupvalue(LuaString luaString) {
        Upvaldesc[] upvaldescArray = this.f.upvalues;
        for (int i = 0; i < this.nups; ++i) {
            if (!upvaldescArray[i].name.eq_b(luaString)) continue;
            return i;
        }
        return 1;
    }

    int newupvalue(LuaString luaString, LexState.expdesc expdesc2) {
        this.checklimit(this.nups + 1, 255, "upvalues");
        if (this.f.upvalues == null || this.nups + 1 > this.f.upvalues.length) {
            this.f.upvalues = FuncState.realloc(this.f.upvalues, this.nups > 0 ? this.nups * 2 : 1);
        }
        this.f.upvalues[this.nups] = new Upvaldesc(luaString, expdesc2.k == 7, expdesc2.u.info);
        short s = this.nups;
        this.nups = (short)(s + 1);
        return s;
    }

    int searchvar(LuaString luaString) {
        for (int i = this.nactvar - 1; i >= 0; --i) {
            if (!luaString.eq_b(this.getlocvar((int)i).varname)) continue;
            return i;
        }
        return 1;
    }

    void markupval(int n) {
        BlockCnt blockCnt = this.bl;
        while (blockCnt.nactvar > n) {
            blockCnt = blockCnt.previous;
        }
        blockCnt.upval = true;
    }

    static int singlevaraux(FuncState funcState, LuaString luaString, LexState.expdesc expdesc2, int n) {
        if (funcState == null) {
            return 1;
        }
        int n2 = funcState.searchvar(luaString);
        if (n2 >= 0) {
            expdesc2.init(7, n2);
            if (n == 0) {
                funcState.markupval(n2);
            }
            return 0;
        }
        int n3 = funcState.searchupvalue(luaString);
        if (n3 < 0) {
            if (FuncState.singlevaraux(funcState.prev, luaString, expdesc2, 0) == 0) {
                return 1;
            }
            n3 = funcState.newupvalue(luaString, expdesc2);
        }
        expdesc2.init(8, n3);
        return 1;
    }

    void movegotosout(BlockCnt blockCnt) {
        int n = blockCnt.firstgoto;
        LexState.Labeldesc[] labeldescArray = this.ls.dyd.gt;
        while (n < this.ls.dyd.n_gt) {
            LexState.Labeldesc labeldesc = labeldescArray[n];
            if (labeldesc.nactvar > blockCnt.nactvar) {
                if (blockCnt.upval) {
                    this.patchclose(labeldesc.pc, blockCnt.nactvar);
                }
                labeldesc.nactvar = blockCnt.nactvar;
            }
            if (this.ls.findlabel(n)) continue;
            ++n;
        }
    }

    void enterblock(BlockCnt blockCnt, boolean bl) {
        blockCnt.isloop = bl;
        blockCnt.nactvar = this.nactvar;
        blockCnt.firstlabel = (short)this.ls.dyd.n_label;
        blockCnt.firstgoto = (short)this.ls.dyd.n_gt;
        blockCnt.upval = false;
        blockCnt.previous = this.bl;
        this.bl = blockCnt;
        FuncState._assert(this.freereg == this.nactvar);
    }

    void leaveblock() {
        BlockCnt blockCnt = this.bl;
        if (blockCnt.previous != null && blockCnt.upval) {
            int n = this.jump();
            this.patchclose(n, blockCnt.nactvar);
            this.patchtohere(n);
        }
        if (blockCnt.isloop) {
            this.ls.breaklabel();
        }
        this.bl = blockCnt.previous;
        this.removevars(blockCnt.nactvar);
        FuncState._assert(blockCnt.nactvar == this.nactvar);
        this.freereg = this.nactvar;
        this.ls.dyd.n_label = blockCnt.firstlabel;
        if (blockCnt.previous != null) {
            this.movegotosout(blockCnt);
        } else if (blockCnt.firstgoto < this.ls.dyd.n_gt) {
            this.ls.undefgoto(this.ls.dyd.gt[blockCnt.firstgoto]);
        }
    }

    void closelistfield(LexState.ConsControl consControl) {
        if (consControl.v.k == 0) {
            return;
        }
        this.exp2nextreg(consControl.v);
        consControl.v.k = 0;
        if (consControl.tostore == 50) {
            this.setlist(consControl.t.u.info, consControl.na, consControl.tostore);
            consControl.tostore = 0;
        }
    }

    boolean hasmultret(int n) {
        return n == 12 || n == 13;
    }

    void lastlistfield(LexState.ConsControl consControl) {
        if (consControl.tostore == 0) {
            return;
        }
        if (this.hasmultret(consControl.v.k)) {
            this.setmultret(consControl.v);
            this.setlist(consControl.t.u.info, consControl.na, -1);
            --consControl.na;
        } else {
            if (consControl.v.k != 0) {
                this.exp2nextreg(consControl.v);
            }
            this.setlist(consControl.t.u.info, consControl.na, consControl.tostore);
        }
    }

    void nil(int n, int n2) {
        int n3;
        int n4 = n + n2 - 1;
        if (this.pc > this.lasttarget && this.pc > 0 && FuncState.GET_OPCODE(n3 = this.f.code[this.pc - 1]) == 4) {
            int n5 = FuncState.GETARG_A(n3);
            int n6 = n5 + FuncState.GETARG_B(n3);
            if (n5 <= n && n <= n6 + 1 || n <= n5 && n5 <= n4 + 1) {
                if (n5 < n) {
                    n = n5;
                }
                if (n6 > n4) {
                    n4 = n6;
                }
                InstructionPtr instructionPtr = new InstructionPtr(this.f.code, this.pc - 1);
                FuncState.SETARG_A(instructionPtr, n);
                FuncState.SETARG_B(instructionPtr, n4 - n);
                return;
            }
        }
        this.codeABC(4, n, n2 - 1, 0);
    }

    int jump() {
        int n = this.jpc.i;
        this.jpc.i = -1;
        IntPtr intPtr = new IntPtr(this.codeAsBx(23, 0, -1));
        this.concat(intPtr, n);
        return intPtr.i;
    }

    void ret(int n, int n2) {
        this.codeABC(31, n, n2 + 1, 0);
    }

    int condjump(int n, int n2, int n3, int n4) {
        this.codeABC(n, n2, n3, n4);
        return this.jump();
    }

    void fixjump(int n, int n2) {
        InstructionPtr instructionPtr = new InstructionPtr(this.f.code, n);
        int n3 = n2 - (n + 1);
        FuncState._assert(n2 != -1);
        if (Math.abs(n3) > 131071) {
            this.ls.syntaxerror("control structure too long");
        }
        FuncState.SETARG_sBx(instructionPtr, n3);
    }

    int getlabel() {
        this.lasttarget = this.pc;
        return this.pc;
    }

    int getjump(int n) {
        int n2 = FuncState.GETARG_sBx(this.f.code[n]);
        if (n2 == -1) {
            return 1;
        }
        return n + 1 + n2;
    }

    InstructionPtr getjumpcontrol(int n) {
        InstructionPtr instructionPtr = new InstructionPtr(this.f.code, n);
        if (n >= 1 && FuncState.testTMode(FuncState.GET_OPCODE(instructionPtr.code[instructionPtr.idx - 1]))) {
            return new InstructionPtr(instructionPtr.code, instructionPtr.idx - 1);
        }
        return instructionPtr;
    }

    boolean need_value(int n) {
        while (n != -1) {
            int n2 = this.getjumpcontrol(n).get();
            if (FuncState.GET_OPCODE(n2) != 28) {
                return false;
            }
            n = this.getjump(n);
        }
        return true;
    }

    boolean patchtestreg(int n, int n2) {
        InstructionPtr instructionPtr = this.getjumpcontrol(n);
        if (FuncState.GET_OPCODE(instructionPtr.get()) != 28) {
            return true;
        }
        if (n2 != 255 && n2 != FuncState.GETARG_B(instructionPtr.get())) {
            FuncState.SETARG_A(instructionPtr, n2);
        } else {
            instructionPtr.set(FuncState.CREATE_ABC(27, FuncState.GETARG_B(instructionPtr.get()), 0, Lua.GETARG_C(instructionPtr.get())));
        }
        return false;
    }

    void removevalues(int n) {
        while (n != -1) {
            this.patchtestreg(n, 0);
            n = this.getjump(n);
        }
    }

    void patchlistaux(int n, int n2, int n3, int n4) {
        while (n != -1) {
            int n5 = this.getjump(n);
            if (this.patchtestreg(n, n3)) {
                this.fixjump(n, n2);
            } else {
                this.fixjump(n, n4);
            }
            n = n5;
        }
    }

    void dischargejpc() {
        this.patchlistaux(this.jpc.i, this.pc, 255, this.pc);
        this.jpc.i = -1;
    }

    void patchlist(int n, int n2) {
        if (n2 == this.pc) {
            this.patchtohere(n);
        } else {
            FuncState._assert(n2 < this.pc);
            this.patchlistaux(n, n2, 255, n2);
        }
    }

    void patchclose(int n, int n2) {
        ++n2;
        while (n != -1) {
            int n3 = this.getjump(n);
            FuncState._assert(FuncState.GET_OPCODE(this.f.code[n]) == 23 && (FuncState.GETARG_A(this.f.code[n]) == 0 || FuncState.GETARG_A(this.f.code[n]) >= n2));
            FuncState.SETARG_A(this.f.code, n, n2);
            n = n3;
        }
    }

    void patchtohere(int n) {
        this.getlabel();
        this.concat(this.jpc, n);
    }

    void concat(IntPtr intPtr, int n) {
        if (n == -1) {
            return;
        }
        if (intPtr.i == -1) {
            intPtr.i = n;
        } else {
            int n2;
            int n3 = intPtr.i;
            while ((n2 = this.getjump(n3)) != -1) {
                n3 = n2;
            }
            this.fixjump(n3, n);
        }
    }

    void checkstack(int n) {
        int n2 = this.freereg + n;
        if (n2 > this.f.maxstacksize) {
            if (n2 >= 250) {
                this.ls.syntaxerror("function or expression too complex");
            }
            this.f.maxstacksize = n2;
        }
    }

    void reserveregs(int n) {
        this.checkstack(n);
        this.freereg = (short)(this.freereg + n);
    }

    void freereg(int n) {
        if (!FuncState.ISK(n) && n >= this.nactvar) {
            this.freereg = (short)(this.freereg - 1);
            FuncState._assert(n == this.freereg);
        }
    }

    void freeexp(LexState.expdesc expdesc2) {
        if (expdesc2.k == 6) {
            this.freereg(expdesc2.u.info);
        }
    }

    int addk(LuaValue luaValue) {
        if (this.h == null) {
            this.h = new Hashtable();
        } else if (this.h.containsKey(luaValue)) {
            return (Integer)this.h.get(luaValue);
        }
        int n = this.nk;
        this.h.put(luaValue, n);
        Prototype prototype = this.f;
        if (prototype.k == null || this.nk + 1 >= prototype.k.length) {
            prototype.k = FuncState.realloc(prototype.k, this.nk * 2 + 1);
        }
        prototype.k[this.nk++] = luaValue;
        return n;
    }

    int stringK(LuaString luaString) {
        return this.addk(luaString);
    }

    int numberK(LuaValue luaValue) {
        int n;
        double d;
        if (luaValue instanceof LuaDouble && (d = luaValue.todouble()) == (double)(n = (int)d)) {
            luaValue = LuaInteger.valueOf(n);
        }
        return this.addk(luaValue);
    }

    int boolK(boolean bl) {
        return this.addk(bl ? LuaValue.TRUE : LuaValue.FALSE);
    }

    int nilK() {
        return this.addk(LuaValue.NIL);
    }

    void setreturns(LexState.expdesc expdesc2, int n) {
        if (expdesc2.k == 12) {
            FuncState.SETARG_C(this.getcodePtr(expdesc2), n + 1);
        } else if (expdesc2.k == 13) {
            FuncState.SETARG_B(this.getcodePtr(expdesc2), n + 1);
            FuncState.SETARG_A(this.getcodePtr(expdesc2), this.freereg);
            this.reserveregs(1);
        }
    }

    void setoneret(LexState.expdesc expdesc2) {
        if (expdesc2.k == 12) {
            expdesc2.k = 6;
            expdesc2.u.info = FuncState.GETARG_A(this.getcode(expdesc2));
        } else if (expdesc2.k == 13) {
            FuncState.SETARG_B(this.getcodePtr(expdesc2), 2);
            expdesc2.k = 11;
        }
    }

    void dischargevars(LexState.expdesc expdesc2) {
        switch (expdesc2.k) {
            case 7: {
                expdesc2.k = 6;
                break;
            }
            case 8: {
                expdesc2.u.info = this.codeABC(5, 0, expdesc2.u.info, 0);
                expdesc2.k = 11;
                break;
            }
            case 9: {
                int n = 6;
                this.freereg(expdesc2.u.ind_idx);
                if (expdesc2.u.ind_vt == 7) {
                    this.freereg(expdesc2.u.ind_t);
                    n = 7;
                }
                expdesc2.u.info = this.codeABC(n, 0, expdesc2.u.ind_t, expdesc2.u.ind_idx);
                expdesc2.k = 11;
                break;
            }
            case 12: 
            case 13: {
                this.setoneret(expdesc2);
                break;
            }
        }
    }

    int code_label(int n, int n2, int n3) {
        this.getlabel();
        return this.codeABC(3, n, n2, n3);
    }

    void discharge2reg(LexState.expdesc expdesc2, int n) {
        this.dischargevars(expdesc2);
        switch (expdesc2.k) {
            case 1: {
                this.nil(n, 1);
                break;
            }
            case 2: 
            case 3: {
                this.codeABC(3, n, expdesc2.k == 2 ? 1 : 0, 0);
                break;
            }
            case 4: {
                this.codeK(n, expdesc2.u.info);
                break;
            }
            case 5: {
                this.codeK(n, this.numberK(expdesc2.u.nval()));
                break;
            }
            case 11: {
                InstructionPtr instructionPtr = this.getcodePtr(expdesc2);
                FuncState.SETARG_A(instructionPtr, n);
                break;
            }
            case 6: {
                if (n == expdesc2.u.info) break;
                this.codeABC(0, n, expdesc2.u.info, 0);
                break;
            }
            default: {
                FuncState._assert(expdesc2.k == 0 || expdesc2.k == 10);
                return;
            }
        }
        expdesc2.u.info = n;
        expdesc2.k = 6;
    }

    void discharge2anyreg(LexState.expdesc expdesc2) {
        if (expdesc2.k != 6) {
            this.reserveregs(1);
            this.discharge2reg(expdesc2, this.freereg - 1);
        }
    }

    void exp2reg(LexState.expdesc expdesc2, int n) {
        this.discharge2reg(expdesc2, n);
        if (expdesc2.k == 10) {
            this.concat(expdesc2.t, expdesc2.u.info);
        }
        if (expdesc2.hasjumps()) {
            int n2 = -1;
            int n3 = -1;
            if (this.need_value(expdesc2.t.i) || this.need_value(expdesc2.f.i)) {
                int n4 = expdesc2.k == 10 ? -1 : this.jump();
                n2 = this.code_label(n, 0, 1);
                n3 = this.code_label(n, 1, 0);
                this.patchtohere(n4);
            }
            int n5 = this.getlabel();
            this.patchlistaux(expdesc2.f.i, n5, n, n2);
            this.patchlistaux(expdesc2.t.i, n5, n, n3);
        }
        expdesc2.t.i = -1;
        expdesc2.f.i = -1;
        expdesc2.u.info = n;
        expdesc2.k = 6;
    }

    void exp2nextreg(LexState.expdesc expdesc2) {
        this.dischargevars(expdesc2);
        this.freeexp(expdesc2);
        this.reserveregs(1);
        this.exp2reg(expdesc2, this.freereg - 1);
    }

    int exp2anyreg(LexState.expdesc expdesc2) {
        this.dischargevars(expdesc2);
        if (expdesc2.k == 6) {
            if (!expdesc2.hasjumps()) {
                return expdesc2.u.info;
            }
            if (expdesc2.u.info >= this.nactvar) {
                this.exp2reg(expdesc2, expdesc2.u.info);
                return expdesc2.u.info;
            }
        }
        this.exp2nextreg(expdesc2);
        return expdesc2.u.info;
    }

    void exp2anyregup(LexState.expdesc expdesc2) {
        if (expdesc2.k != 8 || expdesc2.hasjumps()) {
            this.exp2anyreg(expdesc2);
        }
    }

    void exp2val(LexState.expdesc expdesc2) {
        if (expdesc2.hasjumps()) {
            this.exp2anyreg(expdesc2);
        } else {
            this.dischargevars(expdesc2);
        }
    }

    int exp2RK(LexState.expdesc expdesc2) {
        this.exp2val(expdesc2);
        switch (expdesc2.k) {
            case 1: 
            case 2: 
            case 3: {
                if (this.nk > 255) break;
                expdesc2.u.info = expdesc2.k == 1 ? this.nilK() : this.boolK(expdesc2.k == 2);
                expdesc2.k = 4;
                return FuncState.RKASK(expdesc2.u.info);
            }
            case 5: {
                expdesc2.u.info = this.numberK(expdesc2.u.nval());
                expdesc2.k = 4;
            }
            case 4: {
                if (expdesc2.u.info > 255) break;
                return FuncState.RKASK(expdesc2.u.info);
            }
        }
        return this.exp2anyreg(expdesc2);
    }

    void storevar(LexState.expdesc expdesc2, LexState.expdesc expdesc3) {
        switch (expdesc2.k) {
            case 7: {
                this.freeexp(expdesc3);
                this.exp2reg(expdesc3, expdesc2.u.info);
                return;
            }
            case 8: {
                int n = this.exp2anyreg(expdesc3);
                this.codeABC(9, n, expdesc2.u.info, 0);
                break;
            }
            case 9: {
                int n = expdesc2.u.ind_vt == 7 ? 10 : 8;
                int n2 = this.exp2RK(expdesc3);
                this.codeABC(n, expdesc2.u.ind_t, expdesc2.u.ind_idx, n2);
                break;
            }
            default: {
                FuncState._assert(false);
            }
        }
        this.freeexp(expdesc3);
    }

    void self(LexState.expdesc expdesc2, LexState.expdesc expdesc3) {
        this.exp2anyreg(expdesc2);
        this.freeexp(expdesc2);
        short s = this.freereg;
        this.reserveregs(2);
        this.codeABC(12, s, expdesc2.u.info, this.exp2RK(expdesc3));
        this.freeexp(expdesc3);
        expdesc2.u.info = s;
        expdesc2.k = 6;
    }

    void invertjump(LexState.expdesc expdesc2) {
        InstructionPtr instructionPtr = this.getjumpcontrol(expdesc2.u.info);
        FuncState._assert(FuncState.testTMode(FuncState.GET_OPCODE(instructionPtr.get())) && FuncState.GET_OPCODE(instructionPtr.get()) != 28 && Lua.GET_OPCODE(instructionPtr.get()) != 27);
        int n = FuncState.GETARG_A(instructionPtr.get());
        int n2 = n != 0 ? 0 : 1;
        FuncState.SETARG_A(instructionPtr, n2);
    }

    int jumponcond(LexState.expdesc expdesc2, int n) {
        int n2;
        if (expdesc2.k == 11 && FuncState.GET_OPCODE(n2 = this.getcode(expdesc2)) == 20) {
            --this.pc;
            return this.condjump(27, FuncState.GETARG_B(n2), 0, n != 0 ? 0 : 1);
        }
        this.discharge2anyreg(expdesc2);
        this.freeexp(expdesc2);
        return this.condjump(28, 255, expdesc2.u.info, n);
    }

    void goiftrue(LexState.expdesc expdesc2) {
        this.dischargevars(expdesc2);
        this.concat(expdesc2.f, switch (expdesc2.k) {
            case 10 -> {
                this.invertjump(expdesc2);
                yield expdesc2.u.info;
            }
            case 2, 4, 5 -> -1;
            default -> this.jumponcond(expdesc2, 0);
        });
        this.patchtohere(expdesc2.t.i);
        expdesc2.t.i = -1;
    }

    void goiffalse(LexState.expdesc expdesc2) {
        this.dischargevars(expdesc2);
        this.concat(expdesc2.t, switch (expdesc2.k) {
            case 10 -> expdesc2.u.info;
            case 1, 3 -> -1;
            default -> this.jumponcond(expdesc2, 1);
        });
        this.patchtohere(expdesc2.f.i);
        expdesc2.f.i = -1;
    }

    void codenot(LexState.expdesc expdesc2) {
        this.dischargevars(expdesc2);
        switch (expdesc2.k) {
            case 1: 
            case 3: {
                expdesc2.k = 2;
                break;
            }
            case 2: 
            case 4: 
            case 5: {
                expdesc2.k = 3;
                break;
            }
            case 10: {
                this.invertjump(expdesc2);
                break;
            }
            case 6: 
            case 11: {
                this.discharge2anyreg(expdesc2);
                this.freeexp(expdesc2);
                expdesc2.u.info = this.codeABC(20, 0, expdesc2.u.info, 0);
                expdesc2.k = 11;
                break;
            }
            default: {
                FuncState._assert(false);
            }
        }
        int n = expdesc2.f.i;
        expdesc2.f.i = expdesc2.t.i;
        expdesc2.t.i = n;
        this.removevalues(expdesc2.f.i);
        this.removevalues(expdesc2.t.i);
    }

    static boolean vkisinreg(int n) {
        return n == 6 || n == 7;
    }

    void indexed(LexState.expdesc expdesc2, LexState.expdesc expdesc3) {
        expdesc2.u.ind_t = (short)expdesc2.u.info;
        expdesc2.u.ind_idx = (short)this.exp2RK(expdesc3);
        LuaC._assert(expdesc2.k == 8 || FuncState.vkisinreg(expdesc2.k));
        expdesc2.u.ind_vt = (short)(expdesc2.k == 8 ? 8 : 7);
        expdesc2.k = 9;
    }

    boolean constfolding(int n, LexState.expdesc expdesc2, LexState.expdesc expdesc3) {
        LuaValue luaValue;
        if (!expdesc2.isnumeral() || !expdesc3.isnumeral()) {
            return true;
        }
        if ((n == 16 || n == 17) && expdesc3.u.nval().eq_b(LuaValue.ZERO)) {
            return true;
        }
        LuaValue luaValue2 = expdesc2.u.nval();
        LuaValue luaValue3 = expdesc3.u.nval();
        switch (n) {
            case 13: {
                luaValue = luaValue2.add(luaValue3);
                break;
            }
            case 14: {
                luaValue = luaValue2.sub(luaValue3);
                break;
            }
            case 15: {
                luaValue = luaValue2.mul(luaValue3);
                break;
            }
            case 16: {
                luaValue = luaValue2.div(luaValue3);
                break;
            }
            case 17: {
                luaValue = luaValue2.mod(luaValue3);
                break;
            }
            case 18: {
                luaValue = luaValue2.pow(luaValue3);
                break;
            }
            case 19: {
                luaValue = luaValue2.neg();
                break;
            }
            case 21: {
                return true;
            }
            default: {
                FuncState._assert(false);
                luaValue = null;
            }
        }
        if (Double.isNaN(luaValue.todouble())) {
            return true;
        }
        expdesc2.u.setNval(luaValue);
        return false;
    }

    void codearith(int n, LexState.expdesc expdesc2, LexState.expdesc expdesc3, int n2) {
        if (!this.constfolding(n, expdesc2, expdesc3)) {
            int n3 = n != 19 && n != 21 ? this.exp2RK(expdesc3) : 0;
            int n4 = this.exp2RK(expdesc2);
            if (n4 > n3) {
                this.freeexp(expdesc2);
                this.freeexp(expdesc3);
            } else {
                this.freeexp(expdesc3);
                this.freeexp(expdesc2);
            }
            expdesc2.u.info = this.codeABC(n, 0, n4, n3);
            expdesc2.k = 11;
            this.fixline(n2);
        }
    }

    void codecomp(int n, int n2, LexState.expdesc expdesc2, LexState.expdesc expdesc3) {
        int n3 = this.exp2RK(expdesc2);
        int n4 = this.exp2RK(expdesc3);
        this.freeexp(expdesc3);
        this.freeexp(expdesc2);
        if (n2 == 0 && n != 24) {
            int n5 = n3;
            n3 = n4;
            n4 = n5;
            n2 = 1;
        }
        expdesc2.u.info = this.condjump(n, n2, n3, n4);
        expdesc2.k = 10;
    }

    void prefix(int n, LexState.expdesc expdesc2, int n2) {
        LexState.expdesc expdesc3 = new LexState.expdesc();
        expdesc3.init(5, 0);
        switch (n) {
            case 0: {
                if (expdesc2.isnumeral()) {
                    expdesc2.u.setNval(expdesc2.u.nval().neg());
                    break;
                }
                this.exp2anyreg(expdesc2);
                this.codearith(19, expdesc2, expdesc3, n2);
                break;
            }
            case 1: {
                this.codenot(expdesc2);
                break;
            }
            case 2: {
                this.exp2anyreg(expdesc2);
                this.codearith(21, expdesc2, expdesc3, n2);
                break;
            }
            default: {
                FuncState._assert(false);
            }
        }
    }

    void infix(int n, LexState.expdesc expdesc2) {
        switch (n) {
            case 13: {
                this.goiftrue(expdesc2);
                break;
            }
            case 14: {
                this.goiffalse(expdesc2);
                break;
            }
            case 6: {
                this.exp2nextreg(expdesc2);
                break;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                if (expdesc2.isnumeral()) break;
                this.exp2RK(expdesc2);
                break;
            }
            default: {
                this.exp2RK(expdesc2);
            }
        }
    }

    void posfix(int n, LexState.expdesc expdesc2, LexState.expdesc expdesc3, int n2) {
        switch (n) {
            case 13: {
                FuncState._assert(expdesc2.t.i == -1);
                this.dischargevars(expdesc3);
                this.concat(expdesc3.f, expdesc2.f.i);
                expdesc2.setvalue(expdesc3);
                break;
            }
            case 14: {
                FuncState._assert(expdesc2.f.i == -1);
                this.dischargevars(expdesc3);
                this.concat(expdesc3.t, expdesc2.t.i);
                expdesc2.setvalue(expdesc3);
                break;
            }
            case 6: {
                this.exp2val(expdesc3);
                if (expdesc3.k == 11 && FuncState.GET_OPCODE(this.getcode(expdesc3)) == 22) {
                    FuncState._assert(expdesc2.u.info == FuncState.GETARG_B(this.getcode(expdesc3)) - 1);
                    this.freeexp(expdesc2);
                    FuncState.SETARG_B(this.getcodePtr(expdesc3), expdesc2.u.info);
                    expdesc2.k = 11;
                    expdesc2.u.info = expdesc3.u.info;
                    break;
                }
                this.exp2nextreg(expdesc3);
                this.codearith(22, expdesc2, expdesc3, n2);
                break;
            }
            case 0: {
                this.codearith(13, expdesc2, expdesc3, n2);
                break;
            }
            case 1: {
                this.codearith(14, expdesc2, expdesc3, n2);
                break;
            }
            case 2: {
                this.codearith(15, expdesc2, expdesc3, n2);
                break;
            }
            case 3: {
                this.codearith(16, expdesc2, expdesc3, n2);
                break;
            }
            case 4: {
                this.codearith(17, expdesc2, expdesc3, n2);
                break;
            }
            case 5: {
                this.codearith(18, expdesc2, expdesc3, n2);
                break;
            }
            case 8: {
                this.codecomp(24, 1, expdesc2, expdesc3);
                break;
            }
            case 7: {
                this.codecomp(24, 0, expdesc2, expdesc3);
                break;
            }
            case 9: {
                this.codecomp(25, 1, expdesc2, expdesc3);
                break;
            }
            case 10: {
                this.codecomp(26, 1, expdesc2, expdesc3);
                break;
            }
            case 11: {
                this.codecomp(25, 0, expdesc2, expdesc3);
                break;
            }
            case 12: {
                this.codecomp(26, 0, expdesc2, expdesc3);
                break;
            }
            default: {
                FuncState._assert(false);
            }
        }
    }

    void fixline(int n) {
        this.f.lineinfo[this.pc - 1] = n;
    }

    int code(int n, int n2) {
        Prototype prototype = this.f;
        this.dischargejpc();
        if (prototype.code == null || this.pc + 1 > prototype.code.length) {
            prototype.code = LuaC.realloc(prototype.code, this.pc * 2 + 1);
        }
        prototype.code[this.pc] = n;
        if (prototype.lineinfo == null || this.pc + 1 > prototype.lineinfo.length) {
            prototype.lineinfo = LuaC.realloc(prototype.lineinfo, this.pc * 2 + 1);
        }
        prototype.lineinfo[this.pc] = n2;
        return this.pc++;
    }

    int codeABC(int n, int n2, int n3, int n4) {
        FuncState._assert(FuncState.getOpMode(n) == 0);
        FuncState._assert(FuncState.getBMode(n) != 0 || n3 == 0);
        FuncState._assert(FuncState.getCMode(n) != 0 || n4 == 0);
        return this.code(FuncState.CREATE_ABC(n, n2, n3, n4), this.ls.lastline);
    }

    int codeABx(int n, int n2, int n3) {
        FuncState._assert(FuncState.getOpMode(n) == 1 || FuncState.getOpMode(n) == 2);
        FuncState._assert(FuncState.getCMode(n) == 0);
        FuncState._assert(n3 >= 0 && n3 <= 262143);
        return this.code(FuncState.CREATE_ABx(n, n2, n3), this.ls.lastline);
    }

    int codeextraarg(int n) {
        FuncState._assert(n <= 0x3FFFFFF);
        return this.code(FuncState.CREATE_Ax(39, n), this.ls.lastline);
    }

    int codeK(int n, int n2) {
        if (n2 <= 262143) {
            return this.codeABx(1, n, n2);
        }
        int n3 = this.codeABx(2, n, 0);
        this.codeextraarg(n2);
        return n3;
    }

    void setlist(int n, int n2, int n3) {
        int n4 = (n2 - 1) / 50 + 1;
        int n5 = n3 == -1 ? 0 : n3;
        FuncState._assert(n3 != 0);
        if (n4 <= 511) {
            this.codeABC(36, n, n5, n4);
        } else {
            this.codeABC(36, n, n5, 0);
            this.code(n4, this.ls.lastline);
        }
        this.freereg = (short)(n + 1);
    }

    static class BlockCnt {
        BlockCnt previous;
        short firstlabel;
        short firstgoto;
        short nactvar;
        boolean upval;
        boolean isloop;

        BlockCnt() {
        }
    }
}

