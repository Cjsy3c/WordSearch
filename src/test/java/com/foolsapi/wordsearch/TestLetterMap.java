package com.foolsapi.wordsearch;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestLetterMap {
	
	@Test
	void testAsciiConversion() throws Exception {
		for(int i = 0; i < 26; i++) {
			System.out.println((char) (i + 65));
		}
	}
	
	@Test
	void testWordListOrder() {
		List<String> wordList = Arrays.asList("test", "LONGWORD", "short");
		wordList.sort(Comparator.comparing(String::length).reversed());
		System.out.println(wordList);
	}
	
}
