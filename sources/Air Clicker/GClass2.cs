using System;
using System.Diagnostics;
using System.Drawing;
using System.Reflection;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Threading;

// Token: 0x02000079 RID: 121
public class GClass2
{
	// Token: 0x06000582 RID: 1410
	[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern int SetWindowsHookExA(int int_14, GClass2.Delegate14 delegate14_1, int int_15, int int_16);

	// Token: 0x06000583 RID: 1411
	[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern int CallNextHookEx(int int_14, int int_15, int int_16, GClass2.Struct1 struct1_0);

	// Token: 0x06000584 RID: 1412
	[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern int UnhookWindowsHookEx(int int_14);

	// Token: 0x1400000D RID: 13
	// (add) Token: 0x06000585 RID: 1413 RVA: 0x0001C960 File Offset: 0x0001AB60
	// (remove) Token: 0x06000586 RID: 1414 RVA: 0x0001C99C File Offset: 0x0001AB9C
	public event GClass2.GDelegate0 Event_0
	{
		[CompilerGenerated]
		add
		{
			GClass2.GDelegate0 gdelegate = this.gdelegate0_0;
			GClass2.GDelegate0 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate0 value2 = (GClass2.GDelegate0)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate0>(ref this.gdelegate0_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass2.GDelegate0 gdelegate = this.gdelegate0_0;
			GClass2.GDelegate0 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate0 value2 = (GClass2.GDelegate0)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate0>(ref this.gdelegate0_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x1400000E RID: 14
	// (add) Token: 0x06000587 RID: 1415 RVA: 0x0001C9D4 File Offset: 0x0001ABD4
	// (remove) Token: 0x06000588 RID: 1416 RVA: 0x0001CA0C File Offset: 0x0001AC0C
	public event GClass2.GDelegate1 Event_1
	{
		[CompilerGenerated]
		add
		{
			GClass2.GDelegate1 gdelegate = this.gdelegate1_0;
			GClass2.GDelegate1 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate1 value2 = (GClass2.GDelegate1)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate1>(ref this.gdelegate1_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass2.GDelegate1 gdelegate = this.gdelegate1_0;
			GClass2.GDelegate1 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate1 value2 = (GClass2.GDelegate1)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate1>(ref this.gdelegate1_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x1400000F RID: 15
	// (add) Token: 0x06000589 RID: 1417 RVA: 0x0001CA48 File Offset: 0x0001AC48
	// (remove) Token: 0x0600058A RID: 1418 RVA: 0x0001CA80 File Offset: 0x0001AC80
	public event GClass2.GDelegate2 Event_2
	{
		[CompilerGenerated]
		add
		{
			GClass2.GDelegate2 gdelegate = this.gdelegate2_0;
			GClass2.GDelegate2 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate2 value2 = (GClass2.GDelegate2)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate2>(ref this.gdelegate2_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass2.GDelegate2 gdelegate = this.gdelegate2_0;
			GClass2.GDelegate2 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate2 value2 = (GClass2.GDelegate2)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate2>(ref this.gdelegate2_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x14000010 RID: 16
	// (add) Token: 0x0600058B RID: 1419 RVA: 0x0001CAB8 File Offset: 0x0001ACB8
	// (remove) Token: 0x0600058C RID: 1420 RVA: 0x0001CAF0 File Offset: 0x0001ACF0
	public event GClass2.GDelegate3 Event_3
	{
		[CompilerGenerated]
		add
		{
			GClass2.GDelegate3 gdelegate = this.gdelegate3_0;
			GClass2.GDelegate3 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate3 value2 = (GClass2.GDelegate3)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate3>(ref this.gdelegate3_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass2.GDelegate3 gdelegate = this.gdelegate3_0;
			GClass2.GDelegate3 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate3 value2 = (GClass2.GDelegate3)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate3>(ref this.gdelegate3_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x14000011 RID: 17
	// (add) Token: 0x0600058D RID: 1421 RVA: 0x0001CB28 File Offset: 0x0001AD28
	// (remove) Token: 0x0600058E RID: 1422 RVA: 0x0001CB60 File Offset: 0x0001AD60
	public event GClass2.GDelegate4 Event_4
	{
		[CompilerGenerated]
		add
		{
			GClass2.GDelegate4 gdelegate = this.gdelegate4_0;
			GClass2.GDelegate4 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate4 value2 = (GClass2.GDelegate4)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate4>(ref this.gdelegate4_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass2.GDelegate4 gdelegate = this.gdelegate4_0;
			GClass2.GDelegate4 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate4 value2 = (GClass2.GDelegate4)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate4>(ref this.gdelegate4_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x14000012 RID: 18
	// (add) Token: 0x0600058F RID: 1423 RVA: 0x0001CB98 File Offset: 0x0001AD98
	// (remove) Token: 0x06000590 RID: 1424 RVA: 0x0001CBD0 File Offset: 0x0001ADD0
	public event GClass2.GDelegate5 Event_5
	{
		[CompilerGenerated]
		add
		{
			GClass2.GDelegate5 gdelegate = this.gdelegate5_0;
			GClass2.GDelegate5 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate5 value2 = (GClass2.GDelegate5)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate5>(ref this.gdelegate5_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass2.GDelegate5 gdelegate = this.gdelegate5_0;
			GClass2.GDelegate5 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate5 value2 = (GClass2.GDelegate5)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate5>(ref this.gdelegate5_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x14000013 RID: 19
	// (add) Token: 0x06000591 RID: 1425 RVA: 0x0001CC08 File Offset: 0x0001AE08
	// (remove) Token: 0x06000592 RID: 1426 RVA: 0x0001CC40 File Offset: 0x0001AE40
	public event GClass2.GDelegate6 Event_6
	{
		[CompilerGenerated]
		add
		{
			GClass2.GDelegate6 gdelegate = this.gdelegate6_0;
			GClass2.GDelegate6 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate6 value2 = (GClass2.GDelegate6)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate6>(ref this.gdelegate6_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass2.GDelegate6 gdelegate = this.gdelegate6_0;
			GClass2.GDelegate6 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate6 value2 = (GClass2.GDelegate6)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate6>(ref this.gdelegate6_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x14000014 RID: 20
	// (add) Token: 0x06000593 RID: 1427 RVA: 0x0001CC78 File Offset: 0x0001AE78
	// (remove) Token: 0x06000594 RID: 1428 RVA: 0x0001CCB0 File Offset: 0x0001AEB0
	public event GClass2.GDelegate7 Event_7
	{
		[CompilerGenerated]
		add
		{
			GClass2.GDelegate7 gdelegate = this.gdelegate7_0;
			GClass2.GDelegate7 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate7 value2 = (GClass2.GDelegate7)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate7>(ref this.gdelegate7_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass2.GDelegate7 gdelegate = this.gdelegate7_0;
			GClass2.GDelegate7 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate7 value2 = (GClass2.GDelegate7)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate7>(ref this.gdelegate7_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x14000015 RID: 21
	// (add) Token: 0x06000595 RID: 1429 RVA: 0x0001CCEC File Offset: 0x0001AEEC
	// (remove) Token: 0x06000596 RID: 1430 RVA: 0x0001CD28 File Offset: 0x0001AF28
	public event GClass2.GDelegate8 Event_8
	{
		[CompilerGenerated]
		add
		{
			GClass2.GDelegate8 gdelegate = this.gdelegate8_0;
			GClass2.GDelegate8 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate8 value2 = (GClass2.GDelegate8)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate8>(ref this.gdelegate8_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass2.GDelegate8 gdelegate = this.gdelegate8_0;
			GClass2.GDelegate8 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate8 value2 = (GClass2.GDelegate8)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate8>(ref this.gdelegate8_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x14000016 RID: 22
	// (add) Token: 0x06000597 RID: 1431 RVA: 0x0001CD64 File Offset: 0x0001AF64
	// (remove) Token: 0x06000598 RID: 1432 RVA: 0x0001CD9C File Offset: 0x0001AF9C
	public event GClass2.GDelegate9 Event_9
	{
		[CompilerGenerated]
		add
		{
			GClass2.GDelegate9 gdelegate = this.gdelegate9_0;
			GClass2.GDelegate9 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate9 value2 = (GClass2.GDelegate9)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate9>(ref this.gdelegate9_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass2.GDelegate9 gdelegate = this.gdelegate9_0;
			GClass2.GDelegate9 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate9 value2 = (GClass2.GDelegate9)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate9>(ref this.gdelegate9_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x14000017 RID: 23
	// (add) Token: 0x06000599 RID: 1433 RVA: 0x0001CDD8 File Offset: 0x0001AFD8
	// (remove) Token: 0x0600059A RID: 1434 RVA: 0x0001CE14 File Offset: 0x0001B014
	public event GClass2.GDelegate10 Event_10
	{
		[CompilerGenerated]
		add
		{
			GClass2.GDelegate10 gdelegate = this.gdelegate10_0;
			GClass2.GDelegate10 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate10 value2 = (GClass2.GDelegate10)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate10>(ref this.gdelegate10_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass2.GDelegate10 gdelegate = this.gdelegate10_0;
			GClass2.GDelegate10 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass2.GDelegate10 value2 = (GClass2.GDelegate10)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass2.GDelegate10>(ref this.gdelegate10_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x0600059C RID: 1436 RVA: 0x0001CE54 File Offset: 0x0001B054
	private int method_0(int int_14, int int_15, ref GClass2.Struct1 struct1_0)
	{
		if (int_14 == 0)
		{
			switch (int_15)
			{
			case 512:
			{
				GClass2.GDelegate0 gdelegate = this.gdelegate0_0;
				if (gdelegate != null)
				{
					gdelegate(struct1_0.point_0);
				}
				break;
			}
			case 513:
			{
				GClass2.GDelegate1 gdelegate2 = this.gdelegate1_0;
				if (gdelegate2 != null)
				{
					gdelegate2(struct1_0.point_0);
				}
				break;
			}
			case 514:
			{
				GClass2.GDelegate2 gdelegate3 = this.gdelegate2_0;
				if (gdelegate3 != null)
				{
					gdelegate3(struct1_0.point_0);
				}
				break;
			}
			case 515:
			{
				GClass2.GDelegate3 gdelegate4 = this.gdelegate3_0;
				if (gdelegate4 != null)
				{
					gdelegate4(struct1_0.point_0);
				}
				break;
			}
			case 516:
			{
				GClass2.GDelegate4 gdelegate5 = this.gdelegate4_0;
				if (gdelegate5 != null)
				{
					gdelegate5(struct1_0.point_0);
				}
				break;
			}
			case 517:
			{
				GClass2.GDelegate5 gdelegate6 = this.gdelegate5_0;
				if (gdelegate6 != null)
				{
					gdelegate6(struct1_0.point_0);
				}
				break;
			}
			case 518:
			{
				GClass2.GDelegate6 gdelegate7 = this.gdelegate6_0;
				if (gdelegate7 != null)
				{
					gdelegate7(struct1_0.point_0);
				}
				break;
			}
			case 519:
			{
				GClass2.GDelegate7 gdelegate8 = this.gdelegate7_0;
				if (gdelegate8 != null)
				{
					gdelegate8(struct1_0.point_0);
				}
				break;
			}
			case 520:
			{
				GClass2.GDelegate8 gdelegate9 = this.gdelegate8_0;
				if (gdelegate9 != null)
				{
					gdelegate9(struct1_0.point_0);
				}
				break;
			}
			case 521:
			{
				GClass2.GDelegate9 gdelegate10 = this.gdelegate9_0;
				if (gdelegate10 != null)
				{
					gdelegate10(struct1_0.point_0);
				}
				break;
			}
			case 522:
			{
				GClass2.GEnum2 direction;
				if (struct1_0.int_0 >= 0)
				{
					direction = GClass2.GEnum2.WheelUp;
				}
				else
				{
					direction = GClass2.GEnum2.WheelDown;
				}
				GClass2.GDelegate10 gdelegate11 = this.gdelegate10_0;
				if (gdelegate11 != null)
				{
					gdelegate11(struct1_0.point_0, direction);
				}
				break;
			}
			}
		}
		int result;
		return result;
	}

	// Token: 0x0600059D RID: 1437 RVA: 0x0001D008 File Offset: 0x0001B208
	protected virtual void Finalize()
	{
		GClass2.UnhookWindowsHookEx(this.int_13);
		base.Finalize();
	}

	// Token: 0x0600059E RID: 1438 RVA: 0x0001D01C File Offset: 0x0001B21C
	public void method_1()
	{
		this.delegate14_0 = new GClass2.Delegate14(this.method_0);
		this.int_13 = GClass2.SetWindowsHookExA(14, this.delegate14_0, Marshal.GetHINSTANCE(Assembly.GetExecutingAssembly().GetModules()[0]).ToInt32(), 0);
	}

	// Token: 0x0600059F RID: 1439 RVA: 0x0001D068 File Offset: 0x0001B268
	public void method_2()
	{
		GClass2.UnhookWindowsHookEx(this.int_13);
	}

	// Token: 0x040002C8 RID: 712
	private const int int_0 = 0;

	// Token: 0x040002C9 RID: 713
	private const int int_1 = 14;

	// Token: 0x040002CA RID: 714
	private const int int_2 = 512;

	// Token: 0x040002CB RID: 715
	private const int int_3 = 513;

	// Token: 0x040002CC RID: 716
	private const int int_4 = 514;

	// Token: 0x040002CD RID: 717
	private const int int_5 = 515;

	// Token: 0x040002CE RID: 718
	private const int int_6 = 516;

	// Token: 0x040002CF RID: 719
	private const int int_7 = 517;

	// Token: 0x040002D0 RID: 720
	private const int int_8 = 518;

	// Token: 0x040002D1 RID: 721
	private const int int_9 = 519;

	// Token: 0x040002D2 RID: 722
	private const int int_10 = 520;

	// Token: 0x040002D3 RID: 723
	private const int int_11 = 521;

	// Token: 0x040002D4 RID: 724
	private const int int_12 = 522;

	// Token: 0x040002D5 RID: 725
	private int int_13;

	// Token: 0x040002D6 RID: 726
	private GClass2.Delegate14 delegate14_0;

	// Token: 0x040002D7 RID: 727
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private GClass2.GDelegate0 gdelegate0_0;

	// Token: 0x040002D8 RID: 728
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private GClass2.GDelegate1 gdelegate1_0;

	// Token: 0x040002D9 RID: 729
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GClass2.GDelegate2 gdelegate2_0;

	// Token: 0x040002DA RID: 730
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GClass2.GDelegate3 gdelegate3_0;

	// Token: 0x040002DB RID: 731
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GClass2.GDelegate4 gdelegate4_0;

	// Token: 0x040002DC RID: 732
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GClass2.GDelegate5 gdelegate5_0;

	// Token: 0x040002DD RID: 733
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private GClass2.GDelegate6 gdelegate6_0;

	// Token: 0x040002DE RID: 734
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private GClass2.GDelegate7 gdelegate7_0;

	// Token: 0x040002DF RID: 735
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GClass2.GDelegate8 gdelegate8_0;

	// Token: 0x040002E0 RID: 736
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GClass2.GDelegate9 gdelegate9_0;

	// Token: 0x040002E1 RID: 737
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GClass2.GDelegate10 gdelegate10_0;

	// Token: 0x0200007A RID: 122
	// (Invoke) Token: 0x060005A3 RID: 1443
	private delegate int Delegate14(int nCode, int wParam, ref GClass2.Struct1 lParam);

	// Token: 0x0200007B RID: 123
	private struct Struct1
	{
		// Token: 0x040002E2 RID: 738
		public Point point_0;

		// Token: 0x040002E3 RID: 739
		public int int_0;

		// Token: 0x040002E4 RID: 740
		public int int_1;

		// Token: 0x040002E5 RID: 741
		public int int_2;

		// Token: 0x040002E6 RID: 742
		public int int_3;
	}

	// Token: 0x0200007C RID: 124
	public enum GEnum2
	{
		// Token: 0x040002E8 RID: 744
		WheelUp,
		// Token: 0x040002E9 RID: 745
		WheelDown
	}

	// Token: 0x0200007D RID: 125
	// (Invoke) Token: 0x060005A7 RID: 1447
	public delegate void GDelegate0(Point ptLocat);

	// Token: 0x0200007E RID: 126
	// (Invoke) Token: 0x060005AB RID: 1451
	public delegate void GDelegate1(Point ptLocat);

	// Token: 0x0200007F RID: 127
	// (Invoke) Token: 0x060005AF RID: 1455
	public delegate void GDelegate2(Point ptLocat);

	// Token: 0x02000080 RID: 128
	// (Invoke) Token: 0x060005B3 RID: 1459
	public delegate void GDelegate3(Point ptLocat);

	// Token: 0x02000081 RID: 129
	// (Invoke) Token: 0x060005B7 RID: 1463
	public delegate void GDelegate4(Point ptLocat);

	// Token: 0x02000082 RID: 130
	// (Invoke) Token: 0x060005BB RID: 1467
	public delegate void GDelegate5(Point ptLocat);

	// Token: 0x02000083 RID: 131
	// (Invoke) Token: 0x060005BF RID: 1471
	public delegate void GDelegate6(Point ptLocat);

	// Token: 0x02000084 RID: 132
	// (Invoke) Token: 0x060005C3 RID: 1475
	public delegate void GDelegate7(Point ptLocat);

	// Token: 0x02000085 RID: 133
	// (Invoke) Token: 0x060005C7 RID: 1479
	public delegate void GDelegate8(Point ptLocat);

	// Token: 0x02000086 RID: 134
	// (Invoke) Token: 0x060005CB RID: 1483
	public delegate void GDelegate9(Point ptLocat);

	// Token: 0x02000087 RID: 135
	// (Invoke) Token: 0x060005CF RID: 1487
	public delegate void GDelegate10(Point ptLocat, GClass2.GEnum2 Direction);
}
