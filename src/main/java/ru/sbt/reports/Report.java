package ru.sbt.reports;

import java.io.OutputStream;

interface Report {
    byte[] asBytes();

    void writeTo(OutputStream os);
}

