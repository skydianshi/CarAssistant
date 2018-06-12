package com.skydianshi.obd.obd;


import com.skydianshi.obd.obdreader.ObdCommand;
import com.skydianshi.obd.obdreader.SystemOfUnits;

/**
 * Created by 张海逢 on 2017/3/7.
 */

public abstract class PressureObdCommand extends ObdCommand implements
        SystemOfUnits {
    protected int tempValue = 0;
    protected int pressure = 0;

    /**
     * * Default ctor * * @param cmd
     * */
    public PressureObdCommand(String cmd) {
        super(cmd);
    }

    /**
     * * Copy ctor. * * @param cmd
     * */
    public PressureObdCommand(PressureObdCommand other) {
        super(other);
    }

    /**
     * * Some PressureObdCommand subclasses will need to implement this method
     * in * order to determine the final kPa value. * * *NEED* to read tempValue
     * * * @return
     * */
    protected int preparePressureValue() {
        return tempValue;
    }

    /**
     * *
     * */
    @Override
    public String getFormattedResult() {
        String res = getResult();
        if (!"NODATA".equals(res)) {
            // ignore first two bytes [hh hh] of the response
            tempValue = buffer.get(2);
            pressure = preparePressureValue(); // this will need tempValue
            res = String.format("%d%s", pressure, "kPa");
            if (useImperialUnits) {
                res = String.format("%.1f%s", getImperialUnit(), "psi");
            }
        }
        return res;
    }

    /**
     * * @return the pressure in kPa
     * */
    public int getMetricUnit() {
        return pressure;
    }

    /**
     * * @return the pressure in psi
     * */
    public float getImperialUnit() {
        Double d = pressure * 0.145037738;
        return Float.valueOf(d.toString());
    }
}