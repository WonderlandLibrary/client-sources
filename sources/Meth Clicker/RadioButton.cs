using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x0200001F RID: 31
	[DefaultEvent("CheckedChanged")]
	internal class RadioButton : Control
	{
		// Token: 0x170000AF RID: 175
		// (get) Token: 0x06000247 RID: 583 RVA: 0x000113A9 File Offset: 0x0000F5A9
		// (set) Token: 0x06000248 RID: 584 RVA: 0x000113B4 File Offset: 0x0000F5B4
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
				RadioButton.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
				if (checkedChangedEvent != null)
				{
					checkedChangedEvent(this);
				}
				base.Invalidate();
			}
		}

		// Token: 0x14000015 RID: 21
		// (add) Token: 0x06000249 RID: 585 RVA: 0x000113E8 File Offset: 0x0000F5E8
		// (remove) Token: 0x0600024A RID: 586 RVA: 0x00011420 File Offset: 0x0000F620
		public event RadioButton.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x0600024B RID: 587 RVA: 0x00011455 File Offset: 0x0000F655
		protected override void OnClick(EventArgs e)
		{
			if (!this._Checked)
			{
				this.Checked = true;
			}
			base.OnClick(e);
		}

		// Token: 0x0600024C RID: 588 RVA: 0x00011470 File Offset: 0x0000F670
		private void InvalidateControls()
		{
			if (base.IsHandleCreated && this._Checked)
			{
				try
				{
					foreach (object obj in base.Parent.Controls)
					{
						Control control = (Control)obj;
						if (control != this && control is RadioButton)
						{
							((RadioButton)control).Checked = false;
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

		// Token: 0x0600024D RID: 589 RVA: 0x000114FC File Offset: 0x0000F6FC
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			this.InvalidateControls();
		}

		// Token: 0x170000B0 RID: 176
		// (get) Token: 0x0600024E RID: 590 RVA: 0x0001150A File Offset: 0x0000F70A
		// (set) Token: 0x0600024F RID: 591 RVA: 0x00011512 File Offset: 0x0000F712
		[Category("Options")]
		public RadioButton._Options Options
		{
			get
			{
				return this.O;
			}
			set
			{
				this.O = value;
			}
		}

		// Token: 0x06000250 RID: 592 RVA: 0x0001151B File Offset: 0x0000F71B
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 22;
		}

		// Token: 0x06000251 RID: 593 RVA: 0x0001152C File Offset: 0x0000F72C
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000252 RID: 594 RVA: 0x00011542 File Offset: 0x0000F742
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000253 RID: 595 RVA: 0x00011558 File Offset: 0x0000F758
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000254 RID: 596 RVA: 0x0001156E File Offset: 0x0000F76E
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000255 RID: 597 RVA: 0x00011584 File Offset: 0x0000F784
		public RadioButton()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._BorderColor = Helpers._FlatColor;
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Cursor = Cursors.Hand;
			base.Size = new Size(100, 22);
			this.BackColor = Color.FromArgb(60, 70, 73);
			this.Font = new Font("Segoe UI", 10f);
		}

		// Token: 0x06000256 RID: 598 RVA: 0x00011628 File Offset: 0x0000F828
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width - 1;
			this.H = base.Height - 1;
			Rectangle rect = new Rectangle(0, 2, base.Height - 5, base.Height - 5);
			Rectangle rect2 = new Rectangle(4, 6, this.H - 12, this.H - 12);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			RadioButton._Options o = this.O;
			if (o != RadioButton._Options.Style1)
			{
				if (o == RadioButton._Options.Style2)
				{
					g.FillEllipse(new SolidBrush(this._BaseColor), rect);
					MouseState state = this.State;
					if (state != MouseState.Over)
					{
						if (state == MouseState.Down)
						{
							g.DrawEllipse(new Pen(this._BorderColor), rect);
							g.FillEllipse(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
						}
					}
					else
					{
						g.DrawEllipse(new Pen(this._BorderColor), rect);
						g.FillEllipse(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
					}
					if (this.Checked)
					{
						g.FillEllipse(new SolidBrush(this._BorderColor), rect2);
					}
				}
			}
			else
			{
				g.FillEllipse(new SolidBrush(this._BaseColor), rect);
				MouseState state2 = this.State;
				if (state2 != MouseState.Over)
				{
					if (state2 == MouseState.Down)
					{
						g.DrawEllipse(new Pen(this._BorderColor), rect);
					}
				}
				else
				{
					g.DrawEllipse(new Pen(this._BorderColor), rect);
				}
				if (this.Checked)
				{
					g.FillEllipse(new SolidBrush(this._BorderColor), rect2);
				}
			}
			g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x0400012D RID: 301
		private MouseState State;

		// Token: 0x0400012E RID: 302
		private int W;

		// Token: 0x0400012F RID: 303
		private int H;

		// Token: 0x04000130 RID: 304
		private RadioButton._Options O;

		// Token: 0x04000131 RID: 305
		private bool _Checked;

		// Token: 0x04000133 RID: 307
		private Color _BaseColor;

		// Token: 0x04000134 RID: 308
		private Color _BorderColor;

		// Token: 0x04000135 RID: 309
		private Color _TextColor;

		// Token: 0x0200005B RID: 91
		// (Invoke) Token: 0x06000397 RID: 919
		public delegate void CheckedChangedEventHandler(object sender);

		// Token: 0x0200005C RID: 92
		[Flags]
		public enum _Options
		{
			// Token: 0x040001DE RID: 478
			Style1 = 0,
			// Token: 0x040001DF RID: 479
			Style2 = 1
		}
	}
}
