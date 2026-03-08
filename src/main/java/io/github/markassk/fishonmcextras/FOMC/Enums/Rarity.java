package io.github.markassk.fishonmcextras.FOMC.Enums;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static io.github.markassk.fishonmcextras.FOMC.Types.Defaults.DEFAULT_COLOR;

public enum Rarity implements EnumConstant {
    COMMON("common", Text.literal("\uF033").formatted(Formatting.WHITE),
            Text.literal("\uEEE4\uEEE1 퀃 \uEEE8\uEEE7\uEEE5\uEEE2 "),
            0xFFFFFF),
    RARE("rare", Text.literal("\uF034").formatted(Formatting.WHITE),
            Text.literal("\uEEE4\uEEE1 퀇 \uEEE8\uEEE7\uEEE5\uEEE2 "),
            0x2B85C4),
    EPIC("epic", Text.literal("\uF035").formatted(Formatting.WHITE),
            Text.literal("\uEEE4\uEEE1 퀑 \uEEE8\uEEE7\uEEE5\uEEE2 "),
            0x1CD832),
    LEGENDARY("legendary", Text.literal("\uF036").formatted(Formatting.WHITE),
            Text.literal("\uEEE4\uEEE1 퀕 \uEEE8\uEEE7\uEEE5\uEEE2 "),
            0xD98103),
    MYTHICAL("mythical", Text.literal("\uF037").formatted(Formatting.WHITE),
            Text.literal("\uEEE4\uEEE1 퀙 \uEEE8\uEEE7\uEEE5\uEEE2 "),
            0xC93832),
    SPECIAL("special", Text.literal("\uF092").formatted(Formatting.WHITE),
            Text.literal("\uEEE4\uEEE1 퀃 \uEEE8\uEEE7\uEEE5\uEEE2 ").withColor(0xC746B4),
            0xDD7ACF),
    UNKNOWN(ID_UNKNOWN, Text.empty(), COMMON.LORE_TAG, DEFAULT_COLOR);

    public static final EnumLookup<Rarity> LOOKUP = new EnumLookup<>(values());

    public final String ID;
    public final Text TAG;
    public final Text LORE_TAG;
    public final int COLOR;

    Rarity(String id, Text tag, Text loreTag, int color) {
        this.ID = id;
        this.TAG = tag;
        this.LORE_TAG = loreTag;
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

    public Rarity next() {
        return switch (this) {
            case COMMON -> RARE;
            case RARE -> EPIC;
            case EPIC -> LEGENDARY;
            case LEGENDARY -> MYTHICAL;
            default -> UNKNOWN;
        };
    }

    @Override
    public String toString() {
        return this.ID;
    }
}
