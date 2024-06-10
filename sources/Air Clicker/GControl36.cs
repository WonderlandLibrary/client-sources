using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000A5 RID: 165
public class GControl36 : Control
{
	// Token: 0x17000229 RID: 553
	// (get) Token: 0x06000793 RID: 1939 RVA: 0x000239A8 File Offset: 0x00021BA8
	// (set) Token: 0x06000794 RID: 1940 RVA: 0x000239C0 File Offset: 0x00021BC0
	[Category("Colours")]
	public Color Color_0
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
		}
	}

	// Token: 0x1700022A RID: 554
	// (get) Token: 0x06000795 RID: 1941 RVA: 0x000239CC File Offset: 0x00021BCC
	// (set) Token: 0x06000796 RID: 1942 RVA: 0x000239E4 File Offset: 0x00021BE4
	[Category("Colours")]
	public Color Color_1
	{
		get
		{
			return this.color_1;
		}
		set
		{
			this.color_1 = value;
		}
	}

	// Token: 0x1700022B RID: 555
	// (get) Token: 0x06000797 RID: 1943 RVA: 0x000239F0 File Offset: 0x00021BF0
	// (set) Token: 0x06000798 RID: 1944 RVA: 0x00023A08 File Offset: 0x00021C08
	[Category("Colours")]
	public Color Color_2
	{
		get
		{
			return this.color_2;
		}
		set
		{
			this.color_2 = value;
		}
	}

	// Token: 0x1700022C RID: 556
	// (get) Token: 0x06000799 RID: 1945 RVA: 0x00023A14 File Offset: 0x00021C14
	// (set) Token: 0x0600079A RID: 1946 RVA: 0x00023A2C File Offset: 0x00021C2C
	[Category("Colours")]
	public Color Color_3
	{
		get
		{
			return this.color_3;
		}
		set
		{
			this.color_3 = value;
		}
	}

	// Token: 0x1700022D RID: 557
	// (get) Token: 0x0600079B RID: 1947 RVA: 0x00023A38 File Offset: 0x00021C38
	// (set) Token: 0x0600079C RID: 1948 RVA: 0x00023A50 File Offset: 0x00021C50
	[Category("Colours")]
	public Color Color_4
	{
		get
		{
			return this.color_4;
		}
		set
		{
			this.color_4 = value;
		}
	}

	// Token: 0x1400001A RID: 26
	// (add) Token: 0x0600079D RID: 1949 RVA: 0x00023A5C File Offset: 0x00021C5C
	// (remove) Token: 0x0600079E RID: 1950 RVA: 0x00023A94 File Offset: 0x00021C94
	public event GControl36.GDelegate13 Event_0
	{
		[CompilerGenerated]
		add
		{
			GControl36.GDelegate13 gdelegate = this.gdelegate13_0;
			GControl36.GDelegate13 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl36.GDelegate13 value2 = (GControl36.GDelegate13)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl36.GDelegate13>(ref this.gdelegate13_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl36.GDelegate13 gdelegate = this.gdelegate13_0;
			GControl36.GDelegate13 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl36.GDelegate13 value2 = (GControl36.GDelegate13)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl36.GDelegate13>(ref this.gdelegate13_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x0600079F RID: 1951 RVA: 0x00023AD0 File Offset: 0x00021CD0
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		this.int_0 = e.Location.X;
		base.Invalidate();
		if (e.X < checked(base.Width - 40) && e.X > 40)
		{
			this.Cursor = Cursors.IBeam;
		}
		else
		{
			this.Cursor = Cursors.Arrow;
		}
	}

	// Token: 0x060007A0 RID: 1952 RVA: 0x00023B38 File Offset: 0x00021D38
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		if (this.int_0 <= checked(base.Width - 39))
		{
			if (this.int_0 < 39)
			{
				this.genum7_0 = GControl36.GEnum7.NotToggled;
				this.method_0();
			}
		}
		else
		{
			this.genum7_0 = GControl36.GEnum7.Toggled;
			this.method_0();
		}
		base.Invalidate();
	}

	// Token: 0x1700022E RID: 558
	// (get) Token: 0x060007A1 RID: 1953 RVA: 0x00023B90 File Offset: 0x00021D90
	// (set) Token: 0x060007A2 RID: 1954 RVA: 0x00023BA8 File Offset: 0x00021DA8
	public GControl36.GEnum7 GEnum7_0
	{
		get
		{
			return this.genum7_0;
		}
		set
		{
			this.genum7_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x060007A3 RID: 1955 RVA: 0x00023BB8 File Offset: 0x00021DB8
	private void method_0()
	{
		checked
		{
			if (this.genum7_0 <= GControl36.GEnum7.Toggled)
			{
				if (this.int_1 > 0)
				{
					ref int ptr = ref this.int_1;
					this.int_1 = ptr - 10;
				}
			}
			else if (this.int_1 < 100)
			{
				ref int ptr = ref this.int_1;
				this.int_1 = ptr + 10;
			}
			base.Invalidate();
		}
	}

	// Token: 0x060007A4 RID: 1956 RVA: 0x00023C0C File Offset: 0x00021E0C
	public GControl36()
	{
		this.genum7_0 = GControl36.GEnum7.NotToggled;
		this.int_1 = 0;
		this.color_0 = Color.FromArgb(42, 42, 42);
		this.color_1 = Color.FromArgb(35, 35, 35);
		this.color_2 = Color.FromArgb(255, 255, 255);
		this.color_3 = Color.FromArgb(155, 155, 155);
		this.color_4 = Color.FromArgb(23, 119, 151);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.DoubleBuffer, true);
	}

	// Token: 0x060007A5 RID: 1957 RVA: 0x00023CA4 File Offset: 0x00021EA4
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		Graphics graphics = e.Graphics;
		graphics.Clear(base.Parent.FindForm().BackColor);
		graphics.SmoothingMode = SmoothingMode.AntiAlias;
		graphics.FillRectangle(new SolidBrush(this.color_0), new Rectangle(0, 0, 39, base.Height));
		checked
		{
			graphics.FillRectangle(new SolidBrush(this.color_0), new Rectangle(base.Width - 40, 0, base.Width, base.Height));
			graphics.FillRectangle(new SolidBrush(this.color_0), new Rectangle(38, 9, base.Width - 40, 5));
			Point[] points = new Point[]
			{
				new Point(0, 0),
				new Point(39, 0),
				new Point(39, 9),
				new Point(base.Width - 40, 9),
				new Point(base.Width - 40, 0),
				new Point(base.Width - 2, 0),
				new Point(base.Width - 2, base.Height - 1),
				new Point(base.Width - 40, base.Height - 1),
				new Point(base.Width - 40, 14),
				new Point(39, 14),
				new Point(39, base.Height - 1),
				new Point(0, base.Height - 1),
				default(Point)
			};
			graphics.DrawLines(new Pen(this.color_1, 2f), points);
			if (this.genum7_0 == GControl36.GEnum7.Toggled)
			{
				graphics.FillRectangle(new SolidBrush(this.color_4), new Rectangle((int)Math.Round((double)base.Width / 2.0), 10, (int)Math.Round(unchecked((double)base.Width / 2.0 - 38.0)), 3));
				graphics.FillRectangle(new SolidBrush(this.color_4), new Rectangle(base.Width - 39, 2, 36, base.Height - 5));
			}
			if (this.genum7_0 != GControl36.GEnum7.Toggled)
			{
				if (this.genum7_0 == GControl36.GEnum7.NotToggled)
				{
					graphics.DrawString("OFF", new Font("Microsoft Sans Serif", 7f, FontStyle.Bold), new SolidBrush(this.color_2), new Rectangle(7, -1, (int)Math.Round(unchecked((double)(checked(base.Width - 20)) + 6.666666666666667)), base.Height), new StringFormat
					{
						Alignment = StringAlignment.Near,
						LineAlignment = StringAlignment.Center
					});
					graphics.DrawString("ON", new Font("Microsoft Sans Serif", 7f, FontStyle.Bold), new SolidBrush(this.color_3), new Rectangle(2, -1, (int)Math.Round(unchecked((double)(checked(base.Width - 20)) + 6.666666666666667)), base.Height), new StringFormat
					{
						Alignment = StringAlignment.Far,
						LineAlignment = StringAlignment.Center
					});
				}
			}
			else
			{
				graphics.DrawString("ON", new Font("Microsoft Sans Serif", 7f, FontStyle.Bold), new SolidBrush(this.color_2), new Rectangle(2, -1, (int)Math.Round(unchecked((double)(checked(base.Width - 20)) + 6.666666666666667)), base.Height), new StringFormat
				{
					Alignment = StringAlignment.Far,
					LineAlignment = StringAlignment.Center
				});
				graphics.DrawString("OFF", new Font("Microsoft Sans Serif", 7f, FontStyle.Bold), new SolidBrush(this.color_3), new Rectangle(7, -1, (int)Math.Round(unchecked((double)(checked(base.Width - 20)) + 6.666666666666667)), base.Height), new StringFormat
				{
					Alignment = StringAlignment.Near,
					LineAlignment = StringAlignment.Center
				});
			}
			graphics.DrawLine(new Pen(this.color_1, 2f), new Point((int)Math.Round((double)base.Width / 2.0), 0), new Point((int)Math.Round((double)base.Width / 2.0), base.Height));
		}
	}

	// Token: 0x04000391 RID: 913
	private GControl36.GEnum7 genum7_0;

	// Token: 0x04000392 RID: 914
	private int int_0;

	// Token: 0x04000393 RID: 915
	private int int_1;

	// Token: 0x04000394 RID: 916
	private Color color_0;

	// Token: 0x04000395 RID: 917
	private Color color_1;

	// Token: 0x04000396 RID: 918
	private Color color_2;

	// Token: 0x04000397 RID: 919
	private Color color_3;

	// Token: 0x04000398 RID: 920
	private Color color_4;

	// Token: 0x04000399 RID: 921
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private GControl36.GDelegate13 gdelegate13_0;

	// Token: 0x020000A6 RID: 166
	public enum GEnum7
	{
		// Token: 0x0400039B RID: 923
		Toggled,
		// Token: 0x0400039C RID: 924
		NotToggled
	}

	// Token: 0x020000A7 RID: 167
	// (Invoke) Token: 0x060007A9 RID: 1961
	public delegate void GDelegate13();
}
