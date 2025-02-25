package ro.upt.ac.portofolii.admin;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.upt.ac.portofolii.utils.PasswordGenerator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class AdminService {

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
        return "File " + file.getOriginalFilename() + "was copied to" + filePath + "\n";
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
                String nume = record.get(0);
                String prenume = record.get(1);
                String email = record.get(13);
                String password = new PasswordGenerator().generateRandomPassword();

                writer.write(String.join(",", nume, prenume, email, password));
                writer.newLine();
            }
        }
        return "Generated credentials in " + outputFile;
    }
}
