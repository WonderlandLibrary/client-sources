using System;
using System.ComponentModel;
using System.Drawing;
using System.IO;
using System.Windows.Forms;
using Bunifu.Framework.UI;

namespace Steam_Client_Bootstrapper
{
	// Token: 0x02000003 RID: 3
	public partial class Config : Form
	{
		// Token: 0x0600000E RID: 14 RVA: 0x00002089 File Offset: 0x00000289
		public Config()
		{
			this.InitializeComponent();
		}

		// Token: 0x0600000F RID: 15 RVA: 0x000020A1 File Offset: 0x000002A1
		private void pictureBox1_Click(object sender, EventArgs e)
		{
			MessageBox.Show("Criado por Fearth in CSharp");
		}

		// Token: 0x06000010 RID: 16 RVA: 0x00002D88 File Offset: 0x00000F88
		private void bunifuThinButton21_Click(object sender, EventArgs e)
		{
			File.Create("crashhandler.dll");
			File.Create("crashhandler64.dll");
			File.Create("CSERHelper.dll");
			File.Create("d3dcompiler_46.dll");
			File.Create("d3dcompiler_46_64.dll");
			File.Create("fossilize_engine_filters.json");
			File.Create("GameOverlayRenderer.dll");
			File.Create("GameOverlayRenderer64.dll");
			File.Create("icui18n.dll");
			File.Create("icuuc.dll");
			File.Create("libavcodec-57.dll");
			File.Create("libavformat-57.dll");
			File.Create("libavresample-3.dll");
			File.Create("libavutil-55.dll");
			File.Create("libswscale-4.dll");
			File.Create("libx264-142.dll.crypt");
			File.Create("libx264-142.dll.md5");
			File.Create("openvr_api.dll");
			File.Create("SDL2.dll");
			File.Create("Steam.dll");
			File.Create("Steam2.dll");
			File.Create("steamclient.dll");
			File.Create("steamclient64.dll");
			File.Create("SteamFossilizeVulkanLayer.json");
			File.Create("crashhandler64.dll");
			File.Create("vstdlib_s64.dll");
			File.Create("Steam.dll");
			File.Create("tier0_s64.dll");
			File.Create("crashhandler.dll");
			File.Create("libavresample-3.dll");
			File.Create("tier0_s.dll");
			File.Create("vstdlib_s.dll");
			File.Create("WriteMiniDump.exe");
			File.Create("openvr_api.dll");
			File.Create("ThirdPartyLegalNotices.html");
			File.Create("SteamOverlayVulkanLayer64.dll");
			File.Create("SteamOverlayVulkanLayer.dll");
			File.Create("CSERHelper.dll");
			File.Create("ThirdPartyLegalNotices.doc");
			File.Create("SteamFossilizeVulkanLayer64.json");
			File.Create("SteamFossilizeVulkanLayer.json");
			File.Create("SteamOverlayVulkanLayer64.json");
			File.Create("SteamOverlayVulkanLayer.json");
			File.Create("ThirdPartyLegalNotices.css");
			File.Create("fossilize_engine_filters.json");
			File.Create("libx264-142.dll.md5");
			File.Delete("C:\\Windows\\Recent\\Steam.rar");
			File.Delete("C:\\Windows\\Recent\\Steam.exe");
			File.Delete("C:\\Windows\\Prefetch\\Steam.rar");
			File.Delete("C:\\Windows\\Prefetch\\Steam.exe");
			File.Delete("C:\\Windows\\Temp\\Steam.vshost.exe_200220_082622_946ba7ce-3d92-4144-8773-eddda817a78d.iTrace");
			File.Delete("C:\\Windows\\Temp\\Steam.exe.log");
			File.Delete("C:\\Windows\\Temp\\Steam.rar.log");
			MessageBox.Show("100%", "Finished", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
			Application.Exit();
		}
	}
}
