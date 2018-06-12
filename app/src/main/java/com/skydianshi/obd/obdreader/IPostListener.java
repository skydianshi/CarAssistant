package com.skydianshi.obd.obdreader;

/**
 * Created by 张海逢 on 2017/3/3.
 */

public interface IPostListener {
    void stateUpdate(ObdCommand command, String message);
}
