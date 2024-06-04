package org.spongycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.DirectoryString;






















public class ProfessionInfo
  extends ASN1Object
{
  public static final ASN1ObjectIdentifier Rechtsanwltin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".1");
  




  public static final ASN1ObjectIdentifier Rechtsanwalt = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".2");
  




  public static final ASN1ObjectIdentifier Rechtsbeistand = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".3");
  




  public static final ASN1ObjectIdentifier Steuerberaterin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".4");
  




  public static final ASN1ObjectIdentifier Steuerberater = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".5");
  




  public static final ASN1ObjectIdentifier Steuerbevollmchtigte = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".6");
  




  public static final ASN1ObjectIdentifier Steuerbevollmchtigter = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".7");
  




  public static final ASN1ObjectIdentifier Notarin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".8");
  




  public static final ASN1ObjectIdentifier Notar = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".9");
  




  public static final ASN1ObjectIdentifier Notarvertreterin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".10");
  




  public static final ASN1ObjectIdentifier Notarvertreter = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".11");
  




  public static final ASN1ObjectIdentifier Notariatsverwalterin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".12");
  




  public static final ASN1ObjectIdentifier Notariatsverwalter = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".13");
  




  public static final ASN1ObjectIdentifier Wirtschaftsprferin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".14");
  




  public static final ASN1ObjectIdentifier Wirtschaftsprfer = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".15");
  




  public static final ASN1ObjectIdentifier VereidigteBuchprferin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".16");
  




  public static final ASN1ObjectIdentifier VereidigterBuchprfer = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".17");
  




  public static final ASN1ObjectIdentifier Patentanwltin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".18");
  




  public static final ASN1ObjectIdentifier Patentanwalt = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".19");
  
  private NamingAuthority namingAuthority;
  
  private ASN1Sequence professionItems;
  
  private ASN1Sequence professionOIDs;
  
  private String registrationNumber;
  
  private ASN1OctetString addProfessionInfo;
  

  public static ProfessionInfo getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof ProfessionInfo)))
    {
      return (ProfessionInfo)obj;
    }
    
    if ((obj instanceof ASN1Sequence))
    {
      return new ProfessionInfo((ASN1Sequence)obj);
    }
    

    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  















  private ProfessionInfo(ASN1Sequence seq)
  {
    if (seq.size() > 5)
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
    Enumeration e = seq.getObjects();
    
    ASN1Encodable o = (ASN1Encodable)e.nextElement();
    
    if ((o instanceof ASN1TaggedObject))
    {
      if (((ASN1TaggedObject)o).getTagNo() != 0)
      {

        throw new IllegalArgumentException("Bad tag number: " + ((ASN1TaggedObject)o).getTagNo());
      }
      namingAuthority = NamingAuthority.getInstance((ASN1TaggedObject)o, true);
      o = (ASN1Encodable)e.nextElement();
    }
    
    professionItems = ASN1Sequence.getInstance(o);
    
    if (e.hasMoreElements())
    {
      o = (ASN1Encodable)e.nextElement();
      if ((o instanceof ASN1Sequence))
      {
        professionOIDs = ASN1Sequence.getInstance(o);
      }
      else if ((o instanceof DERPrintableString))
      {
        registrationNumber = DERPrintableString.getInstance(o).getString();
      }
      else if ((o instanceof ASN1OctetString))
      {
        addProfessionInfo = ASN1OctetString.getInstance(o);

      }
      else
      {
        throw new IllegalArgumentException("Bad object encountered: " + o.getClass());
      }
    }
    if (e.hasMoreElements())
    {
      o = (ASN1Encodable)e.nextElement();
      if ((o instanceof DERPrintableString))
      {
        registrationNumber = DERPrintableString.getInstance(o).getString();
      }
      else if ((o instanceof DEROctetString))
      {
        addProfessionInfo = ((DEROctetString)o);

      }
      else
      {
        throw new IllegalArgumentException("Bad object encountered: " + o.getClass());
      }
    }
    if (e.hasMoreElements())
    {
      o = (ASN1Encodable)e.nextElement();
      if ((o instanceof DEROctetString))
      {
        addProfessionInfo = ((DEROctetString)o);

      }
      else
      {
        throw new IllegalArgumentException("Bad object encountered: " + o.getClass());
      }
    }
  }
  
















  public ProfessionInfo(NamingAuthority namingAuthority, DirectoryString[] professionItems, ASN1ObjectIdentifier[] professionOIDs, String registrationNumber, ASN1OctetString addProfessionInfo)
  {
    this.namingAuthority = namingAuthority;
    ASN1EncodableVector v = new ASN1EncodableVector();
    for (int i = 0; i != professionItems.length; i++)
    {
      v.add(professionItems[i]);
    }
    this.professionItems = new DERSequence(v);
    if (professionOIDs != null)
    {
      v = new ASN1EncodableVector();
      for (int i = 0; i != professionOIDs.length; i++)
      {
        v.add(professionOIDs[i]);
      }
      this.professionOIDs = new DERSequence(v);
    }
    this.registrationNumber = registrationNumber;
    this.addProfessionInfo = addProfessionInfo;
  }
  

















  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector vec = new ASN1EncodableVector();
    if (namingAuthority != null)
    {
      vec.add(new DERTaggedObject(true, 0, namingAuthority));
    }
    vec.add(professionItems);
    if (professionOIDs != null)
    {
      vec.add(professionOIDs);
    }
    if (registrationNumber != null)
    {
      vec.add(new DERPrintableString(registrationNumber, true));
    }
    if (addProfessionInfo != null)
    {
      vec.add(addProfessionInfo);
    }
    return new DERSequence(vec);
  }
  



  public ASN1OctetString getAddProfessionInfo()
  {
    return addProfessionInfo;
  }
  



  public NamingAuthority getNamingAuthority()
  {
    return namingAuthority;
  }
  



  public DirectoryString[] getProfessionItems()
  {
    DirectoryString[] items = new DirectoryString[professionItems.size()];
    int count = 0;
    for (Enumeration e = professionItems.getObjects(); e.hasMoreElements();)
    {
      items[(count++)] = DirectoryString.getInstance(e.nextElement());
    }
    return items;
  }
  



  public ASN1ObjectIdentifier[] getProfessionOIDs()
  {
    if (professionOIDs == null)
    {
      return new ASN1ObjectIdentifier[0];
    }
    ASN1ObjectIdentifier[] oids = new ASN1ObjectIdentifier[professionOIDs.size()];
    int count = 0;
    for (Enumeration e = professionOIDs.getObjects(); e.hasMoreElements();)
    {
      oids[(count++)] = ASN1ObjectIdentifier.getInstance(e.nextElement());
    }
    return oids;
  }
  



  public String getRegistrationNumber()
  {
    return registrationNumber;
  }
}
