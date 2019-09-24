package ssw555_refectory.utils;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-09-23 21:59
 */
public class Finder {
    private List<String> fileList;
    private List<Individual> individuals = new ArrayList<>();
    private List<Family> families = new ArrayList<>();

    public Finder(String filePath) throws IOException {
        fileList = FileUtils.readFile(filePath);
        findAllThings();
    }

    private void findAllThings() {
        String flag = "false";
        List<String[]> temp = new ArrayList<>();
        for (String line : fileList) {
            String[] elements = line.split(" ");
            if ("0".equals(elements[0]) && ((flag.equals("FAM")) || flag.equals("INDI"))) {
                if (flag.equals("FAM")) {
                    addFamily(temp);
                } else {
                    addIndividual(temp);
                }
                temp.clear();
                flag = "false";
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

    private void addFamily(List<String[]> familyList) {
        Family family = new Family();
        for (String[] element : familyList) {
            if (element.length < 4) {
                continue;
            }
            switch (element[2]) {
                case "FAM":
                    family.setId(element[3]);
                case "MARR":
                    family.setMarried(element[3]);
                case "HUSB":
                    family.setHusbandID(element[3]);
                case "WIFE":
                    family.setWifeID(element[3]);
                case "CHIL":
                    family.addChildren(element[3]);
                case "DIV":
                    family.setDivorced(element[3]);
                case "DATE":
                    family.setDate(element[3]);
            }
        }
        families.add(family);
    }

    public void printFamilies() {
        for (Family family : families) {
            ;
        }
    }

    private void addIndividual(List<String[]> individualList) {
        Individual individual = new Individual();
        boolean flag = false;
        String date = null;
        String argName = null;
        for (String[] element : individualList) {
            if (element.length < 4 && !element[2].equals("BIRT")) {
                flag = false;
                date = null;
                argName = null;
                continue;
            }
            if (flag) {
                element[2] = argName;
                System.out.println(Arrays.toString(element));
                date = element.length>4?element[3] + "/" + element[4] + "/" + element[5]:"N/A";
            }
            if (((element[2].equals("BIRT") || (element[2].equals("DEAT") && element[3].equals("Y"))) && date == null)) {
                flag = true;
                argName = element[2];
                continue;
            }
            switch (element[2]) {
                case "INDI":
                    individual.setId(element[3]);
                case "NAME":
                    individual.setName(element[3]);
                case "SEX":
                    individual.setGender(element[3]);
                case "BIRT":
                    individual.setBirt(date);
                    argName = null;
                    date = null;
                case "DEAT":
                    individual.setDeath(date);
                    argName = null;
                    date = null;
                case "FAMC":
                    individual.setChild(element[3]);
                case "FAMS":
                    individual.setSpouse(element[3]);
            }
        }
        individuals.add(individual);

    }

    public void printIndividuals() {

        TextFormBulider bulider = TextForm.bulider();
        bulider.title("ID", "NAME", "Gender", "Birthday", "Age", "Alive", "Death", "Child", "Spouse");
        for (Individual i : individuals) {
            bulider.addRow(i.getId() == null ? "N/A" : i.getId(), i.getName() == null ? "N/A" : i.getName(), i.getGender() == null ? "N/A" : i.getGender(),
                    i.getBirt() == null ? "N/A" : i.getBirt(), i.getAge() == null ? "N/A" : i.getAge(), "没完成", i.getDeath() == null ? "N / A" : i.getDeath(), i.getChild() == null ? "N/A" : i.getChild(), i.getSpouse() == null ? "N/A" : i.getSpouse());
        }
        TextForm textForm = bulider.finish();
        bulider.paddingL(2);
        bulider.paddingR(2);
        bulider.separator('.');
        textForm.printFormat();


    }
}
