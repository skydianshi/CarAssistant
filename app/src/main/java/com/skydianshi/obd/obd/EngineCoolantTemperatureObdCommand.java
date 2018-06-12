package com.skydianshi.obd.obd;

/**
 * Created by 张海逢 on 2017/3/7.
 */

public class EngineCoolantTemperatureObdCommand extends TemperatureObdCommand {

    public EngineCoolantTemperatureObdCommand() {
        super("01 05");
    }

    /**
     * * @param other
     * */
    public EngineCoolantTemperatureObdCommand(TemperatureObdCommand other) {
        super(other);
    }

    @Override
    public int getIndex() {
        return 4;
    }

    @Override
    public String getName() {
        return AvailableCommandNames.ENGINE_COOLANT_TEMP.getValue();
    }
}
