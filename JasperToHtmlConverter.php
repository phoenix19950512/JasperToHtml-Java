<?php

$javaExecutable = 'java'; // Adjust the path if necessary
$extractorClass = 'JasperToHtmlConverter';
$jasperFilePath = 'list'; // Jasper file name without extension

// Construct the command
$command = sprintf(
  '%s -cp ".;lib/*" %s %s',
  escapeshellcmd($javaExecutable),
  escapeshellcmd($extractorClass),
  escapeshellarg($jasperFilePath)
);

exec($command, $output, $return_var);

if ($return_var === 0) {
    echo "PDF file generated successfully at " . $outputFilePath;
} else {
    echo "Failed to generate PDF file. Command output: " . implode("\n", $output);
}
