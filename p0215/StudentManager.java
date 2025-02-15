package smallproject0206.code;

import java.util.*;
import java.util.regex.Pattern;

public class StudentManager extends StudentDBIO {

    private final Scanner scanner = new Scanner(System.in);
    private final StudentDAO studentDAO = new StudentDAO();
    private static final Pattern SNO_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]+$");
    private final Map<Integer, Runnable> menuChoice = new HashMap<>();

    public StudentManager() {
        mainMenu();
    }

    private void mainMenu() {
        menuChoice.put(1, () -> this.inputStudent());
        menuChoice.put(2, this::deleteStudentInfo);
        menuChoice.put(3, this::searchBySno);
        menuChoice.put(4, this::sortStudents);
        menuChoice.put(5, this::exitApp);
    }

    private void exitApp() {
        System.exit(0);
    }

    public void run() {
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();

            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                continue;
            }
            Runnable action = menuChoice.get(choice);
            if (action != null) {
                action.run();
            } else {
                System.out.println("재입력");
            }
        }
    }

    private void printMenu() {
        System.out.println("1. add student info");
        System.out.println("2. view student info");
        System.out.println("3. search student info");
        System.out.println("4. sort student info");
        System.out.println("5. exit");
        System.out.println("choice menu");
    }

    // 문자열검증
    private String readValidatedString(String prompt, Pattern pattern, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (pattern.matcher(input).matches()) {
                return input;
            } else {
                System.out.println(errorMessage);
            }
        }
    }
    // 숫자 검증
    private int readValidatedInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println(min + "~" + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            }
        }
    }

    @Override
    public void inputStudent() {
        System.out.println("add");
        String sno = readValidatedString("sno (10자리수): ", SNO_PATTERN, "정확히 10자리 수 재입력");

        Student existingStudent = studentDAO.findStudentBySno(sno);
        if (existingStudent == null) {
            // 신규는 모든정보 입력
            String name = readValidatedString("name (한, 영): ", NAME_PATTERN, "한, 영문으로 재입력");
            int korean = readValidatedInt("korean: ", 0, 100);
            int english = readValidatedInt("english: ", 0, 100);
            int math = readValidatedInt("math: ", 0, 100);
            int science = readValidatedInt("science: ", 0, 100);

            Student student = new Student.StudentBuilder()
                    .sno(sno)
                    .name(name)
                    .addSubject("korean", korean)
                    .addSubject("english", english)
                    .addSubject("math", math)
                    .addSubject("science", science)
                    .build();
            studentDAO.save(student);
            students.add(student);
        } else {
            //이미 등록된 학번은 수정
            System.out.println("already regist sno: " + existingStudent.getName());
            System.out.println("1.edit all info");
            System.out.println("2.edit subject score");
            System.out.println("3.exit add");
            String option = scanner.nextLine().trim();
            if ("1".equals(option)) {
                String name = readValidatedString("name (한, 영): ", NAME_PATTERN, "한, 영문으로 재입력");
                int korean = readValidatedInt("korean: ", 0, 100);
                int english = readValidatedInt("english: ", 0, 100);
                int math = readValidatedInt("math: ", 0, 100);
                int science = readValidatedInt("science: ", 0, 100);

                Student student = new Student.StudentBuilder()
                        .sno(sno)
                        .name(name)
                        .addSubject("korean", korean)
                        .addSubject("english", english)
                        .addSubject("math", math)
                        .addSubject("science", science)
                        .build();
                studentDAO.updateStudent(student);
                updateInMemoryStudent(student);
            } else if ("2".equals(option)) {
                int korean = readValidatedInt("korean: ", 0, 100);
                int english = readValidatedInt("english: ", 0, 100);
                int math = readValidatedInt("math: ", 0, 100);
                int science = readValidatedInt("science: ", 0, 100);

                Student student = new Student.StudentBuilder()
                        .sno(sno)
                        .name(existingStudent.getName())
                        .addSubject("korean", korean)
                        .addSubject("english", english)
                        .addSubject("math", math)
                        .addSubject("science", science)
                        .build();
                studentDAO.updateStudentScores(student);
                updateInMemoryStudent(student);
            } else if ("3".equals(option)){
                System.out.println("exit");
                return;
            } else {
                System.out.println("잘못된 입력");
            }
        }
        System.out.println("success");
    }

    // 메모리 내 리스트 업데이트 (동일 학번 학생 제거 후 새 객체 추가)
    private void updateInMemoryStudent(Student student) {
        students.removeIf(s -> s.getSno().equals(student.getSno()));
        students.add(student);
    }

    @Override
    public void outputStudent() {
        studentDAO.getAllStudents().forEach(System.out::println);
    }

    public void deleteStudentInfo() {
        outputStudent();

        System.out.print("삭제할 학생의 학번을 입력 (삭제하지 않으려면 그냥 엔터): ");
        String deleteSno = scanner.nextLine().trim();

        if (!deleteSno.isEmpty()) {
            deleteStudent(deleteSno);
        } else {
            System.out.println("삭제 없이 종료합니다");
        }
    }

    private void deleteStudent(String sno) {
        studentDAO.delete(sno);
        students.removeIf(s -> s.getSno().equals(sno));
        System.out.println("삭제 완료");
    }

    @Override
    public void searchBySno() {
        System.out.print("enter (sno 기준검색) :");
        String searchSno = scanner.nextLine().trim();

        Student foundStudent = studentDAO.findStudentBySno(searchSno);

        if (foundStudent == null) {
            System.out.println("no " + searchSno);
        } else {
            System.out.println("result");
            System.out.println(foundStudent);
        }
    }

    public void sortStudents() {
        System.out.println("Select sorting criteria:");
        System.out.println("1. Sort by total score ");
        System.out.println("2. Sort by sno ");
        String option = scanner.nextLine().trim();

        int sortChoice;
        try {
            sortChoice = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력");
            return;
        }

        List<Student> studentList = studentDAO.getAllStudents();
        if (studentList.isEmpty()) {
            System.out.println("DB에 저장된 학생 데이터가 없습니다.");
            return;
        }

        Map<Integer, Runnable> sortActions = new HashMap<>();
        sortActions.put(1, () -> sortByTotal(studentList));
        sortActions.put(2, () -> sortBySno(studentList));

        Runnable sortAction = sortActions.get(sortChoice);
        if (sortAction == null) {
            System.out.println("잘못된 입력");
            return;
        }
        sortAction.run();

        System.out.println("Sorted Students:");
        studentList.forEach(System.out::println);
    }

    @Override
    public void sortByTotal(List<Student> studentList) {
        studentList.sort(Comparator.comparingInt(Student::getTotal).reversed());
        System.out.println("Sorted by total score (descending):");
    }
    @Override
    public void sortBySno(List<Student> studentList) {
        studentList.sort(Comparator.comparing(Student::getSno));
        System.out.println("Sorted by sno:");
    }
}