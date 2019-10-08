package ssw555_refectory.core;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;
import ssw555_refectory.utils.Checker;
import ssw555_refectory.utils.TimeUtils;
import ssw555_refectory.utils.FileUtils;
import ssw555_refectory.utils.tableutils.TextForm;
import ssw555_refectory.utils.tableutils.TextFormBulider;

import java.io.IOException;
import java.util.*;

/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-09-23 21:59
 */
public class Finder {
    private List<String> fileList;
    private Map<String, Individual> individuals = new HashMap<>();
    private Map<String, Family> families = new HashMap<>();

    public Finder(String filePath) throws IOException {
        fileList = FileUtils.readFile(filePath);
        findAllThings();
    }

    public Map<String, Individual> getIndividuals() {
        return individuals;
    }

    public Map<String, Family> getFamilies() {
        return families;
    }

    private void findAllThings() {
        String flag = "false";
        List<String[]> temp = new ArrayList<>();
        for (String line : fileList) {
            String[] elements = line.split(" ");
            if ("0".equals(elements[0]) && ((flag.equals("FAM")) || flag.equals("INDI"))) {
                if (temp.get(0)[2].equals("FAM")) {
                    addFamily(temp);
                } else {
                    addIndividual(temp);
                }
                temp.clear();
            } else if ("FAM".equals(elements[2]) && "0".equals(elements[0])) {
                flag = "FAM";
            } else if ("INDI".equals(elements[2]) && "0".equals(elements[0])) {
                flag = "INDI";
            }
            if (flag.equals("FAM")) {
                temp.add(elements);
            } else if (flag.equals("INDI")) {
                temp.add(elements);
            }
        }
    }


    private void addIndividual(List<String[]> individualList) {
        Individual i = new Individual();

        boolean flag = false;
        String arg = "";
        for (String[] e : individualList) {
            if (e.length >= 3 || e[2].equals("BIRT") || e[2].equals("DEAT")) {
                if (flag) {
                    e[2] = arg;
                    e[3] = e.length > 4 ? e[3] + "-" + e[4] + "-" + e[5] : "N/A";
                }
                if ((e[2].equals("BIRT") || e[2].equals("DEAT")) && !flag) {
                    flag = true;
                    arg = e[2];
                    continue;
                }
                switch (e[2]) {
                    case "NAME":
                        i.setFirstName(e[3]);
                        i.setLastName(e[4].split("/")[1]);
                        break;
                    case "INDI":
                        i.setId(e[3]);
                        break;
                    case "SEX":
                        i.setGender(e[3]);
                        break;
                    case "BIRT":
                        i.setBirt(e[3]);
                        flag = false;
                        arg = "";
                        break;
                    case "DEAT":
                        i.setDeath(e[3]);
                        flag = false;
                        arg = "";
                        break;
                    case "FAMC":
                        i.setChild(e[3]);
                        break;
                    case "FAMS":
                        i.setSpouse(e[3]);
                        break;
                }
            }
        }
        try {
            i.setAlive(i.getDeath() == null);
            if (i.getBirt() != null) {
                if (!i.isAlive()) {
                    i.setAge((TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath()) + ""));
                } else {
                    i.setAge(TimeUtils.getAge(i.getBirt()) + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (individuals.get(i.getId()) != null) {
            i.setId("."+i.getId());
            individuals.put(i.getId(), i);
            return;
        }
        if (i.getId() != null) {
            individuals.put(i.getId(), i);
        }
    }

    private void addFamily(List<String[]> familyList) {
        Family f = new Family();
        boolean flag = false;
        String arg = "";
        for (String[] e : familyList) {
            if (e.length >= 3 || e[2].equals("MARR") || e[2].equals("DIV")) {
                if (flag) {
                    e[2] = arg;
                    e[3] = e.length > 4 ? e[3] + "-" + e[4] + "-" + e[5] : "N/A";
                }
                if ((e[2].equals("MARR") || e[2].equals("DIV")) && !flag) {
                    flag = true;
                    arg = e[2];
                    continue;
                }
                switch (e[2]) {
                    case "FAM":
                        f.setId(e[3]);
                        break;
                    case "MARR":
                        f.setDate(e[3]);
                        f.setMarried(e[3]);
                        flag = false;
                        arg = "";
                        break;
                    case "HUSB":
                        f.setHusbandID(e[3]);
                        f.setHusbandName(individuals.get(e[3]).getName());
                        break;
                    case "WIFE":
                        f.setWifeID(e[3]);
                        f.setWifeName(individuals.get(e[3]).getName());
                        break;
                    case "DIV":
                        f.setDivorced(e[3]);
                        flag = false;
                        arg = "";
                        break;
                    case "CHIL":
                        f.addChildren(e[3]);
                        break;
                    case "DIsV":
                        f.setDivorced(e[3]);
                        break;
                }
            }
        }
        if (families.get(f.getId()) != null) {
            if (families.get(f.getId()) != null) {
                f.setId("."+f.getId());
                families.put(f.getId(), f);
                return;
            }
            return;
        }
        families.put(f.getId(), f);
    }

    public void printIndividuals() {

        TextFormBulider bulider = TextForm.bulider();
        bulider.title("ID", "NAME", "Gender", "Birthday", "Age", "Alive", "Death", "Child", "Spouse");
        for (Individual i : individuals.values()) {
            bulider.addRow(i.getId(), i.getName() == null ? "N/A" : i.getName(), i.getGender() == null ? "N/A" : i.getGender(),
                    i.getBirt() == null ? "N/A" : i.getBirt(), i.getAge() == null ? "N/A" : i.getAge(), i.isAlive() + "", i.getDeath() == null ? "N / A" : i.getDeath(), i.getChild() == null ? "N/A" : i.getChild(), i.getSpouse() == null ? "N/A" : i.getSpouse());
        }
        TextForm textForm = bulider.finish();
        bulider.paddingL(2);
        bulider.paddingR(2);
        bulider.separator('.');
        textForm.printFormat();

    }

    public void printFamilies() {

        TextFormBulider bulider = TextForm.bulider();
        bulider.title("ID", "Married", "Divorced", "HusbandID", "HusbandName", "Wife ID", "Wife Name", "Children");
        for (Family f : families.values()) {
            bulider.addRow(f.getId(), f.getDate() == null ? "N/A" : f.getDate(), f.getDivorced() == null ? "N/A" : f.getDivorced(),
                    f.getHusbandID() == null ? "N/A" : f.getHusbandID(), f.getHusbandName() == null ? "N/A" : f.getHusbandName(),
                    f.getWifeID() == null ? "N/A" : f.getWifeID(), f.getWifeName() == null ? "N / A" : f.getWifeName(), f.getChildren() == null ? "N/A" : f.getChildren().toString());
        }
        TextForm textForm = bulider.finish();
        bulider.paddingL(2);
        bulider.paddingR(2);
        bulider.separator('.');
        textForm.printFormat();

    }
}
