using System;

namespace New_CandyClient.ProtectionTools.Utils
{
	// Token: 0x02000033 RID: 51
	public enum SYSTEM_INFORMATION_CLASS
	{
		// Token: 0x040002E5 RID: 741
		SystemBasicInformation,
		// Token: 0x040002E6 RID: 742
		SystemProcessorInformation,
		// Token: 0x040002E7 RID: 743
		SystemPerformanceInformation,
		// Token: 0x040002E8 RID: 744
		SystemTimeOfDayInformation,
		// Token: 0x040002E9 RID: 745
		SystemPathInformation,
		// Token: 0x040002EA RID: 746
		SystemProcessInformation,
		// Token: 0x040002EB RID: 747
		SystemCallCountInformation,
		// Token: 0x040002EC RID: 748
		SystemDeviceInformation,
		// Token: 0x040002ED RID: 749
		SystemProcessorPerformanceInformation,
		// Token: 0x040002EE RID: 750
		SystemFlagsInformation,
		// Token: 0x040002EF RID: 751
		SystemCallTimeInformation,
		// Token: 0x040002F0 RID: 752
		SystemModuleInformation,
		// Token: 0x040002F1 RID: 753
		SystemLocksInformation,
		// Token: 0x040002F2 RID: 754
		SystemStackTraceInformation,
		// Token: 0x040002F3 RID: 755
		SystemPagedPoolInformation,
		// Token: 0x040002F4 RID: 756
		SystemNonPagedPoolInformation,
		// Token: 0x040002F5 RID: 757
		SystemHandleInformation,
		// Token: 0x040002F6 RID: 758
		SystemObjectInformation,
		// Token: 0x040002F7 RID: 759
		SystemPageFileInformation,
		// Token: 0x040002F8 RID: 760
		SystemVdmInstemulInformation,
		// Token: 0x040002F9 RID: 761
		SystemVdmBopInformation,
		// Token: 0x040002FA RID: 762
		SystemFileCacheInformation,
		// Token: 0x040002FB RID: 763
		SystemPoolTagInformation,
		// Token: 0x040002FC RID: 764
		SystemInterruptInformation,
		// Token: 0x040002FD RID: 765
		SystemDpcBehaviorInformation,
		// Token: 0x040002FE RID: 766
		SystemFullMemoryInformation,
		// Token: 0x040002FF RID: 767
		SystemLoadGdiDriverInformation,
		// Token: 0x04000300 RID: 768
		SystemUnloadGdiDriverInformation,
		// Token: 0x04000301 RID: 769
		SystemTimeAdjustmentInformation,
		// Token: 0x04000302 RID: 770
		SystemSummaryMemoryInformation,
		// Token: 0x04000303 RID: 771
		SystemMirrorMemoryInformation,
		// Token: 0x04000304 RID: 772
		SystemPerformanceTraceInformation,
		// Token: 0x04000305 RID: 773
		SystemObsolete0,
		// Token: 0x04000306 RID: 774
		SystemExceptionInformation,
		// Token: 0x04000307 RID: 775
		SystemCrashDumpStateInformation,
		// Token: 0x04000308 RID: 776
		SystemKernelDebuggerInformation,
		// Token: 0x04000309 RID: 777
		SystemContextSwitchInformation,
		// Token: 0x0400030A RID: 778
		SystemRegistryQuotaInformation,
		// Token: 0x0400030B RID: 779
		SystemExtendServiceTableInformation,
		// Token: 0x0400030C RID: 780
		SystemPrioritySeperation,
		// Token: 0x0400030D RID: 781
		SystemVerifierAddDriverInformation,
		// Token: 0x0400030E RID: 782
		SystemVerifierRemoveDriverInformation,
		// Token: 0x0400030F RID: 783
		SystemProcessorIdleInformation,
		// Token: 0x04000310 RID: 784
		SystemLegacyDriverInformation,
		// Token: 0x04000311 RID: 785
		SystemCurrentTimeZoneInformation,
		// Token: 0x04000312 RID: 786
		SystemLookasideInformation,
		// Token: 0x04000313 RID: 787
		SystemTimeSlipNotification,
		// Token: 0x04000314 RID: 788
		SystemSessionCreate,
		// Token: 0x04000315 RID: 789
		SystemSessionDetach,
		// Token: 0x04000316 RID: 790
		SystemSessionInformation,
		// Token: 0x04000317 RID: 791
		SystemRangeStartInformation,
		// Token: 0x04000318 RID: 792
		SystemVerifierInformation,
		// Token: 0x04000319 RID: 793
		SystemVerifierThunkExtend,
		// Token: 0x0400031A RID: 794
		SystemSessionProcessInformation,
		// Token: 0x0400031B RID: 795
		SystemLoadGdiDriverInSystemSpace,
		// Token: 0x0400031C RID: 796
		SystemNumaProcessorMap,
		// Token: 0x0400031D RID: 797
		SystemPrefetcherInformation,
		// Token: 0x0400031E RID: 798
		SystemExtendedProcessInformation,
		// Token: 0x0400031F RID: 799
		SystemRecommendedSharedDataAlignment,
		// Token: 0x04000320 RID: 800
		SystemComPlusPackage,
		// Token: 0x04000321 RID: 801
		SystemNumaAvailableMemory,
		// Token: 0x04000322 RID: 802
		SystemProcessorPowerInformation,
		// Token: 0x04000323 RID: 803
		SystemEmulationBasicInformation,
		// Token: 0x04000324 RID: 804
		SystemEmulationProcessorInformation,
		// Token: 0x04000325 RID: 805
		SystemExtendedHandleInformation,
		// Token: 0x04000326 RID: 806
		SystemLostDelayedWriteInformation,
		// Token: 0x04000327 RID: 807
		SystemBigPoolInformation,
		// Token: 0x04000328 RID: 808
		SystemSessionPoolTagInformation,
		// Token: 0x04000329 RID: 809
		SystemSessionMappedViewInformation,
		// Token: 0x0400032A RID: 810
		SystemHotpatchInformation,
		// Token: 0x0400032B RID: 811
		SystemObjectSecurityMode,
		// Token: 0x0400032C RID: 812
		SystemWatchdogTimerHandler,
		// Token: 0x0400032D RID: 813
		SystemWatchdogTimerInformation,
		// Token: 0x0400032E RID: 814
		SystemLogicalProcessorInformation,
		// Token: 0x0400032F RID: 815
		SystemWow64SharedInformationObsolete,
		// Token: 0x04000330 RID: 816
		SystemRegisterFirmwareTableInformationHandler,
		// Token: 0x04000331 RID: 817
		SystemFirmwareTableInformation,
		// Token: 0x04000332 RID: 818
		SystemModuleInformationEx,
		// Token: 0x04000333 RID: 819
		SystemVerifierTriageInformation,
		// Token: 0x04000334 RID: 820
		SystemSuperfetchInformation,
		// Token: 0x04000335 RID: 821
		SystemMemoryListInformation,
		// Token: 0x04000336 RID: 822
		SystemFileCacheInformationEx,
		// Token: 0x04000337 RID: 823
		SystemThreadPriorityClientIdInformation,
		// Token: 0x04000338 RID: 824
		SystemProcessorIdleCycleTimeInformation,
		// Token: 0x04000339 RID: 825
		SystemVerifierCancellationInformation,
		// Token: 0x0400033A RID: 826
		SystemProcessorPowerInformationEx,
		// Token: 0x0400033B RID: 827
		SystemRefTraceInformation,
		// Token: 0x0400033C RID: 828
		SystemSpecialPoolInformation,
		// Token: 0x0400033D RID: 829
		SystemProcessIdInformation,
		// Token: 0x0400033E RID: 830
		SystemErrorPortInformation,
		// Token: 0x0400033F RID: 831
		SystemBootEnvironmentInformation,
		// Token: 0x04000340 RID: 832
		SystemHypervisorInformation,
		// Token: 0x04000341 RID: 833
		SystemVerifierInformationEx,
		// Token: 0x04000342 RID: 834
		SystemTimeZoneInformation,
		// Token: 0x04000343 RID: 835
		SystemImageFileExecutionOptionsInformation,
		// Token: 0x04000344 RID: 836
		SystemCoverageInformation,
		// Token: 0x04000345 RID: 837
		SystemPrefetchPatchInformation,
		// Token: 0x04000346 RID: 838
		SystemVerifierFaultsInformation,
		// Token: 0x04000347 RID: 839
		SystemSystemPartitionInformation,
		// Token: 0x04000348 RID: 840
		SystemSystemDiskInformation,
		// Token: 0x04000349 RID: 841
		SystemProcessorPerformanceDistribution,
		// Token: 0x0400034A RID: 842
		SystemNumaProximityNodeInformation,
		// Token: 0x0400034B RID: 843
		SystemDynamicTimeZoneInformation,
		// Token: 0x0400034C RID: 844
		SystemCodeIntegrityInformation,
		// Token: 0x0400034D RID: 845
		SystemProcessorMicrocodeUpdateInformation,
		// Token: 0x0400034E RID: 846
		SystemProcessorBrandString,
		// Token: 0x0400034F RID: 847
		SystemVirtualAddressInformation,
		// Token: 0x04000350 RID: 848
		SystemLogicalProcessorAndGroupInformation,
		// Token: 0x04000351 RID: 849
		SystemProcessorCycleTimeInformation,
		// Token: 0x04000352 RID: 850
		SystemStoreInformation,
		// Token: 0x04000353 RID: 851
		SystemRegistryAppendString,
		// Token: 0x04000354 RID: 852
		SystemAitSamplingValue,
		// Token: 0x04000355 RID: 853
		SystemVhdBootInformation,
		// Token: 0x04000356 RID: 854
		SystemCpuQuotaInformation,
		// Token: 0x04000357 RID: 855
		SystemNativeBasicInformation,
		// Token: 0x04000358 RID: 856
		SystemSpare1,
		// Token: 0x04000359 RID: 857
		SystemLowPriorityIoInformation,
		// Token: 0x0400035A RID: 858
		SystemTpmBootEntropyInformation,
		// Token: 0x0400035B RID: 859
		SystemVerifierCountersInformation,
		// Token: 0x0400035C RID: 860
		SystemPagedPoolInformationEx,
		// Token: 0x0400035D RID: 861
		SystemSystemPtesInformationEx,
		// Token: 0x0400035E RID: 862
		SystemNodeDistanceInformation,
		// Token: 0x0400035F RID: 863
		SystemAcpiAuditInformation,
		// Token: 0x04000360 RID: 864
		SystemBasicPerformanceInformation,
		// Token: 0x04000361 RID: 865
		SystemQueryPerformanceCounterInformation,
		// Token: 0x04000362 RID: 866
		SystemSessionBigPoolInformation,
		// Token: 0x04000363 RID: 867
		SystemBootGraphicsInformation,
		// Token: 0x04000364 RID: 868
		SystemScrubPhysicalMemoryInformation,
		// Token: 0x04000365 RID: 869
		SystemBadPageInformation,
		// Token: 0x04000366 RID: 870
		SystemProcessorProfileControlArea,
		// Token: 0x04000367 RID: 871
		SystemCombinePhysicalMemoryInformation,
		// Token: 0x04000368 RID: 872
		SystemEntropyInterruptTimingCallback,
		// Token: 0x04000369 RID: 873
		SystemConsoleInformation,
		// Token: 0x0400036A RID: 874
		SystemPlatformBinaryInformation,
		// Token: 0x0400036B RID: 875
		SystemThrottleNotificationInformation,
		// Token: 0x0400036C RID: 876
		SystemHypervisorProcessorCountInformation,
		// Token: 0x0400036D RID: 877
		SystemDeviceDataInformation,
		// Token: 0x0400036E RID: 878
		SystemDeviceDataEnumerationInformation,
		// Token: 0x0400036F RID: 879
		SystemMemoryTopologyInformation,
		// Token: 0x04000370 RID: 880
		SystemMemoryChannelInformation,
		// Token: 0x04000371 RID: 881
		SystemBootLogoInformation,
		// Token: 0x04000372 RID: 882
		SystemProcessorPerformanceInformationEx,
		// Token: 0x04000373 RID: 883
		SystemSpare0,
		// Token: 0x04000374 RID: 884
		SystemSecureBootPolicyInformation,
		// Token: 0x04000375 RID: 885
		SystemPageFileInformationEx,
		// Token: 0x04000376 RID: 886
		SystemSecureBootInformation,
		// Token: 0x04000377 RID: 887
		SystemEntropyInterruptTimingRawInformation,
		// Token: 0x04000378 RID: 888
		SystemPortableWorkspaceEfiLauncherInformation,
		// Token: 0x04000379 RID: 889
		SystemFullProcessInformation,
		// Token: 0x0400037A RID: 890
		SystemKernelDebuggerInformationEx,
		// Token: 0x0400037B RID: 891
		SystemBootMetadataInformation,
		// Token: 0x0400037C RID: 892
		SystemSoftRebootInformation,
		// Token: 0x0400037D RID: 893
		SystemElamCertificateInformation,
		// Token: 0x0400037E RID: 894
		SystemOfflineDumpConfigInformation,
		// Token: 0x0400037F RID: 895
		SystemProcessorFeaturesInformation,
		// Token: 0x04000380 RID: 896
		SystemRegistryReconciliationInformation,
		// Token: 0x04000381 RID: 897
		SystemEdidInformation,
		// Token: 0x04000382 RID: 898
		MaxSystemInfoClass
	}
}
