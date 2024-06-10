using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x0200003E RID: 62
internal class Control26 : Control
{
	// Token: 0x06000376 RID: 886 RVA: 0x00010CFC File Offset: 0x0000EEFC
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Width = 180;
		base.Height = 80;
	}

	// Token: 0x1700011A RID: 282
	// (get) Token: 0x06000377 RID: 887 RVA: 0x00010D18 File Offset: 0x0000EF18
	// (set) Token: 0x06000378 RID: 888 RVA: 0x00010D30 File Offset: 0x0000EF30
	[Category("Colors")]
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

	// Token: 0x1700011B RID: 283
	// (get) Token: 0x06000379 RID: 889 RVA: 0x00010D3C File Offset: 0x0000EF3C
	// (set) Token: 0x0600037A RID: 890 RVA: 0x00010D54 File Offset: 0x0000EF54
	[Category("Colors")]
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

	// Token: 0x1700011C RID: 284
	// (get) Token: 0x0600037B RID: 891 RVA: 0x00010D60 File Offset: 0x0000EF60
	// (set) Token: 0x0600037C RID: 892 RVA: 0x00010D78 File Offset: 0x0000EF78
	[Category("Colors")]
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

	// Token: 0x1700011D RID: 285
	// (get) Token: 0x0600037D RID: 893 RVA: 0x00010D84 File Offset: 0x0000EF84
	// (set) Token: 0x0600037E RID: 894 RVA: 0x00010D9C File Offset: 0x0000EF9C
	[Category("Colors")]
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

	// Token: 0x1700011E RID: 286
	// (get) Token: 0x0600037F RID: 895 RVA: 0x00010DA8 File Offset: 0x0000EFA8
	// (set) Token: 0x06000380 RID: 896 RVA: 0x00010DC0 File Offset: 0x0000EFC0
	[Category("Colors")]
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

	// Token: 0x1700011F RID: 287
	// (get) Token: 0x06000381 RID: 897 RVA: 0x00010DCC File Offset: 0x0000EFCC
	// (set) Token: 0x06000382 RID: 898 RVA: 0x00010DE4 File Offset: 0x0000EFE4
	[Category("Colors")]
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

	// Token: 0x17000120 RID: 288
	// (get) Token: 0x06000383 RID: 899 RVA: 0x00010DF0 File Offset: 0x0000EFF0
	// (set) Token: 0x06000384 RID: 900 RVA: 0x00010E08 File Offset: 0x0000F008
	[Category("Colors")]
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

	// Token: 0x17000121 RID: 289
	// (get) Token: 0x06000385 RID: 901 RVA: 0x00010E14 File Offset: 0x0000F014
	// (set) Token: 0x06000386 RID: 902 RVA: 0x00010E2C File Offset: 0x0000F02C
	[Category("Colors")]
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

	// Token: 0x17000122 RID: 290
	// (get) Token: 0x06000387 RID: 903 RVA: 0x00010E38 File Offset: 0x0000F038
	// (set) Token: 0x06000388 RID: 904 RVA: 0x00010E50 File Offset: 0x0000F050
	[Category("Colors")]
	public Color Color_8
	{
		get
		{
			return this.color_8;
		}
		set
		{
			this.color_8 = value;
		}
	}

	// Token: 0x06000389 RID: 905 RVA: 0x00010E5C File Offset: 0x0000F05C
	public Control26()
	{
		this.color_0 = Color.FromArgb(220, 85, 96);
		this.color_1 = Color.FromArgb(10, 154, 157);
		this.color_2 = Color.FromArgb(0, 128, 255);
		this.color_3 = Color.FromArgb(35, 168, 109);
		this.color_4 = Color.FromArgb(253, 181, 63);
		this.color_5 = Color.FromArgb(155, 88, 181);
		this.color_6 = Color.FromArgb(45, 47, 49);
		this.color_7 = Color.FromArgb(63, 70, 73);
		this.color_8 = Color.FromArgb(243, 243, 243);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.FromArgb(60, 70, 73);
		base.Size = new Size(160, 80);
		this.Font = new Font("Segoe UI", 12f);
	}

	// Token: 0x0600038A RID: 906 RVA: 0x00010F7C File Offset: 0x0000F17C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			Graphics graphics_ = Class16.graphics_0;
			graphics_.SmoothingMode = SmoothingMode.HighQuality;
			graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics_.Clear(this.BackColor);
			graphics_.FillRectangle(new SolidBrush(this.color_0), new Rectangle(0, 0, 20, 40));
			graphics_.FillRectangle(new SolidBrush(this.color_1), new Rectangle(20, 0, 20, 40));
			graphics_.FillRectangle(new SolidBrush(this.color_2), new Rectangle(40, 0, 20, 40));
			graphics_.FillRectangle(new SolidBrush(this.color_3), new Rectangle(60, 0, 20, 40));
			graphics_.FillRectangle(new SolidBrush(this.color_4), new Rectangle(80, 0, 20, 40));
			graphics_.FillRectangle(new SolidBrush(this.color_5), new Rectangle(100, 0, 20, 40));
			graphics_.FillRectangle(new SolidBrush(this.color_6), new Rectangle(120, 0, 20, 40));
			graphics_.FillRectangle(new SolidBrush(this.color_7), new Rectangle(140, 0, 20, 40));
			graphics_.FillRectangle(new SolidBrush(this.color_8), new Rectangle(160, 0, 20, 40));
			graphics_.DrawString("Color Palette", this.Font, new SolidBrush(this.color_8), new Rectangle(0, 22, this.int_0, this.int_1), Class16.stringFormat_1);
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x040001D2 RID: 466
	private int int_0;

	// Token: 0x040001D3 RID: 467
	private int int_1;

	// Token: 0x040001D4 RID: 468
	private Color color_0;

	// Token: 0x040001D5 RID: 469
	private Color color_1;

	// Token: 0x040001D6 RID: 470
	private Color color_2;

	// Token: 0x040001D7 RID: 471
	private Color color_3;

	// Token: 0x040001D8 RID: 472
	private Color color_4;

	// Token: 0x040001D9 RID: 473
	private Color color_5;

	// Token: 0x040001DA RID: 474
	private Color color_6;

	// Token: 0x040001DB RID: 475
	private Color color_7;

	// Token: 0x040001DC RID: 476
	private Color color_8;
}
