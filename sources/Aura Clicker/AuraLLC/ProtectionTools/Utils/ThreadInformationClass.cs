using System;

namespace AuraLLC.ProtectionTools.Utils
{
	// Token: 0x0200000D RID: 13
	public enum ThreadInformationClass
	{
		// Token: 0x0400023B RID: 571
		ThreadBasicInformation,
		// Token: 0x0400023C RID: 572
		ThreadTimes,
		// Token: 0x0400023D RID: 573
		ThreadPriority,
		// Token: 0x0400023E RID: 574
		ThreadBasePriority,
		// Token: 0x0400023F RID: 575
		ThreadAffinityMask,
		// Token: 0x04000240 RID: 576
		ThreadImpersonationToken,
		// Token: 0x04000241 RID: 577
		ThreadDescriptorTableEntry,
		// Token: 0x04000242 RID: 578
		ThreadEnableAlignmentFaultFixup,
		// Token: 0x04000243 RID: 579
		ThreadEventPair_Reusable,
		// Token: 0x04000244 RID: 580
		ThreadQuerySetWin32StartAddress,
		// Token: 0x04000245 RID: 581
		ThreadZeroTlsCell,
		// Token: 0x04000246 RID: 582
		ThreadPerformanceCount,
		// Token: 0x04000247 RID: 583
		ThreadAmILastThread,
		// Token: 0x04000248 RID: 584
		ThreadIdealProcessor,
		// Token: 0x04000249 RID: 585
		ThreadPriorityBoost,
		// Token: 0x0400024A RID: 586
		ThreadSetTlsArrayAddress,
		// Token: 0x0400024B RID: 587
		ThreadIsIoPending,
		// Token: 0x0400024C RID: 588
		ThreadHideFromDebugger,
		// Token: 0x0400024D RID: 589
		ThreadBreakOnTermination,
		// Token: 0x0400024E RID: 590
		ThreadSwitchLegacyState,
		// Token: 0x0400024F RID: 591
		ThreadIsTerminated,
		// Token: 0x04000250 RID: 592
		ThreadLastSystemCall,
		// Token: 0x04000251 RID: 593
		ThreadIoPriority,
		// Token: 0x04000252 RID: 594
		ThreadCycleTime,
		// Token: 0x04000253 RID: 595
		ThreadPagePriority,
		// Token: 0x04000254 RID: 596
		ThreadActualBasePriority,
		// Token: 0x04000255 RID: 597
		ThreadTebInformation,
		// Token: 0x04000256 RID: 598
		ThreadCSwitchMon,
		// Token: 0x04000257 RID: 599
		ThreadCSwitchPmu,
		// Token: 0x04000258 RID: 600
		ThreadWow64Context,
		// Token: 0x04000259 RID: 601
		ThreadGroupInformation,
		// Token: 0x0400025A RID: 602
		ThreadUmsInformation,
		// Token: 0x0400025B RID: 603
		ThreadCounterProfiling,
		// Token: 0x0400025C RID: 604
		ThreadIdealProcessorEx,
		// Token: 0x0400025D RID: 605
		ThreadCpuAccountingInformation,
		// Token: 0x0400025E RID: 606
		ThreadSuspendCount,
		// Token: 0x0400025F RID: 607
		ThreadDescription = 38,
		// Token: 0x04000260 RID: 608
		ThreadActualGroupAffinity = 41,
		// Token: 0x04000261 RID: 609
		ThreadDynamicCodePolicy
	}
}
