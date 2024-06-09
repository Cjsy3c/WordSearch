package com.foolsapi.wordsearch;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class WordSearchPanel {
	
	private final Font font;
	private final Color selected = Color.YELLOW;
	private final Color notSelected;
	
	public WordSearchPanel(LetterMap letterMap, List<String> wordList, boolean aslFontFlag) {
		font = loadFont(aslFontFlag);
		JFrame frame = new JFrame();
		notSelected = frame.getBackground();

		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		
		JPanel panel = new JPanel(bagLayout);
		
		// display word list
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		addComponent(panel, buildWordListPanel(wordList), bagLayout, constraints);

		// show characters
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.BOTH;
		addComponent(panel, buildGamePanel(letterMap), bagLayout, constraints);

		JScrollPane scrollPane = new JScrollPane(panel);
		frame.add(scrollPane);
		// kick off UI load
		SwingUtilities.invokeLater(() -> {
			frame.pack();
			frame.setVisible(true);
		});
	}
	
	private JPanel buildWordListPanel(List<String> wordList) {
		JPanel wordPanel = new JPanel();
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		wordPanel.setLayout(bagLayout);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		JLabel instructions = new JLabel("The words that you are looking for are: ");
		addComponent(wordPanel, instructions, bagLayout, constraints);
		
		constraints.gridy = 1;
		JLabel words = new JLabel(String.join(", ", wordList));
		addComponent(wordPanel, words, bagLayout, constraints);
		
		return wordPanel;
	}

	private JPanel buildGamePanel(LetterMap letterMap) {
		JPanel gamePanel = new JPanel();
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();

		gamePanel.setLayout(bagLayout);
		
		for(int i = 0; i < letterMap.getSize(); i++) {
			constraints.gridy = i;
			constraints.gridx = 0;
			
			for(char c : letterMap.getRow(i)) {
				JTextField text = new JTextField(String.valueOf(c), 2);
				text.setHorizontalAlignment(JTextField.CENTER);
				text.setFont(font);
				text.setEditable(false);
				text.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						Component c = e.getComponent();
						if(c.getBackground().equals(selected)) {
							c.setBackground(notSelected);
						} else {
							c.setBackground(selected);
						}
					}
				});

				constraints.gridx++;
				
				addComponent(gamePanel, text, bagLayout, constraints);
			}
		}
		
		gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		
		return gamePanel;
	}
	
	private void addComponent(JPanel panel, JComponent comp, GridBagLayout layout, GridBagConstraints constraint) {
		layout.setConstraints(comp, constraint);
		panel.add(comp);
	}
	
	private Font loadFont(boolean useAslFont) {
		if(useAslFont) {
			try {
				return Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("GALLAUDET.ttf"))
						.deriveFont(Font.PLAIN, 80);
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
		}
		return Font.getFont(Font.MONOSPACED);
	}

}
