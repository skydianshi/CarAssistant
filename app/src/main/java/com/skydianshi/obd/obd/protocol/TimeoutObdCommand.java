package com.skydianshi.obd.obd.protocol;


import com.skydianshi.obd.obdreader.ObdCommand;

/**
 * * This will set the value of time in milliseconds (ms) that the OBD interface
 * * will wait for a response from the ECU. If exceeds, the response is
 * "NO DATA".
 * */
public class TimeoutObdCommand extends ObdCommand {
	/**
	 * * @param a * value between 0 and 255 that multiplied by 4 results in the
	 * * desired timeout in milliseconds (ms).
	 * */
	public TimeoutObdCommand(int timeout) {
		super("AT ST " + Integer.toHexString(0xFF & timeout));
	}

	/**
	 * * @param other
	 * */
	public TimeoutObdCommand(ObdCommand other) {
		super(other);
	}

	@Override
	public String getFormattedResult() {
		return getResult();
	}

	@Override
	public String getName() {
		return "Timeout";
	}
}
