import javax.swing.*;

public class DemoFrame extends JFrame {

    private JPanel inputJPanel, prefixJPanel, suffixJPanel, otherJPanel;
    private JLabel inputJLabel;
    private JTextField inputJTextField, prefixJTextField, suffixJTextField, otherJTextField;
    private JButton fileChooseJBtn, runJBtn;
    private JCheckBox prefixJCheckBox, suffixJCheckBox, otherJCheckBox;
    private JComboBox otherJComboBox;

    public DemoFrame() {
        inputJLabel = new JLabel("导入文件：");
        inputJTextField = new JTextField(20);
        fileChooseJBtn = new JButton("...");
        runJBtn = new JButton("Run");

        inputJPanel = new JPanel();
        inputJPanel.add(inputJLabel);
        inputJPanel.add(inputJTextField);
        inputJPanel.add(fileChooseJBtn);
        inputJPanel.add(runJBtn);


        prefixJCheckBox = new JCheckBox("前缀");
        prefixJTextField = new JTextField(20);
        prefixJPanel = new JPanel();
        prefixJPanel.add(prefixJCheckBox);
        prefixJPanel.add(prefixJTextField);

        this.add(inputJPanel);
        this.add(prefixJPanel);


        this.setSize(600, 800);
        this.setTitle("字段过滤");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}