using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x0200003C RID: 60
internal class Control24 : Control
{
	// Token: 0x0600035C RID: 860 RVA: 0x00010700 File Offset: 0x0000E900
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x0600035D RID: 861 RVA: 0x00010718 File Offset: 0x0000E918
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum2_0 = Enum2.Down;
		base.Invalidate();
	}

	// Token: 0x0600035E RID: 862 RVA: 0x00010730 File Offset: 0x0000E930
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum2_0 = Enum2.None;
		base.Invalidate();
	}

	// Token: 0x0600035F RID: 863 RVA: 0x00010748 File Offset: 0x0000E948
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum2_0 = Enum2.Over;
		base.Invalidate();
	}

	// Token: 0x06000360 RID: 864 RVA: 0x00010760 File Offset: 0x0000E960
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		this.int_0 = e.X;
		base.Invalidate();
	}

	// Token: 0x06000361 RID: 865 RVA: 0x0001077C File Offset: 0x0000E97C
	protected virtual void OnClick(EventArgs e)
	{
		base.OnClick(e);
		FormWindowState windowState = base.FindForm().WindowState;
		if (windowState == FormWindowState.Normal)
		{
			base.FindForm().WindowState = FormWindowState.Maximized;
		}
		else if (windowState == FormWindowState.Maximized)
		{
			base.FindForm().WindowState = FormWindowState.Normal;
		}
	}

	// Token: 0x17000116 RID: 278
	// (get) Token: 0x06000362 RID: 866 RVA: 0x000107C0 File Offset: 0x0000E9C0
	// (set) Token: 0x06000363 RID: 867 RVA: 0x000107D8 File Offset: 0x0000E9D8
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

	// Token: 0x17000117 RID: 279
	// (get) Token: 0x06000364 RID: 868 RVA: 0x000107E4 File Offset: 0x0000E9E4
	// (set) Token: 0x06000365 RID: 869 RVA: 0x000107FC File Offset: 0x0000E9FC
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

	// Token: 0x06000366 RID: 870 RVA: 0x00010808 File Offset: 0x0000EA08
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Size = new Size(18, 18);
	}

	// Token: 0x06000367 RID: 871 RVA: 0x00010820 File Offset: 0x0000EA20
	public Control24()
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

	// Token: 0x06000368 RID: 872 RVA: 0x000108B0 File Offset: 0x0000EAB0
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
		if (base.FindForm().WindowState == FormWindowState.Maximized)
		{
			graphics2.DrawString("1", this.Font, new SolidBrush(this.Color_1), new Rectangle(1, 1, base.Width, base.Height), Class16.stringFormat_1);
		}
		else if (base.FindForm().WindowState == FormWindowState.Normal)
		{
			graphics2.DrawString("2", this.Font, new SolidBrush(this.Color_1), new Rectangle(1, 1, base.Width, base.Height), Class16.stringFormat_1);
		}
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

	// Token: 0x040001CA RID: 458
	private Enum2 enum2_0;

	// Token: 0x040001CB RID: 459
	private int int_0;

	// Token: 0x040001CC RID: 460
	private Color color_0;

	// Token: 0x040001CD RID: 461
	private Color color_1;
}
