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
	// Token: 0x02000025 RID: 37
	public class LogInListBox : Control
	{
		// Token: 0x170000DC RID: 220
		// (get) Token: 0x06000264 RID: 612 RVA: 0x0000306F File Offset: 0x0000126F
		// (set) Token: 0x06000265 RID: 613 RVA: 0x0000E898 File Offset: 0x0000CA98
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

		// Token: 0x170000DD RID: 221
		// (get) Token: 0x06000266 RID: 614 RVA: 0x0000E8DC File Offset: 0x0000CADC
		// (set) Token: 0x06000267 RID: 615 RVA: 0x00003079 File Offset: 0x00001279
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

		// Token: 0x170000DE RID: 222
		// (get) Token: 0x06000268 RID: 616 RVA: 0x0000E8F4 File Offset: 0x0000CAF4
		// (set) Token: 0x06000269 RID: 617 RVA: 0x000030AD File Offset: 0x000012AD
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

		// Token: 0x170000DF RID: 223
		// (get) Token: 0x0600026A RID: 618 RVA: 0x0000E90C File Offset: 0x0000CB0C
		// (set) Token: 0x0600026B RID: 619 RVA: 0x000030B7 File Offset: 0x000012B7
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

		// Token: 0x170000E0 RID: 224
		// (get) Token: 0x0600026C RID: 620 RVA: 0x0000E924 File Offset: 0x0000CB24
		// (set) Token: 0x0600026D RID: 621 RVA: 0x000030C1 File Offset: 0x000012C1
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

		// Token: 0x170000E1 RID: 225
		// (get) Token: 0x0600026E RID: 622 RVA: 0x0000E93C File Offset: 0x0000CB3C
		// (set) Token: 0x0600026F RID: 623 RVA: 0x000030CB File Offset: 0x000012CB
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

		// Token: 0x170000E2 RID: 226
		// (get) Token: 0x06000270 RID: 624 RVA: 0x0000E954 File Offset: 0x0000CB54
		// (set) Token: 0x06000271 RID: 625 RVA: 0x000030D5 File Offset: 0x000012D5
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

		// Token: 0x170000E3 RID: 227
		// (get) Token: 0x06000272 RID: 626 RVA: 0x0000E96C File Offset: 0x0000CB6C
		public string SelectedItem
		{
			get
			{
				return Conversions.ToString(this.ListB.SelectedItem);
			}
		}

		// Token: 0x170000E4 RID: 228
		// (get) Token: 0x06000273 RID: 627 RVA: 0x0000E990 File Offset: 0x0000CB90
		public int SelectedIndex
		{
			get
			{
				return this.ListB.SelectedIndex;
			}
		}

		// Token: 0x06000274 RID: 628 RVA: 0x000030DF File Offset: 0x000012DF
		public void Clear()
		{
			this.ListB.Items.Clear();
		}

		// Token: 0x06000275 RID: 629 RVA: 0x0000E9B0 File Offset: 0x0000CBB0
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

		// Token: 0x06000276 RID: 630 RVA: 0x0000EA04 File Offset: 0x0000CC04
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			bool flag = !base.Controls.Contains(this.ListB);
			if (flag)
			{
				base.Controls.Add(this.ListB);
			}
		}

		// Token: 0x06000277 RID: 631 RVA: 0x000030F3 File Offset: 0x000012F3
		public void AddRange(object[] items)
		{
			this.ListB.Items.Remove("");
			this.ListB.Items.AddRange(items);
		}

		// Token: 0x06000278 RID: 632 RVA: 0x0000311E File Offset: 0x0000131E
		public void AddItem(object item)
		{
			this.ListB.Items.Remove("");
			this.ListB.Items.Add(RuntimeHelpers.GetObjectValue(item));
		}

		// Token: 0x06000279 RID: 633 RVA: 0x0000EA48 File Offset: 0x0000CC48
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

		// Token: 0x0600027A RID: 634 RVA: 0x0000EC54 File Offset: 0x0000CE54
		public LogInListBox()
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
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.ListB.DrawMode = DrawMode.OwnerDrawFixed;
			this.ListB.ScrollAlwaysVisible = false;
			this.ListB.HorizontalScrollbar = false;
			this.ListB.BorderStyle = BorderStyle.None;
			this.ListB.BackColor = this._BaseColour;
			this.ListB.Location = new Point(3, 3);
			this.ListB.Font = new Font("Segoe UI", 8f);
			this.ListB.ItemHeight = 20;
			this.ListB.Items.Clear();
			this.ListB.IntegralHeight = false;
			base.Size = new Size(130, 100);
		}

		// Token: 0x0600027B RID: 635 RVA: 0x0000EDB0 File Offset: 0x0000CFB0
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
				this.ListB.Size = new Size(base.Width - 6, base.Height - 5);
				graphics.FillRectangle(new SolidBrush(this._BaseColour), Base);
				graphics.DrawRectangle(new Pen(this._BorderColour, 3f), new Rectangle(0, 0, base.Width, base.Height - 1));
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x040000F6 RID: 246
		private string[] _Items;

		// Token: 0x040000F7 RID: 247
		private Color _BaseColour;

		// Token: 0x040000F8 RID: 248
		private Color _SelectedColour;

		// Token: 0x040000F9 RID: 249
		private Color _ListBaseColour;

		// Token: 0x040000FA RID: 250
		private Color _TextColour;

		// Token: 0x040000FB RID: 251
		private Color _BorderColour;
	}
}
