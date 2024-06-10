using System;
using System.Drawing;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x0200000F RID: 15
	public class ThirteenButton : Button
	{
		// Token: 0x17000088 RID: 136
		// (get) Token: 0x0600018F RID: 399 RVA: 0x0000DCD0 File Offset: 0x0000BED0
		// (set) Token: 0x06000190 RID: 400 RVA: 0x0000DCD8 File Offset: 0x0000BED8
		public ThirteenButton.ColorSchemes ColorScheme
		{
			get
			{
				return this._ColorScheme;
			}
			set
			{
				this._ColorScheme = value;
				base.Invalidate();
			}
		}

		// Token: 0x06000191 RID: 401 RVA: 0x0000DCE7 File Offset: 0x0000BEE7
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = ThirteenButton.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000192 RID: 402 RVA: 0x0000DCFD File Offset: 0x0000BEFD
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = ThirteenButton.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000193 RID: 403 RVA: 0x0000DD13 File Offset: 0x0000BF13
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = ThirteenButton.MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000194 RID: 404 RVA: 0x0000DD29 File Offset: 0x0000BF29
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = ThirteenButton.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x17000089 RID: 137
		// (get) Token: 0x06000195 RID: 405 RVA: 0x0000DD3F File Offset: 0x0000BF3F
		// (set) Token: 0x06000196 RID: 406 RVA: 0x0000DD47 File Offset: 0x0000BF47
		public Color AccentColor
		{
			get
			{
				return this._AccentColor;
			}
			set
			{
				this._AccentColor = value;
				this.OnAccentColorChanged();
			}
		}

		// Token: 0x1400000F RID: 15
		// (add) Token: 0x06000197 RID: 407 RVA: 0x0000DD58 File Offset: 0x0000BF58
		// (remove) Token: 0x06000198 RID: 408 RVA: 0x0000DD90 File Offset: 0x0000BF90
		public event ThirteenButton.AccentColorChangedEventHandler AccentColorChanged;

		// Token: 0x06000199 RID: 409 RVA: 0x0000DDC8 File Offset: 0x0000BFC8
		public ThirteenButton()
		{
			this.AccentColorChanged += this.OnAccentColorChanged;
			this.State = ThirteenButton.MouseState.None;
			this.Font = new Font("Segoe UI Semilight", 9.75f);
			this.ForeColor = Color.White;
			this.BackColor = Color.FromArgb(50, 50, 50);
			this.AccentColor = Color.DodgerBlue;
			this.ColorScheme = ThirteenButton.ColorSchemes.Dark;
		}

		// Token: 0x0600019A RID: 410 RVA: 0x0000DE38 File Offset: 0x0000C038
		protected override void OnPaint(PaintEventArgs e)
		{
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphics = Graphics.FromImage(bitmap);
			base.OnPaint(e);
			ThirteenButton.ColorSchemes colorScheme = this.ColorScheme;
			Color color;
			if (colorScheme != ThirteenButton.ColorSchemes.Light)
			{
				if (colorScheme == ThirteenButton.ColorSchemes.Dark)
				{
					color = Color.FromArgb(50, 50, 50);
				}
			}
			else
			{
				color = Color.White;
			}
			switch (this.State)
			{
			case ThirteenButton.MouseState.None:
				graphics.Clear(color);
				break;
			case ThirteenButton.MouseState.Over:
				graphics.Clear(this.AccentColor);
				break;
			case ThirteenButton.MouseState.Down:
				graphics.Clear(this.AccentColor);
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(50, Color.Black)), new Rectangle(0, 0, base.Width - 1, base.Height - 1));
				break;
			}
			graphics.DrawRectangle(new Pen(Color.FromArgb(100, 100, 100)), new Rectangle(0, 0, base.Width - 1, base.Height - 1));
			StringFormat format = new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			};
			ThirteenButton.ColorSchemes colorScheme2 = this.ColorScheme;
			if (colorScheme2 != ThirteenButton.ColorSchemes.Light)
			{
				if (colorScheme2 == ThirteenButton.ColorSchemes.Dark)
				{
					graphics.DrawString(this.Text, this.Font, Brushes.White, new Rectangle(0, 0, base.Width - 1, base.Height - 1), format);
				}
			}
			else
			{
				graphics.DrawString(this.Text, this.Font, Brushes.Black, new Rectangle(0, 0, base.Width - 1, base.Height - 1), format);
			}
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}

		// Token: 0x0600019B RID: 411 RVA: 0x0000D7BA File Offset: 0x0000B9BA
		protected void OnAccentColorChanged()
		{
			base.Invalidate();
		}

		// Token: 0x040000D4 RID: 212
		private ThirteenButton.ColorSchemes _ColorScheme;

		// Token: 0x040000D5 RID: 213
		private ThirteenButton.MouseState State;

		// Token: 0x040000D6 RID: 214
		private Color _AccentColor;

		// Token: 0x0200004B RID: 75
		public enum MouseState
		{
			// Token: 0x040001BA RID: 442
			None,
			// Token: 0x040001BB RID: 443
			Over,
			// Token: 0x040001BC RID: 444
			Down
		}

		// Token: 0x0200004C RID: 76
		public enum ColorSchemes
		{
			// Token: 0x040001BE RID: 446
			Light,
			// Token: 0x040001BF RID: 447
			Dark
		}

		// Token: 0x0200004D RID: 77
		// (Invoke) Token: 0x0600037F RID: 895
		public delegate void AccentColorChangedEventHandler();
	}
}
