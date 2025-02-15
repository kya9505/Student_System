package smallproject0206.code;

import java.util.List;

public interface SortedStudent {
    void sortByTotal(List<Student> students); // 총점 기준 정렬
    void sortBySno(List<Student> students);   // 학번 기준 정렬
}