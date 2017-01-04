package pl.oldzi.assecoTask.util;

import com.google.common.io.Files;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.security.provider.X509Factory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class CertHelper {

    private static CertHelper ourInstance;

    public static CertHelper getInstance() {
        if (ourInstance == null) {
            ourInstance = new CertHelper();
        }
        return ourInstance;
    }

    public File choosePemFile(Stage stage) {
        return setupChooserForPEMFiles().showOpenDialog(stage);
    }

    public String getCertContentAsString(File certFile) {
        String content = "";
        try {
            content = Files.toString(certFile, Charset.defaultCharset());
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private FileChooser setupChooserForPEMFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Certificate File");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Certificate pem files (*.pem)", "*.pem");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser;
    }

    public String encodeCertContentToPEM(String certInString) {
            String certWithoutHeaders = certInString.replaceAll(X509Factory.BEGIN_CERT, "").replaceAll(X509Factory.END_CERT, "");
            String[] lines = certWithoutHeaders.split(System.getProperty("line.separator"));
            String rawCert = "";
            for (String l: lines) rawCert+=l+"\\n";
            return X509Factory.BEGIN_CERT+rawCert+X509Factory.END_CERT;
    }
}
