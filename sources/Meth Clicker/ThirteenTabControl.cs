using System;
using System.Drawing;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000011 RID: 17
	public class ThirteenTabControl : TabControl
	{
		// Token: 0x1700008B RID: 139
		// (get) Token: 0x060001A2 RID: 418 RVA: 0x0000E128 File Offset: 0x0000C328
		// (set) Token: 0x060001A3 RID: 419 RVA: 0x0000E130 File Offset: 0x0000C330
		public Color AccentColor
		{
			get
			{
				return this._AccentColor;
			}
			set
			{
				this._AccentColor = value;
				base.Invalidate();
			}
		}

		// Token: 0x14000011 RID: 17
		// (add) Token: 0x060001A4 RID: 420 RVA: 0x0000E140 File Offset: 0x0000C340
		// (remove) Token: 0x060001A5 RID: 421 RVA: 0x0000E178 File Offset: 0x0000C378
		public event ThirteenTabControl.ColorSchemeChangedEventHandler ColorSchemeChanged;

		// Token: 0x1700008C RID: 140
		// (get) Token: 0x060001A6 RID: 422 RVA: 0x0000E1AD File Offset: 0x0000C3AD
		// (set) Token: 0x060001A7 RID: 423 RVA: 0x0000E1B8 File Offset: 0x0000C3B8
		public ThirteenTabControl.ColorSchemes ColorScheme
		{
			get
			{
				return this._ColorScheme;
			}
			set
			{
				this._ColorScheme = value;
				ThirteenTabControl.ColorSchemeChangedEventHandler colorSchemeChangedEvent = this.ColorSchemeChangedEvent;
				if (colorSchemeChangedEvent != null)
				{
					colorSchemeChangedEvent();
				}
			}
		}

		// Token: 0x060001A8 RID: 424 RVA: 0x0000E1DC File Offset: 0x0000C3DC
		protected void OnColorSchemeChanged()
		{
			base.Invalidate();
			ThirteenTabControl.ColorSchemes colorScheme = this.ColorScheme;
			if (colorScheme != ThirteenTabControl.ColorSchemes.Light)
			{
				if (colorScheme == ThirteenTabControl.ColorSchemes.Dark)
				{
					this.ClearColor = Color.FromArgb(50, 50, 50);
					this.MainColor = Color.FromArgb(35, 35, 35);
					this.ForeColor = Color.White;
					return;
				}
			}
			else
			{
				this.ClearColor = Color.White;
				this.MainColor = Color.FromArgb(200, 200, 200);
				this.ForeColor = Color.Black;
			}
		}

		// Token: 0x060001A9 RID: 425 RVA: 0x0000E25C File Offset: 0x0000C45C
		public ThirteenTabControl()
		{
			this.ColorSchemeChanged += this.OnColorSchemeChanged;
			base.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
			base.SetStyle(ControlStyles.ResizeRedraw, true);
			base.SetStyle(ControlStyles.UserPaint, true);
			base.SetStyle(ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			int num;
			num++;
			this.BackColor = Color.FromArgb(50, 50, 50);
			this.ForeColor = Color.White;
			this.AccentColor = Color.DodgerBlue;
		}

		// Token: 0x060001AA RID: 426 RVA: 0x0000E2DC File Offset: 0x0000C4DC
		protected override void OnPaint(PaintEventArgs e)
		{
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphics = Graphics.FromImage(bitmap);
			base.OnPaint(e);
			try
			{
				base.SelectedTab.BackColor = this.MainColor;
			}
			catch (Exception ex)
			{
			}
			graphics.Clear(this.ClearColor);
			int num = base.TabPages.Count - 1;
			for (int i = 0; i <= num; i++)
			{
				Rectangle rectangle = new Rectangle(base.GetTabRect(i).X, base.GetTabRect(i).Y + 3, base.GetTabRect(i).Width + 2, base.GetTabRect(i).Height);
				graphics.FillRectangle(new SolidBrush(this.MainColor), rectangle);
				graphics.DrawString(base.TabPages[i].Text, new Font("Segoe UI Semilight", 9.75f), new SolidBrush(this.ForeColor), rectangle, new StringFormat
				{
					LineAlignment = StringAlignment.Center,
					Alignment = StringAlignment.Center
				});
			}
			graphics.FillRectangle(new SolidBrush(this.MainColor), 0, base.ItemSize.Height, base.Width, base.Height);
			if (base.SelectedIndex != -1)
			{
				Rectangle rectangle2 = new Rectangle(base.GetTabRect(base.SelectedIndex).X - 2, base.GetTabRect(base.SelectedIndex).Y, base.GetTabRect(base.SelectedIndex).Width + 4, base.GetTabRect(base.SelectedIndex).Height);
				graphics.FillRectangle(new SolidBrush(this.AccentColor), rectangle2);
				graphics.DrawString(base.TabPages[base.SelectedIndex].Text, new Font("Segoe UI Semilight", 9.75f), new SolidBrush(this.ForeColor), rectangle2, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}

		// Token: 0x040000DA RID: 218
		private Color _AccentColor;

		// Token: 0x040000DB RID: 219
		private Color MainColor;

		// Token: 0x040000DC RID: 220
		private Color ClearColor;

		// Token: 0x040000DE RID: 222
		private ThirteenTabControl.ColorSchemes _ColorScheme;

		// Token: 0x02000050 RID: 80
		public enum ColorSchemes
		{
			// Token: 0x040001C4 RID: 452
			Light,
			// Token: 0x040001C5 RID: 453
			Dark
		}

		// Token: 0x02000051 RID: 81
		// (Invoke) Token: 0x06000387 RID: 903
		public delegate void ColorSchemeChangedEventHandler();
	}
}
