package com.cs193a.manojkumar.notetakingapp;

public class Notes {

    private String headerNotes;
    private String subHeaderNotes;
    private String notesDate;

    public Notes(String headerNotes, String subHeaderNotes, String notesDate) {
        this.headerNotes = headerNotes;
        this.subHeaderNotes = subHeaderNotes;
        this.notesDate = notesDate;
    }

    public String getHeaderNotes() {
        return headerNotes;
    }

    public String getSubHeaderNotes() {
        return subHeaderNotes;
    }

    public String getNotesDate() {
        return notesDate;
    }

    public void setHeaderNotes(String headerNotes) {
        this.headerNotes = headerNotes;
    }

    public void setSubHeaderNotes(String subHeaderNotes) {
        this.subHeaderNotes = subHeaderNotes;
    }

    public void setNotesDate(String notesDate) {
        this.notesDate = notesDate;
    }



}
