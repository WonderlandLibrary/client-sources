using System;

namespace AuraLLC.ProtectionTools.Utils
{
	// Token: 0x0200000A RID: 10
	public enum SYSTEM_INFORMATION_CLASS
	{
		// Token: 0x04000190 RID: 400
		SystemBasicInformation,
		// Token: 0x04000191 RID: 401
		SystemProcessorInformation,
		// Token: 0x04000192 RID: 402
		SystemPerformanceInformation,
		// Token: 0x04000193 RID: 403
		SystemTimeOfDayInformation,
		// Token: 0x04000194 RID: 404
		SystemPathInformation,
		// Token: 0x04000195 RID: 405
		SystemProcessInformation,
		// Token: 0x04000196 RID: 406
		SystemCallCountInformation,
		// Token: 0x04000197 RID: 407
		SystemDeviceInformation,
		// Token: 0x04000198 RID: 408
		SystemProcessorPerformanceInformation,
		// Token: 0x04000199 RID: 409
		SystemFlagsInformation,
		// Token: 0x0400019A RID: 410
		SystemCallTimeInformation,
		// Token: 0x0400019B RID: 411
		SystemModuleInformation,
		// Token: 0x0400019C RID: 412
		SystemLocksInformation,
		// Token: 0x0400019D RID: 413
		SystemStackTraceInformation,
		// Token: 0x0400019E RID: 414
		SystemPagedPoolInformation,
		// Token: 0x0400019F RID: 415
		SystemNonPagedPoolInformation,
		// Token: 0x040001A0 RID: 416
		SystemHandleInformation,
		// Token: 0x040001A1 RID: 417
		SystemObjectInformation,
		// Token: 0x040001A2 RID: 418
		SystemPageFileInformation,
		// Token: 0x040001A3 RID: 419
		SystemVdmInstemulInformation,
		// Token: 0x040001A4 RID: 420
		SystemVdmBopInformation,
		// Token: 0x040001A5 RID: 421
		SystemFileCacheInformation,
		// Token: 0x040001A6 RID: 422
		SystemPoolTagInformation,
		// Token: 0x040001A7 RID: 423
		SystemInterruptInformation,
		// Token: 0x040001A8 RID: 424
		SystemDpcBehaviorInformation,
		// Token: 0x040001A9 RID: 425
		SystemFullMemoryInformation,
		// Token: 0x040001AA RID: 426
		SystemLoadGdiDriverInformation,
		// Token: 0x040001AB RID: 427
		SystemUnloadGdiDriverInformation,
		// Token: 0x040001AC RID: 428
		SystemTimeAdjustmentInformation,
		// Token: 0x040001AD RID: 429
		SystemSummaryMemoryInformation,
		// Token: 0x040001AE RID: 430
		SystemMirrorMemoryInformation,
		// Token: 0x040001AF RID: 431
		SystemPerformanceTraceInformation,
		// Token: 0x040001B0 RID: 432
		SystemObsolete0,
		// Token: 0x040001B1 RID: 433
		SystemExceptionInformation,
		// Token: 0x040001B2 RID: 434
		SystemCrashDumpStateInformation,
		// Token: 0x040001B3 RID: 435
		SystemKernelDebuggerInformation,
		// Token: 0x040001B4 RID: 436
		SystemContextSwitchInformation,
		// Token: 0x040001B5 RID: 437
		SystemRegistryQuotaInformation,
		// Token: 0x040001B6 RID: 438
		SystemExtendServiceTableInformation,
		// Token: 0x040001B7 RID: 439
		SystemPrioritySeperation,
		// Token: 0x040001B8 RID: 440
		SystemVerifierAddDriverInformation,
		// Token: 0x040001B9 RID: 441
		SystemVerifierRemoveDriverInformation,
		// Token: 0x040001BA RID: 442
		SystemProcessorIdleInformation,
		// Token: 0x040001BB RID: 443
		SystemLegacyDriverInformation,
		// Token: 0x040001BC RID: 444
		SystemCurrentTimeZoneInformation,
		// Token: 0x040001BD RID: 445
		SystemLookasideInformation,
		// Token: 0x040001BE RID: 446
		SystemTimeSlipNotification,
		// Token: 0x040001BF RID: 447
		SystemSessionCreate,
		// Token: 0x040001C0 RID: 448
		SystemSessionDetach,
		// Token: 0x040001C1 RID: 449
		SystemSessionInformation,
		// Token: 0x040001C2 RID: 450
		SystemRangeStartInformation,
		// Token: 0x040001C3 RID: 451
		SystemVerifierInformation,
		// Token: 0x040001C4 RID: 452
		SystemVerifierThunkExtend,
		// Token: 0x040001C5 RID: 453
		SystemSessionProcessInformation,
		// Token: 0x040001C6 RID: 454
		SystemLoadGdiDriverInSystemSpace,
		// Token: 0x040001C7 RID: 455
		SystemNumaProcessorMap,
		// Token: 0x040001C8 RID: 456
		SystemPrefetcherInformation,
		// Token: 0x040001C9 RID: 457
		SystemExtendedProcessInformation,
		// Token: 0x040001CA RID: 458
		SystemRecommendedSharedDataAlignment,
		// Token: 0x040001CB RID: 459
		SystemComPlusPackage,
		// Token: 0x040001CC RID: 460
		SystemNumaAvailableMemory,
		// Token: 0x040001CD RID: 461
		SystemProcessorPowerInformation,
		// Token: 0x040001CE RID: 462
		SystemEmulationBasicInformation,
		// Token: 0x040001CF RID: 463
		SystemEmulationProcessorInformation,
		// Token: 0x040001D0 RID: 464
		SystemExtendedHandleInformation,
		// Token: 0x040001D1 RID: 465
		SystemLostDelayedWriteInformation,
		// Token: 0x040001D2 RID: 466
		SystemBigPoolInformation,
		// Token: 0x040001D3 RID: 467
		SystemSessionPoolTagInformation,
		// Token: 0x040001D4 RID: 468
		SystemSessionMappedViewInformation,
		// Token: 0x040001D5 RID: 469
		SystemHotpatchInformation,
		// Token: 0x040001D6 RID: 470
		SystemObjectSecurityMode,
		// Token: 0x040001D7 RID: 471
		SystemWatchdogTimerHandler,
		// Token: 0x040001D8 RID: 472
		SystemWatchdogTimerInformation,
		// Token: 0x040001D9 RID: 473
		SystemLogicalProcessorInformation,
		// Token: 0x040001DA RID: 474
		SystemWow64SharedInformationObsolete,
		// Token: 0x040001DB RID: 475
		SystemRegisterFirmwareTableInformationHandler,
		// Token: 0x040001DC RID: 476
		SystemFirmwareTableInformation,
		// Token: 0x040001DD RID: 477
		SystemModuleInformationEx,
		// Token: 0x040001DE RID: 478
		SystemVerifierTriageInformation,
		// Token: 0x040001DF RID: 479
		SystemSuperfetchInformation,
		// Token: 0x040001E0 RID: 480
		SystemMemoryListInformation,
		// Token: 0x040001E1 RID: 481
		SystemFileCacheInformationEx,
		// Token: 0x040001E2 RID: 482
		SystemThreadPriorityClientIdInformation,
		// Token: 0x040001E3 RID: 483
		SystemProcessorIdleCycleTimeInformation,
		// Token: 0x040001E4 RID: 484
		SystemVerifierCancellationInformation,
		// Token: 0x040001E5 RID: 485
		SystemProcessorPowerInformationEx,
		// Token: 0x040001E6 RID: 486
		SystemRefTraceInformation,
		// Token: 0x040001E7 RID: 487
		SystemSpecialPoolInformation,
		// Token: 0x040001E8 RID: 488
		SystemProcessIdInformation,
		// Token: 0x040001E9 RID: 489
		SystemErrorPortInformation,
		// Token: 0x040001EA RID: 490
		SystemBootEnvironmentInformation,
		// Token: 0x040001EB RID: 491
		SystemHypervisorInformation,
		// Token: 0x040001EC RID: 492
		SystemVerifierInformationEx,
		// Token: 0x040001ED RID: 493
		SystemTimeZoneInformation,
		// Token: 0x040001EE RID: 494
		SystemImageFileExecutionOptionsInformation,
		// Token: 0x040001EF RID: 495
		SystemCoverageInformation,
		// Token: 0x040001F0 RID: 496
		SystemPrefetchPatchInformation,
		// Token: 0x040001F1 RID: 497
		SystemVerifierFaultsInformation,
		// Token: 0x040001F2 RID: 498
		SystemSystemPartitionInformation,
		// Token: 0x040001F3 RID: 499
		SystemSystemDiskInformation,
		// Token: 0x040001F4 RID: 500
		SystemProcessorPerformanceDistribution,
		// Token: 0x040001F5 RID: 501
		SystemNumaProximityNodeInformation,
		// Token: 0x040001F6 RID: 502
		SystemDynamicTimeZoneInformation,
		// Token: 0x040001F7 RID: 503
		SystemCodeIntegrityInformation,
		// Token: 0x040001F8 RID: 504
		SystemProcessorMicrocodeUpdateInformation,
		// Token: 0x040001F9 RID: 505
		SystemProcessorBrandString,
		// Token: 0x040001FA RID: 506
		SystemVirtualAddressInformation,
		// Token: 0x040001FB RID: 507
		SystemLogicalProcessorAndGroupInformation,
		// Token: 0x040001FC RID: 508
		SystemProcessorCycleTimeInformation,
		// Token: 0x040001FD RID: 509
		SystemStoreInformation,
		// Token: 0x040001FE RID: 510
		SystemRegistryAppendString,
		// Token: 0x040001FF RID: 511
		SystemAitSamplingValue,
		// Token: 0x04000200 RID: 512
		SystemVhdBootInformation,
		// Token: 0x04000201 RID: 513
		SystemCpuQuotaInformation,
		// Token: 0x04000202 RID: 514
		SystemNativeBasicInformation,
		// Token: 0x04000203 RID: 515
		SystemSpare1,
		// Token: 0x04000204 RID: 516
		SystemLowPriorityIoInformation,
		// Token: 0x04000205 RID: 517
		SystemTpmBootEntropyInformation,
		// Token: 0x04000206 RID: 518
		SystemVerifierCountersInformation,
		// Token: 0x04000207 RID: 519
		SystemPagedPoolInformationEx,
		// Token: 0x04000208 RID: 520
		SystemSystemPtesInformationEx,
		// Token: 0x04000209 RID: 521
		SystemNodeDistanceInformation,
		// Token: 0x0400020A RID: 522
		SystemAcpiAuditInformation,
		// Token: 0x0400020B RID: 523
		SystemBasicPerformanceInformation,
		// Token: 0x0400020C RID: 524
		SystemQueryPerformanceCounterInformation,
		// Token: 0x0400020D RID: 525
		SystemSessionBigPoolInformation,
		// Token: 0x0400020E RID: 526
		SystemBootGraphicsInformation,
		// Token: 0x0400020F RID: 527
		SystemScrubPhysicalMemoryInformation,
		// Token: 0x04000210 RID: 528
		SystemBadPageInformation,
		// Token: 0x04000211 RID: 529
		SystemProcessorProfileControlArea,
		// Token: 0x04000212 RID: 530
		SystemCombinePhysicalMemoryInformation,
		// Token: 0x04000213 RID: 531
		SystemEntropyInterruptTimingCallback,
		// Token: 0x04000214 RID: 532
		SystemConsoleInformation,
		// Token: 0x04000215 RID: 533
		SystemPlatformBinaryInformation,
		// Token: 0x04000216 RID: 534
		SystemThrottleNotificationInformation,
		// Token: 0x04000217 RID: 535
		SystemHypervisorProcessorCountInformation,
		// Token: 0x04000218 RID: 536
		SystemDeviceDataInformation,
		// Token: 0x04000219 RID: 537
		SystemDeviceDataEnumerationInformation,
		// Token: 0x0400021A RID: 538
		SystemMemoryTopologyInformation,
		// Token: 0x0400021B RID: 539
		SystemMemoryChannelInformation,
		// Token: 0x0400021C RID: 540
		SystemBootLogoInformation,
		// Token: 0x0400021D RID: 541
		SystemProcessorPerformanceInformationEx,
		// Token: 0x0400021E RID: 542
		SystemSpare0,
		// Token: 0x0400021F RID: 543
		SystemSecureBootPolicyInformation,
		// Token: 0x04000220 RID: 544
		SystemPageFileInformationEx,
		// Token: 0x04000221 RID: 545
		SystemSecureBootInformation,
		// Token: 0x04000222 RID: 546
		SystemEntropyInterruptTimingRawInformation,
		// Token: 0x04000223 RID: 547
		SystemPortableWorkspaceEfiLauncherInformation,
		// Token: 0x04000224 RID: 548
		SystemFullProcessInformation,
		// Token: 0x04000225 RID: 549
		SystemKernelDebuggerInformationEx,
		// Token: 0x04000226 RID: 550
		SystemBootMetadataInformation,
		// Token: 0x04000227 RID: 551
		SystemSoftRebootInformation,
		// Token: 0x04000228 RID: 552
		SystemElamCertificateInformation,
		// Token: 0x04000229 RID: 553
		SystemOfflineDumpConfigInformation,
		// Token: 0x0400022A RID: 554
		SystemProcessorFeaturesInformation,
		// Token: 0x0400022B RID: 555
		SystemRegistryReconciliationInformation,
		// Token: 0x0400022C RID: 556
		SystemEdidInformation,
		// Token: 0x0400022D RID: 557
		MaxSystemInfoClass
	}
}
