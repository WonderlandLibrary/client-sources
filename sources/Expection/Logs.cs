using System;
using System.IO;

// Token: 0x02000006 RID: 6
internal class Logs
{
	// Token: 0x06000009 RID: 9 RVA: 0x0000229C File Offset: 0x0000049C
	public static void WriteLog(string strLog)
	{
		string text = Directory.GetCurrentDirectory() + "\\";
		text = text + "Log-" + DateTime.Today.ToString("MM-dd-yyyy") + ".txt";
		FileInfo fileInfo = new FileInfo(text);
		DirectoryInfo directoryInfo = new DirectoryInfo(fileInfo.DirectoryName);
		bool flag = !directoryInfo.Exists;
		bool flag2 = flag;
		bool flag3 = flag2;
		if (flag3)
		{
			directoryInfo.Create();
		}
		bool flag4 = !fileInfo.Exists;
		bool flag5 = flag4;
		bool flag6 = flag5;
		FileStream stream;
		if (flag6)
		{
			stream = fileInfo.Create();
		}
		else
		{
			stream = new FileStream(text, FileMode.Append);
		}
		StreamWriter streamWriter = new StreamWriter(stream);
		streamWriter.WriteLine("# >>  " + strLog);
		streamWriter.WriteLine("#-------------------------------------------------------------------------");
		streamWriter.Close();
	}

	// Token: 0x0600000A RID: 10 RVA: 0x00002370 File Offset: 0x00000570
	public static void DeleteLog()
	{
		FileInfo fileInfo = new FileInfo(Directory.GetCurrentDirectory() + "\\Log-" + DateTime.Today.ToString("MM-dd-yyyy") + ".txt");
		DirectoryInfo directoryInfo = new DirectoryInfo(fileInfo.DirectoryName);
		bool flag = !directoryInfo.Exists;
		bool flag2 = flag;
		bool flag3 = flag2;
		if (flag3)
		{
			directoryInfo.Delete();
		}
		bool exists = fileInfo.Exists;
		bool flag4 = exists;
		bool flag5 = flag4;
		if (flag5)
		{
			fileInfo.Delete();
		}
	}
}
