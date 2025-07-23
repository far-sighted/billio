package dev.billio.endpoint.controllers;

import dev.billio.endpoint.models.InvoiceModel;
import dev.billio.endpoint.services.implementation.InvoiceService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@RestController
@RequestMapping("/api/invoice")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "Invoice", description = "Endpoints for managing invoices in the Billio application. This includes operations for creating, retrieving, updating, and deleting invoices.")
public class InvoiceController {

    //region Fields
    private final InvoiceService invoiceService;
    //region Fields

    /**
     * Constructs an InvoiceController with the specified InvoiceService.
     *
     * @param invoiceService the InvoiceService to be used by this controller
     */
    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * Handles HTTP GET requests to retrieve a paginated list of invoices.
     *
     * @param pageable the pagination information
     * @return a paginated list of InvoiceModel objects
     */
    @GetMapping
    public Page<InvoiceModel> Get(Pageable pageable) {
        return invoiceService.get(pageable);
    }

    /**
     * Handles HTTP GET requests to retrieve an invoice by its UUID.
     *
     * @param uid the UID of the invoice to be retrieved
     * @return the InvoiceModel object corresponding to the given UUID
     */
    @GetMapping("/{uid}")
    public InvoiceModel Get(@PathVariable("uid")UUID uid) {
        return invoiceService.find(uid);
    }

    /**
     * Handles HTTP POST and PUT requests to save or update an invoice.
     *
     * @param invoice the InvoiceModel object to be saved or updated
     * @return the saved InvoiceModel object
     */
    @RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.PUT})
    public InvoiceModel Save(@RequestBody InvoiceModel invoice) {
        return invoiceService.save(invoice);
    }

    /**
     * Handles HTTP DELETE requests to delete an invoice by its UUID.
     *
     * @param uid the UID of the invoice to be deleted
     */
    @DeleteMapping("/{uid}")
    public void Delete(@PathVariable("uid") UUID uid) {
        invoiceService.delete(uid);
    }

}