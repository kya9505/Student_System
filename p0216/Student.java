package smallproject0206.code;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private String sno;
    private String name;
    private List<Subject> subjects;

    private Student(StudentBuilder builder) {
        this.sno = builder.sno;
        this.name = builder.name;
        this.subjects = builder.subjects;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public int getTotal() {
            int total = 0;
            for (Subject subject : subjects) {
                total += subject.getScore();
            }
            return total;
        }

    public double getAverage() {
        return subjects.isEmpty() ? 0 : getTotal() / (double) subjects.size();
    }

    public String computeGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A";
        else if (avg >= 80) return "B";
        else if (avg >= 70) return "C";
        else if (avg >= 60) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("sno='").append(sno).append("', ");
        sb.append("name='").append(name).append("', ");
        sb.append("subjects=").append(subjects).append(", ");
        sb.append("total=").append(getTotal()).append(", ");
        sb.append("average=").append(getAverage()).append(", ");
        sb.append("grade=").append(computeGrade());
        return sb.toString();
    }


    public static class Subject {
        private String name;
        private int score;

        public Subject(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
        @Override
        public String toString() {
            return name + ":" + score;
        }
    }

    public static class StudentBuilder {
        private String sno;
        private String name;
        private List<Subject> subjects = new ArrayList<>();

        public StudentBuilder sno(String sno) {
            this.sno = sno;
            return this;
        }

        public StudentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder addSubject(String subjectName, int score) {
            this.subjects.add(new Subject(subjectName, score));
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
    public String getSno() {
        return sno;
    }
}