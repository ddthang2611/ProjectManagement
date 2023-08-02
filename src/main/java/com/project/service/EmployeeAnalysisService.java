package com.project.service;

import com.itextpdf.text.*;
import com.project.entity.EmployeeAnalysis;
import com.project.entity.User;
import com.project.repository.TaskRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;

@Service

public class EmployeeAnalysisService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;
    public EmployeeAnalysis getEmployeeAnalysis(int employeeId) {
        User user = userRepository.findById(employeeId).orElse(null);
        String employeeName = user.getUsername();

        int assignedTasks = taskRepository.countByAssignedTo(employeeId);
        int completedTasks = taskRepository.countCompletedTasks(employeeId);
        LocalDate currentDate = LocalDate.now();
        Date convertedDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Double averageDaysOverdue = taskRepository.calculateAverageDaysOverdue(employeeId, convertedDate);
        double avgDaysOverdue = averageDaysOverdue != null ? averageDaysOverdue.doubleValue() : 0.0;
        int overdueTasks = taskRepository.countOverdueTasks(employeeId, convertedDate);
        return new EmployeeAnalysis(assignedTasks, completedTasks, overdueTasks, avgDaysOverdue,employeeName);
    }
    public int countTasksByEmployeeId(int employeeId){
        return taskRepository.countByAssignedTo(employeeId);
    }

    public int getTotalTask(){
        return taskRepository.getTotalTask();
    }

    // Hàm này sẽ tạo báo cáo và trả về mảng byte chứa dữ liệu của file PDF
//    public byte[] generateAnalysisReport(List<EmployeeAnalysis> employeeAnalysisList) throws DocumentException, IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        Document document = new Document();
//        PdfWriter.getInstance(document, outputStream);
//
//        // Bắt đầu tạo báo cáo
//        document.open();
//
//        // Thêm tiêu đề và quốc hiệu tiêu ngữ
//        document.add(new Paragraph("Cộng hòa xã hội chủ nghĩa Việt Nam"));
//        document.add(new Paragraph("Độc lập – Tự do – Hạnh phúc"));
//        document.add(new Paragraph("Báo Cáo Phân Tích Hiệu Suất Làm Việc Của Nhân Viên"));
//        document.add(new Paragraph(" ")); // Add a blank line
//
//        // Thêm các thông tin phân tích về nhân viên vào báo cáo
//        PdfPTable table = new PdfPTable(6); // 6 cột tương ứng với các thông số phân tích
//        table.setWidthPercentage(100);
//
//        // Tiêu đề của bảng
//        String[] headers = { "User Name", "Assigned Tasks", "Completed Tasks", "Overdue Tasks", "Avg Days Overdue", "Performance" };
//        for (String header : headers) {
//            PdfPCell cell = new PdfPCell(new Paragraph(header, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(cell);
//        }
//
//        // Thêm dữ liệu từ danh sách EmployeeAnalysis vào bảng
//        for (EmployeeAnalysis analysis : employeeAnalysisList) {
//            table.addCell(analysis.getUserName());
//            table.addCell(String.valueOf(analysis.getAssignedTasks()));
//            table.addCell(String.valueOf(analysis.getCompletedTasks()));
//            table.addCell(String.valueOf(analysis.getOverdueTasks()));
//            table.addCell(String.valueOf(analysis.getAvgDaysOverdue()));
//            table.addCell(getPerformanceDescription(analysis)); // Hàm này sẽ trả về phân tích hiệu suất (Performance) dựa trên các chỉ số
//
//        }
//
//        document.add(table);
//
//        // Cuối báo cáo, thêm địa điểm và ngày tháng
//        document.add(new Paragraph("Hà Nội"));
//        document.add(new Paragraph("Ngày tháng năm: " + java.time.LocalDate.now()));
//
//        document.close();
//
//        return outputStream.toByteArray();
//    }
//
//    // Hàm này sẽ trả về phân tích hiệu suất (Performance) dựa trên các chỉ số
//    private String getPerformanceDescription(EmployeeAnalysis analysis) {
//        // Viết mã logic để xác định hiệu suất dựa vào các chỉ số của nhân viên
//        // Ví dụ: "Tốt", "Kém", "Trung bình", ...
//        return "Tốt";
//    }
//
//
//
//    public ByteArrayInputStream generatePdfReport(List<EmployeeAnalysis> employeeAnalyses, HttpServletResponse response) {
//
//        Document document = new Document(PageSize.A4);
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        try {
//            PdfWriter.getInstance(document, out);
//            document.open();
//
//            // Header
//            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
//            Paragraph header = new Paragraph("Cộng hòa xã hội chủ nghĩa Việt Nam\n\nĐộc lập – Tự do – Hạnh phúc", headerFont);
//            header.setAlignment(Element.ALIGN_CENTER);
//            document.add(header);
//
//            // Report content
//            PdfPTable table = new PdfPTable(6);
//            table.setWidthPercentage(100);
//
//            // Add table headers
//            Font tableHeaderFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//            table.addCell(new PdfPCell(new Paragraph("User Name", tableHeaderFont)));
//            table.addCell(new PdfPCell(new Paragraph("Assigned Tasks", tableHeaderFont)));
//            table.addCell(new PdfPCell(new Paragraph("Completed Tasks", tableHeaderFont)));
//            table.addCell(new PdfPCell(new Paragraph("Overdue Tasks", tableHeaderFont)));
//            table.addCell(new PdfPCell(new Paragraph("Avg. Days Overdue", tableHeaderFont)));
//            table.addCell(new PdfPCell(new Paragraph("Performance", tableHeaderFont)));
//
//            // Add table rows
//            Font contentFont = new Font(Font.FontFamily.HELVETICA, 10);
//            for (EmployeeAnalysis analysis : employeeAnalyses) {
//                System.out.println(analysis.toString());
//                table.addCell(new PdfPCell(new Paragraph(analysis.getUserName(), contentFont)));
//                table.addCell(new PdfPCell(new Paragraph(String.valueOf(analysis.getAssignedTasks()), contentFont)));
//                table.addCell(new PdfPCell(new Paragraph(String.valueOf(analysis.getCompletedTasks()), contentFont)));
//                table.addCell(new PdfPCell(new Paragraph(String.valueOf(analysis.getOverdueTasks()), contentFont)));
//                table.addCell(new PdfPCell(new Paragraph(String.valueOf(analysis.getAvgDaysOverdue()), contentFont)));
//
//                // Perform some analysis here to determine employee performance
//                String performance = ""; // Perform your analysis and set the performance value
//                table.addCell(new PdfPCell(new Paragraph(performance, contentFont)));
//            }
//
//            document.add(table);
//
//            // Footer
//            Font footerFont = new Font(Font.FontFamily.HELVETICA, 12);
//            Paragraph footer = new Paragraph("Hà Nội, Ngày tháng năm", footerFont);
//            footer.setAlignment(Element.ALIGN_RIGHT);
//            document.add(footer);
//
//            document.close();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
//        return new ByteArrayInputStream(out.toByteArray());
//    }
}
