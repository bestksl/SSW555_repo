package ssw555_refectory.utils;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;

import java.util.*;

public class Checker {
    private Map<String, Individual> individuals;
    private Map<String, Family> families;
    private List<String> errList = new ArrayList<>();

    public Checker(Map<String, Individual> individuals, Map<String, Family> familys) {
        this.individuals = individuals;
        this.families = familys;
    }


    public List<String> getErrList() {
        return errList;
    }

    public boolean check() throws Exception {
        //如果有不通过的项会将err信息加入到errList
        for (Individual i : individuals.values()) {
            checkBirthBeforeDeath(i);
        }

        for (Family f : families.values()) {
            uniqueFirstname(f);
        }


        return errList.size() == 0;
    }

    //Haoxuan Li
    private void checkBirthBeforeDeath(Individual i) throws Exception {
        if (i.getBirt() != null && i.getDeath() != null) {
            if (!(TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath()) >= 0)) {
                errList.add(i.getId() + "birth date should earlier than death dates");
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
            errList.add(f.getId() + "family member's first name should be unique!");
        }
    }

    private void checkMarrige(Family F) throws Exception {
        int DivorceData = TimeUtils.getAge(F.getDivorced());
        int MarriageData = TimeUtils.getAge(F.getMarried());
        if(DivorceData > MarriageData){
            errList.add(F.getId()+"Divorce before Marriage");
        }
    }

    // Jeff
    private void birthAfterParentsMarriges(Family F) throws Exception {
        ArrayList<String> children = F.getChildren();
        int marriagePeriod = TimeUtils.getAge(F.getMarried());
        for(String child: children){
            Individual eachChild = individuals.get(child);
            int age = TimeUtils.getAge(eachChild.getBirt());
            if(age >= marriagePeriod){
                errList.add(eachChild.getName()+" is born before Family "+F.getId());
            }
        }
    }

    private void ageOld() {

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
