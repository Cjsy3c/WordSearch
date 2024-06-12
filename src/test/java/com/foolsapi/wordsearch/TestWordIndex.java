package com.foolsapi.wordsearch;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import cjsy3c.wordsearch.LetterMap;
import cjsy3c.wordsearch.WordIndex;

public class TestWordIndex {
	
	@Test
	void testCheckConflict() throws Exception {
		
		WordIndex word = new WordIndex("TEST", Arrays.asList(new int[] {2, 3}, new int[] {3, 3}, new int[] {4, 3}, new int[] {5, 3}));
		
		assertTrue(word.checkConflict(2, 3));
		assertTrue(word.checkConflict(3, 3));
		assertTrue(word.checkConflict(4, 3));
		assertTrue(word.checkConflict(5, 3));
		assertFalse(word.checkConflict(1, 3));
		assertFalse(word.checkConflict(6, 3));
		assertFalse(word.checkConflict(3, 4));
		assertFalse(word.checkConflict(3, 2));
	}
	
	@Test
	void testFindChar() throws Exception {
		
		WordIndex word = new WordIndex("TEST", Arrays.asList(new int[] {2, 3}, new int[] {3, 3}, new int[] {4, 3}, new int[] {5, 3}));
		
		assertEquals(' ', word.findChar(0, 0));
		assertEquals('T', word.findChar(2, 3));
		assertEquals('E', word.findChar(3, 3));
		assertEquals('S', word.findChar(4, 3));
		assertEquals('T', word.findChar(5, 3));
		assertEquals(' ', word.findChar(6, 3));
	}
	
	@Test
	void testStaticCheckConflict() throws Exception {
		
		List<WordIndex> wordIndexList = new ArrayList<>();
		wordIndexList.add(new WordIndex("TEST", Arrays.asList(new int[] {2, 3}, new int[] {3, 3}, new int[] {4, 3}, new int[] {5, 3})));
		wordIndexList.add(new WordIndex("WORD", Arrays.asList(new int[] {1, 1}, new int[] {1, 2}, new int[] {1, 3}, new int[] {1, 4})));
		
		assertFalse(WordIndex.checkConflict(wordIndexList, 0, 0).isPresent());
		assertFalse(WordIndex.checkConflict(wordIndexList, 5, 5).isPresent());
		assertTrue(WordIndex.checkConflict(wordIndexList, 2, 3).isPresent());
		assertTrue(WordIndex.checkConflict(wordIndexList, 3, 3).isPresent());
		assertTrue(WordIndex.checkConflict(wordIndexList, 1, 2).isPresent());
		assertTrue(WordIndex.checkConflict(wordIndexList, 1, 4).isPresent());
		
		assertEquals(Character.valueOf('T'), WordIndex.checkConflict(wordIndexList, 2, 3).get());
		assertEquals(Character.valueOf('E'), WordIndex.checkConflict(wordIndexList, 3, 3).get());
		assertEquals(Character.valueOf('O'), WordIndex.checkConflict(wordIndexList, 1, 2).get());
		assertEquals(Character.valueOf('D'), WordIndex.checkConflict(wordIndexList, 1, 4).get());
	}

	@Test
	void testSetLetterMap() {
		LetterMap letterMap = LetterMap.generateRandom(10, Arrays.asList("TEST"));
	
		char[] row1 = Arrays.copyOf(letterMap.getRow(1), 10);
		WordIndex newWord = new WordIndex("WORD", Arrays.asList(new int[] {1, 1}, new int[] {1, 2}, new int[] {1, 3}, new int[] {1, 4}));
		newWord.setCharacters(letterMap);
		
		assertNotEquals(row1, letterMap.getRow(1));
		assertEquals('W', letterMap.getColumn(1)[1]);
		assertEquals('O', letterMap.getColumn(1)[2]);
		assertEquals('R', letterMap.getColumn(1)[3]);
		assertEquals('D', letterMap.getColumn(1)[4]);
	}
	
	@Test
	void testSetLetterMap2() {
		LetterMap letterMap = LetterMap.generateRandom(10, Arrays.asList("TEST"));
	
		char[] row1 = Arrays.copyOf(letterMap.getRow(1), 10);
		WordIndex newWord = new WordIndex("WORD", Arrays.asList(new int[] {1, 1}, new int[] {2, 1}, new int[] {3, 1}, new int[] {4, 1}));
		newWord.setCharacters(letterMap);
		
		assertNotEquals(row1, letterMap.getRow(1));
		assertEquals('W', letterMap.getRow(1)[1]);
		assertEquals('O', letterMap.getRow(1)[2]);
		assertEquals('R', letterMap.getRow(1)[3]);
		assertEquals('D', letterMap.getRow(1)[4]);
	}
}
