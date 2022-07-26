package com.example.git.model;

import androidx.lifecycle.ViewModel;

import java.time.Instant;
import java.util.Date;

public class Repository  {
    private String name;
    private String language;
    private int size;
    private Instant created_at;
    private Instant last_update;
    private int watchers;

    public Repository(String name, String language, int size, Instant created_at, Instant last_update, int watchers) {
        this.name = name;
        this.language = language;
        this.size = size;
        this.last_update = last_update;
        this.created_at = created_at;
        this.watchers = watchers;
    }

    public int getWatchers() {
        return watchers;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public Instant getLast_update() {
        return last_update;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public int getSize() {
        return size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
