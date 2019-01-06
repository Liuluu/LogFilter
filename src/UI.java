import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UI implements FilterListener{
    private JTextField inputTextField;
    private JButton fileChooseBtn;
    private JButton runBtn;
    private JCheckBox prefixCheckBox;
    private JTextField prefixTextField;
    private JCheckBox suffixCheckBox;
    private JTextField suffixTextField;
    private JCheckBox otherCheckBox;
    private JComboBox otherComboBox;
    private JTextField otherTextField;
    private JTextArea outputTextArea;
    private JPanel panelAll;

    private String lastFilePath;

    public UI() {
        fileChooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser;
                if (lastFilePath != null) {
                    fileChooser = new JFileChooser(lastFilePath);
                } else {
                    fileChooser = new JFileChooser();
                }

                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.showDialog(new JLabel(), "选择日志文件");
                File inputFile = fileChooser.getSelectedFile();
                if (inputFile != null) {
                    inputTextField.setText(inputFile.getAbsolutePath());
                    lastFilePath = inputFile.getParent();
                }
            }
        });

        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputTextField.getText().isEmpty()) {
                    showErrorDialog("请选择日志文件");
                    return ;
                }
                if (!prefixCheckBox.isSelected() && !suffixCheckBox.isSelected()) {
                    showErrorDialog("前后缀需要至少勾选一项");
                    return ;
                }
                LogFilter filter = new LogFilter(new File(inputTextField.getText()), UI.this);
                if (prefixCheckBox.isSelected()) {
                    filter.setHasPrefix(true);
                    if (prefixTextField.getText().isEmpty()) {
                        showErrorDialog("勾选前缀时前缀输入框不能为空");
                        return ;
                    }
                    filter.setPrefix(prefixTextField.getText());
                }
                if (suffixCheckBox.isSelected()) {
                    filter.setHasSuffix(true);
                    if (suffixTextField.getText().isEmpty()) {
                        showErrorDialog("勾选后缀时后缀输入框不能为空");
                        return ;
                    }
                    filter.setSuffix(suffixTextField.getText());
                }
                if (otherCheckBox.isSelected()) {
                    filter.setHasOther(true);
                    filter.setOtherType((String) otherComboBox.getSelectedItem());
                    try {
                        filter.setOtherValue(Double.valueOf(otherTextField.getText().replace("　", "")));
                    } catch (NumberFormatException exception) {
                        showErrorDialog("特殊限制框请输入数字");
                        return ;
                    }
                }
                filter.run();
            }
        });
    }

    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "出错啦", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void filterOver(LogFilter filter) {
        outputTextArea.append("数据存放路径：" + filter.getOutputFile().getAbsolutePath() + "\r\n");
        outputTextArea.append("个数 : " + filter.getCount() + "\r\n");
        outputTextArea.append("总和 : " + filter.getSum() + "\r\n");
        outputTextArea.append("平均值 : " + filter.getAverage() + "\r\n");
        outputTextArea.append("未识别行号（包括空行、不符合要求行、无法识别行） : " + filter.getFailedLine() + "\r\n");
        outputTextArea.append("——————————" + "\r\n");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LogFilter");
        frame.setSize(400, 500);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int)(toolkit.getScreenSize().getWidth() - frame.getWidth())/2;
        int y = (int)(toolkit.getScreenSize().getHeight() - frame.getHeight())/2;
        frame.setLocation(x, y);

        frame.setContentPane(new UI().panelAll);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
