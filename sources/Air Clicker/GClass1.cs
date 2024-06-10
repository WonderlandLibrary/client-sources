using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000074 RID: 116
public class GClass1 : ListBox
{
	// Token: 0x0600055A RID: 1370 RVA: 0x0001B598 File Offset: 0x00019798
	public GClass1()
	{
		base.SetStyle(ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		this.DoubleBuffered = true;
		this.DrawMode = DrawMode.OwnerDrawFixed;
		this.ForeColor = Color.White;
		this.BackColor = Color.FromArgb(61, 61, 61);
		base.BorderStyle = BorderStyle.None;
		this.ItemHeight = 20;
	}

	// Token: 0x0600055B RID: 1371 RVA: 0x0001B5FC File Offset: 0x000197FC
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rectangle_ = checked(new Rectangle(0, 0, base.Width - 1, base.Height - 1));
		base.OnPaint(e);
		graphics.Clear(Color.Transparent);
		graphics.FillPath(new SolidBrush(Color.FromArgb(61, 61, 61)), Class22.smethod_0(rectangle_, 3));
		graphics.DrawPath(new Pen(Color.Black, 2f), Class22.smethod_0(rectangle_, 3));
		e.Graphics.DrawImage(bitmap, new Point(0, 0));
		graphics.Dispose();
		bitmap.Dispose();
	}

	// Token: 0x0600055C RID: 1372 RVA: 0x0001B6A8 File Offset: 0x000198A8
	protected virtual void OnDrawItem(DrawItemEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		graphics.TextRenderingHint = TextRenderingHint.AntiAlias;
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		graphics.SetClip(Class22.smethod_0(new Rectangle(0, 0, base.Width, base.Height), 3));
		graphics.Clear(Color.Transparent);
		checked
		{
			graphics.FillRectangle(new SolidBrush(this.BackColor), new Rectangle(e.Bounds.X, e.Bounds.Y - 1, e.Bounds.Width, e.Bounds.Height + 3));
			if (e.State.ToString().Contains("Selected,"))
			{
				LinearGradientBrush brush = new LinearGradientBrush(new Rectangle(e.Bounds.X, e.Bounds.Y + 1, e.Bounds.Width, e.Bounds.Height), Color.FromArgb(61, 61, 61), Color.FromArgb(41, 41, 31), 90f);
				graphics.FillRectangle(brush, new Rectangle(e.Bounds.X, e.Bounds.Y + 1, e.Bounds.Width, e.Bounds.Height));
				graphics.DrawRectangle(new Pen(Color.FromArgb(128, 128, 128)), new Rectangle(e.Bounds.X, e.Bounds.Y + 1, e.Bounds.Width, e.Bounds.Height));
				try
				{
					graphics.DrawString(base.Items[e.Index].ToString(), new Font("Segoe UI", 10f, FontStyle.Bold), new SolidBrush(Color.FromArgb(245, 245, 245)), new Rectangle(e.Bounds.X + 3, e.Bounds.Y + 1, e.Bounds.Width, e.Bounds.Height), new StringFormat
					{
						LineAlignment = StringAlignment.Center,
						Alignment = StringAlignment.Center
					});
					goto IL_399;
				}
				catch (Exception ex)
				{
					goto IL_399;
				}
			}
			LinearGradientBrush brush2 = new LinearGradientBrush(new Rectangle(e.Bounds.X, e.Bounds.Y + 1, e.Bounds.Width, e.Bounds.Height), Color.FromArgb(81, 81, 81), Color.FromArgb(61, 61, 61), 90f);
			graphics.FillRectangle(brush2, e.Bounds);
			try
			{
				graphics.DrawString(base.Items[e.Index].ToString(), new Font("Segoe UI", 10f, FontStyle.Regular), new SolidBrush(Color.FromArgb(245, 245, 245)), new Rectangle(e.Bounds.X + 3, e.Bounds.Y + 1, e.Bounds.Width, e.Bounds.Height), new StringFormat
				{
					LineAlignment = StringAlignment.Center,
					Alignment = StringAlignment.Center
				});
			}
			catch (Exception ex2)
			{
			}
			IL_399:
			graphics.DrawPath(new Pen(Color.FromArgb(61, 61, 61), 2f), Class22.smethod_0(new Rectangle(0, 0, base.Width - 1, base.Height - 1), 1));
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}
}
