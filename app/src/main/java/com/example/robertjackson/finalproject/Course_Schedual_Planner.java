package com.example.robertjackson.finalproject;
/**
 * Created by wty on 2016-11-10.
 */
public class Course_Schedual_Planner {

    public static final String TABLE_NAME = "courses";
    public static final String COURSE_ID = "courseID";
    public static final String COURSE_NAME = "courseName";
    public static final String CLASS_ROOM = "classRoom";
    public static final String INSTRUCTOR_NAME = "instructorName";
    public static final String DAY_SCHEDULE =  "daySchedule";
    public static final String TIME_SCHEDULE = "startTime";

    private String courseID;
    private String courseName;
    private String classRoom;
    private String instructorName;
    private String daySchedule;
    private String startEndTime;

    private int start;


    public Course_Schedual_Planner() {
        this("CST", "unknown", "unknown", "unknown", "unknown day", "8:00 - 9:00", 8);
    }

    public Course_Schedual_Planner(String id, String cName, String room, String instruName, String day, String time, int i){
        this.courseID = id;
        this.courseName = cName;
        this.classRoom = room;
        this.instructorName = instruName;
        this.daySchedule=day;
        this.startEndTime=time;
        this.start =i;
    }


    public void setCourseId(String cID){
        this.courseID = cID;

    }

    public String getCourseId(){
        return this.courseID;
    }

    public void setCourseName(String cName){
        this.courseName = cName;
    }

    public String getCourseName(){
        return this.courseName;
    }

    public void setClassRoom(String cRoom){
        this.classRoom = cRoom;
    }

    public String getClassRoom(){
        return this.classRoom;
    }

    public void setInstructorName(String instrName){
        this.instructorName = instrName;
    }

    public String getInstructorName(){
        return this.instructorName;
    }

    public void setDaySchedule(String day) {
        this.daySchedule = day;
    }

    public String getDaySchedule(){
        return this.daySchedule;
    }

    public void setStartEndTime(String timeS) {
        this.startEndTime = timeS;
    }

    public String getStartEndTime(){
        return this.startEndTime;
    }

    public void setStart(int i) {
        this.start = i;
    }

    public int getStart(){
        return this.start;
    }




}

