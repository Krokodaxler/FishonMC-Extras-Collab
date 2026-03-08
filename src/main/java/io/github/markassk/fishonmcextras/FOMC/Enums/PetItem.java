package io.github.markassk.fishonmcextras.FOMC.Enums;

import io.github.markassk.fishonmcextras.util.TextHelper;
import net.minecraft.text.Text;

import static io.github.markassk.fishonmcextras.FOMC.Types.Defaults.DEFAULT_COLOR;

public enum PetItem implements EnumConstant {
    BIGFOOTS_AMULET("bigfootsamulet", TextHelper.concat(
            Text.literal("B").withColor(0xB7884A),
            Text.literal("i").withColor(0xB7884A),
            Text.literal("g").withColor(0xB7884A),
            Text.literal("f").withColor(0xB6884F),
            Text.literal("o").withColor(0xB58854),
            Text.literal("o").withColor(0xB58958),
            Text.literal("t").withColor(0xB4895D),
            Text.literal("'").withColor(0xB38962),
            Text.literal("s ").withColor(0xBE7858),
            Text.literal("A").withColor(0xD55744),
            Text.literal("m").withColor(0xE04639),
            Text.literal("u").withColor(0xEC362F),
            Text.literal("l").withColor(0xF72525),
            Text.literal("e").withColor(0xF72525),
            Text.literal("t").withColor(0xF72525)),
            0xF72525),

    BIGFOOTS_NECKLACE("bigfootsnecklace", TextHelper.concat(
            Text.literal("B").withColor(0xB7884A),
            Text.literal("i").withColor(0xB7884A),
            Text.literal("g").withColor(0xB7884A),
            Text.literal("f").withColor(0xB6884E),
            Text.literal("o").withColor(0xB68852),
            Text.literal("o").withColor(0xB58956),
            Text.literal("t").withColor(0xB4895A),
            Text.literal("'").withColor(0xB4895E),
            Text.literal("s ").withColor(0xB38962),
            Text.literal("N").withColor(0xC3A78D),
            Text.literal("e").withColor(0xCBB6A2),
            Text.literal("c").withColor(0xD2C4B7),
            Text.literal("k").withColor(0xDAD3CD),
            Text.literal("l").withColor(0xE2E2E2),
            Text.literal("a").withColor(0xE2E2E2),
            Text.literal("c").withColor(0xE2E2E2),
            Text.literal("e").withColor(0xE2E2E2)),
            0xE2E2E2),

    WADES_AMULET("wadesamulet", TextHelper.concat(
            Text.literal("W").withColor(0x9BAFDB),
            Text.literal("a").withColor(0x95ADDA),
            Text.literal("d").withColor(0x8FABD9),
            Text.literal("e").withColor(0x89AAD8),
            Text.literal("'").withColor(0x83A8D7),
            Text.literal("s ").withColor(0x7CA6D6),
            Text.literal("A").withColor(0x589BD0),
            Text.literal("m").withColor(0x5299CF),
            Text.literal("u").withColor(0x4B97CE),
            Text.literal("l").withColor(0x4596CD),
            Text.literal("e").withColor(0x3F94CC),
            Text.literal("t").withColor(0x3992CB)),
            0x3992CB),

    MIDAS_AMULET("midasamulet", TextHelper.concat(
            Text.literal("M").withColor(0x938420),
            Text.literal("i").withColor(0x9D9024),
            Text.literal("d").withColor(0xA79D28),
            Text.literal("a").withColor(0xB1A92C),
            Text.literal("s ").withColor(0xBAB530),
            Text.literal("A").withColor(0xCECE38),
            Text.literal("m").withColor(0xC8CB39),
            Text.literal("u").withColor(0xC2C839),
            Text.literal("l").withColor(0xBBC53A),
            Text.literal("e").withColor(0xB5C23A),
            Text.literal("t").withColor(0xAFBF3B)),
            0xAFBF3B),

    ONYX_AMULET("onyxamulet", TextHelper.concat(
            Text.literal("O").withColor(0x5A5E62),
            Text.literal("n").withColor(0x5D6266),
            Text.literal("y").withColor(0x606669),
            Text.literal("x Amulet").withColor(0x63696D)),
            0x63696D),

    IVORY_AMULET("ivoryamulet", TextHelper.concat(
            Text.literal("I").withColor(0xE3C6E2),
            Text.literal("v").withColor(0xE2CDE3),
            Text.literal("o").withColor(0xE2D4E5),
            Text.literal("r").withColor(0xE1DAE6),
            Text.literal("y Amulet").withColor(0xE1E1E8)),
            0xE1E1E8),

    FABLED_AMULET("fabledamulet", TextHelper.concat(
            Text.literal("F").withColor(0xFA463F),
            Text.literal("a").withColor(0xFA443D),
            Text.literal("b").withColor(0xFB433B),
            Text.literal("l").withColor(0xFB4139),
            Text.literal("e").withColor(0xFC3F37),
            Text.literal("d Amulet").withColor(0xFC3E35)),
            0xFC3E35),

    UNKNOWN(ID_UNKNOWN, Text.empty(), DEFAULT_COLOR);

    public static final EnumLookup<PetItem> LOOKUP = new EnumLookup<>(values());

    public final String ID;
    public final Text TAG;
    public final int COLOR;

    PetItem(String id, Text tag, int color) {
        this.ID = id;
        this.TAG = tag;
        this.COLOR = color;
    }

    @Override
    public String id() {
        return this.ID;
    }

    @Override
    public Text tag() {
        return this.TAG;
    }

    @Override
    public int color() {
        return this.COLOR;
    }

    @Override
    public String toString() {
        return this.ID;
    }
}
