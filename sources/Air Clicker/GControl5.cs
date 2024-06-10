using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000066 RID: 102
public class GControl5 : Control
{
	// Token: 0x06000500 RID: 1280 RVA: 0x00018A54 File Offset: 0x00016C54
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum8_0 = Enum8.Down;
		base.Invalidate();
	}

	// Token: 0x06000501 RID: 1281 RVA: 0x00018A6C File Offset: 0x00016C6C
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x06000502 RID: 1282 RVA: 0x00018A84 File Offset: 0x00016C84
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x06000503 RID: 1283 RVA: 0x00018A9C File Offset: 0x00016C9C
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum8_0 = Enum8.None;
		base.Invalidate();
	}

	// Token: 0x06000504 RID: 1284 RVA: 0x00018AB4 File Offset: 0x00016CB4
	public GControl5()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		this.DoubleBuffered = true;
		base.Size = new Size(160, 35);
		this.enum8_0 = Enum8.None;
	}

	// Token: 0x06000505 RID: 1285 RVA: 0x00018AF4 File Offset: 0x00016CF4
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			Rectangle rectangle = new Rectangle(1, 5, base.Width - 3, base.Height - 11);
			Rectangle rect = new Rectangle(0, 0, base.Width - 1, 5);
			Rectangle rect2 = new Rectangle(0, base.Height - 6, base.Width - 1, 5);
			Font font = new Font("Segoe UI", 11f, FontStyle.Bold);
			base.OnPaint(e);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			graphics.TextRenderingHint = TextRenderingHint.AntiAlias;
			graphics.Clear(this.BackColor);
			LinearGradientBrush brush = new LinearGradientBrush(rectangle, Color.FromArgb(61, 61, 61), Color.FromArgb(41, 41, 41), LinearGradientMode.Vertical);
			graphics.FillRectangle(brush, rectangle);
			graphics.DrawRectangle(new Pen(Brushes.Black), rectangle);
			LinearGradientBrush brush2 = new LinearGradientBrush(rect, Color.FromArgb(53, 53, 53), Color.FromArgb(62, 62, 62), LinearGradientMode.Vertical);
			graphics.FillRectangle(brush2, rect);
			graphics.DrawRectangle(new Pen(Brushes.Black), rect);
			LinearGradientBrush brush3 = new LinearGradientBrush(rect2, Color.FromArgb(53, 53, 53), Color.FromArgb(62, 62, 62), LinearGradientMode.Vertical);
			graphics.FillRectangle(brush3, rect2);
			graphics.DrawRectangle(new Pen(Brushes.Black), rect2);
			switch (this.enum8_0)
			{
			case Enum8.None:
			{
				LinearGradientBrush brush4 = new LinearGradientBrush(rectangle, Color.FromArgb(61, 61, 61), Color.FromArgb(41, 41, 41), LinearGradientMode.Vertical);
				graphics.FillRectangle(brush4, rectangle);
				graphics.DrawRectangle(new Pen(Brushes.Black), rectangle);
				LinearGradientBrush brush5 = new LinearGradientBrush(rect, Color.FromArgb(53, 53, 53), Color.FromArgb(62, 62, 62), LinearGradientMode.Vertical);
				graphics.FillRectangle(brush5, rect);
				graphics.DrawRectangle(new Pen(Brushes.Black), rect);
				LinearGradientBrush brush6 = new LinearGradientBrush(rect2, Color.FromArgb(53, 53, 53), Color.FromArgb(62, 62, 62), LinearGradientMode.Vertical);
				graphics.FillRectangle(brush6, rect2);
				graphics.DrawRectangle(new Pen(Brushes.Black), rect2);
				graphics.DrawString(this.Text, font, new SolidBrush(Color.FromArgb(245, 245, 245)), rectangle, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			}
			case Enum8.Over:
			{
				LinearGradientBrush brush7 = new LinearGradientBrush(rectangle, Color.FromArgb(41, 41, 41), Color.FromArgb(61, 61, 61), LinearGradientMode.Vertical);
				graphics.FillRectangle(brush7, rectangle);
				graphics.DrawRectangle(new Pen(Brushes.Black), rectangle);
				graphics.DrawString(this.Text, font, new SolidBrush(Color.FromArgb(245, 245, 245)), rectangle, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			}
			case Enum8.Down:
				graphics.TranslateTransform(1f, 1f);
				graphics.DrawString(this.Text, font, new SolidBrush(Color.FromArgb(245, 245, 245)), rectangle, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			}
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x040002A5 RID: 677
	private Enum8 enum8_0;
}
