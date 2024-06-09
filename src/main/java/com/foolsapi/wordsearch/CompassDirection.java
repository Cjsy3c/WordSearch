package com.foolsapi.wordsearch;

import java.util.concurrent.ThreadLocalRandom;

public enum CompassDirection {
	NORTH 		((x,y) -> new int[]{x		, y + 1}),
	NORTHEAST	((x,y) -> new int[]{x + 1	, y + 1}),
	EAST		((x,y) -> new int[]{x + 1	, y}),
	SOUTHEAST	((x,y) -> new int[]{x + 1	, y - 1}),
	SOUTH		((x,y) -> new int[]{x		, y - 1}),
	SOUTHWEST	((x,y) -> new int[]{x - 1	, y - 1}),
	WEST		((x,y) -> new int[]{x - 1	, y}),
	NORTHWEST	((x,y) -> new int[]{x - 1	, y + 1});
	
	private final NextWordStep nextStep;

	private CompassDirection(NextWordStep nextStep) {
		this.nextStep = nextStep;
	}
	
	public int[] nextStep(int x, int y) {
		return nextStep.nextStep(x, y);
	}
	
	public static CompassDirection randomCompassDirection() {
		return CompassDirection.values()[ThreadLocalRandom.current().nextInt(CompassDirection.values().length)];
	}

}
