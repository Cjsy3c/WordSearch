package cjsy3c.wordsearch;

import java.util.List;
import java.util.Optional;

/**
 * Used to store the word and indexes involved to prevent conflicts.
 */
public class WordIndex {

	private String word;
	private List<int[]> indexList;
	
	public WordIndex(String word, List<int[]> indexList) {
		this.word = word;
		this.indexList = indexList;
	}
	
	/**
	 * Check to see if this wordIndex is currently relying on the character in the given x,y coordinate.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean checkConflict(int x, int y) {
		return findChar(x, y) != ' '; 
	}
	
	/**
	 * 
	 * Given a list of word indexes, check if the x, y coordinates
	 *  are already in use and get the character at that position.
	 * 
	 * @param wordIndexList
	 * @param x
	 * @param y
	 * @return
	 */
	public static Optional<Character> checkConflict(List<WordIndex> wordIndexList, int x, int y) {
		
		for(WordIndex wordIndex : wordIndexList) {
			
			char c = wordIndex.findChar(x, y);
			if(c != ' ') {
				// something found
				return Optional.of(c);
			}
		}
		
		return Optional.empty(); 
	}
	
	/**
	 * If a conflict is returned, this method can be used to determine the required character.
	 * 
	 * if this is called on coordinates that aren't required, then it will return the space character ' '.
	 * 
	 * @return
	 */
	public char findChar(int x, int y) {
		for(int i = 0; i < word.length(); i++) {
			int[] val = indexList.get(i);
			if(x == val[0] && y == val[1]) {
				return word.charAt(i);
			}
		}
		return ' ';
	}
	
	public void setCharacters(LetterMap map) {
		
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			int[] val = indexList.get(i);
			int x = val[0];
			int y = val[1];
			
			map.setCharacter(x, y, c);
		}
		
	}
	
}
