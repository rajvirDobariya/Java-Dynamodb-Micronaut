package example.test.controller;

import example.test.model.Student;
import example.test.repo.StudentRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import java.util.List;

@Controller("/students")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Get("/{studentId}")
    public Student findByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId)
                .orElse(null); // Handle not found scenario as needed
    }

    @Get
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Post
    @Status(HttpStatus.CREATED)
    public Student createStudent(@Body  Student student) {
        return studentRepository.createStudent(student);
    }

    @Put("/{studentId}")
    public Student updateStudent(String studentId, @Body  Student updatedStudent) {
        updatedStudent.setStudent_id(studentId);
        return studentRepository.updateStudent(updatedStudent)
                .orElse(null); // Handle update failure or not found scenario as needed
    }


//    @Delete("/{studentId}")
//    public Student deleteStudent(String studentId) {
//        return studentRepository.deleteStudent(studentId)
//                .orElse(null); // Handle not found scenario as needed
//    }
}