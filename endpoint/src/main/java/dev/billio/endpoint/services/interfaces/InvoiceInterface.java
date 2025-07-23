package dev.billio.endpoint.services.interfaces;

import dev.billio.endpoint.models.InvoiceModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InvoiceInterface {

    /**
     * Retrieves a paginated list of invoices.
     *
     * @param pageable the pagination information
     * @return a Page containing InvoiceModel objects
     */
    Page<InvoiceModel> get(Pageable pageable);

    /**
     * Finds an invoice by its UUID.
     *
     * @param uid the UUID of the invoice
     * @return the InvoiceModel object corresponding to the given UUID
     */
    InvoiceModel find(UUID uid);

    /**
     * Saves a new invoice or updates an existing invoice.
     *
     * @param invoice the InvoiceModel object to be saved or updated
     * @return the saved InvoiceModel object
     */
    InvoiceModel save(InvoiceModel invoice);

    /**
     * Deletes an invoice by its UUID.
     *
     * @param uid the UUID of the invoice to be deleted
     */
    void delete(UUID uid);
}
