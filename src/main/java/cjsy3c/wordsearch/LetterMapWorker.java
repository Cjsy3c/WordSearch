package cjsy3c.wordsearch;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class LetterMapWorker extends SwingWorker<LetterMap, Object> {

	private boolean aslFlag;
	private List<String> wordList;
	private int size;
	private Runnable doneMethod = null;
	private boolean displayResult = true;
	
	public LetterMapWorker(boolean aslFlag, int size, List<String> wordList, Runnable doneMethod, boolean displayResult) {
		super();
		this.aslFlag = aslFlag;
		this.wordList = wordList;
		this.size = size;
		this.doneMethod = doneMethod;
		this.displayResult = displayResult;
	}
	
	public LetterMapWorker(boolean aslFlag, int size, List<String> wordList, Runnable doneMethod) {
		super();
		this.aslFlag = aslFlag;
		this.wordList = wordList;
		this.size = size;
		this.doneMethod = doneMethod;
	}
	
	public LetterMapWorker(boolean aslFlag, int size, List<String> wordList) {
		super();
		this.aslFlag = aslFlag;
		this.wordList = wordList;
		this.size = size;
	}

	@Override
	protected LetterMap doInBackground() throws Exception {
		return LetterMap.generateRandom(size, wordList);
	}

	@Override
	protected void done() {
		super.done();
		try {
			if(displayResult) {
				kickOffSearchUI(get(), wordList, aslFlag);
			}
			if(doneMethod != null) {
				doneMethod.run();
			}
			
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private void kickOffSearchUI(LetterMap letterMap, List<String> wordList, boolean aslFontFlag) {
		new WordSearchPanel(letterMap, wordList, aslFontFlag);
	}

}
