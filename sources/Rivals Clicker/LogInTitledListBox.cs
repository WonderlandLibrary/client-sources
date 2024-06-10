using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

namespace svchost
{
	// Token: 0x02000026 RID: 38
	public class LogInTitledListBox : Control
	{
		// Token: 0x170000E5 RID: 229
		// (get) Token: 0x0600027C RID: 636 RVA: 0x0000314E File Offset: 0x0000134E
		// (set) Token: 0x0600027D RID: 637 RVA: 0x0000EE70 File Offset: 0x0000D070
		private virtual ListBox ListB
		{
			[CompilerGenerated]
			get
			{
				return this._ListB;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				DrawItemEventHandler value2 = new DrawItemEventHandler(this.Drawitem);
				ListBox listB = this._ListB;
				if (listB != null)
				{
					listB.DrawItem -= value2;
				}
				this._ListB = value;
				listB = this._ListB;
				if (listB != null)
				{
					listB.DrawItem += value2;
				}
			}
		}

		// Token: 0x170000E6 RID: 230
		// (get) Token: 0x0600027E RID: 638 RVA: 0x0000EEB4 File Offset: 0x0000D0B4
		// (set) Token: 0x0600027F RID: 639 RVA: 0x00003158 File Offset: 0x00001358
		[Category("Control")]
		public Font TitleFont
		{
			get
			{
				return this._TitleFont;
			}
			set
			{
				this._TitleFont = value;
			}
		}

		// Token: 0x170000E7 RID: 231
		// (get) Token: 0x06000280 RID: 640 RVA: 0x0000EECC File Offset: 0x0000D0CC
		// (set) Token: 0x06000281 RID: 641 RVA: 0x00003162 File Offset: 0x00001362
		[Category("Control")]
		public string[] Items
		{
			get
			{
				return this._Items;
			}
			set
			{
				this._Items = value;
				this.ListB.Items.Clear();
				this.ListB.Items.AddRange(value);
				base.Invalidate();
			}
		}

		// Token: 0x170000E8 RID: 232
		// (get) Token: 0x06000282 RID: 642 RVA: 0x0000EEE4 File Offset: 0x0000D0E4
		// (set) Token: 0x06000283 RID: 643 RVA: 0x00003196 File Offset: 0x00001396
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

		// Token: 0x170000E9 RID: 233
		// (get) Token: 0x06000284 RID: 644 RVA: 0x0000EEFC File Offset: 0x0000D0FC
		// (set) Token: 0x06000285 RID: 645 RVA: 0x000031A0 File Offset: 0x000013A0
		[Category("Colours")]
		public Color SelectedColour
		{
			get
			{
				return this._SelectedColour;
			}
			set
			{
				this._SelectedColour = value;
			}
		}

		// Token: 0x170000EA RID: 234
		// (get) Token: 0x06000286 RID: 646 RVA: 0x0000EF14 File Offset: 0x0000D114
		// (set) Token: 0x06000287 RID: 647 RVA: 0x000031AA File Offset: 0x000013AA
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

		// Token: 0x170000EB RID: 235
		// (get) Token: 0x06000288 RID: 648 RVA: 0x0000EF2C File Offset: 0x0000D12C
		// (set) Token: 0x06000289 RID: 649 RVA: 0x000031B4 File Offset: 0x000013B4
		[Category("Colours")]
		public Color ListBaseColour
		{
			get
			{
				return this._ListBaseColour;
			}
			set
			{
				this._ListBaseColour = value;
			}
		}

		// Token: 0x170000EC RID: 236
		// (get) Token: 0x0600028A RID: 650 RVA: 0x0000EF44 File Offset: 0x0000D144
		// (set) Token: 0x0600028B RID: 651 RVA: 0x000031BE File Offset: 0x000013BE
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

		// Token: 0x170000ED RID: 237
		// (get) Token: 0x0600028C RID: 652 RVA: 0x0000EF5C File Offset: 0x0000D15C
		public string SelectedItem
		{
			get
			{
				return Conversions.ToString(this.ListB.SelectedItem);
			}
		}

		// Token: 0x170000EE RID: 238
		// (get) Token: 0x0600028D RID: 653 RVA: 0x0000EF80 File Offset: 0x0000D180
		public int SelectedIndex
		{
			get
			{
				return this.ListB.SelectedIndex;
			}
		}

		// Token: 0x0600028E RID: 654 RVA: 0x000031C8 File Offset: 0x000013C8
		public void Clear()
		{
			this.ListB.Items.Clear();
		}

		// Token: 0x0600028F RID: 655 RVA: 0x0000EFA0 File Offset: 0x0000D1A0
		public void ClearSelected()
		{
			checked
			{
				int num = this.ListB.SelectedItems.Count - 1;
				for (int i = num; i >= 0; i += -1)
				{
					this.ListB.Items.Remove(RuntimeHelpers.GetObjectValue(this.ListB.SelectedItems[i]));
				}
			}
		}

		// Token: 0x06000290 RID: 656 RVA: 0x0000EFF4 File Offset: 0x0000D1F4
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			bool flag = !base.Controls.Contains(this.ListB);
			if (flag)
			{
				base.Controls.Add(this.ListB);
			}
		}

		// Token: 0x06000291 RID: 657 RVA: 0x000031DC File Offset: 0x000013DC
		public void AddRange(object[] items)
		{
			this.ListB.Items.Remove("");
			this.ListB.Items.AddRange(items);
		}

		// Token: 0x06000292 RID: 658 RVA: 0x00003207 File Offset: 0x00001407
		public void AddItem(object item)
		{
			this.ListB.Items.Remove("");
			this.ListB.Items.Add(RuntimeHelpers.GetObjectValue(item));
		}

		// Token: 0x06000293 RID: 659 RVA: 0x0000F038 File Offset: 0x0000D238
		public void Drawitem(object sender, DrawItemEventArgs e)
		{
			bool flag = e.Index < 0;
			checked
			{
				if (!flag)
				{
					e.DrawBackground();
					e.DrawFocusRectangle();
					Graphics graphics = e.Graphics;
					graphics.SmoothingMode = SmoothingMode.HighQuality;
					graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
					graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
					graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
					bool flag2 = Strings.InStr(e.State.ToString(), "Selected,", CompareMethod.Binary) > 0;
					if (flag2)
					{
						graphics.FillRectangle(new SolidBrush(this._SelectedColour), new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height - 1));
						graphics.DrawString(" " + this.ListB.Items[e.Index].ToString(), new Font("Segoe UI", 9f, FontStyle.Bold), new SolidBrush(this._TextColour), (float)e.Bounds.X, (float)(e.Bounds.Y + 2));
					}
					else
					{
						graphics.FillRectangle(new SolidBrush(this._ListBaseColour), new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height));
						graphics.DrawString(" " + this.ListB.Items[e.Index].ToString(), new Font("Segoe UI", 8f), new SolidBrush(this._TextColour), (float)e.Bounds.X, (float)(e.Bounds.Y + 2));
					}
					graphics.Dispose();
				}
			}
		}

		// Token: 0x06000294 RID: 660 RVA: 0x0000F244 File Offset: 0x0000D444
		public LogInTitledListBox()
		{
			this.ListB = new ListBox();
			this._Items = new string[]
			{
				""
			};
			this._BaseColour = Color.FromArgb(42, 42, 42);
			this._SelectedColour = Color.FromArgb(55, 55, 55);
			this._ListBaseColour = Color.FromArgb(47, 47, 47);
			this._TextColour = Color.FromArgb(255, 255, 255);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._TitleFont = new Font("Segeo UI", 10f, FontStyle.Bold);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.ListB.DrawMode = DrawMode.OwnerDrawFixed;
			this.ListB.ScrollAlwaysVisible = false;
			this.ListB.HorizontalScrollbar = false;
			this.ListB.BorderStyle = BorderStyle.None;
			this.ListB.BackColor = this.BaseColour;
			this.ListB.Location = new Point(3, 28);
			this.ListB.Font = new Font("Segoe UI", 8f);
			this.ListB.ItemHeight = 20;
			this.ListB.Items.Clear();
			this.ListB.IntegralHeight = false;
			base.Size = new Size(130, 100);
		}

		// Token: 0x06000295 RID: 661 RVA: 0x0000F3B8 File Offset: 0x0000D5B8
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			Rectangle Base = new Rectangle(0, 0, base.Width, base.Height);
			Graphics graphics = G;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.Clear(this.BackColor);
			checked
			{
				this.ListB.Size = new Size(base.Width - 6, base.Height - 30);
				graphics.FillRectangle(new SolidBrush(this.BaseColour), Base);
				graphics.DrawRectangle(new Pen(this._BorderColour, 3f), new Rectangle(0, 0, base.Width, base.Height - 1));
				graphics.DrawLine(new Pen(this._BorderColour, 2f), new Point(0, 27), new Point(base.Width - 1, 27));
				graphics.DrawString(this.Text, this._TitleFont, new SolidBrush(this._TextColour), new Rectangle(2, 5, base.Width - 5, 20), new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x040000FD RID: 253
		private string[] _Items;

		// Token: 0x040000FE RID: 254
		private Color _BaseColour;

		// Token: 0x040000FF RID: 255
		private Color _SelectedColour;

		// Token: 0x04000100 RID: 256
		private Color _ListBaseColour;

		// Token: 0x04000101 RID: 257
		private Color _TextColour;

		// Token: 0x04000102 RID: 258
		private Color _BorderColour;

		// Token: 0x04000103 RID: 259
		private Font _TitleFont;
	}
}
