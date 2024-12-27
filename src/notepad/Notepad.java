package notepad;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Notepad extends JFrame implements ActionListener {

    private JTextArea area;
    private JScrollPane scpane;
    String text = "";

    public Notepad() {
        super("Notepad");
        setSize(1950, 1050);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar(); // Menu bar

        // File menu
        JMenu file = new JMenu("File");
        JMenuItem newdoc = new JMenuItem("New");
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newdoc.addActionListener(this);

        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);

        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        print.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exit.addActionListener(this);

        // Edit menu
        JMenu edit = new JMenu("Edit");
        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);

        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);

        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);

        JMenuItem selectall = new JMenuItem("Select All");
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectall.addActionListener(this);

        JMenu texttospeech = new JMenu("SpeechAssistant"); 
        JMenuItem textToSpeech = new JMenuItem("Text to Speech");
        textToSpeech.addActionListener(this);
        texttospeech.add(textToSpeech);

        // Help menu
        JMenu about = new JMenu("Help");
        JMenuItem notepad = new JMenuItem("About Notepad");
        notepad.addActionListener(this);

        area = new JTextArea();
        area.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        scpane = new JScrollPane(area);
        scpane.setBorder(BorderFactory.createEmptyBorder());

        setJMenuBar(menuBar);
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(about);
        menuBar.add(texttospeech);

        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);

        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectall);

        about.add(notepad);

        add(scpane, BorderLayout.CENTER);
       
        
        // Add Window Listener to show confirmation when closing the window
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showOptionDialog(Notepad.this,
                        "Are you sure you want to close?",
                        "Close Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new String[]{"Save", "Close"},
                        "Save");

                if (option == JOptionPane.YES_OPTION) {
                    // If "Save" is chosen, show file save dialog
                    JFileChooser saveDialog = new JFileChooser();
                    saveDialog.setApproveButtonText("Save");
                    int saveOption = saveDialog.showSaveDialog(Notepad.this);
                    if (saveOption == JFileChooser.APPROVE_OPTION) {
                        File file = new File(saveDialog.getSelectedFile() + ".txt");
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                            area.write(writer); // Write text to the file
                            System.exit(0); // Close the application after saving
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (option == JOptionPane.NO_OPTION) {
                    // If "Close" is chosen, exit without saving
                    System.exit(0);
                }
            }
        });
        setVisible(true);
       
    }

    
    public void actionPerformed(ActionEvent ae) {
    	
        if (ae.getActionCommand().equals("New")) {
            area.setText("");

        } else if (ae.getActionCommand().equals("Open")) {
            JFileChooser chooser = new JFileChooser("D:/Java");
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
            chooser.addChoosableFileFilter(restrict);

            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                try {
                    FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    area.read(br, null);
                    br.close();
                    area.requestFocus();
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        } else if (ae.getActionCommand().equals("Save")) {
            final JFileChooser SaveAs = new JFileChooser();
            SaveAs.setApproveButtonText("Save");
            int actionDialog = SaveAs.showOpenDialog(this);
            if (actionDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = new File(SaveAs.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(fileName));
                area.write(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (ae.getActionCommand().equals("Print")) {
            try {
                area.print();
            } catch (Exception e) {
            }
        } else if (ae.getActionCommand().equals("Exit")) {
            System.exit(0);
        } else if (ae.getActionCommand().equals("Copy")) {
            text = area.getSelectedText();
        } else if (ae.getActionCommand().equals("Paste")) {
            area.insert(text, area.getCaretPosition());
        } else if (ae.getActionCommand().equals("Cut")) {
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        } else if (ae.getActionCommand().equals("Select All")) {
            area.selectAll();
        } else if (ae.getActionCommand().equals("Text to Speech")) {
            String textToRead = area.getText().trim();
            if (textToRead.isEmpty()) {
                JOptionPane.showMessageDialog(this, "The text area is empty. Please enter text to convert to speech.");
            } else {
                textToSpeech(textToRead);
            }
        } else if (ae.getActionCommand().equals("About Notepad")) {
            new About().setVisible(true);
        }
    }

    public void textToSpeech(String text) {
       
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice("kevin16");

        if (voice != null) {
            voice.allocate();
            try {
                voice.speak(text);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                voice.deallocate();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Voice 'kevin16' not found.");
        }
    }


    public static void main(String args[]) {
        new Notepad();
    }
}
