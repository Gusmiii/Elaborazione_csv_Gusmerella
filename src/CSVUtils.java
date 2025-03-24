import java.io.*;
import java.util.*;

public class CSVUtils {
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
}