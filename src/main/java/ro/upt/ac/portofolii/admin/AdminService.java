package ro.upt.ac.portofolii.admin;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.security.Role;
import ro.upt.ac.portofolii.security.User;
import ro.upt.ac.portofolii.security.UserRepository;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.student.StudentRepository;
import ro.upt.ac.portofolii.tutore.Tutore;
import ro.upt.ac.portofolii.utils.PasswordGenerator;

import java.io.*;
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

			Student s=new Student();
	        s.setNume(record.get(0));
	        s.setPrenume(record.get(1));
	        s.setCnp(record.get(2));
	        s.setDataNasterii(Date.valueOf(record.get(3)));
	        s.setLoculNasterii(record.get(4));
	        s.setCetatenie(record.get(5));
	        s.setSerieCi(record.get(6));
	        s.setNumarCi(record.get(7));
	        s.setAdresa(record.get(8));
	        s.setAnUniversitar(record.get(9));
	        s.setFacultate(record.get(10));
	        s.setSpecializare(record.get(11));
	        s.setAnDeStudiu(Integer.parseInt(record.get(12)));
	        s.setEmail(record.get(13));
	        s.setTelefon(record.get(14));
			//s1.setSemnatura(record.get(15));

        makeSign(studentRepository.save(s));
        createStudentFolder(s);

        System.out.println("ending initialization...");
    }

    private void makeSign(Student s) {
        String baseDir = "studenti";

        File studentiDir = new File(baseDir);
        if (!studentiDir.exists()) {
            boolean created = studentiDir.mkdir();
            if (created) {
                System.out.println("Directorul 'studenti' a fost creat anterior.");
            }
        }

        String profDirPath = baseDir + "/student" + s.getId();
        File profDir = new File(profDirPath);
        if (!profDir.exists()) {
            boolean created = profDir.mkdir();
            if (created) {
                System.out.println("Directorul '" + profDirPath + "' a fost creat.");
            }
        }
        s.setSemnatura(profDirPath);
        studentRepository.save(s);
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
        createStudentFolder(student);
    }

    private void createStudentFolder(Student s) throws IOException {
        String folderName = "student" + s.getId();
        Path studentDir = Paths.get("studenti", folderName);

        if (!Files.exists(studentDir)) {
            Files.createDirectories(studentDir);
            System.out.println("Folder creat: " + studentDir);
        } else {
            System.out.println("Folderul exista deja: " + studentDir);
        }
    }

    public void deleteStudentFolder(Student s) throws IOException {
        String folderName = "student" + s.getId();
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

    public void deleteProfFolder(CadruDidactic c) throws IOException {
        String folderName = "prof" + c.getId();
        Path profDir = Paths.get("cadreDidactice", folderName);

        if (Files.exists(profDir) && Files.isDirectory(profDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(profDir)) {
                for (Path file : stream) {
                    Files.delete(file);
                }
            }
            Files.delete(profDir);
            System.out.println("Folder și conținut șters: " + profDir);
        } else {
            System.out.println("Folderul nu există sau nu este director: " + profDir);
        }
    }

    public void deleteTutoreFolder(Tutore t) throws IOException {
        String folderName = "tutore" + t.getId();
        Path tutoreDir = Paths.get("tutori", folderName);

        if (Files.exists(tutoreDir) && Files.isDirectory(tutoreDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(tutoreDir)) {
                for (Path file : stream) {
                    Files.delete(file);
                }
            }
            Files.delete(tutoreDir);
            System.out.println("Folder și conținut șters: " + tutoreDir);
        } else {
            System.out.println("Folderul nu există sau nu este director: " + tutoreDir);
        }
    }
}
