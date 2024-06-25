import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;

import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JasperToHtmlConverter {

    public static void main(String[] args) {
        try {
            String jasperFilePath = args[0];
            String outputHtmlFilePath = args[1];
            
            Map<String, Object> parameters = new HashMap<>();
            for (int i = 2; i < args.length; i++) {
                String[] param = args[i].split("=", 2);
                if (param.length == 2) {
                    String key = URLDecoder.decode(param[0], StandardCharsets.UTF_8.toString());
                    String value = URLDecoder.decode(param[1], StandardCharsets.UTF_8.toString());
                    parameters.put(key, value);
                }
            }
            
            JRDataSource dataSource = new JREmptyDataSource();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFilePath, parameters, dataSource);
            
            HtmlExporter exporter = new HtmlExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(new FileOutputStream(outputHtmlFilePath)));
            
            SimpleHtmlReportConfiguration reportConfig = new SimpleHtmlReportConfiguration();
            reportConfig.setEmbedImage(true);
            reportConfig.setRemoveEmptySpaceBetweenRows(true);
            reportConfig.setWhitePageBackground(false);
            
            exporter.setConfiguration(reportConfig);
            
            exporter.exportReport();
            
            System.out.println("HTML file generated successfully.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
