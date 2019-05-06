/*
    Triston Gregoire
    April 11, 2019
    Software Testing & QA
 */
import java.util.List;

public class Student {
    private String name;
    private String id;
    private String email;
    private List<Integer> languages; //{C, C++, Java}
    private boolean isCSJobEx;
    private int attendance;
    private List<Integer> assignmentScores;
    private List<Integer> projectScores;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Integer> languages) {
        this.languages = languages;
    }

    public boolean isCSJobEx() {
        return isCSJobEx;
    }

    public void setCSJobEx(boolean CSJobEx) {
        isCSJobEx = CSJobEx;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public List<Integer> getAssignmentScores() {
        return assignmentScores;
    }

    public void setAssignmentScores(List<Integer> assignmentScores) {
        this.assignmentScores = assignmentScores;
    }

    public List<Integer> getProjectScores() {
        return projectScores;
    }

    public void setProjectScores(List<Integer> projectScores) {
        this.projectScores = projectScores;
    }

    /*
    Checks if this Student is equal to the passed Student by comparing their name and id
     */
    public boolean equals(Student student){
        if (student==null)
            throw new IllegalArgumentException("Null student passed to Student.matchName");
        return this.getName().equalsIgnoreCase(student.getName()) && this.getId().equals(student.getId());
    }
    /*
    Checks if this Student's name matches the name passed
     */
    public boolean matchName(String name){
        return this.getName().equalsIgnoreCase(name);
    }
    /*
    Checks if this Student's id matches the id passed
     */
    public boolean matchID(String id){
        return this.getId().equals(id);
    }
}
