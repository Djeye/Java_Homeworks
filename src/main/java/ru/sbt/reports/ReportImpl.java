package ru.sbt.reports;

import java.io.IOException;
import java.io.OutputStream;

public class ReportImpl implements Report {
    private final byte[] byteData;

    public ReportImpl(byte[] byteData) {
        this.byteData = byteData;
    }

    @Override
    public byte[] asBytes() {
        return byteData;
    }

    @Override
    public void writeTo(OutputStream os) {
        try {
            os.write(byteData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
