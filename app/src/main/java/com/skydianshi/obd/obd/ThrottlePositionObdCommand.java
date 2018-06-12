package com.skydianshi.obd.obd;


/** * Read the throttle position in percentage. */
public class ThrottlePositionObdCommand extends PercentageObdCommand {
	/**
	 * * Default ctor.
	 * */
	public ThrottlePositionObdCommand() {
		super("01 11");
	}

	/**
	 * * Copy ctor. * * @param other
	 * */
	public ThrottlePositionObdCommand(ThrottlePositionObdCommand other) {
		super(other);
	}

	/**
	 * *
	 * */
	@Override
	public String getName() {
		return AvailableCommandNames.THROTTLE_POS.getValue();
	}

	@Override
	public int getIndex() {
		return 6;
	}
}
