/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import java.util.Locale;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.BuriedTreasure;
import net.minecraft.world.gen.feature.structure.DesertPyramidPiece;
import net.minecraft.world.gen.feature.structure.EndCityPieces;
import net.minecraft.world.gen.feature.structure.FortressPieces;
import net.minecraft.world.gen.feature.structure.IglooPieces;
import net.minecraft.world.gen.feature.structure.JunglePyramidPiece;
import net.minecraft.world.gen.feature.structure.MineshaftPieces;
import net.minecraft.world.gen.feature.structure.NetherFossilStructures;
import net.minecraft.world.gen.feature.structure.OceanMonumentPieces;
import net.minecraft.world.gen.feature.structure.OceanRuinPieces;
import net.minecraft.world.gen.feature.structure.RuinedPortalPiece;
import net.minecraft.world.gen.feature.structure.ShipwreckPieces;
import net.minecraft.world.gen.feature.structure.StrongholdPieces;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.SwampHutPiece;
import net.minecraft.world.gen.feature.structure.WoodlandMansionPieces;
import net.minecraft.world.gen.feature.template.TemplateManager;

public interface IStructurePieceType {
    public static final IStructurePieceType MSCORRIDOR = IStructurePieceType.register(MineshaftPieces.Corridor::new, "MSCorridor");
    public static final IStructurePieceType MSCROSSING = IStructurePieceType.register(MineshaftPieces.Cross::new, "MSCrossing");
    public static final IStructurePieceType MSROOM = IStructurePieceType.register(MineshaftPieces.Room::new, "MSRoom");
    public static final IStructurePieceType MSSTAIRS = IStructurePieceType.register(MineshaftPieces.Stairs::new, "MSStairs");
    public static final IStructurePieceType NEBCR = IStructurePieceType.register(FortressPieces.Crossing3::new, "NeBCr");
    public static final IStructurePieceType NEBEF = IStructurePieceType.register(FortressPieces.End::new, "NeBEF");
    public static final IStructurePieceType NEBS = IStructurePieceType.register(FortressPieces.Straight::new, "NeBS");
    public static final IStructurePieceType NECCS = IStructurePieceType.register(FortressPieces.Corridor3::new, "NeCCS");
    public static final IStructurePieceType NECTB = IStructurePieceType.register(FortressPieces.Corridor4::new, "NeCTB");
    public static final IStructurePieceType NECE = IStructurePieceType.register(FortressPieces.Entrance::new, "NeCE");
    public static final IStructurePieceType NESCSC = IStructurePieceType.register(FortressPieces.Crossing2::new, "NeSCSC");
    public static final IStructurePieceType NESCLT = IStructurePieceType.register(FortressPieces.Corridor::new, "NeSCLT");
    public static final IStructurePieceType NESC = IStructurePieceType.register(FortressPieces.Corridor5::new, "NeSC");
    public static final IStructurePieceType NESCRT = IStructurePieceType.register(FortressPieces.Corridor2::new, "NeSCRT");
    public static final IStructurePieceType NECSR = IStructurePieceType.register(FortressPieces.NetherStalkRoom::new, "NeCSR");
    public static final IStructurePieceType NEMT = IStructurePieceType.register(FortressPieces.Throne::new, "NeMT");
    public static final IStructurePieceType NERC = IStructurePieceType.register(FortressPieces.Crossing::new, "NeRC");
    public static final IStructurePieceType NESR = IStructurePieceType.register(FortressPieces.Stairs::new, "NeSR");
    public static final IStructurePieceType NESTART = IStructurePieceType.register(FortressPieces.Start::new, "NeStart");
    public static final IStructurePieceType SHCC = IStructurePieceType.register(StrongholdPieces.ChestCorridor::new, "SHCC");
    public static final IStructurePieceType SHFC = IStructurePieceType.register(StrongholdPieces.Corridor::new, "SHFC");
    public static final IStructurePieceType SH5C = IStructurePieceType.register(StrongholdPieces.Crossing::new, "SH5C");
    public static final IStructurePieceType SHLT = IStructurePieceType.register(StrongholdPieces.LeftTurn::new, "SHLT");
    public static final IStructurePieceType SHLI = IStructurePieceType.register(StrongholdPieces.Library::new, "SHLi");
    public static final IStructurePieceType SHPR = IStructurePieceType.register(StrongholdPieces.PortalRoom::new, "SHPR");
    public static final IStructurePieceType SHPH = IStructurePieceType.register(StrongholdPieces.Prison::new, "SHPH");
    public static final IStructurePieceType SHRT = IStructurePieceType.register(StrongholdPieces.RightTurn::new, "SHRT");
    public static final IStructurePieceType SHRC = IStructurePieceType.register(StrongholdPieces.RoomCrossing::new, "SHRC");
    public static final IStructurePieceType SHSD = IStructurePieceType.register(StrongholdPieces.Stairs::new, "SHSD");
    public static final IStructurePieceType SHSTART = IStructurePieceType.register(StrongholdPieces.Stairs2::new, "SHStart");
    public static final IStructurePieceType SHS = IStructurePieceType.register(StrongholdPieces.Straight::new, "SHS");
    public static final IStructurePieceType SHSSD = IStructurePieceType.register(StrongholdPieces.StairsStraight::new, "SHSSD");
    public static final IStructurePieceType TEJP = IStructurePieceType.register(JunglePyramidPiece::new, "TeJP");
    public static final IStructurePieceType ORP = IStructurePieceType.register(OceanRuinPieces.Piece::new, "ORP");
    public static final IStructurePieceType IGLU = IStructurePieceType.register(IglooPieces.Piece::new, "Iglu");
    public static final IStructurePieceType RUINED_PORTAL = IStructurePieceType.register(RuinedPortalPiece::new, "RUPO");
    public static final IStructurePieceType TESH = IStructurePieceType.register(SwampHutPiece::new, "TeSH");
    public static final IStructurePieceType TEDP = IStructurePieceType.register(DesertPyramidPiece::new, "TeDP");
    public static final IStructurePieceType OMB = IStructurePieceType.register(OceanMonumentPieces.MonumentBuilding::new, "OMB");
    public static final IStructurePieceType OMCR = IStructurePieceType.register(OceanMonumentPieces.MonumentCoreRoom::new, "OMCR");
    public static final IStructurePieceType OMDXR = IStructurePieceType.register(OceanMonumentPieces.DoubleXRoom::new, "OMDXR");
    public static final IStructurePieceType OMDXYR = IStructurePieceType.register(OceanMonumentPieces.DoubleXYRoom::new, "OMDXYR");
    public static final IStructurePieceType OMDYR = IStructurePieceType.register(OceanMonumentPieces.DoubleYRoom::new, "OMDYR");
    public static final IStructurePieceType OMDYZR = IStructurePieceType.register(OceanMonumentPieces.DoubleYZRoom::new, "OMDYZR");
    public static final IStructurePieceType OMDZR = IStructurePieceType.register(OceanMonumentPieces.DoubleZRoom::new, "OMDZR");
    public static final IStructurePieceType OMENTRY = IStructurePieceType.register(OceanMonumentPieces.EntryRoom::new, "OMEntry");
    public static final IStructurePieceType OMPENTHOUSE = IStructurePieceType.register(OceanMonumentPieces.Penthouse::new, "OMPenthouse");
    public static final IStructurePieceType OMSIMPLE = IStructurePieceType.register(OceanMonumentPieces.SimpleRoom::new, "OMSimple");
    public static final IStructurePieceType OMSIMPLET = IStructurePieceType.register(OceanMonumentPieces.SimpleTopRoom::new, "OMSimpleT");
    public static final IStructurePieceType OMWR = IStructurePieceType.register(OceanMonumentPieces.WingRoom::new, "OMWR");
    public static final IStructurePieceType ECP = IStructurePieceType.register(EndCityPieces.CityTemplate::new, "ECP");
    public static final IStructurePieceType WMP = IStructurePieceType.register(WoodlandMansionPieces.MansionTemplate::new, "WMP");
    public static final IStructurePieceType BTP = IStructurePieceType.register(BuriedTreasure.Piece::new, "BTP");
    public static final IStructurePieceType SHIPWRECK = IStructurePieceType.register(ShipwreckPieces.Piece::new, "Shipwreck");
    public static final IStructurePieceType NETHER_FOSSIL = IStructurePieceType.register(NetherFossilStructures.Piece::new, "NeFos");
    public static final IStructurePieceType field_242786_ad = IStructurePieceType.register(AbstractVillagePiece::new, "jigsaw");

    public StructurePiece load(TemplateManager var1, CompoundNBT var2);

    public static IStructurePieceType register(IStructurePieceType iStructurePieceType, String string) {
        return Registry.register(Registry.STRUCTURE_PIECE, string.toLowerCase(Locale.ROOT), iStructurePieceType);
    }
}

