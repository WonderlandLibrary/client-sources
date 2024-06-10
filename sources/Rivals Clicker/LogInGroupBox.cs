using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000020 RID: 32
	public class LogInGroupBox : ContainerControl
	{
		// Token: 0x170000BE RID: 190
		// (get) Token: 0x06000228 RID: 552 RVA: 0x0000DD0C File Offset: 0x0000BF0C
		// (set) Token: 0x06000229 RID: 553 RVA: 0x00002F89 File Offset: 0x00001189
		[Category("Colours")]
		public Color BorderColour
		{
			get
			{
				return this._BorderColour;
			}
			set
			{
				this._BorderColour = value;
			}
		}

		// Token: 0x170000BF RID: 191
		// (get) Token: 0x0600022A RID: 554 RVA: 0x0000DD24 File Offset: 0x0000BF24
		// (set) Token: 0x0600022B RID: 555 RVA: 0x00002F93 File Offset: 0x00001193
		[Category("Colours")]
		public Color TextColour
		{
			get
			{
				return this._TextColour;
			}
			set
			{
				this._TextColour = value;
			}
		}

		// Token: 0x170000C0 RID: 192
		// (get) Token: 0x0600022C RID: 556 RVA: 0x0000DD3C File Offset: 0x0000BF3C
		// (set) Token: 0x0600022D RID: 557 RVA: 0x00002F9D File Offset: 0x0000119D
		[Category("Colours")]
		public Color HeaderColour
		{
			get
			{
				return this._HeaderColour;
			}
			set
			{
				this._HeaderColour = value;
			}
		}

		// Token: 0x170000C1 RID: 193
		// (get) Token: 0x0600022E RID: 558 RVA: 0x0000DD54 File Offset: 0x0000BF54
		// (set) Token: 0x0600022F RID: 559 RVA: 0x00002FA7 File Offset: 0x000011A7
		[Category("Colours")]
		public Color MainColour
		{
			get
			{
				return this._MainColour;
			}
			set
			{
				this._MainColour = value;
			}
		}

		// Token: 0x06000230 RID: 560 RVA: 0x0000DD6C File Offset: 0x0000BF6C
		public LogInGroupBox()
		{
			this._MainColour = Color.FromArgb(47, 47, 47);
			this._HeaderColour = Color.FromArgb(42, 42, 42);
			this._TextColour = Color.FromArgb(255, 255, 255);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(160, 110);
			this.Font = new Font("Segoe UI", 10f, FontStyle.Bold);
		}

		// Token: 0x06000231 RID: 561 RVA: 0x0000DE10 File Offset: 0x0000C010
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Graphics graphics = g;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(Color.FromArgb(54, 54, 54));
			graphics.FillRectangle(new SolidBrush(this._MainColour), new Rectangle(0, 28, base.Width, base.Height));
			checked
			{
				graphics.FillRectangle(new SolidBrush(this._HeaderColour), new Rectangle(0, 0, (int)Math.Round((double)(unchecked(graphics.MeasureString(this.Text, this.Font).Width + 7f))), 28));
				graphics.DrawString(this.Text, this.Font, new SolidBrush(this._TextColour), new Point(5, 5));
				Point[] P = new Point[]
				{
					new Point(0, 0),
					new Point((int)Math.Round((double)(unchecked(graphics.MeasureString(this.Text, this.Font).Width + 7f))), 0),
					new Point((int)Math.Round((double)(unchecked(graphics.MeasureString(this.Text, this.Font).Width + 7f))), 28),
					new Point(base.Width - 1, 28),
					new Point(base.Width - 1, base.Height - 1),
					new Point(1, base.Height - 1),
					new Point(1, 1)
				};
				graphics.DrawLines(new Pen(this._BorderColour), P);
				graphics.DrawLine(new Pen(this._BorderColour, 2f), new Point(0, 28), new Point((int)Math.Round((double)(unchecked(graphics.MeasureString(this.Text, this.Font).Width + 7f))), 28));
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x040000DC RID: 220
		private Color _MainColour;

		// Token: 0x040000DD RID: 221
		private Color _HeaderColour;

		// Token: 0x040000DE RID: 222
		private Color _TextColour;

		// Token: 0x040000DF RID: 223
		private Color _BorderColour;
	}
}
