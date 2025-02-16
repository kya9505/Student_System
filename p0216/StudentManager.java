package smallproject0206.code;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class StudentManager extends StudentDBIO {

    private final Scanner scanner = new Scanner(System.in);
    private final StudentDAO studentDAO = new StudentDAO();
    private static final Pattern SNO_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]+$");
    private final Map<Integer, Runnable> menuChoice = new HashMap<>();

    //객체 생성할때 mainMenu()호출해서 동작을 초기화함
    //메인메서드에서 객체 생성하구 manager.run()실행 --> mainmenu()와 run()이 결합하는 느낌
    public StudentManager() {
        mainMenu();
    }
    // 각 메뉴 번호와 실행할 작업을 menuChoice 맵에 등록
    private void mainMenu() {
        menuChoice.put(1, ()-> this.inputStudent()); //람다
        menuChoice.put(2, this::outputStudent); //메서드 참조
        menuChoice.put(3, this::searchBySno);
        menuChoice.put(4, this::sortStudents);
        menuChoice.put(5, this::backupToFile);
        menuChoice.put(6, this::exitApp);

    }

    private void printMenu() {
        System.out.println("1. add student info");
        System.out.println("2. view student info");
        System.out.println("3. search student info");
        System.out.println("4. sort student info");
        System.out.println("5. backup to file");
        System.out.println("6. exit");
        System.out.println("choice menu");
    }
    // printmenu(), 사용자 입력받고 해당 메뉴 실행하는 작업수행
    public void run() {
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim(); // 입력받고

            int choice;

            try {
                choice = Integer.parseInt(input); // string -> int 파싱
                /* str입력받고 int로 파싱할거면 int로 입력받으면 안되나요? 예외처리가 까다로워짐 */
            } catch (NumberFormatException e) { //잘못된 입력 잡아내기
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                continue;
            }
            Runnable action = menuChoice.get(choice); // 입력값 대응 Runnable
            if (action != null) {
                action.run();
            } else {
                System.out.println("재입력");
            }
        }
    }

    // 입력 검증 메서드
    // 기존에 input()에서 조건문 통해 패턴 검사하는 방식이었는데 분리함
    private String readValidatedString(String prompt, Pattern pattern, String errorMessage) {
        while (true) {
            System.out.print(prompt); // 프롬프트 출력
            String input = scanner.nextLine().trim();
            if (pattern.matcher(input).matches()) { // 정규식 패턴 검사
                return input;
            } else {
                System.out.println(errorMessage);
            }
        }
    }

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

    // 1. add menu
    // 신규 -> 정보입력, 기존 등록 -> edit all or edit scroe or exit 선택
    @Override
    public void inputStudent() {
        System.out.println("add");
        String sno = readValidatedString(
                "sno (10자리수): ", SNO_PATTERN, "정확히 10자리 수 재입력");

        Student existingStudent = studentDAO.findStudentBySno(sno); // sno 입력값이 db에 존재하는지 확인하는 메서드 호출
        if (existingStudent == null) { //존재하지 않으면
            String name = readValidatedString("name (한, 영): ", NAME_PATTERN, "한, 영문으로 재입력");
            int korean = readValidatedInt("korean: ", 0, 100);
            int english = readValidatedInt("english: ", 0, 100);
            int math = readValidatedInt("math: ", 0, 100);
            int science = readValidatedInt("science: ", 0, 100);

            //빌더 패턴 통한 객체 생성
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

    //2. view menu
    //db에 저장된 학생정보를 불러와서 출력하고 delete메서드 호출
    @Override
    public void outputStudent() {
        studentDAO.getAllStudents().forEach(System.out::println);
        deleteStudentInfo();
    }

    //삭제 메뉴 메서드
    public void deleteStudentInfo() {
        System.out.print("삭제할 학생의 학번을 입력 (삭제하지 않으려면 그냥 엔터): ");
        String deleteSno = scanner.nextLine().trim();

        if (!deleteSno.isEmpty()) {
            deleteStudent(deleteSno);
        } else {
            System.out.println("삭제 없이 종료합니다");
        }
    }

    //db와 메모리 데이터 삭제 수행
    private void deleteStudent(String sno) {
        studentDAO.delete(sno);
        students.removeIf(s -> s.getSno().equals(sno)); //removeIf사용해서 조건에 맞는 객체 삭제
        System.out.println("삭제 완료");
    }

    // 3.search menu
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

    // 4. sort menu
    public void sortStudents() {
        System.out.println("select");
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

        List<Student> studentList = studentDAO.getAllStudents(); //입력된 sno 학생 정보를 List<Student> 로 불러옴
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
    //정렬 기능 수행하는 메서드
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

    private void backupToFile() {
        // DB에서 최신 학생 데이터를 가져옴
        List<Student> studentList = studentDAO.getAllStudents();
        if (studentList.isEmpty()) {
            System.out.println("백업할 데이터가 없습니다.");
            return;
        }

        // 현재 날짜와 시간을 이용하여 파일명을 생성 (예: students_backup_20250214_153045.csv)
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "students_backup_" + timestamp + ".csv";

        // 새로운 백업 파일을 생성(덮어쓰기가 아닌 새로운 파일로 저장)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // 헤더 작성
            String header = "sno,name,korean,english,math,science,total,average,grade";
            writer.write(header);
            writer.newLine();

            // 학생 데이터 기록
            for (Student s : studentList) {
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
            System.out.println("파일 백업 완료: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // StudentManager 내부 헬퍼 메서드: 특정 과목 점수 추출
    private int getSubjectScore(Student student, String subjectName) {
        return student.getSubjects().stream()
                .filter(subject -> subject.getName().equalsIgnoreCase(subjectName))
                .mapToInt(Student.Subject::getScore)
                .findFirst()
                .orElse(0);
    }

    private void exitApp() {
        System.exit(0); //jvm 종료 표준 메서드
    }
}