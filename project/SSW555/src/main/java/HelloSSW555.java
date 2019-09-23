import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-09-12 16:44
 */
public class HelloSSW555 {
    private BufferedReader bfr;

    {
        try {
            bfr = new BufferedReader(new FileReader("/Users/haoxuanli/Documents/SSW555_Repo/SSW555_repo/project/SSW555/src/main/java/my.ged"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void readFile() throws IOException {
        String line;
        while ((line = bfr.readLine()) != null) {
            System.out.println("--> " + line);
            System.out.println("<-- " + analysis(line));
        }

    }

    String analysis(String line) {
        String[] elements = line.split(" ");
        ArrayList<String> result = new ArrayList<String>();
        int index = -1;
        result.add(elements[0]);
        for (int i = 0; i < elements.length; i++) {
            if (ValidArguemnts.contains(elements[i])) {
                index = i;
                result.add("Y");
                result.add(elements[i]);
                break;
            }
        }
        if (index == -1) {
            result.add("N");
        }
        for (int i = 0; i < elements.length; i++) {
            if (i == 0 || i == index) {
                continue;
            }
            result.add(elements[i]);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            if (i == 0 || i == 1) {
                sb.append(result.get(i) + "|");
            } else {
                sb.append(result.get(i) + " ");
            }

        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        new HelloSSW555().readFile();

    }
}

