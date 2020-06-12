package vn.daikon.myapplication.model;

public class Translation {
     String from ;
     String to;
     int id;
     String from2;
     String to2;

    public Translation(int id, String from, String to , String from2 , String to2) {
        this.from = from;
        this.from2 = from2;
        this.to = to;
        this.to2 = to2;
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public String getFrom2() {
        return from2;
    }

    public int getId() {
        return id;
    }

    public String getTo() {
        return to;
    }

    public String getTo2() {
        return to2;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setFrom2(String from2) {
        this.from2 = from2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setTo2(String to2) {
        this.to2 = to2;
    }
}
