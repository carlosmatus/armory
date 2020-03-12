package com.demo.cm;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LogHandler {

    public static Observable<File> processLogs(final String path) throws IOException {

        Subscriber<File> subscriber = getSubscriber();

        Observable<File> observable = Observable.from(getFileList(path));
        observable.subscribe(subscriber);
        return observable;
    }

    private static List<File> getFileList(final String path) throws IOException {

        return Files
            .list(Paths.get(path))
            .filter(Files::isRegularFile)
            .map(f -> f.toFile())
            .collect(Collectors.toList());
    }

    private static Subscriber<File> getSubscriber() {

        return new Subscriber<File>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

                System.out.println("Error:" + throwable.getMessage());
            }

            @Override
            public void onNext(final File file) {

                processSubscription(file);
            }

        };
    }

    private static void processSubscription(final File file) {

        Observable.fromCallable(() -> {

            System.out.println(file.getName());
            return new Scanner(file);
        }).subscribeOn(Schedulers.newThread()).subscribe(new Observer<Scanner>() {

            @Override
            public void onCompleted() {
                System.out.println("File Log display Completed !!! ");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Scanner scanner) {

                while (scanner.hasNext()) {
                    System.out.println(scanner.nextLine());
                }

            }
        });
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        final  String path = "/Users/carlosma/dev/paso";
        processLogs(path);
        Thread.sleep(10000);
    }

}
