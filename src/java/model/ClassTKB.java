/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Win7
 */
public class ClassTKB extends Subject{

    static void forName(String commysqljdbcDriver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private int id;
    private int idSubject;
    private String name;
    private int slot;
    private String room;
    private String teacher;
    private String time;

    public void setId(int id) {
        this.id = id;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
    }

    public String getRoom() {
        return room;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getTime() {
        return time;
    }

    

    @Override
    public String toString() {
        Subject subject = SubjectDAO.getSubjectByIdClass(id);
        return "&thinsp;&emsp;" + name + "&emsp;&nbsp;&nbsp;" + slot + "&emsp;&emsp;&nbsp;&emsp;&emsp;&emsp;" + teacher + "&emsp;&emsp;&emsp;"
                + room + "&emsp;&emsp;&nbsp;&nbsp;" + time + "&emsp;&emsp;&emsp;&emsp;" + subject.getTCNum();
    }

    
    
}
