package net.java.games.input;















class UsagePair
{
  private final UsagePage usage_page;
  












  private final Usage usage;
  













  public UsagePair(UsagePage usage_page, Usage usage)
  {
    this.usage_page = usage_page;
    this.usage = usage;
  }
  
  public final UsagePage getUsagePage() {
    return usage_page;
  }
  
  public final Usage getUsage() {
    return usage;
  }
  
  public final int hashCode() {
    return usage.hashCode() ^ usage_page.hashCode();
  }
  
  public final boolean equals(Object other) {
    if (!(other instanceof UsagePair))
      return false;
    UsagePair other_pair = (UsagePair)other;
    return (usage.equals(usage)) && (usage_page.equals(usage_page));
  }
  
  public final String toString() {
    return "UsagePair: (page = " + usage_page + ", usage = " + usage + ")";
  }
}
