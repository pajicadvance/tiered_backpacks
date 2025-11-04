package me.pajic.unsophisticated_backpacks;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.pajic.unsophisticated_backpacks.util.BackpackAccessorySlot;
import me.pajic.unsophisticated_backpacks.util.BackpackDimensions;

@Version(version = 1)
public class ModConfig extends Config {
    public ModConfig() {
        super(Main.CONFIG_RL);
    }

    public ValidatedAny<BackpackDimensions> leatherSize = new ValidatedAny<>(new BackpackDimensions(3, 9));
    public ValidatedAny<BackpackDimensions> copperSize = new ValidatedAny<>(new BackpackDimensions(4, 9));
    public ValidatedAny<BackpackDimensions> ironSize = new ValidatedAny<>(new BackpackDimensions(5, 9));
    public ValidatedAny<BackpackDimensions> goldenSize = new ValidatedAny<>(new BackpackDimensions(6, 9));
    public ValidatedAny<BackpackDimensions> diamondSize = new ValidatedAny<>(new BackpackDimensions(6, 11));
    public ValidatedAny<BackpackDimensions> netheriteSize = new ValidatedAny<>(new BackpackDimensions(6, 13));
    public ValidatedBoolean canOpenFromInventory = new ValidatedBoolean(true);
    public ValidatedBoolean canOpenWithRightClick = new ValidatedBoolean(true);
    public ValidatedBoolean canEquipInChestSlot = new ValidatedBoolean(false);
    public ValidatedBoolean canStoreShulkers = new ValidatedBoolean(false);
    public ValidatedBoolean preventUnequipWhenNotEmpty = new ValidatedBoolean(false);
    public ValidatedEnum<BackpackAccessorySlot> accessorySlot = new ValidatedEnum<>(BackpackAccessorySlot.BACK);
}
