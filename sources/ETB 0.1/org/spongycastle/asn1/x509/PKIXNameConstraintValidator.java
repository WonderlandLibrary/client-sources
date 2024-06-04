package org.spongycastle.asn1.x509;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;
import org.spongycastle.util.Strings;

public class PKIXNameConstraintValidator implements NameConstraintValidator
{
  private Set excludedSubtreesDN = new HashSet();
  
  private Set excludedSubtreesDNS = new HashSet();
  
  private Set excludedSubtreesEmail = new HashSet();
  
  private Set excludedSubtreesURI = new HashSet();
  
  private Set excludedSubtreesIP = new HashSet();
  

  private Set permittedSubtreesDN;
  

  private Set permittedSubtreesDNS;
  

  private Set permittedSubtreesEmail;
  

  private Set permittedSubtreesURI;
  

  private Set permittedSubtreesIP;
  


  public PKIXNameConstraintValidator() {}
  

  public void checkPermitted(GeneralName name)
    throws NameConstraintValidatorException
  {
    switch (name.getTagNo())
    {
    case 1: 
      checkPermittedEmail(permittedSubtreesEmail, 
        extractNameAsString(name));
      break;
    case 2: 
      checkPermittedDNS(permittedSubtreesDNS, DERIA5String.getInstance(name
        .getName()).getString());
      break;
    case 4: 
      checkPermittedDN(X500Name.getInstance(name.getName()));
      break;
    case 6: 
      checkPermittedURI(permittedSubtreesURI, DERIA5String.getInstance(name
        .getName()).getString());
      break;
    case 7: 
      byte[] ip = ASN1OctetString.getInstance(name.getName()).getOctets();
      
      checkPermittedIP(permittedSubtreesIP, ip);
    }
    
  }
  






  public void checkExcluded(GeneralName name)
    throws NameConstraintValidatorException
  {
    switch (name.getTagNo())
    {
    case 1: 
      checkExcludedEmail(excludedSubtreesEmail, extractNameAsString(name));
      break;
    case 2: 
      checkExcludedDNS(excludedSubtreesDNS, DERIA5String.getInstance(name
        .getName()).getString());
      break;
    case 4: 
      checkExcludedDN(X500Name.getInstance(name.getName()));
      break;
    case 6: 
      checkExcludedURI(excludedSubtreesURI, DERIA5String.getInstance(name
        .getName()).getString());
      break;
    case 7: 
      byte[] ip = ASN1OctetString.getInstance(name.getName()).getOctets();
      
      checkExcludedIP(excludedSubtreesIP, ip);
    }
  }
  
  public void intersectPermittedSubtree(GeneralSubtree permitted)
  {
    intersectPermittedSubtree(new GeneralSubtree[] { permitted });
  }
  






  public void intersectPermittedSubtree(GeneralSubtree[] permitted)
  {
    Map subtreesMap = new HashMap();
    

    for (int i = 0; i != permitted.length; i++)
    {
      GeneralSubtree subtree = permitted[i];
      Integer tagNo = Integers.valueOf(subtree.getBase().getTagNo());
      if (subtreesMap.get(tagNo) == null)
      {
        subtreesMap.put(tagNo, new HashSet());
      }
      ((Set)subtreesMap.get(tagNo)).add(subtree);
    }
    
    for (Iterator it = subtreesMap.entrySet().iterator(); it.hasNext();)
    {
      Map.Entry entry = (Map.Entry)it.next();
      

      switch (((Integer)entry.getKey()).intValue())
      {
      case 1: 
        permittedSubtreesEmail = intersectEmail(permittedSubtreesEmail, 
          (Set)entry.getValue());
        break;
      case 2: 
        permittedSubtreesDNS = intersectDNS(permittedSubtreesDNS, 
          (Set)entry.getValue());
        break;
      case 4: 
        permittedSubtreesDN = intersectDN(permittedSubtreesDN, 
          (Set)entry.getValue());
        break;
      case 6: 
        permittedSubtreesURI = intersectURI(permittedSubtreesURI, 
          (Set)entry.getValue());
        break;
      case 7: 
        permittedSubtreesIP = intersectIP(permittedSubtreesIP, 
          (Set)entry.getValue());
      }
    }
  }
  
  public void intersectEmptyPermittedSubtree(int nameType)
  {
    switch (nameType)
    {
    case 1: 
      permittedSubtreesEmail = new HashSet();
      break;
    case 2: 
      permittedSubtreesDNS = new HashSet();
      break;
    case 4: 
      permittedSubtreesDN = new HashSet();
      break;
    case 6: 
      permittedSubtreesURI = new HashSet();
      break;
    case 7: 
      permittedSubtreesIP = new HashSet();
    }
    
  }
  




  public void addExcludedSubtree(GeneralSubtree subtree)
  {
    GeneralName base = subtree.getBase();
    
    switch (base.getTagNo())
    {
    case 1: 
      excludedSubtreesEmail = unionEmail(excludedSubtreesEmail, 
        extractNameAsString(base));
      break;
    case 2: 
      excludedSubtreesDNS = unionDNS(excludedSubtreesDNS, 
        extractNameAsString(base));
      break;
    case 4: 
      excludedSubtreesDN = unionDN(excludedSubtreesDN, 
        (ASN1Sequence)base.getName().toASN1Primitive());
      break;
    case 6: 
      excludedSubtreesURI = unionURI(excludedSubtreesURI, 
        extractNameAsString(base));
      break;
    case 7: 
      excludedSubtreesIP = unionIP(excludedSubtreesIP, 
        ASN1OctetString.getInstance(base.getName()).getOctets());
    }
    
  }
  
  public int hashCode()
  {
    return 
    







      hashCollection(excludedSubtreesDN) + hashCollection(excludedSubtreesDNS) + hashCollection(excludedSubtreesEmail) + hashCollection(excludedSubtreesIP) + hashCollection(excludedSubtreesURI) + hashCollection(permittedSubtreesDN) + hashCollection(permittedSubtreesDNS) + hashCollection(permittedSubtreesEmail) + hashCollection(permittedSubtreesIP) + hashCollection(permittedSubtreesURI);
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof PKIXNameConstraintValidator))
    {
      return false;
    }
    PKIXNameConstraintValidator constraintValidator = (PKIXNameConstraintValidator)o;
    return (collectionsAreEqual(excludedSubtreesDN, excludedSubtreesDN)) && 
      (collectionsAreEqual(excludedSubtreesDNS, excludedSubtreesDNS)) && 
      (collectionsAreEqual(excludedSubtreesEmail, excludedSubtreesEmail)) && 
      (collectionsAreEqual(excludedSubtreesIP, excludedSubtreesIP)) && 
      (collectionsAreEqual(excludedSubtreesURI, excludedSubtreesURI)) && 
      (collectionsAreEqual(permittedSubtreesDN, permittedSubtreesDN)) && 
      (collectionsAreEqual(permittedSubtreesDNS, permittedSubtreesDNS)) && 
      (collectionsAreEqual(permittedSubtreesEmail, permittedSubtreesEmail)) && 
      (collectionsAreEqual(permittedSubtreesIP, permittedSubtreesIP)) && 
      (collectionsAreEqual(permittedSubtreesURI, permittedSubtreesURI));
  }
  
  public String toString()
  {
    String temp = "";
    temp = temp + "permitted:\n";
    if (permittedSubtreesDN != null)
    {
      temp = temp + "DN:\n";
      temp = temp + permittedSubtreesDN.toString() + "\n";
    }
    if (permittedSubtreesDNS != null)
    {
      temp = temp + "DNS:\n";
      temp = temp + permittedSubtreesDNS.toString() + "\n";
    }
    if (permittedSubtreesEmail != null)
    {
      temp = temp + "Email:\n";
      temp = temp + permittedSubtreesEmail.toString() + "\n";
    }
    if (permittedSubtreesURI != null)
    {
      temp = temp + "URI:\n";
      temp = temp + permittedSubtreesURI.toString() + "\n";
    }
    if (permittedSubtreesIP != null)
    {
      temp = temp + "IP:\n";
      temp = temp + stringifyIPCollection(permittedSubtreesIP) + "\n";
    }
    temp = temp + "excluded:\n";
    if (!excludedSubtreesDN.isEmpty())
    {
      temp = temp + "DN:\n";
      temp = temp + excludedSubtreesDN.toString() + "\n";
    }
    if (!excludedSubtreesDNS.isEmpty())
    {
      temp = temp + "DNS:\n";
      temp = temp + excludedSubtreesDNS.toString() + "\n";
    }
    if (!excludedSubtreesEmail.isEmpty())
    {
      temp = temp + "Email:\n";
      temp = temp + excludedSubtreesEmail.toString() + "\n";
    }
    if (!excludedSubtreesURI.isEmpty())
    {
      temp = temp + "URI:\n";
      temp = temp + excludedSubtreesURI.toString() + "\n";
    }
    if (!excludedSubtreesIP.isEmpty())
    {
      temp = temp + "IP:\n";
      temp = temp + stringifyIPCollection(excludedSubtreesIP) + "\n";
    }
    return temp;
  }
  
  private void checkPermittedDN(X500Name dns)
    throws NameConstraintValidatorException
  {
    checkPermittedDN(permittedSubtreesDN, ASN1Sequence.getInstance(dns.toASN1Primitive()));
  }
  
  private void checkExcludedDN(X500Name dns)
    throws NameConstraintValidatorException
  {
    checkExcludedDN(excludedSubtreesDN, ASN1Sequence.getInstance(dns));
  }
  


  private static boolean withinDNSubtree(ASN1Sequence dns, ASN1Sequence subtree)
  {
    if (subtree.size() < 1)
    {
      return false;
    }
    
    if (subtree.size() > dns.size())
    {
      return false;
    }
    
    for (int j = subtree.size() - 1; j >= 0; j--)
    {
      if (!subtree.getObjectAt(j).equals(dns.getObjectAt(j)))
      {
        return false;
      }
    }
    
    return true;
  }
  
  private void checkPermittedDN(Set permitted, ASN1Sequence dns)
    throws NameConstraintValidatorException
  {
    if (permitted == null)
    {
      return;
    }
    
    if ((permitted.isEmpty()) && (dns.size() == 0))
    {
      return;
    }
    Iterator it = permitted.iterator();
    
    while (it.hasNext())
    {
      ASN1Sequence subtree = (ASN1Sequence)it.next();
      
      if (withinDNSubtree(dns, subtree))
      {
        return;
      }
    }
    
    throw new NameConstraintValidatorException("Subject distinguished name is not from a permitted subtree");
  }
  

  private void checkExcludedDN(Set excluded, ASN1Sequence dns)
    throws NameConstraintValidatorException
  {
    if (excluded.isEmpty())
    {
      return;
    }
    
    Iterator it = excluded.iterator();
    
    while (it.hasNext())
    {
      ASN1Sequence subtree = (ASN1Sequence)it.next();
      
      if (withinDNSubtree(dns, subtree))
      {
        throw new NameConstraintValidatorException("Subject distinguished name is from an excluded subtree");
      }
    }
  }
  

  private Set intersectDN(Set permitted, Set dns)
  {
    Set intersect = new HashSet();
    for (Iterator it = dns.iterator(); it.hasNext();)
    {
      ASN1Sequence dn = ASN1Sequence.getInstance(
        ((GeneralSubtree)it.next()).getBase().getName().toASN1Primitive());
      if (permitted == null)
      {
        if (dn != null)
        {
          intersect.add(dn);
        }
      }
      else
      {
        Iterator _iter = permitted.iterator();
        while (_iter.hasNext())
        {
          ASN1Sequence subtree = (ASN1Sequence)_iter.next();
          
          if (withinDNSubtree(dn, subtree))
          {
            intersect.add(dn);
          }
          else if (withinDNSubtree(subtree, dn))
          {
            intersect.add(subtree);
          }
        }
      }
    }
    return intersect;
  }
  
  private Set unionDN(Set excluded, ASN1Sequence dn)
  {
    if (excluded.isEmpty())
    {
      if (dn == null)
      {
        return excluded;
      }
      excluded.add(dn);
      
      return excluded;
    }
    

    Set intersect = new HashSet();
    
    Iterator it = excluded.iterator();
    while (it.hasNext())
    {
      ASN1Sequence subtree = (ASN1Sequence)it.next();
      
      if (withinDNSubtree(dn, subtree))
      {
        intersect.add(subtree);
      }
      else if (withinDNSubtree(subtree, dn))
      {
        intersect.add(dn);
      }
      else
      {
        intersect.add(subtree);
        intersect.add(dn);
      }
    }
    
    return intersect;
  }
  

  private Set intersectEmail(Set permitted, Set emails)
  {
    Set intersect = new HashSet();
    for (Iterator it = emails.iterator(); it.hasNext();)
    {
      String email = extractNameAsString(((GeneralSubtree)it.next())
        .getBase());
      
      if (permitted == null)
      {
        if (email != null)
        {
          intersect.add(email);
        }
      }
      else
      {
        Iterator it2 = permitted.iterator();
        while (it2.hasNext())
        {
          String _permitted = (String)it2.next();
          
          intersectEmail(email, _permitted, intersect);
        }
      }
    }
    return intersect;
  }
  
  private Set unionEmail(Set excluded, String email)
  {
    if (excluded.isEmpty())
    {
      if (email == null)
      {
        return excluded;
      }
      excluded.add(email);
      return excluded;
    }
    

    Set union = new HashSet();
    
    Iterator it = excluded.iterator();
    while (it.hasNext())
    {
      String _excluded = (String)it.next();
      
      unionEmail(_excluded, email, union);
    }
    
    return union;
  }
  











  private Set intersectIP(Set permitted, Set ips)
  {
    Set intersect = new HashSet();
    for (Iterator it = ips.iterator(); it.hasNext();)
    {

      byte[] ip = ASN1OctetString.getInstance(((GeneralSubtree)it.next()).getBase().getName()).getOctets();
      if (permitted == null)
      {
        if (ip != null)
        {
          intersect.add(ip);
        }
      }
      else
      {
        Iterator it2 = permitted.iterator();
        while (it2.hasNext())
        {
          byte[] _permitted = (byte[])it2.next();
          intersect.addAll(intersectIPRange(_permitted, ip));
        }
      }
    }
    return intersect;
  }
  










  private Set unionIP(Set excluded, byte[] ip)
  {
    if (excluded.isEmpty())
    {
      if (ip == null)
      {
        return excluded;
      }
      excluded.add(ip);
      
      return excluded;
    }
    

    Set union = new HashSet();
    
    Iterator it = excluded.iterator();
    while (it.hasNext())
    {
      byte[] _excluded = (byte[])it.next();
      union.addAll(unionIPRange(_excluded, ip));
    }
    
    return union;
  }
  








  private Set unionIPRange(byte[] ipWithSubmask1, byte[] ipWithSubmask2)
  {
    Set set = new HashSet();
    

    if (Arrays.areEqual(ipWithSubmask1, ipWithSubmask2))
    {
      set.add(ipWithSubmask1);
    }
    else
    {
      set.add(ipWithSubmask1);
      set.add(ipWithSubmask2);
    }
    return set;
  }
  








  private Set intersectIPRange(byte[] ipWithSubmask1, byte[] ipWithSubmask2)
  {
    if (ipWithSubmask1.length != ipWithSubmask2.length)
    {
      return Collections.EMPTY_SET;
    }
    byte[][] temp = extractIPsAndSubnetMasks(ipWithSubmask1, ipWithSubmask2);
    byte[] ip1 = temp[0];
    byte[] subnetmask1 = temp[1];
    byte[] ip2 = temp[2];
    byte[] subnetmask2 = temp[3];
    
    byte[][] minMax = minMaxIPs(ip1, subnetmask1, ip2, subnetmask2);
    

    byte[] max = min(minMax[1], minMax[3]);
    byte[] min = max(minMax[0], minMax[2]);
    

    if (compareTo(min, max) == 1)
    {
      return Collections.EMPTY_SET;
    }
    
    byte[] ip = or(minMax[0], minMax[2]);
    byte[] subnetmask = or(subnetmask1, subnetmask2);
    return Collections.singleton(ipWithSubnetMask(ip, subnetmask));
  }
  







  private byte[] ipWithSubnetMask(byte[] ip, byte[] subnetMask)
  {
    int ipLength = ip.length;
    byte[] temp = new byte[ipLength * 2];
    System.arraycopy(ip, 0, temp, 0, ipLength);
    System.arraycopy(subnetMask, 0, temp, ipLength, ipLength);
    return temp;
  }
  










  private byte[][] extractIPsAndSubnetMasks(byte[] ipWithSubmask1, byte[] ipWithSubmask2)
  {
    int ipLength = ipWithSubmask1.length / 2;
    byte[] ip1 = new byte[ipLength];
    byte[] subnetmask1 = new byte[ipLength];
    System.arraycopy(ipWithSubmask1, 0, ip1, 0, ipLength);
    System.arraycopy(ipWithSubmask1, ipLength, subnetmask1, 0, ipLength);
    
    byte[] ip2 = new byte[ipLength];
    byte[] subnetmask2 = new byte[ipLength];
    System.arraycopy(ipWithSubmask2, 0, ip2, 0, ipLength);
    System.arraycopy(ipWithSubmask2, ipLength, subnetmask2, 0, ipLength);
    return new byte[][] { ip1, subnetmask1, ip2, subnetmask2 };
  }
  


















  private byte[][] minMaxIPs(byte[] ip1, byte[] subnetmask1, byte[] ip2, byte[] subnetmask2)
  {
    int ipLength = ip1.length;
    byte[] min1 = new byte[ipLength];
    byte[] max1 = new byte[ipLength];
    
    byte[] min2 = new byte[ipLength];
    byte[] max2 = new byte[ipLength];
    
    for (int i = 0; i < ipLength; i++)
    {
      min1[i] = ((byte)(ip1[i] & subnetmask1[i]));
      max1[i] = ((byte)(ip1[i] & subnetmask1[i] | subnetmask1[i] ^ 0xFFFFFFFF));
      
      min2[i] = ((byte)(ip2[i] & subnetmask2[i]));
      max2[i] = ((byte)(ip2[i] & subnetmask2[i] | subnetmask2[i] ^ 0xFFFFFFFF));
    }
    
    return new byte[][] { min1, max1, min2, max2 };
  }
  
  private void checkPermittedEmail(Set permitted, String email)
    throws NameConstraintValidatorException
  {
    if (permitted == null)
    {
      return;
    }
    
    Iterator it = permitted.iterator();
    
    while (it.hasNext())
    {
      String str = (String)it.next();
      
      if (emailIsConstrained(email, str))
      {
        return;
      }
    }
    
    if ((email.length() == 0) && (permitted.size() == 0))
    {
      return;
    }
    
    throw new NameConstraintValidatorException("Subject email address is not from a permitted subtree.");
  }
  

  private void checkExcludedEmail(Set excluded, String email)
    throws NameConstraintValidatorException
  {
    if (excluded.isEmpty())
    {
      return;
    }
    
    Iterator it = excluded.iterator();
    
    while (it.hasNext())
    {
      String str = (String)it.next();
      
      if (emailIsConstrained(email, str))
      {
        throw new NameConstraintValidatorException("Email address is from an excluded subtree.");
      }
    }
  }
  










  private void checkPermittedIP(Set permitted, byte[] ip)
    throws NameConstraintValidatorException
  {
    if (permitted == null)
    {
      return;
    }
    
    Iterator it = permitted.iterator();
    
    while (it.hasNext())
    {
      byte[] ipWithSubnet = (byte[])it.next();
      
      if (isIPConstrained(ip, ipWithSubnet))
      {
        return;
      }
    }
    if ((ip.length == 0) && (permitted.size() == 0))
    {
      return;
    }
    throw new NameConstraintValidatorException("IP is not from a permitted subtree.");
  }
  










  private void checkExcludedIP(Set excluded, byte[] ip)
    throws NameConstraintValidatorException
  {
    if (excluded.isEmpty())
    {
      return;
    }
    
    Iterator it = excluded.iterator();
    
    while (it.hasNext())
    {
      byte[] ipWithSubnet = (byte[])it.next();
      
      if (isIPConstrained(ip, ipWithSubnet))
      {
        throw new NameConstraintValidatorException("IP is from an excluded subtree.");
      }
    }
  }
  











  private boolean isIPConstrained(byte[] ip, byte[] constraint)
  {
    int ipLength = ip.length;
    
    if (ipLength != constraint.length / 2)
    {
      return false;
    }
    
    byte[] subnetMask = new byte[ipLength];
    System.arraycopy(constraint, ipLength, subnetMask, 0, ipLength);
    
    byte[] permittedSubnetAddress = new byte[ipLength];
    
    byte[] ipSubnetAddress = new byte[ipLength];
    

    for (int i = 0; i < ipLength; i++)
    {
      permittedSubnetAddress[i] = ((byte)(constraint[i] & subnetMask[i]));
      ipSubnetAddress[i] = ((byte)(ip[i] & subnetMask[i]));
    }
    
    return Arrays.areEqual(permittedSubnetAddress, ipSubnetAddress);
  }
  
  private boolean emailIsConstrained(String email, String constraint)
  {
    String sub = email.substring(email.indexOf('@') + 1);
    
    if (constraint.indexOf('@') != -1)
    {
      if (email.equalsIgnoreCase(constraint))
      {
        return true;
      }
      
    }
    else if (constraint.charAt(0) != '.')
    {
      if (sub.equalsIgnoreCase(constraint))
      {
        return true;
      }
      
    }
    else if (withinDomain(sub, constraint))
    {
      return true;
    }
    return false;
  }
  
  private boolean withinDomain(String testDomain, String domain)
  {
    String tempDomain = domain;
    if (tempDomain.startsWith("."))
    {
      tempDomain = tempDomain.substring(1);
    }
    String[] domainParts = Strings.split(tempDomain, '.');
    String[] testDomainParts = Strings.split(testDomain, '.');
    
    if (testDomainParts.length <= domainParts.length)
    {
      return false;
    }
    int d = testDomainParts.length - domainParts.length;
    for (int i = -1; i < domainParts.length; i++)
    {
      if (i == -1)
      {
        if (testDomainParts[(i + d)].equals(""))
        {
          return false;
        }
      }
      else if (!domainParts[i].equalsIgnoreCase(testDomainParts[(i + d)]))
      {
        return false;
      }
    }
    return true;
  }
  
  private void checkPermittedDNS(Set permitted, String dns)
    throws NameConstraintValidatorException
  {
    if (permitted == null)
    {
      return;
    }
    
    Iterator it = permitted.iterator();
    
    while (it.hasNext())
    {
      String str = (String)it.next();
      

      if ((withinDomain(dns, str)) || (dns.equalsIgnoreCase(str)))
      {
        return;
      }
    }
    if ((dns.length() == 0) && (permitted.size() == 0))
    {
      return;
    }
    throw new NameConstraintValidatorException("DNS is not from a permitted subtree.");
  }
  

  private void checkExcludedDNS(Set excluded, String dns)
    throws NameConstraintValidatorException
  {
    if (excluded.isEmpty())
    {
      return;
    }
    
    Iterator it = excluded.iterator();
    
    while (it.hasNext())
    {
      String str = (String)it.next();
      

      if ((withinDomain(dns, str)) || (dns.equalsIgnoreCase(str)))
      {
        throw new NameConstraintValidatorException("DNS is from an excluded subtree.");
      }
    }
  }
  











  private void unionEmail(String email1, String email2, Set union)
  {
    if (email1.indexOf('@') != -1)
    {
      String _sub = email1.substring(email1.indexOf('@') + 1);
      
      if (email2.indexOf('@') != -1)
      {
        if (email1.equalsIgnoreCase(email2))
        {
          union.add(email1);
        }
        else
        {
          union.add(email1);
          union.add(email2);
        }
        
      }
      else if (email2.startsWith("."))
      {
        if (withinDomain(_sub, email2))
        {
          union.add(email2);
        }
        else
        {
          union.add(email1);
          union.add(email2);

        }
        

      }
      else if (_sub.equalsIgnoreCase(email2))
      {
        union.add(email2);
      }
      else
      {
        union.add(email1);
        union.add(email2);
      }
      

    }
    else if (email1.startsWith("."))
    {
      if (email2.indexOf('@') != -1)
      {
        String _sub = email2.substring(email1.indexOf('@') + 1);
        if (withinDomain(_sub, email1))
        {
          union.add(email1);
        }
        else
        {
          union.add(email1);
          union.add(email2);
        }
        
      }
      else if (email2.startsWith("."))
      {
        if ((withinDomain(email1, email2)) || 
          (email1.equalsIgnoreCase(email2)))
        {
          union.add(email2);
        }
        else if (withinDomain(email2, email1))
        {
          union.add(email1);
        }
        else
        {
          union.add(email1);
          union.add(email2);
        }
        

      }
      else if (withinDomain(email2, email1))
      {
        union.add(email1);
      }
      else
      {
        union.add(email1);
        union.add(email2);

      }
      


    }
    else if (email2.indexOf('@') != -1)
    {
      String _sub = email2.substring(email1.indexOf('@') + 1);
      if (_sub.equalsIgnoreCase(email1))
      {
        union.add(email1);
      }
      else
      {
        union.add(email1);
        union.add(email2);
      }
      
    }
    else if (email2.startsWith("."))
    {
      if (withinDomain(email1, email2))
      {
        union.add(email2);
      }
      else
      {
        union.add(email1);
        union.add(email2);

      }
      

    }
    else if (email1.equalsIgnoreCase(email2))
    {
      union.add(email1);
    }
    else
    {
      union.add(email1);
      union.add(email2);
    }
  }
  



  private void unionURI(String email1, String email2, Set union)
  {
    if (email1.indexOf('@') != -1)
    {
      String _sub = email1.substring(email1.indexOf('@') + 1);
      
      if (email2.indexOf('@') != -1)
      {
        if (email1.equalsIgnoreCase(email2))
        {
          union.add(email1);
        }
        else
        {
          union.add(email1);
          union.add(email2);
        }
        
      }
      else if (email2.startsWith("."))
      {
        if (withinDomain(_sub, email2))
        {
          union.add(email2);
        }
        else
        {
          union.add(email1);
          union.add(email2);

        }
        

      }
      else if (_sub.equalsIgnoreCase(email2))
      {
        union.add(email2);
      }
      else
      {
        union.add(email1);
        union.add(email2);
      }
      

    }
    else if (email1.startsWith("."))
    {
      if (email2.indexOf('@') != -1)
      {
        String _sub = email2.substring(email1.indexOf('@') + 1);
        if (withinDomain(_sub, email1))
        {
          union.add(email1);
        }
        else
        {
          union.add(email1);
          union.add(email2);
        }
        
      }
      else if (email2.startsWith("."))
      {
        if ((withinDomain(email1, email2)) || 
          (email1.equalsIgnoreCase(email2)))
        {
          union.add(email2);
        }
        else if (withinDomain(email2, email1))
        {
          union.add(email1);
        }
        else
        {
          union.add(email1);
          union.add(email2);
        }
        

      }
      else if (withinDomain(email2, email1))
      {
        union.add(email1);
      }
      else
      {
        union.add(email1);
        union.add(email2);

      }
      


    }
    else if (email2.indexOf('@') != -1)
    {
      String _sub = email2.substring(email1.indexOf('@') + 1);
      if (_sub.equalsIgnoreCase(email1))
      {
        union.add(email1);
      }
      else
      {
        union.add(email1);
        union.add(email2);
      }
      
    }
    else if (email2.startsWith("."))
    {
      if (withinDomain(email1, email2))
      {
        union.add(email2);
      }
      else
      {
        union.add(email1);
        union.add(email2);

      }
      

    }
    else if (email1.equalsIgnoreCase(email2))
    {
      union.add(email1);
    }
    else
    {
      union.add(email1);
      union.add(email2);
    }
  }
  


  private Set intersectDNS(Set permitted, Set dnss)
  {
    Set intersect = new HashSet();
    for (Iterator it = dnss.iterator(); it.hasNext();)
    {
      String dns = extractNameAsString(((GeneralSubtree)it.next())
        .getBase());
      if (permitted == null)
      {
        if (dns != null)
        {
          intersect.add(dns);
        }
      }
      else
      {
        Iterator _iter = permitted.iterator();
        while (_iter.hasNext())
        {
          String _permitted = (String)_iter.next();
          
          if (withinDomain(_permitted, dns))
          {
            intersect.add(_permitted);
          }
          else if (withinDomain(dns, _permitted))
          {
            intersect.add(dns);
          }
        }
      }
    }
    
    return intersect;
  }
  
  private Set unionDNS(Set excluded, String dns)
  {
    if (excluded.isEmpty())
    {
      if (dns == null)
      {
        return excluded;
      }
      excluded.add(dns);
      
      return excluded;
    }
    

    Set union = new HashSet();
    
    Iterator _iter = excluded.iterator();
    while (_iter.hasNext())
    {
      String _permitted = (String)_iter.next();
      
      if (withinDomain(_permitted, dns))
      {
        union.add(dns);
      }
      else if (withinDomain(dns, _permitted))
      {
        union.add(_permitted);
      }
      else
      {
        union.add(_permitted);
        union.add(dns);
      }
    }
    
    return union;
  }
  










  private void intersectEmail(String email1, String email2, Set intersect)
  {
    if (email1.indexOf('@') != -1)
    {
      String _sub = email1.substring(email1.indexOf('@') + 1);
      
      if (email2.indexOf('@') != -1)
      {
        if (email1.equalsIgnoreCase(email2))
        {
          intersect.add(email1);
        }
        
      }
      else if (email2.startsWith("."))
      {
        if (withinDomain(_sub, email2))
        {
          intersect.add(email1);

        }
        

      }
      else if (_sub.equalsIgnoreCase(email2))
      {
        intersect.add(email1);
      }
      

    }
    else if (email1.startsWith("."))
    {
      if (email2.indexOf('@') != -1)
      {
        String _sub = email2.substring(email1.indexOf('@') + 1);
        if (withinDomain(_sub, email1))
        {
          intersect.add(email2);
        }
        
      }
      else if (email2.startsWith("."))
      {
        if ((withinDomain(email1, email2)) || 
          (email1.equalsIgnoreCase(email2)))
        {
          intersect.add(email1);
        }
        else if (withinDomain(email2, email1))
        {
          intersect.add(email2);
        }
        

      }
      else if (withinDomain(email2, email1))
      {
        intersect.add(email2);

      }
      


    }
    else if (email2.indexOf('@') != -1)
    {
      String _sub = email2.substring(email2.indexOf('@') + 1);
      if (_sub.equalsIgnoreCase(email1))
      {
        intersect.add(email2);
      }
      
    }
    else if (email2.startsWith("."))
    {
      if (withinDomain(email1, email2))
      {
        intersect.add(email1);

      }
      

    }
    else if (email1.equalsIgnoreCase(email2))
    {
      intersect.add(email1);
    }
  }
  


  private void checkExcludedURI(Set excluded, String uri)
    throws NameConstraintValidatorException
  {
    if (excluded.isEmpty())
    {
      return;
    }
    
    Iterator it = excluded.iterator();
    
    while (it.hasNext())
    {
      String str = (String)it.next();
      
      if (isUriConstrained(uri, str))
      {
        throw new NameConstraintValidatorException("URI is from an excluded subtree.");
      }
    }
  }
  

  private Set intersectURI(Set permitted, Set uris)
  {
    Set intersect = new HashSet();
    for (Iterator it = uris.iterator(); it.hasNext();)
    {
      String uri = extractNameAsString(((GeneralSubtree)it.next())
        .getBase());
      if (permitted == null)
      {
        if (uri != null)
        {
          intersect.add(uri);
        }
      }
      else
      {
        Iterator _iter = permitted.iterator();
        while (_iter.hasNext())
        {
          String _permitted = (String)_iter.next();
          intersectURI(_permitted, uri, intersect);
        }
      }
    }
    return intersect;
  }
  
  private Set unionURI(Set excluded, String uri)
  {
    if (excluded.isEmpty())
    {
      if (uri == null)
      {
        return excluded;
      }
      excluded.add(uri);
      
      return excluded;
    }
    

    Set union = new HashSet();
    
    Iterator _iter = excluded.iterator();
    while (_iter.hasNext())
    {
      String _excluded = (String)_iter.next();
      
      unionURI(_excluded, uri, union);
    }
    
    return union;
  }
  


  private void intersectURI(String email1, String email2, Set intersect)
  {
    if (email1.indexOf('@') != -1)
    {
      String _sub = email1.substring(email1.indexOf('@') + 1);
      
      if (email2.indexOf('@') != -1)
      {
        if (email1.equalsIgnoreCase(email2))
        {
          intersect.add(email1);
        }
        
      }
      else if (email2.startsWith("."))
      {
        if (withinDomain(_sub, email2))
        {
          intersect.add(email1);

        }
        

      }
      else if (_sub.equalsIgnoreCase(email2))
      {
        intersect.add(email1);
      }
      

    }
    else if (email1.startsWith("."))
    {
      if (email2.indexOf('@') != -1)
      {
        String _sub = email2.substring(email1.indexOf('@') + 1);
        if (withinDomain(_sub, email1))
        {
          intersect.add(email2);
        }
        
      }
      else if (email2.startsWith("."))
      {
        if ((withinDomain(email1, email2)) || 
          (email1.equalsIgnoreCase(email2)))
        {
          intersect.add(email1);
        }
        else if (withinDomain(email2, email1))
        {
          intersect.add(email2);
        }
        

      }
      else if (withinDomain(email2, email1))
      {
        intersect.add(email2);

      }
      


    }
    else if (email2.indexOf('@') != -1)
    {
      String _sub = email2.substring(email2.indexOf('@') + 1);
      if (_sub.equalsIgnoreCase(email1))
      {
        intersect.add(email2);
      }
      
    }
    else if (email2.startsWith("."))
    {
      if (withinDomain(email1, email2))
      {
        intersect.add(email1);

      }
      

    }
    else if (email1.equalsIgnoreCase(email2))
    {
      intersect.add(email1);
    }
  }
  


  private void checkPermittedURI(Set permitted, String uri)
    throws NameConstraintValidatorException
  {
    if (permitted == null)
    {
      return;
    }
    
    Iterator it = permitted.iterator();
    
    while (it.hasNext())
    {
      String str = (String)it.next();
      
      if (isUriConstrained(uri, str))
      {
        return;
      }
    }
    if ((uri.length() == 0) && (permitted.size() == 0))
    {
      return;
    }
    throw new NameConstraintValidatorException("URI is not from a permitted subtree.");
  }
  

  private boolean isUriConstrained(String uri, String constraint)
  {
    String host = extractHostFromURL(uri);
    
    if (!constraint.startsWith("."))
    {
      if (host.equalsIgnoreCase(constraint))
      {
        return true;
      }
      

    }
    else if (withinDomain(host, constraint))
    {
      return true;
    }
    
    return false;
  }
  


  private static String extractHostFromURL(String url)
  {
    String sub = url.substring(url.indexOf(':') + 1);
    
    if (sub.indexOf("//") != -1)
    {
      sub = sub.substring(sub.indexOf("//") + 2);
    }
    
    if (sub.lastIndexOf(':') != -1)
    {
      sub = sub.substring(0, sub.lastIndexOf(':'));
    }
    
    sub = sub.substring(sub.indexOf(':') + 1);
    sub = sub.substring(sub.indexOf('@') + 1);
    
    if (sub.indexOf('/') != -1)
    {
      sub = sub.substring(0, sub.indexOf('/'));
    }
    return sub;
  }
  
  private String extractNameAsString(GeneralName name)
  {
    return DERIA5String.getInstance(name.getName()).getString();
  }
  







  private static byte[] max(byte[] ip1, byte[] ip2)
  {
    for (int i = 0; i < ip1.length; i++)
    {
      if ((ip1[i] & 0xFFFF) > (ip2[i] & 0xFFFF))
      {
        return ip1;
      }
    }
    return ip2;
  }
  







  private static byte[] min(byte[] ip1, byte[] ip2)
  {
    for (int i = 0; i < ip1.length; i++)
    {
      if ((ip1[i] & 0xFFFF) < (ip2[i] & 0xFFFF))
      {
        return ip1;
      }
    }
    return ip2;
  }
  









  private static int compareTo(byte[] ip1, byte[] ip2)
  {
    if (Arrays.areEqual(ip1, ip2))
    {
      return 0;
    }
    if (Arrays.areEqual(max(ip1, ip2), ip1))
    {
      return 1;
    }
    return -1;
  }
  








  private static byte[] or(byte[] ip1, byte[] ip2)
  {
    byte[] temp = new byte[ip1.length];
    for (int i = 0; i < ip1.length; i++)
    {
      temp[i] = ((byte)(ip1[i] | ip2[i]));
    }
    return temp;
  }
  
  private int hashCollection(Collection coll)
  {
    if (coll == null)
    {
      return 0;
    }
    int hash = 0;
    Iterator it1 = coll.iterator();
    while (it1.hasNext())
    {
      Object o = it1.next();
      if ((o instanceof byte[]))
      {
        hash += Arrays.hashCode((byte[])o);
      }
      else
      {
        hash += o.hashCode();
      }
    }
    return hash;
  }
  
  private boolean collectionsAreEqual(Collection coll1, Collection coll2)
  {
    if (coll1 == coll2)
    {
      return true;
    }
    if ((coll1 == null) || (coll2 == null))
    {
      return false;
    }
    if (coll1.size() != coll2.size())
    {
      return false;
    }
    Iterator it1 = coll1.iterator();
    
    while (it1.hasNext())
    {
      Object a = it1.next();
      Iterator it2 = coll2.iterator();
      boolean found = false;
      while (it2.hasNext())
      {
        Object b = it2.next();
        if (equals(a, b))
        {
          found = true;
          break;
        }
      }
      if (!found)
      {
        return false;
      }
    }
    return true;
  }
  
  private boolean equals(Object o1, Object o2)
  {
    if (o1 == o2)
    {
      return true;
    }
    if ((o1 == null) || (o2 == null))
    {
      return false;
    }
    if (((o1 instanceof byte[])) && ((o2 instanceof byte[])))
    {
      return Arrays.areEqual((byte[])o1, (byte[])o2);
    }
    

    return o1.equals(o2);
  }
  







  private String stringifyIP(byte[] ip)
  {
    String temp = "";
    for (int i = 0; i < ip.length / 2; i++)
    {
      temp = temp + Integer.toString(ip[i] & 0xFF) + ".";
    }
    temp = temp.substring(0, temp.length() - 1);
    temp = temp + "/";
    for (int i = ip.length / 2; i < ip.length; i++)
    {
      temp = temp + Integer.toString(ip[i] & 0xFF) + ".";
    }
    temp = temp.substring(0, temp.length() - 1);
    return temp;
  }
  
  private String stringifyIPCollection(Set ips)
  {
    String temp = "";
    temp = temp + "[";
    for (Iterator it = ips.iterator(); it.hasNext();)
    {
      temp = temp + stringifyIP((byte[])it.next()) + ",";
    }
    if (temp.length() > 1)
    {
      temp = temp.substring(0, temp.length() - 1);
    }
    temp = temp + "]";
    return temp;
  }
}
