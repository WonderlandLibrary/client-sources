using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace svchost
{
	// Token: 0x02000033 RID: 51
	public class LogInTrackBar : Control
	{
		// Token: 0x1700011A RID: 282
		// (get) Token: 0x0600031C RID: 796 RVA: 0x000114A0 File Offset: 0x0000F6A0
		// (set) Token: 0x0600031D RID: 797 RVA: 0x000035B7 File Offset: 0x000017B7
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

		// Token: 0x1700011B RID: 283
		// (get) Token: 0x0600031E RID: 798 RVA: 0x000114B8 File Offset: 0x0000F6B8
		// (set) Token: 0x0600031F RID: 799 RVA: 0x000035C1 File Offset: 0x000017C1
		[Category("Colours")]
		public Color BarBaseColour
		{
			get
			{
				return this._BarBaseColour;
			}
			set
			{
				this._BarBaseColour = value;
			}
		}

		// Token: 0x1700011C RID: 284
		// (get) Token: 0x06000320 RID: 800 RVA: 0x000114D0 File Offset: 0x0000F6D0
		// (set) Token: 0x06000321 RID: 801 RVA: 0x000035CB File Offset: 0x000017CB
		[Category("Colours")]
		public Color StripColour
		{
			get
			{
				return this._StripColour;
			}
			set
			{
				this._StripColour = value;
			}
		}

		// Token: 0x1700011D RID: 285
		// (get) Token: 0x06000322 RID: 802 RVA: 0x000114E8 File Offset: 0x0000F6E8
		// (set) Token: 0x06000323 RID: 803 RVA: 0x000035D5 File Offset: 0x000017D5
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

		// Token: 0x1700011E RID: 286
		// (get) Token: 0x06000324 RID: 804 RVA: 0x00011500 File Offset: 0x0000F700
		// (set) Token: 0x06000325 RID: 805 RVA: 0x000035DF File Offset: 0x000017DF
		[Category("Colours")]
		public Color StripAmountColour
		{
			get
			{
				return this._StripAmountColour;
			}
			set
			{
				this._StripAmountColour = value;
			}
		}

		// Token: 0x1700011F RID: 287
		// (get) Token: 0x06000326 RID: 806 RVA: 0x00011518 File Offset: 0x0000F718
		// (set) Token: 0x06000327 RID: 807 RVA: 0x00011530 File Offset: 0x0000F730
		public int Maximum
		{
			get
			{
				return this._Maximum;
			}
			set
			{
				bool flag = value > 0;
				if (flag)
				{
					this._Maximum = value;
				}
				bool flag2 = value < this._Value;
				if (flag2)
				{
					this._Value = value;
				}
				base.Invalidate();
			}
		}

		// Token: 0x14000005 RID: 5
		// (add) Token: 0x06000328 RID: 808 RVA: 0x00011568 File Offset: 0x0000F768
		// (remove) Token: 0x06000329 RID: 809 RVA: 0x000115A0 File Offset: 0x0000F7A0
		public event LogInTrackBar.ValueChangedEventHandler ValueChanged;

		// Token: 0x17000120 RID: 288
		// (get) Token: 0x0600032A RID: 810 RVA: 0x000115D8 File Offset: 0x0000F7D8
		// (set) Token: 0x0600032B RID: 811 RVA: 0x000115F0 File Offset: 0x0000F7F0
		public int Value
		{
			get
			{
				return this._Value;
			}
			set
			{
				bool flag = value == this._Value;
				if (!flag)
				{
					flag = (value < 0);
					if (flag)
					{
						this._Value = 0;
					}
					else
					{
						flag = (value > this._Maximum);
						if (flag)
						{
							this._Value = this._Maximum;
						}
						else
						{
							this._Value = value;
						}
					}
					base.Invalidate();
					LogInTrackBar.ValueChangedEventHandler valueChangedEvent = this.ValueChangedEvent;
					if (valueChangedEvent != null)
					{
						valueChangedEvent();
					}
				}
			}
		}

		// Token: 0x0600032C RID: 812 RVA: 0x0001165C File Offset: 0x0000F85C
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			Rectangle MovementPoint = new Rectangle(new Point(e.Location.X, e.Location.Y), new Size(1, 1));
			checked
			{
				Rectangle Bar = new Rectangle(10, 10, base.Width - 21, base.Height - 21);
				bool flag = new Rectangle(new Point(Bar.X + (int)Math.Round(unchecked((double)Bar.Width * ((double)this.Value / (double)this.Maximum))) - (int)Math.Round(unchecked((double)this.Track.Width / 2.0 - 1.0)), 0), new Size(this.Track.Width, base.Height)).IntersectsWith(MovementPoint);
				if (flag)
				{
					this.CaptureMovement = true;
				}
			}
		}

		// Token: 0x0600032D RID: 813 RVA: 0x000035E9 File Offset: 0x000017E9
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.CaptureMovement = false;
		}

		// Token: 0x0600032E RID: 814 RVA: 0x00011744 File Offset: 0x0000F944
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			bool captureMovement = this.CaptureMovement;
			checked
			{
				if (captureMovement)
				{
					Point MovementPoint = new Point(e.X, e.Y);
					Rectangle Bar = new Rectangle(10, 10, base.Width - 21, base.Height - 21);
					this.Value = (int)Math.Round(unchecked((double)this.Maximum * ((double)(checked(MovementPoint.X - Bar.X)) / (double)Bar.Width)));
				}
			}
		}

		// Token: 0x0600032F RID: 815 RVA: 0x000035FB File Offset: 0x000017FB
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.CaptureMovement = false;
		}

		// Token: 0x06000330 RID: 816 RVA: 0x000117C4 File Offset: 0x0000F9C4
		public LogInTrackBar()
		{
			this._Maximum = 10;
			this._Value = 0;
			this.CaptureMovement = false;
			this.Bar = checked(new Rectangle(0, 10, base.Width - 21, base.Height - 21));
			this.Track = new Size(25, 14);
			this._TextColour = Color.FromArgb(255, 255, 255);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._BarBaseColour = Color.FromArgb(47, 47, 47);
			this._StripColour = Color.FromArgb(42, 42, 42);
			this._StripAmountColour = Color.FromArgb(255, 0, 0);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.Selectable | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(54, 54, 54);
		}

		// Token: 0x06000331 RID: 817 RVA: 0x000118A4 File Offset: 0x0000FAA4
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Graphics graphics = g;
			graphics.SmoothingMode = SmoothingMode.AntiAlias;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			checked
			{
				this.Bar = new Rectangle(13, 11, base.Width - 27, base.Height - 21);
				graphics.Clear(this.BackColor);
				graphics.TextRenderingHint = TextRenderingHint.AntiAliasGridFit;
				graphics.FillRectangle(new SolidBrush(this._StripColour), new Rectangle(3, (int)Math.Round(unchecked((double)base.Height / 2.0 - 4.0)), base.Width - 5, 8));
				graphics.DrawRectangle(new Pen(this._BorderColour, 2f), new Rectangle(4, (int)Math.Round(unchecked((double)base.Height / 2.0 - 4.0)), base.Width - 5, 8));
				graphics.FillRectangle(new SolidBrush(this._StripAmountColour), new Rectangle(4, (int)Math.Round(unchecked((double)base.Height / 2.0 - 4.0)), (int)Math.Round(unchecked((double)this.Bar.Width * ((double)this.Value / (double)this.Maximum))) + (int)Math.Round((double)this.Track.Width / 2.0), 8));
				graphics.FillRectangle(new SolidBrush(this._BarBaseColour), this.Bar.X + (int)Math.Round(unchecked((double)this.Bar.Width * ((double)this.Value / (double)this.Maximum))) - (int)Math.Round((double)this.Track.Width / 2.0), this.Bar.Y + (int)Math.Round((double)this.Bar.Height / 2.0) - (int)Math.Round((double)this.Track.Height / 2.0), this.Track.Width, this.Track.Height);
				graphics.DrawRectangle(new Pen(this._BorderColour, 2f), this.Bar.X + (int)Math.Round(unchecked((double)this.Bar.Width * ((double)this.Value / (double)this.Maximum))) - (int)Math.Round((double)this.Track.Width / 2.0), this.Bar.Y + (int)Math.Round((double)this.Bar.Height / 2.0) - (int)Math.Round((double)this.Track.Height / 2.0), this.Track.Width, this.Track.Height);
				graphics.DrawString(Conversions.ToString(this._Value), new Font("Segoe UI", 6.5f, FontStyle.Regular), new SolidBrush(this._TextColour), new Rectangle(this.Bar.X + (int)Math.Round(unchecked((double)this.Bar.Width * ((double)this.Value / (double)this.Maximum))) - (int)Math.Round((double)this.Track.Width / 2.0), this.Bar.Y + (int)Math.Round((double)this.Bar.Height / 2.0) - (int)Math.Round((double)this.Track.Height / 2.0), this.Track.Width - 1, this.Track.Height), new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x0400013E RID: 318
		private int _Maximum;

		// Token: 0x0400013F RID: 319
		private int _Value;

		// Token: 0x04000140 RID: 320
		private bool CaptureMovement;

		// Token: 0x04000141 RID: 321
		private Rectangle Bar;

		// Token: 0x04000142 RID: 322
		private Size Track;

		// Token: 0x04000143 RID: 323
		private Color _TextColour;

		// Token: 0x04000144 RID: 324
		private Color _BorderColour;

		// Token: 0x04000145 RID: 325
		private Color _BarBaseColour;

		// Token: 0x04000146 RID: 326
		private Color _StripColour;

		// Token: 0x04000147 RID: 327
		private Color _StripAmountColour;

		// Token: 0x02000034 RID: 52
		// (Invoke) Token: 0x06000335 RID: 821
		public delegate void ValueChangedEventHandler();
	}
}
