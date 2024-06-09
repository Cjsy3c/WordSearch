package com.foolsapi.wordsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class LetterMap {

	private final int size;
	private final char[][] map;
	private final List<WordIndex> wordListLocations = new ArrayList<>();

	public static LetterMap generateRandom(int size, List<String> wordList) {
		return new LetterMap(size, wordList);
	}

	public static LetterMap load() {
		throw new RuntimeException("load method not implemented yet");
	}

	/**
	 * Throw validation exception if the valitionFunction returns true;
	 * 
	 * @param failMessage
	 * @param validationFuntion  
	 */
	private void validate(String failMessage, Supplier<Boolean> validationFuntion) {
		if(validationFuntion.get()) {
			throw new IllegalArgumentException(failMessage);
		}
	}
	
	private LetterMap(int size, List<String> wordList) {
		validate("Size must be positive", () -> size < 1);
		validate("Size must be reasonable", () -> size > 1000);
		validate("wordList must have entries", () -> wordList.size() < 1);
		validate("word length can't exceed size", () -> wordList.stream()
				.filter(w -> w.length() > size)
				.findAny()
				.isPresent());
		
		this.size = size;
		this.map = new char[size][size];

		// select random position and direction
		for(String word : wordList) {
			word = word.toUpperCase();
			boolean found = false;
			int counter = 0;
			do {
				
				// make sure we aren't spinning too many cycles on this:
				if(counter > 1000) {
					throw new RuntimeException("Combination where all words are avialble cannot be found. Please try again.");
				}
				
				int xStart = randomInt(size);
				int yStart = randomInt(size);
				CompassDirection dir = CompassDirection.randomCompassDirection();
				
				int x = xStart;
				int y = yStart;
				boolean isValid = true;
				List<int[]> positionList = new ArrayList<>();
				for(char c : word.toCharArray()) {
					positionList.add(new int[] {x, y});
					int[] pos = dir.nextStep(x, y);
					x = pos[0];
					y = pos[1];
					
					Optional<Character> optChar = WordIndex.checkConflict(wordListLocations, x, y);
					if(optChar.isPresent() && !optChar.get().equals(c)) {
						isValid = false;
						break;
					} else if (!isValidIndex(x) || !isValidIndex(y)) {
						isValid = false;
						break;
					}
				}
				
				if(isValid) {
					wordListLocations.add(new WordIndex(word, positionList));
					found = true;
				}
				counter++;
			} while(!found);
		}
		
		// all positions are set, so I should assign those as characters:
		
		// generate random letters, unless its set
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map[i][j] = randomCharacter();
			}
		}
		
		// set required characters
		for(WordIndex wordIndex : wordListLocations) {
			wordIndex.setCharacters(this);
		}
		
	}
	
	private boolean isValidIndex(int i) {
		return i >= 0 && i < size;
	}

	private char randomCharacter() {
		int v = randomInt(26);
		// 65 is the first capital letter in ASCII/UTF-8
		return (char) (v + 65);
	}
	
	private int randomInt(int bound) {
		return ThreadLocalRandom.current().nextInt(bound);
	}

	private void validateIndex(int index) {
		if (index >= size) {
			throw new IllegalArgumentException("Index is out of range for this LetterMap");
		}
	}

	public char[] getRow(int index) {
		validateIndex(index);
		return map[index];
	}

	public char[] getColumn(int index) {
		validateIndex(index);

		char[] temp = new char[size];

		for (int i = 0; i < size; i++) {
			temp[i] = map[i][index];
		}

		return temp;
	}

	public void setCharacter(int x, int y, char c) {
		map[x][y] = c;
	}
	
	public int getSize() {
		return size;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < size; i++) {
			sb.append(joinChars(' ', getRow(i)));
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	private String joinChars(char joinChar, char... chars) {
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		for(char c : chars) {
			if(!first)
				sb.append(joinChar);
			sb.append(c);
			first = false;
		}
		return sb.toString();
	}
}
