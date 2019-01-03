import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UI {
    private JTextField inputTextField;
    private JButton fileChooseBtn;
    private JButton runBtn;
    private JCheckBox prefixCheckBox;
    private JTextField prefixTextField;
    private JCheckBox suffixCheckBox;
    private JTextField suffixTextField;
    private JCheckBox otherCheckBox;
    private JComboBox comboBox1;
    private JTextField otherTextField;
    private JTextArea outputTextArea;
    private JPanel panelAll;

    public UI() {
        fileChooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.showDialog(new JLabel(), "选择日志文件");
                File inputFile = fileChooser.getSelectedFile();
                if (inputFile != null) {
                    inputTextField.setText(inputFile.getAbsolutePath());
                }
            }
        });
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("LogFilter");
        frame.setContentPane(new UI().panelAll);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
