<?php
// Path to JasperStarter executable
$jasperStarterPath = 'JasperStarter\bin\jasperstarter.exe';

// Path to the jrxml file
$jrxmlFile = 'Files\list.jrxml';

// Path to the output PDF file
$outputPdf = 'HTML\report';

// Parameters to pass to the report
$parameters = [
  'Delegation_name' => 'Value for Delegation_name',
  'Classe_name' => 'Value for Classe_name',
  'Comune_name' => 'قيمة',
  'School_name' => 'Value for School_name',
  'Section_name' => 'Value for Section_name',
  'Scholarization_year' => 'Value for Scholarization_year',
  'number_in_classe' => 'Value for number_in_classe',
  'Code_massar' => 'Value for Code_massar',
  'Student_full_name' => 'Value for Student_full_name',
  'Gender' => 'Value for Gender',
  'Date_of_b' => 'Value for Date_of_b',
  'Place_of_b' => 'Value for Place_of_b',
];

// Convert parameters to a string format suitable for JasperStarter
$paramString = '';
foreach ($parameters as $key => $value) {
    $paramString .= ' ' . $key . '=' . escapeshellarg($value);
}

// Command to execute JasperStarter
$command = $jasperStarterPath . ' pr ' . escapeshellarg($jrxmlFile) . ' -f pdf -o ' . escapeshellarg($outputPdf) . ' -P ' . $paramString;

// Execute the command
exec($command, $output, $return_var);

if ($return_var === 0) {
    echo "Report generated successfully.";
} else {
    echo "Error generating report. Command output: " . implode("\n", $output);
}
