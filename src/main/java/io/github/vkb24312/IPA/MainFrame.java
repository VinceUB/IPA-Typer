package io.github.vkb24312.IPA;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
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
        private JMenu input;
            private JRadioButtonMenuItem logical;
            private JRadioButtonMenuItem xSampa;
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
    private File AppData;
    private int inputType;
    
    public final static int INPUT_TYPE_VINCENTCODE = 0;
    public final static int INPUT_TYPE_XSAMPA = 1;

    MainFrame (String title, Dimension size){
        super(title);

        try {
            if(System.getProperty("os.name").toLowerCase().startsWith("win")) AppData = new File(System.getenv("appdata") + "/IPATyper");
            else AppData = new File(System.getProperty("user.home") + "/IPATyper");
            
            properties = new Properties();
            if(!new File(AppData, "gui.properties").exists()) new File(AppData, "gui.properties").createNewFile();
            FileInputStream propertyStream = new FileInputStream(new File(AppData, "gui.properties"));
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
        menuBar = new JMenuBar();
        
        //region Reference menu bar
        //region Create everything
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

        //region Set action listeners
        wikipediaHelp.addActionListener(l -> {
            try {
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/H:IPA"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });

        correctInput.addActionListener(l -> {
            try{
                if(InetAddress.getByName("github.com").isReachable(1000)) {
                    Desktop.getDesktop().browse(new URI("https://github.com/vkb24312/IPA-Converter/blob/master/README.md#how-to-correctly-input"));
                    return;
                } else outputFieldLabel.setText("Note: No Internet Connection");
            } catch (IOException | URISyntaxException e) {
                outputFieldLabel.setText("Note: No internet connection");
                e.printStackTrace();
            }

            try {
                Desktop.getDesktop().browse(new File(AppData, "READMEs/README.html").toURI());
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
        //endregion
        
        //region Input menu bar
        //region Create everything
        input = new JMenu("Input type");
        logical = new JRadioButtonMenuItem("Vincent's input (Suggested)");
        xSampa = new JRadioButtonMenuItem("X-SAMPA input (Use if experienced in X-SAMPA)");
        ButtonGroup inputGroup = new ButtonGroup();
        inputGroup.add(logical);
        inputGroup.add(xSampa);
        input.add(logical);
        input.add(xSampa);
        menuBar.add(input);
        //endregion
        
        //region ActionListeners
        logical.addActionListener(l -> {
            inputType = INPUT_TYPE_VINCENTCODE;
            properties.setProperty("input", Integer.toString(inputType));
            try {
                properties.store(new FileWriter(new File(AppData, "gui.properties")), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    
        xSampa.addActionListener(l -> {
            inputType = INPUT_TYPE_XSAMPA;
            properties.setProperty("input", Integer.toString(inputType));
            try {
                properties.store(new FileWriter(new File(AppData, "gui.properties")), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //endregion
        //endregion
        
        setJMenuBar(menuBar);
    }

    private void initPanel(){
        panel = new JPanel(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);
    }

    private void initInput(){
        inputType = Integer.parseInt((String) properties.get("input"));
        if(inputType==0){
            logical.setSelected(true);
        } else if(inputType==1){
            xSampa.setSelected(true);
        } else {
            logical.doClick();
        }
        
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
            font = Font.createFont(Font.TRUETYPE_FONT, new File(AppData, "/fonts/" + properties.getProperty("font")))
                    .deriveFont((Float.parseFloat((String) properties.get("fontSize"))));
        } catch (FontFormatException | IOException e) {
                font = new Font(properties.get("font").toString(), Font.PLAIN, (int) Float.parseFloat((String) properties.get("fontSize")));
        }

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                font = font.deriveFont((float) font.getSize()-e.getWheelRotation());
                setFontAll(font);
                try{
                    properties.setProperty("fontSize", Float.toString((float) font.getSize()));
                    properties.store(new FileWriter(new File(AppData, "gui.properties")), "null");
                } catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    private void initSubmit(){
        submit = new JButton("Submit");
        submit.addActionListener(l -> updateOutput());

        jcb = new JCheckBox("Auto-submit on?");
        auto = Boolean.parseBoolean((String)properties.get("auto"));
        jcb.setSelected(auto);
        jcb.addActionListener(e -> {
            auto = jcb.isSelected();
            properties.setProperty("auto", Boolean.toString(auto));
    
            try {
                properties.store(new FileWriter(new File(AppData, "gui.properties")), null);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
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
        String[] inputs = inputField.getText().split(" ");
        StringBuilder output = new StringBuilder();

        for (String input : inputs) {
            try {
                char symbol;
                if (inputType == INPUT_TYPE_VINCENTCODE) {
                    symbol = IPAConverter.keyConvert(IPAConverter.toKey(input));
                } else if (inputType == INPUT_TYPE_XSAMPA){
                    symbol = IPAConverter.xSampaConvert(input);
                } else symbol = '\u0000';
                
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