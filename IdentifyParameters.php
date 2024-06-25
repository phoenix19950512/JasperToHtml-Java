<?php
// Define paths
$javaExecutable = 'java'; // Adjust the path if necessary
$extractorClass = 'ParameterIdentifier';
$jasperFilePath = 'report'; // Update this path

// Construct the command
$command = sprintf(
  '%s -cp ".;lib/*" %s %s',
  escapeshellcmd($javaExecutable),
  escapeshellcmd($extractorClass),
  escapeshellarg("Files/" . $jasperFilePath . ".jasper")
);

// Provide font
// jasperstarter pr /path/to/your/report.jasper -f pdf -o /path/to/output/report.pdf -P param1=value1 -P param2=value2 -f /path/to/font.ttf

// Execute the command
exec($command, $output, $return_var);

if ($return_var === 0) {
  echo "Parameters required by the Jasper file:\n";
  foreach ($output as $parameter) {
    echo $parameter . "\n";
  }
} else {
  echo "Failed to extract parameters. Command output: " . implode("\n", $output);
}

