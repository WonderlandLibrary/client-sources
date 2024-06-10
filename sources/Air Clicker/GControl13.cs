using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x0200006E RID: 110
public class GControl13 : Control
{
	// Token: 0x0600052A RID: 1322 RVA: 0x00019C08 File Offset: 0x00017E08
	public GControl13()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.Transparent;
		base.Size = new Size(3, 200);
	}

	// Token: 0x0600052B RID: 1323 RVA: 0x00019C40 File Offset: 0x00017E40
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Width = 3;
	}

	// Token: 0x0600052C RID: 1324 RVA: 0x00019C50 File Offset: 0x00017E50
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
