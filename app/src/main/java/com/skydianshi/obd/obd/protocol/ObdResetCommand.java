package com.skydianshi.obd.obd.protocol;


import com.skydianshi.obd.obdreader.ObdCommand;

import java.io.IOException;
import java.io.InputStream;


/** * This method will reset the OBD connection. */
public class ObdResetCommand extends ObdCommand {
	/**
	 * * @param command
	 * */
	public ObdResetCommand() {
		super("AT Z");
	}

	/**
	 * * @param other
	 * */
	public ObdResetCommand(ObdResetCommand other) {
		super(other);
	}

	/**
	 * * Reset command returns an empty string, so we must override the
	 * following * two methods. * @throws IOException
	 * */
	@Override
	public void readResult(InputStream in) throws IOException {
		// do nothing
		return;
	}

	@Override
	public String getResult() {
		return "";
	}

	@Override
	public String getFormattedResult() {
		return getResult();
	}

	@Override
	public String getName() {
		return "Reset OBD";
	}
}
