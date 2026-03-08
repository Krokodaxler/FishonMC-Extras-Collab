package io.github.markassk.fishonmcextras.FOMC.Enums;

import io.github.markassk.fishonmcextras.util.TextHelper;
import net.minecraft.text.Text;

import static io.github.markassk.fishonmcextras.FOMC.Types.Defaults.DEFAULT_COLOR;

public enum PetType implements EnumConstant {
    BULLFROG("bullfrog", TextHelper.concat(
            Text.literal("B").withColor(0x84CA54),
            Text.literal("u").withColor(0x7FC054),
            Text.literal("l").withColor(0x79B754),
            Text.literal("l").withColor(0x74AD54),
            Text.literal("f").withColor(0x6FA354),
            Text.literal("r").withColor(0x6A9954),
            Text.literal("o").withColor(0x649054),
            Text.literal("g Pet").withColor(0x5F8654)),
            0x5F8654),

    BEAR("bear", TextHelper.concat(
            Text.literal("B").withColor(0x593E3B),
            Text.literal("e").withColor(0x583C3A),
            Text.literal("a").withColor(0x573B3A),
            Text.literal("r Pet").withColor(0x563939)),
            0x563939),

    FOX("fox", TextHelper.concat(
            Text.literal("F").withColor(0xF99752),
            Text.literal("o").withColor(0xF2A75D),
            Text.literal("x Pet").withColor(0xEBB668)),
            0xEBB668),

    KANGAROO("kangaroo", TextHelper.concat(
            Text.literal("K").withColor(0xD19E58),
            Text.literal("a").withColor(0xD1A460),
            Text.literal("n").withColor(0xD1AA69),
            Text.literal("g").withColor(0xD1B071),
            Text.literal("a").withColor(0xD1B779),
            Text.literal("r").withColor(0xD1BD81),
            Text.literal("o").withColor(0xD1C38A),
            Text.literal("o Pet").withColor(0xD1C992)),
            0xD1C992),

    MARSH_RABBIT("marshrabbit", TextHelper.concat(
            Text.literal("M").withColor(0x968F73),
            Text.literal("a").withColor(0x928E71),
            Text.literal("r").withColor(0x8D8D70),
            Text.literal("s").withColor(0x898C6E),
            Text.literal("h").withColor(0x858B6D),
            Text.literal(""),
            Text.literal("R").withColor(0x7C896A),
            Text.literal("a").withColor(0x788868),
            Text.literal("b").withColor(0x748767),
            Text.literal("b").withColor(0x708665),
            Text.literal("i").withColor(0x6B8564),
            Text.literal("t Pet").withColor(0x678462)),
            0x678462),

    SEA_TURTLE("seaturtle", TextHelper.concat(
            Text.literal("S").withColor(0x69BE7B),
            Text.literal("e").withColor(0x71C27E),
            Text.literal("a").withColor(0x79C781),
            Text.literal(""),
            Text.literal("T").withColor(0x89D087),
            Text.literal("u").withColor(0x92D48A),
            Text.literal("r").withColor(0x9AD98D),
            Text.literal("t").withColor(0xA2DD90),
            Text.literal("l").withColor(0xAAE293),
            Text.literal("e Pet").withColor(0xB2E696)),
            0xB2E696),

    DUCK("duck", TextHelper.concat(
            Text.literal("D").withColor(0xEBEAA8),
            Text.literal("u").withColor(0xE2E2A5),
            Text.literal("c").withColor(0xD9DAA3),
            Text.literal("k Pet").withColor(0xD0D2A0)),
            0xD0D2A0),

    EAGLE("eagle", TextHelper.concat(
            Text.literal("E").withColor(0xBEBEBE),
            Text.literal("a").withColor(0xBAB8B6),
            Text.literal("g").withColor(0xB5B3AE),
            Text.literal("l").withColor(0xB1ADA5),
            Text.literal("e Pet ").withColor(0xACA79D)),
            0xACA79D),

    WOLF("wolf", TextHelper.concat(
            Text.literal("W").withColor(0x818587),
            Text.literal("o").withColor(0x7C8083),
            Text.literal("l").withColor(0x787B7F),
            Text.literal("f Pet").withColor(0x73767B)),
            0x73767B),

    PELICAN("pelican", TextHelper.concat(
            Text.literal("P").withColor(0xD9CBA6),
            Text.literal("e").withColor(0xDFC59B),
            Text.literal("l").withColor(0xE6BE90),
            Text.literal("i").withColor(0xECB886),
            Text.literal("c").withColor(0xF2B27B),
            Text.literal("a").withColor(0xF9AB70),
            Text.literal("n Pet").withColor(0xFFA565)),
            0xFFA565),

    CAPYBARA("capybara", TextHelper.concat(
            Text.literal("C").withColor(0x725E39),
            Text.literal("a").withColor(0x7F663F),
            Text.literal("p").withColor(0x8C6E45),
            Text.literal("y").withColor(0x99764B),
            Text.literal("b").withColor(0xA77D51),
            Text.literal("a").withColor(0xB48557),
            Text.literal("r").withColor(0xC18D5D),
            Text.literal("a Pet").withColor(0xCE9563)),
            0xCE9563),

    LYNX("lynx", TextHelper.concat(
            Text.literal("L").withColor(0xA1A278),
            Text.literal("y").withColor(0xA4A571),
            Text.literal("n").withColor(0xA6A96A),
            Text.literal("x Pet").withColor(0xA9AC63)),
            0xA9AC63),

    SHARK("shark", TextHelper.concat(
            Text.literal("S").withColor(0x6C8BE4),
            Text.literal("h").withColor(0x7190DB),
            Text.literal("a").withColor(0x7694D2),
            Text.literal("r").withColor(0x7B99C9),
            Text.literal("k Pet").withColor(0x809DC0)),
            0x809DC0),

    DOLPHIN("dolphin", TextHelper.concat(
            Text.literal("D").withColor(0xBAC7E4),
            Text.literal("o").withColor(0xB8C7DE),
            Text.literal("l").withColor(0xB6C6D8),
            Text.literal("p").withColor(0xB4C6D2),
            Text.literal("h").withColor(0xB1C6CB),
            Text.literal("i").withColor(0xAFC5C5),
            Text.literal("n Pet").withColor(0xADC5BF)),
            0xADC5BF),

    SHEEP("sheep", TextHelper.concat(
            Text.literal("S").withColor(0xADADAD),
            Text.literal("h").withColor(0x757575),
            Text.literal("e").withColor(0x8E918C),
            Text.literal("e").withColor(0xA6ACA3),
            Text.literal("p Pet").withColor(0xDFDFDF)),
            0xDFDFDF),

    KOALA("koala", TextHelper.concat(
            Text.literal("K").withColor(0xAEBFD1),
            Text.literal("o").withColor(0xB1C4D0),
            Text.literal("a").withColor(0xB3C8CF),
            Text.literal("l").withColor(0xB6CDCE),
            Text.literal("a Pet").withColor(0xB8D1CD)),
            0xB8D1CD),

    UNKNOWN(ID_UNKNOWN, Text.empty(), DEFAULT_COLOR);

    public static final EnumLookup<PetType> LOOKUP = new EnumLookup<>(values());

    public final String ID;
    public final Text TAG;
    public final int COLOR;

    PetType(String id, Text tag, int color) {
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
