package cjsy3c.wordsearch;

import java.awt.Dimension;
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

		GridBagConstraints constraints = new GridBagConstraints();
		frame.setLayout(new GridBagLayout());

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		addComponent(frame, buildInfoPanel(), constraints);
		
		constraints.gridy = 1;
		addComponent(frame, buildSelectionPanel(), constraints);
		
		constraints.gridy = 2;
		addComponent(frame, buildButtonPanel(), constraints);
	}
	
	public void show() {
		frame.pack();
		frame.setVisible(true);
	}
	
	private JPanel buildInfoPanel()  {
		JPanel infoPanel = new JPanel();
		GridBagConstraints constraints = new GridBagConstraints();
		infoPanel.setLayout(new GridBagLayout());
		
		JLabel title = new JLabel("Word Search Creator");
		addComponent(infoPanel, title, constraints);
		
		return infoPanel;
	}
	
	private JPanel buildSelectionPanel() {
		JPanel selectionPanel = new JPanel();
		GridBagConstraints constraints = new GridBagConstraints();
		selectionPanel.setLayout(new GridBagLayout());
		
		constraints.weightx = .1;
		constraints.weighty = .1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.anchor = GridBagConstraints.WEST;
		JLabel sizeLabel = new JLabel("Size:");
		sizeLabel.setToolTipText("Number of characters to generate for width and height");
		addComponent(selectionPanel, sizeLabel, constraints);
		
		constraints.gridx = 1;
		sizeField = new JTextField("14");
		sizeField.setEditable(true);
		sizeField.setMinimumSize(new Dimension(22, 20));
		addComponent(selectionPanel, sizeField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		JLabel signLanguageLabel = new JLabel("ASL:");
		signLanguageLabel.setToolTipText("Choose to generate this with a font used for American Sign Language");
		addComponent(selectionPanel, signLanguageLabel, constraints);
		
		constraints.gridx = 1;
		signLanguageCheckbox = new JCheckBox();
		addComponent(selectionPanel, signLanguageCheckbox, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.ipadx = 20;
		JLabel wordLabel = new JLabel("Word List:");
		wordLabel.setToolTipText("Enter the words used with commas separating them");
		addComponent(selectionPanel, wordLabel, constraints);
		
		constraints.gridx = 1;
		constraints.weightx = .8;
		constraints.weighty = .8;
		constraints.ipadx = 20;
		wordListField = new JTextArea("Word, Test, Surprise", 4, 30);
		wordListField.setLineWrap(true);
		addComponent(selectionPanel, wordListField, constraints);
		
		selectionPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		
		return selectionPanel;
	}
	
	private JPanel buildButtonPanel()  {
		JPanel buttonPanel = new JPanel();
		GridBagConstraints constraints = new GridBagConstraints();
		buttonPanel.setLayout(new GridBagLayout());
		
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		generateButton = new JButton("Generate");
		addComponent(buttonPanel, generateButton, constraints);
		
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
	
	
	private void addComponent(JPanel panel, JComponent comp, GridBagConstraints constraint) {
		panel.add(comp, constraint);
	}
	
	private void addComponent(JFrame frame, JComponent comp, GridBagConstraints constraint) {
		frame.add(comp, constraint);
	}
	
//	private void addComponent(Container container, Component comp, )

}
