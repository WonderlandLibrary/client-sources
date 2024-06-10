using System;

namespace New_CandyClient.ProtectionTools.Utils
{
	// Token: 0x02000030 RID: 48
	public enum NtStatus : uint
	{
		// Token: 0x04000157 RID: 343
		Success,
		// Token: 0x04000158 RID: 344
		Wait0 = 0U,
		// Token: 0x04000159 RID: 345
		Wait1,
		// Token: 0x0400015A RID: 346
		Wait2,
		// Token: 0x0400015B RID: 347
		Wait3,
		// Token: 0x0400015C RID: 348
		Wait63 = 63U,
		// Token: 0x0400015D RID: 349
		Abandoned = 128U,
		// Token: 0x0400015E RID: 350
		AbandonedWait0 = 128U,
		// Token: 0x0400015F RID: 351
		AbandonedWait1,
		// Token: 0x04000160 RID: 352
		AbandonedWait2,
		// Token: 0x04000161 RID: 353
		AbandonedWait3,
		// Token: 0x04000162 RID: 354
		AbandonedWait63 = 191U,
		// Token: 0x04000163 RID: 355
		UserApc,
		// Token: 0x04000164 RID: 356
		KernelApc = 256U,
		// Token: 0x04000165 RID: 357
		Alerted,
		// Token: 0x04000166 RID: 358
		Timeout,
		// Token: 0x04000167 RID: 359
		Pending,
		// Token: 0x04000168 RID: 360
		Reparse,
		// Token: 0x04000169 RID: 361
		MoreEntries,
		// Token: 0x0400016A RID: 362
		NotAllAssigned,
		// Token: 0x0400016B RID: 363
		SomeNotMapped,
		// Token: 0x0400016C RID: 364
		OpLockBreakInProgress,
		// Token: 0x0400016D RID: 365
		VolumeMounted,
		// Token: 0x0400016E RID: 366
		RxActCommitted,
		// Token: 0x0400016F RID: 367
		NotifyCleanup,
		// Token: 0x04000170 RID: 368
		NotifyEnumDir,
		// Token: 0x04000171 RID: 369
		NoQuotasForAccount,
		// Token: 0x04000172 RID: 370
		PrimaryTransportConnectFailed,
		// Token: 0x04000173 RID: 371
		PageFaultTransition = 272U,
		// Token: 0x04000174 RID: 372
		PageFaultDemandZero,
		// Token: 0x04000175 RID: 373
		PageFaultCopyOnWrite,
		// Token: 0x04000176 RID: 374
		PageFaultGuardPage,
		// Token: 0x04000177 RID: 375
		PageFaultPagingFile,
		// Token: 0x04000178 RID: 376
		CrashDump = 278U,
		// Token: 0x04000179 RID: 377
		ReparseObject = 280U,
		// Token: 0x0400017A RID: 378
		NothingToTerminate = 290U,
		// Token: 0x0400017B RID: 379
		ProcessNotInJob,
		// Token: 0x0400017C RID: 380
		ProcessInJob,
		// Token: 0x0400017D RID: 381
		ProcessCloned = 297U,
		// Token: 0x0400017E RID: 382
		FileLockedWithOnlyReaders,
		// Token: 0x0400017F RID: 383
		FileLockedWithWriters,
		// Token: 0x04000180 RID: 384
		Informational = 1073741824U,
		// Token: 0x04000181 RID: 385
		ObjectNameExists = 1073741824U,
		// Token: 0x04000182 RID: 386
		ThreadWasSuspended,
		// Token: 0x04000183 RID: 387
		WorkingSetLimitRange,
		// Token: 0x04000184 RID: 388
		ImageNotAtBase,
		// Token: 0x04000185 RID: 389
		RegistryRecovered = 1073741833U,
		// Token: 0x04000186 RID: 390
		Warning = 2147483648U,
		// Token: 0x04000187 RID: 391
		GuardPageViolation,
		// Token: 0x04000188 RID: 392
		DatatypeMisalignment,
		// Token: 0x04000189 RID: 393
		Breakpoint,
		// Token: 0x0400018A RID: 394
		SingleStep,
		// Token: 0x0400018B RID: 395
		BufferOverflow,
		// Token: 0x0400018C RID: 396
		NoMoreFiles,
		// Token: 0x0400018D RID: 397
		HandlesClosed = 2147483658U,
		// Token: 0x0400018E RID: 398
		PartialCopy = 2147483661U,
		// Token: 0x0400018F RID: 399
		DeviceBusy = 2147483665U,
		// Token: 0x04000190 RID: 400
		InvalidEaName = 2147483667U,
		// Token: 0x04000191 RID: 401
		EaListInconsistent,
		// Token: 0x04000192 RID: 402
		NoMoreEntries = 2147483674U,
		// Token: 0x04000193 RID: 403
		LongJump = 2147483686U,
		// Token: 0x04000194 RID: 404
		DllMightBeInsecure = 2147483691U,
		// Token: 0x04000195 RID: 405
		Error = 3221225472U,
		// Token: 0x04000196 RID: 406
		Unsuccessful,
		// Token: 0x04000197 RID: 407
		NotImplemented,
		// Token: 0x04000198 RID: 408
		InvalidInfoClass,
		// Token: 0x04000199 RID: 409
		InfoLengthMismatch,
		// Token: 0x0400019A RID: 410
		AccessViolation,
		// Token: 0x0400019B RID: 411
		InPageError,
		// Token: 0x0400019C RID: 412
		PagefileQuota,
		// Token: 0x0400019D RID: 413
		InvalidHandle,
		// Token: 0x0400019E RID: 414
		BadInitialStack,
		// Token: 0x0400019F RID: 415
		BadInitialPc,
		// Token: 0x040001A0 RID: 416
		InvalidCid,
		// Token: 0x040001A1 RID: 417
		TimerNotCanceled,
		// Token: 0x040001A2 RID: 418
		InvalidParameter,
		// Token: 0x040001A3 RID: 419
		NoSuchDevice,
		// Token: 0x040001A4 RID: 420
		NoSuchFile,
		// Token: 0x040001A5 RID: 421
		InvalidDeviceRequest,
		// Token: 0x040001A6 RID: 422
		EndOfFile,
		// Token: 0x040001A7 RID: 423
		WrongVolume,
		// Token: 0x040001A8 RID: 424
		NoMediaInDevice,
		// Token: 0x040001A9 RID: 425
		NoMemory = 3221225495U,
		// Token: 0x040001AA RID: 426
		NotMappedView = 3221225497U,
		// Token: 0x040001AB RID: 427
		UnableToFreeVm,
		// Token: 0x040001AC RID: 428
		UnableToDeleteSection,
		// Token: 0x040001AD RID: 429
		IllegalInstruction = 3221225501U,
		// Token: 0x040001AE RID: 430
		AlreadyCommitted = 3221225505U,
		// Token: 0x040001AF RID: 431
		AccessDenied,
		// Token: 0x040001B0 RID: 432
		BufferTooSmall,
		// Token: 0x040001B1 RID: 433
		ObjectTypeMismatch,
		// Token: 0x040001B2 RID: 434
		NonContinuableException,
		// Token: 0x040001B3 RID: 435
		BadStack = 3221225512U,
		// Token: 0x040001B4 RID: 436
		NotLocked = 3221225514U,
		// Token: 0x040001B5 RID: 437
		NotCommitted = 3221225517U,
		// Token: 0x040001B6 RID: 438
		InvalidParameterMix = 3221225520U,
		// Token: 0x040001B7 RID: 439
		ObjectNameInvalid = 3221225523U,
		// Token: 0x040001B8 RID: 440
		ObjectNameNotFound,
		// Token: 0x040001B9 RID: 441
		ObjectNameCollision,
		// Token: 0x040001BA RID: 442
		ObjectPathInvalid = 3221225529U,
		// Token: 0x040001BB RID: 443
		ObjectPathNotFound,
		// Token: 0x040001BC RID: 444
		ObjectPathSyntaxBad,
		// Token: 0x040001BD RID: 445
		DataOverrun,
		// Token: 0x040001BE RID: 446
		DataLate,
		// Token: 0x040001BF RID: 447
		DataError,
		// Token: 0x040001C0 RID: 448
		CrcError,
		// Token: 0x040001C1 RID: 449
		SectionTooBig,
		// Token: 0x040001C2 RID: 450
		PortConnectionRefused,
		// Token: 0x040001C3 RID: 451
		InvalidPortHandle,
		// Token: 0x040001C4 RID: 452
		SharingViolation,
		// Token: 0x040001C5 RID: 453
		QuotaExceeded,
		// Token: 0x040001C6 RID: 454
		InvalidPageProtection,
		// Token: 0x040001C7 RID: 455
		MutantNotOwned,
		// Token: 0x040001C8 RID: 456
		SemaphoreLimitExceeded,
		// Token: 0x040001C9 RID: 457
		PortAlreadySet,
		// Token: 0x040001CA RID: 458
		SectionNotImage,
		// Token: 0x040001CB RID: 459
		SuspendCountExceeded,
		// Token: 0x040001CC RID: 460
		ThreadIsTerminating,
		// Token: 0x040001CD RID: 461
		BadWorkingSetLimit,
		// Token: 0x040001CE RID: 462
		IncompatibleFileMap,
		// Token: 0x040001CF RID: 463
		SectionProtection,
		// Token: 0x040001D0 RID: 464
		EasNotSupported,
		// Token: 0x040001D1 RID: 465
		EaTooLarge,
		// Token: 0x040001D2 RID: 466
		NonExistentEaEntry,
		// Token: 0x040001D3 RID: 467
		NoEasOnFile,
		// Token: 0x040001D4 RID: 468
		EaCorruptError,
		// Token: 0x040001D5 RID: 469
		FileLockConflict,
		// Token: 0x040001D6 RID: 470
		LockNotGranted,
		// Token: 0x040001D7 RID: 471
		DeletePending,
		// Token: 0x040001D8 RID: 472
		CtlFileNotSupported,
		// Token: 0x040001D9 RID: 473
		UnknownRevision,
		// Token: 0x040001DA RID: 474
		RevisionMismatch,
		// Token: 0x040001DB RID: 475
		InvalidOwner,
		// Token: 0x040001DC RID: 476
		InvalidPrimaryGroup,
		// Token: 0x040001DD RID: 477
		NoImpersonationToken,
		// Token: 0x040001DE RID: 478
		CantDisableMandatory,
		// Token: 0x040001DF RID: 479
		NoLogonServers,
		// Token: 0x040001E0 RID: 480
		NoSuchLogonSession,
		// Token: 0x040001E1 RID: 481
		NoSuchPrivilege,
		// Token: 0x040001E2 RID: 482
		PrivilegeNotHeld,
		// Token: 0x040001E3 RID: 483
		InvalidAccountName,
		// Token: 0x040001E4 RID: 484
		UserExists,
		// Token: 0x040001E5 RID: 485
		NoSuchUser,
		// Token: 0x040001E6 RID: 486
		GroupExists,
		// Token: 0x040001E7 RID: 487
		NoSuchGroup,
		// Token: 0x040001E8 RID: 488
		MemberInGroup,
		// Token: 0x040001E9 RID: 489
		MemberNotInGroup,
		// Token: 0x040001EA RID: 490
		LastAdmin,
		// Token: 0x040001EB RID: 491
		WrongPassword,
		// Token: 0x040001EC RID: 492
		IllFormedPassword,
		// Token: 0x040001ED RID: 493
		PasswordRestriction,
		// Token: 0x040001EE RID: 494
		LogonFailure,
		// Token: 0x040001EF RID: 495
		AccountRestriction,
		// Token: 0x040001F0 RID: 496
		InvalidLogonHours,
		// Token: 0x040001F1 RID: 497
		InvalidWorkstation,
		// Token: 0x040001F2 RID: 498
		PasswordExpired,
		// Token: 0x040001F3 RID: 499
		AccountDisabled,
		// Token: 0x040001F4 RID: 500
		NoneMapped,
		// Token: 0x040001F5 RID: 501
		TooManyLuidsRequested,
		// Token: 0x040001F6 RID: 502
		LuidsExhausted,
		// Token: 0x040001F7 RID: 503
		InvalidSubAuthority,
		// Token: 0x040001F8 RID: 504
		InvalidAcl,
		// Token: 0x040001F9 RID: 505
		InvalidSid,
		// Token: 0x040001FA RID: 506
		InvalidSecurityDescr,
		// Token: 0x040001FB RID: 507
		ProcedureNotFound,
		// Token: 0x040001FC RID: 508
		InvalidImageFormat,
		// Token: 0x040001FD RID: 509
		NoToken,
		// Token: 0x040001FE RID: 510
		BadInheritanceAcl,
		// Token: 0x040001FF RID: 511
		RangeNotLocked,
		// Token: 0x04000200 RID: 512
		DiskFull,
		// Token: 0x04000201 RID: 513
		ServerDisabled,
		// Token: 0x04000202 RID: 514
		ServerNotDisabled,
		// Token: 0x04000203 RID: 515
		TooManyGuidsRequested,
		// Token: 0x04000204 RID: 516
		GuidsExhausted,
		// Token: 0x04000205 RID: 517
		InvalidIdAuthority,
		// Token: 0x04000206 RID: 518
		AgentsExhausted,
		// Token: 0x04000207 RID: 519
		InvalidVolumeLabel,
		// Token: 0x04000208 RID: 520
		SectionNotExtended,
		// Token: 0x04000209 RID: 521
		NotMappedData,
		// Token: 0x0400020A RID: 522
		ResourceDataNotFound,
		// Token: 0x0400020B RID: 523
		ResourceTypeNotFound,
		// Token: 0x0400020C RID: 524
		ResourceNameNotFound,
		// Token: 0x0400020D RID: 525
		ArrayBoundsExceeded,
		// Token: 0x0400020E RID: 526
		FloatDenormalOperand,
		// Token: 0x0400020F RID: 527
		FloatDivideByZero,
		// Token: 0x04000210 RID: 528
		FloatInexactResult,
		// Token: 0x04000211 RID: 529
		FloatInvalidOperation,
		// Token: 0x04000212 RID: 530
		FloatOverflow,
		// Token: 0x04000213 RID: 531
		FloatStackCheck,
		// Token: 0x04000214 RID: 532
		FloatUnderflow,
		// Token: 0x04000215 RID: 533
		IntegerDivideByZero,
		// Token: 0x04000216 RID: 534
		IntegerOverflow,
		// Token: 0x04000217 RID: 535
		PrivilegedInstruction,
		// Token: 0x04000218 RID: 536
		TooManyPagingFiles,
		// Token: 0x04000219 RID: 537
		FileInvalid,
		// Token: 0x0400021A RID: 538
		InstanceNotAvailable = 3221225643U,
		// Token: 0x0400021B RID: 539
		PipeNotAvailable,
		// Token: 0x0400021C RID: 540
		InvalidPipeState,
		// Token: 0x0400021D RID: 541
		PipeBusy,
		// Token: 0x0400021E RID: 542
		IllegalFunction,
		// Token: 0x0400021F RID: 543
		PipeDisconnected,
		// Token: 0x04000220 RID: 544
		PipeClosing,
		// Token: 0x04000221 RID: 545
		PipeConnected,
		// Token: 0x04000222 RID: 546
		PipeListening,
		// Token: 0x04000223 RID: 547
		InvalidReadMode,
		// Token: 0x04000224 RID: 548
		IoTimeout,
		// Token: 0x04000225 RID: 549
		FileForcedClosed,
		// Token: 0x04000226 RID: 550
		ProfilingNotStarted,
		// Token: 0x04000227 RID: 551
		ProfilingNotStopped,
		// Token: 0x04000228 RID: 552
		NotSameDevice = 3221225684U,
		// Token: 0x04000229 RID: 553
		FileRenamed,
		// Token: 0x0400022A RID: 554
		CantWait = 3221225688U,
		// Token: 0x0400022B RID: 555
		PipeEmpty,
		// Token: 0x0400022C RID: 556
		CantTerminateSelf = 3221225691U,
		// Token: 0x0400022D RID: 557
		InternalError = 3221225701U,
		// Token: 0x0400022E RID: 558
		InvalidParameter1 = 3221225711U,
		// Token: 0x0400022F RID: 559
		InvalidParameter2,
		// Token: 0x04000230 RID: 560
		InvalidParameter3,
		// Token: 0x04000231 RID: 561
		InvalidParameter4,
		// Token: 0x04000232 RID: 562
		InvalidParameter5,
		// Token: 0x04000233 RID: 563
		InvalidParameter6,
		// Token: 0x04000234 RID: 564
		InvalidParameter7,
		// Token: 0x04000235 RID: 565
		InvalidParameter8,
		// Token: 0x04000236 RID: 566
		InvalidParameter9,
		// Token: 0x04000237 RID: 567
		InvalidParameter10,
		// Token: 0x04000238 RID: 568
		InvalidParameter11,
		// Token: 0x04000239 RID: 569
		InvalidParameter12,
		// Token: 0x0400023A RID: 570
		MappedFileSizeZero = 3221225758U,
		// Token: 0x0400023B RID: 571
		TooManyOpenedFiles,
		// Token: 0x0400023C RID: 572
		Cancelled,
		// Token: 0x0400023D RID: 573
		CannotDelete,
		// Token: 0x0400023E RID: 574
		InvalidComputerName,
		// Token: 0x0400023F RID: 575
		FileDeleted,
		// Token: 0x04000240 RID: 576
		SpecialAccount,
		// Token: 0x04000241 RID: 577
		SpecialGroup,
		// Token: 0x04000242 RID: 578
		SpecialUser,
		// Token: 0x04000243 RID: 579
		MembersPrimaryGroup,
		// Token: 0x04000244 RID: 580
		FileClosed,
		// Token: 0x04000245 RID: 581
		TooManyThreads,
		// Token: 0x04000246 RID: 582
		ThreadNotInProcess,
		// Token: 0x04000247 RID: 583
		TokenAlreadyInUse,
		// Token: 0x04000248 RID: 584
		PagefileQuotaExceeded,
		// Token: 0x04000249 RID: 585
		CommitmentLimit,
		// Token: 0x0400024A RID: 586
		InvalidImageLeFormat,
		// Token: 0x0400024B RID: 587
		InvalidImageNotMz,
		// Token: 0x0400024C RID: 588
		InvalidImageProtect,
		// Token: 0x0400024D RID: 589
		InvalidImageWin16,
		// Token: 0x0400024E RID: 590
		LogonServer,
		// Token: 0x0400024F RID: 591
		DifferenceAtDc,
		// Token: 0x04000250 RID: 592
		SynchronizationRequired,
		// Token: 0x04000251 RID: 593
		DllNotFound,
		// Token: 0x04000252 RID: 594
		IoPrivilegeFailed = 3221225783U,
		// Token: 0x04000253 RID: 595
		OrdinalNotFound,
		// Token: 0x04000254 RID: 596
		EntryPointNotFound,
		// Token: 0x04000255 RID: 597
		ControlCExit,
		// Token: 0x04000256 RID: 598
		PortNotSet = 3221226323U,
		// Token: 0x04000257 RID: 599
		DebuggerInactive,
		// Token: 0x04000258 RID: 600
		CallbackBypass = 3221226755U,
		// Token: 0x04000259 RID: 601
		PortClosed = 3221227264U,
		// Token: 0x0400025A RID: 602
		MessageLost,
		// Token: 0x0400025B RID: 603
		InvalidMessage,
		// Token: 0x0400025C RID: 604
		RequestCanceled,
		// Token: 0x0400025D RID: 605
		RecursiveDispatch,
		// Token: 0x0400025E RID: 606
		LpcReceiveBufferExpected,
		// Token: 0x0400025F RID: 607
		LpcInvalidConnectionUsage,
		// Token: 0x04000260 RID: 608
		LpcRequestsNotAllowed,
		// Token: 0x04000261 RID: 609
		ResourceInUse,
		// Token: 0x04000262 RID: 610
		ProcessIsProtected = 3221227282U,
		// Token: 0x04000263 RID: 611
		VolumeDirty = 3221227526U,
		// Token: 0x04000264 RID: 612
		FileCheckedOut = 3221227777U,
		// Token: 0x04000265 RID: 613
		CheckOutRequired,
		// Token: 0x04000266 RID: 614
		BadFileType,
		// Token: 0x04000267 RID: 615
		FileTooLarge,
		// Token: 0x04000268 RID: 616
		FormsAuthRequired,
		// Token: 0x04000269 RID: 617
		VirusInfected,
		// Token: 0x0400026A RID: 618
		VirusDeleted,
		// Token: 0x0400026B RID: 619
		TransactionalConflict = 3222863873U,
		// Token: 0x0400026C RID: 620
		InvalidTransaction,
		// Token: 0x0400026D RID: 621
		TransactionNotActive,
		// Token: 0x0400026E RID: 622
		TmInitializationFailed,
		// Token: 0x0400026F RID: 623
		RmNotActive,
		// Token: 0x04000270 RID: 624
		RmMetadataCorrupt,
		// Token: 0x04000271 RID: 625
		TransactionNotJoined,
		// Token: 0x04000272 RID: 626
		DirectoryNotRm,
		// Token: 0x04000273 RID: 627
		CouldNotResizeLog,
		// Token: 0x04000274 RID: 628
		TransactionsUnsupportedRemote,
		// Token: 0x04000275 RID: 629
		LogResizeInvalidSize,
		// Token: 0x04000276 RID: 630
		RemoteFileVersionMismatch,
		// Token: 0x04000277 RID: 631
		CrmProtocolAlreadyExists = 3222863887U,
		// Token: 0x04000278 RID: 632
		TransactionPropagationFailed,
		// Token: 0x04000279 RID: 633
		CrmProtocolNotFound,
		// Token: 0x0400027A RID: 634
		TransactionSuperiorExists,
		// Token: 0x0400027B RID: 635
		TransactionRequestNotValid,
		// Token: 0x0400027C RID: 636
		TransactionNotRequested,
		// Token: 0x0400027D RID: 637
		TransactionAlreadyAborted,
		// Token: 0x0400027E RID: 638
		TransactionAlreadyCommitted,
		// Token: 0x0400027F RID: 639
		TransactionInvalidMarshallBuffer,
		// Token: 0x04000280 RID: 640
		CurrentTransactionNotValid,
		// Token: 0x04000281 RID: 641
		LogGrowthFailed,
		// Token: 0x04000282 RID: 642
		ObjectNoLongerExists = 3222863905U,
		// Token: 0x04000283 RID: 643
		StreamMiniversionNotFound,
		// Token: 0x04000284 RID: 644
		StreamMiniversionNotValid,
		// Token: 0x04000285 RID: 645
		MiniversionInaccessibleFromSpecifiedTransaction,
		// Token: 0x04000286 RID: 646
		CantOpenMiniversionWithModifyIntent,
		// Token: 0x04000287 RID: 647
		CantCreateMoreStreamMiniversions,
		// Token: 0x04000288 RID: 648
		HandleNoLongerValid = 3222863912U,
		// Token: 0x04000289 RID: 649
		NoTxfMetadata,
		// Token: 0x0400028A RID: 650
		LogCorruptionDetected = 3222863920U,
		// Token: 0x0400028B RID: 651
		CantRecoverWithHandleOpen,
		// Token: 0x0400028C RID: 652
		RmDisconnected,
		// Token: 0x0400028D RID: 653
		EnlistmentNotSuperior,
		// Token: 0x0400028E RID: 654
		RecoveryNotNeeded,
		// Token: 0x0400028F RID: 655
		RmAlreadyStarted,
		// Token: 0x04000290 RID: 656
		FileIdentityNotPersistent,
		// Token: 0x04000291 RID: 657
		CantBreakTransactionalDependency,
		// Token: 0x04000292 RID: 658
		CantCrossRmBoundary,
		// Token: 0x04000293 RID: 659
		TxfDirNotEmpty,
		// Token: 0x04000294 RID: 660
		IndoubtTransactionsExist,
		// Token: 0x04000295 RID: 661
		TmVolatile,
		// Token: 0x04000296 RID: 662
		RollbackTimerExpired,
		// Token: 0x04000297 RID: 663
		TxfAttributeCorrupt,
		// Token: 0x04000298 RID: 664
		EfsNotAllowedInTransaction,
		// Token: 0x04000299 RID: 665
		TransactionalOpenNotAllowed,
		// Token: 0x0400029A RID: 666
		TransactedMappingUnsupportedRemote,
		// Token: 0x0400029B RID: 667
		TxfMetadataAlreadyPresent,
		// Token: 0x0400029C RID: 668
		TransactionScopeCallbacksNotSet,
		// Token: 0x0400029D RID: 669
		TransactionRequiredPromotion,
		// Token: 0x0400029E RID: 670
		CannotExecuteFileInTransaction,
		// Token: 0x0400029F RID: 671
		TransactionsNotFrozen,
		// Token: 0x040002A0 RID: 672
		MaximumNtStatus = 4294967295U
	}
}
