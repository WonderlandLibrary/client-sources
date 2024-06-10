package wtf.opal;

import java.util.ArrayList;
import java.util.Vector;

public class on implements t5 {
  private long a;
  
  private int[] b;
  
  private t5 c;
  
  private long d;
  
  private long[] j;
  
  private static long[] k;
  
  static int[] e;
  
  private static ArrayList f;
  
  private static Vector m;
  
  private static int i;
  
  private static Object o;
  
  private static int h = 52;
  
  private static int l;
  
  private static int n = 17;
  
  public static Object g() {
    return o;
  }
  
  public static t5 a(long paramLong1, long paramLong2, Object paramObject) {
    qg.a((paramLong1 > 0L));
    t5 t51 = d(paramLong1);
    t5 t52 = d(paramLong2);
    t5 t53 = qg.a(t51, t52);
    if (paramObject != null)
      m.add(paramObject); 
    return t53;
  }
  
  static t5 c(long paramLong) {
    int i = (int)a(paramLong, h, 63, e, k);
    if (i < i)
      return f.get(i); 
    if (f.size() % l == 0)
      e = (int[])e.clone(); 
    on on1 = new on(paramLong);
    f.add(on1);
    return on1;
  }
  
  private static t5 d(long paramLong) {
    return new on(paramLong);
  }
  
  static void a(qg paramqg) {
    i = f.size();
    c();
    paramqg.d();
  }
  
  static void b(qg paramqg) {
    c();
    (e = new int[64])[0] = -5;
    (e = new int[64])[1] = -3;
    (e = new int[64])[2] = -10;
    (e = new int[64])[3] = -51;
    (e = new int[64])[4] = 3;
    (e = new int[64])[5] = 5;
    (e = new int[64])[6] = -25;
    (e = new int[64])[7] = -9;
    (e = new int[64])[8] = -39;
    (e = new int[64])[9] = -42;
    (e = new int[64])[10] = -25;
    (e = new int[64])[11] = -16;
    (e = new int[64])[12] = 10;
    (e = new int[64])[13] = -10;
    (e = new int[64])[14] = -7;
    (e = new int[64])[15] = -35;
    (e = new int[64])[16] = 9;
    (e = new int[64])[17] = -12;
    (e = new int[64])[18] = -34;
    (e = new int[64])[19] = -21;
    (e = new int[64])[20] = -13;
    (e = new int[64])[21] = 7;
    (e = new int[64])[22] = -15;
    (e = new int[64])[23] = 10;
    (e = new int[64])[24] = -29;
    (e = new int[64])[25] = -21;
    (e = new int[64])[26] = -37;
    (e = new int[64])[27] = 16;
    (e = new int[64])[28] = -4;
    (e = new int[64])[29] = 12;
    (e = new int[64])[30] = -6;
    (e = new int[64])[31] = 25;
    (e = new int[64])[32] = 4;
    (e = new int[64])[33] = 13;
    (e = new int[64])[34] = -15;
    (e = new int[64])[35] = 25;
    (e = new int[64])[36] = 6;
    (e = new int[64])[37] = 15;
    (e = new int[64])[38] = -10;
    (e = new int[64])[39] = -22;
    (e = new int[64])[40] = 21;
    (e = new int[64])[41] = -4;
    (e = new int[64])[42] = -15;
    (e = new int[64])[43] = -13;
    (e = new int[64])[44] = -18;
    (e = new int[64])[45] = 4;
    (e = new int[64])[46] = 21;
    (e = new int[64])[47] = 39;
    (e = new int[64])[48] = 10;
    (e = new int[64])[49] = 15;
    (e = new int[64])[50] = 35;
    (e = new int[64])[51] = 42;
    (e = new int[64])[52] = 34;
    (e = new int[64])[53] = 29;
    (e = new int[64])[54] = 51;
    (e = new int[64])[55] = -4;
    (e = new int[64])[56] = 13;
    (e = new int[64])[57] = 15;
    (e = new int[64])[58] = -2;
    (e = new int[64])[59] = 4;
    (e = new int[64])[60] = 2;
    (e = new int[64])[61] = 22;
    (e = new int[64])[62] = 18;
    (e = new int[64])[63] = 37;
    paramqg.c();
  }
  
  private on(long paramLong) {
    this.a = paramLong;
    this.b = e;
    this.j = k;
  }
  
  public long a(long paramLong) {
    long l1 = a(8, 55);
    long l2 = this.a ^ paramLong ^ this.d;
    this.a = l2;
    if (this.c != null)
      this.c.a(paramLong); 
    return l1;
  }
  
  public void b(long paramLong) {
    this.d = paramLong;
  }
  
  public void a(t5 paramt5) {
    if (this != paramt5)
      if (this.c == null) {
        this.c = paramt5;
      } else {
        this.c.a(paramt5);
      }  
  }
  
  public int hashCode() {
    return (int)a(8);
  }
  
  public boolean equals(Object paramObject) {
    return (this == paramObject) ? true : ((paramObject instanceof on) ? ((a(56) == ((on)paramObject).a(56))) : false);
  }
  
  public boolean b(t5 paramt5) {
    return (this == paramt5) ? true : ((paramt5 instanceof on) ? ((a(56, 63) - ((on)paramt5).a(56, 63) <= 0L)) : true);
  }
  
  public int[] b() {
    return this.b;
  }
  
  private long a(int paramInt) {
    return a(0, paramInt - 1);
  }
  
  private long a(int paramInt1, int paramInt2) {
    return a(this.a, paramInt1, paramInt2, this.b, this.j);
  }
  
  private static long a(long paramLong, int paramInt1, int paramInt2, int[] paramArrayOfint, long[] paramArrayOflong) {
    long l1 = 0L;
    int i = paramArrayOfint.length;
    for (byte b = 0; b < i; b++) {
      long l = paramLong & k[b];
      int m = paramArrayOfint[b];
      if (l != 0L) {
        if (m > 0) {
          l >>>= m;
        } else if (m < 0) {
          l <<= (m ^ 0xFFFFFFFF) + 1;
        } 
        l1 |= l;
      } 
    } 
    i = 64;
    long l2 = l1;
    int j = i - 1 - paramInt2;
    if (j > 0)
      l2 <<= j; 
    int k = paramInt1 + i - 1 - paramInt2;
    if (k > 0)
      l2 >>>= k; 
    return l2;
  }
  
  private static void c() {
    boolean bool = false;
    a(0, f.size() - 1, f, new ArrayList(f), bool);
  }
  
  private static void a(int paramInt1, int paramInt2, ArrayList paramArrayList1, ArrayList paramArrayList2, int paramInt3) {
    if (paramInt1 < paramInt2) {
      int i = paramInt1 + (paramInt2 - paramInt1) / 2;
      if (++paramInt3 < n) {
        a(paramInt1, i, paramArrayList1, paramArrayList2, paramInt3);
        a(i + 1, paramInt2, paramArrayList1, paramArrayList2, paramInt3);
      } 
      a(paramInt1, i, paramInt2, paramArrayList1, paramArrayList2);
    } 
  }
  
  private static void a(int paramInt1, int paramInt2, int paramInt3, ArrayList<t5> paramArrayList1, ArrayList<t5> paramArrayList2) {
    int i = paramInt1;
    int j = paramInt2 + 1;
    for (int k = paramInt1; k <= paramInt3; k++)
      paramArrayList2.set(k, paramArrayList1.get(k)); 
    while (i <= paramInt2 && j <= paramInt3) {
      t5 t51;
      if (((t5)paramArrayList2.get(i)).b(paramArrayList2.get(j))) {
        t51 = paramArrayList2.get(i++);
      } else {
        t51 = paramArrayList2.get(j++);
      } 
      paramArrayList1.set(paramInt1, t51);
      paramInt1++;
    } 
    while (i <= paramInt2) {
      paramArrayList1.set(paramInt1, paramArrayList2.get(i));
      paramInt1++;
      i++;
    } 
  }
  
  static {
    l = 128;
    k = new long[64];
    long l = 1L;
    for (byte b = 0; b < 64; b++) {
      k[b] = l;
      l <<= 1L;
    } 
    o = new Object();
    m = new Vector();
    f = new ArrayList();
    a0();
    i = f.size();
    c();
  }
  
  private static void a0() {
    e = new int[] { 
        -28, -51, -16, -10, -40, -49, -10, -12, -49, -26, 
        -10, -50, -25, 10, -44, -19, 10, -39, 16, 12, 
        10, -10, -25, -39, -25, -7, -24, -2, 28, 2, 
        -23, 10, 7, -18, 19, 26, -2, 25, 2, -6, 
        -19, -14, -21, -3, 40, 6, 3, 25, -12, 25, 
        24, 18, 51, 23, 49, 14, 39, 49, 44, 19, 
        12, 50, 39, 21 };
    f.add(new on(9200006802793876430L));
    f.add(new on(-1807040979662195412L));
    f.add(new on(-871300643635328761L));
    f.add(new on(5421170208912975185L));
    f.add(new on(-6499749506550448374L));
    f.add(new on(956443671717039640L));
    f.add(new on(4038599498543863981L));
    f.add(new on(1694734682427160547L));
    f.add(new on(6325149080236995172L));
    f.add(new on(-8352136137784869846L));
    f.add(new on(2455716042840857266L));
    f.add(new on(-7240222305062666411L));
    f.add(new on(-3679009388016493118L));
    f.add(new on(-286971477938445402L));
    f.add(new on(4198991805691489365L));
    f.add(new on(4908854890808337908L));
    f.add(new on(-736926832704952982L));
    f.add(new on(5265922612267545656L));
    f.add(new on(1309426457075978571L));
    f.add(new on(6441568728384905344L));
    f.add(new on(3538353132900193779L));
    f.add(new on(3701686924517637978L));
    f.add(new on(7731062638365477248L));
    f.add(new on(-2863637026629655365L));
    f.add(new on(-1594347290982655748L));
    f.add(new on(-6661953586122144109L));
    f.add(new on(8179241547939358838L));
    f.add(new on(-1738687679638190327L));
    f.add(new on(5804040859318579971L));
    f.add(new on(-498416792021280743L));
    f.add(new on(6042485048912997490L));
    f.add(new on(5814712089323955540L));
    f.add(new on(3033989147217672572L));
    f.add(new on(-677764355554898868L));
    f.add(new on(6613677143803185291L));
    f.add(new on(832106110637438922L));
    f.add(new on(172022363795460290L));
    f.add(new on(-2153251392382304850L));
    f.add(new on(-2846151159984586418L));
    f.add(new on(8508704007852153150L));
    f.add(new on(-6095431296422528590L));
    f.add(new on(-117953602953800482L));
    f.add(new on(413985804247325766L));
    f.add(new on(6483033971874393521L));
    f.add(new on(8584696774525746570L));
    f.add(new on(6564257724468741453L));
    f.add(new on(-5445104907482679571L));
    f.add(new on(6564538099942065485L));
    f.add(new on(3433731411450268807L));
    f.add(new on(2218684561206364392L));
    f.add(new on(799949462261508471L));
    f.add(new on(-5274949842621980199L));
    f.add(new on(-3560199172068674639L));
    f.add(new on(6679503843862549051L));
    f.add(new on(4481434220458282088L));
    f.add(new on(9193923320829692569L));
    f.add(new on(396578126830540463L));
    f.add(new on(8497698907651219944L));
    f.add(new on(-9177916168439348839L));
    f.add(new on(2884850369428987720L));
    f.add(new on(-2433727756887183156L));
    f.add(new on(3067529511191530996L));
    f.add(new on(3561931575703132310L));
    f.add(new on(-5418371194178674026L));
    f.add(new on(-7381605114201047004L));
    f.add(new on(140139962872473831L));
    f.add(new on(-8112151640167119359L));
    f.add(new on(-4174967856093904991L));
    f.add(new on(-5763194244438871898L));
    f.add(new on(-1738401806623226359L));
    f.add(new on(501681996251629461L));
    f.add(new on(-5254148103506167365L));
    f.add(new on(-4845230246764234287L));
    f.add(new on(1648805977056834132L));
    f.add(new on(-8566571508337220635L));
    f.add(new on(-887770342060627085L));
    f.add(new on(9005080648565672177L));
    f.add(new on(-8132931511121794452L));
    f.add(new on(2742214878563303835L));
    f.add(new on(-177315737429640527L));
    f.add(new on(-9162107204418073560L));
    f.add(new on(-7289932775919136172L));
    f.add(new on(-4587074281847755900L));
    f.add(new on(1574246386515710236L));
    f.add(new on(886440360447610988L));
    f.add(new on(8863925118365550495L));
    f.add(new on(928253361688169222L));
    f.add(new on(-8132937008671544468L));
    f.add(new on(3026868031890773430L));
    f.add(new on(-6845761511102040954L));
    f.add(new on(-1850912873328537849L));
    f.add(new on(3047553556079643194L));
    f.add(new on(-2343836955127292818L));
    f.add(new on(2493373267986894883L));
    f.add(new on(-7662956370504347245L));
    f.add(new on(5560979363289667242L));
    f.add(new on(-6216936098776308968L));
    f.add(new on(-2460631022716350095L));
    f.add(new on(-6888654575540882472L));
    f.add(new on(3210618102425579642L));
    f.add(new on(9054224146524157086L));
    f.add(new on(-6110851786921337475L));
    f.add(new on(6549062009045569324L));
    f.add(new on(-5219533890995324004L));
    e = (int[])e.clone();
    f.add(new on(-7780446929375011241L));
    f.add(new on(-5578859996137009074L));
    f.add(new on(1781320711377903684L));
    f.add(new on(-4582957116967982445L));
    f.add(new on(7819358983812686185L));
    f.add(new on(-8694227696029036253L));
    f.add(new on(5771105441764306788L));
    f.add(new on(-8999805189432382457L));
    f.add(new on(7472031549827249100L));
    f.add(new on(-3255665008927073699L));
    f.add(new on(-2195320007154716936L));
    f.add(new on(-7717004159428396892L));
    f.add(new on(-5083088877437873849L));
    f.add(new on(-5083907325923014371L));
    f.add(new on(-9184673553410509805L));
    f.add(new on(-5934827480409311047L));
    f.add(new on(4490595181637803275L));
    f.add(new on(3982428707928259010L));
    f.add(new on(-6535368099616313812L));
    f.add(new on(9104661595909311024L));
    f.add(new on(168239405489895762L));
    f.add(new on(-3964873787066778668L));
    f.add(new on(-3609669238145201568L));
    f.add(new on(6441433275448915642L));
    f.add(new on(2530421689186044643L));
    f.add(new on(4502315588945498755L));
    f.add(new on(-5647376099593391211L));
    f.add(new on(8424763668263405125L));
    f.add(new on(1396619293566433042L));
    f.add(new on(-8277368629309732251L));
    f.add(new on(5597298879533148129L));
    f.add(new on(7689339687608509938L));
    f.add(new on(6872744740610776464L));
    f.add(new on(-8961946855103059851L));
    f.add(new on(7256440136900866201L));
    f.add(new on(5044310459175342769L));
    f.add(new on(7224310685714619979L));
    f.add(new on(1050658683443883357L));
    f.add(new on(-2427096510412713098L));
    f.add(new on(1761052754208404002L));
    f.add(new on(3332968060070553861L));
    f.add(new on(-3358526757636545662L));
    f.add(new on(4498006223835105801L));
    f.add(new on(2407065449510039486L));
    f.add(new on(-5184626681009531037L));
    f.add(new on(-5984772217060634361L));
    f.add(new on(-6365930374525672525L));
    f.add(new on(-5317016678921073513L));
    f.add(new on(7029214915076254374L));
    f.add(new on(688432582178292194L));
    f.add(new on(1249137852173720552L));
    f.add(new on(-2445484580089280475L));
    f.add(new on(3982427608425148610L));
    f.add(new on(-3917506108829273390L));
    f.add(new on(886193845172757715L));
    f.add(new on(-4500765137466957306L));
    f.add(new on(9104665993955937072L));
    f.add(new on(-2912437310376210035L));
    f.add(new on(3926814948291655952L));
    f.add(new on(-6923169542543234623L));
    f.add(new on(-2585637807700182370L));
    f.add(new on(3312239935369384054L));
    f.add(new on(-6943596052073573614L));
    f.add(new on(5890401479446651679L));
    f.add(new on(-3461983667482126635L));
    f.add(new on(6036495075413970387L));
    f.add(new on(-2333847907160090209L));
    f.add(new on(1726854786505649489L));
    f.add(new on(-53919859073702000L));
    f.add(new on(3307917328472825253L));
    f.add(new on(-2698907520809566027L));
    f.add(new on(8008975604076870186L));
    f.add(new on(-2369794476438925668L));
    f.add(new on(-5916206699243218871L));
    f.add(new on(-1154051230392946391L));
    f.add(new on(2856391724401820035L));
    f.add(new on(-5569535952481174002L));
    f.add(new on(-4719929623957681127L));
    f.add(new on(48089816496224729L));
    f.add(new on(-6759951053897414262L));
    f.add(new on(5800289101028869096L));
    f.add(new on(-44943087903405895L));
    f.add(new on(6857352670233487644L));
    f.add(new on(4172005600958899528L));
    f.add(new on(-6180278491897757461L));
    f.add(new on(5344419225724796232L));
    f.add(new on(-240457117045692921L));
    f.add(new on(5304211521965248284L));
    f.add(new on(7986801348502088933L));
    f.add(new on(-2999109316833811008L));
    f.add(new on(-2273983394852694689L));
    f.add(new on(2396003999456804243L));
    f.add(new on(8125753415529482059L));
    f.add(new on(4963653895347315309L));
    f.add(new on(4710669602158573727L));
    f.add(new on(-1810498041112501711L));
    f.add(new on(3596586261136643743L));
    f.add(new on(6606017149395405435L));
    f.add(new on(-3883562598883897452L));
    f.add(new on(-2264865891348263276L));
    f.add(new on(1151422177291020231L));
    f.add(new on(-4658510582800221331L));
    f.add(new on(-2891551685627213150L));
    f.add(new on(-6673040643283521996L));
    f.add(new on(5718256531298356504L));
    f.add(new on(5410539381686552178L));
    f.add(new on(4713466486342527343L));
    f.add(new on(6706269393144029451L));
    f.add(new on(812987802444688279L));
    f.add(new on(-2999761895857902117L));
    f.add(new on(508372078680589520L));
    f.add(new on(-7176097512129028624L));
    f.add(new on(2145768296610292291L));
    f.add(new on(8982272069358006328L));
    f.add(new on(7373805434429352230L));
    f.add(new on(-8745203118146177671L));
    f.add(new on(-412825068479522646L));
    f.add(new on(-3251316981765361468L));
    f.add(new on(7068941586229007909L));
    f.add(new on(5242991717124485361L));
    f.add(new on(-6556382630704678514L));
    f.add(new on(-3470984743970368739L));
    f.add(new on(-5701810168307473173L));
    f.add(new on(6746719816804547614L));
    f.add(new on(-234744069498264441L));
    f.add(new on(-4658032273753141810L));
    f.add(new on(5041362174755778035L));
    f.add(new on(5983173888332076876L));
    f.add(new on(-8467601993700483150L));
    f.add(new on(-8668816083876751946L));
    f.add(new on(-3370458497466027187L));
    f.add(new on(6256412093337039511L));
    f.add(new on(-3203901781415666884L));
    f.add(new on(-1885093076777400883L));
    f.add(new on(-4045692590470274318L));
    f.add(new on(-3433422923231249346L));
    f.add(new on(-6992884100341848621L));
    f.add(new on(7265498644800751843L));
    f.add(new on(5490873441139481104L));
    f.add(new on(-7273927102723822884L));
    f.add(new on(928256660223052294L));
    f.add(new on(4257723427653466950L));
    f.add(new on(8705085875679512230L));
    f.add(new on(8485195776885903772L));
    f.add(new on(569894534246849238L));
    f.add(new on(2119336040977251005L));
    f.add(new on(5175700311859950120L));
    f.add(new on(3787941964033976289L));
    f.add(new on(1256821192064954772L));
    f.add(new on(6656462047188096502L));
    f.add(new on(834328791285386327L));
    f.add(new on(4861285870783860456L));
    f.add(new on(-5838164814466156856L));
    f.add(new on(-5762777676912867325L));
    f.add(new on(-4439923970447687119L));
    f.add(new on(3635497478060862956L));
    f.add(new on(8214729717127279743L));
    f.add(new on(-4486376446143038399L));
    f.add(new on(6415251737008381101L));
    f.add(new on(-4798069933877986955L));
    f.add(new on(-4309766946869011715L));
    f.add(new on(4815214809367865153L));
    f.add(new on(-2006862176600710890L));
    f.add(new on(-4001763764213303609L));
    f.add(new on(4716651582436536790L));
    f.add(new on(5805615817861614488L));
    f.add(new on(2373204976881736798L));
    f.add(new on(-7123360047934668081L));
    f.add(new on(-1347783956774517408L));
    f.add(new on(4797711393487796277L));
    f.add(new on(-1014074453416277244L));
    f.add(new on(8228557921488452819L));
    f.add(new on(-8049176752182085745L));
    f.add(new on(-8758836650021311308L));
    f.add(new on(8118064791692704612L));
    f.add(new on(-2792889686633385461L));
    f.add(new on(-9101662860582241028L));
    f.add(new on(-5233384649655078796L));
    f.add(new on(-6665356095340307517L));
    f.add(new on(-5887082758183912582L));
    f.add(new on(5456632073576882801L));
    f.add(new on(2809042084751232807L));
    f.add(new on(-7059997464300142586L));
    f.add(new on(7319243188681158094L));
    f.add(new on(-92709947794499039L));
    f.add(new on(6395948887637766607L));
    e = (int[])e.clone();
    f.add(new on(4251561362002592421L));
    f.add(new on(5733832184461815047L));
    f.add(new on(4813975115319762756L));
    f.add(new on(5742720396426115501L));
    f.add(new on(-7325209681497544793L));
    f.add(new on(-5741326613431949691L));
    f.add(new on(-6523264429214746367L));
    f.add(new on(-998939535318668867L));
    f.add(new on(8547156591150705849L));
    f.add(new on(894066116530988323L));
    f.add(new on(5522324042029670742L));
    f.add(new on(2740323208605032950L));
    f.add(new on(8307778881333501644L));
    f.add(new on(-4719925225911057383L));
    f.add(new on(215518632743740460L));
    f.add(new on(-187926705626537575L));
    f.add(new on(1872081990135636282L));
    f.add(new on(-4114107062232003480L));
    f.add(new on(-2887966429032803639L));
    f.add(new on(-6165676634435844503L));
    f.add(new on(3031763771762443967L));
    f.add(new on(-5908850230339200910L));
    f.add(new on(-5909323392173794578L));
    f.add(new on(-7529440702846805591L));
    f.add(new on(-692879520258954027L));
    f.add(new on(-1805902994281278365L));
    f.add(new on(-3820458671747911666L));
    f.add(new on(-5539772915870188326L));
    f.add(new on(3590675868393223771L));
    f.add(new on(-723802302075928079L));
    f.add(new on(-8047878772409984475L));
    f.add(new on(-3679004989969982270L));
    f.add(new on(-6835736962538263071L));
    f.add(new on(-4825398158847788267L));
    f.add(new on(-8829279300756906766L));
    f.add(new on(2311126514686727918L));
    f.add(new on(4334355138448614919L));
    f.add(new on(2582693387025155607L));
    f.add(new on(-6989634953326258451L));
    f.add(new on(-7669502197340353416L));
    f.add(new on(8876948637948136329L));
    f.add(new on(-7526805934997399995L));
    f.add(new on(5112140237969064736L));
    f.add(new on(-4005582140140352888L));
    f.add(new on(-1158249766905365827L));
    f.add(new on(5710403521460862716L));
    f.add(new on(-6324130134406616552L));
    f.add(new on(-7877244114896199043L));
    f.add(new on(-7242032738817180361L));
    f.add(new on(1092488188429554170L));
    f.add(new on(-3871213003671210080L));
    f.add(new on(-1469830343176873174L));
    f.add(new on(7017977897196766181L));
    f.add(new on(2105090036288550038L));
    f.add(new on(5076683808411465023L));
    f.add(new on(9201060952504056263L));
    f.add(new on(1471604827943396732L));
    f.add(new on(6901130190557361063L));
    f.add(new on(3957196150839605549L));
    f.add(new on(-4749872577402911662L));
    f.add(new on(-4915727100396640222L));
    f.add(new on(-3729099085041788894L));
    f.add(new on(6989421570749374813L));
    f.add(new on(-6455513585652180392L));
    f.add(new on(-2014048533619472536L));
    f.add(new on(-2310252601137121335L));
    f.add(new on(6487035713406913789L));
    f.add(new on(-1565698867743401061L));
    f.add(new on(6966941438595060946L));
    f.add(new on(-2426792691212376256L));
    f.add(new on(2600799193138629971L));
    f.add(new on(-7716401691106827152L));
    f.add(new on(-6118153233844509632L));
    f.add(new on(5249557629633725809L));
    f.add(new on(-2035382943261922344L));
    f.add(new on(7631825851601020558L));
    f.add(new on(-68071270569900055L));
    f.add(new on(2488930842857911153L));
    f.add(new on(478320170266291612L));
    f.add(new on(-2138719705693199645L));
    f.add(new on(-8480052108290064064L));
    f.add(new on(8801148428937114801L));
    f.add(new on(-5786992791720597674L));
    f.add(new on(-6479983190826363961L));
    f.add(new on(-7685875085094023729L));
    f.add(new on(6546265645440001440L));
    f.add(new on(1391271327028803432L));
    f.add(new on(7197126825512952910L));
    f.add(new on(4954136571255861094L));
    f.add(new on(2407386995526101757L));
    f.add(new on(5457242324465737893L));
    f.add(new on(570468306103422299L));
    f.add(new on(-9029712559529421578L));
    f.add(new on(538035854418967592L));
    f.add(new on(5041843428996832615L));
    f.add(new on(3438340373568750370L));
    f.add(new on(-8670950593508376174L));
    f.add(new on(3814330232912411738L));
    f.add(new on(-4024014376478226549L));
    f.add(new on(-59807613578640538L));
    f.add(new on(-6024114033592917348L));
    f.add(new on(-5980963349231663094L));
    f.add(new on(-2608164221234256572L));
    f.add(new on(6600211232970739324L));
    f.add(new on(5737398371862850566L));
    f.add(new on(2463663465679732465L));
    f.add(new on(8071982618945278910L));
    f.add(new on(-4580055184896478221L));
    f.add(new on(5213664430084410483L));
    f.add(new on(-6398241889990252027L));
    f.add(new on(3526195867397258500L));
    f.add(new on(-3474188369110273953L));
    f.add(new on(-5323913814240564726L));
    f.add(new on(-5753330074358522178L));
    f.add(new on(-727550078204860465L));
    f.add(new on(-9080650590077793490L));
    f.add(new on(-7083311524169553682L));
    f.add(new on(1240139760229046352L));
    f.add(new on(3773516420236616239L));
    f.add(new on(-1203361594545247330L));
    f.add(new on(3240446193233916887L));
    f.add(new on(-7691681286129294982L));
    f.add(new on(7753810617230254427L));
    f.add(new on(955636017612547368L));
    f.add(new on(-8726044285232643497L));
    f.add(new on(6208991893427968387L));
    f.add(new on(3148171514991366157L));
    f.add(new on(-2038273406170228742L));
    f.add(new on(5471220320165086046L));
    f.add(new on(2298851022139306517L));
    f.add(new on(-8580100315961489353L));
    f.add(new on(8659262651255516582L));
    f.add(new on(-107408198956129798L));
    f.add(new on(5296195357743909213L));
    f.add(new on(6458515182139983940L));
    f.add(new on(-5159373402285262007L));
    f.add(new on(8719106912082179893L));
    f.add(new on(6797666564243711603L));
    f.add(new on(-7101938714676554101L));
    f.add(new on(5399645622144000226L));
    f.add(new on(6564257724477129805L));
    f.add(new on(-2413897962535564303L));
    f.add(new on(-3848118547461680390L));
    f.add(new on(3092593566590613963L));
    f.add(new on(2862649113003419973L));
    f.add(new on(5795993183958393156L));
    f.add(new on(-9002781563351959553L));
    f.add(new on(-7210344496990896592L));
    f.add(new on(4149125970215263292L));
    f.add(new on(8683199573541199594L));
    f.add(new on(-1155625711697200827L));
    f.add(new on(5803834219755665250L));
    f.add(new on(5092366911844042547L));
    f.add(new on(8379493097464796293L));
    f.add(new on(-8078370596712838987L));
    f.add(new on(-2599432910194558399L));
    f.add(new on(-8922742016278524650L));
    f.add(new on(-7532599300735467476L));
    f.add(new on(3377820277880316980L));
    f.add(new on(7912033425063851196L));
    f.add(new on(7238610084985444265L));
    f.add(new on(3762177348478993888L));
    f.add(new on(7268977915974578509L));
    f.add(new on(4760854871745968391L));
    f.add(new on(720986366040317998L));
    f.add(new on(4497790436732312652L));
    f.add(new on(5656135748349199497L));
    f.add(new on(-589570241334927048L));
    f.add(new on(-7453881902450128814L));
    f.add(new on(-899527032401660859L));
    f.add(new on(-7076952604655258493L));
    f.add(new on(-5363434027447251809L));
    f.add(new on(2328560923113146826L));
    f.add(new on(-3842620574635374401L));
    f.add(new on(-4192189572248519471L));
    f.add(new on(72361935045416573L));
    e = (int[])e.clone();
    f.add(new on(-7079965533798420304L));
    f.add(new on(2643287798841562096L));
    f.add(new on(-3033292334823888292L));
    f.add(new on(-6961002583257483384L));
    f.add(new on(671842721679019692L));
    f.add(new on(-1040571467523476446L));
    f.add(new on(-3402016242768009492L));
    f.add(new on(2279815467974444187L));
    f.add(new on(603655357210478620L));
    f.add(new on(7277105047996793013L));
    f.add(new on(-6693782717549960190L));
    f.add(new on(5170277567216180598L));
    f.add(new on(7438523560354736766L));
    f.add(new on(5292473582609620522L));
    f.add(new on(-1711528972422714111L));
    f.add(new on(-6726181355278408413L));
    f.add(new on(5770409980023424600L));
    f.add(new on(2679661253909636218L));
    f.add(new on(2625578685275762779L));
    f.add(new on(-4485892967018275150L));
    f.add(new on(-3454445505184500478L));
    f.add(new on(8944017654663276851L));
    f.add(new on(6805230980126300270L));
    f.add(new on(2904185719246243222L));
    f.add(new on(-5219534990506837348L));
    f.add(new on(-6859667937205115167L));
    f.add(new on(-835281014957124014L));
    f.add(new on(-688461076409800032L));
    f.add(new on(-2341504569849471080L));
    f.add(new on(396532962930071143L));
    f.add(new on(-1731114145884334493L));
    f.add(new on(-2003009073307638622L));
    f.add(new on(8837053947348979448L));
    f.add(new on(-8553465815683773780L));
    f.add(new on(-1675523773734152014L));
    f.add(new on(-3668924560878168890L));
    f.add(new on(7665171155031296666L));
    f.add(new on(9155585295972241616L));
    f.add(new on(-445182904617961828L));
    f.add(new on(-145829312271407026L));
    f.add(new on(1758740072121991527L));
    f.add(new on(1810624526423159288L));
    f.add(new on(3139880659257121150L));
    f.add(new on(-7744612315744539608L));
    f.add(new on(-8105339648090228153L));
    f.add(new on(4920579250334279478L));
    f.add(new on(-6426696094813968639L));
    f.add(new on(994532259872162563L));
    f.add(new on(1100779941780334278L));
    f.add(new on(4034069342015925237L));
    f.add(new on(-8590321169374671615L));
    f.add(new on(243389991722467560L));
    f.add(new on(-8605352637825251009L));
    f.add(new on(-8492938815081478324L));
    f.add(new on(4398710651284788348L));
    f.add(new on(-7181525217274536860L));
    f.add(new on(8880003483133913563L));
    f.add(new on(6586659793351373279L));
    f.add(new on(5763628544975050820L));
    f.add(new on(-3073433082496856734L));
    f.add(new on(1573731440554221072L));
    f.add(new on(-9088059698134813450L));
    f.add(new on(-6368502568247390366L));
    f.add(new on(-5706116435499483581L));
    f.add(new on(-4192400472959092613L));
    f.add(new on(535098016214332362L));
    f.add(new on(-8403813522400596966L));
    f.add(new on(-5407960936773724314L));
    f.add(new on(8808768475509860323L));
    f.add(new on(-9069002923252134312L));
    f.add(new on(-810122996480351808L));
    f.add(new on(4223092536578796969L));
    f.add(new on(3758548621814949994L));
    f.add(new on(4507950053031819226L));
    e = (int[])e.clone();
    f.add(new on(-587767757384335248L));
    f.add(new on(2055969602532139942L));
    f.add(new on(-2670856816418175355L));
    f.add(new on(3354188204357294317L));
    f.add(new on(5371427579900094632L));
    f.add(new on(-9055810744299364439L));
    f.add(new on(417772245417532711L));
    f.add(new on(6543823847377905547L));
    f.add(new on(-953614525781170448L));
    f.add(new on(-4318493653162540383L));
    f.add(new on(1395242680582923764L));
    f.add(new on(-15990136499822818L));
    f.add(new on(9187393199812466452L));
    f.add(new on(-7183341249765606010L));
    f.add(new on(2876811145216236173L));
    f.add(new on(-6227690541442188494L));
    f.add(new on(4371420755790004732L));
    f.add(new on(5862138870979521267L));
    f.add(new on(-9181150343475638521L));
    f.add(new on(9195044734421239239L));
    f.add(new on(4586500131965879941L));
    f.add(new on(3242790241476209401L));
    f.add(new on(-878960934645165388L));
    f.add(new on(-588400272143056023L));
    f.add(new on(-4110272680624496809L));
    f.add(new on(7680327985135451355L));
    f.add(new on(4872811837515175095L));
    f.add(new on(2149863637011596979L));
    f.add(new on(-459092385109569282L));
    f.add(new on(-3348337336859991516L));
    f.add(new on(690384660554506460L));
    f.add(new on(6909834306432307150L));
    f.add(new on(-1303191993943813266L));
    f.add(new on(-6992730994544043698L));
    f.add(new on(-943093490344120952L));
    f.add(new on(-8399480405030012966L));
    f.add(new on(-7370034982806938922L));
    f.add(new on(3048774301955196661L));
    f.add(new on(-1588155370782078942L));
    f.add(new on(6700674576025226222L));
    f.add(new on(-9220380073832288263L));
    f.add(new on(-7531746712514122084L));
    f.add(new on(-7042326949829898435L));
    f.add(new on(-1646112186964365985L));
    f.add(new on(-112250402405775159L));
    f.add(new on(4179601227440707310L));
    f.add(new on(-3006745806625128295L));
    f.add(new on(2712236331771334515L));
    f.add(new on(-420249754142007134L));
    f.add(new on(-4915726000884883422L));
    f.add(new on(1330507485325727502L));
    f.add(new on(-4292118609130465806L));
    f.add(new on(-3383965367864795869L));
    f.add(new on(-1789435725100727732L));
    f.add(new on(5510982693673721298L));
    f.add(new on(-6953539139487911408L));
    f.add(new on(8607122824782823919L));
    f.add(new on(-2538353164816421950L));
    f.add(new on(1472175968617482485L));
    f.add(new on(-5797024470346162136L));
    f.add(new on(5152739590144420794L));
    f.add(new on(1022282356865942391L));
    f.add(new on(8202454660448907869L));
    f.add(new on(5534631103762754611L));
    f.add(new on(5628018837436372831L));
    f.add(new on(1188283285356480041L));
    f.add(new on(-9084137275632100863L));
    f.add(new on(-4638336636352564988L));
    f.add(new on(3414529101159597232L));
    f.add(new on(6391694498791780013L));
    f.add(new on(1485682402756619751L));
    f.add(new on(-1692450683590405064L));
    f.add(new on(3057795390493492606L));
    f.add(new on(9175975967246451763L));
    f.add(new on(2432627460787541463L));
    f.add(new on(1043042661141038541L));
    f.add(new on(-7396510966068335945L));
    f.add(new on(1928508306410168014L));
    f.add(new on(4631258320203525661L));
    f.add(new on(4240101055999985757L));
    f.add(new on(-3691398061843660207L));
    f.add(new on(-242027768811466014L));
    f.add(new on(-4346597241546921062L));
    f.add(new on(4168319810735334782L));
    f.add(new on(-2117685645891470053L));
    f.add(new on(1368589796373558673L));
    f.add(new on(7766837589313552565L));
    f.add(new on(6179207750137907390L));
    f.add(new on(-7156777133204637895L));
    f.add(new on(6507151785625674674L));
    f.add(new on(8563306003824981437L));
    f.add(new on(-5254143402814135438L));
    f.add(new on(-1658369772776341277L));
    f.add(new on(-1384319885497510650L));
    f.add(new on(6359955211844861792L));
    f.add(new on(-613934774704485229L));
    f.add(new on(-4089297796007517238L));
    f.add(new on(4409684167479431615L));
    f.add(new on(1333641962094983290L));
    f.add(new on(6258521868907910790L));
    f.add(new on(-579106578451913758L));
    f.add(new on(3935360795737694290L));
    f.add(new on(-7517465269673127835L));
    f.add(new on(4747842091425065005L));
    f.add(new on(-3906765722157045665L));
    f.add(new on(1327933222661395525L));
    f.add(new on(7909117195831129799L));
    f.add(new on(-4352126224204089846L));
    f.add(new on(-2099569634835637158L));
    f.add(new on(461290119534767912L));
    f.add(new on(-3252759690091218594L));
    f.add(new on(433938027405141068L));
    f.add(new on(-5884295586880532694L));
    f.add(new on(7260365583180004736L));
    f.add(new on(-8743989117743328457L));
    f.add(new on(-1856365820965783416L));
    f.add(new on(7492045973124400510L));
    f.add(new on(-8599389934196033434L));
    f.add(new on(-1849452465269830792L));
    f.add(new on(-9032426438082893819L));
    f.add(new on(-8245245925431093163L));
    f.add(new on(1414615140669414479L));
    f.add(new on(-3821949264396035545L));
    f.add(new on(-8633697461146512005L));
    f.add(new on(1513738115784946024L));
    f.add(new on(5743920591353944889L));
    f.add(new on(6024067016018037785L));
    f.add(new on(-3432745323401420766L));
    f.add(new on(3967277349658307071L));
    f.add(new on(5419719232524892550L));
    f.add(new on(3486561945818864193L));
    f.add(new on(1995135451432917060L));
    f.add(new on(8684890599774045465L));
    f.add(new on(-621644236220903861L));
    f.add(new on(2082348040640107251L));
    f.add(new on(4003565241637176164L));
    f.add(new on(3128310521062283762L));
    f.add(new on(-4989322991427429173L));
    f.add(new on(482505801255043009L));
    f.add(new on(-6719044653905802402L));
    f.add(new on(-4877797267015171713L));
    f.add(new on(5810788700928193807L));
    f.add(new on(6963410238043907283L));
    f.add(new on(6283762020344420681L));
    f.add(new on(6911710945235790445L));
    f.add(new on(-7813770953465881563L));
    f.add(new on(-2957429366603241438L));
    f.add(new on(-8314408033074877787L));
    f.add(new on(274074011080264944L));
    f.add(new on(4710388906323210999L));
    f.add(new on(-7178249346519555959L));
    f.add(new on(-6951182161356582557L));
    f.add(new on(6242875033705494520L));
    f.add(new on(-2107986368652266797L));
    f.add(new on(-8744926041216092807L));
    f.add(new on(2197357180034900873L));
    f.add(new on(4558263760105438446L));
    f.add(new on(4484900847359755701L));
    f.add(new on(877380403861047370L));
    f.add(new on(-2181257714299073089L));
    f.add(new on(5854464932623442204L));
    f.add(new on(-8597836539561011599L));
    f.add(new on(-6322524156085413792L));
    f.add(new on(7844941248759633618L));
    f.add(new on(-525452030176907190L));
    f.add(new on(-5978087210539445840L));
    f.add(new on(4700181279309297797L));
    f.add(new on(8637816293730470266L));
    f.add(new on(-1897799279307465693L));
    f.add(new on(5872479530215908206L));
    f.add(new on(2921640674735506871L));
    f.add(new on(-2250440466553974352L));
    f.add(new on(-3491027480665815461L));
    f.add(new on(2942922999372487400L));
    f.add(new on(-7429324971442521180L));
    f.add(new on(8438753009086403361L));
    f.add(new on(2327947618789335544L));
    f.add(new on(-5324700653150179662L));
    f.add(new on(8551510863915666694L));
    e = (int[])e.clone();
    f.add(new on(1345785940420740677L));
    f.add(new on(-8278502613187646807L));
    f.add(new on(-2139266976394277351L));
    f.add(new on(-1251243135618026341L));
    f.add(new on(-7705499337482978403L));
    f.add(new on(2574860369123058121L));
    f.add(new on(-361108633613020690L));
    f.add(new on(-7293876790769747200L));
    f.add(new on(-8533334237507750685L));
    f.add(new on(2058476575766193498L));
    f.add(new on(-5267749711397668159L));
    f.add(new on(9200001305235868622L));
    f.add(new on(-8652646199977241146L));
    f.add(new on(-9078764182846929631L));
    f.add(new on(-5076431233813203186L));
    f.add(new on(-7630957028190693896L));
    f.add(new on(4736995238496368719L));
    f.add(new on(7033035211560411303L));
    f.add(new on(7037971162967449729L));
    f.add(new on(5968260352866215514L));
    f.add(new on(5709207035123420537L));
    f.add(new on(-1488175311678527497L));
    f.add(new on(8796379555251208297L));
    f.add(new on(3378849267604072795L));
    f.add(new on(-2720895055771998584L));
    f.add(new on(8179955203897420459L));
    f.add(new on(-7469219473131637905L));
    f.add(new on(6316954504485035852L));
    f.add(new on(2503558806451749488L));
    f.add(new on(8753882678811606334L));
    f.add(new on(-2969437020063401459L));
    f.add(new on(-937362533732360294L));
    f.add(new on(-8343199027838451102L));
    f.add(new on(8409659594075865421L));
    f.add(new on(-6848772974434916396L));
    f.add(new on(-3314432447935676000L));
    f.add(new on(-6602952854309841327L));
    f.add(new on(-3735146169665159874L));
    f.add(new on(5483692909619327569L));
    f.add(new on(-7593622360376452178L));
    f.add(new on(-5373659839532973037L));
    f.add(new on(8147580591346347573L));
    f.add(new on(6053455819276751601L));
    f.add(new on(5072623543140455266L));
    f.add(new on(3164800923385325858L));
    f.add(new on(8223794952561012399L));
    f.add(new on(5314816489818445029L));
    f.add(new on(6871934546195755191L));
    f.add(new on(7123777542115259334L));
    f.add(new on(6156438841921991446L));
    f.add(new on(-7167729470867533274L));
    f.add(new on(-3828934697030581151L));
    f.add(new on(-4643420924352502012L));
    f.add(new on(-6015831407886115276L));
    f.add(new on(3336632836669088895L));
    f.add(new on(4837051790141267094L));
    f.add(new on(536070829656880728L));
    f.add(new on(-3738562183341840303L));
    f.add(new on(9221340849025651267L));
    f.add(new on(-4851831890612981243L));
    f.add(new on(3801588042308618766L));
    f.add(new on(-471747057501885484L));
    f.add(new on(7948287556713572652L));
    f.add(new on(-8024362155349153816L));
    f.add(new on(-8333364377005514982L));
    f.add(new on(8881314184947174830L));
    f.add(new on(-4945295430148393157L));
    f.add(new on(4690257272233559745L));
    f.add(new on(-7943249898618701667L));
    f.add(new on(342908309909818732L));
    f.add(new on(8696980068041873103L));
    f.add(new on(5497418227737206137L));
    f.add(new on(-7490997303229200581L));
    f.add(new on(3809290670798463024L));
    f.add(new on(-7915096663624371952L));
    f.add(new on(4125456391934189309L));
    f.add(new on(-1371758357876474732L));
    f.add(new on(-3998738840173496247L));
    f.add(new on(2047471125103448764L));
    f.add(new on(811604264025971852L));
    f.add(new on(14804108009017838L));
    f.add(new on(-2939257184908236969L));
    f.add(new on(8181718230191128241L));
    f.add(new on(-336920562221980594L));
    f.add(new on(-7000959513374543174L));
    f.add(new on(-8016823648729666702L));
    f.add(new on(-7887737243324830296L));
    f.add(new on(-7280807285011185676L));
    f.add(new on(-8442445843510230951L));
    f.add(new on(2119270362404250827L));
    f.add(new on(847649282306710984L));
    f.add(new on(6288003414054653469L));
    f.add(new on(-3859434310117323735L));
    f.add(new on(-4421711796980635695L));
    f.add(new on(-8708090649309748114L));
    f.add(new on(4492425479753968392L));
    f.add(new on(3121544936988746993L));
    f.add(new on(-3568992035815871877L));
    f.add(new on(-7953096953142883370L));
    f.add(new on(4903909846830543384L));
    f.add(new on(-5562674676994662413L));
    f.add(new on(7743127885047219694L));
    f.add(new on(-3946941217447919622L));
    f.add(new on(8034554952975489477L));
    f.add(new on(-5562842657418440509L));
    f.add(new on(6543528464784247182L));
    f.add(new on(517471627182857086L));
    f.add(new on(-7491943568353371629L));
    f.add(new on(2698453163975330968L));
    f.add(new on(-5176375674084636923L));
    f.add(new on(1340183193622560754L));
    f.add(new on(-1046751048096608196L));
    f.add(new on(4280611993843258737L));
    f.add(new on(2941058962568325108L));
    f.add(new on(-602926129365222629L));
    f.add(new on(1907719453121436343L));
    f.add(new on(-1104639201421230344L));
    f.add(new on(4265946812224196505L));
    f.add(new on(-4202120436907590578L));
    f.add(new on(902945746978220636L));
    f.add(new on(-5370190223488457277L));
    f.add(new on(-2013983618094269621L));
    f.add(new on(-8952768631269711462L));
    f.add(new on(-1796874011948614166L));
    f.add(new on(-5352183173188214659L));
    f.add(new on(-2274106506363878372L));
    f.add(new on(6180290731253768365L));
    f.add(new on(1333326257534011546L));
    f.add(new on(4857669327636185460L));
    f.add(new on(4822903495127131874L));
    f.add(new on(-6180559966866063125L));
    f.add(new on(-371197836723768784L));
    f.add(new on(-430541957577725912L));
    f.add(new on(6767671013769739603L));
    f.add(new on(-1410232872868357181L));
    f.add(new on(-1627184623427852810L));
    f.add(new on(5053260846527243157L));
    f.add(new on(7787679849801778516L));
    f.add(new on(4993018898051916629L));
    f.add(new on(-2826038805478613850L));
    f.add(new on(-2264614068420156584L));
    f.add(new on(-2263673376908880268L));
    f.add(new on(4128874167332301057L));
    f.add(new on(-4436726981587946353L));
    f.add(new on(-5842553675355407808L));
    f.add(new on(1930329118138594107L));
    f.add(new on(-3620610939787308365L));
    f.add(new on(-7944529484200852275L));
    f.add(new on(-8535753791624387128L));
    f.add(new on(-5781666783159703569L));
    f.add(new on(-8303343506252425110L));
    f.add(new on(959444523459228988L));
    f.add(new on(-6471918774155899956L));
    f.add(new on(-6067780276627453006L));
    f.add(new on(-5992609379890143986L));
    f.add(new on(-6183468384422105705L));
    f.add(new on(-1014201478602273486L));
    f.add(new on(-3375500172400028988L));
    f.add(new on(-5886222049057568342L));
    f.add(new on(-8371952953299170525L));
    f.add(new on(3486846719322217281L));
    f.add(new on(-2744695541876555437L));
    f.add(new on(6872222169033131499L));
    f.add(new on(5766600892820056776L));
    f.add(new on(7399908391896049464L));
    f.add(new on(1754949190897728999L));
    f.add(new on(-1693963506380264033L));
    f.add(new on(5118354443140364644L));
    f.add(new on(-3502276712653287575L));
    f.add(new on(6915986463436832990L));
    f.add(new on(-6555089144540640657L));
    f.add(new on(-7171480633610196715L));
    f.add(new on(7060515508714448198L));
    f.add(new on(-5606637635201209284L));
    e = (int[])e.clone();
    f.add(new on(-1335847455947292333L));
    f.add(new on(1686261030669679597L));
    f.add(new on(-697954990447340213L));
    f.add(new on(4870556914688293869L));
    f.add(new on(704000041439497006L));
    f.add(new on(8174791254690425902L));
    f.add(new on(-4292378102589427828L));
    f.add(new on(-5381760658053905018L));
    f.add(new on(-7812010679089365602L));
    f.add(new on(1491074255500948519L));
    f.add(new on(1458560910368160985L));
    f.add(new on(-6946607975969971301L));
    f.add(new on(6531310716858269158L));
    f.add(new on(82987689790873928L));
    f.add(new on(5828203290676847577L));
    f.add(new on(8155676903729925674L));
    f.add(new on(-102812544288444390L));
    f.add(new on(-2722437803693023521L));
    f.add(new on(-5683529411182629263L));
    f.add(new on(3509119113861965699L));
    f.add(new on(2474798385151865935L));
    f.add(new on(4502034113960532867L));
    f.add(new on(-6199229701048864748L));
    f.add(new on(1344031626595321666L));
    f.add(new on(-3516394646226873011L));
    f.add(new on(8579485107319716702L));
    f.add(new on(-1741563190366617058L));
    f.add(new on(-4971942156014184545L));
    f.add(new on(-5749639711804459984L));
    f.add(new on(-1919732999054130019L));
    f.add(new on(7361394313805066731L));
    f.add(new on(-2451706768628226889L));
    f.add(new on(-8362747092190645760L));
    f.add(new on(-73604177591957726L));
    f.add(new on(-3251312583727236668L));
    f.add(new on(-3015163271134886226L));
    f.add(new on(6594933547389425592L));
    f.add(new on(5810793098966334735L));
    f.add(new on(-8986623431510390527L));
    f.add(new on(8201152696309419588L));
    f.add(new on(317430867836891539L));
    f.add(new on(7735268590627487970L));
    f.add(new on(-2376805322550119947L));
    f.add(new on(4601865297407400072L));
    f.add(new on(-5596979448870959525L));
    f.add(new on(-7400960803512459690L));
    f.add(new on(-2688437614554719098L));
    f.add(new on(8047936979279787120L));
    f.add(new on(2481026637765130656L));
    f.add(new on(-4473748216930273138L));
    f.add(new on(2270883239214334927L));
    f.add(new on(7102458977002443711L));
    f.add(new on(5171373375272998583L));
    f.add(new on(4269245410562718192L));
    f.add(new on(8841177523183551632L));
    f.add(new on(7360083762497480463L));
    f.add(new on(7605226735091778678L));
    f.add(new on(1602054540350152974L));
    f.add(new on(3796003001029195329L));
    f.add(new on(8002787506884804780L));
    f.add(new on(-1890986886447719380L));
    f.add(new on(2946458219211571160L));
    f.add(new on(1058654142472784339L));
    f.add(new on(-8786521437253034968L));
    f.add(new on(6136365652352011515L));
    f.add(new on(-3034242693200920337L));
    f.add(new on(-4296709688366144134L));
    f.add(new on(7823519281950623393L));
    f.add(new on(7141853241888228168L));
    f.add(new on(4063216448105236358L));
    f.add(new on(132562959675107713L));
    f.add(new on(6586314225530863889L));
    f.add(new on(-6125418337824192712L));
    f.add(new on(-1860350935578186876L));
    f.add(new on(1113956977232321520L));
    f.add(new on(-5444345842655934334L));
    f.add(new on(-3061856858880071452L));
    f.add(new on(-7980035446376601069L));
    f.add(new on(-7890256940098702553L));
    f.add(new on(-730056269693747641L));
    f.add(new on(3859770855680497566L));
    f.add(new on(-3484013013260150617L));
    f.add(new on(169466279828826095L));
    f.add(new on(1185525008708817937L));
    f.add(new on(-8155322911234544489L));
    f.add(new on(6862374806882643947L));
    f.add(new on(5015897644038505844L));
    f.add(new on(5395186481678577180L));
    f.add(new on(-777388109959048541L));
    f.add(new on(-4107707641423736472L));
    f.add(new on(4904750487318631802L));
    f.add(new on(6799011920098216933L));
    f.add(new on(-8947433346230932375L));
    f.add(new on(-5292897556456955316L));
    f.add(new on(1485797639365574013L));
    f.add(new on(5825667160651845748L));
    f.add(new on(-7778081164652917630L));
    f.add(new on(-9158539598873204253L));
    f.add(new on(7507743668064851563L));
    f.add(new on(6290474747892416113L));
    f.add(new on(-3992083244610392412L));
    f.add(new on(-6539630357212282260L));
    f.add(new on(5675837763076373507L));
    f.add(new on(8350553691978476924L));
    f.add(new on(1710134135672641796L));
    f.add(new on(-8818987679114839948L));
    f.add(new on(-8592827093180696287L));
    f.add(new on(3498184446922721997L));
    f.add(new on(-3369768538186474555L));
    f.add(new on(3083162899648302613L));
    f.add(new on(-9144161329931193140L));
    f.add(new on(-6865472670316981688L));
    f.add(new on(6618141360675739042L));
    f.add(new on(1899183539086433755L));
    f.add(new on(6525302510860370395L));
    f.add(new on(-1627957300715326423L));
    f.add(new on(8871647740848172862L));
    f.add(new on(-3934914500838918269L));
    f.add(new on(-659929966066655004L));
    f.add(new on(6805661504482932443L));
    f.add(new on(-7339328006351296332L));
    f.add(new on(-4412946423371878710L));
    f.add(new on(-741742903347384729L));
    f.add(new on(5080870882183747174L));
    f.add(new on(7122210993716081961L));
    f.add(new on(2211760129981934845L));
    f.add(new on(-4675508529909813066L));
    f.add(new on(447286359305440165L));
    f.add(new on(-3142600349263386299L));
    f.add(new on(2718750671669743408L));
    f.add(new on(7754926019170350624L));
    e = new int[] { 
        -42, -32, -30, -1, 1, -55, -38, -48, -48, -52, 
        -53, -42, -33, -8, -43, -35, -31, -20, -30, -39, 
        -15, 8, -9, -18, -15, -5, -20, -35, -1, 1, 
        5, 9, 30, 32, -15, 15, -7, 20, -13, 15, 
        -12, 18, 42, 7, 38, 33, 20, 31, 30, 15, 
        35, 13, 12, 42, -5, 48, 48, 43, 39, 5, 
        55, 52, 35, 53 };
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\on.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */