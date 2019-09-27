package ssw555_refectory.utils;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;
import java.util.Map;
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

    }

    public void UniqueId(){

    }
}
