package io.github.markassk.fishonmcextras.FOMC.Enums;

import io.github.markassk.fishonmcextras.FOMC.Types.Defaults;
import net.minecraft.text.Text;

public interface EnumConstant {
    String INTERNAL_PREFIX = "__internal_";
    String ID_UNKNOWN = INTERNAL_PREFIX + "unknown";

    String name();
    
    String id();
    
    default Text tag() {
        return Text.empty();
    }
    
    default int color() {
        return Defaults.DEFAULT_COLOR;
    }

    default boolean isInternal() {
        return this.id().startsWith(INTERNAL_PREFIX);
    }

    default boolean isUnknown() {
        return this.id().equals(ID_UNKNOWN);
    }
}
