package cjsy3c.wordsearch;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new WordEntryPanel().show());
	}

}
