package com.skydianshi.obd.obd;


import com.skydianshi.obd.obdreader.ObdCommand;

/**
 * Created by 张海逢 on 2017/3/6.
 */

public class EngineRPMObdCommand extends ObdCommand {
    private int rpm = -1;

    /**
     * * Default ctor.
     * */
    public EngineRPMObdCommand() {
        super("01 0C");
    }

    /**
     * * Copy ctor. * * @param other
     * */
    public EngineRPMObdCommand(EngineRPMObdCommand other) {
        super(other);
    }

    /**
     * * @return the engine rpmbackground per minute
     * */
    @Override
    public String getFormattedResult() {
        if (!"NODATA".equals(getResult())) {
            // ignore first two bytes [41 0C] of the response
            int a = buffer.get(2);
            int b = buffer.get(3);
            rpm = (a * 256 + b) / 4;
        }
        return String.format("%d%s", rpm, "");
    }


    @Override
    public String getName() {
        return AvailableCommandNames.ENGINE_RPM.getValue();
    }

    public int getRPM() {
        return rpm;
    }


    @Override
    public int getIndex() {
        return 1;
    }
}
