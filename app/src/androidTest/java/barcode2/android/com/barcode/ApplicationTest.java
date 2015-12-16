package barcode2.android.com.barcode;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
        try {
            JSONArray kebab = new NetworkBackgroundTask().execute("http://deiauk.stud.if.ktu.lt/" + "searchProduct.php?barcode=" + "123456789").get();
            assertNotNull(kebab);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}