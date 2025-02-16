package smallproject0206.code;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentFileIO extends StudentDBIO {
    // 단일 인스턴스를 보관하는 static final 필드
    private static final StudentFileIO INSTANCE = new StudentFileIO();
    // 백업 파일 경로
    private static final String FILE_PATH = "students_backup.csv";

    // private 생성자: 외부에서 인스턴스 생성 불가
    private StudentFileIO() {
    }

    // public static 메서드를 통해 단일 인스턴스 반환
    public static StudentFileIO getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Student student) {
        // CSV 형식으로 학생 정보를 파일에 추가(append)합니다.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String csvLine = String.format("%s,%s,%d,%d,%d,%d,%d,%.2f,%s",
                    student.getSno(),
                    student.getName(),
                    getSubjectScore(student, "korean"),
                    getSubjectScore(student, "english"),
                    getSubjectScore(student, "math"),
                    getSubjectScore(student, "science"),
                    student.getTotal(),
                    student.getAverage(),
                    student.computeGrade());
            writer.write(csvLine);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // CSV 형식: sno,name,korean,english,math,science,total,average,grade
                String[] tokens = line.split(",");
                if (tokens.length >= 6) {
                    String sno = tokens[0];
                    String name = tokens[1];
                    int korean = Integer.parseInt(tokens[2]);
                    int english = Integer.parseInt(tokens[3]);
                    int math = Integer.parseInt(tokens[4]);
                    int science = Integer.parseInt(tokens[5]);
                    // total, average, grade는 계산 가능하므로 다시 계산하도록 함
                    Student student = new Student.StudentBuilder()
                            .sno(sno)
                            .name(name)
                            .addSubject("korean", korean)
                            .addSubject("english", english)
                            .addSubject("math", math)
                            .addSubject("science", science)
                            .build();
                    studentList.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    @Override
    public void delete(String sno) {
        // 파일 전체를 읽어, 해당 학번을 제외한 데이터로 파일을 재작성합니다.
        List<Student> studentList = getAllStudents();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student s : studentList) {
                if (!s.getSno().equals(sno)) {
                    String csvLine = String.format("%s,%s,%d,%d,%d,%d,%d,%.2f,%s",
                            s.getSno(),
                            s.getName(),
                            getSubjectScore(s, "korean"),
                            getSubjectScore(s, "english"),
                            getSubjectScore(s, "math"),
                            getSubjectScore(s, "science"),
                            s.getTotal(),
                            s.getAverage(),
                            s.computeGrade());
                    writer.write(csvLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 헬퍼 메서드: Student 객체에서 특정 과목 점수를 추출
    private int getSubjectScore(Student student, String subjectName) {
        return student.getSubjects().stream()
                .filter(subject -> subject.getName().equalsIgnoreCase(subjectName))
                .mapToInt(Student.Subject::getScore)
                .findFirst()
                .orElse(0);
    }
}
