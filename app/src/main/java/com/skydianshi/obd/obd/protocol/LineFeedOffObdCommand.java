package com.skydianshi.obd.obd.protocol;


import com.skydianshi.obd.obdreader.ObdCommand;

/** * Turns off line-feed. */
public class LineFeedOffObdCommand extends ObdCommand {
	/**
	 * * @param command
	 * */
	public LineFeedOffObdCommand() {
		super("AT L0");
	}

	/**
	 * * @param other
	 * */
	public LineFeedOffObdCommand(ObdCommand other) {
		super(other);
	}

	@Override
	public String getFormattedResult() {
		return getResult();
	}

	@Override
	public String getName() {
		return "Line Feed Off";
	}
}
