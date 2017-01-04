package pl.oldzi.assecoTask.presenter;
import javafx.collections.ObservableList;
import pl.oldzi.assecoTask.model.Cert;


public interface CertDataCommunicator extends LoginCommunicator {
    void certDataRetrieved(ObservableList<Cert> certificates);
    void certDeleted(boolean success);
    void certAdded(boolean success);
}
