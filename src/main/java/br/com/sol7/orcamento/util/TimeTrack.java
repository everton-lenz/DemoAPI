package br.com.sol7.orcamento.util;

import com.google.common.base.Stopwatch;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public abstract class TimeTrack {


    public static <T> T trackTime(String format, Callable<T> func) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            return func.call();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            stopwatch.stop();
            System.out.println(String.format(format, stopwatch.elapsed(TimeUnit.MICROSECONDS)));
        }
    }

}
