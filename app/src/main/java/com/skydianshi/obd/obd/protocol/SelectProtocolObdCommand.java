package com.skydianshi.obd.obd.protocol;


import com.skydianshi.obd.obdreader.ObdCommand;

/** * Select the protocol to use. */
public class SelectProtocolObdCommand extends ObdCommand {
	private final ObdProtocols protocol;

	/**
	 * * @param command
	 * */
	public SelectProtocolObdCommand(ObdProtocols protocol) {
		super("AT SP " + protocol.getValue());
		this.protocol = protocol;
	}

	@Override
	public String getFormattedResult() {
		return getResult();
	}

	@Override
	public String getName() {
		return "Select Protocol " + protocol.name();
	}
}
