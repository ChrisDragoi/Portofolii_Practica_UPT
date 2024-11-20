package ro.upt.ac.portofolii.student;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService implements UserDetailsService {
    private StudentRepository studentRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Student> student = Optional.ofNullable(studentRepository.findByEmail(email));
        if (student.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return (UserDetails) student.get();
    }

    public Optional<List<Student>> loadAllStudents() {
        Optional<List<Student>> students = Optional.of(studentRepository.findAll());
        if (students.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }else return students;

    }
}
