using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x0200001B RID: 27
	[DefaultEvent("CheckedChanged")]
	public class LogInRadioButton : Control
	{
		// Token: 0x170000A9 RID: 169
		// (get) Token: 0x060001DF RID: 479 RVA: 0x0000CE3C File Offset: 0x0000B03C
		// (set) Token: 0x060001E0 RID: 480 RVA: 0x00002D74 File Offset: 0x00000F74
		[Category("Colours")]
		public Color HighlightColour
		{
			get
			{
				return this._HoverColour;
			}
			set
			{
				this._HoverColour = value;
			}
		}

		// Token: 0x170000AA RID: 170
		// (get) Token: 0x060001E1 RID: 481 RVA: 0x0000CE54 File Offset: 0x0000B054
		// (set) Token: 0x060001E2 RID: 482 RVA: 0x00002D7E File Offset: 0x00000F7E
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._BackColour;
			}
			set
			{
				this._BackColour = value;
			}
		}

		// Token: 0x170000AB RID: 171
		// (get) Token: 0x060001E3 RID: 483 RVA: 0x0000CE6C File Offset: 0x0000B06C
		// (set) Token: 0x060001E4 RID: 484 RVA: 0x00002D88 File Offset: 0x00000F88
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

		// Token: 0x170000AC RID: 172
		// (get) Token: 0x060001E5 RID: 485 RVA: 0x0000CE84 File Offset: 0x0000B084
		// (set) Token: 0x060001E6 RID: 486 RVA: 0x00002D92 File Offset: 0x00000F92
		[Category("Colours")]
		public Color CheckedColour
		{
			get
			{
				return this._CheckedColour;
			}
			set
			{
				this._CheckedColour = value;
			}
		}

		// Token: 0x170000AD RID: 173
		// (get) Token: 0x060001E7 RID: 487 RVA: 0x0000CE9C File Offset: 0x0000B09C
		// (set) Token: 0x060001E8 RID: 488 RVA: 0x00002D9C File Offset: 0x00000F9C
		[Category("Colours")]
		public Color FontColour
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

		// Token: 0x14000002 RID: 2
		// (add) Token: 0x060001E9 RID: 489 RVA: 0x0000CEB4 File Offset: 0x0000B0B4
		// (remove) Token: 0x060001EA RID: 490 RVA: 0x0000CEEC File Offset: 0x0000B0EC
		public event LogInRadioButton.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x170000AE RID: 174
		// (get) Token: 0x060001EB RID: 491 RVA: 0x0000CF24 File Offset: 0x0000B124
		// (set) Token: 0x060001EC RID: 492 RVA: 0x0000CF3C File Offset: 0x0000B13C
		public bool Checked
		{
			get
			{
				return this._Checked;
			}
			set
			{
				this._Checked = value;
				this.InvalidateControls();
				LogInRadioButton.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
				if (checkedChangedEvent != null)
				{
					checkedChangedEvent(this);
				}
				base.Invalidate();
			}
		}

		// Token: 0x060001ED RID: 493 RVA: 0x0000CF74 File Offset: 0x0000B174
		protected override void OnClick(EventArgs e)
		{
			bool flag = !this._Checked;
			if (flag)
			{
				this.Checked = true;
			}
			base.OnClick(e);
		}

		// Token: 0x060001EE RID: 494 RVA: 0x0000CFA0 File Offset: 0x0000B1A0
		private void InvalidateControls()
		{
			bool flag = !base.IsHandleCreated || !this._Checked;
			if (!flag)
			{
				try
				{
					foreach (object obj in base.Parent.Controls)
					{
						Control C = (Control)obj;
						bool flag2 = C != this && C is LogInRadioButton;
						if (flag2)
						{
							((LogInRadioButton)C).Checked = false;
							base.Invalidate();
						}
					}
				}
				finally
				{
					IEnumerator enumerator;
					if (enumerator is IDisposable)
					{
						(enumerator as IDisposable).Dispose();
					}
				}
			}
		}

		// Token: 0x060001EF RID: 495 RVA: 0x00002DA6 File Offset: 0x00000FA6
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			this.InvalidateControls();
		}

		// Token: 0x060001F0 RID: 496 RVA: 0x00002BF6 File Offset: 0x00000DF6
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 22;
		}

		// Token: 0x060001F1 RID: 497 RVA: 0x00002DB7 File Offset: 0x00000FB7
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = DrawHelpers.MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060001F2 RID: 498 RVA: 0x00002DD0 File Offset: 0x00000FD0
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = DrawHelpers.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001F3 RID: 499 RVA: 0x00002DE9 File Offset: 0x00000FE9
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = DrawHelpers.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001F4 RID: 500 RVA: 0x00002E02 File Offset: 0x00001002
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = DrawHelpers.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060001F5 RID: 501 RVA: 0x0000D048 File Offset: 0x0000B248
		public LogInRadioButton()
		{
			this.State = DrawHelpers.MouseState.None;
			this._HoverColour = Color.FromArgb(50, 49, 51);
			this._CheckedColour = Color.FromArgb(173, 173, 174);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._BackColour = Color.FromArgb(54, 54, 54);
			this._TextColour = Color.FromArgb(255, 255, 255);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Cursor = Cursors.Hand;
			base.Size = new Size(100, 22);
		}

		// Token: 0x060001F6 RID: 502 RVA: 0x0000D0FC File Offset: 0x0000B2FC
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			checked
			{
				Rectangle Base = new Rectangle(1, 1, base.Height - 2, base.Height - 2);
				Rectangle Circle = new Rectangle(6, 6, base.Height - 12, base.Height - 12);
				Graphics graphics = G;
				graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
				graphics.SmoothingMode = SmoothingMode.HighQuality;
				graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
				graphics.Clear(this._BackColour);
				graphics.FillEllipse(new SolidBrush(this._BackColour), Base);
				graphics.DrawEllipse(new Pen(this._BorderColour, 2f), Base);
				bool @checked = this.Checked;
				if (@checked)
				{
					DrawHelpers.MouseState state = this.State;
					if (state == DrawHelpers.MouseState.Over)
					{
						graphics.FillEllipse(new SolidBrush(this._HoverColour), new Rectangle(2, 2, base.Height - 4, base.Height - 4));
					}
					graphics.FillEllipse(new SolidBrush(this._CheckedColour), Circle);
				}
				else
				{
					DrawHelpers.MouseState state2 = this.State;
					if (state2 == DrawHelpers.MouseState.Over)
					{
						graphics.FillEllipse(new SolidBrush(this._HoverColour), new Rectangle(2, 2, base.Height - 4, base.Height - 4));
					}
				}
				graphics.DrawString(this.Text, this.Font, new SolidBrush(this._TextColour), new Rectangle(24, 3, base.Width, base.Height), new StringFormat
				{
					Alignment = StringAlignment.Near,
					LineAlignment = StringAlignment.Near
				});
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x040000C1 RID: 193
		private bool _Checked;

		// Token: 0x040000C2 RID: 194
		private DrawHelpers.MouseState State;

		// Token: 0x040000C3 RID: 195
		private Color _HoverColour;

		// Token: 0x040000C4 RID: 196
		private Color _CheckedColour;

		// Token: 0x040000C5 RID: 197
		private Color _BorderColour;

		// Token: 0x040000C6 RID: 198
		private Color _BackColour;

		// Token: 0x040000C7 RID: 199
		private Color _TextColour;

		// Token: 0x0200001C RID: 28
		// (Invoke) Token: 0x060001FA RID: 506
		public delegate void CheckedChangedEventHandler(object sender);
	}
}
