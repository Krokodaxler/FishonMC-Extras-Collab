package io.github.markassk.fishonmcextras.FOMC.Enums;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static io.github.markassk.fishonmcextras.FOMC.Types.Defaults.DEFAULT_COLOR;

public enum PlayerRank implements EnumConstant {
    ANGLER("angler", Text.literal("\uF032").formatted(Formatting.WHITE), 0x20bbd7),
    SAILOR("sailor", Text.literal("\uF031").formatted(Formatting.WHITE), 0x96f564),
    MARINER("mariner", Text.literal("\uF030").formatted(Formatting.WHITE), 0x66f8ae),
    CAPTAIN("captain", Text.literal("\uF029").formatted(Formatting.WHITE), 0xfca307),
    ADMIRAL("admiral", Text.literal("\uF028").formatted(Formatting.WHITE), 0xae5af6),
    STAFF("staff", Text.literal("\uF024").formatted(Formatting.WHITE), 0x000000),
    DESIGNER("designer", Text.literal("\uF026").formatted(Formatting.WHITE), DEFAULT_COLOR),
    BUILDER("builder", Text.literal("\uF027").formatted(Formatting.WHITE), DEFAULT_COLOR),
    MANAGER("manager", Text.literal("\uF023").formatted(Formatting.WHITE), DEFAULT_COLOR),
    ADMIN("admin", Text.literal("\uF022").formatted(Formatting.WHITE), DEFAULT_COLOR),
    OWNER("owner", Text.literal("\uF021").formatted(Formatting.WHITE), DEFAULT_COLOR),
    COMMUNITYMANAGER("communitymanager", Text.literal("\uF088").formatted(Formatting.WHITE), DEFAULT_COLOR),
    FOE("foe", Text.literal("\uE00B").formatted(Formatting.WHITE), 0x325330),
    UNKNOWN(ID_UNKNOWN, Text.empty(), DEFAULT_COLOR);

    public static final EnumLookup<PlayerRank> LOOKUP = new EnumLookup<>(values());

    public final String ID;
    public final Text TAG;
    public final int COLOR;

    PlayerRank(String id, Text tag, int color) {
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
