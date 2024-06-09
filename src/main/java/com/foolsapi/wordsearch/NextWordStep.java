package com.foolsapi.wordsearch;

@FunctionalInterface
public interface NextWordStep {

	/**
	 * Perform the operation necessary to determine the next coordinate position
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int[] nextStep(int x, int y);
}
