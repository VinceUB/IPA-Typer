package io.github.vkb24312.IPA;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

class MainFrame extends JFrame {
    //region The components
    private JMenuBar menuBar;
        private JMenu reference;
            private JMenu IPAChart;
                private JMenuItem IPAChartFull;
                private JMenuItem IPAChartConsonants;
                private JMenuItem IPAChartVowels;
                private JMenuItem IPAChartSpecials;
            private JMenuItem wikipediaHelp;
            private JMenuItem correctInput;
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
    private Properties properties;

    MainFrame (String title, Dimension size){
        super(title);

        try {
            properties = new Properties();
            InputStream propertyStream = getClass().getClassLoader().getResourceAsStream("gui.properties");
            properties.load(propertyStream);
        } catch (IOException e){
            e.printStackTrace();
        }

        setSize(size);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initFont();
        initMenuBar();
        initPanel();
        initInput();
        initOutput();
        initSubmit();

        setFontAll(font);
    }

    private void initMenuBar(){
        //region Create everything
        menuBar = new JMenuBar();

        reference = new JMenu("Reference");

        IPAChart = new JMenu("IPA Chart");
        IPAChartFull = new JMenuItem("Full Chart");
        IPAChartConsonants = new JMenuItem("Consonants");
        IPAChartVowels = new JMenuItem("Vowels");
        IPAChartSpecials = new JMenuItem("Diacritics and Suprasegmentals");
        try {
            ImageIcon wikipediaIcon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/Wikipedia-logo-v2.png")));
            wikipediaHelp = new JMenuItem("Wikipedia Reference", wikipediaIcon);
        } catch (IOException e) {
            wikipediaHelp = new JMenuItem("Wikipedia Reference");
            e.printStackTrace();
        }
        correctInput = new JMenuItem("How to correctly input");

        IPAChart.add(IPAChartFull);
        IPAChart.add(IPAChartConsonants);
        IPAChart.add(IPAChartVowels);
        IPAChart.add(IPAChartSpecials);

        reference.add(wikipediaHelp);
        reference.add(correctInput);
        reference.add(IPAChart);

        menuBar.add(reference);
        //endregion

        setJMenuBar(menuBar);

        //region Set action listeners
        wikipediaHelp.addActionListener(l -> {
            try {
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/H:IPA"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });

        correctInput.addActionListener(l -> {
            if(!new File("temp.html").exists()){
                try {
                    InputStream inStream = getClass().getClassLoader().getResourceAsStream("READMEs/README.html");
                    FileOutputStream fos = new FileOutputStream("temp.html");

                    inStream.transferTo(fos);

                    new File("temp.html").deleteOnExit();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }


            try{
                if(InetAddress.getByName("github.com").isReachable(1000)) {
                    Desktop.getDesktop().browse(new URI("https://github.com/vkb24312/IPA-Converter/blob/master/README.md#how-to-correctly-input"));
                    return;
                } else outputFieldLabel.setText("No Internet Connection");
            } catch (IOException | URISyntaxException e) {
                outputFieldLabel.setText("Something went wrong");
                e.printStackTrace();
            }

            try {
                Desktop.getDesktop().browse(new File("temp.html").toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        IPAChartConsonants.addActionListener(l -> {
            try {
                new PictureFrame(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/IPA_Charts/CONSONANTS.png")));
            } catch (IOException e) {
                outputFieldLabel.setText("Something went wrong");
                e.printStackTrace();
            }
        });

        IPAChartVowels.addActionListener(l -> {
            try {
                new PictureFrame(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/IPA_Charts/VOWELS.png")));
            } catch (IOException e) {
                outputFieldLabel.setText("Something went wrong");
                e.printStackTrace();
            }
        });

        IPAChartSpecials.addActionListener(l -> {
            try {
                new PictureFrame(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/IPA_Charts/SPECIALS.png")));
            } catch (IOException e) {
                outputFieldLabel.setText("Something went wrong");
                e.printStackTrace();
            }
        });

        IPAChartFull.addActionListener(l -> {
            try {
                new PictureFrame(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/IPA_Charts/FULL.png")));
            } catch (IOException e) {
                outputFieldLabel.setText("Something went wrong");
                e.printStackTrace();
            }
        });
        //endregion
    }

    private void initPanel(){
        panel = new JPanel(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);
    }

    private void initInput(){
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
    }

    private void initOutput(){
        outputFieldLabel = new JLabel("The output will appear here");
        outputField = new JTextField(20);
        outputField.setEditable(false);

        outputPanel.add(outputFieldLabel);
        outputPanel.add(outputField);
    }

    private void initFont(){
        try {
            InputStream fontInputStream = getClass().getClassLoader().getResourceAsStream("fonts/" + properties.getProperty("font"));

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
    }

    private void initSubmit(){
        submit = new JButton("Submit");
        submit.addActionListener(l -> updateOutput());

        jcb = new JCheckBox("Auto-submit on?");
        jcb.setSelected(false);
        auto = jcb.isSelected();
        jcb.addActionListener(e -> auto = jcb.isSelected());
        submitPanel.add(submit);
        submitPanel.add(jcb);
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

class PictureFrame extends JFrame{
    private Image _image;

    PictureFrame(Image image){
        super("Picture");
        if(image.getHeight(null)>Toolkit.getDefaultToolkit().getScreenSize().height || image.getWidth(null)>Toolkit.getDefaultToolkit().getScreenSize().width) {
            int originalHeight = image.getHeight(null);
            int originalWidth = image.getWidth(null);
            int i = 1;
            while(originalHeight/i>Toolkit.getDefaultToolkit().getScreenSize().height || originalWidth/i>Toolkit.getDefaultToolkit().getScreenSize().width) {
                i++;
            }
            setSize(image.getWidth(null) / i, image.getHeight(null) / i);
        } else setSize(image.getWidth(null), image.getHeight(null));
        _image = image;

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(_image, 0, 0, getWidth(), getHeight(), null);
    }
}