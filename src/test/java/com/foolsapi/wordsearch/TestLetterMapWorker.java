package com.foolsapi.wordsearch;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import cjsy3c.wordsearch.LetterMap;
import cjsy3c.wordsearch.LetterMapWorker;

public class TestLetterMapWorker {
	
	@Test
	void testLetterMapWorker() throws Exception {
		LetterMapWorker worker = new LetterMapWorker(false, 5, Arrays.asList("TEST", "word"), null, false);
		worker.execute();
	
		LetterMap map = worker.get();
		assertNotNull(map);
		assertTrue(worker.isDone());
	}

}
