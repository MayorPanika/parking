package com.company;

import java.util.LinkedList;
import java.util.List;

public class TestTariffLoader implements TariffLoader {

    @Override
    public List<Tariff> load() {
        LinkedList<Tariff> tariffs = new LinkedList<>();
        Tariff tariff = new Tariff(15, 0);
        Tariff tariff1 = new Tariff(60, 100);
        Tariff tariff2 = new Tariff(180, 250);
        Tariff tariff3 = new Tariff(400, 500);
        tariffs.add(tariff);
        tariffs.add(tariff1);
        tariffs.add(tariff2);
        tariffs.add(tariff3);
        return tariffs;
    }
}
