package com.digdes.school;
//Клабуков Святослав

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {

        JavaSchoolStarter starter = new JavaSchoolStarter();
        Scanner in = new Scanner(System.in);
        boolean exit = true;
        while (exit) {
            System.out.println("Enter a request:");
            System.out.println("For example: INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=40, 'active'=true");
            System.out.println("UPDATE VALUES 'active'=true where 'active'=false");
            System.out.println("SELECT WHERE 'age'>=30 and 'lastName' ilike '%п%'");
            System.out.println("DELETE WHERE 'id'=3");
            System.out.println("Press 1 to exit");
            String s;
            s = in.nextLine();
            if (s.equals("1")){
                break;
            }
            List<Map<String, Object>> result1 = starter.execute(s);
        }

//        String request1 = "INSERT VALUES  'id' =  1 , 'cost'= 7.1, 'age'=5, 'aCTive'=  false";
//        String request2 = "INSERT VALUES 'lastName' = 'Петров' ,  'age5'=30, 'age'=55, 'cost'= 5.4, 'id' =  3 ";
//        String request3 = "insert VALUES 'lastName' = 'Иванов' , 'id'=2, 'age'=35, 'cost'= 4.3, 'active'= false";
//        String request4 = "insert VALUES 'lastName' = 'Сидоров' , 'id'=4, 'age'=25, 'cost'= 4.3, 'active'= false";
//        String request5 = "insert VALUES 'lastName' =  , 'id'= , 'age'= , 'cost'= , 'active'= ";
//        String request6 = "insert VALUE 'lastName' =  , 'id'=7 , 'age'=31 , 'cost'= , 'active'= ";
//        String request7 = "insert  'lastName' =  , 'id'=7 , 'age'=31 , 'cost'= , 'active'= ";
//        String request8 = "insert   VALUEs 'id'=null , 'LASTName' =  null,  'active'= true";
//        String request9 = "INSER VALUEs  ";
//        String request10 = "UPDATE VALUES 'ID'= null, 'cost'=null, 'lastName' = null, 'age'=null, 'ACtiVe' = null wHere 'active' >= false And 'iD'>2 aND 'LASTName' ilike 'Федоров' aND 'LastName' LIke 'Петров'" ;
//        String request11 = "UPDATE VALUES 'iD9' =  9, 'active'=false, 'age' =57, 'cost'=10.1, 'lastName' = 'Викторов' where 'id'=3";
//        String request12 = "UPDATE VALUES 'iD' =  9, 'age' =57 where 'id' >2 and 'cost' >= 7.1 and 'lastName'<=false " ;
//        String request13 = "UPDATE VALUES 'iD' =  9, 'age' =57 where 'age' >10  " ;
//        String request14 = "UPDATE VALUES 'iD' =  9, 'age' =57 where 'cost' <=7 or 'lastName'='Петров' " ;
//        String request15 = "SELECt WHEre 'iD' =  3 AnD 'age' >=50 " ;
//        String request16 = "Delete WHEre 'iD' =  3 or 'age' <30 " ;
//        String request17 = "select" ;
//        String request18 = "update values 'id' =10, 'age' = 20 " ;
//
//        List<Map<String,Object>> result1 = starter.execute(request1);
//        List<Map<String,Object>> result2 = starter.execute(request2);
//        List<Map<String,Object>> result3 = starter.execute(request3);
//        List<Map<String,Object>> result4 = starter.execute(request4);
//        List<Map<String,Object>> result5 = starter.execute(request5);
//        List<Map<String,Object>> result6 = starter.execute(request6);
//        List<Map<String,Object>> result7 = starter.execute(request7);
//        List<Map<String,Object>> result8 = starter.execute(request8);
//        List<Map<String,Object>> result9 = starter.execute(request9);
//        List<Map<String,Object>> result10 = starter.execute(request10);
//        List<Map<String,Object>> result11 = starter.execute(request11);
//        List<Map<String,Object>> result12 = starter.execute(request12);
//        List<Map<String,Object>> result13 = starter.execute(request13);
//        List<Map<String,Object>> result14 = starter.execute(request14);
//        List<Map<String,Object>> result15 = starter.execute(request15);
//        List<Map<String,Object>> result16 = starter.execute(request16);
//        List<Map<String,Object>> result17 = starter.execute(request17);
//        List<Map<String,Object>> result18 = starter.execute(request18);



    }
}