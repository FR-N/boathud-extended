package frn.boathud_extended;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.network.chat.Component;

public class MenuInteg implements ModMenuApi {
    private static final Component TITLE = Component.translatable("boathud.config.title");
    private static final Component CAT = Component.translatable("boathud.config.cat");
    private static final Component ENABLED = Component.translatable("boathud.option.enabled");
    private static final Component EXTENDED = Component.translatable("boathud.option.extended");
    private static final Component TELEMETRY = Component.translatable("boathud.option.telemetry");
    private static final Component TELEMETRY_ENABLED = Component.translatable("boathud.option.telemetry_enabled");
    private static final Component TELEMETRY_DIRECTORY = Component.translatable("boathud.option.telemetry_directory");
    private static final Component TELEMETRY_DIRECTORY_TOOLTIP = Component.translatable("boathud.tooltip.telemetry_directory");
    private static final Component CHECKPOINT = Component.translatable("boathud.option.checkpoint");
    private static final Component CHECKPOINT_ENABLED = Component.translatable("boathud.option.checkpoint_enabled");
    private static final Component CHECKPOINT_FILE = Component.translatable("boathud.option.checkpoint_file");
    private static final Component CHECKPOINT_FILE_TOOLTIP = Component.translatable("boathud.tooltip.checkpoint_file");
    private static final Component CIRCULAR_TRACK = Component.translatable("boathud.option.circular_track");
    private static final Component CIRCULAR_TRACK_TOOLTIP = Component.translatable("boathud.tooltip.circular_track");
    private static final Component BAR_TYPE = Component.translatable("boathud.option.bar_type");
    private static final Component SPEED_FORMAT = Component.translatable("boathud.option.speed_format");
    private static final Component ACCELERATION_FORMAT = Component.translatable("boathud.option.acceleration_format");
    private static final Component TIP_EXTENDED = Component.translatable("boathud.tooltip.extended");
    private static final Component TIP_BAR = Component.translatable("boathud.tooltip.bar_type");
    private static final Component TIP_BAR_PACKED = Component.translatable("boathud.tooltip.bar_type.packed");
    private static final Component TIP_BAR_MIXED = Component.translatable("boathud.tooltip.bar_type.mixed");
    private static final Component TIP_BAR_BLUE = Component.translatable("boathud.tooltip.bar_type.blue");
    private static final Component Y_OFFSET = Component.translatable("boathud.option.y_offset");
    private static final Component Y_OFFSET_TOOLTIP = Component.translatable("boathud.tooltip.y_offset");

    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> {
            ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(TITLE);
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            ConfigCategory cat = builder.getOrCreateCategory(CAT);
            cat.addEntry(entryBuilder.startBooleanToggle(ENABLED, Config.enabled).setDefaultValue(true).setSaveConsumer((newVal) -> {
                Config.enabled = newVal;
            }).build()).addEntry(entryBuilder.startBooleanToggle(EXTENDED, Config.extended).setDefaultValue(true).setSaveConsumer((newVal) -> {
                Config.extended = newVal;
            }).setTooltip(new Component[]{TIP_EXTENDED}).build()).addEntry(entryBuilder.startIntSlider(Y_OFFSET, Config.yOffset, 0, 300).setDefaultValue(36).setSaveConsumer((newVal) -> {
                Config.yOffset = newVal;
            }).setTooltip(new Component[]{Y_OFFSET_TOOLTIP}).build()).addEntry(entryBuilder.startEnumSelector(BAR_TYPE, MenuInteg.BarType.class, MenuInteg.BarType.values()[Config.barType]).setDefaultValue(MenuInteg.BarType.PACKED).setTooltip(new Component[]{TIP_BAR, TIP_BAR_PACKED, TIP_BAR_MIXED, TIP_BAR_BLUE}).setSaveConsumer((newVal) -> {
                Config.barType = newVal.ordinal();
            }).setEnumNameProvider((value) -> {
                return Component.translatable("boathud.option.bar_type." + value.toString());
            }).build()).addEntry(entryBuilder.startEnumSelector(SPEED_FORMAT, MenuInteg.SpeedFormat.class, MenuInteg.SpeedFormat.values()[Config.speedType]).setDefaultValue(MenuInteg.SpeedFormat.MS).setSaveConsumer((newVal) -> {
                Config.setSpeedUnit(newVal.ordinal());
            }).setEnumNameProvider((value) -> {
                return Component.translatable("boathud.option.speed_format." + value.toString());
            }).build()).addEntry(entryBuilder.startEnumSelector(ACCELERATION_FORMAT, MenuInteg.AccelerationFormat.class, MenuInteg.AccelerationFormat.values()[Config.accelerationType]).setDefaultValue(MenuInteg.AccelerationFormat.MSS).setSaveConsumer((newVal) -> {
                Config.setAccelerationUnit(newVal.ordinal());
            }).setEnumNameProvider((value) -> {
                return Component.translatable("boathud.option.acceleration_format." + value.toString());
            }).build());
            SubCategoryBuilder telemetry = entryBuilder.startSubCategory(TELEMETRY).setExpanded(true);
            telemetry.add(entryBuilder.startBooleanToggle(TELEMETRY_ENABLED, Config.telemetryEnabled).setDefaultValue(false).setSaveConsumer((newVal) -> {
                Config.telemetryEnabled = newVal;
            }).build());
            telemetry.add(entryBuilder.startStrField(TELEMETRY_DIRECTORY, Config.telemetryDirectory).setDefaultValue("C:/boat_telemetry/").setSaveConsumer((newVal) -> {
                Config.telemetryDirectory = newVal;
            }).setTooltip(new Component[]{TELEMETRY_DIRECTORY_TOOLTIP}).build());
            cat.addEntry(telemetry.build());
            SubCategoryBuilder checkpoints = entryBuilder.startSubCategory(CHECKPOINT).setExpanded(true);
            checkpoints.add(entryBuilder.startBooleanToggle(CHECKPOINT_ENABLED, Config.checkpointEnabled).setDefaultValue(false).setSaveConsumer((newVal) -> {
                Config.checkpointEnabled = newVal;
            }).build());
            checkpoints.add(entryBuilder.startStrField(CHECKPOINT_FILE, Config.checkpointFile).setDefaultValue("C:/checkpoints.cf").setSaveConsumer((newVal) -> {
                Config.checkpointFile = newVal;
            }).setTooltip(new Component[]{CHECKPOINT_FILE_TOOLTIP}).build());
            checkpoints.add(entryBuilder.startBooleanToggle(CIRCULAR_TRACK, Config.circularTrack).setDefaultValue(false).setSaveConsumer((newVal) -> {
                Config.circularTrack = newVal;
            }).setTooltip(new Component[]{CIRCULAR_TRACK_TOOLTIP}).build());
            cat.addEntry(checkpoints.build());
            builder.setSavingRunnable(() -> {
                Config.save();
            });
            return builder.build();
        };
    }

    public static enum BarType {
        PACKED,
        MIXED,
        BLUE;

        private static MenuInteg.BarType[] $values() {
            return new MenuInteg.BarType[]{PACKED, MIXED, BLUE};
        }
    }

    public static enum SpeedFormat {
        MS,
        KMPH,
        MPH,
        KT;

        private static MenuInteg.SpeedFormat[] $values() {
            return new MenuInteg.SpeedFormat[]{MS, KMPH, MPH, KT};
        }
    }

    public static enum AccelerationFormat {
        MSS,
        G;

        private static MenuInteg.AccelerationFormat[] $values() {
            return new MenuInteg.AccelerationFormat[]{MSS, G};
        }
    }
}
