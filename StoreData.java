package CourseCheckerDuplicate;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StoreData {
	
	public LinkedHashMap<Long, List<String>> studentData;
	public LinkedHashMap<Long, String> studentName;
	public LinkedHashMap<Long, String> studentMajor;
	public LinkedHashMap<Long, String> studentisSenior;
	public LinkedHashMap<String, String> courseidName;
	public LinkedHashMap<String, String> coursenamePrerequisite;
	public LinkedHashMap<String, Integer> courseidSection;
	public LinkedHashMap<String, Integer> courseidSeats;
	public LinkedHashMap<String, String> courseidDay;
	public LinkedHashMap<String, String> courseidTime;

	public void ReadStudentFile(File selectedFile) {
	
	// Read the file with POI
    try (FileInputStream inputstream1 = new FileInputStream(selectedFile);
    		XSSFWorkbook workbook = new XSSFWorkbook(inputstream1)) {

    	XSSFSheet sheet = workbook.getSheetAt(0);
        
        int rows = sheet.getLastRowNum();
        
        // Create the HashMap: key = student ID, value = list of completed courses
        studentData = new LinkedHashMap<>();            
        
        for (int r = 1; r <= rows; r++) {
        	long studentId = (long) sheet.getRow(r).getCell(0).getNumericCellValue();
        	String cellValue = sheet.getRow(r).getCell(2).getStringCellValue();

        	List<String> completedCourses = new ArrayList<>();
        	String[] coursesArray = cellValue.split(",");
            for (String course : coursesArray) {
                if (!course.trim().isEmpty()) {
                    completedCourses.add(course.trim());
                }
            }
            
            studentData.put(studentId, completedCourses);

        }
        
        // Tester
        
        /* for (Map.Entry<String, List<String>> entry : studentData.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        
        System.out.println("---------");  */
        
     // Create the HashMap: key = student ID, value = name
        studentName = new LinkedHashMap<>();            
        
        for (int r = 1; r <= rows; r++) {
        	long studentId = (long) sheet.getRow(r).getCell(0).getNumericCellValue();
        	String cellValue = sheet.getRow(r).getCell(1).getStringCellValue();
        	
        	if (!cellValue.trim().isEmpty()) {
        		cellValue = cellValue.trim();
                }
        	studentName.put(studentId, cellValue);
            }
        // Tester
        /* for (Map.Entry<String, String> entry : studentName.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        
        System.out.println("---------"); */
        
     // Create the HashMap: key = student ID, value = major
        studentMajor = new LinkedHashMap<>();            
        
        for (int r = 1; r <= rows; r++) {
        	long studentId = (long) sheet.getRow(r).getCell(0).getNumericCellValue();
        	String cellValue = sheet.getRow(r).getCell(3).getStringCellValue();
        	
        	if (!cellValue.trim().isEmpty()) {
        		cellValue = cellValue.trim();
                }
        	studentMajor.put(studentId, cellValue);
            }
        // Tester
        /* for (Map.Entry<String, String> entry : studentMajor.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        
        System.out.println("---------"); */
        
     // Create the HashMap: key = student ID, value = isSenior
        studentisSenior = new LinkedHashMap<>();            
        
        for (int r = 1; r <= rows; r++) {
        	long studentId = (long) sheet.getRow(r).getCell(0).getNumericCellValue();
        	String cellValue = sheet.getRow(r).getCell(4).getStringCellValue();
        	
        	if (!cellValue.trim().isEmpty()) {
        		cellValue = cellValue.trim();
                }
        	studentisSenior.put(studentId, cellValue);
            }
        
        // Tester
        /* for (Map.Entry<String, String> entry : studentisSenior.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        
        System.out.println("---------"); */
        
    } catch (Exception ex) {
        ex.printStackTrace();
}
	}
	
	public void ReadCoursesFile(File selectedFile) {
		// Read the file with POI
        try (FileInputStream inputstream2 = new FileInputStream(selectedFile);
             XSSFWorkbook coursesWorkbook = new XSSFWorkbook(inputstream2)) {
        	
        	XSSFSheet coursesSheet = coursesWorkbook.getSheetAt(0);
        	int rows = coursesSheet.getLastRowNum();
            
            // Create the HashMap: key = course ID, value = course name
            courseidName = new LinkedHashMap<>();            
            
            for (int r = 1; r <= rows; r++) {
            	String courseID = coursesSheet.getRow(r).getCell(0).getStringCellValue();
            	String coursename = coursesSheet.getRow(r).getCell(1).getStringCellValue();

            	courseidName.put(courseID, coursename);

            }  
            
         // Create the HashMap: key = course name, value = prerequisite
            coursenamePrerequisite = new LinkedHashMap<>();            
            
            for (int r = 1; r <= rows; r++) {
            	String coursename = coursesSheet.getRow(r).getCell(1).getStringCellValue();
            	String prerequisite = coursesSheet.getRow(r).getCell(6).getStringCellValue();
   
            	coursenamePrerequisite.put(coursename, prerequisite);

            }  
            
         // Create the HashMap: key = course ID, value = section
            courseidSection = new LinkedHashMap<>();            
            
            for (int r = 1; r <= rows; r++) {
            	String courseID = coursesSheet.getRow(r).getCell(0).getStringCellValue();
            	int section = (int) coursesSheet.getRow(r).getCell(2).getNumericCellValue();

            	courseidSection.put(courseID, section);

            }  
            
         // Create the HashMap: key = course ID, value = seats
            courseidSeats = new LinkedHashMap<>();            
            
            for (int r = 1; r <= rows; r++) {
            	String courseID = coursesSheet.getRow(r).getCell(0).getStringCellValue();
            	int seats = (int) coursesSheet.getRow(r).getCell(3).getNumericCellValue();

            	courseidSeats.put(courseID, seats);

            }  
           
         // Create the HashMap: key = course ID, value = day
            courseidDay = new LinkedHashMap<>();            
            
            for (int r = 1; r <= rows; r++) {
            	String courseID = coursesSheet.getRow(r).getCell(0).getStringCellValue();
            	String day = coursesSheet.getRow(r).getCell(4).getStringCellValue();

            	courseidDay.put(courseID, day);

            }  
            
         // Create the HashMap: key = course ID, value = time
            courseidTime = new LinkedHashMap<>();            
            
            for (int r = 1; r <= rows; r++) {
            	String courseID = coursesSheet.getRow(r).getCell(0).getStringCellValue();
            	String time = coursesSheet.getRow(r).getCell(5).getStringCellValue();

            	courseidTime.put(courseID, time);

            }  

        } catch (Exception ex) {
            ex.printStackTrace();
        }

	}
	
	
	
	}

	
