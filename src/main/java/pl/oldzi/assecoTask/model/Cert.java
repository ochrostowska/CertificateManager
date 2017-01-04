package pl.oldzi.assecoTask.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cert {

    private StringProperty id;
    private StringProperty owner;
    private StringProperty raw_bytes; //content in PEM format
    private X509Certificate encodedCertificate;
    // Cert data to display
    private StringProperty certValidBegin;
    private StringProperty certValidEnd;
    private StringProperty certCommonName;

    public Cert() {
        this.id = new SimpleStringProperty();
        this.owner = new SimpleStringProperty();
        this.raw_bytes = new SimpleStringProperty();
    }

    public Cert(String id, String owner, String raw_bytes) {
        this.id = new SimpleStringProperty(id);
        this.owner = new SimpleStringProperty(owner);
        this.raw_bytes = new SimpleStringProperty(raw_bytes);
    }

    public Cert(LinkedHashMap<String, String> certData) {
        this.id = new SimpleStringProperty(certData.get("id"));
        this.owner = new SimpleStringProperty(certData.get("owner"));
        this.raw_bytes = new SimpleStringProperty(certData.get("raw_bytes"));
    }

    public X509Certificate getEncodedCertificate() {
        return encodedCertificate;
    }

    public void setEncodedCertificate(X509Certificate encodedCertificate) {
        this.encodedCertificate = encodedCertificate;
        certValidBegin = new SimpleStringProperty(getSimpleDate(encodedCertificate.getNotBefore()));
        certValidEnd = new SimpleStringProperty(getSimpleDate(encodedCertificate.getNotAfter()));
        certCommonName = new SimpleStringProperty(extractCommonName(encodedCertificate));
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getOwner() {
        return owner.get();
    }

    public StringProperty ownerProperty() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public String getRaw_bytes() {
        return raw_bytes.get();
    }

    public StringProperty raw_bytesProperty() {
        return raw_bytes;
    }

    public void setRaw_bytes(String raw_bytes) {
        this.raw_bytes.set(raw_bytes);
    }

    public String getCertValidBegin() {
        return certValidBegin.get();
    }

    public StringProperty certValidBeginProperty() {
        return certValidBegin;
    }

    public String getCertValidEnd() {
        return certValidEnd.get();
    }

    public StringProperty certValidEndProperty() {
        return certValidEnd;
    }

    public String getCertCommonName() {
        return certCommonName.get();
    }

    public StringProperty certCommonNameProperty() {
        return certCommonName;
    }

    private String getSimpleDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return String.valueOf(c.get(Calendar.DAY_OF_MONTH)) +
                "." + String.valueOf(c.get(Calendar.MONTH)) +
                "." + String.valueOf(c.get(Calendar.YEAR));
    }

    private String extractCommonName(X509Certificate certificate) {
        Pattern commonNamePattern = Pattern.compile("CN=([^,]*),");
        Matcher m = commonNamePattern.matcher(certificate.getSubjectX500Principal().getName());
        if (m.find()) {
            return m.group(1);
        } else {
            throw new IllegalArgumentException("Unable to extract the CN attribute from " + certificate.getIssuerX500Principal().getName());
        }
    }

    public boolean checkIfEnding(Date end) {
        int noOfDays = 7; //i.e two weeks
        Calendar now = Calendar.getInstance();
        Calendar weekFromNow = Calendar.getInstance();
        weekFromNow.add(Calendar.DAY_OF_YEAR, noOfDays);
        Calendar certEnd = Calendar.getInstance();
        certEnd.setTime(end);
        return certEnd.before(weekFromNow) && certEnd.after(now);
    }

}
