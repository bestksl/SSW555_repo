package ssw555_refectory.utils;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;

import java.util.*;

public class CheckAll {
    Map<String, Individual> individuals;
    Map<String, Family> familys;


    public CheckAll(Map<String, Individual> individuals, Map<String, Family> familys){
        this.individuals =  individuals;
        this.familys = familys;
    }


    public void CheckAll(){
        for (Individual individuals : individuals.values()) {


        }

        for(Family familys : familys.values()){

        }
    }

    public void CheckMarrige(){

    }

    public void CheckBirthBeforeDeath(){

    }

    public void birthAfterParentsMarriges(){

    }

    public void ageOld(){

    }

    public void  Unique(){

    }

    public void FamilyMaleLastName(){
        HashMap<String,String> hash_setINDI = new HashMap<>();
        HashMap<String,String> hash_setSEX = new HashMap<>();
        String Fname;
        ArrayList<String> Sname = new ArrayList<>();
        for (Individual individuals : individuals.values()) {
            hash_setINDI.put(individuals.getId(), individuals.getName());
            hash_setSEX.put(individuals.getId(), individuals.getGender());
        }
        for(Family familys : familys.values()){
            Fname = familys.getHusbandName();
            Sname = familys.getChildren();
            for( int i = 0; i < Sname.size(); i++)
            {
                String childname, childgender;
                childname = hash_setINDI.get(Sname.get(i));
                childgender = hash_setSEX.get(Sname.get(i));
                if(childgender.equals("M")&& !childname.equals(Fname))
                {
                    throw new IllegalArgumentException(
                            "The last name for Male person in a family is not the same!");
                }
            }
        }


    }

    public void UniqueId(){
        Set<String> hash_setI = new HashSet<String>();
        Set<String> hash_setF = new HashSet<String>();
        for (Individual individuals : individuals.values()) {
            if(!hash_setI.add(individuals.getId()))
            {
                throw new IllegalArgumentException(
                        "The individuals ID is not unique, please check again!");
            }

        }
        for(Family familys : familys.values()){
            if(!hash_setI.add(familys.getId()))
            {
                throw new IllegalArgumentException(
                        "The family ID is not unique, please check again!");
            }
        }
    }
}
