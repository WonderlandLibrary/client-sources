using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x0200003D RID: 61
internal class Control25 : Control
{
	// Token: 0x06000369 RID: 873 RVA: 0x00010A2C File Offset: 0x0000EC2C
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x0600036A RID: 874 RVA: 0x00010A44 File Offset: 0x0000EC44
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum2_0 = Enum2.Down;
		base.Invalidate();
	}

	// Token: 0x0600036B RID: 875 RVA: 0x00010A5C File Offset: 0x0000EC5C
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum2_0 = Enum2.None;
		base.Invalidate();
	}

	// Token: 0x0600036C RID: 876 RVA: 0x00010A74 File Offset: 0x0000EC74
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x0600036D RID: 877 RVA: 0x00010A8C File Offset: 0x0000EC8C
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		this.int_0 = e.X;
		base.Invalidate();
	}

	// Token: 0x0600036E RID: 878 RVA: 0x00010AA8 File Offset: 0x0000ECA8
	protected virtual void OnClick(EventArgs e)
	{
		base.OnClick(e);
		FormWindowState windowState = base.FindForm().WindowState;
		if (windowState != FormWindowState.Normal)
		{
			if (windowState == FormWindowState.Maximized)
			{
				base.FindForm().WindowState = FormWindowState.Minimized;
			}
		}
		else
		{
			base.FindForm().WindowState = FormWindowState.Minimized;
		}
	}

	// Token: 0x17000118 RID: 280
	// (get) Token: 0x0600036F RID: 879 RVA: 0x00010AEC File Offset: 0x0000ECEC
	// (set) Token: 0x06000370 RID: 880 RVA: 0x00010B04 File Offset: 0x0000ED04
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

	// Token: 0x17000119 RID: 281
	// (get) Token: 0x06000371 RID: 881 RVA: 0x00010B10 File Offset: 0x0000ED10
	// (set) Token: 0x06000372 RID: 882 RVA: 0x00010B28 File Offset: 0x0000ED28
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

	// Token: 0x06000373 RID: 883 RVA: 0x00010B34 File Offset: 0x0000ED34
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Size = new Size(18, 18);
	}

	// Token: 0x06000374 RID: 884 RVA: 0x00010B4C File Offset: 0x0000ED4C
	public Control25()
	{
		this.enum2_0 = Enum2.None;
		this.color_0 = Color.FromArgb(45, 47, 49);
		this.color_1 = Color.FromArgb(243, 243, 243);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.White;
		base.Size = new Size(18, 18);
		this.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
		this.Font = new Font("Marlett", 12f);
	}

	// Token: 0x06000375 RID: 885 RVA: 0x00010BDC File Offset: 0x0000EDDC
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
		graphics2.DrawString("0", this.Font, new SolidBrush(this.Color_1), new Rectangle(2, 1, base.Width, base.Height), Class16.stringFormat_1);
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

	// Token: 0x040001CE RID: 462
	private Enum2 enum2_0;

	// Token: 0x040001CF RID: 463
	private int int_0;

	// Token: 0x040001D0 RID: 464
	private Color color_0;

	// Token: 0x040001D1 RID: 465
	private Color color_1;
}
