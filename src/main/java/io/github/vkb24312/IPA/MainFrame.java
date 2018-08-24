package io.github.vkb24312.IPA;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class MainFrame extends JFrame {
    //region The components
    private JPanel panel;
    private JPanel inputPanel;
        private JLabel inputFieldLabel;
        private JTextField inputField;
    private JPanel submitPanel;
        private JButton submit;
    private JPanel outputPanel;
        private JLabel outputFieldLabel;
        private JTextField outputField;
    //endregion

    MainFrame (String title, Dimension size){
        super(title);

        setSize(size);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //region Main panel setup
        panel = new JPanel(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);
        //endregion

        //region Input panel setup
        inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(inputPanel);
        submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(submitPanel);
        outputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(outputPanel);

        inputFieldLabel = new JLabel("Write your input in the code here (see README)");
        inputField = new JTextField(20);
        inputPanel.add(inputFieldLabel);
        inputPanel.add(inputField);
        //endregion

        //region Output panel setup
        outputFieldLabel = new JLabel("The output will appear here");
        outputField = new JTextField(20);
        outputField.setEditable(false);
        outputPanel.add(outputFieldLabel);
        outputPanel.add(outputField);
        //endregion

        //region Submit panel setup
        submit = new JButton("Button");
        submit.addActionListener(l -> {
            String[] inputs = inputField.getText().toLowerCase().split(" ");
            StringBuilder output = new StringBuilder("\u0000");

            for (String input : inputs) {
                output.append(IPAConverter.symbol(IPAConverter.toKey(input)));
            }

            outputField.setText(output.toString());
            outputFieldLabel.setText("Press the text to copy");
            outputField.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    StringSelection string = new StringSelection(outputField.getText());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(string, null); //FIXME: Doesn't copy to clipboard.
                    System.out.println("Copied " + outputField.getText());
                }
            });
        });
        submitPanel.add(submit);
        //endregion
    }
}
