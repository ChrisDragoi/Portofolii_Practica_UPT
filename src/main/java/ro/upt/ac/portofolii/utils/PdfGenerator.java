package ro.upt.ac.portofolii.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import ro.upt.ac.portofolii.cadruDidactic.CadruDidactic;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import ro.upt.ac.portofolii.student.Student;
import ro.upt.ac.portofolii.tutore.Tutore;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generatePortofoliuPdf(Portofoliu portofoliu) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font boldFont = loadFont(Font.BOLD);
            Font regularFont = loadFont(Font.NORMAL);

            Paragraph p = new Paragraph();
            p.add(new Chunk("ANEXĂ", boldFont));
            p.add(new Chunk(" la ", regularFont));
            p.add(new Chunk("Convenția-cadru", boldFont));
            p.add(new Chunk(" privind efectuarea stagiului de practică în cadrul programelor de studii universitare de licență/masterat ", regularFont));
            document.add(p);
            document.add(new Paragraph(" ", regularFont));
            p = new Paragraph();

            Paragraph title = new Paragraph("PORTOFOLIU DE PRACTICĂ", boldFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            p.add(new Chunk("aferent ", regularFont));
            p.add(new Chunk("Convenției-cadru", boldFont));
            p.add(new Chunk(" privind efectuarea stagiului de practică în cadrul programelor de studii universitare de licență și masterat", regularFont));
            document.add(p);
            document.add(new Paragraph(" "));

            addSection(document,1,"Durata totală a pregătirii practice:", String.valueOf(portofoliu.getDurataPracticii()), boldFont, regularFont, regularFont);
            addSection(document,2,"Calendarul pregătirii:", portofoliu.getOrar(), boldFont, regularFont, regularFont);
            addSection(document, 3,"Perioada stagiului, timpul de lucru și orarul (de precizat zilele de pregătire practică în cazul timpului de lucru parțial):", portofoliu.getDataInceput() + " - " + portofoliu.getDataSfarsit(),boldFont, regularFont, regularFont);
            addSection(document, 4,"Adresa unde se va derula stagiul de pregătire practică:", portofoliu.getLoculDesfasurarii(), boldFont,regularFont, regularFont);
            addSection(document, 5,"Deplasarea în afara locului unde este repartizat practicantul vizează următoarele locații:", portofoliu.getLocatiiExtra(),boldFont, regularFont, regularFont);
            addSection(document, 6,"Condiții de primire a studentului/masterandului în stagiul de practică:", portofoliu.getCompetenteNecesare(),boldFont, regularFont, regularFont);
            addSection(document, 7,"Modalități prin care se asigură complementaritatea între pregătirea dobândită de studentul practicant în instituția de învățământ superior și în cadrul stagiului de practică:", portofoliu.getComplementareInvatamantPractica(),boldFont, regularFont, regularFont);
            addSection(document, 8,"Numele și prenumele cadrului didactic care asigură supravegherea pedagogică a practicantului pe perioada stagiului de practică:", portofoliu.getCadruDidactic().getNume() + " " + portofoliu.getCadruDidactic().getPrenume(),boldFont, regularFont, regularFont);
            addSection(document, 9,"Drepturi și responsabilități ale cadrului didactic care asigură supravegherea pedagogică a practicantului pe perioada stagiului de practică:", "Monitorizarea activității studentului și evaluarea finală.",boldFont, regularFont, regularFont);
            addSection(document, 10,"Numele și prenumele tutorelui desemnat de întreprindere care va asigura respectarea condițiilor de pregătire și dobândirea de către practicant a competențelor profesionale planificate pentru perioada stagiului de practică:", portofoliu.getTutore().getNume() + " " + portofoliu.getTutore().getPrenume(), boldFont, regularFont, regularFont);
            addSection(document, 11,"Drepturi și responsabilități ale tutorelui de practică desemnat de partenerul de practică:", "Îndrumarea studentului și evaluarea progresului său.",boldFont, regularFont, regularFont);
            addSection(document, 12,"Tematica practicii și sarcinile studentului conform prevederilor din Fișa disciplinei a stagiului de practică:", portofoliu.getTematicaSiSarcini(),boldFont, regularFont, regularFont);
            addSection(document, 13,"Definirea competențelor care vor fi dobândite pe perioada stagiului de practică:", "",boldFont, regularFont, regularFont);


            PdfPTable table = getPdfPTable1(boldFont, regularFont, portofoliu.getCompetenteNecesare(), portofoliu.getModDePregatire(), portofoliu.getActivitatiPlanificate(), portofoliu.getObservatii());

            document.add(table);
            addSection(document, 14,"Modalități de evaluare a pregătirii profesionale dobândite de practicant pe perioada stagiului de pregătire practică:", "",boldFont, regularFont, regularFont);

            document.add(new Paragraph(" ", regularFont));
            p = new Paragraph("Evaluarea practicantului pe perioada stagiului de pregătire practică se va face de către tutore.", regularFont);
            document.add(p);

            table = getPdfPTable2(boldFont, regularFont, portofoliu);

            document.add(table);

            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Eroare la generarea PDF-ului: ", e);
        }
    }

    private static PdfPTable getPdfPTable1(
            Font boldFont,
            Font regularFont,
            String competente,
            String modPregatire,
            String activitatiPlanificate,
            String observatii) {
        PdfPTable table = new PdfPTable(new float[]{1f, 3f});
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        String[][] rows = {
                {"Competențe", competente},
                {"Modulul de pregătire", modPregatire},
                //{"Locul de muncă", ""},
                {"Activități planificate", activitatiPlanificate},
                {"Observații", observatii}
        };

        for (String[] row : rows) {
            PdfPCell labelCell = new PdfPCell(new Phrase(row[0], boldFont));
            labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            labelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            labelCell.setPadding(5);
            table.addCell(labelCell);

            PdfPCell valueCell = new PdfPCell(new Phrase(row[1], regularFont));
            valueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            valueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            valueCell.setPadding(5);
            table.addCell(valueCell);
        }

        return table;
    }

private static PdfPTable getPdfPTable2(Font boldFont, Font regularFont, Portofoliu portofoliu) {
    Student student = portofoliu.getStudent();
    CadruDidactic cadruDidactic = portofoliu.getCadruDidactic();
    Tutore tutore = portofoliu.getTutore();

    PdfPTable table = new PdfPTable(new float[]{2f, 2f, 2f, 2f});
    table.setWidthPercentage(100);
    table.setSpacingBefore(10f);
    table.setSpacingAfter(10f);

    String[] headers = {"", "Cadru Didactic", "Tutore", "Student"};
    rows(boldFont, table, headers);

    String[][] rows = {
            {"Nume și Prenume", cadruDidactic.getNume() + " " + cadruDidactic.getPrenume(), tutore.getNume() + " " + tutore.getPrenume(), student.getNume() + " " + student.getPrenume()},
            {"Funcția", "Cadru Didactic", tutore.getFunctie(), "Student"},
            {"Data", String.valueOf(portofoliu.getDataSemnarii()), String.valueOf(portofoliu.getDataSemnarii()), String.valueOf(portofoliu.getDataSemnarii())}
    };

    for (String[] row : rows) {
        rows(regularFont, table, row);
    }

    // ✅ Adăugăm semnăturile în tabel
    PdfPCell labelCell = new PdfPCell(new Phrase("Semnătura", boldFont));
    labelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    labelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    labelCell.setPadding(5);
    table.addCell(labelCell);

    // 🔹 Calea semnăturilor (trebuie să fie absolută)
    String basePath = "src/main/resources/static/";
    String studentSignaturePath = basePath + student.getSemnatura() + "/signature.png";
    String tutoreSignaturePath = basePath + tutore.getSemnatura() + "/signature.png";
    String cadruSignaturePath = basePath + cadruDidactic.getSemnatura() + "/signature.png";

    // 🔹 Adăugăm semnătura în celule (sau lăsăm gol dacă nu există)
    for (String signaturePath : new String[]{cadruSignaturePath, tutoreSignaturePath, studentSignaturePath}) {
        Image signatureImage = getSignatureImage(signaturePath);
        PdfPCell signatureCell;
        if (signatureImage != null) {
            signatureImage.scaleToFit(100, 50); // Redimensionăm imaginea
            signatureCell = new PdfPCell(signatureImage);
        } else {
            signatureCell = new PdfPCell(new Phrase("")); // Celulă goală
        }
        signatureCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        signatureCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        signatureCell.setPadding(5);
        table.addCell(signatureCell);
    }

    return table;
}


    private static void rows(Font regularFont, PdfPTable table, String[] row) {
        for (String cellValue : row) {
            PdfPCell cell = new PdfPCell(new Phrase(cellValue, regularFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(5);
            table.addCell(cell);
        }
    }


    private static void addSection(Document document, int i, String title, String content, Font nrFont, Font titleFont, Font contentFont) throws DocumentException {
        Paragraph p = new Paragraph();
        p.add(new Chunk(i+". ", nrFont));
        p.add(new Chunk(title, titleFont));
        p.add(Chunk.NEWLINE);
        p.add(new Chunk(content != null ? content : "-", contentFont));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);

        document.add(p);
    }
    private static Font loadFont(int style) {
        try {
            BaseFont baseFont = BaseFont.createFont("static/dejavu-sans/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            return new Font(baseFont, (float) 12, style);
        } catch (Exception e) {
            throw new RuntimeException("Eroare la încărcarea fontului: " + "static/dejavu-sans/DejaVuSans.ttf", e);
        }
    }

    private static Image getSignatureImage(String signaturePath) {
        try {
            return Image.getInstance(signaturePath);
        } catch (Exception e) {
            return null;
        }
    }

}