package com.skydianshi.obd.obd;

/**
 * Created by 张海逢 on 2017/3/7.
 */

public class IntakeManifoldPressureObdCommand extends PressureObdCommand {

    public IntakeManifoldPressureObdCommand() {
        super("01 0B");
    }

    public IntakeManifoldPressureObdCommand(
            IntakeManifoldPressureObdCommand other) {
        super(other);
    }

    @Override
    public int getIndex() {
        return 3;
    }

    @Override
    public String getName() {
        return AvailableCommandNames.INTAKE_MANIFOLD_PRESSURE.getValue();
    }
}
