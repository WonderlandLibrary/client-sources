using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x0200006F RID: 111
public class GControl14 : Control
{
	// Token: 0x17000185 RID: 389
	// (get) Token: 0x0600052D RID: 1325 RVA: 0x00019D08 File Offset: 0x00017F08
	// (set) Token: 0x0600052E RID: 1326 RVA: 0x00019D20 File Offset: 0x00017F20
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			if (value > this.int_1)
			{
				this.int_0 = this.int_1;
			}
			else if (value < 0)
			{
				this.int_0 = 0;
			}
			else
			{
				this.int_0 = value;
			}
			base.Invalidate();
		}
	}

	// Token: 0x17000186 RID: 390
	// (get) Token: 0x0600052F RID: 1327 RVA: 0x00019D58 File Offset: 0x00017F58
	// (set) Token: 0x06000530 RID: 1328 RVA: 0x00019D70 File Offset: 0x00017F70
	public int Int32_1
	{
		get
		{
			return this.int_1;
		}
		set
		{
			if (value < 1)
			{
				this.int_1 = 1;
			}
			else
			{
				this.int_1 = value;
			}
			if (value < this.int_0)
			{
				this.int_0 = this.int_1;
			}
			base.Invalidate();
		}
	}

	// Token: 0x06000531 RID: 1329 RVA: 0x00019DA8 File Offset: 0x00017FA8
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum8_0 = Enum8.Down;
		base.Invalidate();
	}

	// Token: 0x06000532 RID: 1330 RVA: 0x00019DC0 File Offset: 0x00017FC0
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x06000533 RID: 1331 RVA: 0x00019DD8 File Offset: 0x00017FD8
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x06000534 RID: 1332 RVA: 0x00019DF0 File Offset: 0x00017FF0
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum8_0 = Enum8.None;
		base.Invalidate();
	}

	// Token: 0x06000535 RID: 1333 RVA: 0x00019E08 File Offset: 0x00018008
	public GControl14()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		base.Size = new Size(160, 50);
		this.DoubleBuffered = true;
		this.int_1 = 100;
	}

	// Token: 0x06000536 RID: 1334 RVA: 0x00019E48 File Offset: 0x00018048
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			int num = (int)Math.Round(unchecked((double)(checked(base.Width - 1)) * ((double)this.int_0 / (double)this.int_1)));
			Rectangle rectangle = new Rectangle(1, 5, base.Width - 3, base.Height - 11);
			Rectangle rect = new Rectangle(0, 0, base.Width - 1, 5);
			Rectangle rect2 = new Rectangle(0, base.Height - 6, base.Width - 1, 5);
			Rectangle rectangle2 = new Rectangle(1, 5, num - 3, base.Height - 11);
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
				graphics.DrawString(this.Text, font, Brushes.White, rectangle, new StringFormat
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
				graphics.DrawString(this.Text, font, Brushes.White, rectangle, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			}
			case Enum8.Down:
				graphics.TranslateTransform(1f, 1f);
				graphics.DrawString(this.Text, font, Brushes.White, rectangle, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			}
			if (num != 0)
			{
				LinearGradientBrush brush8 = new LinearGradientBrush(rectangle2, Color.FromArgb(61, 61, 61), Color.FromArgb(41, 41, 41), 180f);
				graphics.FillPath(brush8, Class22.smethod_0(rectangle2, 3));
				graphics.DrawPath(new Pen(Color.FromArgb(0, 0, 0)), Class22.smethod_0(rectangle2, 3));
				graphics.DrawString(this.Text, font, Brushes.White, rectangle, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x040002AF RID: 687
	private Enum8 enum8_0;

	// Token: 0x040002B0 RID: 688
	private int int_0;

	// Token: 0x040002B1 RID: 689
	private int int_1;
}
