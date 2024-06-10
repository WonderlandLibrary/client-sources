using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x02000058 RID: 88
internal class Control40 : Control
{
	// Token: 0x0600049A RID: 1178 RVA: 0x000162B0 File Offset: 0x000144B0
	protected virtual void CreateHandle()
	{
		base.CreateHandle();
		this.Dock = DockStyle.Bottom;
	}

	// Token: 0x0600049B RID: 1179 RVA: 0x000162C0 File Offset: 0x000144C0
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x1700016A RID: 362
	// (get) Token: 0x0600049C RID: 1180 RVA: 0x000162D0 File Offset: 0x000144D0
	// (set) Token: 0x0600049D RID: 1181 RVA: 0x000162E8 File Offset: 0x000144E8
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

	// Token: 0x1700016B RID: 363
	// (get) Token: 0x0600049E RID: 1182 RVA: 0x000162F4 File Offset: 0x000144F4
	// (set) Token: 0x0600049F RID: 1183 RVA: 0x0001630C File Offset: 0x0001450C
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

	// Token: 0x1700016C RID: 364
	// (get) Token: 0x060004A0 RID: 1184 RVA: 0x00016318 File Offset: 0x00014518
	// (set) Token: 0x060004A1 RID: 1185 RVA: 0x00016330 File Offset: 0x00014530
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

	// Token: 0x1700016D RID: 365
	// (get) Token: 0x060004A2 RID: 1186 RVA: 0x0001633C File Offset: 0x0001453C
	// (set) Token: 0x060004A3 RID: 1187 RVA: 0x00016344 File Offset: 0x00014544
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
		}
	}

	// Token: 0x060004A4 RID: 1188 RVA: 0x00016350 File Offset: 0x00014550
	public string method_0()
	{
		return string.Concat(new string[]
		{
			Conversions.ToString(DateTime.Now.Date),
			" ",
			Conversions.ToString(DateTime.Now.Hour),
			":",
			Conversions.ToString(DateTime.Now.Minute)
		});
	}

	// Token: 0x060004A5 RID: 1189 RVA: 0x000163BC File Offset: 0x000145BC
	public Control40()
	{
		this.bool_0 = false;
		this.color_0 = Color.FromArgb(45, 47, 49);
		this.color_1 = Color.White;
		this.color_2 = Class16.color_0;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.Font = new Font("Segoe UI", 8f);
		this.ForeColor = Color.White;
		base.Size = new Size(base.Width, 20);
	}

	// Token: 0x060004A6 RID: 1190 RVA: 0x00016444 File Offset: 0x00014644
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		this.int_0 = base.Width;
		this.int_1 = base.Height;
		Rectangle rect = new Rectangle(0, 0, this.int_0, this.int_1);
		Graphics graphics_ = Class16.graphics_0;
		graphics_.SmoothingMode = SmoothingMode.HighQuality;
		graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics_.Clear(this.Color_0);
		graphics_.FillRectangle(new SolidBrush(this.Color_0), rect);
		graphics_.DrawString(this.Text, this.Font, Brushes.White, new Rectangle(10, 4, this.int_0, this.int_1), Class16.stringFormat_0);
		graphics_.FillRectangle(new SolidBrush(this.color_2), new Rectangle(4, 4, 4, 14));
		if (this.Boolean_0)
		{
			graphics_.DrawString(this.method_0(), this.Font, new SolidBrush(this.color_1), new Rectangle(-4, 2, this.int_0, this.int_1), new StringFormat
			{
				Alignment = StringAlignment.Far,
				LineAlignment = StringAlignment.Center
			});
		}
		base.OnPaint(e);
		Class16.graphics_0.Dispose();
		e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
		Class16.bitmap_0.Dispose();
	}

	// Token: 0x0400026D RID: 621
	private int int_0;

	// Token: 0x0400026E RID: 622
	private int int_1;

	// Token: 0x0400026F RID: 623
	private bool bool_0;

	// Token: 0x04000270 RID: 624
	private Color color_0;

	// Token: 0x04000271 RID: 625
	private Color color_1;

	// Token: 0x04000272 RID: 626
	private Color color_2;
}
