using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x02000068 RID: 104
public class GControl7 : ContainerControl
{
	// Token: 0x1700017C RID: 380
	// (get) Token: 0x06000509 RID: 1289 RVA: 0x00018F50 File Offset: 0x00017150
	// (set) Token: 0x0600050A RID: 1290 RVA: 0x00018F58 File Offset: 0x00017158
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x0600050B RID: 1291 RVA: 0x00018F68 File Offset: 0x00017168
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Invalidate();
	}

	// Token: 0x0600050C RID: 1292 RVA: 0x00018F78 File Offset: 0x00017178
	public GControl7()
	{
		this.bool_0 = true;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		this.DoubleBuffered = true;
		base.Size = new Size(240, 160);
	}

	// Token: 0x0600050D RID: 1293 RVA: 0x00018FC8 File Offset: 0x000171C8
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Pen pen = new Pen(Color.FromArgb(255, 255, 255), 2f);
		checked
		{
			Rectangle rect = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
			base.OnPaint(e);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			graphics.Clear(this.BackColor);
			LinearGradientBrush brush = new LinearGradientBrush(rect, Color.FromArgb(10, 10, 10), Color.FromArgb(10, 10, 10), 90f);
			graphics.FillRectangle(brush, rect);
			graphics.DrawRectangle(new Pen(Brushes.Black, 2f), rect);
			if (this.bool_0)
			{
				graphics.DrawString(this.Text, new Font("Segoe UI", 11f, FontStyle.Bold), new SolidBrush(Color.FromArgb(245, 245, 245)), new Rectangle(0, 3, base.Width - 1, 30), new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				graphics.DrawLine(pen, 10, 30, base.Width - 11, 30);
			}
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x040002A6 RID: 678
	private bool bool_0;
}
