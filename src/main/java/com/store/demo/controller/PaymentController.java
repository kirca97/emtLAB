package com.store.demo.controller;

import com.store.demo.domain.ChargeRequest;
import com.store.demo.domain.Product;
import com.store.demo.domain.Transaction;
import com.store.demo.service.ProductService;
import com.store.demo.service.StripePaymentService;
import com.store.demo.service.TransactionService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaymentController {

    private final StripePaymentService stripePaymentService;

    private final ProductService productService;

    private final TransactionService transactionService;

    public PaymentController(StripePaymentService stripePaymentService, ProductService productService, TransactionService transactionService) {
        this.stripePaymentService = stripePaymentService;
        this.productService = productService;
        this.transactionService = transactionService;
    }

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
            throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = stripePaymentService.charge(chargeRequest);
        Product product = productService.getProductById(chargeRequest.getProductId());

        Transaction transaction = new Transaction();
        transaction.setStripeId(charge.getId());
        transaction.setAmount(charge.getAmount());
        transaction.setProduct(product);
        this.transactionService.saveTransaction(transaction);

        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
