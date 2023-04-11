package com.digdes.school;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaSchoolStarter {

    static List<Map<String, Object>> data = new ArrayList<>();

    public JavaSchoolStarter() {

    }

    public List<Map<String, Object>> execute(String request) {
        //Здесь начало исполнения вашего кода
        System.out.println("===============================================================");
        System.out.println(request);
        request = request.trim();
        int indexCommand = request.indexOf(" ");
        String command;
        if (indexCommand == -1) {
            command = request.toUpperCase();
        } else {
            command = request.substring(0, indexCommand).toUpperCase();
        }

        switch (command) {
            case "INSERT" -> {
                String checkValues = request.toUpperCase();
                int indexValues = checkValues.indexOf("VALUES");
                try {
                    if (indexValues == -1) {
                        throw new Exception("Must be command VALUES");
                    } else indexValues += 6;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    System.out.println("===============================================================");
                    return null;
                }

                String text = request.substring(indexValues + 1);
                String[] words = text.split(",");

                User user = new User();
                Map<String, Object> row1 = new HashMap<>();
                boolean emptyRow = true;

                for (String word : words) {
                    int indexStart = word.indexOf("'");
                    int indexEnd = word.indexOf("'", indexStart + 1);
                    String nameColumn = word.substring(indexStart + 1, indexEnd).toLowerCase();

                    int indexEqual = word.indexOf("=");
                    String columnValue = word.substring(indexEqual + 1).trim();


                    String value = "";
                    try {
                        if (columnValue.length() == 0) {
                            throw new Exception("Column value \'" + nameColumn + "\'" + " is empty");
                        }
                        char firstChar = columnValue.charAt(0);
                        if (firstChar == '\'') {
                            int start = columnValue.indexOf("'");
                            int end = columnValue.indexOf("'", start + 1);
                            value = columnValue.substring(start + 1, end);
                        } else {
                            value = columnValue;
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        System.out.println("===============================================================");
                        return null;
                    }


                    try {
                        switch (nameColumn) {
                            case "id" -> {
                                try {
                                    user.setId(Long.parseLong(value));
                                    row1.put("id", user.getId());
                                    emptyRow = false;
                                } catch (NumberFormatException nfe) {
                                    System.out.println("NumberFormatException " + nfe.getMessage());
                                }
                            }
                            case "lastname" -> {
                                try {
                                    if (value.equals("null")) {
                                        user.setLastName(value);
                                        row1.put("lastName", user.getLastName());
                                    } else {
                                        user.setLastName(value);
                                        row1.put("lastName", user.getLastName());
                                        emptyRow = false;
                                    }
                                } catch (NumberFormatException nfe) {
                                    System.out.println("NumberFormatException " + nfe.getMessage());
                                }
                            }
                            case "age" -> {
                                try {
                                    user.setAge(Long.parseLong(value));
                                    row1.put("age", user.getAge());
                                    emptyRow = false;
                                } catch (NumberFormatException nfe) {
                                    System.out.println("NumberFormatException " + nfe.getMessage());
                                }
                            }
                            case "cost" -> {
                                try {
                                    user.setCost(Double.parseDouble(value));
                                    row1.put("cost", user.getCost());
                                    emptyRow = false;
                                } catch (NumberFormatException nfe) {
                                    System.out.println("NumberFormatException " + nfe.getMessage());
                                }
                            }
                            case "active" -> {
                                try {
                                    if (value.equals("null")) {
                                        user.setActive(null);
                                        row1.put("active", null);
                                    } else {
                                        user.setActive(Boolean.parseBoolean(value));
                                        row1.put("active", user.getActive());
                                        emptyRow = false;
                                    }
                                } catch (NumberFormatException nfe) {
                                    System.out.println("NumberFormatException " + nfe.getMessage());
                                }
                            }
                            default ->
                                    throw new Exception("\'" + nameColumn + "\'" + " is no such column in the table");
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                try {
                    if (emptyRow) {
                        throw new Exception("The row must not be empty");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    System.out.println("===============================================================");
                    return null;
                }

                data.add(row1);
                System.out.printf("%-5s %-15s %-15s %-15s %-15s", "id", "lastName", "age", "cost", "active");
                System.out.println();
                //ArrayList<Map<String, Object>> newList = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    //newList.add(data.get(i));
                    System.out.printf("%-5s %-15s %-15s %-15s %-15s", data.get(i).get("id"), data.get(i).get("lastName"), data.get(i).get("age"), data.get(i).get("cost"), data.get(i).get("active"));
                    System.out.println();
                }
                System.out.println("===============================================================");
                return data;
                //return newList;
            }
            case "UPDATE" -> {
                String checkValues = request.toUpperCase();
                int indexValues = checkValues.indexOf("VALUES");
                try {
                    if (indexValues == -1) {
                        throw new Exception("Must be command VALUES");
                    } else indexValues += 6;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    System.out.println("===============================================================");
                    return null;
                }

                //найти позицию where
                //String where = request.toUpperCase();
                int indexWhere = checkValues.indexOf("WHERE");
                String text;
                if (indexWhere == -1) {
                    text = request.substring(indexValues + 1);
                } else {
                    text = request.substring(indexValues + 1, indexWhere - 1);
                }

                //String text = request.substring(indexValues + 1, indexWhere - 1);
                String[] words = text.split(",");

                User user = new User();
                Map<String, Object> row1 = new HashMap<>();
                boolean emptyRow = true;

                for (String word : words) {
                    int indexStart = word.indexOf("'");
                    int indexEnd = word.indexOf("'", indexStart + 1);
                    String nameColumn = word.substring(indexStart + 1, indexEnd).toLowerCase();

                    int indexEqual = word.indexOf("=");
                    String columnValue = word.substring(indexEqual + 1).trim();

                    String value = "";
                    try {
                        if (columnValue.length() == 0) {
                            throw new Exception("Column value \'" + nameColumn + "\'" + " is empty");
                        }
                        char firstChar = columnValue.charAt(0);
                        //System.out.println("First char: "+ firstChar);
                        //String value = "";
                        if (firstChar == '\'') {
                            int start = columnValue.indexOf("'");
                            int end = columnValue.indexOf("'", start + 1);
                            value = columnValue.substring(start + 1, end);
                            //System.out.println("Column value: "+ columnValue);
                        } else {
                            value = columnValue;
                            //System.out.println("Column value: "+ columnValue);
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        //value = null;
                        System.out.println("===============================================================");
                        return null;
                    }

                    try {
                        switch (nameColumn) {
                            case "id" -> {
                                try {
                                    if (value.equals("null")) {
                                        user.setId(null);
                                        user.setUpdateId(true);
                                    } else {
                                        user.setId(Long.parseLong(value));
                                        //row1.put("id", user.getId());
                                        emptyRow = false;
                                    }
                                } catch (NumberFormatException nfe) {
                                    System.out.println("NumberFormatException " + nfe.getMessage());
                                }
                            }
                            case "lastname" -> {
                                try {
                                    if (value.equals("null")) {
                                        user.setLastName(value);
                                        user.setUpdateLastName(true);
                                        //row1.put("lastName", user.getLastName());
                                    } else {
                                        user.setLastName(value);
                                        //row1.put("lastName", user.getLastName());
                                        emptyRow = false;
                                    }
                                } catch (NumberFormatException nfe) {
                                    System.out.println("NumberFormatException " + nfe.getMessage());
                                }
                            }
                            case "age" -> {
                                try {
                                    if (value.equals("null")) {
                                        user.setAge(null);
                                        user.setUpdateAge(true);
                                    } else {
                                        user.setAge(Long.parseLong(value));
                                        //row1.put("age", user.getAge());
                                        emptyRow = false;
                                    }
                                } catch (NumberFormatException nfe) {
                                    System.out.println("NumberFormatException " + nfe.getMessage());
                                }
                            }
                            case "cost" -> {
                                try {
                                    if (value.equals("null")) {
                                        user.setCost(null);
                                        user.setUpdateCost(true);
                                    } else {
                                        user.setCost(Double.parseDouble(value));
                                        //row1.put("cost", user.getCost());
                                        emptyRow = false;
                                    }
                                } catch (NumberFormatException nfe) {
                                    System.out.println("NumberFormatException " + nfe.getMessage());
                                }
                            }
                            case "active" -> {
                                try {
                                    if (value.equals("null")) {
                                        user.setActive(null);
                                        user.setUpdateActive(true);
                                        //row1.put("active", null);
                                    } else {
                                        user.setActive(Boolean.parseBoolean(value));
                                        row1.put("active", user.getActive());
                                        emptyRow = false;
                                    }
                                } catch (NumberFormatException nfe) {
                                    System.out.println("NumberFormatException " + nfe.getMessage());
                                }
                            }
                            default -> throw new Exception("'" + nameColumn + "'" + " is not such column in the table");
                            //default -> System.out.println(nameColumn + " is no such column in the table");
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                if (indexWhere == -1) {
                    System.out.println("All rows have been updated:");
                    System.out.printf("%-5s %-15s %-15s %-15s %-15s", "id", "lastName", "age", "cost", "active");
                    System.out.println();
                    for (int i = 0; i < data.size(); i++) {
                        if (!(user.getId() == null) || user.getUpdateId()) {
                            data.get(i).put("id", user.getId());
                        }
                        if (!(user.getLastName() == null) || user.getUpdateLastName()) {
                            data.get(i).put("lastName", user.getLastName());
                        }
                        if (!(user.getCost() == null) || user.getUpdateCost()) {
                            data.get(i).put("cost", user.getCost());
                        }
                        if (!(user.getAge() == null) || user.getUpdateAge()) {
                            data.get(i).put("age", user.getAge());
                        }
                        if (!(user.getActive() == null) || user.getUpdateActive()) {
                            data.get(i).put("active", user.getActive());
                        }
                        System.out.printf("%-5s %-15s %-15s %-15s %-15s", data.get(i).get("id"), data.get(i).get("lastName"), data.get(i).get("age"), data.get(i).get("cost"), data.get(i).get("active"));
                        System.out.println();
                    }
                    System.out.println("===============================================================");
                    return data;
                }

                //обработка запроса после where
                String textAfterWhere = request.substring(indexWhere + 5).trim();
                Pattern pAnd = Pattern.compile("(?i).*and.*");
                Matcher mAnd = pAnd.matcher(textAfterWhere);
                boolean findAnd = mAnd.matches();
                Pattern pOr = Pattern.compile("(?i).*or.*");
                Matcher mOr = pOr.matcher(textAfterWhere);
                boolean findOr = mOr.matches();
                try {
                    if (findAnd && findOr) {
                        throw new Exception("can be only AND or only OR");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    return null;
                }


                if (findAnd) {
                    String[] wordsAfterWhere;
                    wordsAfterWhere = textAfterWhere.split("(?i)and");
                    List<Map<String, Object>> tmpData = new ArrayList<>(data);
                    for (String s : wordsAfterWhere) {
                        String compareOperator = getCompareOperator(s);
                        String[] columnValue = s.split(compareOperator);
                        String nameColumn = getNameColumn(columnValue[0]);
                        try {
                            if (nameColumn.equals("-1")) {
                                throw new Exception("Incorrect column name");
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        String str = columnValue[1].trim();
                        String value = getColumnValue(str);
                        if (nameColumn.equalsIgnoreCase("lastName")) {
                            nameColumn = "lastName";
                        }
                        try {
                            if (value.equals("-1")) {
                                throw new Exception("Column value '" + nameColumn + "'" + " is empty");
                            }
                            switch (nameColumn) {
                                case "id", "age" -> {
                                    long val = Long.parseLong(value);
                                }
                                case "cost" -> {
                                    double val = Double.parseDouble(value);
                                }
                                case "lastName" -> {
                                    boolean b = isNumeric(value);
                                    if (b) {
                                        throw new Exception("The value cannot be a number");
                                    }
                                }
                                case "active" -> {
                                    boolean val = Boolean.parseBoolean(value);
                                }
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        compareOperator = compareOperator.toLowerCase().trim();

                        try {
                            switch (compareOperator) {
                                case "=" -> {
                                    for (int i = 0; i < tmpData.size(); ) {
                                        boolean check = tmpData.get(i).get(nameColumn) == null;
                                        if (check || !(tmpData.get(i).get(nameColumn).toString().equals(value))) {//нахождение строки,  НЕудовлетворяющей сравнению =
                                            tmpData.remove(tmpData.get(i));
                                        } else {
                                            i++;
                                        }
                                    }
                                }
                                case "!=" -> {
                                    for (int i = 0; i < tmpData.size(); ) {
                                        boolean check = tmpData.get(i).get(nameColumn) == null;
                                        if (check || tmpData.get(i).get(nameColumn).toString().equals(value)) {//нахождение строки,  НЕудовлетворяющей сравнению !=
                                            tmpData.remove(tmpData.get(i));
                                        } else {
                                            i++;
                                        }
                                    }
                                }
                                case ">" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case ">=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "like" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'like' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1);
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                int index = tmpData.get(i).get(nameColumn).toString().indexOf(newValue);
                                                if (index == -1) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    i++;
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1);
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                boolean b = tmpData.get(i).get(nameColumn).toString().endsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1);
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                boolean b = tmpData.get(i).get(nameColumn).toString().startsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                boolean b = tmpData.get(i).get(nameColumn).toString().equals(value);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    }
                                }
                                case "ilike" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'ilike' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1).toLowerCase();
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString().toLowerCase());
                                                int index = newStr.indexOf(newValue);
                                                if (index == -1) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    i++;
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1).toLowerCase();
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.endsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1).toLowerCase();
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.startsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString());
                                                boolean b = newStr.equalsIgnoreCase(value);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    }
                                }
                                default -> throw new Exception("'" + compareOperator + "'" + " is not such operator");
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                    }
                    System.out.printf("%-5s %-15s %-15s %-15s %-15s", "id", "lastName", "age", "cost", "active");
                    System.out.println();
                    for (int i = 0; i < data.size(); i++) {
                        boolean isEqual = false;
                        for (int j = 0; j < tmpData.size(); j++) {
                            isEqual = compareRows(data.get(i), tmpData.get(j));
                            if (isEqual) {
                                if (!(user.getId() == null) || user.getUpdateId()) {
                                    data.get(i).put("id", user.getId());
                                }
                                if (!(user.getLastName() == null) || user.getUpdateLastName()) {
                                    data.get(i).put("lastName", user.getLastName());
                                }
                                if (!(user.getCost() == null) || user.getUpdateCost()) {
                                    data.get(i).put("cost", user.getCost());
                                }
                                if (!(user.getAge() == null) || user.getUpdateAge()) {
                                    data.get(i).put("age", user.getAge());
                                }
                                if (!(user.getActive() == null) || user.getUpdateActive()) {
                                    data.get(i).put("active", user.getActive());
                                }
                                System.out.printf("%-5s %-15s %-15s %-15s %-15s", data.get(i).get("id"), data.get(i).get("lastName"), data.get(i).get("age"), data.get(i).get("cost"), data.get(i).get("active"));
                                System.out.println();
                            }
                        }
                    }
                    System.out.println("===============================================================");
                    return tmpData;
                }
                if (findOr || !findAnd) {
                    String[] wordsAfterWhere;
                    wordsAfterWhere = textAfterWhere.split("(?i)or");
                    List<Map<String, Object>> tmpData = new ArrayList<>();
                    for (String s : wordsAfterWhere) {
                        String compareOperator = getCompareOperator(s);
                        String[] columnValue = s.split(compareOperator);
                        String nameColumn = getNameColumn(columnValue[0]);
                        try {
                            if (nameColumn.equals("-1")) {
                                throw new Exception("Incorrect column name");
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        String str = columnValue[1].trim();
                        String value = getColumnValue(str);
                        if (nameColumn.equalsIgnoreCase("lastName")) {
                            nameColumn = "lastName";
                        }
                        try {
                            if (value.equals("-1")) {
                                throw new Exception("Column value '" + nameColumn + "'" + " is empty");
                            }
                            switch (nameColumn) {
                                case "id", "age" -> {
                                    long val = Long.parseLong(value);
                                }
                                case "cost" -> {
                                    double val = Double.parseDouble(value);
                                }
                                case "lastName" -> {
                                    boolean b = isNumeric(value);
                                    if (b) {
                                        throw new Exception("The value cannot be a number");
                                    }
                                }
                                case "active" -> {
                                    boolean val = Boolean.parseBoolean(value);
                                }
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        compareOperator = compareOperator.toLowerCase().trim();

                        try {
                            switch (compareOperator) {
                                case "=" -> {
                                    for (int i = 0; i < data.size(); i++) {
                                        boolean check = data.get(i).get(nameColumn) == null;
                                        if (!check && data.get(i).get(nameColumn).toString().equals(value)) {//нахождение строки, удовлетворяющей сравнению =
                                            boolean isExist = true;
                                            for (int j = 0; j < tmpData.size(); j++) {
                                                if (compareRows(data.get(i), tmpData.get(j))) {
                                                    isExist = false;
                                                }
                                            }
                                            if (isExist) {
                                                tmpData.add(data.get(i));
                                            }
                                        }
                                    }
                                }
                                case "!=" -> {
                                    for (int i = 0; i < data.size(); i++) {
                                        boolean check = data.get(i).get(nameColumn) == null;
                                        if (!check && !(data.get(i).get(nameColumn).toString().equals(value))) {//нахождение строки, НЕудовлетворяющей сравнению =
                                            boolean isExist = true;
                                            for (int j = 0; j < tmpData.size(); j++) {
                                                if (compareRows(data.get(i), tmpData.get(j))) {
                                                    isExist = false;
                                                }
                                            }
                                            if (isExist) {
                                                tmpData.add(data.get(i));
                                            }
                                        }
                                    }
                                }
                                case ">" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case ">=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "like" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'like' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1);
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                int index = data.get(i).get(nameColumn).toString().indexOf(newValue);
                                                if (index != -1) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1);
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                boolean b = data.get(i).get(nameColumn).toString().endsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1);
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                boolean b = data.get(i).get(nameColumn).toString().startsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                boolean b = data.get(i).get(nameColumn).toString().equals(value);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "ilike" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'like' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1).toLowerCase();
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString().toLowerCase());
                                                int index = newStr.indexOf(newValue);
                                                if (index != -1) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1).toLowerCase();
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.endsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1).toLowerCase();
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.startsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString());
                                                boolean b = newStr.equalsIgnoreCase(value);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                default -> throw new Exception("'" + compareOperator + "'" + " is not such operator");
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                    }

                    System.out.printf("%-5s %-15s %-15s %-15s %-15s", "id", "lastName", "age", "cost", "active");
                    System.out.println();
                    for (int i = 0; i < data.size(); i++) {
                        boolean isEqual = false;
                        for (int j = 0; j < tmpData.size(); j++) {
                            isEqual = compareRows(data.get(i), tmpData.get(j));
                            if (isEqual) {
                                if (!(user.getId() == null) || user.getUpdateId()) {
                                    data.get(i).put("id", user.getId());
                                }
                                if (!(user.getLastName() == null) || user.getUpdateLastName()) {
                                    data.get(i).put("lastName", user.getLastName());
                                }
                                if (!(user.getCost() == null) || user.getUpdateCost()) {
                                    data.get(i).put("cost", user.getCost());
                                }
                                if (!(user.getAge() == null) || user.getUpdateAge()) {
                                    data.get(i).put("age", user.getAge());
                                }
                                if (!(user.getActive() == null) || user.getUpdateActive()) {
                                    data.get(i).put("active", user.getActive());
                                }
                                System.out.printf("%-5s %-15s %-15s %-15s %-15s", data.get(i).get("id"), data.get(i).get("lastName"), data.get(i).get("age"), data.get(i).get("cost"), data.get(i).get("active"));
                                System.out.println();
                            }
                        }
                    }
                    System.out.println("===============================================================");
                    return tmpData;
                }

                System.out.println("===============================================================");
                return data;
            }
            case "SELECT" -> {
                String where = request.toUpperCase();
                int indexWhere = where.indexOf("WHERE");

                if (indexWhere == -1) {
                    System.out.println("All rows have been selected");
                    System.out.printf("%-5s %-15s %-15s %-15s %-15s", "id", "lastName", "age", "cost", "active");
                    System.out.println();
                    for (int j = 0; j < data.size(); j++) {
                        System.out.printf("%-5s %-15s %-15s %-15s %-15s", data.get(j).get("id"), data.get(j).get("lastName"), data.get(j).get("age"), data.get(j).get("cost"), data.get(j).get("active"));
                        System.out.println();
                    }
                    System.out.println("===============================================================");
                    return data;
                }

                //обработка запроса после where
                String textAfterWhere = request.substring(indexWhere + 5).trim();
                Pattern pAnd = Pattern.compile("(?i).*and.*");
                Matcher mAnd = pAnd.matcher(textAfterWhere);
                boolean findAnd = mAnd.matches();
                Pattern pOr = Pattern.compile("(?i).*or.*");
                Matcher mOr = pOr.matcher(textAfterWhere);
                boolean findOr = mOr.matches();
                try {
                    if (findAnd && findOr) {
                        throw new Exception("can be only AND or only OR");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    return null;
                }


                if (findAnd) {
                    String[] wordsAfterWhere;
                    wordsAfterWhere = textAfterWhere.split("(?i)and");
                    List<Map<String, Object>> tmpData = new ArrayList<>(data);
                    for (String s : wordsAfterWhere) {
                        String compareOperator = getCompareOperator(s);
                        String[] columnValue = s.split(compareOperator);
                        String nameColumn = getNameColumn(columnValue[0]);
                        try {
                            if (nameColumn.equals("-1")) {
                                throw new Exception("Incorrect column name");
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        String str = columnValue[1].trim();
                        String value = getColumnValue(str);
                        if (nameColumn.equalsIgnoreCase("lastName")) {
                            nameColumn = "lastName";
                        }
                        try {
                            if (value.equals("-1")) {
                                throw new Exception("Column value '" + nameColumn + "'" + " is empty");
                            }
                            switch (nameColumn) {
                                case "id", "age" -> {
                                    long val = Long.parseLong(value);
                                }
                                case "cost" -> {
                                    double val = Double.parseDouble(value);
                                }
                                case "lastName" -> {
                                    boolean b = isNumeric(value);
                                    if (b) {
                                        throw new Exception("The value cannot be a number");
                                    }
                                }
                                case "active" -> {
                                    boolean val = Boolean.parseBoolean(value);
                                }
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        compareOperator = compareOperator.toLowerCase().trim();

                        try {
                            switch (compareOperator) {
                                case "=" -> {
                                    for (int i = 0; i < tmpData.size(); ) {
                                        boolean check = tmpData.get(i).get(nameColumn) == null;
                                        if (check || !(tmpData.get(i).get(nameColumn).toString().equals(value))) {//нахождение строки,  НЕудовлетворяющей сравнению =
                                            tmpData.remove(tmpData.get(i));
                                        } else {
                                            i++;
                                        }
                                    }
                                }
                                case "!=" -> {
                                    for (int i = 0; i < tmpData.size(); ) {
                                        boolean check = tmpData.get(i).get(nameColumn) == null;
                                        if (check || tmpData.get(i).get(nameColumn).toString().equals(value)) {//нахождение строки,  НЕудовлетворяющей сравнению !=
                                            tmpData.remove(tmpData.get(i));
                                        } else {
                                            i++;
                                        }
                                    }
                                }
                                case ">" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case ">=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "like" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'like' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1);
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                int index = tmpData.get(i).get(nameColumn).toString().indexOf(newValue);
                                                if (index == -1) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    i++;
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1);
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                boolean b = tmpData.get(i).get(nameColumn).toString().endsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1);
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                boolean b = tmpData.get(i).get(nameColumn).toString().startsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                boolean b = tmpData.get(i).get(nameColumn).toString().equals(value);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    }
                                }
                                case "ilike" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'ilike' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1).toLowerCase();
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString().toLowerCase());
                                                int index = newStr.indexOf(newValue);
                                                if (index == -1) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    i++;
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1).toLowerCase();
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.endsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1).toLowerCase();
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.startsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString());
                                                boolean b = newStr.equalsIgnoreCase(value);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    }
                                }
                                default -> throw new Exception("'" + compareOperator + "'" + " is not such operator");
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                    }
                    System.out.printf("%-5s %-15s %-15s %-15s %-15s", "id", "lastName", "age", "cost", "active");
                    System.out.println();
                    for (int j = 0; j < tmpData.size(); j++) {
                        System.out.printf("%-5s %-15s %-15s %-15s %-15s", tmpData.get(j).get("id"), tmpData.get(j).get("lastName"), tmpData.get(j).get("age"), tmpData.get(j).get("cost"), tmpData.get(j).get("active"));
                        System.out.println();
                    }
                    System.out.println("===============================================================");
                    return tmpData;
                }
                if (findOr || !findAnd) {
                    String[] wordsAfterWhere;
                    wordsAfterWhere = textAfterWhere.split("(?i)or");
                    List<Map<String, Object>> tmpData = new ArrayList<>();
                    for (String s : wordsAfterWhere) {
                        String compareOperator = getCompareOperator(s);
                        String[] columnValue = s.split(compareOperator);
                        String nameColumn = getNameColumn(columnValue[0]);
                        try {
                            if (nameColumn.equals("-1")) {
                                throw new Exception("Incorrect column name");
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        String str = columnValue[1].trim();
                        String value = getColumnValue(str);
                        if (nameColumn.equalsIgnoreCase("lastName")) {
                            nameColumn = "lastName";
                        }
                        try {
                            if (value.equals("-1")) {
                                throw new Exception("Column value '" + nameColumn + "'" + " is empty");
                            }
                            switch (nameColumn) {
                                case "id", "age" -> {
                                    long val = Long.parseLong(value);
                                }
                                case "cost" -> {
                                    double val = Double.parseDouble(value);
                                }
                                case "lastName" -> {
                                    boolean b = isNumeric(value);
                                    if (b) {
                                        throw new Exception("The value cannot be a number");
                                    }
                                }
                                case "active" -> {
                                    boolean val = Boolean.parseBoolean(value);
                                }
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        compareOperator = compareOperator.toLowerCase().trim();

                        try {
                            switch (compareOperator) {
                                case "=" -> {
                                    for (int i = 0; i < data.size(); i++) {
                                        boolean check = data.get(i).get(nameColumn) == null;
                                        if (!check && data.get(i).get(nameColumn).toString().equals(value)) {//нахождение строки, удовлетворяющей сравнению =
                                            boolean isExist = true;
                                            for (int j = 0; j < tmpData.size(); j++) {
                                                if (compareRows(data.get(i), tmpData.get(j))) {
                                                    isExist = false;
                                                }
                                            }
                                            if (isExist) {
                                                tmpData.add(data.get(i));
                                            }
                                        }
                                    }
                                }
                                case "!=" -> {
                                    for (int i = 0; i < data.size(); i++) {
                                        boolean check = data.get(i).get(nameColumn) == null;
                                        if (!check && !(data.get(i).get(nameColumn).toString().equals(value))) {//нахождение строки, НЕудовлетворяющей сравнению =
                                            boolean isExist = true;
                                            for (int j = 0; j < tmpData.size(); j++) {
                                                if (compareRows(data.get(i), tmpData.get(j))) {
                                                    isExist = false;
                                                }
                                            }
                                            if (isExist) {
                                                tmpData.add(data.get(i));
                                            }
                                        }
                                    }
                                }
                                case ">" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case ">=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "like" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'like' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1);
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                int index = data.get(i).get(nameColumn).toString().indexOf(newValue);
                                                if (index != -1) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1);
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                boolean b = data.get(i).get(nameColumn).toString().endsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1);
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                boolean b = data.get(i).get(nameColumn).toString().startsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                boolean b = data.get(i).get(nameColumn).toString().equals(value);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "ilike" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'like' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1).toLowerCase();
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString().toLowerCase());
                                                int index = newStr.indexOf(newValue);
                                                if (index != -1) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1).toLowerCase();
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.endsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1).toLowerCase();
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.startsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString());
                                                boolean b = newStr.equalsIgnoreCase(value);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                default -> throw new Exception("'" + compareOperator + "'" + " is not such operator");
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                    }

                    System.out.printf("%-5s %-15s %-15s %-15s %-15s", "id", "lastName", "age", "cost", "active");
                    System.out.println();
                    for (int j = 0; j < tmpData.size(); j++) {
                        System.out.printf("%-5s %-15s %-15s %-15s %-15s", tmpData.get(j).get("id"), tmpData.get(j).get("lastName"), tmpData.get(j).get("age"), tmpData.get(j).get("cost"), tmpData.get(j).get("active"));
                        System.out.println();
                    }
                    System.out.println("===============================================================");
                    return tmpData;
                }

                System.out.println("===============================================================");
                return data;
            }
            case "DELETE" -> {
                String where = request.toUpperCase();
                int indexWhere = where.indexOf("WHERE");

                if (indexWhere == -1) {
                    System.out.println("All rows have been deleted:");
                    for (int j = 0; j < data.size(); ) {
                        data.remove(data.get(j));
                    }
                    System.out.println("===============================================================");
                    return data;
                }

                //обработка запроса после where
                String textAfterWhere = request.substring(indexWhere + 5).trim();
                Pattern pAnd = Pattern.compile("(?i).*and.*");
                Matcher mAnd = pAnd.matcher(textAfterWhere);
                boolean findAnd = mAnd.matches();
                Pattern pOr = Pattern.compile("(?i).*or.*");
                Matcher mOr = pOr.matcher(textAfterWhere);
                boolean findOr = mOr.matches();
                try {
                    if (findAnd && findOr) {
                        throw new Exception("can be only AND or only OR");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    return null;
                }


                if (findAnd) {
                    String[] wordsAfterWhere;
                    wordsAfterWhere = textAfterWhere.split("(?i)and");
                    List<Map<String, Object>> tmpData = new ArrayList<>(data);
                    for (String s : wordsAfterWhere) {
                        String compareOperator = getCompareOperator(s);
                        String[] columnValue = s.split(compareOperator);
                        String nameColumn = getNameColumn(columnValue[0]);
                        try {
                            if (nameColumn.equals("-1")) {
                                throw new Exception("Incorrect column name");
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        String str = columnValue[1].trim();
                        String value = getColumnValue(str);
                        if (nameColumn.equalsIgnoreCase("lastName")) {
                            nameColumn = "lastName";
                        }
                        try {
                            if (value.equals("-1")) {
                                throw new Exception("Column value '" + nameColumn + "'" + " is empty");
                            }
                            switch (nameColumn) {
                                case "id", "age" -> {
                                    long val = Long.parseLong(value);
                                }
                                case "cost" -> {
                                    double val = Double.parseDouble(value);
                                }
                                case "lastName" -> {
                                    boolean b = isNumeric(value);
                                    if (b) {
                                        throw new Exception("The value cannot be a number");
                                    }
                                }
                                case "active" -> {
                                    boolean val = Boolean.parseBoolean(value);
                                }
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        compareOperator = compareOperator.toLowerCase().trim();

                        try {
                            switch (compareOperator) {
                                case "=" -> {
                                    for (int i = 0; i < tmpData.size(); ) {
                                        boolean check = tmpData.get(i).get(nameColumn) == null;
                                        if (check || !(tmpData.get(i).get(nameColumn).toString().equals(value))) {//нахождение строки,  НЕудовлетворяющей сравнению =
                                            tmpData.remove(tmpData.get(i));
                                        } else {
                                            i++;
                                        }
                                    }
                                }
                                case "!=" -> {
                                    for (int i = 0; i < tmpData.size(); ) {
                                        boolean check = tmpData.get(i).get(nameColumn) == null;
                                        if (check || tmpData.get(i).get(nameColumn).toString().equals(value)) {//нахождение строки,  НЕудовлетворяющей сравнению !=
                                            tmpData.remove(tmpData.get(i));
                                        } else {
                                            i++;
                                        }
                                    }
                                }
                                case ">" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case ">=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    long tmp = Long.parseLong(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < tmpData.size(); ) {
                                                boolean check = tmpData.get(i).get(nameColumn) == null;
                                                if (check) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    double tmp = Double.parseDouble(tmpData.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        i++;
                                                    } else {
                                                        tmpData.remove(tmpData.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "like" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'like' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1);
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                int index = tmpData.get(i).get(nameColumn).toString().indexOf(newValue);
                                                if (index == -1) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    i++;
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1);
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                boolean b = tmpData.get(i).get(nameColumn).toString().endsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1);
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                boolean b = tmpData.get(i).get(nameColumn).toString().startsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                boolean b = tmpData.get(i).get(nameColumn).toString().equals(value);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    }
                                }
                                case "ilike" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'ilike' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1).toLowerCase();
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString().toLowerCase());
                                                int index = newStr.indexOf(newValue);
                                                if (index == -1) {
                                                    tmpData.remove(tmpData.get(i));
                                                } else {
                                                    i++;
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1).toLowerCase();
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.endsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1).toLowerCase();
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.startsWith(newValue);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < tmpData.size(); ) {
                                            boolean check = tmpData.get(i).get(nameColumn) == null;
                                            if (check) {
                                                tmpData.remove(tmpData.get(i));
                                            } else {
                                                String newStr = new String(tmpData.get(i).get(nameColumn).toString());
                                                boolean b = newStr.equalsIgnoreCase(value);
                                                if (b) {
                                                    i++;
                                                } else {
                                                    tmpData.remove(tmpData.get(i));
                                                }
                                            }
                                        }
                                    }
                                }
                                default -> throw new Exception("'" + compareOperator + "'" + " is not such operator");
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                    }
                    System.out.println("The following rows have been deleted:");
                    System.out.printf("%-5s %-15s %-15s %-15s %-15s", "id", "lastName", "age", "cost", "active");
                    System.out.println();
                    for (int i = 0; i < tmpData.size(); i++) {
                        boolean isEqual = false;
                        for (int j = 0; j < data.size(); ) {
                            isEqual = compareRows(tmpData.get(i), data.get(j));
                            if (isEqual) {
                                System.out.printf("%-5s %-15s %-15s %-15s %-15s", data.get(j).get("id"), data.get(j).get("lastName"), data.get(j).get("age"), data.get(j).get("cost"), data.get(j).get("active"));
                                System.out.println();
                                data.remove(data.get(j));
                            } else {
                                j++;
                            }
                        }
                    }
                }
                if (findOr || !findAnd) {
                    String[] wordsAfterWhere;
                    wordsAfterWhere = textAfterWhere.split("(?i)or");
                    List<Map<String, Object>> tmpData = new ArrayList<>();
                    for (String s : wordsAfterWhere) {
                        String compareOperator = getCompareOperator(s);
                        String[] columnValue = s.split(compareOperator);
                        String nameColumn = getNameColumn(columnValue[0]);
                        try {
                            if (nameColumn.equals("-1")) {
                                throw new Exception("Incorrect column name");
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        String str = columnValue[1].trim();
                        String value = getColumnValue(str);
                        if (nameColumn.equalsIgnoreCase("lastName")) {
                            nameColumn = "lastName";
                        }
                        try {
                            if (value.equals("-1")) {
                                throw new Exception("Column value '" + nameColumn + "'" + " is empty");
                            }
                            switch (nameColumn) {
                                case "id", "age" -> {
                                    long val = Long.parseLong(value);
                                }
                                case "cost" -> {
                                    double val = Double.parseDouble(value);
                                }
                                case "lastName" -> {
                                    boolean b = isNumeric(value);
                                    if (b) {
                                        throw new Exception("The value cannot be a number");
                                    }
                                }
                                case "active" -> {
                                    boolean val = Boolean.parseBoolean(value);
                                }
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                        compareOperator = compareOperator.toLowerCase().trim();

                        try {
                            switch (compareOperator) {
                                case "=" -> {
                                    for (int i = 0; i < data.size(); i++) {
                                        boolean check = data.get(i).get(nameColumn) == null;
                                        if (!check && data.get(i).get(nameColumn).toString().equals(value)) {//нахождение строки, удовлетворяющей сравнению =
                                            boolean isExist = true;
                                            for (int j = 0; j < tmpData.size(); j++) {
                                                if (compareRows(data.get(i), tmpData.get(j))) {
                                                    isExist = false;
                                                }
                                            }
                                            if (isExist) {
                                                tmpData.add(data.get(i));
                                            }
                                        }
                                    }
                                }
                                case "!=" -> {
                                    for (int i = 0; i < data.size(); i++) {
                                        boolean check = data.get(i).get(nameColumn) == null;
                                        if (!check && !(data.get(i).get(nameColumn).toString().equals(value))) {//нахождение строки, НЕудовлетворяющей сравнению =
                                            boolean isExist = true;
                                            for (int j = 0; j < tmpData.size(); j++) {
                                                if (compareRows(data.get(i), tmpData.get(j))) {
                                                    isExist = false;
                                                }
                                            }
                                            if (isExist) {
                                                tmpData.add(data.get(i));
                                            }
                                        }
                                    }
                                }
                                case ">" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp > val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp < val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case ">=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '>=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp >= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "<=" -> {
                                    if (nameColumn.equals("lastName") || nameColumn.equals("active")) {
                                        throw new Exception(s + " - '<=' is incorrect comparison operator");
                                    }
                                    switch (nameColumn) {
                                        case "id", "age" -> {
                                            long val = Long.parseLong(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    long tmp = Long.parseLong(data.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        case "cost" -> {
                                            double val = Double.parseDouble(value);
                                            for (int i = 0; i < data.size(); i++) {
                                                boolean check = data.get(i).get(nameColumn) == null;
                                                if (!check) {
                                                    double tmp = Double.parseDouble(data.get(i).get(nameColumn).toString());
                                                    if (tmp <= val) {
                                                        boolean isExist = true;
                                                        for (int j = 0; j < tmpData.size(); j++) {
                                                            if (compareRows(data.get(i), tmpData.get(j))) {
                                                                isExist = false;
                                                            }
                                                        }
                                                        if (isExist) {
                                                            tmpData.add(data.get(i));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "like" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'like' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1);
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                int index = data.get(i).get(nameColumn).toString().indexOf(newValue);
                                                if (index != -1) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1);
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                boolean b = data.get(i).get(nameColumn).toString().endsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1);
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                boolean b = data.get(i).get(nameColumn).toString().startsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                boolean b = data.get(i).get(nameColumn).toString().equals(value);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                case "ilike" -> {
                                    if (nameColumn.equals("id") || nameColumn.equals("active") || nameColumn.equals("age") || nameColumn.equals("cost")) {
                                        throw new Exception(s + " - 'like' is incorrect comparison operator");
                                    }
                                    boolean bStart = value.startsWith("%");
                                    boolean bEnd = value.endsWith("%");
                                    if (bStart && bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(1, len - 1).toLowerCase();
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString().toLowerCase());
                                                int index = newStr.indexOf(newValue);
                                                if (index != -1) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bStart) {
                                        String newValue = value.substring(1).toLowerCase();
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.endsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (bEnd) {
                                        int len = value.length();
                                        String newValue = value.substring(0, len - 1).toLowerCase();
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString().toLowerCase());
                                                boolean b = newStr.startsWith(newValue);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < data.size(); i++) {
                                            boolean check = data.get(i).get(nameColumn) == null;
                                            if (!check) {
                                                String newStr = new String(data.get(i).get(nameColumn).toString());
                                                boolean b = newStr.equalsIgnoreCase(value);
                                                if (b) {
                                                    boolean isExist = true;
                                                    for (int j = 0; j < tmpData.size(); j++) {
                                                        if (compareRows(data.get(i), tmpData.get(j))) {
                                                            isExist = false;
                                                        }
                                                    }
                                                    if (isExist) {
                                                        tmpData.add(data.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                default -> throw new Exception("'" + compareOperator + "'" + " is not such operator");
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("NumberFormatException " + nfe.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("===============================================================");
                            return null;
                        }
                    }

                    System.out.println("The following rows have been deleted:");
                    System.out.printf("%-5s %-15s %-15s %-15s %-15s", "id", "lastName", "age", "cost", "active");
                    System.out.println();
                    for (int i = 0; i < tmpData.size(); i++) {
                        boolean isEqual = false;
                        for (int j = 0; j < data.size(); ) {
                            isEqual = compareRows(tmpData.get(i), data.get(j));
                            if (isEqual) {
                                System.out.printf("%-5s %-15s %-15s %-15s %-15s", data.get(j).get("id"), data.get(j).get("lastName"), data.get(j).get("age"), data.get(j).get("cost"), data.get(j).get("active"));
                                System.out.println();
                                data.remove(data.get(j));
                            } else {
                                j++;
                            }
                        }
                    }
                }

                System.out.println("===============================================================");
                return data;
            }
            default -> {
                System.out.println(command + " command does not exist");
                System.out.println("===============================================================");
                return null;
            }
        }
        //return newList;
    }

    //метод получения оператора сравнения из строки типа 'id' >= 9
    public String getCompareOperator(String text) {
        String compareOperator = "";
        Pattern p = Pattern.compile("(?i)[\\w'\\s]+(?<op>[=<>!]*)[\\w'\\s]*");
        Pattern p2 = Pattern.compile("(?i)[\\w'\\s]+(?<op2>\\slike?)[\\w'\\s]*");
        Pattern p3 = Pattern.compile("(?i)[\\w'\\s]+(?<op3>ilike?)[\\w'\\s]*");
        Matcher m = p.matcher(text);
        Matcher m2 = p2.matcher(text);
        Matcher m3 = p3.matcher(text);
        StringBuilder operator = new StringBuilder();
        StringBuilder operator2 = new StringBuilder();
        StringBuilder operator3 = new StringBuilder();
        while (m.find()) {
            operator.append(m.group("op"));
        }
        while (m2.find()) {
            operator2.append(m2.group("op2"));
        }
        while (m3.find()) {
            operator3.append(m3.group("op3"));
        }
        if (operator.length() == 0) {
            operator = operator2.length() == 0 ? operator3 : operator2;
        }
        compareOperator = String.valueOf(operator);
        return compareOperator;
    }

    //считывание названия столбца
    public String getNameColumn(String text) {
        try {
            int indexStart = text.indexOf("'");
            if (indexStart == -1) {
                throw new Exception("' is promised");
            }
            int indexEnd = text.indexOf("'", indexStart + 1);
            if (indexEnd == -1) {
                throw new Exception("' is promised");
            }
            String nameColumn = text.substring(indexStart + 1, indexEnd).toLowerCase();
            switch (nameColumn) {
                case "id", "lastname", "cost", "age", "active" -> {
                    return nameColumn;
                }
                default -> {
                    throw new Exception("'" + nameColumn + "'" + " is not such column in the table");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "-1";
        }
    }

    //считывание значения столбца
    public String getColumnValue(String columnValue) {
        try {
            if (columnValue.length() == 0) {
                throw new Exception("Column value is empty");
            }
            char firstChar = columnValue.charAt(0);
            if (firstChar == '\'') {
                int start = columnValue.indexOf("'");
                int end = columnValue.indexOf("'", start + 1);
                return columnValue.substring(start + 1, end);
            } else {
                return columnValue;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "-1";
        }
    }

    //сравнение двух строк у списков
    public boolean compareRows(Map<String, Object> row1, Map<String, Object> row2) {
        boolean isIdentical = true;
        for (String item : row1.keySet()) {
            boolean condition = row1.get(item).equals(row2.get(item));
            if (!condition) {
                isIdentical = false;
            }
        }
        return isIdentical;
    }

    //проверка является ли строка числом
    public boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
