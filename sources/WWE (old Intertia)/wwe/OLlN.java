package wwe;

import javax.crypto.*;
import net.minecraft.util.*;
import java.security.*;

public class OLlN extends EMRf
{
    public byte[] nckt;
    public byte[] RlQa;
    
    public OLlN(final SecretKey secretKey, final PublicKey publicKey, final byte[] array) {
        super(xglv.nllZ.ordinal());
        this.nckt = CryptManager.encryptData((Key)publicKey, (byte[])secretKey.getEncoded());
        this.RlQa = CryptManager.encryptData((Key)publicKey, array);
    }
}
