import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "src/tuocognome.csv";
        String outputFilePath = "src/tuocognome_modified.csv";

        renameFile("src/Gusmerella.csv", inputFilePath);
        addFields(inputFilePath, outputFilePath);
        calculateMaxLengths(outputFilePath);
        addPadding(outputFilePath);
        addRecord(outputFilePath, "newRecord1;newRecord2;newRecord3");
        displaySignificantFields(outputFilePath);
        searchRecord(outputFilePath, "ACQ072");
        modifyRecord(outputFilePath, "ACQ072", "modifiedRecord1;modifiedRecord2;modifiedRecord3");
        logicalDeleteRecord(outputFilePath, "ACQ072");
    }

    public static void renameFile(String oldFilePath, String newFilePath) {
        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);
        if (oldFile.renameTo(newFile)) {
            System.out.println("File renamed successfully");
        } else {
            System.out.println("Failed to rename file");
        }
    }

    public static void addFields(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String header = reader.readLine();
            writer.write(header + ";miovalore;cancellato\n");

            String line;
            Random random = new Random();
            while ((line = reader.readLine()) != null) {
                int miovalore = 10 + random.nextInt(11);
                writer.write(line + ";" + miovalore + ";false\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int countFields(String record) {
        return record.split(";").length;
    }

    public static void calculateMaxLengths(String inputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String header = reader.readLine();
            int maxRecordLength = header.length();
            int[] maxFieldLengths = new int[header.split(";").length];

            String line;
            while ((line = reader.readLine()) != null) {
                maxRecordLength = Math.max(maxRecordLength, line.length());
                String[] fields = line.split(";");
                for (int i = 0; i < fields.length; i++) {
                    maxFieldLengths[i] = Math.max(maxFieldLengths[i], fields[i].length());
                }
            }

            System.out.println("Max record length: " + maxRecordLength);
            System.out.println("Max field lengths: " + Arrays.toString(maxFieldLengths));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPadding(String inputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(inputFilePath + "_padded"))) {
            String header = reader.readLine();
            writer.write(header + "\n");

            String line;
            int maxRecordLength = header.length();
            while ((line = reader.readLine()) != null) {
                maxRecordLength = Math.max(maxRecordLength, line.length());
            }

            reader.close();
            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                writer.write(String.format("%-" + maxRecordLength + "s\n", line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addRecord(String inputFilePath, String newRecord) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFilePath, true))) {
            writer.write(newRecord + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displaySignificantFields(String inputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String header = reader.readLine();
            System.out.println(header);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                System.out.println(fields[0] + " " + fields[1] + " " + fields[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void searchRecord(String inputFilePath, String key) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(key)) {
                    System.out.println("Record found: " + line);
                    return;
                }
            }
            System.out.println("Record not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void modifyRecord(String inputFilePath, String key, String newRecord) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(inputFilePath + "_modified"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(key)) {
                    writer.write(newRecord + "\n");
                } else {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logicalDeleteRecord(String inputFilePath, String key) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(inputFilePath + "_deleted"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(key)) {
                    writer.write(line.replace("false", "true") + "\n");
                } else {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}