import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GradesDB {
    private int numStudents;
    private int numAssignments;
    private int numProjects;
    private List<String> sheetNames;
    private Workbook workbook;

    private HashSet<Student> students = new HashSet<Student>();
    public GradesDB(String filePath) throws IOException {
        File file = new File(this.getClass().getClassLoader().getResource(filePath).getFile());
        System.out.println("file "+ file.toString());
        FileInputStream fileInputStream = new FileInputStream(file);
        workbook = new XSSFWorkbook(fileInputStream);
        sheetNames = new ArrayList<String>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheetNames.add(workbook.getSheetName(i));
        }
        readStudentsInfo();
    }

    /*
    Uses Apache's JOI library to read excel file and populates Student objects with the data. Most of the data is eventually stored in a Hashset collection

    TODO: read in TeamGrades and Teams excel sheets
     */
    private void readStudentsInfo() {
        List<Student> studentList = new ArrayList<Student>();
        String STUDENT_INFO = "StudentsInfo";
        XSSFSheet sheet = ((XSSFWorkbook) workbook).getSheetAt(sheetNames.indexOf(STUDENT_INFO));
        Student student;
        for (Row row : sheet) {//for each row in the sheet
            if (row.getRowNum() == 0) {//skip the header row
                System.out.println(row.getRowNum());
                continue;
            }
            student = new Student();
            List<Integer> languages = new ArrayList<Integer>();
            student.setName(row.getCell(0).getStringCellValue());
            student.setId(
                    Integer.toString((int) row.getCell(1).getNumericCellValue())
            );
            student.setEmail(row.getCell(2).getStringCellValue());
            languages.add((int) row.getCell(3).getNumericCellValue());
            languages.add((int) row.getCell(4).getNumericCellValue());
            languages.add((int) row.getCell(5).getNumericCellValue());
            student.setLanguages(languages);
            student.setCSJobEx(
                    row.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue().equalsIgnoreCase("Y")
            );//map cells with 'Y' to true and else to false
            studentList.add(student);//populate student list
        }

        String ATTENDANCE = "Attendance";
        XSSFSheet attendanceSheet = ((XSSFWorkbook) workbook).getSheetAt(sheetNames.indexOf(ATTENDANCE));
        for (Row row : attendanceSheet) {
            for (Student student1 : studentList) {
                if (student1.matchName(row.getCell(0).getStringCellValue())){
                    student1.setAttendance((int) row.getCell(1).getNumericCellValue());
                    break;
                }
            }
        }

        String INDIVIDUAL_GRADES = "IndividualGrades";
        XSSFSheet individualGradeSheet = ((XSSFWorkbook) workbook).getSheetAt(sheetNames.indexOf(INDIVIDUAL_GRADES));
        numAssignments = 0;
        for (Row row : individualGradeSheet) {
            for (Student student1 : studentList) {
                if (student1.matchName(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue())){
                    List<Integer> assignmentScores = new ArrayList<Integer>();
                    assignmentScores.add((int) row.getCell(1).getNumericCellValue());
                    assignmentScores.add((int) row.getCell(2).getNumericCellValue());
                    assignmentScores.add((int) row.getCell(3).getNumericCellValue());
                    student1.setAssignmentScores(assignmentScores);
                    numAssignments = (assignmentScores.size() > numAssignments) ? assignmentScores.size() : numAssignments;
                    break;
                }
            }
        }

        String INDIVIDUAL_CONTRIBUTIONS = "IndividualContribs";
        XSSFSheet individualContribSheet = ((XSSFWorkbook) workbook).getSheetAt(sheetNames.indexOf(INDIVIDUAL_CONTRIBUTIONS));
        for (Row row : individualContribSheet){
            for (Student student1 : studentList) {
                if (student1.matchName(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue())){
                    List<Integer> projectScores = new ArrayList<Integer>();
                    projectScores.add((int) row.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    projectScores.add((int) row.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    projectScores.add((int) row.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    student1.setProjectScores(projectScores);
                    numProjects = (projectScores.size() > numProjects) ? projectScores.size() : numProjects;
                    break;
                }
            }
        }

        students = new HashSet<Student>(studentList);
        setNumStudents(students.size());
    }

    public Student getStudentByName(String name){
        for (Student student : students) {
            if (student.matchName(name))
                return student;
        }
        return null;
    }

    public Student getStudentByID(String id){
        for (Student student : students) {
            if (student.matchID(id))
                return student;
        }
        return null;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }

    public int getNumAssignments() {
        return numAssignments;
    }

    public void setNumAssignments(int numAssignments) {
        this.numAssignments = numAssignments;
    }

    public int getNumProjects() {
        return numProjects;
    }

    public void setNumProjects(int numProjects) {
        this.numProjects = numProjects;
    }

    public HashSet<Student> getStudents() {
        return students;
    }

    public void setStudents(HashSet<Student> students) {
        this.students = students;
    }
}
