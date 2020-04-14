package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileTariffoader implements TariffLoader {
    @Override
    public List<Tariff> load() {
        List<Tariff> tariffs = new LinkedList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("tariffs.txt"));
            while (true)
            {
                String line = bufferedReader.readLine();
                if (line == null){
                    break;
                }
                String [] values = line.split(" ");
                int minutes = Integer.parseInt(values[0]);
                int rate = Integer.parseInt(values[1]);
                Tariff tariff = new Tariff(minutes, rate);
                tariffs.add(tariff);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tariffs;
    }
}
