package info.wikiroutes.utils.googleplaces;

import info.wikiroutes.android.commons.obj.Triple;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Ironz
 * Date: 03.10.2014, 10:08.
 * E-mail: implimentz@gmail.com
 * vk: iamironz
 */

public class GooglePlacesAddressAutocomplete {

    private List<Predictions> predictions;
    private String status;

    public void setPredictions(List<Predictions> predictions) {
        this.predictions = predictions;
    }

    public List<Predictions> getPredictions() {
        return predictions;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public boolean hasAddresses() {
        return "OK".equals(status) && predictions != null && predictions.size() > 0;
    }

    public int getPredictionsSize() {
        return predictions.size();
    }

    public Triple<String, String, String> getPrediction(int index) {
        return new Triple<>(predictions.get(index).getName(), predictions.get(index).getAddress(), predictions.get(index).getReference());
    }
}

class Predictions {

    private String reference;
    private List<Terms> terms;


    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public List<Terms> getTerms() {
        return terms;
    }

    public void setTerms(List<Terms> terms) {
        this.terms = terms;
    }

    public int tertmsCount() {
        return terms.size();
    }

    public String getAddress() {
        String string = "";
        for (int j = 1; j < tertmsCount(); j++) {
            string += getTerms().get(j).getValue() + ", ";
        }
        return string.length() != 0 ? string.substring(0, string.length() - 1) : "";
    }

    public String getName() {
        return getTerms().get(0).getValue();
    }
}

class Terms {
    private String value;

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
