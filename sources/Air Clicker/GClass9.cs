using System;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000D5 RID: 213
public class GClass9 : TextBox
{
	// Token: 0x14000026 RID: 38
	// (add) Token: 0x06000944 RID: 2372 RVA: 0x0002B830 File Offset: 0x00029A30
	// (remove) Token: 0x06000945 RID: 2373 RVA: 0x0002B868 File Offset: 0x00029A68
	public event GClass9.GDelegate21 Event_0
	{
		[CompilerGenerated]
		add
		{
			GClass9.GDelegate21 gdelegate = this.gdelegate21_0;
			GClass9.GDelegate21 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass9.GDelegate21 value2 = (GClass9.GDelegate21)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass9.GDelegate21>(ref this.gdelegate21_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass9.GDelegate21 gdelegate = this.gdelegate21_0;
			GClass9.GDelegate21 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass9.GDelegate21 value2 = (GClass9.GDelegate21)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass9.GDelegate21>(ref this.gdelegate21_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x17000290 RID: 656
	// (get) Token: 0x06000946 RID: 2374 RVA: 0x0002B8A4 File Offset: 0x00029AA4
	// (set) Token: 0x06000947 RID: 2375 RVA: 0x0002B8BC File Offset: 0x00029ABC
	public GClass9.GEnum13 GEnum13_0
	{
		get
		{
			return this.genum13_0;
		}
		set
		{
			this.genum13_0 = value;
			GClass9.GDelegate21 gdelegate = this.gdelegate21_0;
			if (gdelegate != null)
			{
				gdelegate();
			}
		}
	}

	// Token: 0x06000948 RID: 2376 RVA: 0x0002B8E0 File Offset: 0x00029AE0
	public GClass9()
	{
		this.Event_0 += this.method_0;
		base.BorderStyle = BorderStyle.FixedSingle;
		this.Font = new Font("Segoe UI Semilight", 9.75f);
		this.BackColor = Color.FromArgb(35, 35, 35);
		this.ForeColor = Color.White;
		this.GEnum13_0 = GClass9.GEnum13.Dark;
	}

	// Token: 0x06000949 RID: 2377 RVA: 0x0002B944 File Offset: 0x00029B44
	protected void method_0()
	{
		base.Invalidate();
		GClass9.GEnum13 genum = this.GEnum13_0;
		if (genum != GClass9.GEnum13.Light)
		{
			if (genum == GClass9.GEnum13.Dark)
			{
				this.BackColor = Color.FromArgb(35, 35, 35);
				this.ForeColor = Color.White;
			}
		}
		else
		{
			this.BackColor = Color.White;
			this.ForeColor = Color.Black;
		}
	}

	// Token: 0x0400047B RID: 1147
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private GClass9.GDelegate21 gdelegate21_0;

	// Token: 0x0400047C RID: 1148
	private GClass9.GEnum13 genum13_0;

	// Token: 0x020000D6 RID: 214
	public enum GEnum13
	{
		// Token: 0x0400047E RID: 1150
		Light,
		// Token: 0x0400047F RID: 1151
		Dark
	}

	// Token: 0x020000D7 RID: 215
	// (Invoke) Token: 0x0600094D RID: 2381
	public delegate void GDelegate21();
}
