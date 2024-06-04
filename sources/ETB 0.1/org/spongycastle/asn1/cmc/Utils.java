package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Sequence;

class Utils
{
  Utils() {}
  
  static BodyPartID[] toBodyPartIDArray(ASN1Sequence bodyPartIDs) {
    BodyPartID[] ids = new BodyPartID[bodyPartIDs.size()];
    
    for (int i = 0; i != bodyPartIDs.size(); i++)
    {
      ids[i] = BodyPartID.getInstance(bodyPartIDs.getObjectAt(i));
    }
    
    return ids;
  }
  
  static BodyPartID[] clone(BodyPartID[] ids)
  {
    BodyPartID[] tmp = new BodyPartID[ids.length];
    
    System.arraycopy(ids, 0, tmp, 0, ids.length);
    
    return tmp;
  }
  
  static org.spongycastle.asn1.x509.Extension[] clone(org.spongycastle.asn1.x509.Extension[] ids)
  {
    org.spongycastle.asn1.x509.Extension[] tmp = new org.spongycastle.asn1.x509.Extension[ids.length];
    
    System.arraycopy(ids, 0, tmp, 0, ids.length);
    
    return tmp;
  }
}
