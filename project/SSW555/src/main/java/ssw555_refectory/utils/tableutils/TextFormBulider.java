package ssw555_refectory.utils.tableutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-09-24 00:47
 */


public class TextFormBulider {
    private TextForm textForm;

    TextFormBulider(TextForm textForm) {
        this.textForm = textForm;
    }

    public TextFormBulider title(String... titles) {
        if (textForm.maxCol < titles.length) {
            textForm.maxCol = titles.length;
        }
        for (String title : titles) {
            if (title == null) {
                title = "null";
            }
            textForm.title.add(title);
        }
        return this;
    }

    public void paddingL(int paddingL) {
        textForm.paddingL = paddingL;
    }

    public void paddingR(int paddingR) {
        textForm.paddingR = paddingR;
    }

    public void separator(char separator) {
        textForm.separator = separator;
    }

    public TextFormBulider colMaxLength(int colMaxLength) {
        textForm.colMaxLength = colMaxLength;
        return this;
    }

    public void addRow(String... cols) {
        if (textForm.maxCol < cols.length) {
            textForm.maxCol = cols.length;
        }
        List<String> list = new ArrayList<>(cols.length);
        for (String col : cols) {
            if (col == null) {
                col = "null";
            }
            list.add(col);
        }
        textForm.datas.add(list);
    }

    public TextForm finish() {
        int titleSize = textForm.title.size();
        if (titleSize < textForm.maxCol) {
            for (int i = 0; i < textForm.maxCol - titleSize; i++) {
                textForm.title.add(null);
            }
        }
        for (List<String> data : textForm.datas) {
            int dataSize = data.size();
            if (dataSize < textForm.maxCol) {
                for (int i = 0; i < textForm.maxCol - dataSize; i++) {
                    data.add(null);
                }
            }
        }
        return textForm;
    }
}

