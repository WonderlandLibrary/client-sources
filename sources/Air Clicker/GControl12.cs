using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x0200006D RID: 109
public class GControl12 : Control
{
	// Token: 0x06000527 RID: 1319 RVA: 0x00019B08 File Offset: 0x00017D08
	public GControl12()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.Transparent;
		base.Size = new Size(200, 3);
	}

	// Token: 0x06000528 RID: 1320 RVA: 0x00019B40 File Offset: 0x00017D40
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 3;
	}

	// Token: 0x06000529 RID: 1321 RVA: 0x00019B50 File Offset: 0x00017D50
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rectangle_ = checked(new Rectangle(0, 0, base.Width - 1, base.Height - 1));
		base.OnPaint(e);
		graphics.Clear(this.BackColor);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		graphics.FillPath(new SolidBrush(Color.FromArgb(41, 41, 41)), Class22.smethod_0(rectangle_, 2));
		graphics.DrawPath(new Pen(Color.FromArgb(0, 0, 0)), Class22.smethod_0(rectangle_, 2));
		e.Graphics.DrawImage(bitmap, new Point(0, 0));
		graphics.Dispose();
		bitmap.Dispose();
	}
}
