package wwe;

import javax.crypto.*;
import net.minecraft.util.*;
import java.security.*;

public class bAHg extends xthd.wwe/vgeS
{
    public byte[] rKHM;
    public byte[] pTER;
    
    public bAHg(final SecretKey secretKey, final PublicKey publicKey, final byte[] array) {
        super(xglv.nllZ.ordinal());
        this.rKHM = CryptManager.encryptData((Key)publicKey, (byte[])secretKey.getEncoded());
        this.pTER = CryptManager.encryptData((Key)publicKey, array);
    }
}
