using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Windows.Threading;
using WaveClient.Module;
using WaveClient.ModuleManagment.ModuleExtensions;

namespace WaveClient.GUI
{
	// Token: 0x0200000A RID: 10
	public class MainWindow_ModulePage : Page, IComponentConnector
	{
		// Token: 0x0600002E RID: 46 RVA: 0x00002688 File Offset: 0x00000888
		public MainWindow_ModulePage()
		{
			this.InitializeComponent();
			this.UpdateGUIDispatcherTimer = new DispatcherTimer();
			this.UpdateGUIDispatcherTimer.Interval = TimeSpan.FromMilliseconds(10.0);
			this.UpdateGUIDispatcherTimer.Tick += this.UpdateGUI;
			this.UpdateGUIDispatcherTimer.Start();
		}

		// Token: 0x0600002F RID: 47 RVA: 0x000026E8 File Offset: 0x000008E8
		~MainWindow_ModulePage()
		{
			this.UpdateGUIDispatcherTimer.Stop();
		}

		// Token: 0x06000030 RID: 48 RVA: 0x0000271C File Offset: 0x0000091C
		public void UpdateGUI(object sender, EventArgs e)
		{
			this.SModule_Airjump.Content = GUIExtensions.GetBoolStateText(AirJump.ToggleState);
			this.SModule_NoFall.Content = GUIExtensions.GetBoolStateText(NoFall.ToggleState);
			this.SModule_Instabreak.Content = GUIExtensions.GetBoolStateText(Instabreak.ToggleState);
			this.SModule_Reach.Content = GUIExtensions.GetBoolStateText(Reach.ToggleState);
			this.SModule_NoSwing.Content = GUIExtensions.GetBoolStateText(NoSwing.ToggleState);
			this.SModule_Coords.Content = GUIExtensions.GetBoolStateText(Coords.ToggleState);
			this.SModule_Speed.Content = GUIExtensions.GetBoolStateText(Speed.ToggleState);
			this.SModule_AutoSprint.Content = GUIExtensions.GetBoolStateText(AutoSprint.ToggleState);
		}

		// Token: 0x06000031 RID: 49 RVA: 0x000027D1 File Offset: 0x000009D1
		private void SModule_Airjump_Click(object sender, RoutedEventArgs e)
		{
			AirJump.ToggleState = !AirJump.ToggleState;
			this.SModule_Airjump.Content = GUIExtensions.GetBoolStateText(AirJump.ToggleState);
		}

		// Token: 0x06000032 RID: 50 RVA: 0x000027F5 File Offset: 0x000009F5
		private void SModule_NoFall_Click(object sender, RoutedEventArgs e)
		{
			NoFall.ToggleState = !NoFall.ToggleState;
			this.SModule_NoFall.Content = GUIExtensions.GetBoolStateText(NoFall.ToggleState);
		}

		// Token: 0x06000033 RID: 51 RVA: 0x00002819 File Offset: 0x00000A19
		private void SModule_Instabreak_Click(object sender, RoutedEventArgs e)
		{
			Instabreak.ToggleState = !Instabreak.ToggleState;
			this.SModule_Instabreak.Content = GUIExtensions.GetBoolStateText(Instabreak.ToggleState);
		}

		// Token: 0x06000034 RID: 52 RVA: 0x0000283D File Offset: 0x00000A3D
		private void SModule_Reach_Click(object sender, RoutedEventArgs e)
		{
			Reach.ToggleState = !Reach.ToggleState;
			this.SModule_Reach.Content = GUIExtensions.GetBoolStateText(Reach.ToggleState);
		}

		// Token: 0x06000035 RID: 53 RVA: 0x00002861 File Offset: 0x00000A61
		private void SModule_NoSwing_Click(object sender, RoutedEventArgs e)
		{
			NoSwing.ToggleState = !NoSwing.ToggleState;
			this.SModule_NoSwing.Content = GUIExtensions.GetBoolStateText(NoSwing.ToggleState);
		}

		// Token: 0x06000036 RID: 54 RVA: 0x00002885 File Offset: 0x00000A85
		private void SModule_Coords_Click(object sender, RoutedEventArgs e)
		{
			Coords.ToggleState = !Coords.ToggleState;
			this.SModule_Coords.Content = GUIExtensions.GetBoolStateText(Coords.ToggleState);
		}

		// Token: 0x06000037 RID: 55 RVA: 0x000028A9 File Offset: 0x00000AA9
		private void SModule_Speed_Click(object sender, RoutedEventArgs e)
		{
			Speed.ToggleState = !Speed.ToggleState;
			this.SModule_Speed.Content = GUIExtensions.GetBoolStateText(Speed.ToggleState);
		}

		// Token: 0x06000038 RID: 56 RVA: 0x000028CD File Offset: 0x00000ACD
		private void SModule_AutoSprint_Click(object sender, RoutedEventArgs e)
		{
			AutoSprint.ToggleState = !AutoSprint.ToggleState;
			this.SModule_AutoSprint.Content = GUIExtensions.GetBoolStateText(AutoSprint.ToggleState);
		}

		// Token: 0x06000039 RID: 57 RVA: 0x000028F4 File Offset: 0x00000AF4
		[DebuggerNonUserCode]
		[GeneratedCode("PresentationBuildTasks", "4.0.0.0")]
		public void InitializeComponent()
		{
			if (this._contentLoaded)
			{
				return;
			}
			this._contentLoaded = true;
			Uri resourceLocator = new Uri("/WaveClient;component/waveclient/gui/waveclient.gui.mainwindow.modulepage.xaml", UriKind.Relative);
			Application.LoadComponent(this, resourceLocator);
		}

		// Token: 0x0600003A RID: 58 RVA: 0x00002924 File Offset: 0x00000B24
		[DebuggerNonUserCode]
		[GeneratedCode("PresentationBuildTasks", "4.0.0.0")]
		[EditorBrowsable(EditorBrowsableState.Never)]
		void IComponentConnector.Connect(int connectionId, object target)
		{
			switch (connectionId)
			{
			case 1:
				this.SGrid_Airjump = (Grid)target;
				return;
			case 2:
				this.SModule_Airjump = (Button)target;
				this.SModule_Airjump.Click += this.SModule_Airjump_Click;
				return;
			case 3:
				this.STextBlock_Airjump = (TextBlock)target;
				return;
			case 4:
				this.SGrid_NoFall = (Grid)target;
				return;
			case 5:
				this.SModule_NoFall = (Button)target;
				this.SModule_NoFall.Click += this.SModule_NoFall_Click;
				return;
			case 6:
				this.STextBlock_NoFall = (TextBlock)target;
				return;
			case 7:
				this.SGrid_Instabreak = (Grid)target;
				return;
			case 8:
				this.SModule_Instabreak = (Button)target;
				this.SModule_Instabreak.Click += this.SModule_Instabreak_Click;
				return;
			case 9:
				this.STextBlock_Instabreak = (TextBlock)target;
				return;
			case 10:
				this.SGrid_Reach = (Grid)target;
				return;
			case 11:
				this.SModule_Reach = (Button)target;
				this.SModule_Reach.Click += this.SModule_Reach_Click;
				return;
			case 12:
				this.STextBlock_Reach = (TextBlock)target;
				return;
			case 13:
				this.SGrid_NoSwing = (Grid)target;
				return;
			case 14:
				this.SModule_NoSwing = (Button)target;
				this.SModule_NoSwing.Click += this.SModule_NoSwing_Click;
				return;
			case 15:
				this.STextBlock_NoSwing = (TextBlock)target;
				return;
			case 16:
				this.SGrid_Coords = (Grid)target;
				return;
			case 17:
				this.SModule_Coords = (Button)target;
				this.SModule_Coords.Click += this.SModule_Coords_Click;
				return;
			case 18:
				this.STextBlock_Coords = (TextBlock)target;
				return;
			case 19:
				this.SGrid_Speed = (Grid)target;
				return;
			case 20:
				this.SModule_Speed = (Button)target;
				this.SModule_Speed.Click += this.SModule_Speed_Click;
				return;
			case 21:
				this.STextBlock_Speed = (TextBlock)target;
				return;
			case 22:
				this.SGrid_AutoSprint = (Grid)target;
				return;
			case 23:
				this.SModule_AutoSprint = (Button)target;
				this.SModule_AutoSprint.Click += this.SModule_AutoSprint_Click;
				return;
			case 24:
				this.STextBlock_AutoSprint = (TextBlock)target;
				return;
			default:
				this._contentLoaded = true;
				return;
			}
		}

		// Token: 0x0400000C RID: 12
		private DispatcherTimer UpdateGUIDispatcherTimer;

		// Token: 0x0400000D RID: 13
		internal Grid SGrid_Airjump;

		// Token: 0x0400000E RID: 14
		internal Button SModule_Airjump;

		// Token: 0x0400000F RID: 15
		internal TextBlock STextBlock_Airjump;

		// Token: 0x04000010 RID: 16
		internal Grid SGrid_NoFall;

		// Token: 0x04000011 RID: 17
		internal Button SModule_NoFall;

		// Token: 0x04000012 RID: 18
		internal TextBlock STextBlock_NoFall;

		// Token: 0x04000013 RID: 19
		internal Grid SGrid_Instabreak;

		// Token: 0x04000014 RID: 20
		internal Button SModule_Instabreak;

		// Token: 0x04000015 RID: 21
		internal TextBlock STextBlock_Instabreak;

		// Token: 0x04000016 RID: 22
		internal Grid SGrid_Reach;

		// Token: 0x04000017 RID: 23
		internal Button SModule_Reach;

		// Token: 0x04000018 RID: 24
		internal TextBlock STextBlock_Reach;

		// Token: 0x04000019 RID: 25
		internal Grid SGrid_NoSwing;

		// Token: 0x0400001A RID: 26
		internal Button SModule_NoSwing;

		// Token: 0x0400001B RID: 27
		internal TextBlock STextBlock_NoSwing;

		// Token: 0x0400001C RID: 28
		internal Grid SGrid_Coords;

		// Token: 0x0400001D RID: 29
		internal Button SModule_Coords;

		// Token: 0x0400001E RID: 30
		internal TextBlock STextBlock_Coords;

		// Token: 0x0400001F RID: 31
		internal Grid SGrid_Speed;

		// Token: 0x04000020 RID: 32
		internal Button SModule_Speed;

		// Token: 0x04000021 RID: 33
		internal TextBlock STextBlock_Speed;

		// Token: 0x04000022 RID: 34
		internal Grid SGrid_AutoSprint;

		// Token: 0x04000023 RID: 35
		internal Button SModule_AutoSprint;

		// Token: 0x04000024 RID: 36
		internal TextBlock STextBlock_AutoSprint;

		// Token: 0x04000025 RID: 37
		private bool _contentLoaded;
	}
}
