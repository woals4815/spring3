package example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

public class CalcSum {
    private static final Log log = LogFactory.getLog(CalcSum.class);

    public Integer getSum(String filePath) throws IOException {
        return fileReadtemplate("src/main/resources/numbers.txt", new BufferedReaderCallback<Integer>() {
            @Override
            public Integer doCalculate(Integer result, String line) throws IOException {
                return result + Integer.parseInt(line);
            }
        }, 0);
    }
    public String concat(String filePath) throws IOException {
        return fileReadtemplate(filePath, new BufferedReaderCallback<String>() {
            @Override
            public String doCalculate(String result, String line) throws IOException {
                return result + line;
            }
        }, "");
    }
    public <T> T fileReadtemplate(String filePath, BufferedReaderCallback<T> callback, T initValue) throws IOException {
        BufferedReader br = null;
        try {
            InputStream in = new FileInputStream(new File(filePath));
            br = new BufferedReader( new InputStreamReader(in) );
            String line = br.readLine();
            T result = initValue;
            while(line != null) {
                result = callback.doCalculate(result, line);
                line = br.readLine();
            }
            return result;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
}
