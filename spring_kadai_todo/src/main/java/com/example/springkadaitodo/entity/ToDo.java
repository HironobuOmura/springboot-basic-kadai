package com.example.springkadaitodo.entity;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "todos")
@Data
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer todoId;

    @Column(name = "title")
    private String todoTitle;

    @Column(name = "priority")
    private Priority todoPriority;

    @Column(name = "status")
    private Status todoStatus;
    
    // ---------- Priority Enum ----------
    public enum Priority {
        LOW("低"),
        MEDIUM("中"),
        HIGH("高");

        private final String label;

        Priority(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        // 「高/中/低」→ Enum へ変換
        public static Priority fromLabel(String label) {
            for (Priority p : values()) {
                if (p.label.equals(label)) {
                    return p;
                }
            }
            throw new IllegalArgumentException("No enum constant for label: " + label);
        }
    }

    // ---------- Status Enum ----------
    public enum Status {
        TODO("未着手"),
        IN_PROGRESS("着手中"),
        DONE("完了");

        private final String label;

        Status(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        // 「未着手/着手中/完了」→ Enum へ変換
        public static Status fromLabel(String label) {
            for (Status s : values()) {
                if (s.label.equals(label)) {
                    return s;
                }
            }
            throw new IllegalArgumentException("No enum constant for label: " + label);
        }
    }

    // ---------- Converter for Priority ----------
    @Converter(autoApply = true)
    public static class PriorityConverter implements AttributeConverter<Priority, String> {
        @Override
        public String convertToDatabaseColumn(Priority priority) {
            return priority != null ? priority.getLabel() : null;
        }

        @Override
        public Priority convertToEntityAttribute(String dbData) {
            return dbData != null ? Priority.fromLabel(dbData) : null;
        }
    }

    // ---------- Converter for Status ----------
    @Converter(autoApply = true)
    public static class StatusConverter implements AttributeConverter<Status, String> {
        @Override
        public String convertToDatabaseColumn(Status status) {
            return status != null ? status.getLabel() : null;
        }

        @Override
        public Status convertToEntityAttribute(String dbData) {
            return dbData != null ? Status.fromLabel(dbData) : null;
        }
    }
}