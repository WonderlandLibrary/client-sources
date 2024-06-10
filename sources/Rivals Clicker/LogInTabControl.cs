using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000032 RID: 50
	public class LogInTabControl : TabControl
	{
		// Token: 0x17000113 RID: 275
		// (get) Token: 0x0600030B RID: 779 RVA: 0x00010FDC File Offset: 0x0000F1DC
		// (set) Token: 0x0600030C RID: 780 RVA: 0x0000355F File Offset: 0x0000175F
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

		// Token: 0x17000114 RID: 276
		// (get) Token: 0x0600030D RID: 781 RVA: 0x00010FF4 File Offset: 0x0000F1F4
		// (set) Token: 0x0600030E RID: 782 RVA: 0x00003569 File Offset: 0x00001769
		[Category("Colours")]
		public Color UpLineColour
		{
			get
			{
				return this._UpLineColour;
			}
			set
			{
				this._UpLineColour = value;
			}
		}

		// Token: 0x17000115 RID: 277
		// (get) Token: 0x0600030F RID: 783 RVA: 0x0001100C File Offset: 0x0000F20C
		// (set) Token: 0x06000310 RID: 784 RVA: 0x00003573 File Offset: 0x00001773
		[Category("Colours")]
		public Color HorizontalLineColour
		{
			get
			{
				return this._HorizLineColour;
			}
			set
			{
				this._HorizLineColour = value;
			}
		}

		// Token: 0x17000116 RID: 278
		// (get) Token: 0x06000311 RID: 785 RVA: 0x00011024 File Offset: 0x0000F224
		// (set) Token: 0x06000312 RID: 786 RVA: 0x0000357D File Offset: 0x0000177D
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

		// Token: 0x17000117 RID: 279
		// (get) Token: 0x06000313 RID: 787 RVA: 0x0001103C File Offset: 0x0000F23C
		// (set) Token: 0x06000314 RID: 788 RVA: 0x00003587 File Offset: 0x00001787
		[Category("Colours")]
		public Color BackTabColour
		{
			get
			{
				return this._BackTabColour;
			}
			set
			{
				this._BackTabColour = value;
			}
		}

		// Token: 0x17000118 RID: 280
		// (get) Token: 0x06000315 RID: 789 RVA: 0x00011054 File Offset: 0x0000F254
		// (set) Token: 0x06000316 RID: 790 RVA: 0x00003591 File Offset: 0x00001791
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._BaseColour;
			}
			set
			{
				this._BaseColour = value;
			}
		}

		// Token: 0x17000119 RID: 281
		// (get) Token: 0x06000317 RID: 791 RVA: 0x0001106C File Offset: 0x0000F26C
		// (set) Token: 0x06000318 RID: 792 RVA: 0x0000359B File Offset: 0x0000179B
		[Category("Colours")]
		public Color ActiveColour
		{
			get
			{
				return this._ActiveColour;
			}
			set
			{
				this._ActiveColour = value;
			}
		}

		// Token: 0x06000319 RID: 793 RVA: 0x000035A5 File Offset: 0x000017A5
		protected override void CreateHandle()
		{
			base.CreateHandle();
			base.Alignment = TabAlignment.Top;
		}

		// Token: 0x0600031A RID: 794 RVA: 0x00011084 File Offset: 0x0000F284
		public LogInTabControl()
		{
			this._TextColour = Color.FromArgb(255, 255, 255);
			this._BackTabColour = Color.FromArgb(54, 54, 54);
			this._BaseColour = Color.FromArgb(35, 35, 35);
			this._ActiveColour = Color.FromArgb(47, 47, 47);
			this._BorderColour = Color.FromArgb(30, 30, 30);
			this._UpLineColour = Color.FromArgb(0, 160, 199);
			this._HorizLineColour = Color.FromArgb(255, 0, 0);
			this.CenterSF = new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			};
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Font = new Font("Segoe UI", 10f);
			base.SizeMode = TabSizeMode.Normal;
			base.ItemSize = new Size(240, 32);
		}

		// Token: 0x0600031B RID: 795 RVA: 0x00011180 File Offset: 0x0000F380
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Graphics graphics = g;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.Clear(this._BaseColour);
			try
			{
				base.SelectedTab.BackColor = this._BackTabColour;
			}
			catch (Exception ex)
			{
			}
			try
			{
				base.SelectedTab.BorderStyle = BorderStyle.FixedSingle;
			}
			catch (Exception ex2)
			{
			}
			graphics.DrawRectangle(new Pen(this._BorderColour, 2f), new Rectangle(0, 0, base.Width, base.Height));
			checked
			{
				int num = base.TabCount - 1;
				for (int i = 0; i <= num; i++)
				{
					Rectangle Base = new Rectangle(new Point(base.GetTabRect(i).Location.X, base.GetTabRect(i).Location.Y), new Size(base.GetTabRect(i).Width, base.GetTabRect(i).Height));
					Rectangle BaseSize = new Rectangle(Base.Location, new Size(Base.Width, Base.Height));
					bool flag = i == base.SelectedIndex;
					if (flag)
					{
						graphics.FillRectangle(new SolidBrush(this._BaseColour), BaseSize);
						graphics.FillRectangle(new SolidBrush(this._ActiveColour), new Rectangle(Base.X + 1, Base.Y - 3, Base.Width, Base.Height + 5));
						graphics.DrawString(base.TabPages[i].Text, this.Font, new SolidBrush(this._TextColour), new Rectangle(Base.X + 7, Base.Y, Base.Width - 3, Base.Height), this.CenterSF);
						graphics.DrawLine(new Pen(this._HorizLineColour, 2f), new Point(Base.X + 3, (int)Math.Round(unchecked((double)Base.Height / 2.0 + 2.0))), new Point(Base.X + 9, (int)Math.Round(unchecked((double)Base.Height / 2.0 + 2.0))));
						graphics.DrawLine(new Pen(this._UpLineColour, 2f), new Point(Base.X + 3, Base.Y - 3), new Point(Base.X + 3, Base.Height + 5));
					}
					else
					{
						graphics.DrawString(base.TabPages[i].Text, this.Font, new SolidBrush(this._TextColour), BaseSize, this.CenterSF);
					}
				}
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
				graphics = null;
			}
		}

		// Token: 0x04000136 RID: 310
		private Color _TextColour;

		// Token: 0x04000137 RID: 311
		private Color _BackTabColour;

		// Token: 0x04000138 RID: 312
		private Color _BaseColour;

		// Token: 0x04000139 RID: 313
		private Color _ActiveColour;

		// Token: 0x0400013A RID: 314
		private Color _BorderColour;

		// Token: 0x0400013B RID: 315
		private Color _UpLineColour;

		// Token: 0x0400013C RID: 316
		private Color _HorizLineColour;

		// Token: 0x0400013D RID: 317
		private StringFormat CenterSF;
	}
}
