using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000AE RID: 174
[DefaultEvent("Scroll")]
public class GControl40 : Control
{
	// Token: 0x17000253 RID: 595
	// (get) Token: 0x06000819 RID: 2073 RVA: 0x00025ECC File Offset: 0x000240CC
	// (set) Token: 0x0600081A RID: 2074 RVA: 0x00025EE4 File Offset: 0x000240E4
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

	// Token: 0x17000254 RID: 596
	// (get) Token: 0x0600081B RID: 2075 RVA: 0x00025EF0 File Offset: 0x000240F0
	// (set) Token: 0x0600081C RID: 2076 RVA: 0x00025F08 File Offset: 0x00024108
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

	// Token: 0x17000255 RID: 597
	// (get) Token: 0x0600081D RID: 2077 RVA: 0x00025F14 File Offset: 0x00024114
	// (set) Token: 0x0600081E RID: 2078 RVA: 0x00025F2C File Offset: 0x0002412C
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

	// Token: 0x17000256 RID: 598
	// (get) Token: 0x0600081F RID: 2079 RVA: 0x00025F38 File Offset: 0x00024138
	// (set) Token: 0x06000820 RID: 2080 RVA: 0x00025F50 File Offset: 0x00024150
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

	// Token: 0x17000257 RID: 599
	// (get) Token: 0x06000821 RID: 2081 RVA: 0x00025F5C File Offset: 0x0002415C
	// (set) Token: 0x06000822 RID: 2082 RVA: 0x00025F74 File Offset: 0x00024174
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

	// Token: 0x17000258 RID: 600
	// (get) Token: 0x06000823 RID: 2083 RVA: 0x00025F80 File Offset: 0x00024180
	// (set) Token: 0x06000824 RID: 2084 RVA: 0x00025F98 File Offset: 0x00024198
	[Category("Colours")]
	public Color Color_5
	{
		get
		{
			return this.color_5;
		}
		set
		{
			this.color_5 = value;
		}
	}

	// Token: 0x17000259 RID: 601
	// (get) Token: 0x06000825 RID: 2085 RVA: 0x00025FA4 File Offset: 0x000241A4
	// (set) Token: 0x06000826 RID: 2086 RVA: 0x00025FBC File Offset: 0x000241BC
	[Category("Colours")]
	public Color Color_6
	{
		get
		{
			return this.color_6;
		}
		set
		{
			this.color_6 = value;
		}
	}

	// Token: 0x1700025A RID: 602
	// (get) Token: 0x06000827 RID: 2087 RVA: 0x00025FC8 File Offset: 0x000241C8
	// (set) Token: 0x06000828 RID: 2088 RVA: 0x00025FE0 File Offset: 0x000241E0
	[Category("Colours")]
	public Color Color_7
	{
		get
		{
			return this.color_7;
		}
		set
		{
			this.color_7 = value;
		}
	}

	// Token: 0x1400001D RID: 29
	// (add) Token: 0x06000829 RID: 2089 RVA: 0x00025FEC File Offset: 0x000241EC
	// (remove) Token: 0x0600082A RID: 2090 RVA: 0x00026024 File Offset: 0x00024224
	public event GControl40.GDelegate16 Event_0
	{
		[CompilerGenerated]
		add
		{
			GControl40.GDelegate16 gdelegate = this.gdelegate16_0;
			GControl40.GDelegate16 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl40.GDelegate16 value2 = (GControl40.GDelegate16)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl40.GDelegate16>(ref this.gdelegate16_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl40.GDelegate16 gdelegate = this.gdelegate16_0;
			GControl40.GDelegate16 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl40.GDelegate16 value2 = (GControl40.GDelegate16)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl40.GDelegate16>(ref this.gdelegate16_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x1700025B RID: 603
	// (get) Token: 0x0600082B RID: 2091 RVA: 0x0002605C File Offset: 0x0002425C
	// (set) Token: 0x0600082C RID: 2092 RVA: 0x00026074 File Offset: 0x00024274
	public int Int32_0
	{
		get
		{
			return this.int_2;
		}
		set
		{
			this.int_2 = value;
			if (value > this.int_4)
			{
				this.int_4 = value;
			}
			if (value > this.int_3)
			{
				this.int_3 = value;
			}
			this.method_0();
		}
	}

	// Token: 0x1700025C RID: 604
	// (get) Token: 0x0600082D RID: 2093 RVA: 0x000260A8 File Offset: 0x000242A8
	// (set) Token: 0x0600082E RID: 2094 RVA: 0x000260C0 File Offset: 0x000242C0
	public int Int32_1
	{
		get
		{
			return this.int_3;
		}
		set
		{
			if (value < this.int_4)
			{
				this.int_4 = value;
			}
			if (value < this.int_2)
			{
				this.int_2 = value;
			}
		}
	}

	// Token: 0x1700025D RID: 605
	// (get) Token: 0x0600082F RID: 2095 RVA: 0x000260E8 File Offset: 0x000242E8
	// (set) Token: 0x06000830 RID: 2096 RVA: 0x00026100 File Offset: 0x00024300
	public int Int32_2
	{
		get
		{
			return this.int_4;
		}
		set
		{
			if (value != this.int_4)
			{
				if (value < this.int_2)
				{
					this.int_4 = this.int_2;
				}
				else if (value <= this.int_3)
				{
					this.int_4 = value;
				}
				else
				{
					this.int_4 = this.int_3;
				}
				this.method_1();
				GControl40.GDelegate16 gdelegate = this.gdelegate16_0;
				if (gdelegate != null)
				{
					gdelegate(this);
				}
			}
		}
	}

	// Token: 0x1700025E RID: 606
	// (get) Token: 0x06000831 RID: 2097 RVA: 0x0002616C File Offset: 0x0002436C
	// (set) Token: 0x06000832 RID: 2098 RVA: 0x00026184 File Offset: 0x00024384
	public int Int32_3
	{
		get
		{
			return this.int_5;
		}
		set
		{
			if (value < 1 || value > ((-(((this.int_5 == value) > false) ? 1 : 0)) ? 1 : 0))
			{
			}
		}
	}

	// Token: 0x1700025F RID: 607
	// (get) Token: 0x06000833 RID: 2099 RVA: 0x000261AC File Offset: 0x000243AC
	// (set) Token: 0x06000834 RID: 2100 RVA: 0x000261C4 File Offset: 0x000243C4
	public int Int32_4
	{
		get
		{
			return this.int_7;
		}
		set
		{
			if (value >= 1)
			{
				this.int_7 = value;
			}
		}
	}

	// Token: 0x17000260 RID: 608
	// (get) Token: 0x06000835 RID: 2101 RVA: 0x000261E0 File Offset: 0x000243E0
	// (set) Token: 0x06000836 RID: 2102 RVA: 0x000261F8 File Offset: 0x000243F8
	public int Int32_5
	{
		get
		{
			return this.int_6;
		}
		set
		{
			if (value >= 16)
			{
				this.int_6 = value;
			}
			else
			{
				this.int_6 = 16;
			}
		}
	}

	// Token: 0x06000837 RID: 2103 RVA: 0x00026220 File Offset: 0x00024420
	protected virtual void OnSizeChanged(EventArgs e)
	{
		this.method_0();
	}

	// Token: 0x06000838 RID: 2104 RVA: 0x00026228 File Offset: 0x00024428
	private void method_0()
	{
		this.rectangle_0 = new Rectangle(0, 1, 0, base.Height);
		checked
		{
			this.rectangle_2 = new Rectangle(this.rectangle_0.Right + 1, 0, base.Width - 3, base.Height);
			this.bool_0 = (this.int_3 - this.int_2 != 0);
			this.rectangle_3 = new Rectangle(0, 1, this.int_1, base.Height - 3);
			GControl40.GDelegate16 gdelegate = this.gdelegate16_0;
			if (gdelegate != null)
			{
				gdelegate(this);
			}
			this.method_1();
		}
	}

	// Token: 0x06000839 RID: 2105 RVA: 0x000262B8 File Offset: 0x000244B8
	private void method_1()
	{
		this.rectangle_3.X = checked((int)Math.Round(unchecked(checked((double)(this.int_4 - this.int_2) / (double)(this.int_3 - this.int_2)) * (double)(checked(this.rectangle_2.Width - this.int_1)) + 1.0)));
		base.Invalidate();
	}

	// Token: 0x0600083A RID: 2106 RVA: 0x00026318 File Offset: 0x00024518
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		checked
		{
			if (e.Button == MouseButtons.Left && this.bool_0)
			{
				if (!this.rectangle_0.Contains(e.Location))
				{
					if (!this.rectangle_1.Contains(e.Location))
					{
						if (this.rectangle_3.Contains(e.Location))
						{
							this.bool_2 = true;
							return;
						}
						if (e.X >= this.rectangle_3.X)
						{
							this.int_0 = this.int_4 + this.int_7;
						}
						else
						{
							this.int_0 = this.int_4 - this.int_7;
						}
					}
					else
					{
						this.int_0 = this.int_4 + this.int_5;
					}
				}
				else
				{
					this.int_0 = this.int_4 - this.int_5;
				}
				this.Int32_2 = Math.Min(Math.Max(this.int_0, this.int_2), this.int_3);
				this.method_1();
			}
		}
	}

	// Token: 0x0600083B RID: 2107 RVA: 0x00026414 File Offset: 0x00024614
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		checked
		{
			if (this.bool_2 && this.bool_0)
			{
				int num = e.X - this.rectangle_0.Width - this.int_1 / 2;
				int num2 = this.rectangle_2.Width - this.int_1;
				this.int_0 = (int)Math.Round(unchecked((double)num / (double)num2 * (double)(checked(this.int_3 - this.int_2)))) + this.int_2;
				this.Int32_2 = Math.Min(Math.Max(this.int_0, this.int_2), this.int_3);
				this.method_1();
			}
		}
	}

	// Token: 0x0600083C RID: 2108 RVA: 0x000264B4 File Offset: 0x000246B4
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		this.bool_2 = false;
	}

	// Token: 0x0600083D RID: 2109 RVA: 0x000264C0 File Offset: 0x000246C0
	public GControl40()
	{
		this.int_1 = 24;
		this.int_2 = 0;
		this.int_3 = 100;
		this.int_4 = 0;
		this.int_5 = 1;
		this.int_6 = 16;
		this.int_7 = 10;
		this.color_0 = Color.FromArgb(35, 35, 35);
		this.color_1 = Color.FromArgb(23, 119, 151);
		this.color_2 = Color.FromArgb(37, 37, 37);
		this.color_3 = Color.FromArgb(47, 47, 47);
		this.color_4 = Color.FromArgb(55, 55, 55);
		this.color_5 = Color.FromArgb(65, 65, 65);
		this.color_6 = Color.FromArgb(55, 55, 55);
		this.color_7 = Color.FromArgb(35, 35, 35);
		this.bool_2 = false;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.Selectable | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		base.Height = 18;
	}

	// Token: 0x0600083E RID: 2110 RVA: 0x000265B8 File Offset: 0x000247B8
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Width);
		Graphics graphics = Graphics.FromImage(bitmap);
		Graphics graphics2 = graphics;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.Clear(Color.FromArgb(47, 47, 47));
		checked
		{
			Point[] points = new Point[]
			{
				new Point(5, (int)Math.Round((double)base.Height / 2.0)),
				new Point(13, (int)Math.Round((double)base.Height / 4.0)),
				new Point(13, (int)Math.Round(unchecked((double)base.Height / 2.0 - 2.0))),
				new Point(base.Width - 13, (int)Math.Round(unchecked((double)base.Height / 2.0 - 2.0))),
				new Point(base.Width - 13, (int)Math.Round((double)base.Height / 4.0)),
				new Point(base.Width - 5, (int)Math.Round((double)base.Height / 2.0)),
				new Point(base.Width - 13, (int)Math.Round(unchecked((double)base.Height - (double)base.Height / 4.0 - 1.0))),
				new Point(base.Width - 13, (int)Math.Round(unchecked((double)base.Height / 2.0 + 2.0))),
				new Point(13, (int)Math.Round(unchecked((double)base.Height / 2.0 + 2.0))),
				new Point(13, (int)Math.Round(unchecked((double)base.Height - (double)base.Height / 4.0 - 1.0)))
			};
			graphics2.FillPolygon(new SolidBrush(this.color_2), points);
			graphics2.FillRectangle(new SolidBrush(this.color_4), this.rectangle_3);
			graphics2.DrawRectangle(new Pen(this.color_0), this.rectangle_3);
			graphics2.DrawRectangle(new Pen(this.color_5), this.rectangle_3.X + 1, this.rectangle_3.Y + 1, this.rectangle_3.Width - 2, this.rectangle_3.Height - 2);
			graphics2.DrawLine(new Pen(this.color_1, 2f), new Point(this.rectangle_3.X + 4, (int)Math.Round(unchecked((double)this.rectangle_3.Height / 2.0 + 1.0))), new Point(this.rectangle_3.Right - 4, (int)Math.Round(unchecked((double)this.rectangle_3.Height / 2.0 + 1.0))));
			graphics2.DrawRectangle(new Pen(this.color_6), 0, 0, base.Width - 1, base.Height - 1);
			graphics2.DrawRectangle(new Pen(this.color_7), 1, 1, base.Width - 3, base.Height - 3);
			base.OnPaint(e);
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x040003D0 RID: 976
	private int int_0;

	// Token: 0x040003D1 RID: 977
	private Rectangle rectangle_0;

	// Token: 0x040003D2 RID: 978
	private Rectangle rectangle_1;

	// Token: 0x040003D3 RID: 979
	private Rectangle rectangle_2;

	// Token: 0x040003D4 RID: 980
	private Rectangle rectangle_3;

	// Token: 0x040003D5 RID: 981
	private bool bool_0;

	// Token: 0x040003D6 RID: 982
	private bool bool_1;

	// Token: 0x040003D7 RID: 983
	private int int_1;

	// Token: 0x040003D8 RID: 984
	private int int_2;

	// Token: 0x040003D9 RID: 985
	private int int_3;

	// Token: 0x040003DA RID: 986
	private int int_4;

	// Token: 0x040003DB RID: 987
	private int int_5;

	// Token: 0x040003DC RID: 988
	private int int_6;

	// Token: 0x040003DD RID: 989
	private int int_7;

	// Token: 0x040003DE RID: 990
	private Color color_0;

	// Token: 0x040003DF RID: 991
	private Color color_1;

	// Token: 0x040003E0 RID: 992
	private Color color_2;

	// Token: 0x040003E1 RID: 993
	private Color color_3;

	// Token: 0x040003E2 RID: 994
	private Color color_4;

	// Token: 0x040003E3 RID: 995
	private Color color_5;

	// Token: 0x040003E4 RID: 996
	private Color color_6;

	// Token: 0x040003E5 RID: 997
	private Color color_7;

	// Token: 0x040003E6 RID: 998
	private bool bool_2;

	// Token: 0x040003E7 RID: 999
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GControl40.GDelegate16 gdelegate16_0;

	// Token: 0x020000AF RID: 175
	// (Invoke) Token: 0x06000842 RID: 2114
	public delegate void GDelegate16(object sender);
}
