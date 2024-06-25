import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

public class ParameterIdentifier {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ParameterIdentifier <jasper_file>");
            System.exit(1);
        }

        String jasperFilePath = args[0];

        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasperFilePath);
            JRParameter[] parameters = jasperReport.getParameters();

            StringBuilder missingParams = new StringBuilder();

            for (JRParameter parameter : parameters) {
                if (!parameter.isSystemDefined()) {
                    missingParams.append(parameter.getName()).append(",");
                }
            }

            if (missingParams.length() > 0) {
                missingParams.deleteCharAt(missingParams.length() - 1); // Remove the last comma
                System.out.println(missingParams.toString());
            } else {
                System.out.println("No missing parameters found.");
            }

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
