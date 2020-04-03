package com.store.demo.domain;

import java.util.Objects;

public class ChargeRequest {

    public enum Currency {
        EUR, USD;
    }

    private Long productId;
    private String description;
    private int amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;

    public ChargeRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargeRequest that = (ChargeRequest) o;
        return amount == that.amount &&
                Objects.equals(description, that.description) &&
                currency == that.currency &&
                Objects.equals(stripeEmail, that.stripeEmail) &&
                Objects.equals(stripeToken, that.stripeToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, amount, currency, stripeEmail, stripeToken);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getStripeEmail() {
        return stripeEmail;
    }

    public void setStripeEmail(String stripeEmail) {
        this.stripeEmail = stripeEmail;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
