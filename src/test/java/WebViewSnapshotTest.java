import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.geometry.Dimension2D;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.util.WaitForAsyncUtils.sleep;

@ExtendWith(ApplicationExtension.class)
class WebViewSnapshotTest {

    private WebViewSnapshot webViewScene;
    private Printer defaultPrinter;
    private PrinterJob job;

    @Start
    void onStart(Stage stage) {
        webViewScene = new WebViewSnapshot();
        stage.setScene(webViewScene);
        stage.show();
    }

    @Test
    void print_snapshot_of_webview(FxRobot robot) throws InterruptedException {
        WebView webView = robot.lookup(".web-view").query();
        assertNotNull(webView, "Error: no WebView node found");

        CountDownLatch latch = new CountDownLatch(1);

        run(() -> {
            webView.getEngine().getLoadWorker().stateProperty().subscribe(newState -> {
                System.out.println("WebState state: " + newState);
                if (newState == Worker.State.SUCCEEDED) {
                    //resize the view
                    Dimension2D dimensions = new Dimension2D(1080, 1920);
                    webView.setMinSize(dimensions.getWidth(), dimensions.getHeight());
                    webView.setPrefSize(dimensions.getWidth(), dimensions.getHeight());
                    webView.setMaxSize(dimensions.getWidth(), dimensions.getHeight());
                    webView.autosize();
                    latch.countDown();

                }
            });

            //reset webview and start loading
            System.out.println("Loading...");
            webView.getTransforms().clear();
            webView.getEngine().load(WebViewSnapshot.URL);
        });

        assertTrue(latch.await(10, TimeUnit.SECONDS), "Timeout");

        run(() -> {
            assertNull(webView.getEngine().getLoadWorker().getException());
            assertEquals(webView.getEngine().getLoadWorker().getState(), Worker.State.SUCCEEDED);
        });

        // print the snapshot
        run(() -> {
            defaultPrinter = Printer.getDefaultPrinter();
            assertNotNull(defaultPrinter, "Error: No printer found");
        });

        sleep(2, TimeUnit.SECONDS);

        run(() -> {
            job = PrinterJob.createPrinterJob(defaultPrinter);
            assertNotNull(job, "Error: PrinterJob was null");
            job.jobStatusProperty().subscribe(status -> System.out.println("job status = " + status));
        });

        sleep(1, TimeUnit.SECONDS);

        run(() -> {
            System.out.println("Sending to printer");
            boolean success = job.printPage(webView);
            assertTrue(success);
            boolean result = job.endJob();
            assertTrue(result);
            System.out.println("Done sending to printer");
        });

        sleep(2, TimeUnit.SECONDS);
        System.out.println("End!");
    }

    protected void run(Runnable runnable) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            runnable.run();
            countDownLatch.countDown();
        });
        try {
            Assertions.assertTrue(countDownLatch.await(10, TimeUnit.SECONDS), "Timeout");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}