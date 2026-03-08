package io.github.markassk.fishonmcextras.FOMC.Enums;

import io.github.markassk.fishonmcextras.FOMC.Types.Defaults;
import io.github.markassk.fishonmcextras.util.TextHelper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static io.github.markassk.fishonmcextras.FOMC.Types.Defaults.DEFAULT_COLOR;

public enum RareCatch implements EnumConstant {
    SHARD("shard", Text.literal("Shard").formatted(Formatting.GOLD), 0xfca800),
    PET("pet", Text.literal("Pet").withColor(0xFD95F6), 0xFD95F6),
    LIGHTNING_BOTTLE("lightning bottle", TextHelper.concat(
            Text.literal("L").withColor(0xEFE038),
            Text.literal("i").withColor(0xEFC32C),
            Text.literal("g").withColor(0xEABC34),
            Text.literal("h").withColor(0xE4B43C),
            Text.literal("t").withColor(0xEABC34),
            Text.literal("n").withColor(0xEFC32C),
            Text.literal("i").withColor(0xEFE038),
            Text.literal("n").withColor(0xEFFC44),
            Text.literal("g in a Bottle").withColor(0xE4B43C)),
            0xe1b23b),
    INFUSION_CAPSULE("infusion capsule", Text.literal("Infusion Capsule").formatted(Formatting.WHITE), Defaults.DEFAULT_COLOR),
    PROSPECTING_AMULET("prospectingamulet", TextHelper.concat(
            Text.literal("P").withColor(0xE4CC2B),
            Text.literal("r").withColor(0xE3CE2D),
            Text.literal("o").withColor(0xE3D030),
            Text.literal("s").withColor(0xE2D332),
            Text.literal("p").withColor(0xE1D534),
            Text.literal("e").withColor(0xE1D737),
            Text.literal("c").withColor(0xE0D939),
            Text.literal("t").withColor(0xDFDC3B),
            Text.literal("i").withColor(0xDFDE3E),
            Text.literal("n").withColor(0xDEE040),
            Text.literal("g ").withColor(0xDEE243),
            Text.literal("A").withColor(0xDCE747),
            Text.literal("m").withColor(0xDCE94A),
            Text.literal("u").withColor(0xDBEB4C),
            Text.literal("l").withColor(0xDAEE4E),
            Text.literal("e").withColor(0xDAF051),
            Text.literal("t").withColor(0xD9F253)),
            0xded436),
    BIGFOOT_FUR("bigfoot fur", Text.literal("Bigfoot Fur").formatted(Formatting.WHITE), Defaults.DEFAULT_COLOR),
    BIGFOOT_TOOTH("bigfoot tooth", Text.literal("Bigfoot Tooth").formatted(Formatting.WHITE), Defaults.DEFAULT_COLOR),
    UNKNOWN(ID_UNKNOWN, Text.empty(), DEFAULT_COLOR);

    public static final EnumLookup<RareCatch> LOOKUP = new EnumLookup<>(values());

    public final String ID;
    public final Text TAG;
    public final int COLOR;

    RareCatch(String id, Text tag, int color) {
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
