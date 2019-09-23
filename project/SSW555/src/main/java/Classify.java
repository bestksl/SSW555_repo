import java.io.*;
import java.util.*;
import java.time.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JFrame; 
import javax.swing.JScrollPane; 
import javax.swing.JTable; 



public class Classify {
   private BufferedReader bfr;

   {
      try {
         bfr = new BufferedReader(new FileReader("Testing.txt"));
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
   }
   
   List<String> list = new ArrayList<>(
      Arrays.asList("INDI", "FAM", "HEAD", "TRLR", "NOTE","NAME", "SEX", "BIRT", "DEAT", 
        		"FAMC", "FAMS", "MARR", "HUSB", "WIFE", "CHIL", "DIV","DATE"));

   void readFile() throws IOException {
      String line;
      while ((line = bfr.readLine()) != null) {
         analysisIndividual(line);
      }
      draw("Individual");
   
     
   
   }

   Map<String, Family> idToFamily = new HashMap<String, Family>();
   Map<String, Individual> idToIndividual = new HashMap<String, Individual>();
   StringBuilder sb = new StringBuilder();

   void analysisIndividual(String line){
      String[] elements = line.split(" ");
      if(!elements[1].equals("NOTE") && !elements[1].equals("HEAD") 
      	&& !elements[1].equals("TRLR")){
         if(sb.length() != 0 && elements[0].equals("0")){
            generateEachIndividual(sb.toString(), info);
            //eachIndividual.add(sb.toString());
            sb = new StringBuilder();
         }
         sb.append(line);
         sb.append("-");
      }
   }


   ArrayList<Individual> info = new ArrayList<>();
   ArrayList<Family> infoF = new ArrayList<>();
   void generateEachIndividual(String each, ArrayList<Individual> info){
       		         
      if(each.contains("INDI")){
         Individual member = define(each);
         info.add(member);
         idToIndividual.put(member.id, member); 
      }else{
         Family member = defineF(each);
         infoF.add(member);
         idToFamily.put(member.id, member);
      }
      
   }

   Family defineF(String each){
      String[] elements = each.split("-");
      String[] dateIndicator = new String[1];
      Family fMember = new Family();
      for(String element: elements){
         	 
         checkFamly(dateIndicator, element, fMember);
      }
      return fMember;
   }
   
   Individual define(String each){   
      String[] elements = each.split("-");
      String[] dateIndicator = new String[1];      
      Individual iMember = new Individual();	
      for(String element: elements){
         checkIndividual(dateIndicator, element, iMember);
      }
      return iMember;   
   }

   void checkFamly(String[] dateIndicator,  String element, Family fMember){
      String[] elements = element.split(" ");
   		
      if(dateIndicator[0] == null || dateIndicator[0].length() == 0){
         if(elements[elements.length-1].equals("FAM")){
            fMember.id = elements[1];
            return;
         }
         if(elements[1].equals("HUSB")){
            fMember.husbandID = elements[2];
            fMember.husbandName = idToIndividual.get(elements[2]).name();
            return;
         }
      
         if(elements[1].equals("WIFE")){
            fMember.wifeID = elements[2];
            fMember.wifeName = idToIndividual.get(elements[2]).name();
            return;
         }
      
         if(elements[1].equals("CHIL")){
            fMember.children.add(elements[2]);
            return;
         }
      
         if(elements[1].equals("MARR") || elements[1].equals("DIV") ){
            dateIndicator[0] = elements[1];
         } 		
      }else{
         if(dateIndicator[0].equals("MARR")){
            String marriedDate = elements[2]+"-"+elements[3]+"-"+elements[4];
            fMember.married = marriedDate;
            fMember.divorced = "N/A";            
         }else{      
            String divDate = elements[2]+"-"+elements[3]+"-"+elements[4];
            fMember.divorced = divDate;
         }
         dateIndicator[0] = "";
      }
   
   }



   void checkIndividual(String[] dateIndicator,  String element, Individual member){
      String[] elements = element.split(" ");
   		
      if(dateIndicator[0] == null || dateIndicator[0].length() == 0){
         if(elements[elements.length-1].equals("INDI")){
            member.id = elements[1];
            return;
         }
         if(elements[1].equals("NAME")){
            member.name = elements[2]+" "+elements[3];
            return;
         }
      
         if(elements[1].equals("SEX")){
            member.gender = elements[2];
            return;
         }
      
         if(elements[1].equals("FAMS")){
            member.spouse = elements[2];         
            return;
         }
      
         if(elements[1].equals("FAMC")){
            member.child = elements[2]; 
            return;
         }
      
         if(elements[1].equals("BIRT") || elements[1].equals("DEAT")){
            dateIndicator[0] = elements[1];
         }      		
      }else{
         if(dateIndicator[0].equals("BIRT")){
            String birthDate = elements[2]+"-"+elements[3]+"-"+elements[4];
            member.birt = birthDate;
            member.death = "N/A";
            member.age = "10";
            member.alive = true;
         }else{		
            member.alive = false;
            String DeathDate = elements[2]+"-"+elements[3]+"-"+elements[4];
            member.death = DeathDate;
         }
         dateIndicator[0] = "";
      }   
   }
   
  

   String[][] IterateMap(){
      String[][] tableDataIndividual = new String[idToIndividual.size()][9];
      int level = 0;
      for (Map.Entry<String,Individual> entry : idToIndividual.entrySet()){  
         Individual member = entry.getValue();
         tableDataIndividual[level][0] = member.id();
         tableDataIndividual[level][1] = member.name();
         tableDataIndividual[level][2] = member.gender();
         tableDataIndividual[level][3] = member.birt();
         tableDataIndividual[level][4] = member.age();
         tableDataIndividual[level][5] = String.valueOf(member.alive());
         tableDataIndividual[level][6] = member.death();
         tableDataIndividual[level][7] = (member.child() == null) ? "N/A" : member.child();
         tableDataIndividual[level][8] = (member.spouse() == null) ? "N/A" : member.spouse();
         level++;        
      }
      return tableDataIndividual;
   }

   JFrame f;
   JTable j;
   void draw(String type){
      f = new JFrame();
      f.setTitle("Individual");
      IterateMap();
      String[][] tableDataIndividual = IterateMap();
      String[] columnNames = { "ID", "Name", "Gender", "Birthday", "Age", "Alive",
         "Death", "Child", "Spouse"}; 
      j = new JTable(tableDataIndividual, columnNames); 
      j.setBounds(30, 40, 200, 300); 
      JScrollPane sp = new JScrollPane(j); 
      f.add(sp); 
        // Frame Size 
      f.setSize(500, 200); 
        // Frame Visible = true 
      f.setVisible(true);
   }

   

   public static void main(String[] args) throws IOException {
      new Classify().readFile();
      
   
   }


}
