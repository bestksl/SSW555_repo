package ssw555_refectory.utils.tableutils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-09-24 00:45
 */


public class TextForm {

    int paddingL = 1;
    int paddingR = 1;

    List<String> title = new ArrayList<>();

    List<List<String>> datas = new ArrayList<>();

    int maxCol = 0;

    int colMaxLength = 19;

    char separator = '.';

    private TextForm() {
    }

    public static TextFormBulider bulider() {
        return new TextFormBulider(new TextForm());
    }


    public void printFormat() {
        List<List<String>> formData = new ArrayList<>();
        formData.add(title);
        formData.addAll(datas);
        Map<Integer, Integer> colMaxLengthMap = colMaxLength(formData);
        for (List<String> row : formData) {
            for (int j = 0; j < row.size(); j++) {
                Formatter formatter = new Formatter();
                String str = row.get(j);
                if (str.length() > colMaxLength) {
                    str = str.substring(0, colMaxLength);
                }
                int chineseNum = getChineseNum(str);
                Integer maxLength = colMaxLengthMap.get(j);
                String val = formatter.format("%-" + (maxLength - chineseNum) + "s", str).toString();
                row.set(j, val);
            }
        }
        Map<Integer, Integer> colMinBlankLengthMap = colMinBlankLength(formData);
        for (List<String> row : formData) {
            for (int j = 0; j < row.size(); j++) {
                String val = row.get(j);
                Integer minBlankLength = colMinBlankLengthMap.get(j);
                val = val.substring(0, val.length() - minBlankLength);
                row.set(j, val);
            }
        }
        String line = "";
        List<String> rows = new ArrayList<>();
        for (List<String> strings : formData) {
            String pL = StringUtils.repeat(" ", paddingL);
            String pR = StringUtils.repeat(" ", paddingR);
            String row = separator + pL + String.join(pL + separator + pR, strings) + pR + separator;
            if (line.length() < row.length()) {
                line = StringUtils.repeat(separator, row.length());
            }
            rows.add(row);
        }
        System.out.println(line);
        for (String row : rows) {
            System.out.println(row);
            System.out.println(line);
        }
    }

    private Map<Integer, Integer> colMaxLength(List<List<String>> formData) {
        Map<Integer, Integer> map = new HashMap<>();
        for (List<String> formDatum : formData) {
            int col = 0;
            List<String> strings = formDatum;
            while (strings.size() > col) {
                String val = strings.get(col);
                if (val.length() > colMaxLength) {
                    val = val.substring(0, colMaxLength);
                    strings.set(col, val);
                }
                int length = val.getBytes().length;
                Integer integer = map.get(col);
                if (integer == null) {
                    map.put(col, length);
                } else {
                    if (integer < length) {
                        map.put(col, length);
                    }
                }
                col++;
            }
        }
        return map;
    }


    private Map<Integer, Integer> colMinBlankLength(List<List<String>> formData) {
        Map<Integer, Integer> map = new HashMap<>();
        for (List<String> formDatum : formData) {
            int col = 0;
            while (formDatum.size() > col) {
                String val = formDatum.get(col);
                int length = 0;
                for (int i1 = val.length() - 1; i1 >= 0; i1--) {
                    if (val.charAt(i1) == ' ') {
                        length++;
                    } else {
                        break;
                    }
                }
                Integer integer = map.get(col);
                if (integer == null) {
                    map.put(col, length);
                } else {
                    if (integer > length) {
                        map.put(col, length);
                    }
                }
                col++;
            }
        }
        return map;
    }


    private int getChineseNum(String val) {
        if (val == null) {
            val = "null";
        }
        String regex = "[\u4e00-\u9fa5|。|，]";
        ArrayList<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(val);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list.size();
    }
}
