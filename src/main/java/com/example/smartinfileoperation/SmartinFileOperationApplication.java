package com.example.smartinfileoperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

@SpringBootApplication
public class SmartinFileOperationApplication {

    public static void main(String[] args)  {
        SpringApplication.run(SmartinFileOperationApplication.class, args);

        String hostFile="error";
        String myHostFile="C:\\Users\\yasemin\\Desktop\\Smartin\\MyHost.txt";

        if(!new File(myHostFile).isFile() && !new File(myHostFile).isDirectory()) {
            try {
                throw new FileNotFoundException("MyHost.txt didnt find");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if(!new File(hostFile).isFile() && !new File(hostFile).isDirectory()) {
            try {
                throw new FileNotFoundException("host.txt didnt find");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        Path myHostFilePath = Paths.get(hostFile);
        Path hostFilePath = Paths.get(myHostFile);


        try (Stream<String> lines = Files.lines(myHostFilePath, StandardCharsets.UTF_8)) {

            Map<String, String> ipDnsMap = new LinkedHashMap<>();

            for (String line : (Iterable<String>) lines::iterator) {
                ipDnsMap.put(line.substring(line.indexOf(' ') + 1), line.substring(0, line.indexOf(' ')));
            }

            Stream<String> lines2 = Files.lines(hostFilePath, StandardCharsets.UTF_8);
            for (String line : (Iterable<String>) lines2::iterator) {
                ipDnsMap.put(line.substring(line.indexOf(' ') + 1), line.substring(0, line.indexOf(' ')));
            }
            Files.deleteIfExists(myHostFilePath);

            FileOutputStream f = new FileOutputStream(hostFile);

            PrintStream ps = new PrintStream(f);

            for (var entry : ipDnsMap.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
                ps.print(entry.getValue() + " " + entry.getKey());
                ps.println();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
