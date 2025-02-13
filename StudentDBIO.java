package smallproject0206.code;

import java.util.ArrayList;
import java.util.List;

public class StudentDBIO extends ObjectIO implements StudentIO{
    public List<Student> students = new ArrayList<>();

    @Override
    public void inputStudent() {}

    @Override
    public void outputStudent() {}

    @Override
    public void searchStudentByName() {}

    @Override
    public void sortByTotal(List<Student> students) {}

    @Override
    public void sortBySno(List<Student> students) {}
}
