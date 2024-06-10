using System;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000C9 RID: 201
public class GControl41 : ContainerControl
{
	// Token: 0x14000022 RID: 34
	// (add) Token: 0x06000908 RID: 2312 RVA: 0x0002AB44 File Offset: 0x00028D44
	// (remove) Token: 0x06000909 RID: 2313 RVA: 0x0002AB7C File Offset: 0x00028D7C
	public event GControl41.GDelegate17 Event_0
	{
		[CompilerGenerated]
		add
		{
			GControl41.GDelegate17 gdelegate = this.gdelegate17_0;
			GControl41.GDelegate17 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl41.GDelegate17 value2 = (GControl41.GDelegate17)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl41.GDelegate17>(ref this.gdelegate17_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl41.GDelegate17 gdelegate = this.gdelegate17_0;
			GControl41.GDelegate17 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl41.GDelegate17 value2 = (GControl41.GDelegate17)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl41.GDelegate17>(ref this.gdelegate17_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x1700028A RID: 650
	// (get) Token: 0x0600090A RID: 2314 RVA: 0x0002ABB8 File Offset: 0x00028DB8
	// (set) Token: 0x0600090B RID: 2315 RVA: 0x0002ABD0 File Offset: 0x00028DD0
	public GControl41.GEnum8 GEnum8_0
	{
		get
		{
			return this.genum8_0;
		}
		set
		{
			this.genum8_0 = value;
			GControl41.GDelegate17 gdelegate = this.gdelegate17_0;
			if (gdelegate != null)
			{
				gdelegate();
			}
		}
	}

	// Token: 0x0600090C RID: 2316 RVA: 0x0002ABF4 File Offset: 0x00028DF4
	protected void method_0()
	{
		base.Invalidate();
		GControl41.GEnum8 genum = this.GEnum8_0;
		if (genum != GControl41.GEnum8.Light)
		{
			if (genum == GControl41.GEnum8.Dark)
			{
				this.BackColor = Color.FromArgb(50, 50, 50);
				this.ForeColor = Color.White;
			}
		}
		else
		{
			this.BackColor = Color.White;
			this.ForeColor = Color.Black;
		}
	}

	// Token: 0x1700028B RID: 651
	// (get) Token: 0x0600090D RID: 2317 RVA: 0x0002AC4C File Offset: 0x00028E4C
	// (set) Token: 0x0600090E RID: 2318 RVA: 0x0002AC64 File Offset: 0x00028E64
	public Color Color_0
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
			this.method_1();
		}
	}

	// Token: 0x0600090F RID: 2319 RVA: 0x0002AC74 File Offset: 0x00028E74
	public GControl41()
	{
		this.Event_0 += this.method_0;
		this.Event_1 += this.method_1;
		this.point_0 = new Point(0, 0);
		this.bool_0 = false;
		this.int_1 = 0;
		this.DoubleBuffered = true;
		this.Font = new Font("Segoe UI Semilight", 9.75f);
		this.Color_0 = Color.DodgerBlue;
		this.GEnum8_0 = GControl41.GEnum8.Dark;
		this.ForeColor = Color.White;
		this.BackColor = Color.FromArgb(50, 50, 50);
		this.int_0 = 32;
	}

	// Token: 0x14000023 RID: 35
	// (add) Token: 0x06000910 RID: 2320 RVA: 0x0002AD18 File Offset: 0x00028F18
	// (remove) Token: 0x06000911 RID: 2321 RVA: 0x0002AD54 File Offset: 0x00028F54
	public event GControl41.GDelegate18 Event_1
	{
		[CompilerGenerated]
		add
		{
			GControl41.GDelegate18 gdelegate = this.gdelegate18_0;
			GControl41.GDelegate18 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl41.GDelegate18 value2 = (GControl41.GDelegate18)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl41.GDelegate18>(ref this.gdelegate18_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl41.GDelegate18 gdelegate = this.gdelegate18_0;
			GControl41.GDelegate18 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl41.GDelegate18 value2 = (GControl41.GDelegate18)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl41.GDelegate18>(ref this.gdelegate18_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x06000912 RID: 2322 RVA: 0x0002AD90 File Offset: 0x00028F90
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		if (e.Button == MouseButtons.Left & new Rectangle(0, 0, base.Width, this.int_0).Contains(e.Location))
		{
			this.bool_0 = true;
			this.point_0 = e.Location;
		}
	}

	// Token: 0x06000913 RID: 2323 RVA: 0x0002ADE8 File Offset: 0x00028FE8
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		if (this.bool_0)
		{
			base.Parent.Location = Control.MousePosition - (Size)this.point_0;
		}
	}

	// Token: 0x06000914 RID: 2324 RVA: 0x0002AE1C File Offset: 0x0002901C
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.bool_0 = false;
	}

	// Token: 0x06000915 RID: 2325 RVA: 0x0002AE2C File Offset: 0x0002902C
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		this.Dock = DockStyle.Fill;
		base.Parent.FindForm().FormBorderStyle = FormBorderStyle.None;
	}

	// Token: 0x06000916 RID: 2326 RVA: 0x0002AE4C File Offset: 0x0002904C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		base.OnPaint(e);
		graphics.Clear(this.BackColor);
		graphics.DrawLine(new Pen(this.color_0, 2f), new Point(0, 30), new Point(base.Width, 30));
		checked
		{
			graphics.DrawString(this.Text, this.Font, new SolidBrush(this.ForeColor), new Rectangle(8, 6, base.Width - 1, base.Height - 1), StringFormat.GenericDefault);
			graphics.DrawLine(new Pen(this.color_0, 3f), new Point(8, 27), new Point((int)Math.Round((double)(unchecked(8f + graphics.MeasureString(this.Text, this.Font).Width))), 27));
			graphics.DrawRectangle(new Pen(Color.FromArgb(100, 100, 100)), new Rectangle(0, 0, base.Width - 1, base.Height - 1));
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x06000917 RID: 2327 RVA: 0x0002AF88 File Offset: 0x00029188
	protected void method_1()
	{
		base.Invalidate();
	}

	// Token: 0x06000918 RID: 2328 RVA: 0x0002AF90 File Offset: 0x00029190
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x06000919 RID: 2329 RVA: 0x0002AFA0 File Offset: 0x000291A0
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Invalidate();
	}

	// Token: 0x04000459 RID: 1113
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GControl41.GDelegate17 gdelegate17_0;

	// Token: 0x0400045A RID: 1114
	private GControl41.GEnum8 genum8_0;

	// Token: 0x0400045B RID: 1115
	private Color color_0;

	// Token: 0x0400045C RID: 1116
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GControl41.GDelegate18 gdelegate18_0;

	// Token: 0x0400045D RID: 1117
	private Point point_0;

	// Token: 0x0400045E RID: 1118
	private bool bool_0;

	// Token: 0x0400045F RID: 1119
	private int int_0;

	// Token: 0x04000460 RID: 1120
	private int int_1;

	// Token: 0x020000CA RID: 202
	public enum GEnum8
	{
		// Token: 0x04000462 RID: 1122
		Light,
		// Token: 0x04000463 RID: 1123
		Dark
	}

	// Token: 0x020000CB RID: 203
	// (Invoke) Token: 0x0600091D RID: 2333
	public delegate void GDelegate17();

	// Token: 0x020000CC RID: 204
	// (Invoke) Token: 0x06000921 RID: 2337
	public delegate void GDelegate18();
}
