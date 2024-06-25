import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.util.Map;

public class JasperReportGenerator {
    public static void generateReport(String jrxmlFile, Map<String, Object> parameters, String pdfFile) throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
        exporter.exportReport();
    }
}
