package ru.javarush.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Victor Bugaenko
 * @since 17.09.2018
 */

@Entity
@Table(name = "part")
public class Part {
    @Id
    @Column(name = "id")
    private int id;
    private String title;
    private boolean enabled;
    private int amount;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", enabled=" + enabled +
                ", amount=" + amount +
                '}';
    }
}
