using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x02000071 RID: 113
public class GControl16 : TabControl
{
	// Token: 0x06000539 RID: 1337 RVA: 0x0001A5DC File Offset: 0x000187DC
	public GControl16()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		this.DoubleBuffered = true;
		base.SizeMode = TabSizeMode.Fixed;
		base.Alignment = TabAlignment.Left;
		base.ItemSize = new Size(35, 100);
	}

	// Token: 0x0600053A RID: 1338 RVA: 0x0001A62C File Offset: 0x0001882C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			Rectangle rect = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
			LinearGradientBrush brush = new LinearGradientBrush(rect, Color.FromArgb(61, 61, 61), Color.FromArgb(41, 41, 41), 90f);
			LinearGradientBrush brush2 = new LinearGradientBrush(rect, Color.FromArgb(81, 81, 81), Color.FromArgb(61, 61, 61), 90f);
			try
			{
				base.SelectedTab.BackColor = Color.FromArgb(61, 61, 61);
			}
			catch (Exception ex)
			{
			}
			base.OnPaint(e);
			graphics.Clear(this.BackColor);
			graphics.FillRectangle(brush, rect);
			graphics.DrawRectangle(new Pen(Brushes.Black, 2f), rect);
			int num = base.TabCount - 1;
			for (int i = 0; i <= num; i++)
			{
				Rectangle r = new Rectangle(new Point(base.GetTabRect(i).Location.X + 1, base.GetTabRect(i).Location.Y), new Size(base.GetTabRect(i).Width - 1, base.GetTabRect(i).Height));
				if (i == base.SelectedIndex)
				{
					Rectangle rectangle_ = new Rectangle(new Point(base.GetTabRect(i).Location.X + 1, base.GetTabRect(i).Location.Y + 1), new Size(base.GetTabRect(i).Width - 1, base.GetTabRect(i).Height - 1));
					graphics.FillPath(brush, Class22.smethod_0(rectangle_, 2));
					graphics.DrawPath(new Pen(Brushes.Black), Class22.smethod_0(rectangle_, 2));
					graphics.DrawString(base.TabPages[i].Text, new Font("Segoe UI", 10f, FontStyle.Bold), new SolidBrush(Color.FromArgb(245, 245, 245)), r, new StringFormat
					{
						LineAlignment = StringAlignment.Center,
						Alignment = StringAlignment.Center
					});
				}
				else
				{
					Rectangle rectangle_2 = new Rectangle(new Point(base.GetTabRect(i).Location.X + 4, base.GetTabRect(i).Location.Y + 1), new Size(base.GetTabRect(i).Width - 4, base.GetTabRect(i).Height - 1));
					graphics.FillPath(brush2, Class22.smethod_0(rectangle_2, 2));
					graphics.DrawPath(new Pen(Brushes.Black), Class22.smethod_0(rectangle_2, 2));
					graphics.DrawString(base.TabPages[i].Text, new Font("Segoe UI", 10f, FontStyle.Regular), new SolidBrush(Color.FromArgb(245, 245, 245)), r, new StringFormat
					{
						LineAlignment = StringAlignment.Center,
						Alignment = StringAlignment.Center
					});
				}
			}
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}
}
