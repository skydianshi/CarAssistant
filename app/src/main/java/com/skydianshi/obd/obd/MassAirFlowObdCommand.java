package com.skydianshi.obd.obd;


import com.skydianshi.obd.obdreader.ObdCommand;

/**
 * Created by 张海逢 on 2017/3/7.
 */

public class MassAirFlowObdCommand extends ObdCommand {
    private float maf = -1.0f;

    /**
     * * Default ctor.
     * */
    public MassAirFlowObdCommand() {
        super("01 10");
    }

    /**
     * * Copy ctor. * * @param other
     * */
    public MassAirFlowObdCommand(MassAirFlowObdCommand other) {
        super(other);
    }

    /**
     * *
     * */
    @Override
    public String getFormattedResult() {
        if (!"NODATA".equals(getResult())) {
            // ignore first two bytes [hh hh] of the response
            int a = buffer.get(2);
            int b = buffer.get(3);
            maf = (a * 256 + b) / 100.0f;
        }
        return String.format("%.2f%s", maf, "g/s");
    }

    /**
     * * @return mafbackground value for further calculus.
     * */
    public double getMAF() {
        return maf;
    }

    @Override
    public String getName() {
        return AvailableCommandNames.MAF.getValue();
    }

    @Override
    public int getIndex() {
        return 2;
    }
}
