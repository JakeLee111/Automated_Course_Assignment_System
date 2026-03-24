package CourseCheckerDuplicate;

import org.apache.poi.xssf.usermodel.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class ExportData {
    private final LinkedHashMap<Long, List<String>> completedCoursesByStudent;
    private final LinkedHashMap<Long, List<String>> incompletedCoursesResult;
    private final LinkedHashMap<Long, List<String>> eligibleCoursesResult;
    private final LinkedHashMap<Long, List<String>> assignedCourseIdsResult;
    private final LinkedHashMap<Long, List<String>> assignedDaysResult;
    private final LinkedHashMap<Long, List<String>> assignedTimesResult;
    private final LinkedHashMap<Long, List<Integer>> assignedSectionsResult;

    public ExportData(ProcessData processData) {
        this.completedCoursesByStudent = processData.getStoreData().studentData;
        this.incompletedCoursesResult = processData.getIncompletedCoursesResult();
        this.eligibleCoursesResult = processData.getEligibleCoursesResult();
        this.assignedCourseIdsResult = processData.getAssignedCourseIdsResult();
        this.assignedDaysResult = processData.getAssignedDaysResult();
        this.assignedTimesResult = processData.getAssignedTimesResult();
        this.assignedSectionsResult = processData.getAssignedSectionsResult();
    }

    private String joinList(List<String> list) {
        if (list == null || list.isEmpty()) return "";
        return String.join(", ", list);
    }

    public void exportToExcel(File file) {

        if (file == null) {
            System.out.println("No file selected.");
            return;
        }

        if (assignedCourseIdsResult == null || assignedCourseIdsResult.isEmpty()) {
            System.out.println("No assignment data to export.");
            return;
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Result");

            // Header
            XSSFRow header = sheet.createRow(0);

            int col = 0;
            header.createCell(col++).setCellValue("Student ID");
            header.createCell(col++).setCellValue("Completed Courses");
            header.createCell(col++).setCellValue("Incompleted Courses");
            header.createCell(col++).setCellValue("Eligible Courses");

            // Course 1..4 blocks
            for (int i = 1; i <= 4; i++) {
                header.createCell(col++).setCellValue("Course" + i);
                header.createCell(col++).setCellValue("Section" + i);
                header.createCell(col++).setCellValue("Day" + i);
                header.createCell(col++).setCellValue("Time" + i);
            }

            int rowNum = 1;

            for (Entry<Long, List<String>> e : assignedCourseIdsResult.entrySet()) {
                Long studentId = e.getKey();

                List<String> completed = completedCoursesByStudent.get(studentId);
                List<String> incompleted = incompletedCoursesResult.get(studentId);
                List<String> eligible = eligibleCoursesResult.get(studentId);

                List<String> courses = assignedCourseIdsResult.get(studentId);
                List<Integer> sections = assignedSectionsResult.get(studentId);
                List<String> days = assignedDaysResult.get(studentId);
                List<String> times = assignedTimesResult.get(studentId);

                XSSFRow row = sheet.createRow(rowNum++);

                int c = 0;
                row.createCell(c++).setCellValue(studentId);
                row.createCell(c++).setCellValue(joinList(completed));
                row.createCell(c++).setCellValue(joinList(incompleted));
                row.createCell(c++).setCellValue(joinList(eligible));

                // Fill 4 slots safely
                for (int i = 0; i < 4; i++) {
                    String course = (courses != null && i < courses.size() && courses.get(i) != null) ? courses.get(i) : "";
                    String section = (sections != null && i < sections.size() && sections.get(i) != null) ? String.valueOf(sections.get(i)) : "";
                    String day = (days != null && i < days.size() && days.get(i) != null) ? days.get(i) : "";
                    String time = (times != null && i < times.size() && times.get(i) != null) ? times.get(i) : "";

                    row.createCell(c++).setCellValue(course);
                    row.createCell(c++).setCellValue(section);
                    row.createCell(c++).setCellValue(day);
                    row.createCell(c++).setCellValue(time);
                }
            }

            // Auto-size columns (0..19)
            for (int i = 0; i < 20; i++) sheet.autoSizeColumn(i);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

            System.out.println("Excel exported to: " + file.getAbsolutePath());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
