using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x0200003B RID: 59
internal class Control23 : Control
{
	// Token: 0x0600034F RID: 847 RVA: 0x00010464 File Offset: 0x0000E664
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x06000350 RID: 848 RVA: 0x0001047C File Offset: 0x0000E67C
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum2_0 = Enum2.Down;
		base.Invalidate();
	}

	// Token: 0x06000351 RID: 849 RVA: 0x00010494 File Offset: 0x0000E694
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum2_0 = Enum2.None;
		base.Invalidate();
	}

	// Token: 0x06000352 RID: 850 RVA: 0x000104AC File Offset: 0x0000E6AC
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x06000353 RID: 851 RVA: 0x000104C4 File Offset: 0x0000E6C4
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		this.int_0 = e.X;
		base.Invalidate();
	}

	// Token: 0x06000354 RID: 852 RVA: 0x000104E0 File Offset: 0x0000E6E0
	protected virtual void OnClick(EventArgs e)
	{
		base.OnClick(e);
		Environment.Exit(0);
	}

	// Token: 0x06000355 RID: 853 RVA: 0x000104F0 File Offset: 0x0000E6F0
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Size = new Size(18, 18);
	}

	// Token: 0x17000114 RID: 276
	// (get) Token: 0x06000356 RID: 854 RVA: 0x00010508 File Offset: 0x0000E708
	// (set) Token: 0x06000357 RID: 855 RVA: 0x00010520 File Offset: 0x0000E720
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

	// Token: 0x17000115 RID: 277
	// (get) Token: 0x06000358 RID: 856 RVA: 0x0001052C File Offset: 0x0000E72C
	// (set) Token: 0x06000359 RID: 857 RVA: 0x00010544 File Offset: 0x0000E744
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

	// Token: 0x0600035A RID: 858 RVA: 0x00010550 File Offset: 0x0000E750
	public Control23()
	{
		this.enum2_0 = Enum2.None;
		this.color_0 = Color.FromArgb(168, 35, 35);
		this.color_1 = Color.FromArgb(243, 243, 243);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.White;
		base.Size = new Size(18, 18);
		this.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
		this.Font = new Font("Marlett", 10f);
	}

	// Token: 0x0600035B RID: 859 RVA: 0x000105E4 File Offset: 0x0000E7E4
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rect = new Rectangle(0, 0, base.Width, base.Height);
		Graphics graphics2 = graphics;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.Clear(this.BackColor);
		graphics2.FillRectangle(new SolidBrush(this.color_0), rect);
		graphics2.DrawString("r", this.Font, new SolidBrush(this.Color_1), new Rectangle(0, 0, base.Width, base.Height), Class16.stringFormat_1);
		Enum2 @enum = this.enum2_0;
		if (@enum == Enum2.Over)
		{
			graphics2.FillRectangle(new SolidBrush(Color.FromArgb(30, Color.White)), rect);
		}
		else if (@enum == Enum2.Down)
		{
			graphics2.FillRectangle(new SolidBrush(Color.FromArgb(30, Color.Black)), rect);
		}
		base.OnPaint(e);
		graphics.Dispose();
		e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
		bitmap.Dispose();
	}

	// Token: 0x040001C6 RID: 454
	private Enum2 enum2_0;

	// Token: 0x040001C7 RID: 455
	private int int_0;

	// Token: 0x040001C8 RID: 456
	private Color color_0;

	// Token: 0x040001C9 RID: 457
	private Color color_1;
}
