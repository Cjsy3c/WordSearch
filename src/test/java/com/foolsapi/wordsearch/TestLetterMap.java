package com.foolsapi.wordsearch;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestLetterMap {
	
	@Test
	void testAsciiConversion() throws Exception {
		for(int i = 0; i < 26; i++) {
			System.out.println((char) (i + 65));
		}
	}

}
