using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x020000AA RID: 170
public class GControl38 : Control
{
	// Token: 0x1700023E RID: 574
	// (get) Token: 0x060007D4 RID: 2004 RVA: 0x00024D4C File Offset: 0x00022F4C
	// (set) Token: 0x060007D5 RID: 2005 RVA: 0x00024D64 File Offset: 0x00022F64
	[Category("Colours")]
	public Color Color_0
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

	// Token: 0x1700023F RID: 575
	// (get) Token: 0x060007D6 RID: 2006 RVA: 0x00024D70 File Offset: 0x00022F70
	// (set) Token: 0x060007D7 RID: 2007 RVA: 0x00024D88 File Offset: 0x00022F88
	[Category("Colours")]
	public Color Color_1
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

	// Token: 0x17000240 RID: 576
	// (get) Token: 0x060007D8 RID: 2008 RVA: 0x00024D94 File Offset: 0x00022F94
	// (set) Token: 0x060007D9 RID: 2009 RVA: 0x00024DAC File Offset: 0x00022FAC
	[Category("Colours")]
	public Color Color_2
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

	// Token: 0x17000241 RID: 577
	// (get) Token: 0x060007DA RID: 2010 RVA: 0x00024DB8 File Offset: 0x00022FB8
	// (set) Token: 0x060007DB RID: 2011 RVA: 0x00024DD0 File Offset: 0x00022FD0
	[Category("Colours")]
	public Color Color_3
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

	// Token: 0x17000242 RID: 578
	// (get) Token: 0x060007DC RID: 2012 RVA: 0x00024DDC File Offset: 0x00022FDC
	// (set) Token: 0x060007DD RID: 2013 RVA: 0x00024DF4 File Offset: 0x00022FF4
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

	// Token: 0x17000243 RID: 579
	// (get) Token: 0x060007DE RID: 2014 RVA: 0x00024E00 File Offset: 0x00023000
	// (set) Token: 0x060007DF RID: 2015 RVA: 0x00024E18 File Offset: 0x00023018
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			if (value > 0)
			{
				this.int_0 = value;
			}
			if (value < this.int_1)
			{
				this.int_1 = value;
			}
			base.Invalidate();
		}
	}

	// Token: 0x1400001B RID: 27
	// (add) Token: 0x060007E0 RID: 2016 RVA: 0x00024E40 File Offset: 0x00023040
	// (remove) Token: 0x060007E1 RID: 2017 RVA: 0x00024E78 File Offset: 0x00023078
	public event GControl38.GDelegate14 Event_0
	{
		[CompilerGenerated]
		add
		{
			GControl38.GDelegate14 gdelegate = this.gdelegate14_0;
			GControl38.GDelegate14 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl38.GDelegate14 value2 = (GControl38.GDelegate14)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl38.GDelegate14>(ref this.gdelegate14_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl38.GDelegate14 gdelegate = this.gdelegate14_0;
			GControl38.GDelegate14 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl38.GDelegate14 value2 = (GControl38.GDelegate14)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl38.GDelegate14>(ref this.gdelegate14_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x17000244 RID: 580
	// (get) Token: 0x060007E2 RID: 2018 RVA: 0x00024EB4 File Offset: 0x000230B4
	// (set) Token: 0x060007E3 RID: 2019 RVA: 0x00024ECC File Offset: 0x000230CC
	public int Int32_1
	{
		get
		{
			return this.int_1;
		}
		set
		{
			if (value != this.int_1)
			{
				if (value < 0)
				{
					this.int_1 = 0;
				}
				else if (value <= this.int_0)
				{
					this.int_1 = value;
				}
				else
				{
					this.int_1 = this.int_0;
				}
				base.Invalidate();
				GControl38.GDelegate14 gdelegate = this.gdelegate14_0;
				if (gdelegate != null)
				{
					gdelegate();
				}
			}
		}
	}

	// Token: 0x060007E4 RID: 2020 RVA: 0x00024F2C File Offset: 0x0002312C
	protected virtual void OnHandleCreated(EventArgs e)
	{
		this.BackColor = Color.Transparent;
		base.OnHandleCreated(e);
	}

	// Token: 0x060007E5 RID: 2021 RVA: 0x00024F40 File Offset: 0x00023140
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		Rectangle rect = new Rectangle(new Point(e.Location.X, e.Location.Y), new Size(1, 1));
		checked
		{
			Rectangle rectangle = new Rectangle(10, 10, base.Width - 21, base.Height - 21);
			if (new Rectangle(new Point(rectangle.X + (int)Math.Round(unchecked((double)rectangle.Width * ((double)this.Int32_1 / (double)this.Int32_0))) - (int)Math.Round(unchecked((double)this.size_0.Width / 2.0 - 1.0)), 0), new Size(this.size_0.Width, base.Height)).IntersectsWith(rect))
			{
				this.bool_0 = true;
			}
		}
	}

	// Token: 0x060007E6 RID: 2022 RVA: 0x00025020 File Offset: 0x00023220
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.bool_0 = false;
	}

	// Token: 0x060007E7 RID: 2023 RVA: 0x00025030 File Offset: 0x00023230
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		checked
		{
			if (this.bool_0)
			{
				Point point = new Point(e.X, e.Y);
				Rectangle rectangle = new Rectangle(10, 10, base.Width - 21, base.Height - 21);
				this.Int32_1 = (int)Math.Round(unchecked((double)this.Int32_0 * ((double)(checked(point.X - rectangle.X)) / (double)rectangle.Width)));
			}
		}
	}

	// Token: 0x060007E8 RID: 2024 RVA: 0x000250AC File Offset: 0x000232AC
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.bool_0 = false;
	}

	// Token: 0x060007E9 RID: 2025 RVA: 0x000250BC File Offset: 0x000232BC
	public GControl38()
	{
		this.int_0 = 10;
		this.int_1 = 0;
		this.bool_0 = false;
		this.rectangle_0 = checked(new Rectangle(0, 10, base.Width - 21, base.Height - 21));
		this.size_0 = new Size(25, 14);
		this.color_0 = Color.FromArgb(255, 255, 255);
		this.color_1 = Color.FromArgb(35, 35, 35);
		this.color_2 = Color.FromArgb(47, 47, 47);
		this.color_3 = Color.FromArgb(42, 42, 42);
		this.color_4 = Color.FromArgb(23, 119, 151);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.Selectable | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
	}

	// Token: 0x060007EA RID: 2026 RVA: 0x00025188 File Offset: 0x00023388
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Graphics graphics2 = graphics;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		checked
		{
			this.rectangle_0 = new Rectangle(13, 13, base.Width - 27, base.Height - 25);
			graphics2.Clear(base.Parent.FindForm().BackColor);
			graphics2.SmoothingMode = SmoothingMode.AntiAlias;
			graphics2.TextRenderingHint = TextRenderingHint.AntiAliasGridFit;
			graphics2.FillRectangle(new SolidBrush(this.color_3), new Rectangle(3, (int)Math.Round(unchecked((double)base.Height / 2.0 - 5.0)), base.Width - 11, 11));
			graphics2.FillRectangle(new SolidBrush(this.color_2), this.rectangle_0.X + (int)Math.Round(unchecked((double)this.rectangle_0.Width * ((double)this.Int32_1 / (double)this.Int32_0))) - (int)Math.Round((double)this.size_0.Width / 2.0), this.rectangle_0.Y + (int)Math.Round((double)this.rectangle_0.Height / 2.0) - (int)Math.Round((double)this.size_0.Height / 2.0), this.size_0.Width, this.size_0.Height);
			graphics2.DrawString(Conversions.ToString(this.int_1), new Font("Segoe UI", 6.5f, FontStyle.Regular), new SolidBrush(this.color_0), new Rectangle(this.rectangle_0.X + (int)Math.Round(unchecked((double)this.rectangle_0.Width * ((double)this.Int32_1 / (double)this.Int32_0))) - (int)Math.Round((double)this.size_0.Width / 2.0), this.rectangle_0.Y + (int)Math.Round((double)this.rectangle_0.Height / 2.0) - (int)Math.Round((double)this.size_0.Height / 2.0), this.size_0.Width - 1, this.size_0.Height), new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			});
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x040003AE RID: 942
	private int int_0;

	// Token: 0x040003AF RID: 943
	private int int_1;

	// Token: 0x040003B0 RID: 944
	private bool bool_0;

	// Token: 0x040003B1 RID: 945
	private Rectangle rectangle_0;

	// Token: 0x040003B2 RID: 946
	private Size size_0;

	// Token: 0x040003B3 RID: 947
	private Color color_0;

	// Token: 0x040003B4 RID: 948
	private Color color_1;

	// Token: 0x040003B5 RID: 949
	private Color color_2;

	// Token: 0x040003B6 RID: 950
	private Color color_3;

	// Token: 0x040003B7 RID: 951
	private Color color_4;

	// Token: 0x040003B8 RID: 952
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GControl38.GDelegate14 gdelegate14_0;

	// Token: 0x020000AB RID: 171
	// (Invoke) Token: 0x060007EE RID: 2030
	public delegate void GDelegate14();
}
