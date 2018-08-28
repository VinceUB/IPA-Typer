package io.github.vkb24312.IPA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.io.InputStream;

class MainFrame extends JFrame {
    //region The components
    private JPanel panel;
    private JPanel inputPanel;
        private JLabel inputFieldLabel;
        private JTextField inputField;
    private JPanel submitPanel;
        private JButton submit;
        private JCheckBox jcb;
    private JPanel outputPanel;
        private JLabel outputFieldLabel;
        private JTextField outputField;
    //endregion

    private boolean auto;
    private Font font;

    MainFrame (String title, Dimension size){
        super(title);

        setSize(size);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //region Font setup
        InputStream fontInputStream = getClass().getClassLoader().getResourceAsStream("fonts/DejaVuSans.ttf");

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontInputStream).deriveFont(11.5f);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            font = null;
            System.exit(0);
        }

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                font = font.deriveFont((float) font.getSize()-e.getWheelRotation());
                setFontAll(font);
            }
        });
        //endregion

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
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                if(e.getKeyChar()==KeyEvent.VK_ENTER) submit.doClick();

                if (auto) MainFrame.this.updateOutput();
            }

        });
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
        submit = new JButton("Submit");
        submit.addActionListener(l -> updateOutput());

        jcb = new JCheckBox("Auto-submit on?");
        jcb.setSelected(false);
        auto = jcb.isSelected();
        jcb.addActionListener(e -> auto = jcb.isSelected());
        submitPanel.add(submit);
        submitPanel.add(jcb);
        //endregion

        setFontAll(font);
    }

    private void setFontAll(Font f){
        inputFieldLabel.setFont(f);
        inputField.setFont(f);
        submit.setFont(f);
        jcb.setFont(f);
        outputFieldLabel.setFont(f);
        outputField.setFont(f);
    }

    private void updateOutput(){
        String[] inputs = inputField.getText().toLowerCase().split(" ");
        StringBuilder output = new StringBuilder();

        for (String input : inputs) {
            try {
                char symbol = IPAConverter.symbol(IPAConverter.toKey(input));
                if(symbol!='\u0000'){
                    output.append(symbol);
                } else output.append(" ").append(input).append(" ");
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                output.append(" ").append(input).append(" ");
            }
        }

        outputField.setText(output.toString());
        outputFieldLabel.setText("Double click text to select");
    }
}