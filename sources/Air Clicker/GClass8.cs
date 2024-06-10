using System;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000D1 RID: 209
public class GClass8 : Button
{
	// Token: 0x1700028E RID: 654
	// (get) Token: 0x06000933 RID: 2355 RVA: 0x0002B4F8 File Offset: 0x000296F8
	// (set) Token: 0x06000934 RID: 2356 RVA: 0x0002B510 File Offset: 0x00029710
	public GClass8.GEnum12 GEnum12_0
	{
		get
		{
			return this.genum12_0;
		}
		set
		{
			this.genum12_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x06000935 RID: 2357 RVA: 0x0002B520 File Offset: 0x00029720
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.genum11_0 = GClass8.GEnum11.Over;
		base.Invalidate();
	}

	// Token: 0x06000936 RID: 2358 RVA: 0x0002B538 File Offset: 0x00029738
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.genum11_0 = GClass8.GEnum11.None;
		base.Invalidate();
	}

	// Token: 0x06000937 RID: 2359 RVA: 0x0002B550 File Offset: 0x00029750
	protected virtual void OnMouseDown(MouseEventArgs mevent)
	{
		base.OnMouseDown(mevent);
		this.genum11_0 = GClass8.GEnum11.Down;
		base.Invalidate();
	}

	// Token: 0x06000938 RID: 2360 RVA: 0x0002B568 File Offset: 0x00029768
	protected virtual void OnMouseUp(MouseEventArgs mevent)
	{
		base.OnMouseUp(mevent);
		this.genum11_0 = GClass8.GEnum11.Over;
		base.Invalidate();
	}

	// Token: 0x1700028F RID: 655
	// (get) Token: 0x06000939 RID: 2361 RVA: 0x0002B580 File Offset: 0x00029780
	// (set) Token: 0x0600093A RID: 2362 RVA: 0x0002B598 File Offset: 0x00029798
	public Color Color_0
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
			this.method_0();
		}
	}

	// Token: 0x14000025 RID: 37
	// (add) Token: 0x0600093B RID: 2363 RVA: 0x0002B5A8 File Offset: 0x000297A8
	// (remove) Token: 0x0600093C RID: 2364 RVA: 0x0002B5E4 File Offset: 0x000297E4
	public event GClass8.GDelegate20 Event_0
	{
		[CompilerGenerated]
		add
		{
			GClass8.GDelegate20 gdelegate = this.gdelegate20_0;
			GClass8.GDelegate20 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass8.GDelegate20 value2 = (GClass8.GDelegate20)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass8.GDelegate20>(ref this.gdelegate20_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GClass8.GDelegate20 gdelegate = this.gdelegate20_0;
			GClass8.GDelegate20 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GClass8.GDelegate20 value2 = (GClass8.GDelegate20)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GClass8.GDelegate20>(ref this.gdelegate20_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x0600093D RID: 2365 RVA: 0x0002B61C File Offset: 0x0002981C
	public GClass8()
	{
		this.Event_0 += this.method_0;
		this.genum11_0 = GClass8.GEnum11.None;
		this.Font = new Font("Segoe UI Semilight", 9.75f);
		this.ForeColor = Color.White;
		this.BackColor = Color.FromArgb(50, 50, 50);
		this.Color_0 = Color.DodgerBlue;
		this.GEnum12_0 = GClass8.GEnum12.Dark;
	}

	// Token: 0x0600093E RID: 2366 RVA: 0x0002B68C File Offset: 0x0002988C
	protected virtual void OnPaint(PaintEventArgs pevent)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		base.OnPaint(pevent);
		GClass8.GEnum12 genum = this.GEnum12_0;
		Color color;
		if (genum != GClass8.GEnum12.Light)
		{
			if (genum == GClass8.GEnum12.Dark)
			{
				color = Color.FromArgb(50, 50, 50);
			}
		}
		else
		{
			color = Color.White;
		}
		checked
		{
			switch (this.genum11_0)
			{
			case GClass8.GEnum11.None:
				graphics.Clear(color);
				break;
			case GClass8.GEnum11.Over:
				graphics.Clear(this.Color_0);
				break;
			case GClass8.GEnum11.Down:
				graphics.Clear(this.Color_0);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(50, Color.Black)), new Rectangle(0, 0, base.Width - 1, base.Height - 1));
				break;
			}
			graphics.DrawRectangle(new Pen(Color.FromArgb(100, 100, 100)), new Rectangle(0, 0, base.Width - 1, base.Height - 1));
			StringFormat format = new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			};
			GClass8.GEnum12 genum2 = this.GEnum12_0;
			if (genum2 != GClass8.GEnum12.Light)
			{
				if (genum2 == GClass8.GEnum12.Dark)
				{
					graphics.DrawString(this.Text, this.Font, Brushes.White, new Rectangle(0, 0, base.Width - 1, base.Height - 1), format);
				}
			}
			else
			{
				graphics.DrawString(this.Text, this.Font, Brushes.Black, new Rectangle(0, 0, base.Width - 1, base.Height - 1), format);
			}
			pevent.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x0600093F RID: 2367 RVA: 0x0002B828 File Offset: 0x00029A28
	protected void method_0()
	{
		base.Invalidate();
	}

	// Token: 0x04000470 RID: 1136
	private GClass8.GEnum12 genum12_0;

	// Token: 0x04000471 RID: 1137
	private GClass8.GEnum11 genum11_0;

	// Token: 0x04000472 RID: 1138
	private Color color_0;

	// Token: 0x04000473 RID: 1139
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GClass8.GDelegate20 gdelegate20_0;

	// Token: 0x020000D2 RID: 210
	public enum GEnum11
	{
		// Token: 0x04000475 RID: 1141
		None,
		// Token: 0x04000476 RID: 1142
		Over,
		// Token: 0x04000477 RID: 1143
		Down
	}

	// Token: 0x020000D3 RID: 211
	public enum GEnum12
	{
		// Token: 0x04000479 RID: 1145
		Light,
		// Token: 0x0400047A RID: 1146
		Dark
	}

	// Token: 0x020000D4 RID: 212
	// (Invoke) Token: 0x06000943 RID: 2371
	public delegate void GDelegate20();
}
