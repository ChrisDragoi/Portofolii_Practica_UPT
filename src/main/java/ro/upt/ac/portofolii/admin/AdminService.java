package ro.upt.ac.portofolii.admin;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;
import ro.upt.ac.portofolii.security.UserRepository;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.student.StudentRepository;
import ro.upt.ac.portofolii.utils.PasswordGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public AdminService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public String saveStudentCsv(MultipartFile file, String UPLOAD_DIR) throws IOException {
        return copyFile(file, UPLOAD_DIR) + uploadStudentsWithCredentialsCsv(UPLOAD_DIR);
    }
    private String copyFile(MultipartFile file, String UPLOAD_DIR) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            System.out.println("Directory created");
        }else{
            System.out.println("Directory already exists");
        }
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv")) {
            throw new IOException("File is not a .csv");
        }
        Path filePath = uploadPath.resolve("Students_Details.csv");
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "File " + file.getOriginalFilename() + " was copied to " + filePath + "\n";
    }
    private String uploadStudentsWithCredentialsCsv(String UPLOAD_DIR) throws IOException {
        Path sourceFile = Paths.get(UPLOAD_DIR, "Students_Details.csv");
        Path outputFile = Paths.get(UPLOAD_DIR, "Students_Credentials.csv");

        if (!Files.exists(sourceFile)) {
            throw new FileNotFoundException("File doesn't exist: " + sourceFile);
        }

        try (BufferedReader reader = Files.newBufferedReader(sourceFile);
             BufferedWriter writer = Files.newBufferedWriter(outputFile)) {

            String header = "Nume,Prenume,Email,Password";
            writer.write(header);
            writer.newLine();

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setTrim(true)
                    .setQuoteMode(QuoteMode.ALL)
                    .build();

            CSVParser csvParser = new CSVParser(reader, csvFormat);

            for (CSVRecord record : csvParser) {
                addStudentToDB(record);
                String nume = record.get(0);
                String prenume = record.get(1);
                String email = record.get(13);
                String password = new PasswordGenerator().generateRandomPassword();

                writer.write(String.join(",", nume, prenume, email, password));
                writer.newLine();
                addStudentUser(email, password);
            }
        }
        return "Generated credentials in " + outputFile;
    }
    private void addStudentToDB(CSVRecord record) throws IOException {
        System.out.println("starting student initialization...");

			Student s1=new Student();
	        s1.setNume(record.get(0));
	        s1.setPrenume(record.get(1));
	        s1.setCnp(record.get(2));
	        s1.setDataNasterii(Date.valueOf(record.get(3)));
	        s1.setLoculNasterii(record.get(4));
	        s1.setCetatenie(record.get(5));
	        s1.setSerieCi(record.get(6));
	        s1.setNumarCi(record.get(7));
	        s1.setAdresa(record.get(8));
	        s1.setAnUniversitar(record.get(9));
	        s1.setFacultate(record.get(10));
	        s1.setSpecializare(record.get(11));
	        s1.setAnDeStudiu(Integer.parseInt(record.get(12)));
	        s1.setEmail(record.get(13));
	        s1.setTelefon(record.get(14));
			//s1.setSemnatura(record.get(15));

        studentRepository.save(s1);
        createStudentFolder(s1.getNume(),s1.getPrenume());

        System.out.println("ending initialization...");
    }
    private void addStudentUser(String email, String password) {
        User user=new User(email,password, Role.STUDENT);
        userRepository.save(user);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudentCredentialsToCSV(Student student) throws IOException {
        Path filePath = Paths.get("upload", "Students_Credentials.csv");

        boolean fileExists = Files.exists(filePath);

        String password = new PasswordGenerator().generateRandomPassword();

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (!fileExists) {
                writer.write("Nume,Prenume,Email,Password");
                writer.newLine();
            }

            writer.write(String.join(",", student.getNume(), student.getPrenume(), student.getEmail(), password));
            writer.newLine();
        }

        addStudentUser(student.getEmail(), password);
        createStudentFolder(student.getNume(), student.getPrenume());
    }

    private void createStudentFolder(String nume, String prenume) throws IOException {
        String folderName = nume+prenume;
        Path studentDir = Paths.get("studenti", folderName);

        if (!Files.exists(studentDir)) {
            Files.createDirectories(studentDir);
            System.out.println("Folder creat: " + studentDir);
        } else {
            System.out.println("Folderul exista deja: " + studentDir);
        }
    }
    public void deleteStudentFolder(String nume, String prenume) throws IOException {
        String folderName = nume + prenume;
        Path studentDir = Paths.get("studenti", folderName);

        if (Files.exists(studentDir) && Files.isDirectory(studentDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(studentDir)) {
                for (Path file : stream) {
                    Files.delete(file);
                }
            }
            Files.delete(studentDir);
            System.out.println("Folder și conținut șters: " + studentDir);
        } else {
            System.out.println("Folderul nu există sau nu este director: " + studentDir);
        }
    }


}
