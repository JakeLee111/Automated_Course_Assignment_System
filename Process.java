package CourseCheckerDuplicate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ProcessData {

    public List<String> completedCourses;
    public List<String> incompletedCourses;
    public List<String> eligibleCourses;
    public long studentId;
    public LinkedHashSet<String> uniqueCourses;
    public LinkedHashMap<Long, List<String>> incompletedCoursesResult;
    public LinkedHashMap<Long, List<String>> eligibleCoursesResult;
    public List<String> allCourses;

    private final StoreData storeData;

    public LinkedHashMap<Long, List<String>> assignedCourseIdsResult;
    public LinkedHashMap<Long, List<String>> assignedDaysResult;
    public LinkedHashMap<Long, List<String>> assignedTimesResult;
    public LinkedHashMap<Long, List<Integer>> assignedSectionsResult;

    public ProcessData(StoreData storeData) {
        this.storeData = storeData;
    }

    // Assign up to N courses for a student (4 for seniors, 2 for non-seniors)
    private void assignUpToNCoursesForStudent(Long sid, int maxCourses) {

        List<String> eligibleForStudent = eligibleCoursesResult.get(sid);
        if (eligibleForStudent == null) eligibleForStudent = new ArrayList<>();
        else eligibleForStudent = new ArrayList<>(eligibleForStudent);

        List<String> assignedCourseIds = new ArrayList<>();
        List<String> assignedDays = new ArrayList<>();
        List<String> assignedTimes = new ArrayList<>();
        List<Integer> assignedSections = new ArrayList<>();

        LinkedHashSet<String> occupiedSlots = new LinkedHashSet<>();

        List<String> allCourseIds = new ArrayList<>(storeData.courseidName.keySet());

        while (assignedCourseIds.size() < maxCourses) {
            String pickedCourseId = null;

            for (String courseId : allCourseIds) {
                Integer seats = storeData.courseidSeats.get(courseId);
                if (seats == null) seats = 0;
                if (seats <= 0) continue;

                String courseName = storeData.courseidName.get(courseId);
                if (courseName == null) continue;

                // skip if the course is not eligible
                if (!eligibleForStudent.contains(courseName)) continue;

                // conflict check (same day + same time not allowed)
                String day = storeData.courseidDay.get(courseId);
                String time = storeData.courseidTime.get(courseId);
                String key = slotKey(day, time);
                if (occupiedSlots.contains(key)) continue;

                // add courses that pass the checks
                pickedCourseId = courseId;
                occupiedSlots.add(key);

                // update the remaining seats
                storeData.courseidSeats.put(courseId, seats - 1);

                // prevent picking same course name again
                eligibleForStudent.remove(courseName);

                // store course/day/time/section
                assignedCourseIds.add(courseId);
                assignedDays.add(day);
                assignedTimes.add(time);
                assignedSections.add(storeData.courseidSection.get(courseId));

                break;
            }

            // no more courses possible
            if (pickedCourseId == null) break;
        }

        // keep output format always 4 slots (course3/course4 become null for non-seniors)
        assignedCourseIds = padToFour(assignedCourseIds);
        assignedDays = padToFour(assignedDays);
        assignedTimes = padToFour(assignedTimes);
        assignedSections = padToFour(assignedSections);

        assignedCourseIdsResult.put(sid, assignedCourseIds);
        assignedDaysResult.put(sid, assignedDays);
        assignedTimesResult.put(sid, assignedTimes);
        assignedSectionsResult.put(sid, assignedSections);
    }

    public void Processing() {

        incompletedCoursesResult = new LinkedHashMap<>();
        eligibleCoursesResult = new LinkedHashMap<>();

        assignedCourseIdsResult = new LinkedHashMap<>();
        assignedDaysResult = new LinkedHashMap<>();
        assignedTimesResult = new LinkedHashMap<>();
        assignedSectionsResult = new LinkedHashMap<>();

        // Unique course names (remove duplicates)
        uniqueCourses = new LinkedHashSet<>();
        for (String name : storeData.courseidName.values()) {
            if (name != null && !name.trim().isEmpty()) {
                uniqueCourses.add(name.trim());
            }
        }
        allCourses = new ArrayList<>(uniqueCourses);

        for (Entry<Long, List<String>> input : storeData.studentData.entrySet()) {
            studentId = input.getKey();
            completedCourses = input.getValue();

            incompletedCourses = new ArrayList<>();
            eligibleCourses = new ArrayList<>();

            // Uncompleted = all courses - completed
            for (String course : allCourses) {
                if (!completedCourses.contains(course)) {
                    incompletedCourses.add(course);
                }
            }

            // eligible = not completed && prerequisite satisfied
            for (Map.Entry<String, String> entry : storeData.coursenamePrerequisite.entrySet()) {
                String course = entry.getKey();
                String prerequisite = entry.getValue();

                if (completedCourses.contains(course)) continue;

                if (prerequisite == null ||
                        prerequisite.trim().isEmpty() ||
                        prerequisite.equalsIgnoreCase("No")) {

                    eligibleCourses.add(course);

                } else if (completedCourses.contains(prerequisite.trim())) {
                    eligibleCourses.add(course);
                }
            }

            incompletedCoursesResult.put(studentId, incompletedCourses);
            eligibleCoursesResult.put(studentId, eligibleCourses);
        }

        // Seniors first: assign up to 4 courses
        for (Entry<Long, String> entry : storeData.studentisSenior.entrySet()) {
            Long sid = entry.getKey();
            if (!"Yes".equalsIgnoreCase(entry.getValue())) continue;
            assignUpToNCoursesForStudent(sid, 4);
        }

        // Non-seniors after: assign up to 2 courses (using remaining seats)
        for (Entry<Long, String> entry : storeData.studentisSenior.entrySet()) {
            Long sid = entry.getKey();
            if ("Yes".equalsIgnoreCase(entry.getValue())) continue;
            assignUpToNCoursesForStudent(sid, 2);
        }

        // Print uncompleted courses
        System.out.println("Checking incompleted course...");
        System.out.println("---------");
        System.out.println("Student ID: [Incompleted courses]");
        for (Map.Entry<Long, List<String>> entry : incompletedCoursesResult.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("---------");

        // Print eligible courses
        System.out.println("Checking prerequisite requirements...");
        System.out.println("---------");
        System.out.println("Student ID: [Eligible courses]");
        for (Map.Entry<Long, List<String>> entry : eligibleCoursesResult.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Print courses assigning result
        System.out.println("---------");
        System.out.println("Checking senior students...");
        System.out.println("Assigning courses...");
        System.out.println("---------");
        System.out.println("FINAL ASSIGNMENT RESULTS (Seniors first, then non-seniors)");
        System.out.println("---------");

        for (Map.Entry<Long, List<String>> e : assignedCourseIdsResult.entrySet()) {
            Long sid = e.getKey();
            List<String> courses = e.getValue();
            List<Integer> sections = assignedSectionsResult.get(sid);
            List<String> days = assignedDaysResult.get(sid);
            List<String> times = assignedTimesResult.get(sid);

            System.out.println("Student " + sid + " assigned:");
            for (int i = 0; i < 4; i++) {
                System.out.println("  course" + (i + 1) + ": " + courses.get(i)
                        + " | section" + (i + 1) + ": " + sections.get(i)
                        + " | day" + (i + 1) + ": " + days.get(i)
                        + " | time" + (i + 1) + ": " + times.get(i));
            }
            System.out.println("---------");
        }
    }

    // Create a day+time key to detect conflicts
    private String slotKey(String day, String time) {
        if (day == null) day = "";
        if (time == null) time = "";
        return day.trim().toLowerCase() + "|" + time.trim().toLowerCase();
    }

    // Set limit to 4 output slots
    private <T> List<T> padToFour(List<T> list) {
        while (list.size() < 4) list.add(null);
        if (list.size() > 4) return new ArrayList<>(list.subList(0, 4));
        return list;
    }

    public LinkedHashMap<Long, List<String>> getAssignedCourseIdsResult() {
        return assignedCourseIdsResult;
    }

    public LinkedHashMap<Long, List<String>> getAssignedDaysResult() {
        return assignedDaysResult;
    }

    public LinkedHashMap<Long, List<String>> getAssignedTimesResult() {
        return assignedTimesResult;
    }

    public LinkedHashMap<Long, List<Integer>> getAssignedSectionsResult() {
        return assignedSectionsResult;
    }

    public LinkedHashMap<Long, List<String>> getIncompletedCoursesResult() {
        return incompletedCoursesResult;
    }

    public LinkedHashMap<Long, List<String>> getEligibleCoursesResult() {
        return eligibleCoursesResult;
    }

    public List<String> getAllCourses() {
        return allCourses;
    }

    public StoreData getStoreData() {
        return storeData;
    }
}