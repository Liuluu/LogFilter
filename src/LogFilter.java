import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LogFilter {

    private File file;
    private File outputFile;

    private boolean isHasPrefix;
    private String prefix;

    private boolean isHasSuffix;
    private String suffix;

    private boolean isHasOther;
    private String otherType;
    private double otherValue;

    private int count;
    private double sum;
    private double average;
    private double time;
    private ArrayList<Integer> failedLine;

    private FilterListener filterListener;

    public LogFilter(File file, FilterListener listener) {
        this.file = file;
        this.filterListener = listener;
        failedLine = new ArrayList<>();
    }


    public void run() {
        if (!file.exists()) {
            UI.showErrorDialog("文件不存在，请重新选择");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String outputFileName = dateFormat.format(new Date(System.currentTimeMillis())) + ".txt";
        outputFile = new File(file.getParent(), outputFileName);

        if (outputFile.exists()) {
            outputFile.delete();
        }
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk"));
            writer = new BufferedWriter(new FileWriter(outputFile));
            String text = null;
            int line = 0;
            while ((text = reader.readLine()) != null) {
                line++;
                if (text.isEmpty()) {
                    failedLine.add(line);
                    continue;
                }
                if (filter(text)) {
                    count++;
                    sum += time;
                    writer.write(String.valueOf(time));
                    writer.newLine();
                } else {
                    failedLine.add(line);
                }
            }
            if (count > 0) {
                average = sum / count;
            }

            filterListener.filterOver(this);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 字符串过滤
     *
     * @param text 输入文本
     * @return 是否过滤成功
     */
    private boolean filter(String text) {
        if (isHasPrefix) {
            if (text.lastIndexOf(prefix) > -1) {
                text = text.substring(text.lastIndexOf(prefix) + prefix.length());
            } else {
                return false;
            }
        }
        if (isHasSuffix) {
            if (text.lastIndexOf(suffix) > -1) {
                text = text.substring(0, text.lastIndexOf(suffix));
            } else {
                return false;
            }
        }

        try {
            time = Double.valueOf(text);
        } catch (NumberFormatException e) {
            return false;
        }

        if (isHasOther) {
            switch (otherType) {
                case "大于" :
                    if (time <= otherValue) {
                        return false;
                    }
                    break;
                case "小于":
                    if (time >= otherValue) {
                        return false;
                    }
                    break;
                case "等于":
                    if (time != otherValue) {
                        return false;
                    }
                    break;
                case "不等于":
                    if (time == otherValue) {
                        return false;
                    }
                    break;
                case "大于等于":
                    if (time < otherValue) {
                        return false;
                    }
                    break;
                case "小于等于":
                    if (time > otherValue) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    public boolean isHasPrefix() {
        return isHasPrefix;
    }

    public void setHasPrefix(boolean hasPrefix) {
        isHasPrefix = hasPrefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isHasSuffix() {
        return isHasSuffix;
    }

    public void setHasSuffix(boolean hasSuffix) {
        isHasSuffix = hasSuffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean isHasOther() {
        return isHasOther;
    }

    public void setHasOther(boolean hasOther) {
        isHasOther = hasOther;
    }

    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    public double getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(double otherValue) {
        this.otherValue = otherValue;
    }

    public int getCount() {
        return count;
    }

    public double getSum() {
        return sum;
    }

    public double getAverage() {
        return average;
    }

    public ArrayList<Integer> getFailedLine() {
        return failedLine;
    }

    public File getOutputFile() {
        return outputFile;
    }
}
