package com.valber.financial_control.infrastructure.api;

import com.valber.financial_control.domain.entity.Debt;
import com.valber.financial_control.domain.entity.InstallmentDebt;
import com.valber.financial_control.domain.entity.Revenue;
import com.valber.financial_control.domain.entity.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Tag(name = "Transactions", description = "API for managing financial transactions")
@RequestMapping("/api/v1/transactions")
public interface TransactionApi {

    @Operation(summary = "Add a new revenue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Revenue created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/revenues")
    ResponseEntity<Revenue> addRevenue(@RequestBody Revenue revenue);

    @Operation(summary = "Create a new debt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Debt created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/debts")
    ResponseEntity<Debt> createDebt(@RequestBody Debt debt);

    @Operation(summary = "List all transactions for a given period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping
    ResponseEntity<List<Transaction>> listTransactions(
            @RequestParam String userId,
            @RequestParam Month month,
            @RequestParam Year year
    );

    @Operation(summary = "Pay an installment of a debt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Installment paid"),
            @ApiResponse(responseCode = "404", description = "Debt not found"),
            @ApiResponse(responseCode = "400", description = "Invalid operation")
    })
    @PostMapping("/debts/{id}/pay-installment")
    ResponseEntity<InstallmentDebt> payInstallment(@PathVariable("id") String debtId);

}
