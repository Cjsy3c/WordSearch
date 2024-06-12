package cjsy3c.wordsearch;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WordEntryPanel {
	
	private JFrame frame;
	private JTextArea wordListField;
	private JTextField sizeField;
	private JCheckBox signLanguageCheckbox;
	private JButton generateButton;

	public WordEntryPanel() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		frame.setLayout(bagLayout);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		addComponent(frame, buildInfoPanel(), bagLayout, constraints);
		
		constraints.gridy = 1;
		addComponent(frame, buildSelectionPanel(), bagLayout, constraints);
		
		constraints.gridy = 2;
		addComponent(frame, buildButtonPanel(), bagLayout, constraints);
	}
	
	public void show() {
		frame.pack();
		frame.setVisible(true);
	}
	
	private JPanel buildInfoPanel()  {
		JPanel infoPanel = new JPanel();
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		infoPanel.setLayout(bagLayout);
		
		JLabel title = new JLabel("Word Search Creator");
		addComponent(infoPanel, title, bagLayout, constraints);
		
		return infoPanel;
	}
	
	private JPanel buildSelectionPanel() {
		JPanel selectionPanel = new JPanel();
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		selectionPanel.setLayout(bagLayout);
		
		constraints.weightx = .1;
		constraints.weighty = .1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.WEST;
		JLabel sizeLabel = new JLabel("Size:");
		sizeLabel.setToolTipText("Number of characters to generate for width and height");
		addComponent(selectionPanel, sizeLabel, bagLayout, constraints);
		
		constraints.gridx = 1;
		sizeField = new JTextField("10", 3);
		sizeField.setEditable(true);
		addComponent(selectionPanel, sizeField, bagLayout, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		JLabel signLanguageLabel = new JLabel("ASL:");
		signLanguageLabel.setToolTipText("Choose to generate this with a font used for American Sign Language");
		addComponent(selectionPanel, signLanguageLabel, bagLayout, constraints);
		
		constraints.gridx = 1;
		signLanguageCheckbox = new JCheckBox();
		addComponent(selectionPanel, signLanguageCheckbox, bagLayout, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.ipadx = 20;
		JLabel wordLabel = new JLabel("Word List:");
		wordLabel.setToolTipText("Enter the words used with commas separating them");
		addComponent(selectionPanel, wordLabel, bagLayout, constraints);
		
		constraints.gridx = 1;
		constraints.weightx = .8;
		constraints.weighty = .8;
		constraints.ipadx = 20;
		wordListField = new JTextArea("Word, Test, Surprise", 4, 30);
		wordListField.setLineWrap(true);
		addComponent(selectionPanel, wordListField, bagLayout, constraints);
		
		selectionPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		
		return selectionPanel;
	}
	
	private JPanel buildButtonPanel()  {
		JPanel buttonPanel = new JPanel();
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		buttonPanel.setLayout(bagLayout);
		
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		generateButton = new JButton("Generate");
		addComponent(buttonPanel, generateButton, bagLayout, constraints);
		
		generateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				generateButton.setEnabled(false);
				int size = Integer.parseInt(sizeField.getText());
				boolean aslFlag = signLanguageCheckbox.isSelected();
				
				String wordCsv = wordListField.getText();
				wordCsv = wordCsv.replace('|', ',').replace('\t', ',').replace('\n', ',');
				wordCsv = wordCsv.replaceAll("[^A-Za-z,]", "");
				List<String> wordList = Arrays.asList(wordCsv.split(","));
				
				new LetterMapWorker(aslFlag, size, wordList, () -> generateButton.setEnabled(true)).execute();
				
			}
		});
		
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
		
		return buttonPanel;
	}
	
	
	private void addComponent(JPanel panel, JComponent comp, GridBagLayout layout, GridBagConstraints constraint) {
		layout.setConstraints(comp, constraint);
		panel.add(comp);
	}
	
	private void addComponent(JFrame frame, JComponent comp, GridBagLayout layout, GridBagConstraints constraint) {
		layout.setConstraints(comp, constraint);
		frame.add(comp);
	}

}
