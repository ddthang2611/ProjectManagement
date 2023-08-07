package com.project.controller;
import com.itextpdf.text.DocumentException;
import com.project.entity.*;
import com.project.entity.enums.UserRole;
import com.project.helper.CookieHelper;
import com.project.service.*;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

@Controller
public class AnalysisController {

    @Autowired
    private EmployeeAnalysisService employeeAnalysisService;
    @Autowired
    private ProjectVersionService projectVersionService;
    @Autowired
    private ProjectVersionAnalysisService projectVersionAnalysisService;
    @Autowired
    private UserService userService;
    @Autowired
    private CookieHelper cookieHelper;
    @Autowired
    LineGraphEmployeeTaskAnalysisService lineGraphEmployeeTaskAnalysisService;

    @GetMapping("/analysis")
    public String getAnalysisMainPage(Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        return "analysis/MainPage";
    }

//    @GetMapping("/hi")
//    public ResponseEntity<ByteArrayResource> generateFile() throws IOException {
//        // Create the XWPFDocument using the createReport method
//        List<EmployeeAnalysis> employeeAnalyses = getListEmployeeAnalysis();
//        XWPFDocument document = createReport(employeeAnalyses); // Provide the list of employees as needed
//
//        // Convert the XWPFDocument to a downloadable format (DOCX) using ByteArrayOutputStream
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        document.write(byteArrayOutputStream);
//
//        // Convert ByteArrayOutputStream to ByteArrayResource
//        ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
//
//        // Set the response headers to make the file downloadable
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=HopDongMuaBanCanHo.docx");
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//
//        // Return the file as a ResponseEntity
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(resource);
//    }

//    private XWPFDocument createReport(List<EmployeeAnalysis> employeeAnalyses) {
//
//        XWPFDocument document = new XWPFDocument();
//
//        //tao doan van ban
//        //slogan
//        XWPFParagraph slogan = document.createParagraph();
//        XWPFRun run = slogan.createRun();
//
//        slogan.setAlignment(ParagraphAlignment.CENTER);
//        String title = "CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM";
//
//        run.setFontSize(17);
//        //run.setFontFamily("Times New Roman");
//        run.setBold(true);
//        run.setText(title);
//
//        run.addBreak();
//        String title1 = "Độc lập - Tự Do - Hạnh phúc";
//
//        run.setText(title1);
//
//        run.addBreak();
//        String title4 = "------------";
//
//        run.setText(title4);
//
//        run.addBreak();
//        String title2 = "HỢP ĐỒNG MUA BÁN CĂN HỘ";
//
//        run.setText(title2);
//
//        run.addBreak();
//
//        XWPFRun run2 = slogan.createRun();
//        String title3 = "Mã Hợp Đồng : /2022";
//
//        run2.setFontSize(14);
//        run2.setText(title3);
//        //Paragraph 1
//        XWPFParagraph para1 = document.createParagraph();
//
//        para1.setAlignment(ParagraphAlignment.LEFT);
//        XWPFRun runPara1 = para1.createRun();
//
//        runPara1.setFontSize(14);
//        String text1 = "Căn cứ";
//
//        runPara1.setText(text1);
//
//        runPara1.addBreak();
//
//        runPara1.setBold(true);
//        ;
//        runPara1.setUnderline(UnderlinePatterns.THICK);
//
//        XWPFRun runPara2 = para1.createRun();
//
//        runPara2.setFontSize(14);
//        String text2 = "-  Các quy định pháp luật hiện hành.";
//        String text3 = "-  Các quyết định phê duyệt dự án.";
//
//        runPara2.setText(text2);
//
//        runPara2.addBreak();
//
//        runPara2.setText(text3);
//
//        runPara2.addBreak();
//
//        runPara2.addBreak();
//        String text4 = "Hôm nay, ngày.....tháng.....năm....., tại............., hai bên chúng tôi gồm:";
//
//        runPara2.setText(text4);
//
//        addAdditionalInformation(document, employeeAnalyses);
//        //Para3 Bên bán
//        XWPFParagraph BenBan = document.createParagraph();
//        XWPFRun runBenBanHeading = BenBan.createRun();
//
//        runBenBanHeading.setFontSize(17);
//        runBenBanHeading.setBold(true);
//        String text5 = "1. BÊN BÁN CĂN HỘ ( Gọi tắt là 'Bên A'):";
//        String text6 = "     Tập Đoàn Việt Anh";
//
//        runBenBanHeading.setText(text5);
//
//        runBenBanHeading.addBreak();
//
//        XWPFRun runBenBan1 = BenBan.createRun();
//
//        runBenBan1.setFontSize(14);
//        runBenBan1.setText(text6);
//        runBenBan1.addBreak();
//
//        String truSo = "     Trụ sở chính: Tầng 3, Tòa nhà C’land, Khu đô thị Royal City, 72A Nguyễn Trãi , Thanh Xuân, Hà Nội";
//        String sdt = "     Điện thoại: 0981 682 633";
//        String fax = "Fax: 0981 696 969";
//        String taikhoan = "     Tài khoản số: 0308213567213";
//        String masothue = "     Mã số thuế: MSTBCL0701";
//        String nguoidaidien = "     Đại diện bên A: ";
//        String chucvu = "     Mã NV: ";
//        String text7 = "(Theo Giấy ủy quyền ngày.....tháng.....năm..... của...............................)";
//
//        runBenBan1.setText(truSo);
//
//        runBenBan1.addBreak();
//
//        runBenBan1.setText(sdt);
//
//        runBenBan1.addTab();
//
//        runBenBan1.addTab();
//
//        runBenBan1.addTab();
//
//        runBenBan1.setText(fax);
//
//        runBenBan1.addBreak();
//
//        runBenBan1.setText(taikhoan);
//
//        runBenBan1.addBreak();
//
//        runBenBan1.setText(masothue);
//
//        runBenBan1.addBreak();
//
//        runBenBan1.setText(nguoidaidien);
//
//        runBenBan1.addTab();
//
//        runBenBan1.addTab();
//
//        runBenBan1.setText(chucvu);
//
//        runBenBan1.addBreak();
//
//        runBenBan1.setText(text7);
//        //Para4 Bên mua
//        XWPFParagraph BenMua = document.createParagraph();
//        XWPFRun runBenMuaHeading = BenMua.createRun();
//
//        runBenMuaHeading.setFontSize(17);
//        runBenMuaHeading.setBold(true);
//        String text8 = "2. BÊN MUA CĂN HỘ ( Gọi tắt là 'Bên B'):";
//
//        runBenMuaHeading.setText(text8);
//        runBenMuaHeading.addBreak();
//
//        XWPFRun runBenMua1 = BenMua.createRun();
//        runBenMua1.setFontSize(14);
//        String text9 = "     Ông/Bà: ";
//        String ngaysinh = "     Ngày sinh: ";
//        String socmt = "     Số CMND: ";
//        String diachi1 = "     Địa chỉ: ";
//        String sdt1 = "     Số điện thoại: ";
//
//        runBenMua1.setText(text9);
//
//        runBenMua1.addBreak();
//
//        runBenMua1.setText(ngaysinh);
//
//        runBenMua1.addBreak();
//
//        runBenMua1.setText(socmt);
//
//        runBenMua1.addBreak();
//
//        runBenMua1.setText(diachi1);
//
//        runBenMua1.addBreak();
//
//        runBenMua1.setText(sdt1);
//
//        // Para 5 Mua Bán
//        XWPFParagraph MuaBan = document.createParagraph();
//        XWPFRun runMuaBanHeading = MuaBan.createRun();
//        XWPFRun runMuaBan = MuaBan.createRun();
//
//        runMuaBanHeading.setFontSize(17);
//        runMuaBanHeading.setBold(true);
//        runMuaBan.setFontSize(14);
//        //  runMuaBan.setBold(true);
//        String mb1 = "3. THÔNG TIN MUA BÁN :";
//        String mb2 = "     Bên A có đầy đủ quyền sở hữu hợp pháp đối với căn hộ :";
//        String mb3 = "     Mã Căn Hộ: ";
//        String mb4 = "     Diện Tích:  m²";
//        String mb5 = "     Số Phòng: ";
//        String mb6 = "     Khu Căn Hộ: ";
//
//        String mb7 = "     Bằng hợp đồng này Bên A đồng ý bán, Bên B đồng ý mua toàn bộ căn hộ nêu trên với những điều khoản thỏa thuận dưới đây:";
//        String mb8 = "     1. Giá mua bán hai bên thoả thuận là đồng (trả bằng tiền Nhà nước Việt Nam hiện hành) đã bao gồm các loại thuế phí. Nếu có thuế phí phát sinh sẽ do bên A chịu trách nghiệm chi trả";
//        String mb9 = "     2. Bên A có nghĩa vụ giao căn hộ nêu trên cùng toàn bộ bản chính giấy tờ hợp pháp về quyền sở hữu căn hộ cho Bên B.";
//        String mb10 = "     3. Việc giao nhận căn hộ và giấy tờ kèm theo do hai bên tự thực hiện và chịu trách nhiệm trước pháp luật.";
//        String mb11 = "     4. Bên A đã nhận đầy đủ số tiền đặt cọc trước của bên B có giá trị là  đồng.";
//        String mb12 = "     5. Bên B có nghĩa vụ thanh toán số tiền còn lại là đồng cho bên A đúng thời hạn theo  đợt và đăng ký quyền sở hữu căn hộ tại cơ quan có thẩm quyền theo quy định của pháp luật.";
//        String mb13 = "     6. Chi tiết các đợt thanh toán: ";
//
//        XWPFParagraph paragraph = document.createParagraph();
//
//        XWPFRun runHeading = paragraph.createRun();
//        runHeading.setFontSize(14);
//        runHeading.setBold(true);
//        runHeading.setText("THÔNG TIN NHÂN VIÊN");
//
//        // Create a table to display employee information
//        XWPFTable table = document.createTable(employeeAnalyses.size() + 1, 6);
//
//        // Set table width
//        CTTblWidth tableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
//        tableWidth.setType(STTblWidth.DXA);
//        tableWidth.setW(BigInteger.valueOf(8500));
//
//        // Set table borders
//        table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 1, "000000");
//        table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 1, "000000");
//        table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 1, "000000");
//        table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 1, "000000");
//
//        // Set table header row
//        XWPFTableRow headerRow = table.getRow(0);
//        headerRow.getCell(0).setText("Tên nhân viên");
//        headerRow.getCell(1).setText("Công việc đã giao");
//        headerRow.getCell(2).setText("Công việc hoàn thành");
//        headerRow.getCell(3).setText("Công việc quá hạn");
//        headerRow.getCell(4).setText("Số ngày trễ trung bình");
//
//        // Format decimal numbers with two decimal places
//        DecimalFormat decimalFormat = new DecimalFormat("#.##");
//
//        // Set data rows
//        for (int i = 0; i < employeeAnalyses.size(); i++) {
//            EmployeeAnalysis employeeAnalysis = employeeAnalyses.get(i);
//            XWPFTableRow dataRow = table.getRow(i + 1);
//            dataRow.getCell(0).setText(employeeAnalysis.getUserName());
//            dataRow.getCell(1).setText(String.valueOf(employeeAnalysis.getAssignedTasks()));
//            dataRow.getCell(2).setText(String.valueOf(employeeAnalysis.getCompletedTasks()));
//            dataRow.getCell(3).setText(String.valueOf(employeeAnalysis.getOverdueTasks()));
//            dataRow.getCell(4).setText(decimalFormat.format(employeeAnalysis.getAvgDaysOverdue()));
//
//        }
////        ArrayList<String> DTT = new ArrayList<>();
////        for (int i = 0; i < dateDTT.size(); i++) {
////            DTT.add("   Số Tiền Đợt " + (i + 1) + ": " + convertMoney(moneyDTT.get(i)) + "                                           Thời Hạn: " + dateDTT.get(i));
////        }
////        String mb14 = "";
////        String mb15 = "   7. Trong mỗi đợt thanh toán, Bên B có thể chia ra thành các giao dịch nhỏ thanh toán một phần số tiền.";
////        String mb16 = "   8. Đến hết đợt thanh toán nếu Bên B vẫn chưa thanh toán hết số tiền, số tiền còn lại sẽ được tính lãi 0.5% cho mỗi tháng chậm trễ";
////        String mb17 = "     Bản Hợp đồng này có hiệu lực ngay sau khi hai bên ký kết và được lập thành 05 (năm) bản, có giá trị như nhau, mỗi bên giữ 02 (hai) bản và lưu một (01) bản tại Phòng Công chứng.";
////        String mb18 = "     Mọi sửa đổi, bổ sung hoặc huỷ bỏ Hợp đồng này chỉ có giá trị khi được hai bên đồng thuận và lập thành văn bản có đầy đủ chữ ký của các bên và chỉ được thực hiện khi Bên mua chưa đăng ký sang tên quyền sở hữu theo Hợp đồng này.";
////
////        runMuaBanHeading.setText(mb1);
////        runMuaBanHeading.addBreak();
////
////        run(runMuaBan, mb2);
////        run(runMuaBan, mb3);
////        run(runMuaBan, mb4);
////        run(runMuaBan, mb5);
////        run(runMuaBan, mb6);
////        run(runMuaBan, mb7);
////        run(runMuaBan, mb8);
////        run(runMuaBan, mb9);
////        run(runMuaBan, mb10);
////        run(runMuaBan, mb11);
////        run(runMuaBan, mb12);
////        run(runMuaBan, mb13);
////        for (int i = 0; i < DTT.size(); i++) {
////            run(runMuaBan, DTT.get(i));
////        }
////        run(runMuaBan, mb14);
////        run(runMuaBan, mb15);
////        run(runMuaBan, mb16);
////        run(runMuaBan, mb17);
////        run(runMuaBan, mb18);
//        //Ký tên
//        XWPFTable KyTen = document.createTable();
//
//        KyTen.setWidth(
//                "9500");
//
//        KyTen.setTableAlignment(TableRowAlign.CENTER);
//
//        KyTen.removeBorders();
//
//        XWPFParagraph para3 = document.createParagraph();
//
//        para3.setAlignment(ParagraphAlignment.CENTER);
//
//        XWPFTableRow rowOne = KyTen.getRow(0);
//
//        rowOne.getCell(0).setParagraph(para3);
//        rowOne.getCell(0).setText("BÊN A");
//        rowOne.addNewTableCell()
//                .setParagraph(para3);
//        rowOne.getCell(1).setText("BÊN B");
//
//        XWPFTableRow rowTwo = KyTen.createRow();
//
//        rowTwo.getCell(0).setParagraph(para3);
//        rowTwo.getCell(0).setText("(Ký ghi rõ họ tên, nếu là tổ chức mua nhà thì đóng dấu của tổ chức)");
//
//        rowTwo.getCell(1).setParagraph(para3);
//        rowTwo.getCell(1).setText("(Ký ghi rõ họ tên, chức vụ và đóng dấu của doanh nghiệp)");
//
//
//        //ghi file
//        return document;
//    }

//    private void addAdditionalInformation(XWPFDocument document, List<EmployeeAnalysis> employeeAnalysisList) {
//        XWPFParagraph paragraph = document.createParagraph();
//        paragraph.setSpacingAfter(300);
//
//        XWPFRun runHeading = paragraph.createRun();
//        runHeading.setFontSize(14);
//        runHeading.setBold(true);
//        runHeading.setText("THÔNG TIN BỔ SUNG");
//
//        // Find employee with the most assigned tasks
//        EmployeeAnalysis mostAssignedTasksEmployee = employeeAnalysisList.stream()
//                .max(Comparator.comparingInt(EmployeeAnalysis::getAssignedTasks))
//                .orElse(null);
//
//        // Find employee with the least assigned tasks
//        EmployeeAnalysis leastAssignedTasksEmployee = employeeAnalysisList.stream()
//                .min(Comparator.comparingInt(EmployeeAnalysis::getAssignedTasks))
//                .orElse(null);
//
//        // Find employee with the most overdue tasks
//        EmployeeAnalysis mostOverdueTasksEmployee = employeeAnalysisList.stream()
//                .max(Comparator.comparingInt(EmployeeAnalysis::getOverdueTasks))
//                .orElse(null);
//
//        // Find employee with the least overdue tasks
//        EmployeeAnalysis leastOverdueTasksEmployee = employeeAnalysisList.stream()
//                .min(Comparator.comparingInt(EmployeeAnalysis::getOverdueTasks))
//                .orElse(null);
//
//        // Find employee with the highest average days overdue
//        EmployeeAnalysis highestAvgDaysOverdueEmployee = employeeAnalysisList.stream()
//                .max(Comparator.comparingDouble(EmployeeAnalysis::getAvgDaysOverdue))
//                .orElse(null);
//
//        // Find employee with the lowest average days overdue
//        EmployeeAnalysis lowestAvgDaysOverdueEmployee = employeeAnalysisList.stream()
//                .min(Comparator.comparingDouble(EmployeeAnalysis::getAvgDaysOverdue))
//                .orElse(null);
//
//        // Add the information to the document
//        XWPFRun runInfo = paragraph.createRun();
//        runInfo.setFontSize(12);
//
//        runInfo.addBreak();
//        runInfo.setText("Nhân viên có nhiều công việc nhất: " + mostAssignedTasksEmployee.getUserName() +
//                " (Số công việc: " + mostAssignedTasksEmployee.getAssignedTasks() + ")");
//
//        runInfo.addBreak();
//        runInfo.setText("Nhân viên có ít công việc nhất: " + leastAssignedTasksEmployee.getUserName() +
//                " (Số công việc: " + leastAssignedTasksEmployee.getAssignedTasks() + ")");
//
//        runInfo.addBreak();
//        runInfo.setText("Nhân viên có nhiều công việc quá hạn nhất: " + mostOverdueTasksEmployee.getUserName() +
//                " (Số công việc quá hạn: " + mostOverdueTasksEmployee.getOverdueTasks() + ")");
//
//        runInfo.addBreak();
//        runInfo.setText("Nhân viên có ít công việc quá hạn nhất: " + leastOverdueTasksEmployee.getUserName() +
//                " (Số công việc quá hạn: " + leastOverdueTasksEmployee.getOverdueTasks() + ")");
//
//        runInfo.addBreak();
//        runInfo.setText("Nhân viên có số ngày trễ trung bình cao nhất: " + highestAvgDaysOverdueEmployee.getUserName() +
//                " (Số ngày trễ trung bình: " + highestAvgDaysOverdueEmployee.getAvgDaysOverdue() + ")");
//
//        runInfo.addBreak();
//        runInfo.setText("Nhân viên có số ngày trễ trung bình thấp nhất: " + lowestAvgDaysOverdueEmployee.getUserName() +
//                " (Số ngày trễ trung bình: " + lowestAvgDaysOverdueEmployee.getAvgDaysOverdue() + ")");
//    }

//    private String creatceReport(List<EmployeeAnalysis> employeeList) {
//        String report = "CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM\n";
//        report += "Độc lập - Tự do - Hạnh phúc\n";
//        report += "\nBÁO CÁO VỀ TÌNH TRẠNG GIAO VIỆC CHO NHÂN VIÊN\n";
//
//   // Hàm lấy danh sách nhân viên từ database hoặc list đầu vào
//
//        // Thông tin nhân viên có số công việc nhiều nhất
//        EmployeeAnalysis mostAssigned = null;
//        // Thông tin nhân viên có số công việc ít nhất
//        EmployeeAnalysis leastAssigned = null;
//        // Thông tin nhân viên có số công việc muộn nhiều nhất
//        EmployeeAnalysis mostOverdue = null;
//        // Thông tin nhân viên có số công việc muộn ít nhất
//        EmployeeAnalysis leastOverdue = null;
//
//        int maxAssignedTasks = 0;
//        int minAssignedTasks = Integer.MAX_VALUE;
//        int maxOverdueTasks = 0;
//        int minOverdueTasks = Integer.MAX_VALUE;
//
//        double maxAvgDaysOverdue = 0.0;
//        double minAvgDaysOverdue = Double.MAX_VALUE;
//
//        for (EmployeeAnalysis employee : employeeList) {
//            if (employee.getAssignedTasks() > maxAssignedTasks) {
//                mostAssigned = employee;
//                maxAssignedTasks = employee.getAssignedTasks();
//            }
//            if (employee.getAssignedTasks() < minAssignedTasks) {
//                leastAssigned = employee;
//                minAssignedTasks = employee.getAssignedTasks();
//            }
//            if (employee.getOverdueTasks() > maxOverdueTasks) {
//                mostOverdue = employee;
//                maxOverdueTasks = employee.getOverdueTasks();
//            }
//            if (employee.getOverdueTasks() < minOverdueTasks) {
//                leastOverdue = employee;
//                minOverdueTasks = employee.getOverdueTasks();
//            }
//            if (employee.getAvgDaysOverdue() > maxAvgDaysOverdue) {
//                maxAvgDaysOverdue = employee.getAvgDaysOverdue();
//            }
//            if (employee.getAvgDaysOverdue() < minAvgDaysOverdue) {
//                minAvgDaysOverdue = employee.getAvgDaysOverdue();
//            }
//        }
//
//        report += "\nNhân viên có số công việc nhiều nhất: " + mostAssigned.getUserName() + " - " + mostAssigned.getAssignedTasks() + " công việc";
//        report += "\nNhân viên có số công việc ít nhất: " + leastAssigned.getUserName() + " - " + leastAssigned.getAssignedTasks() + " công việc";
//        report += "\nNhân viên có số công việc muộn nhiều nhất: " + mostOverdue.getUserName() + " - " + mostOverdue.getOverdueTasks() + " công việc";
//        report += "\nNhân viên có số công việc muộn ít nhất: " + leastOverdue.getUserName() + " - " + leastOverdue.getOverdueTasks() + " công việc";
//        report += "\nNhân viên có số ngày trễ trung bình nhiều nhất: " + mostOverdue.getUserName() + " - " + String.format("%.2f", maxAvgDaysOverdue) + " ngày";
//        report += "\nNhân viên có số ngày trễ trung bình ít nhất: " + leastOverdue.getUserName() + " - " + String.format("%.2f", minAvgDaysOverdue) + " ngày";
//
//        // Thêm Hà Nội và ngày tháng năm vào báo cáo
//        report += "\n\nHà Nội, ngày tháng năm: " + java.time.LocalDate.now();
//
//        return report;
//    }


private List<EmployeeAnalysis> getListEmployeeAnalysis(){
    List<UserDTO> users = userService.getAllUsers();
    users.removeIf(user -> user.getRole().equals(UserRole.MANAGER));
    List<EmployeeAnalysis> employeeAnalyses = new ArrayList<>();

    for (UserDTO user : users) {
        EmployeeAnalysis employeeAnalysis = employeeAnalysisService.getEmployeeAnalysis(user.getUserId());
        System.out.println(employeeAnalysis.toString());
        employeeAnalyses.add(employeeAnalysis);
    }
    return employeeAnalyses;
}
@GetMapping("/analysis/bar-chart/user-task")
    public String getUserAnalysis(Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        List<UserDTO> users = userService.getAllUsers();
        users.removeIf(user -> user.getRole().equals(UserRole.MANAGER));
        List<EmployeeAnalysis> employeeAnalyses = new ArrayList<>();

        for (UserDTO user : users) {
            EmployeeAnalysis employeeAnalysis = employeeAnalysisService.getEmployeeAnalysis(user.getUserId());
            System.out.println(employeeAnalysis.toString());
            employeeAnalyses.add(employeeAnalysis);
        }

        model.addAttribute("employeeAnalyses", employeeAnalyses);

        return "analysis/BarChart_UserTask";
    }
    @GetMapping("/analysis/pie-chart/user-task")
    public String getUserAnalysisPieChart(Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        List<UserDTO> users = userService.getAllUsers();
        users.removeIf(user -> user.getRole().equals(UserRole.MANAGER));
        List<Map<String, Object>> employeeTasks = new ArrayList<>();

        for (UserDTO user : users) {
            int employeeId = user.getUserId();
            int tasksCount = employeeAnalysisService.countTasksByEmployeeId(employeeId);
            String employeeName = user.getUsername();
            System.out.println(employeeId+":"+tasksCount);
            Map<String, Object> employeeTaskMap = new HashMap<>();
            employeeTaskMap.put("userName", employeeName);
            employeeTaskMap.put("taskCount", tasksCount);
            employeeTasks.add(employeeTaskMap);
        }

        model.addAttribute("employeeTasks", employeeTasks);

        int totalTasks = employeeAnalysisService.getTotalTask();
        System.out.println(totalTasks);
        model.addAttribute("totalTasks", totalTasks);

        return "analysis/PieChart_UserTask";
    }
    @GetMapping("/analysis/bar-chart/version-task")
    public String getProjectVersionAnalysis(Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);

        List<ProjectVersion> projectVersions = projectVersionService.findAll();
        List<ProjectVersionAnalysis> projectVersionAnalyses = new ArrayList<>();

        for (ProjectVersion projectVersion : projectVersions) {
            ProjectVersionAnalysis projectVersionAnalysis = projectVersionAnalysisService.getProjectVersionAnalysis(projectVersion.getProjectVersionId());
            projectVersionAnalyses.add(projectVersionAnalysis);
        }

        model.addAttribute("projectVersionAnalyses", projectVersionAnalyses);

        return "analysis/BarChart_VersionTask";

    }
    @GetMapping("/analysis/line-graph/user-task")
    public String getLineGraphEmployeeTask(Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);

        // Lấy danh sách LineGraphEmployeeTaskAnalysis từ service
        List<LineGraphEmployeeTaskAnalysis> lineGraphDataList = lineGraphEmployeeTaskAnalysisService.getAllLineGraphData();

        // Thêm danh sách LineGraphEmployeeTaskAnalysis vào model
        model.addAttribute("lineGraphDataList", lineGraphDataList);

        return "analysis/LineGraph_UserTask"; // Trả về tên view mà bạn muốn hiển thị dữ liệu
    }

//    @PostMapping("/analysis/user/download")
//    public ResponseEntity<FileSystemResource> downloadEmployeeAnalysis(RedirectAttributes redirectAttributes) {
//        // TODO: Implement logic to retrieve employee analysis data from the list of EmployeeAnalysis
//        List<EmployeeAnalysis> employeeAnalyses = // Your code to get the list of EmployeeAnalysis
//
//                String fileName = "employee_analysis_report.pdf";
//        String fole  = "employee_analysis_report.pdf";
//
//        try {
//            employeeAnalysisService.generateReport(employeeAnalyses);
//            redirectAttributes.addFlashAttribute("message", "Báo cáo đã được tạo và tải xuống thành công!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi tạo báo cáo: " + e.getMessage());
//        }
//
//        // Tạo đường dẫn tới file PDF đã được tạo
//        String pathToDownload = "/path/to/your/directory/" + fileName;
//        FileSystemResource file = new FileSystemResource(pathToDownload);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
//
//        // Trả về response với file PDF để trình duyệt có thể tải xuống
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(file);
//    }

}

