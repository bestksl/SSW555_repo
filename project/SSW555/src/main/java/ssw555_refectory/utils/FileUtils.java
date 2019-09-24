package ssw555_refectory.utils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-09-23 21:31
 */
public class FileUtils {


    public static List<String> readFile(String filePath) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader(filePath));
        List<String> stringList = new ArrayList<>();
        String line;
        while ((line = bfr.readLine()) != null) {
            stringList.add(analysis(line));
        }
        return stringList;

    }

    private static String analysis(String line) {
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
            sb.append(result.get(i) + " ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
