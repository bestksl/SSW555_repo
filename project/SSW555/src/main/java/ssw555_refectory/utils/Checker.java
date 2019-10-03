package ssw555_refectory.utils;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;

import java.util.*;

public class Checker {
    private Map<String, Individual> individuals;
    private Map<String, Family> families;
    private static List<String> errList = new ArrayList<>();

    public Checker(Map<String, Individual> individuals, Map<String, Family> familys) {
        this.individuals = individuals;
        this.families = familys;
    }


    public static void addErr(String errMsg) {
        errList.add(errMsg);
    }

    public boolean check() throws Exception {
        //如果有不通过的项会将err信息加入到errList
        for (Individual i : individuals.values()) {
            checkBirthBeforeDeath(i);
            ageOld(i);
        }

        for (Family f : families.values()) {
            uniqueFirstname(f);
            checkMarrige(f);
            birthAfterParentsMarriges(f);
            parentNotTooOld(f);
        }


        return errList.size() == 0;
    }

    //Haoxuan Li
    private void checkBirthBeforeDeath(Individual i) throws Exception {
        if (i.getBirt() != null && i.getDeath() != null) {
            if (!(TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath()) >= 0)) {
                errList.add(i.getId() + ":  birth date should earlier than death dates");
            }
        }
    }

    //Haoxuan Li
    private void uniqueFirstname(Family f) {
        Set<String> nameSet = new HashSet<>();
        List<String> idList = new ArrayList<>();

        String husId = f.getHusbandID();
        if (husId != null) {
            idList.add(husId);
        }
        String wifeId = f.getWifeID();
        if (wifeId != null) {
            idList.add(wifeId);
        }
        if (f.getChildren().size() != 0) {
            idList.addAll(f.getChildren());
        }
        for (String id : idList) {
            if (individuals.get(id).getName() != null) {
                nameSet.add(individuals.get(id).getFirstName());
            }
        }
        if (!(nameSet.size() == idList.size())) {
            errList.add(f.getId() + ":  family member's first name should be unique!");
        }
    }

    private void checkMarrige(Family f) throws Exception {
        if (f.getMarried() == null || f.getDivorced() == null) {
            return;
        }
        int DivorceData = TimeUtils.getAge(f.getDivorced());
        int MarriageData = TimeUtils.getAge(f.getMarried());

        if (DivorceData > MarriageData) {
            errList.add(f.getId() + ":  Divorce before Marriage");
        }
    }

    // Jeff
    private void birthAfterParentsMarriges(Family f) throws Exception {
        ArrayList<String> children = f.getChildren();
        if (f.getMarried() == null) {
            return;
        }
        int marriagePeriod = TimeUtils.getAge(f.getMarried());
        for (String child : children) {
            Individual eachChild = individuals.get(child);
            int age = TimeUtils.getAge(eachChild.getBirt());
            if (age >= marriagePeriod) {
                errList.add(eachChild.getName() + " is born before Family " + f.getId());
            }
        }
    }

    public static void printErr() {
        for (String err : errList) {
            System.out.println(err);
        }
    }

    
    //Zihan Li
    private void ageOld(Individual i)throws Exception{
        if(i.getBirt()!=null&&i.getDeath()!=null){
            if(TimeUtils.getAge(i.getBirt())-TimeUtils.getAge(i.getDeath())>=150){
                errList.add(i.getId()+": one person should less than 150 years old");
            }
        }
    }

    //Zihan Li
    private void parentsNotTooOld(Family f)throws Exception{
        ArrayList<String> children=f.getChildren();
        ArrayList<String> father=f.getHusbandID();
        ArrayList<String> mother=f.getWifeID();
        Individual dad=individuals.get(father);
        Individual mom=individuals.get(mother);
        for(String child: children){
            Individual eachchildren=individuals.get(child);
            if(eachchildren.getBirt()!=null){
                if((TimeUtils.getAge(dad.getBirt())-TimeUtils.getAge(eachchildren.getBirt())>=80)||(TimeUtils.getAge(mom.getBirt())-TimeUtils.getAge(eachchildren.getBirt())>=60)){
                   errList.add(f.getId()+": Mother should be less than 60 years older than her children and father should be less than 80 years older than his children");
                }
            }
        }
    }


    private void familyMaleLastName() {
        HashMap<String,String> hash_setINDI = new HashMap<>();
        HashMap<String,String> hash_setSEX = new HashMap<>();
        String Fname;
        ArrayList<String> Sname = new ArrayList<>();
        for (Individual individuals : individuals.values()) {
            hash_setINDI.put(individuals.getId(), individuals.getLastName());
            hash_setSEX.put(individuals.getId(), individuals.getGender());
        }
        for(Family familys : families.values()){
            Fname = hash_setINDI.get(familys.getHusbandID());
            Sname = familys.getChildren();
            for( int i = 0; i < Sname.size(); i++)
            {
                String childLastname, childgender;
                childLastname = hash_setINDI.get(Sname.get(i));
                childgender = hash_setSEX.get(Sname.get(i));
                if(childgender.equals("M")&& !childLastname.equals(Fname))
                {
                    errList.add(Sname.get(i) + hash_setINDI.get(Sname.get(i)) + "The last name for Male person in a family is not the same!");
                }
            }
        }
    }

    private void uniqueId() {
        Set<String> hash_setI = new HashSet<String>();
        Set<String> hash_setF = new HashSet<String>();
        for (Individual individuals : individuals.values()) {
            if(!hash_setI.add(individuals.getId()))
            {
                errList.add("The individuals ID "+ individuals.getId() +" is not unique, please check again!");
            }
        }
        for(Family familys : families.values()){
            if(!hash_setI.add(familys.getId()))
            {
                errList.add("The family ID"+ familys.getId() +"is not unique, please check again!");
            }
        }
    }
}
